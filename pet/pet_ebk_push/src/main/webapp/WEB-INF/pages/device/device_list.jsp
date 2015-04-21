<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ebk推送管理</title>

<link rel="stylesheet" type="text/css" href="<%=path %>/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/easyui/themes/icon.css">


<script type="text/javascript" src="<%=path%>/easyui/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=path%>/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/device.js"></script>

</head>
<body>
<table class="easyui-datagrid" title="ebk推送管理" id="device_list"
			data-options="rownumbers:true,singleSelect:true,url:'<%=path %>/manager/loadDevices.do',toolbar:'#tb',fix:true">
		<thead>
			<tr>
				<th data-options="field:'udid',width:140">设备编号</th>
				<th data-options="field:'adminName',width:100">当前登录用户</th>
				<th data-options="field:'isOnline',width:100,align:'center'">设备状态</th>
				<th data-options="field:'netWorkType',width:100,align:'center'">网络类型</th>
				<th data-options="field:'remoteIp',width:100,align:'center'">连接ip</th>
				<th data-options="field:'todayMstNum',width:140">当日推送总数</th>
				<th data-options="field:'msgTimeOutNum',width:140">消息超时数</th>
				<th data-options="field:'attr1',width:140,align:'right'"></th>
			</tr>
		</thead>
	</table>
	
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a onClick="common.action.showUploadLogDialog();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">上传日志</a>
			<a onClick="common.action.deleteHistoryDate();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">删除历史数据</a>
			<a href="#" onClick="common.action.executeCommand('<%=path%>/manager/executeCommand.do?command=RESTART_DEVICE')" class="easyui-linkbutton" iconCls="icon-edit" plain="true">重启设备</a>
			<a href="#" onClick="common.action.showSyncAddCodeDialog();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">同步码号</a>
			<a onClick="common.action.showViewMSGDialog();" class="easyui-linkbutton" iconCls="icon-edit" plain="true">查看消息记录</a>
			<a href="#" onClick="$('#syncNewDeviceDialog').dialog('open');" class="easyui-linkbutton" iconCls="icon-edit" plain="true">新设备数据同步</a>
			<a href="#" class="easyui-linkbutton" onClick="$('#device_list').datagrid('reload')" iconCls="icon-edit" >刷新</a>
		</div>
	</div>
	
	<div id="uploadLogiDialog" closed="true" class="easyui-dialog" title="上传日志" data-options="iconCls:'icon-save'" style="width:250px;height:300px;padding:10px">
		<form id="syncLogForm" method="post">
		<table>
			<tr>
				<td>选择上传日期 
				</td>
				<td>
				<input type="hidden" name="command" value="UPLOAD_LOG"/>
				<input name="date" data-options="required:true" class="easyui-datebox"></td>
				</tr>
				<tr>
				<td>
				</td>
				<td>
				<a href="#" onClick="common.action.executeCommand('<%=path%>/manager/executeCommand.do?a=1','syncLogForm');" class="easyui-linkbutton"  >上传</a>
				</td>
				</tr>
				</table>
		</form>
	</div>
	
	<div id="viewMSGDialog" closed="true" class="easyui-dialog" title="查看消息" data-options="iconCls:'icon-print'" style="width:800px;height:600px;padding:10px">
		<form id="viewMSGForm" method="post">
			<input name="date" data-options="required:true" class="easyui-datebox"></td>
			<a href="#" onClick="common.action.showViewMSGDialog();" class="easyui-linkbutton"  >查询</a>
		</form>
		<table id="msg_list" class="easyui-datagrid" >
		
		</table>
	</div>

	<div id="syncAddCodeDialog" closed="true" class="easyui-dialog" title="同步码" data-options="iconCls:'icon-save'" style="width:250px;height:150px;padding:10px">
	<form id="syncCodeForm" method="post">
	<table>
			<tr>
				<td>输入码号</td>
				<td>
				<input type="hidden" name="command" value="PULL"/>
				<input class="easyui-validatebox"  name="addCode" data-options="required:true"></td>
			</tr>
			<tr>
			<td>
			</td>
			<td>
			<a onClick="common.action.subSyncAddCodForm('<%=path%>/manager/executeCommand.do?a=1');" class="easyui-linkbutton" iconCls="icon-save">同步</a></td>
			</tr>
		</table>
</form>
	
	</div>
	
	<div id="syncNewDeviceDialog" closed="true" class="easyui-dialog" title="新设备码号同步" data-options="iconCls:'icon-save'" style="width:350px;height:200px;padding:10px">
	<form id="syncNewDeviceForm" method="post">
	<table>
			<tr>
				<td>输入设备号号</td>
				<td>
				<input type="hidden" name="command" value="PULL"/>
				<input class="easyui-validatebox"  name="udid" data-options="required:true"></td>
			</tr>
			<tr>
			<td>
			同步进度:
			</td>
			<td>
			<div id="processDiv"></div>
			</td>
			</tr>
			<tr>
			
			
			<td>
			<a onClick="common.action.syncNewDevice();" class="easyui-linkbutton" iconCls="icon-save">同步</a></td>
			</tr>
		</table>
	</form>
	
	</div>
	
</body>
</html>
