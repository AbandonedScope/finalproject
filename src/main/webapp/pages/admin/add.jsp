<%--
  Created by IntelliJ IDEA.
  User: AbandonedScope
  Date: 19.04.2022
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Title</title>
</head>
<body>
<form action="controller">
<input type="hidden" name="command" value="add-menu-item"/>
    <label>
        Meal name:
        <br/>
        <input type="text" name="name" value="">
    </label>
    <br/>
    <label>
        Meal price:
        <br/>
        <input type="number" name="price" value="">
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
    <br/>
    <input type="submit" name="sub" value="add">
</form>
</body>
</html>
