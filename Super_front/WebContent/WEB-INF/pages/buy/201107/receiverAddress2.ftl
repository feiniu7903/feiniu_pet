<script type="text/javascript">
//---邮寄地址相关操作. 提供给未登录用户的邮寄地址输入框.--------------------------------------------------------------------//
	$(function(){
		 //省份、城市联动.
		$("select[name='receiverAddress.province']").change(function(){
			var val=$(this).val();
			var $selectCity=$("select[name='receiverAddress.city']");
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
</script>

<h3><s></s>邮寄地址信息	</h3>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <input name="physical" value="true" type="hidden"/>
    <tr class="fapiao_heigh">
    <td align="right" width="160"><font color="#FF0000">*</font>收件人姓名：</td>
    <td><input name="receiverAddress.receiverName" type="text" value="" class="refer_click" maxlength="16"/></td>
  </tr>
  <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>手机号码：</td>
    <td><input name="receiverAddress.mobileNumber" type="text" value="" class="refer_click" /></td>
  </tr>
    <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>省份：</td>
    <td>
    <select name="receiverAddress.province">
    									<#list provinceList as province>
										<option value="${province.provinceId}" <#if receiverAddress?? && receiverAddress.province == province.provinceId>selected</#if>>${province.provinceName}</option>
										</#list>
									</select>
									<select name="receiverAddress.city">
										<#if cityList??&&cityList?size >0>
											<#list cityList as city>
											<option value="${city.cityId}" <#if  receiverAddress?? &&  city.cityId == receiverAddress.city>selected</#if>>${city.cityName}</option>
											</#list>
										
										<#else>
										<option value="">请选择</option>										
										</#if>
									</select>
    </td>
  </tr>
    <tr class="fapiao_heigh">
    <td align="right"><font color="#FF0000">*</font>收件地址：</td>
    <td><input name="receiverAddress.address" type="text" value="" class="tait_text" maxlength="150"/></td>
  </tr>
  
  <tr class="fapiao_heigh">
    <td align="right"> 邮编：</td>
    <td><input name="receiverAddress.postCode" type="text"/></td>
  </tr>
  </table>

  
  
