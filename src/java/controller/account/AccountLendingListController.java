/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.account;

import dal.BookLendingDBContext;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.BookLending;
import model_enum.LendingState;

/**
 *
 * @author DELL
 */
public class AccountLendingListController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Account a = (Account) request.getSession().getAttribute("account");
        
        String title = request.getParameter("title");
        String raw_state = request.getParameter("state");
        String raw_isFrom = request.getParameter("isFrom");
        String raw_isTo = request.getParameter("isTo");
        String raw_dueFrom = request.getParameter("dueFrom");
        String raw_dueTo = request.getParameter("dueTo");
        String raw_rtFrom = request.getParameter("rtFrom");
        String raw_rtTo = request.getParameter("rtTo");
        
        LendingState state = (raw_state == null || raw_state.equals("all")) ? null : LendingState.valueOf(raw_state);
        Date isFrom = (raw_isFrom == null || raw_isFrom.length() == 0) ? null : Date.valueOf(raw_isFrom);
        Date isTo = (raw_isTo == null || raw_isTo.length() == 0) ? null : Date.valueOf(raw_isTo);
        Date dueFrom = (raw_dueFrom == null || raw_dueFrom.length() == 0) ? null : Date.valueOf(raw_dueFrom);
        Date dueTo = (raw_dueTo == null || raw_dueTo.length() == 0) ? null : Date.valueOf(raw_dueTo);
        Date rtFrom = (raw_rtFrom == null || raw_rtFrom.length() == 0) ? null : Date.valueOf(raw_rtFrom);
        Date rtTo = (raw_rtTo == null || raw_rtTo.length() == 0) ? null : Date.valueOf(raw_rtTo);
        
        BookLendingDBContext db = new BookLendingDBContext();
        ArrayList<BookLending> lendings = db.searchUser(a.getCard().getCardNumber(), title, isFrom, isTo, dueFrom, dueTo, rtFrom, rtTo, state);

        request.setAttribute("title", title);
        request.setAttribute("state", state);
        request.setAttribute("isFrom", isFrom);
        request.setAttribute("isTo", isTo);
        request.setAttribute("dueFrom", dueFrom);
        request.setAttribute("dueTo", dueTo);
        request.setAttribute("rtFrom", rtFrom);
        request.setAttribute("rtTo", rtTo);
        request.setAttribute("lendings", lendings);
        request.setAttribute("states", LendingState.values());
        request.getRequestDispatcher("../view/account/accountLendingList.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
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
