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
    <c:param name="title" value="Create Request"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">

            <div class="page-header">
                <h1>Request Items
                    <small>Goods, Semi-Expandable, and Equipment</small>
                </h1>
            </div>

            <c:set var="requestName" value="requestSessionNo${requestId}"/>
            <c:set var="relatedRequest" value="${sessionScope[requestName]}"/>

            <div class="row">

                <div class="panel panel-default no-panel-border">

                    <c:set var="requestIdValue" value="${relatedRequest.requestId}"/>
                    <c:set var="basketName" value="requestDetailsMap-RequestNo${requestIdValue}"/>
                    <c:set var="errors"
                           value="${requestScope['org.springframework.validation.BindingResult.requestDetails'].allErrors}"/>
                    <c:if test="${not empty errors}">
                        <div>
                            <ul class="list-group">
                                <c:forEach items="${errors}" var="error" varStatus="loop">
                                    <li class="list-group-item list-group-item-warning text-danger"><span
                                            class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>

                    <div class="panel-body">
                        <nav class="col-md-12 padd-t-lg">

                            <%--<form role="search" class="col-md-6 col-lg-offset-3 hidden-xs">
                                <div class="app-search">
                                    <input type="text" disabled placeholder="Search for an item..."
                                           class="form-control form-control-circle">
                                    <hr class="clean">
                                </div>

                            </form>--%>

                            <ul class="list-inline col-md-12">
                                <%--
                                    <li class="pull-right">
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
                                <c:set var="basketMap" value="${sessionScope[basketName]}"/>
                                <li class="dropdown">
                                    <div class="order-list-container">
                                        <a href="#" class="btn btn-default btn-sm order-btn" data-toggle="dropdown"><i
                                                class="fa fa-list-alt fa-lg"></i></a>
                                        <span class="badge bg-red badge-overlap">${basketMap.size()}</span>
                                        <div class="dropdown-menu arrow pull-left md panel panel-default arrow-top-left no-padd">
                                            <div class="panel-heading">
                                                Item to Request
                                            </div>

                                            <c:choose>
                                                <c:when test="${not empty basketMap}">
                                                    <div class="panel-body no-padd">
                                                        <table cellpadding="0" cellspacing="0" border="0"
                                                        class="table table-striped table-bordered no-padd">
                                                            <thead>
                                                                <th>Items</th>
                                                                <th>Quantity</th>
                                                            </thead>
                                                            <tbody>
                                                            <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                                                <c:set var="requestDet" value="${entry.value}"/>
                                                                <tr>
                                                                    <td>${requestDet.item.name}</td>
                                                                    <td>${requestDet.requestQuantity}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                        <c:if test="${not empty basketMap}">
                                                            <a href="/request-details/basket/${requestIdValue}"
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
                                                            <p>Browse the Request that You Saved.</p>
                                                            <button type="button" class="btn btn-primary">View other Request
                                                            </button>
                                                        </div>
                                                        <hr class="clean">
                                                    </div> <!--end of panel body-->
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </nav>

                        <div class="request-body-container col-md-12 no-padd">
                            <div class="item-body col-md-12 no-padd">
                                <!-- Items-thumbnail-and-content-of-the-item-thumbnail -->
                                <c:forEach items="${itemList}" var="item" varStatus="loop">
                                    <form:form class='col-xs-3 thumbnail item-content-thumbnail' commandName="requestDetails"
                                               method="post">
                                <%--<form class='col-md-3 thumbnail item-content-thumbnail'>--%>
                                    <div class="col-md-12 no-padd">
                                        <c:choose>
                                            <c:when test="${not empty item.picName}">
                                                <img src="${baseUrl}${item.picName}" alt="item image" width="100%"/>
                                            </c:when>
                                            <c:otherwise>
                                                <img src="${resourceURL}/images/shared-images/no-item.png" alt="item image"
                                                     width="100%"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="item-infomation text-center col-md-12">
                                        <h4>${item.name}</h4>
                                        <div class="form-group clearfix">
                                            <h5>${item.itemType}</h5>
                                        </div>
                                        <div class="form-group">
                                            <label>Available QTY:</label>
                                            <p>${item.quantity}</p>

                                        </div>
                                        <div class="form-group clearfix">
                                            <label for="itemReqQty${item.name}" class="col-md-12">Request QTY:</label>
                                            <div class="col-md-6 col-md-push-3">
                                                <form:input path="requestQuantity"
                                                            class="form-control form-control-flat input-sm text-center no-padd"
                                                            type="number" id="itemReqQty${item.name}" min="0"
                                                            max="${item.quantity}"
                                                            value="0"/>
                                            </div>
                                        </div>

                                        <form:hidden path="item" value="${item.name}"/>
                                        <form:hidden path="request" value="${requestIdValue}"/>
                                        <form:hidden path="requestDetailsID.itemName" value="${item.name}"/>
                                        <form:hidden path="requestDetailsID.requestId" value="${requestIdValue}"/>

                                        <div class="button-group form-group">
                                            <button class="btn btn-md btn-purple btn-block" type="submit">Add to Request</button>
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
        <!-- Warper Ends Here (working area) -->





    <%--<div class="warper container-fluid">

        <div class="page-header">
            <h3>Order Request
                <small>for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <c:set var="requestName" value="requestSessionNo${requestId}"/>
        <c:set var="relatedRequest" value="${sessionScope[requestName]}"/>

        <div class="row">
            <p>Request Number: ${relatedRequest.requestId}</p>
            <p>Request
                By: ${relatedRequest.user.firstName}&nbsp;${relatedRequest.user.middleName}&nbsp;${relatedOrder.user.lastName}</p>
            <p>Creation Date: ${relatedRequest.requestDate}</p>
        </div>

        <c:set var="requestIdValue" value="${relatedRequest.requestId}"/>
        <c:set var="basketName" value="requestDetailsMap-RequestNo${requestIdValue}"/>
        <c:set var="errors"
               value="${requestScope['org.springframework.validation.BindingResult.requestDetails'].allErrors}"/>
        <c:if test="${not empty errors}">
            <div>
                <ul class="list-group">
                    <c:forEach items="${errors}" var="error" varStatus="loop">
                        <li class="list-group-item list-group-item-warning text-danger"><span
                                class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>

        <div class="row">

            <div class="request-body-container">
                <nav class="col-md-12 padd-t-lg">

                    <form role="search" class="col-md-6 col-lg-offset-3 hidden-xs">
                        <div class="app-search">
                            <input type="text" placeholder="Search for an item..."
                                   class="form-control form-control-circle">
                            <hr class="clean">
                        </div>

                    </form>


                    <ul class="list-inline col-md-12">
                        <li class="pull-right">
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
                        </li>
                    </ul>
                </nav>

                <div class="item-body col-md-9">
                    <!-- Items-thumbnail-and-content-of-the-item-thumbnail -->

                    <c:forEach items="${itemList}" var="item" varStatus="loop">
                        <form:form class='col-xs-3 thumbnail item-content-thumbnail' commandName="requestDetails"
                                   method="post">
                            <c:choose>
                                <c:when test="${not empty item.picName}">
                                    <img src="${baseUrl}${item.picName}" alt="item image" width="304px"/>
                                </c:when>
                                <c:otherwise>
                                    <img src="${resourceURL}/images/shared-images/no-item.png" alt="item image"
                                         width="304px"/>
                                </c:otherwise>
                            </c:choose>

                            <div class="item-infomation text-center">
                                <h4>${item.name}</h4>
                                <div class="form-group">
                                    <label>Available QTY:</label>
                                    <p>${item.quantity}</p>
                                </div>
                                <div class="form-group clearfix">
                                    <label for="itemReqQty${item.name}" class="col-md-12">Request QTY:</label>
                                    <div class="col-md-6 col-md-push-3">
                                        <form:input path="requestQuantity"
                                                    class="form-control input-sm text-center no-padd"
                                                    type="number" id="itemReqQty${item.name}" min="0"
                                                    max="${item.quantity}"
                                                    value="0"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <h5>${item.itemType}</h5>
                                </div>

                                <form:hidden path="item" value="${item.name}"/>
                                <form:hidden path="request" value="${requestIdValue}"/>
                                <form:hidden path="requestDetailsID.itemName" value="${item.name}"/>
                                <form:hidden path="requestDetailsID.requestId" value="${requestIdValue}"/>

                                <div class="form-group">
                                    <button class="btn btn-md btn-purple" type="submit">Add to Request</button>
                                </div>
                            </div>
                        </form:form>
                    </c:forEach>
                </div>

                <!--   View Item Request -->
                <div class="col-md-3">
                    <div class="panel panel-default no-padd">
                        <div class="panel-heading clean">
                            <span> Item Request</span>
                            <a href="#"><i class="fa fa-refresh text-green pull-right"></i></a>
                        </div>
                        <div class="panel-body no-padd">
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class="table table-striped table-bordered no-padd">
                                <thead>
                                <th>Items</th>
                                <th>Quantity</th>
                                </thead>
                                <tbody>
                                <c:set var="basketMap" value="${sessionScope[basketName]}"/>
                                <c:choose>
                                    <c:when test="${not empty basketMap}">
                                        <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                                            <c:set var="requestDet" value="${entry.value}"/>
                                            <tr>
                                                <td>${requestDet.item.name}</td>
                                                <td>${requestDet.requestQuantity}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td colspan="2">No requested item in the table</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>


                                </tbody>
                            </table>
                            <c:if test="${not empty basketMap}">
                                <a href="/request-details/basket/${requestIdValue}"
                                   class="btn btn-info btn-flat btn-block"> View All </a>
                            </c:if>
                        </div>
                    </div>
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
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->--%>
        <c:import url="../../includes/footer.jsp"/>

</body>
</html>

