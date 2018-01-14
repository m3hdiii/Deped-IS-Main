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

<html>
<c:import url="../../includes/head.jsp">
    <c:param name="title" value="Item Registration"/>
    <c:param name="description" value="Item Registration Page"/>
</c:import>

<body>
<c:import url="../../includes/left-nav.jsp"/>
<section class="content">
    <c:import url="../../includes/top-nav.jsp"/>

    <div class="warper container-fluid">

        <div class="page-header">
            <h3>Manage Request&nbsp;<small>&nbsp;for Goods, Semi-Expendable and Equipment</small>
            </h3>
        </div>

        <div class="row">
            <h4>Request Information</h4>
            <p>Request Number: ${relatedRequest.requestId}</p>
            <p>Requested
                By: ${relatedRequest.user.firstName}&nbsp;${relatedRequest.user.lastName}</p>
            <p>Reason: ${relatedRequest.userMessage}</p>
        </div>


        <c:set var="basketMap" value="${requestDetailsForm.map}"/>

        <form:form commandName="requestDetailsForm" method="post">

            <div class="panel panel-default">
                <div class="panel-body">

                    <table cellpadding="0" cellspacing="0" border="0" class="table table-hover"
                           id="basic-datatable">
                        <thead>
                        <tr>
                            <th>#
                                <%--<label class="cr-styled">
                                    <input type="checkbox" ng-model="todo.done">
                                    <i class="fa"></i>
                                </label>--%>
                            </th>
                            <th>Image</th>
                            <th>Name</th>
                            <th data-toggle="tooltip" title="Quantity of Available Item">Available QTY</th>
                            <th data-toggle="tooltip" title="Quantity of Request">Requested QTY</th>
                            <th>Item Type</th>
                            <th>Note</th>
                            <th>State</th>
                        </tr>
                        </thead>

                        <tbody>

                        <c:forEach items="${basketMap}" var="entry" varStatus="loop">
                            <c:set var="strKey" value="${entry.key}"/>
                            <c:set var="requestDet" value="${entry.value}"/>
                            <tr>
                                <td>${loop.index + 1}
                                    <%--<label class="cr-styled">
                                        <input type="checkbox" ng-model="todo.done">
                                        <i class="fa"></i>
                                    </label>--%>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty requestDet.item.picName}">
                                            <img src="${baseUrl}${requestDet.item.picName}" alt="item image"
                                                 width="76px" height="50px"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${resourceURL}/images/shared-images/no-item.png"
                                                 alt="item image" width="76px" height="50px"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <th>
                                    <div>
                                            ${requestDet.item.name}
                                    </div>
                                    <div>
                                        <small>SONY</small>
                                    </div>
                                </th>

                                <td>${requestDet.item.quantity}</td>
                                <td>${requestDet.requestQuantity}</td>

                                <c:choose>
                                    <c:when test="${requestDet.item.itemType eq 'EQUIPMENT'}">
                                        <td>
                                            <a href="/item-details/create/${requestDet.item.itemId}/${requestDet.requestQuantity}">${requestDet.item.itemType}</a>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        ${requestDet.item.itemType}
                                    </c:otherwise>
                                </c:choose>

                                <td>
                                    <input type="textarea" id="${strKey}" disabled/>
                                </td>
                                <td>
                                    <form:select id="requestDetailsStatus${loop.index}" multiple="single"
                                                 path="map['${strKey}'].requestDetailsStatus"
                                                 onchange="processReason('requestDetailsStatus${loop.index}', '${strKey}')">
                                        <form:options items="${nextRequestDetailsStatuses}"/>
                                    </form:select>
                                </td>

                                <form:hidden path="map['${strKey}'].requestDetailsID.requestId"
                                             value="${requestDet.request.requestId}"/>
                                <form:hidden path="map['${strKey}'].requestDetailsID.itemId"
                                             value="${requestDet.item.itemId}"/>

                            </tr>
                        </c:forEach>


                            <%--<tr>--%>
                            <%--<td>Tiger Nixon</td>--%>
                            <%--<td>System Architect</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>61</td>--%>
                            <%--<td>2011/04/25</td>--%>
                            <%--<td>$320,800</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Garrett Winters</td>--%>
                            <%--<td>Accountant</td>--%>
                            <%--<td>Tokyo</td>--%>
                            <%--<td>63</td>--%>
                            <%--<td>2011/07/25</td>--%>
                            <%--<td>$170,750</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Ashton Cox</td>--%>
                            <%--<td>Junior Technical Author</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>66</td>--%>
                            <%--<td>2009/01/12</td>--%>
                            <%--<td>$86,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Cedric Kelly</td>--%>
                            <%--<td>Senior Javascript Developer</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>22</td>--%>
                            <%--<td>2012/03/29</td>--%>
                            <%--<td>$433,060</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Airi Satou</td>--%>
                            <%--<td>Accountant</td>--%>
                            <%--<td>Tokyo</td>--%>
                            <%--<td>33</td>--%>
                            <%--<td>2008/11/28</td>--%>
                            <%--<td>$162,700</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Brielle Williamson</td>--%>
                            <%--<td>Integration Specialist</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>61</td>--%>
                            <%--<td>2012/12/02</td>--%>
                            <%--<td>$372,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Herrod Chandler</td>--%>
                            <%--<td>Sales Assistant</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>59</td>--%>
                            <%--<td>2012/08/06</td>--%>
                            <%--<td>$137,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Rhona Davidson</td>--%>
                            <%--<td>Integration Specialist</td>--%>
                            <%--<td>Tokyo</td>--%>
                            <%--<td>55</td>--%>
                            <%--<td>2010/10/14</td>--%>
                            <%--<td>$327,900</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Colleen Hurst</td>--%>
                            <%--<td>Javascript Developer</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>39</td>--%>
                            <%--<td>2009/09/15</td>--%>
                            <%--<td>$205,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Sonya Frost</td>--%>
                            <%--<td>Software Engineer</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>23</td>--%>
                            <%--<td>2008/12/13</td>--%>
                            <%--<td>$103,600</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Jena Gaines</td>--%>
                            <%--<td>Office Manager</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>30</td>--%>
                            <%--<td>2008/12/19</td>--%>
                            <%--<td>$90,560</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Quinn Flynn</td>--%>
                            <%--<td>Support Lead</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>22</td>--%>
                            <%--<td>2013/03/03</td>--%>
                            <%--<td>$342,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Charde Marshall</td>--%>
                            <%--<td>Regional Director</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>36</td>--%>
                            <%--<td>2008/10/16</td>--%>
                            <%--<td>$470,600</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Haley Kennedy</td>--%>
                            <%--<td>Senior Marketing Designer</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>43</td>--%>
                            <%--<td>2012/12/18</td>--%>
                            <%--<td>$313,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Tatyana Fitzpatrick</td>--%>
                            <%--<td>Regional Director</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>19</td>--%>
                            <%--<td>2010/03/17</td>--%>
                            <%--<td>$385,750</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Michael Silva</td>--%>
                            <%--<td>Marketing Designer</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>66</td>--%>
                            <%--<td>2012/11/27</td>--%>
                            <%--<td>$198,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Paul Byrd</td>--%>
                            <%--<td>Chief Financial Officer (CFO)</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>64</td>--%>
                            <%--<td>2010/06/09</td>--%>
                            <%--<td>$725,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Gloria Little</td>--%>
                            <%--<td>Systems Administrator</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>59</td>--%>
                            <%--<td>2009/04/10</td>--%>
                            <%--<td>$237,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Bradley Greer</td>--%>
                            <%--<td>Software Engineer</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>41</td>--%>
                            <%--<td>2012/10/13</td>--%>
                            <%--<td>$132,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Dai Rios</td>--%>
                            <%--<td>Personnel Lead</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>35</td>--%>
                            <%--<td>2012/09/26</td>--%>
                            <%--<td>$217,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Jenette Caldwell</td>--%>
                            <%--<td>Development Lead</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>30</td>--%>
                            <%--<td>2011/09/03</td>--%>
                            <%--<td>$345,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Yuri Berry</td>--%>
                            <%--<td>Chief Marketing Officer (CMO)</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>40</td>--%>
                            <%--<td>2009/06/25</td>--%>
                            <%--<td>$675,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Caesar Vance</td>--%>
                            <%--<td>Pre-Sales Support</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>21</td>--%>
                            <%--<td>2011/12/12</td>--%>
                            <%--<td>$106,450</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Doris Wilder</td>--%>
                            <%--<td>Sales Assistant</td>--%>
                            <%--<td>Sidney</td>--%>
                            <%--<td>23</td>--%>
                            <%--<td>2010/09/20</td>--%>
                            <%--<td>$85,600</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Angelica Ramos</td>--%>
                            <%--<td>Chief Executive Officer (CEO)</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>47</td>--%>
                            <%--<td>2009/10/09</td>--%>
                            <%--<td>$1,200,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Gavin Joyce</td>--%>
                            <%--<td>Developer</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>42</td>--%>
                            <%--<td>2010/12/22</td>--%>
                            <%--<td>$92,575</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Jennifer Chang</td>--%>
                            <%--<td>Regional Director</td>--%>
                            <%--<td>Singapore</td>--%>
                            <%--<td>28</td>--%>
                            <%--<td>2010/11/14</td>--%>
                            <%--<td>$357,650</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Brenden Wagner</td>--%>
                            <%--<td>Software Engineer</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>28</td>--%>
                            <%--<td>2011/06/07</td>--%>
                            <%--<td>$206,850</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Fiona Green</td>--%>
                            <%--<td>Chief Operating Officer (COO)</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>48</td>--%>
                            <%--<td>2010/03/11</td>--%>
                            <%--<td>$850,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Shou Itou</td>--%>
                            <%--<td>Regional Marketing</td>--%>
                            <%--<td>Tokyo</td>--%>
                            <%--<td>20</td>--%>
                            <%--<td>2011/08/14</td>--%>
                            <%--<td>$163,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Michelle House</td>--%>
                            <%--<td>Integration Specialist</td>--%>
                            <%--<td>Sidney</td>--%>
                            <%--<td>37</td>--%>
                            <%--<td>2011/06/02</td>--%>
                            <%--<td>$95,400</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Suki Burks</td>--%>
                            <%--<td>Developer</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>53</td>--%>
                            <%--<td>2009/10/22</td>--%>
                            <%--<td>$114,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Prescott Bartlett</td>--%>
                            <%--<td>Technical Author</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>27</td>--%>
                            <%--<td>2011/05/07</td>--%>
                            <%--<td>$145,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Gavin Cortez</td>--%>
                            <%--<td>Team Leader</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>22</td>--%>
                            <%--<td>2008/10/26</td>--%>
                            <%--<td>$235,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Martena Mccray</td>--%>
                            <%--<td>Post-Sales support</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>46</td>--%>
                            <%--<td>2011/03/09</td>--%>
                            <%--<td>$324,050</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Unity Butler</td>--%>
                            <%--<td>Marketing Designer</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>47</td>--%>
                            <%--<td>2009/12/09</td>--%>
                            <%--<td>$85,675</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Howard Hatfield</td>--%>
                            <%--<td>Office Manager</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>51</td>--%>
                            <%--<td>2008/12/16</td>--%>
                            <%--<td>$164,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Hope Fuentes</td>--%>
                            <%--<td>Secretary</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>41</td>--%>
                            <%--<td>2010/02/12</td>--%>
                            <%--<td>$109,850</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Vivian Harrell</td>--%>
                            <%--<td>Financial Controller</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>62</td>--%>
                            <%--<td>2009/02/14</td>--%>
                            <%--<td>$452,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Timothy Mooney</td>--%>
                            <%--<td>Office Manager</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>37</td>--%>
                            <%--<td>2008/12/11</td>--%>
                            <%--<td>$136,200</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Jackson Bradshaw</td>--%>
                            <%--<td>Director</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>65</td>--%>
                            <%--<td>2008/09/26</td>--%>
                            <%--<td>$645,750</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Olivia Liang</td>--%>
                            <%--<td>Support Engineer</td>--%>
                            <%--<td>Singapore</td>--%>
                            <%--<td>64</td>--%>
                            <%--<td>2011/02/03</td>--%>
                            <%--<td>$234,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Bruno Nash</td>--%>
                            <%--<td>Software Engineer</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>38</td>--%>
                            <%--<td>2011/05/03</td>--%>
                            <%--<td>$163,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Sakura Yamamoto</td>--%>
                            <%--<td>Support Engineer</td>--%>
                            <%--<td>Tokyo</td>--%>
                            <%--<td>37</td>--%>
                            <%--<td>2009/08/19</td>--%>
                            <%--<td>$139,575</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Thor Walton</td>--%>
                            <%--<td>Developer</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>61</td>--%>
                            <%--<td>2013/08/11</td>--%>
                            <%--<td>$98,540</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Finn Camacho</td>--%>
                            <%--<td>Support Engineer</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>47</td>--%>
                            <%--<td>2009/07/07</td>--%>
                            <%--<td>$87,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Serge Baldwin</td>--%>
                            <%--<td>Data Coordinator</td>--%>
                            <%--<td>Singapore</td>--%>
                            <%--<td>64</td>--%>
                            <%--<td>2012/04/09</td>--%>
                            <%--<td>$138,575</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Zenaida Frank</td>--%>
                            <%--<td>Software Engineer</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>63</td>--%>
                            <%--<td>2010/01/04</td>--%>
                            <%--<td>$125,250</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Zorita Serrano</td>--%>
                            <%--<td>Software Engineer</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>56</td>--%>
                            <%--<td>2012/06/01</td>--%>
                            <%--<td>$115,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Jennifer Acosta</td>--%>
                            <%--<td>Junior Javascript Developer</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>43</td>--%>
                            <%--<td>2013/02/01</td>--%>
                            <%--<td>$75,650</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Cara Stevens</td>--%>
                            <%--<td>Sales Assistant</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>46</td>--%>
                            <%--<td>2011/12/06</td>--%>
                            <%--<td>$145,600</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Hermione Butler</td>--%>
                            <%--<td>Regional Director</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>47</td>--%>
                            <%--<td>2011/03/21</td>--%>
                            <%--<td>$356,250</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Lael Greer</td>--%>
                            <%--<td>Systems Administrator</td>--%>
                            <%--<td>London</td>--%>
                            <%--<td>21</td>--%>
                            <%--<td>2009/02/27</td>--%>
                            <%--<td>$103,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Jonas Alexander</td>--%>
                            <%--<td>Developer</td>--%>
                            <%--<td>San Francisco</td>--%>
                            <%--<td>30</td>--%>
                            <%--<td>2010/07/14</td>--%>
                            <%--<td>$86,500</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Shad Decker</td>--%>
                            <%--<td>Regional Director</td>--%>
                            <%--<td>Edinburgh</td>--%>
                            <%--<td>51</td>--%>
                            <%--<td>2008/11/13</td>--%>
                            <%--<td>$183,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Michael Bruce</td>--%>
                            <%--<td>Javascript Developer</td>--%>
                            <%--<td>Singapore</td>--%>
                            <%--<td>29</td>--%>
                            <%--<td>2011/06/27</td>--%>
                            <%--<td>$183,000</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                            <%--<td>Donna Snider</td>--%>
                            <%--<td>Customer Support</td>--%>
                            <%--<td>New York</td>--%>
                            <%--<td>27</td>--%>
                            <%--<td>2011/01/25</td>--%>
                            <%--<td>$112,000</td>--%>
                            <%--</tr>--%>
                        </tbody>
                    </table>
                    <hr class="clean">
                </div>

                <div class="modal-footer">
                    <a href="/request/approval-list" class="btn btn-default pull-left"><i
                            class="fa fa-chevron-left"></i><span> Back</span></a>
                    <button type="submit" class="btn btn-purple pull-right">Confirm</button>
                </div>
            </div>
        </form:form>
    </div>
    <!-- Warper Ends Here (working area) -->


    <%--<div class="warper container-fluid">--%>

    <%--<div class="page-header">--%>
    <%--<h1>Manage Request--%>
    <%--<small>DepEd-Baguio City Division Office</small>--%>
    <%--</h1>--%>

    <%--</div>--%>

    <%--<div class="row item-body">--%>
    <%--<c:set var="basketMap" value="${requestDetailsForm.map}"/>--%>

    <%--<form:form commandName="requestDetailsForm" method="post">--%>
    <%--<div class="panel panel-default">--%>
    <%--<h3 class="text-center">Requested Items</h3>--%>
    <%--<div class="panel-body">--%>

    <%--<table class="table table-hover">--%>
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<th>--%>
    <%--<label class="checkbox checkbox-inline">--%>
    <%--<input type="checkbox"/>--%>
    <%--</label>--%>
    <%--</th>--%>
    <%--<th>Image</th>--%>
    <%--<th>Name</th>--%>
    <%--<th data-toggle="tooltip" title="Quantity of Available Item">Available QTY</th>--%>
    <%--<th data-toggle="tooltip" title="Quantity of Request">Requested QTY</th>--%>
    <%--<th>Item Type</th>--%>
    <%--<th>Note</th>--%>
    <%--<th>State</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>

    <%--<c:forEach items="${basketMap}" var="entry" varStatus="loop">--%>
    <%--<c:set var="strKey" value="${entry.key}"/>--%>
    <%--<c:set var="requestDet" value="${entry.value}"/>--%>
    <%--<tr>--%>
    <%--<td>--%>
    <%--<label class="checkbox checkbox-inline">--%>
    <%--<input type="checkbox">--%>
    <%--</label>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<c:choose>--%>
    <%--<c:when test="${not empty requestDet.item.picName}">--%>
    <%--<img src="${baseUrl}${requestDet.item.picName}" alt="item image" width="76px" height="50px"/>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
    <%--<img src="${resourceURL}/images/shared-images/no-item.png"--%>
    <%--alt="item image" width="76px" height="50px"/>--%>
    <%--</c:otherwise>--%>
    <%--</c:choose>--%>
    <%--</td>--%>
    <%--<th><div>--%>
    <%--${requestDet.item.name}--%>
    <%--</div>--%>
    <%--<div>--%>
    <%--<small>SONY</small>--%>
    <%--</div>--%>
    <%--</th>--%>

    <%--<td>${requestDet.item.quantity}</td>--%>
    <%--<td>${requestDet.requestQuantity}</td>--%>
    <%--<td>${requestDet.item.itemType}</td>--%>
    <%--<td>--%>
    <%--<input type="textarea" id="${strKey}" disabled/>--%>
    <%--</td>--%>
    <%--<td>--%>
    <%--<form:select id="requestDetailsStatus${loop.index}" multiple="single"--%>
    <%--path="map['${strKey}'].requestDetailsStatus"--%>
    <%--onchange="processReason('requestDetailsStatus${loop.index}', '${strKey}')">--%>
    <%--<form:options items="${nextRequestDetailsStatuses}"/>--%>
    <%--</form:select>--%>
    <%--</td>--%>

    <%--<form:hidden path="map['${strKey}'].requestDetailsID.requestId"--%>
    <%--value="${requestDet.request.requestId}"/>--%>
    <%--<form:hidden path="map['${strKey}'].requestDetailsID.itemId" value="${requestDet.item.itemId}"/>--%>

    <%--</tr>--%>
    <%--</c:forEach>--%>
    <%--</tbody>--%>
    <%--</table>--%>
    <%--</div>--%>
    <%--<div class="modal-footer">--%>
    <%--<a href="#" class="btn btn-default pull-left"><i class="fa fa-chevron-left"></i><span> Back</span></a>--%>
    <%--<button type="submit" class="btn btn-purple pull-right">Confirm</button>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</form:form>--%>

    <%--</div>--%>

    <%--</div> <!-- Warper Ends Here (working area) -->--%>


        <c:import url="../../includes/footer.jsp"/>
    <script type="text/javascript" src="${resourceURL}/js/additional/request-operation.js"></script>

</body>
</html>
