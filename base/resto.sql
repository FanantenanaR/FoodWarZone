create database resto;

create table categorie(
	id serial,
	nom varchar(20),
	primary key(id)
);

create table plat(
	id serial,
	idCat int,
	nom varchar(50),
	prix integer,
	primary key(id),
	foreign key(idCat) references categorie(id)
);

insert into categorie(nom) values('Entree');
insert into categorie(nom) values('Resistance');
insert into categorie(nom) values('Dessert');

insert into plat(idCat,nom,prix) values(1,'Salade de pate',2500);
insert into plat(idCat,nom,prix) values(1,'Feuillete sauce blanche',4000);
insert into plat(idCat,nom,prix) values(2,'Henakisoa & Anana',5000);
insert into plat(idCat,nom,prix) values(2,'Akoho rony',4500);
insert into plat(idCat,nom,prix) values(3,'Banane flambe',3000);
insert into plat(idCat,nom,prix) values(3,'Salade de fruits',2500);	


create view vPlat as select plat.id as idPlat, categorie.id as idCategorie, categorie.nom as nomCat, plat.nom as nomPlat,prix from plat join categorie on plat.idCat = categorie.id;

select * from vPlat;

drop view vtips;
drop view vcommande;
drop view vmontantcommande;
drop table commandefille;
drop table commandemere; 

create table serveur (
    id serial primary key,
    nom varchar(120)
);

insert into serveur (id, nom) values (1, 'Rakoto');
insert into serveur (id, nom) values (2, 'Rasoa');

create table commandemere (
    id serial primary key,
    dateheure TIMESTAMP DEFAULT CURRENT_DATE
);

insert into commandemere (id, dateheure) values (1,  '2022-03-21 08:56');
insert into commandemere (id, dateheure) values (2,  '2022-03-22 08:56');
insert into commandemere (id, dateheure) values (3,  '2022-03-23 08:56');
insert into commandemere (id, dateheure) values (4,  '2022-03-23 10:56');

insert into commandemere (id, dateheure) values (5,  '2022-03-21 08:56');
insert into commandemere (id, dateheure) values (6,  '2022-03-22 08:56');
insert into commandemere (id, dateheure) values (7,  '2022-03-23 08:56');
insert into commandemere (id, dateheure) values (8,  '2022-03-23 10:56');


create table commandefille (
    id serial primary key,
    idcommandemere int references commandemere (id),
    idplat int references plat (id),
    idserveur int references serveur (id),
    prix double precision default 0
);

insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (1, 1, 1, 1, 2500);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (2, 2, 2, 1, 4000);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (3, 3, 3, 1, 5000);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (4, 4, 4, 1, 4500);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (5, 5, 5, 2, 3000);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (6, 6, 4, 2, 4500);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (7, 7, 6, 2, 2500);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (8, 8, 6, 2, 2500);
insert into commandefille (id, idcommandemere, idplat, idserveur, prix) values (9, 8, 6, 1, 2500);






create or replace view vmontantcommande as
    select idserveur ,idcommandemere, sum(prix) as montanttotal
    from commandefille GROUP BY idcommandemere, idserveur;

create or replace view vcommande as 
    select commandemere.id as idcommandemere,  
            vmontantcommande.idserveur,
            serveur.nom as nomserveur,
            dateheure,
            vmontantcommande.montanttotal,
            vmontantcommande.montanttotal * 0.02 as tips
    from commandemere 
        join vmontantcommande on vmontantcommande.idcommandemere = commandemere.id 
        join serveur on serveur.id = vmontantcommande.idserveur;

create or replace view vtips as
    select idserveur, sum(tips) as montanttips, nomserveur from vcommande group by idserveur, nomserveur ;

select idserveur, sum(tips) as montanttips, nomserveur from vcommande where dateheure between '2022-03-21 08:56' and '2022-03-23 08:56' group by idserveur, nomserveur;

select idserveur, sum(tips) as montanttips, nomserveur from vcommande where dateheure between {d1} and {d2} group by idserveur, nomserveur ;

