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
        <input type="text" name="menu-item-name" value="">
    </label>
    <br/>
    <label>
        Meal cost:
        <br/>
        <input type="number" name="menu-item-cost" value="">
    </label>
    <br/>
    <label>
        Meal cal:
        <br/>
        <input type="number" name="menu-item-calories" value="">
    </label>
    <br/>
    <label>
        Meal fats:
        <br/>
        <input type="number" name="menu-item-fats" value="">
    </label>
    <br/>
    <label>
        Meal proteins:
        <br/>
        <input type="number" name="menu-item-proteins" value="">
    </label>
    <br/>
    <label>
        Meal carbohydrates:
        <br/>
        <input type="number" name="menu-item-carbohydrates" value="">
    </label>
    <br/>
    <label>
        Photo :
        <br/>
        <input type="file" accept="image/jpeg,image/png,image/jpg">
    </label>
    <br/>
    <div style="display: flex">
        <div>
            Ingredients :
            <ul id="ingredients" style="max-height: 200px;
        max-width: 200px;
            overflow-y:auto;
display: block;">
                <c:forEach var="ingredient" items="${requestScope.ingredients}">
                    <li>
                        <img id="ingredient-image" style="max-width: 30px;
max-height: 30px" src="data:image/png;base64, ${ingredient.getPicture()}" alt="">
                            ${ingredient.name}
                        <input type="checkbox" name="ingredient">
                        <input class="weight_option" style="display: none;" type="number" name="weight">
                    </li>
                </c:forEach>
            </ul>
        </div>
        <div>
            Chosen Ingredients :
            <ul id="chosen-ingredients" style="max-height: 200px;
        max-width: 200px;
            overflow-y:auto;
display: block;">
            </ul>
        </div>
    </div>
    <br/>
    <input type="submit" name="sub" value="add">
</form>
<script>
    const $list = document.getElementById("ingredients");
    const $li = $list.getElementsByTagName("li");
    for (let i = 0; i < $li.length; i++) {
        $li[i].addEventListener("change", toChosen.bind($li[i]));
    }

    function toChosen(event) {
        console.log(event.target);
        if (event.target.getAttribute("type") === "checkbox") {
            const $ul = document.getElementById("chosen-ingredients");
            this.getElementsByClassName("weight_option")[0].style.display = "block";
            $ul.append(this);
            this.removeEventListener("change", toChosen.bind(this));
            this.addEventListener("change", toUnChosen.bind(this));
        }
    }

    function toUnChosen(event) {
        console.log(event.target);
        if (event.target.getAttribute("type") === "checkbox") {
            const $list = document.getElementById("ingredients");
            console.log(this.getElementsByClassName("weight_option"));
            this.getElementsByClassName("weight_option")[0].style.display = "none"
            $list.append(this);
            this.removeEventListener("change", toUnChosen.bind(this));
            this.addEventListener("change", toChosen.bind(this));
        }
    }
</script>
</body>
</html>
