<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Create Section"/>
    <c:param name="description" value="This page allows the user to create a section within a select department it should belong to."/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>


        <div class="warper container-fluid">

            <div class="page-header">
                <h3>Section
                    <small>DepEd-Baguio City Division Office</small>
                </h3>

            </div>


            <div class="row new-item-body">
                <div class="col-md-12">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="panel panel-default">
                            <form:form commandName="section" method="post">
                                <h3 class="text-center">Add New Section</h3>
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
                                            </c:when>
                                        </c:choose>
                                        <c:set var="errors"
                                               value="${requestScope['org.springframework.validation.BindingResult.section'].allErrors}"/>

                                        <c:if test="${not empty errors}">
                                            <div>
                                                <ul class="list-group">
                                                    <c:forEach items="${fieldErrors}" var="error" varStatus="loop">
                                                        <li class="list-group-item list-group-item-warning text-danger"><span
                                                                class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </c:if>

                                        <div class="form-group">
                                            <label for="newCategName">Name</label>
                                            <form:input type="text" path="name" class="form-control" id="newCategName"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newCategDesc">Description</label>
                                            <form:textarea type="text" path="description" class="form-control" cols="3" rows="3" id="newCategDesc" maxlength="100"/>
                                        </div>

                                        <div class="form-group">
                                            <label for="newDepSec" class="control-label"><span>Choose a Department: </span></label>
                                            <form:select path="department" id="newDepSec">
                                                <form:options items="${departments}" itemValue="departmentId" itemLabel="name"/>
                                            </form:select>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i> Back</a>
                                    <div class="button-footer pull-right">
                                        <input type="reset" class="btn btn-default" value="Clear"/>
                                        <button type="submit" class="btn btn-primary">Create</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div> <!-- New Item Body closing -->
            </div>
        </div>
        <!-- Warper Ends Here (working area) -->

    <%--<c:choose>--%>
        <%--<c:when test="${not empty notCreated}">--%>
            <%--<p style="color: red;">${notCreated}</p>--%>
        <%--</c:when>--%>


        <%--<c:when test="${not empty successfullyCreated}">--%>
            <%--<p style="color: green;">${successfullyCreated}</p>--%>
            <%--&nbsp;&nbsp;<a href="/brand/create">Create New Brand</a>--%>
        <%--</c:when>--%>

    <%--</c:choose>--%>
    <%--<div class="row">--%>
        <%--<div class="col-lg-12">--%>
            <%--<div class="panel-heading"><h1>Enter Section Information</h1></div>--%>
            <%--<hr class="style13"/>--%>
            <%--<c:set var="errors"--%>
                   <%--value="${requestScope['org.springframework.validation.BindingResult.section'].allErrors}"/>--%>

            <%--<form:form commandName="section" method="post">--%>
                <%--<c:if test="${not empty errors}">--%>
                    <%--<div>--%>
                        <%--<ul class="list-group">--%>
                            <%--<c:forEach items="${fieldErrors}" var="error" varStatus="loop">--%>
                                <%--<li class="list-group-item list-group-item-warning text-danger"><span--%>
                                        <%--class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}--%>
                                <%--</li>--%>
                            <%--</c:forEach>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</c:if>--%>
                <%--<div class="row form-group">--%>
                    <%--<div class="col-sm-2">--%>
                        <%--<label class="control-label"><span>name: </span></label>--%>
                        <%--<form:input path="name"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="row form-group">--%>
                    <%--<div class="col-sm-2">--%>
                        <%--<label class="control-label"><span>Description: </span></label>--%>
                        <%--<form:textarea path="description"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="row form-group">--%>
                    <%--<div class="col-sm-2">--%>
                        <%--<label class="control-label"><span>Choose a Department: </span></label>--%>
                        <%--<form:select path="department">--%>
                            <%--<form:options items="${departments}" itemValue="departmentId" itemLabel="name"/>--%>
                        <%--</form:select>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="row form-group btn-group-sm">--%>
                    <%--<div class="col-sm-2">--%>
                        <%--<button type="submit" class="btn btn-success btn-block">Create Section</button>--%>
                        <%--<button type="reset" class="btn btn-primary btn-block">Reset Fields</button>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</form:form>--%>
        <%--</div>--%>
    <%--</div>--%>

            <c:import url="../../includes/footer.jsp"/>

</body>
</html>