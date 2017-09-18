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
		<input class="prawyPrzycisk" type="button" name="powrotDoPaneluAdministratora" value="Wr&oacute;&cacute; do panelu" 
	        	   onclick="checkWroc()"> 
	</label>
</form>

<form id="edycjaForm" action="PrzejdzDoEtapu2Administrator.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<br/><br/>
	
	
	<label>
		<Span>Wybierz dzie&nacute;:</Span>
		<input class="datePicker" type="date" name="wybranaData" required>
	</label>
	<br/><br/>
	<label>
	        <span id="gabinet">Wybierz gabinet:</span>
	        <select name="idGabinetu" id="listaGabinetow" required>
		        <!--option value="0" disabled selected> -- wybierz typ pracownika -- </option>  -->
		        <%
					while (iteratorGabinetow.hasNext()) {
					Gabinet gabinet = (Gabinet)iteratorGabinetow.next();
					out.print("<option id="+gabinet.getId()+" value="+gabinet.getId()+">Gabinet "+gabinet.getNumer()+"&nbsp;&nbsp;&nbsp;</option>");
					}
				%>	
			</select>
	</label>
	<br/><br/>
	<label>
		<span></span>
		<input class="button" type="submit" value="Wybierz gabinet i dzie&nacute; do edycji" > 
	</label> 
	<br/>
</form>