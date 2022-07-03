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
<c:set var="customer" value="${sessionScope.user}"/>
<div class="m-5 d-flex flex-column">
    <div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.name"/>
                </label>
                <div>
                    ${customer.name}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.surname"/>
                </label>
                <div>
                    ${customer.surname}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.login"/>
                </label>
                <div>
                    ${customer.login}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.role"/>
                </label>
                <div>
                    ${customer.role}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.loyal-points"/>
                </label>
                <div>
                    ${customer.loyaltyPoints}
                </div>
            </li>
        </ul>
    </div>
    <div class="m-1 d-flex justify-content-evenly">
        <a href="orders.jsp">
            <button class="btn btn-outline-primary fs-5">
                <fmt:message key="navigation.user.orders"/>
            </button>
        </a>
        <form action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
            <input class="btn btn-outline-secondary fs-5" type="submit" value="<fmt:message key="action.user.logout"/>"/>
        </form>
    </div>
</div>
<c:import url="../footer.jsp"/>
</body>
</html>
