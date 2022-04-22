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
        <input type="text" name="ingredient-name" value="" required>
    </label>
    <br/>
    <label>
        Ingredient calories:
        <br/>
        <input type="number" name="ingredient-calories" value="" step="0.1" min="0" required>
    </label>
    <br/>
    <label>
        Ingredient fats:
        <br/>
        <input type="number" name="ingredient-fats" value="" step="0.1" min="0" required>
    </label>
    <br/>
    <label>
        Ingredient carbohydrates:
        <br/>
        <input type="number" name="ingredient-carbohydrates" value="" step="0.1" min="0" required>
    </label>
    <br/>
    <label>
        Ingredient proteins:
        <br/>
        <input type="number" name="ingredient-proteins" value="" step="0.1" min="0" required>
    </label>
    <br/>
    <label>
        Ingredient picture :
        <br/>
        <input name="ingredient-picture" type="file" accept="image/jpeg,image/png,image/jpg">
    </label>
    <br/>
    <input type="submit" name="sub" value="add">
</form>
${ingredient-add-msg}
</body>
</html>
