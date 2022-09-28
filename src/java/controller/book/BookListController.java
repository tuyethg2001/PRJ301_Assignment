/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.book;

import dal.BookDBContext;
import dal.CategoryDBContext;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Book;
import model.Category;

/**
 *
 * @author DELL
 */
public class BookListController extends HttpServlet {

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
        String key = request.getParameter("key");
        String searchBy = request.getParameter("searchBy");
        String raw_cid = request.getParameter("cid");

        String title = null;
        String author = null;
        String publ = null;
        if (searchBy != null) {
            switch (searchBy) {
                case "title": {
                    title = key;
                    break;
                }
                case "author": {
                    author = key;
                    break;
                }
                case "publ": {
                    publ = key;
                    break;
                }
                case "all": {
                    title = key;
                    author = key;
                    publ = key;
                    break;
                }
            }

        }
        Integer cid = (raw_cid == null || raw_cid.equals("-1")) ? null : Integer.parseInt(raw_cid);

        CategoryDBContext cdb = new CategoryDBContext();
        ArrayList<Category> categories = cdb.getCategories();

        BookDBContext bdb = new BookDBContext();
        ArrayList<Book> books = bdb.search(title, author, publ, cid);

        request.setAttribute("key", key);
        request.setAttribute("searchBy", searchBy);
        request.setAttribute("cid", cid);
        request.setAttribute("categories", categories);
        request.setAttribute("books", books);
        request.getRequestDispatcher("../view/book/bookList.jsp").forward(request, response);
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
