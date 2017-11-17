<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Pack List"/>
    <c:param name="description" value="Pack List Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>

        <div class="warper container-fluid">

            <div class="page-header">
                <h3>Pack
                    <small>for Goods, Semi-Expendable and Equipment</small>
                </h3>
            </div>


            <div class="panel panel-default">
                <div class="panel-heading">Packages</div>
                <div class="panel-body">

                    <table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered" id="basic-datatable">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Associated Item</th>
                            <th>Edit</th>
                            <th>Remove</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${packs}" var="pack">
                            <tr>
                                <td>${pack.name}</td>
                                <td>${pack.description}</td>
                                <td>${pack.item.name}</td>
                                <td>
                                    <a href="/pack/update/${pack.packId}" class="btn btn-purple"><i class="fa fa-pencil"></i></a>
                                </td>
                                <td>
                                    <button class="btn btn-danger"><i class="fa fa-trash"></i> </button>
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
        <%--<h3> Pack List </h3>--%>
    <%--</div>--%>

    <%--<div class="row">--%>
        <%--<div class="col col-lg-3"/>--%>
        <%--<div class="col col-lg-9">--%>
            <%--<table class="table table-hover">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                    <%--<th class="col-md-5">Name</th>--%>
                    <%--<th class="col-md-5">Description</th>--%>
                    <%--<th class="col-md-5">Associated Item</th>--%>
                    <%--<th class="col-md-5">Edit Section</th>--%>
                    <%--<th class="col-md-5">Remove Section</th>--%>

                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<c:forEach items="${packs}" var="pack">--%>
                    <%--<tr>--%>
                        <%--<td class="col-md-2">${pack.name}</td>--%>
                        <%--<td class="col-md-2">${pack.description}</td>--%>
                        <%--<td class="col-md-2">${pack.item.name}</td>--%>
                        <%--<td class="col-md-2"><a href="/pack/update/${pack.packId}"><img src="${resourceURL}/images/edit.png"--%>
                                                                       <%--width="16"/></a></td>--%>
                        <%--<td class="col-md-2"><img src="${resourceURL}/images/delete.png" width="16"/></td>--%>
                    <%--</tr>--%>
                <%--</c:forEach>--%>
                <%--</tbody>--%>
            <%--</table>--%>
        <%--</div>--%>
    <%--</div>--%>

    <c:import url="../../includes/footer.jsp"/>

</body>
</html>