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
<link rel="stylesheet" href="css/place/backstage_table.css">
<link href="css/place/map.css" rel="stylesheet" type="text/css" />
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="js/place/houtai.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.3"></script>
<script type="text/javascript" src="http://dev.baidu.com/wiki/static/map/API/examples/script/convertor.js"></script>
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

	<table width="100%"  border="10"  cellpadding="4" cellspacing="1" bgcolor="#464646">
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
	    <td>根据景区名称或地址查询1（优先）：</td>
	    <td><label>
	      <input type="hidden" id="placeId" name="placeId" value="<s:property value='placeCoordinateVo.placeId'/>"/>
	      <input type="hidden" id="placeAddress" name="placeAddress" value="<s:property value='placeCoordinateVo.placeAddress'/>"/>
	      <input type="text" style="background-color: #ffeeee" id="placeName" name="placeName" value='<s:property value="placeCoordinateVo.coordinateName"/>' size="60" />
	      <input type="button" value="搜索坐标" onclick="chosethis(document.getElementById('placeName').value)"/>
	    </label></td>
	  </tr>
	  <tr  height="25" bgcolor="#FFFFFF">
	    <td>根据景区名称或地址查询2：</td>
	    <td><label>
	      <input type="text" id="otherInput" name="textfield2" style="background-color: #eeeeee" size="60" value="<s:property value='placeCoordinateVo.placeAddress'/>"/>
	      <input type="button" value="搜索坐标" onClick="chosethis(document.getElementById('otherInput').value)"/>
	    </label></td>
	  </tr>	  
	  <tr  bgcolor="#FFFFFF">
	  	<!-- 左边查百度得到的百度地名列表 -->
	    <td style="background-color: #FFFF00" valign="top"><div id="results"></div></td>
	    <td valign="top">
	    <table width="100%"  border="1" cellpadding="0" cellspacing="0">
	      <tr>
	        <td width="65%">
	          <!-- 百度地图展示容器 -->
	          <div style="width: 100%; height: 450px; float:center; border: 1px solid black;" id="container"><input type="hidden" value="123456" id="centerId"/></div>
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
		            <td  height="40"><p id="coordinateAddress"/></td>    
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
		              <input type="button" id="Submit3" value="更新经纬度坐标" onClick="getLocationAddress()"/>&nbsp;
		              <s:if test='placeCoordinateVo.isValid=="N"'>
		              <input type="button" id="Submit5" value="取消标记" onClick="updateMarker('Y');"/>
		              </s:if>
		              <s:else>
		              <input type="button" id="Submit4" value="标记有问题" onClick="updateMarker('N');"/>
		              </s:else>		              
		            </td>
		         </tr>
	        </table></td>
	      </tr>
	    </table></td>
	  </tr>
	</table>

    <!-- 登录弹出层 -->
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
		    <td height="40"><p id="coordinateAddress2"/></td>
		  </tr>
		  <tr>
		    <td>
		      <div align="center">
		        <input type="button" id="Submit6" value="更新坐标" onClick="updateCoordinate('Y')"/>
		         <s:if test='placeCoordinateVo.isValid=="N"'>
		         <input type="button" id="Submit7" value="取消标记" onClick="updateMarker('Y');"/>
		         </s:if>
		         <s:else>
		         <input type="button" id="Submit8" value="标记有问题" onClick="updateMarker('N');"/>
		         </s:else>	
		        <input type="button" id="Submit9" value="不更新" onClick="hideRapidDiv()"/>
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
//初始化百度地图
initBaiduMap(localLongitude,localLatitude,placeName,placeCAddress);

function initBaiduMap(localLongitude,localLatitude,placeName,placeCAddress){
	if(localLongitude==""){
	localLongitude=116.404844;
    }
	if(localLatitude==""){
	localLatitude=39.923125;
    }
	var map = new BMap.Map("container");
	var point = new BMap.Point(localLongitude,localLatitude);
	map.centerAndZoom(point, 15);
	map.enableScrollWheelZoom();                  // 启用滚轮放大缩小。
    map.enableKeyboard();                         // 启用键盘操作。
    map.addControl(new BMap.OverviewMapControl());//添加缩略地图控件
	map.addControl(new BMap.NavigationControl()); //添加导航控件
	map.addOverlay(markerInfoWindow(placeName,placeCAddress,point));
	
	 /**
      * 根据景区名称查询坐标
      */ 
    chosethis = function (searchWord){
			if (searchWord=="") {
				alert("请输入搜索的景区名称");
			}
			window.openInfoWinFuns = null;
			var options = {
			  onSearchComplete: function(results){
			    // 判断状态是否正确
			    if (local.getStatus() == BMAP_STATUS_SUCCESS){
			    	document.getElementById("results").innerHTML="";
			        var s = [];
			        s.push('<div style="font-family: arial,sans-serif; border: 1px solid rgb(153, 153, 153); font-size: 12px;">');
			        s.push('<div style="background: none repeat scroll 0% 0% rgb(255, 255, 255);">');
			        s.push('<ol style="list-style: none outside none; padding: 0pt; margin: 0pt;">');
			        openInfoWinFuns = [];
			        for (var i = 0; i < results.getCurrentNumPois(); i ++){
			            var marker = addMarker(results.getPoi(i).point,i);
			            var openInfoWinFun = addInfoWindow(marker,results.getPoi(i),i);
			            openInfoWinFuns.push(openInfoWinFun);
			            // 默认打开第一标注的信息窗口
			            var selected = "";
			            if(i == 0){
			                selected = "background-color:#f0f0f0;";
			                openInfoWinFun();
			            }
			            s.push('<li id="list' + i + '" style="margin: 2px 0pt; padding: 0pt 5px 0pt 3px; cursor: pointer; overflow: hidden; line-height: 17px;' + selected + '" onclick="openInfoWinFuns[' + i + ']()">');
			            s.push('<span style="width:1px;background:url(http://api.map.baidu.com/bmap/red_labels.gif) 0 ' + ( 2 - i*20 ) + 'px no-repeat;padding-left:10px;margin-right:3px"> </span>');
			            s.push('<span style="color:#00c;text-decoration:underline">' + results.getPoi(i).title.replace(new RegExp(results.keyword,"g"),'<b>' + results.keyword + '</b>') + '</span>');
			            s.push('<span style="color:#666;"> - ' + results.getPoi(i).address + '</span>');
			            s.push('</li>');
			            s.push('');
			        }
			        s.push('</ol></div></div>');
			        document.getElementById("results").innerHTML = s.join("");
			    }else{
			    	$("#coordinateInfoTable").css({"display":"none"});
			    	document.getElementById("results").innerHTML="";
			    	alert("没有找到相关信息,请重新选择关键词！");
			    }
			  }
			};

			// 添加标注
			function addMarker(point, index){
			  var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
			    offset: new BMap.Size(10, 25),
			    imageOffset: new BMap.Size(0, 0 - index * 25)
			  });
			  var marker = new BMap.Marker(point, {icon: myIcon});
			  map.addOverlay(marker);
			  return marker;
			}
			// 添加信息窗口
			function addInfoWindow(marker,poi,index){
			    var maxLen = 10;
			    // infowindow的标题
			    var infoWindowTitle = '<div style="font-weight:bold;color:#CE5521;font-size:14px">'+poi.title+'</div>';
			    // infowindow的显示信息
			    var infoWindowHtml = [];
			    infoWindowHtml.push('<table cellspacing="0" style="table-layout:fixed;width:100%;font:12px arial,simsun,sans-serif"><tbody>');
			    infoWindowHtml.push('<tr>');
			    infoWindowHtml.push('<td style="vertical-align:top;line-height:16px">' + poi.address + ' </td>');
			    infoWindowHtml.push('</tr>');
			    infoWindowHtml.push('</tbody></table>');
			    var infoWindow = new BMap.InfoWindow(infoWindowHtml.join(""),{title:infoWindowTitle,width:200}); 
			    var openInfoWinFun = function(){
			        marker.openInfoWindow(infoWindow);
			        for(var cnt = 0; cnt < maxLen; cnt++){
			            if(!document.getElementById("list" + cnt)){continue;}
			            if(cnt == index){
			                document.getElementById("list" + cnt).style.backgroundColor = "#f0f0f0";
			            }else{
			                document.getElementById("list" + cnt).style.backgroundColor = "#fff";
			            }
			            $("#coordinateInfoTable").css({"display":""});
						$("#coordinateAddress").html(poi.address);
						$("#longitude").val(marker.point.lng);
						$("#latitude").val(marker.point.lat);
			        }
			    }
			    
				var gc = new BMap.Geocoder();
			    marker.addEventListener("click", function(e){
			    	var pt = e.point;
				    gc.getLocation(pt, function(rs){
			        var addComp = rs.addressComponents;
			    	$("#coordinateInfoTable").css({"display":""});
					$("#coordinateAddress").html(poi.address);
					$("#longitude").val(e.point.lng);
					$("#latitude").val(e.point.lat);
					});
					//打开信息窗口
				    openInfoWinFun();
			    });
			    return openInfoWinFun;
			}
			var local = new BMap.LocalSearch("全国", options);
			local.search(searchWord);
		}
}

function markerInfoWindow(title,placeCAddress,point){
	var opts = {
	  width : 250,     // 信息窗口宽度
	  height: 100,     // 信息窗口高度
	  title : title  // 信息窗口标题
	}
	var infoWindow = new BMap.InfoWindow(placeCAddress, opts);  // 创建信息窗口对象
	var marker = new BMap.Marker(point);
	marker.enableDragging(true); // 设置标注可拖拽
	marker.addEventListener("dragging", function(e){
		$("#coordinateInfoTable").css({"display":""});
		$("#coordinateAddress").html(placeCAddress);
		$("#longitude").val(e.point.lng);
		$("#latitude").val(e.point.lat);
	});          
	marker.addEventListener("click", function(e){          
       this.openInfoWindow(infoWindow); 
        $("#coordinateInfoTable").css({"display":""});
		$("#coordinateAddress").html(placeCAddress);
		$("#longitude").val(e.point.lng);
		$("#latitude").val(e.point.lat);
	});
	return marker;
}

		/**
		 * 根据坐标查询地区详细地址
		 */
		function getLocationAddress(){
			var longitude = $("#longitude").val();
			var latitude = $("#latitude").val();
			
			if ($.trim(longitude)=="" || $.trim(latitude)=="") {
				alert("经纬度不能为空");
				return;
			}
		    var pt = new BMap.Point(longitude,latitude);
		    var myGeo = new BMap.Geocoder();  
			// 根据坐标得到地址描述  
			myGeo.getLocation(pt, function(result){  
			 if (result){  
			   $("#coordinateAddress2").html(result.address);	
			   showRadidLoginDiv();
			 }else{
				 $("#coordinateInfoTable").css({"display":"none"});
				alert("没有找到详细地址");
			 }
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
				var coordinateAddress = $("#coordinateAddress2").html();
				var longitude = $("#longitude").val();
				var latitude = $("#latitude").val();	
				jsonData = {id:placeId,latitude:latitude,longitude:longitude,coordinateAddress:coordinateAddress,isValid:isValid};
			}		
						
			$.ajax({type:"POST",
				url:"/pet_back/updatePlaceCoordinatBaidu.do",
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
		 * 更新标记
		 */
		function updateMarker(isValid) {	
			var placeId = $("#placeId").val();	
			var jsonData = {id:placeId,isValid:isValid};			
			$.ajax({type:"POST", 
				url:"/pet_back/updatePlaceCoordinatBaidu.do",
				data:jsonData, 
				dataType:"json", 
				success:function (json) {
		     	if (json.flag=='Y' || json.msg!=null) {
		     		alert("修改成功！");
		     	} 
		     	location.reload();
			}});
		}
		
		function showRadidLoginDiv() {
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
</script>