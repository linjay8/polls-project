<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create a Poll</title>
</head>
   <body>
    	<%String email = (String)request.getSession().getAttribute("email"); %>
   
      	<form name="myForm" action = "PollCreation" method = "GET" onsubmit="return validate()">
      	
      	
      	Class Code <input type="text" name="classCode"/>
        <br /><br />
      	
      	Poll Visibility
      	<br /><br />
      	Private <input type="checkbox" name="private" value="Private"/>
      	Public <input type="checkbox" name="public" value="Public"/>
      	<br /><br />
      	
      	
        Question: <input type="text" name="question"/>
      	<br /><br />
      	
   		Design your answers:
   		<br /><br />
   		
        Answer A <input type="text" name="answer1"/>
        <br /><br />
        Answer B<input type="text" name="answer2"/>
        <br /><br />
        Answer C  <input type="text" name="answer3"/>
        <br /><br />
        Answer D<input type="text" name="answer4"/>
        <br /><br />
        
        <input type = "submit" value = "Submit" />  
        
      </form>

      
   </body>
</html>