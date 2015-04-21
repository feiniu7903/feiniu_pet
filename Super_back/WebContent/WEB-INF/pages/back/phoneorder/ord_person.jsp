<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>供应商基本信息</title>
<link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath%>js/base/jquery.form.js"></script>
<script type="text/javascript">
	//关闭弹出层
	function closeMyDiv2(){
		$("select").each(function(){$(this).show();});
		$("#editReceiverDialg").hide();
	}
	//修改或新增信息
	function saveOrUpdateOrdPerson(){
		//判断当前页面是否有修改
		if(isUpdate) {
			alert("未做修改，不用保存");
			return false;
		}
		var options = {
				url:$("#ordPersonForm").attr("action"),
				success:function(data){
					if(data=="") {
						alert("操作成功!");
						// TODO
						var refresh = $("#refresh").val();
						if(refresh=='approveDiv'){
							$("#editReceiverDialg").hide();
							refresh = "approveDiv";
						}else{
							$("#addReceiverDialg").hide();
							refresh = "historyDiv";
						}
						//请求数据,重新载入层
						$("#"+refresh).reload({"orderId":$("#orderId").val()});
					} else {
						alert(data);
					}
				}
		};
		if(!checkPersonAllSubmit()) {
			return false;
		}
		$("#ordPersonForm").ajaxSubmit(options);
	}
	$(function(){
		putdatepicker();
	})
	function putdatepicker() {
		$("input.date").datepicker({dateFormat:'yy-mm-dd',
				changeMonth: true,
				changeYear: true,
				showOtherMonths: true,
				selectOtherMonths: true,
				buttonImageOnly: true,
				yearRange: '-100:+0'
		});	
	}
	
	function checkPersonAllSubmit() {
		//检查姓名
		if(checkPersonName()){
			return false;
		}
		return true;
	}
	
	function checkPersonName(){
		var volid = false;
		$("#ordPersonForm #personName").each(function() {
			if($(this).val()==""){
				alert("请输入姓名！");
				$(this).focus();
				volid = true;
				return false;
			}
		});
		if(volid) {
			return false;
		}
	}
	var isUpdate = true; 
	$(function(){
		$("#ordPersonForm input,#ordPersonForm select").each(function() {
			$(this).bind("change",function(){
				isUpdate=false;
			});
		});
	});
</script>
</head>
<body>
<div class="view-window" id="passedWindow" 
	style="width: 1100px;margin-left: -80px;overflow:auto;">
	<div>
		<span style="font-size: 14px;padding: 0px;margin: 0px; float: left;">
			修改/新增游客信息
		</span>
		<span style=" float: right;">
			<input type="button" id="closebtn" onclick="closeMyDiv2();"
					name="btnCloseMyDiv2" value="关闭" class="button"  style="">
		</span>
	</div>
	<div style="clear:both;"></div>
	<form id="ordPersonForm" method="post" action="<%=basePath %>ord/saveOrUpdate.do">
		<table style="font-size: 12px;" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont04" id="personListTable">
				<tr bgcolor="#f4f4f4" align="center">
					<td width="14%">类别</td>
					<td width="6%">姓名<font color="red">*</font></td>
					<td width="6%">拼音</td>
					<td width="8%">联系方式</td>
					<td width="7%">座机号</td>
					<td width="10%">证件类型</td>
					<td width="5%">证件号码</td>
					<td width="8%">出生日期</td>
					<td width="9%">性别</td>
					<td width="10%">Email</td>
					<td width="6%">邮编</td>
					<td width="10%">地址</td>
					<td width="6%">操作</td>
				</tr>
				<s:iterator value="personList" var="per" status="i">
					<s:if test="personType=='CONTACT' || personType=='TRAVELLER' || personType=='EMERGENCY_CONTACT'">
						<tr bgcolor='#ffffff' align="center" id="addTr${i.index }">
							<td>
								${per.personType eq 'CONTACT'?"取票/联系人":"" }
								${per.personType eq 'TRAVELLER'?"游客":"" }
								${per.personType eq 'EMERGENCY_CONTACT'?"紧急联系人":"" }
								<input type="hidden" value="${per.personId}" name="person[${i.index }].personId" id="personId"/>
								<input type="hidden" value="${per.personType}" name="person[${i.index }].personType" id="personType"/>
							</td>
							<td>
								<input name="person[${i.index }].name" id="personName" size="6"
									type="text" value="${per.name}" maxlength="40"/>
							</td>
							<td>
								<input name="person[${i.index }].pinyin" id="personNamePin" type="text" 
									size="8" value="${per.pinyin}" maxlength="40"/>
							</td>
							<td>
								<input name="person[${i.index }].mobile" type="text" id="mobileNumber"
									value="${per.mobile}" maxlength="30" size="9" />
							</td>
							<td>
								<input name="person[${i.index }].tel" type="text" size="8"
									value="${per.tel}" maxlength="30" />
							</td>
							<td>
								<s:select value="certType" name="person[%{#i.index }].certType" 
									list="#{'':'请选择','ID_CARD':'身份证','HUZHAO':'护照','CUSTOMER_SERVICE_ADVICE':'客服联系我','ERTONG':'儿童无证件','GANGAO':'港澳通行证','HUIXIANG':'回乡证','JUNGUAN':'军官证','TAIBAO':'台胞证','OTHER':'其他'}"></s:select>
							</td>
							<td>
								<input name="person[${i.index }].certNo" type="text" size="18"
									value="${per.certNo}" maxlength="30" />
							</td>
							<td>
								<input name="person[${i.index }].brithday" type="text" size="9"
									value="${per.zhBrithday}" maxlength="40" class="date"/>
							</td>
							<td>
								<input type="radio" name="person[${i.index }].gender" value="M"
									<s:if test='gender=="M"'>checked</s:if> />男 
								<input type="radio" name="person[${i.index }].gender" value="F"
									<s:if test='gender=="F"'>checked</s:if> />女
							</td>
							<td>
								<input name="person[${i.index }].email" type="text" size="16"
									value="${per.email}" maxlength="50" />
							</td>
							<td>
								<input name="person[${i.index }].postcode" id="personPostCode" size="5"
									type="text" value="${per.postcode }" maxlength="40" />
							</td>
							<td>
								<input name="person[${i.index }].address" id="personAddress" size="16"
									type="text" value="${per.address}" />
							</td>
							<td>
								<s:if test="(personType=='TRAVELLER' || personType=='EMERGENCY_CONTACT') && !orderDetail.hasSupplierChannelOrder">
									<a href="javascript:void(0)" onclick="deleteTr('${i.index }', '${per.personId }')">删除</a>
								</s:if>
							</td>
						</tr>
					</s:if>
				</s:iterator>
			</table>
			<s:hidden id="orderId" name="orderId"></s:hidden>
			<input type="hidden" value="" id="deletePersonIds" name="deletePersonIds"/>
			<div id="usrReceiversList" style="float: left;width: 100%;">
				 <p class="selectname" style="text-align: left;">已有取票人选择：
					 <select id="usrReceiver">
					 	<option value="">请选择</option>
						 <s:iterator value="receiversList" var="rec">
						 	<option value="${rec.receiverId }" json="<s:property value='receiverJsonData'/>">${rec.receiverName}</option>
						 </s:iterator>
					 </select>
					 <s:if test="!orderDetail.hasSupplierChannelOrder"><a href="javascript:void(0)" id="addTourise" onclick="personByAdd()">新增</a></s:if>
    				
		   		</p>
			</div>
			<div style="clear:left;"></div>
			<p>
				<input type="button" name="btnSaveOrUpdateOrdPerson" value="保存"
					onclick="saveOrUpdateOrdPerson();" class="button">
			</p>
		</form>
	</div>
</body>
<script type="text/javascript">
	var indexTr = "${fn:length(personList)}";
	var nullRecJson = ${nullRecJson};
	function personByAdd() {
		var rec = $("#usrReceiversList #usrReceiver");
		if(rec.val() != null && rec.val() != "") {
			var recJson = $("#usrReceiversList #usrReceiver :selected").attr("json"); 
			recJson = eval("("+recJson+")");
			if(checkExistsUserInfo(recJson)) {
				return false;
			}
			addPerson(recJson);
		} else {
			addPerson(nullRecJson);
		}
	}
	var certTypeOption = "<option value=''>请选择</option><option value='ID_CARD'>身份证</option>"
	+ "<option value='HUZHAO'>护照</option><option value='CUSTOMER_SERVICE_ADVICE'>客服联系我</option><option value='ERTONG'>儿童无证件</option>"
	+ "<option value='GANGAO'>港澳通行证</option><option value='HUIXIANG'>回乡证</option><option value='JUNGUAN'>军官证</option>"
	+ "<option value='TAIBAO'>台胞证</option><option value='OTHER'>其它</option>"
	
	function addPerson(recJson) {
		
		$("#passedWindow #personListTable")
			.append("<tr bgcolor='#ffffff' align='center' id='addTr"+indexTr+"'>"
			+ "<td><select name='person["+indexTr+"].personType'><option selected='selected' value='TRAVELLER'>游客</option><option value='EMERGENCY_CONTACT'>紧急联系人</option></select></td>"
			+ "<td><input name='person["+indexTr+"].name' id='personName' size='6' type='text' maxlength='40' value='"+recJson.receiverName+"'/></td>"
			+"<td><input name='person["+indexTr+"].pinyin' id='personNamePin' type='text' value='"+recJson.pinyin+"' size='8' maxlength='40'/></td>"
			+ "<td><input name='person["+indexTr+"].mobile' id='mobileNumber' value='"+recJson.mobileNumber+"' size='9' maxlength='30' /></td>"
			+ "<td><input name='person["+indexTr+"].tel' type='text' id='personTel' value='"+recJson.phone+"' size='8' maxlength='30' /></td>"
			+ "<td><select name='person["+indexTr+"].certType'>"+certTypeOption+"</select></td>"
			+ "<td><input name='person["+indexTr+"].certNo' value='"+recJson.cardNum+"' id='personCertno' type='text' value='${usrReceivers.cardNum}' size='18' maxlength='30' /></td>"
			+ "<td><input name='person["+indexTr+"].brithday' value='"+recJson.zhBrithday+"' type='text' size='9' class='date' /></td>"
			+ "<td><input type='radio' id='genderM' name='person["+indexTr+"].gender' value='M' />男 "
			+ "<input type='radio' id='genderF' name='person["+indexTr+"].gender' value='F' />女</td>"
			+ "<td><input name='person["+indexTr+"].email' id='personEmail' value='"+recJson.email+"' type='text' size='16' maxlength='50' /></td>"
			+ "<td><input name='person["+indexTr+"].postcode' id='personPostCode' value='"+recJson.postCode+"' size='5' type='text' maxlength='40' /></td>"
			+ "<td><input name='person["+indexTr+"].address' id='personAddress' value='"+recJson.address+"' size='16' type='text' /></td>"
			+ "<td><a href='javascript:void(0)' onclick='deleteTr("+indexTr+")'>删除</a><input type='hidden' id='receiverId_"+recJson.receiverId+"' name='receiverId' value='"+recJson.receiverId+"'/></td></tr>");
		if(recJson.receiverId) {
			if(recJson.gender == 'M') {
				$("#addTr"+indexTr + " #genderM").attr("checked");
			} else {
				$("#addTr"+indexTr + " #genderF").attr("checked");
			}
			$("#addTr" + indexTr + " [name='person[" + indexTr + "].certType']").val(recJson.cardType);
		}
		indexTr++;
		putdatepicker();
		isUpdate=false;
	};
	function checkExistsUserInfo(recJson){
		if(recJson != null) {
			if($("#ordPersonForm #receiverId_"+recJson.receiverId).size() > 0) {
				alert("不能重复添加同一游玩人的信息！");
				return true;
			}
		}
		return false;
	}
	function deleteTr(id, oldId) {
		$("#passedWindow table #addTr"+id).remove();
		if(oldId) {
			$("#ordPersonForm #deletePersonIds").val($("#ordPersonForm #deletePersonIds").val() + "," + oldId);
		}
		isUpdate=false;
	};
</script>
</html>