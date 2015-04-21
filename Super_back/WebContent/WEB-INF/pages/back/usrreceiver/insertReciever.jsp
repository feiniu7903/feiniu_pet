<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <link href="<%=basePath%>themes/base/jquery.ui.all.css" rel="stylesheet"></link>
	<script type="text/javascript" src="<%=basePath%>js/base/jquery.datepick-zh-CN.js"></script>		
	<script type="text/javascript">

	function closeMyDiv(obj){
		$("select").each(function(){$(this).show();});
		$("#addReceiverDialg").hide();
	}
	
	function closeMyDiv2(){
		$("select").each(function(){$(this).show();});
		$("#editReceiverDialg").hide();
	}
	
	</script>
  </head>
  
  <body>
    <div class="view-window" id="passedWindow" style="display: ">
    <s:form theme="simple" id="insertUpdateReceiverForm">
           <table width="100%" border="0"  class="contactlist">
            <tr>
             <td width="19%">取票(联系)人：</td>
             <td width="30%"><input name="usrReceivers.receiverName" id="receiverName" type="text" value="${usrReceivers.receiverName}" maxlength="50"/></td>			 
             <td width="17%">Email：</td>
             <td width="34%"><input name="usrReceivers.email" type="text" value="${usrReceivers.email}" maxlength="50"/></td>			 
            </tr>
          
            <tr>
             <td>联系电话：</td>
             <td><input name="usrReceivers.mobileNumber" id="mobileNumber" type="text" value="${usrReceivers.mobileNumber}" maxlength="20"/></td>			 
             <td>座机号：</td>
             <td><input name="usrReceivers.phone" type="text" value="${usrReceivers.phone}" maxlength="50"/></td>				 			 
            </tr>
            <tr>
             <td>证件类型：</td>
             <td>
             <s:select value="usrReceivers.cardType" name="usrReceivers.cardType" list="#{'':'请选择','ID_CARD':'身份证','HUZHAO':'护照','OTHER':'其他'}"></s:select>
             </td>		 
             <td>证件号码：</td>
             <td><input name="usrReceivers.cardNum" type="text"  value="${usrReceivers.cardNum}" maxlength="30"/></td>            
			</tr>
            <tr>
             <td>传真：</td>
             <td><input name="usrReceivers.fax" type="text" value="${usrReceivers.fax}" /></td>			 
             <td>传真接收人：</td>
             <td><input name="usrReceivers.faxContactor" type="text" value="${usrReceivers.faxContactor}" /></td>			 
            </tr>
		   </table>
		   <s:hidden name="userId"></s:hidden>
		   <s:hidden name="receiverId"></s:hidden>
		   <s:hidden name="orderId"></s:hidden>
		   <p><input type="reset" name="btnResetReceiver" value="清空" class="button">&nbsp;&nbsp;&nbsp;
		   <s:if test="receiverId==null||receiverId==''">
		   
		    <input type="button" name="btnAddReceiver" value="保存" onclick="addReceivers();" class="button">&nbsp;&nbsp;&nbsp;
		   </s:if> <s:else>
		    <input type="button" name="btnEditReceiver" value="保存" onclick="editReceiver();" class="button">&nbsp;&nbsp;&nbsp;
		   </s:else>
		   <input type="button" name="btnAddContactor" value="保存为联系人" onclick="addContactor();" class="button">&nbsp;&nbsp;&nbsp;
		   <s:if test="receiverId==null||receiverId==''">
		   <input type="button" id="closebtn" onclick="closeMyDiv();" name="closebtn" value="关闭" class="button">
		   </s:if> <s:else>
		       <input type="button" id="closebtn" onclick="closeMyDiv2();" name="closebtn" value="关闭" class="button">
		   </s:else>
		   </p>
		   </s:form>
</div>
  </body>
</html>
