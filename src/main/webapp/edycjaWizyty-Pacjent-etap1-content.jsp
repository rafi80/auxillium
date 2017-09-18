<%@page import="com.rhl.auxilium.dao.GabinetDAO"%>
<%@page import="com.rhl.auxilium.entities.Gabinet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>

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
		<input class="prawyPrzycisk" type="button" name="powrotDoPanelu" value="Wr&oacute;&cacute; do panelu" 
	        	   onclick="checkWroc()"> 
	</label>
</form>

<form id="edycjaForm" action="PrzejdzDoEtapu2Pacjent.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<br/><br/>
	<%String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());%>
	
	<label>
		<Span>Wybierz dzie&nacute; wizyty:</Span>
		<input class="datePicker" type="date" name="edytowanaData" min=<%=date%> required>
	</label>
	<br/><br/>

	<label>
		<span></span>
		<input class="button" type="submit" value="Wybierz dzie&nacute; wizyty" > 
	</label> 
	<br/>
</form>