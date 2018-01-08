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
    <c:param name="title" value="Basket"/>
    <c:param name="description" value="Basket Page"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">
            <div class="page-header">
                <h3>Checkout Order
                    <small>for Goods, Semi-Expendable and Equipment</small>
                </h3>
            </div>

            <c:set var="orderName" value="orderSessionNo${orderId}"/>
            <c:set var="relatedOrder" value="${sessionScope[orderName]}"/>

            <div class="col-md-12 no-padd">
                <div class="col-md-5 no-padd">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            Order Information
                        </div>
                        <div class="panel-body">
                            <p>Required Date: ${relatedOrder.requiredDate}</p>
                            <p>Schedule: ${relatedOrder.orderSchedule}</p>
                            <p>Budget Amount: ${relatedOrder.budgetAmount}</p>
                        </div>
                    </div>
                </div>
                <%--<div class="col-md-6 no-padd pull-right">--%>

                    <%--<div class="panel panel-default">--%>
                        <%--<div class="panel-heading">--%>
                            <%--Order Summary (Estimation)--%>
                        <%--</div>--%>
                        <%--<div class="panel-body">--%>
                            <%--<p>Subtotal Unit Price:</p>--%>
                            <%--<hr>--%>
                            <%--<p>Total Unit Price:</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            </div>

            <c:set var="orderIdValue" value="${relatedOrder.orderId}"/>
            <c:set var="basketName" value="orderDetailsMap-OrderNo${orderIdValue}"/>
            <c:set var="basketMap" value="${sessionScope[basketName]}"/>

            <c:set var="errors"
                   value="${requestScope['org.springframework.validation.BindingResult.orderDetailsForm'].allErrors}"/>

            <c:if test="${not empty errors}">
                <div>
                    <ul class="list-group">
                        <c:forEach items="${errors}" var="error">
                            <li class="list-group-item list-group-item-warning text-danger"><span
                                    class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <div class="col-md-12 no-padd">
                <div class="panel panel-default">
                    <form:form commandName="orderDetailsForm" method="post">
                    <div class="panel-heading">Item to Order</div>
                    <div class="panel-body no-padd">
                        <table cellpadding="0" cellspacing="0" border="0" class="table table-hover">
                            <thead>
                            <tr>
                                <th class="col-md-2 fixed-column">Items</th>
                                <th class="col-md-1">Capacity</th>
                                <th class="col-md-1">Unit</th>
                                <th class="col-md-1">No. of Unit</th>
                                <th class="col-md-1">Total QTY</th>
                                <th class="col-md-1">Unity Price</th>
                                <th class="col-sm-2">Category</th>
                                <th class="col-sm-2">Supplier</th>
                                <th class="col-xs-1">Remove</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                <c:set var="strKey" value="${entry.key}"/>
                                <c:set var="orderDet" value="${entry.value}"/>
                                <tr>
                                    <td>
                                        <div class="col-md-12 no-padd text-center">
                                            <div class="item-t-img col-md-12">
                                                <c:choose>
                                                    <c:when test="${not empty orderDet.item.picName}">
                                                        <img width="100" height="50" src="${baseUrl}${orderDet.item.picName}" alt="item image"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img width="100" height="50" src="${resourceURL}/images/shared-images/no-item.png"
                                                                 alt="item image"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div class="item-t-info col-md-12 no-padd">
                                                <h4 class="no-margn margn-t-xs">${orderDet.item.name}</h4>
                                                <p class="no-margn margn-t-xs"><strong>${orderDet.item.itemType}</strong></p>
                                                <p class="no-margn margn-t-xs">${orderDet.item.quantity}</p>
                                            </div>
                                        </div>

                                    </td>

                                    <td>
                                        <div class="form-group col-md-12 no-margn no-padd">
                                            <form:input type="text" class="form-control input-sm text-center" path="map['${strKey}'].unitCapacity" value="${orderDet.unitCapacity}"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group col-md-12 no-margn no-padd">
                                            <form:select class="form-control input-sm" multiple="single" path="map['${strKey}'].unit">
                                                <form:option value="${orderDet.unit.name}" selected="true"
                                                             label="${orderDet.unit.name}"/>
                                                <option disabled>──────────</option>
                                                <form:options items="${units}" itemLabel="name" itemValue="name"/>
                                            </form:select>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="form-group col-md-12 no-margn no-padd">
                                            <form:input type="text" class="form-control input-sm text-center" path="map['${strKey}'].noOfUnits"
                                                        value="${orderDet.noOfUnits}"/>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="form-group col-md-12 no-padd no-margn">
                                            <form:input type="text" class="form-control input-sm text-center" path="map['${strKey}'].totalQuantityRequestNo"
                                                        value="${orderDet.totalQuantityRequestNo}"/>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="form-group col-md-12 no-padd no-margn">
                                            <form:input type="text" class="form-control input-sm text-center" path="map['${strKey}'].unitPrice" value="${orderDet.unitPrice}"/>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="form-group col-md-12 no-margn no-padd text-center">
                                            <form:select class="form-control input-sm" multiple="single" path="map['${strKey}'].category">
                                                <form:option value="${orderDet.category.name}" selected="true"
                                                             label="${orderDet.category.name}"/>
                                                <option disabled>──────────</option>
                                                <form:options items="${categories}" itemLabel="name" itemValue="name"/>
                                            </form:select>
                                        </div>
                                    </td>

                                    <td>
                                        <div class="form-group no-margn no-padd">
                                            <form:select class="form-control input-sm" multiple="single" path="map['${strKey}'].supplier">
                                                <form:option value="${orderDet.supplier.supplierId}" selected="true"
                                                             label="${orderDet.supplier.name}"/>
                                                <option disabled>──────────</option>
                                                <form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                                            </form:select>
                                        </div>
                                    </td>

                                    <form:hidden path="map['${strKey}'].order" value="${relatedOrder.orderId}"/>
                                    <form:hidden path="map['${strKey}'].item" value="${orderDet.item.name}"/>
                                    <form:hidden path="map['${strKey}'].orderDetailsID.categoryName"
                                                 value="${orderDet.category.name}"/>
                                    <form:hidden path="map['${strKey}'].orderDetailsID.orderId" value="${orderDet.order.orderId}"/>
                                    <form:hidden path="map['${strKey}'].orderDetailsID.itemName" value="${orderDet.item.name}"/>

                                    <td>
                                        <a href="/order-details/delete/${strKey}" class="btn btn-sm btn-danger">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class=" modal-footer ">
                        <button name="actionParam" value="UPDATE_ALL" class="pull-left btn btn-link"
                                data-toggle="tooltip" title="Update the data and Request more"><i
                                class="fa fa-arrow-left"></i> <strong>Request
                            more</strong></button>

                        <button name="actionParam" value="DELETE_ALL" class="btn btn-danger"
                                data-toggle="tooltip"
                                title="Delete the list and re-request">Delete All
                        </button>
                        <button name="actionParam" value="SAVE_ALL" class="btn btn-primary"
                                data-toggle="tooltip"
                                title="Save the list">Save
                        </button>
                        <button name="actionParam" value="ORDER_ALL" class="btn btn-success"
                                data-toggle="tooltip"
                                title="Send the Request">Send Order
                        </button>
                    </div>
                    </form:form>
                </div>

            </div>
        </div><!-- Warper Ends Here (working area) -->


        <!-- ajsdkjaskld --->

    <%--<div class="page-header">
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
    <c:set var="basketMap" value="${sessionScope[basketName]}"/>
    <hr class="style13">
        <c:set var="errors"
               value="${requestScope['org.springframework.validation.BindingResult.orderDetailsForm'].allErrors}"/>

        <c:if test="${not empty errors}">
        <div>
            <ul class="list-group">
                <c:forEach items="${errors}" var="error">
                    <li class="list-group-item list-group-item-warning text-danger"><span
                            class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                    </li>
                </c:forEach>
            </ul>
        </div>
        </c:if>

    <div class="row">
        <table class="table table-hover">
            <thead>
            <th>Name</th>
            <th>Item Type</th>
            <th>Available Quantity</th>
            <th>Item Picture</th>
            <th>Units</th>
            <th>Unit Capacity</th>
            <th>No of Units</th>
            <th>Total Capacity</th>
            <th>Category</th>
            <th>Unit Price</th>
            <th>Suppliers</th>
            <td>Edit</td>
            <th>Delete Item</th>
            </thead>
            &lt;%&ndash;
            <form:form commandName="orderDetailsForm" method="post">
                <c:forEach items="${basketMap}" var="basketElement" varStatus="loop">
                    <c:set var="strKey" value="${basketElement.key}"/>
                    <c:set var="orderDetails" value="${basketElement.value}"/>
            &ndash;%&gt;
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
                            <form:select multiple="single" path="map['${strKey}'].unit">
                                <form:option value="${orderDet.unit.name}" selected="true"
                                             label="${orderDet.unit.name}"/>
                                <option disabled>──────────</option>
                                <form:options items="${units}" itemLabel="name" itemValue="name"/>
                            </form:select>
                        </td>

                        <td>
                            <form:input path="map['${strKey}'].unitCapacity" value="${orderDet.unitCapacity}"/>
                        </td>

                        <td>
                            <form:input path="map['${strKey}'].noOfUnits"
                                        value="${orderDet.noOfUnits}"/>
                        </td>

                        <td>
                            <form:input path="map['${strKey}'].totalQuantityRequestNo"
                                        value="${orderDet.totalQuantityRequestNo}"/>
                        </td>

                        <td>
                            <form:select multiple="single" path="map['${strKey}'].category">
                                <form:option value="${orderDet.category.name}" selected="true"
                                             label="${orderDet.category.name}"/>
                                <option disabled>──────────</option>
                                <form:options items="${categories}" itemLabel="name" itemValue="name"/>
                            </form:select>
                        </td>

                        <td>
                            <form:input path="map['${strKey}'].unitPrice" value="${orderDet.unitPrice}"/>
                        </td>
                        <td>
                            <form:select multiple="single" path="map['${strKey}'].supplier">
                                <form:option value="${orderDet.supplier.supplierId}" selected="true"
                                             label="${orderDet.supplier.name}"/>
                                <option disabled>──────────</option>
                                <form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                            </form:select>

                        </td>

                        <form:hidden path="map['${strKey}'].order" value="${relatedOrder.orderId}"/>
                        <form:hidden path="map['${strKey}'].item" value="${orderDet.item.name}"/>
                        <form:hidden path="map['${strKey}'].orderDetailsID.categoryName"
                                     value="${orderDet.category.name}"/>
                        <form:hidden path="map['${strKey}'].orderDetailsID.orderId" value="${orderDet.order.orderId}"/>
                        <form:hidden path="map['${strKey}'].orderDetailsID.itemName" value="${orderDet.item.name}"/>

                        <td><img src="${resourceURL}/images/edit.png"></td>
                        <td>
                            <a href="/order-details/delete/${strKey}"><img src="${resourceURL}/images/delete.png"></a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                        <button name="actionParam" class="btn btn-purple btn-block" value="UPDATE_ALL">Update the List And Order More</button>
                    </td>

                    <td colspan="3">
                        <button name="actionParam" class="btn btn-danger btn-block" value="DELETE_ALL">DELETE the List and Re-Order</button>
                    </td>

                    <td colspan="3">
                        <button name="actionParam" class="btn btn-primary btn-block" value="SAVE_ALL">Update all and SAVE the List</button>
                    </td>

                    <td colspan="3">
                        <button name="actionParam" class="btn btn-success btn-block" value="ORDER_ALL">Update all and ORDER the List</button>
                    </td>
                </tr>
            </form:form>

        </table>
    </div>--%>

            <c:import url="../../includes/footer.jsp"/>
</body>
</html>

