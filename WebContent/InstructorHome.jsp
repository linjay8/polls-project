<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<% 
		String email = (String)request.getSession().getAttribute("email"); 
	%>

	<h1>Instructor Home Page</h1>
	<p>add name, class list </p>
	
	<a href="CreateClass.html">Create class</a>
	<br>
	<a href="CheckBox.html">Create poll</a>
	<br>
	<a href="Results.html">View Results</a>
	<br>
	<a href="availableChatrooms">Start Chatting</a>
	<br>
	<a href="InstructorStart.jsp">Start Office Hours</a>
	
	<br></br>
	<h1>Viewable Polls</h1>
	
	
	
</body>
</html>