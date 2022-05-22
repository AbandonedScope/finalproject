<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>500</title>
</head>
<body>
Request from : ${pageContext.errorData.requestURI} is failed. <br/>
Servlet name : ${pageContext.errorData.servletName} <br/>
Status code : ${pageContext.errorData.statusCode} <br/>
Exception : ${pageContext.errorData.throwable} <br/>
<c:import url="../footer.jsp"/>
</body>
</html>
