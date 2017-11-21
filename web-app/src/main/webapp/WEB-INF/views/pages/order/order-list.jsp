<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Brand List"/>
    <c:param name="description" value="Brand List Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> &nbsp; Brand List </h3>
    </div>

    <div class="row">
        <div class="col col-lg-3"/>
        <div class="col col-lg-9">
            <c:set var="globalOrderState" value="" scope="request"/>

            <c:forEach items="${orders}" var="order" varStatus="i">
            <c:choose>
            <c:when test="${globalOrderState ne order.orderState}">

            <c:if test="${i.index ne 0}">
                </tbody>
                </table>
                <hr>
            </c:if>

            <table class="table table-hover">
                <thead>
                <tr>
                    <th class="col-md-5">Order Creation</th>
                    <th class="col-md-5">Required Date</th>
                    <th class="col-md-5">Ordered By</th>
                    <th class="col-md-5">Order Schedule</th>
                    <th class="col-md-5">Budget Amount</th>
                    <th class="col-md-5">Order State</th>
                    <th class="col-md-5">Arrival Description</th>
                    <th class="col-md-5">See Details</th>
                </tr>
                </thead>
                <tbody>
                    <c:set var="globalOrderState" value="${order.orderState}" scope="request"/>
                </c:when>


                <c:when test="${globalOrderState ne order.orderState}">
                <tr>
                    <td class="col-md-2">${order.orderDate}</td>
                    <td class="col-md-2">${order.requiredDate}</td>
                    <td class="col-md-2">${order.user.firstName} ${order.user.lastName}</td>
                    <td class="col-md-2">${order.orderSchedule}</td>
                    <td class="col-md-2">${order.budgetAmount}</td>
                    <td class="col-md-2">${order.orderState}</td>
                    <td class="col-md-2">${order.arrivalDescription}</td>
                        <%--
                        <sec:authorize access="hasRole('ROLE_ADMIN')">

                        </sec:authorize>

                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">

                        </sec:authorize>
                        --%>
                    <td class="col-md-2">
                        <a href="/order-details/list/${order.orderId}">Details</a>
                        <a href="/order-details/list/${order.orderId}">Details</a>
                    </td>

                </tr>
                </c:when>

                </c:choose>
                </c:forEach>

        </div>
    </div>

        <section class="navbar-fixed-bottom">
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>