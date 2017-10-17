<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="top-head container-fluid">

    <form role="search" class="navbar-left app-search pull-left hidden-xs">
        <input type="text" placeholder="Search" class="form-control form-control-circle">
    </form>

    <nav class=" navbar-default hidden-xs" role="navigation">
        <ul class="nav navbar-nav">
            <li><a href="/dashboard">Home</a></li>
            <li><a href="/profile">Profile</a></li>
            <li class="dropdown">
                <a data-toggle="dropdown" class="dropdown-toggle" href="#">Search & Filter <span
                        class="caret"></span></a>
                <ul role="menu" class="dropdown-menu">
                    <li><a href="/goods/list">Goods</a></li>
                    <li><a href="/semi-expendable/list">Semi-Expendables</a></li>
                    <li class="divider">
                    <li><a href="#">Computer Peripehrals</a></li>
                    <li><a href="#">Data Storage</a></li>
                    </li>
                    <li><a href="/equipment/list">Equipment</a></li>
                    <li class="divider">
                    <li><a href="#">Electrionics</a></li>
                    <li><a href="#">Non-Electronics</a></li>
                    </li>
                </ul>
            </li>
        </ul>
    </nav>

    <ul class="nav-toolbar">
        <li class="sub-toolbar">
            <div class="cart-menu">
                <a href="#" data-toggle="modal" data-target="#item-cart-modal"><i class="fa fa-shopping-cart fa-2x" aria-hidden="true"></i>
                    <span class="badge bg-warning">3</span></a>
            </div>
        </li>
        <li class="dropdown"><a href="#" data-toggle="dropdown"><i class="fa fa-comments-o"></i> <span
                class="badge bg-warning">7</span></a>
            <div class="dropdown-menu md arrow pull-right panel panel-default arrow-top-right messages-dropdown">
                <div class="panel-heading">
                    Private Messages
                </div>

                <div class="list-group">

                    <a href="#" class="list-group-item">
                        <div class="media">
                            <div class="user-status busy pull-left">
                                <img class="media-object img-circle pull-left"
                                     src="${resourceURL}/images/avtar/user2.png"
                                     alt="user#1" width="40">
                            </div>
                            <div class="media-body">
                                <h5 class="media-heading">Lorem ipsum dolor sit consect....</h5>
                                <small class="text-muted">23 Sec ago</small>
                            </div>
                        </div>
                    </a>
                    <a href="#" class="list-group-item">
                        <div class="media">
                            <div class="user-status offline pull-left">
                                <img class="media-object img-circle pull-left"
                                     src="${resourceURL}/images/avtar/user3.png"
                                     alt="user#1" width="40">
                            </div>
                            <div class="media-body">
                                <h5 class="media-heading">Nunc elementum, enim vitae</h5>
                                <small class="text-muted">23 Sec ago</small>
                            </div>
                        </div>
                    </a>
                    <a href="#" class="list-group-item">
                        <div class="media">
                            <div class="user-status invisibled pull-left">
                                <img class="media-object img-circle pull-left"
                                     src="${resourceURL}/images/avtar/user4.png"
                                     alt="user#1" width="40">
                            </div>
                            <div class="media-body">
                                <h5 class="media-heading">Praesent lacinia, arcu eget</h5>
                                <small class="text-muted">23 Sec ago</small>
                            </div>
                        </div>
                    </a>
                    <a href="#" class="list-group-item">
                        <div class="media">
                            <div class="user-status online pull-left">
                                <img class="media-object img-circle pull-left"
                                     src="${resourceURL}/images/avtar/user5.png"
                                     alt="user#1" width="40">
                            </div>
                            <div class="media-body">
                                <h5 class="media-heading">In mollis blandit tempor.</h5>
                                <small class="text-muted">23 Sec ago</small>
                            </div>
                        </div>
                    </a>

                    <a href="#" class="btn btn-info btn-flat btn-block">View All Messages</a>

                </div>

            </div>
        </li>
        <li class="dropdown"><a href="#" data-toggle="dropdown"><i class="fa fa-bell-o"></i><span
                class="badge">3</span></a>
            <div class="dropdown-menu arrow pull-right md panel panel-default arrow-top-right notifications">
                <div class="panel-heading">
                    System Notifications
                </div>

                <div class="list-group">

                    <a href="#" class="list-group-item">
                        <p>Installing App v1.2.1
                            <small class="pull-right text-muted">45% Done</small>
                        </p>
                        <div class="progress progress-xs no-margn progress-striped active">
                            <div class="progress-bar" role="progressbar" aria-valuenow="45" aria-valuemin="0"
                                 aria-valuemax="100" style="width: 45%">
                                <span class="sr-only">45% Complete</span>
                            </div>
                        </div>
                    </a>

                    <a href="#" class="list-group-item">
                        Fusce dapibus molestie tincidunt. Quisque facilisis libero eget justo iaculis
                    </a>

                    <a href="#" class="list-group-item">
                        <p>Server Status</p>
                        <div class="progress progress-xs no-margn">
                            <div class="progress-bar progress-bar-success" style="width: 35%">
                                <span class="sr-only">35% Complete (success)</span>
                            </div>
                            <div class="progress-bar progress-bar-warning" style="width: 20%">
                                <span class="sr-only">20% Complete (warning)</span>
                            </div>
                            <div class="progress-bar progress-bar-danger" style="width: 10%">
                                <span class="sr-only">10% Complete (danger)</span>
                            </div>
                        </div>
                    </a>


                    <a href="#" class="list-group-item">
                        <div class="media">
                            <span class="label label-danger media-object img-circle pull-left">Danger</span>
                            <div class="media-body">
                                <h5 class="media-heading">Lorem ipsum dolor sit consect..</h5>
                            </div>
                        </div>
                    </a>


                    <a href="#" class="list-group-item">
                        <p>Server Status</p>
                        <div class="progress progress-xs no-margn">
                            <div style="width: 60%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="60"
                                 role="progressbar" class="progress-bar progress-bar-info">
                                <span class="sr-only">60% Complete (warning)</span>
                            </div>
                        </div>
                    </a>
                </div>

            </div>
        </li>
        <li class="dropdown"><a href="#" data-toggle="dropdown"><i class="fa fa-ellipsis-v"></i></a>
            <div class="dropdown-menu lg pull-right arrow panel panel-default arrow-top-right">
                <div class="panel-heading">
                    More Apps
                </div>
                <div class="panel-body text-center">
                    <div class="row">
                        <div class="col-xs-6 col-sm-4"><a href="#" class="text-green"><span class="h2"><i
                                class="fa fa-envelope-o"></i></span>
                            <p class="text-gray no-margn">Messages</p></a></div>
                        <div class="col-xs-6 col-sm-4"><a href="#" class="text-purple"><span class="h2"><i
                                class="fa fa-calendar-o"></i></span>
                            <p class="text-gray no-margn">Calendar</p></a></div>

                        <div class="col-xs-12 visible-xs-block">
                            <hr>
                        </div>

                        <div class="col-xs-6 col-sm-4"><a href="#" class="text-red"><span class="h2"><i
                                class="fa fa-comments-o"></i></span>
                            <p class="text-gray no-margn">Chatting</p></a></div>

                        <div class="col-lg-12 col-md-12 col-sm-12  hidden-xs">
                            <hr>
                        </div>

                        <div class="col-xs-12 visible-xs-block">
                            <hr>
                        </div>

                        <div class="col-xs-6 col-sm-4"><a href="#" class="text-primary"><span class="h2"><i
                                class="glyphicon glyphicon-comment"></i></span>
                            <p class="text-gray">Notifications</p></a></div>

                        <div class="col-xs-6 col-sm-4"><a href="#" class="text-primary"><span class="h2"><i
                                class="fa fa-sign-out"></i></span>
                            <p class="text-gray">Logout</p></a>
                        </div>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</header>
