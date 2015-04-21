<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<title></title>
<style type="text/css">
	.text_style{
		width: 136px;
		height: 23px;
	}
	#managerTable td span {
		cursor:pointer;
	}
	.main_table tr {
		height:30px;
	}
	#mask_div {
		position:absolute;
		width:100%;
		height:100%;
		z-index:998;
		background: #999;
		filter:alpha(opacity=60);
		-moz-opacity:0.6;
		-khtml-opacity: 0.6;
		opacity: 0.6;
		top: 0;
		left: 0;
	}
</style>
</head>
<body>
<form action="" id="controlRoleForm">
<input type="hidden" name="role.productControlRoleId" value="<s:property value="role.productControlRoleId"/>"/>
<table class="main_table">
	<tr>
		<td><em>姓名：<span class="require">[*]</span></em></td>
		<s:if test="role.productControlRoleId == null">
			<td>
				<input type="hidden" name="role.userId" id="userId" value="<s:property value="role.userId"/>"/>
				<input type="text" value="${role.userName}" id="inputUserId">
			</td>
		</s:if>
		<s:else>
			<td>
				<input type="hidden" name="role.userId" id="userId" value="<s:property value="role.userId"/>"/>
				<span style="font-weight:bold;">
					<s:property value="role.userName"/>
				</span>
			</td>
		</s:else>
			<td width="100"></td>
	</tr>
	<tr>
		<td><em>查看范围：<span class="require">[*]</span></em></td>
		<td>
			<s:select name="role.roleArea"
				 value="role.roleArea"
				 id="roleAreaSelect"
				 cssClass="text_style"
				 list="@com.lvmama.comm.vo.Constant$PRODUCT_CONTROL_ROLE@values()"
				 listKey="code" listValue="cnName" headerKey="" headerValue="请选择" />
		</td>
		<td></td>
	</tr>
	<tr id="managerTr">
		<td colspan="3">
			<div style="border: dotted 1px #999;padding: 5px;margin-top: 5px;position: relative;">
			<div id="mask_div"></div>
				<span>选择产品经理</span>
				<br/><br/>
				<em>姓名：</em><s:hidden id="managerId"/><input type="text" id="inputManagerId"> &nbsp; <span onclick="addManager()" style="cursor:pointer;">增加</span>
				<br/><br/>
				<input type="hidden" name="role.managerIdList" id="managerIdList" value="<s:property value="role.managerIdList"/>"/>
				<div style="height:150px; overflow-y: auto;">
					<table id="managerTable" border="0" cellspacing="0" cellpadding="0" class="newTable">
						<tr>
							<td style="color:#15428B;font-weight:bold;width:150px;background: #e5eeff;">姓名</td>
							<td style="color:#15428B;font-weight:bold;background: #e5eeff;">操作</td>
						</tr>
						<s:if test="role.productControlRoleId != null">
							<s:iterator value="userList" id="user">
								<tr mId="<s:property value="userId"/>" >
									<td><s:property value="realName"/></td>
									<td><span onclick="deleteManager('<s:property value="userId"/>', this)">删除</span></td>
								</tr>
							</s:iterator>
						</s:if>
					</table>
				</div>
			</div>
		</td>
	</tr>
</table>
</form>
<div style="text-align: center;position: absolute;bottom: 10px;left: 70px;">
	<input type="button" style="width: 100px;height: 20px;" value="保存" onclick="addControlRole()">
	<input type="button" style="width: 100px;height: 20px;" value="关闭" onclick="closeControlRoleDialog()">
</div>
</body>
<script>

function showMask() {
	$('#mask_div').fadeIn(500);
}

function hideMask() {
	$('#mask_div').fadeOut(500);
}

(function initPage() {
	var val = $('#roleAreaSelect').val();
	if (val) {
		if (val == 'ROLE_MANAGER') {
			$('#mask_div').hide();
		} else {
			showMask();
		}
	} else {
		showMask();
	}
})();

function toJsonArr(str) {
	if (str) {
		return eval('(' + str + ')');
	}
	return [];
}

function arrToStr(arr) {
	if (arr) {
		var str = '[';
		for (var i = 0; i < arr.length; i++) {
			str += arr[i];
			if (i < arr.length - 1) {
				str += ',';
			}
		}
		str += ']';
		return str;
	}
	return '[]';
}

function addManager() {
	var managerId = $("#managerId").val();
	var managerName = $('#inputManagerId').val();
	if (!managerId) {
		alert("用户名错误");
		return;
	}
	var selectList = toJsonArr($('#managerIdList').val());
	
	for (var i = 0; i < selectList.length; i++) {
		if (selectList[i] == managerId) {
			alert("用户[" + managerName + "]已经添加,不能重复");
			return;
		}
	}
	selectList.push(managerId);
	
	var tr = '<tr mId="' + managerId + '" >';
	tr += '<td>' + managerName + '</td>';
	tr += '<td><span onclick="deleteManager(' + managerId +', this)">删除</span></td></tr>';
	$('#managerIdList').val(arrToStr(selectList));
	$('#managerTable').find('tr:last').after(tr);
	
};

function deleteManager(id, span) {
	var index = -1;
	$('#managerTable').find('tr[mId='+ id +']').remove();
	var selectList = toJsonArr($('#managerIdList').val());
	var arr = [];
	for (var i = 0; i < selectList.length; i++) {
		if (id == selectList[i]) {
			index = i;
			break;
		}
	}
	if (index != -1) {
		var last = selectList[selectList.length -1];
		selectList[selectList.length -1] = selectList[index];
		selectList[index] = last;
		selectList.pop();
		$('#managerIdList').val(arrToStr(selectList));
	}
};

$(document).ready(function(){
	$("#inputUserId").jsonSuggest({
		url:"/pet_back/perm_user/search_user.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#userId").val(item.id);
			$("#inputUserId").blur();
		}
	});
	
	$("#inputManagerId").jsonSuggest({
		url:"/pet_back/perm_user/search_user.do",
		maxResults: 10,
		minCharacters:1,
		onSelect:function(item){
			$("#managerId").val(item.id);
			$("#inputManagerId").blur();
		}
	});
	
	$('#roleAreaSelect').change(function(){
		var val = $(this).val();
		if (val) {
			if (val == 'ROLE_MANAGER') {
				hideMask();
			} else {
				showMask();
			}
		} else {
			showMask();
		}
	});
});
</script>
</html>