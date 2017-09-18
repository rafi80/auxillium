<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%	String zrodlo = new String("");
	session.setAttribute( "zrodlo", zrodlo );
	zrodlo = "panelPacjenta";
%>

<form action="WyborZPaneluPacjenta.do" method="post" class="auxilium">
	
	<h1>Witamy!
		<span>Zapraszamy do zdalnej rezerwacji wizyt.</span>
	</h1>
	<br/><br/>
	<div>
		<input type="hidden" name="zrodlo"  value=<%out.print(zrodlo);%>>
	  	<input class="bigButton" type="submit" name="rezerwacjaWizyt" value="Rezerwacja wizyt" 
	  			formaction="EdycjaWizytPacjentEtap1.do" /> 
     </div>
   	<br />
   	<p>W przypadku pyta&nacute; albo uwag zapraszamy do kontaktu:</p>
	<p>Pod numerem telefonu: +48 71 12312332</p>
	<p>Pod adresem mailowym: kontakt@auxilium.com</p>
	<p>Osobi&sacute;cie lub korespondencyjnie: ul. Szewska 21, 50-500 Wroc&lstrok;aw</p>
</form>