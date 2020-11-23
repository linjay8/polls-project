<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ page import = "models.*" %>
<meta charset="UTF-8">
<title>Student Home</title>
<link rel="stylesheet" type="text/css" href="homePageStyles.css" />
</head>

<body>

	<% 
		String email = (String)request.getSession().getAttribute("email");
	 	/* String name = DatabaseUtil.getStudent(email).getFullName(); */
	%>
	
	
	<h1>Student Home Page</h1>
	<%-- <p>Welcome,  <%=name %> </p> --%>
	<br>
	<br>
	<a href="Enroll.html">Add a new class</a>
	<br>
	<br>
	<a href="PollList">View class polls</a>
	<br>
	<br>
	<a href="PublicPollList">View public polls</a>
	<br>
	<br>
	<a href="OldPolls">View previous polls</a>
	<br>
	<br>
	<a href="availableChatrooms">Start Chatting</a>
	
	
	<br>
	<a href="StudentStart.jsp">Join Office Hours </a>
	
<!-- 	<form name="myForm" action = "StudentStart.jsp" method = "GET">
	Join Office Hours 
	<br>
	
	Class Code<input type="text" name="classCode"/>
	<input type = "submit" value = "Submit" />  
	</form>
	 -->

</body>
</html>