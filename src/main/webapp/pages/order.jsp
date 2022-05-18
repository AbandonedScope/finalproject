<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"
         import="com.mahanko.finalproject.controller.ParameterType"
         import="com.mahanko.finalproject.controller.command.CommandType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="header.jsp"%>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<c:set var="cart" value="${sessionScope.shoppingCart}"/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.ADD_ORDER}"/>
    <c:if test="${sessionScope.user == null || sessionScope.user.role == RoleType.GUEST}">
        <p>Name</p>
    </c:if>
    <input type="datetime-local" name="order-time" required>
    <c:if test="${not empty requestScope.get(ParameterType.SERVING_DATETIME_VALIDATION_MESSAGE)}">
        <fmt:message key="message.validation.order.serving-date"/>
    </c:if>
    <p>Minimum value now + 30 minutes</p>
    <input type="submit" value="<fmt:message key="action.guest.make-order"/>"/>
</form>
</body>
</html>