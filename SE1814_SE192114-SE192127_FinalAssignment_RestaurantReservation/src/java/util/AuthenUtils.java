/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.REntityDAO;
import dao.ReservationDAO;
import dao.RestDAO;
import dao.RestPhotoDAO;
import dao.UserDAO;
import dto.REntityDTO;
import dto.ReservationDTO;
import dto.RestDTO;
import dto.UserDTO;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
public class AuthenUtils {

    public static final String ADMIN_ROLE = "AD";
    public static final String USER_ROLE = "US";
    private static UserDAO udao = new UserDAO();
    private static RestDAO rdao = new RestDAO();
    private static RestPhotoDAO rpdao = new RestPhotoDAO();
    private static REntityDAO edao = new REntityDAO();
    private static ReservationDAO rsvdao = new ReservationDAO();

    public static boolean isGuestOrAbove(HttpSession session) {
        return session.getAttribute("user") != null;
    }

    public static boolean isLoggedIn(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDAO udao = new UserDAO();
        return !udao.searchByID(user.getUserID()).getType().equals("Guest");
    }

    public static boolean isUserLoggedIn(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDAO udao = new UserDAO();
        return udao.searchByID(user.getUserID()).getType().equals("User");
    }

    public static boolean isOwnerLoggedIn(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        UserDAO udao = new UserDAO();
        return udao.searchByID(user.getUserID()).getType().equals("Owner");
    }

    public static boolean isOwnerOfRest(HttpSession session, int restID) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        user = udao.searchByID(user.getUserID());
        RestDTO rest = rdao.searchByID(restID);
        return rest.getOwnerID() == user.getUserID();
    }

    public static boolean isOwnerOfEntity(HttpSession session, int entID) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        user = udao.searchByID(user.getUserID());
        REntityDTO rent = edao.searchByID(entID);
        RestDTO rest = rdao.searchByID(rent.getRestID());
        return rest.getOwnerID() == user.getUserID();
    }

    public static boolean reservationAccess(HttpSession session, int rsvID) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        ReservationDTO resv = rsvdao.searchByID(rsvID);
        if(resv.getUserID()==user.getUserID()){
            return true;
        }
        REntityDTO rent = edao.searchByID(resv.getEntID());
        RestDTO rest = rdao.searchByID(rent.getRestID());
        return rest.getOwnerID() == user.getUserID();
    }
    
    public static boolean reservationOwner(HttpSession session, int rsvID) {
        if (session.getAttribute("user") == null) {
            return false;
        }
        UserDTO user = (UserDTO) session.getAttribute("user");
        ReservationDTO resv = rsvdao.searchByID(rsvID);
        return (resv.getUserID()==user.getUserID());
    }

}
