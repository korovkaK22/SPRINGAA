<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot Session Example</title>
</head>
<body>
<div>
    <!-- Form Action might need to be adjusted based on your JSP setup -->
    <form action="<c:url value='/persistMessage'/>" method="post">
        <textarea name="msg" cols="40" rows="2"></textarea>
        <br> <input type="submit" value="Save Message" />
    </form>
</div>
<div>
    <h2>Messages</h2>
    <ul>
        <!-- Replace Thymeleaf iteration with JSP JSTL c:forEach tag -->
        <c:forEach var="message" items="${sessionMessages}">
            <li>${message}</li>
        </c:forEach>
    </ul>
</div>
<div>
    <!-- Form Action might need to be adjusted based on your JSP setup -->
    <form action="<c:url value='/destroy'/>" method="post">
        <input type="submit" value="Destroy Session" />
    </form>
</div>
</body>
</html>
