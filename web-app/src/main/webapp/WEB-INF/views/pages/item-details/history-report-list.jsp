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
    <c:param name="title" value="Item Details Manager"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Equipment Information
                <small> report</small>
            </h1>
        </div>

        <div class="row">
            <div class="col-md-12 no-padd">
                <div class="col-md-3 no-padd padd-t-xs">
                    <div class="panel panel-default col-md-12 no-padd">
                        <div class="panel-heading">Filter</div>
                        <form:form commandName="borrowHistorySearch">
                            <div class="panel-body padd-sm">
                                <a href="#" class="pull-right" type="reset">Clear all</a>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="officeSerialNumberId">Office Serial Number</label>
                                        <form:input id="officeSerialNumberId" path="officeSerialNo"
                                                    class="form-control form-control-flat input-sm"
                                                    placeholder="Office Serial Number..."/>
                                    </div>
                                </div>

                                <div class="form-group col-md-12 no-padd">
                                    <label for="ownById">Own By</label>
                                    <form:select id="ownById" path="username" items="${userList}" itemLabel="username"
                                                 itemValue="username"
                                                 class="form-control form-control-flat input-sm chosen-select"
                                                 data-placeholder="Choose who request..."
                                    />
                                </div>

                                <div class="col-md-12 no-padd margn-t-sm">
                                    <label class="col-md-12 no-padd">Borrow Date</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="borrowDateFrom" type="date"
                                                    class="form-control form-control-flat input-sm"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="borrowDateTo" type="date"
                                                    class="form-control form-control-flat input-sm"/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd margn-t-sm">
                                    <label class="col-md-12 no-padd">Return Date</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="returnDateFrom" type="date"
                                                    class="form-control form-control-flat input-sm"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="returnDateTo" type="date"
                                                    class="form-control form-control-flat input-sm"/>
                                    </div>
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
                                    <th>Item</th>
                                    <th>Office Serial No</th>
                                    <th>Colour</th>
                                    <th>Condition</th>
                                    <th>Current Availability</th>
                                    <th>User</th>
                                    <th>Borrow Date</th>
                                    <th>Return Date</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${borrowItemList}" var="borrowItem">
                                    <tr>
                                        <th>${borrowItem.itemDetails.item.name}</th>
                                        <th>${borrowItem.itemDetails.officeSerialNo}</th>
                                        <th>${borrowItem.itemDetails.colour.name}</th>
                                        <th>${borrowItem.itemDetails.condition.name}</th>
                                        <th>${borrowItem.itemDetails.equipmentAvailability.name}</th>
                                        <td>${borrowItem.user.username}</td>
                                        <td>${borrowItem.borrowDate}</td>
                                        <td>${borrowItem.returnDate}</td>
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