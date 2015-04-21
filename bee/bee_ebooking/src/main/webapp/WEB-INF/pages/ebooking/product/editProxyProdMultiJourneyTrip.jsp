<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>驴妈妈供应商管理系统</title>
<!-- 引用EBK公共资源(CSS、JS) -->
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/button.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.cropzoom.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.all.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.core.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.base.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/ui-common.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/panel-content.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.ui.theme.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery-ui-1.8.18.custom.css"/>

<script src="${basePath }js/base/jquery-1.4.4.min.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script src="http://pic.lvmama.com/js/v4/modules/pandora-dialog.js"></script>
<script type="text/javascript" src="${basePath}js/ebooking/editEbkProdProductInfo.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.cropzoom.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.validate.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
<script src="${basePath }js/base/jquery-ui-1.8.5.js" defer="defer"></script>
<script type="text/javascript">
function goBack(id) {
	window.location.href="${contextPath }/ebooking/product/editEbkProdMultirpInit.do?ebkProdProductId="+id;
	
}
</script>
</head>
<body id="body_cpgl">
	<jsp:include page="../../common/head.jsp"></jsp:include>
	<s:include value="./subPage/navigation.jsp"></s:include>
	<!--以上是公用部分-->

	<!--订单详情-->
	<form action="${contextPath }/ebooking/product/saveEbkProdMultiTripPicture.do" id="confirm" method="post">
		<input name="ebkProdProduct.ebkProdProductId" id="ebkProdProductId" value="${ebkProdProduct.ebkProdProductId}" type="hidden">
		<input name="ebkProdProduct.supplierId" id="supplierId" value="${ebkProdProduct.supplierId }" type="hidden">
		<input name="ebkProdProduct.productType" id="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input name="ebkProductViewType" value="${ebkProdProduct.productType }" type="hidden">
		<input type="hidden" name='tripPicDayNum' id="tripPicDayNum"/>
		<input type="hidden" name="days" id="showDate" value="${days }"/>
		<input type="hidden" name="tripSaveDayNum" value="${days }"/>
		<input name="toShowEbkProduct" value="${toShowEbkProduct }" id="toShowEbkProduct" type="hidden">
		<input name="multiJourneyId" value="${ebkMultiJourney.multiJourneyId }" type="hidden">
		<input name="productId" value="${ebkProdProduct.ebkProdProductId}" type="hidden">
		
		
		<!--新增产品基础信息-->
		<div class="xzxx_box">
			<span class="fp_btn kcwh_btn_t">提交审核</span>
			<ul class="xzxx_tab">
				<li class="tab_this" id="EBK_AUDIT_TAB">产品基础信息</li>
				<li id="EBK_AUDIT_TAB_TIME_PRICE">价格/库存维护</li>
			</ul>
			<div class="xzxx_box_list" style="display: block;">
				<s:include value="./subPage/editEbkProductNavigate.jsp"></s:include>
				<ul class="xzxx_list">
					<li style="display: block;">
		            	<p class="xcts_t">
		            		<span class="red_ff4444">*</span>行程展示：${ebkMultiJourney.journeyName }
					   		 <a href="#" onclick="goBack('${ebkProdProduct.ebkProdProductId}')">返回行程</a>
		            	</p>
		            	<table class="xcts_list"  border="0"  id="showDate_tab">
		            		<s:if test="ebkMultiJourney != null && ebkMultiJourney.multiJourneyId!=null">
	                        	<s:iterator value="ebkProdJourneyList" var="ebkProdJourney" status="index">
	                        		<tr id="tr_${index.index }" class='first_tr'>
            							<td>
			                        		<table>
				                        		<tr>
							                    	<td width="50" rowspan="7" class="xcts_list_first">第 ${ebkProdJourney.dayNumber } 天<input type="hidden" name="ebkProdProduct.ebkProdJourneys[${index.index }].dayNumber" value="${ebkProdJourney.dayNumber }"><input type="hidden" name="ebkProdProduct.ebkProdJourneys[${index.index }].journeyId" value="${ebkProdJourney.journeyId }"></td>
							                        <td width="50" align="right">标题:</td>
							                        <td>
							                        	<input type="text" name="ebkProdProduct.ebkProdJourneys[${index.index }].title"  maxLength="100" value="${ebkProdJourney.title }">
							                        	<span tip-content="出发地、目的地 飞机用“/”表示 巴士等用“-”表示" class="text_ts tip-icon tip-icon-info"></span>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">描述：</td>
							                        <td>
							                        	<div class="fysm_b">
								                        	<textarea style="width: 500px;" rows="10"  name="ebkProdProduct.ebkProdJourneys[${index.index }].content"  maxLength='2000' >${ebkProdJourney.content }</textarea>
								                        	<span tip-content="如有航班必须写在第一行写 参考航班：" class="text_ts tip-icon tip-icon-info"></span>
							                        	</div>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">交通：</td>
							                        <td>
							                        
								                        
							                        
							                        	<label><input type="checkbox" name="ebkProdProduct.ebkProdJourneys[${index.index }].traffic" value='AIRPLANE'
								                        	<s:iterator value="#ebkProdJourney.traffics" var="tra" >
									                        	<s:if test="#tra=='AIRPLANE'">
									                        		 checked 
									                        	</s:if>	
								                        	</s:iterator>
							                        	>飞机</label>
							                        	<label><input type="checkbox"  name="ebkProdProduct.ebkProdJourneys[${index.index }].traffic" value='BUS'
								                        	<s:iterator value="#ebkProdJourney.traffics" var="tra" >
									                        	<s:if test="#tra=='BUS'">
									                        		 checked 
									                        	</s:if>	
								                        	</s:iterator>
							                        	>巴士</label>
							                            <label><input type="checkbox"  name="ebkProdProduct.ebkProdJourneys[${index.index }].traffic" value='SHIP'
								                        	<s:iterator value="#ebkProdJourney.traffics" var="tra" >
									                        	<s:if test="#tra=='SHIP'">
									                        		 checked 
									                        	</s:if>	
								                        	</s:iterator>
							                            >轮船</label>
							                            <label><input type="checkbox"  name="ebkProdProduct.ebkProdJourneys[${index.index }].traffic" value='TRAIN'
								                        	<s:iterator value="#ebkProdJourney.traffics" var="tra" >
									                        	<s:if test="#tra=='TRAIN'">
									                        		 checked 
									                        	</s:if>	
								                        	</s:iterator>
							                            >火车</label>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">用餐：</td>
							                        <td>
							                        	<input type="text"  name="ebkProdProduct.ebkProdJourneys[${index.index }].dinner" value="${ebkProdJourney.dinner }" class="width_320"  maxLength='100' ><span tip-content="用含或敬请自理或者是特色餐" class="text_ts tip-icon tip-icon-info"></span>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">住宿：</td>
							                        <td>
							                        	<input type="text" name="ebkProdProduct.ebkProdJourneys[${index.index }].hotel" value="${ebkProdJourney.hotel }" class="width_320"  maxLength='1000' ><span tip-content='酒店必须有具体的酒店名称，如果用预付款协议也必须写具体的参考酒店名称' class="text_ts tip-icon tip-icon-info"></span>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right"></td>
							                        <td>
							                            <span class="as-file js_up">
								                            <s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
											            		<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn">上传图片</span>
							                            		<input type="file" exts="png|jpg|bmp" name='tripPic' class="as-input-file" id="${index.index }" onchange="uploadPicFun(${index.index })"/>
															</s:if>
							                            </span>　可上传图片（尺寸：320*240）；比例为4:3；(0&lt;上传张数&gt;2)
							                        </td>
							                    </tr>
							                    <tr>
							                    	<td></td>
							                        <td id='show_img_id_${index.index }'>
							                        	<s:if test="#ebkProdJourney.comPictureJourneyList != null && #ebkProdJourney.comPictureJourneyList.size > 0">
	                        								<s:iterator value="#ebkProdJourney.comPictureJourneyList" var="comPicture" status="picIndex">
	                        									<span class="as-file" id='pic_span_${comPicture.pictureId }'>
										                        	<img src="http://pic.lvmama.com/pics/${comPicture.pictureUrl }" id="${comPicture.pictureId }" width="320" height="240" alt="" currentpictureid="${comPicture.pictureId }"><br>
										                        	<input type="text" id="fileName_${index.index }" value="${comPicture.pictureName }" maxLength="200" name="ebkProdProduct.ebkProdJourneys[${index.index }].comPictureJourneyList[${picIndex.index }].pictureName">
										                        	<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
													            		<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn" onclick="delThisPic('${index.index }','${comPicture.pictureId }')">删除</span>
													            		<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn js_min_edit" id='<s:property value="pictureId"/>'>编辑</span>
																	</s:if>
										                        	<input type="hidden" id="fill_name_hidden_${index.index }_${picIndex.index }" name="ebkProdProduct.ebkProdJourneys[${index.index }].comPictureJourneyList[${picIndex.index }].pictureId" value="${comPicture.pictureId }" />
									                        	</span>
	                        								</s:iterator>
	                        							</s:if>	
							                        </td>
							                    </tr>
						                    </table>
			                        	</td>
				            		</tr>
								</s:iterator>
	                        </s:if>
	                        <s:else>
	                        	<tr id="tr_0" class='first_tr'>
            							<td>
			                        		<table>
				                        		<tr>
							                    	<td width="50" rowspan="7" class="xcts_list_first">第 1 天<input type="hidden" name="ebkProdProduct.ebkProdJourneys[0].dayNumber" value="1"><input type="hidden" name="ebkProdProduct.ebkProdJourneys[0].journeyId" ></td>
							                        <td width="50" align="right">标题：</td>
							                        <td>
							                        	<input type="text" name="ebkProdProduct.ebkProdJourneys[0].title"  maxLength="100" >
							                        	<span tip-content="出发地、目的地 飞机用“/”表示 巴士等用“-”表示" class="text_ts tip-icon tip-icon-info"></span>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">描述：</td>
							                        <td>
														<div class="fysm_b">
							                        		<textarea style="width: 500px;" rows="10" name="ebkProdProduct.ebkProdJourneys[0].content"  maxLength='2000' ></textarea>
															<span tip-content="如有航班必须写在第一行写 参考航班：" class="text_ts tip-icon tip-icon-info"></span>
														</div>
													</td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">交通：</td>
							                        <td>
							                        
								                        
							                        
							                        	<label><input type="checkbox" name="ebkProdProduct.ebkProdJourneys[0].traffic" value='AIRPLANE'>飞机</label>
							                        	<label><input type="checkbox"  name="ebkProdProduct.ebkProdJourneys[0].traffic" value='BUS'>巴士</label>
							                            <label><input type="checkbox"  name="ebkProdProduct.ebkProdJourneys[0].traffic" value='SHIP'>轮船</label>
							                            <label><input type="checkbox"  name="ebkProdProduct.ebkProdJourneys[0].traffic" value='TRAIN'>火车</label>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">用餐：</td>
							                        <td>
							                        	<input type="text"  name="ebkProdProduct.ebkProdJourneys[0].dinner"  class="width_320"  maxLength='100' value="早餐：敬请自理、中餐：敬请自理、晚餐：敬请自理"><span tip-content="用含或敬请自理或者是特色餐" class="text_ts tip-icon tip-icon-info"></span>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right">住宿：</td>
							                        <td>
							                        	<input type="text" name="ebkProdProduct.ebkProdJourneys[0].hotel" class="width_320"  maxLength='1000' ><span tip-content='酒店必须有具体的酒店名称，如果用预付款协议也必须写具体的参考酒店名称' class="text_ts tip-icon tip-icon-info"></span>
							                        </td>
							                    </tr>
							                    <tr>
							                        <td width="50" align="right"></td>
							                        <td>
							                            <span class="as-file js_up">
								                            <s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
											            		<span style="margin-left: 8px;margin-bottom: 8px;" class="fp_btn">上传图片</span>
							                            		<input type="file" exts="png|jpg|bmp" name='tripPic' class="as-input-file" id="0" onchange="uploadPicFun(0)"/>
															</s:if>
							                            </span>　可上传图片（尺寸：320*240）；比例为4:3；(0&lt;上传张数&gt;2)
							                        </td>
							                    </tr>
							                    <tr>
							                    	<td></td>
							                        <td id='show_img_id_0'>
							                        </td>
							                    </tr>
						                    </table>
			                        	</td>
				            		</tr>
	                        </s:else>
		                </table>
		                
		            </li>
				</ul>
				<s:if test="toShowEbkProduct!='SHOW_EBK_PRODUCT'">
					<span class="btn_bc submitEdit">保存</span>
				</s:if>
			</div>
		</div>
	</form>

	<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
<script type="text/javascript">
	$(function(){
		changeDate();
		
	});

	function changeDate(){
		var reg1 =  /^\d+$/; 
		if($("#showDate").val()==''){
			return;
		}
		if($("#showDate").val().match(reg1) == null){
			alert("天数必须是正整数！");
			$("#showDate").focus();
			return;
		}
		
		for(var i=0;i<$("#showDate").val();i++){
			if($("#tr_"+i).html()==null){
				
				var showDay = i+1;
				
				var tr =$("<tr class='first_tr' id='tr_"+i+"'></tr>");
				var td =$("<td></td>");
				var tab = $("<table></table>");
				
				var tr1 = $("<tr><td width='50' rowspan='7' class='xcts_list_first'>第  "+showDay+" 天<input type='hidden' name='ebkProdProduct.ebkProdJourneys["+i+"].dayNumber' value='"+showDay+"'><input type='hidden' name='ebkProdProduct.ebkProdJourneys["+i+"].journeyId' ></td><td width='50' align='right'>标题：</td><td><input type='text' name='ebkProdProduct.ebkProdJourneys["+i+"].title'  maxLength='100'><span tip-content='出发地、目的地 飞机用“/”表示 巴士等用“-”表示' class='text_ts tip-icon tip-icon-info'></span></td></tr>");
					
				var tr2 = $("<tr><td width='50' align='right'>描述：</td><td><div class='fysm_b'><textarea style='width: 500px;' rows='10'  name='ebkProdProduct.ebkProdJourneys["+i+"].content'  maxLength='2000' ></textarea><span tip-content='如有航班必须写在第一行写 参考航班：' class='text_ts tip-icon tip-icon-info'></span></div></td></tr>");
					
				var tr3 = $("<tr><td width='50' align='right'>交通：</td><td><label><input type='checkbox' name='ebkProdProduct.ebkProdJourneys["+i+"].traffic' value='AIRPLANE'>飞机</label><label><input  type='checkbox' name='ebkProdProduct.ebkProdJourneys["+i+"].traffic'  value='BUS'>巴士</label><label><input  type='checkbox'  name='ebkProdProduct.ebkProdJourneys["+i+"].traffic' value='SHIP'>轮船</label><label><input  type='checkbox'  name='ebkProdProduct.ebkProdJourneys["+i+"].traffics' value='TRAIN'>火车</label></td></tr>");
				
				var tr4 = $("<tr><td width='50' align='right'>用餐：</td><td><input type='text'  name='ebkProdProduct.ebkProdJourneys["+i+"].dinner'  class='width_320'  maxLength='100' value='早餐：敬请自理、中餐：敬请自理、晚餐：敬请自理' ><span tip-content='用含或敬请自理或者是特色餐' class='text_ts tip-icon tip-icon-info'></span></td></tr>");
				
				var tr5 = $("<tr><td width='50' align='right'>住宿：</td><td><input type='text' name='ebkProdProduct.ebkProdJourneys["+i+"].hotel' class='width_320'  maxLength='1000' ><span tip-content='酒店必须有具体的酒店名称，如果用预付款协议也必须写具体的参考酒店名称' class='text_ts tip-icon tip-icon-info'></span></td></tr>");
				
				var tr6 = $("<tr><td width='50' align='right'></td><td><span class='as-file js_up'><span style='margin-left: 8px;margin-bottom: 8px;' class='fp_btn'>上传图片</span><input type='file' exts='png|jpg|bmp' name='tripPic' class='as-input-file' id='"+i+"' onchange='uploadPicFun("+i+")'/></span>　可上传图片（尺寸：320*240）；比例为4:3；(0=<上传张数=<2)</td></tr>");
				
				var tr7 = $("<tr><td></td><td id='show_img_id_"+i+"'></td></tr>" );
               
				tab.append(tr1);
				tab.append(tr2);
				tab.append(tr3);
				tab.append(tr4);
				tab.append(tr5);
				tab.append(tr6);
				tab.append(tr7);
				td.append(tab);
				tr.append(td);
				$("#showDate_tab").append(tr);
				
			}else if($("#tr_"+i).is(":hidden")){
				$("#tr_"+i).show();
			}
		}
		if($(".first_tr").length>$("#showDate").val()){
			for(var j=$(".first_tr").length-1;j>=$("#showDate").val();j--){
				$("#tr_"+j).hide();
			}
		}
		$('.text_ts').ui('lvtip', {
			hovershow : 200,
			events : "live"
		});
	}
		
	function uploadPicFun(id){
		var dayIndex = id;
		var imgLength = $("#show_img_id_"+dayIndex).children("span").length;
		if(imgLength>=2){
			alert("每天行程最多只能上传2张图片！");
			return;
		}
		var fill_name_hidden = $("#fill_name_hidden_"+dayIndex+"_"+0);
		if(fill_name_hidden.val()==''||fill_name_hidden.val()==undefined){
			imgLength = 0;
		}else{
			imgLength = 1;
		}
		$('#confirm').ajaxForm({
			dataType: 'json',  
		    beforeSend: function() {
				$("#tripPicDayNum").val(dayIndex);
		    },
		    success: function(date) {
		    	var result = eval(date);
		        if(result.success || result.success=="true"){
		        	var html_ = $("<span class='as-file' id='pic_span_"+result.fileId+"'><img src='http://pic.lvmama.com/pics/"+result.fileUrl+"' id='"+result.fileId+"' width='320' height='240' currentpictureid='"+result.fileId+"'/><br><input type='text' value='"+result.fileName+"' maxLength='200' name='ebkProdProduct.ebkProdJourneys["+dayIndex+"].comPictureJourneyList["+imgLength+"].pictureName'/><span style='margin-left: 8px;margin-bottom: 8px;' class='fp_btn' onclick='delThisPic("+dayIndex+","+result.fileId+")'>删除</span><span style=\"margin-left: 8px;margin-bottom: 8px;\" class=\"fp_btn js_min_edit\" id='"+result.fileId+"'>编辑</span><input type='hidden' id='fill_name_hidden_"+dayIndex+"_"+imgLength+"' name='ebkProdProduct.ebkProdJourneys["+dayIndex+"].comPictureJourneyList["+imgLength+"].pictureId' value='"+result.fileId+"' /></span>");
		        	$("#show_img_id_"+dayIndex).append(html_);
		        }else{
		        	var errorMessage = errorTimePriceMessage(result.message);
					alert("保存失败 原因："+errorMessage);
		        }
		    },
		    complete: function(date) {
				$("input[name='tripPic']").val("");
			}
		}); 

		$('#confirm').submit();
	}
	
	function delThisPic(dayIndex,picId){
		$.ajax({ 
			type:"POST", 
			url:'${contextPath }/ebooking/product/deleteEbkProdPic.do', 
			data:{pictureId:picId,ebkProdProductId:$("#ebkProdProductId").val()},
			async: false, 
			success:function (result) { 
				var message=eval(result); 
				if(message.success=='true' || message.success){
					alert("图片删除成功！");
					$("#pic_span_"+picId).remove();
				}else{
					var errorMessage = errorTimePriceMessage(result.message);
					alert("图片删除失败 原因："+errorMessage);
				}
			} 
		}); 
	}
	
	/*基本信息，与驴妈妈的结算信息*/
	$('.submitEdit').live('click', function() {
		var reg1 =  /^\d+$/; 
		if($("#showDate").val().match(reg1) == null){
			alert("天数必须是正整数！");
			$("#showDate").focus();
			return;
		}
		$.ajax({
			type:"POST", 
			url:'${contextPath }/ebooking/product/saveEbkMultiJourneyTrip.do', 
			data:$("#confirm").serialize(), 
			//async: false, 
			success:function (result) {
				var message=eval(result); 
				if(message.success=='true' || message.success){
					alert("保存成功！");
				}else{
					var errorMessage = errorTimePriceMessage(result.message);
					if(undefined==errorMessage){
						errorMessage = result.message;
					}
					alert("保存失败 原因："+errorMessage);
				}
			}
		}); 
	});
</script>
</html>