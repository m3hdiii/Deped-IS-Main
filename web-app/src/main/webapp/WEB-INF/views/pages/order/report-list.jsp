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
                                        <%--<a href="#" class="pull-right" type="reset">Clear all</a>--%>

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
                                            <form:input id="requiredDateFrom" path="requiredDateFrom"
                                                        onclick="clickOnDateInput('orderDateFromId')" type="text"
                                                        class="form-control form-control-flat input-sm"
                                                        placeholder="MM/DD/YYY"/>
                                        </div>
                                        <div class="col-md-2 no-padd text-center margn-t-xs">
                                            To
                                        </div>
                                        <div class="form-group col-md-5 no-padd">
                                            <form:input id="requiredDateTo" path="requiredDateTo"
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
                                        <label for="arrivalDescription">Arrival Description Contains</label>
                                        <form:input path="arrivalDescription" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    id="arrivalDescription"
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
                                            <label for="categoryId">Categories</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="categoryId" path="categories" items="${categoryList}"
                                                         itemLabel="name" itemValue="name" multiple="true"
                                                         data-placeholder="Choose Categories..."/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-12 no-padd">
                                            <label for="unitsId">Units</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="unitsId" path="units" items="${unitList}"
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
                                            <label for="supplierId">Suppliers</label>
                                            <form:select class="form-control form-control-flat input-sm chosen-select"
                                                         id="supplierId" path="suppliers" items="${suppliers}"
                                                         itemLabel="name" itemValue="name" multiple="true"
                                                         data-placeholder="Suppliers..."/>
                                        </div>
                                    </div>

                                    <div class="form-group col-md-12 no-padd margn-t-sm">
                                        <label for="disapprovalMessageId">Disapproval Message</label>
                                        <form:input path="disapprovalMessage" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    id="disapprovalMessageId"
                                                    placeholder="Enter Disapproval Message ..."/>
                                    </div>

                                    <div class="form-group col-md-12 no-padd margn-t-sm">
                                        <label for="arrivalMessageId">Not Arrival Message</label>
                                        <form:input path="notArrivalMessage" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    id="arrivalMessageId"
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
                                        <th>Order Date</th>
                                        <th>Required Date</th>
                                        <th>Schedule</th>
                                        <th>Budget Amount</th>
                                        <th>State</th>
                                        <th>Arrival Description</th>
                                        <th>More Info</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${orders}" var="order">
                                        <tr>
                                            <td>${order.orderId}</td>
                                            <td>${order.user.firstName} ${order.user.lastName}</td>
                                            <td>${order.orderDate}</td>
                                            <td>${order.requiredDate}</td>
                                            <td>${order.orderSchedule}</td>
                                            <td>${order.budgetAmount}</td>
                                            <td>${order.orderState}</td>
                                            <td>${order.arrivalDescription}</td>
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
        <c:import url="../../includes/footer.jsp"/>

        <script type="text/javascript" src="${resourceURL}/js/main/bootstrap-datepicker.js"></script>
        <script type="text/javascript" src="${resourceURL}/js/main/main.js"></script>
</body>
</html>