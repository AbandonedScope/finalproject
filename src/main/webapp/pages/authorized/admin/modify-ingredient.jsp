<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.admin.modify.item"/></title>
</head>
<body>
<c:set var="ingredients" value="${requestScope.ingredients}"/>
<div class="m-5">
    <form action="${pageContext.request.contextPath}/controller" method="get">
        <input type="hidden" name="command" value="find-ingredients-by-name">
        <div class="input-group">
            <div class="form-floating flex-fill">
                <input class="form-control" id="ingredientSearchingName" type="text"
                       name="ingredient-name" placeholder="Name"
                       required/>
                <label for="ingredientSearchingName"><fmt:message key="label.ingredient.name"/></label>
            </div>
            <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="action.admin.change.find"/>">
        </div>
    </form>
    <c:if test="${not empty ingredients}">
        <div class="accordion accordion-flush" id="accordionFlushExample">
            <c:forEach var="ingredient" items="${ingredients}">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="flush-headingOne">
                        <button onclick="changeCurrentIngredientId('${ingredient.id}')"
                                class="accordion-button collapsed"
                                type="button"
                                data-bs-toggle="collapse"
                                data-bs-target="#flush-collapse${ingredient.id}" aria-expanded="false"
                                aria-controls="flush-collapseOne">
                                ${ingredient.name}
                        </button>
                    </h2>
                    <div id="flush-collapse${ingredient.id}" class="accordion-collapse collapse"
                         aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                        <div class="accordion-body">
                            <div class="row row-cols-2 p-4">
                                <div class="col">
                                    <img id="ingredientPictureImg-${ingredient.id}" style="user-select: none"
                                         src="data:image/png;base64,${ingredient.pictureBase64}"
                                         class="card-img-top"
                                         alt="${ingredient.name}">
                                </div>
                                <div class="col d-flex flex-column justify-content-between">

                                    <div>
                                        <form id="ingredientForm-${ingredient.id}" class="m-0"
                                              action="${pageContext.request.contextPath}/controller"
                                              method="post" enctype="multipart/form-data">
                                            <input type="hidden" name="command" value="modify-ingredient">
                                            <input type="hidden" name="ingredient-id" value="${ingredient.id}">
                                            <div class="form-floating">
                                                <input class="form-control" id="ingredientName" type="text"
                                                       name="ingredient-name" placeholder="Name"
                                                       value="${ingredient.name}"
                                                       required>
                                                <label for="ingredientName"><fmt:message
                                                        key="label.ingredient.name"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control" id="ingredientCalories-${ingredient.id}"
                                                       type="number"
                                                       name="ingredient-calories"
                                                       min="0.01" step="0.01"
                                                       placeholder="10" value="${ingredient.calories}" required>
                                                <label for="ingredientCalories-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.calories"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control" id="ingredientProteins-${ingredient.id}"
                                                       type="number"
                                                       name="ingredient-proteins"
                                                       min="0.01" step="0.01"
                                                       placeholder="10" value="${ingredient.proteins}" required>
                                                <label for="ingredientProteins-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.proteins"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control" id="ingredientFats-${ingredient.id}"
                                                       type="number"
                                                       name="ingredient-fats"
                                                       min="0.01" step="0.01"
                                                       placeholder="10" value="${ingredient.fats}" required>
                                                <label for="ingredientFats-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.fats"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control"
                                                       id="ingredientCarbohydrates-${ingredient.id}" type="number"
                                                       name="ingredient-carbohydrates"
                                                       min="0.01" step="0.01"
                                                       placeholder="10" value="${ingredient.carbohydrates}" required>
                                                <label for="ingredientCarbohydrates-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.carbohydrates"/></label>
                                            </div>
                                            <div class="my-2">
                                                <label for="menuItemPictureInput-${ingredient.id}">
                                                    <fmt:message key="label.ingredient.picture"/></label>
                                                <input onchange="onloadPictureChange(event)"
                                                       id="ingredientPictureInput-${ingredient.id}"
                                                       class="form-control mb-2"
                                                       name="ingredient-picture" type="file" accept="image/png">
                                                <p class="mb-2">
                                                    <fmt:message key="label.ingredient.picture.condition"/>
                                                </p>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="d-flex justify-content-evenly mt-3">
                                        <form id="ingredientRemoveForm-${ingredient.id}"
                                              action="${pageContext.request.contextPath}/controller"
                                              method="post">
                                            <input type="hidden" name="ingredient-id" value="${ingredient.id}">
                                            <input type="hidden" name="command" value="remove-ingredient">
                                        </form>
                                        <button onclick="onRemoveButtonPressed()"
                                                class="btn btn-outline-danger text-wrap" type="submit"
                                                name="remove-button">
                                            <fmt:message key="action.modify.delete"/>
                                        </button>
                                        <button onclick="onModifyButtonPressed()"
                                                class="btn btn-outline-primary text-wrap" type="submit"
                                                name="submit-button">
                                            <fmt:message key="action.modify.save"/>
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <div class="modal fade" id="validationModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            <fmt:message key="message.validation"/>
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div id="validationModalBody" class="modal-body">
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="successModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            <fmt:message key="message.success"/>
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div id="successModalBody" class="modal-body">
                        <fmt:message key="message.success.modifying"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="removeModal" tabindex="-1">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="removeModelHeader">
                        </h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div id="removeModelBody" class="modal-body">
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<c:import url="../../footer.jsp"/>
<script>
    const url = 'http://localhost:8080/demo1_war_exploded/controller';
    let currentIngredientId = -1;
    const ingredientPictureImgIdPrefix = 'ingredientPictureImg-';
    const ingredientFormIdPrefix = 'ingredientForm-';
    const ingredientRemoveFormIdPrefix = 'ingredientRemoveForm-';
    const validationModalId = 'validationModal';
    const successModalId = 'successModal';
    const removeModalId = 'removeModal';
    const removeModalHeaderId = 'removeModelHeader';
    const removeModalBodyId = 'removeModelBody';
    const validationModalBodyId = 'validationModalBody';

    const success = '<fmt:message key="message.success"/>';
    const fail = '<fmt:message key="message.fail"/>';
    const removeFail = '<fmt:message key="message.fail.remove"/>';
    const removeSuccess = '<fmt:message key="message.success.remove"/>';

    const INGREDIENT_NAME_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-name"/>';
    const INGREDIENT_PICTURE_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-picture"/>';
    const INGREDIENT_PROTEINS_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-proteins"/>';
    const INGREDIENT_FATS_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-fats"/>';
    const INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-carbohydrates"/>';

    const changeCurrentIngredientId = function (newId) {
        currentIngredientId = newId;
    }

    const onloadPictureChange = function (event) {
        let image = document.getElementById(ingredientPictureImgIdPrefix + currentIngredientId);
        image.src = URL.createObjectURL(event.target.files[0]);
    };

    const fillModalWithValidations = (validations) => {
        const $modalBody = document.getElementById(validationModalBodyId);
        $modalBody.innerText = '';
        const $validationsDiv = document.createElement("div");
        for (let validationMessage of validations) {
            let message;
            switch (validationMessage) {
                case 'ingredient-name':
                    message = INGREDIENT_NAME_VALIDATION_MESSAGE;
                    break;
                case 'ingredient-picture':
                    message = INGREDIENT_PICTURE_VALIDATION_MESSAGE;
                    break;
                case 'ingredient-proteins':
                    message = INGREDIENT_PROTEINS_VALIDATION_MESSAGE;
                    break;
                case 'ingredient-fats':
                    message = INGREDIENT_FATS_VALIDATION_MESSAGE;
                    break;
                case 'ingredient-carbohydrates':
                    message = INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE;
                    break;
            }
            const $validationDiv = document.createElement("div");
            $validationDiv.innerText = message;
            $validationsDiv.append($validationDiv);
        }
        $modalBody.append($validationsDiv);
    }

    const onRemoveButtonPressed = async () => {
        let $form = document.getElementById(ingredientRemoveFormIdPrefix + currentIngredientId);
        let formData = new FormData($form);
        fetch(url, {
            method: 'POST',
            credentials: 'include',
            cache: 'no-cache',
            body: formData
        })
            .then(async (response) => {
                let $removalModalHeader = document.getElementById(removeModalHeaderId);
                let $removalModalBody = document.getElementById(removeModalBodyId);
                $removalModalHeader.innerText = '';
                $removalModalBody.innerText = '';
                if (response.ok) {
                    $removalModalHeader.innerText = success;
                    $removalModalBody.innerText = removeSuccess;
                } else {
                    $removalModalHeader.innerText = fail;
                    $removalModalBody.innerText = removeFail;
                }
                const modal = new bootstrap.Modal(document.getElementById(removeModalId));
                modal.show();
            });
    }

    const onModifyButtonPressed = async () => {
        let $form = document.getElementById(ingredientFormIdPrefix + currentIngredientId);
        let formData = new FormData($form);
        fetch(url, {
            method: 'POST',
            credentials: 'include',
            cache: 'no-cache',
            body: formData
        })
            .then(async (response) => {
                if (response.ok) {
                    const modal = new bootstrap.Modal(document.getElementById(successModalId));
                    modal.show();
                } else if (response.status === 400) {
                    let data = await response.json();
                    if (data.validation_msg) {
                        fillModalWithValidations(data.validation_msg);
                        const modal = new bootstrap.Modal(document.getElementById(validationModalId));
                        modal.show();
                    }
                }
            });
    }
</script>
</body>
</html>
