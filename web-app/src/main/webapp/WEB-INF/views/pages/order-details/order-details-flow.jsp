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
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Manage Order
                <small>DepEd-Baguio City Division Office</small>
            </h1>
        </div>

        <div class="row">
            <p>Order Number: ${relatedOrder.orderId}</p>
            <p>Order
                By: ${relatedOrder.user.firstName}&nbsp;${relatedOrder.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>
            <p>Schedule For: ${relatedOrder.orderSchedule}</p>
            <p>Budget: ${relatedOrder.budgetAmount}</p>
        </div>

        <div class="row item-body">
            <c:set var="basketMap" value="${orderDetailsForm.map}"/>

            <form:form commandName="orderDetailsForm" method="post">
                <div class="panel panel-default">
                    <h3 class="text-center">Ordered Items</h3>
                    <div class="panel-body">

                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th>Image</th>
                                <th>Name</th>
                                <th>Available QTY</th>
                                <th>Packages</th>
                                <th>Pack Capacity</th>
                                <th>Ordered QTY</th>
                                <th>Category</th>
                                <th>Unit Price</th>
                                <th>Suppliers</th>
                                <th>Item Type</th>
                                <th>State</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                <c:set var="strKey" value="${entry.key}"/>
                                <c:set var="orderDet" value="${entry.value}"/>
                                <tr>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty orderDet.item.picName}">
                                                <img src="${baseUrl}${orderDet.item.picName}" alt="item image" width="76px" height="50px"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${resourceURL}/images/shared-images/no-item.png"
                                                     alt="item image" width="76px" height="50px"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <th>${orderDet.item.name}</th>
                                    <td>${orderDet.item.quantity}</td>
                                    <td>${orderDet.pack.name}</td>
                                    <td>${orderDet.packCapacity}</td>
                                    <td>${orderDet.totalQuantityRequestNo}</td>
                                    <td>${orderDet.category.name}</td>
                                    <td>${orderDet.unitPrice}</td>
                                    <td>${orderDet.supplier.name}</td>
                                    <td>${orderDet.item.itemType}</td>
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
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i><span> Back</span></a>
                        <button type="submit" class="btn btn-purple pull-right">Confirm</button>
                    </div>
                </div>
            </form:form>

        </div>

    </div> <!-- Warper Ends Here (working area) -->


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/order.js"></script>
</section>
</body>
</html>
