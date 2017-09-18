<%@page import="com.rhl.auxilium.dao.BlokCzasuDAO"%>
<%@page import="com.rhl.auxilium.entities.BlokCzasu"%>
<%@page import="com.rhl.auxilium.dao.UzytkownikDAO"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="com.rhl.auxilium.dao.WizytaDAO"%>
<%@page import="com.rhl.auxilium.entities.Wizyta"%>
<%@page import="com.rhl.auxilium.dao.SpecjalizacjaDAO"%>
<%@page import="com.rhl.auxilium.entities.Specjalizacja"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	WizytaDAO wizytaDAO = new WizytaDAO();
	BlokCzasuDAO blokCzasuDAO = new BlokCzasuDAO();
	List<BlokCzasu> listaBlokowCzasu = blokCzasuDAO.getListaAktywnychBlokow();
	Iterator<BlokCzasu> iteratorBlokowCzasu = listaBlokowCzasu.iterator();
	
	SpecjalizacjaDAO specjalizacjaDAO = new SpecjalizacjaDAO();
	List<Specjalizacja> listaSpecjalizacji = specjalizacjaDAO.getListaSpecjalizacji() ;
	Iterator<Specjalizacja> iteratorSpecjalizacji = listaSpecjalizacji.iterator();
%>
<script type="text/javascript" src="scripts/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript">

		// spytaj o potwierdzenie opuszczenia formularza 
	
	function checkWroc()
		{	  	
			  var r = confirm("Czy na pewno opu\u015bci\u0107 ekran?");
			  if (r==true) {
				 document.getElementById("wrocForm").submit();		  
				}			
		};
	function checkWrocEtap1()
		{	  	
			  var r = confirm("Czy na pewno wr\u00f3ci\u0107 do do wyboru daty i gabinetu?");
			  if (r==true) {
				 document.getElementById("wrocDoEtap1Form").submit();		  
				}			
		};
</script>

<form id="wrocForm" action="WrocDoPanelu.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<input type='hidden' name='edytowanaData' value=<%=request.getAttribute("edytowanaData")%>>
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoPanelu" value="Wr&oacute;&cacute; do panelu" 
	        	   onclick="checkWroc()"> 
	    
	</label>
</form>
<form id="wrocDoEtap1Form" action="EdycjaWizytPacjentEtap1.do" method="post" class="auxilium" onsubmit="return checkForm(this);">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<input type='hidden' name='edytowanaData' value=<%=request.getAttribute("edytowanaData")%>>
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoEtapu1" value="Wr&oacute;&cacute; do wyboru daty" 
	        	   onclick="checkWrocEtap1()"> 
	</label>
	<br/><br/><br/><br/><br/>
	<label> 
	<span id="specjalizacjaSpan">Specjalizacja:</span>
	    <select name="specjalizacja" id="specjalizacja">
			<option value="0" selected> -- filtr specjalizacji -- </option>
		    <%
				while (iteratorSpecjalizacji.hasNext()) {
					Specjalizacja specjalizacja = (Specjalizacja)iteratorSpecjalizacji.next();
					out.print("<option value="+specjalizacja.getId()+">"+specjalizacja.getNazwaSpecjalizacji()+"</option>");
				}
			%>	
		</select>
		<input type="image" name="wyszukaj" src="icons/ic_action_search.png"
	        	   formaction="WyszukajWizytyPoSpecjalizacjiLekarza.do">
		<input type="image" name="pokazWszystkich" src="icons/ic_action_undo.png" title="Poka&zdot; wszystkich"
	        	   formaction="PrzejdzDoEtapu2Pacjent.do">
	</label> 
	<label>
		<span>Edytowany dzie&nacute;: </span> 
		<input type='text' value=<%=request.getAttribute("edytowanaData")%> disabled>
	</label>
</form>

<form method="post" class="auxilium">
	<table>
	<tr>
		<th><label class="listHeader">Wizyta</label></th>
		<th><label class="listHeader">Gabinet</label></th>
		<th><label class="listHeader">Lekarz</label></th>
		<th><label class="listHeader">Status</label></th>
		<th></th>
	</tr>   
	<%
	try {
		if (request.getAttribute("listaWizyt")!=null) {
			@SuppressWarnings("unchecked")
			List<Wizyta> aktualnaListWizyt= (List<Wizyta>)request.getAttribute("listaWizyt");
				
			if (aktualnaListWizyt.isEmpty()==false) {
									
					Iterator<Wizyta>  iteratorWizyt = null;
					boolean wizytaIstniejeDlaBloku = false;
							
					@SuppressWarnings("unchecked")
					List<Wizyta> listaWizyt = (List<Wizyta>)request.getAttribute("listaWizyt");
					iteratorWizyt = listaWizyt.iterator();
					Wizyta aktualnaWizyta = null;
								
					// ustal, czy dla danego bloku czasu istnieje wizyta:
					while (iteratorWizyt.hasNext()) {
						Wizyta wizyta = (Wizyta)iteratorWizyt.next();
						aktualnaWizyta = wizyta;
						boolean wolnaWizyta = true;	
						try {
							if (aktualnaWizyta.getPacjent()!=null) {
								wolnaWizyta = false;
							}
							
						} catch (NullPointerException e) {
						//Nie rób nic.
						}
					   
						//wyswietl blok czasu
						out.print("<tr>");
						out.print("<form method='post'>");
						out.print("<input type='text' hidden name='edytowanaData' value="+request.getAttribute("edytowanaData")+">");
						//bloki czasu sa numerowane od id=1 dla bloku 0:00-1:00. Stad, zeby wyswietlic godzine, odejmujemy 1 od id
						out.print("<td><span name='blokCzasu' value="+ aktualnaWizyta.getBlokCzasu().getId() + " >"+(aktualnaWizyta.getBlokCzasu().getId()-1)+":00</span>"
								+ "<input type='text' hidden name='blokCzasu' value="+aktualnaWizyta.getBlokCzasu().getId()+"></td>");
						
						// wyswietl gabinet
						out.print("<td><span name='gabinet' value="+ aktualnaWizyta.getGabinet().getId() + " >"+aktualnaWizyta.getGabinet().getNumer()+"</span>");
					
						// wyswietl zaplanowanego lekarza 
						out.print("<td><input type='text' hidden name='idWizyta' value="+aktualnaWizyta.getId()+">");
							
						out.print("<span>"+aktualnaWizyta.getLekarz().getImie() +" "+aktualnaWizyta.getLekarz().getNazwisko()+" - "
									+ specjalizacjaDAO.getNazwaSpecjalizacjiPoId(aktualnaWizyta.getLekarz().getSpecjalizacja()) +"&nbsp;&nbsp;&nbsp;</span></td>");
							
						if (wolnaWizyta){
							//wyswietl status
							out.print("<td>Wolna</td>");				
							// dodaj przycisk rejestracji 
							out.print("<td><input type='image' src='icons/ic_action_accept.png' alt='Rejestracja' formaction='ZarejestrujPacjentaNaWizyte.do'; value='"+aktualnaWizyta.getBlokCzasu().getId()+"'></td>");
							out.print("</form>");
							out.print("</tr>");	
						} else {
							//wyswietl status 
							out.print("<td>Zarejestrowano</td>");				
							// dodaj przycisk rejestracji 
							out.print("<td><input type='image' src='icons/ic_action_cancel.png' alt='Wyrejestrowanie' formaction='WyrejestrujPacjentaZWizyty.do'; value='"+aktualnaWizyta.getBlokCzasu().getId()+"'></td>");
							out.print("</form>");
							out.print("</tr>");	
						}
					}
					// zniszcz liste i iterator
					listaWizyt=null;
					iteratorWizyt=null; 
			}
			// nie ma zadnych zaplanowanych i wolnych albo zajetych przez aktualnego wizyt dla podanej daty
			} else {
				 out.print("<br/><br/>");
				 out.print("<h1 class='error'>Dla tego dnia (i wybranej specjalizacji) nie ma &zdot;adnej dost&eogon;pnej wizyty.</h1><br/><br/>");
				 out.print("<br/>");
				 out.print("<label>");
				 out.print("<span>Filtrowana specjalizacja: </span>"); 
				 out.print("<input type='text' disabled value="+ request.getAttribute("specjalizacja")+">");
				out.print("</label>");
			} 
	} catch (NullPointerException e) {
		//Nie rób nic.
		}
	%>	

	</table>
</form>