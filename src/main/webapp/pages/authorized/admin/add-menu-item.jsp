<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ParameterType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.menuitem"/></title>
</head>
<body>
<div class="mx-5 mt-2">
    <h2><fmt:message key="navigation.admin.menuitem"/></h2>
    <div class="justify-content-center mx-5">
        <form id="form" action="${pageContext.request.contextPath}/controller" method="post"
              enctype="multipart/form-data">
            <input type="hidden" name="command" value="add-menu-item"/>
            <div class="form-floating my-5">
                <input class="form-control" id="name" type="text" name="menu-item-name" placeholder="Name" required>
                <c:if test="${not empty requestScope.get(ParameterType.MEAL_NAME_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.meal-name"/>
                </c:if>
                <label for="name"><fmt:message key="label.menuitem.name"/></label>
            </div>
            <div class="form-floating my-5">
                <input class="form-control" id="cost" type="number" name="menu-item-cost" min="0.01" step="0.01"
                       placeholder="10" required>
                <c:if test="${not empty requestScope.get(ParameterType.MEAL_COST_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.meal-cost"/>
                </c:if>
                <label for="cost"><fmt:message key="label.menuitem.cost"/></label>
            </div>
            <div class="form-floating my-5">
                <input class="form-control" id="description" type="text" name="menu-item-description" maxlength="600"
                       placeholder="description" required>
                <c:if test="${not empty requestScope.get(ParameterType.MEAL_DESCRIPTION_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.meal-description"/>
                </c:if>
                <label for="description"><fmt:message key="label.menuitem.description"/></label>
            </div>
            <div class="form-floating my-5">
                <select class="form-select" id="section" name="menu-item-section-id" required>
                    <c:forEach var="section" items="${sessionScope.sections}">
                        <option value="${section.id}">${section.name}</option>
                    </c:forEach>
                </select>
                <label for="section"><fmt:message key="label.menuitem.section"/></label>
            </div>
            <div class="col-sm-8 my-5">
                <label for="picture"><fmt:message key="label.menuitem.picture"/></label>
                <input id="picture" class="form-control mb-2" name="menu-item-picture" type="file" accept="image/png"
                       required>
                <p class="mb-2">
                    <fmt:message key="label.menuitem.picture.condition"/>
                </p>
                <p>
                    <c:if test="${not empty requestScope.get(ParameterType.MEAL_PICTURE_VALIDATION_MESSAGE)}">
                        <fmt:message key="message.validation.meal-picture"/>
                    </c:if>
                </p>
            </div>
            <div class="justify-content-evenly my-5" style="display: flex">
                <div>
                    <fmt:message key="label.ingredients"/>
                    <ul class="list-group">
                        <div style="max-height: 250px;
                                    width: 450px;
                                    max-width: 500px;" class="d-inline-block overflow-auto" id="ingredients">
                        </div>
                    </ul>
                </div>
                <div>
                    <fmt:message key="label.menuitem.ingredients"/>
                    <ul class="list-group">
                        <div style="max-height: 250px;
                                    width: 450px;
                                    max-width: 500px;" class="d-inline-block overflow-auto" id="chosen-ingredients">
                        </div>
                    </ul>
                </div>
            </div>
            <div class="d-flex justify-content-end">
                <input class="btn btn-outline-primary fs-5" type="submit" name="sub"
                       value="<fmt:message key="action.admin.add.menuitem"/>">
            </div>
        </form>
        <c:if test="${not empty requestScope.get(ParameterType.MEAL_ADDED_SUCCESSFULLY_MESSAGE)}">
            <fmt:message key="message.add.meal.success"/>
        </c:if>
    </div>
</div>
<script>
    const $form = document.getElementById("form");
    const $list = document.getElementById("ingredients");
    const $chosenList = document.getElementById("chosen-ingredients");
    const ingredients =
    ${sessionScope.ingredients}
    const chosenIngredients = [];

    const createListItemElement = (ingr, name) => {
        const $listItem = document.createElement('li');
        const $image = document.createElement('img');
        const $input = document.createElement('input');

        const imgSize = '30px';
        const imgSrc = "data:image/png;base64," + ingr.pictureBase64;

        $listItem.dataset.ingr = ingr.id;
        $listItem.classList.add('list-group-item', 'd-flex', 'justify-content-between', 'wx-1');

        $image.style.maxWidth = imgSize;
        $image.style.maxHeight = imgSize;
        $image.setAttribute('src', imgSrc);

        $input.setAttribute('type', 'checkbox');
        $input.name = name;
        $input.checked = false;
        $input.readOnly = true;
        $input.onfocus = () => {
            $input.readOnly = false;
        };
        $input.classList.add('ingredient-checkbox', 'form-check-input');

        $listItem.appendChild($image);
        $listItem.append(ingr.name);
        $listItem.appendChild($input);

        return $listItem;
    }

    const createChosenListItemElement = (ingr, name) => {
        const $listItem = createListItemElement(ingr, name);
        const weightInput = document.createElement('input');
        const weightLabel = document.createElement('label')
        const weightDiv = document.createElement('div');
        const idInput = document.createElement('input');

        weightInput.setAttribute('type', 'number');
        weightLabel.setAttribute('for', ingr.id)
        weightLabel.innerText = '<fmt:message key="label.ingredient.weight"/>';
        weightDiv.classList.add('form-floating');
        weightInput.id = ingr.id;
        weightInput.name = 'ingredient-weight';
        weightInput.placeholder = 'Weight';
        weightInput.required = true;
        weightInput.step= '0.01';
        weightInput.min = '0.01';
        weightInput.style.maxWidth = '90px';
        weightInput.style.maxHeight = '30px';
        if (ingr.weight) weightInput.value = ingr.weight;

        idInput.setAttribute('type', 'hidden');
        idInput.name = "ingredient-id";
        idInput.value = ingr.id;

        weightDiv.appendChild(weightInput);
        weightInput.appendChild(weightLabel);
        $listItem.appendChild(weightInput);
        $listItem.appendChild(idInput);

        return $listItem;
    }

    const renderHTMLIngredientList = () => {
        $list.innerHTML = '';
        for (let ingr of ingredients) {
            $list.appendChild(createListItemElement(ingr, 'ingredient'));
        }
    }

    const renderHTMLChosenIngredientList = () => {
        $chosenList.innerHTML = '';
        for (let ingr of chosenIngredients) {
            const $listItem = createChosenListItemElement(ingr, 'chosen-ingredient');

            $listItem.children.namedItem('chosen-ingredient').checked = true;

            $chosenList.appendChild($listItem);
        }

    }

    $list.addEventListener('change', (event) => {
        const ingrId = +event.target.parentElement.dataset.ingr;
        const ingredient = ingredients.find(ingr => ingr.id === ingrId);
        if (event.target.checked) {
            chosenIngredients.unshift(ingredient);
        } else {
            chosenIngredients.splice(chosenIngredients.indexOf(ingredient), 1);
        }

        renderHTMLChosenIngredientList();
    });

    $chosenList.addEventListener('change', (event) => {
        const ingrId = +event.target.parentElement.dataset.ingr;
        const ingredient = ingredients.find(ingr => ingr.id === ingrId);
        if (event.target.type === "checkbox") {
            const $listItem = [...$list.children].find(listItem => listItem.dataset.ingr == ingrId);
            $listItem.children.namedItem('ingredient').checked = false;
            chosenIngredients.splice(chosenIngredients.indexOf(ingredient), 1);
            renderHTMLChosenIngredientList();
        } else {
            if (event.target.value > 0) {
                ingredient.weight = event.target.value;
            }
        }
    });

    window.addEventListener('load', renderHTMLIngredientList);
</script>
<c:import url="../../footer.jsp"/>
</body>
</html>
