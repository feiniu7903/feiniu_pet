<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<form id="usrInfofrm" name='usrInfofrm' method='post'>
	<input type="hidden" id="callUserInfo_mobileNumber" name="callUserInfo.mobileNumber" value="<s:property value='callUserInfo.mobileNumber'/>" />
	<input type="hidden" id="callUserInfo_userId" name="callUserInfo.userId" value="<s:property value='callUserInfo.userId'/>" />
	<input type="hidden" id="callUserInfo_id" name="callUserInfo.id" value="<s:property value='callUserInfo.id'/>" />
	<input type="hidden" id="callUserInfo_isUsers" name="callUserInfo.isUsers" value="<s:property value='callUserInfo.isUsers'/>" />
	<input type="hidden" value="<s:property value='agentname'/>" name="agentname" />
	<input type="hidden" value="<s:property value='isCallBack'/>" name="isCallBack" />
	<input type="hidden" value="<s:property value='userName'/>" name="userName" />
	<input type="hidden" value="<s:property value='callerEmail'/>" name="callerEmail" />
	<input type="hidden" value="<s:property value='memberShipCard'/>" name="memberShipCard" />
	<input type="hidden" value="" name="moblieNumber" id="moblieNumber"/>
	
	<TABLE border=0 cellPadding=4 cellSpacing=0 class="tab1-cc tab1-cc-2"
		style="FONT-SIZE: 12px; margin-bottom: 10px">
		<TBODY>
			<TR align=middle class="tab1-cc-tr1">
				<TD width="25%" align="left" valign="middle">
					真实姓名
				</TD>
				<TD width="75%" align=left valign="middle">
					<INPUT id=umis_realName name=callUserInfo.realName
						value="<s:property value='callUserInfo.realName'/>">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					性别
				</TD>
				<TD align=left valign="middle">
					<INPUT id=mgender value=M type=radio <s:if test='callUserInfo.gender=="M"'>checked</s:if>  name=callUserInfo.gender>
					男
					<INPUT id=fgender value=F
						<s:if test='callUserInfo.gender=="F"'>checked</s:if> type=radio
						name=callUserInfo.gender>
					女
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr1">
				<TD height=25 align="left" valign="middle">
					会员名
				</TD>
				<TD align=left valign="middle">
					<input readonly="true" style="background-color:#cccccc;"  name=callUserInfo.userName
						value="<s:property value='callUserInfo.userName'/>">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					会员邮箱
				</TD>
				<TD align=left valign="middle">
					<INPUT readonly="true" style="background-color:#cccccc;" name=callUserInfo.email id="callUserInfo_email"
						value="<s:property value='callUserInfo.email '/>">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					返现余额
				</TD>
				<TD align=left valign="middle">
				<s:property value='callUserInfo.awardBalanceYuan'/>
				</TD>
			</TR>
			<TR align=middrle class="tab1-cc-tr1">
				<TD align="left" valign="middle">
					城市省份
				</TD>
				<TD align=left valign="middle">
				 	<s:select list="comProvinceList" id="proviceSelect"
						name="callUserInfo.provinceId" listKey="provinceId"
						listValue="provinceName" ></s:select>
					<s:select  name="callUserInfo.cityId" id="citySelect"
						listKey="cityId" listValue="cityName" list="comCityList"></s:select>
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					注册时间
				</TD>
				<TD align=left valign="middle">
					<s:property value='callUserInfo.createDateStr'/>
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr1">
				<TD align="left" valign="middle">
					地址
				</TD>
				<TD align=left valign="middle">
					<INPUT id=umis_address name=callUserInfo.address
						value="<s:property value='callUserInfo.address '/>">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					身份证号码
				</TD>
				<TD align=left valign="middle">
					<INPUT id=umis_cardId name=callUserInfo.idNumber
						value="<s:property value='callUserInfo.idNumber'/>">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD align="left" valign="middle">
					备注
				</TD>
				<TD align=left valign="middle">
					<INPUT id=umis_memo name=callUserInfo.memo
						value="<s:property value='callUserInfo.memo'/>">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr1">
				<TD colSpan=2 align=center valign="middle">
				</TD>
			</TR>
			<TR align=middle class="tab1-cc-tr2">
				<TD colSpan=2 align=center valign="middle">
					<INPUT onclick="btn_updateBaseUser()" value=基本信息修改
						type="button" name="btnUpdateBaseUser" class="input1">
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form>

<!-- 员工状态处理 -->
<!-- 罗俊说去掉 -->
<!-- <form id="userStatusForm" name='userStatusForm' method='post'>
	<TABLE border=0 cellPadding=4 cellSpacing=0 class="tab1-cc tab1-cc-2"
		style="FONT-SIZE: 12px; margin-bottom: 10px">
		<TBODY>
			<TR align=middle class="tab1-cc-tr1">
				<TD id="onlineTd" width="33%" align="center" valign="middle">
					<input id="user_status_online" onclick="updateUserStatus(this);" type="radio" name="userStatus" value="ONLINE">可接单
				</TD>
				<TD id="busyTd" width="33%" align="center" valign="middle">
					<input id="user_status_busy" onclick="updateUserStatus(this);" type="radio" name="userStatus" value="BUSY">忙碌
				</TD>
				<TD id="offlineTd" width="33%" align="center" valign="middle">
					<input id="user_status_offline" onclick="updateUserStatus(this);" type="radio" name="userStatus" value="OFFLINE">不可接单
				</TD>
			</TR>
		</TBODY>
	</TABLE>
</form> -->

