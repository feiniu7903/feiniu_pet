<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class='snspt_poptab'>
	<tr><th width='80'>操作时间</th><th width='100'>操作人用户名</th><th width='80'>操作人姓名</th><th>操作详情</th></tr>
	<s:if test="comloglist != null">
		<s:iterator id="item" value="comloglist">
			<tr>
				<td>
					<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>${operatorName }</td>
				<td>&nbsp;</td>
				<td>${content }</td>
			</tr>
		</s:iterator>
	</s:if>
</table>