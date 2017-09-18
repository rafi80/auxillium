<%@page import="com.rhl.auxilium.dao.GabinetDAO"%>
<%@page import="com.rhl.auxilium.entities.Gabinet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	GabinetDAO gabinetDAO = new GabinetDAO();
	List<Gabinet> listaGabinetow = gabinetDAO.getListaGabinetow();
	Iterator<Gabinet> iteratorGabinetow = listaGabinetow.iterator();
%>
<script type="text/javascript" src="scripts/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript">

	function checkDelete()
	{	  	
		  var r = confirm("Czy usun\u0105\u0107 rekord?");
		  if (r==true) {
			  document.getElementById("edycjaForm").action = "UsunGabinet.do";		  
			}			
	}

		// spytaj o potwierdzenie opuszczenia formularza 
	
	function checkWroc()
		{	  	
			  var r = confirm("Czy na pewno opu\u015bci\u0107 ekran?");
			  if (r==true) {
				 document.getElementById("wrocForm").submit();		  
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

<form id="edycjaForm" action="PrzejdzDoEtapu2Lekarz.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<br/><br/>
	
	
	<label>
		<Span>Wybierz dzie&nacute;:</Span>
		<input class="datePicker" type="date" name="wybranaData" required>
	</label>
	<br/><br/>
	<label>
		<span></span>
		<input class="button" type="submit" value="Wybierz dzie&nacute; do przegl&aogon;dania" > 
	</label> 
	<br/>
</form>