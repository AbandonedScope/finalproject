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
<form action="controller" method="post" enctype="multipart/form-data">
    <input type="hidden" name="command" value="add-menu-item"/>
    <label>
        Meal name:
        <br/>
        <input type="text" name="name" value="">
    </label>
    <br/>
    <label>
        Meal cost:
        <br/>
        <input type="number" name="cost" value="">
    </label>
    <br/>
    <label>
        Meal cal:
        <br/>
        <input type="number" name="cal-amount" value="">
    </label>
    <br/>
    <label>
        Meal fats:
        <br/>
        <input type="number" name="fats-amount" value="">
    </label>
    <br/>
    <label>
        Meal carbonates:
        <br/>
        <input type="number" name="carbonates-amount" value="">
    </label>
    <br/>
    <label>
        Meal proteins:
        <br/>
        <input type="number" name="proteins-amount" value="">
    </label>
    <br/>
    <label>
        Meal weight:
        <br/>
        <input type="number" name="weight" value="">
    </label>
    <label>
        Photo :
        <br/>
        <input type="file" accept="image/jpeg,image/png,image/jpg">
    </label>
    <br/>
    <label>
        Ingredients :
        <ul>
            <c:forEach var="ingredient" items="${requestScope.ingredients}">
                <li>
                    <div>
                        <label>
                                ${ingredient.name}
                            <input type="checkbox" name="ingredient">
                        </label>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </label>
    <br/>
    <input type="submit" name="sub" value="add">
</form>
</body>
</html>
