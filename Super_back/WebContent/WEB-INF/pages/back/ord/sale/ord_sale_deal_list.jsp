<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="popbox">
	<table style="font-size: 12px" cellspacing="1" cellpadding="4"
		border="0" bgcolor="#666666" width="100%" class="newfont05">
		<tbody>
			<tr bgcolor="#eeeeee">
				<td height="35" width="10%">
					处理内容
				</td>
				<td width="10%">
					申请人
				</td>
				<td width="10%">
					申请时间
				</td>
			</tr>
			<s:iterator value="ordSaleServiceDealList">
				<tr bgcolor="#ffffff">
					<td height="30">
						${dealContent }
					</td>
					<td>
						${operatorUsers.userName}
					</td>
					<td>
						<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>
