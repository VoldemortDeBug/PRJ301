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

        <h1>book information</h1>

        <%--use javabean--%>
        <jsp:useBean id="bookInfo" class="dto.BookDTO" scope="request" >
        
        <form action="MainController" method="post">
            
            
            <input type="hidden" name="action" value="add"/>
            Book ID: <input type="text" name="txtBookID" value="${book.bookID}"/><br/>
         
            <c:if test="${book.bookID==null}">
            <span style="color: red">ERROR BOOK ID!</span><br>
            </c:if>>
            
            Title : <input type="text" name="txtTitle" value="${book.title}"/><br/>
            
            <c:if test="${book.title==null}">
            <span style="color: red">ERROR BOOK TITLE!</span><br>
            </c:if>
            Author: <input type="text" name="txtAuthor" value="${book.author}"/><br/>
            
            <c:if test="${book.author==null}">
            <span style="color: red">ERROR BOOK AUTHOR!</span><br>
            </c:if>
            Publish year: <input type="number" name="txtPubYear" value="${book.publishyear}"/><br/>
            
            <c:if test="${book.publishyear==null}">
            <span style="color: red">ERROR BOOK PUBLISHYEAR!</span><br>
            </c:if>
            Price: <input type="number" name="txtPrice" value="${book.price}"/><br/>
            
            <c:if test="${book.price<0}">
            <span style="color: red">ERROR BOOK PRICE!</span><br>
            </c:if>
            Quantity: <input type="number" name="txtQuantity" value="${book.quantity}"/><br/>
            
            <c:if test="${book.quantity<0}">
            <span style="color: red">ERROR BOOK QUANTITY!</span><br>
            </c:if>
            <input type="submit" value="Save"/><br/>
            <input type="reset" value="Delete"/>
        </form>
        </jsp:useBean>
    </body>
</html>
