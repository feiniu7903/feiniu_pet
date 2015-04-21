<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="send_sms_form" action="<%=request.getContextPath() %>/callCenter/saveSmsConnRecord.do" method="post" >
	<input type="hidden" name="orderId" id="sms_order_id"/>
	<input type="hidden" name="userId" id="user_id" value="${userUser.id }"/>
	<table>
		<tr>
			<td>电话号码：</td>
			<td><input name="mobilePhoneNo" type="text" id="sms_mobilePhoneNo"/></td>
		</tr>
		<tr>
			<td>短信模板类型：</td>
			<td>
				<select id="sms_type_list">
					<option value="" selected="selected">全部</option>
					<s:iterator value="smsTypeMap" >
						<option value="<s:property value="key"/>" ><s:property value="value"/></option>
					</s:iterator>
				</select>
			</td>
		</tr>
		<tr>
			<td>短信模板：</td>
			<td>
				<select id="sms_list"  name="templateId"></select>
			</td>
		</tr>
		<tr>
			<td>模板内容：</td>
			<td >
				<textarea rows="5" cols="50" id="sms_content" name="smsContent"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" id="submit_form_btn" value="确认发送"/>
			</td>
		</tr>
	</table>
</form>