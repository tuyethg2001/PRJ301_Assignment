/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.account;

import dal.AccountDBContext;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 *
 * @author DELL
 */
public class PasswordController extends HttpServlet {

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
        Account a = (Account) request.getSession().getAttribute("account");
        request.setAttribute("account", a);
        request.getRequestDispatcher("../view/account/changePass.jsp").forward(request, response);
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
        String currentPass = request.getParameter("current");
        String newPass = request.getParameter("new");
        String confirmPass = request.getParameter("confirm");

        Account a = (Account) request.getSession().getAttribute("account");
        if (!currentPass.equals(a.getPassword())) {
            response.getWriter().println("Your current password is incorrect!");
        } else if (!newPass.equals(confirmPass)) {
            response.getWriter().println("New Password and Confirm Password do not match, please try again!");
        } else if (currentPass.equals(newPass)) {
            response.getWriter().println("Your new password can not be the same as your current password. "
                    + "Please choose a new password!");
        } else {
            a.setPassword(newPass);
            AccountDBContext db = new AccountDBContext();
            db.updatePass(a);
            response.getWriter().println("Change Password successful!");
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
