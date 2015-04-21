<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>新建产品</title>
		<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
		<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/kindeditor-min.js"></script>
		<script charset="utf-8" src="${basePath}/kindeditor-4.0.2/lang/zh_CN.js"></script>

		<script type="text/javascript">
		var editor;
		$(function(){
			// 初始化编辑控件
			editor = KindEditor.create('#contentA',{
		    	resizeType : 1,
		    	width:'700px',
		    	filterMode : true,
		    	uploadJson:'/pet_back/upload/uploadImg.do'
		    });
		});
		</script>	
	</head>
	<body>
 		<div>
 			<form id='saveShopProuctForm' method='post' action='<%=basePath%>shop/shopProduct/savaOrUpdateShopProduct.do' enctype='multipart/form-data'>
		 	    <input type="hidden" name="shopProduct.productId" value="${shopProduct.productId}" id="productId" />
		 	    <input type="hidden" name="shopProduct.pictures"  value="${shopProduct.pictures}" />
		 	    <input type="hidden" name="shopProduct.isValid"   value="${shopProduct.isValid}" id="valid" />
		 	    <input id="description" name="shopProduct.content" type="hidden"/>
		 	    <input type="hidden" name="productCode" value="${productCode}"/>
		 	    <input type="hidden" name="productName" value="${productName}"/>
		 	    <input type="hidden" name="changeType" value="${changeType}"/>
		 	    <input type="hidden" name="productType" value="${productType}"/>
		 	    <input type="hidden" name="isValid" value="${isValid}"/>
		 		<table class="p_table form-inline">
		 		<tr>
		 			<td class="p_label">
		 				产品名称： <span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<input id="productName" name="shopProduct.productName" value="${shopProduct.productName}" type="text"/>
		 			</td>
		 			<td class="p_label">
		 				产品编号：<span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<input id="productCode" name="shopProduct.productCode" value="${shopProduct.productCode}" type="text"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				所需积分：<span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<input id="pointChange" name="shopProduct.pointChange" value="${shopProduct.pointChange}" onchange="checkNum('pointChange');" type="text"/>
		 			</td>
		 			<td class="p_label">
		 				市场价：  <span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<input id="marketPrice" name="shopProduct.marketPrice" value="${shopProduct.marketPrice}" onchange="checkNum('marketPrice');" type="text"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				库存：  <span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<input id="stocks" name="shopProduct.stocks" value="${shopProduct.stocks}" onchange="checkNum('stocks');" type="text"/>
		 			</td>
		 			<td class="p_label">
		 				推荐位置：
		 			</td>
		 			<td>
		 				<input name="shopProduct.isHotProduct" value="Y" type="checkbox" <s:if test='"Y".equals(shopProduct.isHotProduct)'>checked</s:if> />
		 				首页热门推荐&nbsp;&nbsp;&nbsp;
		 				<input name="shopProduct.isRecommend" value="Y"  type="checkbox" <s:if test='"Y".equals(shopProduct.isRecommend)'>checked</s:if>/>首页呈现
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				兑换类别：  <span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<s:radio id="changeType" name="shopProduct.changeType"  list="%{#{'POINT_CHANGE':'积分兑换','RAFFLE':'抽奖概率'}}" onclick="checkChangeType(this.value)" />
		 				<input id="winningRate" name="shopProduct.winningRate" value='${shopProduct.winningRateForString}' style="width: 70px" type="text" onchange="checkNum('winningRate');" />(0.00001-0.99999)
		 			</td>
		 			<td class="p_label">
		 				产品类别：  <span style="color:red;">[*]</span>
		 			</td>
		 			<td>
		 				<s:radio id="productType" name="shopProduct.productType"  list="%{#{'PRODUCT':'实物','COOPERATION_COUPON':'合作网站优惠券','COUPON':'优惠券'}}" onclick="checkProductType(this.value);" />
		 				<input id="showCouponId" name="couponId" value="${couponId}" style="width: 70px;display: none" type="text" onchange="checkNum('showCouponId');" />
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				兑换限制：
		 			</td>
		 			<td colspan="3">
		 				<input name="shopProduct.shopProductConditions[0].conditionX" type="checkbox" value="CHECK_EXCHANGE_EMAIL" <s:if test='isCheckEmail'>checked</s:if> />
		 				仅限邮箱验证会员兑换&nbsp;&nbsp;&nbsp;
		 				<input name="shopProduct.shopProductConditions[1].conditionX" type="checkbox" value="CHECK_EXCHANGE_ORDER" <s:if test='isCheckOrder'>checked</s:if> />
		 				仅限下过订单会员兑换
		 			</td>
		 			
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				兑换数量：
		 			</td>
		 			<td colspan="3">
		 				<input name="shopProduct.shopProductConditions[2].conditionX" id="checkExchangeNum" type="checkbox" value="CHECK_EXCHANGE_NUM" <s:if test='isCheckNum'>checked</s:if> onclick="changeExchangeNum();" /> 单个会员最多兑换
		 				<input name="shopProduct.shopProductConditions[2].conditionY" id="conditionY" type="text" value="${num}" style="width: 40px" onchange="checkNum('conditionY');"  />个
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				是否需要手机验证：
		 			</td>
		 			<td colspan="3">
		 				<s:if test="shopProduct.isValidate == null">
			 				<s:radio id="changeType" name="shopProduct.isValidate"  list="%{#{'Y':'是','N':'否'}}" value="'N'"/>
		 				</s:if>
		 				<s:else>
		 					<s:radio id="changeType" name="shopProduct.isValidate"  list="%{#{'Y':'是','N':'否'}}"/>
		 				</s:else>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				产品上线时间：
		 			</td>
		 			<td colspan="3">
		 				<input name="shopProduct.beginTime" id="beginTime" type="text" class="date"  value="<s:date name="shopProduct.beginTime" format="yyyy-MM-dd"/>"  /> &nbsp;~&nbsp;
		 				<input name="shopProduct.endTime" id="endTime"     type="text" class="date" value="<s:date name="shopProduct.endTime" format="yyyy-MM-dd"/>"  />  
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				图片：<span style="color:red;">[*]</span>
		 			</td>
		 			<td colspan="3">
		 				<s:if test="shopProduct.absolutePictureUrl==null">
		 				<div id="imgDiv">
			 				<div>
			 					<input class="c_upfile" type="file" id="fuMain" name="fileData" id="fileData"  size="60"/>
			 				</div>
		 				</div>
		 				<input type="button" value="增加一个" class="btn btn-small w3" onclick="addImg();"/>
		 				</s:if>
		 				<s:else>
		 					<div id="newImgDiv">
		 				    </div>
		 					<div id="existImg">
		 						<table border="0" width="100%">
		 							<tr>
		 								<td>已上传图片</td>
		 							</tr>
	 								<s:iterator value="shopProduct.absolutePictureUrl" status="status" id="picUrl">
		 								<tr>
			 								<td>
			 									<img src='<s:property value="picUrl"/>' />
			 								</td>
		 								</tr>
		 							</s:iterator>
		 							<tr>
		 								<td>
		 									<input type="button" value="重新上传" class="btn btn-small w3" onclick="uploadImg();"/>
		 								</td>
		 							</tr>
		 						</table>
		 					</div>
		 				</s:else>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td class="p_label">
		 				产品详细信息： <span style="color:red;">[*]</span>
		 			</td>
		 			<td colspan="3">
		 				<textarea id="contentA" cols="70"  style="width: 90%;height: 450px;">${shopProduct.content}</textarea>
		 			</td>
		 		</tr>
		 		</table>
		 		<p class="tc mt10">
		 			<input type="button"  class="btn btn-small w3" onclick="submitButton('Y');" value="保存并上线" />
		 			<input type="button"  class="btn btn-small w3" onclick="submitButton();" value="保存" />
		 		</p> 			 			 			
 		    </form>
 	    </div>
 	    
		<script type="text/javascript">
		  $(function(){
			//初始化
			//--日期控件
	    	$("input.date").datepicker({dateFormat: 'yy-mm-dd'});
	    	checkProductType('${shopProduct.productType}');
	    	checkChangeType('${shopProduct.changeType}');
	    	changeExchangeNum();
		  });
		  //提交
		  function submitButton(isValid){
	   		if(checkForm()){
	   			$("#description").val(editor.html());
	   			if(typeof(isValid) != "undefined"){
	   				$("#valid").val(isValid);
	   			}
	    		$("#saveShopProuctForm").submit(); 
	   		}
		  }
		  function addImg(){
			  var num=$("#imgDiv").find("input[type='file']").length;
			  if(num==4){
				  alert("最多只可上传四张图片！");
				  return;
			  }else{
				  num+=1;
			  }
			  var str="<div id='imgId"+num+"'>"+
						"<input type='file' size='60' name='fileData' />&nbsp;&nbsp;"+
						"<input type='button' value='删除' class='btn btn-small w3' onclick='removeImgDiv("+num+");'/>"+
			           "</div>";
			$("#imgDiv").append(str);
		  }
		  function removeImgDiv(num){
			  $("#imgId"+num).remove();
		  }
		  function uploadImg(){
			  $("#existImg").remove();
			  var str='<div id="imgDiv">'+
						 '<input class="c_upfile"  type="file" name="fileData" id="fileData"  size="60"/>'+
			 		   '</div>'+
						'<input type="button" value="增加一个" class="btn btn-small w3" onclick="addImg();"/>';
			  $("#newImgDiv").append(str);
		  }
		  //保存或更新校验信息
		  function checkForm(){
			  if($("#productName").val()==""){
				  alert("产品名称不能为空！");
				  $("#productName").focus();
				  return;
			  }
			  if($("#productCode").val()==""){
				  alert("产品编号不能为空！");
				  $("#productCode").focus();
				  return;
			  }
			  if($("#pointChange").val()==""){
				  alert("兑换积分不能为空！");
				  $("#pointChange").focus();
				  return;
			  }
			  if($("#marketPrice").val()==""){
				  alert("市场价不能为空！");
				  $("#marketPrice").focus();
				  return;
			  }
			  if($("#stocks").val()==""){
				  alert("库存不能为空！");
				  $("#stocks").focus();
				  return;
			  }
			  //验证图片
			 var flag=0;
			 $("#imgDiv").find("input[type='file']").each(function(i){
				  var picUrl=this.value;
				  if(typeof(picUrl) == "undefined" ||picUrl=="" ||picUrl==null ){
					  alert("请选择要上传的产品图片！");
					  flag=1;
					  return false;
				  }else{
					 var ext= picUrl.substring(picUrl.lastIndexOf(".") + 1);
					 if(!(ext=="jpg"||ext=="gif" ||ext=="jpeg" ||ext=="png")){
						 alert("请选择正确的图片上传！");
						 flag=1;
						 return false;
					 }
				  }
				 });
			  if(flag==1){
				  return;
			  }
			  
			  if(($("#beginTime").val() !="" && $("#endTime").val() =="" )||($("#beginTime").val() == "" && $("#endTime").val() !="" )){
				  alert("2个时间必须同时填写.");
				  return;
			  }
			  
			  //兑换类别
			  var changeType = $("input[name='shopProduct.changeType']:checked").val();
			  if(changeType=='RAFFLE'){
				  var winRate=$("#winningRate").val();
				  if(!(parseFloat(winRate)>=0.00001 && parseFloat(winRate)<=0.99999)){
					  alert("抽奖概率的范围必须在0.00001-0.99999之间！");
					  $("#winningRate").val('');
					  return;
				  }  
			  }
			  var checkExchangeNum=$("input[id='checkExchangeNum']:checked").val();
			  if(typeof(checkExchangeNum)!="undefined" && checkExchangeNum!=""){
				  var checkNum=$("#conditionY").val();
				  if(checkNum=="" ||checkNum==0){
					  alert("请填写单个会员最多兑换个数！");
					  $("#conditionY").focus();
					  return;
				  }
			  }
			  if(editor.html()==""){
	    			alert("请填写产品详细信息！");
	    			return ;
	    		}
			  return true;
		  }
		//产品类型
 		function checkProductType(productType){
 			if(productType=='COUPON'){
 				$("#showCouponId").show();
 			}else{
 				$("#showCouponId").hide();
 			}
 			if(productType=='COOPERATION_COUPON'){
 				if($("#productId").val()==""){
 					$("#stocks").val(0);
 				}
 				$("#stocks").attr("disabled","disabled");
 			}else{
 				$("#stocks").removeAttr("disabled");
 			}
 		}
		//兑换限制最多兑换个数
		function changeExchangeNum(){
			var checkExchangeNum=$("input[id='checkExchangeNum']:checked").val();
			if(typeof(checkExchangeNum)!="undefined" && checkExchangeNum!=""){
				$("#conditionY").removeAttr("disabled");
			}else{
				$("#conditionY").val('');
				$("#conditionY").attr("disabled","disabled");
			}
		}
		//兑换类型
		function checkChangeType(changeType){
			if(changeType=='POINT_CHANGE'){
				$("#winningRate").val('');
				$("#winningRate").attr("disabled","disabled");
			}else{
				$("#winningRate").removeAttr("disabled");
			}
		}
	  //数字验证
	  function checkNum(id){
		  var num =$("#"+id).val();
		  if(isNaN(num)){
			  alert("非法字符！亲,请输入正确的数字哦！");
			  $("#"+id).val('');
			  $("#"+id).focus();
		  }
	  }
		</script> 	    
	</body>
</html>