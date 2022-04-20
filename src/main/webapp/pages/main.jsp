<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 05.04.2022
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
Result (forward) = ${sessionScope.user.getName()}
<br/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.LOGOUT}"/>
    <input type="submit" value="logout"/>
</form>
<hr/>
<form id="add-button" action="${pageContext.request.contextPath}/pages/admin/add.jsp">
    <input type="hidden" name="command" value="${CommandType.ADD_MENU_ITEM}"/>
    <input type="submit" value="add-item"/>
</form>
<script type="text/javascript">
    if (${sessionScope.user != null && sessionScope.user.getRole() == RoleType.ADMIN}) {
        setAddButtonAccesses(true);
    }
    else {
        setAddButtonAccesses(false)
    }

    function setAddButtonAccesses(isAdmin) {
        const $form = document.getElementById('add-button')
        if (isAdmin) {
            $form.classList.add("visible");
        }
        else
        {
            $form.classList.add("hidden");
        }
    }
</script>
</body>
</html>
