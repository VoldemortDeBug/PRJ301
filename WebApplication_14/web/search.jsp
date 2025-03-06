<%-- 
    Document   : user
    Created on : Feb 16, 2025, 6:55:32 PM
    Author     : Admin
--%>

<%@page import="java.util.Random"%>
<%@page import="dto.BookDTO"%>
<%@page import="java.util.List"%>
<%@page import="java.rmi.ServerError"%>
<%@page import="dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User Page</title>
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

        <%@include file="header.jsp"%>
        <c:if test="${not empty sessionScope.user}">
            <c:set var="searchTerm" value="${requestScope.searchTerm==null?'':requestScope.searchTerm}">
                <form action="MainController">
                    <input type="hidden" name="action" value="logout"/>
                    <input type="submit" value="Logout"/>
                </form>

                <br/>


                <%                Random rand = new Random();
                    int rnum;
                    String imgsrc = "";
                %>
                <form action="MainController" method="get">
                    <input type="hidden" name="action" value="search"/>
                    Book title : <input type="text" name="searchTerm" value="${searchTerm}"/>
                    <input type="submit" value="search"/>
                </form>

                <c:if test="${sessionScope.user.roleID eq 'AD'}">
                    <a href="bookForm.jsp">
                        Add new book
                    </a>
                </c:if>

                <%
                    if (request.getAttribute("books") != null) {
                        List<BookDTO> listBooks = (List<BookDTO>) request.getAttribute("books");
                %>
                <c:if test="${not empty requestScope.books}">

                    <table>
                        <tr>
                            <td>ID</td>
                            <td>Title</td>
                            <td>Author</td>
                            <td>Price</td>
                            <td>PublishYear</td>
                            <td>Quantity</td>
                        </tr>

                        <%
                            for (BookDTO i : listBooks) {
                        %>
                        <tr>
                            <td><%= i.getBookID()%></td>
                            <td><%= i.getTitle()%></td>
                            <td><%= i.getAuthor()%></td>
                            <td><%= i.getPrice()%></td>
                            <td><%= i.getPublishyear()%></td>
                            <td><%= i.getQuantity()%></td>

                            <%
                                rnum = rand.nextInt(2);
                                if (rnum == 1) {
                                    imgsrc = "zamn.jpg";
                                } else {
                                    imgsrc = "chad.png";
                                }
                            %>
                            <td> <a href="MainController?action=delete&bookID=<%= i.getBookID()%>&searchTerm=${searchTerm}"><img src="assets/img/<%=imgsrc%>" width="30" height="30" alt="chad"/></a> </td>
                        </tr>
                        <%}%>
                    </table>
                </c:if>

                <%@include file="footer.jsp"  %>
            </c:set>
        </c:if>

    </body>
</html>
