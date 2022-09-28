<%-- 
    Document   : home
    Created on : Oct 15, 2021, 10:07:47 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home</title>
        <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <style>
            body {
                padding-top: 60px;
            }

            .underline {
                text-decoration: underline;
                text-decoration-color: orange;
            }

            .icon {
                font-size: 0.2em;
            }

            .card {
                height: 170px;
            }

            .card-body {
                font-style: italic;
            }

            .card-title {
                color: #3a86ff;
                font-style: normal;
            }
        </style>
    </head>
    <body>

        <!--navbar-->
        <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top" aria-label="Third navbar example">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="home">Home</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="book" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Book Dashboard
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="book">
                                <li><a class="dropdown-item" href="book/list">Book</a></li>
                                <li><a class="dropdown-item" href="bookItem/insert">Add Book</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="lending" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Lending Dashboard
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="lending">
                                <li><a class="dropdown-item" href="lending/list">Book Lending</a></li>
                                <li><a class="dropdown-item" href="lending/insert">Add Book Lending</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="account" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Account
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="account">
                                <li><a class="dropdown-item" href="account/view">Profile</a></li>
                                <li><a class="dropdown-item" href="account/lendingList">History</a></li>
                            </ul>
                        </li>
                    </ul>
                    <button type="button" class="btn btn-outline-secondary me-2"
                            onclick="window.location.href = 'login'">Login</button>
                    <button type="button" class="btn btn-outline-secondary"
                            data-bs-toggle="modal" data-bs-target="#logout">Logout</button>
                </div>
            </div>
        </nav>

        <!--logout confirm-->
        <div class="modal fade" id="logout" tabindex="-1" aria-labelledby="logoutLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="logoutLabel">Log Out</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure you want to log-off?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary"
                                onclick="window.location.href = 'logout'">Logout</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>

        <!--search-->
        <div class="p-5 mb-4 bg-img" style="background-image: url('https://www.telehouse.com/wp-content/uploads/2016/01/Total-Customer-satisfaction-people.jpg');">
            <div class="container-fluid p-5 rounded-3 mt-4" style="width:fit-content; background-color:white;">
                <h2 class="display-5 fw-bold">What can we help you find?</h2>
                <p class="fs-4">Search across the titles, authors and so much more</p>
                <form action="home" method="POST" class="row g-2 align-items-center justify-content-center">
                    <div class="col-auto">
                        <input type="text" name="key" class="form-control">
                    </div>
                    <div class="col-auto">
                        <button class="btn btn-primary btn-lg" type="submit">Search</button>
                    </div>
                </form>        
            </div>
        </div>

        <!--news & popular-->
        <div class="row">
            <div class="col-12 col-md-6 px-5 p-md-5">
                <div class="d-flex pb-1 justify-content-between align-items-center">
                    <div class="me-1">
                        <h2 class="fs-2 underline fst-italic">New Arriavals</h2>
                    </div>
                    <div class="row">
                        <div class="col-1 col-md-3">
                            <button class="btn btn-primary res" type="button" data-bs-target="#news" data-bs-slide="prev">
                                <i class="fa fa-arrow-left"></i>
                            </button>
                        </div>
                        <div class="col-1 col-md-3">

                            <button class="btn btn-primary" type="button" data-bs-target="#news" data-bs-slide="next">
                                <i class="fa fa-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div id="news" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner row">
                        <c:forEach items="${requestScope.news}" var="b" varStatus="status">
                            <div class="carousel-item mb-4 ${status.first ? 'active' : ''}">
                                <div class="card text-dark border-dark">
                                    <div class="card-body">
                                        <p class="card-title fs-4 fw-bold">${b.title}</p>
                                        <p class="card-text">${b.author}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="col-12 col-md-6 px-5 p-md-5">
                <div class="d-flex pb-1 justify-content-between align-items-center">
                    <h2 class="fs-2 underline fst-italic">Most Popular</h2>
                    <div class="row">
                        <div class="col-1 col-md-3">
                            <button class="btn btn-primary res" type="button" data-bs-target="#popular" data-bs-slide="prev">
                                <i class="fa fa-arrow-left"></i>
                            </button>
                        </div>
                        <div class="col-1 col-md-3">

                            <button class="btn btn-primary" type="button" data-bs-target="#popular" data-bs-slide="next">
                                <i class="fa fa-arrow-right"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div id="popular" class="carousel slide" data-bs-ride="carousel">
                    <div class="carousel-inner row">
                        <c:forEach items="${requestScope.popular}" var="b" varStatus="status">
                            <div class="carousel-item mb-4 ${status.first ? 'active' : ''}">
                                <div class="card text-dark border-dark">
                                    <div class="card-body">
                                        <p class="card-title fs-4 fw-bold">${b.title}</p>
                                        <p class="card-text">${b.author}</p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <!--library card-->
        <div class="p-5 mb-4 bg-img" style="background-image: url('https://www.indypl.org/uploads/callouts/_2000x600_crop_center-center/CTA_GetLibraryCard_v2.jpg');">
            <div class="container-fluid p-5 rounded-3 me-4" style="width:fit-content; background-color:white;">
                <h2 class="display-5 fw-bold">Get a Library Card</h2>
                <p class="fs-4">Enjoy books right now!</p>
                <button class="btn btn-primary btn-lg"  type="submit"
                        onclick="window.location.href = 'getCard'">Learn more</button>
            </div>
        </div>

        <!--ask-->
        <div class="p-5 mb-4 bg-img" style="background-image: url('https://www.indypl.org/uploads/callouts/_2000x600_crop_center-center/CTA_InterLoanflip.jpg');">
            <div class="container-fluid p-5 rounded-3 ms-4" style="width:fit-content; background-color:white;">
                <h2 class="display-5 fw-bold">Need help? Ask-a-Librarian!</h2>
                <p class="fs-4">Get answers to reference questions, assistance.</p>
                <div class="row g-2 align-items-center justify-content-center">
                    <div class="col-auto">
                        <input type="text" name="key" class="form-control">
                    </div>
                    <div class="col-auto">
                        <button class="btn btn-primary btn-lg"  type="submit"
                                onclick="window.location.href = 'contact'">Call, Text, or Email Us</button>
                    </div>
                </div>
            </div>
        </div>

        <!--footer-->
        <footer class="bg-dark text-center text-white">
            <!-- Grid container -->
            <div class="container p-4 pb-0">
                <!-- Section: Social media -->
                <section class="mb-4">
                    <!-- Facebook -->
                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button">
                        <i class="fa fa-facebook-f"></i>
                    </a>

                    <!-- Twitter -->
                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button">
                        <i class="fa fa-twitter"></i>
                    </a>

                    <!-- Google -->
                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button">
                        <i class="fa fa-google"></i>
                    </a>

                    <!-- Instagram -->
                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button">
                        <i class="fa fa-instagram"></i>
                    </a>

                    <!-- Linkedin -->
                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button">
                        <i class="fa fa-linkedin"></i>
                    </a>

                    <!-- Github -->
                    <a class="btn btn-outline-light btn-floating m-1" href="#!" role="button">
                        <i class="fa fa-github"></i>
                    </a>
                </section>
                <!-- Section: Social media -->
            </div>
            <!-- Grid container -->

            <!-- Copyright -->
            <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
                © 2020 Copyright:
            </div>
            <!-- Copyright -->
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    </body>
</html>
