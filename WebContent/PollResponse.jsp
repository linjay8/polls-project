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
		[For testing] Enter class code <input type="text" name="classCode"/>
		<br>
		[For testing] Enter user token <input type="text" name="userId"/>
		<input type = "submit" value = "Submit" />  
	</form>

</body>
</html>