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
    <c:param name="title" value="Update Equipment Info"/>
    <c:param name="description" value="Update Equipment Info Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Equipment Update
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row">
            <div class="col-md-12">
                <div class="col-lg-12">
                    <form class="form-inline" method="post" action="/item-details/update">
                        <div class="form-group">
                            <label for="searchId" class="sr-only">Office Serial Number</label>
                            <input type="text" name="searchKeyword" class="form-control" id="searchId"
                                   placeholder="Office Serial Number">
                        </div>
                        <button name="fetchObject" value="FETCH" class="btn btn-default" type="submit">Find</button>
                    </form>
                </div>
            </div>
        </div>

        <c:if test="${not empty nothingFound}">
            <p style="color: red;">Nothing Found</p>
        </c:if>

        <c:if test="${not empty itemDetailsResult}">

            <div class="row new-item-body margn-t-sm">
                <div class="col-md-12">
                    <div class="col-md-8 col-md-offset-2">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.itemDetailsResult'].allErrors}"/>

                        <form:form commandName="itemDetailsResult" method="post" class="form-horizontal"
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
                                <h3 class="text-center">Update Equipment Information</h3>
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
                                                &nbsp;&nbsp;<a href="/item-details/report-list">Report List</a>
                                            </c:when>
                                        </c:choose>

                                        <div class="form-group">
                                            <label for="equipmentAvailabilitiesId">Equipment Availability</label>
                                            <form:select id="equipmentAvailabilitiesId" path="equipmentAvailability"
                                                         items="${availabilities}" itemLabel="name"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         data-placeholder="Choose Equipment Availability..."/>
                                        </div>
                                        <div class="form-group">
                                            <label for="conditionId">Condition</label>
                                            <form:select id="conditionId" path="condition"
                                                         items="${conditions}" itemLabel="name"
                                                         class="form-control form-control-flat input-sm chosen-select"
                                                         data-placeholder="Choose Equipment Condition ..."/>
                                            <form:hidden path="officeSerialNo"/>
                                        </div>


                                    </div>
                                </div><!-- New Brand Body closing -->

                                <div class="modal-footer">
                                    <a href="/item-details/report-list" class="btn btn-default pull-left"><i
                                            class="fa fa-chevron-left"></i>
                                        Back</a>
                                    <div class="button-footer pull-right">
                                        <input type="reset" value="Clear" class="btn btn-default" value="Reset"/>
                                        <button type="submit" name="update-info" value="UPDATE" class="btn btn-primary">
                                            Update
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </c:if>
    </div> <!-- Warper Ends Here (working area) -->
    <c:import url="../../includes/footer.jsp"/>
</body>
</html>