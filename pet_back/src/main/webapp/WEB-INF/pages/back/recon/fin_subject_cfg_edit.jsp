<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<link rel="stylesheet" type="text/css" href="${basePath}css/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/base/back_base.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css"  href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css"  href="${basePath}css/panel-content.css"></link>
<script type="text/javascript">  var basePath='${basePath}';  </script>
<script type="text/javascript" src="${basePath}js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="${basePath}js/base/dialog.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.form.js"></script>
</head>
<body>
<form id="fin-subjetc-cfg-form" method="post" action="${basePath}recon/finSubjectCfg/save.do">
<input name="finGLSubjectCfg.subjectConfigId" type="hidden" value="${finGLSubjectCfg.subjectConfigId }" />
<table class="p_table form-inline"> 
	<tbody>
		<tr class="p_label">
			<td><label><font color="red">*</font></label>做账类别:</td>
			<td colspan="3">
			<s:select list="accountTypes" name="finGLSubjectCfg.accountType"  id="accountTypeSelect" headerValue="请选择" headerKey="" listKey="code" listValue="cnName" />
			</td>
		</tr>
		<tr class="p_label" >
			<td colspan="4" style="background: none repeat scroll 0 0 #E2EAF4;">借方科目配置</td>
		</tr>
		<tr class="p_label">
			<td>产品类型:</td>
			<td>
				<s:select list="productTypes" name="finGLSubjectCfg.config1"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td>产品子类型:</td>
			<td>
				<s:select list="subProductTypes" name="finGLSubjectCfg.config2"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
		</tr>
		<tr class="p_label">
			<td>对账网关:</td>
			<td>
				<s:select list="reconGwTypes" name="finGLSubjectCfg.config6"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td>所属公司:</td>
			<td>
				<s:select list="filialeNames" name="finGLSubjectCfg.config7"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
		</tr>
		<tr class="p_label">
			<td>是否实体票:</td>
			<td>
				<s:select  name="finGLSubjectCfg.config3" list="#{'':'请选择','true':'有','false':'无'}" />
			</td>
			<td>是否境外:</td>
			<td>
				<s:select  name="finGLSubjectCfg.config4" list="#{'':'请选择','Y':'是','N':'否'}" />
			</td>
		</tr>
		<tr class="p_label">
			<td>区域:</td>
			<td>
				<s:select list="regionNames" name="finGLSubjectCfg.config5"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td><label><font color="red">*</font></label>科目CODE:</td>
			<td>
				<input type="text" name="finGLSubjectCfg.borrowSubjectCode" value="${finGLSubjectCfg.borrowSubjectCode}" />
			</td>
		</tr>
		
		<tr class="p_label">
			<td colspan="4" style="background: none repeat scroll 0 0 #E2EAF4;">贷方科目配置</td>
		</tr>
		<tr class="p_label">
			<td>产品类型:</td>
			<td>
				<s:select list="productTypes" name="finGLSubjectCfg.lendConfig1"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td>产品子类型:</td>
			<td>
				<s:select list="subProductTypes"  name="finGLSubjectCfg.lendConfig2"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
		</tr>
		<tr class="p_label">
			<td>对账网关:</td>
			<td>
				<s:select list="reconGwTypes" name="finGLSubjectCfg.lendConfig6"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td>所属公司:</td>
			<td>
				<s:select list="filialeNames" name="finGLSubjectCfg.lendConfig7"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
		</tr>
		<tr class="p_label">
			<td>是否实体票:</td>
			<td>
				<s:select  name="finGLSubjectCfg.lendConfig3" list="#{'':'请选择','true':'有','false':'无'}" />
			</td>
			<td>是否境外:</td>
			<td>
				<s:select  name="finGLSubjectCfg.lendConfig4" list="#{'':'请选择','Y':'是','N':'否'}" />
			</td>
		</tr>
		<tr class="p_label">
			<td>区域:</td>
			<td>
				<s:select list="regionNames" name="finGLSubjectCfg.lendConfig5"   headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
			</td>
			<td><label><font color="red">*</font></label>科目CODE:</td>
			<td>
				<input type="text" name="finGLSubjectCfg.lendSubjectCode" value="${finGLSubjectCfg.lendSubjectCode}" />
			</td>
		</tr>
	</tbody>
</table>
	<p class="tc mt20">
		<input type="button" value="保存" id="sumbit-form-btn" class="btn btn-small w6" />
	</p>
</form>
<script type="text/javascript">
	$(document).ready(function(){
		
	    $("#fin-subjetc-cfg-form").ajaxForm({ 
	        dataType:'json', 
	        success: function(data){
	        	if(data.success==true){
	        		alert("保存成功");
	        	}
	        }
	    }); 
		
		$("#sumbit-form-btn").click( function () { 
			
			var _form=$("#fin-subjetc-cfg-form");
			
			var accountType=$("#accountTypeSelect").val();
			
			var bSubjectCode=$.trim($(_form).find("input[name='finGLSubjectCfg.borrowSubjectCode']").val());
			
			var lSubjectCode=$.trim($(_form).find("input[name='finGLSubjectCfg.lendSubjectCode']").val());
			
			if(accountType==""){
				alert("请选择做账类别");
				return;
			}
			
			if(bSubjectCode==""){
				alert("请输入借方科目编码");
				return;
			}
			
			if(lSubjectCode==""){
				alert("请输入贷方科目编码");
				return;
			}
			
			$(_form).submit();
			
		});
		
		
		
	});
</script>
</body>
</html>