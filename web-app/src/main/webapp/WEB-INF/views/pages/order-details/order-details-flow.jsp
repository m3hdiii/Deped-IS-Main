<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 10/22/17
  Time: 10:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%
    //System.out.println(getServletContext().getRealPath("/"));
%>
<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>

<html>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="${pageTitle}"/>
    <c:param name="description" value="${topHeading}"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbspOrder ${h1Placeholder}&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
        </h3>
    </div>

    <div class="row">
        <p>Order Number: ${relatedOrder.orderId}</p>
        <p>Order
            By: ${relatedOrder.user.firstName}&nbsp;${relatedOrder.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>
        <p>Schedule For: ${relatedOrder.orderSchedule}</p>
        <p>Budget: ${relatedOrder.budgetAmount}</p>
    </div>

    <hr class="style13">


    <div class="row">
        <table class="table table-hover">
            <thead>
            <th>Name</th>
            <th>Item Type</th>
            <th>Available Quantity</th>
            <th>Item Picture</th>
            <th>Packages</th>
            <th>Package Capacity</th>
            <th>No of Packs</th>
            <th>Quantity Request</th>
            <c:if test="${currentOrderDetailsState eq 'ARRIVED'}">
                <th>Quantity Arrived</th>
            </c:if>
            <th>Category</th>
            <th>Unit Price</th>
            <th>Suppliers</th>
            <td>Status</td>
            </thead>

            <c:set var="basketMap" value="${orderDetailsForm.map}"/>

            <form:form commandName="orderDetailsForm" method="post">

                <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                    <c:set var="strKey" value="${entry.key}"/>
                    <c:set var="orderDet" value="${entry.value}"/>

                    <tr>
                        <td>${orderDet.item.name}</td>
                        <td>${orderDet.item.itemType}</td>
                        <td>${orderDet.item.quantity}</td>

                        <c:choose>
                            <c:when test="${not empty orderDet.item.picName}">
                                <td><img width="64" src="${baseUrl}${orderDet.item.picName}" alt="item image"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><img width="64" src="${resourceURL}/images/shared-images/no-item.png"
                                         alt="item image"/>
                                </td>
                            </c:otherwise>
                        </c:choose>

                        <td>
                                ${orderDet.pack}
                        </td>

                        <td>
                                ${orderDet.packCapacity}
                        </td>

                        <th>
                                ${orderDet.noOfPacks}
                        </th>

                        <td>
                                ${orderDet.totalQuantityRequestNo}
                        </td>

                        <c:if test="${currentOrderDetailsState eq 'ARRIVED'}">
                            <th>
                                <form:input path="map['${strKey}'].totalQuantityArrivedNo"
                                            value="${orderDet.totalQuantityRequestNo}"/>
                            </th>
                        </c:if>
                        <td>
                                ${orderDet.category.name}
                        </td>

                        <td>
                                ${orderDet.unitPrice}
                        </td>
                        <td>
                                ${orderDet.supplier.name}
                        </td>

                        <td>
                            <form:select multiple="single" path="map['${strKey}'].orderDetailsState">
                                <form:options items="${nextOrderDetailsStates}"/>
                            </form:select>
                        </td>

                        <form:hidden path="map['${strKey}'].orderDetailsID.categoryId"
                                     value="${orderDet.category.categoryId}"/>
                        <form:hidden path="map['${strKey}'].orderDetailsID.orderId" value="${orderDet.order.orderId}"/>
                        <form:hidden path="map['${strKey}'].orderDetailsID.itemId" value="${orderDet.item.itemId}"/>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                        <button>Submit My Decision!</button>
                    </td>
                </tr>
            </form:form>

        </table>
    </div>


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/order.js"></script>
</section>
</body>
</html>
