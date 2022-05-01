<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<c:import url="../header.jsp"/>
    <form action="${pageContext.request.contextPath}/controller" method="post">
        <input type="hidden" name="command" value="add-section">
        <label>
            Section name :
            <input type="text" name="section-name">
        </label>
    </form>
<c:import url="../footer.jsp"/>
</body>
</html>
