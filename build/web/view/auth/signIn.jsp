<%-- 
    Document   : signIn
    Created on : Oct 15, 2021, 9:34:16 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Sign In</h1>
        
        <form action="signIn" method="POST">
            <table>
                <tr>
                    <th>Library Card:</th>
                    <td><input type="text" name="card" required/></td>
                </tr>
                <tr>
                    <th>Username:</th>
                    <td><input type="text" name="user" required/></td>
                </tr>
                <tr>
                    <th>Password:</th>
                    <td><input type="password" name="pass" required/></td>
                </tr>
                <tr>
                    <th>Confirm Password:</th>
                    <td><input type="password" name="confirmPass" required/></td>
                </tr>
                <tr>
                    <td><input type="submit" value="Sign In"/></td>
                </tr>
            </table>
        </form>
    </body>
</html>
