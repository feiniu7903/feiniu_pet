<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>产品出团日期列表</title>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/affix_upload.js"></script>
		
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/lvmama_common.js"></script>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" />
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/themes/cc.css" />
		
		
		<script type="text/javascript">
			$(function(){
				$("#search_form").submit(function(){
					if($.trim($("input[name=endVisit]").val())!='')	{
						if($.trim($("input[name=startVisit]").val())==''){
							alert("输入了结束时间一定要输入 开始时间");
							return false;
						}
					}
				});
			})
		</script>
		
	</head>
	<body>
		<div  class="table_box">
			<div class="mrtit3" style="padding:10px;">
			<form id="search_form" method="post">
				<table>
					<tr>
						<td>产品ID：</td><td><input type="text" name="productId" value="<s:property value="productId"/>"/></td>
						<td>产品名称：</td><td>
							<input type="text" name="productName" value="<s:property value="productName"/>"/>
						</td>	
						<td>团号：</td>
						<td>
							<input type="text" name="travelGroupCode" value="<s:property value="travelGroupCode"/>"/>
						</td>		
						<td>出团日期：</td><td><input type="text" name="startVisit" class="date" value="<s:date name="startVisit" format="yyyy-MM-dd"/>" style="width:100px;"/>到<input type="text" name="endVisit" value="<s:date name="endVisit" format="yyyy-MM-dd"/>" class="date" style="width:100px;"/></td>					
						<td><input type="submit" value="查询" class="right-button08"/></td>
					</tr>
				</table>
			</form>
			</div>
		
		<table cellspacing="1" cellpadding="4" border="0"
							bgcolor="#666666" width="100%" class="newfont06"
							style="font-size: 12; text-align: center;">
			<tr bgcolor="#eeeeee">
				<td height="35" width="8%">产品ID</td>
				<td width="18%">销售产品</td>				
				<td>开团日期	</td>
			</tr>
			<s:iterator value="pagination.records" var="product">
			<tr bgcolor="#ffffff">
				<td>${product.productId}</td>
				<td>${product.productName}</td>
				<td style="text-align: left;">
					<s:if test="#product.sameProductGroup!=null">
					<s:iterator value="#product.sameProductGroup" var="d">
						<a href="<%=request.getContextPath()%>/op/travelGroupList.do?travelGroupCode=${d.travelGroupCode}&nofirst=true"><s:date name="#d.visitTime" format="MM-dd"/></a>|
					</s:iterator>
					</s:if>
				</td>
			</tr>
			</s:iterator>
		</table>
		<table width="90%" border="0" align="center">
			<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
		</table>
</div>
	<div style="display:none" id="change_initial_div">
		<table width="100%">
			<tr>
				<td class="title"></td>
			</tr>
			<tr>
				<td>计划人数:<input type="text" name="num"/><span class="msg" style="color:red"></span></td>
			</tr>
		</table>
	</div>
	</body>
</html>
