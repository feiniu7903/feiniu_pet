<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>super后台——产品标地信息</title>
    <%@ include file="/WEB-INF/pages/back/base/jquery.jsp" %>
    <%@ include file="/WEB-INF/pages/back/base/jsonSuggest.jsp" %>
    <script type="text/javascript" src="<%=basePath%>js/prod/place.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
</head>

<body>
<div class="main main07">
    <div class="row1">
        <h3 class="newTit">销售产品信息 </h3>
    </div>
    <!--row1 end-->
    <div class="row2">

    </div>
    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" id="place_tb" class="newTable">
            <tr class="newTableTit">
                <td>ID号</td>
                <td>名称</td>
                <td>出发地</td>
                <td>目的地</td>
            </tr>
            <s:iterator value="placeList">
                <tr id="tr_<s:property value="productPlaceId"/>" productPlaceId="<s:property value="productPlaceId"/>">
                    <td><s:property value="placeId"/></td>
                    <td><s:property value="placeName"/></td>
                    <td class='from'><s:if test="from=='true'">true</s:if></td>
                    <td class='to'><s:if test="to=='true'">true</s:if></td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <!--row2 end-->
</div>
<!--main01 main05 end-->
</body>
</html>


