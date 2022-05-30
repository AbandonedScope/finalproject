<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ParameterType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.section"/></title>
</head>
<body>
<div class="mx-5 mt-2">
    <h2><fmt:message key="navigation.admin.section"/></h2>
    <div class="justify-content-center mx-5">
        <form id="form" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="add-menu-section"/>
            <div class="form-floating my-5">
                <input class="form-control" id="name" type="text" name="menu-section-name" placeholder="Name" required>
                <c:if test="${not empty requestScope.get(ParameterType.SECTION_NAME_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.section-name"/>
                </c:if>
                <label for="name">
                    <fmt:message key="label.section.name"/>
                </label>
            </div>
            <div class="d-flex justify-content-end">
                <input class="btn btn-outline-primary fs-5" type="submit" name="sub"
                       value="<fmt:message key="action.admin.add.section"/>">
            </div>
        </form>
    </div>
</div>
<c:import url="../../footer.jsp"/>
</body>
</html>
