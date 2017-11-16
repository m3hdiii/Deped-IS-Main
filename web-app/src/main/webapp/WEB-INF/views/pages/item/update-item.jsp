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
            <h1>Edit Item
                <small>DepEd-Baguio City Division Office</small>
            </h1>
        </div>

        <div class="row new-item-body">
            <div class="col-md-12">
                <div class="col-md-8 col-md-offset-2">
                    <div class="panel panel-default">
                        <c:set var="errors"
                               value="${requestScope['org.springframework.validation.BindingResult.item'].allErrors}"/>

                        <form:form commandName="item" method="post" class="form-horizontal" role="form"
                                   enctype="multipart/form-data">
                            <c:if test="${not empty errors}">
                                <div>
                                    <ul class="list-group">
                                        <c:forEach items="${fieldErrors}" var="error" varStatus="loop">
                                            <li class="list-group-item list-group-item-warning text-danger"><span
                                                    class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;${error.defaultMessage}
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </c:if>
                            <div class="item-image text-center">
                                <h3>Update Item Info</h3>
                            </div>

                            <div class="panel-body">
                                <div class="col-md-10 col-sm-offset-1">
                                    <div class="text-center form-group">

                                        <c:if test="${not empty item.picName}">
                                            <div class="row col-md-12">
                                                <p class="text-center"><img width="180" height="150"
                                                                            src="${baseUrl}${item.picName}"
                                                                            alt="item image"/></p>
                                            </div>
                                        </c:if>
                                        <input class="btn btn-purple btn-sm" type="file"/>
                                        <%--<button class="btn btn-purple btn-sm" type="file"><i class="fa fa-pencil"></i>--%>
                                        </input>

                                    </div>

                                    <div class="form-group">
                                        <label for="updateItemName">Name</label>
                                        <form:input path="name" type="text" class="form-control" id="updateItemName"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateItemBrand">Brand</label>
                                        <select id="updateItemBrand" class="form-control">
                                            <option disabled selected>Choose...</option>
                                            <option>Generic</option>
                                            <option>Sand Disk</option>
                                            <option selected>Bosch</option>
                                            <option>Standard</option>
                                            <option>SONY</option>
                                            <option>SAMSUNG</option>
                                        </select>
                                    </div>
                                    <div class="text-right">
                                        <button class="btn btn-purple btn-xs" data-toggle="modal"
                                                data-target="#new-brand-modal"><i class="fa fa-plus-circle"> Add
                                            Brand</i></button>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateItemDesc">Description</label>
                                        <form:textarea path="description" type="text" class="form-control limit-char-45"
                                                       cols="3" rows="3" id="updateItemDesc"
                                                       maxlength="100"></form:textarea>
                                        <!--<span class="word-count">0</span> / <span class="word-left">100</span>-->
                                    </div>

                                    <div class="form-group">
                                        <label for="updateItemThresh">Threshold</label>
                                        <form:input path="threshold" type="number" class="form-control"
                                                    id="updateItemThresh"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateItemType">Item Type</label>
                                        <form:select path="itemType" id="updateItemType"
                                                     class="form-control chosen-select"
                                                     data-placeholder="Select a Category">
                                            <!-- <option>Select a Category</option> -->
                                            <form:option value="GOODS">Goods</form:option>
                                            <form:option value="SEMI_EXPENDABLE">Semi-Expendables</form:option>
                                            <form:option value="EQUIPMENT">Equipment</form:option>
                                        </form:select>
                                    </div>
                                    <div class="form-group">
                                        <label for="updateFunctionType">Function Type</label>
                                        <form:select path="functionType" id="updateFunctionType"
                                                     class="form-control chosen-select"
                                                     data-placeholder="Select a Category">
                                            <form:option value="ELECTRICAL">Electrical</form:option>
                                            <form:option value="NON_ELECTRICAL">Non Electrical</form:option>
                                        </form:select>
                                    </div>

                                    <form:hidden path="quantity"/>
                                    <form:hidden path="picName"/>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i>
                                    Back</a>
                                <div class="button-footer pull-right">
                                    <input type="button" class="btn btn-default" value="Clear"/>
                                    <button type="submit" class="btn btn-primary">Save</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div> <!-- New Item Body closing -->
        </div>
    </div> <!-- Warper Ends Here (working area) -->


    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>
