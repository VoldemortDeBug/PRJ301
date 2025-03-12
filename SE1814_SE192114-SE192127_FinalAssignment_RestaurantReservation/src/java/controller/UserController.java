/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.REntityDAO;
import dao.ReservationDAO;
import dao.RestDAO;
import dao.RestPhotoDAO;
import dao.UserDAO;
import dto.PhotoDTO;
import dto.REntityDTO;
import dto.ReservationDTO;
import dto.RestDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import util.AuthenUtils;
import util.Utils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UserController", urlPatterns = {"/UserController"})

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UserController extends HttpServlet {

    private static final String LOGIN_PAGE = "Login.jsp";
    private static final String USER_IMG_FOLDER = "D:\\FPTK19\\PRJ\\PRJ301\\SE1814_SE192114-SE192127_FinalAssignment_RestaurantReservation\\web\\users\\img\\";
    private static final String REST_IMG_FOLDER = "D:\\FPTK19\\PRJ\\PRJ301\\SE1814_SE192114-SE192127_FinalAssignment_RestaurantReservation\\web\\users\\rimg\\";
    private UserDAO udao = new UserDAO();
    private RestDAO rdao = new RestDAO();
    private RestPhotoDAO rpdao = new RestPhotoDAO();
    private REntityDAO edao = new REntityDAO();
    private ReservationDAO rsvdao = new ReservationDAO();

    public UserDTO getUser(String strUsername) {
        UserDTO newuser = udao.searchByUsername(strUsername);
        return newuser;
    }

    public boolean isValidLogin(String usname, String passw) {
        dto.UserDTO user = udao.searchByUsername(usname);
        System.out.println(user);
        System.out.println(passw + "hello" + Utils.encrypt(passw));
        if (user == null) {
            return false;
        }
        if (passw.equals(Utils.decrypt(user.getPassword()))) {
            return true;
        }
        return false;
    }

    private String processLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        //login action
        String username = (String) request.getParameter("strUserID");
        String password = (String) request.getParameter("strPassword");
        if (isValidLogin(username, password)) {
            url = "Home.jsp";
            UserDTO user = getUser(username);
            System.out.println(user + " logged in");
            request.getSession().setAttribute("user", user);
            System.out.println(user);
        } else {
            request.setAttribute("message", "Login failed. Incorrect Username or Password.");
            url = LOGIN_PAGE;
        }
        return url;
    }

    private String processGuestLogin(HttpServletRequest request, HttpServletResponse response) {
        String url = "Home.jsp";
        //login action
        UserDTO user = new UserDTO("GUEST", "", "", "default.jpg", "Guest");
        request.getSession().setAttribute("user", user);
        return url;
    }

    private String processUpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        String url = "UserProfile.jsp";
        if (!AuthenUtils.isLoggedIn(request.getSession())) {
            return "Home.jsp";
        }
        System.out.println("ur not going home");
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        String phone = request.getParameter("txtPhone");
        String name = request.getParameter("txtName");
        String email = request.getParameter("txtEmail");

        System.out.println("User old data: " + user);

        if (Utils.validString(phone)) {
            user.setPhone(phone);
        }

        if (Utils.validString(name)) {
            user.setName(name);
        }

        if (Utils.validString(email)) {
            user.setEmail(email);
        }

        try {
            Part filePhoto = request.getPart("photo");
            System.out.println("worked till now" + filePhoto);
            if (filePhoto != null && filePhoto.getSize() > 0) {
                filePhoto.write(USER_IMG_FOLDER + user.getUserName() + ".jpg");
                user.setProfilepic(user.getUserName() + ".jpg");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        udao.update(user);

        request.setAttribute("updateProfilePicMessage", "succeed");
        return url;
    }

    private String processRegister(HttpServletRequest request, HttpServletResponse response) {
        String url = "Home.jsp";
        System.out.println("registering in proccess...");

        int newID = udao.newID();
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String phone = request.getParameter("txtPhone");
        String name = request.getParameter("txtName");
        String email = request.getParameter("txtEmail");
        boolean checkError = false;
        if (!Utils.validString(username)) {
            checkError = true;
        } else {
            request.setAttribute("username", username);
        }
        if (!Utils.validString(password)) {
            checkError = true;
        } else {
            request.setAttribute("password", password);
        }
        if (!Utils.validString(phone)) {
            checkError = true;
        } else {
            request.setAttribute("phone", phone);
        }
        if (!Utils.validString(name)) {
            checkError = true;
        } else {
            request.setAttribute("name", name);
        }
        if (!Utils.validString(email)) {
            checkError = true;
        } else {
            request.setAttribute("email", email);
        }

        if (checkError) {
            request.setAttribute("error", true);
            return "Register.jsp";
        }

        String photoname = "default.jpg";
        try {
            Part filePhoto = request.getPart("photo");
            System.out.println("worked till now" + filePhoto);
            if (filePhoto != null && filePhoto.getSize() > 0) {
                filePhoto.write(USER_IMG_FOLDER + username + ".jpg");
                photoname = username + ".jpg";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String type = request.getParameter("type");

        UserDTO user = new UserDTO(newID, name, username, email, phone, password, 0, photoname, type);
        System.out.println(user);
        udao.create(user);
        request.getSession().setAttribute("user", user);
        return url;
    }

    private String processOwnedRestList(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestList.jsp";
        System.out.println("getting restaurant list in proccess...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Home.jsp";
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        Date currentSqlDate = new Date(System.currentTimeMillis());
        try {
            List<RestDTO> rlist = rdao.searchOwnedBy(user.getUserID());
            for (RestDTO i : rlist) {
                List<REntityDTO> lrent = edao.EntitiesAtRest(i.getResID());
                int c = 0;
                for (REntityDTO j : lrent) {
                    c += rsvdao.GetReservationsAtEntity(j.getEnID()).size();
                }
                i.setEntites(lrent.size());
                i.setReservations(c);
            }
            request.setAttribute("rlist", rlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String processCreateRest(HttpServletRequest request, HttpServletResponse response) {
        String url = "UserProfile.jsp";
        System.out.println("creating restaurant list in proccess...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        String rname = request.getParameter("rname");
        String rloc = request.getParameter("rloc");
        boolean checkError = false;

        if (!Utils.validString(rname)) {
            checkError = true;
        }
        if (!Utils.validString(rloc)) {
            checkError = true;
        }

        if (checkError) {
            request.setAttribute("rloc", rloc);
            request.setAttribute("rname", rname);
            return url = "UserProfile.jsp";
        }

        RestDTO rest = new RestDTO(rdao.newID(), rname, rloc, user.getUserID(), null, 0);

        try {
            rdao.create(rest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("createRestMes", "suceed");
        System.out.println("created rest successfully");
        return url;
    }

    private String processOwnerRestProfile(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestaurantProfile.jsp";
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int restID = Integer.parseInt(request.getParameter("restID"));

        try {
            RestDTO rest = rdao.restOwnedBy(restID, user.getUserID());
            List<PhotoDTO> lphoto = (List<PhotoDTO>) rpdao.getAllPhoto(restID);
            List<REntityDTO> lent = (List<REntityDTO>) edao.EntitiesAtRest(restID);
            request.setAttribute("lent", lent);
            request.setAttribute("lphoto", lphoto);
            request.setAttribute("rest", rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String processRestProfile(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestaurantProfile.jsp";
        if (!AuthenUtils.isGuestOrAbove(request.getSession())) {
            return "Login.jsp";
        }

        int restID = Integer.parseInt(request.getParameter("restID"));
        System.out.println(restID);

        try {
            RestDTO rest = rdao.searchByID(restID);
            List<PhotoDTO> lphoto = (List<PhotoDTO>) rpdao.getAllPhoto(restID);
            List<REntityDTO> lent = (List<REntityDTO>) edao.EntitiesAtRest(restID);
            List<REntityDTO> filteredList = new ArrayList<>();
            Date currentdate = new Date(System.currentTimeMillis());
            for (REntityDTO ent : lent) {
                if (!ent.getActiveTill().before(currentdate)) {
                    filteredList.add(ent);
                }
            }
            request.setAttribute("lent", filteredList);
            request.setAttribute("lphoto", lphoto);
            request.setAttribute("rest", rest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String processAddRestPhoto(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestaurantProfile.jsp";
        System.out.println("adding restaurant photo ...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int restID = Integer.parseInt(request.getParameter("restID"));

        try {
            try {
                PhotoDTO rphoto = new PhotoDTO(rpdao.newID(), restID, "");
                Part filePhoto = request.getPart("rphoto");
                System.out.println("worked till now" + filePhoto);
                if (filePhoto != null && filePhoto.getSize() > 0) {
                    filePhoto.write(REST_IMG_FOLDER + rphoto.getPhotoID() + "_" + user.getUserName() + "_" + restID + user.getEmail() + ".jpg");
                    rphoto.setPhoto(rphoto.getPhotoID() + "_" + user.getUserName() + "_" + restID + user.getEmail() + ".jpg");
                    rpdao.create(rphoto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            processOwnerRestProfile(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String processUpdateMainPhoto(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestaurantProfile.jsp";
        System.out.println("updating main restaurant photo ...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int restID = Integer.parseInt(request.getParameter("restID"));
        RestDTO rest = rdao.restOwnedBy(restID, user.getUserID());

        try {
            PhotoDTO rphoto = new PhotoDTO(rpdao.newID(), restID, "");
            Part filePhoto = request.getPart("rphoto");

            String photoname = rest.getMainPhoto();
            if (photoname == null || photoname.equals("")) {
                photoname = rest.getRestID() + "_MAIN_" + ".jpg";
                rest.setMainPhoto(photoname);
            }

            if (filePhoto != null && filePhoto.getSize() > 0) {
                filePhoto.write(REST_IMG_FOLDER + photoname);
                rphoto.setPhoto(rphoto.getPhotoID() + photoname);
                rdao.updateMainPhoto(restID, photoname);
            }

            processOwnerRestProfile(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String processDelRestPhoto(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestaurantProfile.jsp";
        System.out.println("deleting restaurant photo ...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        int photoID = Integer.parseInt(request.getParameter("photoID"));
        int restID = Integer.parseInt(request.getParameter("restID"));
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        RestDTO rest = rdao.restOwnedBy(restID, user.getUserID());
        if (rest == null) {
            return "Login.jsp";
        }
        try {
            rpdao.delete(photoID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        processOwnerRestProfile(request, response);
        return url;
    }

    private String processCreateEntity(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestaurantProfile.jsp";
        System.out.println("Creating new entity ...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        System.out.println("hello");
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int restID = Integer.parseInt(request.getParameter("restID"));
        RestDTO rest = rdao.restOwnedBy(restID, user.getUserID());
        if (rest == null) {
            return "Login.jsp";
        }

        try {
            int eFee = Integer.parseInt(request.getParameter("eFee"));
            String eType = request.getParameter("eType");
            int eSeatCap = Integer.parseInt(request.getParameter("eSeatCap"));
            int eForwardLim = Integer.parseInt(request.getParameter("eForwardLim"));
            Date eActiveTill = null;
            if (request.getParameter("eActiveTill") != null && !request.getParameter("eActiveTill").equals("")) {
                eActiveTill = Date.valueOf(request.getParameter("eActiveTill"));
            }

            int eDaily = 0;
            String[] eHour = request.getParameterValues("eHour");
            boolean[] checkhour = new boolean[24];
            if (eHour != null) {
                for (String s : eHour) {
                    System.out.println(s + " - ");
                    eDaily += Utils.PO2[Integer.parseInt(s)];
                    checkhour[Integer.parseInt(s)] = true;
                }
            }
            int eWeekly = 0;
            boolean[] checkday = new boolean[7];
            String[] eDay = request.getParameterValues("eDay");
            if (eDay != null) {
                for (String s : eDay) {
                    System.out.println(s + " - ");
                    eWeekly += Utils.PO2[Integer.parseInt(s)];
                    checkday[Integer.parseInt(s)] = true;
                }
            }

            boolean chE = false;
            if (eFee <= 0) {
                chE = true;
            }
            if (!eType.equals("Room") && !eType.equals("Table")) {
                chE = true;
            }
            if (eSeatCap <= 0) {
                chE = true;
            }
            if (eForwardLim <= 0) {
                chE = true;
            }
            if (eActiveTill == null) {
                chE = true;
            }
            if (eDaily == 0) {
                chE = true;
            }
            if (eWeekly == 0) {
                chE = true;
            }

            if (chE) {
                request.setAttribute("eFee", eFee);
                request.setAttribute("eType", eType);
                request.setAttribute("eSeatCap", eSeatCap);
                request.setAttribute("eForwardLim", eForwardLim);
                request.setAttribute("eActiveTill", eActiveTill);
                request.setAttribute("checkhour", checkhour);
                request.setAttribute("checkday", checkday);
            } else {
                REntityDTO newEntity = new REntityDTO(edao.newID(), restID, eType, eFee, eActiveTill, eSeatCap, eForwardLim, eDaily, eWeekly);
                System.out.println(newEntity);
                edao.create(newEntity);
            }

            System.out.println("fee=" + eFee + " type= " + eType + " daily= " + eDaily + " weekly=" + eWeekly + " seatcap= " + eSeatCap + " forwardlim = " + eForwardLim);

        } catch (Exception e) {
            e.printStackTrace();
        }
        processOwnerRestProfile(request, response);
        return url;
    }

    private void processUpdateEntity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("changing entity active date ...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return;
        }
        Date newEdate = null;
        if (request.getParameter("newEdate") != null && !request.getParameter("newEdate").equals("")) {
            newEdate = Date.valueOf(request.getParameter("newEdate"));

            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int restID = Integer.parseInt(request.getParameter("restID"));
            int entID = Integer.parseInt(request.getParameter("entID"));
            RestDTO rest = rdao.restOwnedBy(restID, user.getUserID()); // get direct restaurant to ensure it's valid restaurant from valid user, prevent hacking.
            REntityDTO rent = edao.GetResvEntity(rest.getResID(), entID);
            Date currentSqlDate = new Date(System.currentTimeMillis());
            currentSqlDate = Utils.xdaysFromNow(rent.getForwardLim());
            if (newEdate.after(currentSqlDate)) {
                edao.updateActiveTill(entID, newEdate);
            } else {
                return;
            }
        }

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    private void processDeleteEntity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("changing entity active date ...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return;
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        int restID = Integer.parseInt(request.getParameter("restID"));
        int entID = Integer.parseInt(request.getParameter("entID"));
        RestDTO rest = rdao.restOwnedBy(restID, user.getUserID()); // get direct restaurant to ensure it's valid restaurant from valid user, prevent hacking.
        REntityDTO rent = edao.GetResvEntity(rest.getResID(), entID);
        Date currentSqlDate = new Date(System.currentTimeMillis());
        if (currentSqlDate.after(Utils.xDaysFromy(1, rent.getActiveTill()))) {
            //delet all reservation (because there's no reservation in the future)
            List<ReservationDTO> rsvList = rsvdao.GetReservationsAtEntity(rent.getEnID());
            for (ReservationDTO i : rsvList) {
                if (currentSqlDate.after(i.getDate())) {
                    rsvdao.delete(i.getRsvID());
                }
            }
            //delete enityt
            edao.delete(rent.getEnID());
        } else {
            System.out.println("CANNOT DELETE");
            return;
        }

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    private String processHome(HttpServletRequest request, HttpServletResponse response) {
        String url = "Home.jsp";
        System.out.println("Entering home...");
        if (!AuthenUtils.isGuestOrAbove(request.getSession())) {
            return "Login.jsp";
        }
        request.setAttribute("allrest", rdao.readAll());
        return url;
    }

    private String processSortHome(HttpServletRequest request, HttpServletResponse response) {
        String url = "Home.jsp";
        System.out.println("Entering home...");
        if (!AuthenUtils.isGuestOrAbove(request.getSession())) {
            return "Login.jsp";
        }
        if (request.getParameter("sortterm").equals("a-z")) {
            request.setAttribute("sortterm", "z-a");
        }else{
            request.setAttribute("sortterm", "a-z");
        }
        request.setAttribute("allrest", rdao.readAll());
        return url;
    }

    private String processReservePage(HttpServletRequest request, HttpServletResponse response) {
        String url = "Reserve.jsp";
        System.out.println("Going to reserve page...");
        if (!AuthenUtils.isLoggedIn(request.getSession())) {
            return "Login.jsp";
        }

        try {
            int restID = Integer.parseInt(request.getParameter("restID"));
            int entID = Integer.parseInt(request.getParameter("entID"));
            RestDTO rest = rdao.searchByID(restID);
            REntityDTO rent = edao.searchByID(entID);
            request.setAttribute("rest", rest);
            request.setAttribute("rent", rent);

            //showing tthe available dates
            int ap = 0; //available page
            if (request.getParameter("ap") != null) {
                ap = Integer.parseInt(request.getParameter("ap"));
            }
            System.out.println("current showing date: " + ap + " " + Utils.xdaysFromNow(0 + ap));
            if (ap < 0) {
                ap = 0;
            }
            Date idate;
            for (int i = 0; i < 7; i++) {
                idate = Utils.xdaysFromNow(i + ap);
                System.out.println("reservation at " + idate);
                List<ReservationDTO> lresv = rsvdao.GetReservationsAtDate(idate, rent.getEnID());
                request.setAttribute(idate + "", lresv);
                request.setAttribute("ap", ap);
            }

            return url;
        } catch (Exception e) {
            System.out.println("ERROR");
            return processHome(request, response);
        }
    }

    private String processMakeReservation(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Making reservation...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        try {
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int restID = Integer.parseInt(request.getParameter("restID"));
            int entID = Integer.parseInt(request.getParameter("entID"));
            RestDTO rest = rdao.searchByID(restID);
            REntityDTO rent = edao.searchByID(entID);
            int rSeat = Integer.parseInt(request.getParameter("rSeat"));
            Date rDate = null;
            if (request.getParameter("rDate") != null && !request.getParameter("rDate").equals("")) {
                rDate = Date.valueOf(request.getParameter("rDate"));
            }

            int rDaily = 0;
            String[] rHour = request.getParameterValues("rHour");

            boolean[] checkhour = new boolean[24];
            if (rHour != null) {
                for (String s : rHour) {
                    System.out.println(s + " - ");
                    rDaily += Utils.PO2[Integer.parseInt(s)];
                    checkhour[Integer.parseInt(s)] = true;
                }
            }
            Date limDate = Utils.xdaysFromNow(rent.getForwardLim()).before(rent.getActiveTill()) ? Utils.xdaysFromNow(rent.getForwardLim()) : rent.getActiveTill();

            if (rDaily == 0 || rSeat <= 0 || rSeat > rent.getSeatCap() || rDate == null || rDate.after(limDate) || !Utils.hasbit(rent.getWeekly(), Utils.toWeekDay(rDate))) {
                request.setAttribute("rSeat", rSeat);
                request.setAttribute("rDate", rDate);
                request.setAttribute("checkhour", checkhour);
                return processReservePage(request, response);
            }

            int dailyLimit = rent.getDaily();
            List<ReservationDTO> lresv = rsvdao.GetReservationsAtDate(rDate, rent.getEnID());
            for (ReservationDTO i : lresv) {
                if (!Utils.hasbit(dailyLimit, i.getHours())) {
                    //the old reservations must be in the limit, otherwise something went wrong badly
                    System.out.println("There's an error in server, cannot make any more reservation.");
                    request.getSession().invalidate();
                    return LOGIN_PAGE;
                }
                dailyLimit -= i.getHours();
            }

            if (!Utils.hasbit(dailyLimit, rDaily)) {
                //that time for today is not available.
                request.setAttribute("rSeat", rSeat);
                request.setAttribute("rDate", rDate);
                request.setAttribute("checkhour", checkhour);
                return processReservePage(request, response);
            }

            System.out.println("rSeat=" + rSeat + " rdate= " + rDate + "total fee= " + rent.getEnFee() * Integer.bitCount(rDaily));
            System.out.println("Done choosing, moving to receipt");

            request.setAttribute("rest", rest);
            request.setAttribute("rent", rent);
            request.setAttribute("rSeat", rSeat);
            request.setAttribute("rDate", rDate);
            request.setAttribute("checkhour", checkhour);
            request.setAttribute("rDaily", rDaily);
            return "Receipt.jsp";

        } catch (Exception e) {
            System.out.println("ERROR");
            return processHome(request, response);
        }
    }

    private String processConfirmReservation(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("Confirming reservation...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
            return "Login.jsp";
        }

        try {
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int restID = Integer.parseInt(request.getParameter("restID"));
            int entID = Integer.parseInt(request.getParameter("entID"));
            RestDTO rest = rdao.searchByID(restID);
            REntityDTO rent = edao.searchByID(entID);

            int rSeat = Integer.parseInt(request.getParameter("rSeat"));
            Date rDate = null;
            if (request.getParameter("rDate") != null && !request.getParameter("rDate").equals("")) {
                rDate = Date.valueOf(request.getParameter("rDate"));
            }
            int rDaily = Integer.parseInt(request.getParameter("rDaily"));

            Date limDate = Utils.xdaysFromNow(rent.getForwardLim()).before(rent.getActiveTill()) ? Utils.xdaysFromNow(rent.getForwardLim()) : rent.getActiveTill();

            if (rDaily == 0 || rSeat <= 0 || rSeat > rent.getSeatCap() || rDate == null || rDate.after(limDate) || !Utils.hasbit(rent.getWeekly(), Utils.toWeekDay(rDate))) {
                return processReservePage(request, response);
            }

            int dailyLimit = rent.getDaily();
            List<ReservationDTO> lresv = rsvdao.GetReservationsAtDate(rDate, rent.getEnID());
            for (ReservationDTO i : lresv) {
                if (!Utils.hasbit(dailyLimit, i.getHours())) {
                    //the old reservations must be in the limit, otherwise something went wrong badly
                    System.out.println("There's an error in server, cannot make any more reservation.");
                    request.getSession().invalidate();
                    return LOGIN_PAGE;
                }
                dailyLimit -= i.getHours();
            }
            if (!Utils.hasbit(dailyLimit, rDaily)) {
                //error with confirmation, someone probably made another reservation for that time first.                
                return processReservePage(request, response);
            }
            UserDTO userFromDatabase = udao.searchByUsername(user.getUserName());
            if (userFromDatabase.getCoins() < (rent.getEnFee() * Integer.bitCount(rDaily))) {
                request.setAttribute("notEnoughCoins", "Your account does not have enough coins to make this reservation. Try again.");
                return processReservePage(request, response);
            }

            ReservationDTO reservation = new ReservationDTO(rsvdao.newID(), user.getUserID(), rent.getEnID(), rDaily, rDate, rSeat);
            rsvdao.makeReservation(reservation, rent.getEnFee() * Integer.bitCount(rDaily), rest.getOwnerID());

            System.out.println("Sucessfully making reservation");
            userFromDatabase = udao.searchByUsername(user.getUserName());
            request.getSession().setAttribute("user", userFromDatabase);

        } catch (Exception e) {
            System.out.println("ERROR");
            return processHome(request, response);
        }

        return processReservePage(request, response);
    }

    private String processMyReservations(HttpServletRequest request, HttpServletResponse response) {
        String url = "UserReservations.jsp";
        System.out.println("Checking my reservationss...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        try {

            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            List<ReservationDTO> lresv = rsvdao.GetReservationsByUser(user.getUserID());
            request.setAttribute("allresv", lresv);

        } catch (Exception e) {
        }

        return url;
    }

    private String processCancelReservation(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        System.out.println("Checking my reservationss...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        try {

            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int rsvID = Integer.parseInt(request.getParameter("rsvID"));
            ReservationDTO rsv = rsvdao.GetReservationByUser(user.getUserID(), rsvID);
            rsvdao.delete(rsv.getRsvID());

            return processMyReservations(request, response);

        } catch (Exception e) {
        }

        return url;
    }

    private String processDeleteRestaurant(HttpServletRequest request, HttpServletResponse response) {
        String url = LOGIN_PAGE;
        System.out.println("Checking my reservationss...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        try {

            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int restID = Integer.parseInt(request.getParameter("restID"));
            RestDTO rest = rdao.restOwnedBy(restID, user.getUserID());

            if (edao.EntitiesAtRest(rest.getResID()).size() > 0) {
                request.setAttribute("message", "There's still tables or rooms in this restaurant. Please delete all tables and rooms before trying to delete the restaurant.");
                return processOwnedRestList(request, response);
            }

            rdao.delete(rest.getResID());
            request.setAttribute("message", "Restaurant is deleted.");

            return processOwnedRestList(request, response);
        } catch (Exception e) {
        }

        return url;
    }

    private String processCheckReservation(HttpServletRequest request, HttpServletResponse response) {
        String url = "ReservationDetail.jsp";

        System.out.println("checking reservation...");
        if (!AuthenUtils.isOwnerLoggedIn(request.getSession())) {
            return "Login.jsp";
        }

        try {
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            int rsvID = Integer.parseInt(request.getParameter("rsvID"));
            ReservationDTO resv = rsvdao.searchByID(rsvID);
            REntityDTO rent = edao.searchByID(resv.getEntID());
            RestDTO rest = rdao.searchByID(rent.getRestID());
            UserDTO rsvUser = udao.searchByID(resv.getUserID());
            if (rest != null && rent != null && resv != null) {
                request.setAttribute("resv", resv);
                request.setAttribute("rest", rest);
                request.setAttribute("rent", rent);
                request.setAttribute("rsvUser", rsvUser);

            }
        } catch (Exception e) {
        }
        return url;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        System.out.println("RESPONDING FROM USERCONTROLLER");
        try {
            String action = request.getParameter("action");
            System.out.println("action = " + action);
            if (action == null) {
                url = LOGIN_PAGE;
            }
            if (action != null && action.equals("login")) {
                url = processLogin(request, response);
            }
            if (action != null && action.equals("guestLogin")) {
                url = processGuestLogin(request, response);
            }
            if (action != null && action.equals("logout")) {
                url = LOGIN_PAGE;
                request.getSession().invalidate();
            }
            if (action != null && action.equals("register")) {
                url = processRegister(request, response);
            }
            if (action != null && action.equals("updateProfile")) {
                url = processUpdateProfile(request, response);
            }
            if (action != null && action.equals("ownedRestList")) {
                url = processOwnedRestList(request, response);
            }
            if (action != null && action.equals("createRest")) {
                url = processCreateRest(request, response);
            }
            if (action != null && action.equals("restProfile")) {
                url = processOwnerRestProfile(request, response);
            }
            if (action != null && action.equals("addRestPic")) {
                url = processAddRestPhoto(request, response);
            }
            if (action != null && action.equals("updateMainPhoto")) {
                url = processUpdateMainPhoto(request, response);
            }
            if (action != null && action.equals("deleteResPhoto")) {
                url = processDelRestPhoto(request, response);
            }
            if (action != null && action.equals("createEntity")) {
                url = processCreateEntity(request, response);
            }
            if (action != null && action.equals("updateEntity")) {
                processUpdateEntity(request, response);
                url = processOwnerRestProfile(request, response);
            }
            if (action != null && action.equals("deleteEntity")) {
                processDeleteEntity(request, response);
                url = processOwnerRestProfile(request, response);
            }
            if (action != null && action.equals("home")) {
                url = processHome(request, response);
            }
            if (action != null && action.equals("clientRestProfile")) {
                url = processRestProfile(request, response);
            }
            if (action != null && action.equals("reservePage")) {
                url = processReservePage(request, response);
            }
            if (action != null && action.equals("makeReservation")) {
                url = processMakeReservation(request, response);
            }
            if (action != null && action.equals("confirmReservation")) {
                url = processConfirmReservation(request, response);
            }
            if (action != null && action.equals("myReservations")) {
                url = processMyReservations(request, response);
            }
            if (action != null && action.equals("cancelReservation")) {
                url = processCancelReservation(request, response);
            }
            if (action != null && action.equals("deleteRestaurant")) {
                url = processDeleteRestaurant(request, response);
            }
            if (action != null && action.equals("checkReservation")) {
                url = processCheckReservation(request, response);
            }
            if (action != null && action.equals("sortHome")) {
                url = processSortHome(request, response);
            }

            if (url.equals("Home.jsp")) {
                url = processHome(request, response);
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            //testing
            //testing
            System.out.println(url + " is final url");

            RequestDispatcher rd = request.getRequestDispatcher(url);
            if (!url.equals("MainController")) {
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
