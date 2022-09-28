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
import model.Account;
import model.LibraryCard;
import model.Permission;

/**
 *
 * @author DELL
 */
public class AccountDBContext extends DBContext {

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            String sql = "select username\n"
                    + "from Account";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account a = new Account();
                a.setUsername(rs.getString("username"));
                accounts.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public Account getAccount(String username, String password) {
        try {
            String sql = "select a.username, password, p.perID, url, name, gender, dob\n"
                    + "	 , email, phone, address, lc.cardNumber\n"
                    + "from Account a inner join LibraryCard lc\n"
                    + "on a.cardNumber = lc.cardNumber\n"
                    + "left join Account_Role ar\n"
                    + "on a.username = ar.username\n"
                    + "left join Role r\n"
                    + "on r.roleID = ar.roleID\n"
                    + "left join Role_Permission rp\n"
                    + "on r.roleID = rp.roleID\n"
                    + "left join Permission p\n"
                    + "on rp.perID = p.perID\n"
                    + "where (a.username = ? or lc.cardNumber = ?) and password = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            Account a = null;
            while (rs.next()) {
                if (a == null) {
                    LibraryCard lc = new LibraryCard();
                    lc.setCardNumber(rs.getString("cardNumber"));
                    lc.setName(rs.getString("name"));
                    lc.setGender(rs.getBoolean("gender"));
                    lc.setDob(rs.getDate("dob"));
                    lc.setEmail(rs.getString("email"));
                    lc.setPhone(rs.getString("phone"));
                    lc.setAddress(rs.getString("address"));

                    a = new Account();
                    a.setUsername(username);
                    a.setCard(lc);
                    a.setPassword(password);
                }
                int pid = rs.getInt("perID");
                if (pid != 0) {
                    Permission p = new Permission();
                    p.setId(pid);
                    p.setUrl(rs.getString("url"));
                    a.getPers().add(p);
                }
            }
            return a;
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean insert(Account a) {
        try {
            // check if exist card
            String sql_exist_card = "select * from LibraryCard\n"
                    + "where cardNumber = ?";
            PreparedStatement ps_exist_card = connection.prepareStatement(sql_exist_card);
            ps_exist_card.setString(1, a.getCard().getCardNumber());
            ResultSet rs_exist_card = ps_exist_card.executeQuery();
            if (!rs_exist_card.next()) {
                return false;
            }
            // check if exist account has username or card
            String sql_exist_acc = "select * from Account\n"
                    + "where username = ? or cardNumber = ?";
            PreparedStatement ps_exist_acc = connection.prepareStatement(sql_exist_acc);
            ps_exist_acc.setString(1, a.getUsername());
            ps_exist_acc.setString(2, a.getCard().getCardNumber());
            ResultSet rs_exist_acc = ps_exist_acc.executeQuery();
            if (rs_exist_acc.next()) {
                return false;
            }
            // insert
            String sql = "insert into Account\n"
                    + "	(username, password, cardNumber)\n"
                    + "values (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.setString(3, a.getCard().getCardNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    public void updateProfile(Account a) {
        try {
            String sql = "update LibraryCard\n"
                    + "set name = ?, gender = ?, dob = ?, email = ?, phone = ?\n"
                    + "	, address = ?\n"
                    + "where cardNumber = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, a.getCard().getName());
            ps.setBoolean(2, a.getCard().isGender());
            ps.setDate(3, a.getCard().getDob());
            ps.setString(4, a.getCard().getEmail());
            ps.setString(5, a.getCard().getPhone());
            ps.setString(6, a.getCard().getAddress());
            ps.setString(7, a.getCard().getCardNumber());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updatePass(Account a) {
        try {
            String sql = "update Account\n"
                    + "set password = ?\n"
                    + "where username = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, a.getPassword());
            ps.setString(2, a.getUsername());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
