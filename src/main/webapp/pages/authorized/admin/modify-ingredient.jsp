<%@ page contentType="text/html;charset=UTF-8" %>
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
                                        <form onsubmit="onModifyFormSubmit(event)" id="ingredientForm-${ingredient.id}"
                                              class="m-0"
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
                                                       max="1000"
                                                       min="0" step="0.01"
                                                       placeholder="10" value="${ingredient.calories}" required>
                                                <label for="ingredientCalories-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.calories"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control" id="ingredientProteins-${ingredient.id}"
                                                       type="number"
                                                       name="ingredient-proteins"
                                                       max="1000"
                                                       min="0" step="0.01"
                                                       placeholder="10" value="${ingredient.proteins}" required>
                                                <label for="ingredientProteins-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.proteins"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control" id="ingredientFats-${ingredient.id}"
                                                       type="number"
                                                       name="ingredient-fats"
                                                       max="1000"
                                                       min="0" step="0.01"
                                                       placeholder="10" value="${ingredient.fats}" required>
                                                <label for="ingredientFats-${ingredient.id}"><fmt:message
                                                        key="label.ingredient.fats"/></label>
                                            </div>
                                            <div class="form-floating my-2">
                                                <input class="form-control"
                                                       id="ingredientCarbohydrates-${ingredient.id}" type="number"
                                                       name="ingredient-carbohydrates"
                                                       max="1000"
                                                       min="0" step="0.01"
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
                                            <input class="btn btn-outline-primary text-wrap" type="submit"
                                                   name="submit-button"
                                                   value="<fmt:message key="action.modify.save"/>"/>
                                        </form>
                                    </div>
                                    <div class="d-flex justify-content-end mt-3">
                                        <form onsubmit="onRemoveFormSubmit(event)"
                                              id="ingredientRemoveForm-${ingredient.id}"
                                              action="${pageContext.request.contextPath}/controller"
                                              method="post">
                                            <input type="hidden" name="ingredient-id" value="${ingredient.id}">
                                            <input type="hidden" name="command" value="remove-ingredient">
                                            <input class="btn btn-outline-danger text-wrap" type="submit"
                                                   name="remove-button"
                                                   value="<fmt:message key="action.modify.delete"/>"/>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="../../modals/modals.jsp"/>
    </c:if>
</div>
<c:import url="../../footer.jsp"/>
<script>
    let currentIngredientId = -1;
    const ingredientPictureImgIdPrefix = 'ingredientPictureImg-';

    const INGREDIENT_NAME_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-name"/>';
    const INGREDIENT_PICTURE_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-picture"/>';
    const INGREDIENT_PROTEINS_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-proteins"/>';
    const INGREDIENT_FATS_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-fats"/>';
    const INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-carbohydrates"/>';

    const changeCurrentIngredientId = function (newId) {
        currentIngredientId = newId;
        console.log(currentIngredientId);
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
</script>
</body>
</html>
