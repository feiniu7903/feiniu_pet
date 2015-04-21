<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<body>
		<!--=========================我的历史订单弹出层==============================-->
		<div class="orderpoptit">
			<strong>订单查看：</strong>
			<p class="inputbtn">
				<input type="button" name="btnCloseOrder" class="button" value="关闭"
					onclick="javascript:closeDetailDiv('historyDiv');">
			</p>
		</div>
		<div class="orderpopmain">
			<table style="font-size: 12px" width="100%" border="0"
				id="orderTable">
				<tr>
					<td align="right" width="10%">
						订单编号：
					</td>
					<td width="15%">
						${(reservationsOrderDetail.orderNo)!}
					</td>
					<td align="right" width="10%">
						下单人：
					</td>
					<td width="20%">
						${(reservationsOrderDetail.userId)!}
					</td>
					<td align="right" width="15%">
						下单时间：
					</td >
					<td width="30%">
						${(reservationsOrderDetail.createdTime?string('yyyy-MM-dd HH:mm:ss'))!}
					</td>
				</tr>
				<tr>
					<td align="right" >
						应付金额：
					</td>
					<td>
						${(reservationsOrderDetail.oughtPay/100)!}
					</td>
					<td align="right">
						实付金额：
					</td>
					<td>
						${(reservationsOrderDetail.actualPay/100)!}
					</td>
					<td align="right">
						支付等待时间：
					</td>
					<td>
						${(reservationsOrderDetail.waitPayment?string('yyyy-MM-dd HH:mm:ss'))!}
					</td>
				</tr>
				<tr>
					<td align="right">
						订单状态：
					</td>
					<td>
						${(reservationsOrderDetail.orderStatusZH)!}
					</td>
					<td align="right">
						支付状态：
					</td>
					<td>
						${(reservationsOrderDetail.paymentStatusZH)!}
					</td>
					<td align="right">
						支付时间：
					</td>
					<td>
						${(reservationsOrderDetail.paymentTime?string('yyyy-MM-dd HH:mm:ss'))!}
					</td>
				</tr>
				<tr>
					<td align="right">
						下单渠道：
					</td>
					<td>
						${(reservationsOrderDetail.channel)!}
					</td>
					<td align="right">
						审核状态：
					</td>
					<td>
						${(reservationsOrderDetail.approveStatusZH)!} 
					</td>
					
					<td align="right">
						最晚取消时间：
					</td>
					<td>
						${(reservationsOrderDetail.lastCancelTime?string('yyyy-MM-dd HH:mm'))!}
					</td>
				</tr>
				<tr>
					<td align="right">
						废单人：
					</td>
					<td>
						${(reservationsOrderDetail.cancelOperator)!}
					</td>
					<td align="right">
						退款金额：
					</td>
					<td>
						<#if refundMoney??>
							${(refundMoney/100)!}
						</#if>
					</td>
					<td align="right">
						废单时间：
					</td>
					<td>
						${(reservationsOrderDetail.cancelTime?string('yyyy-MM-dd HH:mm:ss'))!}
					</td>
				</tr>
				<tr>
					<td align="right">
						废单原因：
					</td>
					<td colspan="5">
						${(reservationsOrderDetail.cancelReason)!}
					</td>
				</tr>
				<tr>
					<td align="right">
						用户备注：
					</td>
					<td colspan="5">
						${(reservationsOrderDetail.userMemo)!}
					</td>
				</tr>
			</table>
		</div>
		<div class="popbox">
			<strong>商品清单</strong>
			<table  width="100%" cellspacing="0" cellpadding="0" align="center">							
				<tr class="fila1">
					<td>入住时间： <#if reservationsOrderHotelDetailRes??>
					${(reservationsOrderHotelDetailRes[0].checkin?string('yyyy-MM-dd'))!}
					至
					${(reservationsOrderHotelDetailRes[0].checkout?string('yyyy-MM-dd'))!}</#if>
					</td>
				</tr>
				<tr class="fila1">
					<td>最晚取消时间：${(reservationsOrderDetail.lastCancelTime?string('yyyy-MM-dd
					HH:mm'))!}
					</td>
				</tr>
			</table>
			<table class="newTable">
				<tr>
					<td colspan="8" class="prodw">
						${(reservationsOrderHotelDetailRes[0].hotelName)!}
					</td>
				</tr>
				<tr>
					<td>
						序号
					</td>
					<td>
						产品名称
					</td>
					<td>
						入住人数
					</td>
					<td>
						房间数
					</td>
					<td>
						会员价
					</td>
					<td>
						总金额
					</td>
					<td>
						入住及离店时间
					</td>
				</tr>
				<#if reservationsOrderRoomDetailRes??> <#list
				reservationsOrderRoomDetailRes as reservationsOrderRoom>
				<tr>
					<td>
						${(reservationsOrderRoom.id)!}
					</td>
					<td>
						${(reservationsOrderRoom.roomType)!}
					</td>
					<td>
						<#if reservationsOrderRoom.adults??>
						${(reservationsOrderRoom.adults)!}成人 </#if> <#if
						reservationsOrderRoom.children??> <#if
						reservationsOrderRoom.adults??>，</#if>
						${(reservationsOrderRoom.children)!}儿童 </#if>
					</td>
					<td>
						${(reservationsOrderRoom.roomNumber)!}
					</td>
					<td>
						${(reservationsOrderRoom.salePrice/100)!}
					</td>
					<#if reservationsOrderRoom_index==0>
					<td rowspan="${count}">
						${(reservationsOrderDetail.oughtPay/100)!}
					</td>
					</#if>
					<td>
						<#if reservationsOrderHotelDetailRes??>
						${(reservationsOrderHotelDetailRes[0].checkin?string('yyyy-MM-dd'))!}
						至
						${(reservationsOrderHotelDetailRes[0].checkout?string('yyyy-MM-dd'))!}
						</#if>
					</td>
				</tr>
				</#list> </#if>
			</table>	
		</div>
		<div class="popbox">
			<strong>用户信息</strong>
			<table class="newTable">
				<tr>
					<td>
						类别
					</td>
					<td>
						姓名
					</td>
					<td>
						手机
					</td>
					<td>
						Email
					</td>
					<td>
						证件类型
					</td>
					<td>
						证件号码
					</td>
					<td>
						邮编
					</td>
					<td>
						地址
					</td>
					<td>
						联系电话
					</td>
					<td>
						传真
					</td>
					<td>
						备注
					</td>
				</tr>
				<#if reservationsOrderPersonDetailRes??> <#list
				reservationsOrderPersonDetailRes as reservationsOrderPerson>
				<tr>
					<td>
						<#if reservationsOrderPerson.personType??> <#if
						reservationsOrderPerson.personType=='2'> 入住人/联系人 <#else> 入住人
						</#if> <#else> 入住人 </#if>
					</td>
					<td>
						${(reservationsOrderPerson.name)!}
					</td>
					<td>
						${(reservationsOrderPerson.mobile)!}
					</td>
					<td>
						${(reservationsOrderPerson.email)!}
					</td>
					<td>
						${(reservationsOrderPerson.certType)!}
					</td>
					<td>
						${(reservationsOrderPerson.certNo)!}
					</td>
					<td>
						${(reservationsOrderPerson.postcode)!}
					</td>
					<td>
						${(reservationsOrderPerson.address)!}
					</td>
					<td>
						${(reservationsOrderPerson.tel)!}
					</td>
					<td>
						${(reservationsOrderPerson.fax)!}
					</td>
					<td>
						${(reservationsOrderPerson.memo)!}
					</td>
				</tr>
				</#list> </#if>
			</table>
		</div>	
		<!--popbox end-->
		<p class="submitbtn2">
			<#if reservationsOrderDetail.orderStatus!='CANCEL'>
				<select name="cancelReason" id="cancelReason">
					<#list cancelReasons as reason>
						<option value="${reason.code }">
							${reason.name }
						</option>
					</#list>
				</select>
				<#if reservationsOrderDetail.paymentStatus=='PAYED'>
					<input type="button" name="btnCancelOrder1"
								onclick="doCancelOrder('historyDiv','${(reservationsOrderDetail.orderNo)!}');" value="废 单"
								class="right-button08">
				<#else>
					<input type="button" name="btnCancelOrder1"
								onclick="doCancelLocalOrder('historyDiv','${(reservationsOrderDetail.orderNo)!}');" value="废 单"
								class="right-button08">
				</#if>
			<#else>
				订单状态：取消
			</#if>
			<input type="button" name="btnCloseOrdHis" class="left-button08"
				value="关闭" onclick="javascript:closeDetailDiv('historyDiv');" />
		<div class="popbox">
			<strong>取消规则</strong>
			<table class="newTable">
				<tr>
					<td>
						最晚取消时间
					</td>
					<td>
						取消费用的比率（%）
					</td>
					<td>
						取消费用
					</td>
					<td>
						取消晚数
					</td>
					<td>
						退款金额
					</td>
					<td>
						备注
					</td>
				</tr>
				<#if (voucher??) && (voucher?length>0)>
					<tr>
						<td>
							${(reservationsOrderDetail.lastCancelTime?string('yyyy-MM-dd HH:mm'))!}
						</td>
						<td>
							${(percentageCharged)!}
						</td>
						<td>
							${(firstPrice/100)!}
						</td>
						<td>
							<#if percentageOf=='F'>
								First Night
							<#else>
								Whole Reservation
							</#if>
						</td>
						<td>
							<#if reservationsOrderDetail.paymentStatus=='REFUNDED' || reservationsOrderDetail.paymentStatus=='PAYED'>
								${((reservationsOrderDetail.actualPay-firstPrice)/100)!}
							<#else>
								0
							</#if>
						</td>
						<td>
							${(remarks)!}
						</td>
					</tr>
				</#if>
			</table>
		</div>
		</p>
		<!-- 操作日志 -->
				<strong>操作日志</strong>
				<table style="font-size: 12px" cellspacing="1" cellpadding="4"
					border="0" bgcolor="#B8C9D6" width="100%" class="newfont03">
					<tr bgcolor="#f4f4f4" align="center">
						<td height="30">
							日志名称
						</td>
						<td>
							内容
						</td>
						<td>
							操作人
						</td>
						<td>
							创建时间
						</td>
						<td>
							备注
						</td>
					</tr>
					<#if comLogs??>
						<#list comLogs as log>
							<tr bgcolor="#ffffff" align="center">
								<td height="25">
									${(log.logName)!}
								</td>
								<td>
									${(log.content)!}
									<#if log.logType=='cancelToCreateNew_new'>
								老订单ID${log.parentId}
								</#if>
									<#if log.logType=='cancelToCreateNew_original'>
								新订单ID${log.parentId}
								</#if>
								</td>
								<td>
									${(log.operatorName)!}
								</td>
								<td>
									${(reservationsOrderDetail.createdTime?string('yyyy-MM-dd HH:mm:ss'))!}
								</td>
								<td>
									${(log.memo)!}
								</td>
							</tr>
						</#list>
					</#if>
				</table>
	</body>
</html>
