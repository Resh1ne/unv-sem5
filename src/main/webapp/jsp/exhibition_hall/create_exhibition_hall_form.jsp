<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Create Exhibition hall</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new exhibition hall</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_exhibition_hall" />
      <label>Name:<input name="name" type="text" required /></label>
      <br />
      <label>Area:<input type="number" name="area" min="0" max="1000" /></label>
      <br />
      <label>Address:<input type="text" name="address" required /></label>
      <br />
      <label for="phone">
        Phone(+375291234578):
        <input
          type="tel"
          name="phone"
          pattern="+[0-9]{3}[0-9]{2}[0-9]{3}[0-9]{2}[0-9]{2}"
          required
        />
      </label>
      <br />
      <label for="owners">Enter owner:</label>
      <select name="ownerId">
        <c:forEach var="owner" items="${owners}">
          <option value="${owner.id}">${owner.name}</option>
        </c:forEach>
      </select>
      <br />
      <input type="submit" value="Create" />
    </form>
  </body>
</html>
