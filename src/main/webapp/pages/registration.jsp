<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType"
         import="com.mahanko.finalproject.controller.ParameterType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:choose>
    <c:when test="${not empty language}"> <fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"> <fmt:setLocale value="${language = 'ru_RU'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="${CommandType.ADD_USER}"/>
    <label>
        <fmt:message key="label.user.name"/> :
        <input type="text" value="${pageContext.request.getParameter(ParameterType.USER_NAME)}" name="name" maxlength="20"/>
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
    </label>
    <br/>
    <label>
        <fmt:message key="label.user.password.confirm"/> :
        <input type="password" name="confirm-password" value="${pageContext.request.getParameter(ParameterType.USER_CONFIRM_PASSWORD)}" minlength="8" maxlength="20"/>
    </label>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.guest.login.button.text"/>">
</form>
${register_msg}
</body>
</html>
