<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
            <a href="/category/create" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top"
               title="Add Category"><i class="fa fa-plus"></i></a>
            <%--<button type="button" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top"
                    title="Delete"><i class="fa fa-trash"></i></button>--%>
        </div>
        <hr class="clean">


        <div class="panel panel-default">
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-hover"
                       id="basic-datatable">
                    <thead>
                    <tr>
                        <th>#
                            <%--<label class="cr-styled">
                                <input type="checkbox" ng-model="todo.done">
                                <i class="fa"></i>
                            </label>--%>
                        </th>
                        <th>Name</th>
                        <th>Description</th>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <th>Actions</th>
                        </sec:authorize>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${categories}" var="category" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}
                                <%--<label class="cr-styled">
                                    <input type="checkbox" ng-model="todo.done">
                                    <i class="fa"></i>
                                </label>--%>
                            </td>
                            <td>
                                <a href="<c:url value='/category/${category.name}' />">${category.name}</a>
                            </td>
                            <td>
                                    ${category.description}
                            </td>
                            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                                <td>
                                    <div class="btn-group visible-lg-inline-block">
                                        <a href="<c:url value='/category/update/${category.name}' />"
                                           class="btn btn-purple tooltip-btn" data-toggle="tooltip" data-placement="top"
                                           title="Edit Item"><i
                                                class="fa fa-pencil"></i></a>
                                        <%--<button type="button" class="btn btn-danger tooltip-btn" data-toggle="tooltip"
                                                data-placement="top" title="Delete"><i class="fa fa-trash"></i></button>--%>
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
    <c:import url="../../includes/footer.jsp"/>
</body>
</html>