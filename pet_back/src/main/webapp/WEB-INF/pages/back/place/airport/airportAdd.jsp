<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
<title>机场管理</title>
<style type="text/css">
   label{
		float: right;
	}
</style>
</head>
<body>
<div id="popDiv" style="display: none"></div>
<div class="p_box">
<form action="" method="post" id="airportFrom">
     <input type="hidden" name="placeAirport.placeAirportId"   value="${placeAirport.placeAirportId}"/> 
     <input id="placeId" type="hidden" name="placeAirport.placeId"  value="${placeAirport.placeId}"/>
	<table class="p_table no_bd form-inline" width="100%">
			<tr> 
		      <td class="p_label" width="120px;">城市名称:</td>
		      <td> 
			      <input type="text" name="search" id="search" value="${search}"/>
				  </td>
		      <td class="p_label" >城市编码:</td>
		      <td><input id="airportCode" name="placeAirport.cityCode" value="${placeAirport.cityCode}"/> </td>
		      </tr>
		   <tr>
		      <td class="p_label"  >机场编码 :</td>
		      <td><input  name="placeAirport.airportCode"  value="${placeAirport.airportCode}" />
		        </td>
		      <td class="p_label"  >机场名称:</td>
		      <td><input  name="placeAirport.airportName" value="${placeAirport.airportName}" /></td>
		      </tr>
		   <tr> 
		      <td class="p_label"  >机场英文名:</td>
		      <td><input  name="placeAirport.enName" colspan="2" value="${placeAirport.enName}" /></td>
		      </tr>
		</table>
	<p class="tc mt10 mb10">
	 <input class="btn btn-small w5" type="button" value="确定"  onclick="javascript:return airportAddSumit();">
	 <input class="btn btn-small w5" type="button"  value="关闭" onclick="javascript:closeDialog();"/> 
 	</p>
</form>
</div>
<!-- js -->
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
<script src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<script type="text/javascript">
$(function(){
	//校验
	$("#airportFrom").validate({
		rules: {  
			"search":{
				 required:true
			},
			"placeAirport.cityCode":{
				 required:true,
	             maxlength:3
			},
			"placeAirport.airportCode":{
				 required:true,
	             maxlength:3
			},
			"placeAirport.airportName":{
				required:true
			},
			"placeAirport.enName":{
				 required:true
			}
		}, 
		messages: {  
			"search":{
				 required:"不能为空"
			},
			"placeAirport.cityCode":{
				required:"不能为空",
				maxlength:"只能输入三个字符"
			},
			"placeAirport.airportCode":{
				 required:"不能为空",
	             maxlength:"只能输入三个字符"
			},
			"placeAirport.airportName":{
				 required:"不能为空"
			},
			"placeAirport.enName":{
				 required:"不能为空"
			}
		}
	});
});
function airportAddSumit(){
	if(!$("#airportFrom").valid()){
		return false;
	}
    var  url='${basePath}/place/airportSave.do';
	var form='airportFrom';
	checkAndSubmit(url,form);
}
function closeDialog(){
	$("#popDiv").dialog("close");
	 window.location.href="${basePath}/place/airportList.do";
}
//提交之后，重刷新table
function checkAndSubmit(url,form) {
var options = {
		url:url,
		type : 'POST',
		dataType:'json',
			success:function(data){
      		   if(data.success==true) {
				 alert("操作成功!");
				 $("#popDiv").dialog("close");
				 window.location.href="${basePath}/place/airportList.do";
				} else {
				  alert("操作失败："+data.message);
			    }
			},
		error:function(){
                 alert("出现错误");
             }
	};
$('#'+form).ajaxSubmit(options);
}
$(function(){
	$("#search").jsonSuggest({
		url:"${basePath}/place/searchPlace.do",
		maxResults: 20,
		minCharacters:1,
		onSelect:function(item){
			$("#placeId").val(item.id);
			$("#airportCode").val(item.airportCode);
		}
	});
});
</script>
</body>
</html>