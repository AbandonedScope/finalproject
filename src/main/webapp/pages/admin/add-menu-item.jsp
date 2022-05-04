<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:choose>
    <c:when test="${not empty language}"><fmt:setLocale value="${language}" scope="session"/></c:when>
    <c:when test="${empty language}"><fmt:setLocale value="${language = 'en_US'}" scope="session"/></c:when>
</c:choose>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.menuitem"/></title>
</head>
<body>
<c:import url="../header.jsp"/>
<form id="form" action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add-menu-item"/>
    <label>
        <fmt:message key="label.menuitem.name"/> :
        <br/>
        <input type="text" name="menu-item-name" required>
    </label>
    <br/>
    <label>
        <fmt:message key="label.menuitem.cost"/> :
        <br/>
        <input type="number" name="menu-item-cost" min="0.01" step="0.01" required>
    </label>
    <br/>
    <label>
        <fmt:message key="label.menuitem.description"/> :
        <br/>
        <input type="text" name="menu-item-description" required>
    </label>
    <br/>
    <label>
        <fmt:message key="label.menuitem.section"/> :
        <br/>
        <select name="menu-item-section-id" required>
            <c:forEach var="section" items="${sessionScope.sections}">
                <option value="${section.id}">${section.name}</option>
            </c:forEach>
        </select>
    </label>
    <br/>
    <label>
        <fmt:message key="label.menuitem.picture"/> :
        <br/>
        <input name="menu-item-picture" type="file" accept="image/png" required>
        <br/>
        <p>
            <fmt:message key="label.menuitem.picture.condition"/>
        </p>
    </label>
    <br/>
    <div style="display: flex">
        <div>
            <fmt:message key="label.ingredients"/> :
            <ul id="ingredients" style="max-height: 200px;
        max-width: 200px;
            overflow-y:auto;
display: block;">
            </ul>
        </div>
        <div>
            <fmt:message key="label.menuitem.ingredients"/> :
            <ul id="chosen-ingredients" style="max-height: 200px;
        max-width: 200px;
            overflow-y:auto;
display: block;">
            </ul>
        </div>
    </div>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.admin.add.menuitem.button.text"/>">
</form>
${sessionScope.menu_item_add_msg}
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
        $input.classList.add('ingredient-checkbox');

        $listItem.appendChild($image);
        $listItem.append(ingr.name);
        $listItem.appendChild($input);

        return $listItem;
    }

    const createChosenListItemElement = (ingr, name) => {
        const $listItem = createListItemElement(ingr, name);
        const weightInput = document.createElement('input');
        const idInput = document.createElement('input');

        weightInput.setAttribute('type', 'number');
        weightInput.name = "ingredient-weight";
        weightInput.required = true;
        if (ingr.weight) weightInput.value = ingr.weight;

        idInput.setAttribute('type', 'hidden');
        idInput.name = "ingredient-id";
        idInput.value = ingr.id;

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
<c:import url="../footer.jsp"/>
</body>
</html>
