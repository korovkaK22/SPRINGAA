<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" type="text/css" href="../../css/homepage.css">
    <link rel="stylesheet" type="text/css" href="../../css/errorPage.css">
    <script>
        function toggleDeveloperInfo() {
            const developerInfo = document.querySelector('.developer-info');
            if (developerInfo.style.display === 'none') {
                developerInfo.style.display = 'block';
            } else {
                developerInfo.style.display = 'none';
            }
        }
    </script>
</head>
<body>
<h1>Error Occurred</h1>
<p>Something went wrong. Please try again later.</p>

<p class="toggle-link" onclick="toggleDeveloperInfo()">Інформація для розробників:</p>
<div class="developer-info">
   
    <p>${pageContext.request.getAttribute("javax.servlet.error.timestamp")}</p>
    <p>There was an unexpected error (type=${pageContext.request.getAttribute("javax.servlet.error.exception_type")},
        status=${pageContext.request.getAttribute("javax.servlet.error.status_code")}).
    </p>
    <p>${pageContext.request.getAttribute("javax.servlet.error.message")}</p>
    <p>${pageContext.request.getAttribute("javax.servlet.error.exception")}</p>
    <p>${pageContext.request.getAttribute("javax.servlet.error.request_uri")}</p>
</div>
</body>
</html>
