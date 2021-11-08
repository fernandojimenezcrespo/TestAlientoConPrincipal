/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;


public class ControlVariable {

    public boolean ControlVariable(String variable) {
        if (variable.isEmpty())
            return false;
        else if (variable==null)
            return false;
        else
            return true;
    }
    
    
}
