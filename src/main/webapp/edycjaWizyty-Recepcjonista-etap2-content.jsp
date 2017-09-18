<%@page import="com.rhl.auxilium.dao.GabinetDAO"%>
<%@page import="com.rhl.auxilium.entities.Gabinet"%>
<%@page import="com.rhl.auxilium.dao.BlokCzasuDAO"%>
<%@page import="com.rhl.auxilium.entities.BlokCzasu"%>
<%@page import="com.rhl.auxilium.dao.UzytkownikDAO"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="com.rhl.auxilium.dao.WizytaDAO"%>
<%@page import="com.rhl.auxilium.entities.Wizyta"%>
<%@page import="com.rhl.auxilium.dao.SpecjalizacjaDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	GabinetDAO gabinetDAO = new GabinetDAO();
	List<Gabinet> listaGabinetow = gabinetDAO.getListaGabinetow();
	Iterator<Gabinet> iteratorGabinetow = listaGabinetow.iterator();
	BlokCzasuDAO blokCzasuDAO = new BlokCzasuDAO();
	List<BlokCzasu> listaBlokowCzasu = blokCzasuDAO.getListaAktywnychBlokow();
	Iterator<BlokCzasu> iteratorBlokowCzasu = listaBlokowCzasu.iterator();
	
	UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
	WizytaDAO wizytaDAO = new WizytaDAO();
	SpecjalizacjaDAO specjalizacjaDAO = new SpecjalizacjaDAO();
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
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoPanelu" value="Wr&oacute;&cacute; do panelu" 
	        	   onclick="checkWroc()"> 
	</label>
</form>
<form id="wrocDoEtap1Form" action="EdycjaWizytRecepcjonistaEtap1.do" method="post" class="auxilium" onsubmit="return checkForm(this);">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<input type='hidden' name='edytowanaData' value=<%=request.getAttribute("edytowanaData")%>>
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoEtapu1" value="Wr&oacute;&cacute; do wyboru daty i gabinetu" 
	        	   onclick="checkWrocEtap1()"> 
	</label>
</form>

<form method="post" class="auxilium">
	<label>
		<span>Edytowany dzie&nacute;: </span> 
		<input type='text' value=<%=request.getAttribute("edytowanaData")%> disabled>
	</label><br/><br/>
	<label>
		<span>Edytowany gabinet: </span> 
		<input type='text' value='Gabinet <%=gabinetDAO.zwrocNumerPoId(Integer.parseInt((String)request.getAttribute("idGabinetu")))%>'  disabled>
	</label>

	<table>
	<tr>
		<th><label class="listHeader">Wizyta</label></th>
		<th><label class="listHeader">Lekarz</label></th>
		<th><label class="listHeader">Pacjent</label></th>
		<th><label class="listHeader">Czy pacjent przyby&lstrok;?</label></th>
		<th></th>
	</tr>   
	<%
	@SuppressWarnings("unchecked")
	List<Wizyta> aktualnaListWizyt= (List<Wizyta>)request.getAttribute("listaWizyt");
	if (aktualnaListWizyt.isEmpty()==false) {
		while (iteratorBlokowCzasu.hasNext()) {
			BlokCzasu blokCzasu = (BlokCzasu)iteratorBlokowCzasu.next();
			out.print("<tr>");
			out.print("<form method='post'>");
			out.print("<input type='text' hidden name='idGabinetu' value="+request.getAttribute("idGabinetu")+">");
			out.print("<input type='text' hidden name='edytowanaData' value="+request.getAttribute("edytowanaData")+">");
			//bloki czasu sa numerowane od id=1 dla bloku 0:00-1:00. Stad, zeby wyswietlic godzine, odejmujemy 1 od id
			out.print("<td><span name='blokCzasu' value="+ blokCzasu.getId() + " >"+(blokCzasu.getId()-1)+":00</span>"
					+ "<input type='text' hidden name='blokCzasu' value="+blokCzasu.getId()+"></td>");
			
			Iterator<Wizyta>  iteratorWizyt = null;
			boolean wizytaIstniejeDlaBloku = false;
			
			@SuppressWarnings("unchecked")
			List<Wizyta> listaWizyt = (List<Wizyta>)request.getAttribute("listaWizyt");
			iteratorWizyt = listaWizyt.iterator();
			Wizyta aktualnaWizyta = null;
			
			// ustal, czy dla danego bloku czasu istnieje wizyta:
			while (iteratorWizyt.hasNext()) {
				Wizyta wizyta = (Wizyta)iteratorWizyt.next();
				//System.out.println("Bloku czasu wizyty z bazy: "+ wizyta.getBlokCzasu().getId());
				if (wizyta.getBlokCzasu().getId() == blokCzasu.getId()) {					
					wizytaIstniejeDlaBloku = true;
					aktualnaWizyta = wizyta;					
				} 
			}
		
		
			// jezeli wizyta istnieje wyswietl zapisanego lekarza
			if (wizytaIstniejeDlaBloku) {
				// wyswietl zaplanowanego lekarza 
				out.print("<td><input type='text' hidden name='idWizyta' value="+aktualnaWizyta.getId()+">");
				out.print("<span>"+aktualnaWizyta.getLekarz().getImie() +" "+aktualnaWizyta.getLekarz().getNazwisko()+" - "
						+ specjalizacjaDAO.getNazwaSpecjalizacjiPoId(aktualnaWizyta.getLekarz().getSpecjalizacja()) +"&nbsp;&nbsp;&nbsp;</span></td>");
				// lista aktywnych pacjentow
				Integer idZarejestrowanego = -1;
				if(aktualnaWizyta.getPacjent()!=null){
					idZarejestrowanego = aktualnaWizyta.getPacjent().getId();
				}
				
				//jezeli na wizyte zarejestrowany jest juz pacjent
				if(idZarejestrowanego!=-1){
					out.print("<td><select required name='pacjentId' id='listaPacjentow'>");
					// wyswietl dane zarejestrowanego pacjenta
					out.print("<option value="+aktualnaWizyta.getPacjent().getId() + " selected >" 
								+ aktualnaWizyta.getPacjent().getImie() +" "+aktualnaWizyta.getPacjent().getNazwisko()+" - ("
								+ aktualnaWizyta.getPacjent().getPesel() +")&nbsp;&nbsp;&nbsp;</option>");
					out.print("<option value='0'> -- usu&nacute; pacjenta -- </option>");
					
					//dodaj do combo pozostalych pacjentow
					List<Uzytkownik> listaAktywnychPacjentow = null; 
					Iterator<Uzytkownik> iteratorPacjentow = null;
					listaAktywnychPacjentow = uzytkownikDAO.getListaAktywnychPacjentow();
					iteratorPacjentow = listaAktywnychPacjentow.iterator();
					while (iteratorPacjentow.hasNext()) {
						Uzytkownik uzytkownik = (Uzytkownik)iteratorPacjentow.next();
						if (uzytkownik.getId()!=aktualnaWizyta.getPacjent().getId()){
							out.print("<option value="+uzytkownik.getId() + ">" 
									+ uzytkownik.getImie() +" "+uzytkownik.getNazwisko()+" - ("
									+ uzytkownik.getPesel() +")&nbsp;&nbsp;&nbsp;</option>");						
						}
					}
					// domknij selecta
					out.print("</select></td>");
					String stanCheckBoxaCzyPacjentPrzybyl = null;
					if (aktualnaWizyta.getCzyPacjentPrzybyl()==1) {
						stanCheckBoxaCzyPacjentPrzybyl="checked";
					}
					out.print("<td><input id='czyPacjentPrzybyl' type='checkbox' name='czyPacjentPrzybyl' value='1'" 
						+stanCheckBoxaCzyPacjentPrzybyl+"></td>");
				// jezeli nie ma zarejestrowanego pacjenta
				} else { 
					//wyswietl wszystkich aktywnych pacjentow
					List<Uzytkownik> listaAktywnychPacjentow = uzytkownikDAO.getListaAktywnychPacjentow();
					Iterator<Uzytkownik> iteratorPacjentow = listaAktywnychPacjentow.iterator();
					out.print("<td><select required name='pacjentId' id='listaPacjentow'>");
					out.print("<option selected></option>");
					while (iteratorPacjentow.hasNext()) {
						Uzytkownik uzytkownik = (Uzytkownik)iteratorPacjentow.next();
						out.print("<option value="+uzytkownik.getId() + ">" 
									+ uzytkownik.getImie() +" "+uzytkownik.getNazwisko()+" - ("
									+ uzytkownik.getPesel() +")&nbsp;&nbsp;&nbsp;</option>");		
					}
					// domknij selecta
					out.print("</select></td>");
					// dla wizyt bez zarejestrowanego pacjenta nie pokazuj checkboxa "czt Pacjent przybyl"
					out.print("<td></td>");
					
			 	} 	
					// zniszcz liste i iterator
				listaWizyt=null;
				iteratorWizyt=null; 
				// dodaj przycisk akceptacji zmiany
				out.print("<td><input type='image' src='icons/ic_action_accept.png' alt='Edytuj' formaction='EdytujWizyteRecepcjonista.do'; value='"+blokCzasu.getId()+"'></td>");
				out.print("</form>");
				out.print("</tr>");					
				
			// jezeli dla bloku nie ma wizyty
			} else {
				// wyswietl nieaktywne pole lekarza		
				out.print("<td>-- nie zaplanowano --</td>");
				// wyswietl nieaktywne pole pacjenta
				out.print("<td>-- nie zaplanowano --</td>");
				// nie pokazuj checkboxa "czt Pacjent przybyl"
				out.print("<td></td>");
				// nie udostepniaj przycisku edycji
				out.print("<td></td>");	
			}			
		} 
		
	// nie ma zadnych zaplanowanych wizyt dla podanej daty i gabinetu	
	} else {
		 out.print("<br/><br/>");
		 out.print("<h1 class='error'>Dla tego dnia nie ma &zdot;adnej dost&eogon;pnej wizyty.</h1><br/><br/>");
	} 
	%>	

	</table>
</form>