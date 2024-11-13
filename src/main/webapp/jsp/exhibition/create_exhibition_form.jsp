<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Create Exhibition</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new exhibition</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_exhibition" />
      <label>Name:<input name="name" type="text" required /></label>
      <br />
      <label>Start date:<input type="date" name="start_date" min="2018-01-01" max="2040-01-01" required /></label>
      <br />
      <label>End date:<input type="date" name="end_date" min="2018-01-01" max="2040-01-01" required /></label>
      <br />
      <label for="exhibition_halls">Enter exhibition hall:</label>
      <select name="exhibition_hall">
        <c:forEach var="exH" items="${exhibition_halls}">
          <option value="${exH.id}">${exH.name}</option>
        </c:forEach>
      </select>
      <br />
       <label for="types">Enter type:</label>
              <select name="type">
                <option value="VISUAL_ART">Visual Art</option>
                <option value="APPLIED_ART">Applied Art</option>
                <option value="SCULPTURE">Sculpture</option>
              </select>
      <br />
      <input type="submit" value="Create" />
    </form>
  </body>
</html>
