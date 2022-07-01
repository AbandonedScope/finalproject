<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../header.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="navigation.admin.orders"/></title>
</head>
<body>
<c:set var="orders" value="${requestScope.orders}"/>
<div class="m-5">
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
