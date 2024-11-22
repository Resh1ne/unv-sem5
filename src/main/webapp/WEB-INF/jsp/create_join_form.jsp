<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/joinForm.css">
    <title>Join Event</title>
    <script>
        // Функция для обновления action формы перед отправкой
        function updateAction(event) {
            // Получаем значение, которое ввел пользователь
            var accessKey = document.getElementById("accessKey").value;

            // Обновляем атрибут action формы с подставленным значением accessKey
            var form = document.getElementById("join-event-form");
            form.action = "/events/screen-share/" + accessKey;
        }
    </script>
</head>

<body id="event-join-page">
    <jsp:include page="navbar.jsp" />

    <h1 id="join-event-title">Join Event</h1>

    <form id="join-event-form" action="/events/screen-share/{accessKey}" method="GET" onsubmit="updateAction(event)">
        <input type="hidden" name="eventId" id="event-id">

        <label for="accessKey" id="accessKey-label">Access key:</label>
        <input type="text" id="accessKey" name="accessKey" required><br>

        <br/>
        <button type="submit" id="submit-button">Join</button>
    </form>
</body>

</html>
