<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>
		<title>中行分期对账管理</title>
		<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ui/jquery-ui-1.8.5.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
		<script type="text/javascript" src="<%=basePath%>js/base/remoteUrlLoad.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/icon.css">
		<script type="text/javascript" src="<%=basePath%>js/base/form.js"></script>
		<script language="javascript" src="<%=basePath%>js/xiangmu.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
		<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath()%>/js/base/perm.js"></script>
		<script type="text/javascript">
	var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
	$(function() {
		$("input[name='beginTime']" ).datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='endTime']").datepicker({dateFormat:'yy-mm-dd'});
		$("input[name='beginTime']").bind("blur",function(){
			var beginTime = $("input[name='beginTime']").val();
			if(beginTime != ""){
				if(!DATE_FORMAT.test(beginTime)){
					alert("您填写的时期格式不正确!");
					$("input[name='beginTime']").val("");
				}
			}
		})
		$("input[name='endTime']").bind("blur",function(){
			var endTime = $("input[name='endTime']").val();
			if(endTime != ""){
				if(!DATE_FORMAT.test(endTime)){
					alert("您填写的时期格式不正确!");
					$("input[name='endTime']").val("");
				}
			}
		})
	});
	
		</script>
	</head>
	<body>
		<div id="tg_bocRecord">

			<div class="wrap">
				<div class="main2">
					<!--=========================主体内容==============================-->
					<div class="table_box" id=tags_content_1>
						<form name='bocForm' id="bocForm" method='post'
							action='<%=basePath%>boc/bocRecordsQuery.do'>
							<div class="mrtit3">
								<table width="100%" border="0" style="font-size: 12;">
									<tr>										
										<td>
											对账时间：
										</td>
										<td>
											<input name="beginTime" type="text"
												value="<s:date name="beginTime" format="yyyy-MM-dd"/>" />
												~
											<input name="endTime" type="text"
												value="<s:date name="endTime" format="yyyy-MM-dd"/>" />
										</td>
										<td colspan="2" align="center">
											<input type='submit' value="查询" class="right-button08" />
										</td>
									</tr>
								</table>
							</div>
							<table cellspacing="1" cellpadding="4" border="0"
								bgcolor="#666666" width="100%" class="newfont06"
								style="font-size: 12; text-align: center;">
								<tbody>
									<tr bgcolor="#eeeeee">
										<td width="6%">
											对账日期
										</td>
										<td width="6%">
											原始文件
										</td>
										<td width="6%">
											解密文件
										</td>
										<td width="6%">
											下载时间
										</td>
										<td width="6%">
											记录数
										</td>										
										<td width="8%">
											操作
										</td>
									</tr>
									<s:iterator value="pagination.records" var="bocRecord">
											<tr bgcolor="#ffffff">												
												<td>
													<s:date name="#bocRecord.fileDate" format="yyyy-MM-dd"/>
												</td>
												<td>
													${bocRecord.originalFileName}
												</td>												
												<td>
													${bocRecord.decrpytFileName}
												</td>
												<td>
													<s:date name="#bocRecord.downloadTime" format="yyyy-MM-dd HH:mm:ss"/>
												</td>
												<td>
													${bocRecord.recordCount}
												</td>
												<td>
													<a href="<%=basePath%>boc/encryptFileDownload.do?id=${bocRecord.id}&originalFileId=${bocRecord.originalFileId}&originalFileName=${bocRecord.originalFileName}">下载原始文件</a>													
													<a href="<%=basePath%>boc/bocRecordFileExport.do?id=${bocRecord.id}&decrpytFileId=${bocRecord.decrpytFileId}&decrpytFileName=${bocRecord.decrpytFileName}">下载解密文件</a>													
													<a href="javascript:openWin('<%=request.getContextPath()%>/log/viewSuperLog.zul?objectId=${bocRecord.id}&objectType=FIN_RECON_BOC_FILES',700,400)">查看日志</a>
												</td>
											</tr>
									</s:iterator>
								</tbody>
							</table>
						</form>
					</div>
					<!--=========================主体内容 end==============================-->
				</div>
				<!--main2 end-->
			</div>

				<table width="90%" border="0" align="center">
					<s:include value="/WEB-INF/pages/back/base/pag.jsp" />
				</table>	
		</div>
	</body>
</html>
