/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;
import model.BookItem;

/**
 *
 * @author DELL
 */
public class BookItemDBContext extends DBContext {

    public ArrayList<BookItem> getBookItems() {
        ArrayList<BookItem> items = new ArrayList<>();
        try {
            String sql = "select barcode, title\n"
                    + "from BookItem bi inner join Book b\n"
                    + "on bi.isbn = b.isbn";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setTitle(rs.getString("title"));
                
                BookItem bi = new BookItem();
                bi.setBarcode(rs.getString("barcode"));
                bi.setBook(b);
                items.add(bi);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

}
