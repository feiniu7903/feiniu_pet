<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<s:include value="/WEB-INF/pages/pub/timepicker.jsp"/>
<link rel="stylesheet" type="text/css" href="<%=basePath %>/css/callCenter/call.css" />
</head>
<body>
<script type="text/javascript">
	function checkSubmit(){
		var feedbackTime = document.getElementById("feedbackTime").value;
		var feedbackTimeEnd = document.getElementById("feedbackTimeEnd").value;
		if(feedbackTime =="" || feedbackTimeEnd == ""){
			alert("请选择创建时间");
			return false;
		}
		return true;
	}
	$(function(){
		$("input.date").datetimepicker({
			showSecond: true,
			timeFormat: 'hh:mm:ss',
			stepHour: 1,
			stepMinute: 5,
			stepSecond: 5,
			showButtonPanel:false
		});
	});
	
</script>
<TABLE style="FONT-SIZE: 12px; margin-bottom: 10px;" border=0 cellSpacing=0 cellPadding=4 class="tab1-cc tab1-cc-3">
	<TBODY>
<form action="<%=basePath%>/call/query.do" method="post" onsubmit="return checkSubmit()">
		<tr class="tab1-cc-tr1">
			<td colspan="4" >创建时间：<input type="text" id="feedbackTime" value="<s:date name="connRecord.feedbackTime" format="yyyy-MM-dd HH:mm:ss"/>" name="connRecord.feedbackTime" class="date">~
			<input type="text" id="feedbackTimeEnd" value="<s:date name="connRecord.feedbackTimeEnd" format="yyyy-MM-dd HH:mm:ss"/>" name="connRecord.feedbackTimeEnd" class="date">&nbsp;&nbsp;&nbsp;</td>
			<td colspan="2" >来电记录：<input type="radio" name="connRecord.callBack" <s:if test='connRecord.callBack=="false"'>checked="checked"</s:if> value="false">去电&nbsp;&nbsp;&nbsp;
				<input type="radio" name="connRecord.callBack" <s:if test='connRecord.callBack=="true"'>checked="checked"</s:if> value="true">来电
			</td>
			<td colspan="3" >
			<center>
				<input type="submit" value="查询">
				<s:if test="connRecordPage.items.size>0">
					<input type="button" onclick="window.open('<s:property value="exportCallbackExcelUrl"/>');" value="导出">
				</s:if>
			</center>
			</td>
		</tr>
</form>
		<TR align=middle class="tr1">
			<TD width="10%" align="left" valign="middle">
				服务类型
			</TD>
			<TD width="10%" align="left" valign="middle">
				服务
			</TD>
			<TD width="10%" align="left" valign="middle">
				产品类型
			</TD>
			<TD width="8%" align="left" valign="middle">
				景区名字
			</TD>
			<TD align="left" valign="middle">
				交流内容
			</TD>
			<TD width="10%" align="left" valign="middle">
				电话号码
			</TD>
			<TD width="8%" align="left" valign="middle">
				交流类型
			</TD>
			<TD width="8%" align="left" valign="middle">
				时间
			</TD>
			<TD width="6%"  align="left" valign="middle">
				操作人
			</TD>
		</TR>
		
		<s:iterator value="connRecordPage.items" status="starus" var="connRecord">
		<TR class="tab1-cc-tr<s:property value="#starus.index%2+1"/>">
			<TD align="left" valign="middle">
				<s:property value="serviceType"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="subServiceType"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="businessType"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="placeName"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="memo"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="mobile"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:if test='hasCallBack()'>来电</s:if><s:else>去电</s:else>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:date name="feedbackTime" format="yyyy-MM-dd HH:mm" />
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="operatorUserId"/>
				<br/>
			</TD>
		</TR>
		</s:iterator>
		<tr>
			<td>
				总条数：<s:property value="connRecordPage.totalResultSize"/>
			</td>
			<td colspan="8" align="right"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(connRecordPage.pageSize,connRecordPage.totalPageNum,connRecordPage.url,connRecordPage.currentPage)"/></td>
		</tr>
	</TBODY>
</TABLE>
</body>
</html>