<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Exhibition Participants and Artworks</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Participants and Artworks on ${exhibition.name}, from ${exhibition.startDate} to ${exhibition.endDate}</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Artwork title</th>
        <th>Execution</th>
        <th>Artist name</th>
        <th>Artist age</th>
        <th>Creation date</th>
      </tr>

      <c:forEach items="${exhibitions}" var="exh" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${exh.artworkTitle}</td>
          <td>${exh.executionType}</td>
          <td>${exh.artistName}</td>
          <td>${exh.artistAge}</td>
          <td>${exh.creationDate}</td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>