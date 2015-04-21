<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<TABLE style="FONT-SIZE: 12px; margin-bottom: 10px;" border=0 cellSpacing=0 cellPadding=4 class="tab1-cc tab1-cc-3">
	<TBODY>
		<TR align=middle class="tr1">
			<TD width="10%" align="left" valign="middle">
				服务类型
			</TD>
			<TD width="10%" align="left" valign="middle">
				服务
			</TD>
			<TD width="10%" align="left" valign="middle">
				产品类型
			</TD>
			<TD align="left" valign="middle">
				交流内容
			</TD>
			<TD width="10%" align="left" valign="middle">
				电话号码
			</TD>
			<TD width="8%" align="left" valign="middle">
				交流类型
			</TD>
			<TD width="8%" align="left" valign="middle">
				时间
			</TD>
			<TD width="6%"  align="left" valign="middle">
				操作人
			</TD>
		</TR>
		
		<s:iterator var="orderFeedBackList" value="connRecordPage.items" status="starus">
		<TR class="tab1-cc-tr<s:property value="#starus.index%2+1"/>">
			<TD align="left" valign="middle">
				<s:property value="serviceType"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="subServiceType"/>
				<br/>
			</TD>
			<TD align="left" valign="middle" class="<s:if test='serviceType=="产品咨询"&&businessType!=null && businessType!="火车票"'>contentTip</s:if>" data="<s:property value='connRecordId'/>">
				<s:property value="businessType"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="memo"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="mobile"/>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:if test='serviceType=="短信"'>
					短信
				</s:if>
				<s:else>
					<s:if test='#orderFeedBackList.hasCallBack()'>来电</s:if><s:else>去电</s:else>
				</s:else>
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:date name="feedbackTime" format="yyyy-MM-dd HH:mm" />
				<br/>
			</TD>
			<TD align="left" valign="middle">
				<s:property value="operatorUserId"/>
				<br/>
			</TD>
		</TR>
		</s:iterator>
	</TBODY>
</TABLE>
<div id="contentTipDiv" style="position:absolute;width:50%;display:none;">
<TABLE id="ticketTab" class="tab1-cc tab1-cc-3" style="FONT-SIZE: 12px; width:100%;margin-top:0px;display:none;" border="0" cellSpacing="0" cellPadding="4">
	<TBODY>
		<TR align="middle" class="tr1">
			<TD width="10%" align="left" valign="middle">
				游玩时间
			</TD>
			<TD width="10%" align="left" valign="middle">
				目的地
			</TD>
			<TD width="10%" align="left" valign="middle">
				产品ID
			</TD>
			<TD width="10%" align="left" valign="middle">
				人数
			</TD>
			<TD width="10%" align="left" valign="middle">
				分公司
			</TD>
			<TD width="10%" align="left" valign="middle">
				是否团购
			</TD>
		</TR>
		<s:iterator var="orderFeedBackList" value="connRecordPage.items" status="starus">
			 <s:if test="serviceType=='产品咨询' && businessType=='门票'">
				<TR class="tab1-cc-tr1" style="display: none;'" id="tr<s:property value='connRecordId'/>">
					<TD align="left" valign="middle">
						<s:property value="visitTime"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="toPlaceName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="productId"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="quantity"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="filialeName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="zhGroup"/>
						<br/>
					</TD>
				</TR>
			</s:if>
		</s:iterator>
	</TBODY>
</TABLE>
<TABLE id="hotelTab" class="tab1-cc tab1-cc-3" style="FONT-SIZE: 12px; width:100%;margin-top:0px;display:none;" border="0" cellSpacing="0" cellPadding="4">
	<TBODY>
		<TR align="middle" class="tr1">
			<TD width="10%" align="left" valign="middle">
				入住日期
			</TD>
			<TD width="10%" align="left" valign="middle">
				房型
			</TD>
			<TD width="10%" align="left" valign="middle">
				目的地
			</TD>
			<TD width="10%" align="left" valign="middle">
				晚数
			</TD>
			<TD width="10%" align="left" valign="middle">
				产品ID
			</TD>
			<TD width="10%" align="left" valign="middle">
				人数
			</TD>
			<TD width="10%" align="left" valign="middle">
				分公司
			</TD>
			<TD width="10%" align="left" valign="middle">
				是否团购
			</TD>
		</TR>
		<s:iterator var="orderFeedBackList" value="connRecordPage.items" status="starus">
			 <s:if test="serviceType=='产品咨询' && businessType=='酒店'">
				<TR class="tab1-cc-tr1" style="display: none;'" id="tr<s:property value='connRecordId'/>">
					<TD align="left" valign="middle">
						<s:property value="visitTime"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="branchTypeStr"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="toPlaceName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="day"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="productId"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="quantity"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="filialeName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="zhGroup"/>
						<br/>
					</TD>
				</TR>
			</s:if>
		</s:iterator>
	</TBODY>
</TABLE>
<TABLE id="routeTab" class="tab1-cc tab1-cc-3" style="FONT-SIZE: 12px; width:100%;margin-top:0px;display:none;" border="0" cellSpacing="0" cellPadding="4">
	<TBODY>
		<TR align="middle" class="tr1">
			<TD width="10%" align="left" valign="middle">
				出发地
			</TD>
			<TD width="10%" align="left" valign="middle">
				目的地
			</TD>
			<TD width="10%" align="left" valign="middle">
				天数
			</TD>
			<TD width="10%" align="left" valign="middle">
				产品ID
			</TD>
			<TD width="10%" align="left" valign="middle">
				人数
			</TD>
			<TD width="10%" align="left" valign="middle">
				分公司
			</TD>
			<TD width="10%" align="left" valign="middle">
				区域
			</TD>
			<TD width="10%" align="left" valign="middle">
				是否团购
			</TD>
		</TR>
		<s:iterator var="orderFeedBackList" value="connRecordPage.items" status="starus">
			 <s:if test="serviceType=='产品咨询' && (businessType=='目的地自由行' || businessType=='短途跟团游' || businessType=='长途跟团游' || businessType=='长途自由行' || businessType=='出境跟团游' || businessType=='出境自由行')">
				<TR class="tab1-cc-tr1" style="display: none;'" id="tr<s:property value='connRecordId'/>">
					<TD align="left" valign="middle">
						<s:property value="fromPlaceName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="toPlaceName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="day"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="productId"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="quantity"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="filialeName"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="productZone"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="zhGroup"/>
						<br/>
					</TD>
				</TR>
			</s:if>
		</s:iterator>
	</TBODY>
</TABLE>
<TABLE id="visaTab" class="tab1-cc tab1-cc-3" style="FONT-SIZE: 12px; width:100%;margin-top:0px;display:none;" border="0" cellSpacing="0" cellPadding="4">
	<TBODY>
		<TR align="middle" class="tr1">
			<TD width="10%" align="left" valign="middle">
				签证国家
			</TD>
			<TD width="10%" align="left" valign="middle">
				签证类型
			</TD>
		</TR>
		<s:iterator var="orderFeedBackList" value="connRecordPage.items" status="starus">
			 <s:if test="serviceType=='产品咨询' && businessType=='签证'">
				<TR class="tab1-cc-tr1" style="display: none;'" id="tr<s:property value='connRecordId'/>">
					<TD align="left" valign="middle">
						<s:property value="visaCountry"/>
						<br/>
					</TD>
					<TD align="left" valign="middle">
						<s:property value="visaType"/>
						<br/>
					</TD>
				</TR>
			</s:if>
		</s:iterator>
	</TBODY>
</TABLE>
</div>
<script type="text/javascript">
$(function(){
	var $tooltip;
	$(document).ready(function(){
		$tooltip = $("#contentTipDiv");
		$("td.contentTip").live("mouseover", function() {
			var $this = $(this);
			var offset = $this.offset();
			$tooltip.css("top",(offset.top+8)+"px");
			$tooltip.css("left",(offset.left+$this.width()-2)+"px");
			$("table[id$='Tab']").hide();
			var type = $.trim($this.text());
			if(type == "门票") {
				$("#ticketTab").show();
			} else if(type == "酒店") {
				$("#hotelTab").show();
			} else if(type == "签证") {
				$("#visaTab").show();
			} else {
				$("#routeTab").show();
			}
			var id = $this.attr("data");
			$("tr[id^='tr']").hide();
			$("#tr" + id).show();
			$tooltip.show();
		}).live("mouseout", function() {
			if($tooltip){
				$tooltip.hide();
			}
		});
	});
})
</script>