<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	</head>
	<body>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr class="tableTit">
				<td>
					姓名
				</td>
				<td>
					姓名拼音
				</td>
				<td>
					联系电话
				</td>
				<td>
					Email
				</td>
				<td>
					证件类型
				</td>
				<td>
					证件号码
				</td>
				<td>
					出生日期
				</td>
				<td>
					性别
				</td>
				<td>
					邮编
				</td>
				<td>
					地址
				</td>
				<td>
					操作
				</td>
			</tr>
			<s:if test="visitorList != null">
				<s:iterator value="visitorList">
					<tr>
						<td receiverId="visitor${key.receiverId }">
							${key.receiverName }
						</td>
						<td>
							${key.pinyin }
						</td>
						<td>
							${key.mobileNumber }
						</td>
						<td>
							${key.email }&nbsp;
						</td>
						<td>
							${key.zhCardType }&nbsp;
						</td>
						<td>
							${key.cardNum }&nbsp;
						</td>
						<td>
							<s:date name="key.brithday" format="yyyy-MM-dd" />
							&nbsp;
						</td>
						<td>
							${key.zhGender }&nbsp;
						</td>
						<td>
							${key.postCode }&nbsp;
						</td>
						<td>
							${key.address }&nbsp;
						</td>
						<td>
							<a href="javascript:void(0)"
								onclick="doOperator('visitorList', '${key.receiverId}&delete');">删除</a>
							<s:if test="value == 'true'">
								<input type="checkbox" id="toubao_${key.receiverId}"
									cardType="${key.cardType }" receiverId="${key.receiverId }"
									checked />
							</s:if>
							<s:else>
								<input type="checkbox" id="toubao_${key.receiverId}"
									cardType="${key.cardType }" gender="${key.gender }" receiverId="${key.receiverId }" />
							</s:else>
							投保信息
							<a href="javascript:void(0);"
								onclick="doShowDialog('toubaoDiv', {'to': 'toubao', 'id': '${key.receiverId }&toubao'}, '修改投保信息');">修改投保信息</a>
						</td>
					</tr>
				</s:iterator>
			</s:if>
		</table>
	</body>
</html>
