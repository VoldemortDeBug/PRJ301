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
            UserDTO user = new UserDTO();
            if(request.getAttribute("user")!=null){
                user=(UserDTO) request.getAttribute("user");
            }else{
                user.setFullname("");
            }
            String text="";
            if(action == null){
                text="Return to login page";
            }
         %>
            <h1>welcome <%= user.getFullname() %></h1>
            <a href="Login.jsp"><%=text%></a>
            <a href ="MainController?action=logout">LOGOUT<a/>        
        </div>
        <%@include file="footer.jsp"  %>
        
</html>
