/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UserDAO;
import dto.UserDTO;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "Login.jsp";
    private static final String USER_IMG_FOLDER = "D:\\FPTK19\\PRJ\\PRJ301\\SE1814_SE192114-SE192127_FinalAssignment_RestaurantReservation\\web\\users\\img\\";
    private UserDAO udao = new UserDAO();

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = LOGIN_PAGE;
        try {
            String action = request.getParameter("action");
            System.out.println("action = " + action);
            if (action == null) {
                url = LOGIN_PAGE;
            }
            //
            if (action != null && action.equals("login")) {
                //login action
                String username = (String) request.getParameter("strUserID");
                String password = (String) request.getParameter("strPassword");
                if (isValidLogin(username, password)) {
                    url = "Home.jsp";
                    UserDTO user = getUser(username);
                    System.out.println(user+" logged in");
                    request.getSession().setAttribute("user", user);
                    System.out.println(user);
                } else {
                    request.setAttribute("message", "Login failed. Incorrect Username or Password.");
                    url = LOGIN_PAGE;
                }

                System.out.println("no error till now");
            }
            if (action != null && action.equals("logout")) {
                url = LOGIN_PAGE;
                request.getSession().invalidate();
            }

            if (action != null && action.equals("register")) {
                System.out.println("registering in proccess...");
                url = "Home.jsp";
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");
                String phone = request.getParameter("txtPhone");
                String name = request.getParameter("txtName");
                String email = request.getParameter("txtEmail");
                String photoname = "default.jpg";

                System.out.println(phone + " is the phone");
                Part filePhoto = request.getPart("photo");
                System.out.println(filePhoto);
                if (filePhoto != null && filePhoto.getSize() > 0) {
                    filePhoto.write(USER_IMG_FOLDER + username + ".jpg");
                    photoname = username + ".jpg";
                }
                UserDTO user = new UserDTO(name, username, email, phone, password, 0, photoname);
                System.out.println(user);
                udao.create(user);
                request.getSession().setAttribute("user", user);
            }
             if (action != null && action.equals("updateProfile")) {
                System.out.println("registering in proccess...");
                url = "UserProfile.jsp";
                String username = request.getParameter("txtUsername");
                String password = request.getParameter("txtPassword");
                String phone = request.getParameter("txtPhone");
                String name = request.getParameter("txtName");
                String email = request.getParameter("txtEmail");
                String photoname = "default.jpg";

                System.out.println(phone + " is the phone");
                Part filePhoto = request.getPart("photo");
                System.out.println(filePhoto);
                if (filePhoto != null && filePhoto.getSize() > 0) {
                    filePhoto.write(USER_IMG_FOLDER + username + ".jpg");
                    photoname = username + ".jpg";
                }
                UserDTO user = new UserDTO(name, username, email, phone, password, 0, photoname);
                System.out.println(user);
                udao.create(user);
                request.getSession().setAttribute("user", user);
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
