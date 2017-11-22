<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Supplier List"/>
    <c:param name="description" value="Supplier PAGE"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Supplier
                <small>for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <div class="btn-group visible-lg-inline-block">
            <a href="/supplier/create" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top"
               title="Add Supplier"><i class="fa fa-plus"></i></a>
        </div>
        <hr class="clean">


        <div class="panel panel-default">
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-hover"
                       id="basic-datatable">
                    <thead>
                    <tr>
                        <%--<th>
                            <label class="cr-styled">
                                <input type="checkbox" ng-model="todo.done">
                                <i class="fa"></i>
                            </label>
                        </th>--%>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Contact Number</th>
                        <th class="col-md-3">Central Office Address</th>
                        <th class="col-md-3">Service Center Address</th>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <th>Actions</th>
                        </sec:authorize>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${suppliers}" var="supplier">
                        <tr>
                                <%-- <td>
                                     <label class="cr-styled">
                                         <input type="checkbox" ng-model="todo.done">
                                         <i class="fa"></i>
                                     </label>
                                 </td>--%>

                            <c:choose>
                                <c:when test="${not empty supplier.picName}">
                                    <td>
                                        <img src="${baseUrl}${supplier.picName}" class="img-circle" alt="user image"
                                             width="50px" height="50px"/>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <img src="../../${resourceURL}/images/avtar/supplier.png" class="img-circle"
                                             alt="User#1" width="50px" height="50px">
                                    </td>
                                </c:otherwise>
                            </c:choose>

                            <td>${supplier.name}</td>
                            <td>${supplier.description}</td>
                            <td>
                                <div class="fa phone1">
                                    <i class="fa-mobile"></i>
                                    <a href="#">
                                        <u>${supplier.supplierContactNo1}</u>
                                    </a>
                                </div>
                                <div class="fa phone2">
                                    <i class="fa-phone"></i>
                                    <a href="#">
                                        <u>${supplier.supplierContactNo2}</u>
                                    </a>
                                </div>
                            </td>
                            <td>${supplier.supplierAddress}</td>
                            <td>${supplier.remarks}</td>
                            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                                <td>
                                    <div class="btn-group visible-lg-inline-block">
                                        <a href="/supplier/update/${supplier.supplierId}"
                                           class="btn btn-purple tooltip-btn"
                                           data-toggle="tooltip" data-placement="top" title="Edit Supplier"><i
                                                class="fa fa-pencil"></i></a>
                                            <%--<button type="button" class="btn btn-danger tooltip-btn" data-toggle="tooltip" data-placement="top" title="Delete" ><i class="fa fa-trash"></i></button>--%>
                                    </div>
                                </td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->


    <%--<div class="page-header">--%>
    <%--<h3> Supply List </h3>--%>
    <%--</div>--%>

    <%--<p>--%>
    <%--JSP location:--%>
    <%--</p>--%>
    <%--<p style="color: red; font-weight: bold;">--%>
    <%--WEB-INF/views/<c:out value="${jspLocation}"/>.jsp--%>
    <%--</p>--%>
    <%--<hr>--%>
    <%--<p>--%>
    <%--Controller Class:--%>
    <%--</p>--%>
    <%--<p style="color: red; font-weight: bold;">--%>
    <%--${controllerClazz}--%>
    <%--</p>--%>
    <%--<hr>--%>
    <%--<p>--%>
    <%--Method Name:--%>
    <%--</p>--%>
    <%--<p style="color: red; font-weight: bold;">--%>
    <%--${methodName}--%>
    <%--</p>--%>

    <%--<div class="row">--%>
    <%--<div class="col col-lg-3"/>--%>
    <%--<div class="col col-lg-9">--%>
    <%--<table class="table table-hover">--%>
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th>Supply ID</th>--%>
    <%--<th>Description</th>--%>


    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>
    <%--<c:forEach items="${supplies}" var="supply">--%>
    <%--<tr>--%>
    <%--<td>${supply.supplyId}</td>--%>
    <%--<td>${supply.description}</td>--%>
    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</tbody>--%>
    <%--</table>--%>
    <%--</div>--%>
    <%--</div>--%>

    <c:import url="../../includes/footer.jsp"/>
</body>
</html>