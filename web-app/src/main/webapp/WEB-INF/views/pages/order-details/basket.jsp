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
    <c:set var="basketMap" value="${sessionScope[basketName]}"/>
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
            <th>Quantity Request</th>
            <th>Category</th>
            <th>Unit Price</th>
            <th>Suppliers</th>
            <td>Edit</td>
            <th>Delete Item</th>
            </thead>
            <%--
            <form:form commandName="orderDetailsForm" method="post">
                <c:forEach items="${basketMap}" var="basketElement" varStatus="loop">
                    <c:set var="strKey" value="${basketElement.key}"/>
                    <c:set var="orderDetails" value="${basketElement.value}"/>
            --%>
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
                            <form:select multiple="single" path="map['${strKey}'].pack">
                                <form:option value="${orderDet.pack.packId}" selected="true"
                                             label="${orderDet.pack.name}"/>
                                <option disabled>──────────</option>
                                <form:options items="${packs}" itemLabel="name" itemValue="packId"/>
                            </form:select>
                        </td>

                        <td>
                            <form:input path="map['${strKey}'].packCapacity" value="${orderDet.packCapacity}"/>
                        </td>

                        <td>
                            <form:input path="map['${strKey}'].totalQuantityRequestNo"
                                        value="${orderDet.totalQuantityRequestNo}"/>
                        </td>
                        <td>
                            <form:select multiple="single" path="map['${strKey}'].category">
                                <form:option value="${orderDet.category.categoryId}" selected="true"
                                             label="${orderDet.category.name}"/>
                                <option disabled>──────────</option>
                                <form:options items="${categories}" itemLabel="name" itemValue="categoryId"/>
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
                        <form:hidden path="map['${strKey}'].item" value="${orderDet.item.itemId}"/>

                        <td><img src="${resourceURL}/images/edit.png"></td>
                        <td>
                            <a href="/order-details/delete/${strKey}"><img src="${resourceURL}/images/delete.png"></a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                        <button name="actionParam" value="UPDATE_ALL">Update the List And Order More</button>
                    </td>

                    <td colspan="3">
                        <button name="actionParam" value="DELETE_ALL">DELETE the List and Re-Order</button>
                    </td>

                    <td colspan="3">
                        <button name="actionParam" value="SAVE_ALL">Update all and SAVE the List</button>
                    </td>

                    <td colspan="3">
                        <button name="actionParam" value="ORDER_ALL">Update all and ORDER the List</button>
                    </td>
                </tr>
            </form:form>

        </table>
    </div>


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>

