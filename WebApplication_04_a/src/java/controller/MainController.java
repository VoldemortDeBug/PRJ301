/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
    public int GCD(int a,int b){
        int min = Math.min(a, b);
        for(int i=min;i>=1;i--){
            if(a%i==0 && b%i==0){
                return i;
            }
        }
        return 1;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String txtA= request.getParameter("txtA");
            String txtB= request.getParameter("txtB");
            
            if(txtA==null || txtA.trim().length() == 0){
                out.println("pls enter value");
                return;
            }
             if(txtB==null || txtB.trim().length() == 0){
                out.println("pls enter value");
                return;
            }
            
            int a=0;
            int b=0;
            try{
                a=Integer.parseInt(txtA);
                b=Integer.parseInt(txtB);
                
            }catch(Exception e){
                out.println("must be valid values");
            }
             
            int res = GCD(a, b);
            out.println("GCD = "+res);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
