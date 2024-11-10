<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Current Exhibitions</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Current Exhibitions</h1>
    <table>
      <tr>
        <th>#</th>
        <th>Name</th>
        <th>Address</th>
      </tr>

      <c:forEach items="${exhibitions}" var="exhibition" varStatus="counter">
        <tr>
          <td>${counter.count}</td>
          <td>${exhibition.exhibitionName}</td>
          <td>${exhibition.hallAddress}</td>
        </tr>
      </c:forEach>
    </table>
  </body>
</html>