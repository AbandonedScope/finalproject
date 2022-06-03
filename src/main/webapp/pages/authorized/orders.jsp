<%@ page contentType="text/html;charset=UTF-8"
         import="com.mahanko.finalproject.controller.command.CommandType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../header.jsp" %>
<html>
<head>
    <title><fmt:message key="navigation.user.orders"/></title>
</head>
<body>
<c:set var="orders" value="${requestScope.userOrders}"/>
<div class="m-5">
    <div>
        <c:choose>
            <c:when test="${not empty orders}">
                <h2><fmt:message key="navigation.user.orders"/></h2>
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
                        <div class="accordion-item">
                            <h5 class="accordion-header" id="flush-headingOne">
                                <ul class="list-group list-group-horizontal my-1">
                                    <li class="list-group-item flex-fill"
                                        style="width: 27rem">${order.cost}</li>
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
                                    <li class="list-group-item flex-fill"
                                        style="width: 27.5rem">
                                        <button class="accordion-button collapsed"
                                                type="button"
                                                data-bs-toggle="collapse"
                                                data-bs-target="#flush-collapse${order.id}" aria-expanded="false"
                                                aria-controls="flush-collapseOne">
                                        </button>
                                    </li>
                                </ul>

                            </h5>
                            <div id="flush-collapse${order.id}" class="accordion-collapse collapse">
                                <div class="accordion-body">Placeholder content for this accordion, which is intended to
                                    demonstrate the <code>.accordion-flush</code> class. This is the first item's
                                    accordion body.
                                </div>
                            </div>
                        </div>
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
<c:import url="../footer.jsp"/>
</body>
</html>
