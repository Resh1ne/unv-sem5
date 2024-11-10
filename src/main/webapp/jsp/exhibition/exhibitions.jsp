<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Exhibitions</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>All Exhibitions</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Id</th>
        <th>Name</th>
      </tr>

      <c:forEach items="${exhibitions}" var="exhibition" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${exhibition.id}</td>
          <td>
            <a href="controller?command=exhibition&id=${exhibition.id}">${exhibition.name}</a>
          </td>
          <td>
            <a href="controller?command=edit_exhibition_form&id=${exhibition.id}">Edit</a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>
