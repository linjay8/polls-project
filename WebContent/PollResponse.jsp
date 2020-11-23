<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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