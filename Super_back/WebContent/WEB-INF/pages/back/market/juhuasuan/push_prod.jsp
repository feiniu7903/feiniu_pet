<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设置产品聚划算信息</title>
<style type="text/css">
	#cateForm td{line-height: 25px;font-size: 13px;}
	#cateForm td a{color: blue;}
	#cateForm td span {font-size: 12px;color: gray;}
	#cateForm .textStyle{text-align: right;padding-left: 5px;width: 150px;}
	#saveBtn,#saveAndPushBtn{height: 25px;width: 70px; margin-right: 8px;}
</style>
<script type="text/javascript" >
//保存产品信息
	function saveMsg(){
		if(!checkDetailProduct()){
			return false;
		}
		if(confirm("是否保存信息？")){
			$("#cateForm").ajaxSubmit({
			    url: "/super_back/juhuasuanProd/save.do",
			    dataType:'json',
			    type: "POST",
			    success: function(myJSON){
			        if (myJSON.flag == "success") {
			        	alert("保存成功!");
			    		//$("#query_form").submit();
			        } else {
			        	alert(myJSON.msg);
			        }
			    }
			});
		}
	}
	function saveAndPush(){
		if(!checkDetailProduct()){
			return false;
		}
		if(confirm("是否保存并发布信息？")){
			$("#cateForm").ajaxSubmit({
			    url: "${basePath}/juhuasuanProd/saveAndPush.do",
			    dataType:'json',
			    type: "POST",
			    success: function(myJSON){
			        if (myJSON.flag == "success") {
			        	alert("操作成功!");
			    		$("#query_form").submit();
			        } else {
			        	alert(myJSON.msg);
			        }
			    }
			});
		}
	}
</script>
</head>
<body>
	<form id="cateForm" name="cateForm" method="post" enctype="multipart/form-data">
		<s:hidden name="tbProductInterfaceId" />
		<table class="cg_xx" width="100%" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td class="textStyle">产品名称：</td>
				<td>${taobaoProduct.prodProduct.productName}
				</td>
			</tr>
			<tr>
				<td class="textStyle">上线时间：</td>
				<td>${taobaoProduct.prodProduct.onlineTimeStr}</td>
			</tr>
			<tr>
				<td class="textStyle">下线时间：</td>
				<td>${taobaoProduct.prodProduct.offlineTimeStr}</td>
			</tr>
			<tr>
				<td class="textStyle">状态：</td>
				<td>${taobaoProduct.prodProduct.strOnLine}</td>
			</tr>
			<tr>
				<td class="textStyle">上传淘宝时间：</td>
				<td>${taobaoProduct.tbPushDateStr }</td>
			</tr>
			<tr>
				<td class="textStyle">淘宝状态：</td>
				<td>${taobaoProduct.strTbStatus }</td>
			</tr>
			<tr>
				<td class="textStyle">产品淘宝类目：</td>
				<td>${taobaoProduct.cateName}</td>
			</tr>
			<tr>
				<td class="textStyle">是否入仓：</td>
				<td><input type="radio" id="isStorage" name="isStorage" checked="checked" value="否" /></td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>报名城市：</td>
				<td>
					<s:select style="width:100px;" list="applyCityList" name="applyCityId" 
						listKey="applyCityId" listValue="cityName" />
				</td>
			</tr>
			<tr>
				<td class="textStyle" valign="top"><span style="color:red;">*&nbsp;</span>产品主图：</td>
				<td>
					<input type="file" id="uploadFile" name="uploadFile"/>
					<span>
						显示在网站列表页以及团购详情页的首张大图<br />
						图片要求：jpg、jpeg、gif、png文件，像素高于960*640，宽高比3:2，大小1M以内
					</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>宝贝短名称：</td>
				<td>
					<s:if test="#session.detailProd.shortName==null">
						<input type="text" id="shortName" name="shortName" class="newtext1" 
							value="${taobaoProduct.prodProduct.productName}" maxlength="10" />
					</s:if>
					<s:else>
						<input type="text" id="shortName" name="shortName" class="newtext1" 
							value="${detailProd.shortName}" maxlength="10" />
					</s:else>
					<span style="font-size: 12px;color: red;">
						<span id="shortNameCount"  style="font-size: 12px;color: red;">10</span>/10(你还可输入<span id="shortNameCount1"  style="font-size: 12px;color: red;">10</span>个字符)
					</span>
					<span>（用于商品在聚划算的宣传，10个汉子以内）</span>
				</td>
			</tr>
			<tr>
				<td valign="top" class="textStyle"><span style="color:red;">*&nbsp;</span>宝贝长名称：</td>
				<td>
					<s:if test="#session.detailProd.shortName==null">
						<textarea rows="2" cols="30" id="longName" maxlength="100" name="longName">${taobaoProduct.prodProduct.productName}</textarea>
					</s:if>
					<s:else>
						<textarea rows="2" cols="30" id="longName" maxlength="100" name="longName">${detailProd.longName}</textarea>
					</s:else>
					<span  style="font-size: 12px;color: red;">
						<span id="longNameCount" style="font-size: 12px;color: red;">100</span>/100(你还可输入<span id="longNameCount1" style="font-size: 12px;color: red;">100</span>个字符)
					</span><br />
					<span>小提示：可以尽量在标题中提现你的宝贝的特点和优惠</span>
				</td>
			</tr>
			
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>宝贝原价：</td>
				<td>
					<input type="text" size="8" class="newtext1" id="originalPrice" name="originalPrice" style="color: gray"
						 value="${taobaoProduct.prodProduct.marketPriceYuan}" readonly="readonly"  onblur="checkResult()" />&nbsp;元
				</td>
			</tr>
			
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>团购价格：</td>
				<td>
					<input type="text" size="8" class="newtext1" id="activityPrice" name="activityPrice"
						 value="${detailProd.activityPrice}"  onblur="checkResult()" />&nbsp;元
					<span>(店内宝贝在淘宝出售的一口价<a href="javascript:void(0)">查看宝贝</a>)</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>折扣：</td>
				<td>
					<input type="text" size="8" class="newtext1" id="discount" style="color: gray"
						 name="discount" value="${detailProd.discount}" readonly="readonly" />&nbsp;折
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>团购数量：</td>
				<td>
					<input type="text" size="8" class="newtext1" id="itemCount"
						 value="${detailProd.itemCount}" name="itemCount" />
					<span class="fontColor">
						(请确保开团当日店内宝贝的实际库存与团购数量一致，若低于团购数量，将取消开团 <a href="javascript:void(0)">查看宝贝</a>)
					</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>每个ID限购：</td>
				<td>
					<input type="text" size="8" class="newtext1" id="limitNum"
						 value="${detailProd.limitNum}" name="limitNum" />&nbsp;件
					<span>(每个买家累计的最大可拍下数量)</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>所在城市：</td>
				<td>
					<input type="text" size="8" class="newtext1" id="locCity" name="locCity" 
						value="上海" readonly="readonly" style="color: gray" />
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>旅游类型：</td>
				<td>
					<input type="radio" id="tripTypeValue" name="tripTypeValue" value="5" checked="checked" />景点门票
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>出发地：</td>
				<td><s:hidden name="destination"></s:hidden>
					<s:select style="width:100px;" name="startArea" list="cateList1" listKey="code"
						 listValue="name" headerKey="" headerValue="不限制" onchange="changeStartPlace(this)"></s:select>
					<s:select style="width:100px;" name="startArea2" list="#{'':'不限制'}" listKey="code"
						 listValue="name" headerKey="" headerValue="不限制"></s:select>
				</td>
			</tr>
			<tr>
				<td class="textStyle"><span style="color:red;">*&nbsp;</span>目的地：</td>
				<td>
					<s:select style="width:100px;" name="endArea" list="cateList1" listKey="code"
						 listValue="name" headerKey="" headerValue="不限制" onchange="changePlace(this)"></s:select>
					<s:select style="width:100px;" name="endArea2" list="#{'':'不限制'}" listKey="code"
						 listValue="name" headerKey="" headerValue="不限制" onchange="changePlace2(this)"></s:select>
					<s:select style="width:100px;" name="endArea3" list="#{'':'不限制'}" listKey="code"
						 listValue="name" headerKey="" headerValue="不限制"></s:select>
				</td>
			</tr>
			<tr>
				<td class="textStyle" valign="top">点评/口碑等上的评价：</td>
				<td><textarea rows="2" cols="30" maxlength="200" id="optinion" name="optinion">${detailProd.optinion}</textarea>
					<span  style="font-size: 12px;color: red;">
						<span id="optinionCount" style="font-size: 12px;color: red;">200</span>/200(你还可输入<span id="optinionCount1" style="font-size: 12px;color: red;">200</span>个字符)
					</span><br />
					<span>小提示：只可以输入200个字符，填写这些信息有助于通过审核，请认真填写</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle" valign="top">申请多城原因：</td>
				<td><textarea rows="2" cols="30" maxlength="20" id="multiCityReason" name="multiCityReason">${detailProd.multiCityReason}</textarea>
					<span  style="font-size: 12px;color: red;">
						<span id="multiCityReasonCount" style="font-size: 12px;color: red;">20</span>/20(你还可输入<span id="multiCityReasonCount1" style="font-size: 12px;color: red;">20</span>个字符)
					</span><br />
					<span>小提示：只可以输入20个字符，填写这些信息有助于通过审核，请认真填写</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle" valign="top">排期建议原因：</td>
				<td><textarea rows="2" cols="30" maxlength="40" id="scheduleAdvice" name="scheduleAdvice">${detailProd.scheduleAdvice}</textarea>
					<span  style="font-size: 12px;color: red;">
						<span id="scheduleAdviceCount" style="font-size: 12px;color: red;">40</span>/40(你还可输入<span id="scheduleAdviceCount1" style="font-size: 12px;color: red;">40</span>个字符)
					</span><br />
					<span>小提示：只可以输入40个字符，填写这些信息有助于通过审核，请认真填写</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle" valign="top">历史团购网址：</td>
				<td><textarea rows="2" cols="30" maxlength="200" id="tgHistory" name="tgHistory">${detailProd.tgHistory}</textarea>
					<span  style="font-size: 12px;color: red;">
						<span id="tgHistoryCount" style="font-size: 12px;color: red;">200</span>/200(你还可输入<span id="tgHistoryCount1" style="font-size: 12px;color: red;">200</span>个字符)
					</span><br />
					<span>小提示：只可以输入200个字符，填写这些信息有助于通过审核，请认真填写</span>
				</td>
			</tr>
			<tr>
				<td class="textStyle" valign="top">商品亮点优势：</td>
				<td><textarea rows="2" cols="30" maxlength="200" id="strength" name="strength">${detailProd.strength}</textarea>
					<span  style="font-size: 12px;color: red;">
						<span id="strengthCount" style="font-size: 12px;color: red;">200</span>/200(你还可输入<span id="strengthCount1" style="font-size: 12px;color: red;">200</span>个字符)
					</span><br />
					<span>小提示：只可以输入200个字符，填写这些信息有助于通过审核，请认真填写</span>
				</td>
			</tr>
		</table>
		<center>
			<input type="button" value="保存 " id="saveBtn" onclick="saveMsg()" />
			<input type="button" value="保存并发布" id="saveAndPushBtn" onclick="saveAndPush()"/>
		</center>
	</form>
</body>
<script type="text/javascript">
//获取出发地和目的地信息
function changeArea(){
	var json = eval($("#destination").val());
	for(var i=0;i<json.length;i++){
		for(var n=0;n<json[i].child.length;n++){
			var child = json[i].child[n];
			if(child.code=="${detailProd.startArea}"){
				$("#startArea").val(json[i].code);
				$("#startArea").change();
				$("#startArea2").val(child.code);
			}

			if(child.code=="${detailProd.destinyArea}"){
				$("#endArea").val(json[i].code);
				$("#endArea").change();
				$("#endArea2").val(child.code);
			}
		}
	}
}
$(document).ready(function(){
	checkTextLength();
	
	$("#shortName").keyup(function(){
		$("#shortNameCount").text(10-$("#shortName").val().length);
		$("#shortNameCount1").text(10-$("#shortName").val().length);
	});
	$("#longName").keyup(function(){
		$("#longNameCount").text(100-$("#longName").val().length);
		$("#longNameCount1").text(100-$("#longName").val().length);
	});
	$("#optinion").keyup(function(){
		$("#optinionCount").text(200-$("#optinion").val().length);
		$("#optinionCount1").text(200-$("#optinion").val().length);
	});
	$("#tgHistory").keyup(function(){
		$("#tgHistoryCount").text(200-$("#tgHistory").val().length);
		$("#tgHistoryCount1").text(200-$("#tgHistory").val().length);
	});
	$("#strength").keyup(function(){
		$("#strengthCount").text(200-$("#strength").val().length);
		$("#strengthCount1").text(200-$("#strength").val().length);
	});
	$("#multiCityReason").keyup(function(){
		$("#multiCityReasonCount").text(20-$("#multiCityReason").val().length);
		$("#multiCityReasonCount1").text(20-$("#multiCityReason").val().length);
	});
	$("#scheduleAdvice").keyup(function(){
		$("#scheduleAdviceCount").text(40-$("#scheduleAdvice").val().length);
		$("#scheduleAdviceCount1").text(40-$("#scheduleAdvice").val().length);
	});
	changeArea();
});
//加载页面，初始化输入文本框中的字符长度
function checkTextLength(){
	$("#shortNameCount").text(10-$("#shortName").val().length);
	$("#shortNameCount1").text(10-$("#shortName").val().length);

	$("#longNameCount").text(100-$("#longName").val().length);
	$("#longNameCount1").text(100-$("#longName").val().length);

	$("#optinionCount").text(200-$("#optinion").val().length);
	$("#optinionCount1").text(200-$("#optinion").val().length);

	$("#tgHistoryCount").text(200-$("#tgHistory").val().length);
	$("#tgHistoryCount1").text(200-$("#tgHistory").val().length);

	$("#strengthCount").text(200-$("#strength").val().length);
	$("#strengthCount1").text(200-$("#strength").val().length);

	$("#multiCityReasonCount").text(20-$("#multiCityReason").val().length);
	$("#multiCityReasonCount1").text(20-$("#multiCityReason").val().length);
	
	$("#scheduleAdviceCount").text(40-$("#scheduleAdvice").val().length);
	$("#scheduleAdviceCount1").text(40-$("#scheduleAdvice").val().length);
}
</script>
</html>