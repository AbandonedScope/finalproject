<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ParameterType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.admin.modify"/></title>
</head>
<body>
<c:set var="items" value="${requestScope.menuItems}"/>
<c:set var="existingIngredients" value="${requestScope.ingredients}"/>
<div class="m-5">
    <form action="${pageContext.request.contextPath}/controller" method="get">
        <input type="hidden" name="command" value="find-menu-items-by-name">
        <input name="menu-item-name" type="text" required>
        <input type="submit">
    </form>
    <c:if test="${not empty items}">
    <div class="accordion accordion-flush" id="accordionFlushExample">
        <c:forEach var="item" items="${items}">
            <div class="accordion-item">
                <h2 class="accordion-header" id="flush-headingOne">
                    <button onclick="changeCurrentItemId('${item.id}')" class="accordion-button collapsed" type="button"
                            data-bs-toggle="collapse"
                            data-bs-target="#flush-collapse${item.id}" aria-expanded="false"
                            aria-controls="flush-collapseOne">
                            ${item.name}
                    </button>
                </h2>
                <div id="flush-collapse${item.id}" class="accordion-collapse collapse"
                     aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                    <div class="accordion-body">
                        <div class="row row-cols-2 p-4">
                            <div class="col">
                                <img style="user-select: none"
                                     src="data:image/png;base64,${item.pictureBase64}"
                                     class="card-img-top"
                                     alt="${item.name}">
                            </div>
                            <div class="col d-flex flex-column justify-content-between">
                                <form class="m-0" action="${pageContext.request.contextPath}/controller"
                                      method="post">
                                    <input type="hidden" name="command" value="modify-item">
                                    <input type="hidden" name="menu-item-id" value="${item.id}">
                                    <div>
                                        <div>
                                            <div class="form-floating">
                                                <input class="form-control" id="mealName" type="text"
                                                       name="menu-item-name" placeholder="Name" value="${item.name}"
                                                       required>
                                                <c:if test="${not empty requestScope.get(ParameterType.MEAL_NAME_VALIDATION_MESSAGE)}">
                                                    <fmt:message key="message.validation.meal-name"/>
                                                </c:if>
                                                <label for="mealName"><fmt:message key="label.menuitem.name"/></label>
                                            </div>
                                            <div id="menuItemIngredients-${item.id}" class="d-flex flex-wrap">
                                                <c:forEach var="ingredient" items="${item.ingredients}">
                                                    <div id="itemIngredientDiv-${item.id}-${ingredient.id}"
                                                         class="w-25 d-flex flex-column justify-content-between p-2">
                                                        <input type="hidden" name="ingredient-id"
                                                               value="${ingredient.id}"/>
                                                        <div class="d-flex flex-wrap align-items-center text-dark m-1 p-2">
                                                            <span class="input-group-addon">
                                                                <img style="user-select: none; max-width: 40px; max-height: 40px"
                                                                     src="data:image/png;base64,${ingredient.pictureBase64}"
                                                                     alt="${ingredient.name}">
                                                            </span>
                                                            <div class="input-group-addon ms-1 align-middle">
                                                                    ${ingredient.name}
                                                            </div>
                                                        </div>
                                                        <div class="d-flex align-items-end flex-column">
                                                            <div class="form-floating">
                                                                <input id="ingredient-${ingredient.id}" type="number"
                                                                       class="form-control"
                                                                       name="ingredient-weight"
                                                                       value="${ingredient.weight}" placeholder="Weight"
                                                                       min="0.01" step="0.01" required>
                                                                <label for="ingredient-${ingredient.id}"><fmt:message
                                                                        key="label.ingredient.weight"/></label>
                                                            </div>
                                                            <button onclick="deleteIngredient('itemIngredientDiv-${item.id}-${ingredient.id}')"
                                                                    class="btn btn-outline-danger mt-1 p-1"
                                                                    type="button">
                                                                <em class="bi bi-trash"></em>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </c:forEach>
                                            </div>
                                            <div class="d-flex p-2">
                                                <button type="button" class="btn btn-outline-primary ms-auto p-2"
                                                        data-bs-toggle="modal"
                                                        data-bs-target="#ingredientModal">
                                                    <em class="bi bi-plus-lg fw-bold"></em>
                                                </button>
                                            </div>
                                        </div>
                                        <div class="form-floating my-2">
                                            <input class="form-control" id="cost" type="number" name="menu-item-cost"
                                                   min="0.01" step="0.01"
                                                   placeholder="10" value="${item.cost}" required>
                                            <c:if test="${not empty requestScope.get(ParameterType.MEAL_COST_VALIDATION_MESSAGE)}">
                                                <fmt:message key="message.validation.meal-cost"/>
                                            </c:if>
                                            <label for="cost"><fmt:message key="label.menuitem.cost"/></label>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-evenly mt-3">
                                        <form>
                                            <input class="btn btn-outline-danger text-wrap" type="submit"
                                                   name="submit-button"
                                                   value="<fmt:message key="action.modify.delete"/>"/>
                                        </form>
                                        <input class="btn btn-outline-primary text-wrap" type="submit"
                                               name="submit-button"
                                               value="<fmt:message key="action.modify.save"/>"/>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="ingredientModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="modal-ingredients">
                        <c:forEach var="ingredient" items="${existingIngredients}">
                            <div class="d-flex justify-content-between">
                                <div class="d-flex flex-wrap align-items-center text-dark m-1 p-2">
                                    <span class="input-group-addon">
                                        <img style="user-select: none; max-width: 40px; max-height: 40px"
                                             src="data:image/png;base64,${ingredient.pictureBase64}"
                                             alt="${ingredient.name}">
                                    </span>
                                    <div class="input-group-addon ms-1 align-middle">
                                            ${ingredient.name}
                                    </div>
                                </div>
                                <div>
                                    <button onclick="addNewIngredient(event, '${ingredient.id}', '${ingredient.name}', '${ingredient.pictureBase64}')" id="existingIngredientButton-${ingredient.id}" type="button" class="btn btn-outline-primary ms-auto p-2">
                                        <em class="bi bi-plus-lg fw-bold"></em>
                                    </button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline-secondary fs-5" data-bs-dismiss="modal">
                        <fmt:message key="cart.order-modal.close"/></button>
                    <button type="submit" id="confirm" class="btn btn-outline-success fs-5"
                            data-bs-dismiss="modal"
                            disabled><fmt:message
                            key="cart.order-modal.confirm"/></button>
                </div>
            </div>
        </div>
    </div>
    </c:if>
    <c:import url="../../footer.jsp"/>
    <script>
        const $modal = document.getElementById('ingredientModal');
        const delimiter = '-';
        let currentMenuItemId = -1;
        const menuItemIngredientsContainerIdPrefix = 'menuItemIngredients-';
        const menuItemIngredientIdPrefix = 'itemIngredientDiv-';
        const existingIngredientButtonIdPrefix = 'existingIngredientButton-';
        const deleteIngredient = function (removeId) {
            const ingredientDiv = document.getElementById(removeId);
            ingredientDiv.remove();
        }

        const changeCurrentItemId = function (newId) {
            currentMenuItemId = newId;
        }

        const addNewIngredient = function (event, ingredientId, ingredientName, ingredientPicture) {
            const $ingrDiv = document.createElement('div');
            const $ingrIdInput = document.createElement('input');
            const $imgDiv = document.createElement('div');
            const $imgSpan = document.createElement('span');
            const $img = document.createElement('img')
            $ingrDiv.id = menuItemIngredientIdPrefix+currentMenuItemId+ingredientId;
            $ingrDiv.className = 'w-25 d-flex flex-column justify-content-between p-2';
            $ingrIdInput.type = 'hidden';
            $ingrIdInput.name = 'ingredient-id';
            $ingrIdInput.value = ingredientId;
            $ingrDiv.appendChild($ingrIdInput);
            $imgDiv.className = 'd-flex flex-wrap align-items-center text-dark m-1 p-2';
            $imgSpan.className = 'input-group-addon';
            $img.style.userSelect = 'none';
            $img.style.maxWidth = '40px';
            $img.style.maxHeight = '40px';
            $img.src = 'data:image/png;base64,' + ingredientPicture;
            $img.alt = ingredientName;
            $imgSpan.appendChild($img);
            $imgDiv.appendChild($imgSpan);
            const $button = event.target();
            $button.className = 'btn btn-outline-danger ms-auto p-2';
            const $em = $button.children[0];
            $em.className = 'bi bi-dash-lg fw-bold';
        }

        const openingIngredientListElements = function () {
            console.log('open');
            const menuItem = document.getElementById(menuItemIngredientsContainerIdPrefix + currentMenuItemId)
            for (const ingredient of menuItem.children) {
                const ingredientId = ingredient.id.split('-')[2];
                const $button = document.getElementById(existingIngredientButtonIdPrefix + ingredientId);
                $button.className = 'btn btn-outline-danger ms-auto p-2';
                const $em = $button.children[0];
                $em.className = 'bi bi-dash-lg fw-bold';
            }
        }

        const closingIngredientListElements = function () {
            console.log('close');
            const menuItem = document.getElementById(menuItemIngredientsContainerIdPrefix + currentMenuItemId)
            for (const ingredient of menuItem.children) {
                const ingredientId = ingredient.id.split('-')[2];
                const $button = document.getElementById(existingIngredientButtonIdPrefix + ingredientId);
                $button.className = 'btn-outline-primary ms-auto p-2';
                const $em = $button.children[0];
                $em.className = 'bi bi-plus-lg fw-bold';
            }
        }

        $modal.addEventListener('shown.bs.modal', openingIngredientListElements);
        $modal.addEventListener('hidden.bs.modal', closingIngredientListElements);
    </script>
</body>
</html>
