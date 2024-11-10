<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Exhibition Halls</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Exhibition Halls</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Name</th>
        <th>Address</th>
        <th>Area</th>
        <th>Owner</th>
      </tr>

      <c:forEach items="${exhibition_halls}" var="exhibition_hall" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${exhibition_hall.id}</td>
          <td>
            <a href="controller?command=exhibition_hall&id=${exhibition_hall.id}">${exhibition_hall.name}</a>
          </td>
          <td>${exhibition_hall.address}</td>
          <td>${exhibition_hall.area}</td>
          <td>${exhibition_hall.ownerId.name}</td>
          <td>
            <a href="controller?command=edit_exhibition_hall_form&id=${exhibition_hall.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>