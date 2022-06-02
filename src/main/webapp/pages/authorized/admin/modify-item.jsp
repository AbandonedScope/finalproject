<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ParameterType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.admin.modify.item"/></title>
</head>
<body>
<c:set var="items" value="${requestScope.menuItems}"/>
<c:set var="existingIngredients" value="${requestScope.ingredients}"/>
<c:set var="sections" value="${requestScope.sections}"/>
<div class="m-5">
    <form action="${pageContext.request.contextPath}/controller" method="get">
        <input type="hidden" name="command" value="find-menu-items-by-name">
        <div class="input-group">
            <div class="form-floating flex-fill">
                <input class="form-control" id="mealSearchingName" type="text"
                       name="menu-item-name" placeholder="Name"
                       required>
                <label for="mealSearchingName"><fmt:message key="label.menuitem.name"/></label>
            </div>
            <input class="btn btn-outline-primary" type="submit">
        </div>
    </form>
    <c:if test="${not empty items}">
        <div class="accordion accordion-flush" id="accordionFlushExample">
            <c:forEach var="item" items="${items}">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="flush-headingOne">
                        <button onclick="changeCurrentItemId('${item.id}')" class="accordion-button collapsed"
                                type="button"
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
                                    <img id="menuItemPictureImg-${item.id}" style="user-select: none"
                                         src="data:image/png;base64,${item.pictureBase64}"
                                         class="card-img-top"
                                         alt="${item.name}">
                                </div>
                                <div class="col d-flex flex-column justify-content-between">
                                    <form class="m-0" action="${pageContext.request.contextPath}/controller"
                                          method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="command" value="modify-menu-item">
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
                                                    <label for="mealName"><fmt:message
                                                            key="label.menuitem.name"/></label>
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
                                                                    <input id="ingredient-${ingredient.id}"
                                                                           type="number"
                                                                           class="form-control"
                                                                           name="ingredient-weight"
                                                                           value="${ingredient.weight}"
                                                                           placeholder="Weight"
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
                                            <div class="my-2">
                                                <div class="form-floating my-5">
                                                    <select class="form-select" id="sectionId"
                                                            name="menu-item-section-id"
                                                            required>
                                                        <c:forEach var="sectionId" items="${sections}">
                                                            <c:choose>
                                                                <c:when test="${sectionId.id == item.sectionId}">
                                                                    <option value="${sectionId.id}"
                                                                            selected>${sectionId.name}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${sectionId.id}">${sectionId.name}</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                    <label for="sectionId"><fmt:message
                                                            key="label.menuitem.sectionId"/></label>
                                                </div>
                                                <label for="menuItemPictureInput-${item.id}"><fmt:message
                                                        key="label.menuitem.picture"/></label>
                                                <input onchange="onloadPictureChange(event)"
                                                       id="menuItemPictureInput-${item.id}" class="form-control mb-2"
                                                       name="menu-item-picture" type="file" accept="image/png">
                                                <p class="mb-2">
                                                    <fmt:message key="label.menuitem.picture.condition"/>
                                                </p>
                                                <p>
                                                    <c:if test="${not empty requestScope.get(ParameterType.MEAL_PICTURE_VALIDATION_MESSAGE)}">
                                                        <fmt:message key="message.validation.meal-picture"/>
                                                    </c:if>
                                                </p>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control" id="cost" type="number"
                                                       name="menu-item-cost"
                                                       min="0.01" step="0.01"
                                                       placeholder="10" value="${item.cost}" required>
                                                <c:if test="${not empty requestScope.get(ParameterType.MEAL_COST_VALIDATION_MESSAGE)}">
                                                    <fmt:message key="message.validation.meal-cost"/>
                                                </c:if>
                                                <label for="cost"><fmt:message key="label.menuitem.cost"/></label>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-evenly mt-3">
                                            <form action="${pageContext.request.contextPath}/controller"
                                                  method="post">
                                                <input type="hidden" name="command" value="delete-item">
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
        <div class="modal fade" id="ingredientModal" tabindex="-1" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header d-flex">
                        <div class="form-floating flex-fill">
                            <input oninput="searchingByName()" class="form-control" id="searching-ingredient-name-input"
                                   type="text" name="searching-ingredient-name"
                                   placeholder="Name" required>
                            <label for="searching-ingredient-name-input"><fmt:message
                                    key="label.ingredient.searching-name"/></label>
                        </div>
                        <button type="button" class="btn-close ms-1" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body overflow-auto" style="height: 500px">
                        <div id="modal-ingredients">
                            <c:forEach var="ingredient" items="${existingIngredients}">
                                <div id="existingIngredientDiv-${ingredient.id}"
                                     class="d-flex justify-content-between existingIngredient">
                                    <div class="d-flex flex-wrap align-items-center text-dark m-1 p-2">
                                    <span class="input-group-addon">
                                        <img style="user-select: none; max-width: 40px; max-height: 40px"
                                             src="data:image/png;base64,${ingredient.pictureBase64}"
                                             alt="${ingredient.name}">
                                        <p id="existingIngredientPictureP-${ingredient.id}"
                                           class="d-none">${ingredient.pictureBase64}</p>
                                    </span>
                                        <div id="existingIngredientNameDiv-${ingredient.id}"
                                             class="input-group-addon ms-1 align-middle">
                                                ${ingredient.name}
                                        </div>
                                    </div>
                                    <div>
                                        <button onclick="addNewIngredient(event, '${ingredient.id}', '${ingredient.name}', '${ingredient.pictureBase64}')"
                                                id="existingIngredientButton-${ingredient.id}" type="button"
                                                class="btn btn-outline-primary ms-auto p-2">
                                            <em class="bi bi-plus-lg fw-bold"></em>
                                        </button>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<script>
    const $modal = document.getElementById('ingredientModal');
    const delimiter = '-';
    let currentMenuItemId = -1;
    const menuItemPictureImgIdPrefix = 'menuItemPictureImg-';
    const menuItemIngredientsContainerIdPrefix = 'menuItemIngredients-';
    const menuItemIngredientIdPrefix = 'itemIngredientDiv-';
    const existingIngredientButtonIdPrefix = 'existingIngredientButton-';
    const existingIngredientNameDivIdPrefix = 'existingIngredientNameDiv-';
    const existingIngredientPicturePIdPrefix = 'existingIngredientPictureP-';
    const existingIngredientDivIdPrefix = 'existingIngredientDiv-';

    const deleteIngredient = function (removeId) {
        const ingredientDiv = document.getElementById(removeId);
        ingredientDiv.remove();
    }

    const changeCurrentItemId = function (newId) {
        currentMenuItemId = newId;
    }

    const addNewIngredient = function (event, ingredientId, ingredientName, ingredientPicture) {
        const $itemIngredientsDiv = document.getElementById(menuItemIngredientsContainerIdPrefix + currentMenuItemId);
        const $ingrDiv = document.createElement('div');
        const $ingrIdInput = document.createElement('input');
        const $imgDiv = document.createElement('div');
        const $imgSpan = document.createElement('span');
        const $img = document.createElement('img')
        const $ingrNameDiv = document.createElement('div');
        const $ingrFooterDiv = document.createElement('div');
        const $ingrWeightDiv = document.createElement('div');
        const $weightInput = document.createElement('input');
        const $weightInputLabel = document.createElement('label');
        const $ingrDeleteButton = document.createElement('button');
        const $emImage = document.createElement('em');
        $ingrDiv.id = menuItemIngredientIdPrefix + currentMenuItemId + delimiter + ingredientId;
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
        $ingrNameDiv.className = 'input-group-addon ms-1 align-middle';
        $ingrNameDiv.innerText = ingredientName;
        $imgDiv.appendChild($ingrNameDiv);
        $ingrDiv.appendChild($imgDiv);
        $ingrFooterDiv.className = 'd-flex align-items-end flex-column';
        $ingrWeightDiv.className = 'form-floating';
        $weightInput.id = 'ingredient-' + ingredientId;
        $weightInput.type = 'number';
        $weightInput.className = 'form-control';
        $weightInput.name = 'ingredient-weight';
        $weightInput.placeholder = "Weight";
        $weightInput.min = '0.01';
        $weightInput.step = '0.01';
        $weightInput.required = true;
        $ingrWeightDiv.appendChild($weightInput);
        $weightInputLabel.setAttribute('for', 'ingredient-' + ingredientId);
        $weightInputLabel.innerText = '<fmt:message key="label.ingredient.weight"/>';
        $ingrWeightDiv.appendChild($weightInputLabel);
        $ingrFooterDiv.appendChild($ingrWeightDiv);
        $ingrDeleteButton.type = 'button';
        $ingrDeleteButton.className = 'btn btn-outline-danger mt-1 p-1';
        $ingrDeleteButton.setAttribute('onclick', 'deleteIngredient(\'' + $ingrDiv.id + '\')');
        $emImage.className = 'bi bi-trash';
        $ingrDeleteButton.appendChild($emImage);
        $ingrFooterDiv.appendChild($ingrDeleteButton);
        $ingrDiv.appendChild($ingrFooterDiv);
        $itemIngredientsDiv.appendChild($ingrDiv);
        let $button = event.target;
        let $em;
        if (event.target.tagName.toLowerCase() === 'button') {
            $em = $button.children[0];
        } else {
            $em = $button;
            $button = $em.parentElement;
        }
        $button.setAttribute('onclick', 'minusIngredient(event)');
        $button.className = 'btn btn-outline-danger ms-auto p-2';
        $em.className = 'bi bi-dash-lg fw-bold';
    }

    const minusIngredient = function (event) {
        let $button = event.target;
        let $em;
        if (event.target.tagName.toLowerCase() === 'button') {
            $em = $button.children[0];
        } else {
            $em = $button;
            $button = $em.parentElement;
        }
        const ingredientId = $button.id.split(delimiter)[1];
        const removalId = menuItemIngredientIdPrefix + currentMenuItemId + delimiter + ingredientId;
        deleteIngredient(removalId);
        const ingredientName = document.getElementById(existingIngredientNameDivIdPrefix + ingredientId).innerText;
        const pictureBase64 = document.getElementById(existingIngredientPicturePIdPrefix + ingredientId).innerText;
        $button.setAttribute('onclick', "addNewIngredient(event, '" + ingredientId + "', '" + ingredientName + "', '" + pictureBase64 + "')");
        $button.className = 'btn btn-outline-primary ms-auto p-2';
        $em.className = 'bi bi-plus-lg fw-bold';
    }

    const openingIngredientListElements = function () {
        const menuItem = document.getElementById(menuItemIngredientsContainerIdPrefix + currentMenuItemId)
        for (const ingredient of menuItem.children) {
            const ingredientId = ingredient.id.split(delimiter)[2];
            const $button = document.getElementById(existingIngredientButtonIdPrefix + ingredientId);
            $button.setAttribute('onclick', 'minusIngredient(event)');
            $button.className = 'btn btn-outline-danger ms-auto p-2';
            const $em = $button.children[0];
            $em.className = 'bi bi-dash-lg fw-bold';
        }
    }

    const closingIngredientListElements = function () {
        const menuItem = document.getElementById(menuItemIngredientsContainerIdPrefix + currentMenuItemId)
        for (const ingredient of menuItem.children) {
            const ingredientId = ingredient.id.split('-')[2];
            const $button = document.getElementById(existingIngredientButtonIdPrefix + ingredientId);
            const ingredientName = document.getElementById(existingIngredientNameDivIdPrefix + ingredientId).innerText.trim();
            const pictureBase64 = document.getElementById(existingIngredientPicturePIdPrefix + ingredientId).innerText.trim();
            $button.setAttribute('onclick', "addNewIngredient(event, '" + ingredientId + "', '" + ingredientName + "', '" + pictureBase64 + "')");
            $button.className = 'btn btn-outline-primary ms-auto p-2';
            const $em = $button.children[0];
            $em.className = 'bi bi-plus-lg fw-bold';
        }
        const existingList = document.getElementsByClassName('existingIngredient');
        for (const existingIngredient of existingList) {
            if (existingIngredient.classList.contains('d-none')) {
                existingIngredient.classList.replace('d-none', 'd-flex');
            }
        }
    }

    const searchingByName = function () {
        const $searchingInput = document.getElementById('searching-ingredient-name-input');
        const name = $searchingInput.value;
        const existingList = document.getElementsByClassName('existingIngredient');
        for (const existingIngredient of existingList) {
            if (name === '' || name == null) {
                if (existingIngredient.classList.contains('d-none')) {
                    existingIngredient.classList.replace('d-none', 'd-flex');
                }
            } else {
                const ingredientId = existingIngredient.id.split(delimiter)[1];
                const ingredientName = document.getElementById(existingIngredientNameDivIdPrefix + ingredientId).innerText.trim();
                if (ingredientName.toLowerCase().includes(name.toLowerCase(), 0)) {
                    if (existingIngredient.classList.contains('d-none')) {
                        existingIngredient.classList.replace('d-none', 'd-flex');
                    }
                } else {
                    if (existingIngredient.classList.contains('d-flex')) {
                        existingIngredient.classList.replace('d-flex', 'd-none');
                    }
                }
            }
        }
    }

    const onloadPictureChange = function (event) {
        let image = document.getElementById(menuItemPictureImgIdPrefix + currentMenuItemId);
        image.src = URL.createObjectURL(event.target.files[0]);
    };

    $modal.addEventListener('shown.bs.modal', openingIngredientListElements);
    $modal.addEventListener('hidden.bs.modal', closingIngredientListElements);
</script>
<c:import url="../../footer.jsp"/>
</body>
</html>
