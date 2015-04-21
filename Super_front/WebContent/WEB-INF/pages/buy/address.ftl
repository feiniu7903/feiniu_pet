<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script src="/js/buy/inv_add.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(document).ready(function() {
			if($("input[name='addressId']").length > 0) {
				$("input[name='addressId']").get(0).checked=true;
			}
		});
		</script>
</head>

<html>
	<body>
		<div class="s2-info-area">
	    	<h3>地址信息<a href="javascript:void(0)" class="qijiashuoming">地址说明</a></h3>
	        <div class="shuoming">如果订单中包含需邮寄的商品，如：实物票，或需索取发票，请填写收件地址信息。</div>
	    	<div class="adress-info"><strong>已有地址：</strong><span id="adress_info"></span><span id="handle_area"></span></div>
	    	<table cellspacing="1" cellpadding="4" border="0" width="100%" class="t-margin">
	    		<tr class="data-head">
	    			<td></td><td>接收人</td><td>电话</td><td>地址</td><td>邮编</td><td>操作</td>
	    		</tr>
	    		<@s.iterator value="usrReceiversList" var="usrReceiversVar">
	    			<tr>
		    			<td width="5%" align="center"><input type="radio" name="addressId" value="${usrReceiversVar.receiverId }" /></td>
		    			<td width="10%">${usrReceiversVar.receiverName }</td>
		    			<td width="10%">${usrReceiversVar.mobileNumber }</td>
		    			<td width="20%">${usrReceiversVar.address }</td>
		    			<td width="10%">${usrReceiversVar.postCode }</td>
		    			<td width="10%"><a href="javascript:showEditAddress('${usrReceiversVar.receiverId }');">修改</a>
					   <a href="javascript:deleteAddress('${usrReceiversVar.receiverId }');">删除</a></td>
	    			</tr>
	    		</@s.iterator>
	    	</table>
	        <div class="adress-div edit" id="edit_div">
	        <input name="receiverId" type="hidden" id="receiverId" value="${usrReceivers.receiverId }" />
	        	<table width="100%" border="0">
					<tr height="35">
						<td width="14%">
							地址：
						</td>
						<td width="40%">
							<input name="address" type="text" id="address" size="30" value="${usrReceivers.address }" />
						</td>
						<td>
							联系人：
						</td>
						<td>
							<input name="receiverName" type="text" id="receiverName" value="${usrReceivers.receiverName }" />
						</td>
					</tr>
					<tr height="35">
						<td>
							联系电话：
						</td>
						<td>
							<input name="mobileNumber" type="text" id="mobileNumber" value="${usrReceivers.mobileNumber }" />
						</td>
						<td>
							邮编：
						</td>
						<td>
							<input name="postCode" type="text" id="postCode" value="${usrReceivers.postCode }" />
						</td>
					</tr>
					<tr height="35">
						<td></td>
						<td><input type="button" value="新增地址" name="buttonStr" class="save-btn" onclick="addOrUpdateAddress('/usrReceivers/saveAddress.do');" /></td>
						<td><input type="button" value="保存修改" name="buttonStr"  class="save-btn" onclick="addOrUpdateAddress('/usrReceivers/updateAddress.do');" /></td>
						<td></td><td></td>
					</tr>
				</table>
	        </div>
	    </div>
	</body>
</html>
