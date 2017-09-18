<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page errorPage="ShowError.jsp" %>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html>
<html>
<head>
	<title>${param.title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="Content-Language" content="pl" />
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/styl.css" />
	<link rel="shortcut icon" href="images/Auxilium_Logosmaller.png"> 
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="/${param.content}.jsp"/>
	<jsp:include page="footer.jsp"/>
</body>
</html>