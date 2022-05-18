<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType"
         import="com.mahanko.finalproject.controller.ParameterType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.guest.registration"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="${CommandType.ADD_USER}"/>
    <label>
        <fmt:message key="label.user.name"/> :
        <input type="text" value="${pageContext.request.getParameter(ParameterType.USER_NAME)}" name="name" maxlength="20"/>
        <c:if test="${not empty requestScope.get(ParameterType.USERNAME_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.username"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.user.surname"/> :
        <input type="text" name="surname" value="${pageContext.request.getParameter(ParameterType.USER_SURNAME)}" maxlength="20"/>
    </label>
    <br/>
    <label>
        <fmt:message key="label.user.login"/> :
        <input type="text" name="login" value="${pageContext.request.getParameter(ParameterType.USER_LOGIN)}" maxlength="16" minlength="8"/>
    </label>
    <br/>
    <label>
        <fmt:message key="label.user.password"/> :
        <input type="password" name="password" value="${pageContext.request.getParameter(ParameterType.USER_PASSWORD)}" minlength="8" maxlength="20"/>
        <c:if test="${not empty requestScope.get(ParameterType.PASSWORD_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.password"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.user.password.confirm"/> :
        <input type="password" name="confirm-password" value="${pageContext.request.getParameter(ParameterType.USER_CONFIRM_PASSWORD)}" minlength="8" maxlength="20"/>
    </label>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.guest.login"/>">
    <c:if test="${not empty requestScope.get(ParameterType.REGISTRATION_USER_EXISTS_MESSAGE)}">
        <fmt:message key="message.registration.user-exists"/>
    </c:if>
</form>
</body>
</html>
