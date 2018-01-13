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
            <h3>Manage Items
                <small>for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <%--<div class="btn-group">

            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                Item Type <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#">Goods</a></li>
                <li><a href="#">Semi-Expendable</a></li>
                <li><a href="#">Equipment</a></li>
            </ul>
        </div>

        <div class="btn-group">

            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                Brand <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#">Sony</a></li>
                <li><a href="#">Sandisk</a></li>
                <li><a href="#">Copy</a></li>
            </ul>
        </div>--%>

        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
            <div class="btn-group visible-lg-inline-block">
                <a href="/item/create" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top"
                   title="Add Item"><i class="fa fa-plus"></i></a>
                <%--<button type="button" class="btn btn-default tooltip-btn" data-toggle="tooltip" data-placement="top"
                        title="Delete"><i class="fa fa-trash"></i></button>--%>
            </div>
        </sec:authorize>

        <hr class="clean">


        <div class="panel panel-default">
            <div class="panel-body">

                <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-hover"
                       id="basic-datatable">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Image</th>
                        <th>Name</th>
                        <%--<th>Brand</th>--%>
                        <th class="col-xs-2">Description</th>
                        <th>Item Type</th>
                        <th>QTY</th>
                        <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                            <th>Actions</th>
                        </sec:authorize>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${itemList}" var="item" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
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
                                <%--<td>SONY</td>--%>
                            <td>${item.description}</td>
                            <td>${item.itemType}</td>
                            <td>${item.quantity}</td>
                            </td>
                            <sec:authorize access="hasRole('ROLE_SUPPLY_OFFICER')">
                                <td>
                                    <div class="btn-group visible-lg-inline-block">
                                        <a href="<c:url value='/item/update/${item.name}'/>"
                                           class="btn btn-purple tooltip-btn"
                                           data-toggle="tooltip" data-placement="top" title="Edit Item"><i
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

    <c:import url="../../includes/footer.jsp"/>
</body>
</html>





