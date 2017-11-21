
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
    <c:param name="title" value="Request Manager"/>
    <c:param name="description" value="List of Item"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">

    <c:import url="../../includes/top-nav.jsp"/>
    <div class="warper container-fluid">
        <div class="page-header">
            <h1>Requested Items
                <small>DepEd-Baguio City Division Office</small>
            </h1>

        </div>

        <div class="row item-body">

            <c:forEach items="${requests}" var="request" varStatus="loop">
                <form:form class="panel panel-warning col-md-4 no-padd">
                    <div class="panel-heading no-padd">
                    </div>
                    ${request.requestId}
                    ${request.user.firstName}
                    ${request.requestDetails}
                    <div class="panel-body no-padd">
                        <nav class="navbar-default hidden-xs col-md-12" role="navigation">
                            <ul class="nav navbar-nav">
                                <li><a href="#"><img src="../../assets/images/avtar/user.png" class="img-circle" alt="user#2" width="40"></a></li>
                                <li><a href="#"><strong>adhwheb u. gfffgf</strong></a></li>
                                <li class="dropdown">
                                    <a href="#" data-toggle="dropdown" class="col-md-2"><i class="fa fa-ellipsis-v"></i></a>
                                    <ul role="menu" class="dropdown-menu">
                                        <li><a href="#">Approve All</a></li>
                                        <li><a href="#">Reject All</a></li>
                                        <li class="divider">
                                        <li><a href="#">Notify User</a></li>
                                        </li>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                        </nav>
                        <%--<c:forEach items="${requestDetails}">--%>
                            <%--<div class="panel panel-default no-padd col-md-12">--%>
                                <%--<div class="panel-body">--%>
                                    <%--<label class="label label-info pull-left padd-xs text-right">HBW Ballpen</label>--%>
                                    <%--<p class="pull-right"><label>QTY: </label> 3 box</p>--%>
                                    <%--<div class="request-info col-md-12">--%>

                                        <%--<p>This Ballpen is for teachers of Boys High Baguio.</p>--%>
                                        <%--<div class="req-footer margn-t-sm">--%>
                                            <%--<p class="pull-left margn-t-xs text-gray">12/04/2017 11:21:00</p>--%>
                                            <%--<div class="btn-group pull-right">--%>
                                                <%--<button class="btn btn-danger btn-sm"><i class="fa fa-close"></i></button>--%>
                                                <%--<button class="btn btn-success btn-sm"><i class="fa fa-check"></i> </button>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>

                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</c:forEach>--%>


                    </div>
                </form:form>
            </c:forEach>

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





