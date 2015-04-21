<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<s:set var="basePath"><%=request.getContextPath() + "/"%></s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>查看</title>
<s:include value="/WEB-INF/pages/pub/lvmamacard.jsp" />

</head>
<body>
<script type="text/javascript">
$(function(){
	$("#btn_print").click(function(){
		$("input:[type=button]").hide();
		$("#noPrintDiv").hide();
		window.print();
		$("input:[type=button]").show();
		$("#noPrintDiv").show();
	});
});

</script>
    <div id="popDiv" style="display: none"></div>
    <div class="iframe-content">
        <div class="p_box">

            <input type="hidden" name="outCode" value="${storedCardOut.outCode}" />
				<table class="p_table form-inline" width="100%">
					<tr>
						<td rowspan="${2 + fn:length(detailsList)}" class="p_label">出库卡信息</td>
					</tr>
				<s:iterator value="detailsList" var="b">
					<tr>
						<td class="detail">面值：<input readonly="readonly" value="${b.outDetailsAmount}" /> 
							数量：<input readonly="readonly" value="${b.outDetailsCount}" class="outDetailCount onlyNum2" style="width:50px;" />&nbsp;&nbsp;
							卡号范围：<input readonly="readonly" value="${b.cardNoBegin}~${b.cardNoEnd}" class="outDetailCount onlyNum2" style="width:180px;" />
						</td>
					</tr>
				</s:iterator>
					<tr>
						<td>购买公司名称：<input readonly="readonly" name="saleToCompany"  value="${storedCardOut.saleToCompany}" />&nbsp;&nbsp;合同号：<input  readonly="readonly" name="contractId" value="${storedCardOut.contractId}" /></td>
					</tr>
					<tr> 
						<td rowspan="2" class="p_label">销售人信息:</td>
						<td>销售人员：<input readonly="readonly" type="text" name="salePerson" value="${storedCardOut.salePerson}" /></td>						
					</tr>
					<tr>
						<td>公司：<select disabled="disabled" class="four_linkage" name="salePersonInfor1" id="Hierarchy1">
		<option value="">请选择...</option>
	</select>
	<select class="four_linkage" disabled="disabled" name="salePersonInfor2" id="Hierarchy2">
		<option value="">请选择...</option>
	</select>
	<select class="four_linkage" disabled="disabled" name="salePersonInfor3" id="Hierarchy3">
		<option value="">请选择...</option>
	</select>
	<select class="four_linkage" disabled="disabled" name="salePersonInfor4" id="Hierarchy4">
		<option value="">请选择...</option>
	</select></td>
					</tr>
					<tr>
						<td rowspan="2" class="p_label">销售信息</td>
						<td>销售折让额度：
							<input style="width:20px;" id="saleDiscountAmount" value="${storedCardOut.saleDiscountAmount}" name="saleDiscountAmount" readonly="readonly" />%，
							<input id="saleDisMoney" name="saleDisMoney" value="${storedCardOut.saleDisMoney}" readonly="readonly" />元&nbsp;<br>客户返点：
							<input id="saleRebate" readonly="readonly" class="onlyNum" onpaste="return false" style="ime-mode:disabled;display: none" name="saleRebate" value="${storedCardOut.saleRebate}" />
							<input id="saleRebateMoney" readonly="readonly" class="onlyNum" onpaste="return false" style="ime-mode:disabled;" value="${storedCardOut.saleRebateMoney}"  name="saleRebateMoney" />
							&nbsp;<select id="rebate2" name="rebate" class="proSel">
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
							&nbsp;<br>销售总额：<input id="saleSum" name="saleSum" value="${storedCardOut.saleSum}" readonly="readonly" />元
						</td>
					</tr>
					<tr>
						<td colspan="3"><input class="radio" disabled="disabled" name="saleFlag" <s:if test='storedCardOut.saleFlag==0'>checked="true"</s:if> type="radio" value="0"/>销售&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<input  class="radio" name="saleFlag" disabled="disabled" type="radio" <s:if test="storedCardOut.saleFlag==1">checked="true"</s:if> value="1" />其他 </td>
					</tr>
					<tr>
						<td>备注：</td>
						<td><s:textarea  readonly="true" value="%{storedCardOut.remarks}" name="remarks" id="remarks" cssStyle="width:90%" rows="6" ></s:textarea>
	        				<div id="limitTips"></div>
        				</td>
					<tr>					
                </table>
                <br>
                <div align="center">
                <input type="button"  id="btn_print" class="btn btn-small w3"  value="打&nbsp;&nbsp;印" />
                </div>
                <br>
                <div id="noPrintDiv">
               <form action="${basePath}outStorage/outStorageQuery.do" name="form1" id="form1" method="post">
               <input type="hidden" name="outCode" value="${outCode}" />
                <table class="p_table form-inline" width="100%">
                	<tr>
                		<td colspan="2">礼品卡情况</td>
                		<td colspan="5">已使用：${storedCard.used}&nbsp;未使用：${storedCard.notused }&nbsp;冻结：${storedCard.freeze}</td>
                	</tr>
                	<tr>
                		<td colspan="7">卡号：<input name="cardNo" value="${cardNo}" />&nbsp;状态：<select   name="storedCard.status">
                             <option   value="">全部</option>
                                     <s:iterator value="cardStatusList" var="var" status="st">
                                     <s:if test="storedCard.status == key">
                                     	<option selected="selected" value="${key}">${value}</option>
                                      </s:if>
                                      <s:else>
                                        <option   value="${key}">${value}</option>
                                        </s:else>
                                    </s:iterator>
                            </select> &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" class="btn btn-small w5" value="查&nbsp;&nbsp;询" />
                            &nbsp;&nbsp;&nbsp;<input type="button" onclick="javascript:location.href = '${basePath}outStorage/outStoragePre.do'" id="btn_close" class="btn btn-small w3"  value="返&nbsp;&nbsp;回" />
                            </td>
                		
                	</tr>
                	<tr>
                		<th>卡号</th>
                		<th>面值</th>
                		<th>已使用金额</th>
                		<th>未使用金额</th>
                		<th>有效期</th>
                		<th>卡状态</th>
                		<th>明细</th>
                	</tr>
                	 <s:iterator value="pagination.allItems" var="var" status="st">
                        <tr>
                            <td>${cardNo}</td>
                            <td>${amountFloat}</td>
                            <td>${amountFloat-balanceFloat}</td>
                            <td>${balanceFloat}</td>
                            <td><s:date name="overTime" format="yyyy年MM月dd日" /></td>
                            <td>${cnStatus}</td>
                            <td><a href="javascript:void(0)" onclick="javascript:loadPopDiv('${basePath}/cardManage/detail.do?cardNo=${cardNo}','明细',1000,350);">明细</a>&nbsp&nbsp </td>
                        </tr>
                 </s:iterator>
                  <tr>
                    <td colspan="4" align="right">总条数：<s:property  value="pagination.totalResultSize" /></td>
                    <td colspan="3">  
                        <div style="text-align: right;">
                            <s:property escape="false"   value="pagination.pagination" />
                        </div>
                    </td>
                </tr>
                </table>
                </form>
                </div>
				<p class="tc mt10">
					
				</p>	
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