/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Account {
    
    private String username;
    private String password;
    private LibraryCard card;
    
    private ArrayList<Permission> pers = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LibraryCard getCard() {
        return card;
    }

    public void setCard(LibraryCard card) {
        this.card = card;
    }

    public ArrayList<Permission> getPers() {
        return pers;
    }

    public void setPers(ArrayList<Permission> pers) {
        this.pers = pers;
    }
    
}
