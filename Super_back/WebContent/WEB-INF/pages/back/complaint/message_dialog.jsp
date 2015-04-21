<%--
  Created by IntelliJ IDEA.
  User: zhushuying
  Date: 13-11-11
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%

%>
<html>
<head>
    <title></title>
    <style type="text/css">
        .ar {
            text-align: right;
            padding-right: 5px;
        }

        .al {
            text-align: left;
            padding-left: 5px;
        }
    </style>
</head>
<body>
<div>
    <form id="message_from" style="text-align: center;">
        <s:hidden name="complaintId" id="complaintId"/>
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar">*手机号码：</td>
                <td class="nc_tr_body al">
                    <input type="text" id="mobilePhone" name="mobilePhoto" />
                    	（当多个手机号时用英文逗号间隔）
                </td>
            </tr>
            <tr>
                <td class="nc_tr_head ar">*处理信息：</td>
                <td class="nc_tr_body al">
                	<s:textarea name="processMessage" id="processMessage" cssStyle="width: 350px;height: 60px;"/>
                </td>
            </tr>
        </table>
        <br>
        <input type="button" id="saveBtn" onclick="saveMessage()" value="确认" style="align: center; width: 70px; height: 25px"/>
    </form>
</div>
<div>
	已发短信列表
	<table class="nc_table" cellspacing="1" cellpadding="1">
		<tr>
			<td class="nc_tr_head">手机号码</td>
			<td class="nc_tr_head">短信内容</td>
			<td class="nc_tr_head">发送时间</td>
			<td class="nc_tr_head">操作人</td>
		</tr>
		<s:iterator value="contentLogsList" var="list">
			<tr>
				<td class="nc_tr_body">${mobile }</td>
				<td class="nc_tr_body">${content }</td>
				<td class="nc_tr_body">
					<s:date name="sendDate" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="nc_tr_body">${userId }</td>
			</tr>
		</s:iterator>
		<s:iterator value="contentsList" var="list">
			<tr>
				<td class="nc_tr_body">${mobile }</td>
				<td class="nc_tr_body">${content }</td>
				<td class="nc_tr_body">
					<s:date name="sendDate" format="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td class="nc_tr_body">${userId }</td>
			</tr>
		</s:iterator>
	</table>
</div>
</body>
<script type="text/javascript">
function checkMessageValue(){
	if($("#mobilePhone").val()==""){
		alert("请输入手机号码");
		return false;
	}
	//判断输入的文本内容中是否存在中文输入法的逗号
	var mobile=$("#mobilePhone").val();
	if(/，/.test(mobile)){
	    alert("请使用英文逗号分隔");
	    return false;
	}
	if($("#processMessage").val()==""){
		alert("请输入处理信息");
		return false;
	}
    return true;
}
function saveMessage(){

	if(!checkMessageValue()){
		return false;
	}

	$.post("order/complaint/saveMessage.do", {
			mobilePhone : $("#mobilePhone").val(),
			processMessage : $("#processMessage").val(),
			complaintId:$("#complaintId").val()
		}, function(data) {
			if (data == "success") {
				alert("发送成功");
                window.location.reload(window.location.href);
			} else {
				alert("发送失败");
			}
		});
}
</script>
</html>