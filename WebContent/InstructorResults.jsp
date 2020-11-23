<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
   <%@ page import = "models.*" %>
<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="style.css" />

<title>Office Hours Started!</title>
</head>
<body>
	<h1>Instructor Office Hours Started</h1>
	
	<div id="gallery-text">

    	<div id="gallery-text-left">
    	<p id="gallery-paragraph-1">
		<!-- print students in meeting -->
    	<%-- <%
    		String emailString = (String)request.getSession().getAttribute("email");
    		Instructor i = DatabaseUtil.getInstructor(emailString);
   			
    	//	System.out.println("TIME " + (int)i.getCurrentOH().getTimeSlot());
   			response.setIntHeader("Refresh", 15);
   	    	
   	    	for (Student s : i.getCurrentOH().getStudentsInMeeting()) 
   	    	{
   	    		out.println(s.getFullName());
  	%>
    		<br/>
    	<%}%> --%>
    	</p>
    </div>
   	<div id="gallery-text">

    	<div id="gallery-text-left">
    	<p id="gallery-paragraph-1">
    	<!-- print students in wait list -->
    	<%-- <%
    	int count = 0;
    	for (Student s : i.getCurrentOH().getWaitingStudents()) 
    	{
    		out.println(count + ": " + s.getFullName());  
    		count++;}%> --%>
    		

    	</p>
    </div>
</div>
	
	<div class="bloc l-bloc none " id="demobody">
	<div class="container bloc-md">
		<div class="row">
			<div class="col-md-6">
				<form id="form_1" data-form-type="blocs-form" action="InstructorServletResults">
					<button class="bloc-button btn btn-lg btn-block btn-clean btn-d" name = "endOHButton" type="submit" id="endOHButton">
						End OH
					</button>
				</form>
			</div>
		</div>
	</div>
	
	
	
</body>
</html>