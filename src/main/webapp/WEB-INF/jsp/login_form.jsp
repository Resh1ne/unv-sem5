<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <jsp:include page="navbar.jsp" />
</head>

<body id="login-page">
    <h1 id="login-title">Sign in</h1>

    <form id="login-form" action="/login" method="post">

        <label for="email" id="email-label">Email:</label>
        <input type="email" id="email" name="email" required><br>

        <label for="password" id="password-label">Password:</label>
        <input type="password" id="password" name="password" required><br>

        <br />
        <button type="submit" id="login-button">Sign in</button>
    </form>

    <br />
    <a href="/users/create" id="signup-link">Go to sign up</a>
</body>

</html>