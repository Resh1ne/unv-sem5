<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Create Artwork</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new artwork</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_artwork" />
      <label>Title:<input name="title" type="text" required /></label>
      <br />
      <label>Creation date:<input type="date" name="creation_date" required /></label>
      <br />
      <label>Height:<input type="number" name="height" /></label>
      <br />
      <label>Width:<input type="number" name="width" /></label>
      <br />
      <label>Volume:<input type="number" name="volume" /></label>
      <br />
      <label for="artists">Enter artist:</label>
      <select name="artist">
        <c:forEach var="art" items="${artists}">
          <option value="${art.id}">${art.name}</option>
        </c:forEach>
      </select>
      <br />
      <label for="types">Enter type:</label>
                    <select name="execution_type">
                      <option value="PAINTING">Painting</option>
                      <option value="WATERCOLOR">Watercolor</option>
                      <option value="SCULPTURE">Sculpture</option>
                    </select>
      <br />
      <input type="submit" value="Create" />
    </form>
  </body>
</html>
