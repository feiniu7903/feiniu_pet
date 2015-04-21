<script type="text/javascript">
//---邮寄地址相关操作. 提供给已登录用户的邮寄地址输入框.--------------------------------------------------------------------//
	$(function(){
				$("a.close").click(function(){
		 				$("#addNewCustomAddressDiv").hide(300);
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
				 		
				 		$.post("${base}/buy/getReceivers.do",{"usrReceivers.receiverId":receiverId},function(dt){
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
		 		  		var $oldUsrReceiversListDiv = $("#oldUsrReceiversListDiv");
		 		  		var offset=$oldUsrReceiversListDiv.offset(); 
		 		  		$div.css("top",(offset.top-2)+"px").css("left",(offset.left+2)+"px");
		 		  		
		 		  		if(edit){		 		  			
		 		  			var data=result.info;
		 		  			$("#receiverId_2").val(data.receiverId);
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
		 		  		var receiverId=$("#receiverId_2").val();
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
		 		  		if($.trim(province)=='' || $.trim(province)=='请选择'  ||$.trim(city)=='' || $.trim(city)=='请选择'){
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
		 		  		$.post('${base}/buy/confirmAddress.do',param,function(dt){
		 		  			var data=eval("("+dt+")");
		 		  			if(data.success){
		 		  				$div.hide(300);
		 		  				loadReceiversList();		 		  				
		 		  			}else{
		 		  				alert(data.msg);
		 		  			}		 		  			
		 		  		});		 		  		
		 		  });
		 		  //加载邮寄地址列表.
		 		  function loadReceiversList(){
		 		  	$.post('${base}/buy/loadReceiversList.do',null,function(dt){
		 		  		$("#oldUsrReceiversListDiv").html(dt);
		 		  	});
		 		  }	
		 		  
		 //省份、城市联动.
		$("select[name=province]").change(function(){
			var val=$(this).val();
			var $selectCity=$("select[name=city]");
			$selectCity.empty();
			if($.trim(val)!=''){
				$.post("${base}/buy/citys.do",{"provinceId":val},function(dt){
					var data=eval("("+dt+")");
					for(var i=0;i<data.list.length;i++){
						var city=data.list[i];
						var $option=$("<option/>");
						$option.text(city.cityName).val(city.cityId);
						$selectCity.append($option);
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
				//删除地址时，首先判断地址列表，如果仅剩一个地址，不再允许被删除；
		 		//注：这里的小于等于2是因为存在一个不在地址列表中但name为"receiverId"的input表单元素.
		 		if ($("input[name='receiverId']").length <= 1) {
		 			alert("地址列表不能为空!");
		 			return;
		 		}
		 		if(!receiverId){
		 			alert("没有选中要删除的地址");
		 			return;
		 		}
		 		$.post('${base}/buy/removeAddress.do',{"usrReceivers.receiverId":receiverId},function(dt){
		 			var data=eval("("+dt+")");
		 			if(data.success){
		 				$("#tb_"+receiverId).remove();
		 			}else{
		 				alert(data.msg);
		 			}
		 		});
		 	}
		 	
		 	

</script>



<h3><s></s>邮寄地址信息	</h3>
	<input name="physical" value="true" type="hidden"/>
    <!-- 已存在的地址列表. -->
    <div id="oldUsrReceiversListDiv" style="overflow-y: auto;">
    	<#include "/WEB-INF/pages/buy/201107/receivers_list.ftl"/>
    </div>
 <table width="680" border="0" cellspacing="0" cellpadding="0"> 
  <tr class="fapiao_heigh">
    <td align="right"></td>
    <td>&nbsp;&nbsp;
    	 <input id="addNewCustomAddressBtn" class="add_new_bt" type="button" name="addNewCustomAddressBtn" value="新增地址" />
    </td>
  </tr>
  </table>
<div id="addNewCustomAddressDiv" style="padding:10px 30px;display:none;z-index:10000;position: absolute;width:660px;border:1px solid #ccc;background-color:#fefefe;height: 239px;">
<div style="text-align:right"><a href="javascript:void(0);" class="close">关闭</a></div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <input id="receiverId_2" value="" type="hidden"/>
    <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>收件人姓名：</td>
    <td><input name="receiverName" type="text" value="" class="refer_click" maxlength="16"/></td>
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
  </table>
  </div>
  
  
