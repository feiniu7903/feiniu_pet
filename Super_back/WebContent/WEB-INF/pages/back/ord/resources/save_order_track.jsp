<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	</head>
	<script type="text/javascript"
			src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
	<script type="text/javascript"
		src="<%=basePath%>js/base/lvmama_dialog.js"></script>
		<script type="text/javascript">
			function chk_value(){
				if("".indexOf($("#ordTrackType").val())>-1){
					alert("请填写处理结果选项!");
					return false;
				}
				return true;
			}
		</script>
	<body>
		<s:form name='frmOrdSms' action='saveOrdTarck.do' theme="simple" namespace="/ord" onsubmit="return chk_value();">
			<input type="hidden" name="trackId" value="${trackId}">
			<input type="hidden" name="orderId" value="${orderId}"/>
			<div class="orderpoptit">
				<strong>跟踪日报</strong>
				<p class="inputbtn">
					<input type="button" name="btnCloseDetailDiv" class="button"
						value="关闭" onclick="javascript: closeDetailDiv('followDiv');" />
				</p>
			</div>
			<div class="orderpopmain">
				<div class="popbox">
						<%@ include file="/WEB-INF/pages/back/ord/resources/tarct_log_list.jsp"%>
					<strong>跟踪日报</strong>
					<table style="font-size: 12px" cellspacing="1" cellpadding="4"
						border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
						<tbody>
							<tr align="center">
								<td bgcolor="#f4f4f4" width="15%">
									处理结果
								</td>
								<td bgcolor="#ffffff" align="left">
									<s:select id="ordTrackType" list="tarckItemList" name="trackType" listKey="code"
										listValue="name" headerKey="" headerValue="请选择" >
									</s:select>
								</td>
							</tr>
							<tr align="center">
								<td height="35" bgcolor="#f4f4f4" width="10%">
									备注
								</td>
								<td bgcolor="#ffffff" align="left">
									<textarea id="ordContent" name="memo" rows="5" cols="80"></textarea>
								</td>
							</tr>
							<tr align="center">
								<td bgcolor="#ffffff" colspan="2" align="center">
									<input type="submit" name="btnOrdSmsSend" class="right-button08" value="提交" />
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<br/>
			</div>
		</s:form>
	</body>
</html>
