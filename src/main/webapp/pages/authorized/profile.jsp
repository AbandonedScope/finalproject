<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<p>${sessionScope.user.name}</p>
<p>${sessionScope.user.login}</p>
<a href="orders.jsp">
    <button class="btn btn-outline-primary fs-5">
        <fmt:message key="navigation.user.orders"/>
    </button>
</a>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
    <input class="btn btn-outline-secondary fs-5" type="submit" value="<fmt:message key="action.user.logout"/>"/>
</form>
<c:import url="../footer.jsp"/>
</body>
</html>
