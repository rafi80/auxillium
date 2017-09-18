<%@page import="com.rhl.auxilium.dao.GabinetDAO"%>
<%@page import="com.rhl.auxilium.entities.Gabinet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	GabinetDAO gabinetDAO = new GabinetDAO();
	@SuppressWarnings("unchecked")
	List<Gabinet> listaGabinetow = (List<Gabinet>)request.getAttribute("listaZnalezionychGabinetow");
	Iterator<Gabinet> i = listaGabinetow.iterator();
%>

<script type="text/javascript">

  function checkDelete()
  {	  	
	  var r = confirm("Czy usun\u0105\u0107 rekord? Dodawanie wizyt do gabinetu nie b\u0119dzie mo\u017cliwe."
		  		+	" Aktualnie zaplanowane wizyty pozostaj\u0105 aktualne.");
	  if (r==true) {
		  document.getElementById("edycjaForm").action = "UsunGabinet.do";		  
		}			
  }

</script>

<form id ="edycjaForm" action="WyswietlGabinety.do" method="post" class="auxilium">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<br />
	<br />
	<label>
	        <span>Wyszukaj gabinet po numerze:</span>
	        <input type="text" name="numerGabinetu" value= <%=request.getAttribute("wyszukiwanyNumer")%>>
	        <input type="image" name="wyszukaj" src="icons/ic_action_search.png"
	        	   onclick="form.action='WyszukajGabinet.do';"/>
	        <input type="image" name="pokazWszystkie" src="icons/ic_action_undo.png" title="Poka&zdot; wszystkie"
	        	   formaction="WyswietlGabinety.do">
	        <input class="prawyPrzycisk" type="submit" name="powrotDoPanelu" value="Wr&oacute;&cacute; do panelu" 
	        	   formaction="WrocDoPanelu.do"> 
	</label>
	<br/>
	<br/>
	<br/> 

	<table>
	<tr>
	<th><label class="listHeader">Numer gabinetu</label></th>
	<th><label class="listHeader">Opis gabinetu</label></th>
	<th></th>
	<th></th>
	</tr>   

	<%
		while (i.hasNext()) {
		Gabinet gabinet = (Gabinet)i.next();
		out.print("<tr>");
		out.print("<td>"+gabinet.getNumer()+"</td>");
		out.print("<td>"+gabinet.getOpis()+"</td>");
		out.print("<td><input type='image' name='usuwaneId' src='icons/ic_action_cancel.png' alt='Usuń' value='"+gabinet.getId()+"' onclick='checkDelete()'></td>");
		out.print("<td><input type='image' name='doEdycjiId' src='icons/ic_action_edit.png' alt='Edytuj' formaction='EdytujGabinet.do' value="+gabinet.getId()+" ></td>");
		out.print("</tr>");
	}
	
	%>	
		<tr>
			<td><input type="text" name="dodawanyNumerGabinetu" pattern='.{3,15}'></td>
			<td><input type="text" name="dodawanyOpisGabinetu" pattern='.{0,100}'></td>
			<td></td>
			<td><input type="image" name="dodaj" src="icons/ic_action_new.png" alt='Dodaj' formaction="DodajGabinet.do"></td>
		</tr>
	</table>
</form>