/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.book;

import dal.BookDBContext;
import dal.CategoryDBContext;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Book;
import model.BookItem;
import model.Category;
import model_enum.BookStatus;

/**
 *
 * @author DELL
 */
public class BookUpdateController extends HttpServlet {

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
        CategoryDBContext cdb = new CategoryDBContext();
        ArrayList<Category> categories = cdb.getCategories();

        BookDBContext db = new BookDBContext();
        Book b = db.getBook(request.getParameter("isbn"));

        request.setAttribute("categories", categories);
        request.setAttribute("book", b);
        request.getRequestDispatcher("../view/book/bookUpdate.jsp").forward(request, response);
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
        Category c = new Category();
        c.setId(Integer.parseInt(request.getParameter("cid")));

        Book b = new Book();
        b.setIsbn(request.getParameter("isbn"));
        b.setTitle(request.getParameter("title"));
        b.setAuthor(request.getParameter("author"));
        b.setPublication(request.getParameter("publ"));
        b.setCategory(c);
        b.setPrice(Double.parseDouble(request.getParameter("price")));

        String[] barcodes = request.getParameterValues("barcode");
        String[] purDate = request.getParameterValues("purDate");
        String[] publDate = request.getParameterValues("publDate");
        if (barcodes != null) {
            for (int i = 0; i < barcodes.length; i++) {
                BookItem bi = new BookItem();
                bi.setBarcode(barcodes[i]);
                bi.setDateOfPurchase(Date.valueOf(purDate[i]));
                bi.setPublicationDate(Date.valueOf(publDate[i]));
                b.getItems().add(bi);
            }
        }

        BookDBContext db = new BookDBContext();
        boolean check = db.update(b);
        if (check) {
            response.getWriter().println("Update successful!");
        } else {
            response.getWriter().println("Update failed!");
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
