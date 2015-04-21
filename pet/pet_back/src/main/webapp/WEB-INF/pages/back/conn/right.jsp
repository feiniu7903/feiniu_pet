<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form action='<%=request.getContextPath() %>/callCenter/saveConnRecord.do' id="feedBackForm" name="feedBackForm" method="post">
<input type="hidden" name="connRecord.mobile" id="mobile" value="<s:property value='callerid'/>"/>
<input type="hidden" name="connRecord.userId" id="userId" value="<s:property value='userUser.id'/>"/>
<input type="hidden" name="userNo" id="userNo" value="<s:property value='userUser.userNo'/>"/>
<input type="hidden" name="userName" id="userName" value="<s:property value='userUser.userName'/>"/>
<input type="hidden" name="connRecord.callTypeId" id="callTypeId" value=""/>
<input type="hidden" name="connRecord.serviceType" id="serviceType" value=""/>
<input type="hidden" name="connRecord.subServiceType" id="subServiceType" value=""/>
<input type="hidden" name="connRecord.callBack" id="isCallBack" value="<s:property value='callBack'/>"/>
<input type="hidden" name="connRecord.operatorUserId" id="operatorUserId" value="<s:property value='#request.operatorUserName'/>"/>

<p align="left"><b><br/>&nbsp;订单记录：</b>已完成订单数：<font color="red"><span id="finishedOrdersCountSpan">0</span></font>个。已完成订单总金额：<font color="red"><span id="finishedOrdersAmountSpan">0</span></font>元
	<input type="button" value="短信服务" id="sms_pop_btn" style="margin-left:10px;"/>
	<s:if test="activity!=null">
		&nbsp;&nbsp;&nbsp;
		渠道来源：<s:select name="connRecord.channelId" list="channelList" listKey="channelId" listValue="name"  headerKey="" headerValue="请选择" />
		&nbsp;&nbsp;
		统计时间为：<s:date name="activity.beginDate" format="yyyy.MM.dd" />-<s:date name="activity.endDate" format="yyyy.MM.dd" />
	</s:if>
</p> 
<TABLE style="FONT-SIZE: 12px" border=0 cellSpacing=0 cellPadding=4
	class="tab1-cc tab1-cc-3">
	<thead>
		<TR align=middle class="tr1">
			<TD width="10%" align="left" valign="middle">
				订单号
			</TD>
			<TD width="15%" align="left" valign="middle">
				下单时间
			</TD>
			<TD width="50%" align="left" valign="middle">
				产品名称
			</TD>
			<TD width="10%" align="left" valign="middle">
				数量
			</TD>
			<TD align="left" valign="middle">
				订单金额
			</TD>
		</TR>
	</thead>
	<tbody id="orderListId">
	</tbody>
</TABLE>

<!-- 新系统的订单列表 -->
<p align="left"><b><br/>&nbsp;订单记录：</b>已完成订单数：<font color="red"><span id="finishedOrdersCountSpanNew">0</span></font>个。已完成订单总金额：<font color="red"><span id="finishedOrdersAmountSpanNew">0</span></font>元
	<input type="button" value="短信服务" id="sms_pop_btn_new" style="margin-left:10px;"/>
</p> 
<TABLE style="FONT-SIZE: 12px" border=0 cellSpacing=0 cellPadding=4
	class="tab1-cc tab1-cc-3">
	<thead>
		<TR align=middle class="tr1">
			<TD width="10%" align="left" valign="middle">
				订单号
			</TD>
			<TD width="15%" align="left" valign="middle">
				下单时间
			</TD>
			<TD width="50%" align="left" valign="middle">
				产品名称
			</TD>
			<TD width="10%" align="left" valign="middle">
				数量
			</TD>
			<TD align="left" valign="middle">
				订单金额
			</TD>
		</TR>
	</thead>
	<tbody id="newOrderListId">
	</tbody>
</TABLE>

<TABLE style="FONT-SIZE: 12px" border=0 cellSpacing=0 cellPadding=4
	class="tab1-cc tab1-cc-3">
	<TBODY>
		<TR align=middle class="tab1-cc-tr1">
			<TD width="20%" align="left" valign="middle">
				<span class="tab1-span-left">服务类型</span><span class="tab1-span2">*</span>
			</TD>
			<TD width="80%" colSpan=3 align=left valign="middle">
				<s:iterator var="pCallTypeList" value="connTypeList">
					<span class="tab1-span"><input type="radio" name="serviceTypeRadioName" onclick="onclickServiceType('<s:property value="callTypeId"/>','<s:property value="callTypeName"/>')" value='<s:property value="callTypeId"/>'/>  <s:property value="callTypeName"/></span>
				</s:iterator>
			</TD>
		</TR>
		<TR align=middle class="tab1-cc-tr2">
			<TD align="left" valign="middle">
				<span class="tab1-span-left">服务项</span>
				<span class="tab1-span2">*</span>
			</TD>
			<TD colSpan=3 align=left valign="middle" id="secondCallTypePanel">
				<BR>
			</TD>
		</TR>
		<s:if test='#parameters.isCallBack[0]=="Y"'>
		<TR align=middle class="tab1-cc-tr1">
			<TD align="left" valign="middle">
				产品类型
			</TD>
			<TD colSpan=3 align=left valign="middle" id="thirdCallTypePanel">
				<input type="radio" onclick="selectProductServer('ticket','TICKET','')" name="connRecord.businessType" value='门票'/>门票
				<input type="radio"  onclick="selectProductServer('hotel','HOTEL','')" name="connRecord.businessType" value='酒店'/>酒店
				<input type="radio"  onclick="selectProductServer('route','ROUTE','FREENESS')" name="connRecord.businessType" value='目的地自由行'/>目的地自由行
				<input type="radio"  onclick="selectProductServer('route','ROUTE','GROUP')" name="connRecord.businessType" value='短途跟团游'/>短途跟团游
				<input type="radio"  onclick="selectProductServer('route','ROUTE','GROUP_LONG')" name="connRecord.businessType" value='长途跟团游'/>长途跟团游
				<input type="radio"  onclick="selectProductServer('route','ROUTE','FREENESS_LONG')" name="connRecord.businessType" value='长途自由行'/>长途自由行
				<input type="radio"  onclick="selectProductServer('route','ROUTE','GROUP_FOREIGN')" name="connRecord.businessType" value='出境跟团游'/>出境跟团游
				<input type="radio"  onclick="selectProductServer('route','ROUTE','FREENESS_FOREIGN')" name="connRecord.businessType" value='出境自由行'/>出境自由行
				<input type="radio"  onclick="selectProductServer('train','TRAFFIC','TRAIN')" name="connRecord.businessType" value='火车票'/>火车票				
				<input type="radio"  onclick="selectProductServer('visa','OTHER','VISA')" name="connRecord.businessType" value='签证'/>签证
			</TD>
		</TR>
		<TR align=middle class="tab1-cc-tr2 productClass"  style="display:none">
			<TD align="left" valign="middle">
				<span class="tab1-span-left">产品需求</span>
			</TD>
			<TD colSpan=3 align=left valign="middle" id="productPanel">
				 <div id="productTypeForm">
				 	
				 </div>
 				 <div id="other" style="display: none;">
 				 <input type="hidden" id="currentProductType" name="productType"/>
 				 <input type="hidden" id="currentSubProductType" name="type"/>
 				 <input type="hidden" id="useType" name="useType" value="true"/>
 				 <input type="hidden" id="productId" name="connRecord.productId"/>
 				  <span id="productSuggestSpan">产品：<input type="text" value="" id="productSuggest"></span>&nbsp;&nbsp;&nbsp;
				 人数：<select name="connRecord.quantity">
				 		<option value="1">1</option>
				 		<option value="2">2</option>
				 		<option value="3">3</option>
				 		<option value="4">4</option>	
				 		<option value="5">5人以上</option>
				 	</select>&nbsp;&nbsp;&nbsp;
				 分公司：<select name="connRecord.filialeName" id="filialeNameSelect">
				 		<option value="">请选择</option>
				 		<option value="北京分部">北京分部</option>
				 		<option value="成都分部">成都分部</option>
				 		<option value="广州分部">广州分部</option>
				 		<option value="黄山办事处">黄山办事处</option>	
				 		<option value="杭州分部">杭州分部</option>
				 		<option value="上海总部">上海总部</option>
				 		<option value="三亚分部">三亚分部</option>
				 		<option value="厦门办事处">厦门办事处</option>
				 	</select>&nbsp;<input type="checkbox" name="connRecord.group" value="true"/>团购产品
				</div>
				 <div id="zone" style="display: none;" class="div">
					 区域：<select name="connRecord.productZone">
					 		
						</select>
				</div>
			</TD>
		</TR>
		</s:if>
		<TR align=middle class="tab1-cc-tr1">
			<TD align="left" valign="middle">
				内容：
			</TD>
			<TD colSpan=3 align=left valign="middle">
				<textarea id=memo rows=5 cols=70% name="connRecord.memo"></textarea><BR>
			</TD>
		</TR>
		<TR align=middle class="tab1-cc-tr2">
			<TD colSpan=4 align=center valign="middle">
				<INPUT value=提交 type="button" name="feedBackFormSubmitButton" id="feedBackFormSubmitButton" class="input1">
				<INPUT value="查看未处理工单" type="button" id="workOrderButton" class="input1" />
				<BR>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
<div id="productTypeContainer">
<div id="hotel" style="display: none;" class="div"><input type="hidden" name="connRecord.toPlaceName" id="place_toPlace_hotel"/>
	入住日期：<input type="text" value="" name="connRecord.visitTime" class="date" readonly="readonly">~<input type="text" value="" class="date" name="leaveTime" readonly="readonly">&nbsp;&nbsp;&nbsp;
		房型：<select id="branch_branchType" name="connRecord.branchTypeStr">
				<option value="">请选择</option>
				<option value="大床房">大床房</option>
				<option value="圆床房">圆床房</option>
				<option value="双床房">双床房</option>
				<option value="三人房">三人房</option>
				<option value="家庭房">家庭房</option>
				<option value="套房">套房</option>
				<option value="大床/双床">大床/双床</option>
				<option value="自定义">自定义</option>
				<option value="加床费">加床费</option>
			</select>&nbsp;&nbsp;&nbsp;<br/>
		目的地：<input type="text" value="" place="toPlace" iid="place_toPlace_hotel">&nbsp;&nbsp;&nbsp;
	晚数:<input type="text" value="" name="connRecord.day" class="justNumber"/>
</div>
<div id="ticket" style="display: none;" class="div"><input type="hidden" name="connRecord.toPlaceName" id="ticketToPlace"/>
		 游玩时间：<input type="text" name="connRecord.visitTime" value="" class="date" readonly="readonly">&nbsp;&nbsp;&nbsp;
		 目的地：<input type="text" value="" place="toPlace" iid="ticketToPlace">&nbsp;&nbsp;&nbsp;
 </div>
 <div id="visa" style="display: none;" class="div">
		 签证国家：<input type="text" value="" name="connRecord.visaCountry">&nbsp;&nbsp;&nbsp;
		 签证类型：<input type="text" value="" name="connRecord.visaType">&nbsp;&nbsp;&nbsp;
 </div>
 <div id="traffic" style="display: none;" class="div">
		
 </div>
<div id="route" style="display: none;" class="div"><input type="hidden" name="connRecord.fromPlaceName" id="place_fromPlace_route"/>
	 <input type="hidden" name="connRecord.toPlaceName" id="place_toPlace_route"/>
	  	出发地：<input type="text" value="" place="fromPlace" iid="place_fromPlace_route">&nbsp;&nbsp;&nbsp;
	目的地：<input type="text" value="" place="toPlace" iid="place_toPlace_route">&nbsp;&nbsp;&nbsp;
	天数:<input type="text" value="" name="connRecord.day" class="justNumber"/>
</div>
</div>
<div id="zone_foreign" style="display: none">
<option value="" selected="true">请选择</option>
<option value="港澳" tt="港澳">港澳</option>
<option value="澳新" tt="澳新">澳新</option>
<option value="中东非" tt="中东非">中东非</option>
<option value="南亚" tt="南亚">南亚</option>
<option value="美洲" tt="美洲">美洲</option>
<option value="游轮" tt="游轮">游轮</option>
</div>
<div id="zone_province" style="display: none">
<option value="">请选择</option>
<option value="宁夏" tt="640000">宁夏</option>
<option value="澳门" tt="F20000">澳门</option>
<option value="北京" tt="110000">北京</option>
<option value="甘肃" tt="620000">甘肃</option>
<option value="天津" tt="120000">天津</option>
<option value="陕西" tt="610000">陕西</option>
<option value="河北" tt="130000">河北</option>
<option value="广西 " tt="450000">广西 </option>
<option value="河南" tt="410000">河南</option>
<option value="山东" tt="370000">山东</option>
<option value="贵州" tt="520000">贵州</option>
<option value="海南" tt="460000">海南</option>
<option value="上海" tt="310000">上海</option>
<option value="安徽" tt="340000">安徽</option>
<option value="辽宁" tt="210000">辽宁</option>
<option value="广东" tt="440000">广东</option>
<option value="江西" tt="360000">江西</option>
<option value="四川" tt="510000">四川</option>
<option value="湖北" tt="420000">湖北</option>
<option value="吉林" tt="220000">吉林</option>
<option value="江苏" tt="320000">江苏</option>
<option value="香港" tt="F10000">香港</option>
<option value="云南" tt="530000">云南</option>
<option value="山西" tt="140000">山西</option>
<option value="西藏" tt="540000">西藏</option>
<option value="黑龙江" tt="230000">黑龙江</option>
<option value="湖南" tt="430000">湖南</option>
<option value="浙江" tt="330000">浙江</option>
<option value="福建" tt="350000">福建</option>
<option value="内蒙古" tt="150000">内蒙古</option>
<option value="青海" tt="630000">青海</option>
<option value="重庆" tt="500000">重庆</option>
<option value="新疆" tt="650000">新疆</option>
<option value="台湾" tt="F30000">台湾</option>
</div>
<div id="orderFeedBackList">
	<%@ include file="/WEB-INF/pages/back/conn/orderFeedBackList.jsp"%>
</div>
<div id="sms_form_container" style="display: none;">
	<%@ include file="/WEB-INF/pages/back/conn/send_sms_form.jsp"%>
</div>
<script type="text/javascript">
$('.justNumber').live('keyup', function() {
	var v = $.trim($(this).val());
	if (v.length > 0) {
		if (isNaN(v)) {
			alert("输入有误！");
			$(this).val("");
			return;
		}
		if (v.indexOf(".") > 0) {
			alert("输入有误！");
			$(this).val("");
			return;
		}
	}
	$(this).val(v);
});
var monitorCallcenterDlg=null;
$("#workOrderButton").click(function(){
	var radom=Math.random();
	monitorCallcenterDlg=$("<iframe id='monitor_callcenter' frameborder='0' ></iframe>").dialog({
		autoOpen: true, 
        modal: true,
        title : "查看未处理工单",
        position: 'center',
        width: 960, 
        height: 460
	}).width(940).height(460).attr("src","<%=request.getContextPath()%>/work/task/monitor_callcenter.do?status=UNCOMPLETED&workOrderMobileNumber=<s:property value='callerid'/>&radom="+radom);
});
function closePopWin(){
		monitorCallcenterDlg.dialog("close");
		$("#monitor_callcenter").empty();
}
</script>
