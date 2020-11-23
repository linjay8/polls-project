<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<%@ page import = "models.*" %>
    <meta charset="utf-8">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
        
	<link rel="stylesheet" type="text/css" href="style.css" />
	
    <title>Student Join Office Hours Page</title>

</head>
<body>
<!-- Main container -->

	<h1>Student Join Office Hours</h1>


<div class="page-container">

<!-- JSP checking for existing error message -->
<%
		String errorMessageClass = (String)request.getAttribute("errorMessageClass");
		String classString = "";
		String errorMessageStart = (String)request.getAttribute("errorMessageStart");
		String errorMessageClassDNE = (String)request.getAttribute("errorMessageClassDNE");
		String errorMessageStudentClass = (String)request.getAttribute("errorMessageInstructorDNE");
		
		if (errorMessageClass != null || errorMessageStart != null
				|| errorMessageClassDNE != null || errorMessageStudentClass != null) {
			if (errorMessageClass == null)
			{
				errorMessageClass = "";
			}
			if (errorMessageStart == null)
			{
				errorMessageStart = "";
			}
			if (errorMessageClassDNE == null)
			{
				errorMessageClassDNE = "";
			}
			if (errorMessageStudentClass == null)
			{
				errorMessageStudentClass = "";
			}
		} else {
			errorMessageClass = "";
			errorMessageStart = "";
			errorMessageClassDNE = "";
			errorMessageStudentClass = "";
		}
%>
<!-- JSP end -->

<!-- body -->

<form id="form_1" data-form-type="blocs-form" action="StudentServletStart">
	<div class="form-group" id="formclass">
		<label>
			Class Code
		</label>
		<input id="class" name="class" value="<%= classString %>"class="form-control"/>
		<%= errorMessageClass %>
		<%= errorMessageClassDNE %>
		<br/>
		<br/>

	</div>
	<button class="bloc-button btn btn-lg btn-block btn-clean btn-d" name = "joinOHButton" type="submit" id="joinOHButton">
						Join OH!
	</button>
	<%= errorMessageStart %>
</form>

<%= errorMessageStudentClass %>

<!-- demobody END -->

</div>
<!-- Main container END -->

</body>
</html>