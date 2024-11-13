<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Create owner</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new owner</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="edit_owner" />
      <input name="id" type="hidden" value="${owner.id}" />
      <label>Name:<input name="name" type="text" value="${owner.name}" required /></label>
      <br />
      <label>Address:<input type="text" name="address" value="${owner.address}" required /></label>
      <br />
      <label for="phone">
              Phone(+375291234578):
              <input
                type="tel"
                name="phone"
                pattern="+[0-9]{3}[0-9]{2}[0-9]{3}[0-9]{2}[0-9]{2}"
                value="${owner.phone}"
                required
              />
      </label>
      <br />
       <label for="types">Enter type:</label>
              <select name="type">
                <option value="CITY_ORGANIZATION" ${owner.ownerType=="CITY_ORGANIZATION" ? 'selected' : ''}>City organization</option>
                <option value="REGIONAL_ORGANIZATION" ${owner.ownerType=="REGIONAL_ORGANIZATION" ? 'selected' : ''}>Regional organization</option>
                <option value="PUBLIC_ORGANIZATION" ${owner.ownerType=="PUBLIC_ORGANIZATION" ? 'selected' : ''}>Public organization</option>
                <option value="PRIVATE_INDIVIDUAL" ${owner.ownerType=="PRIVATE_INDIVIDUAL" ? 'selected' : ''}>Private organization</option>
              </select>
      <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
