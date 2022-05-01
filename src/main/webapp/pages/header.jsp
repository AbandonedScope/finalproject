<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
<div style="display: flex;
justify-content: space-between;">
    <a href="${pageContext.request.contextPath}/pages/main.jsp">Main</a>
    <c:if test="${sessionScope.user != null && sessionScope.user.getRole() == RoleType.ADMIN}">
        <a href="${pageContext.request.contextPath}/pages/admin/add-ingredient.jsp">Add ingredient</a>
        <a href="${pageContext.request.contextPath}/pages/admin/add-menu-item.jsp">Add menu item</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/pages/registration.jsp">Registration</a>
</div>
</body>
</html>
