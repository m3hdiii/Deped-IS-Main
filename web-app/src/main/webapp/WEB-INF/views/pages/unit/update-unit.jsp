<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Update Unit"/>
    <c:param name="description" value="EDIT LATER PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Unit
                <small>
                    for Goods, Semi-Expendable and Equipment
                </small>
            </h3>
        </div>
        <div class="row new-item-body">

            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.unit'].allErrors}"/>

                        <form:form commandName="unit" method="post" class="form-horizontal" role="form">
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
                            <h3 class="text-center">Update Unit</h3>
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
                                        <label for="updateUnitName">Name</label>
                                        <form:input path="name" class="form-control" placeholder="Unit Name"
                                                    id="updateUnitName"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateUnitDesc">Description</label>
                                        <form:textarea type="text" path="description" class="form-control"
                                                       placeholder="Unit Description Here..." cols="3" rows="3"
                                                       id="updateUnitDesc" maxlength="100"></form:textarea>
                                    </div>
                                </div>
                            </div>
                            <form:hidden path="previousIdName" value="${unit.name}"/>
                            <div class="modal-footer">
                                <a href="/unit/list" class="btn btn-default pull-left"><i
                                        class="fa fa-chevron-left"></i> Back</a>
                                <div class="button-footer pull-right">
                                    <input type="reset" class="btn btn-default" value="Reset"/>
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