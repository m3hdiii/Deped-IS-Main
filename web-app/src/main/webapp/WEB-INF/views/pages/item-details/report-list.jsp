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
                        <form:form commandName="borrowSearch">
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

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="colorListId">Colors</label>
                                        <form:select id="colorListId"
                                                     class="form-control form-control-flat input-sm chosen-select"
                                                     multiple="true" path="colours" items="${colors}" itemLabel="name"
                                                     data-placeholder="Choose the colors..."/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="conditionListId">Conditions</label>
                                        <form:select id="conditionListId" path="conditions" items="${conditions}"
                                                     itemLabel="name"
                                                     class="form-control form-control-flat input-sm chosen-select"
                                                     multiple="true"
                                                     data-placeholder="Choose item condition..."/>
                                    </div>
                                </div>

                                <div class="form-group col-md-12 no-padd margn-t-sm">
                                    <label for="purchasePriceFromId">Purchase Price From</label>
                                    <form:input path="purchasePriceFrom" type="text"
                                                class="form-control form-control-flat input-sm" id="purchasePriceFromId"
                                                placeholder="Purchase Price From..."/>
                                </div>

                                <div class="form-group col-md-12 no-padd">
                                    <label for="purchasePriceToId">Purchase Price To</label>
                                    <form:input path="purchasePriceTo" type="text"
                                                class="form-control form-control-flat input-sm" id="purchasePriceToId"
                                                placeholder="Purchase Price To..."/>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="equipmentAvailabilitiesId">Equipment Availabilities</label>
                                        <form:select id="equipmentAvailabilitiesId" path="equipmentAvailabilities"
                                                     items="${equipmentAvailabilities}" itemLabel="name"
                                                     class="form-control form-control-flat input-sm chosen-select"
                                                     multiple="true"
                                                     data-placeholder="Choose Equipment Availability..."/>
                                    </div>
                                </div>

                                <div class="form-group col-md-12 no-padd">
                                    <label for="equipmentSerialNoId">Equipment Serial Number</label>
                                    <form:input path="equipmentSerialNo" type="text"
                                                class="form-control form-control-flat input-sm" id="equipmentSerialNoId"
                                                placeholder="Equipment Serial Number..."/>
                                </div>

                                <div class="col-md-12 no-padd margn-t-sm">
                                    <label class="col-md-12 no-padd">Creation Date</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="creationDateFrom" type="date"
                                                    class="form-control form-control-flat input-sm"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="creationDateTo" type="date"
                                                    class="form-control form-control-flat input-sm"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="materialId">Materials</label>
                                        <form:select id="materialId" path="materials" items="${materials}"
                                                     itemLabel="name"
                                                     class="form-control form-control-flat input-sm chosen-select"
                                                     multiple="true"
                                                     data-placeholder="Materials ..."/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd">
                                    <label class="col-md-12 no-padd">Weight in Gram</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="weightInGramFrom" type="number" min="0"
                                                    class="form-control form-control-flat input-sm" placeholder="0"/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input path="weightInGramTo" type="number" min="0"
                                                    class="form-control form-control-flat input-sm" placeholder="0"/>
                                    </div>
                                </div>

                                <div class="col-md-12 no-padd">
                                    <label class="col-md-12 no-padd">Life Span</label>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="lifeSpanFromId" path="lifeSpanFrom" type="number" min="0"
                                                    max="120" class="form-control form-control-flat input-sm"
                                                    placeholder="life span from number ..."/>
                                    </div>
                                    <div class="col-md-2 no-padd text-center margn-t-xs">
                                        To
                                    </div>
                                    <div class="form-group col-md-5 no-padd">
                                        <form:input id="lifeSpanToId" path="lifeSpanTo" type="number" min="0" max="120"
                                                    class="form-control form-control-flat input-sm"
                                                    placeholder="life span to number ..."/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-md-12 no-padd margn-t-sm">
                                        <label for="itemsId">Items</label>
                                        <form:select id="itemsId" path="items" items="${itemList}" itemLabel="name"
                                                     itemValue="name"
                                                     class="form-control form-control-flat input-sm chosen-select"
                                                     multiple="true"
                                                     data-placeholder="Choose Items ..."/>
                                    </div>
                                </div>

                                <div class="form-group col-md-12 no-padd">
                                    <label for="ownById">Own By</label>
                                    <form:select id="ownById" path="ownBy" items="${userList}" itemLabel="username"
                                                 itemValue="username"
                                                 class="form-control form-control-flat input-sm chosen-select"
                                                 multiple="true"
                                                 data-placeholder="Choose who request..."
                                    />
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
                                    <th>Office Serial Number</th>
                                    <th>Color</th>
                                    <th>Condition</th>
                                    <th>Purchase Price</th>
                                    <th>Availability</th>
                                    <th>equipmentSerialNo</th>
                                    <th>Material</th>
                                    <th>Weight In Gram</th>
                                    <th>Life Span</th>
                                    <th>Owner</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${itemDetailsList}" var="itemDetails">
                                    <tr>
                                        <th>${itemDetails.item.name}</th>
                                        <td>${itemDetails.officeSerialNo}</td>
                                        <td>${itemDetails.colour.name}</td>
                                        <td>${itemDetails.condition.name}</td>
                                        <td>${itemDetails.purchasePrice}</td>
                                        <td>${itemDetails.equipmentAvailability}</td>
                                        <td>${itemDetails.equipmentSerialNo}</td>
                                        <td>${itemDetails.material}</td>
                                        <td>${itemDetails.weightInGram}</td>
                                        <td>${itemDetails.lifeSpan}</td>
                                        <td>${itemDetails.ownBy}</td>
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