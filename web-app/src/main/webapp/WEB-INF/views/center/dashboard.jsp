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


        <div class="row">
            <div class="col-lg-8">

                <div class="panel panel-default">
                    <div class="panel-heading clean">
                        Statistics
                        <div class="pull-right">
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-sm btn-circle" data-toggle="dropdown">
                                    Monthly <span class="caret"></span></button>
                                <ul class="dropdown-menu pull-right" role="menu">
                                    <li><a href="#">Daily</a></li>
                                    <li><a href="#">Weekly</a></li>
                                    <li class="active"><a href="#">Monthly</a></li>
                                    <li><a href="#">Yearly</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#">Specific Time</a></li>
                                </ul>
                            </div>
                            <button type="button" class="btn btn-default btn-sm btn-circle">GO</button>
                        </div>
                    </div>
                    <div class="panel-body">
                        <div id="side-by-side-bar" style="height:280px;"></div>
                    </div>
                </div>

            </div>
            <div class="col-lg-4">
                <div class="panel panel-default">
                    <div class="panel-body text-center">
                        <h5 class="no-margn"><strong>Disposed Items</strong></h5>
                        <p class="text-muted">
                            <small>total 153,248</small>
                        </p>
                        <div id="dashboard-stats-sparkline6"></div>
                    </div>
                </div>
            </div>
            <div class="col-lg-4">
                <div class="panel panel-default">
                    <div class="panel-body text-center">
                        <h5 class="no-margn"><strong>Damaged Equipment</strong></h5>
                        <p class="text-muted">
                            <small>total 153,248</small>
                        </p>
                        <div id="dashboard-stats-sparkline5"></div>
                    </div>
                </div>

            </div>
        </div>

        <div class="row">
            <div class="col-lg-7">

                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">Calendar</div>
                            <div class="panel-body">

                                <div class="panel-heading clean clearfix text-center">
                                    <div class="btn-group pull-left">
                                        <button class="btn btn-default btn-sm" data-calendar-nav="prev">&lt;< Prev</button>
                                        <button class="btn btn-sm btn-default" data-calendar-nav="today">Today</button>
                                        <button class="btn btn-sm btn-default" data-calendar-nav="next">Next >&gt;</button>
                                    </div>
                                    <b class="calender-title"></b>
                                    <div class="btn-group pull-right">
                                        <button class="btn btn-sm btn-default" data-calendar-view="year">Year</button>
                                        <button class="btn btn-sm btn-default active" data-calendar-view="month">Month</button>
                                        <button class="btn btn-sm btn-default" data-calendar-view="week">Week</button>
                                        <button class="btn btn-sm btn-default" data-calendar-view="day">Day</button>
                                    </div>
                                </div>


                                <div class="panel-body">
                                    <div id="calendar"></div>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-lg-5">

                <ul class="nav nav-tabs" role="tablist">
                    <li class="active"><a href="#acceptRequest" role="tab" data-toggle="tab"><span class="hidden-xs">Accept</span>
                        Requests</a></li>
                    <li><a href="#acceptOrder" role="tab" data-toggle="tab"><span class="hidden-xs">Accept</span>
                        Orders</a></li>
                </ul>

                <div class="tab-content">

                    <div class="panel panel-default tab-pane active tabs-up" id="acceptRequest">
                        <div class="panel-body">
                            <ul class="activities-list list-unstyled nicescroll">
                                <li class="default-activity">
                                    <span class="time">moments ago</span>
                                    <p><a href="#">Name</a> Request for an .</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default btn-xs">Actions</button>
                                        <button type="button" class="btn btn-default btn-xs dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="info-activity">
                                    <span class="time">min ago</span>
                                    <p class="text-info">Call to customer Jacob and discuss the detail.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="warning-activity">
                                    <span class="time">10mins ago</span>
                                    <p class="text-warning">Jessi assign you a <span class="label label-info">task Mockup</span>
                                        Design.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="default-activity">
                                    <span class="time">8hrs ago</span>
                                    <p>Integer ut consequat nulla. Etiam aliquam</p>
                                    <div class="btn-group  btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="danger-activity">
                                    <span class="time">9hrs ago</span>
                                    <p>Lorem ipsum dolor sit amet, <span class="label label-success">consectetur</span>
                                        adi...</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="success-activity">
                                    <span class="time">Thu, 28 Jan</span>
                                    <p>Nam pretium id sem ut convallis. </p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="primary-activity">
                                    <span class="time">Wed, 17 Jan</span>
                                    <p class="text-purple">Maecenas finibus est eget neque pharetra.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="default-activity">
                                    <span class="time">Tue, 23 Jan</span>
                                    <p>Integer ut consequat nulla. Etiam aliquam...</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>

                    </div>
                    <!-- Request Tab Ends -->

                    <div class="panel panel-default tab-pane tabs-up" id="acceptOrder">
                        <div class="panel-body">
                            <ul class="activities-list list-unstyled nicescroll">
                                <li class="default-activity">
                                    <span class="time">moments ago</span>
                                    <p><a href="#">Akshay</a> commented your post.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default btn-xs">Actions</button>
                                        <button type="button" class="btn btn-default btn-xs dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="info-activity">
                                    <span class="time">min ago</span>
                                    <p class="text-info">Call to customer Jacob and discuss the detail.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="warning-activity">
                                    <span class="time">10mins ago</span>
                                    <p class="text-warning">Jessi assign you a <span class="label label-info">task Mockup</span>
                                        Design.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="default-activity">
                                    <span class="time">8hrs ago</span>
                                    <p>Integer ut consequat nulla. Etiam aliquam</p>
                                    <div class="btn-group  btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="danger-activity">
                                    <span class="time">9hrs ago</span>
                                    <p>Lorem ipsum dolor sit amet, <span class="label label-success">consectetur</span>
                                        adi...</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="success-activity">
                                    <span class="time">Thu, 28 Jan</span>
                                    <p>Nam pretium id sem ut convallis. </p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="primary-activity">
                                    <span class="time">Wed, 17 Jan</span>
                                    <p class="text-purple">Maecenas finibus est eget neque pharetra.</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="default-activity">
                                    <span class="time">Tue, 23 Jan</span>
                                    <p>Integer ut consequat nulla. Etiam aliquam...</p>
                                    <div class="btn-group btn-group-xs activity-actions">
                                        <button type="button" class="btn btn-default">Actions</button>
                                        <button type="button" class="btn btn-default dropdown-toggle"
                                                data-toggle="dropdown">
                                            <span class="caret"></span>
                                            <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu pull-right" role="menu">
                                            <li><a href="#">Action</a></li>
                                            <li><a href="#">Another action</a></li>
                                            <li><a href="#">Something else here</a></li>
                                            <li class="divider"></li>
                                            <li><a href="#">Separated link</a></li>
                                        </ul>
                                    </div>
                                </li>
                            </ul>
                        </div>

                    </div>
                    <!-- Order Tab Ends -->

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
