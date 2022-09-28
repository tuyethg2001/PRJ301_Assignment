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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Book;
import model.BookItem;
import model.Category;
import model_enum.BookStatus;

/**
 *
 * @author DELL
 */
public class BookDBContext extends DBContext {

    public ArrayList<Book> getBooks() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String sql = "select isbn, title, author, publication, c.categoryID, price\n"
                    + "	, categoryName\n"
                    + "from Book b inner join Category c\n"
                    + "on b.categoryID = c.categoryID";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("categoryID"));
                c.setName(rs.getString("categoryName"));

                Book b = new Book();
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPublication(rs.getString("publication"));
                b.setCategory(c);
                b.setPrice(rs.getDouble("price"));
                books.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public Book getBook(String isbn) {
        try {
            String sql = "select title, author, publication, c.categoryID, price\n"
                    + "	, categoryName, barcode, status, dateOfPurchase\n"
                    + "	, publicationDate\n"
                    + "from Book b inner join Category c\n"
                    + "on b.categoryID = c.categoryID\n"
                    + "inner join BookItem bi\n"
                    + "on b.isbn = bi.isbn\n"
                    + "where b.isbn = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();
            Book b = null;
            while (rs.next()) {
                if (b == null) {
                    Category c = new Category();
                    c.setId(rs.getInt("categoryID"));
                    c.setName(rs.getString("categoryName"));

                    b = new Book();
                    b.setIsbn(isbn);
                    b.setTitle(rs.getString("title"));
                    b.setAuthor(rs.getString("author"));
                    b.setPublication(rs.getString("publication"));
                    b.setCategory(c);
                    b.setPrice(rs.getDouble("price"));
                }
                BookStatus bs = BookStatus.valueOf(rs.getString("status").toUpperCase());

                BookItem bi = new BookItem();
                bi.setBarcode(rs.getString("barcode"));
                bi.setBook(b);
                bi.setStatus(bs);
                bi.setDateOfPurchase(rs.getDate("dateOfPurchase"));
                bi.setPublicationDate(rs.getDate("publicationDate"));
                b.getItems().add(bi);
            }
            return b;
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Book> search(String title, String author, String publ, Integer cid) {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String sql = "select isbn, title, author, publication, c.categoryID, price\n"
                    + "	, categoryName\n"
                    + "from Book b inner join Category c\n"
                    + "on b.categoryID = c.categoryID\n"
                    + "where 1 = 1";
            HashMap<Integer, Object[]> params = new HashMap<>();
            int paramIndex = 0;
            if (title != null && author != null && publ != null) {
                sql += "\nand (title like '%' + ? + '%' or \n"
                        + "	  author like '%' + ? + '%' or \n"
                        + "	  publication like '%' + ? + '%')";
                // title
                paramIndex++;
                Object[] param1 = new Object[2];
                param1[0] = String.class.getName();
                param1[1] = title;
                params.put(paramIndex, param1);
                // author
                paramIndex++;
                Object[] param2 = new Object[2];
                param2[0] = String.class.getName();
                param2[1] = author;
                params.put(paramIndex, param2);
                // publ
                paramIndex++;
                Object[] param3 = new Object[2];
                param3[0] = String.class.getName();
                param3[1] = publ;
                params.put(paramIndex, param3);
            } else {
                if (title != null) {
                    sql += "\nand title like '%' + ? + '%' ";
                    paramIndex++;
                    Object[] param = new Object[2];
                    param[0] = String.class.getName();
                    param[1] = title;
                    params.put(paramIndex, param);
                }
                if (author != null) {
                    sql += "\nand author like '%' + ? + '%' ";
                    paramIndex++;
                    Object[] param = new Object[2];
                    param[0] = String.class.getName();
                    param[1] = author;
                    params.put(paramIndex, param);
                }
                if (publ != null) {
                    sql += "\nand publication like '%' + ? + '%' ";
                    paramIndex++;
                    Object[] param = new Object[2];
                    param[0] = String.class.getName();
                    param[1] = publ;
                    params.put(paramIndex, param);
                }
            }
            if (cid != null) {
                sql += "\nand c.categoryID = ?";
                paramIndex++;
                Object[] param = new Object[2];
                param[0] = Integer.class.getName();
                param[1] = cid;
                params.put(paramIndex, param);
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : params.entrySet()) {
                Integer key = entry.getKey();
                Object[] value = entry.getValue();
                String type = value[0].toString();
                if (type.equals(String.class.getName())) {
                    ps.setString(key, value[1].toString());
                } else if (type.equals(Integer.class.getName())) {
                    ps.setInt(key, Integer.valueOf(value[1].toString()));
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("categoryID"));
                c.setName(rs.getString("categoryName"));

                Book b = new Book();
                b.setIsbn(rs.getString("isbn"));
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPublication(rs.getString("publication"));
                b.setCategory(c);
                books.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public boolean insert(Book b) {
        try {
            // check exist isbn
            String sql_exist_isbn = "select * from Book\n"
                    + "where isbn = ?";
            PreparedStatement ps_exist_isbn = connection.prepareStatement(sql_exist_isbn);
            ps_exist_isbn.setString(1, b.getIsbn());
            ResultSet rs_exist_isbn = ps_exist_isbn.executeQuery();
            if (rs_exist_isbn.next()) {
                return false;
            }
            // check exist barcode
            String sql_exist_barcode = "select * from BookItem\n"
                    + "where barcode = ?";
            PreparedStatement ps_exist_barcode = connection.prepareStatement(sql_exist_barcode);
            for (BookItem bi : b.getItems()) {
                ps_exist_barcode.setString(1, bi.getBarcode());
                ResultSet rs_exist_barcode = ps_exist_barcode.executeQuery();
                if (rs_exist_barcode.next()) {
                    return false;
                }
            }
            connection.setAutoCommit(false);
            // insert book
            String sql = "insert into Book \n"
                    + "	(isbn, title, author, publication, categoryID, price)\n"
                    + "values (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, b.getIsbn());
            ps.setString(2, b.getTitle());
            ps.setString(3, b.getAuthor());
            ps.setString(4, b.getPublication());
            ps.setInt(5, b.getCategory().getId());
            ps.setDouble(6, b.getPrice());
            ps.executeUpdate();
            // insert bookitem
            String sql_item = "insert into BookItem\n"
                    + "	(barcode, isbn, status, dateOfPurchase, publicationDate)\n"
                    + "values (?, ?, ?, ?, ?)";
            PreparedStatement ps_item = connection.prepareStatement(sql_item);
            ps_item.setString(2, b.getIsbn());
            ps_item.setString(3, "available");
            for (BookItem bi : b.getItems()) {
                ps_item.setString(1, bi.getBarcode());
                ps_item.setDate(4, bi.getDateOfPurchase());
                ps_item.setDate(5, bi.getPublicationDate());
                ps_item.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public boolean insertItem(Book b) {
        try {
            // check exist barcode
            String sql_exist = "select * from BookItem\n"
                    + "where barcode = ?";
            PreparedStatement ps_exist = connection.prepareStatement(sql_exist);
            for (BookItem bi : b.getItems()) {
                ps_exist.setString(1, bi.getBarcode());
                ResultSet rs_exist = ps_exist.executeQuery();
                if (rs_exist.next()) {
                    return false;
                }
            }
            connection.setAutoCommit(false);
            // insert
            String sql = "insert into BookItem\n"
                    + "	(barcode, isbn, status, dateOfPurchase, publicationDate)\n"
                    + "values (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(2, b.getIsbn());
            ps.setString(3, "available");
            for (BookItem bi : b.getItems()) {
                ps.setString(1, bi.getBarcode());
                ps.setDate(4, bi.getDateOfPurchase());
                ps.setDate(5, bi.getPublicationDate());
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        return true;
    }

    public boolean update(Book b) {
        try {
            // check if exist book have title and author
            String sql_exist = "select *\n"
                    + "from Book\n"
                    + "where isbn != ? and UPPER(title) = ? and UPPER(author) = ?";
            PreparedStatement ps_exist = connection.prepareStatement(sql_exist);
            ps_exist.setString(1, b.getIsbn());
            ps_exist.setString(2, b.getTitle());
            ps_exist.setString(3, b.getAuthor());
            ResultSet rs_exist = ps_exist.executeQuery();
            if (rs_exist.next()) {
                return false;
            }
            // update book
            String sql = "update Book\n"
                    + "set title = ?, author = ?, publication = ?, categoryID = ?\n"
                    + "	, price = ?\n"
                    + "where isbn = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, b.getTitle());
            ps.setString(2, b.getAuthor());
            ps.setString(3, b.getPublication());
            ps.setInt(4, b.getCategory().getId());
            ps.setDouble(5, b.getPrice());
            ps.setString(6, b.getIsbn());
            ps.executeUpdate();
            // update bookitem            
            ArrayList<BookItem> items = b.getItems();
            if (items.size() > 0) {
                // sql update bookitem
                String sql_up_bookitem = "update BookItem\n"
                        + "set dateOfPurchase = ?, publicationDate = ?\n"
                        + "where barcode = ?";
                PreparedStatement ps_up_bookitem = connection.prepareStatement(sql_up_bookitem);
                for (BookItem i : items) {
                    // update bookitem
                    ps_up_bookitem.setDate(1, i.getDateOfPurchase());
                    ps_up_bookitem.setDate(2, i.getPublicationDate());
                    ps_up_bookitem.setString(3, i.getBarcode());
                    ps_up_bookitem.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public boolean delete(Book b) {
        try {
            connection.setAutoCommit(false);
            // sql del booklending
            String sql_del_bl = "delete from BookLending\n"
                    + "where barcode = ?";
            PreparedStatement ps_del_bl = connection.prepareStatement(sql_del_bl);
            // sql delete bookitem
            String sql_del_bi = "delete from BookItem\n"
                    + "where barcode = ?";
            PreparedStatement ps_del_bi = connection.prepareStatement(sql_del_bi);
            for (BookItem bi : b.getItems()) {
                // delete booklending 
                ps_del_bl.setString(1, bi.getBarcode());
                ps_del_bl.executeUpdate();
                // delete bookitem
                ps_del_bi.setString(1, bi.getBarcode());
                ps_del_bi.executeUpdate();
            }
            // delete book
            String sql = "delete from Book\n"
                    + "where isbn in (select b.isbn\n"
                    + "			  from Book b left join BookItem bi\n"
                    + "			  on b.isbn = bi.isbn\n"
                    + "			  group by b.isbn\n"
                    + "			  having COUNT(barcode) = 0)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

    public ArrayList<Book> getNewArriavals() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String sql = "select top 10 *\n"
                    + "from (select distinct title, author, publication, categoryName\n"
                    + "		   , dateOfPurchase, publicationDate\n"
                    + "	  from BookItem bi inner join Book b\n"
                    + "	  on bi.isbn = b.isbn\n"
                    + "	  inner join Category c\n"
                    + "	  on c.categoryID = b.categoryID) t\n"
                    + "order by dateOfPurchase desc, publicationDate desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setName(rs.getString("categoryName"));

                Book b = new Book();
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPublication(rs.getString("publication"));
                b.setCategory(c);
                books.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public ArrayList<Book> getMostPopular() {
        ArrayList<Book> books = new ArrayList<>();
        try {
            String sql = "select top 10 title, author, publication, categoryName\n"
                    + "from Book b inner join Category c\n"
                    + "on b.categoryID = c.categoryID\n"
                    + "inner join BookItem bi\n"
                    + "on b.isbn = bi.isbn\n"
                    + "inner join BookLending bl\n"
                    + "on bl.barcode = bi.barcode\n"
                    + "group by b.isbn, title, author, publication, categoryName\n"
                    + "order by count(bi.barcode) desc";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Category c = new Category();
                c.setName(rs.getString("categoryName"));

                Book b = new Book();
                b.setTitle(rs.getString("title"));
                b.setAuthor(rs.getString("author"));
                b.setPublication(rs.getString("publication"));
                b.setCategory(c);
                books.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

}
