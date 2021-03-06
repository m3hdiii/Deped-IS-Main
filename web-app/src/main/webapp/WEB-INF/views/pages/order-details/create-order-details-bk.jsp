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
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbspOrder Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
        </h3>
    </div>

    <c:set var="orderName" value="orderSessionNo${orderId}"/>
    <c:set var="relatedOrder" value="${sessionScope[orderName]}"/>

    <div class="row">
        <p>Order Number: ${relatedOrder.orderId}</p>
        <p>Order
            By: ${relatedOrder.user.firstName}&nbsp;${relatedOrder.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>
        <p>Schedule For: ${relatedOrder.orderSchedule}</p>
        <p>Budget: ${relatedOrder.budgetAmount}</p>
    </div>

    <c:set var="orderIdValue" value="${relatedOrder.orderId}"/>
    <c:set var="basketName" value="orderDetailsMap-OrderNo${orderIdValue}"/>

    <div>
        <c:if test="${not empty sessionScope[basketName]}">
            <a href="/order-details/basket/${orderIdValue}"><img width="16"
                                                                 src="${resourceURL}/images/order/add-to-cart.png"
                                                                 alt="add to cart"/><span>${fn:length(sessionScope[basketName])}</span></a>
        </c:if>
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
            <th>Category</th>
            <th>Unit Price</th>
            <th>Suppliers</th>
            <th>Add To Cart</th>
            </thead>
            <%--            <c:set var="errors" value="${requestScope['org.springframework.validation.BindingResult.order'].allErrors}" />
                        <c:if test="${not empty errors}">
                            <div>
                                <ul class="list-group">
                                    <c:forEach items="${fieldErrors}" var="error" varStatus="loop">
                                        <li class="list-group-item list-group-item-warning text-danger"><span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>--%>
            <c:forEach items="${itemList}" var="item" varStatus="loop">
                <form:form commandName="orderDetail" method="post">
                    <tr>
                        <td>${item.name}</td>
                        <td>${item.itemType}</td>
                        <td>${item.quantity}</td>

                        <c:choose>
                            <c:when test="${not empty item.picName}">
                                <td><img width="64" src="${baseUrl}${item.picName}" alt="item image"/></td>
                            </c:when>
                            <c:otherwise>
                                <td><img width="64" src="${resourceURL}/images/shared-images/no-item.png"
                                         alt="item image"/>
                                </td>
                            </c:otherwise>
                        </c:choose>
                        <td>
                            <form:select path="pack">
                                <form:options items="${packs}" itemLabel="name" itemValue="packId"/>
                            </form:select>
                        </td>

                        <td>
                            <form:input path="packCapacity"/>
                        </td>

                        <td>
                            <form:input path="noOfUnits"/>
                        </td>

                        <td>
                            <form:input path="totalQuantityRequestNo"/>
                        </td>
                        <td>
                            <form:select path="category">
                                <form:options items="${categories}" itemLabel="name" itemValue="categoryId"/>
                            </form:select>
                        </td>

                        <td>
                            <form:input path="unitPrice"/>
                        </td>
                        <td>
                            <form:select path="supplier">
                                <form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                            </form:select>

                        </td>

                        <form:hidden path="order" value="${relatedOrder.orderId}"/>
                        <form:hidden path="item" value="${item.itemId}"/>

                        <td>
                            <button class="btn btn-success btn-block">Add To Cart</button>
                        </td>
                    </tr>
                </form:form>
            </c:forEach>

        </table>
    </div>

            <c:import url="../../includes/footer.jsp"/>
</body>
</html>

