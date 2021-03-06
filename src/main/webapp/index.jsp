<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/pages/header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.guest.main"/></title>
</head>
<c:set var="sections" value="${requestScope.sections}"/>
<c:if test="${empty sections}">
    <c:redirect url="/controller?command=load-main-page-resources"/>
</c:if>
<body data-bs-spy="scroll" data-bs-target="#navbar-sections" data-bs-offset="150">
<nav id="navbar-sections" class="navbar sticky-top navbar-light bg-light px-3" style="z-index: 1">
    <ul class="nav nav-pills">
        <c:forEach var="sectionId" items="${sections}">
            <li class="nav-item">
                <a class="nav-link" href="#scrollspyHeading${sectionId.id}">${sectionId.name}</a>
            </li>
        </c:forEach>
    </ul>
</nav>
<div>
    <c:forEach var="section" items="${sections}">
        <div id="scrollspyHeading${section.id}" class="row row-cols-4 row-cols-md-4 g-3 mx-5 px-5 my-3">
            <c:forEach var="menuItem" items="${section.items}">
                <div class="col">
                    <div class="card bg-light">
                        <div class="p-3">
                            <a data-bs-toggle="modal"
                               data-bs-target="#item-${menuItem.id}">
                                <img style="user-select: none" src="data:image/png;base64,${menuItem.pictureBase64}"
                                     class="card-img-top"
                                     alt="${menuItem.name}">
                            </a>
                            <div class="modal fade" id="item-${menuItem.id}" tabindex="-1"
                                 aria-labelledby="exampleModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-scrollable modal-xl modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-body row row-cols-2 row-cols-md-2 p-4">
                                            <div class="col">
                                                <img style="user-select: none"
                                                     src="data:image/png;base64,${menuItem.pictureBase64}"
                                                     class="card-img-top"
                                                     alt="${menuItem.name}">
                                            </div>
                                            <div class="col d-flex justify-content-between flex-column">
                                                <div>
                                                    <div>
                                                        <div class="d-flex justify-content-between">
                                                            <div class="d-flex flex-wrap">
                                                                <h4>${menuItem.name}</h4>
                                                            </div>
                                                            <div>
                                                                <button type="button" class="btn-close"
                                                                        data-bs-dismiss="modal"></button>
                                                            </div>
                                                        </div>
                                                        <div class="d-flex flex-wrap">
                                                            <c:forEach var="ingredient" items="${menuItem.ingredients}">
                                                                <div class="d-flex align-items-center text-dark m-1 p-2">
                                                                    <img style="user-select: none; max-width: 40px; max-height: 40px"
                                                                         src="data:image/png;base64,${ingredient.pictureBase64}"
                                                                         alt="${ingredient.name}">
                                                                    <div class="ms-1">
                                                                            ${ingredient.name}
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                    <div>
                                                        <ul class="list-group list-group-flush">
                                                            <li class="list-group-item">
                                                                    <fmt:message key="label.menuItem.calories"/>&ensp;:&emsp;${menuItem.calories}&ensp;<fmt:message
                                                                    key="label.metrics.calories"/>
                                                            </li>
                                                            <li class="list-group-item">
                                                                    <fmt:message key="label.menuItem.proteins"/>&ensp;:&emsp;${menuItem.proteins}&ensp;<fmt:message
                                                                    key="label.metrics.weight"/>
                                                            </li>
                                                            <li class="list-group-item">
                                                                    <fmt:message key="label.menuItem.fats"/>&ensp;:&emsp;${menuItem.fats}&ensp;<fmt:message
                                                                    key="label.metrics.weight"/>
                                                            </li>
                                                            <li class="list-group-item">
                                                                    <fmt:message
                                                                            key="label.menuItem.carbohydrates"/>&ensp;:&emsp;${menuItem.carbohydrates}&ensp;<fmt:message
                                                                    key="label.metrics.weight"/>
                                                            </li>
                                                            <li class="list-group-item">
                                                                    <fmt:message key="label.menuItem.weight"/>&ensp;:&emsp;${menuItem.weight}&ensp;<fmt:message
                                                                    key="label.metrics.weight"/>
                                                            </li>
                                                            <li class="list-group-item">
                                                                    <fmt:message key="label.menuitem.cost"/>&ensp;:&emsp;${menuItem.cost}&ensp;<fmt:message
                                                                    key="label.metrics.currency"/>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-end">
                                                    <form onsubmit="onAddFormSubmitted(event)"
                                                          id="addItemToCartForm-${menuItem.id}" class="m-0"
                                                          action="${pageContext.request.contextPath}/controller"
                                                          method="post">
                                                        <input type="hidden" name="command" value="add-item-to-cart">
                                                        <input type="hidden" name="menu-item-id" value="${menuItem.id}">
                                                        <div class="input-group">
                                                            <input class="form-control" type="number"
                                                                   name="menu-item-count" value="1"
                                                                   min="1"
                                                                   max="10"
                                                                   step="1" required>
                                                            <input class="btn btn-outline-primary text-wrap"
                                                                   type="submit"
                                                                   name="submit-button"
                                                                   value="
                                                                <fmt:message key="action.guest.cart.add-to-cart"/>"/>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card-body p-1 ps-3">
                            <h5 class="card-title m-0 text-truncate">${menuItem.name}</h5>
                        </div>
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item" style="height: 2.5rem">
                                <div class="d-flex justify-content-between">
                                    <p><fmt:message key="label.menuItem.calories"/></p>
                                    <p>${menuItem.calories}&ensp;<fmt:message key="label.metrics.calories"/></p>
                                </div>
                            </li>
                            <li class="list-group-item" style="height: 2.5rem">
                                <div class="d-flex justify-content-between">
                                    <p><fmt:message key="label.menuitem.cost"/></p>
                                    <p>${menuItem.cost}&ensp;<fmt:message key="label.metrics.currency"/></p>
                                </div>
                            </li>
                            <li class="list-group-item p-0">
                                <form onsubmit="onAddFormSubmitted(event)" id="addItemToCartForm-${menuItem.id}"
                                      class="m-0"
                                      action="${pageContext.request.contextPath}/controller"
                                      method="post">
                                    <input type="hidden" name="command" value="add-item-to-cart">
                                    <input type="hidden" name="menu-item-id" value="${menuItem.id}">
                                    <div class="input-group">
                                        <input class="form-control" type="number" name="menu-item-count" value="1"
                                               min="1"
                                               max="10"
                                               step="1" required>
                                        <input style="z-index: 0" class="btn btn-outline-primary text-wrap"
                                               type="submit"
                                               value="<fmt:message key="action.guest.cart.add-to-cart"/>">
                                    </div>
                                </form>
                            </li>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:forEach>
    <div style="z-index: 5000" id="toast" class="toast position-fixed bottom-0 end-0 m-3" role="alert">
        <div class="toast-header">
            <em class="me-2 bi bi-bag-check"></em>
            <strong class="me-auto">
                <ftm:message key="navigation.guest.cart"/>
            </strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div id="toastBody" class="toast-body">
        </div>
    </div>
</div>
<c:import url="pages/footer.jsp"/>
<script>
    const toastElList = document.querySelectorAll('.toast');
    const toastList = [...toastElList].map(toastEl => new bootstrap.Toast(toastEl, {
        autohide: true
    }));
    const url = 'http://localhost:8080/demo1_war_exploded/controller';
    const addItemToCartFormIdPrefix = 'addItemToCartForm-';
    const toastBodyId = 'toastBody';
    const toastId = 'toast';
    const added = '<ftm:message key="message.add-to-cart.success"/>';
    const notAdded = '<ftm:message key="message.add-to-cart.fail"/>';

    const onAddFormSubmitted = async (event) => {
        event.preventDefault();
        let formData = new FormData(event.target);

        fetch(url, {
            method: 'POST',
            credentials: 'include',
            cache: 'no-cache',
            body: formData
        })
            .then(async (response) => {
                const toast = new bootstrap.Toast(document.getElementById(toastId));
                const $divBody = document.getElementById(toastBodyId);
                $divBody.innerText = '';
                if (response.ok) {
                    $divBody.innerText = added;
                } else {
                    $divBody.innerText = notAdded;
                }
                toast.show();
            });

        return false;
    }
</script>
</body>
</html>
