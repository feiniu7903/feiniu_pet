<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<script type="javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>

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
<script type="javascript" src="<%=basePath %>js/common.js"></script> 
<script type="text/javascript"> 
	
</script>
</head>
<body>
<div class="map_new">
  <div id="map_canvas" style="width:<s:property value="width"/>; height:<s:property value="height"/>; float:left; border: 1px solid black;"><input type="hidden" value="123456" id="centerId"/></div>
</div>
</body>
<script type="text/javascript"> 
var localLongitude="<s:property value="placeCoordinateVo.longitude"/>";
var localLatitude="<s:property value="placeCoordinateVo.latitude"/>";
var placeName="<s:property value="placeCoordinateVo.coordinateName"/>";
var placeCAddress="<s:property value="placeCoordinateVo.coordinateAddress"/>";
var navigationControlFlag = "<s:property value="navigationControlFlag"/>";
initData(localLongitude,localLatitude,placeName,placeCAddress,navigationControlFlag);
/**
 * 初始化数据
 */ 
function initData(localLongitude,localLatitude,placeName,placeCAddress,navigationControlFlag) {   
	if(localLongitude==""){
		localLongitude=116.404844;
    }
	if(localLatitude==""){
		localLatitude=39.923125;
    }
	var map = new BMap.Map("map_canvas");
	var point = new BMap.Point(localLongitude,localLatitude);
	map.centerAndZoom(point, 15);
	map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
    map.enableKeyboard();                         // 启用键盘操作。
    map.addControl(new BMap.OverviewMapControl());//添加缩略地图控件
    if(navigationControlFlag == 'true'){
    	map.addControl(new BMap.NavigationControl()); //添加导航控件
    }
	map.addOverlay(new BMap.Marker(point));
}
</script>
</html>
