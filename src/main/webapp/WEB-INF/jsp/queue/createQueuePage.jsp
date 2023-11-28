<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ page errorPage="errorPage.jsp" %>--%>

<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="../../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../../css/authorization.css">

    <%--Гугл шрифт на черги--%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alegreya:wght@500&display=swap" rel="stylesheet">

    <%--Гугл шрифт на верхній надпис--%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alegreya:wght@500&family=Vollkorn:wght@500&display=swap"
          rel="stylesheet">

</head>
<body>
<jsp:include page="../header.jsp"/>


<c:if test="${user == null}">
<div class="centerSign">
    Чергу можуть створювати <br> тільки зареєстровані користувачі.<br>
    <a href="/login">Увійти</a>
</div>
</c:if>

<c:if test="${user != null}">
<div class="centerSign">
    Створення черги
</div>
<div class="authorization-container">
    <form action="/queues/create" method="post">
        <input type="text" name="name" id="name" placeholder=" Ім'я черги" required>
        <button type="submit">Створити</button>
    </form>
    </c:if>




