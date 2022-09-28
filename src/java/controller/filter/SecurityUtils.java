/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.filter;

import dal.PermissionDBContext;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import model.Account;
import model.Permission;

/**
 *
 * @author DELL
 */
public class SecurityUtils {
    
    // check if page need login
    public static boolean isSecurityPage(HttpServletRequest request) {
        String urlPattern = request.getServletPath();
        
        PermissionDBContext db = new PermissionDBContext();
        ArrayList<Permission> pers = db.getPers();
        
        for (Permission p : pers) {
            if (urlPattern != null && urlPattern.equals(p.getUrl())) {
                return true;
            }
        }
        
        return false;
    }
    
    // check if role has permission
    public static boolean hasPermission(HttpServletRequest request) {
        String urlPattern = request.getServletPath();        
        Account a = (Account) request.getSession().getAttribute("account");
        if (a == null) {
            return false;
        }
        
        ArrayList<Permission> pers = a.getPers();        
        for (Permission p : pers) {
            if (urlPattern != null && urlPattern.equals(p.getUrl())) {
                return true;
            }
        }
        
        return false;
    }
    
}
