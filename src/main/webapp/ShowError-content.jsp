<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<h1>Ups...</h1>
<h1>Przepraszamy, wyst&aogon;pi&lstrok; b&lstrok;&aogon;d!.</h1>
<label>Oto szczeg&oacute;&lstrok;y: </label>
<br/>
<%	
	String error = "";
	@SuppressWarnings("unchecked")
	List<String> errors = (List<String>)request.getAttribute("errors");
	Iterator<String> i = errors.iterator();
	String stack="";
	stack = (String)request.getAttribute("stacktrace");
	
	while (i.hasNext()) {
		error = (String)i.next();
		out.print("<label style='color: red;'>"+error+ "</label><br/>");
	}
	
	out.print("<label style='color: red;'>"+stack+ "</label><br/>");
%>
<hr>
<h1>
	Kliknij <a href="panelAdministratora.jsp">tu</a> aby spr&oacute;bowa&cacute; jeszcze raz.
</h1>
