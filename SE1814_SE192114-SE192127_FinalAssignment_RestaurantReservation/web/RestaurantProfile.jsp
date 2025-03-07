<%-- 
    Document   : RestaurantProfile
    Created on : Mar 5, 2025, 10:22:58 PM
    Author     : Admin
--%>

<%@page import="util.Utils"%>
<%@page import="dto.REntityDTO"%>
<%@page import="dto.PhotoDTO"%>
<%@page import="java.util.List"%>
<%@page import="dto.RestDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>

        <style>
            .image-container {
                position: relative;
                display: inline-block;
                width: 300px; /* Fixed width */
                height: 200px; /* Fixed height */
                overflow: hidden;
            }

            .image-container img {
                display: block;
                width: 100%; /* Match container width */
                height: 100%; /* Match container height */
                object-fit: cover; /* Ensures image covers area */
            }

            .hover-btn {
                position: absolute;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                background-color: rgba(0, 0, 0, 0.7);
                color: white;
                border: none;
                padding: 10px 20px;
                cursor: pointer;
                border-radius: 5px;
                opacity: 0; /* Initially hidden */
                visibility: hidden;
                transition: opacity 0.3s ease, visibility 0.3s ease;
                width: 120px; /* Fixed button width */
                height: 40px; /* Fixed button height */
                text-align: center;
                line-height: 40px; /* Centers text vertically */
            }

            /* Show button when hovering over the image or button */
            .image-container:hover .hover-btn,
            .hover-btn:hover {
                opacity: 1;
                visibility: visible;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <%
            if (request.getAttribute("rest") != null) {
                RestDTO rest = (RestDTO) request.getAttribute("rest");

        %>

        <h1>Restaurant: <%= rest.getName()%></h1>
        <h1>Location: <%= rest.getLoc()%></h1>

        <h1>Photos:</h1>

        <%if (request.getAttribute("lphoto") != null) {
                List<PhotoDTO> lphoto = (List<PhotoDTO>) request.getAttribute("lphoto");
                for (PhotoDTO i : lphoto) {
        %>

        <div class="image-container">
            <img src="users/rimg/<%= i.getPhoto()%>">
            <form method="post" action="UserController">
                <input type="hidden" name="action" value="deleteResPhoto"/>
                <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
                <input type="hidden" name="photoID" value="<%= i.getPhotoID()%>"/>
                <button class="hover-btn"  type="submit">Delete</button>
            </form>
        </div>

        <%}%>
        <%}%>

        <h1>Add restaurant picture</h1>

        <form method="post" action="UserController" enctype="multipart/form-data">
            <input type="hidden" name="action" value="addRestPic"/>
            <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
            New Profile Photo: <input type="file" name="rphoto" size="50" /><br/>
            <input type="submit" value="Save">

        </form>


        <%if (request.getAttribute("lent") != null) {
                List<REntityDTO> lent = (List<REntityDTO>) request.getAttribute("lent");%>
        <h1>List of tables/rooms</h1>

        <%
            for (REntityDTO i : lent) {
        %>

        <div><%= i%></div>


        <%}%>
        <%}%>


        <h1>Add new table/room</h1>

        <%
            String select1 = "";
            String select2 = "";
            int eFee = 0;
            if (request.getAttribute("eFee") != null) {
                eFee = (int) request.getAttribute("eFee");
            }
            int eSeatCap = 0;
            if (request.getAttribute("eSeatCap") != null) {
                eSeatCap = (int) request.getAttribute("eSeatCap");
            }
            int eForwardLim = 0;
            if (request.getAttribute("eForwardLim") != null) {
                eForwardLim = (int) request.getAttribute("eForwardLim");
            }

        %>

        <form method="post" action="UserController" >
            <input type="hidden" name="action" value="createEntity"/>
            <input type="hidden" name="restID" value="<%= rest.getResID()%>"/>
            Reservation Fee: <input type="number" name="eFee"  required value="<%= eFee%>"/><br/>
            Type: 
            <select id="options" name="eType">
                <option value="Table" <%= select1%>>Table</option>
                <option value="Room" <%= select2%>>Room</option>
            </select><br/>
            Seat capacity: <input type="number" name="eSeatCap" min=1 step=1 required value="<%= eSeatCap%>" /><br/>
            Forward limit (days): <input type="number" name="eForwardLim" min=1 step=1 required  value="<%= eForwardLim%>"/><br/>
            Active until: <input type="date" name="eActiveTill"/> (*You can only extend the 'Active Until' date beyond the forward limit*)<br/>
            Daily hours available:
            <%
                boolean[] checkhour = new boolean[24];
                if (request.getAttribute("checkhour") != null) {
                    checkhour = (boolean[]) request.getAttribute("checkhour");
                }
            %>
            <%for (int i = 0; i < 24; i++) {%>
            <input type="checkbox" name="eHour" value=<%= i + ""%> <%= checkhour[i] ? "checked" : ""%> > <%= i%> |
            <%}%>
            <br>

            Weekdays available:
            <%
                boolean[] checkday = new boolean[7];
                if (request.getAttribute("checkday") != null) {
                    checkday = (boolean[]) request.getAttribute("checkday");
                }
            %>
            <%for (int i = 0; i < 7; i++) {%>
            <input type="checkbox" name="eDay" value=<%= i + ""%> <%= checkday[i] ? "checked" : ""%> > <%= Utils.weekday(i)%> |
            <%}%>
            <br>


            <input type="submit" value="Create">
            <%
                String tMessage = "";
                if (request.getAttribute("createEntMes") != null) {
                    tMessage = (String) request.getAttribute("createEntMes");
                }
            %>
            <span style="color: green"><%= tMessage%></span>
        </form>



        <a href="Home.jsp"><input type="button" value="Home" /></a>

        <%}%>
        <%@include file="footer.jsp"  %>

    </body>
</html>
