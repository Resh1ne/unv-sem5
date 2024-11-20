<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>

<head>
    <link rel="stylesheet" type="text/css" href="/css/eventForm.css">
    <title>Create Event</title>
</head>

<body id="event-create-page">
    <jsp:include page="navbar.jsp" />

    <h1 id="create-event-title">Create Event</h1>

    <form id="create-event-form" action="/events/create" method="post">
        <input type="hidden" name="eventId" id="event-id">

        <label for="title" id="title-label">Title:</label>
        <input type="text" id="title" name="title" required><br>

        <label for="description" id="description-label">Description:</label>
        <input type="text" id="description" name="description" required><br>

        <br/>
        <button type="submit" id="submit-button">Create</button>
    </form>
</body>

</html>
