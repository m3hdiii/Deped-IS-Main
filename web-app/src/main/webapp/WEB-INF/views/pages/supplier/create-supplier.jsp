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
        <h3> Create Supplier </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <c:choose>
                        <c:when test="${not empty notCreated}">
                            <p style="color: red;">${notCreated}</p>
                        </c:when>
                        <c:when test="${not empty successfullyCreated}">
                            <p style="color: green;">${successfullyCreated}</p>
                            &nbsp;&nbsp;<a href="/supplier/create">Create New Supplier</a>
                        </c:when>
                    </c:choose>
                    <c:set var="errors"
                           value="${requestScope['org.springframework.validation.BindingResult.supplier'].allErrors}"/>

                    <form:form commandName="supplier" method="post" class="form-horizontal"
                               enctype="multipart/form-data">
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


                        <p>
                        <div class="form-group">
                            <label class="col-sm-2"> Name: </label>
                            <form:input path="name" class="col-md-4"/>
                        </div>
                        </p>

                        <p>
                        <div class="form-group">
                            <label class="col-sm-2"> Description: </label>
                            <form:textarea path="description" class="col-md-4"/>
                        </div>
                        </p>

                        <p>
                        <div class="form-group">
                            <label class="col-sm-2"> Contact Number: </label>
                            <form:input path="supplierContactNo1" class="col-md-3"/>
                        </div>
                        </p>

                        <p>
                        <div class="form-group">
                            <label class="col-sm-2"> Contact Number 2: </label>
                            <form:input path="supplierContactNo2" class="col-md-3"/>
                        </div>
                        </p>

                        <p>
                        <div class="form-group">
                            <label class="col-sm-2"> Central Office Address: </label>
                            <form:input path="supplierAddress" class="col-md-4"/>
                        </div>
                        </p>

                        <p>
                        <div class="form-group">
                            <label class="col-sm-2"> Service Center Address: </label>
                            <form:input path="remarks" class="col-md-4"/>
                        </div>
                        </p>

                        <div>
                            <label class="col-sm-2"> Service Center Address: </label>
                            <input type="file" name="supplyPic" id="brandPicture" class="col-md-4"/>
                        </div>

                        <p>
                        <div class="form-group">
                            <div class="col-md-2">
                                <button type="submit" class="btn btn-green"> Create Supplier</button>
                            </div>
                            <div class="col-md-2">
                                <button type="reset" class="btn btn-green"> Reset</button>
                            </div>
                        </div>
                        </p>

                    </form:form>
                </div>
            </div>
        </div>
    </div>

        <section class="navbar-fixed-bottom">
            <c:import url="../../includes/footer.jsp"/>

</body>
</html>