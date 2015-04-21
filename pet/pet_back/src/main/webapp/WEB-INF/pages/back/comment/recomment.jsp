<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>点评招募管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<link rel="stylesheet" type="text/css" href="${basePath}/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${basePath}/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/themes/mis.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/back_base.css">
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
</head>
<body>
<div><form action="${basePath}/commentManager/cmtRecommentPlaceQuery.do" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="search_table">
			 <tr>
			 	<td>
				 	<input type="button" value="查询" class="button" id="query"/>&nbsp;&nbsp;
				 	<input type="button" value="手动添加" class="button" id="hindleAdd"/>&nbsp;&nbsp;
				 	<input type="button" value="自动添加保存" class="button" id="autoSave"/>
			 	</td>
			 </tr>
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th width="15"><input type="checkbox" id="allChange"/></th>
			<th>ID</th>
			<th>景点ID</th>
			<th>动作</th>
		</tr>
		<s:iterator value="placeList" var="place">
		<tr>
			<td width="15"><input type="checkbox" value="<s:property value="cmtRecommendPlaceId"/>" name="cmtRecommendPlaceId"/></td>
			<td><s:property value="cmtRecommendPlaceId"/></td>
			<td><input type="text" name="placeId" value="<s:property value="placeId"/>" disabled="disabled"/></td>
			<td recommend_index="<s:property value="cmtRecommendPlaceId"/>"><s:property value="zhAction"/></td>
		</tr>	
		</s:iterator>
	</table>
</div>
<script type="text/javascript" charset="utf-8">
	$(function(){
		$("#allChange").click(function(){
			$(":input:checkbox").attr("checked",$(this).attr("checked"));
			$(":input:text[name='placeId']").attr("disabled",!$(this).attr("checked"));
		});
		$(":input:checkbox[name='cmtRecommendPlaceId']").click(function(){
			$(this).parent().parent().find(":input:text[name='placeId']").attr("disabled",!$(this).attr("checked"));
		});
		$("#query").click(function(){
			document.forms[0].submit();
		});
		$("#hindleAdd").click(function(){
			var recommendArrayStr = "";
			var recomendArray = new Array();
			$(":input:checkbox:checked[name='cmtRecommendPlaceId']").each(function(){
				recommendArrayStr+=$(this).val()+"="+$(this).parent().parent().find(":input:text[name='placeId']").val()+",";
				recomendArray+=$(this).val()+",";
			});
			if(recommendArrayStr.length<1){
				alert("请选择要修改的招募景点ID");
			}else{
				recommendArrayStr = recommendArrayStr.substring(0,recommendArrayStr.length-1);
			}
			$.ajax( {
				url : "${basePath}/commentManager/recommendSaveMu.do",
				data:{"placeArray":recommendArrayStr},
				type: "POST",
				success : function(data) {
						if(data.success){
							$(recomendArray.split(",")).each(function(){
								$("td[recommend_index='"+this+"']").text("手动");
							});
							alert("修改成功");
						}else{
							alert("景点编号："+data.errorText+" 不存在");
						}
					}
				});
		});
		$("#autoSave").click(function(){
			if (confirm("请确认此操作")){
				document.forms[0].action="${basePath}/commentManager/recommendSaveAu.do";
				document.forms[0].submit();
			}
		});
	});
</script>
</body>
</html>