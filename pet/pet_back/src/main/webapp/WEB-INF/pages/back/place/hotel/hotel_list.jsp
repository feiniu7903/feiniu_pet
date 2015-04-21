<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
<script language="JavaScript" type="text/javascript" src="${basePath}/js/base/jquery.cropzoom.js"></script>
<link href="${basePath}/css/jquery.cropzoom.css" rel="stylesheet" type="text/css" />
<script src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/place/place.js"></script>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/place/houtai.js"></script>
<script charset="utf-8" src="${basePath}/js/base/kindeditor-4.1.7/kindeditor.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript">

$(function(){
	
	$("#hotel_add").click(function(){
		$.ajaxDialog("${basePath}place/placeAdd.do?stage=3","酒店名称添加","hotel_detail_dialog")
	});
	
});
function placePicList(placeId){
	$.ajaxDialog({
			url:"${basePath}/place/placePhotoListVer2.do?placePhoto.placeId="+placeId+"&stage=3",
			title:"酒店图片",
			id:"pic_list_dialog"
		});
}
function checkForm(){
	var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
	if($("input[name='place.placeId']").val()!=""){
		if(!reg.test($("input[name='place.placeId']").val())){
			alert("请输入正确的酒店ID（数字）!");
			$("input[name='place.placeId']").focus();
			return false;
		}
	}
	if($("input[name='place.parentPlaceId']").val()!=""){
		if(!reg.test($("input[name='place.parentPlaceId']").val())){
			alert("请输入正确的上级目的地ID（数字）!");
			$("input[name='place.parentPlaceId']").focus();
			return false;
		}
	}
	$("#form1").submit();
	return true;
	
}
function deleteMemcached(url){
	$.getJSON("${pageContext.request.contextPath}/pub/memcachedTool!clearMemcached.do?memcachedKey="+url,function(data){
		if(data.flag == "true"){
			alert("清除成功!");
		}else{
			alert("没有缓存!");
		}
	});
}

</script>
</head>
<body>
<div id="popDiv" style="display: none;height:auto;"></div>
<div class="p_box">
	<form action="${basePath}/place/hotelList.do" method="post" id="form1">
		<table class="p_table no_bd form-inline" width="100%">
			<tr> 
		      <td class="p_label" width="50px;">酒店ID:</td>
		      <td>
		      	<s:textfield name="place.placeId"></s:textfield>
		      </td>
		      <td class="p_label" width="120px;"> 酒店名称(模糊):</td>
		      <td><s:textfield name="place.name"></s:textfield></td>
		      <td class="p_label" width="120px;">上级目的地ID:</td>
		      <td><s:textfield name="place.parentPlaceId"></s:textfield></td>
		    </tr>
		    <tr>
		      <td class="p_label" width="120px;">上级目的地名称:</td>
		      <td><s:textfield name="place.parentPlaceName"></s:textfield></td>
		       <td class="p_label">酒店主主题:</td>
		       <td><s:select  list="subjectList"  name="place.firstTopic" headerKey="" headerValue="--请选择--" theme="simple" listKey="subjectName" listValue="subjectName">
			    </s:select></td>
			    <td class="p_label">状态:</td>
		       <td><s:select  list="isValidList"  name="place.isValid" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue"></s:select>
		       </td>
			</tr>
			<tr>
			   <td  class="p_label">创建时间:</td>
			   <td colspan="5"><input  name="startDate" id="startDate" value='<s:property value="startDate"/>' type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			       -<input name="endDate" id="endDate" value="<s:property value="endDate"/>" type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})"/></td>
			</tr>
	</table>
			<p class="tc mt10 mb10">
			  <input class="btn btn-small w5" type="button" name="saveseq" id="saveseq" value="保存排序"/> 
	          <input class="btn btn-small w5" type="button" name="hotel_add" id="hotel_add" value="添加酒店"/> 
			  <input class="btn btn-small w5" name="btnQuery" value="查询" type="button" onclick="return checkForm()">
			</p>
	</form>
</div>
<table class="p_table table_center" >
  <tr>
    <th width="5%;" style="text-align: left;"><input type="checkbox" name="chk_all" id="chk_all"/></th>
    <th width="7%">ID</th>
    <th width="20%">名称</th>
    <th width="5%">状态</th>
    <th width="10%">境内/境外</th>
    <th width="10%">创建时间</th>
    <th width="5%">排序值</th>
    <th width="38%">操作</th>
  </tr>
  <s:iterator value="placeList">
	
  <tr >
    <td><input type="checkbox" name="chk_list"  value="${placeId }"/></td>
    <td><s:property value="placeId"/></td>
    <td><a href="http://www.lvmama.com/hotel/v${placeId}" target="_blank"><s:property value="name"/></a></td>
    <td><s:if test='isValid=="Y"'><font color=green>有效</font></s:if><s:else><font color=red>无效</font></s:else></td>
    <td><s:if test='placeType=="DOMESTIC"'>国内</s:if><s:elseif test='placeType=="ABROAD"'>国外</s:elseif><s:else>无</s:else></td>
    <td><s:date name="createTime" format="yyyy-MM-dd"/></td>
    <td><input type="text"  class="seq_check" id="chk_list_${placeId }" value="${seq }" style="width:100px"/></td>
    <td class="gl_cz">
   <mis:checkPerm permCode="4006"><a  href="javascript:$.ajaxDialog({url:'${basePath}/place/hotelEdit.do?placeId=${placeId}&stage=3',title:'酒店基本信息',id:'hotel_info_dialog',kindEditorId : 'remarkesId',kindEditorType : 'simple'})">酒店基本信息</a></mis:checkPerm>
   <mis:checkPerm permCode="4007"><a class="hotelPicList" href="javascript:placePicList(${placeId});">酒店图片</a></mis:checkPerm>
   <mis:checkPerm permCode="4009"><a  href="javascript:$.ajaxDialog('${basePath}/place/queryAllIntroduce.do?placeId=${placeId}','酒店介绍','introduce_dialog')">酒店介绍</a></mis:checkPerm>
   <a  href="javascript:$.ajaxDialog('${basePath}/hotel/viewHotelTrafficInfo.do?placeId=${placeId}&stage=3','酒店交通信息','traffice_info_dialog')">交通信息</a>
   <mis:checkPerm permCode="4009"><a href="javascript:$.ajaxDialog('${basePath}/place/placeHotelNotice.do?placeId=${placeId}&noticeType=ALL','公告','placeHotel_OneId')">公告</a></mis:checkPerm>
   <mis:checkPerm permCode="4019"><a href="${basePath}/place/productSeqList.do?productSearchInfo.productAlltoPlaceIds=${placeId}">关联产品</a></mis:checkPerm>
   <mis:checkPerm permCode="4020"><a style='display: none' href="${basePath}/QuestionAnswer/QueryAsk.do?placeId=${placeId}">问答</a></mis:checkPerm>
   <mis:checkPerm permCode="4019"><a class="showLogDialog" param="{'parentType':'SCENIC_LOG_PLACE','parentId':${placeId}}" href="#log">操作日志</a></mis:checkPerm>
   <a href="#" onclick="javascript:deleteMemcached('<s:property  value="@com.lvmama.comm.vo.Constant@HOLIDAY_HOTEL_PLACEID"/>${placeId}')">清除缓存</a>
    </td>
  </tr>
 
</s:iterator>
  <tr><td colspan="8" style="text-align: right;padding-right: 20px;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td></tr>
</table>
</body>
</html>