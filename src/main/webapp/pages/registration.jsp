<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType"
         import="com.mahanko.finalproject.controller.ValidationMessage" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.guest.registration"/></title>
</head>
<body>
<div class="d-flex justify-content-center pt-5">
    <div class="p-3 pt-2 card" style="width: 32rem">
        <p class="text-center m-0" style="font-size: 2rem;">
            <fmt:message key="label.registration"/>
        </p>
        <form class="m-0" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="${CommandType.ADD_USER}"/>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="name" type="text" name="name" placeholder="Name" maxlength="20"
                       required/>
                <c:if test="${not empty requestScope.get(ValidationMessage.USERNAME_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.username"/>
                </c:if>
                <label for="name">
                    <fmt:message key="label.user.name"/></label>
            </div>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="surname" type="text" name="surname" placeholder="Surname" maxlength="20"
                       required/>
                <c:if test="${not empty requestScope.get(ValidationMessage.USER_SURNAME_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.user.surname"/>
                </c:if>
                <label for="surname">
                    <fmt:message key="label.user.surname"/></label>
            </div>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="login" type="text" name="login" placeholder="Login" maxlength="16"
                       minlength="8" required/>
                <c:if test="${not empty requestScope.get(ValidationMessage.LOGIN_REGISTRATION_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.login"/>
                </c:if>
                <label for="login">
                    <fmt:message key="label.user.login"/></label>
            </div>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="password" type="password" name="password" placeholder="Password"
                       maxlength="20" minlength="8" required/>
                <c:if test="${not empty requestScope.get(ValidationMessage.PASSWORD_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.password"/>
                </c:if>
                <label for="password">
                    <fmt:message key="label.user.password"/></label>
            </div>
            <div class="form-floating my-5 fs-5">
                <input class="form-control" id="confirm-password" type="password" name="confirm-password"
                       placeholder="ConfirmPassword" maxlength="20" minlength="8" required/>
                <c:if test="${not empty requestScope.get(ValidationMessage.PASSWORD_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.password"/>
                </c:if>
                <label for="confirm-password">
                    <fmt:message key="label.user.password.confirm"/></label>
            </div>
            <input class="btn btn-outline-primary fs-5" type="submit" name="login-sub"
                   value="<fmt:message key="action.guest.login"/>">
            <c:if test="${not empty requestScope.get(ValidationMessage.REGISTRATION_USER_EXISTS_MESSAGE)}">
                <fmt:message key="message.registration.user-exists"/>
            </c:if>
        </form>
    </div>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
