<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 15.04.2022
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
import="com.mahanko.finalproject.controller.command.CommandType" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="${CommandType.ADD_USER}" />
    <label>
        Name :
        <input type="text" name="name" maxlength="20"/>
    </label>
    <br/>
    <label>
        Surname :
        <input type="text" name="surname" maxlength="20"/>
    </label>
    <br/>
    <label>
        Login :
        <input type="text" name="login" maxlength="16" minlength="8"/>
    </label>
    <br/>
    <label>
        Password :
        <input type="password" name="password" minlength="8" maxlength="20"/>
    </label>
    <br/>
    <label>
        Confirm password :
        <input type="password" name="confirm-password" minlength="8" maxlength="20"/>
    </label>
    <br/>
    <input type="submit" name="sub" value="log in">
</form>
${register_msg}
</body>
</html>
