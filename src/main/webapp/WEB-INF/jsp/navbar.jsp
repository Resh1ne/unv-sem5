<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <link rel="stylesheet" type="text/css" href="/css/navbar.css">
        </head>

        <body>
            <nav>
                <ul>
                    <li><a href="/" onclick="navigate('home')">Home</a></li>
                    <li><a href="/users" onclick="navigate('allUsers')">All Users</a></li>
                    <c:if test="${empty pageContext.request.session.getAttribute('user')}">
                        <li><a href="/login" onclick="navigate('allUsers')">Sign in</a></li>
                        <li><a href="/users/create" onclick="navigate('allUsers')">Sign Up</a></li>
                    </c:if>
                    <c:if test="${not empty pageContext.request.session.getAttribute('user')}">
                        <li><a href="/logout" onclick="navigate('allUsers')">Log out</a></li>
                    </c:if>

                </ul>
            </nav>

        </body>

        </html>