<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Create Artist</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new artist</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_artist" />
      <label>Name:<input name="name" type="text" required /></label>
      <br />
      <label>Birth place:<input type="text" name="birth_place" required /></label>
      <br />
      <label>Birth date:<input type="date" name="birth_date" required /></label>
      <br />
      <label>Biography:<input type="text" name="biography" /></label>
      <br />
      <label>Education:<input type="text" name="education" /></label>
      <br />
      <input type="submit" value="Create" />
    </form>
  </body>
</html>
