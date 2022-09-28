<%-- 
    Document   : insert
    Created on : Oct 17, 2021, 10:59:43 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Add Book</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">

        <style>
            body {
                padding-top: 60px;
            }

            .autocomplete {
                /*the container must be positioned relative:*/
                position: relative;
                display: inline-block;
            }

            .autocomplete-items {
                position: absolute;
                border: 1px solid #d4d4d4;
                border-bottom: none;
                border-top: none;
                z-index: 99;
                /*position the autocomplete items to be the same width as the container:*/
                top: 100%;
                left: 0;
                right: 0;
            }

            .autocomplete-items div {
                padding: 10px;
                cursor: pointer;
                background-color: #fff;
                border-bottom: 1px solid #d4d4d4;
            }

            .autocomplete-items div:hover {
                /*when hovering an item:*/
                background-color: #e9e9e9;
            }

            .autocomplete-active {
                /*when navigating through the items using the arrow keys:*/
                background-color: DodgerBlue !important;
                color: #ffffff;
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
            <h1 class="display-5 text-center mt-3 mb-4">Add Book</h1>

            <!--form add-->
            <form action="insert" method="POST" class="row g-3 px-4 py-2" autocomplete="off">
                <table id="wrapper" class="table table-borderless">
                    <tr>
                        <th>ISBN:</th>
                        <td>
                            <div class="autocomplete w-100">
                                <input type="text" name="isbn" 
                                       class="form-control" id="isbn" required>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>Title:</th>
                        <td><input type="text" class="form-control" name="title" id="title" required/></td>
                    </tr>
                    <tr>
                        <th>Author:</th>
                        <td><input type="text" class="form-control" name="author" id="author" required/></td>
                    </tr>
                    <tr>
                        <th>Publication:</th>
                        <td><input type="text" class="form-control" name="publ" id="publ" required/></td>
                    </tr>
                    <tr>
                        <th>Category:</th>
                        <td>
                            <select class="form-select form-select-sm" name="cid" id="cid">
                                <c:forEach items="${requestScope.categories}" var="c">
                                    <option value="${c.id}">${c.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Price:</th>
                        <td><input type="text" class="form-control" name="price" id="price" required/></td>
                    </tr>
                    <tr>
                        <th>Barcode:</th>
                        <td>
                            <div class="col-6 w-100">
                                <input type="text" name="barcode" class="form-control" required>
                                <input type="date" name="purDate" class="form-control" required/>
                                <input type="date" name="publDate" class="form-control"  required/>
                            </div>
                        </td>
                        <td></td>
                    </tr>
                </table>
                <div class="col-12">
                    <button type="button" class="btn btn-outline-success btn-sm"
                            onclick="add(content)"><i class="fa fa-plus"></i></button>
                </div>

                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Add</button>
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
                                onclick="window.location.href = '../home'">Yes</button>
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

        <script>
                                    //            autocomplete
                                    function autocomplete(inp, arr) {
                                        /*the autocomplete function takes two arguments,
                                         the text field element and an array of possible autocompleted values:*/
                                        var currentFocus;
                                        /*execute a function when someone writes in the text field:*/
                                        inp.addEventListener("input", function (e) {
                                            var a, b, i, val = this.value;
                                            /*close any already open lists of autocompleted values*/
                                            closeAllLists();
                                            if (!val) {
                                                return false;
                                            }
                                            currentFocus = -1;
                                            /*create a DIV element that will contain the items (values):*/
                                            a = document.createElement("DIV");
                                            a.setAttribute("id", this.id + "autocomplete-list");
                                            a.setAttribute("class", "autocomplete-items");
                                            /*append the DIV element as a child of the autocomplete container:*/
                                            this.parentNode.appendChild(a);
                                            /*for each item in the array...*/
                                            for (i = 0; i < arr.length; i++) {
                                                /*check if the item starts with the same letters as the text field value:*/
                                                if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                                                    /*create a DIV element for each matching element:*/
                                                    b = document.createElement("DIV");
                                                    /*make the matching letters bold:*/
                                                    b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                                                    b.innerHTML += arr[i].substr(val.length);
                                                    /*insert a input field that will hold the current array item's value:*/
                                                    b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                                                    /*execute a function when someone clicks on the item value (DIV element):*/
                                                    b.addEventListener("click", function (e) {
                                                        /*insert the value for the autocomplete text field:*/
                                                        var content = this.getElementsByTagName("input")[0].value.split('_');
                                                        inp.value = content[0];
                                                        /*close the list of autocompleted values,
                                                         (or any other open lists of autocompleted values:*/
                                                        closeAllLists();
                                                    });
                                                    a.appendChild(b);
                                                }
                                            }
                                        });
                                        /*execute a function presses a key on the keyboard:*/
                                        inp.addEventListener("keydown", function (e) {
                                            var x = document.getElementById(this.id + "autocomplete-list");
                                            if (x)
                                                x = x.getElementsByTagName("div");
                                            if (e.keyCode == 40) {
                                                /*If the arrow DOWN key is pressed,
                                                 increase the currentFocus variable:*/
                                                currentFocus++;
                                                /*and and make the current item more visible:*/
                                                addActive(x);
                                            } else if (e.keyCode == 38) { //up
                                                /*If the arrow UP key is pressed,
                                                 decrease the currentFocus variable:*/
                                                currentFocus--;
                                                /*and and make the current item more visible:*/
                                                addActive(x);
                                            } else if (e.keyCode == 13) {
                                                /*If the ENTER key is pressed, prevent the form from being submitted,*/
                                                e.preventDefault();
                                                if (currentFocus > -1) {
                                                    /*and simulate a click on the "active" item:*/
                                                    if (x)
                                                        x[currentFocus].click();
                                                }
                                            }
                                        });
                                        function addActive(x) {
                                            /*a function to classify an item as "active":*/
                                            if (!x)
                                                return false;
                                            /*start by removing the "active" class on all items:*/
                                            removeActive(x);
                                            if (currentFocus >= x.length)
                                                currentFocus = 0;
                                            if (currentFocus < 0)
                                                currentFocus = (x.length - 1);
                                            /*add class "autocomplete-active":*/
                                            x[currentFocus].classList.add("autocomplete-active");
                                        }
                                        function removeActive(x) {
                                            /*a function to remove the "active" class from all autocomplete items:*/
                                            for (var i = 0; i < x.length; i++) {
                                                x[i].classList.remove("autocomplete-active");
                                            }
                                        }
                                        function closeAllLists(elmnt) {
                                            /*close all autocomplete lists in the document,
                                             except the one passed as an argument:*/
                                            var x = document.getElementsByClassName("autocomplete-items");
                                            for (var i = 0; i < x.length; i++) {
                                                if (elmnt != x[i] && elmnt != inp) {
                                                    x[i].parentNode.removeChild(x[i]);
                                                }
                                            }
                                        }
                                        /*execute a function when someone clicks in the document:*/
                                        document.addEventListener("click", function (e) {
                                            closeAllLists(e.target);
                                        });
                                    }

                                    // delete row
                                    function deleteRow(o) {
                                        var p = o.parentNode.parentNode;
                                        p.parentNode.removeChild(p);
                                    }

                                    // add row
                                    function add(content) {
                                        var table = document.getElementById("wrapper");
                                        var row = table.insertRow(-1);
                                        var cell1 = row.insertCell(0);
                                        cell1.innerHTML = "";
                                        var index = 1;
                                        for (var i = 0; i < content.length; i++, index++) {
                                            var cell = row.insertCell(index);
                                            cell.innerHTML = content[i];
                                        }
                                        var cell2 = row.insertCell(index);
                                        cell2.innerHTML = "<button type=\"button\" class=\"btn btn-danger btn-sm px-2\" " +
                                                "onclick=\"deleteRow(this)\"><i class=\"fa fa-close\"></i></button>";
                                    }

                                    var list = [
            <c:forEach items="${requestScope.books}" var="b">
                                        '<c:out value="${b.isbn}_${b.title}"/>',
            </c:forEach>
                                    ];
                                    autocomplete(document.getElementById("isbn"), list);

                                    var content = ["<input type=\"text\" name=\"barcode\" class=\"form-control\"  id=\"barcode\" required/><input type=\"date\" name=\"purDate\" class=\"form-control\" required/><input type=\"date\" name=\"publDate\" class=\"form-control\"  required/>"];
        </script> 
    </body>
</html>
