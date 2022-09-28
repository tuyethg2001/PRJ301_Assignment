/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.authentication;

import dal.AccountDBContext;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.LibraryCard;

/**
 *
 * @author DELL
 */
public class SignInController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("view/auth/signIn.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");
        String confirmPass = request.getParameter("confirmPass");
        
        if (!pass.equals(confirmPass)) {
            response.getWriter().println("Your password and confirm password do not match!");
            return;
        }
        
        LibraryCard lc = new LibraryCard();
        lc.setCardNumber(request.getParameter("card"));
        
        Account a = new Account();
        a.setUsername(user);
        a.setPassword(pass);
        a.setCard(lc);
        
        AccountDBContext db = new AccountDBContext();
        boolean check = db.insert(a);
        if (check) {
            response.getWriter().println("Sign in successful!");
        } else {
            response.getWriter().println("Sign in failed!");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
