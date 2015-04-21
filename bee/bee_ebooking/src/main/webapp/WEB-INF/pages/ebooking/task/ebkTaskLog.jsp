<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="bg_opacity1 show_hide" id="ebk_log_bg_opacity1"></div>
<iframe class="bg_opacity2 show_hide" id="ebk_log_bg_opacity2"></iframe>
<div class="eject_rz show_hide" id="ebk_log_eject_rz">
	<h4>查看日志</h4>
    <table class="eject_rz_table" width="760" border="0" cellspacing="0" cellpadding="0">
      <tr class="eject_rz_table_tr">
      	<th width="12%" style="text-align: center;">序号</th>
        <th width="12%" style="text-align: center;">订单号</th>
        <th width="12%" style="text-align: center;">操作者</th>
        <th width="24%" style="text-align: center;">操作时间</th>
        <th width="40%" style="text-align: center;">操作内容</th>
      </tr>
      <s:iterator var="log" value="logList" status="i">
      <tr>
        <td width="12%">${i.index+1 }</td>
        <td width="12%">${log.parentId }</td>
        <td width="12%">${log.operatorName }</td>
        <td width="24%"><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
        <td width="40%"><p>${log.content }</p></td>
      </tr>
      </s:iterator>
    </table>
    <span class="close"></span>
</div>
</body>
</html>