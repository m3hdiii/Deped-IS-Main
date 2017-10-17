<%--
  Created by IntelliJ IDEA.
  User: kevin
  Date: 23/09/2017
  Time: 11:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%
    //System.out.println(getServletContext().getRealPath("/"));
%>
<c:url value="/public" var="resourceURL" scope="request"/>
<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Goods Registration"/>
    <c:param name="description" value="Goods Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="page-header">
        <h3>&nbsp;&nbsp;&nbspItem Registration&nbsp;<small>&nbsp;for Goods and Semi-Expendable</small>
        </h3>
    </div>

    <div>


        <c:choose>
            <c:when test="${not empty notCreated}">
                <p style="color: red;">${notCreated}</p>
            </c:when>


            <c:when test="${not empty successfullyCreated}">
                <p style="color: green;">${successfullyCreated}</p>
                &nbsp;&nbsp;<a href="/goods/create">Create New Goods</a>
            </c:when>

        </c:choose>
    </div>

    <div class="row">

        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">Goods Information</div>
                <div class="panel-body">
                    <form:form commandName="goods" method="post" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Name</label>
                            <div class="col-sm-7">
                                <form:input path="name" class="form-control typeahead" placeholder=""/>
                            </div>
                        </div>
                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Description</label>
                            <div class="col-lg-7">
                                <form:textarea path="description" class="col-sm-7 form-control typeahead"
                                               placeholder="enter description here..." rows="7"></form:textarea>
                            </div>
                        </div>
                        <hr class="style13">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Item Type</label>
                            <div class="col-sm-7">
                                <form:select path="itemType" class="form-control chosen-select"
                                             data-placeholder="Select a Category">
                                    <!-- <option>Select a Category</option> -->
                                    <form:option value="GOODS">Goods</form:option>
                                    <form:option value="SEMI_EXPENDABLE">Semi-Expendables</form:option>
                                </form:select>
                            </div>
                        </div>
                        <hr class="style13">
                        <%--
                                                <div class="form-group">
                                                    <label class="col-sm-2 control-label">Visibility</label>
                                                </div>


                                                                        <div class="form-group">
                                                                            <label class="col-sm-4 control-label">Supply Officer</label>
                                                                            <div class="col-sm-1">
                                                                                <form:checkbox path="visibility" class="form-control"/>
                                                                            </div>
                                                                        </div>

                                                                        <div class="form-group">
                                                                            <label class="col-sm-4 control-label">Chief</label>
                                                                            <div class="col-sm-1">
                                                                                <form:checkbox path="visibility" class="form-control"/>
                                                                            </div>
                                                                        </div>

                                                                        <div class="form-group">
                                                                            <label class="col-sm-4 control-label">Personnel</label>
                                                                            <div class="col-sm-1">
                                                                                <form:checkbox path="visibility"  class="form-control"/>
                                                                            </div>
                                                                        </div> --%>

                        <hr class="style13">

                        <div class="form-group">
                            <label class="col-sm-2 control-label">Threshold</label>
                            <div class="col-sm-2">
                                <form:input path="threshold" type="number" class="form-control"/>
                            </div>
                        </div>

                        <%--
                        <div class="form-group">
                            <label class="col-sm-2 control-label">Quantity</label>
                            <div class="col-sm-2">
                                <form:input path="quantity" type="number" class="form-control"/>
                            </div>
                        </div>
                        --%>

                        <hr class="style13">

                        <div class="btn-group-sm row">
                            <div class="col-sm-2">
                                <button type="submit" class="btn btn-success btn-block">Confirm</button>
                            </div>
                            <div class="form-group col-sm-2">
                                <button type="reset" class="btn btn-primary btn-block">Reset Fields</button>
                            </div>
                        </div>

                        <hr class="style13">

                    </form:form>
                </div>
            </div>
        </div>

    </div>

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>
