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
    <c:param name="title" value="Create Order"/>
    <c:param name="description" value="Create Order Page"/>
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

        <c:set var="orderIdValue" value="${relatedOrder.orderId}"/>
        <c:set var="basketName" value="orderDetailsMap-OrderNo${orderIdValue}"/>

        <div>
            <c:if test="${not empty sessionScope[basketName]}">
                <a href="/order-details/basket/${orderIdValue}" class="btn btn-primary"> Check Cart<span>${fn:length(sessionScope[basketName])}</span></a>
            </c:if>
        </div>
        <c:set var="errors"
               value="${requestScope['org.springframework.validation.BindingResult.orderDetail'].allErrors}"/>
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
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-heading">Basic DataTable</div>
                    <div class="panel-body">

                        <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered"
                               id="basic-datatable">
                            <thead>
                            <tr>
                                <th class="col-sm-1">Image</th>
                                <th class="col-sm-1">Name</th>
                                <th class="col-sm-1">Available QTY</th>
                                <th class="col-sm-1">Package Capacity</th>
                                <th class="col-sm-1">Packages</th>
                                <th class="col-sm-1">No. of Packs</th>
                                <th class="col-sm-1">Item QTY</th>
                                <th class="col-sm-1">Category</th>
                                <th class="col-sm-1">Item Type</th>
                                <th class="col-sm-1">Unit Price</th>
                                <th class="col-sm-1">Suppliers</th>
                                <th class="col-sm-1">Add To Order</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${itemList}" var="item" varStatus="loop">
                                <tr>
                                <form:form commandName="orderDetail" id="orderDetails${loop.index}" method="post">

                                    <c:choose>
                                        <c:when test="${not empty item.picName}">
                                            <td class="col-sm-2"><img width="64" src="${baseUrl}${item.picName}" alt="item image"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td class="col-sm-2"><img width="64" src="${resourceURL}/images/shared-images/no-item.png"
                                                                      alt="item image"/>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>

                                    <td class="col-sm-2">${item.name}</td>

                                    <td class="col-sm-2">${item.quantity}</td>

                                    <td class="col-sm-2">
                                        <div class="col-sm-2">
                                            <form:input class="col-sm-2" type="number" min="0" path="packCapacity"/>
                                        </div>
                                    </td>

                                    <td class="col-sm-4">
                                        <form:select class="col-sm-4" path="pack">
                                            <form:options class="col-sm-4" items="${packs}" itemLabel="name" itemValue="packId"/>
                                        </form:select>
                                    </td>

                                    <td class="col-sm-2">
                                        <form:input class="col-sm-2" type="number" min="0" path="noOfPacks"/>
                                    </td>

                                    <td class="col-sm-2">
                                        <form:input class="col-sm-2" ype="number" min="0" path="totalQuantityRequestNo"/>
                                    </td>


                                    <td class="col-sm-4">
                                        <form:select class="col-sm-4" path="category">
                                            <form:options class="col-sm-4" items="${categories}" itemLabel="name" itemValue="categoryId"/>
                                        </form:select>
                                    </td>

                                    <td class="col-sm-3">${item.itemType}</td>

                                    <td class="col-sm-3">
                                        <form:input class="col-sm-3" path="unitPrice"/>
                                    </td>

                                    <td class="col-sm-4">
                                        <form:select class="col-sm-4" path="supplier">
                                            <form:options class="col-sm-4" items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                                        </form:select>
                                    </td>

                                    <input type="hidden" name="order" value="${relatedOrder.orderId}"/>
                                    <input type="hidden" name="item" value="${item.itemId}"/>

                                    <td class="col-sm-2">
                                        <button class="col-sm-2 btn btn-success btn-block" type="submit">Add To Order</button>
                                    </td>
                                    </tr>
                                </form:form>
                            </c:forEach>

                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>

    </div>

            <c:import url="../../includes/footer.jsp"/>

</body>
</html>

