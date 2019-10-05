<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Shop</title>
</head>
<body>
<h1> User logged in: </h1>
<div style="color: #000000;">${user}</div>
<form action="ProcessInfo" method ="post">
	<input type="submit" name="bt" value="Logout">
</form>
<br>
<h1> Antal varor in varukorg: </h1>
<div style="color: #000000;">${antalVarorIVarukorg}</div>
<form action="ProcessInfo" method ="post">
	<input type="submit" name="bt" value="Visa">
</form>
<br>
<h1> Produkter </h1>

<table>
  <c:forEach items="${produktlista}" var="produkt">
  <form action="ProcessInfo" method ="post">     
	<label>${produkt.name}</label>
    <input type="hidden" id="vara" name="vara" value="${produkt.name}">
	<input type="submit" name="bt" value="buy">
   </form>
   <br>
  </c:forEach>
</table>

</body>
</html>