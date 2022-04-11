<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 05.04.2022
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
Result (forward) = ${user}
<br/>
Result (redirect/forward) = ${user_name}
<br/>
<form action="controller">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="logout"/>
</form>
<hr/>
${filter_attribute}
</body>
</html>
