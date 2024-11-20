<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/user.css">
    <title>User</title>
</head>

<body id="user-page">
    <jsp:include page="navbar.jsp" />
    <h1 id="user-info-title">User Information</h1>
    <ul id="user-info-list">
        <li><strong>ID:</strong>
            <c:out value="${user.id}" />
        </li>
        <li><strong>Name:</strong>
            <c:out value="${user.username}" />
        </li>
        <li><strong>Email:</strong>
            <c:out value="${user.email}" />
        </li>
        <li><strong>Password:</strong>
            <c:out value="${user.password}" />
        </li>
        <li><strong>Created At:</strong>
            <c:out value="${user.createdAt}" />
        </li>
        <li><a href="/users" id="go-to-users-list">Go to users list</a></li>
    </ul>
</body>

</html>