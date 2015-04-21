<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>机场管理</title>
<!-- js -->
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
<script src="${basePath}/js/base/jquery.jsonSuggest-2.min.js"></script>
<script type="text/javascript">
function airportAdd(){
	var url="${basePath}/place/airportAdd.do";
	var title="增加";
	showDetail(url,title,670,300);
}
function airportEdit(placeAirportId){
	var url="${basePath}/place/airportEdit.do?placeAirportId="+placeAirportId;
	var title="编辑";
	showDetail(url,title,670,300);
}
function airportDel(placeAirportId){
	var url="${basePath}/place/airportDel.do?placeAirportId="+placeAirportId;
	doAjax(url);
}

//post处理，重刷新
function doAjax(url) {
	var options = {
			url:url,
			type : 'POST',
			dataType:'json',
 			success:function(data){
          		   if(data.success==true) {
          			 alert("操作成功："+data.message);
    				 window.location.href="${basePath}/place/airportList.do";
   		          } else {
					  alert("操作失败："+data.message);
				    }
 			},
			error:function(){
                     alert("出现错误");
                 }
		};
	
	$.ajax(options);
}
function showDetail(url,title,width,height) {
	 $("#popDiv").html("");
	 $("#popDiv").load(url,function() {
			$(this).dialog({
	         		modal:true,
	         		title:title,
	         		width:width,
	        		height:height
	             });
			$("#searchPlace").jsonSuggest({
				url:"${basePath}/place/searchPlace.do",
				maxResults: 20,
				minCharacters:1,
				onSelect:function(item){
					$("#comPlaceId").val(item.id);
				}
			});
	         });
	 
	}
</script>
</head>
<body>
<div id="popDiv" style="display: none"></div>
<div class="p_box">
<form action="${basePath}/place/airportList.do" id="airportSearchFrom" method="post">
<table class="p_table no_bd form-inline" width="100%">
		<tr> 
	      <td class="p_label" width="50px;">三字编码:</td>
	      <td><input name="cityCode" value="${cityCode}" > </td>
	      <td class="p_label" width="120px;">城市名称:</td>
	      <td> 
	      <input type="text" name="search" id="searchPlace" value="${search}"/>
		  <input type="hidden" name="placeId" id="comPlaceId" value="${placeId}"/></td>
	   </tr>
	   <tr> <td class="p_label" width="120px;">机场名称:</td>
	      <td><input  name="airportName" value="${airportName}"></td>
	      <td class="p_label" width="120px;">机场英文名:</td>
	      <td><input  name="enName" value="${enName}" ></td>
	   </tr>
</table>
	<p class="tc mt10 mb10">
	 <input class="btn btn-small w5" type="submit" value="查询"  />
	 <input  class="btn btn-small w5" type="button"  value="添加" onclick="javascript:return airportAdd();"/> 
 	</p>
</form>
</div>
<div class="p_box">
<table class="p_table table_center" >
  <tr>
    <th>ID</th>
    <th>机场编码</th>
    <th>城市名称</th>
    <th>机场名称</th>
    <th>英文名称</th>
    <th>操作</th>
   </tr>
 <s:iterator value="placeAirportList">
  <tr >
    <td>${placeAirportId}</td>
    <td>${airportCode}</td>
    <td>${cityName}</td>
    <td>${airportName}</td>
    <td>${enName}</td>
    <td><a  href="#" 
    onclick="javascript:return airportEdit('${placeAirportId}');"/>编辑
<%--      <a href="#" onclick="javascript:return airportDel('${placeAirportId}');" /> 删除 --%>
     </td>
   </tr>
 </s:iterator>
  <tr>
   <td colspan="2" >总记录：${pagination.totalResultSize}</td>
  <td colspan="4" style="text-align: right;padding-right: 20px;">
      ${pagination.pagination}
   </td></tr>
</table>
</div>
<script type="text/javascript">
$(function(){
	$("#searchPlace").jsonSuggest({
		url:"${basePath}/place/searchPlace.do",
		maxResults: 20,
		minCharacters:1,
		onSelect:function(item){
			$("#comPlaceId").val(item.id);
		}
	});
});

$(function(){
	//校验
	$("#airportSearchFrom").validate({
		rules: {    
			"cityCode":{
				 required:false,
	             maxlength:3
			}
		}, 
		messages: {    
			"cityCode":{
				maxlength:"只能输入三个字符"
			}
		}
	});
});
</script>
</body>
</html>