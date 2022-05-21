<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.user.profile"/></title>
</head>
<body>
<c:set var="cart" value="${sessionScope.shoppingCart}"/>
<c:if test="${not empty cart.items}">
    <div class="m-5" style="max-width: 55rem">
        <table class="table table-bordered">
            <thead>
            <tr class="table-primary">
                <th scope="col"><fmt:message key="cart.table.column.meal"/></th>
                <th scope="col"><fmt:message key="cart.table.column.cost"/></th>
                <th scope="col"><fmt:message key="cart.table.column.sum"/></th>
                <th scope="col"><fmt:message key="cart.table.column.amount"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="entryItem" items="${cart.items}">
                <c:set var="item" value="${entryItem.key}"/>
                <c:set var="count" value="${entryItem.value}"/>
                <tr>
                    <td class="align-middle">
                        <div class="d-flex justify-content-around align-items-center">
                            <img style="max-width: 5rem; max-height: 5rem;"
                                 src="data:image/png;base64,${item.pictureBase64}"
                                 alt="${item.name}">
                            <h5>${item.name}</h5>
                        </div>
                    </td>
                    <td class="align-middle">
                        <p>${item.cost}</p>
                    </td>
                    <td class="align-middle"><p>${count * item.cost}</p></td>
                    <td class="align-middle">
                        <form action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="change-item-in-cart-amount">
                            <input type="hidden" name="menu-item-id" value="${item.id}">
                            <div class="input-group">
                                <input class="form-control" type="number" name="menu-item-count" value="${count}" min="1" step="1" required>
                                <input class="btn btn-outline-primary" type="submit" name="submit-button" value="<fmt:message key="action.guest.cart.add-to-cart"/>"/>
                            </div>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <p><fmt:message key="label.shopping-cart.cost"/> : ${cart.cost}</p>
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
        <input type="submit" value="<fmt:message key="action.user.logout"/>"/>
    </form>
    <form action="${pageContext.request.contextPath}/pages/order.jsp">
        <input type="submit" value="<fmt:message key="action.guest.make-order"/>"/>
    </form>
</c:if>
<c:if test="${empty cart.items}">
    <p>
        <fmt:message key="cart.message.empty"/>
    </p>
</c:if>
</body>
</html>