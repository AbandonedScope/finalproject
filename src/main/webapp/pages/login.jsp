<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.guest.login"/></title>
</head>
<body>
<div class="d-flex justify-content-center pt-5">
    <div class="p-3 pt-2 card" style="width: 27rem">
        <p class="text-center m-0" style="font-size: 2rem;">
            <fmt:message key="label.login"/>
        </p>
        <form action="${pageContext.request.contextPath}/controller" method="get">
            <input type="hidden" name="command" value="login"/>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="login" type="text" name="login" placeholder="Login" required/>
                <label for="login">
                    <fmt:message key="label.user.login"/></label>
            </div>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="password" type="password" name="password" placeholder="********"
                       required/>
                <label for="password">
                    <fmt:message key="label.user.password"/></label>
            </div>
            <input class="btn btn-primary fs-5" type="submit" name="sub"
                   value="<fmt:message key="action.guest.login"/>"/>
        </form>
        <form class="m-0" action="${pageContext.request.contextPath}/controller">
            <input type="hidden" name="command" value="register">
            <input class="btn btn-outline-primary fs-5" type="submit" name="register-sub"
                   value="<fmt:message key="action.guest.registration"/>">
        </form>
        ${login_msg}
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>