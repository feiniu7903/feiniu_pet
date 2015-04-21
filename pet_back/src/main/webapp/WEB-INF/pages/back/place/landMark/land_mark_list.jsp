<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()+"/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script src="${basePath}/js/place/place.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>

<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/place/jquery.autocomplate.js"></script>

<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>

<script type="text/javascript">
$(function () {
    $("#auto_place").autocomplete("autocomplate.do", {dataType: "xml", width: "auto"});
});
function checkBaseForm(){
	$("#parentPlaceId").val($("#placeParentPlaceId").val());
	$("#form1").submit();
	return true;
}
</script>
<script type="text/javascript" src="${basePath}/js/place/houtai.js"></script>

</head>
	<body>
		<div id="popDiv" style="display: none"></div>
		<div class="iframe-content">
			<div class="p_box">
				<form action="${basePath}/place/placeLandMarkList.do" method="post" id="form1">
				
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">地标名称:</td>
							<td>
								<s:textfield name="placeLandMark.landMarkName" />	
							</td> 
							
							<td class="p_label">上级目的地：</td>
							<td>
								<input class="input_b" id="auto_place" name="parentPlaceName" value="<s:property value="parentPlaceName"/>">
								<input type="hidden" id="placeParentPlaceId" name="place.parentPlaceId" value="<s:property value="place.parentPlaceId"/>"/>
								<input type="hidden" name="parentPlaceId" id="parentPlaceId" value="<s:property value='parentPlaceId'/>"/>
							</td>
						</tr>
					</table>
					<p class="tc mt20">
    					<input type="button" class="btn btn-small w5" id="placeLandMark_add" value="新增地标"/>
						<button class="btn btn-small w5" type="submit" onclick="return checkBaseForm()">查询</button>
					</p>
				</form>		
			</div>
			
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th width="3%"><input type="checkbox" name="chk_all" id="chk_all"/></th>
	    				<th>ID</th>
					    <th>地标名称</th>
					    <th>上级目的地</th>
					    <th>拼音</th>
					    <th>经度</th>
					    <th>纬度</th>
					    <th>操作</th>
					</tr>
					<s:iterator value="placeLandMarkList">
						<tr>
							<td><input type="checkbox" name="chk_list"  value="${placeLandMarkId}"/></td>
	    					<td><s:property value="placeLandMarkId"/></td>
						    <td><s:property value="landMarkName"/></td>
						    <td><s:property value="parentPlaceName"/></td>
						    <td><s:property value="pinYin"/></td>
						    <td><s:property value="longitude"/></td>
						    <td><s:property value="latitude"/></td>
						    <td class="gl_cz">
						    	<a href="javascript:placeLandMarkEdit(${placeLandMarkId});">地标属性</a>
						    	<s:if test='isValid == "Y"'><a href="javascript:placeLandMarkSetValid(${placeLandMarkId},'N');">设无效</a></s:if>
						    	<s:if test='isValid == "N"'><a href="javascript:placeLandMarkSetValid(${placeLandMarkId},'Y');">设有效</a></s:if>
						    </td>
					    </tr>
					</s:iterator>
					<tr>
     					<td colspan="3" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="6"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
    				</tr>
				</table>
			</div>
		</div>
	</body>

	<script type="text/javascript">
		$(function(){
			$("#placeLandMark_add").click(function(){
				 $("#popDiv").load("${basePath}place/placeLandMarkEdit.do",function() {
						$(this).dialog({
		            		modal:true,
		            		title:"新增地标",
		            		width:550,
		            		height:550
		                });
		            });		
			});			
		});
		
		function placeLandMarkEdit(placeLandMarkId) {
			$("#popDiv").load("${basePath}/place/placeLandMarkEdit.do?placeLandMarkId=" + placeLandMarkId, function() {
				$(this).dialog({
		        	modal:true,
		            title:"编辑地标",
		            width:550,
		            height:550
		        });
		     });							
		} 
		
		function placeLandMarkSetValid(placeLandMarkId,valid){
			$.ajax({
				url:"${basePath}/place/doPlaceLandMarkSetValid.do",
				data:{"placeLandMarkId":placeLandMarkId,"valid":valid},
				dataType:"",
				type : "POST",
				success:function(data){
					if(data== "success") {
						alert("操作成功!"); 
						window.location.reload();
					} else { 
						alert("操作失败，请稍后再试!"); 
					} 
				}
			});
		};
	</script>
</html>
