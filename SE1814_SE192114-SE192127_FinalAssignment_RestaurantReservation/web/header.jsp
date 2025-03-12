<%-- 
    Document   : header
    Created on : Feb 17, 2025, 8:57:14 AM
    Author     : Admin
--%>

<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%
            UserDTO user = new UserDTO();
            if (request.getSession().getAttribute("user") != null) {
                user = (UserDTO) request.getSession().getAttribute("user");
            }


        %>

        <h1>
            This is header!        
            <% if (request.getSession().getAttribute("user") != null) {%>

            <form action="UserController">
                <input type="hidden" name="action" value="home"/>
                <input type="submit" value="HOME"/>
            </form>

            <%if (user.getType().equals("User")) {%>

            <form action="UserController">
                <input type="hidden" name="action" value="myReservations"/>
                <input type="submit" value="My Reservations"/>
            </form>
            <%}%>

            <%if (user.getType().equals("Owner")) {%>

            <form action="UserController">
                <input type="hidden" name="action" value="ownedRestList"/>
                <input type="submit" value="OwnedRestaurants"/>
            </form>
            <%}%>

            <a href="UserProfile.jsp"><img src="users/img/<%= user.getProfilepic()%>"  style="width: 40px; height: 40px; border-radius: 50%"/></a>
                <% }%>
        </h1>

        <%if(user.getType()!=null && !user.getType().equals("Guest")) {%>
        <form action="UserController">
            <input type="hidden" name="action" value="logout"/>
            <input type="submit" value="Logout"/>
        </form>
        <%} else {%>    
        <a href="Login.jsp"> <input type="button" value="Login"/> </a>
        <%}%>
    </body>
</html>
