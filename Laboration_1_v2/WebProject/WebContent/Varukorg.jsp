<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@page import="test.felix.Vara" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Varukorg</title>
</head>
<body>
<h1> User logged in: </h1>
<div style="color: #000000;">${user}</div>
<form action="ProcessInfo" method ="post">
	<input type="submit" name="bt" value="Logout">
</form>
<br>
<table>
  <c:forEach items="${varukorgslista}" var="vara">
   <tr>
     <td>${vara.name}</td>
   </tr>
  </c:forEach>
</table>
<form action="ProcessInfo" method ="post">
	<input type="submit" name="bt" value="Back">
</form>
</body>
</html>