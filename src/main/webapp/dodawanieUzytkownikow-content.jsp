<%@page import="com.rhl.auxilium.dao.UzytkownikDAO"%>
<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@page import="com.rhl.auxilium.dao.SpecjalizacjaDAO"%>
<%@page import="com.rhl.auxilium.dao.TypPracownikaDAO"%>
<%@page import="com.rhl.auxilium.entities.Specjalizacja"%>
<%@page import="com.rhl.auxilium.entities.TypPracownika"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix ="fmt" %>
<%
	
	//zalogowany uzytkownik
	Uzytkownik uzytkownikWSesji = (Uzytkownik) session.getAttribute("uzytkownik");

	TypPracownikaDAO typPracownikaDAO = new TypPracownikaDAO();
	List<TypPracownika> listaTypowPracownikow = typPracownikaDAO.getListaTypowPracownikow() ;
	Iterator<TypPracownika> iteratorTypowPracownika = listaTypowPracownikow.iterator();
	
	SpecjalizacjaDAO specjalizacjaDAO = new SpecjalizacjaDAO();
	List<Specjalizacja> listaSpecjalizacji = specjalizacjaDAO.getListaSpecjalizacji() ;
	Iterator<Specjalizacja> iteratorSpecjalizacji = listaSpecjalizacji.iterator();
%>

<script type="text/javascript" src="scripts/jquery-2.1.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript">

	// spytaj o potwierdzenie opuszczenia formularza 
	function checkWroc()
	{	  	
		  var r = confirm("Czy opu\u015bci\u0107 ekran przed zapisem?");
		  if (r==true) {
			 document.getElementById("wrocForm").submit();		  
			}			
	};

	// spytaj o potwierdzenie zapisania zmian
	function validate(form)
	{	  	
		 var r = confirm("Czy zapisa\u0107 zmiany?");
		 if (r==true) {
			 document.getElementById("edycjaForm").action = "ZapiszDodawanegoUzytkownika.do";
			 document.getElementById("edycjaForm").submit();		
			 } else {
			  return false;
				 }		
	};

	// dopasowanie zawartosci formularza do typu edytowanego Uzytkownika
	// wyswietla combo typ pracownika, jezeli uzytkownik jest pracownikiem	
 		 $(document).ready( function() {
		      $('#czyPracownik').bind('change', function (e) { 
		    	  if( $('#czyPracownik').is(':checked') ) {
		    		  $('#typPracownikaSpan').show();
					  $('#typPracownika').show(); 
					  $('#typPracownika').prop('required',true);
					//  $('#typPracownika').val('0');
		    	  } else {
		    		$('#typPracownika').prop('required',false);
		    		$('#specjalizacja').prop('required',false);
		        	$('#typPracownika').val(null);
					$('#specjalizacja').val(null);
					$('#typPracownikaSpan').hide();
					$('#typPracownika').hide();
					$('#specjalizacja').hide();
					$('#specjalizacjaSpan').hide();
		        }         
			      }).trigger('change');
		    });
 		// wyswietla combo specjalnosc lekarska, jezeli uzytkownik jest lekarzem	
 		 $(document).ready( function() {
		      $('#typPracownika').bind('change', function (e) { 
		    	  if( $('#typPracownika').val() == '3') {
		    		  $('#specjalizacjaSpan').show();
					  $('#specjalizacja').show(); 
					  $('#specjalizacja').prop('required',true);
				 //	  $('#specjalizacja').val('0');
		        }
		        else if( $('#typPracownika').val() != '3') {
		        	$('#specjalizacja').prop('required',false);
		        	$('#specjalizacja').val(null);
		        	$('#specjalizacja').val('0');
		        	$('#specjalizacjaSpan').hide();
					$('#specjalizacja').hide();
					
		        }         
			      }).trigger('change');
		    }); 

		 //sprawdza czy zgodne hasla
		  $(document).ready( function() {
			        $("#submit").click(function(){
			        $(".error").hide();
			        var hasError = false;
			        var passwordVal = $("#haslo").val();
			        var checkVal = $("#powtorzoneHaslo").val();
			        if (passwordVal == '') {
			            $("#haslo").after('<br/><span style="color: red;" class="error">Prosz&eogon; wprowadzi&cacute; has&lstrok;o.</span>');
			            hasError = true;
			        } else if (checkVal == '') {
			            $("#powtorzoneHaslo").after('<br/><span style="color: red;" class="error">Prosz&eogon; powtórzy&cacute; has&lstrok;o.</span>');
			            hasError = true;
			        } else if (passwordVal != checkVal ) {
			            $("#powtorzoneHaslo").after('<br/><span style="color: red;" class="error">Podane has&lstrok;a nie s&aogon; takie same.</span>');
			            hasError = true;
			        }
			        if(hasError == true) {return false;}
			    });
			});
		    
</script>

<form id="wrocForm" action="WyswietlUzytkownikow.do" method="post" class="auxilium" onsubmit="return checkForm(this);" >
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<label>
		Prosz&eogon; poda&cacute; dane u&zdot;ytkownika (pola oznaczone * s&aogon; obowi&aogon;zkowe).
	</label>
	<label> 
		<input class="prawyPrzycisk" type="button" name="powrotDoListyUzytkownikow" value="Wr&oacute;&cacute; do listy u&zdot;ytkownik&oacute;w" 
	        	   onclick="checkWroc()"> 
	</label>
</form>
	<%

		if (request.getAttribute("errors")!=null) {
	    	@SuppressWarnings("unchecked")
			List<String> errors = (List<String>)request.getAttribute("errors");
			Iterator<String> i = errors.iterator();
			
			while (i.hasNext()){
				String error = i.next();
				out.print("<h2 class='error'>"+error+"</h1>");
			}
		}
	%>
<form onsubmit="return validate(this);" id="edycjaForm" method="post" class="auxilium">
	<input type="hidden" name="zrodlo" value=<%=request.getAttribute("zrodlo")%>>
	<input type="hidden" name="idDoEdycji" value = <%=request.getAttribute("idDoEdycji")%>>
	
	<label>
	        <span>Imi&eogon;*:</span>
	        <input id="imie" type="text" name="imie" required pattern='.{2,30}'>
	</label>
	<br/>    
	<label>
	        <span>Drugie imi&eogon;:</span>
	        <input id="drugieImie" type="text" name="drugieImie"  pattern='.{2,30}'>
	</label>
	<br/>    
	<label>
	        <span>Nazwisko*:</span>
	        <input id="nazwisko" type="text" name="nazwisko" required pattern='.{2,50}'>
	</label>
	<br/>   
	<label>
	        <span>Pesel*:</span>
	        <input id="pesel" type="text" name="pesel" required pattern='.{11}'>
	</label>
	<br/>    
		<label>
	        <span>Numer telefonu:</span>
	        <input id="numerTelefonu" type="text" name="numerTelefonu" pattern='.{9,30}'>
	</label>
	<br/> 
	<label>
	        <span>Adres:</span>
	        <input id="adres" type="text" name="adres" pattern='.{3,150}'>
	</label>
	<br/>    
	<label>
	        <span>Adres e-mail:</span>
	        <input id="email" type="email" name="email" pattern='.{3,100}'>
	</label>
	<br/>   
	<label>
	        <span>Czy Pracownik?</span>
	        <input id="czyPracownik" type="checkbox" name="czyPracownik" value="1" <%if(uzytkownikWSesji.getTypPracownika()==2){out.print("disabled");}; %>>
	</label>
	<br/><br/>
	<label>
	        <span id="typPracownikaSpan" hidden="true">Typ pracownika:
	        	<br/><small>Nale&zdot;y wybra&cacute; typ pracownika</small>
	        </span>
	        <select name="typPracownika" id="typPracownika" hidden="true">
		        <option value="0" disabled selected> -- wybierz typ pracownika -- </option>
		        <%
					while (iteratorTypowPracownika.hasNext()) {
					TypPracownika typPracownika = (TypPracownika)iteratorTypowPracownika.next();
					out.print("<option value="+typPracownika.getId()+">"+typPracownika.getTypPracownika()+"</option>");
					}
				
				%>	
			</select>
	</label>
	<br/> <br/> 
	<label>
	        <span id="specjalizacjaSpan" hidden="true">Specjalizacja:
	        	<br/><small>Nale&zdot;y wybra&cacute; specjalizacj&eogon;</small>
	        </span>
	        <select name="specjalizacja" id="specjalizacja" hidden="true">
		        <option selected></option>
		        <%
					while (iteratorSpecjalizacji.hasNext()) {
					Specjalizacja specjalizacja = (Specjalizacja)iteratorSpecjalizacji.next();
					out.print("<option value="+specjalizacja.getId()+">"+specjalizacja.getNazwaSpecjalizacji()+"</option>");
					}
				%>	
			</select>
	</label>
	<br/>  <br/>  
	<label>
	        <span>Czy Aktywny?</span>
	        <input id="czyAktywny" type="checkbox" name="czyAktywny" value="1" checked>
	</label>
	<br/><br/>
    <label>
	        <span>Login*:
	        	<br/><small>Login musi zawiera&cacute; minimum 3 znaki</small>
	        </span>
	        <input id="login" type="text" name="login" required pattern='.{3,41}'>
	</label>
	<br/><br/>
	<label>
	        <span>Has&lstrok;o*:
	        	<br/><small>Has&lstrok;o musi zawiera&cacute; minimum 5 znak&oacute;w</small>
	        </span>
	        <input id="haslo" type="password" name="haslo" pattern='.{5,41}'>
	</label> 
	<br/>   
	<br/>
	<label>
	        <span>Powt&oacute;rz has&lstrok;o*:</span>
	        <input id="powtorzoneHaslo" type="password" pattern='.{5,41}'>
	</label> 
	<br/>   
	<label> 
		<input class="prawyPrzycisk" id="submit" type="submit" value="Zapisz">
   </label>
	<br /><br />	
</form>
