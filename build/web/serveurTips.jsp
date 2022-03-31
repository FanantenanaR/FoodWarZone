
<%@page import="java.util.ArrayList"%>
<%@page import="entity.ServeurTips"%>
<%@page import="entity.Categorie"%>
<%
      ArrayList<ServeurTips> listeTips = (ArrayList<ServeurTips>) request.getAttribute("list");
//      listeTips.add(new ServeurTips(150, "Rakoto"));
//      listeTips.add(new ServeurTips(150, "Rasoa"));
      
      
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
                    <li class="nav-item"><a class="nav-link active" href="serveurTips.jsp">Liste des pourboires</a></li>
                </ul>
            </div>
        </div>
    </nav>
    <main class="page testimonials">
        <section class="clean-block clean-testimonials dark">
            <div class="container" style="padding: 20px;margin-top: 20px;">
                <div>
                    <h1 style="text-align: center;border-color: var(--bs-blue);color: var(--bs-blue);">Liste des Pourboires</h1>
                    <hr>
                </div>
                <div>
                    <h5 class="text-center h4 text-info mb-4">Filtrer</h5>
                    <form style="margin-bottom: 15px;" action="TipsController" class="row g-3" method="POST">
                        <div class="col-md-1">
                            <label for="staticEmail2" class="visually-hidden">Date debut</label>
                            <input type="text" class="form-control-plaintext" id="staticEmail2" value="Entre">
                        </div>
                        <div class="col-md-3">
                            <label for="inputPassword2" class="visually-hidden">Password</label>
                            <input type="date" name="datedebut" class="form-control" id="inputPassword2" placeholder="Date debut">
                        </div>
                        <div class="col-md-1 ">
                            <label for="staticEmail2" class="visually-hidden"></label>
                            <input type="text" class="form-control-plaintext text-center" id="staticEmail2" value="et">
                        </div>
                        <div class="col-md-3">
                            <label for="inputPassword2" class="visually-hidden">Password</label>
                            <input type="date" name="datefin" class="form-control" id="inputPassword2" placeholder="Date debut">
                        </div>
                        
                          <button type="submit" class="btn btn-primary mb-3 col-md-2">Filtrer</button>
                        
                        
                    </form>
                    <hr>
                </div>
                <div>
                    <div class="table-responsive mt-4">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Nom du Serveur</th>
                                    <th>Montant pour boire</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%if (listeTips!=null) { %>
                                <% for (int i = 0; i < listeTips.size(); i++) { %>
                                    <tr>
                                        <td><%= listeTips.get(i).getNomServeur()  %></td>
                                        <td><%= listeTips.get(i).getMontantTips() %></td>
                                    </tr>
                                <% } %>
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