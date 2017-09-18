<%@page import="com.rhl.auxilium.dao.BlokCzasuDAO"%>
<%@page import="com.rhl.auxilium.entities.BlokCzasu"%>
<%@page import="com.rhl.auxilium.dao.UzytkownikDAO"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="com.rhl.auxilium.dao.WizytaDAO"%>
<%@page import="com.rhl.auxilium.entities.Wizyta"%>
<%@page import="com.rhl.auxilium.dao.SpecjalizacjaDAO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	//zalogowany uzytkownik
	Uzytkownik uzytkownikWSesji = (Uzytkownik) session.getAttribute("uzytkownik");

	//pobieranie aktualnego pacjenta
	//Integer idPacjenta = (Integer) request.getAttribute("idPacjenta");
	Integer idPacjenta = Integer.parseInt((String)request.getAttribute("idPacjenta"));
	Uzytkownik uzytkownik = new Uzytkownik();
	UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
	
	if (idPacjenta!=null) {
		uzytkownik = uzytkownikDAO.zaladujDoEdycji(idPacjenta);
	}
	request.setAttribute("idPacjenta", idPacjenta);
	
	//pobranie znalezionych wizyt pacjenta
	@SuppressWarnings("unchecked")
	List<Wizyta> listaWizytPacjenta = (List<Wizyta>) request.getAttribute("listaZnalezionychWizyt");
	
	SpecjalizacjaDAO specjalizacjaDAO = new SpecjalizacjaDAO();

	// przekazanie zrodla wywolania do sesji
	session.setAttribute("wywolanoZEkranu", "edycjaKartoteki");
%>

<script type="text/javascript" src="scripts/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript">

	// spytaj o potwierdzenie opuszczenia formularza 
	function checkWroc()
	{	  	
		  var r = confirm("Czy opu\u015bci\u0107 ekran?");
		  if (r==true) {
			 document.getElementById("wrocForm").submit();		  
			}			
	};

	// spytaj o potwierdzenie zapisania zmian
	function validate(form)
	{	  	
		 var r = confirm("Czy zapisa\u0107 zmiany?");
		 if (r==true) {
			 document.getElementById("edycjaForm").action = "EdytujOpisWizytyLekarz.do";
			 document.getElementById("edycjaForm").submit();		
			 } else {
			  return false;
				 }		
	};
</script>

<form id="wrocForm" action="EdycjaKartotekiPacjentaEtap1.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>

	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoListyWizyt" value="Wr&oacute;&cacute; do listy pacjentów w Kartotece" 
	        	   onclick="checkWroc()"> 
	</label>
</form>
	<label>
	        <span>Imi&eogon;:&nbsp;</span>
	        <input id="imie" type="text" disabled value="<%out.print(uzytkownik.getImie());%>&nbsp;&nbsp;&nbsp;" >
	</label>
    <label>
	        <span>Nazwisko:</span>
	        <input id="nazwisko" type="text" disabled value="<%out.print(uzytkownik.getNazwisko());%>&nbsp;&nbsp;&nbsp;">
	</label>
	<label>
	        <span>Pesel:</span>
	        <input id="pesel" type="text" disabled value="<%out.print(uzytkownik.getPesel());%>" required pattern='.{11}'>
	</label>
	<br/>    
<form id ="edycjaForm" method="post" class="auxilium">
		
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<br />
	<br />
	 
	<table>
	<tr>
		<th><label class="listHeader">Data wizyty</label></th>
		<th><label class="listHeader">Godzina wizyty</label></th>
		<th><label class="listHeader">Lekarz</label></th>
		<th></th>
	<th></th>
	</tr>   
	<%
		System.out.println(listaWizytPacjenta);
		if (listaWizytPacjenta.isEmpty()==false) {
			Iterator<Wizyta>iteratorWizyt = listaWizytPacjenta.iterator();
			Wizyta aktualnaWizyta = null;
			// ustal, czy dla danego bloku czasu istnieje wizyta:
			while (iteratorWizyt.hasNext()) {
				aktualnaWizyta = (Wizyta)iteratorWizyt.next();
				System.out.println("Aktualna wizyta pacjenta: " + aktualnaWizyta);
				out.print("<tr>");
				out.print("<form method='post'>");
				out.print("<input type='hidden' name='idWizyta' value="+aktualnaWizyta.getId()+">");
				out.print("<input type='hidden' name='dataWizyty' value="+aktualnaWizyta.getDataWizyty()+">");
				out.print("<input type='hidden' name='idPacjenta' value="+idPacjenta+">");
				String pobranaDataWizyty =aktualnaWizyta.getDataWizyty().toString();
				String sformatowanaData= pobranaDataWizyty.substring(0,10);
				out.print("<td>"+sformatowanaData+"</td>");
				out.print("<td><span name='blokCzasu' value="+ aktualnaWizyta.getBlokCzasu().getId() + " >"+(aktualnaWizyta.getBlokCzasu().getId()-1)+":00</span>"
						+ "<input type='text' hidden name='blokCzasu' value="+aktualnaWizyta.getBlokCzasu().getId()+"></td>");
				out.print("<td>"+aktualnaWizyta.getLekarz().getImie() +" "+aktualnaWizyta.getLekarz().getNazwisko()+" - "
						+ specjalizacjaDAO.getNazwaSpecjalizacjiPoId(aktualnaWizyta.getLekarz().getSpecjalizacja()) +"&nbsp;&nbsp;&nbsp;</td>");
				out.print("<td><input type='image' src='icons/ic_action_edit.png' alt='Edytuj' formaction='WyswietlOpisWizytyLekarz.do'; value='"+aktualnaWizyta.getId()+"'></td>");
				out.print("</form>");
				out.print("</tr>");
			}
		} else {
			out.print("<br/><br/>");
			out.print("<h1 class='error'>Dla tego pacjenta nie ma jeszcze w kartotece &zdot;adnej wizyty.</h1><br/><br/>");
		}
	
	%>	
	</table>
</form>