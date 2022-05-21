<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.guest.login"/></title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="login"/>
    <label>
        <fmt:message key="label.user.login"/> :
        <input type="text" name="login" value="${sessionScope.user.getLogin()}"/>
    </label>
    <br/>
    <label>
        <fmt:message key="label.user.password"/> :
        <input type="password" name="password" value=""/>
    </label>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.guest.login"/>"/>
    <br/>
</form>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="register">
    <input type="submit" name="sub" value="<fmt:message key="action.guest.registration"/>">
</form>
${login_msg}
</body>
</html>