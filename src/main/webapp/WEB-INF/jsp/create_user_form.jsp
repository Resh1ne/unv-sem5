<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/userForm.css">
    <title>Create User</title>
</head>

<body id="user-create-page">
    <jsp:include page="navbar.jsp" />

    <h1 id="create-user-title">Create User</h1>

    <form id="create-user-form" action="/users/create" method="post">
        <input type="hidden" name="userId" id="user-id">

        <label for="username" id="username-label">Username:</label>
        <input type="username" id="username" name="username" required><br>

        <label for="email" id="email-label">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="password" id="password-label">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <br/>
        <button type="submit" id="submit-button">Save User</button>
    </form>

    <a href="/users" id="back-to-users-link">Go to users list</a>
</body>

</html>
