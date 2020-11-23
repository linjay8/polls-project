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
	
    <title>Instructor Start Office Hours Page</title>

</head>
<body>
<!-- Main container -->

	<h1>Instructor Start Office Hours</h1>



<div class="page-container">

<!-- JSP checking for existing error message -->

<%
		String email = (String)request.getSession().getAttribute("email");

		String errorMessageClass = (String)request.getAttribute("errorMessageClass");
		String errorMessageLink = (String)request.getAttribute("errorMessageLink");
		String errorMessageStart = (String)request.getAttribute("errorMessageStart");
		String errorMessageClassDNE = (String)request.getAttribute("errorMessageClassDNE");
		String errorMessageInstructorClass = (String)request.getAttribute("errorMessageInstructorDNE");
		String classString = "";
		String meetingLimitString = "";
		String timeslotString = "";
		String linkString = "";
		String minLongString = "";
		if (errorMessageClass != null || errorMessageLink != null || errorMessageStart != null
				|| errorMessageClassDNE != null || errorMessageInstructorClass != null) {
			if (errorMessageClass == null)
			{
				errorMessageClass = "";
			}
			if (errorMessageLink == null)
			{
				errorMessageLink = "";
			}
			if (errorMessageStart == null)
			{
				errorMessageStart = "";
			}
			if (errorMessageClassDNE == null)
			{
				errorMessageClassDNE = "";
			}
			if (errorMessageInstructorClass == null)
			{
				errorMessageInstructorClass = "";
			}
			classString = request.getParameter("class");
			meetingLimitString = request.getParameter("meetinglimit");
			timeslotString = request.getParameter("timeslot");
			linkString = request.getParameter("link");
			minLongString = request.getParameter("minlong");
		} else {
			errorMessageClass = "";
			errorMessageLink = "";
			errorMessageStart = "";
			errorMessageClassDNE = "";
			errorMessageInstructorClass = "";
		}
%>

	<h1><%=email %></h1>
<!-- JSP end -->

<!-- body -->



<form id="form_1" data-form-type="blocs-form" action="InstructorServlet">
	<div class="form-group" id="formclass">
		<label>
			Class Code
		</label>
		<input id="class" name="class" value="<%= classString %>"class="form-control"/>
		<%= errorMessageClass %>
		<%= errorMessageClassDNE %>
	</div>
	<div class="form-group" id="formmeetinglimit">
		<label>
			Meeting Limit
		</label>
		<input id="meetinglimit" name="meetinglimit" value="<%= meetingLimitString %>"class="form-control"/>
	</div>
	<div class="form-group" id="formtimeslot">
		<label>
			Time Slot Length
		</label>
		<input id="timeslot" name="timeslot" value="<%= timeslotString %>"class="form-control"/>
	</div>
	<div class="form-group" id="formlink">
		<label>
			Zoom Link
		</label>
		<input id="link" name="link" value="<%= linkString %>"class="form-control"/>
		<%= errorMessageLink %>
	</div>
	<div class="form-group" id="formendtime">
		<label>
			Enter length of office hours (in minutes)
		</label>
		<input id="minlong" name="minlong" value="<%= minLongString %>"class="form-control"/>
	</div>
	<br/>
	<button class="bloc-button btn btn-lg btn-block btn-clean btn-d" name = "startOHButton" type="submit" id="startOHButton">
		Start
	</button>
	<%= errorMessageStart %>
</form>

<%= errorMessageInstructorClass %>


<!-- demobody END -->


</div>
<!-- Main container END -->

</body>
</html>