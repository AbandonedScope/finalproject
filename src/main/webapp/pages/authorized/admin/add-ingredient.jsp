<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ValidationMessage" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.admin.ingredient"/></title>
</head>
<body>
<div class="mx-5 mt-2">
    <h2><fmt:message key="navigation.admin.ingredient"/></h2>
    <div class="justify-content-center mx-5">
        <form onsubmit="onModifyFormSubmit(event)" action="${pageContext.request.contextPath}/controller" method="post"
              enctype="multipart/form-data">
            <input type="hidden" name="command" value="add-ingredient"/>
            <div class="form-floating my-5">
                <input class="form-control" id="name" type="text" name="ingredient-name" placeholder="Name" required>
                <c:if test="${not empty requestScope.get(ValidationMessage.INGREDIENT_NAME_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.ingredient-name"/>
                </c:if>
                <label for="name">
                    <fmt:message key="label.ingredient.name"/></label>
            </div>
            <div class="form-floating my-5">
                <input class="form-control" id="calories" type="number" name="ingredient-calories" step="0.01" min="0"
                       max="5000" placeholder="100" required>
                <c:if test="${not empty requestScope.get(ValidationMessage.INGREDIENT_CALORIES_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.ingredient-calories"/>
                </c:if>
                <label for="calories">
                    <fmt:message key="label.ingredient.calories"/>
                </label>
            </div>
            <div class="form-floating my-5">
                <input class="form-control" id="proteins" type="number" name="ingredient-proteins" step="0.01" min="0"
                       max="5000" placeholder="20" required>
                <c:if test="${not empty requestScope.get(ValidationMessage.INGREDIENT_PROTEINS_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.ingredient-proteins"/>
                </c:if>
                <label for="proteins">
                    <fmt:message key="label.ingredient.proteins"/>
                </label>
            </div>
            <div class="form-floating my-5">
                <input class="form-control" id="fats" type="number" name="ingredient-fats" step="0.01" min="0"
                       max="5000" placeholder="20" required>
                <c:if test="${not empty requestScope.get(ValidationMessage.INGREDIENT_FATS_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.ingredient-fats"/>
                </c:if>
                <label for="fats">
                    <fmt:message key="label.ingredient.fats"/>
                </label>
            </div>
            <div class="form-floating my-5">
                <input class="form-control" id="carbohydrates" type="number" name="ingredient-carbohydrates" step="0.01"
                       min="0" max="5000" placeholder="20" required>
                <c:if test="${not empty requestScope.get(ValidationMessage.INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.ingredient-carbohydrates"/>
                </c:if>
                <label for="carbohydrates">
                    <fmt:message key="label.ingredient.carbohydrates"/>
                </label>
            </div>
            <div class="my-5">
                <label for="picture">
                    <fmt:message key="label.ingredient.picture"/>
                </label>
                <input class="form-control mb-2" id="picture" name="ingredient-picture" type="file"
                       accept="image/png" required>
                <p class="mb-2">
                    <fmt:message key="label.ingredient.picture.condition"/>
                </p>
                <p>
                    <c:if test="${not empty requestScope.get(ValidationMessage.INGREDIENT_PICTURE_VALIDATION_MESSAGE)}">
                        <fmt:message key="message.validation.ingredient-picture"/>
                    </c:if>
                </p>
            </div>
            <div class="d-flex justify-content-end">
                <input class="btn btn-outline-primary fs-5" type="submit" name="sub"
                       value="<fmt:message key="action.admin.add.ingredient"/>">
            </div>
        </form>
        <jsp:include page="../../modals/addModal.jsp"/>
    </div>
</div>
<c:import url="../../footer.jsp"/>
<script>
    const ingredientPictureImgIdPrefix = 'ingredientPictureImg-';

    const INGREDIENT_NAME_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-name"/>';
    const INGREDIENT_PICTURE_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-picture"/>';
    const INGREDIENT_PROTEINS_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-proteins"/>';
    const INGREDIENT_FATS_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-fats"/>';
    const INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE = '<fmt:message key="message.validation.ingredient-carbohydrates"/>';
    const INGREDIENT_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE = '<fmt:message key="message.validation.ingredient-name.exists"/>';

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
                case 'ingredient-exists':
                    message = INGREDIENT_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE;
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
