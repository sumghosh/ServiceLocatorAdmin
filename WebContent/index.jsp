<%@ page language="java" 
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Service Locator Admin</title>
</head>
<body>
  <form action="companies" method="post">
      <input type="submit" name="jdbc_query" value="Query DB" />
  </form>
    <form action="admin/saxparsing" method="post">
      <input type="submit" name="saxparser" value="SAX Parser" />
  </form>
    <form action="rest/xmlServices/send" method="post">
      <input type="submit" name="restclient" value="REST Client with XML input" />
  </form>
</body>
</html>