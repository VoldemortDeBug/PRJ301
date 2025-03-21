<%-- 
    Document   : user
    Created on : Feb 16, 2025, 6:55:32 PM
    Author     : Admin
--%>

<%@page import="dto.BookDTO"%>
<%@page import="java.util.List"%>
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
        <%        
             String action = request.getParameter("action");
            UserDTO user = (UserDTO) request.getSession().getAttribute("user");
            if(user!=null){
            
         %>
        <%@include file="header.jsp"%>
        <div style="min-height: 800px">
         
            <h1>welcome <%= user.getFullname() %></h1>
            <form action = "MainController" method="get">
                <input type="hidden" name="action" value="logout"/>
                <input type="submit" value="logout"/>
            </form>
            
            
        </div>
            
            <form action="MainController" method="get">
                <input type="hidden" name="action" value="search"/>
                Book title : <input type="text" name="searchTerm"/>
                <input type="submit" value="search"/>
            </form>
            
            <%
                if(request.getAttribute("books")!=null){
                    List<BookDTO> listBooks = (List<BookDTO>) request.getAttribute("books");
            %>
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
                    for(BookDTO i:listBooks){
                %>
                <tr>
                    <td><%= i.getBookID() %></td>
                    <td><%= i.getTitle() %></td>
                    <td><%= i.getAuthor() %></td>
                    <td><%= i.getPrice() %></td>
                    <td><%= i.getPublishyear() %></td>
                    <td><%= i.getQuantity() %></td>
                </tr>
                <%}%>
            </table>
            <%}%>
            
        <%@include file="footer.jsp"  %>
        
        <%}else{%>
        <h1 style="align-content: center; text-align: center">GET OUTTTTT!!!!!!!!!</h1>
        <%}%>
        
</html>
