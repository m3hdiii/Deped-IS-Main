<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Item"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Manage Items Details
                <small>for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <div class="btn-group">

            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                Item Type <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#">Goods</a></li>
                <li><a href="#">Semi-Expendable</a></li>
                <li><a href="#">Equipment</a></li>
            </ul>
        </div>


        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
            <div class="btn-group visible-lg-inline-block">
                <a href="/item-details/insert-data" class="btn btn-default tooltip-btn" data-toggle="tooltip"
                   data-placement="top"
                   title="Add Item"><i class="fa fa-plus"></i></a>
            </div>
        </sec:authorize>

        <hr class="clean">


        <div class="panel panel-default">
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-hover"
                       id="basic-datatable">
                    <thead>
                    <tr>
                        <th>Office Sr#</th>
                        <th>Item Name</th>
                        <th>Colour</th>
                        <th>Condition</th>
                        <th>Availability</th>
                        <th>Equipment Sr#</th>
                        <th>Material</th>
                        <th>Weight In Gram</th>
                        <th>Life Span</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${itemDetailsList}" var="itemDetails">
                        <tr>
                            <th>${itemDetails.officeSerialNo}</th>
                            <th>${itemDetails.item.name}</th>
                            <td>${itemDetails.colour.name}</td>
                            <td>${itemDetails.condition.name}</td>
                            <th>${itemDetails.equipmentAvailability.name}</th>
                            <td>${itemDetails.equipmentSerialNo}</td>
                            <td>${itemDetails.material.name}</td>
                            <td>${itemDetails.weightInGram}</td>
                            <td>${itemDetails.lifeSpan}</td>

                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>

    <c:import url="../../includes/footer.jsp"/>
</body>
</html>





