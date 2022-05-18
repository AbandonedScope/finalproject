<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<c:import url="../header.jsp"/>
<p>${sessionScope.user.name}</p>
<p>${sessionScope.user.login}</p>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
    <input type="submit" value="<fmt:message key="action.user.logout"/>"/>
</form>
</body>
</html>
