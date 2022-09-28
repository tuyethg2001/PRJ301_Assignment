<%-- 
    Document   : update
    Created on : Oct 18, 2021, 11:26:01 AM
    Author     : DELL
--%>

<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Update Profile</title>
        <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon">
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
                                <li><a class="dropdown-item" href="../book/list">Book</a></li>
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
                                <li><a class="dropdown-item" href="view">Profile</a></li>
                                <li><a class="dropdown-item" href="lendingList">History</a></li>
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
                                onclick="window.location.href = '../logout'">Logout</button>
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>

        <!--content-->
        <div class="p-5 mb-4">
            <h1 class="display-5 text-center mb-4">Update Profile</h1>

            <form  action="update" method="POST" class="row g-3 px-4 py-2">
                <div class="col-md-2">
                    <label for="name" class="form-label">Name:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" name="name" value="${requestScope.account.card.name}" 
                           class="form-control" id="name" required>
                </div>

                <div class="col-md-2">
                    <p class="form-label">Gender:</p>
                </div>
                <div class="form-check col-md-5">
                    <input class="form-check-input" type="radio" name="gender" id="male"
                           required ${requestScope.account.card.gender ? "checked" : ""}>
                    <label class="form-check-label" for="male">
                        Male
                    </label>
                </div>
                <div class="form-check col-md-5">
                    <input class="form-check-input" type="radio" name="gender" id="female"
                           ${!requestScope.account.card.gender ? "checked" : ""}>
                    <label class="form-check-label" for="male" required>
                        Female
                    </label>
                </div>

                <div class="col-md-2">
                    <label for="dob" class="form-label">Date of birth:</label>
                </div>
                <div class="col-md-10">
                    <input type="date" name="dob" value="${requestScope.account.card.dob}" 
                           class="form-control" id="dob" required>
                </div>

                <div class="col-md-2">
                    <label for="email" class="form-label">Email:</label>
                </div>
                <div class="col-md-10">
                    <input type="email" name="email" value="${requestScope.account.card.email}" 
                           class="form-control" id="email" required>
                </div>

                <div class="col-md-2">
                    <label for="phone" class="form-label">Phone:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" name="phone" value="${requestScope.account.card.phone}" 
                           class="form-control" id="phone" required>
                </div>

                <div class="col-md-2">
                    <label for="address" class="form-label">Address:</label>
                </div>
                <div class="col-md-10">
                    <input type="text" min="0" name="address" value="${requestScope.account.card.address}" 
                           class="form-control" id="address" required>
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
                                onclick="window.location.href = 'view'">Yes</button>
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
