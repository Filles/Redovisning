<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Start</title>
</head>
<body>

<h2> Logga in för att kunna handla: </h2>

<h3> Skapa konto: </h3>
<form action="ProcessInfo" method ="post">
<label>anvandarnamn : </label>
<input type="text" name="anvandarnamn"><br>
<label>losenord : </label>
<input type="text" name="losenord"><br>
<input type="submit" name="bt" value="Skapa">
</form>

<h3> Logga in: </h3>
<form action="ProcessInfo" method ="post">
<label>anvandarnamn : </label>
<input type="text" name="anvandarnamn"><br>
<label>losenord : </label>
<input type="text" name="losenord"><br>
<input type="submit" name="bt" value="Login">
</form>
<br>
<div style="color: #FF0000;">${errorMessage}</div>

</body>
</html>