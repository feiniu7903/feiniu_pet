<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head></head>
	<body>
		<form action="/prod/saveOrUpdateProperty.do" method="post" id="editModelPropertyForm">
		    <input type="hidden" value="<s:property value='productModelProperty.id'/>" name="productModelProperty.id" id="pmp_id" class="newtext1">
			<table width="100%" cellspacing="0" cellpadding="0"
				class="pro_pop_tab">
				<tr>
					<td width="17%" rowspan="2" valign="top">
						名称(必填)
					</td>
					<td width="83%">
						<input type="text" id="p_name" value="<s:property value='productModelProperty.property'/>" name="productModelProperty.property"
							class="newtext1" onkeyup="this.value=this.value.replace(/[^\a-zA-Z\u4E00-\u9FA5\+\（\）]*/g,'')" maxlength="20">
					</td>
				</tr>
				<tr>
					<td>
						<span class="txt_grey">可录入内容：汉字、英文、"+"、"（）" (最长可输入20个字符)</span>
					</td>
				</tr>
				<tr>
					<td rowspan="2" valign="top">
						拼音：
					</td>
					<td>
						<input type="text" value="<s:property value='productModelProperty.pingying'/>" name="productModelProperty.pingying"
							class="newtext1" onkeyup="this.value=this.value.replace(/[^\a-zA-Z\、]*/g,'')">
					</td>
				</tr>
				<tr>
					<td>
						<span class="txt_grey">可录入内容：英文；分隔符"、"</span>
					</td>
				</tr>
				<tr>
					<td rowspan="2" valign="top">
						排序值：
					</td>
					<td>
						<input type="text" value="<s:property value='productModelProperty.orderNum'/>" name="productModelProperty.orderNum"
							class="newtext1" onkeyup="this.value=this.value.replace(/[^0-9\-]*/g,'')">
					</td>
				</tr>
				<tr>
					<td>
						<span class="txt_grey">可录入内容：数字，"-" ；由低到高排列</span>
					</td>
				</tr>
				<tr>
					<td rowspan="2" valign="top">
						叙词:
					</td>
					<td>
						<input type="text" value="<s:property value='productModelProperty.thesaurus'/>" name="productModelProperty.thesaurus"
							class="newtext1" onkeyup="this.value=this.value.replace(/[^\a-zA-Z\u4E00-\u9FA5\、]*/g,'')">
					</td>
				</tr>
				<tr>
					<td>
						<span class="txt_grey">可录入内容：汉字、英文；分隔符"、" </span>
					</td>
				</tr>
				
			</table>
			<table cellspacing="0" cellpadding="0" class="pro_pop_tab1">
				<tr>
					<td width="90">
						所属模块：
					</td>
					<td width="80">
						一级模块：
					</td>
					<td width="112">
					<!--<select class="edit_firstGradeModel" name="productModelProperty.firstModelId" id="p_firstModelId" >
						</select>-->
						<s:select list="firstGradeModelList"  name="productModelProperty.firstModelId" id="p_firstModelId" listKey="code" listValue="name"/>
					</td>
					<td width="80">
						二级模块：
					</td>
					<td width="120">
						<select class="edit_secondGradeModel" name="productModelProperty.secondModelId" id="p_secondModelId" >
						</select>
					</td>
				</tr>
				
				<tr>
					<td valign="top">
						适用产品类型:
					</td>
					<td colspan="4" class="pro_type_td">
				        <s:iterator value="subProductTypeList">
					    	<input type="checkbox" <s:if test='checked'>checked="checked"</s:if> value="<s:property value='code'/>" name="productModelProperty.productType">
							<label class="checkboxLabel"><s:property value="name"/></label>
						</s:iterator>
					</td>
				</tr>
			</table>
			<table width="100%" cellspacing="0" cellpadding="0" border="0"
				class="pro_pop_btntab">
				<td align="center">
					<span class="button opt_btn_re">确定</span>
				</td>
				</tr>
			</table>
	</form>
	 <script type="text/javascript">	
		 	$("#p_firstModelId").live("change", function(){
		        updateSecondGradeModel2($(this).val());
		    });
	 	
			function updateSecondGradeModel2(value) {
				$('.edit_secondGradeModel').empty();
			  	<s:iterator value="modelTypeList">
				  <s:if test="parentId!=null">
				  if(value=="<s:property value='parentId'/>") {
					  $('.edit_secondGradeModel').append("<option value='<s:property value='id'/>' <s:if test='id==productModelProperty.secondModelId'>selected='selected'</s:if>><s:property value='modelName'/></option>");			  
				  }
				  </s:if>
			   	</s:iterator>
		  	}
			
	       //$("select[name=productModelProperty.firstModelId]").trigger("change");
	        
	        $(function(){
	        	var id=$("#pmp_id").val();
	        	var firstValue=$("#p_firstModelId").val();
	        	if(id!=null&&id!=""){
	        		updateSecondGradeModel2(firstValue);
	        		$("#p_name").attr("disabled","disabled");
	        		$("#p_firstModelId").attr("disabled","disabled");
	        		$("#p_secondModelId").attr("disabled","disabled");
	        	}else{
	        		updateSecondGradeModel2(firstValue);
	        	}
	        })
    </script>
	</body>
</html>