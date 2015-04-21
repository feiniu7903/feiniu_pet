<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>标签近义管理</title>
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
	
	$("#searchMageTagsRes").click(function(){
	  	$("#form1").submit();
	});
	
	$("#delMageTagsRes").click(function(){
		var _arrid = "";
		$(":checkbox:[name=chk]").each(function(i,n){
			if(this.checked==true){
				_arrid = _arrid + n.value + ",";
		    }
		});
		if(_arrid == ""){
			alert("请选择要取消的标签");
			return false;
		}
		if(confirm("确定取消该近义词标签关联吗？")){
			   $.ajax({
		           type : "POST",
		           url : "${basePath}/tagsRes/delMageTagsRes.do",
		           data : {
		               "tagsIdList" : _arrid 
		           },
		           success : function(json) {
		               if(json=="true"){
		                   alert("取消成功!");
		                   window.location.reload(true);
		               }else{
		                   alert("取消失败!");
		               }
		           }
			   });
		}});
	});
	
	$("#addPreTagsRes").click(function(){
		var arrid = "",arrResType="",_resTypeId,_idSel=false,_typeSel=false;
		$(":checkbox").each(function(i,n){
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
	    }
});
	
	function doUpdate(tagsResId){
		var url = '${basePath}tagsRes/preUpdTagsRes.do?tagsRes.relationshipId='+tagsResId;
	    $("#popDiv").load(url,function() {
	           $(this).dialog({
	               modal:true,
	               title:"近义词标签关联编辑",
	               width:600,
	               height:350
	           });
	     }); 
	}
</script>    
<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div class="p_box">
        <form action="${basePath}tagsRes/searchMageTagsRes.do" method="post" id="form1">
            <table class="p_table form-inline" width="80%">
                <tr class="p_label">
                	<td>标签：<input id="search_tagsName" name="search_tagsName" value="${search_tagsName}" ></td>
                	<td>近义类型：<s:select  cssClass="userTagsResType" list="userTagsResType" name="tagsRes.relationshipType" listKey="elementCode" listValue="elementValue"></s:select></td>
					<td><input type="button" class="btn btn-small w5" id="searchMageTagsRes" value="查&nbsp;&nbsp;询"/></td>
				</tr>
            </table>
        </form>
        </div>
		
		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th width="18"></th>
					<th>标签名称1</th>
					<th>标签名称2</th>
					<th>近义类型</th>
					<th>操作</th>
				</tr>

            <s:iterator value="relationships" var="tagsResList">
				<tr>
					<td><input type="checkbox" value="${tagsResList.relationshipId}" name="chk" id="79" /></td>
					<td>${tagsResList.tagsName1}</td>
					<td>${tagsResList.tagsName2}</td>
					<td><s:if test="#tagsResList.relationshipType==1">同义</s:if> 
						<s:elseif test="#tagsResList.relationshipType==2">别名</s:elseif>
						<s:elseif test="#tagsResList.relationshipType==3">错别字</s:elseif>
						<s:elseif test="#tagsResList.relationshipType==4">其他</s:elseif>					
					</td>
					<td class="gl_cz">
					    <a href="javascript:doUpdate('${tagsResList.relationshipId}')">编辑</a>
					</td>
				</tr>
			</s:iterator>
			
				<tr>		
				<td><input class="J_select-all" type="checkbox"></td>
				<td><input id="delMageTagsRes" type="button" class="btn btn-small w5" value="取&nbsp;&nbsp;消"></td>
				<td align="right">总<s:property value="pagination.totalResultSize" />条</td>
				<td colspan="2"><div style="text-align: right;">
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