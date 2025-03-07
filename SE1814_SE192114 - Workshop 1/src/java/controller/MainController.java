/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProjectDAO;
import dao.UserDAO;
import dto.ProjectDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private static final String LOGIN_PAGE = "Login.jsp";
    private UserDAO udao = new UserDAO();
    private ProjectDAO pdao = new ProjectDAO();

    public UserDTO getUser(String strUserID) {
        UserDTO newuser = udao.searchByID(strUserID);
        return newuser;
    }

    public boolean isValidLogin(String usname, String passw) {

        dto.UserDTO user = udao.searchByID(usname);
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
            if (action == null) {
                url = LOGIN_PAGE;
            }
            //
            if (action != null && action.equals("login")) {
                //login action
                String username = (String) request.getParameter("strUserID");
                String password = (String) request.getParameter("strPassword");
                //System.out.println(isValidLogin(username, password) + " is working");
                if (isValidLogin(username, password)) {
                    url = "search.jsp";
                    UserDTO user = getUser(username);
                    request.getSession().setAttribute("user", user);
                    request.setAttribute("allProjects", pdao.readAll());
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
            if (action != null && action.equals("searchName")) {
                url = "search.jsp";
                String searchInfo = request.getParameter("searchInfo");
                request.setAttribute("searchInfo", searchInfo);
                request.setAttribute("allProjects", pdao.readAll());
                request.setAttribute("searchedProjects", pdao.searchByName(searchInfo));
            }
            if (action != null && action.equals("updateProjectStatus")) {
                url = "search.jsp";
                int id = Integer.parseInt(request.getParameter("projectID"));
                String newInfo = request.getParameter("newInfo");
                if (pdao.updateStatus(id, newInfo)) {
                    request.setAttribute("updatedOn", id);
                }
                request.setAttribute("allProjects", pdao.readAll());
            }

            if (action != null && action.equals("createProject")) {
                url = "search.jsp";
                request.setAttribute("allProjects", pdao.readAll());
                String pname = (String) request.getParameter("txtPname");
                String pdes = (String) request.getParameter("txtPdes");;
                String pstat = (String) request.getParameter("txtPstat");;
                Date estlaunch = null;
                if (request.getParameter("txtPdate") != null && !request.getParameter("txtPdate").equals("")) {
                    estlaunch = Date.valueOf(request.getParameter("txtPdate"));
                    Date currentSqlDate = new Date(System.currentTimeMillis());
                    if (currentSqlDate.after(estlaunch)) {
                        estlaunch = null;
                    }

                }
                ProjectDTO newp = new ProjectDTO(-1, pname, pdes, pstat, estlaunch);
                System.out.println(newp);
                if (pname.equals("") || pstat.equals("") || estlaunch == null) {
                    request.setAttribute("projectError", newp);
                } else {
                    pdao.create(newp);
                }
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
