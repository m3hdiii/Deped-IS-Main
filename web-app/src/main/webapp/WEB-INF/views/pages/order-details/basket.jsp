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

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbsp;Order Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
        </h3>
    </div>

    <c:set var="orderName" value="orderSessionNo${orderId}"/>
    <c:set var="relatedOrder" value="${sessionScope[orderName]}"/>


    <div class="container-fluid">
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

        <div class="panel panel-default">
            <div class="panel-heading">

            </div>

            <div class="table-responsive">
                <table>
                    <thead>
                    <th>Name</th>
                    <th>Item Type</th>
                    <th>Available Quantity</th>
                    <th>Item Picture</th>
                    <th>Units</th>
                    <th>Unit Capacity</th>
                    <th>No.of Units</th>
                    <th>Total Capacity</th>
                    <th>Category</th>
                    <th>Unit Price</th>
                    <th>Suppliers</th>
                    <th>Edit</th>
                    <th>Delete</th>
                    </thead>

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
                            <td>
                                <button name="actionParam" class="btn btn-purple btn-block" value="UPDATE_ALL">Update the List And Order More</button>
                            </td>

                            <td>
                                <button name="actionParam" class="btn btn-danger btn-block" value="DELETE_ALL">DELETE the List and Re-Order</button>
                            </td>

                            <td>
                                <button name="actionParam" class="btn btn-primary btn-block" value="SAVE_ALL">Update all and SAVE the List</button>
                            </td>

                            <td>
                                <button name="actionParam" class="btn btn-success btn-block" value="ORDER_ALL">Update all and ORDER the List</button>
                            </td>
                        </tr>
                    </form:form>
                </table>

            </div>
        </div>

            <%--
            <form:form commandName="orderDetailsForm" method="post">
                <c:forEach items="${basketMap}" var="basketElement" varStatus="loop">
                    <c:set var="strKey" value="${basketElement.key}"/>
                    <c:set var="orderDetails" value="${basketElement.value}"/>
            --%>

            <c:import url="../../includes/footer.jsp"/>
</body>
</html>

