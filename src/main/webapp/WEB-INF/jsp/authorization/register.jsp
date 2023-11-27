<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Реєстрація</title>
    <link rel="stylesheet" type="text/css" href="../../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../../css/authorization.css">


</head>
<body>
<div class="mainDiv">
    <div class="centerSign">
        Реєстрація
    </div>

    <div class="authorization-container">
        <form action="/register" method="post" >
            <input type="text" name="username" id="username" value="${username}" placeholder=" Логін" required>
            <input type="password" name="password" id="password" placeholder=" Пароль" required minlength="3">
            <input type="password" name="reppassword" id="reppassword" placeholder=" Підтвердження паролю" required minlength="3">
            <button type="submit">Зареєструватися</button>
            <div class="or-action"><a href="/login">Увійти</a></div>
        </form>

        <c:if test="${failMessage != null}">
            <div class="failMessage"><c:out value="${failMessage} "/></div>
        </c:if>
    </div>




</div>
</body>
</html>
