<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查询所有用户标签</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/base/typeFirstType.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
    <link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<body>
	<div id="popDiv" style="display: none"></div>
	<div class="iframe-content">
		<div class="p_box">
		<form action="" method="post" id="form2"></form>
		<form action="${basePath}tags/search.do" method="post" id="form1">
			<table class="p_table form-inline" width="80%">
				<tr class="p_label">
					 <td class="p_label">一级类别:</td>
                    <td>
                        <s:select id="typeFirstType" list="userTagsFirstType" name="userTagsType.typeFirstType" listKey="elementValue" listValue="elementValue" headerKey="" headerValue="--请选择--"></s:select>
                    </td>
					<td class="p_label">二级类别：</td>
                    <td><select id="typeSecondType" name="userTagsType.typeSecondType"></select></td>
					 <td class="p_label">标签名称：</td>
					 <td><input id="tagsName" type="text" name="tags.tagsName" /></td>
					 <td class="p_label">状态:</td>
					 <td>
					  <input type="radio" name="tags.tagsStatus" value="1" checked="checked"/>可用 &nbsp;&nbsp;
                      <input type="radio" name="tags.tagsStatus" value="2"/>不可用
					 </td>
					 <td>
					  <input type="submit" class="btn btn-small w5" id="doSearchUserTags" value="查&nbsp;&nbsp;询" onclick="return doSearchAndCheck();"/>
					 </td>
				</tr>
				</table>
		  </form>
		</div>

		<div id="tagListTable" class="p_box">
			<table class="p_table table_center">
				<tr>
					<th>编号</th>
					<th>标签名称</th>
					<th>一级类别</th>
					<th>二级类别</th>
					<th>状态</th>
					<th>操作</th>
				</tr>

     <s:if test="userTagsList.size()>0">
            <s:iterator value="userTagsList" var="userTags">
				<!-- 内容部分 -->
				<tr>
					<td>${userTags.tagsId}</td>
					<td>${userTags.tagsName}</td>
					<td>${userTags.typeFirstType}</td>
					<td>${userTags.typeSecondType}</td>
					<td><s:if test="#userTags.tagsStatus==1">可用</s:if><s:elseif test="#userTags.tagsStatus==2">不可用</s:elseif><s:else>无</s:else></td>
					<td class="gl_cz">
					    <s:if test="#userTags.tagsStatus==1">
					       <a href="javascript:doRelations('${userTags.tagsId}','${userTags.tagsName}');">近义</a>
					    </s:if><s:elseif test="#userTags.tagsStatus==2">
					       <a >近义</a>
					    </s:elseif><s:else></s:else>
						<a href="javascript:doUpdate('${userTags.tagsId}');">修改</a>
					</td>
				</tr>
				<!-- 内容部分 -->
			</s:iterator>
	</s:if>			
				<tr>
				<td colspan="2" align="right">总<s:property value="pagination.totalResultSize" />条</td>
					<td colspan="5"><div style="text-align: right;">
							<s:property escape="false"
								value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)" />
						</div></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
changeFirstTypeOnloadSecondType();

function doSearchAndCheck(){
	var secondType = $("#typeSecondType").val();
	var firstType= $("#typeFirstType").val();
	if(!""== firstType && null == secondType){
        alert("二级类别为空-请选择要查询的一级类别");
        return false;
    }
	
	var options = { 
       url:"${basePath}tags/search.do",
       dataType:"",
       type : "POST"
   };
   $('#form1').ajaxSubmit(options);
}

function doUpdate(tagsId){
    $("#popDiv").load("${basePath}tags/goUpdate.do?tags.tagsId="+tagsId,function() {
           $(this).dialog({
               modal:true,
               title:"编辑用户标签",
               width:600,
               height:350
           });
     }); 
}

function doRelations(tagsId,tagsName){
	$("#form2").attr("action","${basePath}tagsRes/searchTagsRes.do?tags.tagsId="+tagsId);
	$("#form2").submit();
}
</script>
</html>