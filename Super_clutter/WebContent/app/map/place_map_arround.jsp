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
  var beaches = [
                 ['上海城隍庙', 31.2430, 121.2943],
               ['东方明珠电视塔', 31.1430, 121.2943]

             ];

var map=null;
  function initialize() {
    var mylatlng = new google.maps.LatLng(31.1430, 121.2943);
    var myOptions = {
       zoom: 12,
	center: mylatlng,
	mapTypeId: google.maps.MapTypeId.ROADMAP
    };
     map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    setMarkers(map,beaches);


  }

  function  setMarkers(map, locations) {
	  for (var i = 0; i < locations.length; i++) {
		  var beach = locations[i];
	var latlng = new google.maps.LatLng(beach[1], beach[2]);
	var marker = new google.maps.Marker({
	    position: latlng,
	    map: map,
	    title:beach[0]
	});
	attachSecretMessage(marker,beach[0]);
	  }
  }
  
  function attachSecretMessage(marker, msg) {
  var infowindow = new google.maps.InfoWindow(
      { content: msg,
        size: new google.maps.Size(20,20)
      });
  infowindow.open(map,marker);
  google.maps.event.addListener(marker, 'click', function() {
    infowindow.open(map,marker);
  });
  }
</script>
</head>
<body onload="initialize()" style="margin:0 0 0 0;">
  <div id="map_canvas" style="width:100%; height:100%"></div>
</body>
</html>

