
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <h1>Manage Items
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row item-body">


            <div class="panel panel-default">
                <h3 class="text-center">Created Items</h3>
                <div class="panel-body">
                    <div class="nav">
                        <a href="/item/create" class="btn btn-sm btn-default text-right" data-toggle="tooltips"
                           title="Add New Item">
                            <i class="fa fa-plus text-green"></i>
                        </a>
                        <button class="btn btn-sm btn-default text-right" data-toggle="tooltips" title="Delete Item">
                            <i class="fa fa-trash text-danger"></i>
                        </button>
                    </div>
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>
                                <label class="checkbox checkbox-inline">
                                    <input type="checkbox"/>
                                </label>
                            </th>
                            <th>Image</th>
                            <th>Name</th>
                            <th>Brand</th>
                            <th class="col-xs-2">Description</th>
                            <th>Item Type</th>
                            <th>QTY</th>
                            <th>Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${itemList}" var="item">
                            <tr>
                                <td>
                                    <label class="checkbox checkbox-inline">
                                        <input type="checkbox">
                                    </label>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty item.picName}">
                                            <img width="104px" height="76px" src="${baseUrl}${item.picName}"
                                                 alt="item image"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img width="104px" height="76px"
                                                 src="${resourceURL}/images/shared-images/no-item.png"
                                                 alt="item image"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <th>${item.name}</th>
                                <td>SONY</td>
                                <td>${item.description}</td>
                                <td>${item.itemType}</td>
                                <td>${item.quantity}</td>
                                </td>
                                <td>
                                    <a href="/item/update/${item.itemId}" class="btn btn-purple"><i
                                            class="fa fa-pencil"></i></a>
                                    <button class="btn btn-danger"><i class="fa fa-trash"></i></button>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <%--
            <a href="/item/${item.itemId}">
            ${item.functionType}
            ${item.threshold}
            <a href="/item/update/${item.itemId}"><img src="${resourceURL}/images/edit.png" width="16"/></a>
            <img src="${resourceURL}/images/delete.png" width="16"/>
        --%>

    </div>

    <c:import url="../../includes/footer.jsp"/>

</body>
</html>





