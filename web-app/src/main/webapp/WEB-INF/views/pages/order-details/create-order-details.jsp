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

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Order Request
                <small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
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

        <div class="panel panel-default">
            <div class="panel-heading">Basic DataTable</div>
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered"
                       id="basic-datatable">
                    <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Available QTY</th>
                        <th>Package Capacity</th>
                        <th>Packages</th>
                        <th>No. of Packs</th>
                        <th>Item QTY</th>
                        <th>Category</th>
                        <th>Item Type</th>
                        <th>Unit Price</th>
                        <th>Suppliers</th>
                        <th>Add To Order</th>
                    </tr>
                    </thead>
                    <tbody>

                        <c:forEach items="${itemList}" var="item" varStatus="loop">
                            <tr>
                            <form:form commandName="orderDetail" id="orderDetails${loop.index}" method="post">

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

                                    <td>${item.name}</td>

                                    <td>${item.quantity}</td>

                                    <td>
                                        <div class="col-md-2">
                                            <form:input type="number" min="0" path="packCapacity"/>
                                        </div>
                                    </td>

                                    <td>
                                        <form:select path="pack">
                                            <form:options items="${packs}" itemLabel="name" itemValue="packId"/>
                                        </form:select>
                                    </td>

                                    <td>
                                        <form:input type="number" min="0" path="noOfPacks"/>
                                    </td>

                                    <td>
                                        <form:input type="number" min="0" path="totalQuantityRequestNo"/>
                                    </td>


                                    <td>
                                        <form:select path="category">
                                            <form:options items="${categories}" itemLabel="name" itemValue="categoryId"/>
                                        </form:select>
                                    </td>

                                    <td>${item.itemType}</td>

                                    <td>
                                        <form:input path="unitPrice"/>
                                    </td>

                                    <td>
                                        <form:select path="supplier">
                                            <form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                                        </form:select>
                                    </td>

                                <input type="hidden" name="order" value="${relatedOrder.orderId}"/>
                                <input type="hidden" name="item" value="${item.itemId}"/>

                                    <td>
                                        <button class="btn btn-success btn-block" type="submit">Add To Order</button>
                                    </td>
                                </tr>
                            </form:form>
                        </c:forEach>

                    </tbody>
                </table>

            </div>
        </div>
    </div>

            <c:import url="../../includes/footer.jsp"/>

</body>
</html>

