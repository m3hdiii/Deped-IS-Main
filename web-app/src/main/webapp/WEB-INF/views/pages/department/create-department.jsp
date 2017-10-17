<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Create Department"/>
    <c:param name="description" value="Create Department Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <c:choose>
        <c:when test="${not empty notCreated}">
            <p style="color: red;">${notCreated}</p>
        </c:when>


        <c:when test="${not empty successfullyCreated}">
            <p style="color: green;">${successfullyCreated}</p>
            &nbsp;&nbsp;<a href="/department/create">Create New Department</a>
        </c:when>

    </c:choose>
    <div class="row">
        <div class="col-lg-12">
            <div class="panel-heading"><h1>Enter Section Information</h1></div>
            <hr class="style13"/>
            <form:form commandName="department" method="post">
                <div class="row form-group">
                    <div class="col-sm-2">
                        <label class="control-label"><span>name: </span></label>
                        <form:input path="name"/>
                    </div>
                </div>
                <div class="row form-group">
                    <div class="col-sm-2">
                        <label class="control-label"><span>Description: </span></label>
                        <form:textarea path="description"/>
                    </div>
                </div>
                <div class="row form-group">
                    <div class="col-sm-2">
                        <label class="control-label"><span>Department Head: </span></label>
                        <form:input path="departmentHead"/>
                    </div>
                </div>
                <div class="row form-group btn-group-sm">
                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-success btn-block">Create Department</button>
                        <button type="reset" class="btn btn-primary btn-block">Reset Fields</button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>