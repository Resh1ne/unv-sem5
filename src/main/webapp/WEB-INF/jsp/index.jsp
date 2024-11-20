<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <div id="button-container">
        <button id="create-event-button" onclick="navigateTo('controller?command=exhibition_halls')">Create Event</button>
        <button id="join-event-button" onclick="navigateTo('controller?command=exhibition_halls')">Join Event</button>
    </div>
</body>
</html>
