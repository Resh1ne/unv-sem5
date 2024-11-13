<!DOCTYPE html>
<html>
  <head>
    <title>Create owner</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <jsp:include page="../navbar.jsp" />
    <h1>Register new owner</h1>
    <form method="post" action="controller">
      <input name="command" type="hidden" value="create_owner" />
      <label>Name:<input name="name" type="text" required /></label>
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
       <label for="types">Enter type:</label>
              <select name="type">
                <option value="CITY_ORGANIZATION">City organization</option>
                <option value="REGIONAL_ORGANIZATION">Regional organization</option>
                <option value="PUBLIC_ORGANIZATION">Public organization</option>
                <option value="PRIVATE_INDIVIDUAL">Private organization</option>
              </select>
      <br />
      <input type="submit" value="Create" />
    </form>
  </body>
</html>
