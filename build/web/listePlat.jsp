
<%@page import="java.util.ArrayList"%>
<%@page import="entity.Vplat"%>
<%@page import="entity.Categorie"%>
<%
      ArrayList<Vplat> listePlat = (ArrayList<Vplat>) request.getAttribute("list");
      ArrayList<Categorie> listeCategorie = (ArrayList<Categorie>)request.getAttribute("listCategorie");
      
%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Liste des plats - FoodWar</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css?h=179982c95cf5b5aa564f948733aa5617">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Alex+Brush">
    <link rel="stylesheet" href="assets/fonts/simple-line-icons.min.css?h=a436bee1e5ae414f98db7ca13adfd7c0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/vanilla-zoom.min.css?h=e737f76df021b46fa7180f48799a5d20">
</head>

<body>
    <nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar" style="box-shadow: 0px 4px 8px -7px;">
        <div class="container"><a class="navbar-brand logo" href="#" style="font-family: 'Alex Brush', cursive;">FoodWar</a><button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link active" href="ListPlatController">Liste des plats</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <main class="page testimonials">
        <section class="clean-block clean-testimonials dark">
            <div class="container" style="padding: 20px;margin-top: 20px;">
                <div>
                    <h1 style="text-align: center;border-color: var(--bs-blue);color: var(--bs-blue);">Liste des plats</h1>
                </div>
                <div>
                    <form style="margin-bottom: 15px;" action="ListPlatCategorieController" method="POST">
                        <label class="form-label">Catégorie:</label>
                        <div class="row">
                            <div class="col-auto">
                                <select class="form-select" name="idcategorie">
                                    <option value="0" selected="">Toutes categorie</option>
                                    <% for(int i=0; i<listeCategorie.size(); i++ ) {  %>
                                        <option value="<%= listeCategorie.get(i).getId()%>"><%= listeCategorie.get(i).getNom() %></option>
                                    <% } %>
                                </select>
                            </div>
                            <div class="col-auto">
                                <button class="btn btn-primary" type="submit">Filtrer</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div>
                    <div class="table-responsive mt-4">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Nom du plat</th>
                                    <th>Categorie</th>
                                    <th>Prix</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (int i = 0; i < listePlat.size(); i++) { %>
                                    <tr>
                                        <td><%= listePlat.get(i).getNomPlat()  %></td>
                                        <td><%= listePlat.get(i).getNomCat() %></td>
                                        <td><%= listePlat.get(i).getPrix() %></td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </main>
    <script src="assets/bootstrap/js/bootstrap.min.js?h=0de0a05340ecfd010938b6967a030757"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
    <script src="assets/js/vanilla-zoom.js?h=6a37ea9c996b05f763161c73127d66bc"></script>
    <script src="assets/js/theme.js?h=a083aeb15550c5e1266c666e8a5846d9"></script>
</body>

</html>