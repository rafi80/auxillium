<%@page import="com.rhl.auxilium.dao.UzytkownikDAO"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="com.rhl.auxilium.dao.OpisWizytyDAO"%>
<%@page import="com.rhl.auxilium.entities.OpisWizyty"%>
<%@page import="com.rhl.auxilium.dao.WizytaDAO"%>
<%@page import="com.rhl.auxilium.entities.Wizyta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%	
	//scriptlet do ustawienia danych na formularzu
	Integer idDoEdycji = (Integer) request.getAttribute("idPacjenta");
	System.out.println("oto id Pacjenta:" + idDoEdycji);
	request.setAttribute("idPacjenta", idDoEdycji);
	request.setAttribute("listaWizyt",request.getParameter("listaWizyt")); 
	request.setAttribute("idWizyta",request.getParameter("idWizyta"));
	
	//zalogowany uzytkownik
	Uzytkownik uzytkownikWSesji = (Uzytkownik) session.getAttribute("uzytkownik");
	
	// jezeli formularz w wersji do zmiany danych,
	// pobieramy dane edytowanego uzytkownika po id i ustawiamy na formularzu
	Uzytkownik uzytkownik = new Uzytkownik();
	UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
	
	if (idDoEdycji!=null) {
		
		uzytkownik = uzytkownikDAO.zaladujDoEdycji(idDoEdycji);		
	} 
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

	

	function checkWrocDoKartoteki()
	{	  	
		  var r = confirm("Czy na pewno przej\u0105\u0107 do kartoteki pacjenta?");
		  if (r==true) {
			 document.getElementById("wrocDoKartotekiForm").submit();		  
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

<form id="wrocForm" action="PrzejdzDoEtapu2Lekarz.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
    <input type="hidden" name="wybranaData" value = <%=request.getAttribute("dataWizyty")%>>

	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoListyWizyt" value="Wr&oacute;&cacute; do listy wizyt" 
	        	   onclick="checkWroc()"> 
	</label><br/><br/>
	<%
		if (session.getAttribute("wywolanoZEkranu")!=null && session.getAttribute("wywolanoZEkranu")=="edycjaKartoteki") {
			out.print("<br/>");
			out.print("<label>"); 
			out.print("<input class='prawyPrzycisk' type='button' name='powrotDoKartotekiPacjenta' value='Wy&sacute;wietl Kartotek&eogon; pacjenta'" 
		        	   + "onclick='checkWrocDoKartoteki()'>");
			out.print("</label>");
			out.print("<br/>");
		}
	
	%>
</form>
<form id="wrocDoKartotekiForm" action="WyswietlKartotekePacjenta.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<input type="hidden" name="idPacjent" value=<%=request.getAttribute("idPacjenta")%>>
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
	<label>
	        <span>Data:</span>
	        <input id="dataWizyty" type="text" disabled value=<%=request.getAttribute("dataWizyty")%>>
	</label>
	<br/> 
<form onsubmit="return validate(this);" id="edycjaForm" method="post" class="auxilium">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<input type="hidden" name="dataWizyty" value = <%=request.getAttribute("dataWizyty")%>>
	<input type="hidden" name="idWizyta" value = <%=request.getAttribute("idWizyta")%>>
	<input type="hidden" name="idPacjenta" value = <%=idDoEdycji%>>
	
	<label>
		<textarea name="opis" placeholder="Podaj prosz&eogon; opis wizyty..." wrap="hard" maxlength="65000" rows="800" cols="80"><%
		OpisWizyty opisWizyty = new OpisWizyty();
		OpisWizytyDAO opisWizytyDAO = new OpisWizytyDAO(); 
		
		Wizyta wizyta = new Wizyta();
		WizytaDAO wizytaDAO = new WizytaDAO();
		
		wizyta = (Wizyta) request.getAttribute("wizyta");
		String opisWizytyZBazy = "";
		try {
			opisWizytyZBazy = opisWizytyDAO.getOpisWizytyPoWizycie(wizyta).getOpis();
			if (opisWizytyZBazy == null) {
				opisWizytyZBazy ="";
			}
			//out.print(opisWizytyZBazy);
		} catch (NullPointerException e) {
				opisWizytyZBazy ="";
		}
		out.print(opisWizytyZBazy);
		%></textarea>
	</label>
    <br /><br />

    <label> 
		<input id="zapiszZmiany" class="prawyPrzycisk" type="submit" value="Zapisz">
    </label>
	<br /><br />	
</form>