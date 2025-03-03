<%-- 
    Document   : user
    Created on : Feb 16, 2025, 6:55:32 PM
    Author     : Admin
--%>

<%@page import="org.apache.coyote.ErrorState"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Random"%>
<%@page import="java.util.List"%>
<%@page import="java.rmi.ServerError"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
        <style>
            table {
                border: 2px solid black; /* Outer border */
                border-collapse: collapse; /* Merges cell borders */
            }
            td, th {
                border: 1px solid black; /* Borders for each cell */
                padding: 8px;
            }
        </style>
    </head>
    <body>
        <%        
             String action = request.getParameter("action");
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            if(user!=null){
            
         %>
        <%@include file="header.jsp"%>
            <h1>welcome <%= user.getName()%></h1>
            <form action = "MainController" method="get">
                <input type="hidden" name="action" value="logout"/>
                <input type="submit" value="logout"/>
            </form>
            
                    
        <%@include file="footer.jsp"  %>
        
        <%}else{%>
        <h1>You're not logged in.</h1>
        <%}%>
        
</html>
