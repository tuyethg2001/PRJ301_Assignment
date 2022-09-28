/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.lending;

import dal.BookLendingDBContext;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookLending;
import model_enum.LendingState;

/**
 *
 * @author DELL
 */
public class LendingListController extends HttpServlet {

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
        String card = request.getParameter("card");
        String title = request.getParameter("title");
        String raw_from = request.getParameter("from");
        String raw_to = request.getParameter("to");
        String raw_state = request.getParameter("state");
        
        Date from = (raw_from == null || raw_from.length() == 0) ? null : Date.valueOf(raw_from);
        Date to = (raw_to == null || raw_to.length() == 0) ? null : Date.valueOf(raw_to);
        LendingState state = (raw_state == null || raw_state.equals("all")) ? null : LendingState.valueOf(raw_state);
        
        BookLendingDBContext db = new BookLendingDBContext();
        ArrayList<BookLending> lendings = db.search(card, title, from, to, state);
        
        request.setAttribute("card", card);
        request.setAttribute("title", title);
        request.setAttribute("from", from);
        request.setAttribute("to", to);
        request.setAttribute("state", state);
        request.setAttribute("states", LendingState.values());
        request.setAttribute("lendings", lendings);
        request.getRequestDispatcher("../view/lending/lendingList.jsp").forward(request, response);
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