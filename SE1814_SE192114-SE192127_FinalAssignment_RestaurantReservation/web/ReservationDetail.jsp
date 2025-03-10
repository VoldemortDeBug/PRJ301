<%-- 
    Document   : ReservationDetail
    Created on : Mar 11, 2025, 12:31:26 AM
    Author     : Admin
--%>

<%@page import="dto.RestDTO"%>
<%@page import="dto.ReservationDTO"%>
<%@page import="dto.REntityDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail reservation</title>
    </head>
    <body>
        
        <%@include file="header.jsp"  %>

        <%
            if (request.getAttribute("rest") != null && request.getAttribute("rent") != null) {
                RestDTO rest = (RestDTO) request.getAttribute("rest");
                REntityDTO rent = (REntityDTO) request.getAttribute("rent");
                ReservationDTO resv = (ReservationDTO) request.getAttribute("resv");
                UserDTO rsvUser =  (UserDTO) request.getAttribute("rsvUser");
        %>
        
        <h1>RESTAURANT DETAILS</h1>
        <%= rest %>
        
        <h1><%= rent.getEnType() %> DETAILS</h1>
        <%= rent %>
        
        <h1>RESERVATION DETAILS</h1>
        <%= resv %>
        
        <h1>CUSTOMER INFO</h1>
        <%= rsvUser %>
        
        <% }%>
        
        <%@include file="footer.jsp"  %>
    </body>
</html>
