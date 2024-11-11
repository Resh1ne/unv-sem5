<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Edit</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register exhibition hall</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="edit_exhibition_hall" />
      <input name="id" type="hidden" value="${exhibition.id}" />
      <label
        >Name:<input name="name" type="text" required value="${exhibition.name}"
      /></label>
      <br />
      <label
        >Area:<input name="area" type="number" value="${exhibition.area}"
      /></label>
      <br />
      <label
        >Address:<input name="address" type="text" value="${exhibition.address}"
      /></label>
      <br />
      <label for="phone">
        Phone(+375291234578):
        <input
          type="tel"
          name="phone"
          pattern="+[0-9]{3}[0-9]{2}[0-9]{3}[0-9]{2}[0-9]{2}"
          required
          value="${exhibition.phone}"
        />
        <br />
        <label for="owners">Enter owner:</label>
        <select name="ownerId">
          <c:forEach var="owner" items="${owners}">
            <option value="${owner.id}" ${exhibition.ownerId.id==owner.id ? 'selected' : ''}>${owner.name}</option>
          </c:forEach>
        </select>
        <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
