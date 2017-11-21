<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Create Pack"/>
    <c:param name="description" value="EDIT LATER PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">

            <div class="page-header">
                <h3>Pack<small>
                    for Goods, Semi-Expendable and Equipment</small>
                </h3>
            </div>
            <div class="row new-item-body">

                <div class="col-md-12">
                    <div class="col-md-8 col-md-offset-2">
                        <div class="panel panel-default">
                            <c:set var="errors"
                                   value="${requestScope['org.springframework.validation.BindingResult.pack'].allErrors}"/>

                            <form:form commandName="pack" method="post" class="form-horizontal" role="form">
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
                                <h3 class="text-center">Add New Pack</h3>
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

                                        <div class="form-group">
                                            <label for="newPackName">Name</label>
                                            <form:input path="name" class="form-control" placeholder="Pack Name" id="newPackName"/>
                                        </div>
                                        <div class="form-group">
                                            <label for="newPackDesc">Description</label>
                                            <form:textarea type="text" path="description" class="form-control"
                                                           placeholder="Pack Description Here..." cols="3" rows="3" id="newPackDesc" maxlength="100"></form:textarea>
                                        </div>

                                        <%--Capacity of package--%>

                                        <%--<div class="form-group">--%>
                                            <%--<label for="newPackCapacity">Capacity</label>--%>
                                            <%--<form:input type="number" min="0" path="capacity" id="newPackCapacity" class="form-control"/>--%>
                                        <%--</div>--%>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <a href="/pack/list" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i> Back</a>
                                    <div class="button-footer pull-right">
                                        <button type="reset" class="btn btn-default">Clear</button>
                                        <button type="submit" class="btn btn-primary">Create</button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div><!-- New Item Body closing -->
        </div><!-- Warper Ends Here (working area) -->

    <%--<div class="page-header">--%>
        <%--<h3>&nbsp;&nbsp;&nbspPackage Registration&nbsp;<small>&nbsp;for an Item</small>--%>
        <%--</h3>--%>
    <%--</div>--%>

    <%--<div>--%>


        <%--<c:choose>--%>
            <%--<c:when test="${not empty notCreated}">--%>
                <%--<p style="color: red;">${notCreated}</p>--%>
            <%--</c:when>--%>


            <%--<c:when test="${not empty successfullyCreated}">--%>
                <%--<p style="color: green;">${successfullyCreated}</p>--%>
                <%--&nbsp;&nbsp;<a href="/pack/create">Create New Pack</a>--%>
            <%--</c:when>--%>

        <%--</c:choose>--%>
    <%--</div>--%>

    <%--<div class="row">--%>

        <%--<div class="col-md-12">--%>
            <%--<div class="panel panel-default">--%>
                <%--<div class="panel-heading">Pack Information</div>--%>
                <%--<div class="panel-body">--%>
                    <%--<c:set var="errors"--%>
                           <%--value="${requestScope['org.springframework.validation.BindingResult.pack'].allErrors}"/>--%>

                    <%--<form:form commandName="pack" method="post" class="form-horizontal" role="form">--%>
                        <%--<div class="form-group">--%>
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
                            <%--<label class="col-sm-2 control-label">Name</label>--%>
                            <%--<div class="col-sm-7">--%>
                                <%--<form:input path="name" class="form-control typeahead" placeholder=""/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">Description</label>--%>
                            <%--<div class="col-lg-7">--%>
                                <%--<form:textarea path="description" class="col-sm-7 form-control typeahead"--%>
                                               <%--placeholder="enter description here..." rows="7"></form:textarea>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">Capacity:</label>--%>
                            <%--<div class="col-lg-7">--%>
                                <%--<form:input type="number" path="capacity" class="col-sm-7 form-control typeahead"--%>
                                            <%--placeholder="enter description here..." rows="7"/>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--&lt;%&ndash;--%>
                        <%--<hr class="style13">--%>
                        <%--<div class="form-group">--%>
                            <%--<label class="col-sm-2 control-label">This Package is for Item</label>--%>
                            <%--<div class="col-lg-7">--%>
                                <%--<form:select path="item">--%>
                                    <%--<form:options items="${items}" itemValue="itemId" itemLabel="name"/>--%>
                                <%--</form:select>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--&ndash;%&gt;--%>

                        <%--<hr class="style13">--%>
                        <%--<p>--%>
                        <%--<div class="form-group">--%>
                            <%--<div class="col-md-2">--%>
                                <%--<button type="submit" class="btn btn-green"> Create Pack</button>--%>
                            <%--</div>--%>
                            <%--<div class="col-md-2">--%>
                                <%--<button type="reset" class="btn btn-green"> Reset</button>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--</p>--%>

                        <%--<hr class="style13">--%>

                    <%--</form:form>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

    <%--</div>--%>
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>