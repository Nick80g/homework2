<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>

</head>
<body>

<h2>Список книг</h2>

<c:forEach var="book" items="${requestScope.book}">
    <ul>
        ID: <c:out value="${book.id}"/> <br>
        Название: <c:out value="${book.title}"/> <br>
    </ul>
    <hr/>
</c:forEach>

<a href="/books-list">List</a>



</body>
