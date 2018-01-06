<%--
  Created by IntelliJ IDEA.
  User: mehdi
  Date: 10/22/17
  Time: 10:39 AM
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
    <c:param name="title" value="Insert Equipment Info"/>
    <c:param name="description" value="Insert Equipment Info Page"/>
</c:import>
<html>
<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Insert Information
                <small>for Arrived Equipment</small>
            </h3>
        </div>

        <c:set var="errors"
               value="${requestScope['org.springframework.validation.BindingResult.requestDetails'].allErrors}"/>
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

        <div class="row">

            <div class="request-body-container">
                <nav class="col-md-12 padd-t-lg">

                    <form role="search" class="col-md-6 col-lg-offset-3 hidden-xs">
                        <div class="app-search">
                            <input type="text" placeholder="Search for an item..."
                                   class="form-control form-control-circle">
                            <hr class="clean">
                        </div>

                    </form>
                </nav>

                <div class="item-body col-lg-12">
                    <!-- Items-thumbnail-and-content-of-the-item-thumbnail -->

                    <div class="row">

                        <c:forEach items="${itemDetailsBeanForm.captureInfoList}" var="capInfo"
                                   varStatus="loop">

                            <div class='col-xs-3 thumbnail item-content-thumbnail2'>

                                <c:choose>
                                    <c:when test="${not empty capInfo.pictureName}">
                                        <img src="${baseUrl}${capInfo.pictureName}" alt="item image"
                                             style="max-height: 200px;"/>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${resourceURL}/images/shared-images/no-item.png" alt="item image"
                                             height="200px"/>
                                    </c:otherwise>
                                </c:choose>

                                <div class="item-infomation text-center">
                                    <h4>${capInfo.itemName}</h4>
                                    <div class="form-group">
                                        <label>Available QTY in Supply
                                            Office:</label><span>&nbsp;${capInfo.quantityAvailableInSupplyOffice}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Ordered QTY:</label>
                                        <span>&nbsp;${capInfo.totalReuqestedQuantity}</span>
                                    </div>
                                    <div class="form-group">
                                        <label>Inserted Info QTY:</label>
                                        <span>&nbsp;${capInfo.numberOfCapturedItems}</span>
                                    </div>

                                    <div class="form-group clearfix">
                                        <label class="col-md-12">Must Insert QTY:</label>
                                        <div class="col-md-6 col-md-push-3">
                                            <input class="form-control input-sm text-center no-padd"
                                                   type="number"
                                                   value="${capInfo.numberOfRemainingCapturedItems}" disabled/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <c:choose>
                                            <c:when test="${capInfo.numberOfRemainingCapturedItems > 0}">
                                                <button class="btn btn-md btn-purple" data-toggle="collapse"
                                                        data-target="#demo${loop.index}" type="button">Insert Info
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-md btn-purple" type="button" disabled>Insert Info
                                                </button>
                                            </c:otherwise>
                                        </c:choose>

                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <form:form commandName="itemDetailsBeanForm" method="post">
                        <c:forEach items="${itemDetailsBeanForm.captureInfoList}" var="capInfo"
                                   varStatus="loop">
                            <c:if test="${capInfo.numberOfRemainingCapturedItems > 0}">
                                <c:set var="insertExist" value="yes" scope="page"/>
                                <div class="row">

                                    <div id="demo${loop.index}" class="collapse">
                                        <div class="panel panel-default">
                                            <div class="panel-heading">Insert equipment info for
                                                new ${capInfo.numberOfRemainingCapturedItems} ${capInfo.itemName}
                                            </div>
                                            <div class="panel-body">
                                                <c:set var="itemDetailsList"
                                                       value="${capInfo.itemDetailsList}"/>
                                                <div class="table-responsive" style="overflow:scroll">
                                                    <table cellspacing="0" border="0"
                                                           class="table table-striped table-hover"
                                                           id="basic-datatable">
                                                        <thead>
                                                        <th>#</th>
                                                        <th>Item</th>
                                                        <th>Office Sr #</th>
                                                        <th>Colour</th>
                                                        <th>Price</th>
                                                        <th>Equipment Sr #</th>
                                                        <th>Material</th>
                                                        <th>Weight</th>
                                                        <th>Life Span</th>

                                                        </thead>

                                                        <tr class="info">
                                                            <td><a href="#"
                                                                   onclick="insertDummy(${loop.index}, ${fn:length(capInfo.itemDetailsList)})"><img
                                                                    src="${resourceURL}/images/duplicate.png"/></a></td>
                                                            <td><input value="${capInfo.itemName}" disabled/></td>
                                                            <td>
                                                                <div class="input-group">
                                                                    <input id="officeSerialNo-${loop.index}"
                                                                                                placeholder="constant part" class="form-control"/>
                                                                    <span class="input-group-addon"> - </span>
                                                                    <input id="officeSerialNoChanging-${loop.index}" class="form-control"/>
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <select class="form-control" id="colour-${loop.index}">
                                                                    <c:forEach var="colour" items="${colours}">
                                                                        <option value="${colour}">${colour.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>

                                                            <td><input class="form-control"
                                                                       id="purchasePrice-${loop.index}"/></td>
                                                            <td><input class="form-control"
                                                                       id="equipmentSerialNo-${loop.index}"/></td>
                                                            <td>
                                                                <select class="form-control"
                                                                        id="material-${loop.index}">
                                                                    <c:forEach var="material" items="${materials}">
                                                                        <option value="${material}">${material.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <td><input class="form-control"
                                                                       id="weightInGram-${loop.index}"/></td>
                                                            <td><input class="form-control"
                                                                       id="lifeSpan-${loop.index}"/></td>
                                                        </tr>
                                                        <c:forEach items="${capInfo.itemDetailsList}" varStatus="loop2"
                                                                   var="itemDetails">
                                                            <tr>
                                                                <td>${loop2.index + 1}</td>
                                                                <td><form:input
                                                                            path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].item"
                                                                            value="${capInfo.itemName}" disabled="true"
                                                                            id="item-${loop.index}"
                                                                            cssClass="form-control"/>

                                                                </td>
                                                                <td><form:input
                                                                        path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].officeSerialNo"
                                                                        cssClass="officeSerialNo-${loop.index} form-control"/></td>

                                                                <td>
                                                                    <form:select
                                                                            cssClass="colour-${loop.index} form-control"
                                                                            multiple="single"
                                                                            path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].colour">
                                                                        <form:options items="${colours}"
                                                                                      itemLabel="name"/>
                                                                    </form:select>

                                                                </td>

                                                                <td><form:input
                                                                        path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].purchasePrice"
                                                                        cssClass="purchasePrice-${loop.index} form-control"/></td>


                                                                <td>
                                                                        <%--<form:hidden
                                                                            path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].orderDetails"/>--%>
                                                                    <form:input
                                                                            path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].equipmentSerialNo"
                                                                            cssClass="equipmentSerialNo-${loop.index} form-control"/></td>

                                                                <td><form:select
                                                                        cssClass="material-${loop.index} form-control"
                                                                        multiple="single"
                                                                        path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].material">
                                                                    <form:options items="${materials}"
                                                                                  itemLabel="name"/>
                                                                </form:select>
                                                                </td>
                                                                <td><form:input
                                                                        path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].weightInGram"
                                                                        cssClass="weightInGram-${loop.index} form-control"/></td>
                                                                <td><form:input
                                                                        path="captureInfoList['${loop.index}'].itemDetailsList['${loop2.index}'].lifeSpan"
                                                                        cssClass="lifeSpan-${loop.index} form-control"/></td>


                                                            </tr>
                                                        </c:forEach>

                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                        <c:if test="${not empty insertExist}">
                            <div class="row">
                                <div>
                                    <button type="submit" class="col-md-2 btn btn-lg btn-default">Submit
                                        Info
                                    </button>
                                </div>
                            </div>
                        </c:if>

                    </form:form>
                </div>
            </div>
        </div>
    </div>
        <br/>
        <br/>
    <!-- Warper Ends Here (working area) -->
        <c:import url="../../includes/footer.jsp"/>
        <script src="${resourceURL}/js/additional/item-info.js" type="text/javascript"></script>

</body>
</html>

