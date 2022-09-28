/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.lending;

import dal.AccountDBContext;
import dal.BookItemDBContext;
import dal.BookLendingDBContext;
import dal.LibraryCardDBContext;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import model.BookItem;
import model.BookLending;
import model.LibraryCard;

/**
 *
 * @author DELL
 */
public class LendingInsertController extends HttpServlet {

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
        LibraryCardDBContext lcdb = new LibraryCardDBContext();
        ArrayList<LibraryCard> cards = lcdb.getCards();

        BookItemDBContext bidb = new BookItemDBContext();
        ArrayList<BookItem> items = bidb.getBookItems();

        request.setAttribute("cards", cards);
        request.setAttribute("items", items);
        request.getRequestDispatcher("../view/lending/lendingInsert.jsp").forward(request, response);
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
        String cardNumber = request.getParameter("card");
        Date issueDate = Date.valueOf(request.getParameter("issueDate"));

        LibraryCard lc = new LibraryCard();
        lc.setCardNumber(request.getParameter("card"));

        HashSet<String> uniqueBar = new HashSet<>();
        String[] barcodes = request.getParameterValues("barcode");
        String[] dueDates = request.getParameterValues("dueDate");
        if (barcodes != null) {
            for (int i = 0; i < barcodes.length; i++) {
                if (uniqueBar.contains(barcodes[i])) {
                    response.getWriter().print("Barcode is duplicated!");
                    return;
                }
                uniqueBar.add(barcodes[i]);
                
                BookItem bi = new BookItem();
                bi.setBarcode(barcodes[i]);

                BookLending bl = new BookLending();
                bl.setIssueDate(issueDate);
                bl.setItem(bi);
                bl.setDueDate(Date.valueOf(dueDates[i]));
                lc.getLendings().add(bl);
            }
        }

        BookLendingDBContext db = new BookLendingDBContext();
        boolean check = db.insert(lc);
        if (check) {
            response.getWriter().println("Add successful!");
        } else {
            response.getWriter().println("Add failed!");
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
