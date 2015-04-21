<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>奖金提现_驴妈妈旅游网</title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>themes/cc.css" />
	</head>
	<body>
		<div class="main2">
			<div>
				<div class="table_box">
					<strong class="stitle"> 提现记录 <s:if
							test='drawMoneyInfo.status=="NEW"'>
							<span>待审核</span>
						</s:if> <s:if test='drawMoneyInfo.status=="PASS"'>
							<span>审核通过</span>
						</s:if> <s:if test='drawMoneyInfo.status=="CANCEL"'>
							<span>取消</span>
						</s:if> <s:if test='drawMoneyInfo.status=="PAYOUT"'>
							<span>已打款</span>
						</s:if> </strong>
					<table width="100%" border="0" class="newfont06"
								style="font-size: 12; text-align: left;">
						<form action="" method="post" name="frm">
							<input type="hidden" name="detail" id="detail" value="1">
							<input type="hidden" name="id" id="id"
								value="<s:property value="drawMoneyInfo.drawMoneyInfoId" />">
							<input type="hidden" name="status" id="status" value="">
						<tr>
							<td>
								编号
							</td>
							<td>
								<s:property value="drawMoneyInfo.drawMoneyInfoId" />
							</td>
							<td>
								申请金额
							</td>
							<td>
								<s:property value="drawMoneyInfo.amountYuan" />
								元
							</td>
							<td>
								发票
							</td>
							<td>
								<s:if test='drawMoneyInfo.isInvoice == "Y"'>提供</s:if>
								<s:if test='drawMoneyInfo.isInvoice == "N"'>不提供</s:if>
							</td>
						</tr>
						<tr>
							<td>
								税款
							</td>
							<td>
								<s:property value="drawMoneyInfo.taxYuan" />
								元
							</td>
							<td>
								实际可提现金额
							</td>
							<td>
								<s:property value="drawMoneyInfo.cashYuan" />
								元
							</td>
							<td>
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td>
								开户银行
							</td>
							<td>
								<s:property value="drawMoneyInfo.bank" />
							</td>
							<td>
								账户名
							</td>
							<td>
								<s:property value="drawMoneyInfo.bankName" />
							</td>
							<td>
								账户号码
							</td>
							<td>
								<s:property value="drawMoneyInfo.bankNumber" />
							</td>
						</tr>
						<tr>
							<td>
								所属分行
							</td>
							<td>
								<s:property value="drawMoneyInfo.bankBranchName" />
							</td>
							<td>
								联系人姓名
							</td>
							<td>
								<s:property value="drawMoneyInfo.contactName" />
							</td>
							<td>
								联系人手机
							</td>
							<td>
								<s:property value="drawMoneyInfo.contactMobile" />
							</td>
						</tr>
						<tr>
							<td>
								用户备注
							</td>
							<td colspan=5>
								<s:property value="drawMoneyInfo.userRemark" />
							</td>
						</tr>
						<tr>
							<td>
								审核备注
							</td>
							<td colspan=5>
								<textarea name="operatorRemark" id="operatorRemark" cols="50" rows="10"><s:property value="drawMoneyInfo.operatorRemark" /></textarea>
							</td>
						</tr>
						<tr>
							<s:if test='drawMoneyInfo.status=="NEW"'>
								<td colspan=2>
									<a onclick="javascript:return updateStatus('PASS')" href="#">审核通过</a>
								</td>
								<td colspan=2>
									<a onclick="javascript:return updateStatus('CANCEL')" href="#">取消</a>
								</td>
								<td colspan=2>
									<a onclick="javascript:return updateStatus('EDIT')" href="#">修改备注</a>
								</td>
							</s:if>
							<s:if test='drawMoneyInfo.status=="PASS"'>
								<td colspan=2>
									<a onclick="javascript:return updateStatus('PAYOUT')" href="#">已打款</a>
								</td>
								<td colspan=2>
									<a onclick="javascript:return updateStatus('CANCEL')" href="#">取消</a>
								</td>
								<td colspan=2>
									<a onclick="javascript:return updateStatus('EDIT')" href="#">修改备注</a>
								</td>
							</s:if>
							<s:if test='drawMoneyInfo.status=="CANCEL"'>
								<td colspan=6>
									<a onclick="javascript:return updateStatus('EDIT')" href="#">修改备注</a>
								</td>
							</s:if>
							<s:if test='drawMoneyInfo.status=="PAYOUT"'>
								<td colspan=6>
									<a onclick="javascript:return updateStatus('EDIT')" href="#">修改备注</a>
								</td>
							</s:if>
						</tr>
						</from>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
function updateStatus(status)
{
	if(confirm("您确定要做此操作？")){
		if(status=='PASS'||status=='CANCEL'||status=='PAYOUT'||status=='EDIT'){
			document.frm.status.value=status;
			document.frm.action="<%=basePath%>bonus/edit.do";
			document.frm.submit();
			}else{
				return false;
			}
		}else{
			return false;
		}
}
</script>
