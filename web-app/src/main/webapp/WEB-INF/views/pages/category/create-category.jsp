<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Create Category"/>
    <c:param name="description" value="Create Category Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Category
                <small>for Goods, Semi-Expendable and Equipment</small>
            </h3>
            <hr class="clean">
        </div>


        <div class="row new-item-body">
            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <form:form commandName="category" method="post" class="form-horizontal">
                            <h3 class="text-center">Add New Category</h3>
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
                                           value="${requestScope['org.springframework.validation.BindingResult.category'].allErrors}"/>

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

                                    <div class="form-group">
                                        <label for="newCategName">Name</label>
                                        <form:input type="text" path="name" class="form-control" id="newCategName"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="newCategDesc">Description</label>
                                        <form:textarea type="text" path="description" class="form-control" cols="3"
                                                       rows="3" id="newCategDesc" maxlength="100"/>
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <a href="/category/list" class="btn btn-default pull-left"><i
                                        class="fa fa-chevron-left"></i> Back</a>
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

    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>