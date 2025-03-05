/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.RestDAO;
import dto.RestDTO;
import dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
public class Utils {

    private static final String USER_IMG_FOLDER = "D:\\FPTK19\\PRJ\\PRJ301\\SE1814_SE192114-SE192127_FinalAssignment_RestaurantReservation\\web\\users\\img\\";

    public static boolean validString(String s) {
        return (!s.equals("") && s.trim().length() > 0);
    }

    public static String saveNewPhoto(HttpServletRequest request, String photoname) {
        try {
            Part filePhoto = request.getPart("photo");
//            System.out.println("worked till now" + filePhoto);
            if (filePhoto != null && filePhoto.getSize() > 0) {
                filePhoto.write(USER_IMG_FOLDER + photoname);
            }
            System.out.println(photoname+" is the new profile pic");
            return photoname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("default.jpg is the new profile pic");
        return "default.jpg";
    }

    public static void sendRestaurantList(HttpServletRequest request, RestDAO rdao, UserDTO user) {
        List<RestDTO> rlist = rdao.searchOwnedBy(user.getUserID());
        request.setAttribute("rlist", rlist);
    }

}
