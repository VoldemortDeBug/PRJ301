/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dto.UserDTO;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class AuthenUtils {
    public static final String ADMIN_ROLE="AD";
    public static final String USER_ROLE="US";

    

    public static boolean isLoggedIn(HttpSession session){
        return session.getAttribute("user")!=null;
    }
    public static boolean isUser(HttpSession session){
        if(!isLoggedIn(session)){
            return false;
        }
        return ((UserDTO)session.getAttribute("user")).getRoleID().equals(USER_ROLE);
    }
    
    public static boolean isAdmin(HttpSession session){
        if(!isLoggedIn(session)){
            return false;
        }
        return ((UserDTO)session.getAttribute("user")).getRoleID().equals(ADMIN_ROLE);
    }
    
}
