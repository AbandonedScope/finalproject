<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.model.entity.RoleType" %>
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
<c:set var="orders" value="${requestScope.customerOrders}"/>
<c:set var="customer" value="${requestScope.customer}"/>
<c:if test="${empty customer}">
    <c:redirect url="user-find.jsp"/>
</c:if>
<div class="mx-5 mt-2">
    <div>
        <ul class="list-group list-group-flush">
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.name"/>
                </label>
                <div>
                    ${customer.name}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.surname"/>
                </label>
                <div>
                    ${customer.surname}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.login"/>
                </label>
                <div>
                    ${customer.login}
                </div>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.role"/>
                </label>
                <div>
                    ${customer.role}
                </div>
                <c:if test="${customer.role == RoleType.CUSTOMER}">
                    <div class="mt-2">
                        <form class="m-0">
                            <input type="hidden" name="customer-id" value="${customer.id}"/>
                            <input type="hidden" name="command" value="set-customer-role-admin">
                            <input class="btn btn-outline-warning" type="submit"
                                   value="<fmt:message key="action.admin.set-role-admin"/>">
                        </form>
                    </div>
                </c:if>
                <c:if test="${customer.role == RoleType.ADMIN}">
                    <div class="mt-2">
                        <form class="m-0">
                            <input type="hidden" name="customer-id" value="${customer.id}"/>
                            <input type="hidden" name="command" value="set-customer-role-customer">
                            <input class="btn btn-outline-warning" type="submit"
                                   value="<fmt:message key="action.admin.set-role-customer"/>">
                        </form>
                    </div>
                </c:if>
            </li>
            <li class="list-group-item">
                <label>
                    <fmt:message key="label.user.loyal-points"/>
                </label>
                <div>
                    ${customer.loyaltyPoints}
                </div>
            </li>
            <li class="list-group-item">
                <div>
                    <label>
                        <fmt:message key="label.user.blocked"/>
                    </label>
                    <div class="d-flex">
                        <c:choose>
                            <c:when test="${customer.blocked}">
                                <div>
                                    <div class="badge bg-danger text-wrap d-flex justify-content-center">
                                        <p class="my-auto">
                                            <ftm:message key="label.user.blocked.true"/>
                                        </p>
                                    </div>
                                    <div class="mt-2">
                                        <form class="m-0">
                                            <input type="hidden" name="customer-id" value="${customer.id}"/>
                                            <input type="hidden" name="command" value="unblock-customer">
                                            <input class="btn btn-outline-success" type="submit"
                                                   value="<fmt:message key="action.admin.unblock-user"/>">
                                        </form>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${not customer.blocked}">
                                <div>
                                    <div class="badge bg-success text-wrap d-flex justify-content-center">
                                        <p class="my-auto">
                                            <ftm:message key="label.user.blocked.false"/>
                                        </p>
                                    </div>
                                    <c:if test="${customer.role != RoleType.ADMIN}">
                                        <div class="mt-2">
                                            <form class="m-0">
                                                <input type="hidden" name="customer-id" value="${customer.id}"/>
                                                <input type="hidden" name="command" value="block-customer">
                                                <input class="btn btn-outline-danger" type="submit"
                                                       value="<fmt:message key="action.admin.block-user"/>">
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                            </c:when>
                        </c:choose>

                    </div>
                </div>
            </li>
        </ul>
    </div>
    <div>
        <c:choose>
            <c:when test="${not empty orders}">
                <h2><fmt:message key="navigation.admin.orders"/></h2>
                <ul class="list-group list-group-horizontal my-1">
                    <li class="list-group-item list-group-item-info flex-fill" style="width: 27rem">
                        <fmt:message key="orders.order.cost"/>
                    </li>
                    <li class="list-group-item list-group-item-info flex-fill" style="width: 24rem">
                        <fmt:message key="orders.order.is-taken"/>
                    </li>
                    <li class="list-group-item list-group-item-info flex-fill" style="width: 27.5rem">
                        <fmt:message key="orders.order.payment-type"/>
                    </li>
                    <li class="list-group-item list-group-item-info flex-fill" style="width: 27rem">
                        <fmt:message key="orders.order.serving-datetime"/>
                    </li>
                    <li class="list-group-item list-group-item-info flex-fill" style="width: 27rem">
                        <fmt:message key="orders.order.more"/>
                    </li>
                </ul>
                <div class="accordion accordion-flush" id="accordionFlushExample">
                    <c:forEach var="order" items="${orders}">
                        <ul class="list-group list-group-horizontal my-1">
                            <li class="list-group-item flex-fill"
                                style="width: 27rem">${order.orderedCost}</li>
                            <li class="list-group-item flex-fill" style="width: 24rem">
                                <c:choose>
                                    <c:when test="${order.taken}">
                                        <div class="badge bg-success text-wrap">
                                            <fmt:message key="orders.order.is-taken.true"/>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="badge bg-danger text-wrap">
                                            <fmt:message key="orders.order.is-taken.false"/>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </li>
                            <li class="list-group-item flex-fill"
                                style="width: 27.5rem">${order.paymentType.toString()}</li>
                            <li class="list-group-item flex-fill" style="width: 27rem">${order.servingTime}</li>
                            <li class="list-group-item flex-fill" style="width: 27rem">
                                <form class="m-0" action="${pageContext.request.contextPath}/controller" method="get">
                                    <input type="hidden" name="command" value="get-order-information">
                                    <input type="hidden" name="order-id" value="${order.id}">
                                    <input class="btn btn-outline-primary fs-5" type="submit" name="more-sub"
                                           value="<fmt:message key="orders.order.more"/>">
                                </form>
                            </li>
                        </ul>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex justify-content-center">
                    <div class="align-items-center text-wrap" style="width: 50%">
                        <div class="text-center bi bi-emoji-frown" style="font-size: 12rem;"></div>
                        <p class="text-center" style="font-size: 2rem;">
                            <fmt:message key="orders.empty"/>
                        </p>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<c:import url="../../footer.jsp"/>
</body>
</html>
