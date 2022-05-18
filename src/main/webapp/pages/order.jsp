<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"
         import="com.mahanko.finalproject.controller.command.CommandType"
         import="java.time.Instant" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<jsp:include page="header.jsp"/>
<c:set var="cart" value="${sessionScope.shoppingCart}"/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.ADD_ORDER}"/>
    <c:if test="${sessionScope.user == null || sessionScope.user.role == RoleType.GUEST}">
        <p>Name</p>
    </c:if>
    <input type="datetime-local" name="order-time" required>
    <p>Minimum value now + 30 minutes</p>
    <input type="submit" value="<fmt:message key="action.guest.make-order"/>"/>
</form>
</body>
</html>