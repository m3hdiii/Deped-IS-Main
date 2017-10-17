<%--
  Created by Kieth Opiniano
  User: Win8
  Date: 10/4/2017
  Time: 4:15 PM
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
    <c:param name="title" value="Search User"/>
    <c:param name="description" value="Enables to search for certain users"/>
</c:import>

<body>

<c:import url="../includes/left-nav.jsp" />

<section class="content">

    <c:import url="../includes/top-nav.jsp" />

    <div class="page-header">
        <h3> &nbsp; Search User </h3>
    </div>

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-body">
                    <form class="form-horizontal" role="search">
                        <div class="col-md-12 form-group">
                            <div class="col-md-9">
                                <input type="text" placeholder="Search" class="form-control form-control-circle">
                            </div>
                            <div class="col-sm-2">
                                <button type="submit" class="btn btn-block"> Search</button>
                            </div>
                        </div>
                        <div class="col-md-12 form-group">
                            <label class="col-sm-2 control-label"> Search By: </label>
                            <div class="col-sm-2">
                                <select class="form-control chosen-select" data-placeholder="Personnel">
                                    <option value="Personnel"> Personnel</option>
                                    <option value="Admin"> Admin</option>
                                    <option value="Officer"> Officer</option>
                                </select>
                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>

        <div class="col-md-12">
            <ul class="list-group col-md-9">
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
                <li class="list-group-item col-md-6">
                    <a href="" class="col-md-11">
                        <div class="col-md-3">
                            <img src="" height="70" width="70">
                        </div>
                        <div class="list-group-item-text col-md-5">
                            <p> Name: </p>
                            <p> Section: </p>
                            <p> Account Type: </p>
                        </div>
                    </a>
                </li>
            </ul>
        </div>
    </div>


    <c:import url="../includes/footer.jsp" />

</section>

</body>
</html>
