<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Artists</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Artists</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Name</th>
      </tr>

      <c:forEach items="${artists}" var="artist" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${artist.id}</td>
          <td>
            <a href="controller?command=artist&id=${artist.id}">${artist.name}</a>
          </td>
          <td>
            <a href="controller?command=edit_artist_form&id=${artist.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>