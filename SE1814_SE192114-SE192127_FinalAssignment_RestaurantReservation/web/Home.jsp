<%-- 
    Document   : Home
    Created on : Mar 2, 2025, 5:02:16 PM
    Author     : Admin
--%>

<%@page import="dto.RestDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME</title>
    </head>
    <body>

        <%@include file="header.jsp" %>

        <h1>Hello!</h1>
        <h1>welcome <%= user.getName()%></h1>

        <br/>

        <%
            List<RestDTO> lrest;
            if (request.getAttribute("allrest") != null) {
                lrest = (List<RestDTO>) request.getAttribute("allrest");
                for (RestDTO i : lrest) {
        %>
        <a href="UserController?action=clientRestProfile&restID=<%= i.getResID() %>"><img src="users/rimg/<%= (i.getMainPhoto()==null)?"Default.jpg":i.getMainPhoto() %>" /></a><br/>
        <span><%=i%></span>
        <br/>
        <br/>
        <br/>

        <% } %>
        <% }%>


    </body>
</html>
