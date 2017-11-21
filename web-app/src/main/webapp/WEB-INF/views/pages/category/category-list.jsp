<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Category List"/>
    <c:param name="description" value="Category List Page"/>
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
            </div>

            <div class="btn-group visible-lg-inline-block">
                <a href="/category/create" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top" title="Add Category"><i class="fa fa-plus"></i></a>
                <button type="button" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top" title="Delete"><i class="fa fa-trash"></i></button>
            </div>
            <hr class="clean">


            <div class="panel panel-default">
                <div class="panel-body">

                    <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-hover" id="basic-datatable">
                        <thead>
                        <tr>
                            <th>
                                <label class="cr-styled">
                                    <input type="checkbox" ng-model="todo.done">
                                    <i class="fa"></i>
                                </label>
                            </th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${categories}" var="category">
                            <tr>
                                <td>
                                    <label class="cr-styled">
                                        <input type="checkbox" ng-model="todo.done">
                                        <i class="fa"></i>
                                    </label>
                                </td>
                                <td>
                                    <a href="/category/${category.categoryId}">${category.name}</a>
                                </td>
                                <td>
                                        ${category.description}
                                </td>
                                <td>
                                    <div class="btn-group visible-lg-inline-block">
                                        <a href="/category/update/${category.categoryId}" class="btn btn-purple tooltip-btn" data-toggle="tooltip" data-placement="top" title="Edit Item"><i
                                                class="fa fa-pencil"></i></a>
                                        <button type="button" class="btn btn-danger tooltip-btn" data-toggle="tooltip" data-placement="top" title="Delete" ><i class="fa fa-trash"></i></button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>
        </div>
            <!-- Warper Ends Here (working area) -->


    <%--<div class="page-header">--%>
        <%--<h3> &nbsp; Category </h3>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
        <%--<div class="col col-lg-3"/>--%>
        <%--<div class="col col-lg-9">--%>
            <%--<table class="table table-hover">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th class="col-md-5">Name</th>--%>
                    <%--<th class="col-md-5">Description</th>--%>
                    <%--<th class="col-md-2">Edit</th>--%>
                    <%--<th class="col-md-2">Delete</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<c:forEach items="${categories}" var="category">--%>
                    <%--<tr>--%>
                        <%--<td class="col-md-2"><a href="/category/${category.categoryId}">${category.name}</a></td>--%>
                        <%--<td class="col-md-2">${category.description}</td>--%>
                        <%--<td><a href="/category/update/${category.categoryId}"><img src="${resourceURL}/images/edit.png"--%>
                                                                                   <%--width="16"/></a></td>--%>
                        <%--<td><img src="${resourceURL}/images/delete.png" width="16"/></td>--%>
                    <%--</tr>--%>
                <%--</c:forEach>--%>
                <%--</tbody>--%>
            <%--</table>--%>
        <%--</div>--%>
    <%--</div>--%>
        <section class="navbar-fixed-bottom">
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>