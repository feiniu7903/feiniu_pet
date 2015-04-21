<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>邮寄方式填写</title>
<#include "/common/commonJsIncluedTopNew.ftl"/>
<link href="http://pic.lvmama.com/styles/v4style/myspace_fapiao.css?r=3252" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
					/**
					 * 上海驴妈妈国际旅行社有限公司(门票、酒店、自由行)
					 */
	 				var COMPANY_1 = "COMPANY_1";
	 				/**
					* 上海景域文化传播有限公司(国内游)
					*/
					var COMPANY_2 = "COMPANY_2";
					/**
					 * 兴旅(无)
					 */
					var COMPANY_3 = "COMPANY_3";
					
					var deliveryType = '${invoiceForm.deliveryType}';
					var detail = '${invoiceForm.detail}';
			//页面初始化:
			$(document).ready(function () {					 				 
					 
					 //根据不同的结算主题公司选择不同的"发票明细"列表.
					 var COMPANY_TYPE_VALUE = '${companyType}';
					 if (COMPANY_TYPE_VALUE == COMPANY_1) {
					 	$('#COMPANY_1').show();
					 	$('#COMPANY_2').remove();
					 	$('#COMPANY_3').remove();
					 } else if (COMPANY_TYPE_VALUE == COMPANY_2) {
					 	$('#COMPANY_1').remove();
					 	$('#COMPANY_2').show();
					 	$('#COMPANY_3').remove();
					 } else if (COMPANY_TYPE_VALUE == COMPANY_3) {
					 	$('#COMPANY_1').remove();
					 	$('#COMPANY_2').remove();
					 	$('#COMPANY_3').show();
					 }
					 
					 //初始化快递费用:当发票金额大于300元时,快递费为0元,否则快递费为5元.
					 if ('${invoiceForm.amountYuan &gt;= 300}' == 'true') {
					 	$('#expressPayId').text('快递免费');
					 } else {
					 	$('#expressPayId').text('快递货到付款 5元');
					 }
					 
			});
			
			
		 	$(function(){
		 			$("a.close").click(function(){
		 				$("#addNewCustomAddressDiv").hide(300);
		 			});
		 		  //点击"快递货到付款"单选按钮.
		 		    $('#takeInvoiceByExpressRadio').click(function(){
			 		  	 $('#lvmamaAddressDiv').attr("style","display:none;");
			 		  	 $('#customAddressDiv').attr("style","display:block;");
			 		  	  deliveryType = $(this).val();
		 		  });
		 		  //点击"EMS货到付款"单选按钮.
		 		  $('#takeInvoiceByEmsRadio').click(function(){
			 		  	 $('#lvmamaAddressDiv').attr("style","display:none;");
			 		  	 $('#customAddressDiv').attr("style","display:block;");
			 		  	   deliveryType = $(this).val();
		 		  });
		 		  //点击"自取"单选按钮.
		 		   $('#takeInvoiceByMyselfRadio').click(function(){
			 		  	 $('#lvmamaAddressDiv').attr("style","display:block;");
			 		  	 $('#customAddressDiv').attr("style","display:none;");
		 		 		  deliveryType = $(this).val();
		 		 		 
		 		  });
		 		  //点击"新增地址"按钮.
		 		  $('#addNewCustomAddressBtn').click(function() {
		 		  		showEditDlg(false);
		 		  });
		 		  //点击"修改"超链接.
				 	$("a.update_btn").live("click",function() {
				 		var receiverId=$(this).attr("result");
				 		if(!receiverId){
				 			alert("没有选中要修改的地址");
				 			return;
				 		}
				 		
				 		$.post("${base}/usr/getReceivers.do",{"usrReceivers.receiverId":receiverId},function(dt){
				 			var data=eval("("+dt+")");
				 			if(data.success)
				 			{
				 				showEditDlg(true,data);
				 			}else{
				 				alert(data.msg);
				 			}
				 		});
				 	});
		 		  
		 		  //显示编辑地址框
		 		  function showEditDlg(edit,result){
		 		  		var $div=$('#addNewCustomAddressDiv');
		 		  		var offset=$("a[name=address_pos]").offset();
		 		  		$div.css("top",(offset.top+16)+"px").css("left","0px");
		 		  		if(edit){			 		  			
		 		  			var data=result.info;
		 		  			$div.find("input[name=receiverId]").val(data.receiverId);
		 		  			$div.find("input[name=receiverName]").val(data.receiverName);
		 		  			$div.find("input[name=mobileNumber]").val(data.mobileNumber);
		 		  			$div.find("select[name=province] option[value="+data.province+"]").attr("selected",true);
		 		  			
		 		  			var citys=result.citys;
		 		  			var $selectCity=$div.find("select[name=city]");
		 		  			$selectCity.empty(); 
		 		  			if(typeof(citys)!=undefined){
		 		  			for(var i=0;i<citys.length;i++){
								var city=citys[i];
								var $option=$("<option/>");
								$option.text(city.cityName).val(city.cityId);
								$selectCity.append($option);
							}
							}else{
								var $option=$("<option/>");
								$option.text("请选择").val("");
								$selectCity.append($option);
							}
		 		  			
		 		  			$div.find("select[name=city] option[value="+data.city+"]").attr("selected",true);
		 		  			$div.find("input[name=address]").val(data.address);
		 		  			$div.find("input[name=postcode]").val(data.postCode);		  			
		 		  		}else{
		 		  			$div.find("input").val("");		 		  			
		 		  		}	
		 		  		$div.show(500);
		 		  }
		 		  
		 		  //点击确认地址按钮.
		 		  $('#confirmAddressBtn').click(function(){
		 		  		var $div=$('#addNewCustomAddressDiv');
		 		  		var receiverId=$div.find("input[name=receiverId]").val();
		 		  		var name=$div.find("input[name=receiverName]").val();
		 		  		var mobile=$div.find("input[name=mobileNumber]").val();
		 		  		var province=$div.find("select[name=province] option:selected").val();
		 		  		var city=$div.find("select[name=city] option:selected").val();
		 		  		var address=$div.find("input[name=address]").val();
		 		  		var postcode=$div.find("input[name=postcode]").val();
		 		  		
		 		  		if($.trim(name)==""){
		 		  			alert("收件人不可以为空");
		 		  			return false;
		 		  		}
		 		  		if($.trim(mobile)==""){
		 		  			alert("联系电话不可以为空");
		 		  			return false;
		 		  		}
		 		  		if($.trim(province)==''||$.trim(city)==''){
		 		  			alert("请选择省市");
		 		  			return false;
		 		  		}		 		  		
		 		  		if($.trim(address)==""){
		 		  			alert("收件地址不可以为空");
		 		  			return false;
		 		  		}
		 		  		
		 		  		if (!checkUserName(name)) {
                           alert("请填写有效的姓名");
                           $div.find("input[name=receiverName]").focus();
                           return false;
                        }
                         
                        if(!checkMobile(mobile)){
                           alert("手机号码不正确");
                           $div.find("input[name=mobileNumber]").focus();
                           return false;
                        }  
                         
                        var postCode = $("input[name='usrReceiver.postCode']").val();
                        if(postcode!=null && postcode!="" && !checkPostCode(postcode)){
                           alert("请填写正确邮编");
                           $div.find("input[name=postcode]").focus();
                           return false;
                        }
		 		  		
		 		  		var param={"usrReceivers.receiverId":receiverId,
		 		  					"usrReceivers.receiverName":name,
		 		  					"usrReceivers.mobileNumber":mobile,
		 		  					"usrReceivers.province":province,
		 		  					"usrReceivers.city":city,
		 		  					"usrReceivers.address":address,
		 		  					"usrReceivers.postCode":postcode};
		 		  		//保存地址信息
		 		  		$.post('${base}/usr/confirmAddress.do',param,function(dt){
		 		  			var data=eval("("+dt+")");
		 		  			if(data.success){
		 		  				$div.hide(300);
		 		  				loadReceiversList();		 		  				
		 		  			}else{
		 		  				alert(data.msg);
		 		  			}		 		  			
		 		  		});		 		  		
		 		  });
		 		  
		 		  function loadReceiversList(){
		 		  	$.post('${base}/usr/loadReceiversList.do',null,function(dt){
		 		  		$("#oldUsrReceiversListDiv").html(dt);
		 		  	});
		 		  }		 		  
		 		  
		 		  //点击"提交"按钮.
		 		  $('#submitId').click(function(){
						if ($('#invoiceTitleId').val() == '') {
		 					alert('发票抬头不能为空!');
		 					return false;
		 				}
						//配送方式为非自取并且没有有效的历史地址信息,需要提示用户填写有效的地址信息.
						if ($("input[name='invoiceForm.deliveryType']:checked").val() != 'SELF' && $("input[name='receiverId']:checked").val() == undefined) {
							alert('请填写有效的地址信息!');
							return false;
						}
						
		 		  		$('#invoiceForm').submit();
		 		  });
		 		  
		 		  //点击"返回"按钮.
		 		  $('#backId').click(function(){
		 		 	 window.location.href='${base}/usr/invoiceApply.do'; 		 
		 		  });
		 		  
		 		  
		 		  	$("select[name=province]").change(function(){
			var val=$(this).val();
			var $selectCity=$("select[name=city]");
			$selectCity.empty();
			if($.trim(val)!=''){
				$.post("${base}/usr/citys.do",{"provinceId":val},function(dt){
					//alert(dt);
					var data=eval("("+dt+")");
					//alert(data.list.length);
					for(var i=0;i<data.list.length;i++){
						//alert(i);
						var city=data.list[i];
						var $option=$("<option/>");
						//$option.val(city.cityId).text(city.cityName); //super_back/WEB-INF/pages/back/usrreceiver/insertAddress.jsp页面中此种用法正确,但这里出错.
						$option.text(city.cityName).val(city.cityId);
						$selectCity.append($option);
						//alert("dddd");
					}
				});
			}else{
				var $option=$("<option/>");
				$option.val("").text("请选择");
				$selectcity.append($option);
			}
		});
		 		 
		 	});
		 	
		 	
		 	//点击"删除"超链接.
		 	function deleteOperate(receiverId) {
		 		if(!receiverId){
		 			alert("没有选中要删除的地址");
		 			return;
		 		}
		 		
		 		$.post('${base}/usr/removeAddress.do',{"usrReceivers.receiverId":receiverId},function(dt){
		 			var data=eval("("+dt+")");
		 			if(data.success){
		 				$("#tb_"+receiverId).remove();
		 			}else{
		 				alert(data.msg);
		 			}
		 		});
		 	}
		 		 	
		 	/** 
			* 表单验证.
			*/
		 	function validateForm() {
		 		if ($('#invoiceTitleId').val() == '') {
		 			alert('发票抬头不能为空!');
		 			return false;
		 		}
		 		if ($('#receiverId').val() == '') {
		 			alert('收件人姓名不能为空!');
		 			return false;
		 		}
		 		if ($('#telId').val() == '') {
		 			alert('手机号码不能为空!');
		 			return false;
		 		}
		 		if ($('#addressId').val() == '') {
		 			alert('收件地址不能为空!');
		 			return false;
		 		}
		 		if ($('#postcodeId').val() == '') {
		 			alert('邮编不能为空!');
		 			return false;
		 		}
		 		
		 		if ($('#provinceId').val() == '') {
		 			alert('省份不能为空!');
		 			return false;
		 		}
		 		if ($('#cityId').val() == '请选择') {
		 			alert('城市不能为空!');
		 			return false;
		 		}
		 		return true;
		 	}		 
	</script>
</head>

<body>


<#--
<h2 class="fapiao_mess">发票申请查询<span class="tips01"><font color="#FF0000">*</font>为必填项</span></h2>-->
<form id="invoiceForm" action="${base}/usr/saveInvoice.do" method="post">
<input type="hidden" name="companyType" value="${companyType}" />
<input type="hidden" name="orderIds" value="${orderIds}" />

<div class="mess_contant">

  <h3>发票信息</h3> 
  <div style="margin-top:20px;"> 
   <table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr class="fapiao_heigh">
    <td width="106" align="right">订单号：</td>
    <td width="614">${orderIds}</td>
  </tr>
  <tr class="fapiao_heigh">
    <td align="right">发票金额：</td>
    <td><span class="price_red">￥${invoiceForm.amountYuan}</span></td>
  </tr>

  <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>发票抬头：</td>
    <td>
    	<input id="invoiceTitleId" name="invoiceForm.title" value="${invoiceForm.title}" type="text" value="个人" class="tait_text" />
   		<span class="tips002">(请准确填写发票抬头，发票一经开出，不可更改)</span>
    </td>
  </tr>
  <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>发票明细：</td>
    <td>
    	 <div id="COMPANY_1">
      	<select id="company2Select" name="invoiceForm.detail">
      	<option value="REGISTER_PROXY_CHARGE">住宿代理费</option>
      	<option value="TICKED_PROXY_CHARGE">门票代理费</option>
      	</select>
      </div>
      <div id="COMPANY_2">
      	<select id="company1Select" name="invoiceForm.detail">
      	<option value="REGISTER_PROXY_CHARGE">住宿代理费</option>
      	<option value="SERVICE_CHARGE">服务费</option>
      	<option value="TICKED_CHARGE">门票费</option>
      	<option value="TRAVEL_CHARGE" >旅游费</option>
      	<option value="COMMITTEE_CHARGE" >会务费</option>
      	</select>
      </div>
      
      <div id="COMPANY_3">
      	<select id="company3Select" name="invoiceForm.detail">
      	<option value="HOTEL_CHARGE">酒店房费</option>
      	<option value="FLIGHTTICKET_CHARGE">机票款</option>
      	<option value="TRAVEL_CHARGE">旅游费</option>
      	<option value="GROUP_CHARGE">团费</option>
      	</select>
      </div>
    </td>
  </tr>
  <tr>
    <td align="right" valign="top"  class="fapiao_heigh"><font color="#FF0000">*</font>配送方式：</td>
    <td>
    	<div class="fapiao_001">
    		<input type="radio"  class="refer_click" name="invoiceForm.deliveryType" value="EXPRESS" id="takeInvoiceByExpressRadio" checked="checked"><span id="expressPayId">快递货到付款　<font color="#d3189d">5元</font>   </span> 　
			<input type="radio"  class="refer_click" name="invoiceForm.deliveryType" value="EMS" id="takeInvoiceByEmsRadio">EMS货到付款 <font color="#d3189d">20元　　</font>
			<input type="radio"  class="refer_click" name="invoiceForm.deliveryType" value="SELF" id="takeInvoiceByMyselfRadio" />自取　<font color="#666666">免费</font>
		</div>
		<!-- lvmama地址DIV. -->
    	<div id="lvmamaAddressDiv" class="tips_003" style="display:none">
        <h3>领取发票方式：</h3>
 			<p style="margin-top:10px;">请您携带游玩人身份证复印件到驴妈妈领取</p>  
  			<p>领取地址：上海市金沙江路3131号上海国际信息服务产业园3号楼2楼</p>   
  			<p>请提前与<!--69108666-7893，-->楚静联系</p>     
        </div>
    </td>
  </tr>
      <tr class="fapiao_heigh">
<td align="left" colspan="2"><div class="tips004"><strong>[温馨提示]</strong> 开票地为上海，如需申请其他分公司所开发票，请先致电1010-6060询问。</div></td>
  </tr>
  </table>
  
  <!-- 客户地址DIV -->
  <div id="customAddressDiv" style="display:block" > 
  <table width="720" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td align="center" valign="top"><hr /></td>
    </tr>
    <tr>
    	<td align="left" valign="top"><a name="address_pos"></a><h3>收件地址</h3></td>
    </tr>
  </table>
  <!-- 已存在的地址列表. -->
    <div id="oldUsrReceiversListDiv" style="max-height: 200px;overflow-y: auto;width:680px">
    	<#include "/WEB-INF/pages/usr/invoice/receivers_list.ftl"/>
    </div>
  
  <table width="680" border="0" cellspacing="0" cellpadding="0"> 
  <tr class="fapiao_heigh">
    <td align="right"></td>
    <td><input id="addNewCustomAddressBtn" name="addNewCustomAddressBtn" type="button" value="新增地址" /></td>
  </tr>
  </table>
  <table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	<td >
  	<div class="tips004"><strong>[温馨提示]</strong> 如果您有多条发票要申请寄往同一地址，您仅需支付一次费用</div>
  	</td>
  </tr>
</table>
</div>
<table>
<tr class="fapiao_heigh">
    <td align="right" valign="middle">备注：</td>
    <td>        <textarea name="invoiceForm.memo" cols="" style="height:30px;" class="text_area"></textarea>
    <!-- <br /><input name="" type="button"  class="mess_bt01"/> -->
    </td>
  </tr>
</table>
</div>
<div>
	<table>
		<tr><td width="120"><input id="submitId" type="button"  class="mess_bt001" style="color:#fff;font-weight:bold;" value="提交"/> </td>
		<td><input id="backId" type="button" class="mess_bt001" style="color:#fff;font-weight:bold;"  value="返回"/></td></tr>
	</table>
</div>
</form>
<div id="addNewCustomAddressDiv" style="padding:10px 30px;display:none;z-index:10000;position: absolute;width:660px;border:1px solid #ccc;background-color:#fefefe;height: 239px;">
<div style="text-align:right"><a href="javascript:void(0);" class="close">关闭</a></div>
<form onsubmit="return false" method="post">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <input name="receiverId" value="" type="hidden"/>
    <tr class="fapiao_heigh">
    
    <td align="right"><font color="#FF0000">*</font>收件人姓名：</td>
    <td><input name="receiverName" type="text" value="" class="refer_click" /></td>
  </tr>
  <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>手机号码：</td>
    <td><input name="mobileNumber" type="text" value="" class="refer_click" /></td>
  </tr>
    <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>省份：</td>
    <td>
    <select name="province">
    									<#list provinceList as province>
										<option value="${province.provinceId}" <#if usrReceivers.province == province.provinceId>selected</#if>>${province.provinceName}</option>
										</#list>
									</select>
									<select name="city">
										<#if cityList??&&cityList?size >0>
											<#list cityList as city>
											<option value="${city.cityId}" <#if city.cityId == usrReceivers.city>selected</#if>>${city.cityName}</option>
											</#list>
										
										<#else>
										<option value="">请选择</option>										
										</#if>
									</select>
    </td>
  </tr>
    <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>收件地址：</td>
    <td><input name="address" type="text" value="" class="tait_text" maxlength="150"/></td>
  </tr>
  
  <tr class="fapiao_heigh">
    <td align="right"> 邮编：</td>
    <td><input name="postcode" type="text"/></td>
  </tr>
  <tr class="fapiao_heigh">
    <td align="right"></td>
    <td><button id="confirmAddressBtn" type="button">确认地址</button></td>
  </tr>
  </table></form>
  </div>
</body>
</html>
