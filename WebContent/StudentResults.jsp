<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<link rel="stylesheet" type="text/css" href="style.css" />

<title>Office Hours Started!</title>
</head>
<body>
		
<h1>Student has joined Office Hours</h1>
<% 
String emailString = request.getParameter("email");
Student s = DatabaseUtil.getStudent(emailString);
response.setIntHeader("Refresh", (int)(s.getOHClass().getOH().getTimeSlot())/2); 
%>
		
	<div class="bloc l-bloc none " id="demobody">
	<div class="container bloc-md">
		<div class="row">
			<div class="col-md-6">
				<form id="form_1" data-form-type="blocs-form" action="StudentServletStart">
					<button class="bloc-button btn btn-lg btn-block btn-clean btn-d" name = "leaveOHButton" type="submit" id="leaveOHButton">
						Leave OH
					</button>
				</form>
			</div>
		</div>
	</div>
</div>
</body>
</html>