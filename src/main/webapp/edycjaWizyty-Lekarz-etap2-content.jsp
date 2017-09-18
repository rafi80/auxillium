<%@page import="com.rhl.auxilium.entities.Gabinet"%>
<%@page import="com.rhl.auxilium.entities.BlokCzasu"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="com.rhl.auxilium.dao.WizytaDAO"%>
<%@page import="com.rhl.auxilium.entities.Wizyta"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	WizytaDAO wizytaDAO = new WizytaDAO();
%>
<script type="text/javascript" src="scripts/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jquery.dataTables.min.js"></script>
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
<form id="wrocDoEtap1Form" action="EdycjaWizytLekarzEtap1.do" method="post" class="auxilium" onsubmit="return checkForm(this);">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoEtapu1" value="Wr&oacute;&cacute; do wyboru daty" 
	        	   onclick="checkWrocEtap1()"> 
	</label>
</form>




<form method="post" class="auxilium">
<br/>
<label>
	<span>Wybrany dzie&nacute;: </span>
	<input type='text' value=<%=request.getAttribute("edytowanaData")%> name="edytowanaData" disabled>
</label>
<br/><br/>
	<table>
	<tr>
		<th><label class="listHeader">Wizyta</label></th>
		<th><label class="listHeader">Pacjent</label></th>
		<th><label class="listHeader">Gabinet</label></th>
		<th><label class="listHeader">Czy pacjent przyby&lstrok;?</label></th>
		<th></th>
	</tr>   
	<%
	//request.setAttribute("listaWizyt",request.getAttribute("listaWizyt"));
		
	Uzytkownik zalogowanyLekarz = (Uzytkownik) session.getAttribute("uzytkownik");
	
	@SuppressWarnings("unchecked")
	List<Wizyta> aktualnaListWizyt= (List<Wizyta>)session.getAttribute("listaWizyt");
	if (aktualnaListWizyt.isEmpty()==false) {

			@SuppressWarnings("unchecked")
			List<Wizyta> listaWizyt = (List<Wizyta>)session.getAttribute("listaWizyt");
			Iterator<Wizyta> iteratorWizyt = listaWizyt.iterator();
			
			while (iteratorWizyt.hasNext()) {
				Wizyta wizyta = (Wizyta)iteratorWizyt.next();
				
				out.print("<tr>");
				out.print("<form method='post'>");
					//id wizyta
					out.print("<td><input type='text' hidden name='idWizyta' value="+wizyta.getId()+">");
					// blok czasu
					out.print("<span name='blokCzasu' value="+ wizyta.getBlokCzasu().getId() + " >"+(wizyta.getBlokCzasu().getId()-1)+":00</span></td>");
					//pacjent 
					if (wizyta.getPacjent()!=null){
						out.print("<td><span name='pacjent' value="+ wizyta.getPacjent().getId() + " >"+ wizyta.getPacjent().getImie() +" "+wizyta.getPacjent().getNazwisko()+" - ("
								+ wizyta.getPacjent().getPesel() +")</span></td>");
					} else {
						out.print("<td><span name='pacjent' value='0'>-- nie zarejestrowano pacjenta --</span></td>");
					}
					//gabinet			
					out.print("<td><span name='pacjent' value="+ wizyta.getGabinet().getId() + " > Gabinet"+ wizyta.getGabinet().getNumer() +"</span></td>");
									
					if (wizyta.getPacjent()!=null){
						//czy Pacjent przybyl?
						String stanCheckBoxaCzyPacjentPrzybyl = null;
						if (wizyta.getCzyPacjentPrzybyl()==1) {
							stanCheckBoxaCzyPacjentPrzybyl="checked";
						}
						out.print("<td><input disabled id='czyPacjentPrzybyl' type='checkbox' name='czyPacjentPrzybyl' value='1'" 
							+stanCheckBoxaCzyPacjentPrzybyl+"></td>");
						
						
						// edytuj wizyte
						out.print("<td><input type='image' src='icons/ic_action_edit.png' alt='Edytuj' formaction='EdytujWizyteLekarz.do'; value='"+wizyta.getId()+"'></td>");
					} else {
						out.print("<td></td>");
					}
				out.print("</form>");
				out.print("</tr>");		
		}
	// nie ma zadnych zaplanowanych wizyt dla podanej daty i gabinetu	
	 } else {
		 out.print("<br/><br/>");
		 out.print("<h1 class='error'>Dla tego dnia nie ma &zdot;adnej zaplanowanej wizyty.</h1><br/><br/>");
		 out.print("<br/><br/>");
	 } 
	%>	

	</table>
</form>