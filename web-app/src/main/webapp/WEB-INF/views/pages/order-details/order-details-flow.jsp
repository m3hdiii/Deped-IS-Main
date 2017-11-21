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


    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Order Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <div class="row">
            <h5>Order Information</h5>
            <p>Order Number: ${relatedOrder.orderId}</p>
            <p>Order
                By: ${relatedOrder.user.firstName}&nbsp;${relatedOrder.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>
            <p>Schedule For: ${relatedOrder.orderSchedule}</p>
            <p>Budget: ${relatedOrder.budgetAmount}</p>
        </div>


        <c:set var="basketMap" value="${orderDetailsForm.map}"/>

        <form:form commandName="orderDetailsForm" method="post">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <a href="#" data-column="0" class="toggle-vis btn btn-default btn-sm">Image</a>
                    <a href="#" data-column="7" class="toggle-vis btn btn-default btn-sm">Category</a>
                    <a href="#" data-column="8" class="toggle-vis btn btn-default btn-sm">Unit Price</a>
                    <a href="#" data-column="9" class="toggle-vis btn btn-default btn-sm">Supplier</a>
                </div>
                <div class="panel-body table-responsive">

                    <table cellpadding="0" cellspacing="0" border="0" class="table table-hover"
                           id="toggleColumn-datatable">
                        <thead>
                        <tr>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Available QTY</th>
                            <th>Packages</th>
                            <th>Pack Capacity</th>
                            <th>Ordered QTY</th>
                            <c:if test="${currentOrderDetailsState eq 'ARRIVED'}">
                                <th>Quantity Arrived</th>
                            </c:if>
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
                                            <img src="${baseUrl}${orderDet.item.picName}" alt="item image" width="76px"
                                                 height="50px"/>
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
                                <c:if test="${currentOrderDetailsState eq 'ARRIVED'}">
                                    <th>
                                        <div class="col-md-12">
                                            <form:input min="0" type="number"
                                                        class="form-control form-control-flat no-margn"
                                                        path="map['${strKey}'].totalQuantityArrivedNo"
                                                        value="${orderDet.totalQuantityRequestNo}"/>
                                        </div>

                                    </th>
                                </c:if>
                                <td>${orderDet.category.name}</td>
                                <td>${orderDet.unitPrice}</td>
                                <td>${orderDet.supplier.name}</td>
                                <td>${orderDet.item.itemType}</td>
                                <td>
                                    <form:select multiple="single" path="map['${strKey}'].orderDetailsState">
                                        <form:options items="${nextOrderDetailsStates}" itemLabel="action"/>
                                    </form:select>
                                </td>

                                <form:hidden path="map['${strKey}'].orderDetailsID.categoryId"
                                             value="${orderDet.category.categoryId}"/>
                                <form:hidden path="map['${strKey}'].orderDetailsID.orderId"
                                             value="${orderDet.order.orderId}"/>
                                <form:hidden path="map['${strKey}'].orderDetailsID.itemId"
                                             value="${orderDet.item.itemId}"/>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                    <hr class="clean">
                </div>

                <div class="modal-footer">
                    <a href="/order/approval-list" class="btn btn-default pull-left"><i
                            class="fa fa-chevron-left"></i><span> Back</span></a>
                    <button type="submit" class="btn btn-purple pull-right">Confirm</button>
                </div>
            </div>
        </form:form>
    </div>
    <!-- Warper Ends Here (working area) -->
            <c:import url="../../includes/footer.jsp"/>
            <script type="text/javascript" src="${resourceURL}/js/additional/order.js"></script>

</body>
</html>
