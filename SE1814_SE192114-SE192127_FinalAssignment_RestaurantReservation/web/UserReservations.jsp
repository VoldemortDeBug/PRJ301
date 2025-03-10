<%-- 
    Document   : UserReservations
    Created on : Mar 10, 2025, 7:53:12 AM
    Author     : Admin
--%>

<%@page import="dto.ReservationDTO"%>
<%@page import="dto.RestDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@include file="header.jsp"  %>
        <h1>My reservations: </h1>
        <%
            List<ReservationDTO> lresv;
            if (request.getAttribute("allresv") != null) {
                lresv = (List<ReservationDTO>) request.getAttribute("allresv");
                for (ReservationDTO i : lresv) {
        %>
        <%= i%>
        
        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="checkReservation"/>
            <input type="hidden" name="rsvID" value="<%= i.getRsvID()%>"/>
            <input type="submit" value="Details"/>
        </form>        

        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="cancelReservation"/>
            <input type="hidden" name="rsvID" value="<%= i.getRsvID()%>"/>
            <input type="submit" value="Cancel reservation"/>
        </form>
        <br/>


        <%
                }
            }
        %>


        <%@include file="footer.jsp"  %>
    </body>
</html>
