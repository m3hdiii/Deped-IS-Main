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
    <c:choose>
    <c:when test="${not empty successfullyCreated}">
    <p style="color: green;">${successfullyCreated}</p>
    &nbsp;&nbsp;<a href="/brand/list">Brand List</a>
    </c:when>
    </c:choose>
    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Create Brands
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>


        <div class="row new-item-body">
            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <c:set var="errors"
                           value="${requestScope['org.springframework.validation.BindingResult.brand'].allErrors}"/>
                    <form:form commandName="brand" method="post" class="form-horizontal"
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
                            <h3 class="text-center">Add New Brand</h3>
                            <div class="panel-body">
                                <div class="col-md-10 col-sm-offset-1">

                                    <c:choose>
                                        <c:when test="${not empty notCreated}">
                                            <div class="alert alert-danger alert-dismissable fade in">
                                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                <strong>Failed!</strong> ${notCreated}.
                                            </div>
                                        </c:when>
                                        <c:when test="${not empty successfullyCreated}">
                                            <div class="alert alert-success alert-dismissable fade in">
                                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                <strong>Success!</strong> ${successfullyCreated}.
                                            </div>
                                            &nbsp;&nbsp;<a href="/brand/list">All Brands</a>
                                        </c:when>
                                    </c:choose>

                                    <div class="form-group">
                                        <label for="newBrandName">*Name</label>
                                        <form:input path="name" type="text" class="form-control" id="newBrandName" placeholder="Name of the Brand"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="newBrandDesc">Description</label>
                                        <form:textarea path="description" type="text" class="form-control limit-char-45"
                                                       cols="3" rows="3" id="newBrandDesc" maxlength="100" placeholder="Enter Description Here..."/>
                                        <!--<span class="word-count">0</span> / <span class="word-left">100</span>-->
                                    </div>

                                    <div class="form-group">
                                        <label for="newBrandPic">Brand Logo</label>
                                        <input type="file" name="brandPic" id="newBrandPic"/>
                                    </div>
                                </div>
                            </div><!-- New Brand Body closing -->

                            <div class="modal-footer">
                                <a href="/brand/list" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i>
                                    Back</a>
                                <div class="button-footer pull-right">
                                    <button type="reset" value="Clear" class="btn btn-default">Clear</button>
                                    <button type="submit" value="Submit" class="btn btn-primary">Create</button>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>