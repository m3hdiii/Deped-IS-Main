<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="List of Departments"/>
    <c:param name="description" value="This page retrieves all the list of existing departments"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">

            <div class="page-header">
                <h3>Department
                    <small>DepEd-Baguio City Division Office</small>
                </h3>
            </div>

            <div class="btn-group visible-lg-inline-block">
                <a href="/department/create" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top" title="Add Department"><i class="fa fa-plus"></i></a>
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
                            <th>Department Head</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${departments}" var="department">
                            <tr>
                                <td>
                                    <label class="cr-styled">
                                        <input type="checkbox" ng-model="todo.done">
                                        <i class="fa"></i>
                                    </label>
                                </td>
                                <td>
                                    <a href="<c:url value='/department/${department.name}' />">${department.name}</a>
                                </td>
                                <td>${department.description}</td>
                                <td>${department.departmentHead}</td>
                                <td>
                                    <div class="btn-group visible-lg-inline-block">
                                        <a href="<c:url value='/department/update/${department.name}' />"
                                           class="btn btn-purple tooltip-btn" data-toggle="tooltip" data-placement="top"
                                           title="Edit Department"><i
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

            <c:import url="../../includes/footer.jsp"/>
</body>
</html>