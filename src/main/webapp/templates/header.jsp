<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% Uzytkownik uzytkownikWSesji = (Uzytkownik) session.getAttribute("uzytkownik"); %>
    <header id="naglowek">
	    <p id="naglowek-content">
	            ${param.tytulStrony}
	        </p>
        <p id="logo">
       		 <img src="${pageContext.request.contextPath}/images/Auxilium_Logosmall.png">
        </p>
       	<%
    	if (uzytkownikWSesji!=null) {
    		out.print("<label class='wyloguj'> Zalogowany u&zdot;ytkownik: "+uzytkownikWSesji.getLogin()+
    			" "+"<a href='wyloguj.jsp'>Wyloguj</a> &nbsp;</label>");	
    		//out.print("<Label class='wyloguj'></Label>");
		}
    	%>	
		<br/>
    </header>