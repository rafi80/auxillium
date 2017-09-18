<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%	String zrodlo = new String("");
	zrodlo = "panelAdministratora";
	session.setAttribute( "zrodlo", zrodlo );
%>

<form method="post" class="auxilium">
	
	<br />
	<br />
	<div>
		<input type="hidden" name="zrodlo" value=<%out.print(zrodlo);%>>
	  	<input class="bigButton" type="submit" name="edycjaGabinetow" value="Edytowanie danych gabinet&oacute;w" 
	  		   formaction="WyswietlGabinety.do">
    	<input class="bigButton" type="submit" name="edycjaUzytkownikow" value="Edytowanie danych u&zdot;ytkownik&oacute;w"
    		   formaction="WyswietlUzytkownikow.do">	 
    	<br />
     </div>
    <br />
     <div>
       	<input class="bigButton" type="submit" name="edycjaWizyt" value="Edytowanie wizyt" 
       			formaction="EdycjaWizytAdministratorEtap1.do"> 
    </div>
	<br /><br /><br /><br /><br />
	
	
	
</form>