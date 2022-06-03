<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.ValidationMessage"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.guest.cart"/></title>
</head>
<body>
<c:set var="cart" value="${sessionScope.shoppingCart}"/>
<div class="mx-5 mt-2">
    <c:if test="${not empty cart.items}">
        <h2><fmt:message key="navigation.guest.cart"/></h2>
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
                <c:forEach var="entryItem" items="${cart.items}">
                    <c:set var="item" value="${entryItem.key}"/>
                    <c:set var="count" value="${entryItem.value}"/>
                    <tr>
                        <td class="align-middle">
                            <div class="d-flex justify-content-between align-items-center">
                                <img style="max-width: 5rem; max-height: 5rem;"
                                     src="data:image/png;base64,${item.pictureBase64}"
                                     alt="${item.name}">
                                <h5 class="m-0">${item.name}</h5>
                            </div>
                        </td>
                        <td class="align-middle">
                            <p class="m-0">${item.cost}</p>
                        </td>
                        <td class="align-middle"><p class="m-0">${count * item.cost}</p></td>
                        <td class="align-middle">
                            <div class="d-flex mx-3 align-items-center justify-content-between">
                                <form class="m-0" action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="change-item-in-cart-amount">
                                    <input type="hidden" name="menu-item-id" value="${item.id}">
                                    <div class="input-group">
                                        <input class="form-control" type="number" name="menu-item-count"
                                               value="${count}"
                                               min="1" step="1" required>
                                        <input class="btn btn-outline-primary" type="submit" name="submit-button"
                                               value="<fmt:message key="cart.table.amount.change"/>"/>
                                    </div>
                                </form>
                                <form class="m-0" action="${pageContext.request.contextPath}/controller" method="post">
                                    <input type="hidden" name="command" value="remove-menu-item-from-cart">
                                    <input type="hidden" name="menu-item-id" value="${item.id}">
                                    <div>
                                        <button class="btn btn-outline-danger p-2 rounded-circle">
                                            <em class="bi bi-x-lg"></em>
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <h5><fmt:message key="label.shopping-cart.cost"/> : ${cart.cost}</h5>
        </div>
        <!-- Button trigger modal -->
        <c:if test="${not empty requestScope.get(ValidationMessage.SERVING_DATETIME_VALIDATION_MESSAGE)}">
            <fmt:message key="message.validation.order.serving-date"/>
        </c:if>
        <c:choose>
            <c:when test="${sessionScope.user.role == RoleType.GUEST}">
                <a href="login.jsp">
                    <button type="button" class="btn btn-outline-primary fs-5">
                        <fmt:message key="action.guest.make-order"/>
                    </button>
                </a>
            </c:when>
            <c:otherwise>
                <button type="button" class="btn btn-outline-primary fs-5" data-bs-toggle="modal"
                        data-bs-target="#orderModal">
                    <fmt:message key="action.guest.make-order"/>
                </button>
            </c:otherwise>
        </c:choose>

        <!-- Modal -->
        <div class="modal fade" id="orderModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="cart.order-modal.header"/></h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="${pageContext.request.contextPath}/controller">
                        <div class="modal-body">
                            <input type="hidden" name="command" value="${CommandType.ADD_ORDER}"/>
                            <c:if test="${sessionScope.user == null || sessionScope.user.role == RoleType.GUEST}">
                                <p>Name</p>
                            </c:if>
                            <label for="order-time"><fmt:message key="cart.order-modal.serving-date"/></label>
                            <input class="form-control" onchange="continueButton()" id="order-time"
                                   type="datetime-local" name="order-time"
                                   required>
                            <div class="form-floating my-5">
                                <select class="form-select" id="payment-type-sectionId" name="payment-type" required>
                                    <c:forEach var="paymentType" items="${requestScope.paymentTypes}">
                                        <option value="${paymentType}">${paymentType}</option>
                                    </c:forEach>
                                </select>
                                <label for="payment-type-sectionId"><fmt:message key="label.menuitem.sectionId"/></label>
                            </div>
                            <c:if test="${not empty requestScope.get(ValidationMessage.SERVING_DATETIME_VALIDATION_MESSAGE)}">
                                <fmt:message key="message.validation.order.serving-date"/>
                            </c:if>
                            <p>Minimum value now + 30 minutes</p>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-outline-secondary fs-5" data-bs-dismiss="modal">
                                <fmt:message
                                        key="cart.order-modal.close"/></button>
                            <button type="submit" id="confirm" class="btn btn-outline-success fs-5"
                                    data-bs-dismiss="modal"
                                    disabled><fmt:message
                                    key="cart.order-modal.confirm"/></button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${empty cart.items}">
        <div class="align-items-center">
            <div class="text-center bi bi-cart4" style="font-size: 12rem;"></div>
            <p class="text-center" style="font-size: 2rem;">
                <fmt:message key="cart.message.empty"/>
            </p>
            <div style="text-align:center">
                <a href="${pageContext.request.contextPath}/index.jsp">
                    <button class="btn btn-outline-warning rounded-pill">
                        <fmt:message key="cart.button.continue-shopping"/>
                    </button>
                </a>
            </div>
        </div>
    </c:if>
</div>
<script>
    function continueButton() {
        if (document.getElementById("order-time").value !== "") {
            document.getElementById("confirm").removeAttribute("disabled");
        }
    }
</script>
<c:import url="footer.jsp"/>
</body>
</html>