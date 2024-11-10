<html>
  <head>
    <title>Exhibition hall</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Exhibition Hall</h1>
    <h3>${date}</h3>
    <p>Id: ${exhibition.id}</p>
    <p>Name: ${exhibition.name}</p>
    <p>Address: ${exhibition.address}</p>
    <p>Phone: ${exhibition.phone}</p>
    <p>Owner: ${exhibition.ownerId.name}</p>
  </body>
</html>