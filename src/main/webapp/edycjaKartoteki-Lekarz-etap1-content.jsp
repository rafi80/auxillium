<%@page import="com.rhl.auxilium.dao.UzytkownikDAO"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	UzytkownikDAO uzytkownikDAO = new UzytkownikDAO();
	Uzytkownik uzytkownikWSesji = (Uzytkownik) session.getAttribute("uzytkownik");
	List<Uzytkownik> listaUzytkownikow = null;
	
	listaUzytkownikow = uzytkownikDAO.getListaPacjentow();
	Iterator<Uzytkownik> i = listaUzytkownikow.iterator();
%>

<form id ="edycjaForm" action="WyswietlUzytkownikow.do" method="post" class="auxilium">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<br />
	<br />
	<label>
	        <span>Wyszukaj u&zdot;ytkownika po peselu:</span>
	        <input type="text" name="peselDoWyszukania">
	        <input type="image" name="wyszukaj" src="icons/ic_action_search.png"
	        	   formaction="WyszukajUzytkownikaWKartotece.do">
	        <input class="prawyPrzycisk" type="submit" name="powrotDoPanelu" value="Wr&oacute;&cacute; do panelu" 
	        	   formaction="WrocDoPanelu.do"> 
	</label>
	<br/>
	<br/>
	<br/> 
	<table>
	<tr>
	<th><label class="listHeader">Imi&eogon;</label></th>
	<th><label class="listHeader">Nazwisko</label></th>
	<th><label class="listHeader">Pesel</label></th>
	<th></th>
	</tr>   

	<%
		while (i.hasNext()) {
		Uzytkownik uzytkownik = (Uzytkownik)i.next();
		out.print("<tr>");
		out.print("<td>"+uzytkownik.getImie()+"</td>");
		out.print("<td>"+uzytkownik.getNazwisko()+"</td>");
		out.print("<td>"+uzytkownik.getPesel()+"</td>");
		out.print("<td><input type='image' name='idPacjent' src='icons/ic_action_search.png' alt='WyswietlKartoteke' formaction='WyswietlKartotekePacjenta.do' value='"+uzytkownik.getId()+"'></td>");
		out.print("</tr>");
	}
	
	%>	
		
	</table>
</form>
