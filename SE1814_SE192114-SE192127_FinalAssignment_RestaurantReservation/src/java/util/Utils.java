/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Admin
 */
public class Utils {
    public static boolean validString(String s){
        return (!s.equals("") && s.trim().length()>0);
    }
}
