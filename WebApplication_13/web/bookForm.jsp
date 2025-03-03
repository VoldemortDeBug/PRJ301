<%-- 
    Document   : bookForm
    Created on : Feb 27, 2025, 8:16:22 AM
    Author     : Admin
--%>

<%@page import="dto.BookDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="MainController" method="post">
            <%
                BookDTO bookInfo =new BookDTO();
                boolean nobook = true;
                if(request.getAttribute("bookInfo")!=null){
                    bookInfo = (BookDTO) request.getAttribute("bookInfo");
                    nobook = false;
                }
            %>
            <input type="hidden" name="action" value="add"/>
            Book ID: <input type="text" name="txtBookID" value="<%= bookInfo.getBookID()%>"/><br/>
            <%
                if(!nobook && bookInfo.getBookID().equals("")){
            %>
            <span style="color: red">ERROR BOOK ID!</span><br>
            <%}%>
            Title : <input type="text" name="txtTitle" value="<%= bookInfo.getTitle()%>"/><br/>
            <%
                if(!nobook && bookInfo.getTitle().equals("")){
            %>
            <span style="color: red">ERROR BOOK TITLE!</span><br>
            <%}%>
            Author: <input type="text" name="txtAuthor" value="<%= bookInfo.getAuthor()%>"/><br/>
            <%
                if(!nobook && bookInfo.getAuthor().equals("")){
            %>
            <span style="color: red">ERROR BOOK AUTHOR!</span><br>
            <%}%>
            Publish year: <input type="number" name="txtPubYear" value="<%= (bookInfo.getPublishyear()>0)?bookInfo.getPublishyear():""%>"/><br/>
            <%
                if(!nobook && bookInfo.getPublishyear()<0){
            %>
            <span style="color: red">ERROR BOOK PUBLISHYEAR!</span><br>
            <%}%>
            Price: <input type="number" name="txtPrice" value="<%= (bookInfo.getPrice()>0)?bookInfo.getPrice():""%>"/><br/>
            <%
                if(!nobook && bookInfo.getPrice()<0){
            %>
            <span style="color: red">ERROR BOOK PRICE!</span><br>
            <%}%>
            Quantity: <input type="number" name="txtQuantity" value="<%= (bookInfo.getQuantity()>0)?bookInfo.getQuantity():""%>"/><br/>
            <%
                if(!nobook && bookInfo.getQuantity()<0){
            %>
            <span style="color: red">ERROR BOOK QUANTITY!</span><br>
            <%}%>
            <input type="submit" value="Save"/><br/>
            <input type="reset" value="Delete"/>
        </form>
    </body>
</html>
