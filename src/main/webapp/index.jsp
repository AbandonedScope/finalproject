<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/pages/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.guest.main"/></title>
</head>
<body>

<div class="row row-cols-4 row-cols-md-4 g-3 mx-5 my-3">
    <c:forEach var="menuItem" items="${sessionScope.menuItems}">
        <div class="col">
            <div style="width: 18rem;" class="card bg-light h-100">
                <div class="p-3">
                    <img src="data:image/png;base64,${menuItem.pictureBase64}" class="card-img-top"
                         alt="${menuItem.name}">
                </div>
                <div class="card-body p-1 ps-3">
                    <h5 class="card-title m-0">${menuItem.name}</h5>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item" style="height: 2.5rem">
                        <div class="d-flex justify-content-between">
                            <p><fmt:message key="label.menuItem.calories"/></p>
                            <p>${menuItem.calories}</p>
                        </div>
                    </li>
                    <li class="list-group-item" style="height: 2.5rem">
                        <div class="d-flex justify-content-between">
                            <p><fmt:message key="label.menuitem.cost"/></p>
                            <p>${menuItem.cost}</p>
                        </div>
                    </li>
                    <li class="list-group-item p-0">
                        <form class="m-0" action="${pageContext.request.contextPath}/controller" method="post">
                            <input type="hidden" name="command" value="add-item-to-cart">
                            <input type="hidden" name="menu-item-id" value="${menuItem.id}">
                            <div class="input-group">
                            <input class="form-control" type="number" name="menu-item-count" value="1" min="1" step="1" required>
                            <input class="btn btn-outline-primary" type="submit" name="submit-button" value="<fmt:message key="action.guest.cart.add-to-cart"/>"/>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </c:forEach>
</div>
<c:import url="pages/footer.jsp"/>
</body>
</html>
