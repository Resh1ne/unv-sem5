<html>
  <head>
    <title>Exhibition</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Exhibition</h1>
    <h3>${date}</h3>
    <p>Id: ${exhibition.id}</p>
    <p>Title: ${exhibition.name}</p>
    <p>Start date: ${exhibition.startDate}</p>
    <p>End date: ${exhibition.endDate}</p>
    <button class="favorite styled" type="button" onclick="window.location.href='controller?command=exhibition_par_and_art&id=${exhibition.id}'">
        Display the Participants and Paintings of the exhibition
    </button>
  </body>
</html>