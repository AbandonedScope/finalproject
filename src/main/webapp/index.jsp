<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.guest.main"/></title>
</head>
<body>
<c:import url="pages/header.jsp"/>
<ul>
    <c:forEach var="menuItem" items="${sessionScope.menuItems}">
        <li>
            <h3>${menuItem.name}</h3>
            <div style="display: flex;">
                <img style="max-width: 150px; max-height: 150px;" src="data:image/png;base64,${menuItem.pictureBase64}"
                     alt="${menuItem.name}">
                <div style="display: block;">
                    <p>${menuItem.description}</p>
                    <div style="display: flex;
justify-content: space-between">
                        <c:forEach var="ingredient" items="${menuItem.ingredients}">
                            <div style="display: block;">
                                <p>${ingredient.name}</p>
                                <img style="max-width: 30px; max-height: 30px;"
                                     src="data:image/png;base64,${ingredient.picture}" alt="${menuItem.name}">
                            </div>
                        </c:forEach>
                    </div>
                    <p><fmt:message key="label.menuitem.cost"/> ${menuItem.cost}</p>
                    <p><fmt:message key="label.menuItem.calories"/> ${menuItem.calories}</p>
                    <p><fmt:message key="label.menuItem.proteins"/> ${menuItem.proteins}</p>
                    <p><fmt:message key="label.menuItem.carbohydrates"/> ${menuItem.carbohydrates}</p>
                    <p><fmt:message key="label.menuItem.weight"/> ${menuItem.weight}</p>
                </div>
            </div>
        </li>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="add-item-to-cart">
            <input type="hidden" name="menu-item-id" value="${menuItem.id}">
            <input type="number" name="menu-item-count" value="1" min="1" step="1" required>
            <input type="submit" name="submit-button" value="<fmt:message key="action.guest.cart.add-to-cart"/>"/>
        </form>
    </c:forEach>
</ul>
<hr/>
<c:if test="${sessionScope.user != null && sessionScope.user.getRole() == RoleType.ADMIN}">
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="${CommandType.ON_ADD_MENU_ITEM}"/>
        <input type="submit" value="<fmt:message key="action.admin.add.menuitem"/>"/>
    </form>
    <br/>
    <form action="${pageContext.request.contextPath}/pages/admin/add-ingredient.jsp" method="post">
        <input type="submit" value="<fmt:message key="action.admin.add.ingredient"/>"/>
    </form>
</c:if>
<c:import url="pages/footer.jsp"/>
</body>
</html>
