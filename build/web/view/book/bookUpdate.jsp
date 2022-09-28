<%-- 
    Document   : update
    Created on : Oct 17, 2021, 8:42:42 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Update Book</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <style>
            body {
                padding-top: 60px;
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
            <h1 class="display-5 text-center mt-3 mb-4">Update Book</h1>

            <!--form update-->
            <form action="update" method="POST" class="row g-3 px-4 py-2">
                <div class="col-md-2">
                    <label for="isbn" class="form-label">ISBN:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" readonly name="isbn" value="${requestScope.book.isbn}" 
                           class="form-control" id="isbn" required>
                </div>

                <div class="col-md-2">
                    <label for="title" class="form-label">Title:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" name="title" value="${requestScope.book.title}" 
                           class="form-control" id="title" required>
                </div>

                <div class="col-md-2">
                    <label for="author" class="form-label">Author:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" name="author" value="${requestScope.book.author}" 
                           class="form-control" id="author" required>
                </div>

                <div class="col-md-2">
                    <label for="publ" class="form-label">Publication:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" name="publ" value="${requestScope.book.publication}" 
                           class="form-control" id="publ" required>
                </div>

                <div class="col-md-2">
                    <label for="cid" class="form-label">Category:</label>
                </div>
                <div class="col-md-10">
                    <select class="form-select form-select-sm" id="cid" name="cid">
                        <c:forEach items="${requestScope.categories}" var="c">
                            <option ${c.id == requestScope.book.category.id ? "selected" : ""} 
                                value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-2">
                    <label for="price" class="form-label">Price:</label>
                </div>
                <div class="col-md-10">
                    <input type="number" min="0" name="price" value="${requestScope.book.price}" 
                           class="form-control" id="price" required>
                </div>

                <div class="table-responsive">
                    <table border="1px" class="table table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>Barcode</th>
                                <th>Status</th>
                                <th>Date of Purchase</th>
                                <th>Publication date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.book.items}" var="i">
                                <tr>
                                    <td><input type="text" readonly name="barcode" value="${i.barcode}"/></td>
                                    <td><input type="text" readonly name="status" value="${i.status.value}"/></td>
                                    <td><input type="date" name="purDate" class="form-control" 
                                               value="${i.dateOfPurchase}" required/></td>
                                    <td><input type="date" name="publDate" class="form-control" 
                                               value="${i.publicationDate}" required/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Update</button>
                    <button type="button" class="btn btn-outline-primary"
                            data-bs-toggle="modal" data-bs-target="#cancel">Cancel</button>
                </div>
            </form>
        </div>

        <!--Cancel confirm-->
        <div class="modal fade" id="cancel" tabindex="-1" aria-labelledby="logoutLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-body">
                        <p>Are you sure you want to cancel?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary"
                                onclick="window.location.href = 'list'">Yes</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">No</button>
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
