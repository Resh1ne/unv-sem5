<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Update Artist</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Update artist</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="edit_artist" />
      <input name="id" type="hidden" value="${artist.id}" />
      <label>Name:<input name="name" type="text" required value="${artist.name}"/></label>
      <br />
      <label>Birth place:<input type="text" name="birth_place" required value="${artist.birthPlace}"/></label>
      <br />
      <label>Birth date:<input type="date" name="birth_date" required value="${artist.birthDate}"/></label>
      <br />
      <label>Biography:<input type="text" name="biography" value="${artist.biography}"/></label>
      <br />
      <label>Education:<input type="text" name="education" value="${artist.education}"/></label>
      <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
