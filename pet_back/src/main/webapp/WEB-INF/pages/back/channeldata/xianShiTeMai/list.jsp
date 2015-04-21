<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>限时特卖后台管理页面</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
		<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
		<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/base/log.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
	</head>
	<body>
		<div id="popDiv" style="display: none"></div>
		<div class="ui_title">
			<ul class="ui_tab">
				<li class="active"><a href="#">限时特卖后台管理页面</a></li>
			</ul>
		</div>
		<div class="p_box">
			<form action="<%=basePath%>/channeldata/xianShiTeMai/list.do" method="post">
				<table class="p_table form-inline" width="100%">
					<tr>
						<td class="p_label">国家</td>
						<td>
							<select name="remark"/>
								<option value="">所有</option>	
								<option value = "1" <s:if test='remark == "1"'>selected</s:if> >门票</option>
								<option value = "2" <s:if test='remark == "2"'>selected</s:if> >周边</option>
								<option value = "3" <s:if test='remark == "3"'>selected</s:if> >国内</option>
								<option value = "4" <s:if test='remark == "4"'>selected</s:if> >出境</option>
							</select>
						</td>	
					</tr>			
				</table>
				<input type="hidden" name="filiale" value="${filiale }"/>
				<p class="tc mt20"><button class="btn btn-small w5" type="submit">查询</button>　</p>
			</form>
		</div>		
		
		<div class="iframe-content">
			<div class="p_box">
			</div>
			<div class="p_box">
				<table class="p_table table_center">
					<tr>
						<th>类型</th>
						<th>产品名称</th>
						<th>产品链接</th>
						<th>规划在线时间</th>
						<th>实际上线时间</th>
						<th>实际下线时间</th>
						<th>折扣率</th>
						<th>排序值</th>
						<th width="325">操作</th>
					</tr>										
					<s:iterator value="recommendInfos" var="info">
						<tr>
							<td>${info.bakWord1 }</td>
							<td>${info.title }</td>
							<td>${info.url }</td>
							<td>${info.bakWord2 }天</td>
							<td>${info.bakWord4 }</td>
							<td>${info.bakWord5 }</td>
							<td>${info.bakWord3 }</td>
							<td>${info.seq }</td>
							<td class="oper">
								<a href="javascript:edit(${info.recommendInfoId })">修改</a>
							</td>
						</tr>
					</s:iterator>
				</table>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		function edit(id) {
			$("#popDiv").load("<%=basePath%>/channeldata/xianShiTeMai/edit.do?recommendInfoId=" + id,function() {
				$(this).dialog({
            		modal:true,
            		title:"编辑",
            		width:600,
            		height:400
                });
            });	
		}
	</script>

</html>
