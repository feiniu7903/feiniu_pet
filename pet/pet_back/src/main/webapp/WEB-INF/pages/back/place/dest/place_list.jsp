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
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>

<script type="text/javascript">
function checkBaseForm(){
	var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
	if($("input[name='place.placeId']").val()!=""){
		if(!reg.test($("input[name='place.placeId']").val())){
			alert("请输入正确的城市ID（数字）!");
			$("input[name='place.placeId']").focus();
			return false;
		}
	}
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
				<form action="${basePath}/place/placeList.do" method="post" id="form1">
				<input type="hidden" name="stage" id="stage" value="<s:property value='stage'/>"/>
					<table class="p_table form-inline" width="100%">
						<tr>
							<td class="p_label">城市ID：</td>
							<td>
								<s:textfield name="place.placeId"/>	
							</td>
							<td class="p_label">城市名称:</td>
							<td>
								<s:textfield name="place.name" />	
							</td>
							<td class="p_label">城市状态:</td>
							<td>
								<s:select list="isValidList"  name="place.isValid" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue">
	</s:select>
							</td>
						</tr>
						<tr>
							<td class="p_label">使用模板：</td>
							<td>
								<s:select list="templateList"  name="place.template" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue">
	</s:select> 
							</td>
							<td class="p_label">目的地类型:</td>
							<td>
								<s:select list="placeTypeList"  name="place.placeType" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue">
	</s:select>
							</td>
							<td class="p_label">国内/国外:</td>
							<td>
								<s:select list="isExitList"  name="place.isExit" headerKey="" headerValue="全部" theme="simple" listKey="elementCode" listValue="elementValue">
								</s:select>
							</td>
						</tr>						
					</table>
					<p class="tc mt20">
						<mis:checkPerm permCode="4000"><input type="button" class="btn btn-small w5" id="saveseq" value="保存排序"/></mis:checkPerm>
    					<mis:checkPerm permCode="4001"><input type="button" class="btn btn-small w5" id="place_add" value="新增目的地"/></mis:checkPerm>
						<button class="btn btn-small w5" type="submit" onclick="return checkBaseForm()">查询</button>　
					</p>
				</form>		
			</div>
			
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th width="3%"><input type="checkbox" name="chk_all" id="chk_all"/></th>
	    				<th>ID</th>
					    <th>名称</th>
					    <th>状态</th>
					    <th>使用模板</th>
					    <th>国内/国外</th>
					    <th>创建时间</th>
                        <th>类型</th>
					    <th>排序值</th>
					    <th>操作</th>
					</tr>
					<s:iterator value="placeList">
						<tr>
							<td><input type="checkbox" name="chk_list"  value="${placeId }"/></td>
	    					<td><s:property value="placeId"/></td>
						    <td><a href="http://www.lvmama.com/dest/<s:property value="pinYinUrl"/>" target="_blank"><s:property value="name"/></a></td>
						    <td><s:if test='isValid=="Y"'><font color=green>有效</font></s:if><s:else><font color=red>无效</font></s:else></td>
						    <td><s:if test='template=="template_zhongguo"'>国内</s:if><s:elseif test='template=="template_abroad"'>国外</s:elseif><s:else>无</s:else></td>
						   	<td><s:if test='isExit=="template_zhongguo"'>国内</s:if><s:elseif test='isExit=="template_abroad"'>国外</s:elseif><s:else>无</s:else></td>
						    <td><s:date name="createTime" format="yyyy-MM-dd"/></td>
                            <td><s:property value="placeTypeCn" /></td>
						    <td><input type="text"  class="seq_check" id="chk_list_${placeId }" value="${seq }"/></td>
						    <td class="gl_cz">
						    	<s:if test='placeType!="ISLAND"'> <mis:checkPerm permCode="4002"><a href="javascript:placeEdit(${placeId });">目的地属性</a></mis:checkPerm></s:if>	
						    	<s:if test='placeType!="ISLAND"'> <mis:checkPerm permCode="4003"><a href="${basePath}/seo/recommendBlock.do?placeId=${placeId }&pageChannel=dest&checkBlock=true">推荐管理</a></mis:checkPerm> </s:if>  
						    	<s:if test='placeType!="ISLAND"'> <mis:checkPerm permCode="4019"><a class="showLogDialog" param="{'parentType':'SCENIC_LOG_PLACE','parentId':${placeId}}" href="#log">操作日志</a></mis:checkPerm> </s:if>  
						    </td>
					    </tr>
					</s:iterator>
					<tr>
     					<td colspan="3" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
						<td colspan="7"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
    				</tr>
				</table>    
			</div>
		</div>
	</body>

	<script type="text/javascript">
		$(function(){
			$("#place_add").click(function(){
				 $("#popDiv").load("${basePath}place/placeAdd.do?stage=1",function() {
						$(this).dialog({
		            		modal:true,
		            		title:"新增目的地",
		            		width:300,
		            		height:150
		                });
		            });		
			});			
		});
		
		function placeEdit(placeId) {
			$("#popDiv").load("${basePath}/place/placeEdit.do?placeId=" + placeId, function() {
				$(this).dialog({
		        	modal:true,
		            title:"编辑目的地",
		            width:700,
		            height:550
		        });
		     });							
		} 
		
	</script>

		
</html>