<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<form action="controller" method="post">
    <input type="hidden" name="command" value="login"/>
    <label>
        Login :
        <input type="text" name="login" value="${sessionScope.user.getLogin()}"/>
    </label>
    <br/>
    <label>
        Password :
        <input type="password" name="password" value=""/>
    </label>
    <br/>
    <input type="submit" name="sub" value="log in"/>
    <br/>
</form>
<form action="controller">
    <input type="hidden" name="command" value="register">
    <input type="submit" name="sub" value="register">
</form>
${login_msg}
</body>
</html>