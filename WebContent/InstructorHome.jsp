<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ page import = "models.*" %>
<meta charset="UTF-8">
<title>Instructor Home</title>


<link rel="stylesheet" type="text/css" href="homePageStyles.css" />

</head>
<body>
	
	<%
	String email = (String)request.getSession().getAttribute("email"); 
/* 	String name = DatabaseUtil.getInstructor(email).getFullName(); */
	 %>

	<h1>Instructor Home Page</h1>
<%-- 	<p>Welcome,  <%=name %> </p>  --%>
	
	 <p>Welcome</p>
	<a href="CreateClass.html">Create class</a>
	<br><br>
	<a href="CheckBox.html">Create poll</a>
	<br><br>
	<a href="Results.html">View Results</a>
	<br><br>
	<a href="availableChatrooms">Start Chatting</a>
	<br><br>
	<a href="InstructorStart.jsp">Start Office Hours</a>
	
	
	
	
	
</body>
</html>