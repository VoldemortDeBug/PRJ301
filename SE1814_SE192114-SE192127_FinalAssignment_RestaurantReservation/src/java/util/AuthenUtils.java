/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.UserDAO;
import dto.UserDTO;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class AuthenUtils {

    public static final String ADMIN_ROLE = "AD";
    public static final String USER_ROLE = "US";

    
    
    public static boolean isGuestOrAbove(HttpSession session) {
        return session.getAttribute("user")!=null;
    }
    
    public static boolean isLoggedIn(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDAO udao=new UserDAO();
        return !udao.searchByID(user.getUserID()).getType().equals("Guest");
    }
    public static boolean isUserLoggedIn(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDAO udao=new UserDAO();
        return udao.searchByID(user.getUserID()).getType().equals("User");
    }

    public static boolean isOwnerLoggedIn(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDAO udao=new UserDAO();
        return udao.searchByID(user.getUserID()).getType().equals("Owner");
    }

}
