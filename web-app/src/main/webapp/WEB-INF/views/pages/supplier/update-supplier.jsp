<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Update Supplier"/>
    <c:param name="description" value="Update Supplier PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>
    <c:choose>
    <c:when test="${not empty notUpdated}">
    <p style="color: red;">${notUpdated}</p>
    </c:when>
    <c:when test="${not empty successfullyUpdated}">
    <p style="color: green;">${successfullyUpdated}</p>
    &nbsp;&nbsp;<a href="/supplier/create">Create Supplier</a>
    </c:when>
    </c:choose>
        <div class="warper container-fluid">

            <div class="page-header">
                <h1>Supplier
                    <small>DepEd-Baguio City Division Office</small>
                </h1>

            </div>

            <div class="row new-item-body">
                <div class="col-md-12">
                    <div class="col-md-8 col-md-offset-2">
                        <c:set var="errors" value="${requestScope['org.springframework.validation.BindingResult.supplier'].allErrors}"/>

                        <form:form commandName="supplier" method="post" class="form-horizontal"
                                   enctype="multipart/form-data">

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

                            <div class="panel panel-default">
                                <h3 class="text-center">Update Supplier</h3>
                                <div class="panel-body">
                                    <div class="col-md-10 col-sm-offset-1">

                                        <c:choose>
                                            <c:when test="${not empty notUpdated}">
                                                <div class="alert alert-danger alert-dismissable fade in">
                                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                    <strong>Failed!</strong> ${notUpdated}.
                                                </div>
                                            </c:when>
                                            <c:when test="${not empty successfullyUpdated}">
                                                <div class="alert alert-success alert-dismissable fade in">
                                                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                    <strong>Success!</strong> ${successfullyUpdated}.
                                                </div>
                                            </c:when>
                                        </c:choose>

                                        <div class="form-group">
                                            <label for="newSuppName">*Name</label>
                                            <form:input path="name" type="text" class="form-control" id="newSuppName" placeholder="Name of the Supplier"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newSuppDesc">Description</label>
                                            <form:textarea path="description" type="text" class="form-control limit-char-45"
                                                           cols="3" rows="3" id="newSuppDesc" maxlength="100" placeholder="Enter Description Here..."/>
                                            <!--<span class="word-count">0</span> / <span class="word-left">100</span>-->
                                        </div>

                                        <div class="form-group">
                                            <label for="newSuppPhone">*Contact Number</label>
                                            <form:input path="supplierContactNo1" type="number" class="form-control"
                                                        id="newSuppPhone" placeholder="ex. 09123456789"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newSuppTelephone">Contact Number 2</label>
                                            <form:input path="supplierContactNo2" type="number" class="form-control"
                                                        id="newSuppTelephone" placeholder="ex. 477-2380-32"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newOfficeAddress">*Central Office Address</label>
                                            <form:input path="supplierAddress" type="text" class="form-control"
                                                        id="newOfficeAddress" placeholder="Supplier Central Office"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newServiceAddress">Service Center Address</label>
                                            <form:input path="remarks" type="text" class="form-control"
                                                        id="newServiceAddress" placeholder="Supplier Service Center"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newsuppPic">Supplier Image</label>
                                            <input type="file" name="supplyPic" id="newsuppPic"/>
                                        </div>
                                    </div>
                                </div><!-- New Supplier Body closing -->

                                <form:hidden path="picName"/>
                                <div class="modal-footer">
                                    <a href="/supplier/list" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i>
                                        Back</a>
                                    <div class="button-footer pull-right">
                                        <input type="reset" value="Reset" class="btn btn-default"/>
                                        <button type="submit" value="Submit" class="btn btn-primary">Update</button>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div><!-- Warper Ends Here (working area) -->
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>