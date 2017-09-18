<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

<script type="text/javascript" src="scripts/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>

<script type="text/javascript">
//sprawdza czy zgodne hasla
/* 	$(document).ready( function() {
		$( "form" ).submit(function( event ) {
		 	$("#submit").click(function(){ 
	        $(".errorForm").hide();
	        var hasError = false;
	        var loginVal = $("#login").val();
	        var passwordVal = $("#haslo").val();
			if (loginVal.length == 0) {
				
	            $("#login").after('<br/><span style="color: red;" class="errorForm">Login nie mo&zdot;e by&cacute; pusty.</span>');
	            hasError = true;
	        }

	        if (passwordVal.length == 0) {
	            $("#haslo").after('<br/><span style="color: red;" class="errorForm">Has&lstrok;o nie mo&zdot;e by&cacute; puste.</span>');
	            hasError = true;
	        } 

	        if(hasError == true) {return false;}
	    });
	}); */
</script>

<%
	if(session.getAttribute("uzytkownik") !=null) {
		 ((HttpServletResponse) response).sendRedirect("goHome.jsp");
		}
%>


<form id="daneLogowania" action="Logowanie.do" method="post" class="auxilium">
	
	<h1>Witamy!
		<span>Proszę podać login i hasło.</span>
	</h1>
	<br/><br/>
	<%

		if (request.getAttribute("errors")!=null) {
	    	@SuppressWarnings("unchecked")
			List<String> errors = (List<String>)request.getAttribute("errors");
			Iterator<String> i = errors.iterator();
			
			while (i.hasNext()){
				String error = i.next();
				out.print("<h2 class='error'>"+error+"</h1>");
			}
		}
	%>
	
	<label>
	        <span>Login:</span>
	        <input id="login" type="text" name="login">
	</label>
	<br/><br/>    
	<label>
	        <span>Hasło:</span>
	        <input id="haslo" type="password" name="haslo">
	</label>
	<br /><br />
	<label>
		<span>&nbsp;</span>
    	<input type="submit" class="button" value="Zaloguj"> 
	</label>
	<br /><br /><br /><br /><br />
</form>