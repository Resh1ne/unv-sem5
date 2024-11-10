<html>
  <head>
    <title>Artwork</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Artwork</h1>
    <h3>${date}</h3>
    <p>Id: ${artwork.id}</p>
    <p>Title: ${artwork.title}</p>
    <p>Execution: ${artwork.executionType}</p>
    <p>Creation date: ${artwork.creationDate}</p>
    <p>Height: ${artwork.height}</p>
    <p>Width: ${artwork.width}</p>
    <p>Artist: ${artwork.artistId.name}</p>
  </body>
</html>