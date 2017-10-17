<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="EDIT LATER"/>
    <c:param name="description" value="EDIT LATER PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbspItem Registration&nbsp;<small>&nbsp;for Equipment</small>
        </h3>
    </div>

    <div>


        <c:choose>
            <c:when test="${not empty notCreated}">
                <p style="color: red;">${notCreated}</p>
            </c:when>


            <c:when test="${not empty successfullyCreated}">
                <p style="color: green;">${successfullyCreated}</p>
                &nbsp;&nbsp;<a href="/equipment/create">Create New Equipment</a>
            </c:when>

        </c:choose>
    </div>

    <div class="row">

        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">Equipment Information</div>
                <div class="panel-body">
                    <form:form commandName="equipment" method="post" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-7">
                                <form:input path="name" class="form-control typeahead" placeholder=""/>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Description</label>
                            <div class="col-lg-7">
                                <form:textarea path="description" class="col-sm-7 form-control typeahead"
                                               placeholder="enter description here..." rows="7"></form:textarea>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Item Type</label>
                            <div class="col-sm-7">
                                <form:select path="equipmentStatus" class="form-control chosen-select"
                                             data-placeholder="Select a Category">
                                    <!-- <option>Select a Category</option> -->
                                    <form:option value="OK">Good Condtion</form:option>
                                    <form:option value="UNREPAIRABLE">Unrepairable</form:option>
                                    <form:option value="DAMAGED">Damaged</form:option>
                                    <form:option value="LOST">Lost</form:option>
                                </form:select>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Model Number</label>
                            <div class="col-sm-2">
                                <form:input path="modelNumber" type="number" class="form-control"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Equipment Color</label>
                            <div class="col-sm-7">
                                <form:select path="color" class="form-control chosen-select"
                                             data-placeholder="Select a Category">
                                    <!-- <option>Select a Category</option> -->
                                    <form:option value="RED" cssClass="bg-red">Red</form:option>
                                    <form:option value="BLUE" cssClass="bg-blue">Blue</form:option>
                                    <form:option value="BLACK" cssClass="bg-black">Black</form:option>
                                    <form:option value="WHITE">White</form:option>
                                    <form:option value="GRAY" cssClass="bg-gray">Gray</form:option>
                                    <form:option value="SILVER" cssClass="bg-silver">Silver</form:option>
                                    <form:option value="PINK" cssClass="bg-pink">Pink</form:option>
                                    <form:option value="YELLOW" cssClass="bg-yellow">Yellow</form:option>
                                    <form:option value="BROWN" cssClass="bg-brown">Brown</form:option>
                                    <form:option value="CREAM" cssClass="bg-cream">Cream</form:option>
                                    <form:option value="ORANGE" cssClass="bg-orange">Orange</form:option>
                                </form:select>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Purchased Price</label>
                            <div class="col-sm-2">
                                <form:input path="purchasePrice" type="number" class="form-control"/>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="btn-group-sm row">
                            <div class="col-sm-2">
                                <button type="submit" class="btn btn-success btn-block">Submit</button>
                            </div>
                            <div class="form-group col-sm-2">
                                <button type="reset" class="btn btn-primary btn-block">Reset Fields</button>
                            </div>
                        </div>

                        <hr class="style13">

                    </form:form>
                </div>
            </div>
        </div>

    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>