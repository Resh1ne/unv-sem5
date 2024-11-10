<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Artworks</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Artworks</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Title</th>
        <th>Artist</th>
      </tr>

      <c:forEach items="${artworks}" var="artwork" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${artwork.id}</td>
          <td>
            <a href="controller?command=artwork&id=${artwork.id}">${artwork.title}</a>
          </td>
          <td>${artwork.artistId.name}</td>
          <td>
            <a href="controller?command=edit_artwork_form&id=${artwork.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>