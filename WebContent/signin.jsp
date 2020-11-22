<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="keywords" content="">
	    <meta name="description" content="">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, viewport-fit=cover">
	    
		
	    <title>Sign In</title>
	    
		<meta name="google-signin-scope" content="profile email">
    	<meta name="google-signin-client_id" 
    		content="174253236251-mh1mc3akeh7dgv53b3d52aa13kr77egh.apps.googleusercontent.com">
		<script src="https://apis.google.com/js/platform.js" async defer></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
		
	    
	</head>
	
	<body>
	
	<p>Sign in as a Student:</p>
	<div class="g-signin2" data-onsuccess="onSignInStudent" data-theme="dark">Student Sign In</div>
	
	<p>Sign in as an instructor:</p>
	<div class="g-signin2" data-onsuccess="onSignInInstructor" data-theme="dark">Instructor Sign In</div>
	
	
	<script>
      function onSignInStudent(googleUser) {
        // some data from profile
        var profile = googleUser.getBasicProfile();
        console.log("ID: " + profile.getId()); // Don't send this directly to your server!
        console.log('Full Name: ' + profile.getName());
        console.log('Given Name: ' + profile.getGivenName());
        console.log('Family Name: ' + profile.getFamilyName());
        console.log("Image URL: " + profile.getImageUrl());
        console.log("Email: " + profile.getEmail());

        // The ID token to pass to backend:
        var id_token = googleUser.getAuthResponse().id_token;
        console.log("ID Token: " + id_token);
        
        account_level = 1;
        
        var redirectUrl = 'AuthenticationServlet';
        var form = $('<form action="' + redirectUrl + '" method="post">' +
                '<input type="text" name="id_token" value="' +
                 id_token + '" />' +
                 '<input type="text" name="firstname" value="' +
                 profile.getGivenName() + '" />' +
                 '<input type="text" name="lastname" value="' +
                 profile.getFamilyName() + '" />' +
                 '<input type="text" name="email" value="' +
                 profile.getEmail() + '" />' +
                 '<input type="text" name="accountlevel" value="' +
                 account_level + '" />' +
                                                      '</form>');
		$('body').append(form);
		form.submit();
        
      }
      
      function onSignInInstructor(googleUser) {
          // some data from profile
          var profile = googleUser.getBasicProfile();
          console.log("ID: " + profile.getId()); // Don't send this directly to your server!
          console.log('Full Name: ' + profile.getName());
          console.log('Given Name: ' + profile.getGivenName());
          console.log('Family Name: ' + profile.getFamilyName());
          console.log("Image URL: " + profile.getImageUrl());
          console.log("Email: " + profile.getEmail());

          // The ID token to pass to backend:
          var id_token = googleUser.getAuthResponse().id_token;
          console.log("ID Token: " + id_token);
          
          account_level = 2;
          
          var redirectUrl = 'AuthenticationServlet';
          var form = $('<form action="' + redirectUrl + '" method="post">' +
                  '<input type="text" name="id_token" value="' +
                   id_token + '" />' +
                   '<input type="text" name="firstname" value="' +
                   profile.getGivenName() + '" />' +
                   '<input type="text" name="lastname" value="' +
                   profile.getFamilyName() + '" />' +
                   '<input type="text" name="email" value="' +
                   profile.getEmail() + '" />' +
                   '<input type="text" name="accountlevel" value="' +
                   account_level + '" />' +
                                                        '</form>');
  		$('body').append(form);
  		form.submit();
          
        }
    </script>
    
	</body>
</html>