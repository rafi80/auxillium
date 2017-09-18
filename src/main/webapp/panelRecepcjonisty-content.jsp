<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%	String zrodlo = new String("");
	session.setAttribute( "zrodlo", zrodlo );
	zrodlo = "panelRecepcjonisty";
%>

<form action="WyborZPaneluRecepcjonisty.do" method="post" class="auxilium">
	
	<br />
	<br />
	<div>
		<input type="hidden" name="zrodlo"  value=<%out.print(zrodlo);%>>
	  	<input class="bigButton" type="submit" name="edycjaWizyt" value="Edytowanie wizyt" 
	  			formaction=EdycjaWizytRecepcjonistaEtap1.do> 
	  		
    	<input class="bigButton" type="submit" name="edycjaUzytkownikow" value="Edytowanie danych u&zdot;ytkownik&oacute;w" 
				formaction="WyswietlUzytkownikow.do"> 
    	<br />
     </div>
   	<br /><br /><br /><br /><br />
	
	
	
</form>