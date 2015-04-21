<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table style="font-size: 15px" border="0" width="100%" bgcolor="#B8C9D6">
	<tbody>
		<s:iterator value="trackLogList">
			<tr align="center">
				<td bgcolor="#ffffff" height="20" width="20%">
					<s:property value="ZhTrackStatus" />
				</td>
				<td bgcolor="#ffffff" align="left">
					<s:property value="memo" />
				</td>
				<td bgcolor="#ffffff" align="left" width="20%">
					<s:date format='yyyy-MM-dd HH:mm:ss' name="createTime" />
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
