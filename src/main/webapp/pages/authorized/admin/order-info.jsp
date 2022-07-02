<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.order-info"/></title>
</head>
<body>
<c:set var="order" value="${requestScope.order}"/>
<c:set var="orderCustomer" value="${requestScope.orderCustomer}"/>

<c:if test="${empty order}">
    <c:redirect url="orders.jsp"/>
</c:if>
<div class="mx-5 mt-2">
    <div class="m-5 d-flex justify-content-evenly">
        <div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <label>
                        <fmt:message key="orders.order.customer"/>
                    </label>
                    <div class="d-flex">
                        <div>
                            ${orderCustomer.name} ${orderCustomer.surname}
                        </div>
                        <form class="m-0 d-flex justify-content-center"
                              action="${pageContext.request.contextPath}/controller">
                            <input type="hidden" name="command" value="get-customer-info">
                            <input type="hidden" name="customer-id" value="${orderCustomer.id}">
                            <button class="btn btn-default p-0">
                                <em class="bi bi-link-45deg"></em>
                            </button>
                        </form>
                        <c:if test="${orderCustomer.loyalPoints < 0}">
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
                <li class="list-group-item">
                    <div>
                        <label>
                            <fmt:message key="orders.order.creation-datetime"/>
                        </label>
                        <div>
                            ${order.creationTime}
                        </div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div>
                        <label>
                            <fmt:message key="orders.order.serving-datetime"/>
                        </label>
                        <div>
                            ${order.servingTime}
                        </div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div>
                        <label>
                            <fmt:message key="orders.order.is-taken"/>
                        </label>
                        <div>
                            <c:choose>
                                <c:when test="${order.taken}">
                                    <div class="badge bg-success text-wrap">
                                        <fmt:message key="orders.order.is-taken.true"/>
                                    </div>
                                </c:when>
                                <c:when test="${not order.taken}">
                                    <div class="d-flex">
                                        <div class="badge bg-danger text-wrap d-flex justify-content-center">
                                            <p class="my-auto"><fmt:message key="orders.order.is-taken.false"/>
                                            </p>
                                        </div>
                                        <button type="button" class="btn btn-primary ms-3" data-bs-toggle="modal"
                                                data-bs-target="#set-taken-modal">
                                            <fmt:message key="orders.order.is-taken.take"/>
                                        </button>
                                        <div id="set-taken-modal" class="modal fade" data-bs-backdrop="static"
                                             data-bs-keyboard="false" tabindex="-1">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title" id="staticBackdropLabel">
                                                            <fmt:message key="orders.order.is-taken.confirmation"/>
                                                        </h5>
                                                        <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <form class="m-0 ms-1"
                                                          action="${pageContext.request.contextPath}/controller">
                                                        <input type="hidden" name="command" value="set-order-taken">
                                                        <input type="hidden" name="order-id" value="${order.id}">
                                                        <input type="hidden" name="customer-id"
                                                               value="${orderCustomer.id}">
                                                        <div class="modal-body">
                                                            <div class="form-floating my-5">
                                                                <input class="form-control" id="bonuses" type="number"
                                                                       name="bonuses" min="-25" max="25" step="5"
                                                                       placeholder="bonuses" required>
                                                                <label for="bonuses">
                                                                    <fmt:message
                                                                            key="orders.order.is-taken.bonuses"/></label>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary"
                                                                    data-bs-dismiss="modal">
                                                                <fmt:message key="modal.close"/>
                                                            </button>
                                                            <button type="submit" class="btn btn-outline-primary">
                                                                <fmt:message key="modal.confirm"/>
                                                            </button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div>
                        <label>
                            <fmt:message key="orders.order.is-served"/>
                        </label>
                        <div>
                            <c:choose>
                                <c:when test="${order.served}">
                                    <div class="badge bg-success text-wrap">
                                        <fmt:message key="orders.order.is-served.true"/>
                                    </div>
                                </c:when>
                                <c:when test="${not order.served}">
                                    <div class="d-flex">
                                        <div class="badge bg-danger text-wrap d-flex justify-content-center">
                                            <p class="my-auto"><fmt:message key="orders.order.is-served.false"/>
                                            </p>
                                        </div>
                                        <form class="m-0 ms-3"
                                              action="${pageContext.request.contextPath}/controller">
                                            <input type="hidden" name="command" value="set-order-served">
                                            <input type="hidden" name="order-id" value="${order.id}">
                                            <button type="submit" class="btn btn-primary">
                                                <fmt:message key="orders.order.is-served.serve"/>
                                            </button>
                                        </form>
                                    </div>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div>
                        <label>
                            <fmt:message key="orders.order.payment-type"/>
                        </label>
                        <div>
                            ${order.paymentType.toString()}
                        </div>
                    </div>
                </li>
                <li class="list-group-item">
                    <div>
                        <fmt:message key="label.shopping-cart.cost"/> : ${order.cost}
                    </div>
                </li>
            </ul>
        </div>
        <div class="m-5 table-responsive" style="max-width: 55rem">
            <table class="table table-bordered">
                <thead>
                <tr class="table-primary">
                    <th scope="col"><fmt:message key="cart.table.column.meal"/></th>
                    <th scope="col"><fmt:message key="cart.table.column.cost"/></th>
                    <th scope="col"><fmt:message key="cart.table.column.sum"/></th>
                    <th scope="col"><fmt:message key="cart.table.column.amount"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="entryItem" items="${order.items}">
                    <c:set var="item" value="${entryItem.key}"/>
                    <c:set var="count" value="${entryItem.value}"/>
                    <tr>
                        <td class="align-middle">
                            <div class="d-flex justify-content-between align-items-center">
                                <img style="max-width: 5rem; max-height: 5rem;"
                                     src="data:image/png;base64,${item.pictureBase64}"
                                     alt="${item.name}">
                                <div class="text-wrap w-50">
                                        ${item.name}
                                </div>
                            </div>
                        </td>
                        <td class="align-middle">
                            <p class="m-0">${item.cost}</p>
                        </td>
                        <td class="align-middle"><p class="m-0">${count * item.cost}</p></td>
                        <td class="align-middle">
                            <div class="d-flex mx-3 align-items-center">
                                    ${count}
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<c:import url="../../footer.jsp"/>
<script>
    const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
    const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))
</script>
</body>
</html>
