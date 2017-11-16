<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page isELIgnored="false" %>

<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html lang="en">

<c:url value="/" var="routePath" scope="request"/>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Brand List"/>
    <c:param name="description" value="Brand List Page"/>
</c:import>

<body>

<c:import url="../../includes/left-nav.jsp"/>

<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>


    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Brand List
                <small>DepEd-Baguio City Division Office</small>
            </h1>
        </div>


        <div class="row item-body">

            <div role="toolbar" class="btn-toolbar">
                <div class="pull-left">
                    <%--<button class="btn btn-purple btn-sm">Select All</button>--%>
                    <a href="/brand/create" class="btn btn-default btn-sm"><i class="fa fa-plus text-success"></i></a>
                    <%--<button class="btn btn-default btn-sm"><i class="fa fa-trash text-danger"></i></button>--%>
                </div>
            </div>

            <div class="brand-list-body padd-t-lg">
                <!-- Brand-thumbnail-and-content-of-the-brand-thumbnail -->
                <c:forEach items="${brands}" var="brand">
                    <div class='col-xs-4 thumbnail brand-content-thumbnail padd-md'>
                            <%--<div class="round">
                                <input class="pull-left" type="checkbox" id="checkbox ${brand.brandId}"/>
                                <label for="checkbox ${brand.brandId}"></label>
                            </div>--%>
                        <div class="dropdown pull-right">
                            <a href="#" data-toggle="dropdown"><i class="fa fa-ellipsis-v text-purple"></i></a>
                            <ul class="dropdown-menu">
                                <li><a href="/brand/update/${brand.brandId}"><i class="fa fa-pencil text-primary"></i>
                                    Edit</a></li>
                                <li><a href="#"><i class="fa fa-trash text-danger"></i> Delete</a></li>
                            </ul>
                        </div>

                        <div class="item-infomation text-center">
                            <h3><a href="/category/${brand.brandId}">${brand.name}</a></h3>
                            <!-- <small>The .img-thumbnail class creates a thumbnail of the image:</small> -->
                            <details>
                                <summary class="officeAddress">Brand Name</summary>
                                <small>${brand.name}</small>
                            </details>
                            <c:choose>
                                <c:when test="${not empty brand.logoUrl}">
                                    <img width="104px" height="76px" src="${baseUrl}${brand.logoUrl}"
                                         alt="item image"/>
                                </c:when>
                                <c:otherwise>
                                    <img width="104px" height="76px"
                                         src="${resourceURL}/images/shared-images/no-brand.jpg"
                                         alt="item image"/>
                                </c:otherwise>
                            </c:choose>
                            <hr size="30">
                            <div class="address col-xs-6">
                                <details>
                                    <summary class="officeAddress">Office Address</summary>
                                    <small>${brand.centralOfficeAddress}</small>
                                </details>
                                <details>
                                    <summary class="serviceCenter">Service Center Address</summary>
                                    <small>${brand.serviceCenterAddress}</small>
                                </details>
                            </div>

                            <div class="col-xs-6 contacts">
                                <p><label><i class="fa fa-mobile-phone"></i></label>${brand.contactNumber}</p>
                                <p><label><i class="fa fa-phone"></i></label>${brand.contactNumber2}</p>
                            </div>

                        </div>
                    </div>
                </c:forEach>

            </div>


            <!-- PAGINATION FOR THE ITEMS -->

            <div class="items-pagination col-md-12 text-center">
                <nav aria-label="Pagination-for-items">
                    <ul class="pagination">
                        <li class="page-item disabled">
                            <a class="page-link" href="#" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                                <span class="sr-only">Previous</span>
                            </a>
                        </li>
                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                        <li class="page-item">
                            <a class="page-link" href="#" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                                <span class="sr-only">Next</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    </div> <!-- Warper Ends Here (working area) -->

    <c:import url="../../modals/cart.jsp"/>
</section>
<section class="content">
    <c:import url="../../includes/footer.jsp"/>
</section>
</body>
</html>