<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
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
		String errorMessageLink = (String)request.getAttribute("errorMessageLink");
		String classString = "";
		String meetingLimitString = "";
		String timeslotString = "";
		String linkString = "";
		String startTimeString = "";
		String endTimeString = "";
		if (errorMessageClass != null || errorMessageLink != null) {
			if (errorMessageClass == null)
			{
				errorMessageClass = "";
			}
			else if (errorMessageLink == null)
			{
				errorMessageLink = "";
			}
			classString = request.getParameter("class");
			meetingLimitString = request.getParameter("meetinglimit");
			timeslotString = request.getParameter("timeslot");
			linkString = request.getParameter("link");
			startTimeString = request.getParameter("starttime");
			endTimeString = request.getParameter("endtime");
		} else {
			errorMessageClass = "";
			errorMessageLink = "";
		}
%>
<!-- JSP end -->

<!-- body -->


<div class="bloc l-bloc none " id="demobody">
	<div class="container bloc-md">
		<div class="row">
			<div class="col-md-6">
				<form id="form_1" data-form-type="blocs-form" action="StudentServletStart">
					<button class="bloc-button btn btn-lg btn-block btn-clean btn-d" name = "joinOHButton" type="submit" id="joinOHButton">
						Join OH!
					</button>
				</form>
			</div>
		</div>
	</div>
</div>
<!-- demobody END -->

</div>
<!-- Main container END -->

</body>
</html>