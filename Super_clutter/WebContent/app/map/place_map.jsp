<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=ABQIAAAAneOf_UjYOrowp_44MUrKThT3d7ZCT8FDBIk6SZTO35J3Sa6C0xQ2jxoAtu9Ka5s5SixPg3myZo7MKA"></script>
<script type="text/javascript">
var lo = <%=request.getParameter("longitude")%>;
var la = <%=request.getParameter("latitude")%>;
  function initialize() {
    var latlng = new google.maps.LatLng(la,lo);
    var myOptions = {
       zoom: 12,
	center: latlng,
	mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
	var contentString = '<div id="content">东方明珠电视塔</div>';
	
	var infowindow = new google.maps.InfoWindow({
    content: contentString
});

var marker = new google.maps.Marker({
    position: latlng,
    map: map,
    title:"东方明珠电视塔"
});



google.maps.event.addListener(marker, 'click', function() {
  //infowindow.open(map,marker);
});

  }

</script>
</head>
<body onload="initialize()" style="margin:0 0 0 0;">
  <div id="map_canvas" style="width:100%; height:100%"></div>
</body>
</html>
