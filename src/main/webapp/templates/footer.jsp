<%@page import="com.rhl.auxilium.entities.Uzytkownik"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% Uzytkownik uzytkownikWSesji = (Uzytkownik) session.getAttribute("uzytkownik"); %>
    <footer>
	<%--     	<%
    	if (uzytkownikWSesji!=null) {
    		out.print("<label class='wyloguj'> Zalogowany u&zdot;ytkownik: "+uzytkownikWSesji.getLogin()+". "+"<a href='Wyloguj.do'>Wyloguj</a> &nbsp;</label>");    		
		}
    	%> --%>
	</footer>