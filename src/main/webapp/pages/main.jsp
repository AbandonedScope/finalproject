<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"
         import="com.mahanko.finalproject.controller.command.CommandType"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
</head>
<body>
<ul>
<c:forEach var="menuItem" items="${sessionScope.menuItems}">
    <li>
        <h3>${menuItem.name}</h3>
        <img style="max-width: 150px; max-height: 150px;" src="data:image/png;base64,${menuItem.pictureBase64}" alt="${menuItem.name}">
            <c:forEach var="ingredient" items="${menuItem.ingredients}">
                ${ingredient.name}
                    <img style="max-width: 30px; max-height: 30px;" src="data:image/png;base64,${ingredient.picture}" alt="${menuItem.name}">
            </c:forEach>
        ${menuItem.cost}
            ${menuItem.calories}
            ${menuItem.proteins}
            ${menuItem.carbohydrates}
            ${menuItem.weight}
    </li>
</c:forEach>
</ul>
<br/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
    <input type="submit" value="logout"/>
</form>
<hr/>
<c:if test="${sessionScope.user != null && sessionScope.user.getRole() == RoleType.ADMIN}">
    <form action="${pageContext.request.contextPath}/controller">
        <input type="hidden" name="command" value="${CommandType.ON_ADD_MENU_ITEM}"/>
        <input type="submit" value="add item"/>
    </form>
    <br/>
    <form action="${pageContext.request.contextPath}/pages/admin/add-ingredient.jsp">
        <input type="submit" value="add ingredient"/>
    </form>
</c:if>
</body>
</html>
