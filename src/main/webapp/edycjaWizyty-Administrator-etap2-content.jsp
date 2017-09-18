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
		<input class="prawyPrzycisk" type="button" name="powrotDoPaneluAdministratora" value="Wr&oacute;&cacute; do panelu" 
	        	   onclick="checkWroc()"> 
	</label>
</form>
<form id="wrocDoEtap1Form" action="EdycjaWizytAdministratorEtap1.do" method="post" class="auxilium" onsubmit="return checkForm(this);">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoEtapu1" value="Wr&oacute;&cacute; do wyboru daty i gabinetu" 
	        	   onclick="checkWrocEtap1()"> 
	</label>
</form>

<form method="post" class="auxilium">
<label>
	<span>Edytowany dzie&nacute;: </span> 
	<input type='text' value=<%=request.getAttribute("edytowanaData")%> disabled>
</label><br/>
<label>
	<span>Edytowany gabinet: </span>
	<input type='text' value='Gabinet <%=gabinetDAO.zwrocNumerPoId(Integer.parseInt((String)request.getAttribute("idGabinetu")))%>'  disabled>
</label>

	<table>
	<tr>
		<th><label class="listHeader">Wizyta</label></th>
		<th><label class="listHeader">Lekarz</label></th>
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
			// lista aktywnych lekarzy
			out.print("<td><select required name='lekarz' id='listaLekarzy'>");
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
				// wyswietl wybranego lekarza i opcje jego usuniecia
				out.print("<option value="+aktualnaWizyta.getLekarz().getId() + " selected >" 
						+ aktualnaWizyta.getLekarz().getImie() +" "+aktualnaWizyta.getLekarz().getNazwisko()+" - "
						+ specjalizacjaDAO.getNazwaSpecjalizacjiPoId(aktualnaWizyta.getLekarz().getSpecjalizacja()) +"&nbsp;&nbsp;&nbsp;</option>");
						out.print("<option value='0'> -- usu&nacute; lekarza -- </option>");
				//dodaj do combo pozostalych lekarzy
				List<Uzytkownik> listaAktywnychLekarzy = null; 
				Iterator<Uzytkownik> iteratorLekarzy = null;
				listaAktywnychLekarzy = uzytkownikDAO.getListaAktywnychLekarzy();
				iteratorLekarzy = listaAktywnychLekarzy.iterator();
				while (iteratorLekarzy.hasNext()) {
					Uzytkownik uzytkownik = (Uzytkownik)iteratorLekarzy.next();
					if (uzytkownik.getId()!=aktualnaWizyta.getLekarz().getId()){
						out.print("<option value="+uzytkownik.getId()+">"+uzytkownik.getImie()+" "+uzytkownik.getNazwisko()+" - "+specjalizacjaDAO.getNazwaSpecjalizacjiPoId(uzytkownik.getSpecjalizacja())+"&nbsp;&nbsp;&nbsp;</option>");
					}
				}
			// wyswietl liste wszystkich aktywnych lekarzy
			} else {
				List<Uzytkownik> listaAktywnychLekarzy = uzytkownikDAO.getListaAktywnychLekarzy();
				Iterator<Uzytkownik> iteratorLekarzy = listaAktywnychLekarzy.iterator();
				out.print("<option selected></option>");
				while (iteratorLekarzy.hasNext()) {
					Uzytkownik uzytkownik = (Uzytkownik)iteratorLekarzy.next();
					out.print("<option value="+uzytkownik.getId()+">"+uzytkownik.getImie()+" "+uzytkownik.getNazwisko()+" - "
					+specjalizacjaDAO.getNazwaSpecjalizacjiPoId(uzytkownik.getSpecjalizacja())+"&nbsp;&nbsp;&nbsp;</option>");
				}
			}
			// domknij selecta
			out.print("</select></td>");
			// zniszcz liste i iterator
		 	listaWizyt=null;
			iteratorWizyt=null; 
			// dodaj przycisk akceptacji zmiany
			out.print("<td><input type='image' src='icons/ic_action_accept.png' alt='Edytuj' formaction='EdytujWizyte.do'; value='"+blokCzasu.getId()+"'></td>");
			out.print("</form>");
			out.print("</tr>");		
		}
	// nie ma zadnych zaplanowanych wizyt dla podanej daty i gabinetu	
	 } else {
		
		while (iteratorBlokowCzasu.hasNext()) {
			BlokCzasu blokCzasu = (BlokCzasu)iteratorBlokowCzasu.next();
			out.print("<tr>");
			out.print("<form method='post'>");
			out.print("<input type='text' hidden name='idGabinetu' value="+request.getAttribute("idGabinetu")+">");
			out.print("<input type='text' hidden name='edytowanaData' value="+request.getAttribute("edytowanaData")+">");
			//bloki czasu sa numerowane od id=1 dla bloku 0:00-1:00. Stad, zeby wyswietlic godzine, odejmujemy 1 od id
			out.print("<td><span name='blokCzasu' value="+ blokCzasu.getId() + " >"+(blokCzasu.getId()-1)+":00</span>" 
					+ "<input type='text' hidden name='blokCzasu' value="+blokCzasu.getId()+"></td>");
			// lista aktywnych lekarzy
			out.print("<td><select required name='lekarz' id='listaLekarzy'>");
			List<Uzytkownik> listaAktywnychLekarzy = uzytkownikDAO.getListaAktywnychLekarzy();
			Iterator<Uzytkownik> iteratorLekarzy = listaAktywnychLekarzy.iterator();
			out.print("<option selected></option>");
			while (iteratorLekarzy.hasNext()) {
				Uzytkownik uzytkownik = (Uzytkownik)iteratorLekarzy.next();
				out.print("<option value="+uzytkownik.getId()+">"+uzytkownik.getImie()+" "+uzytkownik.getNazwisko()+" - "
				+specjalizacjaDAO.getNazwaSpecjalizacjiPoId(uzytkownik.getSpecjalizacja())+"&nbsp;&nbsp;&nbsp;</option>");
			} 
			listaAktywnychLekarzy = null; 
			iteratorLekarzy = null;
			out.print("</select></td>");
			out.print("<td><input type='image' src='icons/ic_action_accept.png' alt='Dodaj' formaction='EdytujWizyte.do'; value='"+blokCzasu.getId()+"'></td>");
			out.print("</form>");
			out.print("</tr>"); 
		}  
	 } 
	%>	

	</table>
</form>