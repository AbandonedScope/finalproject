<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.2/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand">Cafee</a>
        <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/index.jsp">
                        <fmt:message key="navigation.guest.main"/>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/pages/cart.jsp">
                        <fmt:message key="navigation.guest.cart"/>
                    </a>
                </li>
                <c:if test="${sessionScope.user != null && sessionScope.user.role == RoleType.ADMIN}">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <fmt:message key="navigation.admin.edit"/>
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
                            <li>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/pages/authorized/admin/add-menu-item.jsp">
                                    <fmt:message key="navigation.admin.menuitem"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/pages/authorized/admin/add-ingredient.jsp">
                                    <fmt:message key="navigation.admin.ingredient"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/pages/authorized/admin/add-sections.jsp">
                                    <fmt:message key="navigation.admin.section"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/pages/authorized/admin/modify-item.jsp">
                                    <fmt:message key="navigation.admin.modify.item"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/pages/authorized/admin/modify-section.jsp">
                                    <fmt:message key="navigation.admin.modify.section"/>
                                </a>
                            </li>
                            <li>
                                <a class="dropdown-item"
                                   href="${pageContext.request.contextPath}/pages/authorized/admin/modify-ingredient.jsp">
                                    <fmt:message key="navigation.admin.modify.ingredient"/>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active"
                           href="${pageContext.request.contextPath}/pages/authorized/admin/orders.jsp">
                            <fmt:message key="navigation.admin.orders"/>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active"
                           href="${pageContext.request.contextPath}/pages/authorized/admin/user-find.jsp">
                            <fmt:message key="navigation.admin.users"/>
                        </a>
                    </li>
                </c:if>
                <c:choose>
                    <c:when test="${sessionScope.user != null && sessionScope.user.getRole() == RoleType.GUEST}">
                        <a class="nav-link active" href="${pageContext.request.contextPath}/pages/login.jsp">
                            <fmt:message key="navigation.guest.login"/>
                        </a>
                    </c:when>
                    <c:when test="${sessionScope.user != null && sessionScope.user.getRole() != RoleType.GUEST}">
                        <a class="nav-link active"
                           href="${pageContext.request.contextPath}/pages/authorized/profile.jsp">
                            <fmt:message key="navigation.user.profile"/>
                        </a>
                    </c:when>
                </c:choose>
            </ul>
        </div>

        <div style="display: flex;">
            <form class="mb-0" action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="set-localization-to-english">
                <input type="hidden" name="path" value="${pageContext.request.requestURI}">
                <input class="btn" type="submit" name="en" value="en">
            </form>
            <form class="mb-0" action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="set-localization-to-russian">
                <input type="hidden" name="path" value="${pageContext.request.requestURI}">
                <input class="btn" type="submit" name="en" value="ru">
            </form>
        </div>
    </div>
</nav>
</body>
</html>
