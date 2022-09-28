/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.util.ArrayList;
import model_enum.LendingState;

/**
 *
 * @author DELL
 */

public class BookLending {
    
    private int id;
    private BookItem item;
    private LibraryCard card;
    private Date issueDate;
    private Date dueDate;
    private Date returnDate;
    
    private LendingState state;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookItem getItem() {
        return item;
    }

    public void setItem(BookItem item) {
        this.item = item;
    }

    public LibraryCard getCard() {
        return card;
    }

    public void setCard(LibraryCard card) {
        this.card = card;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public LendingState getState() {
        return state;
    }

    public void setState(LendingState state) {
        this.state = state;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
    
}
