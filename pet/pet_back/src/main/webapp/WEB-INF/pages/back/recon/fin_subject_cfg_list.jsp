<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>科目配置管理</title>
<script type="text/javascript">  var basePath='${basePath}';  </script>
<script type="text/javascript" src="${basePath}/js/base/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery-ui-1.8.5.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/back_base.css" />
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css"  href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css"  href="${basePath}css/panel-content.css"></link>
</head>
<body>
    <div class="iframe-content">
        <div class="p_box">
            <form method="post" id="fin-subject-cfg-search-form"  action="${basePath}/recon/queryFinGlSubjectCfg.do">
           	 <input type="hidden" name="moreTerm"  id="moreTerm" value="${moreTerm }"/>
                <table class="p_table form-inline" width="80%">
                <tr>
                    <td class="p_label">做账类别:</td>
                    <td colspan="7">
                   	 	<s:select list="accountTypes" name="finGLSubjectCfg.accountType"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName" />
                    </td>
               	</tr>
               	
				<tr class="p_label" name="more-tr">
					<td colspan="8" style="background: none repeat scroll 0 0 #E2EAF4;">借方科目配置</td>
				</tr>
				<tr class="p_label" name="more-tr">
					<td>产品类型:</td>
					<td>
						<s:select list="productTypes" name="finGLSubjectCfg.config1"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>产品子类型:</td>
					<td>
						<s:select list="subProductTypes" name="finGLSubjectCfg.config2"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>对账网关:</td>
					<td>
						<s:select list="reconGwTypes" name="finGLSubjectCfg.config6"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>所属公司:</td>
					<td>
						<s:select list="filialeNames" name="finGLSubjectCfg.config7"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
				</tr>
				<tr class="p_label" name="more-tr">
					<td>是否实体票:</td>
					<td>
						<s:select  name="finGLSubjectCfg.config3" list="#{'':'请选择','true':'有','false':'无'}" />
					</td>
					<td>是否境外:</td>
					<td>
						<s:select  name="finGLSubjectCfg.config4" list="#{'':'请选择','Y':'是','N':'否'}" />
					</td>
					<td>区域:</td>
					<td>
						<s:select list="regionNames" name="finGLSubjectCfg.config5"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>科目CODE:</td>
					<td>
						<input type="text" name="finGLSubjectCfg.borrowSubjectCode" value="${finGLSubjectCfg.borrowSubjectCode}" />
					</td>
				</tr>
				<tr class="p_label" name="more-tr">
					<td colspan="8" style="background: none repeat scroll 0 0 #E2EAF4;">贷方科目配置</td>
				</tr>
				<tr class="p_label" name="more-tr">
					<td>产品类型:</td>
					<td>
						<s:select list="productTypes" name="finGLSubjectCfg.lendConfig1"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>产品子类型:</td>
					<td>
						<s:select list="subProductTypes"  name="finGLSubjectCfg.lendConfig2"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>对账网关:</td>
					<td>
						<s:select list="reconGwTypes" name="finGLSubjectCfg.lendConfig6"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>所属公司:</td>
					<td>
						<s:select list="filialeNames" name="finGLSubjectCfg.lendConfig7"  headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
				</tr>
				<tr class="p_label" name="more-tr">
					<td>是否实体票:</td>
					<td>
						<s:select  name="finGLSubjectCfg.lendConfig3" list="#{'':'请选择','true':'有','false':'无'}" />
					</td>
					<td>是否境外:</td>
					<td>
						<s:select  name="finGLSubjectCfg.lendConfig4" list="#{'':'请选择','Y':'是','N':'否'}" />
					</td>
					<td>区域:</td>
					<td>
						<s:select list="regionNames" name="finGLSubjectCfg.lendConfig5"   headerValue="请选择" headerKey="" listKey="code" listValue="cnName"/>
					</td>
					<td>科目CODE:</td>
					<td>
						<input type="text" name="finGLSubjectCfg.lendSubjectCode" value="${finGLSubjectCfg.lendSubjectCode}" />
					</td>
				</tr>
				
                </table>
                <p class="tc mt20">
                    <input type="submit" class="btn btn-small w5" value="查&nbsp;&nbsp;询" id="cfg-form-submit-btn"/>
                    <a class="btn btn-small w2" value="新&nbsp;&nbsp;增" href="javascript:;" onclick="editCfg('');">新增</a>
                </p>
            </form>
        </div>
        <div class="p_box">
            <table class="p_table ">
                <tr>
                    <th width="100">借/贷 &nbsp;&nbsp;&nbsp;做账类别</th>
                    <th>产品类型</th>
                    <th>产品子类型</th>
                    <th>对账网关</th>
                    <th>所属公司</th>
                    <th>是否实体票</th>
                    <th>是否境外</th>
                    <th>区域</th>
                    <th>科目CODE</th>
                    <th>操作</th>
                </tr>
                 <s:iterator value="pagination.items" var="item" status="st">
                        <tr>
	                        <td>
	                           	${item.accountTypeCn }
	                        </td>
	                        <td>
		                        ${item.config1Cn }<hr>
		                        ${item.lendConfig1Cn }
	                        </td>
	                        <td>
	                        	${item.config2Cn }<hr>
	                        	${item.lendConfig2Cn }
	                        </td>
	                         <td>
	                        	${item.config6Cn }<hr>
	                        	${item.lendConfig6Cn }
	                        </td>
	                         <td>
	                        	${item.config7Cn }<hr>
	                        	${item.lendConfig7Cn }
	                        </td>
	                        <td>
	                        	<s:if test="#item.config3=='true'">有</s:if><s:if test="#item.config3=='false'">无</s:if>
	                            	<hr>
	                            <s:if test="#item.lendConfig3=='true'">有</s:if><s:if test="#item.lendConfig3=='false'">无</s:if>
	                        </td>
	                        <td>
	                        	<s:if test='#item.config4=="Y"'>是</s:if><s:if test='#item.config4=="N"'>否</s:if>
	                            	<hr>
	                            <s:if test='#item.lendConfig4=="Y"'>是</s:if><s:if test='#item.lendConfig4=="N"'>否</s:if>
	                        </td>
	                        <td>
	                        	${item.config5Cn }<hr>
	                        	${item.lendConfig5Cn }
	                        </td>
	                        <td>
	                        	${item.borrowSubjectCode }<hr>
	                        	${item.lendSubjectCode }
	                        </td>
	                        <td>
	                        	<a class="btn btn-small w2" href="javascript:;" onclick="editCfg(${item.subjectConfigId});">编辑</a>
	                        	
	                        	<a class="btn btn-small w2" href="javascript:;" onclick="removeCfg(${item.subjectConfigId});">删除</a>
	                        </td>
                        </tr>
                      
                 </s:iterator>
                  <tr>
                    <td colspan="4" align="right">查询结果： <s:property  value="pagination.totalResultSize" /></td>
                    <td colspan="6">  
                        <div style="text-align: right;">
                            <s:property escape="false" value="pagination.pagination" />
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <script type="text/javascript">
    
    //编辑 
    function editCfg(id){
    	var url="${basePath}/recon/finSubjectCfg/edit.do";
    	var _title="编辑";
    	if(id==""){
    		_title="新增";
    	}else{
    		url+="?subjectConfigId="+id;
    	}
		$("<iframe frameborder='0' id='editFinSubjectCfgDetailWin'></iframe>").dialog(
			{
			    autoOpen: true,
			    modal: true,
			    title : _title+"科目配置",
			    position: 'top',
			    width: 720
		    }
		).width(700).height(450).attr("src",url);
    }
    
    //删除
    function removeCfg(id){
    	if(confirm("确定要删除该科目配置吗?")){
   			$.ajax({
   	   		   type: "GET",
   	   		   dataType:"json",
   	   		   url: "${basePath}/recon/finSubjectCfg/remove.do?subjectConfigId="+id,
   	   		   success: function(data){
   	   		    	if(data.success==true){
   	   		    		alert("删除成功");
   	   		    		$("#cfg-form-submit-btn").trigger("click");
   	   		    	}
   	   		   }
   	    	});
    	}
    }
    </script>
</body>
</html>