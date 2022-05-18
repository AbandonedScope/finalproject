<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<c:import url="header.jsp"/>
<c:set var="cart" value="${sessionScope.shoppingCart}"/>
<c:forEach var="entryItem" items="${cart.items}">
    <c:set var="item" value="${entryItem.key}"/>
    <c:set var="count" value="${entryItem.value}"/>
    <li>
        <h3>${item.name}</h3>
        <div style="display: flex;">
            <img style="max-width: 100px; max-height: 100px;" src="data:image/png;base64,${item.pictureBase64}"
                 alt="${item.name}">
            <div style="display: block;">
                <p><fmt:message key="label.menuitem.count"/> ${count}</p>
                <p><fmt:message key="label.menuitem.cost"/> ${item.cost}</p>
                <p><fmt:message key="label.menuItem.calories"/> ${item.calories}</p>
            </div>
        </div>
    </li>
</c:forEach>
<p><fmt:message key="label.shopping-cart.cost"/> : ${cart.cost}</p>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
    <input type="submit" value="<fmt:message key="action.user.logout"/>"/>
</form>
<form action="${pageContext.request.contextPath}/pages/order.jsp">
    <input type="submit" value="<fmt:message key="action.guest.make-order"/>"/>
</form>
</body>
</html>