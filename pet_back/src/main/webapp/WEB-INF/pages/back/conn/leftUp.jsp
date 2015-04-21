<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form name='userFrm' method='post' action="<%=request.getContextPath() %>/call/verifyUser.do"
	onSubmit="return verifyUser()">
	<input type="hidden" value="<s:property value="agentname"/>" name="agentname"/>
	<input type="hidden" value="<s:property value="isCallBack"/>" name="isCallBack"/>	
	<TABLE border=0 cellPadding=4 cellSpacing=0 class="tab1-cc tab1-cc-2">
		<TBODY>
			<TR align=middle class="tab1-cc-tr1">
				<TD width="25%" align="left" valign="middle">
					会员名
				</TD>
				<TD width="75%" align=left>
					<input name="userName" id="userNameLeft" value="<s:property value='userName'/>" />
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					会员手机
				</TD>
				<TD align=left>
					<input name="callerid" id="calleridLeft" value="<s:property value='callerid'/>" />
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr1"> 
				<TD height=25 align="left" valign="middle"> 
				手机归属地 
				</TD> 
				<TD align=left> 
				<s:property value='comMobileArea.provinceName'/>,<s:property value='comMobileArea.cityName'/>
				</TD> 
			</TR>
			<TR align=middle class="tab1-cc-tr1">
				<TD height=25 align="left" valign="middle">
					会员邮箱
				</TD>
				<TD align=left>
					<input name="callerEmail" id="callerEmailLeft" value='<s:property value="callerEmail"/>' />
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr1">
				<TD height=25 align="left" valign="middle">
					会员卡
				</TD>
				<TD align=left>
					<input name="memberShipCard" id="memberShipCardLeft" value="<s:property value='memberShipCard'/>" />
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD colSpan=2 align=middle>
					<INPUT value=核实账号 type=submit class="input1">&nbsp;
					<INPUT onclick="doRegistUser()" value=会员注册
						type="button" name="btnRegistUser" id="IDBtnRegistUser" class="input1">
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>