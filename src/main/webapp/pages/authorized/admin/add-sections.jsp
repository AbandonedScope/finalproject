<%@ page contentType="text/html;charset=UTF-8"
import="com.mahanko.finalproject.controller.ParameterType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.section"/></title>
</head>
<body>
<c:import url="../../header.jsp"/>
<form id="form" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add-menu-section"/>
    <label>
        <fmt:message key="label.section.name"/> :
        <br/>
        <input type="text" name="menu-section-name" required>
        <c:if test="${not empty requestScope.get(ParameterType.SECTION_NAME_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.section-name"/>
        </c:if>
    </label>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.admin.add.menuitem"/>">
</form>
<c:import url="../../footer.jsp"/>
</body>
</html>
