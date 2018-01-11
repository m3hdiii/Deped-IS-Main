<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Order Manager"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">
            <div class="page-header">
                <h1>Ordered Items
                    <small>Goods, Semi-Expandable, and Equipment</small>
                </h1>
            </div>

            <div class="row">
                <div class="col-md-12 no-padd">
                    <div class="col-md-3 no-padd padd-t-xs">
                        <div class="panel panel-default col-md-12 no-padd">
                            <div class="panel-heading">Filter</div>
                            <form:form commandName="orderSearch">
                                <div class="panel-body padd-sm">
                                    <a href="#" class="pull-right" type="reset">Clear all</a>

                                    <div class="col-md-12 no-padd">
                                        <label class="col-md-12 no-padd">Order Date</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input id="orderDateFromId" path="orderDateFrom"
                                                        onclick="clickOnDateInput('orderDateFromId')" type="text"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="MM/DD/YYY"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input id="orderDateToId" path="orderDateTo"
                                                        onclick="clickOnDateInput('orderDateToId')" type="text"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="MM/DD/YYY"/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd">
                                        <label class="col-md-12 no-padd">Required Date</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input id="orderDateFromId" path="requiredDateFrom"
                                                        onclick="clickOnDateInput('orderDateFromId')" type="text"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="MM/DD/YYY"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input id="orderDateToId" path="requiredDateTo"
                                                        onclick="clickOnDateInput('orderDateToId')" type="text"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="MM/DD/YYY"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd margn-t-sm">
                                            <label for="reqUserList">Ordered Users</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         multiple="true" path="users"
                                                         id="reqUserList" items="${userList}" itemLabel="username"
                                                         itemValue="username" data-placeholder="Choose who order..."/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="schedulesId">Schedules</label>
                                            <form:select path="orderSchedules" items="${schedules}" itemLabel="name"
                                                         class="form-control form-control-flat in1put-sm chosen-select"
                                                         id="schedulesId"
                                                         multiple="true" data-placeholder="Choose Order Schedules ..."/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label class="col-md-12 no-padd">Budget Amount</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="budgetAmountFrom" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="budgetAmountTo" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="orderStatesId">Order States</label>
                                            <form:select path="orderStates" items="${orderStates}" itemLabel="name"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         id="orderStatesId"
                                                         multiple="true" data-placeholder="Choose Order States ..."/>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-12 no-padd margn-t-sm">
                                        <label for="userMessage">Arrival Description Contains</label>
                                        <form:input path="arrivalDescription" type="text"
                                                    class="form-control form-control-flat input-sm" id="userMessage"
                                                    placeholder="Enter Arrival Description words ..."/>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="itemList">Items</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="itemList" path="items" items="${itemList}" itemLabel="name"
                                                         itemValue="name" multiple="true"
                                                         data-placeholder="Choose Items..."/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="itemList">Categories</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="categoryId" path="categories" items="${categoryList}"
                                                         itemLabel="name" itemValue="name" multiple="true"
                                                         data-placeholder="Choose Categories..."/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="itemList">Units</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="categoryId" path="units" items="${unitList}"
                                                         itemLabel="name" itemValue="name" multiple="true"
                                                         data-placeholder="Choose Units..."/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label class="col-md-12 no-padd">Unit Price</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="unitPriceFrom" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="unitPriceTo" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label class="col-md-12 no-padd">Unit capacity</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="unitCapacityFrom" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="unitCapacityTo" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label class="col-md-12 no-padd">No of units</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="noOfUnitsFrom" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="noOfUnitsTo" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label class="col-md-12 no-padd">Total quantity requests</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="totalQuantityRequestNoFrom" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="totalQuantityRequestNoTo" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                    </div>

                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label class="col-md-12 no-padd">Total quantity Arrived</label>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="totalQuantityArrivedNoFrom" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input path="totalQuantityArrivedNoTo" type="number" min="0"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="Enter a number"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="statesId">Order Details States</label>
                                            <form:select path="orderDetailsStates" items="${orderDetailsStateList}"
                                                         itemLabel="name"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         id="statesId" multiple="true"
                                                         data-placeholder="Order Details State..."/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="itemList">Suppliers</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="supplierId" path="suppliers" items="${suppliers}"
                                                         itemLabel="name" itemValue="name" multiple="true"
                                                         data-placeholder="Suppliers..."/>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-12 no-padd margn-t-sm">
                                        <label for="userMessage">Disapproval Message</label>
                                        <form:input path="disapprovalMessage" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    id="disapprovalMessageId"
                                                    placeholder="Enter Disapproval Message ..."/>
                                    </div>

                                    <div class="form-group col-md-12 no-padd margn-t-sm">
                                        <label for="userMessage">Not Arrival Message</label>
                                        <form:input path="notArrivalMessage" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    id="disapprovalMessageId"
                                                    placeholder="Enter Not Arrival Message ..."/>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd margn-t-sm">
                                            <label for="orderedByUserList1">Ordered By Users</label>
                                            <form:select path="orderedByUsers" items="${userList}"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         id="orderedByUserList1" multiple="true"
                                                         data-placeholder="Choose who Ordered..."
                                                         itemLabel="username" itemValue="username"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd margn-t-sm">
                                            <label for="receivedByListId">Received By Users</label>
                                            <form:select path="receivedByUsers" items="${userList}"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         id="receivedByListId" multiple="true"
                                                         data-placeholder="Choose who received..."
                                                         itemLabel="username" itemValue="username"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd margn-t-sm">
                                            <label for="consideredByListId2">Considered By Users</label>
                                            <form:select path="consideredByUsers" items="${userList}"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         id="consideredByListId2" multiple="true"
                                                         data-placeholder="Choose who considered..."
                                                         itemLabel="username" itemValue="username"/>
                                        </div>
                                    </div>
                                    <div class="col-md-12 padd-sm">
                                        <button name="web" value="WEB" class="btn btn-sm btn-purple pull-right" data-toggle="tooltip"
                                                title="Save the list">Report Web</button>
                                        <button name="xml" value="XML" class="btn btn-sm btn-success" data-toggle="tooltip"
                                                title="Send the Order">Import Excel</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                    <!-- Report Body -->
                    <div class="col-md-9 no-padd padd-xs">
                        <div class="panel panel-default col-md-12 no-padd">
                            <div class="panel-heading clean text-center">Report Result</div>
                            <div class="panel-body no-padd padd-t-md">
                                <table cellpadding="0" cellspacing="0" border="0" class="table table-hover"`>
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>User</th>
                                        <th>User Message</th>
                                        <th>Date Ordered</th>
                                        <th>Item Type</th>
                                        <th>Status</th>
                                        <th>More Info</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${orders}" var="order">
                                        <tr>
                                            <td>${order.orderId}</td>
                                            <td>${order.user.firstName} ${order.user.lastName}</td>
                                            <td>${order.userMessage}</td>
                                            <td>${order.orderDate}</td>
                                            <td>${order.itemType}</td>
                                            <td>${order.orderStatus}</td>
                                            <td>
                                                <a class="btn btn-primary btn-sm" role="button"
                                                   href="<c:url value='${orderDetailsInfo}${order.orderId}'/>">Details</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- Warper Ends Here (working area) -->

    <%--<div class="warper container-fluid">
        <div class="page-header">
            <h1>Requested Items
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div>
            <form:form cssClass="form-inline" commandName="requestSearch">
                <table class="table-bordered">
                    <tbody>
                        <tr>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="requestDateForm">Request Date From: </label><form:input type="date" id="requestDateForm" path="requestDateFrom" cssClass="form-control"
                                                                                                        placeholder="request date fro"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="requestDateTo">Request Date To: </label><form:input type="date" path="requestDateTo" cssClass="form-control"
                                                                                                    placeholder="requested date to"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="items">Items:</label>
                                    <form:select multiple="true" id="items" path="items" items="${itemList}" itemLabel="name" itemValue="name"
                                                 cssClass="form-control"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="requestedUsers">Requested Users:</label>
                                    <form:select multiple="true" path="requestedUsers" id="requestedUsers" items="${userList}" itemLabel="username"
                                                 itemValue="username" cssClass="form-control" placeholder="requested users"/>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <div class="form-group">
                                    <label for="consideredByUsers">Considered By Users: </label><form:select multiple="true" id="consideredByUsers" path="consideredByUsers" items="${userList}"
                                                                                                             itemLabel="username" itemValue="username"
                                                                                                             cssClass="form-control"/>
                                </div>
                            </td>
                            <td style="padding: 10px;">
                                <label for="issuedByUsers">Issued By Users: </label><form:select multiple="true" id="issuedByUsers" path="issuedByUsers" items="${userList}"
                                                                                                 itemLabel="username" itemValue="username"
                                                                                                 cssClass="form-control"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="userMessage">User Message Contain: </label><form:input path="userMessage" id="userMessage" cssClass="form-control"
                                                                        placeholder="user message contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="adminNotice">Admin Notice Contains: <form:input path="adminNotice" id="adminNotice" cssClass="form-control"
                                                                         placeholder="admin notice"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="disapprovalMessage">Disapproval Message Contains: </label><form:input path="disapprovalMessage" id="disapprovalMessage" cssClass="form-control"
                                                                                placeholder="Disapproval Message Contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="cancellationReason">Cancellation Reason Contains: </label><form:input path="cancellationReason" id="cancellationReason" cssClass="form-control"
                                                                                placeholder="Cancellation Reason Contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="supplyOfficeRemark">Supply Officer Remark Contains: </label><form:input path="supplyOfficeRemark" id="supplyOfficeRemark" cssClass="form-control"
                                                                                  placeholder="Supply Officer Remark Contains"/>
                            </td>
                            <td style="padding: 10px;">
                                <label>Item Type:</label>
                                     <form:select multiple="true" path="itemTypes" items="${itemTypes}" itemLabel="name"
                                                  class="form-control"/>

                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="requestQuantityFrom">Requested Quantity From: </label><form:input path="requestQuantityFrom" id="requestQuantityFrom" cssClass="form-control"
                                                                           placeholder="Requested Quantity From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="requestQuantityTo">Requested Quantity To: </label><form:input path="requestQuantityTo" id="requestQuantityTo" cssClass="form-control"
                                                                         placeholder="Requested Quantity To"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="approvedQuantityFrom">Approved Quantity From: </label><form:input path="approvedQuantityFrom" id="approvedQuantityFrom" cssClass="form-control"
                                                                          placeholder="Approved Quantity From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="approvedQuantityTo">Approved Quantity To: </label><form:input path="approvedQuantityTo" id="approvedQuantityTo" cssClass="form-control"
                                                                        placeholder="Approved Quantity To"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="approvalDisapprovalDateFrom"></label>Consideration Date From: <form:input type="date" path="approvalDisapprovalDateFrom"
                                                                           class="form-control" id="approvalDisapprovalDateFrom"
                                                                           placeholder="Consideration Date From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="approvalDisapprovalDateTo">Consideration Date To: </label><form:input type="date" path="approvalDisapprovalDateTo"
                                                                         class="form-control" id="approvalDisapprovalDateTo"
                                                                         placeholder="Consideration Date To"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="releaseDateFrom">Release Date From: </label><form:input type="date" id="releaseDateFrom" path="releaseDateFrom" class="form-control"
                                                                     placeholder="Release Date From"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="releaseDateTo">Release Date To: </label><form:input type="date" path="releaseDateTo" id="releaseDateTo" class="form-control"
                                                                   placeholder="Release Date To"/>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 10px;">
                                <label for="requestStatuses">Request Status: </label>
                                    <form:select multiple="true" path="requestStatuses" id="requestStatuses" class="form-control"
                                                 items="${statuses}" itemLabel="name"/>
                            </td>
                            <td style="padding: 10px;">
                                <label for="requestDetailsStatuses">Request Details Status: </label>
                                <form:select multiple="true" path="requestDetailsStatuses" id="requestDetailsStatuses" class="form-control"
                                             items="${detailsStatuses}" itemLabel="name"/>
                            </td>
                            <td style="padding: 10px;">
                                <button name="web" value="WEB" class="btn btn-primary"
                                        data-toggle="tooltip"
                                        title="Save the list">Report Web
                                </button>
                            </td>
                            <td style="padding: 10px;">
                                <button name="xml" value="XML" class="btn btn-success"
                                        data-toggle="tooltip"
                                        title="Send the Request">Report XMl
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>

            </form:form>

            <br/>
            <br/>
            <br/>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading clean text-center">Report Result</div>
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-hover" id="basic-datatable">
                    <thead>
                    <tr>
                        <th>Request ID</th>
                        <th>User</th>
                        <th>User Message</th>
                        <th>Date Requested</th>
                        <th>Item Type</th>
                        <th>Status</th>
                        <th>More Info</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requests}" var="request">
                        <tr>
                            <td>${request.requestId}</td>
                            <td>${request.user.firstName} ${request.user.middleName} ${request.user.lastName}</td>
                            <td>${request.userMessage}</td>
                            <td>${request.requestDate}</td>
                            <td>${request.itemType}</td>
                            <td>${request.requestStatus}</td>
                            <td>
                                <a class="btn btn-info" role="button"
                                   href="<c:url value='${requestDetailsInfo}${request.requestId}' />">request
                                    details</a>
                            </td>
                            <td>
                                <a class="btn btn-info" role="button"
                                   href="<c:url value='${requestDetailsInfo}${request.requestId}' />">request
                                    details</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>--%>

        <c:import url="../../includes/footer.jsp"/>

        <script type="text/javascript" src="${resourceURL}/js/main/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${resourceURL}/js/main/main.js"></script>
</body>
</html>