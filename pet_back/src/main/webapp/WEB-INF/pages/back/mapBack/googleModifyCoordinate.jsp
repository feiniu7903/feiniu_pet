<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>驴妈妈_景点/目的地管理后台</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link rel="stylesheet" href="css/place/backstage_table.css" >
<link href="css/place/map.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="js/place/houtai.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=true&libraries=places"></script>
<style>
.map_l li{ width:300px; margin:0 auto; line-height:27px;}
#box{ font-family:Arial; font-weight:bold; display:inline-block; margin-right:4px; background:url(<%=basePath%>img/hongse1.png) no-repeat; width:20px; height:27px; text-align:center; line-height:18px; font-size:15px; color:#FFF; overflow:hidden; cursor:default;}
#box.bj_red_hover{ background:url(<%=basePath%>img/lanse1.png) no-repeat;}
.box2{ margin:0 auto; position:relative; width:300px; height:200px; background:#CCC;}
.biaoji{ font-family:Arial; font-weight:bold; font-size:15px; text-align:center; line-height:40px; width:26px; height:36px; background:url(<%=basePath%>img/hongse1.png) no-repeat 3px 11px; overflow:hidden; color:#FFF; cursor:default;}
.biaoji_2{ background:url(<%=basePath%>img/lanse1.png) no-repeat 3px 11px;}
.biaoji_hover{ background:url(<%=basePath%>img/lanse2.png) no-repeat 0 0; line-height:24px; font-size:18px;}
</style>
</head>

<body>

   <table width="98%" border="0"  align="center" cellpadding="0" cellspacing="0">
     <tr>
       <td height="30" width="47"></td>
       <td width="552" colspan="2">
       <font onClick="history.back()"  style="cursor:pointer;font:#FF0000">返回上一页</font>
        </td>
      </tr>          
    </table>

	<table width="100%"  border="10"  cellpadding="4" cellspacing="1"   bgcolor="#464646">
	  <tr height="30" bgcolor="#FFFFFF">
	    <td width="22%"  bgcolor="#FFFFFF">景区名称：</td>
	    <td><s:property value="placeCoordinateVo.coordinateName"/>&nbsp;</td>
	  </tr>
	  <tr  height="25" bgcolor="#FFFFFF">
	    <td>填写地址：</td>
	    <td><s:property value="placeCoordinateVo.placeAddress"/>&nbsp;</td>
	  </tr>
	  <tr  height="25" bgcolor="#FFFFFF">
	    <td>本地经纬度地址：</td>
	    <td><s:property value="placeCoordinateVo.coordinateAddress"/>&nbsp;</td>
	  </tr>
	  <tr  height="25" bgcolor="#FFFFFF">
	    <td>本地经度：</td>
	    <td><s:property value="placeCoordinateVo.longitude"/>&nbsp;</td>
	  </tr>
	  <tr  height="25"  bgcolor="#FFFFFF">
	    <td>本地纬度：</td>
	    <td><input type="hidden" id="localLatitude" value="<s:property value="placeCoordinateVo.latitude"/>"/><s:property value="placeCoordinateVo.latitude"/>&nbsp;</td>
	  </tr>
	  <tr  height="25" bgcolor="#FFFFFF">
	    <td>根据景区名称或地址查询（优先）：</td>
	    <td><label>
	      <input type="hidden" id="placeId" name="placeId" value="<s:property value='placeCoordinateVo.placeId'/>"/>
	      <input type="hidden" id="placeAddress" name="placeAddress" value="<s:property value='placeCoordinateVo.placeAddress'/>"/>
	      <input type="text" style="background-color: #ffeeee" id="placeName" name="placeName" value='<s:property value="placeCoordinateVo.coordinateName"/>' size="60" />
	      <input type="button" value="搜索坐标" onclick="geoAddress(document.getElementById('placeName').value)"/>
	    </label></td>
	  </tr>
	  <tr  height="25" bgcolor="#FFFFFF">
	    <td>根据景区名称或地址查询：</td>
	    <td><label>
	      <input type="text" id="otherInput" name="textfield2" style="background-color: #eeeeee" size="60" value="<s:property value='placeCoordinateVo.placeAddress'/>"/>
	      <input type="button" value="搜索坐标" onClick="geoAddress(document.getElementById('otherInput').value)"/>
	    </label></td>
	  </tr>	  
	  <tr  bgcolor="#FFFFFF">
	  	<!-- 左边查百度得到的百度地名列表 -->
	    <td style="background-color: #FFFF00" valign="top"><div id="results" ></div></td>
	    <td valign="top">
	    <table width="100%"  border="1" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="65%">
	          <!-- google地图展示容器 -->
	          <div style="width: 100%; height: 450px; float:center; border: 1px solid black;" id="googleMap"><input type="hidden" value="123456" id="centerId"/></div>
	        </td>
	        <!-- 右边坐标展示 -->
	        <td valign="top" bgcolor="#00FFFF">
	          <table width="100%" border="1" cellpadding="4" cellspacing="1" bgcolor="#464646" style="display:none" id="coordinateInfoTable">
		          <tr  bgcolor="#FFFFFF">
		            <td height="25">填写地址：</td>
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td height="40"><s:property value='placeCoordinateVo.placeAddress'/></td>       
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td height="25">经纬度地址：</td>     
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td  height="40"><input id="coordinateAddress" value=0.0 type="text" style="width:300px" readonly="readonly"/></td>    
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td height="25">经度：</td>
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td height="40"><input id="longitude" value=0.0 type="text" readonly="readonly"/></td>  
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td height="25">纬度：</td>       
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td height="40"><input id="latitude"  value=0.0 type="text" readonly="readonly"/></td>
		          </tr>
		          <tr  bgcolor="#FFFFFF">
		            <td  height="100%" align="center">
		              <input type="button" id="Submit3" value="更新经纬度坐标" onClick="showRadidLoginDiv()"/>&nbsp;
		              <s:if test='placeCoordinateVo.isValid=="N"'>
		              <input type="button" id="Submit5" value="取消标记" onClick="updateValid('Y');"/>
		              </s:if>
		              <s:else>
		              <input type="button" id="Submit4" value="标记有问题" onClick="updateValid('N');"/>
		              </s:else>		              
		            </td>
		         </tr>
	        </table></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>

    <!-- 弹出层 -->
	<div class="bgLogin"></div>
	<div class="LoginAndReg">
		<p class="topLogin"><span class="titleLogin">更新坐标</span><a class="btn-close" onclick="hideRapidDiv()"><img src="http://pic.lvmama.com/img/icons/closebtn.gif" alt="关闭" /></a></p>
		<table width="100%" border="1">
		  <tr>
		    <td>填写地址：</td>
		  </tr>
		  <tr>
		    <td height="40"><s:property value="placeCoordinateVo.placeAddress"/></td>
		  </tr>
		  <tr>
		    <td>经纬度反查地址：</td>
		  </tr>
		  <tr>
		    <td height="40"><span id="lastCoordinateAddress"></span></td>
		  </tr>
		  <tr>
		    <td>
		      <div align="center">
		        <input type="button" id="Submit6" value="更新坐标!" onClick="updateCoordinate('Y')"/>
		         <s:if test='placeCoordinateVo.isValid=="N"'>
		         <input type="button" id="Submit7" value="取消标记" onClick="updateValid('Y');"/>
		         </s:if>
		         <s:else>
		         <input type="button" id="Submit8" value="标记有问题" onClick="updateValid('N');"/>
		         </s:else>	
		        <input type="button" id="Submit9" value="不更新" onClick="hideRapidDiv()"     />
		        </div>
		    </td>
		  </tr>
		</table>		
	</div>	
</body>
</html>

<script type="text/javascript">
var localLongitude="<s:property value="placeCoordinateVo.longitude"/>";
var localLatitude="<s:property value="placeCoordinateVo.latitude"/>";
var placeName="<s:property value="placeCoordinateVo.coordinateName"/>";
var placeCAddress="<s:property value="placeCoordinateVo.coordinateAddress"/>";
/**全局变量**/
var com_image = '<%=basePath %>img/hongse1.png';
var com_image0 = '<%=basePath %>img/hongse2.png';
var com_image1 = '<%=basePath %>img/lanse1.png';
var com_image2 = '<%=basePath %>img/lanse2.png';
var geoResults;
var defaultMap;
var markersArray = [];
/**根据坐标获得地图公共方法**/  
function getMap(myLatlng){
var myOptions = {
	    zoom: 14,
	    center: myLatlng,
	    panControl: true,
	    zoomControl: true,
	    scaleControl: true,
	    //mapTypeId: google.maps.MapTypeId.HYBRID,
	    mapTypeId: google.maps.MapTypeId.ROADMAP,
	    //mapTypeId: google.maps.MapTypeId.SATELLITE,
	    //mapTypeId: google.maps.MapTypeId.TERRAIN,
	    mapTypeControl: true,
	    //streetViewControl: true,
	    //overviewMapControl: true
	  }	
return  new google.maps.Map(document.getElementById("googleMap"),    myOptions);
}
/**默认坐标**/
if(localLongitude==""||localLatitude==""){
	localLongitude=116.404844;
	localLatitude=39.923125;
}

/**初始化地图**/
initializeGoogle();
function initializeGoogle(){
	var myLatlng =new google.maps.LatLng(localLatitude, localLongitude);	
	var map = getMap(myLatlng);
	var marker = new google.maps.Marker({
	      position: myLatlng,
	      title:placeName +" 地址： " + placeCAddress,
	      draggable:true,
	      animation: google.maps.Animation.DROP,
	      icon:com_image,
	  });
	marker.setMap(map);	
	//var panoramioLayer = new google.maps.panoramio.PanoramioLayer();
	//panoramioLayer.setMap(map);
	google.maps.event.addListener(marker, 'click', toggleBounce);
	/**控制Marker跳动 **/
	function toggleBounce() {
		  if (marker.getAnimation() != null) {
		    marker.setAnimation(null);
		  } else {
		    marker.setAnimation(google.maps.Animation.BOUNCE);
		  }
	}
}

/**删除所有图标**/
function clearOverlays() {
  if (markersArray) {
    for (i in markersArray) {
      markersArray[i].setMap(null);
    }
  }
}
/**删除第x个图标**/
function clearOverlays(x) {
  if (markersArray) {
    for (i in markersArray) {
    	if(x==i){
         markersArray[i].setMap(null);}
    }
  }
}
/**改变第x个图标的图片**/
function changOverlaysImage(x , icon) {
  if (markersArray) {
    for (i in markersArray) {
    	if(x==i){
         markersArray[i].setIcon(icon);}
    }
  }
}

/**显示所有图标**/
function showOverlays() {
  if (markersArray) {
    for (i in markersArray) {
      markersArray[i].setMap(defaultMap);
    }
  }
}
/**显示x图标**/
function showOverlays(x) {
  if (markersArray) {
    for (i in markersArray) {
    	if(x==i){
      markersArray[i].setMap(defaultMap);}
    }
  }
}


/**左边根据地址或地名反查GOOGLE地图匹配出可选择的兴趣点**/
function geoAddress(keyAddress) {
      var geocoder = new google.maps.Geocoder();
      geocoder.geocode( { 'address': keyAddress}, function(results, status) {
    	  if (status == google.maps.GeocoderStatus.OK) {
    		$("#coordinateInfoTable").css({"display":"none"});
    		geoResults=results;
    	 	var map =getMap(results[0].geometry.location);
    	 	document.getElementById("results").innerHTML="";
    	 	var s = [];
	        s.push('<ul id="map_l" class="map_l map_click" style="background-color:#ff0000; ">');
	        for (var i = 0; i < results.length; i++){
	            s.push('<li  onclick=" map_click()" onmouseover="openInfoWinFuns('+ i +') ,map_hover()" id="list' + i + '" style="background-color:#eeeeee ; padding:7px ;cursor: pointer; overflow: hidden; line-height: 17px;" >');
	            s.push('<span class="box" id="box">'+i+'</span>');
	            s.push('<span style="color:#ff0000;text-decoration:underline">' + '<b>' + results[i].address_components[0].short_name + '</b>' + '</span>');
	            s.push('<span style="color:#666;">   - 地址： ' + results[i].formatted_address + '</span>');
	            s.push('</li>');
	            s.push('');	
	            //标记在地图上
	            var marker = new google.maps.Marker({
	            	  map: map,
			          position: results[i].geometry.location,
			  	      title: results[i].address_components[0].short_name+'  地址：'+results[i].formatted_address,
			  	      draggable:true,
			  	      animation: google.maps.Animation.DROP,
			  	      icon:com_image,
			  	  });
	          
	          //保持在全局变量 中
	            markersArray.push(marker); 
	        }
	        s.push('</ul>');	        
	        document.getElementById("results").innerHTML = s.join("");
	        defaultMap=map;
	        map_hover();
	        windowInfoBind(map ,geoResults,markersArray);
	        bindMarkersArray(markersArray);    

      } else {
        alert("Geocode was not successful for the following reason: " + status);
      }
    });
      
  }  

/**
 * 为markersArray 绑定事件
 */
function bindMarkersArray(markersArray) {
	for ( var i = 0; i < markersArray.length; i++) {
		  /**绑定事件换色**/
        google.maps.event.addListener(markersArray[i], 'mouseover', function(event) {
        	markersArray[i].setIcon(com_image2);
		  });
	}
	
}

/**窗口事件**/
function windowInfoBind(map ,geoResults,markersArray  ){
	for ( var i = 0; i < markersArray.length; i++) {	
	var contentString = '<div id="content">'+
	    '<div id="markerInfo'+i+'" >'+
	    '<span>'+ geoResults[i].formatted_address+'</span>'+
	    '</div>';	    
	var infowindow = new google.maps.InfoWindow({
	    content: contentString
	});
	google.maps.event.addListener(markersArray[i], 'click', function() {
	  infowindow.open(map,markersArray[i]);
	});
	
  }
}
 

/** 右边展示坐标，地址，以及绑定标记事件**/
function openInfoWinFuns(i){
	defaultMap.setCenter(geoResults[i].geometry.location);
	markersArray[i].setAnimation(google.maps.Animation.BOUNCE);
	$("#coordinateInfoTable").css({"display":""});
	$("#coordinateAddress").val(geoResults[i].formatted_address);
	$("#longitude").val(geoResults[i].geometry.location.lng());
	$("#latitude").val(geoResults[i].geometry.location.lat());	
	google.maps.event.addListener(markersArray[i], 'click', toggleBounce);
	/**控制Marker跳动 **/
	function toggleBounce() {
		  if (markersArray[i].getAnimation() != null) {
			  markersArray[i].setAnimation(null);
		  } else {
			  markersArray[i].setAnimation(google.maps.Animation.BOUNCE);
		  }
	}
	
	google.maps.event.addListener(markersArray[i], 'drag', function(event) {
		$("#longitude").val(event.latLng.lng());
		$("#latitude").val(event.latLng.lat());	
	  });
	
	google.maps.event.addListener(markersArray[i], 'dragend', function(event) {
		//根据最新经纬度反查地址
		var newLatlng =new google.maps.LatLng(event.latLng.lat(), event.latLng.lng());	
		 var geocoder = new google.maps.Geocoder();
	    geocoder.geocode( { 'location': newLatlng}, function(results, status) {
	  	  if (status == google.maps.GeocoderStatus.OK) {	  		
	  		$("#coordinateAddress").val(results[0].formatted_address);
	    }
	  });		
  });	
}


/**
 * 更新坐标
 */
function updateCoordinate(isValid) {	
	 $("#submit6").attr("disabled", true); 
	var placeId = $("#placeId").val();			
	var jsonData = {id:placeId,isValid:isValid};			
	if (isValid=="Y") {
		var coordinateAddress = $("#lastCoordinateAddress").html();
		var longitude = $("#longitude").val();
		var latitude = $("#latitude").val();	
		jsonData = {id:placeId,latitude:latitude,longitude:longitude,coordinateAddress:coordinateAddress,isValid:isValid};
	}		
				
	$.ajax({type:"POST",
		url:"/pet_back/updatePlaceCoordinatGoogle.do",
		data:jsonData, 
		dataType:"json", 
		success:function (json) {
     	if (json.flag=='Y') {
     		alert("保存成功！");
     	} else {
     		alert(json.msg);		
     	}
     
     	hideRapidDiv();
     	location.reload();	
	}});
}

/**
 * 更新坐标有效性
 */
function updateValid(isValid) {
	var placeId = $("#placeId").val();	
	var jsonData = {id:placeId,isValid:isValid};			
	$.ajax({type:"POST", 
		url:"/pet_back/updatePlaceCoordinatGoogle.do",
		data:jsonData, 
		dataType:"json", 
		success:function (json) {
     	if (json.flag=='Y' || json.msg!=null) {
     		alert("修改成功！");
     	}
     	//location.reload();
	}});
}

function showRadidLoginDiv() {
	$("#lastCoordinateAddress").html($("#coordinateAddress").val());
	$(".bgLogin").show();		
	$(".LoginAndReg").show();						
	var topN = $(window).scrollTop()/2 + 150;
	var leftN = $(window).width()/2 - $(".LoginAndReg").width()/2;
	$(".LoginAndReg").fadeIn(200).css({"top":topN,"left":leftN,"position":"absolute"}); 	
}

function hideRapidDiv() {		
	$(".bgLogin").hide();		
	$(".LoginAndReg").hide();	
}		

function map_click(){
	   $('.map_click li').click(function(){ 
			var _list = $(this).index();
			$('.map_click').removeClass('map_l');
			$(this).find('#box').addClass('bj_red_hover');
			$(this).siblings().find('#box').removeClass('bj_red_hover');
			clearOverlays(_list);
			changOverlaysImage(_list,com_image1);
			showOverlays(_list);
			//$(this).find('#box').attr('onmouseover','');
			$('#map_l li').unbind("hover",map_hover);
					  });
};

function map_hover(){	
	   $('.map_l li').hover(function(){ 
		var _list = $(this).index();		
		$(this).find('#box').addClass('bj_red_hover');
		//defaultMap.setCenter(geoResults[_list].geometry.location);
		defaultMap.panTo(geoResults[_list].geometry.location);
		clearOverlays(_list);
		changOverlaysImage(_list,com_image1);
		showOverlays(_list);
				  },
	     function(){ 
		var _list = $(this).index();
		$(this).find('#box').removeClass('bj_red_hover');
		clearOverlays(_list);
		changOverlaysImage(_list,com_image);
		showOverlays(_list);
				  });	   
}

</script>


<!-- 
var contentString = '<div id="content">'+
    '<div id="siteNotice">'+
    '
    '</div>';

var infowindow = new google.maps.InfoWindow({
    content: contentString
});

var marker = new google.maps.Marker({
    position: myLatlng,
    map: map,
    title:"Uluru (Ayers Rock)"
});

google.maps.event.addListener(marker, 'click', function() {
  infowindow.open(map,marker);
});
 -->