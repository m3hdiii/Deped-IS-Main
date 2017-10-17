<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Update Category"/>
    <c:param name="description" value="Update Category Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> &nbsp; Update Category </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <c:choose>
                        <c:when test="${not empty notUpdated}">
                            <p style="color: red;">${notUpdated}</p>
                        </c:when>


                        <c:when test="${not empty successfullyUpdated}">
                            <p style="color: green;">${successfullyUpdated}</p>
                            &nbsp;&nbsp;<a href="/category/create">Create New Category</a>
                        </c:when>
                    </c:choose>

                    <form:form commandName="category" method="post" class="form-horizontal">

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
                            <div class="col-md-2">
                                <button type="submit" class="btn btn-green"> Create Category</button>
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

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>