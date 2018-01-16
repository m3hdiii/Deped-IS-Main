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
    <c:param name="title" value="Update Brand"/>
    <c:param name="description" value="Update Brand Page"/>
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
    &nbsp;&nbsp;<a href="/brand/create">Create Brand</a>
    </c:when>

    </c:choose>
    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Equipment Info
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row">
            <div class="col-lg-3">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for...">
                    <span class="input-group-btn">
                         <button class="btn btn-secondary" type="button">Go!</button>
                    </span>
                </div>
            </div>
        </div>

        <div class="row new-item-body">
            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <c:set var="errors"
                           value="${requestScope['org.springframework.validation.BindingResult.brand'].allErrors}"/>

                    <form:form commandName="item-details" method="post" class="form-horizontal"
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
                                        <label for="newBrandName">Equipment Availability</label>
                                        <form:input path="equipmentAvailability" type="text" class="form-control"
                                                    id="newBrandName"
                                                    placeholder="Name of the Brand"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="newBrandDesc">Condition</label>
                                        <form:textarea path="condition" type="text" class="form-control limit-char-45"
                                                       cols="3" rows="3" id="newBrandDesc" maxlength="100"
                                                       placeholder="Enter Description Here..."/>
                                        <!--<span class="word-count">0</span> / <span class="word-left">100</span>-->
                                    </div>

                                </div>
                            </div><!-- New Brand Body closing -->

                            <div class="modal-footer">
                                <a href="/item-details/report-list" class="btn btn-default pull-left"><i
                                        class="fa fa-chevron-left"></i>
                                    Back</a>
                                <div class="button-footer pull-right">
                                    <input type="reset" value="Clear" class="btn btn-default" value="Reset"/>
                                    <button type="submit" value="Update" class="btn btn-primary">Update</button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div> <!-- Warper Ends Here (working area) -->
    <c:import url="../../includes/footer.jsp"/>
</body>
</html>