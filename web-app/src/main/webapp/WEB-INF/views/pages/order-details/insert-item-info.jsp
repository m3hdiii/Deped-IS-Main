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


                    <ul class="list-inline col-md-12">
                        <li class="pull-right">
                            <a href="#" class="text-purple"><i class="fa fa-list fa-lg"></i> </a>
                        </li>

                        <li class="pull-right active">
                            <a href="#" class="text-purple"><i class="fa fa-th-large fa-lg"></i></a>
                        </li>
                        <li>
                            <div class="form-inline">
                                <label for="sortingItem">Sort by:</label>
                                <select id="sortingItem" class="form-control form-control-flat input-sm">
                                    <option>Name: A-Z</option>
                                    <option>Name: Z-A</option>
                                    <option>Quantity: Ascending</option>
                                    <option>Quantity: Decending</option>
                                </select>
                            </div>
                        </li>

                        <li>
                            <div class="form-inline">
                                <label for="itemType">Type:</label>
                                <select id="itemType" class="form-control input-sm">
                                    <option>All</option>
                                    <option>Goods</option>
                                    <option>Semi-Expandable</option>
                                    <option>Equipment</option>
                                </select>
                            </div>
                        </li>

                        <li>
                            <div class="form-inline">
                                <label for="filterBrand">Brand:</label>
                                <select id="filterBrand" class="form-control input-sm">
                                    <option>Brand Name</option>
                                    <option>Brand Name</option>
                                    <option>Choose Brand Name Here</option>
                                    <option>Choose Brand Naem Here</option>
                                </select>
                            </div>
                        </li>
                    </ul>
                </nav>

                <div class="item-body col-md-9">
                    <!-- Items-thumbnail-and-content-of-the-item-thumbnail -->


                    <c:forEach items="${itemDetailsBeanForm.itemDetailsBeans}" var="itemDetailsBean"
                               varStatus="loop">

                        <div class='col-xs-3 thumbnail item-content-thumbnail2'>

                            <c:set var="capInfo" value="${itemDetailsBean.captureInfo}"/>

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
                                    <label>Arrived QTY:</label>
                                    <span>&nbsp;${capInfo.numberOfArrivedItems}</span>
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
                                            <button class="btn btn-md btn-purple" type="submit">Insert Info</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-md btn-purple" type="submit" disabled>Insert Info
                                            </button>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div>


                    <form:form commandName="itemDetailsBeanForm" method="post">
                        <c:forEach items="${itemDetailsBeanForm.itemDetailsBeans}" var="itemDetailsBean"
                                   varStatus="loop">
                            <c:forEach items="${itemDetailsBean.itemDetailsList}" var="itemDetails">

                            </c:forEach>

                            <form:input path="itemDetailsBeans.itemDetailsList[loop.index]"/>
                        </c:forEach>
                    </form:form>


                    <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                        <c:set var="strKey" value="${entry.key}"/>
                        <c:set var="orderDet" value="${entry.value}"/>
                    </c:forEach>
                    </form:form>
                </div>

            </div>
        </div>
    </div>
    <!-- Warper Ends Here (working area) -->
    <%--<c:import url="../../includes/footer.jsp"/>--%>

</body>
</html>

