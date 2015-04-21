<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>预处理标签近义</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<script type="text/javascript">
$(document).ready(function(){
	$(".J_select-all").change(function(){
	      $(":checkbox").attr("checked", $(".J_select-all").attr("checked"));
	});
	
	$("#addPreTagsRes").click(function(){
		var arrid = "",arrResType="",_resTypeId,_idSel=false,_typeSel=false;
		$(":checkbox[name='chk']").each(function(i,n){
			_resTypeId = $(".userTagsResType").eq(i).val();
			if(this.checked==true){
				_idSel = true;
				if(_resTypeId != 0){
					_typeSel = true;
			    	arrid = arrid + n.value + "|";
			    	arrResType = arrResType + _resTypeId + ",";
				}else if(_idSel && _resTypeId == 0){
					_typeSel = false;
				}
		    }
		});
		if(!_idSel){
			alert("请选择要修改的标签");
			return false;
		}
		if (!_typeSel) {
		    alert("请给选择的标签设置近义类型");
		    return false;
		};
	    if(confirm("你确定对选中的标签进行关联吗？")){
		   $.ajax({
	           type : "POST",
	           url : "${basePath}/tagsRes/addPreTagsRes.do",
	           data : {
	               "tagsIdList" : arrid,
	               "tagsResTypeList" : arrResType
	           },
	           success : function(json) {
	               if(json=="true"){
	                   alert("关联成功!");
	                   window.location.reload(true);
	               }else{
	                   alert("关联失败!");
	               }
	           }
		   });
	    }});
});
</script>    
<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th width="18"></th>
					<th>标签名称1</th>
					<th>标签名称2</th>
					<th>近义类型</th>
				</tr>

            <s:iterator value="relationships" var="bean">
				<tr>
					<td><input type="checkbox" value="${bean.tagsId1},${bean.tagsId2}" name="chk" id="79" /></td>
					<td>${bean.tagsName1}</td>
					<td>${bean.tagsName2}</td>
					<td><s:select cssClass="userTagsResType" list="userTagsResType" name="tagsRes.relationshipType" listKey="elementCode" listValue="elementValue" headerKey="0" headerValue="无"></s:select></td>					
				</tr>
			</s:iterator>
			
				<tr>		
				<td><input class="J_select-all" type="checkbox"></td>
				<td><input id="addPreTagsRes" type="button" class="btn btn-small w5" value="提&nbsp;&nbsp;交"></td>
				<td>总<s:property value="pagination.totalResultSize" />条</td>
				<td><div style="text-align: right;">
					<s:property escape="false"
						value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
					</div>
				</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>