<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Category Info"/>
    <c:param name="description" value="Category Info Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> &nbsp; Category Info </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Name: </label> ${categoryInfo.name}</p>
                    </div>
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Description: </label> ${categoryInfo.description}</p>
                    </div>
                    <div class="col-md-11">
                        <p><label class="col-sm-2"> Creation Date: </label> ${categoryInfo.creationDate}</p>
                    </div>
                    <div class="col-md-3">
                        <a class="btn btn-info" role="button" href="/category/update/${categoryInfo.categoryId}"> Update
                            Info </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <section class="navbar-fixed-bottom">
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>