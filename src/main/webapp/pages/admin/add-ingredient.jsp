<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 19.04.2022
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>

</head>
<body>

<form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add-ingredient"/>
    <label>
        Ingredient name:
        <br/>
        <input type="text" name="ingredient-name" required>
    </label>
    <br/>
    <label>
        Ingredient calories:
        <br/>
        <input type="number" name="ingredient-calories" step="0.1" min="0" max="5000" required>
    </label>
    <br/>
    <label>
        Ingredient fats:
        <br/>
        <input type="number" name="ingredient-fats" step="0.1" min="0" max="5000" required>
    </label>
    <br/>
    <label>
        Ingredient carbohydrates:
        <br/>
        <input type="number" name="ingredient-carbohydrates" step="0.1" min="0" max="5000" required>
    </label>
    <br/>
    <label>
        Ingredient proteins:
        <br/>
        <input type="number" name="ingredient-proteins" step="0.1" min="0" max="5000" required>
    </label>
    <br/>
    <label>
        Ingredient picture :
        <br/>
        <input name="ingredient-picture" type="file" accept="image/png" required>
        <br/>
        Can be .png and no more than 512*512
    </label>
    <br/>
    <input type="submit" name="sub" value="add">
</form>
${requestScope.ingredient_add_msg}
</body>
</html>
