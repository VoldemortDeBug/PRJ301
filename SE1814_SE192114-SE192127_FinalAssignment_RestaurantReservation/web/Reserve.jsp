<%-- 
    Document   : Reserve
    Created on : Mar 9, 2025, 1:35:00 PM
    Author     : Admin
--%>

<%@page import="java.util.List"%>
<%@page import="dto.ReservationDTO"%>
<%@page import="util.Utils"%>
<%@page import="java.sql.Date"%>
<%@page import="dto.REntityDTO"%>
<%@page import="dto.RestDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>RESERVATION MAKING</title>
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
        <%@include file="header.jsp"  %>

        <%
            if (request.getAttribute("rest") != null && request.getAttribute("rent") != null) {
                RestDTO rest = (RestDTO) request.getAttribute("rest");
                REntityDTO rent = (REntityDTO) request.getAttribute("rent");
                int ap = 0;
                if (request.getAttribute("ap") != null) {
                    ap = (int) request.getAttribute("ap");
                }
        %>

        <h1>Available time for reservation: </h1>
        <table>
            <tr>
                <td>Date</td>
                <% for (int i = 0; i < 24; i++) {%>
                <td><%=i%>h</td>
                <% }%>
            </tr>

            <%
                for (int i = 0; i < 7; i++) {
                    Date idate = Utils.xdaysFromNow(i + ap);
                    if (request.getAttribute(idate + "") != null) {
                        List<ReservationDTO> lresv = (List<ReservationDTO>) request.getAttribute(idate + "");
            %>
            <tr>
                <td><%= idate%></td>
                <% for (int j = 0; j < 24; j++) {%>
                <td>
                    <%if (idate.after(rent.getActiveTill()) || !Utils.hasbit(rent.getWeekly(), Utils.toWeekDay(idate)) || !Utils.hasbit(rent.getDaily(), Utils.PO2[j])) {%>
                        <div style="background-color: beige; width: 30px; height: 30px">-</div>
                        <% } else {
                            int rsvID = Utils.reservationAtTime(lresv, j, idate);
                            if (rsvID == -1) {%>
                                <div style="background-color: <%= (Utils.xdaysFromNow(0).equals(idate)) ? "beige" : "#66ff66"%>; width: 30px; height: 30px"></div>
                            <% } else {%>
                                <%if (user.getType().equals("Owner") && user.getUserID()==rest.getOwnerID()) {%><a href="UserController?action=checkReservation&restID=<%= rest.getResID()%>&entID=<%= rent.getEnID()%>&rsvID=<%= rsvID%>"><%}%>
                                <div style="background-color: #ff9999; width: 30px; height: 30px"></div>
                                <%if (user.getType().equals("Owner") && user.getUserID()==rest.getOwnerID()) {%></a><%}%>
                            <% } %>
                        <% } %>
                </td>
                <% } %>
            </tr>

            <% }%>
            <% }%>

        </table>

        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="reservePage"/>
            <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
            <input type="hidden" name="entID" value="<%= rent.getEnID()%>"/>
            <input type="hidden" name="ap" value="<%= ((ap - 7) >= 0) ? ap - 7 : ap%>"/>
            <input type="submit" value="<-Prev">
        </form>

        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="reservePage"/>
            <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
            <input type="hidden" name="entID" value="<%= rent.getEnID()%>"/>
            <input type="hidden" name="ap" value="<%= ap + 7%>"/>
            <input type="submit" value="Next->">
        </form>

        <br/><br/>

        <% if (!user.getType().equals("Owner")) { %>

        <h1>Making reservation:</h1>
        <%
            int rSeat = 1;
            if (request.getAttribute("rSeat") != null) {
                rSeat = (int) request.getAttribute("rSeat");
            }
            Date rDate = null;
            if (request.getAttribute("rDate") != null) {
                rDate = (Date) request.getAttribute("rDate");
            }

        %>
        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="makeReservation"/>
            <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
            <input type="hidden" name="entID" value="<%= rent.getEnID()%>"/>
            People: <input max="<%= rent.getSeatCap()%>" type="number" name="rSeat" min=1 step=1 required value="<%= rSeat%>" /> (<= <%= rent.getSeatCap()%> )<br/>
            Date: <input type="date" value="<%= rDate%>" name="rDate" min="<%= Utils.xdaysFromNow(1)%>" max="<%= Utils.xdaysFromNow(rent.getForwardLim()).before(rent.getActiveTill()) ? Utils.xdaysFromNow(rent.getForwardLim()) : rent.getActiveTill()%>" /> (*You can only reserve maximum <%= rent.getForwardLim()%> days into the future and until <%= rent.getActiveTill()%>*)<br/>
            Time:
            <%
                boolean[] checkhour = new boolean[24];
                if (request.getAttribute("checkhour") != null) {
                    checkhour = (boolean[]) request.getAttribute("checkhour");
                }
            %>
            <%for (int i = 0; i < 24; i++) {%>
            <input type="checkbox" name="rHour" value=<%= i + ""%> <%= checkhour[i] ? "checked" : ""%> > <%= i%>h |
            <%}%>
            <br>


            <input type="submit" value="Reserve">
        </form>
        <%}%>



        <% }%>

        <%@include file="footer.jsp"  %>
    </body>
</html>
