<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<body>
<div class="modal fade" id="validationModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <fmt:message key="message.validation"/>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div id="validationModalBody" class="modal-body">
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="successModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">
                    <fmt:message key="message.success"/>
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div id="successModalBody" class="modal-body">
                <fmt:message key="message.success.operation"/>
            </div>
        </div>
    </div>
</div>
<script>
    const url = 'http://localhost:8080/demo1_war_exploded/controller';
    const validationModalId = 'validationModal';
    const validationModalBodyId = 'validationModalBody';
    const successModalId = 'successModal';

    const onModifyFormSubmit = async (event) => {
        event.preventDefault();
        let formData = new FormData(event.target);
        fetch(url, {
            method: 'POST',
            credentials: 'include',
            cache: 'no-cache',
            body: formData
        })
            .then(async (response) => {
                if (response.ok) {
                    const modal = new bootstrap.Modal(document.getElementById(successModalId));
                    modal.show();
                } else if (response.status === 400) {
                    let data = await response.json();
                    if (data.validation_msg) {
                        fillModalWithValidations(data.validation_msg);
                        const modal = new bootstrap.Modal(document.getElementById(validationModalId));
                        modal.show();
                    }
                }
            });

        return false;
    }
</script>
</body>
</html>
