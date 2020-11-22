<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="keywords" content="">
	    <meta name="description" content="">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
	    
		
	    <title>Signed In!</title>
	    
		<meta name="google-signin-scope" content="profile email">
    	<meta name="google-signin-client_id" 
    		content="174253236251-mh1mc3akeh7dgv53b3d52aa13kr77egh.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		
	    
	</head>
	
	<body>
		<% 
		
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String id_token = request.getParameter("id_token");
		String accountlevel = request.getParameter("accountlevel");
		
		
		%>
		
		<p>First Name: <%= firstname %></p>
		<p>Last Name: <%= lastname %></p>
		<p>Email: <%= email %></p>
		<p>id_token: <%= id_token %></p>
		<p>Account Level: <%= accountlevel %></p>
	
	
	
	<script>
      
    </script>
    
	</body>
</html>