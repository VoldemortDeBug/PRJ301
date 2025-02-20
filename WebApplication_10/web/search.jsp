<%-- 
    Document   : user
    Created on : Feb 16, 2025, 6:55:32 PM
    Author     : Admin
--%>

<%@page import="java.rmi.ServerError"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
    </head>
    <body>
        <%@include file="header.jsp"%>
        <div style="min-height: 800px">
         <%        
             String action = request.getParameter("action");
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            if(user==null){
                user=new UserDTO();
            }
         %>
            <h1>welcome <%= user.getFullname() %></h1>
            <form action = "MainController" method="get">
                <input type="hidden" name="action" value="logout"/>
                <input type="submit" value="logout"/>
            </form>
            
        </div>
        <%@include file="footer.jsp"  %>
        
</html>
