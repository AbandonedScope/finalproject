<%@ page contentType="text/html;charset=UTF-8"
        import="com.mahanko.finalproject.controller.ValidationMessage" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.orders"/></title>
</head>
<body>
<c:forEach var="order" items="${requestScope.orders}">
    <p>Serving ${order.servingTime.toString()}</p>
    <p>Received ${order.creationTime.toString()}</p>
</c:forEach>
<c:import url="../../footer.jsp"/>
</body>
</html>
