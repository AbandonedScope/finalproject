<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 27.04.2022
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="add-section">
        <label>
            Section name :
            <input type="text" name="section-name">
        </label>
    </form>
</body>
</html>
