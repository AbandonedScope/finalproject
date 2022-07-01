<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}" scope="session"/>
<fmt:setBundle basename="language"/>
<html>
<body>
<div class="modal fade" id="removeModal" tabindex="-1">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="removeModelHeader">
                </h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div id="removeModelBody" class="modal-body">
            </div>
        </div>
    </div>
</div>
<script>
    const removeModalId = 'removeModal';
    const removeModalHeaderId = 'removeModelHeader';
    const removeModalBodyId = 'removeModelBody';
    const success = '<fmt:message key="message.success"/>';
    const fail = '<fmt:message key="message.fail"/>';
    const removeFail = '<fmt:message key="message.fail.remove"/>';
    const removeSuccess = '<fmt:message key="message.success.remove"/>';

    const onRemoveFormSubmit = async (event) => {
        event.preventDefault();
        let formData = new FormData(event.target);
        fetch(url, {
            method: 'POST',
            credentials: 'include',
            cache: 'no-cache',
            body: formData
        })
            .then(async (response) => {
                let $removalModalHeader = document.getElementById(removeModalHeaderId);
                let $removalModalBody = document.getElementById(removeModalBodyId);
                $removalModalHeader.innerText = '';
                $removalModalBody.innerText = '';
                if (response.ok) {
                    $removalModalHeader.innerText = success;
                    $removalModalBody.innerText = removeSuccess;
                } else {
                    $removalModalHeader.innerText = fail;
                    $removalModalBody.innerText = removeFail;
                }
                const modal = new bootstrap.Modal(document.getElementById(removeModalId));
                modal.show();
            });

        return false;
    }
</script>
</body>
</html>
