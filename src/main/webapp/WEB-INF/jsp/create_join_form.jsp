<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/joinForm.css">
    <title>Join Event</title>
</head>

<body id="event-join-page">
    <jsp:include page="navbar.jsp" />

    <h1 id="join-event-title">Join Event</h1>

    <form id="join-event-form" action="/events/join" method="post">
        <input type="hidden" name="eventId" id="event-id">

        <label for="accessKey" id="accessKey-label">Access key:</label>
        <input type="text" id="accessKey" name="accessKey" required><br>

        <br/>
        <button type="submit" id="submit-button">Join</button>
    </form>
</body>

</html>
