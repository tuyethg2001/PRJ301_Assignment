/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.book;

import dal.BookDBContext;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Book;
import model.BookItem;

/**
 *
 * @author DELL
 */
public class BookDeleteController extends HttpServlet {

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
        String isbn = request.getParameter("isbn");
        BookDBContext db = new BookDBContext();
        Book b = db.getBook(isbn);
        request.setAttribute("book", b);
        request.getRequestDispatcher("../view/book/bookDelete.jsp").forward(request, response);
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
        String[] barcodes = request.getParameterValues("del");
        
        Book b = new Book();
        for (String barcode : barcodes) {
            BookItem bi = new BookItem();
            bi.setBarcode(barcode);
            b.getItems().add(bi);
        }
        
        if (barcodes != null) {
            BookDBContext db = new BookDBContext();
            boolean check = db.delete(b);
            if (check) {
                response.getWriter().println("Delete successful!");
            } else {
                response.getWriter().println("Delete failed!");
            }
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
