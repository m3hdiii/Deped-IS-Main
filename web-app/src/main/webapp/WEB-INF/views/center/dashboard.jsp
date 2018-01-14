<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/public" var="resourceURL" scope="request"/>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<c:import url="../includes/head.jsp">
    <c:param name="title" value="Dashboard"/>
    <c:param name="description" value="Dashboard page"/>
</c:import>
<body data-ng-app>

<c:import url="../includes/left-nav.jsp" />

<section class="content">

<c:import url="../includes/top-nav.jsp" />

    <div class="warper container-fluid">

        <div class="page-header">
            <h1>Dashboard
                <small>DepEd Division Office Inventory System</small>
            </h1>
        </div>
        <div>
            <c:if test="${not empty dataUpdateSuccess}">
                <p style="color: green;">${dataUpdateSuccess}</p>
            </c:if>
        </div>


        <div class="row">

            <div class="col-md-3 col-sm-6">
                <div class="panel panel-default clearfix dashboard-stats rounded">
                    <span id="dashboard-stats-sparkline1" class="sparkline transit"></span>
                    <i class="fa fa-cubes bg-purple transit stats-icon"></i>
                    <h3 class="transit">${dashboardInfo.lastWeekRequest}</h3>
                    <p class="text-muted transit">New Requests</p>
                </div>
            </div>
            <div class="col-md-3 col-sm-6">
                <div class="panel panel-default clearfix dashboard-stats rounded">
                    <span id="dashboard-stats-sparkline2" class="sparkline transit"></span>
                    <i class="fa fa-truck bg-info transit stats-icon"></i>
                    <h3 class="transit">${dashboardInfo.lastWeekOrders}
                    </h3>
                    <p class="text-muted transit">New Orders</p>
                </div>
            </div>
            <div class="col-md-3 col-sm-6">
                <div class="panel panel-default clearfix dashboard-stats rounded">
                    <span id="dashboard-stats-sparkline4" class="sparkline transit"></span>
                    <i class="fa fa-warning bg-warning transit stats-icon"></i>
                    <h3 class="transit">${dashboardInfo.underThreshold}
                    </h3>
                    <p class="text-muted transit">Low Quantity</p>
                </div>
            </div>
            <div class="col-md-3 col-sm-6">
                <div class="panel panel-default clearfix dashboard-stats rounded">
                    <span id="dashboard-stats-sparkline3" class="sparkline transit"></span>
                    <i class="fa fa-user bg-success transit stats-icon"></i>
                    <h3 class="transit">${dashboardInfo.totalUsers}
                    </h3>
                    <p class="text-muted transit">Total Users</p>
                </div>
            </div>

        </div>


        <hr class="clean">
        <div class="row">
            <!--===================== Personnel Row ==========================-->
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-user fa-5x text-primary"></i>
                        <h4>My Profile</h4>
                        <div class="no-padd">
                            <a href="/user/${user.username}" class="btn btn-primary btn-flat col-md-12 btn-sm"><span class="pull-left">View Profile</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-list-alt fa-5x text-success"></i>
                        <h4>Request</h4>
                        <div class="no-padd">
                            <a href="/request/create" class="btn btn-success btn-flat col-md-12 btn-sm"><span class="pull-left">Create</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-list fa-5x text-danger"></i>
                        <h4>My Requests</h4>
                        <div class="no-padd">
                            <a href="/request/summary" class="btn btn-danger btn-flat col-md-12 btn-sm"><span class="pull-left">Show List</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>

            <!--===================== Chief Row ==========================-->
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-shopping-cart fa-5x text-info"></i>
                        <h4>Manage Order</h4>
                        <div class="no-padd">
                            <a href="/order/approval-list" class="btn btn-info btn-flat col-md-12 btn-sm"><span class="pull-left">Approve</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-archive fa-5x text-purple"></i>
                        <h4>Manage Request</h4>
                        <div class="no-padd">
                            <a href="/request/approval-list" class="btn btn-purple btn-flat col-md-12 btn-sm"><span class="pull-left">Approve</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-info fa-5x text-warning"></i>
                        <h4>Equipment Details</h4>
                        <div class="no-padd">
                            <a href="/item-details/insert-data" class="btn btn-warning btn-flat col-md-12 btn-sm"><span class="pull-left">Insert Info</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>

            <!--===================== Supply Officer Row ==========================-->
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-truck fa-5x text-purple"></i>
                        <h4>Manage Order</h4>
                        <div class="no-padd">
                            <a href="/order/arrival-list" class="btn btn-purple btn-flat col-md-12 btn-sm"><span class="pull-left">Arrived Checklist</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-cubes fa-5x text-primary"></i>
                        <h4>Manage Request</h4>
                        <div class="no-padd">
                            <a href="/request/release-list" class="btn btn-primary btn-flat col-md-12 btn-sm"><span class="pull-left">Release</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
            <div class="col-md-4 col-sm-6">
                <div class="panel panel-default clearfix">
                    <div class="panel-body text-center no-padd padd-t-md">
                        <i class="fa fa-file-text fa-5x text-success"></i>
                        <h4>Order</h4>
                        <div class="no-padd">
                            <a href="/order/create" class="btn btn-success btn-flat col-md-12 btn-sm"><span class="pull-left">Create</span><i class="fa fa-arrow-circle-o-right pull-right padd-xs"></i></a>
                        </div>

                    </div>

                </div>
            </div>
        </div>

    </div>
    <!-- Warper Ends Here (working area) -->

    <c:import url="../includes/footer.jsp"/>

    <!-- Sparkline JS -->
    <script type="text/javascript" src="${resourceURL}/js/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!-- For Demo Sparkline -->
    <script type="text/javascript" src="${resourceURL}/js/plugins/sparkline/jquery.sparkline.demo.js"></script>

    <!-- Chart JS -->
    <script type="text/javascript" src="${resourceURL}/js/plugins/DevExpressChartJS/dx.chartjs.js"></script>
    <script type="text/javascript" src="${resourceURL}/js/plugins/DevExpressChartJS/world.js"></script>
    <!-- For Demo Charts -->

    <!-- Chart JS -->
    <script type="text/javascript" src="${resourceURL}/js/plugins/DevExpressChartJS/dx.chartjs.js"></script>
    <!-- For Demo Charts -->
    <script type="text/javascript" src="${resourceURL}/js/main/charts-values.js"></script>
    <%--<script type="text/javascript" src="${resourceURL}/js/plugins/DevExpressChartJS/demo-charts.js"></script>--%>

    <!-- Calendar JS -->
    <script type="text/javascript" src="${resourceURL}/js/plugins/calendar/calendar.js"></script>
    <!-- Calendar Conf -->
    <script type="text/javascript" src="${resourceURL}/js/plugins/calendar/calendar-conf.js"></script>

</body>
</html>
