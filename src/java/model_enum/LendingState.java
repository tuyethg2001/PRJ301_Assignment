/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model_enum;

/**
 *
 * @author DELL
 */
public enum LendingState {
    
    DONE("Done"), 
    NOTYET("Not yet"),
    OVERDUE("Overdue"),
    LOST("Lost");
    
    private String value;   

    private LendingState(String value) {
        this.value = value;
    }  

    public String getValue() {
        return value;
    }
    
}
