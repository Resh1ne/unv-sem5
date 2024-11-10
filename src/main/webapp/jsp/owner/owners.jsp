<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Owners</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Owners</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Name</th>
      </tr>

      <c:forEach items="${owners}" var="owner" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${owner.id}</td>
          <td>
            <a href="controller?command=owner&id=${owner.id}">${owner.name}</a>
          </td>
          <td>
            <a href="controller?command=edit_owner_form&id=${owner.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>