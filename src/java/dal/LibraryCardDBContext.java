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
import model.LibraryCard;

/**
 *
 * @author DELL
 */
public class LibraryCardDBContext extends DBContext {

    public ArrayList<LibraryCard> getCards() {
        ArrayList<LibraryCard> cards = new ArrayList<>();
        try {
            String sql = "select cardNumber, name\n"
                    + "from LibraryCard";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LibraryCard lc = new LibraryCard();
                lc.setCardNumber(rs.getString("cardNumber"));
                lc.setName(rs.getString("name"));
                cards.add(lc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cards;
    }

}
