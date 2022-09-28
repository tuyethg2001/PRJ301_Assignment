/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Book;
import model.BookLending;
import model.BookItem;
import model.LibraryCard;
import model_enum.BookStatus;
import model_enum.LendingState;

/**
 *
 * @author DELL
 */
public class BookLendingDBContext extends DBContext {

    public ArrayList<BookLending> getLendings() {
        ArrayList<BookLending> lendings = new ArrayList<>();
        try {
            String sql = "select bl.lendingID, username, issueDate\n"
                    + "	, (case\n"
                    + "			when COUNT(case \n"
                    + "							when returnDate is null then 1 \n"
                    + "						end) = 0 then 'done'\n"
                    + "			when COUNT(case \n"
                    + "							when status = 'lost' then 1 \n"
                    + "						end) != 0 then 'lost'\n"
                    + "			when COUNT(case \n"
                    + "							when returnDate is null\n"
                    + "								and dueDate < GETDATE() then 1 \n"
                    + "						end) != 0 then 'overdue'\n"
                    + "			else 'notyet'\n"
                    + "		end) as state\n"
                    + "from BookLending bl inner join BookLendingDetail bld\n"
                    + "on bl.lendingID = bld.lendingID\n"
                    + "inner join BookItem bi\n"
                    + "on bld.barcode = bi.barcode\n"
                    + "group by bl.lendingID, username, issueDate";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account();
                a.setUsername(rs.getString("username"));

                BookLending bl = new BookLending();
                bl.setId(rs.getInt("lendingID"));
                bl.setIssueDate(rs.getDate("issueDate"));
                bl.setState(LendingState.valueOf(rs.getString("state").toUpperCase()));
                lendings.add(bl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lendings;
    }

    public BookLending getLending(int id) {
        try {
            String sql = "select lendingID, lc.cardNumber, name, bi.barcode, title\n"
                    + "	 , issueDate, dueDate, returnDate, status\n"
                    + "from BookLending bl inner join LibraryCard lc\n"
                    + "on bl.cardNumber = lc.cardNumber\n"
                    + "inner join BookItem bi\n"
                    + "on bl.barcode = bi.barcode\n"
                    + "inner join Book b\n"
                    + "on bi.isbn = b.isbn\n"
                    + "where lendingID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Book b = new Book();
                b.setTitle(rs.getString("title"));

                BookItem bi = new BookItem();
                bi.setBarcode(rs.getString("barcode"));
                bi.setBook(b);
                bi.setStatus(BookStatus.valueOf(rs.getString("status").toUpperCase()));

                LibraryCard lc = new LibraryCard();
                lc.setCardNumber(rs.getString("cardNumber"));
                lc.setName(rs.getString("name"));

                BookLending bl = new BookLending();
                bl.setId(id);
                bl.setCard(lc);
                bl.setItem(bi);
                bl.setIssueDate(rs.getDate("issueDate"));
                bl.setDueDate(rs.getDate("dueDate"));
                bl.setReturnDate(rs.getDate("returnDate"));
                return bl;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateReturn(BookLending bl) {
        boolean check = true;
        try {
            connection.setAutoCommit(false);
            // update returnDate
            String sql = "update BookLending\n"
                    + "set returnDate = ?\n"
                    + "where lendingID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, bl.getReturnDate());
            ps.setInt(2, bl.getId());
            ps.executeUpdate();
            // update bookitem's status
            String sql_status = "update BookItem\n"
                    + "set status = 'available'\n"
                    + "where barcode = (select barcode from BookLending \n"
                    + "				 where lendingID = ?)";
            PreparedStatement ps_status = connection.prepareStatement(sql_status);
            ps_status.setInt(1, bl.getId());
            ps_status.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            check = false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public boolean insert(LibraryCard lc) {
        boolean check = true;
        try {
            // check if card exist
            String sql_exist_card = "select * from LibraryCard\n"
                    + "where cardNumber = ?";
            PreparedStatement ps_exist_card = connection.prepareStatement(sql_exist_card);
            ps_exist_card.setString(1, lc.getCardNumber());
            ResultSet rs_exist_card = ps_exist_card.executeQuery();
            if (!rs_exist_card.next()) {
                return false;
            }
            // sql check if bookitem exist and available
            String sql_exist_book = "select * from BookItem\n"
                    + "where barcode = ? and status = 'available'";
            PreparedStatement ps_exist_book = connection.prepareStatement(sql_exist_book);
            for (BookLending bl : lc.getLendings()) {
                ps_exist_book.setString(1, bl.getItem().getBarcode());
                ResultSet rs_exist_book = ps_exist_book.executeQuery();
                if (!rs_exist_book.next()) {
                    return false;
                }
            }
            connection.setAutoCommit(false);
            // sql insert booklending
            String sql = "insert into BookLending\n"
                    + "	(cardNumber, barcode, issueDate, dueDate)\n"
                    + "values (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, lc.getCardNumber());
            ps.setDate(3, lc.getLendings().get(0).getIssueDate());
            // sql update bookitem status to loaned
            String sql_up_status = "update BookItem\n"
                    + "set status = 'loaned'\n"
                    + "where barcode = ?";
            PreparedStatement ps_up_status = connection.prepareStatement(sql_up_status);

            for (BookLending bl : lc.getLendings()) {
                // insert
                ps.setString(2, bl.getItem().getBarcode());
                ps.setDate(4, bl.getDueDate());
                ps.executeUpdate();
                // update status
                ps_up_status.setString(1, bl.getItem().getBarcode());
                ps_up_status.executeUpdate();
            }
            connection.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            check = false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public boolean delete(int id) {
        boolean check = true;
        try {
            connection.setAutoCommit(false);
            // update bookitem status to available (except lost)
            String sql_up_status = "update BookItem\n"
                    + "set status = 'available'\n"
                    + "where barcode = (select barcode from BookLending\n"
                    + "				  where lendingID = ?)\n"
                    + "	  and status != 'lost'";
            PreparedStatement ps_up_status = connection.prepareStatement(sql_up_status);
            ps_up_status.setInt(1, id);
            ps_up_status.executeUpdate();
            // delete booklending
            String sql = "delete from BookLending\n"
                    + "where lendingID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            try {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex1);
            }
            check = false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public ArrayList<BookLending> search(String card, String title, Date from, Date to, LendingState state) {
        ArrayList<BookLending> lendings = new ArrayList<>();
        try {
            String sql = "select *\n"
                    + "from (select lendingID, cardNumber, title, issueDate\n"
                    + "		   , (case\n"
                    + "				  when returnDate is not null then 'done'\n"
                    + "				  when status = 'lost' then status\n"
                    + "				  when dueDate < GETDATE() then 'overdue'\n"
                    + "				  else 'notyet'\n"
                    + "			  end) as state\n"
                    + "from BookLending bl inner join BookItem bi\n"
                    + "on bl.barcode = bi.barcode\n"
                    + "inner join Book b\n"
                    + "on bi.isbn = b.isbn) t\n"
                    + "where 1 = 1";
            HashMap<Integer, Object[]> map = new HashMap<>();
            int index = 0;
            if (card != null) {
                index++;
                sql += "\nand cardNumber like '%' + ? + '%'";
                Object[] params = new Object[2];
                params[0] = String.class.getName();
                params[1] = card;
                map.put(index, params);
            }
            if (title != null) {
                index++;
                sql += "\nand title like '%' + ? + '%'";
                Object[] params = new Object[2];
                params[0] = String.class.getName();
                params[1] = title;
                map.put(index, params);
            }
            if (from != null) {
                index++;
                sql += "\nand issueDate >= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = from;
                map.put(index, params);
            }
            if (to != null) {
                index++;
                sql += "\nand issueDate <= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = to;
                map.put(index, params);
            }
            if (state != null) {
                index++;
                sql += "\nand state like '%' + ? + '%'";
                Object[] params = new Object[2];
                params[0] = LendingState.class.getName();
                params[1] = state;
                map.put(index, params);
            }

            PreparedStatement ps = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Object[] value = entry.getValue();
                if (value[0].equals(String.class.getName())) {
                    ps.setString(key, value[1].toString());
                }
                if (value[0].equals(Date.class.getName())) {
                    ps.setDate(key, (Date) value[1]);
                }
                if (value[0].equals(LendingState.class.getName())) {
                    ps.setString(key, ((LendingState) value[1]).toString().toLowerCase());
                }
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setTitle(rs.getString("title"));

                BookItem bi = new BookItem();
                bi.setBook(b);

                LibraryCard lc = new LibraryCard();
                lc.setCardNumber(rs.getString("cardNumber"));

                BookLending bl = new BookLending();
                bl.setId(rs.getInt("lendingID"));
                bl.setCard(lc);
                bl.setItem(bi);
                bl.setIssueDate(rs.getDate("issueDate"));
                bl.setState(LendingState.valueOf(rs.getString("state").toUpperCase()));
                lendings.add(bl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lendings;
    }

    public ArrayList<BookLending> searchUser(String card, String title,
            Date isFrom, Date isTo, Date duFrom, Date duTo, Date rtFrom,
            Date rtTo, LendingState state) {
        ArrayList<BookLending> lendings = new ArrayList<>();
        try {
            String sql = "select *\n"
                    + "from (select lendingID, cardNumber, title, issueDate\n"
                    + "		   , (case\n"
                    + "				  when returnDate is not null then 'done'\n"
                    + "				  when status = 'lost' then status\n"
                    + "				  when dueDate < GETDATE() then 'overdue'\n"
                    + "				  else 'notyet'\n"
                    + "			  end) as state, dueDate, returnDate\n"
                    + "	  from BookLending bl inner join BookItem bi\n"
                    + "	  on bl.barcode = bi.barcode\n"
                    + "	  inner join Book b\n"
                    + "	  on bi.isbn = b.isbn) t\n"
                    + "where 1 = 1";
            HashMap<Integer, Object[]> map = new HashMap<>();
            int index = 0;
            if (title != null) {
                index++;
                sql += "\nand title like '%' + ? + '%'";
                Object[] params = new Object[2];
                params[0] = String.class.getName();
                params[1] = title;
                map.put(index, params);
            }
            if (isFrom != null) {
                index++;
                sql += "\nand issueDate >= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = isFrom;
                map.put(index, params);
            }
            if (isTo != null) {
                index++;
                sql += "\nand issueDate <= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = isTo;
                map.put(index, params);
            }
            if (duFrom != null) {
                index++;
                sql += "\nand dueDate >= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = duFrom;
                map.put(index, params);
            }
            if (duTo != null) {
                index++;
                sql += "\nand dueDate <= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = duTo;
                map.put(index, params);
            }
            if (rtFrom != null) {
                index++;
                sql += "\nand returnDate >= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = rtFrom;
                map.put(index, params);
            }
            if (rtTo != null) {
                index++;
                sql += "\nand returnDate <= ?";
                Object[] params = new Object[2];
                params[0] = Date.class.getName();
                params[1] = rtTo;
                map.put(index, params);
            }
            if (state != null) {
                index++;
                sql += "\nand state like '%' + ? + '%'";
                Object[] params = new Object[2];
                params[0] = LendingState.class.getName();
                params[1] = state;
                map.put(index, params);
            }
            sql += "\nand cardNumber = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            for (Map.Entry<Integer, Object[]> entry : map.entrySet()) {
                Integer key = entry.getKey();
                Object[] value = entry.getValue();
                if (value[0].equals(String.class.getName())) {
                    ps.setString(key, value[1].toString());
                }
                if (value[0].equals(Date.class.getName())) {
                    ps.setDate(key, (Date) value[1]);
                }
                if (value[0].equals(LendingState.class.getName())) {
                    ps.setString(key, ((LendingState) value[1]).toString().toLowerCase());
                }
            }
            index++;
            ps.setString(index, card);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book b = new Book();
                b.setTitle(rs.getString("title"));

                BookItem bi = new BookItem();
                bi.setBook(b);

                LibraryCard lc = new LibraryCard();
                lc.setCardNumber(rs.getString("cardNumber"));

                BookLending bl = new BookLending();
                bl.setId(rs.getInt("lendingID"));
                bl.setCard(lc);
                bl.setItem(bi);
                bl.setIssueDate(rs.getDate("issueDate"));
                bl.setDueDate(rs.getDate("dueDate"));
                bl.setReturnDate(rs.getDate("returnDate"));
                bl.setState(LendingState.valueOf(rs.getString("state").toUpperCase()));
                lendings.add(bl);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lendings;
    }

    public void lost(int id) {
        try {
            String sql = "update BookItem\n"
                    + "set status = 'lost'\n"
                    + "where barcode = (select barcode from BookLending \n"
                    + "				 where lendingID = ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(BookLendingDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
