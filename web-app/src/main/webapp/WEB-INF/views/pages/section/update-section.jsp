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

    <c:choose>
        <c:when test="${not empty notUpdated}">
            <p style="color: red;">${notUpdated}</p>
        </c:when>

        <c:when test="${not empty successfullyUpdated}">
            <p style="color: green;">${successfullyUpdated}</p>
            &nbsp;&nbsp;<a href="/brand/create">Create New Brand</a>
        </c:when>
    </c:choose>

    <div class="row">
        <div class="col-lg-12">
            <div class="panel-heading"><h1>Edit Section</h1></div>
            <hr class="style13"/>
            <c:set var="errors"
                   value="${requestScope['org.springframework.validation.BindingResult.section'].allErrors}"/>

            <form:form commandName="section" method="post">
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
                <div class="row form-group">
                    <div class="col-sm-2">
                        <label class="control-label"><span>name: </span></label>
                        <form:input path="name"/>
                    </div>
                </div>
                <div class="row form-group">
                    <div class="col-sm-2">
                        <label class="control-label"><span>description: </span></label>
                        <form:textarea path="description"/>
                    </div>
                </div>
                <div class="row form-group">
                    <div class="col-sm-2">
                        <label class="control-label"><span>List of Departments: </span></label>
                        <form:select path="department.departmentId" items="${departments}" itemValue="departmentId"
                                     itemLabel="name"/>
                    </div>
                </div>
                <div class="row form-group btn-group-sm">
                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-success btn-block">Update Section</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

    <c:import url="../../includes/footer.jsp"/>

</body>
</html>