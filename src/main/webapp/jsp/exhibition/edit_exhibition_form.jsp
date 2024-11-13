<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Update Exhibition</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Update exhibition</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="edit_exhibition" />
      <input name="id" type="hidden" value="${exhibition.id}" />
      <label>Name:<input name="name" type="text" required value="${exhibition.name}" /></label>
      <br />
      <label>Start date:<input type="date" name="start_date" min="2018-01-01" max="2040-01-01" value="${exhibition.startDate}" required /></label>
      <br />
      <label>End date:<input type="date" name="end_date" min="2018-01-01" max="2040-01-01" value="${exhibition.endDate}" required /></label>
      <br />
      <label for="exhibition_halls">Enter exhibition hall:</label>
      <select name="exhibition_hall">
        <c:forEach var="exH" items="${exhibition_halls}">
          <option value="${exH.id}" ${exhibition.hallId.id==exH.id ? 'selected' : ''} >${exH.name}</option>
        </c:forEach>
      </select>
      <br />
       <label for="types">Enter type:</label>
              <select name="type">
                <option value="VISUAL_ART" ${exhibition.type=="VISUAL_ART" ? 'selected' : ''}>Visual Art</option>
                <option value="APPLIED_ART" ${exhibition.type=="APPLIED_ART" ? 'selected' : ''}>Applied Art</option>
                <option value="SCULPTURE" ${exhibition.type=="SCULPTURE" ? 'selected' : ''}>Sculpture</option>
              </select>
      <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
