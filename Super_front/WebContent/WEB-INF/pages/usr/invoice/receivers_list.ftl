<#list usrReceiversList as obj> 
  	<table width="680" border="0" cellspacing="0" cellpadding="0" id="tb_${obj.receiverId}">
  	<tr>
    	<td align="right" valign="top" style="width:100px;">
    		<input name="receiverId" type="radio" value="${obj.receiverId}" class="refer_click"  <#if usrReceivers.receiverId ==obj.receiverId || obj_index == 0>checked="checked"</#if>/>地址${obj_index + 1}：
    	</td>
    	<td>
    	<div class="old_addes" style="padding:5px;">
       		<table width="550" border="0" cellspacing="0" cellpadding="0">
  				<tr>
    				<td width="61" align="right" class="add_addes01">收件人</td>
    				<td width="520">${obj.receiverName}</td>
  				</tr>
  				<tr>
    				<td align="right" class="add_addes01">收件地址</td>
   			 		<td>${obj.province}${obj.city}${obj.address}</td>
  				</tr>
  				<tr>
    				<td align="right" class="add_addes01">邮　编</td>
    				<td>${obj.postCode}</td>
  				</tr>
  				<tr>
    				<td align="right" class="add_addes01">电　话</td>
    				<td>${obj.mobileNumber}</td>
  				</tr>
			</table>
			<div class="add_del"><a href="javascript:void(0)" class="update_btn" result="${obj.receiverId}">修改&nbsp;/&nbsp;</a><a href="javascript:deleteOperate('${obj.receiverId}')">删除</a></div>
    	</div>
    	</td>
  </tr>
  </table>
  </#list>
