<%-- 
    Document   : Receipt
    Created on : Mar 10, 2025, 2:40:34 AM
    Author     : Admin
--%>

<%@page import="util.Utils"%>
<%@page import="java.sql.Date"%>
<%@page import="dto.REntityDTO"%>
<%@page import="dto.RestDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ReceiptPage</title>
    </head>
    <body>
        <%@include file="header.jsp"  %>

        <%
            if (request.getAttribute("rest") != null && request.getAttribute("rent") != null) {
                RestDTO rest = (RestDTO) request.getAttribute("rest");
                REntityDTO rent = (REntityDTO) request.getAttribute("rent");
        %>


        <h1>Reservation for <%=rent.getEnType()%></h1>


        <%
            int rSeat = 1;
            if (request.getAttribute("rSeat") != null) {
                rSeat = (int) request.getAttribute("rSeat");
            }
            Date rDate = null;
            if (request.getAttribute("rDate") != null) {
                rDate = (Date) request.getAttribute("rDate");
            }
            int rDaily = 0;
            if (request.getAttribute("rDaily") != null) {
                rDaily = (int) request.getAttribute("rDaily");
            }

        %>
        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="confirmReservation"/>
            <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
            <input type="hidden" name="entID" value="<%= rent.getEnID()%>"/>
            <input type="hidden" name="rSeat" value="<%= rSeat%>"/>
            <input type="hidden" name="rDate" value="<%= rDate%>"/>
            <input type="hidden" name="rDaily" value="<%= rDaily%>"/>

            People: <%= rSeat%> <br/>
            Date: <%= rDate%> <br/>
            Time:
            <%
                boolean[] checkhour = new boolean[24];
                if (request.getAttribute("checkhour") != null) {
                    checkhour = (boolean[]) request.getAttribute("checkhour");
                }
            %>
            <%
                for (int i = 0; i < 24; i++)
                    if (checkhour[i]) {
            %>
            <%= i%>h 
            <%}%>
            <br>
            Total fee: <%= Integer.bitCount(rDaily) * rent.getEnFee()%> <br/>


            <input type="submit" value="Confirm">
        </form>



        <% }%>

        <%@include file="footer.jsp"  %>    </body>
</html>
