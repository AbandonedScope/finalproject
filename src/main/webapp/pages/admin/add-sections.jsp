<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty language}"><fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"><fmt:setLocale value="${language = 'en_US'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.section"/></title>
</head>
<body>
<c:import url="../header.jsp"/>
<form id="form" action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="add-menu-section"/>
    <label>
        <fmt:message key="label.section.name"/> :
        <br/>
        <input type="text" name="menu-section-name" required>
    </label>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.admin.add.menuitem.button.text"/>">
</form>
<c:import url="../footer.jsp"/>
</body>
</html>
