/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.BookDAO;
import dao.UserDAO;
import dto.BookDTO;
import dto.UserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.AuthenUtils;

/**
 *
 * @author Admin
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

    private BookDAO bdao = new BookDAO();
    private static final String LOGIN_PAGE = "Login.jsp";

    public UserDTO getUser(String strUserID) {
        UserDAO udao = new UserDAO();
        UserDTO user = udao.searchByID(strUserID);
        return user;
    }

    public boolean isValidLogin(String usname, String passw) {
        dao.UserDAO userDao = new UserDAO();
        dto.UserDTO user = userDao.searchByID(usname);
        System.out.println(user);
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(passw)) {
            return true;
        }
        return false;
    }

    private void searchResponse(HttpServletRequest request, HttpServletResponse response) {
        String searchTerm = (String) request.getParameter("searchTerm");
        if (searchTerm == null) {
            searchTerm = "";
        }
        List<BookDTO> books = bdao.searchByTitle(searchTerm);
        request.setAttribute("books", books);
        request.setAttribute("searchTerm", searchTerm);
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
                String username = request.getParameter("strUserID");
                String password = request.getParameter("strPassword");
                if (isValidLogin(username, password)) {
                    url = "search.jsp";
                    UserDTO user = getUser(username);
                    request.getSession().setAttribute("user", user);
                    searchResponse(request, response);
                } else {
                    request.setAttribute("message", "Login failed. Incorrect Username or Password.");
                    url = LOGIN_PAGE;
                }
            }
            if (action != null && action.equals("logout")) {
                PrintWriter out = response.getWriter();
                request.setAttribute("user", null);
                out.println("<b>Logged out</b>");
                out.println("<a href = 'MainController'>Return to login</a>");
                url = "MainController";
                request.getSession().invalidate();
            }
            if (action != null && action.equals("search")) {
                url = "search.jsp";
                searchResponse(request, response);
            }
            if (action != null && action.equals("delete") && AuthenUtils.isAdmin(request.getSession())) {
                url = "search.jsp";
                String bookID = request.getParameter("bookID");
                bdao.delete(bookID);
                searchResponse(request, response);
            }
            if (action != null && action.equals("add") && AuthenUtils.isAdmin(request.getSession())) {
                url = "search.jsp";

                boolean checkError = false;
                String txtBookID = request.getParameter("txtBookID");
                String txtTitle = request.getParameter("txtTitle");
                String txtAuthor = request.getParameter("txtAuthor");

                if (txtBookID == null || txtBookID.trim().isEmpty()) {
                    checkError = true;
                }
                if (txtTitle == null || txtTitle.trim().isEmpty()) {
                    checkError = true;
                }
                if (txtAuthor == null || txtAuthor.trim().isEmpty()) {
                    checkError = true;
                }
                int pubYear = -1;
                if (request.getParameter("txtPubYear") == null || request.getParameter("txtPubYear").equals("")) {
                    checkError = true;
                } else {
                    pubYear = Integer.parseInt(request.getParameter("txtPubYear"));
                }

                double price = -1;
                if (request.getParameter("txtPrice") == null || request.getParameter("txtPrice").equals("")) {
                    checkError = true;
                } else {
                    price = Double.parseDouble(request.getParameter("txtPrice"));
                }

                int quantity = -1;
                if (request.getParameter("txtQuantity") == null || request.getParameter("txtQuantity").equals("")) {
                    checkError = true;
                } else {
                    quantity = Integer.parseInt(request.getParameter("txtQuantity"));
                }

                if (pubYear < 0 || price < 0 || quantity < 0) {
                    checkError = true;
                }

                BookDTO newbook = new BookDTO(txtBookID, txtTitle, txtAuthor, pubYear, price, quantity);

//                System.out.println(newbook);
//                System.out.println(pubYear + " signal inside if 1 " + (txtBookID == null || txtBookID.trim().isEmpty()));
//                System.out.println(checkError + " is check error and is is....hmmm");
                if (checkError) {
                    url = "bookForm.jsp";
                    request.setAttribute("bookInfo", newbook);
                } else {
                    bdao.create(newbook);
                    searchResponse(request, response);
                }

            }
        } catch (Exception e) {
            log("Error at MainController: " + e.toString());
        } finally {
            System.out.println(url);
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
