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

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>&nbsp;&nbsp;&nbspItem Registration&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
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

        <div class="row new-item-body">

            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <form:form commandName="item" method="post" class="form-horizontal" role="form" enctype="multipart/form-data">
                            <h3 class="text-center">Add New Item</h3>
                            <div class="panel-body">
                                <div class="col-md-10 col-sm-offset-1">
                                    <div class="form-group">
                                        <label for="newItemName">Name</label>
                                        <form:input path="name" class="form-control typeahead" placeholder="" id="newItemName"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemBrand">Brand</label>
                                        <select id="newItemBrand" class="form-control">
                                            <option disabled selected>Choose...</option>
                                            <option>Generic</option>
                                            <option>Sand Disk</option>
                                            <option>Bosch</option>
                                            <option>Standard</option>
                                            <option>SONY</option>
                                            <option>SAMSUNG</option>
                                        </select>
                                    </div>
                                    <div class="text-right">
                                        <button class="btn btn-purple btn-xs" data-toggle="modal" data-target="#new-brand-modal"><i class="fa fa-plus-circle"> Add Brand</i></button>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemDesc">Description</label>
                                        <form:textarea type="text" path="description" class="form-control typeahead"
                                                   placeholder="enter description here..." cols="3" rows="3" id="newItemDesc" maxlength="100"></form:textarea>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemImg">Image</label>
                                        <input name="itemPic" type="file" class="form-control-file text-danger" id="newItemImg" aria-describedby="fileHelp">
                                        <small id="fileHelp" class="form-text text-muted"></small>
                                    </div>

                                    <div class="form-group">
                                        <label for="newItemThresh">Threshold</label>
                                        <form:input path="threshold" type="number" class="form-control" id="newItemThresh"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemType">Type</label>
                                        <form:select path="itemType" id="newItemType" class="form-control chosen-select"
                                                     data-placeholder="Select a Type">
                                            <!-- <option>Select a Category</option> -->
                                            <form:option value="GOODS">Goods</form:option>
                                            <form:option value="SEMI_EXPENDABLE">Semi-Expendables</form:option>
                                            <form:option value="EQUIPMENT">Equipment</form:option>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <label for="newItemCategory">Type</label>
                                        <form:select path="functionType" id="newItemCategory" class="form-control chosen-select"
                                                     data-placeholder="Select a Category">
                                            <form:option value="ELECTRICAL">Electrical</form:option>
                                            <form:option value="NON_ELECTRICAL">Non Electrical</form:option>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i> Back</a>
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

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>
