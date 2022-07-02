<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.modify.section"/></title>
</head>
<body>
<c:set var="customers" value="${requestScope.customers}"/>
<div class="m-5">
    <form onsubmit="" action="${pageContext.request.contextPath}/controller" method="get">
        <input type="hidden" name="command" value="find-customer-by-name">
        <div class="input-group">
            <div class="form-floating flex-fill">
                <input class="form-control" id="customerSearchingName" type="text"
                       name="customer-name" placeholder="Name"
                       required>
                <label for="customerSearchingName"><fmt:message key="label.customer.find"/></label>
            </div>
            <input class="btn btn-outline-primary" type="submit" value="<fmt:message key="action.admin.change.find"/>">
        </div>
    </form>
    <c:if test="${not empty customers}">
        <div>
            <ul class="list-group list-group-flush">
                <c:forEach var="customer" items="${customers}">

                    <li class="list-group-item">
                        <div class="d-flex">
                            <div>
                                    ${customer.name} ${customer.surname} (${customer.login})
                            </div>
                            <form class="m-0 d-flex justify-content-center"
                                  action="${pageContext.request.contextPath}/controller">
                                <input type="hidden" name="command" value="get-customer-info">
                                <input type="hidden" name="customer-id" value="${customer.id}">
                                <button class="btn btn-default p-0">
                                    <em class="bi bi-link-45deg"></em>
                                </button>
                            </form>
                            <c:if test="${customer.loyalPoints < 0}">
                                <div class="m-2 position-relative">
                                <span class="position-absolute top-50 start-50 translate-middle" tabindex="0"
                                      data-bs-toggle="popover"
                                      data-bs-trigger="hover focus"
                                      data-bs-content="<fmt:message key="label.user.loyal-points.negative"/>">
                                    <em class="bi bi-exclamation-circle text-danger">
                                    </em>
                                </span>
                                </div>
                            </c:if>

                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
</div>
<c:import url="../../footer.jsp"/>
<script>
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
    const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
</script>
</body>
</html>