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
import model.Permission;

/**
 *
 * @author DELL
 */
public class PermissionDBContext extends DBContext {

    public ArrayList<Permission> getPers() {
        ArrayList<Permission> pers = new ArrayList<>();
        try {
            String sql = "select perID, url from Permission";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Permission p = new Permission();
                p.setId(rs.getInt("perID"));
                p.setUrl(rs.getString("url"));
                pers.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pers;
    }

}
