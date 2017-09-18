<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%	String zrodlo = new String("");
	zrodlo = "panelLekarza";
	session.setAttribute( "zrodlo", zrodlo );
%>

<form action="WyborZPaneluLekarza" method="post" class="auxilium">
	
	<br />
	<br />
	<div>
		<input type="hidden" name="zrodlo" value=<%out.print(zrodlo);%>>
	  	<input class="bigButton" type="submit" name="przegladanieWizyt" value="Edytowanie wizyt" 
	  		   formaction="EdycjaWizytLekarzEtap1.do"/> 
    	<input class="bigButton" type="submit" name="kartotekaPacjenta" value="Kartoteka Pacjenta" 
    		    formaction="EdycjaKartotekiPacjentaEtap1.do"/> 
    	<br />
     </div>
   	<br /><br /><br /><br /><br />
	
	
	
</form>