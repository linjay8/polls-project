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
	
	<h1>Student Home Page</h1>
	<p>add name, class list </p>
	
	<a href="Enroll.html">Add a new class</a>
	<br>
	<a href="PollList">View class polls</a>
	<br>
	<a href="PublicPollList">View public polls</a>
	<br>
	<a href="OldPolls">View previous polls</a>
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
	<br></br>
	<h1>Viewable Polls</h1>
	

</body>
</html>