<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<%@ page errorPage="errorPage.jsp" %>--%>

<html>
<head>
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="../../css/default.css">
    <link rel="stylesheet" type="text/css" href="../../css/homepage.css">

    <%--Гугл шрифт на черги--%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alegreya:wght@500&display=swap" rel="stylesheet">

    <%--Гугл шрифт на верхній надпис--%>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Alegreya:wght@500&family=Vollkorn:wght@500&display=swap" rel="stylesheet">

</head>
<body>
<jsp:include page="../header.jsp"/>


<c:if test="${user == null}">
    <div class="centerSign">
        Передивлятись свої черги можуть <br> тільки зареєстровані користувачі <br>
        <a href="/login">Увійти</a>
    </div>
</c:if>

<c:if test="${user != null}">

    <div class="lastQueues">

        <c:choose>
            <c:when test="${queues.size()==0}">У вас ще немає черг. Але ви можете це виправити!</c:when>
            <c:otherwise>
                <div class="queueTitle">
                    Мої черги
                </div>
                <c:forEach items="${queues}" var="queue">
                    <div class="queue">
                        <a href="/queues/${queue.id}"><c:out value="${queue.name}"/>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>


</c:if>


</body>
</html>