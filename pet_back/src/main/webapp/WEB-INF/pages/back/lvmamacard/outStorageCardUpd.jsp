<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改出库</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />

</head>
<body>
<script type="text/javascript">
    $(function(){
    	$("#remarks").keyup(function(){
    		var l = 0; var a = $("#remarks").val().split("");
    		 for (var i=0;i<a.length;i++) {  
    			 if (a[i].charCodeAt(0)<299) {
    				 l++;  
    			} else {
    				l+=2;
    			} 
    		}
    		if(l>300){
    			alert("备注超过长度");
    			$("#remarks").val($("#remarks").val().substring(0,$("#remarks").val().length-1));
    		}
    	});
    	$(".amountModel").attr("id",null);
    	$("#addAmount").click(function(){
    		if($(".detail").size()<8){
    			var _detail = $(".detail:first").clone(true);
        		_detail = $("<tr>").append($(_detail).show());
    			$(this).parent().parent().before(_detail);
    			$(".p_label:first").attr("rowspan",$(".p_label:first").attr("rowspan")+1);
    		}
    	});
    	$("input:[type=text]").add("select").blur(function(){
    		if($(this).attr("class") == "proSel")
    			return false;
    		countAmount();
    	});
    	$("#btn_ok").click(function(){
    		if(confirm("确定修改出库？")){
    			var i = 0;
    			$(".detail").each(function(index){
    				if(index != 0){
    					_amount = $(this).find(".amountModel").val();
    					_count = $(this).find(".outDetailCount").val();
    					if(!isNaN(_amount) && !isNaN(_count)){
    						if(_count * _amount > 0){
    							$(this).find(".amountModel").attr("name","storedCardOut.details["+i+"].outDetailsAmount");
    							$(this).find(".outDetailCount").attr("name","storedCardOut.details["+i+"].outDetailsCount");
    							i++;
    						}
    					}
    				}
    			});
    			if(i==0){
    				alert('请填写出库卡信息');
    				return false;
    			}
    			$.ajax({type : "POST",
 		           url : "${basePath}outStorage/outStorageUpd.do",data : $("#form1").serialize(),
 		           success : function(json) {
 		               if(json=="true"){
 		                   alert("修改成功");
 		                   location.href = '${basePath}outStorage/outStoragePre.do'; 
 		               }else{
 		                   alert("修改失败!");
 		               }
 		           }
 			   });
    			 
    		}
    	});
    });

</script>
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
        <div class="p_box">
            <form action="${basePath}outStorage/outStorageAdd.do" name="form1" method="post" id="form1">
            <input type="hidden" name="outCode" value="${storedCardOut.outCode}" />
				<table class="p_table form-inline" width="100%">
					<td style="display: none" class="detail">面值：<s:select cssClass="amountModel" list="amountModel" listKey="elementValue" listValue="elementValue" headerKey="0" headerValue="请选择"></s:select>
							数量：<input class="outDetailCount onlyNum2" />
						</td>
					<tr>
						<td rowspan="${2 + fn:length(detailsList)}" class="p_label">出库卡信息</td>
					</tr>
				<s:iterator value="detailsList" var="b">
					<tr>
						<td class="detail">面值：<s:select name="amount" cssClass="amountModel" list="amountModel" listKey="elementValue" listValue="elementValue" value="#b.outDetailsAmount"  headerKey="0" headerValue="请选择" ></s:select>
							数量：<input  value="${b.outDetailsCount}" class="outDetailCount onlyNum2"  />
						</td>
					</tr>
				</s:iterator>
					<tr>
						<td>购买公司名称：<input name="saleToCompany"  value="${storedCardOut.saleToCompany}" />&nbsp;&nbsp;合同号：<input name="contractId" value="${storedCardOut.contractId}" />&nbsp;&nbsp;<input id="addAmount" class="btn btn-small w5" type="button" value="增加面值"></td>
					</tr>
					<tr> 
						<td rowspan="2" class="p_label">销售人信息:</td>
						<td>销售人员：<input type="text" name="salePerson" value="${storedCardOut.salePerson}" /></td>						
					</tr>
					<tr>
						<td>公司：<select class="four_linkage" name="salePersonInfor1" id="Hierarchy1">
		<option value="">请选择...</option>
	</select>
	<select class="four_linkage" name="salePersonInfor2" id="Hierarchy2">
		<option value="">请选择...</option>
	</select>
	<select class="four_linkage" name="salePersonInfor3" id="Hierarchy3">
		<option value="">请选择...</option>
	</select>
	<select class="four_linkage" name="salePersonInfor4" id="Hierarchy4">
		<option value="">请选择...</option>
	</select></td>
					</tr>
					<tr>
						<td rowspan="2" class="p_label">销售信息</td>
						<td>销售折让额度：
							<input style="width:20px;" id="saleDiscountAmount" value="${storedCardOut.saleDiscountAmount}" name="saleDiscountAmount" readonly="readonly" />%，
							<input id="saleDisMoney" name="saleDisMoney" value="${storedCardOut.saleDisMoney}" readonly="readonly" />元&nbsp;客户返点：
							<input id="saleRebate" class="onlyNum" onpaste="return false" style="ime-mode:disabled;display: none" name="saleRebate" value="${storedCardOut.saleRebate}" />
							<input id="saleRebateMoney" class="onlyNum" onpaste="return false" style="ime-mode:disabled;" value="${storedCardOut.saleRebateMoney}"  name="saleRebateMoney" />
							&nbsp;<select id="rebate" name="rebate" class="proSel">
										<option value="1">金额（元）</option>
										<option value="0">比例（%）</option>
								</select>
						</td>						
					</tr>
					<tr>
						<td>销售奖金：<input id="saleBonus" name="saleBonus" value="${storedCardOut.saleBonus}" readonly="readonly"  style="display: none"/>
							<input id="saleBonusMoney"  readonly="readonly" name="saleBonusMoney" value="${storedCardOut.saleBonusMoney}" />
							&nbsp;<select id="bonus" name="bonus"  disabled="disabled">
							<option value="1">金额（元）</option>
							<option value="0">比例（%）</option></select>
							&nbsp;销售总额：<input id="saleSum" name="saleSum" value="${storedCardOut.saleSum}" readonly="readonly" />元
						</td>
					</tr>
					<tr>
						<td colspan="3"><input class="radio" name="saleFlag" <s:if test='storedCardOut.saleFlag==0'>checked="true"</s:if> type="radio" value="0"/>销售&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input  class="radio" name="saleFlag" type="radio" <s:if test="storedCardOut.saleFlag==1">checked="true"</s:if> value="1" />其他 </td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><s:if test="storedCardOut.saleFlag==0"><s:textarea readonly="true"  value="%{storedCardOut.remarks}" name="remarks" id="remarks" cssStyle="width:90%" rows="6" ></s:textarea></s:if>
							<s:else><s:textarea   value="%{storedCardOut.remarks}" name="remarks" id="remarks" cssStyle="width:90%" rows="6" ></s:textarea></s:else>
	        				<div id="limitTips"></div>
        				</td>
					<tr>					
                </table>
				<p class="tc mt10"><input type="button" name="con" id="btn_ok" class="btn btn-small w3"  value="修&nbsp;&nbsp;改" />&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;<input type="button" id="btn_close" onclick="javascript:location.href = '${basePath}outStorage/outStoragePre.do'" class="btn btn-small w3"  value="取&nbsp;&nbsp;消" />
				</p>	
            </form>
        </div>
    </div>

<script>
$(function(){ 

	var data1 = ${json};
	var num = 0;
	var Hierarchy1 = document.getElementById('Hierarchy1');
	var Hierarchy2 = document.getElementById('Hierarchy2');
	var Hierarchy3 = document.getElementById('Hierarchy3');
	var Hierarchy4 = document.getElementById('Hierarchy4');
	for(var i=0;i<data1.length;i++){
		if(data1[i].superId==0){
			Hierarchy1[num+1]=new Option(data1[i].name,data1[i].id);
			num+=1;
		};
	};
	$('.four_linkage').change(function(){ 
		var thisN= $(this).index();
		var thisV= $(this).attr('value');
		var num2 = 0 ;
		if(thisN==0){
			Hierarchy2.length=Hierarchy3.length=Hierarchy4.length=1;
		}else if(thisN==1){
			Hierarchy3.length=Hierarchy4.length=1;
		}else if(thisN==2){
			Hierarchy4.length=1;
		};
		for(var i=0;i<data1.length;i++){
			if(data1[i].superId==thisV){
				if(thisN==0){
					Hierarchy2[num2+1]=new Option(data1[i].name,data1[i].id);
				}else if(thisN==1){
					Hierarchy3[num2+1]=new Option(data1[i].name,data1[i].id);
					
				}else if(thisN==2){
					Hierarchy4[num2+1]=new Option(data1[i].name,data1[i].id);
				}
				num2+=1;
			};
		};
	});
	var optionId = ['${storedCardOut.salePersonInfor1}','${storedCardOut.salePersonInfor2}','${storedCardOut.salePersonInfor3}','${storedCardOut.salePersonInfor4}'];
	var selectName = $('.four_linkage');
	selectName.each(function(){
		var thisFind = $(this).find('option');
		for(var j=1;j<thisFind.length;j++){
			for(var i=0;i<optionId.length;i++){
				if(thisFind.eq(j).attr('value')==optionId[i]){
					selectName.get(i).selectedIndex=j;
					selectName.eq(i).change();
				}
			};
		}
	});
});
</script>        
    
</body>
</html>