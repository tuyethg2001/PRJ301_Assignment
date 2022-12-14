<%-- 
    Document   : list
    Created on : Oct 15, 2021, 10:04:24 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Book</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <style>
            body {
                padding-top: 60px;
            }

            table {
                counter-reset: rowNumber;
            }

            table tr td:first-child::before {
                counter-increment: rowNumber;
                content: counter(rowNumber);
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
                            <a class="nav-link active" aria-current="page" href="../home">Home</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="book" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Book Dashboard
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="book">
                                <li><a class="dropdown-item" href="list">Book</a></li>
                                <li><a class="dropdown-item" href="../bookItem/insert">Add Book</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="lending" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Lending Dashboard
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="lending">
                                <li><a class="dropdown-item" href="../lending/list">Book Lending</a></li>
                                <li><a class="dropdown-item" href="../lending/insert">Add Book Lending</a></li>
                            </ul>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" id="account" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Account
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="account">
                                <li><a class="dropdown-item" href="../account/view">Profile</a></li>
                                <li><a class="dropdown-item" href="../account/lendingList">History</a></li>
                            </ul>
                        </li>
                    </ul>
                    <button type="button" class="btn btn-outline-secondary me-2"
                            onclick="window.location.href = '../login'">Login</button>
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

        <!--content-->
        <div class="p-2">
            <!--form search-->
            <form action="list" class="row g-3 px-4 py-2">
                <div class="col-md-1">
                    <label for="key" class="form-label">Key search for:</label>
                </div>
                <div class="col-md-11">
                    <input type="text" name="key" value="${requestScope.key}" 
                           class="form-control" id="key">
                </div>
                <div class="col-md-1">
                    <label for="searchBy" class="form-label">Search by:</label>
                </div>
                <div class="col-md-5">
                    <select class="form-select form-select-sm" id="searchBy" name="searchBy">
                        <option ${requestScope.searchBy eq "all" ? "selected" : ""}
                            value="all">Any Field</option>
                        <option ${requestScope.searchBy eq "title" ? "selected" : ""}
                            value="title">Title</option>
                        <option ${requestScope.searchBy eq "author" ? "selected" : ""}
                            value="author">Author</option>
                        <option ${requestScope.searchBy eq "publ" ? "selected" : ""}
                            value="publ">Publication</option>
                    </select>
                </div>

                <div class="col-md-1">
                    <label for="cid" class="form-label">Category:</label>
                </div>
                <div class="col-md-5">
                    <select class="form-select form-select-sm" id="cid" name="cid">
                        <option ${requestScope.cid == null ? "" : "selected"}
                            value="-1">All</option>
                        <c:forEach items="${requestScope.categories}" var="c">
                            <option ${requestScope.cid == c.id ? "selected" : ""} 
                                value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
            <br/>

            <!--list-->
            <div class="table-responsive">
                <table border="1px" class="table table-hover">
                    <thead class="table-dark">
                        <tr>
                            <th>No</th>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Publication</th>
                            <th>Category</th>
                            <th>Detail</th>
                            <th></th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.books}" var="b">
                            <tr>
                                <td class="text-center"></td>
                                <td>${b.title}</td>
                                <td>${b.author}</td>
                                <td>${b.publication}</td>
                                <td>${b.category.name}</td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-outline-secondary btn-sm"
                                            onclick="window.location.href = 'detail?isbn=${b.isbn}'">View</button>
                                </td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-outline-primary btn-sm"
                                            onclick="window.location.href = 'update?isbn=${b.isbn}'">Update</button>
                                </td>
                                <td class="text-center">
                                    <button type="button" class="btn btn-danger btn-sm px-2"
                                            onclick="window.location.href = 'delete?isbn=${b.isbn}'">
                                        <i class="fa fa-close"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
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
                ?? 2020 Copyright:
            </div>
            <!-- Copyright -->
        </footer>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

    </body>
</html>
