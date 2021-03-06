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
    <c:param name="title" value="Request Manager"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Requested Items
                <small>Goods, Semi-Expandable, and Equipment</small>
            </h1>
        </div>

        <div class="row">
            <div class="col-md-12 no-padd">
                <div class="col-md-3 no-padd padd-t-xs">
                    <div class="panel panel-default col-md-12 no-padd">
                        <div class="panel-heading">Filter</div>
                        <form:form commandName="requestSearch">
                            <div class="panel-body padd-sm">
                                    <%--<a href="#" class="pull-right" type="reset">Clear all</a>--%>

                                <div class="col-md-12 no-padd">
                                    <label class="col-md-12 no-padd">Request Date</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="requestDateFromId"
                                                    onclick="clickOnDateInput('requestDateFromId')" name="date"
                                                    type="text" class="form-control form-control-flat input-sm"
                                                    path="requestDateFrom" placeholder="MM/DD/YYY"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="requestDateToId" name="date"
                                                    onclick="clickOnDateInput('requestDateToId')" type="text"
                                                    class="form-control form-control-flat input-sm" path="requestDateTo"
                                                    placeholder="MM/DD/YYY"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-md-12 no-padd">
                                        <label for="itemList">Items</label>
                                        <form:select class="form-control form-control-flat input-sm chosen-select"
                                                     id="itemList" path="items" items="${itemList}" itemLabel="name"
                                                     itemValue="name" multiple="true"
                                                     data-placeholder="Choose Item..."/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="reqUserList">Requested User</label>
                                        <form:select class="form-control form-control-flat input-sm chosen-select"
                                                     multiple="true" path="requestedUsers" id="reqUserList"
                                                     items="${userList}" itemLabel="username"
                                                     itemValue="username" data-placeholder="Choose who request..."/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="consideredUserList">Considered By Users</label>
                                        <form:select class="form-control form-control-flat input-sm chosen-select"
                                                     id="consideredUserList" multiple="true"
                                                     data-placeholder="Choose who considered..."
                                                     path="consideredByUsers" items="${userList}"
                                                     itemLabel="username" itemValue="username"/>
                                    </div>
                                </div>

                                <div class="form-group col-md-12 no-padd margn-t-sm">
                                    <label for="userMessage">User Message Contains</label>
                                    <form:input path="userMessage" type="text"
                                                class="form-control form-control-flat input-sm" id="userMessage"
                                                placeholder="Enter User Message..."/>
                                </div>
                                <div class="form-group col-md-12 no-padd">
                                    <label for="adminNotice">Admin Notice Contains</label>
                                    <form:input path="adminNotice" type="text"
                                                class="form-control form-control-flat input-sm" id="adminNotice"
                                                placeholder="Enter Admin Notice..."/>
                                </div>
                                <div class="form-group col-md-12 no-padd">
                                    <label for="cancelReason">Cancellation Reason Contains</label>
                                    <form:input path="cancellationReason" type="text"
                                                class="form-control form-control-flat input-sm" id="cancelReason"
                                                placeholder="Enter Cancellation Reason..."/>
                                </div>
                                <div class="form-group col-md-12 no-padd">
                                    <label for="officerRemark">Supply Officer Remark Contains</label>
                                    <form:input path="supplyOfficeRemark" type="text"
                                                class="form-control form-control-flat input-sm" id="officerRemark"
                                                placeholder="Enter Supply Officer Remark..."/>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd">
                                        <label for="itemType">Item Type</label>
                                        <form:select path="itemTypes" items="${itemTypes}" itemLabel="name"
                                                     class="form-control form-control-flat input-sm chosen-select"
                                                     id="itemType" multiple="true"
                                                     data-placeholder="Choose Type of Item..."/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd margn-t-sm">
                                    <label class="col-md-12 no-padd">Request Quantity</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="requestQuantityFrom" type="number" min="0"
                                                    class="form-control form-control-flat input-sm" placeholder="0"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="requestQuantityTo" type="number" min="0"
                                                    class="form-control form-control-flat input-sm" placeholder="0"/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd">
                                    <label class="col-md-12 no-padd">Approved Quantity</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="approvedQuantityFrom" type="number" min="0"
                                                    class="form-control form-control-flat input-sm" placeholder="0"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="approvedQuantityTo" type="number" min="0"
                                                    class="form-control form-control-flat input-sm" placeholder="0"/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd">
                                    <label class="col-md-12 no-padd">Consideration Date</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="consideredDateFromId" name="date"
                                                    onclick="clickOnDateInput('consideredDateFromId')"
                                                    path="approvalDisapprovalDateFrom" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    placeholder="MM/DD/YYY"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="consideredDateToId" name="date"
                                                    onclick="clickOnDateInput('consideredDateToId')"
                                                    path="approvalDisapprovalDateTo" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    placeholder="MM/DD/YYY"/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd">
                                    <label class="col-md-12 no-padd">Release Date</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="releaseDateFromId" name="date"
                                                    onclick="clickOnDateInput('releaseDateFromId')"
                                                    path="releaseDateFrom" type="text"
                                                    class="form-control form-control-flat input-sm"
                                                    placeholder="MM/DD/YYY"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="releaseDateToId" name="date"
                                                    onclick="clickOnDateInput('releaseDateToId')" path="releaseDateTo"
                                                    type="text" class="form-control form-control-flat input-sm"
                                                    placeholder="MM/DD/YYY"/>
                                    </div>
                                </div>
                                <div class="form-group col-md-12 no-padd">
                                    <label for="requestStat">Request Status</label>
                                    <form:select path="requestStatuses" items="${statuses}" itemLabel="name"
                                                 class="form-control form-control-flat input-sm chosen-select"
                                                 multiple="true" id="requestStat"/>
                                </div>
                                <div class="form-group col-md-12 no-padd">
                                    <label for="reqDetStat">Request Details Status</label>
                                    <form:select path="requestDetailsStatuses"
                                                 class="form-control form-control-flat input-sm chosen-select"
                                                 id="reqDetStat" multiple="true"
                                                 items="${detailsStatuses}" itemLabel="name"/>
                                </div>

                                <div class="form-group col-md-12 no-padd">
                                    <label for="sectionId">Sections</label>
                                    <form:select path="sections"
                                                 class="form-control form-control-flat input-sm chosen-select"
                                                 id="sectionId" multiple="true"
                                                 items="${sections}" itemLabel="name"
                                                 itemValue="name"
                                                 data-placeholder="Choose Request Section ..."/>
                                </div>


                                <div class="col-md-12 padd-sm">
                                    <button name="web" value="WEB" class="btn btn-sm btn-purple pull-right"
                                            data-toggle="tooltip"
                                            title="Save the list">Report Web
                                    </button>
                                    <button name="xml" value="XML" class="btn btn-sm btn-success" data-toggle="tooltip"
                                            title="Send the Request">Import Excel
                                    </button>
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
                            <table cellpadding="0" cellspacing="0" border="0" class="table table-hover" `>
                                <thead>
                                <tr>
                                    <th>ID</th>
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
                                        <td>${request.user.firstName} ${request.user.lastName}</td>
                                        <td>${request.userMessage}</td>
                                        <td>${request.requestDate}</td>
                                        <td>${request.itemType}</td>
                                        <td>${request.requestStatus}</td>
                                        <td>
                                            <a class="btn btn-primary btn-sm" role="button"
                                               href="<c:url value='${requestDetailsInfo}${request.requestId}'/>">Details</a>
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
    <c:import url="../../includes/footer.jsp"/>

    <script type="text/javascript" src="${resourceURL}/js/main/bootstrap-datepicker.js"></script>
    <script type="text/javascript" src="${resourceURL}/js/main/main.js"></script>
</body>
</html>