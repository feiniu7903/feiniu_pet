<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath()%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>配置项查询</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/validate.jsp"/>
<script type="text/javascript" src="${basePath}/js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
<script type="text/javascript" src="${basePath}/js/base/city.js"></script>

<script type="text/javascript">
function addElement(name){
	return "<td><input type=\"text\" name=\""+name+"\"/></td>";
}
$(function(){
	$("#queryConfig").click(function(){
		$("#form1").submit();
	});
	$(window.document).ready(function(){
		$("select[name=tempSelectConfig1]").val('<s:property value="config1"/>');
	});
	$("select[name=tempSelectConfig1]").change(function(){
		$("#parameter_config1").val($(this).val());
	});
	$("#config1_gw,#config1,#config1_filiale").live("change",function(){
		var option_val=$(this).val();
		$(this).parent().parent().find(":input[name=config1Value]").val(option_val);
	});
	$("#newConfig").click(function(){
		var code_html=$($("#resultstable").find("tr").get(1));
		$($("#resultstable").find("tr").get(1)).before("<tr>"+code_html.html()+"</tr>");
		code_html=$($("#resultstable").find("tr").get(1));
		code_html.find("select,:input").each(function(){$(this).attr("disabled","");});
		code_html.find(":checkbox").attr("checked","checked");
		code_html.find(":input[name=subjectConfigId]").val("");
	});
	$("#allconfig").click(function(){
		$(":input:checkbox").attr("checked",$(this).attr("checked"));
		$("#resultstable").find(":input,select").not(":checkbox").attr("disabled",!$(this).attr("checked"));
	});
	$(":checkbox[name=config]").live("click",function(){
		$(this).parent().parent().find(":input,select").not(":checkbox").attr("disabled",!$(this).attr("checked"));
	});
	$(":button[name=update]").live("click",function(){
		var parent_element = $(this).parent().parent();
		$.ajax({
			   type: "POST",
			   url: "${basePath}/recon/saveFinGLSubjectConfig.do",
			   data: "finGLSubjectConfig.subjectConfigId="+parent_element.find(":input[name=subjectConfigId]").val()+
			   "&finGLSubjectConfig.config1="+parent_element.find(":input[name=config1Value]").val()+
			   "&finGLSubjectConfig.config2="+parent_element.find("select[name=config2]").val()+
			   "&finGLSubjectConfig.config3="+parent_element.find("select[name=config3]").val()+
			   "&finGLSubjectConfig.config4="+parent_element.find("select[name=config4]").val()+
			   "&finGLSubjectConfig.config5="+parent_element.find("select[name=config5]").val()+
			   "&finGLSubjectConfig.subjectCode="+parent_element.find(":input[name=subjectCode]").val()+
			   "&finGLSubjectConfig.subjectType="+parent_element.find("select[name=subjectType]").val(),
			   success: function(msg){
				   msg = eval("("+msg+")");
				   if(msg.success){
				     alert("保存成功");
				     $("#form1").submit();
				   }else{
					   alert("保存失败");
				   }
			   }
			});
	});
	$(":button[name=delete]").live("click",function(){
		var parent_element = $(this).parent().parent();
		$.ajax({
			   type: "POST",
			   url: "${basePath}/recon/deleteFinGLSubjectConfig.do",
			   data: "finGLSubjectConfig.subjectConfigId="+parent_element.find(":input[name=subjectConfigId]").val()+
			   "&finGLSubjectConfig.config1="+parent_element.find(":input[name=config1Value]").val()+
			   "&finGLSubjectConfig.config2="+parent_element.find("select[name=config2]").val()+
			   "&finGLSubjectConfig.config3="+parent_element.find("select[name=config3]").val()+
			   "&finGLSubjectConfig.config4="+parent_element.find("select[name=config4]").val()+
			   "&finGLSubjectConfig.config5="+parent_element.find("select[name=config5]").val()+
			   "&finGLSubjectConfig.subjectCode="+parent_element.find(":input[name=subjectCode]").val()+
			   "&finGLSubjectConfig.subjectType="+parent_element.find("select[name=subjectType]").val(),
			   success: function(msg){
				   msg = eval("("+msg+")");
				   if(msg.success){
					     alert("删除成功");
					     $("#form1").submit();
					   }else{
						   alert("删除失败");
					   }
			   }
			});
	});
})
</script>
</head>
<body>
<div><form id="form1" action="${basePath}/recon/queryFinGLSubjectConfig.do" method="post">
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table">
		<tr>
			<th>产品类型/对账网关</th>
			<th>产品子类型</th>
			<th>科目CODE</th>
			<th>科目类别</th>
		</tr>
		<tr>    
			<td>
				<s:select list="productTypes" name="tempSelectConfig1"   headerValue="请选择" headerKey="" listKey="code" listValue="cnName" /><br/>
				<s:select list="reconGwTypes" name="tempSelectConfig1"   headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/><br/>
				<s:select list="filialeNames" name="tempSelectConfig1"   headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/><br/>
				<input type="text" name="config1" value="<s:property value="config1"/>"  id="parameter_config1"/>
			</td>
			<td>	
				<s:select list="subProductTypes" name="config2" id="config2" headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td>	
				<input type="text" name="subjectCode" id="subjectCode" value="<s:property value="subjectCode"/>"/>
			</td>
			<td> 	
				<select name="subjectType" style="height: 22px;width: 185px;" id="subjectType">
					<option value="">请选择</option>
					<s:iterator var="obj" value="subjectTypes">
						<option value="${obj.code}" <s:if test="#obj.code==subjectType">selected="selected"</s:if>>${obj.cnName}</option>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="button" value="  查 询    " class="button" id="queryConfig" name="queryConfig"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="  新增    " class="button" id="newConfig" name="newConfig"/>
			</td>
		</tr> 
	</table>
</form>
</div>
<div>
	<table border="0" cellspacing="0" cellpadding="0" class="gl_table"   id="resultstable">
		<tr>
			<th width="10"><input type="checkbox" name="allconfig" id="allconfig"/></th>
			<th align="center">产品类型/对账网关</th>
			<th align="center">产品子类型</th>
			<th align="center">是否实体票</th>
			<th align="center">是否境外</th>
			<th align="center">区域</th>
			<th align="center">科目CODE</th>
			<th align="center">科目类别</th>
			<th align="center">操作</th>
		</tr>
		<s:iterator value="pagination.items" var="obj">			
		<tr>
			<td><input type="checkbox" name="config" id="config"/><input type="hidden" name="subjectConfigId" value="${obj.subjectConfigId}"/></td>
			<td><s:select list="productTypes" name="config1"  id="config1" disabled="true" headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/><br/>
			<s:select list="reconGwTypes" name="config1"  id="config1_gw" disabled="true" headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/><br/>
			<s:select list="filialeNames" name="config1"  id="config1_filiale" disabled="true" headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/><br/>
			<input type="text" name="config1Value" value="${obj.config1}" disabled="true"/></td>
			<td><s:select list="subProductTypes" name="config2"  id="config2" disabled="true" headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/></td>
			<td><s:select id="config3" name="config3" list="#{'':'请选择','true':'有','false':'无'}" disabled="true"/></td>
			<td><s:select id="config4" name="config4" list="#{'':'请选择','Y':'是','N':'否'}" disabled="true"/></td>
			<td><s:select list="regionNames" name="config5"  id="config5" disabled="true" headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/></td>
			<td><input type="text" name="subjectCode" value="${obj.subjectCode}" disabled="true"/></td>
			<td><s:select list="subjectTypes" name="subjectType"  id="subjectType" disabled="true" listKey="code" listValue="cnName"/></td>
			<td><input type="button" name="update" value="保存"  disabled="true"/>  <input type="button" name="delete" value="删除"  disabled="true"/></td>				
		</tr>	
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="pagination.totalResultSize"/>
			</td>
			<td colspan="16" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></td>
		</tr>
	</table>
</div>
</body>
</html>