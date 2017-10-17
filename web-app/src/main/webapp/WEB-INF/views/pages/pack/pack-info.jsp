<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Pack Info"/>
    <c:param name="description" value="Pack Info Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3> &nbsp; Pack Info </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Name: </label> ${packInfo.name}</p>
                    </div>
                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Description: </label> ${packInfo.description}</p>
                    </div>

                    <div class="col-md-11">
                        <p> <label class="col-sm-2"> Pack for the Item: </label> ${packInfo.item.name}</p>
                    </div>

                    <div class="col-md-3">
                        <button type="button" class="btn btn-green"> <a href="/pack/update/${packId}"> Update Info </a> </button>
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