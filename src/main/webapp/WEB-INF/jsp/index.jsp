<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="stylesheet" type="text/css" href="/css/index.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <jsp:include page="navbar.jsp" />
</head>

<body id="home-page">
    <c:if test="${empty pageContext.request.session.getAttribute('user')}">
        <!-- Если пользователь не авторизован, перенаправить на страницу логина -->
        <script type="text/javascript">
            window.location.href = '/login'; // Переход на страницу авторизации
        </script>
    </c:if>

    <c:if test="${not empty pageContext.request.session.getAttribute('user')}">
        <!-- Если пользователь авторизован, показываем кнопки -->
        <div id="button-container">
            <button id="create-event-button" onclick="window.location.href='/events/create'">Create Event</button>
            <button id="join-event-button" onclick="window.location.href='/events/join'">Join Event</button>
        </div>
    </c:if>
</body>

</html>
