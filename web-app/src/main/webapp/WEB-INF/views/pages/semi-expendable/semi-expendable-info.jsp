<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Semi-Expendable Info"/>
    <c:param name="description" value="Semi-Expendable Info Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>&nbsp;&nbsp;Information&nbsp;<small>&nbsp;for Semi Expendable</small>
        </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Name: </label> ${semiExpendableInfo.name}</p>
                    </div>
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Description: </label> ${semiExpendableInfo.description}</p>
                    </div>
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Item Type: </label> ${semiExpendableInfo.itemType}</p>
                    </div>
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Threshold: </label> ${semiExpendableInfo.threshold}</p>
                    </div>

                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Quantity: </label> ${semiExpendableInfo.quantity}</p>
                    </div>
                    <div class="col-md-3">
                        <button type="button" class="btn btn-green"> <a href="/semi-expendable/update/${semiExpendableId}"> Update Info </a> </button>
                    </div>
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