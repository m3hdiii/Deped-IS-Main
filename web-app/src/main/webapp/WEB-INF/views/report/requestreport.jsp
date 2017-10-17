<%--
  Created by Kieth Opiniano
  User: Win8
  Date: 9/30/2017
  Time: 1:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>
<c:import url="../includes/head.jsp">
    <c:param name="title" value="Request Report"/>
    <c:param name="description" value="Enables to view report on the request by the user"/>
</c:import>

<body>

<c:import url="../includes/left-nav.jsp" />

<section class="content">

    <c:import url="../includes/top-nav.jsp" />

    <div class="page-header">
        <h3> &nbsp; Request Report </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label"> Name: </label>
                            <p> Kieth </p>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"> Section: </label>
                            <p> Personnel </p>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"> Date of Request: </label>
                            <p> November 17, 2017 </p>
                        </div>
                        <div class="form-group">
                            <div class="panel-heading"> Requested Items </div>
                            <div class="panel-body">
                                <table class="table">
                                    <tr class="table-header">
                                        <th> Item </th>
                                        <th> Brand </th>
                                        <th> Item Type </th>
                                        <th> Quantity </th>
                                    </tr>
                                    <tr class="item-data">
                                        <td> Laptop </td>
                                        <td> ASUS </td>
                                        <td> Equipment </td>
                                        <td> 1 </td>
                                    </tr>
                                    <tr class="item-data">
                                        <td> Short Bond Paper </td>
                                        <td> Best Buy </td>
                                        <td> Goods </td>
                                        <td> 20 </td>
                                    </tr>
                                    <tr class="item-data">
                                        <td> Ball-point Pen </td>
                                        <td> HBW </td>
                                        <td> Semi-Expendable </td>
                                        <td> 2 </td>
                                    </tr>
                                    <tr class="item-data">
                                        <td> 4 GB Flash Drive </td>
                                        <td> Transcend </td>
                                        <td> Semi-Expendable </td>
                                        <td> 1 </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label"> Export as: </label>
                            <div class="col-sm-2">
                                <select class="form-group chosen-select">
                                    <option value="PDF"> PDF </option>
                                    <option value="Word"> Word </option>
                                    <option value="Excel"> Excel </option>
                                </select>
                            </div>
                            <button type="button"> Download File </button>
                        </div>
                        <div class="col-lg-4 col-lg-offset-4">
                            <button type="submit" class="btn btn-purple btn-block"> Confirm </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <c:import url="../includes/footer.jsp" />

</section>

</body>
</html>
