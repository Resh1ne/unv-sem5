<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>Update Artwork</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Update artwork</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="edit_artwork" />
      <input name="id" type="hidden" value="${artwork.id}" />
      <label>Title:<input name="title" type="text" required value="${artwork.title}" /></label>
      <br />
      <label>Creation date:<input type="date" name="creation_date" required value="${artwork.creationDate}" /></label>
      <br />
      <label>Height:<input type="number" name="height" value="${artwork.height}" /></label>
      <br />
      <label>Width:<input type="number" name="width" value="${artwork.width}" /></label>
      <br />
      <label>Volume:<input type="number" name="volume" value="${artwork.volume}" /></label>
      <br />
      <label for="artists">Enter artist:</label>
      <select name="artist">
        <c:forEach var="art" items="${artists}">
          <option value="${art.id}" ${artwork.artistId.id==art.id ? 'selected' : ''}>${art.name}</option>
        </c:forEach>
      </select>
      <br />
      <label for="types">Enter type:</label>
                          <select name="execution_type">
                            <option value="PAINTING" ${artwork.executionType=="PAINTING" ? 'selected' : ''}>Painting</option>
                            <option value="WATERCOLOR" ${artwork.executionType=="WATERCOLOR" ? 'selected' : ''}>Watercolor</option>
                            <option value="SCULPTURE" ${artwork.executionType=="SCULPTURE" ? 'selected' : ''}>Sculpture</option>
                          </select>
      <br />
      <input type="submit" value="Update" />
    </form>
  </body>
</html>
