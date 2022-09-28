/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.account;

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
public class AccountUpdateController extends HttpServlet {

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
        request.getRequestDispatcher("../view/account/accountUpdate.jsp").forward(request, response);
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
        LibraryCard lc = new LibraryCard();
        lc.setName(request.getParameter("name"));
        lc.setGender(request.getParameter("gender").equals("male"));
        lc.setDob(Date.valueOf(request.getParameter("dob")));
        lc.setEmail(request.getParameter("email"));
        lc.setPhone(request.getParameter("phone"));
        lc.setAddress(request.getParameter("address"));

        Account a = (Account) request.getSession().getAttribute("account");
        a.setCard(lc);

        AccountDBContext db = new AccountDBContext();
        db.updateProfile(a);
        response.getWriter().println("Update successful!");
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
