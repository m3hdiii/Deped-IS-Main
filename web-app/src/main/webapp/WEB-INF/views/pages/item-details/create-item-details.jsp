<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Create Brand"/>
    <c:param name="description" value="Create Brand Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Create Item Details
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <form:form method="post" commandName="itemDetailsForm">
        <c:forEach items="${itemDetailsForm.itemDetailsList}" var="itemDetails" varStatus="status">
        <div class="panel panel-default">
            <div class="panel-heading">${itemDetails.item.name} #${status.index + 1}</div>
            <div class="panel-body">
                <div class="form-group">
                    <label for="equipmentSerialNumber">Equipment Serial Number</label>

                    <form:input id="equipmentSerialNumber"
                                path="itemDetailsList[${status.index}].equipmentSerialNo" class="form-control"/>
                </div>

                <div class="form-group">
                    <label for="itemDetailsColour">Colour</label>
                    <form:select class="form-control" id="itemDetailsColour"
                                 path="itemDetailsList[${status.index}].colour">
                        <form:option value="" label="--Please Select"/>
                        <form:options items="${itemDetails.colour}" itemLabel="name"/>
                    </form:select>
                </div>

                <div class="form-group">
                    <label for="itemDetailsCondition">Condition</label>
                    <form:select class="form-control" id="itemDetailsCondition"
                                 path="itemDetailsList[${status.index}].condition">
                        <form:option value="" label="--Please Select"/>
                        <form:options items="${itemDetails.condition}" itemLabel="name"/>
                    </form:select>
                </div>


                <div class="form-group">
                    <label for="newBudget">Purchase Price</label>
                    <div class="input-group" id="newBudget">
                        <span class="input-group-addon">₱</span>
                        <form:input id="itemDetailsPrice" type="number"
                                    path="itemDetailsList[${status.index}].purchasePrice" class="form-control"
                                    min="0"/>
                        <span class="input-group-addon">.00</span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="officeSerialNumber">Office Serial Number</label>

                    <form:input id="officeSerialNumber"
                                path="itemDetailsList[${status.index}].officeSerialNo" class="form-control"/>
                </div>

                <div class="form-group">
                    <label for="itemDetailsMaterial">Material</label>
                    <form:select class="form-control" id="itemDetailsMaterial"
                                 path="itemDetailsList[${status.index}].material">
                        <form:option value="" label="--Please Select"/>
                        <form:options items="${itemDetails.material}" itemLabel="name"/>
                    </form:select>
                </div>

                <div class="form-group">
                    <label for="weightInGram">Weight in Gram</label>
                    <form:input id="weightInGram" type="number"
                                path="itemDetailsList[${status.index}].weightInGram" class="form-control"/>
                </div>

                <div class="form-group">
                    <label for="lifeSpan">Life Span</label>

                    <form:input id="lifeSpan" type="number"
                                path="itemDetailsList[${status.index}].lifeSpan" class="form-control"/>
                    <form:hidden path="itemDetailsList[${status.index}].item.itemId"/>
                </div>

            </div>
        </div>
        </c:forEach>

        <div class="panel panel-default">
            <div class="panel-heading">
                <button type="submit" class="btn btn-purple btn-block">Create</button>
            </div>
        </div>

        </form:form>

        <%--<div class="panel-body">

            <table class="table table-hover">
                <thead>
                <tr>
                    <th><input type="checkbox" class="checkbox checkbox-inline"/></th>
                    <th>Picture</th>
                    <th>Colour</th>
                    <th>Condition</th>
                    <th>Purchase Price</th>
                    <th>Equipment Serial No</th>
                    <th>Material</th>
                    <th>Weight</th>
                    <th>Life Span</th>
                    <th>Item</th>

                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <input type="checkbox" class="checkbox checkbox-inline"/>
                    </td>

                </tr>

                </tbody>
            </table>
        </div>
        <div class=" modal-footer ">
            <button name="actionParam" value="UPDATE_ALL" class="pull-left btn btn-link"
                    data-toggle="tooltip" title="Update the data and Request more"><i
                    class="fa fa-arrow-left"></i> <strong>Request
                more</strong></button>

            <button name="actionParam" value="DELETE_ALL" class="btn btn-danger"
                    data-toggle="tooltip"
                    title="Delete the list and re-request">Delete All
            </button>
            <button name="actionParam" value="SAVE_ALL" class="btn btn-primary"
                    data-toggle="tooltip"
                    title="Save the list">Save
            </button>
            <button name="actionParam" value="REQUEST_ALL" class="btn btn-success"
                    data-toggle="tooltip"
                    title="Send the Request">Finalize
            </button>
        </div>
        </form:form>
        </c:when>
        <c:otherwise>
            <div class="panel-body">
                <h1 class="text-center padd-t-lg">No Request Available</h1>
            </div>
            <div class=" modal-footer ">
                <button name="actionParam" class="btn btn-danger disabled">Checkout</button>
            </div>
        </c:otherwise>

        </c:choose>


    </div>


    </div>
    <!-- Warper Ends Here (working area) -->
--%>
        <section class="navbar-fixed-bottom">
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>