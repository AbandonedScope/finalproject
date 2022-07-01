<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ValidationMessage" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.section"/></title>
</head>
<body>
<div class="mx-5 mt-2">
    <h2><fmt:message key="navigation.admin.section"/></h2>
    <div class="justify-content-center mx-5">
        <form onsubmit="onModifyFormSubmit(event)" id="form" action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="add-menu-section"/>
            <div class="form-floating my-5">
                <input class="form-control" id="name" type="text" name="menu-section-name" placeholder="Name" required>
                <c:if test="${not empty requestScope.get(ValidationMessage.SECTION_NAME_VALIDATION_MESSAGE)}">
                    <fmt:message key="message.validation.section-name"/>
                </c:if>
                <label for="name">
                    <fmt:message key="label.section.name"/>
                </label>
            </div>
            <div class="d-flex justify-content-end">
                <input class="btn btn-outline-primary fs-5" type="submit" name="sub"
                       value="<fmt:message key="action.admin.add.section"/>">
            </div>
        </form>
    </div>
    <jsp:include page="../../modals/addModal.jsp"/>
</div>
<c:import url="../../footer.jsp"/>
<script>
    const SECTION_NAME_VALIDATION_MESSAGE = '<fmt:message key="message.validation.section-name"/>';
    const MENU_SECTION_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE = '<fmt:message key="message.validation.section-name.exists"/>';

    const fillModalWithValidations = (validations) => {
        const $modalBody = document.getElementById(validationModalBodyId);
        $modalBody.innerText = '';
        const $validationsDiv = document.createElement("div");
        for (let validationMessage of validations) {
            let message;
            switch (validationMessage) {
                case 'section-name':
                    message = SECTION_NAME_VALIDATION_MESSAGE;
                    break;
                case 'menu-section-exists':
                    message = MENU_SECTION_WITH_SUCH_NAME_ALREADY_EXISTS_MESSAGE;
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
