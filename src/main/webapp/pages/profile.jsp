<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty language}"><fmt:setLocale value="${language}"/></c:when>
    <c:when test="${empty language}"><fmt:setLocale value="${language = 'en_US'}"/></c:when>
</c:choose>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<p>${sessionScope.user.name}</p>
<p>${sessionScope.user.login}</p>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
    <input type="submit" value="<fmt:message key="action.user.logout.button.text"/>"/>
</form>
</body>
</html>
