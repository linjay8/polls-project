<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Checkbox</title>
</head>
<%!  
	String classCode="";
	String values1="";
	String values2="";
	String values3="";
	String values4="";
	String private_= "";
	String public_= "";
	
	
%>
<body>
<h1>Reading Checkbox Data</h1>
<ul>
 	<!-- Check if class code exists -->
	<!-- Check that classCode input has only numbers-->
	
	<% classCode = request.getParameter("classCode"); 
	if( !classCode.equals("") )%>
		<li><%= classCode %> </li>

	<!-- Check that only one box is selected -->
	<% public_ = request.getParameter("public");
	private_ = request.getParameter("private"); 
	
	if( classCode.equals("") && public_.equals("Public") )%>
		<li><%= "Poll is public" %> </li>
	
<%-- 	<% if(!classCode.equals("") && private_.equals("Private"))%> 
		<li>"Poll is private" </li> --%>
	




	<!-- Handle value equals null -->
	<% values1 = request.getParameter("answer1"); 
	if(! (values1.equals(""))){%>
		<li><%= values1 %> </li>
	<%}%>
	
	<% values2 = request.getParameter("answer2"); 
	if( ! (values2.equals(""))){%>
		<li><%= values2 %> </li>
	<%}%>
		
	<% values3 = request.getParameter("answer3"); 
	if( ! (values3.equals(""))){%>
		<li><%= values3 %> </li>
	<%}%>
		
	<% values4 = request.getParameter("answer4"); 
	if( !(values4.equals(""))){%>
		<li><%= values4 %> </li>
	<%}%>
	
	
</ul>
</body>
</html>
