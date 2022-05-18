<%@ page contentType="text/html;charset=UTF-8"
import="com.mahanko.finalproject.controller.ParameterType" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <title><fmt:message key="navigation.admin.ingredient"/></title>

</head>
<body>
<c:import url="../../header.jsp"/>
<form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add-ingredient"/>
    <label>
        <fmt:message key="label.ingredient.name"/> :
        <br/>
        <input type="text" name="ingredient-name" required>
        <c:if test="${not empty requestScope.get(ParameterType.INGREDIENT_NAME_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.ingredient-name"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.ingredient.calories"/> :
        <br/>
        <input type="number" name="ingredient-calories" step="0.1" min="0" max="5000" required>
        <c:if test="${not empty requestScope.get(ParameterType.INGREDIENT_CALORIES_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.ingredient-calories"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.ingredient.proteins"/> :
        <br/>
        <input type="number" name="ingredient-proteins" step="0.1" min="0" max="5000" required>
        <c:if test="${not empty requestScope.get(ParameterType.INGREDIENT_PROTEINS_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.ingredient-proteins"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.ingredient.fats"/> :
        <br/>
        <input type="number" name="ingredient-fats" step="0.1" min="0" max="5000" required>
        <c:if test="${not empty requestScope.get(ParameterType.INGREDIENT_FATS_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.ingredient-fats"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.ingredient.carbohydrates"/> :
        <br/>
        <input type="number" name="ingredient-carbohydrates" step="0.1" min="0" max="5000" required>
        <c:if test="${not empty requestScope.get(ParameterType.INGREDIENT_CARBOHYDRATES_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.ingredient-carbohydrates"/>
        </c:if>
    </label>
    <br/>
    <label>
        <fmt:message key="label.ingredient.picture"/> :
        <br/>
        <input name="ingredient-picture" type="file" accept="image/png" required>
        <br/>
        <p>
            <fmt:message key="label.ingredient.picture.condition"/>
        </p>
        <c:if test="${not empty requestScope.get(ParameterType.INGREDIENT_PICTURE_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.ingredient-picture"/>
        </c:if>
    </label>
    <br/>
    <input type="submit" name="sub" value="<fmt:message key="action.admin.add.ingredient"/>">
</form>
${requestScope.ingredient_add_msg}
<c:import url="../../footer.jsp"/>
</body>
</html>
