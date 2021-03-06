<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Update Section Info"/>
    <c:param name="description" value="Update an existing Section Information"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Section<small>
                DepEd-Baguio City Division Office</small>
            </h3>
        </div>
        <div class="row new-item-body">

            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.section'].allErrors}"/>

                        <form:form commandName="section" method="post">
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
                            <h3 class="text-center">Update Section</h3>
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
                                        <label for="updateSectionName">Name</label>
                                        <form:input path="name" class="form-control" placeholder="Section Name" id="updateSectionName"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateSectionDesc">Description</label>
                                        <form:textarea type="text" path="description" class="form-control"
                                                       placeholder="Section Description Here..." cols="3" rows="3" id="updateSectionDesc" maxlength="100"></form:textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateSectionDep">Department</label>
                                        <form:select path="department.name" items="${departments}" itemValue="name"
                                                     id="updateSectionDep" class="form-control"
                                                     itemLabel="name"/>
                                    </div>
                                </div>
                            </div>
                            <form:hidden path="previousIdName" value="${section.name}"/>
                            <div class="modal-footer">
                                <a href="/section/list" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i> Back</a>
                                <div class="button-footer pull-right">
                                    <input type="reset" class="btn btn-default" value="Reset Fields"/>
                                    <button type="submit" class="btn btn-primary">Update</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div><!-- New Item Body closing -->
    </div><!-- Warper Ends Here (working area) -->


            <c:import url="../../includes/footer.jsp"/>

</body>
</html>