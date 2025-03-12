<%-- 
    Document   : Home
    Created on : Mar 2, 2025, 5:02:16 PM
    Author     : Admin
--%>

<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
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
            String sortterm = "a-z";
            if (request.getAttribute("sortterm") != null) {
                sortterm = (String) request.getAttribute("sortterm");
            }
        %>
        <form action="UserController">
            <input type="hidden" name="action" value="sortHome"/>
            <input type="hidden" name="sortterm" value="<%= sortterm%>"/>
            <input type="submit" value="Sort by name <%= sortterm%>"/>
        </form>

        <%
            String searchTerm = "";
            if (request.getAttribute("searchTerm") != null) {
                searchTerm = (String) request.getAttribute("searchTerm");
            }
        %>
        <form action="UserController">
            <input type="hidden" name="action" value="searchName"/>
            <input type="hidden" name="sortterm" value="<%= sortterm%>"/>
            <input type="text" name="searchTerm" value="<%= searchTerm%>"/>
            <input type="submit" value="Search"/>
        </form>


        <br/>

        <%
            ArrayList<RestDTO> lrest;
            if (request.getAttribute("allrest") != null) {
                lrest = (ArrayList<RestDTO>) request.getAttribute("allrest");
                if (sortterm.equals("z-a")) {
                    Collections.sort(lrest);
                    Collections.reverse(lrest);
                }
                if (sortterm.equals("a-z")) {
                    Collections.sort(lrest);
                }
                for (int x = 0; x < lrest.size(); x++) {
                    RestDTO i = lrest.get(x);
        %>

        <a href="UserController?action=clientRestProfile&restID=<%= i.getResID()%>"><img src="users/rimg/<%= (i.getMainPhoto() == null) ? "Default.jpg" : i.getMainPhoto()%>" /></a><br/>
        <span><%=i%></span>
        <br/>
        <br/>
        <br/>

        <% } %>
        <% }%>


    </body>
</html>
