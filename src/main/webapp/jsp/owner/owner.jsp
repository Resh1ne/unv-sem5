<html>
  <head>
    <title>Owner</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Owner</h1>
    <h3>${date}</h3>
    <p>Id: ${owner.id}</p>
    <p>Name: ${owner.name}</p>
    <p>Address: ${owner.address}</p>
    <p>Phone: ${owner.phone}</p>
    <p>Type: ${owner.ownerType}</p>
  </body>
</html>