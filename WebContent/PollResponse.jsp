<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Enter Class Code</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">


</head>
<body>
	
	<% String email = (String)request.getSession().getAttribute("email"); %>
	
	<form name="myForm" action = "PollList" method = "GET">
		Enter class code <input type="text" name="classCode"/>
		<br>
		<input type = "submit" value = "Submit" />  
	</form>

</body>
</html>