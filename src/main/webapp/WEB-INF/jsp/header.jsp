<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../css/header.css">

    <%--Гугл шрифт на хедер--%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alegreya:wght@500&family=Varela&family=Vollkorn:wght@500&display=swap"
          rel="stylesheet">


</head>
<body>
<header>
    <nav>
        <div class="nav-item"><a href="/">На Головну</a></div>
        <div class="nav-item"><a href="/">Усі черги</a></div>
        <div class="nav-item"><a href="/">Створити чергу</a></div>
        <c:if test="${sessionScope.user == null}">
            <div class="nav-item"><a href="/login">Увійти</a></div>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <form action="/logout" method="post" id="logoutForm" style="display: none;">
            </form>
            <div class="nav-item">
                Вітаємо, <c:out value="${sessionScope.user.name}"/>
                <a href="#" onclick="document.getElementById('logoutForm').submit(); return false;">(Вийти)</a>
            </div>

        </c:if>
    </nav>
</header>

</body>
</html>
