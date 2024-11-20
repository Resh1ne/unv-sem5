<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/users.css">
    <title>Users List</title>
</head>

<body id="users-list-page">
    <jsp:include page="navbar.jsp" />
    <h1 id="users-list-title">Users List</h1>
    <table id="users-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user" varStatus="counter">
                <tr id="user-${user.id}">
                    <td>
                        <c:out value="${user.id}" />
                    </td>
                    <td>
                        <c:out value="${user.username}" />
                    </td>
                    <td>
                        <c:out value="${user.email}" />
                    </td>
                    <td><a href="users/${user.id}" id="user-${user.id}-details-link">Details</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>

</html>