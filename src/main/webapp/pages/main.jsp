<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 05.04.2022
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Main</title>
</head>
<body>
Result (forward) = ${sessionScope.user.getName()}
<br/>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="logout"/>
</form>
<hr/>
${filter_attribute}
</body>
</html>
