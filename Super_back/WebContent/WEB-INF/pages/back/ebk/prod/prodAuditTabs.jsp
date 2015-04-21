<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="supplierDetail" url="http://super.lvmama.com/pet_back/sup/detail.do"></div>
<ul class="ebk_tab" >

	<s:if test='%{#request.tabName=="CHECK_PENDING"}'>
		<li class="current" >待审核(${statusCountMap['PENDING_AUDIT'] })</li>
	</s:if>
	<s:else>
		<li ><a href="${basePath}/ebooking/prod/prodApprovalAuditList.do">待审核(${statusCountMap['PENDING_AUDIT'] })</a></li>
	</s:else>
	
	
	<s:if test="#request.tabName=='CHECKED'">
		<li class="current" >已审核(${statusCountMap['THROUGH_AUDIT']+statusCountMap['REJECTED_AUDIT']})</li>
	</s:if>
	<s:else>
		<li ><a href="${basePath}/ebooking/prod/prodAuditedList.do">已审核(${statusCountMap['THROUGH_AUDIT']+statusCountMap['REJECTED_AUDIT']})</a></li>
	</s:else>
	
	<s:if test="#request.tabName=='ALL'">
		<li class="current" >所有申请(${statusCountMap['ALL'] })</li>
	</s:if>
	<s:else>
		<li ><a href="${basePath}/ebooking/prod/prodAuditList.do">所有申请(${statusCountMap['ALL'] })</a></li>
	</s:else>

</ul>
