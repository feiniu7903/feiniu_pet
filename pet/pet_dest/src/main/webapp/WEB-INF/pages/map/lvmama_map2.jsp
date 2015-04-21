<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link href="<%=basePath %>css/google.css" type="text/css" rel="stylesheet"  />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true&libraries=places"></script>
<script language="javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
  <style type="text/css">
    body {
      font-family: sans-serif;
      font-size: 13px;
    }
    input:focus {
      outline: none;
    }
    #mess_box p strong{color:#c06;}
    .map_new{position:relative;height:<s:property value="height"/>;width:<s:property value="width"/>;}
    .map_canvas{position:relative;z-index:10;}
    .map_type{position:absolute;bottom:20px;right:10px;z-index:20;border:2px solid #dddddd;}
  </style>
<script language="javascript" src="<%=basePath %>js/common.js"></script> 
<script type="text/javascript">      
    /**
     * 初始化数据
     */ 
    function initData() {   
    	var placeCoord;
        // 周围景区坐标数据       
         <s:iterator value="viewCoordinateList">
            <s:if test=' latitude!=null && longitude!=null'> 
                  placeCoord = ['<s:property value="coordinateName" />',<s:property value="latitude" />,<s:property value="longitude" />,'<s:property value="stage" />',<s:property value="placeId" />,'<s:property value="infoFlag" />', '<s:property value="pinYinUrl" />'];
                  beachArray[beachArray.length] = placeCoord; 
            </s:if>
        </s:iterator>
        // 中心坐标数据
        <s:if test='viewPlaceCoordinate!=null && viewPlaceCoordinate.latitude!=null && viewPlaceCoordinate.longitude!=null'>
            centerParamT = <s:property value="viewPlaceCoordinate.latitude" />;
            centerParamG = <s:property value="viewPlaceCoordinate.longitude" />;
            
            placeCoord = ['<s:property value="viewPlaceCoordinate.coordinateName" />',<s:property value="viewPlaceCoordinate.latitude" />,<s:property value="viewPlaceCoordinate.longitude" />,'<s:property value="viewPlaceCoordinate.stage" />',<s:property value="viewPlaceCoordinate.placeId" />,'<s:property value="viewPlaceCoordinate.infoFlag" />', '<s:property value="viewPlaceCoordinate.pinYinUrl" />'];
            beachArray[beachArray.length] = placeCoord;
        </s:if>
        <s:else>
            centerParamT = 34.264877;
            centerParamG = 108.94386;
        </s:else> 
    }
</script>
</head>
<body onload="initialize(<s:property value="mapZoom" />)">
<div class="map_new">
  <div id="map_canvas" style="width: <s:property value="width"/>; height: <s:property value="height"/>; float:left; border: 1px solid black;"></div>
  <div class="map_type"><img src="<%=basePath %>img/map_type2.png" /></div>
</div>
</body>
</html>
