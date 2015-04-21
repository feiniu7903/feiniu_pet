<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<link href="<%=basePath %>style/google.css" type="text/css" rel="stylesheet"  />
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
<script type="text/javascript">  
/* 采用版本： Google Maps JavaScript API V3 */

    // 景区坐标数据
    var beachArray = new Array();
    // 中心坐标
    var centerParamT, centerParamG;
    // 地图对象
    var map;
   
    /**
     * 初始化数据
     */ 
    function initData() {   
        var placeCoord;
        
        // 中心坐标数据
        <s:if test='viewPlaceCoordinate!=null && viewPlaceCoordinate.latitude!=null && viewPlaceCoordinate.longitude!=null'>
            centerParamT = <s:property value="viewPlaceCoordinate.latitude" />;
            centerParamG = <s:property value="viewPlaceCoordinate.longitude" />;
            placeCoord = ['<s:property value="viewPlaceCoordinate.placeName" />',<s:property value="viewPlaceCoordinate.latitude" />,<s:property value="viewPlaceCoordinate.longitude" />,'<s:property value="viewPlaceCoordinate.stage" />',<s:property value="viewPlaceCoordinate.placeId" />,'<s:property value="viewPlaceCoordinate.infoFlag" />', '<s:property value="viewPlaceCoordinate.pinYinUrl" />'];
            beachArray[beachArray.length] = placeCoord;
        </s:if>
        <s:else>
            centerParamT = 34.264877;
            centerParamG = 108.94386;
        </s:else> 
    }
    
    /**
     * 初始化地图 
     */
    function initialize(mapZoom) {
        initData();
        
        //创建中心坐标对象
        var centerLatLng = new google.maps.LatLng(centerParamT, centerParamG); 
        
        //Map地图规范参数
        var myOptions = { 
                zoom: mapZoom,
                center: centerLatLng, 
                streetViewControl: false,
                mapTypeControl: false,
                zoomControl: false,
                mapTypeId: google.maps.MapTypeId.ROADMAP 
            };
        
        // 创建地图对象
        map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
        
        // 循环创建景区标记
        for (var i = 0; i < beachArray.length; i++) {
           createMarker(beachArray[i]);
        }
    }
    
    /**
     * 创建景区标记
     * @param {Object} beach
     */
    function createMarker(beach) {      
        // 坐标对象
        var latlng = new google.maps.LatLng(beach[1],beach[2]);
        var image = getIconImages(beach);
        
        if (image!="") {    
            // 创建标记对象
            var marker = new google.maps.Marker({
                    position: latlng,
                    map: map,
                    title: beach[0],
                    icon: getIconImages(beach)
                });
        }
    }
    
     /**
      * 标记图片
      */
    function getIconImages(beach) {     
        if (beach[3]=="2") {
            if (beach[5]=="true") {
                return "<%=basePath %>img/ticket2.png"
            } else {
                return "<%=basePath %>img/ticket1.png"
            }
        }
        if (beach[3]=="3") {
            if (beach[5]=="true") {
                return "<%=basePath %>img/hotel2.png"
            } else {
                return "<%=basePath %>img/hotel1.png"
            }
        }
        return "<%=basePath %>img/1.png";
    }
</script>
</head>
<body onload="initialize(<s:property value="mapZoom" />)">
  <div id="map_canvas" style="width: <s:property value="width"/>; height: <s:property value="height"/>; float:left; border: 1px solid black;"></div>
</body>
</html>
