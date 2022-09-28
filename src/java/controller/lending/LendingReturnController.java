/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.lending;

import dal.BookLendingDBContext;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookLending;

/**
 *
 * @author DELL
 */
public class LendingReturnController extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        BookLendingDBContext db = new BookLendingDBContext();
        BookLending bl = db.getLending(Integer.parseInt(id));
        request.setAttribute("lending", bl);
        request.getRequestDispatcher("../view/lending/lendingReturn.jsp").forward(request, response);
    }

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
        String id = request.getParameter("id");
        BookLendingDBContext db = new BookLendingDBContext();
        BookLending bl = db.getLending(Integer.parseInt(id));
        request.setAttribute("lending", bl);
        request.getRequestDispatcher("../view/lending/lendingReturn.jsp").forward(request, response);
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
        BookLending bl = new BookLending();
        bl.setId(Integer.parseInt(request.getParameter("id")));
        bl.setReturnDate(Date.valueOf(request.getParameter("rtDate")));
        BookLendingDBContext db = new BookLendingDBContext();
        boolean check = db.updateReturn(bl);
        if (check) {
            response.getWriter().print("Return successful!");
        } else {
            response.getWriter().print("Return failed!");
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
