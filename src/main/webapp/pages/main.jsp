<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
</head>
<body>
Result (forward) = ${sessionScope.user.getName()}
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
