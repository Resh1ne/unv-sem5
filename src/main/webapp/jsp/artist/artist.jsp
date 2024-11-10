<html>
  <head>
    <title>Artist</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Artist</h1>
    <h3>${date}</h3>
    <p>Id: ${artist.id}</p>
    <p>Name: ${artist.name}</p>
    <p>Birth place: ${artist.birthPlace}</p>
    <p>Birth date: ${artist.birthDate}</p>
    <p>Biography: ${artist.biography}</p>
    <p>Education: ${artist.education}</p>
  </body>
</html>