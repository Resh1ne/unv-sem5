<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" type="text/css" href="/css/error.css">
</head>
<body>
    <div class="error-container">
        <h1>${statusCode}</h1>
        <h1>Error</h1>
        <p>${error}</p>
    </div>
</body>
</html>