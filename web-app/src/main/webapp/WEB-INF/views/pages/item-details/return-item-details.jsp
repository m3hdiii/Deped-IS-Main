<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Return Equipment"/>
    <c:param name="description" value="Return Equipment Details Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Equipment Return
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row">
            <div class="col-lg-3">
                <form class="form-inline" method="post" action="/item-details/return">
                    <div class="form-group">
                        <label for="searchId" class="sr-only">Office Serial Number</label>
                        <input type="text" name="searchKeyword" class="form-control" id="searchId"
                               placeholder="Office Serial Number">
                    </div>
                    <button name="fetchObject" value="FETCH" class="btn btn-default" type="submit">Find</button>
                </form>
            </div>
        </div>

        <c:if test="${not empty nothingFound}">
            <p style="color: red;">Nothing Found</p>
        </c:if>


        <div class="row new-item-body">
            <div class="col-md-12">
                <c:choose>
                    <c:when test="${not empty success}">
                        <div class="alert alert-success alert-dismissable fade in">
                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                            <strong>Success!</strong>
                        </div>
                    </c:when>
                </c:choose>
                <form:form commandName="itemDetailsReturn" method="post" class="form-horizontal">

                    <div class="panel panel-default">
                        <h3 class="text-center">Update Equipment Information</h3>
                        <div class="panel-body">
                            <div class="col-md-10 col-sm-offset-1">

                                <table cellpadding="0" cellspacing="0" border="0"
                                       class="table table-striped table-hover" id="basic-datatable">
                                    <thead>
                                    <tr>
                                        <th>Office Sr. No</th>
                                        <th>Colour</th>
                                        <th>Condition</th>
                                        <th>Purchase Price</th>
                                        <th>Availability</th>
                                        <th>Equipment Sr. No</th>
                                        <th>Material</th>
                                        <th>Weight in Gram</th>
                                        <th>Life Span</th>
                                        <th>Action</th>

                                    </tr>
                                    </thead>
                                    <c:if test="${not empty itemDetailsReturn}">
                                        <tbody>
                                        <tr>
                                            <th>${itemDetailsReturn.officeSerialNo}</th>
                                            <th>${itemDetailsReturn.colour}</th>
                                            <th>${itemDetailsReturn.condition}</th>
                                            <th>${itemDetailsReturn.purchasePrice}</th>
                                            <th>${itemDetailsReturn.equipmentAvailability}</th>
                                            <th>${itemDetailsReturn.equipmentSerialNo}</th>
                                            <th>${itemDetailsReturn.material}</th>
                                            <th>${itemDetailsReturn.weightInGram}</th>
                                            <th>${itemDetailsReturn.lifeSpan}
                                                <form:hidden path="officeSerialNo"/>
                                            </th>

                                            <th>
                                                <button name="return-action" value="RETURN" class="btn btn-default"
                                                        type="submit">Return
                                                </button>
                                            </th>
                                        </tr>
                                        </tbody>
                                    </c:if>
                                </table>


                            </div>
                        </div><!-- New Brand Body closing -->
                    </div>
                </form:form>

            </div>
        </div>

    </div> <!-- Warper Ends Here (working area) -->
    <c:import url="../../includes/footer.jsp"/>
</body>
</html>