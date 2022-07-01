<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ValidationMessage" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.modify.section"/></title>
</head>
<body>
<c:set var="sections" value="${requestScope.sections}"/>
<div class="m-5">
    <form onsubmit="" action="${pageContext.request.contextPath}/controller" method="get">
        <input type="hidden" name="command" value="find-menu-sections-by-name">
        <div class="input-group">
            <div class="form-floating flex-fill">
                <input class="form-control" id="sectionSearchingName" type="text"
                       name="menu-section-name" placeholder="Name"
                       required>
                <label for="sectionSearchingName"><fmt:message key="label.section.name"/></label>
            </div>
            <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="action.admin.change.find"/>">
        </div>
    </form>
    <c:if test="${not empty sections}">
        <div id="accordionFlushExample" class="accordion accordion-flush">
            <c:forEach var="section" items="${sections}">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="flush-headingOne">
                        <button class="accordion-button collapsed"
                                type="button"
                                data-bs-toggle="collapse"
                                data-bs-target="#flush-collapse${section.id}" aria-expanded="false"
                                aria-controls="flush-collapseOne">
                                ${section.name}
                        </button>
                    </h2>
                    <div id="flush-collapse${section.id}" class="accordion-collapse collapse"
                         aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
                        <div class="accordion-body">
                            <div class="row row-cols-1 p-4">
                                <div class="col d-flex flex-column justify-content-between">
                                    <form onsubmit="onModifyFormSubmit(event)" class="m-0" action="${pageContext.request.contextPath}/controller"
                                          method="post">
                                        <input type="hidden" name="command" value="modify-menu-section">
                                        <input type="hidden" name="menu-section-id" value="${section.id}">
                                        <div>
                                            <div class="form-floating">
                                                <input class="form-control" id="sectionName" type="text"
                                                       name="menu-section-name" placeholder="Name" value="${section.name}"
                                                       required>
                                                <c:if test="${not empty requestScope.get(ValidationMessage.SECTION_NAME_VALIDATION_MESSAGE)}">
                                                    <fmt:message key="message.validation.section-name"/>
                                                </c:if>
                                                <label for="sectionName"><fmt:message
                                                        key="label.section.name"/></label>
                                            </div>
                                        </div>
                                        <div class="mt-3">
                                            <input class="btn btn-outline-primary text-wrap" type="submit"
                                                   name="submit-button"
                                                   value="<fmt:message key="action.modify.save"/>"/>
                                        </div>
                                    </form>
                                        <div class="d-flex justify-content-end mt-3">
                                            <form onsubmit="onRemoveFormSubmit(event)" action="${pageContext.request.contextPath}/controller"
                                                  method="post">
                                                <input type="hidden" name="command" value="remove-menu-section"/>
                                                <input type="hidden" name="menu-section-id" value="${section.id}"/>
                                                <input class="btn btn-outline-danger text-wrap" type="submit"
                                                       name="submit-button"
                                                       value="<fmt:message key="action.modify.delete"/>"/>
                                            </form>

                                        </div>

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        <jsp:include page="../../modals/modals.jsp"/>
    </c:if>
</div>
<c:import url="../../footer.jsp"/>
<script>
    const SECTION_NAME_VALIDATION_MESSAGE = '<fmt:message key="message.validation.section-name"/>';

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