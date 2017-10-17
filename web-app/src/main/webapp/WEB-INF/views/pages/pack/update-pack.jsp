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
        <h3>&nbsp;&nbsp;&nbspPackage Registration&nbsp;<small>&nbsp;for an Item</small>
        </h3>
    </div>

    <div>


        <c:choose>
            <c:when test="${not empty notCreated}">
                <p style="color: red;">${notCreated}</p>
            </c:when>


            <c:when test="${not empty successfullyCreated}">
                <p style="color: green;">${successfullyCreated}</p>
                &nbsp;&nbsp;<a href="/pack/create">Create New Pack</a>
            </c:when>

        </c:choose>
    </div>

    <div class="row">

        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">Pack Information</div>
                <div class="panel-body">
                    <form:form commandName="pack" method="post" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-7">
                                <form:input path="name" class="form-control typeahead" placeholder=""/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">Description</label>
                            <div class="col-lg-7">
                                <form:textarea path="description" class="col-sm-7 form-control typeahead"
                                               placeholder="enter description here..." rows="7"></form:textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label">Capacity:</label>
                            <div class="col-lg-7">
                                <form:input type="number" path="capacity" class="col-sm-7 form-control typeahead"
                                            placeholder="enter description here..." rows="7"/>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">This Package is for Item</label>
                            <div class="col-lg-7">
                                <form:select path="item.itemId" items="${items}" itemValue="itemId"
                                             itemLabel="name"/>
                            </div>
                        </div>

                        <hr class="style13">
                        <div class="btn-group-sm row">
                            <div class="col-sm-2">
                                <button type="submit" class="btn btn-success btn-block">Submit</button>
                            </div>
                            <div class="form-group col-sm-2">
                                <button type="reset" class="btn btn-primary btn-block">Reset Fields</button>
                            </div>
                        </div>

                        <hr class="style13">

                    </form:form>
                </div>
            </div>
        </div>

    </div>


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>