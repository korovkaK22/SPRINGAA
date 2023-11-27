<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ page errorPage="errorPage.jsp" %>--%>

<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="../../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../../css/queuepage.css">

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
<jsp:include page="../header.jsp" />

<div class="centerSign">
    Черга "<c:out value="${queue.name}"/>"<c:if test="${queue.isOpen == false}">(Закрита)</c:if>
</div>

<div class="queue-introduction">
    Власник: <c:out value="${queue.owner.username}"/>
</div>

<div class="queue-list">

    <c:choose>
        <c:when test="${queue.users.size()==0}">
            <div class="queue-introduction">
                Ще ніхто не записався у цю чергу.<br> Але ви можете це виправити!
            </div>
        </c:when>
        <c:otherwise>
            <%-- Пішла сама таблиця--%>
            <table class="user-table">
                <tr>
                    <th>Номер</th>
                    <th>Ім'я</th>
                </tr>
                <c:forEach items="${queue.users}" var="user" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td class="user-name"><c:out value="${user.username}"/></td>
                    </tr>
                </c:forEach>
            </table>

        </c:otherwise>
    </c:choose>
</div>

<%--Внизу кнопочки--%>

<c:if test="${sessionScope.user == null}">
    <div class="queue-introduction">
        Для запис в чергу потрібно <br> <a href="/login"> авторизуватися </a>
    </div>
</c:if>


<c:if test="${sessionScope.user != null}">
<div class="buttons">
        <form action="/your-target-page">
            <button type="submit">Записатися</button>
        </form>
    <c:if test="${sessionScope.user.id == queue.owner.id}">
        <form action="/your-target-page">
            <button type="submit">Закрити чергу</button>
        </form>
        <form action="/your-target-page">
            <button type="submit">Здвинути чергу</button>
        </form>
    </c:if>
</div>
</c:if>



</body>
</html>