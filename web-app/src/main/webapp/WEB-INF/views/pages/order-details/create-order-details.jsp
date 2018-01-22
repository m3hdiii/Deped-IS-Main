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
                <h1>Order Items
                    <small>Goods, Semi-Expandable, and Equipment</small>
                </h1>
            </div>

            <c:set var="orderName" value="orderSessionNo${orderId}"/>
            <c:set var="relatedOrder" value="${sessionScope[orderName]}"/>

            <c:set var="orderIdValue" value="${relatedOrder.orderId}"/>
            <c:set var="basketName" value="orderDetailsMap-OrderNo${orderIdValue}"/>

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

                <!--<div class="col-md-12">
                    <div class="col-md-5">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="no-margn">Order Information</h4>
                            </div>
                            <div class="panel-body">
                                <p>Date Required:</p>
                                <p>Schedule:</p>
                                <p>Budget Amount:</p>
                            </div>
                        </div>

                    </div>

                </div>-->


                <div class="col-md-12">
                    <div class="panel panel-default no-panel-border">
                        <div class="panel-body">
                            <nav class="col-md-12 padd-t-lg">

                                <%--<form role="search" class="col-md-6 col-lg-offset-3 hidden-xs">
                                    <div class="app-search">
                                        <input type="text" placeholder="Search for an item..."
                                               class="form-control form-control-circle">
                                        <hr class="clean">
                                    </div>
                                </form>--%>

                                <ul class="list-inline col-md-12">

                                    <%-- <li class="pull-right">
                                         <a href="#" class="text-purple"><i class="fa fa-list fa-lg"></i> </a>
                                     </li>

                                     <li class="pull-right active">
                                         <a href="#" class="text-purple"><i class="fa fa-th-large fa-lg"></i></a>
                                     </li>

                                     <li>
                                         <div class="form-inline">
                                             <label for="sortingItem">Sort by:</label>
                                             <select id="sortingItem" class="form-control form-control-flat input-sm">
                                                 <option>Name: A-Z</option>
                                                 <option>Name: Z-A</option>
                                                 <option>Quantity: Ascending</option>
                                                 <option>Quantity: Decending</option>
                                             </select>
                                         </div>
                                     </li>

                                     <li>
                                         <div class="form-inline">
                                             <label for="itemType">Type:</label>
                                             <select id="itemType" class="form-control input-sm">
                                                 <option>All</option>
                                                 <option>Goods</option>
                                                 <option>Semi-Expandable</option>
                                                 <option>Equipment</option>
                                             </select>
                                         </div>
                                     </li>

                                     <li>
                                         <div class="form-inline">
                                             <label for="filterBrand">Brand:</label>
                                             <select id="filterBrand" class="form-control input-sm">
                                                 <option>Brand Name</option>
                                                 <option>Brand Name</option>
                                                 <option>Choose Brand Name Here</option>
                                                 <option>Choose Brand Naem Here</option>
                                             </select>
                                         </div>
                                     </li>--%>
<!--START========================================================================================== -->
                                    <c:set var="basketMap" value="${sessionScope[basketName]}"/>
                                    <li class="dropdown">
                                        <div class="order-list-container">
                                            <a href="#" class="btn btn-default btn-sm order-btn" data-toggle="dropdown"><i
                                                    class="fa fa-file-text-o fa-lg"></i></a>
                                            <span class="badge bg-red badge-overlap">${basketMap.size()}</span>
                                            <div class="dropdown-menu arrow pull-left md panel panel-default arrow-top-left no-padd">
                                                <div class="panel-heading">
                                                    Item to Order
                                                </div>
                                                <c:choose>
                                                    <c:when test="${not empty basketMap}">
                                                        <div class="panel-body no-padd">
                                                            <table cellpadding="0" cellspacing="0" border="0"
                                                                   class="table table-striped table-bordered no-padd">
                                                                <thead>
                                                                <th>Items</th>
                                                                <th>Total Quantity</th>
                                                                </thead>
                                                                <tbody>
                                                                <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                                                    <c:set var="orderDet" value="${entry.value}"/>
                                                                    <tr>
                                                                        <td>${orderDet.item.name}</td>
                                                                        <td>${orderDet.totalQuantityRequestNo}</td>
                                                                    </tr>
                                                                </c:forEach>
                                                                </tbody>
                                                            </table>
                                                            <c:if test="${not empty basketMap}">
                                                                <a href="/order-details/basket/${orderIdValue}"
                                                                   class="btn btn-info btn-flat btn-block"> View All and Details </a>
                                                            </c:if>
                                                        </div>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="panel-body text-center">
                                                            <hr class="clean">
                                                            <div class="order-item-content">
                                                                <i class="fa fa-list-alt fa-5x text-primary"></i>
                                                                <h4><strong>Your Don't Have Item Yet</strong></h4>
                                                                <p>Browse the Orders that You Saved.</p>
                                                                <button type="button" class="btn btn-primary">View other Order
                                                                </button>
                                                            </div>
                                                            <hr class="clean">
                                                        </div> <!--end of panel body-->
                                                    </c:otherwise>
                                                </c:choose>

                                                <%--<div class="panel-body text-center">
                                                    <hr class="clean">
                                                    <div class="order-item-content">
                                                        <i class="fa fa-file-text-o fa-5x text-primary"></i>
                                                        <h4><strong>Your Don't Have Item Yet</strong></h4>
                                                        <p>Browse the Orders that You Saved.</p>
                                                        <button type="button" class="btn btn-primary">View other Order
                                                        </button>
                                                    </div>
                                                    <hr class="clean">
                                                </div> <!--end of panel body-->
                                                <!--<div class="panel-body no-padd">
                                                    <table cellpadding="0" cellspacing="0" border="0"
                                                           class="table table-striped table-bordered no-padd">
                                                        <thead>
                                                        <th>Items</th>
                                                        <th>Total QTY</th>
                                                        </thead>
                                                        <tbody>
                                                        <tr>
                                                            <td>Item Name Here</td>
                                                            <td>Total QTY</td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                    <a href="#" class="btn btn-info btn-flat btn-block"> View All and Details</a>
                                                </div>-->--%>
                                            </div>
                                        </div>
                                    </li>
                                    <!--END========================================================================================== -->
                                </ul>
                            </nav>

                            <div class="order-body-container col-md-12 no-padd">
                                <div class="order-body col-md-12 no-padd">

                                    <!-- Items-thumbnail-and-content-of-the-item-thumbnail -->
                                    <c:forEach items="${itemList}" var="item" varStatus="loop">
                                        <form:form class="col-md-4 thumbnail order-content-thumbnail" commandName="orderDetail" id="orderDetails${loop.index}" method="post">
                                            <div class="col-md-12 text-center no-padd">
                                                <c:choose>
                                                    <c:when test="${not empty item.picName}">
                                                        <img width="60%" src="${baseUrl}${item.picName}" alt="item image"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="${resourceURL}/images/shared-images/no-item.png"
                                                                                  alt="item image" width="60%"/>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>

                                            <div class="item-infomation text-center no-padd col-md-12">
                                                <div>
                                                    <h4 class="no-margn margn-t-xs">${item.name}</h4>
                                                    <p class="no-margn margn-t-xs"><strong>${item.itemType}</strong></p>
                                                </div>
                                                <table cellpadding="0" cellspacing="0" border="0"
                                                       class="table table-hover no-padd">
                                                    <tbody>
                                                    <tr>
                                                        <th>Remaining</th>
                                                        <td>
                                                            ${item.quantity}
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Capacity / Unit</th>
                                                        <td class="col-md-12">
                                                            <div class="form-group col-md-6 no-margn no-padd">
                                                                <form:input id="num1${loop.index}" onkeyup="lockDownTotal('${loop.index}')" class="form-control input-sm" type="number" path="unitCapacity"/>
                                                            </div>
                                                            <%--<h3 class="col-md-2 no-margn no-padd">&#x2215;</h3>--%>

                                                            <div class="form-group col-md-6">
                                                                <form:select id="unit${loop.index}" class="form-control input-sm" path="unit">
                                                                    <form:options items="${units}" itemLabel="name"
                                                                                  itemValue="name"/>
                                                                </form:select>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>No. of Units</th>
                                                        <td>
                                                            <div class="form-group no-margn">
                                                                <form:input id="num2${loop.index}" onkeyup="lockDownTotal('${loop.index}')" class="form-control input-sm" type="number" min="0" path="noOfUnits"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Total Qty</th>
                                                        <td>
                                                            <div class="form-group no-margn">
                                                                <form:input id="total${loop.index}" onkeyup="lockDownTheFirstTwo('${loop.index}')" class="form-control input-sm" type="number" min="0" path="totalQuantityRequestNo"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Unit Price</th>
                                                        <td>
                                                            <div class="form-group no-margn">
                                                                <form:input class="form-control input-sm" type="text" path="unitPrice"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Category</th>
                                                        <td>
                                                            <div class="form-group no-margn no-padd">
                                                                <form:select class="form-control input-sm" path="category">
                                                                    <form:options items="${categories}" itemLabel="name"
                                                                                  itemValue="name"/>
                                                                </form:select>

                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <th>Supplier</th>
                                                        <td>
                                                            <div class="form-group no-margn no-padd">
                                                                <form:select class="form-control input-sm" path="supplier">
                                                                    <form:options items="${suppliers}" itemLabel="name" itemValue="supplierId"/>
                                                                </form:select>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>

                                                <input type="hidden" name="order" value="${relatedOrder.orderId}"/>
                                                <input type="hidden" name="item" value="${item.name}"/>

                                                <div class="button-group form-group">
                                                    <button class="btn btn-md btn-purple btn-block" type="submit">Add to Order</button>
                                                </div>
                                            </div>
                                        </form:form>
                                    </c:forEach>
                                </div>

                                <!-- PAGINATION FOR THE ITEMS -->
                                <div class="items-pagination col-md-12">
                                    <ul class="list-inline pull-right">
                                        <li>
                                            <div class="form-group no-margn pull-right">
                                                <select class="form-control">
                                                    <option>12</option>
                                                    <option>24</option>
                                                    <option>100</option>
                                                    <option>120</option>
                                                </select>
                                            </div>

                                        </li>
                                        <li>
                                            <div aria-label="Pagination-for-items">
                                                <ul class="pagination no-margn">
                                                    <li class="page-item disabled">
                                                        <a class="page-link" href="#" aria-label="Previous">
                                                            <span aria-hidden="true">&laquo;</span>
                                                            <span class="sr-only">Previous</span>
                                                        </a>
                                                    </li>
                                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                                                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                                                    <li class="page-item">
                                                        <a class="page-link" href="#" aria-label="Next">
                                                            <span aria-hidden="true">&raquo;</span>
                                                            <span class="sr-only">Next</span>
                                                        </a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </li>

                                    </ul>

                                </div>
                            </div>
                        </div> <!-- end of panel body -->
                    </div>
                </div>


            </div>

        </div>
        <!-- Warper Ends Here (working area) -->



    <%--<div class="warper container-fluid">

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
                                <th class="col-sm-1">Unit Capacity</th>
                                <th class="col-sm-1">Unit</th>
                                <th class="col-sm-1">No. of Units</th>
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
                                            <form:input class="col-sm-2" type="number" min="0" path="unitCapacity"/>
                                        </div>
                                    </td>

                                    <td class="col-sm-4">
                                        <form:select class="col-sm-4" path="unit">
                                            <form:options class="col-sm-4" items="${units}" itemLabel="name"
                                                          itemValue="name"/>
                                        </form:select>
                                    </td>

                                    <td class="col-sm-2">
                                        <form:input class="col-sm-2" type="number" min="0" path="noOfUnits"/>
                                    </td>

                                    <td class="col-sm-2">
                                        <form:input class="col-sm-2" ype="number" min="0" path="totalQuantityRequestNo"/>
                                    </td>


                                    <td class="col-sm-4">
                                        <form:select class="col-sm-4" path="category">
                                            <form:options class="col-sm-4" items="${categories}" itemLabel="name"
                                                          itemValue="name"/>
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
                                    <input type="hidden" name="item" value="${item.name}"/>

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

    </div>--%>

            <c:import url="../../includes/footer.jsp"/>

        <script src="${resourceURL}/js/main/main.js"></script>

</body>
</html>

