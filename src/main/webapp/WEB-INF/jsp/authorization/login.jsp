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


<div class="mainDiv">
    <div class="centerSign">
        Авторизація
    </div>

    <%--Логін--%>
    <div class="login-container">
        <form action="/login" method="post">
            <input type="text" name="username" value="${username}" placeholder=" Логін" required>
            <input type="password" name="password" placeholder=" Пароль" required>
            <button type="submit">Ввійти</button>
        </form>
        <c:if test="${failMessage != null}">
            <div class="failMessage"><c:out value="${failMessage} "/></div>
        </c:if>
    </div>

    <div class="buttons">

    </div>

</div>


</body>
</html>