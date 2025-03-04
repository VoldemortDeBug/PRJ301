/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.RestDAO;
import dao.UserDAO;
import dto.RestDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
    private UserDAO udao = new UserDAO();
    private RestDAO rdao = new RestDAO();

    public UserDTO getUser(String strUsername) {
        UserDTO newuser = udao.searchByUsername(strUsername);
        return newuser;
    }

    public boolean isValidLogin(String usname, String passw) {
        dto.UserDTO user = udao.searchByUsername(usname);
        System.out.println(user);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(passw)) {
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

    private String processUpdateProfile(HttpServletRequest request, HttpServletResponse response) {
        String url = "UserProfile.jsp";
        System.out.println(AuthenUtils.isUserLoggedIn(request.getSession()) + " is user logged in...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
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

        UserDTO user = new UserDTO(newID, name, username, email, phone, password, 0, photoname);
        System.out.println(user);
        udao.create(user);
        request.getSession().setAttribute("user", user);
        return url;
    }

    private String processRestList(HttpServletRequest request, HttpServletResponse response) {
        String url = "RestList.jsp";
        System.out.println("getting restaurant list in proccess...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
            return "Login.jsp";
        }
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");

        try {
            List<RestDTO> rlist = rdao.searchOwnedBy(user.getUserID());
            System.out.println("worked till now"+ rlist);
            request.setAttribute("rlist", rlist);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    private String processCreateRest(HttpServletRequest request, HttpServletResponse response) {
        String url = "UserProfile.jsp";
        System.out.println("creating restaurant list in proccess...");
        if (!AuthenUtils.isUserLoggedIn(request.getSession())) {
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

        RestDTO rest = new RestDTO(0, rname, rloc, user.getUserID());

        try {
            rdao.create(rest);
        } catch (Exception e) {
            e.printStackTrace();
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
            if (action != null && action.equals("restList.jsp")) {
                url = processRestList(request, response);
            }
            if (action != null && action.equals("createRest")) {
                url = processCreateRest(request, response);
            }

        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
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
