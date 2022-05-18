<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title></title>
</head>
<body>
<div style="display: flex;
justify-content: space-between;">
        <a href="${pageContext.request.contextPath}/index.jsp">
            <fmt:message key="navigation.guest.main"/>
        </a>
    <form action="${pageContext.request.contextPath}/pages/cart.jsp">
        <input type="submit" value="<fmt:message key="navigation.guest.cart"/>"/>
    </form>
    <c:if test="${sessionScope.user != null && sessionScope.user.getRole() == RoleType.ADMIN}">
        <form action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="on-add-menu-item"/>
            <input type="submit" value="<fmt:message key="navigation.admin.menuitem"/>"/>
        </form>
        <form action="${pageContext.request.contextPath}/pages/authorized/admin/add-ingredient.jsp">
            <input type="submit" value="<fmt:message key="navigation.admin.ingredient"/>"/>
        </form>
        <form action="${pageContext.request.contextPath}/pages/authorized/admin/add-sections.jsp">
            <input type="submit" value="<fmt:message key="navigation.admin.section"/>"/>
        </form>
    </c:if>
    <c:choose>
        <c:when test="${sessionScope.user != null && sessionScope.user.getRole() == RoleType.GUEST}">
            <a href="${pageContext.request.contextPath}/pages/login.jsp">
                <fmt:message key="navigation.guest.login"/>
            </a>
            <a href="${pageContext.request.contextPath}/pages/registration.jsp">
                <fmt:message key="navigation.guest.registration"/>
            </a>
        </c:when>
        <c:when test="${sessionScope.user != null && sessionScope.user.getRole() != RoleType.GUEST}">
            <a href="${pageContext.request.contextPath}/pages/authorized/profile.jsp">
                <fmt:message key="navigation.user.profile"/>
            </a>
        </c:when>
    </c:choose>
    <div style="display: flex;">
        <form action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="set-localization-to-english">
            <input type="hidden" name="path" value="${pageContext.request.requestURI}">
            <input type="submit" name="en" value="en">
        </form>
        <form action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="set-localization-to-russian">
            <input type="hidden" name="path" value="${pageContext.request.requestURI}">
            <input type="submit" name="en" value="ru">
        </form>
    </div>
</div>
</body>
</html>
