<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="http://e.lvmama.com/ebooking/js/base/jquery.form.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
</head>
<body id="body_ddgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>订单处理</li>
    	<li>线路订单处理</li>
    </ul>
</div><!--以上是公用部分-->
 
<!--订单详情-->
<form action="${basePath }ebooking/task/orderTaskSave.do" id="confirm" method="post">
<input id="waitTime" name="waitTime" value="" type="hidden">
<input id="versionId" name="version" value="${ebkTask.ebkCertificate.version }" type="hidden">
<input id="TaskId" name="ebkTaskId" value='<s:property value="ebkTask.ebkTaskId"/>' type="hidden">

<div class="dingdan_cl" id="print_content">
	<h3>订单详情</h3>
	<h1 class="big_title">${ebkTask.ebkCertificate.zhEbkCertificateType}</h1>
    <ul class="dingdan_cl_list">
    	<li>
            <span class="cl_p_1">订单号：</span>
            <p class="cl_p_1">${ebkTask.orderId}</p>
        </li>
		<li>
		<s:if test="ebkTask.ebkCertificate.ebkCertificateType=='CHANGE'">
			<span>变更说明：</span>
            <p><b class="c_em">${ebkTask.ebkCertificate.ebkCertificateData.changeInfo}</b></p>
		</s:if>
		</li>
        <li>
            
            <span>出发时间：</span>
            <p>${ebkTask.ebkCertificate.zhVisitTime}</p>
        </li>
        <li>
         
            <span>产品ID：</span>
            <p>${ebkTask.ebkCertificate.mainMetaProductID}</p>
        </li>
        <li>
            
          <span>产品名称：</span>
          <p>${ebkTask.ebkCertificate.mainMetaProductName }</p>
         </li>
        <li>
			<table class="table-xline table-thdcenter">
			    <tr>
					<th>类别</th>
					<th>份数</th>
					<th>成人数</th>
					<th>儿童数</th>
					<s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.showSettlementFlag == 'true'">
					<th>结算单价</th>
					</s:if>
				</tr>
				<s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.items" id="item">
					<tr>
						<td>${item.baseInfo.metaBranchName}</td>
						<td>${item.baseInfo.quantity}</td>
						<td>${item.baseInfo.adultQuantity}</td>
						<td>${item.baseInfo.childQuantity}</td>
						<s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.showSettlementFlag == 'true'">
						<td>${item.baseInfo.settlementPrice}</td>		
						</s:if>
					</tr>
				</s:iterator>
				<s:if test="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.showSettlementFlag == 'true'">
				<tr>
					<td colspan="5" class="tr"><b>结算总价：</b> <b class="orange"><s:property value="ebkTask.ebkCertificate.ebkCertificateData.certificate.baseInfo.totalSettlementPrice"/></b>元</td>
				</tr>
				</s:if>
			</table>
			
			<table class="table-xline table-thdcenter">
			    <tr>
					<th>游玩人姓名</th>
					<th>手机号</th>
					<th>证件类型</th>
					<th>证件号码</th>
				</tr>
				<s:iterator value="ebkTask.ebkCertificate.ebkCertificateData.certificate.travellerList" id="person">
				<tr>
					<td>${person.name}</td>				
					<td>${person.mobile}</td>
					<td>${person.zhCertType}</td>
					<td>${person.certNo}</td>
				</tr>
				</s:iterator>
			</table>
		</li>
        <li>
			<span>特殊要求：</span>
            <p class="width_800">
            	<s:if test="ebkTask.ebkCertificate.allUserMemo !=''">${ebkTask.ebkCertificate.allUserMemo }</s:if>
            	<s:else>无</s:else>
            </p>
        </li>
        <li class="li_hr"></li> 
        <s:if test="ebkTask.ebkCertificate.ebkCertificateType=='CHANGE'||ebkTask.ebkCertificate.ebkCertificateType=='ENQUIRY'">
			<li class="margin_b_5">
	        	<span>订单处理：</span>
	            <p>
	            	<select name="certificateStatus" id="status" onchange="showReason(this);">
	                	<option value="ACCEPT">接受</option>
	                    <option value="REJECT">不接受</option>
	                </select>
	            </p>
	        </li>
        </s:if>
        <s:else><input id="CertificateStatus" name="CertificateStatus" value="ACCEPT" type="hidden"></s:else>
        <s:if test="ebkTask.ebkCertificate.ebkCertificateType=='ENQUIRY'">
		<li id="reasonLi">
        	<span>支付等待时间：</span>
            <p>
			    <input type="text" placeholder="请选择时间" id="txtWaitTime" name="txtWaitTime">
				<select id="txtWaitHour" name="txtWaitHour">
				    <option value="00">00</option>
				    <option value="01">01</option>
				    <option value="02">02</option>
				    <option value="03">03</option>
				    <option value="04">04</option>
				    <option value="05">05</option>
				    <option value="06">06</option>
				    <option value="07">07</option>
				    <option value="08">08</option>
				    <option value="09">09</option>
					<option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
					<option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				</select>
				时
				<select id="txtWaitMin" name="txtWaitMin">
				    <option value="00">00</option>
				    <option value="01">01</option>
				    <option value="02">02</option>
				    <option value="03">03</option>
				    <option value="04">04</option>
				    <option value="05">05</option>
				    <option value="06">06</option>
				    <option value="07">07</option>
				    <option value="08">08</option>
				    <option value="09">09</option>
					<option value="10">10</option>
				    <option value="11">11</option>
				    <option value="12">12</option>
				    <option value="13">13</option>
				    <option value="14">14</option>
				    <option value="15">15</option>
				    <option value="16">16</option>
				    <option value="17">17</option>
				    <option value="18">18</option>
				    <option value="19">19</option>
					<option value="20">20</option>
				    <option value="21">21</option>
				    <option value="22">22</option>
				    <option value="23">23</option>
				    <option value="24">24</option>
				    <option value="25">25</option>
				    <option value="26">26</option>
				    <option value="27">27</option>
				    <option value="28">28</option>
				    <option value="29">29</option>
					<option value="30">30</option>
				    <option value="31">31</option>
				    <option value="32">32</option>
				    <option value="33">33</option>
				    <option value="34">34</option>
				    <option value="35">35</option>
				    <option value="36">36</option>
				    <option value="37">37</option>
				    <option value="38">38</option>
				    <option value="39">39</option>
					<option value="40">40</option>
				    <option value="41">41</option>
				    <option value="42">42</option>
				    <option value="43">43</option>
				    <option value="44">44</option>
				    <option value="45">45</option>
				    <option value="46">46</option>
				    <option value="47">47</option>
				    <option value="48">48</option>
				    <option value="49">49</option>
					<option value="50">50</option>
				    <option value="51">51</option>
				    <option value="52">52</option>
				    <option value="53">53</option>
				    <option value="54">54</option>
				    <option value="55">55</option>
				    <option value="56">56</option>
				    <option value="57">57</option>
				    <option value="58">58</option>
				    <option value="59">59</option>
				</select>
				分
            </p>
        </li>
        </s:if>
        <li>
        	<span>供应商备注：</span><p><textarea class="beizhu_in" id="memo" name="memo" onFocus="if(value=='若有特殊要求可在此处填写'){value=''}" onBlur="if (value ==''){value='若有特殊要求可在此处填写'}">若有特殊要求可在此处填写</textarea></p>
        </li>
        <li>
        	<a href="javascript:void(0)" class="submit_but but_width124" onclick="checkAndSubmit(this);">确认提交</a>
        </li>
    </ul>
</div>

</form>
<script type="text/javascript">
var bClicked = false;
function controlSumit() {
	if (bClicked) {
		return false;
	}
	bClicked = true;
	return true;
}
function checkAndSubmit(ele) {
	if($("#memo").val() == "若有特殊要求可在此处填写") {
		$("#memo").val("");
	}
	if($("#status").val() == "ACCEPT") {
		$("#waitTime").val($("#txtWaitTime").val() + " " + $("#txtWaitHour").val() + ":" + $("#txtWaitMin").val());
	}
	if($("#status").val()=="REJECT"&&$("#memo").val()==""){
		alert("若不接受，请输入不接受理由");
		$("#memo").focus();  
		return false;
	}
	//+" "+$("#txtWaitHour").val()+":"+$("#txtWaitMin")
	var options = {
			url:$("#confirm").attr("action"),
			success:function(data){
				var res = eval(data);
				if(res.success) {
					alert("操作成功");
					location = "${contextPath }/ebooking/task/enquireSeatOrderView.do?ebkTaskId=${ebkTask.ebkTaskId}";
					window.opener.location.reload();
				} else {
					bClicked = false;
					alert(res.msg);
				}
			}
		};
	if(!controlSumit()) {
		return false;
	}
	$("#confirm").ajaxSubmit(options);
}
function showReason(ele) {
	if($(ele).val() == "REJECT") {
		$("#reasonLi").hide();
	} else {
		$("#reasonLi").show();
	}
}
</script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
<script type="text/javascript">
$("body").ui("calendar",{
   input : "#txtWaitTime",
   parm:{dateFmt:'yyyy-MM-dd'}
})
</script>
</body>
</html>