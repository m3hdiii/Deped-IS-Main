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
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>
    <c:choose>
    <c:when test="${not empty successfullyCreated}">
    <p style="color: green;">${successfullyCreated}</p>
    &nbsp;&nbsp;<a href="/item/list">Item List</a>
    </c:when>
    </c:choose>
    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Item Registration
                <small>for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>
        <div class="row new-item-body">

            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.item'].allErrors}"/>

                        <form:form commandName="item" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">
                            <c:if test="${not empty errors}">
                                <div>
                                    <ul class="list-group">
                                        <c:forEach items="${errors}" var="error" varStatus="loop">
                                            <li class="list-group-item list-group-item-warning text-danger"><span
                                                    class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <h3 class="text-center">Add New Item</h3>
                            <div class="panel-body">
                                <div class="col-md-10 col-sm-offset-1">
                                    <c:choose>
                                        <c:when test="${not empty notCreated}">
                                            <div class="alert alert-danger alert-dismissable fade in">
                                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                <strong>Failed!</strong> ${notCreated}.
                                            </div>
                                        </c:when>
                                        <c:when test="${not empty successfullyCreated}">
                                            <div class="alert alert-success alert-dismissable fade in">
                                                <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                <strong>Success!</strong> ${successfullyCreated}.
                                            </div>
                                        </c:when>
                                    </c:choose>

                                    <div class="form-group">
                                        <label for="newItemName">Name</label>
                                        <form:input path="name" class="form-control typeahead" placeholder="Item Name" id="newItemName"/>
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label for="newItemBrand">Brand</label>--%>
                                        <%--<select id="newItemBrand" class="form-control">--%>
                                            <%--<option disabled selected>Choose...</option>--%>
                                            <%--<option>Generic</option>--%>
                                            <%--<option>Sand Disk</option>--%>
                                            <%--<option>Bosch</option>--%>
                                            <%--<option>Standard</option>--%>
                                            <%--<option>SONY</option>--%>
                                            <%--<option>SAMSUNG</option>--%>
                                        <%--</select>--%>
                                    <%--</div>--%>
                                    <%--<div class="text-right">--%>
                                        <%--<a href="/brand/create" class="btn btn-purple btn-xs"><i class="fa fa-plus-circle"> Add Brand</i></a>--%>
                                    <%--</div>--%>
                                    <div class="form-group">
                                        <label for="newItemDesc">Description</label>
                                        <form:textarea type="text" path="description" class="form-control typeahead"
                                                   placeholder="Item Description Here..." cols="3" rows="3" id="newItemDesc" maxlength="100"></form:textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemImg">Image</label>
                                        <input name="itemPic" type="file" class="form-control-file text-danger" id="newItemImg" aria-describedby="fileHelp">
                                        <small id="fileHelp" class="form-text text-muted"></small>
                                    </div>

                                    <div class="form-group">
                                        <label for="newItemThresh">Threshold</label>
                                        <form:input path="threshold" min="0" type="number" class="form-control" id="newItemThresh"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemType">Item Type</label>

                                        <form:select path="itemType" id="newItemType" class="form-control chosen-select"
                                                     data-placeholder="Select a Type">
                                            <form:option value="" label="--Please Select"/>
                                            <form:options items="${itemType}" itemLabel="name"/>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemCategory">Function Type</label>
                                        <form:select path="functionType" id="newItemCategory" class="form-control chosen-select"
                                                     data-placeholder="Select a Category">
                                            <form:option value="" label="--Please Select"/>
                                            <form:options items="${functionType}" itemLabel="name"/>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <form:hidden path="previousIdName" value="${item.name}"/>

                            <div class="modal-footer">
                                <a href="/item/list" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i> Back</a>
                                <div class="button-footer pull-right">
                                    <button type="reset" class="btn btn-default">Clear</button>
                                    <button type="submit" class="btn btn-primary">Create</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div><!-- New Item Body closing -->
    </div><!-- Warper Ends Here (working area) -->
            <c:import url="../../includes/footer.jsp"/>
</body>
</html>
