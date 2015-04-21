<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" media="all" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">

<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script type="text/javascript" src="${contextPath}/js/base/jquery.validate.js"></script>
<script type="text/javascript" src="${contextPath}/js/base/jquery.form.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>


<script type="text/javascript">
$(function(){	 
	 $("#tableFlight").ui("calendar", {
			input : "#Calendar1",
			parm : {
				dateFmt : 'yyyy-MM-dd'
			}
		});
	 if("${sendType}"=="1"){
		 send();
	 }
}); 

function deleteOrdRouteTravelFlight(obj){
	$(obj).parent().parent().remove();
	
}
function insertOrdRouteTravelFlight(){
	//增加一航班信息
	var calendarId = "Calendar1"+new Date().getTime();
	var  html="";
	html+="<tr>";
	html+="<td><textarea class='w120' id='flightInfo' name='groupTravelInfo.place'></textarea></td>";
	html+="<td><textarea id='"+calendarId+"' readonly='readonly' id='flightInfo' name='groupTravelInfo.flightDate' class='w80' ></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.flightNo'></textarea></td>";
	html+="<td><textarea class='w120' id='flightInfo' name='groupTravelInfo.airport'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.takeOffTime'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.arriveTime'></textarea></td>";
	html+='<td><span class="fp_btn add_traffic_span" onclick="deleteOrdRouteTravelFlight(this)">删除</span></td>';
	html+="</tr>";   
	$("#tableFlight").append(html);

	$("#tableFlight").ui("calendar", {
		input : "#"+calendarId,
		parm : {
			dateFmt : 'yyyy-MM-dd'
		}
	});
}

function previewGroupAdviceNote(){
	//var jiesong = $("input[name='jiesong']:checked").val();
	//$("#div_jiesong").hide();
	//$("#jiesong").text(jiesong);
	//$("#div_jiesong_2").show();
	$(".fp_btn.add_traffic_span").hide();
	var html = $("#div_content").html();
	$("#content").val(html);
	$(".fp_btn.add_traffic_span").show();
	//$("#div_jiesong").show();
	//$("#div_jiesong_2").hide();
	document.forms[0].action="${basePath}/ebooking/task/previewGroupAdviceNote.do";
	document.forms[0].method = "post";
	document.forms[0].submit();
}

function check() {
	if ($("#productName").val().replace(/\s/g,"")=='') {
		alert('产品名称不能为空');
		return false;
	}
	if ($("#flightDate").val().replace(/\s/g,"")=='') {
		alert('航班时间不能为空');
		return false;
	}
	if ($("#place").val().replace(/\s/g,"")=='') {
		alert('出发地点不能为空');
		return false;
	}
	if ($("#firstTravellerName").val().replace(/\s/g,"")=='') {
		alert('预订游客姓名不能为空');
		return false;
	}
	if ($("#costContain").val().replace(/\s/g,"")=='') {
		alert('费用包含不能为空');
		return false;
	}
	if ($("#noCostContain").val().replace(/\s/g,"")=='') {
		alert('费用不包含不能为空');
		return false;
	}
	if ($("#flightInfo").val()==null || $("#flightInfo").val()=='') {
		alert('航班信息不能为空');
		return false;
	}
	if ($("#jiesong").val()==null || $("#jiesong").val()=='') {
		alert('是否境外机场接送不能为空');
		return false;
	}
	return true;
}

function submitGroupAdviceNoteForm(){
	//var jiesong = $("input[name='jiesong']:checked").val();
	//$("#div_jiesong").hide();
	//$("#jiesong").text(jiesong);
	//$("#div_jiesong_2").show();
	//$(".fp_btn.add_traffic_span").hide();
	//var html = $("#div_content").html();
	//$("#content").val(html);
	//$(".fp_btn.add_traffic_span").show();
	//$("#div_jiesong").show();
	//$("#div_jiesong_2").hide();
	if (!check()) {
		return;
	}
	$('#groupAdviceNoteForm').attr("action","${basePath}/ebooking/task/gotoGroupNote.do");
	$("#sendType").val("1");
	$('#groupAdviceNoteForm').submit();
	
}
function send(){
	$(".fp_btn.add_traffic_span").hide();
	var html = $("#div_content").html();
	$("#content").val(html);
	$(".fp_btn.add_traffic_span").show();
	$('#groupAdviceNoteForm').ajaxSubmit({
		url : "${basePath}/ebooking/task/submitGroupAdviceNote.do",
		success : function(obj) {
			if("success" == obj.flag){
				alert('出团通知书生成并发送成功');
			}else{
				alert('出团通知书生成并发送失败');
			}
		}
	});
}


</script>
</head>
<body id="body_ddgl">
<form name="groupAdviceNoteForm" id="groupAdviceNoteForm" method="post" >
<input type="hidden" id="sendType" name="sendType" />
<input type="hidden" id="content" name="content" value=""/>
<input type="hidden" id="subProductType" name="subProductType" value="${subProductType}"/>
<!--出团通知书开始-->
<div class="chutuan_box" id="div_content">
	<h3>出团通知书—出境自由行</h3>
    <div class="chutuan_list">
        <table class="chutuan_table1">
            <tr>
                <td width="120" align="right"><b>致尊敬的游客：</b></td>
                <td></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>产品名称：</td>
                <td><textarea id="productName" name="productName" class="w600">${productName}</textarea></td>
            </tr>
            <tr>
                <td align="right">订单编号：</td>
                <td><textarea id="orderId" name="orderId" readonly="readonly">${orderId}</textarea></td>
            </tr>
            <tr>
                <td align="right">出发日期：</td>
                <td><textarea name="travelTimeStart" readonly="readonly" class="w120">${travelTimeStart}</textarea>~<textarea name="travelTimeEnd" readonly="readonly" class="w120">${travelTimeEnd}</textarea></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>航班时间：</td>
                <td><textarea id="flightDate" name="flightDate" class="w120">${flightDate }</textarea></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>出发地点：</td>
                <td><textarea id="place" name="place" class="w600">${place }</textarea></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>预订游客姓名：<br />（中文/拼音）</td>
                <td><textarea id="firstTravellerName" name="firstTravellerName" class="w600">${firstTravellerName }</textarea></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>是否境外机场接送：</td>
                <td>
	                <%-- <div id="div_jiesong">
		                <s:if test="#jiesong=='是'">
			                <label class="input_radio"><input type="radio" name="jiesong" value="是" checked />是</label>
			                <label class="input_radio"><input type="radio" name="jiesong" value="否" />否</label>
		                </s:if>
		                <s:else>
			                <label class="input_radio"><input type="radio" name="jiesong" value="是" />是</label>
			                <label class="input_radio"><input type="radio" name="jiesong" value="否" checked />否</label>
		                </s:else>
	                </div> --%>
	                <div id="div_jiesong_2">
	                	<%-- <span id="jiesong" ></span> --%>
	                	<textarea id="jiesong" name="jiesong" class="w120">${jiesong }</textarea>
	                </div>
                </td>
            </tr>
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span>预订人数：</td>
                <td>
                	<table class="table01">
                    	<tr>
                        	<th>成人</th>
                            <th>儿童</th>
                            <th>总数</th>
                        </tr>
                    	<tr>
                        	<td><textarea name="adultQuantity" readonly="readonly" class="w150">${adultQuantity }</textarea></td>
                            <td><textarea name="childQuantity" readonly="readonly" class="w150">${childQuantity }</textarea></td>
                            <td><textarea name="quantity" readonly="readonly" class="w150">${quantity }</textarea></td>
                        </tr>
                    </table>
                </td>
            </tr>
            
        </table>
    </div>
    

    <div class="chutuan_list">
        <table class="chutuan_table2">
            <tr>
                <td width="120" align="right">境外地接社：</td>
                <td><textarea name="groupTravelInfo.foreignGroupName">${groupTravelInfo.foreignGroupName }</textarea></td>
                <td width="140" align="right">境外地接社紧急联系人：</td>
                <td><textarea name="groupTravelInfo.foreignGroupContact">${groupTravelInfo.foreignGroupContact }</textarea></td>
            </tr>
            <tr>
                <td align="right">境外地接社地址：</td>
                <td><textarea name="groupTravelInfo.foreignGroupAddress">${groupTravelInfo.foreignGroupAddress }</textarea></td>
                <td align="right">驴妈妈应急联系电话：</td>
                <td><textarea readonly="readonly">1010-6060</textarea></td>
            </tr>
            <tr>
                <td align="right">驴妈妈质检电话：</td>
                <td><textarea readonly="readonly">+86-021-60561634</textarea></td>
                <td align="right"></td>
                <td></td>
            </tr>
        </table>
    </div>
    
    <div class="chutuan_list">
    	<table class="chutuan_table3">
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>费用包含：</b></td>
                <td><textarea id="costContain" name="costContain">${costContain}</textarea></td>
            </tr>
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>费用不包含：</b></td>
                <td><textarea id="noCostContain" name="noCostContain">${noCostContain}</textarea></td>
            </tr>
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>航班信息：<br />（往返交通）</b></td>
                <td>
                	<table id="tableFlight" class="table01">
                    	<tr>
                        	<th>地点</th>
                        	<th>日期</th>
                            <th>航班号</th>
                            <th>机场</th>
                            <th>起飞时间</th>
                            <th>抵达时间</th>
                            <th width="50"></th>
                        </tr>
                        <s:if test="travelFlightList!=null || travelFlightList.size!=0">
                        <s:iterator value="travelFlightList" var="obj">
                    	<tr>
                    		<td><textarea class='w120' id="flightInfo" name='groupTravelInfo.place'>${obj.place }</textarea></td>
                    		<td><textarea id='Calendar1' readonly='readonly' class='w80' id="flightInfo" name='groupTravelInfo.flightDate'>${obj.flightDate }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.flightNo'>${obj.flightNo }</textarea></td>
                    		<td><textarea class='w120' id="flightInfo" name='groupTravelInfo.airport'>${obj.airport }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.takeOffTime'>${obj.takeOffTime }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.arriveTime'>${obj.arriveTime }</textarea></td>
                    		<td><span class="fp_btn add_traffic_span" onclick="deleteOrdRouteTravelFlight(this)">删除</span></td>
                    	</tr>
                        </s:iterator>  
                        </s:if>
                    </table>
                    <span class="fp_btn add_traffic_span" onclick="insertOrdRouteTravelFlight()">新增一栏</span>
                </td>
            </tr>
            
        </table>
    </div>
    
    <div class="chutuan_list">
    	<table class="chutuan_table3">
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>行程信息：</b></td>
                <td>
                	<s:iterator value="viewJourneyVoList" var="vj">           	
                	<table class="chutuan_xc">
                    	<tr>
                        	<td colspan="2" width="170">${vj.visitTimeDesc}<b>${vj.titleDesc}</b>
                        	<textarea name="viewJourneyVo.visitTime" style="display:none">${vj.visitTimeDesc}</textarea>
                        	<textarea name="viewJourneyVo.title" style="display:none">${vj.titleDesc}</textarea></td>
                        </tr>
                        <tr>
                        	<td width="60" class="vtop">活动内容：</td>
                            <td><textarea style="overflow:auto; height:auto;" name="viewJourneyVo.content">${vj.contentDesc}</textarea></td>
                        </tr>
                        <tr>
                        	<td class="vtop">用餐标准：</td>
                            <td><textarea name="viewJourneyVo.dinner">${vj.dinnerDesc}</textarea></td>
                        </tr>
                        <tr>
                        	<td class="vtop">住宿标准：</td>
                            <td><textarea name="viewJourneyVo.hotel">${vj.hotelDesc}</textarea></td>
                        </tr>
                    </table>
                	</s:iterator>
                </td>
            </tr>
        </table>
    </div>
    
    <div class="chutuan_list">
    	<table>
            <tr>
                <td width="120" align="right" class="vtop"><b>酒店提示：</b></td>
                <td><textarea name="hotelTips">${hotelTips}</textarea></td>
            </tr>
            <tr>
                <td width="120" align="right" class="vtop"><b>出行提示：</b></td>
                <td><textarea name="actionToKnow">${actionToKnow}</textarea></td>
            </tr>
        </table>
    </div>
    
    <div class="chutuan_list">
    	<table>
            <tr>
                <td width="120" align="right" class="vtop"><b>附件：酒店预订单</b></td>
            </tr>
            <tr>
                <td width="120"></td>
                <td><textarea name="hotelOrderList">${hotelOrderList}</textarea></td>
            </tr>
        </table>
    </div>
    
    <p class="chutuan_ts">
    	<span class="f_red">！</span>请认真核实预定内容,如有不符,请在1个工作日内通知以便更改,否则视为自动认同.
    </p>
</div>
<div class="btn_box">
	<a class="fp_btn" href="javascript:previewGroupAdviceNote()" target="_self">预览</a>
    <a class="fp_btn" href="javascript:submitGroupAdviceNoteForm()" target="_self">提交</a>
    <a class="fp_btn" href="javascript:window.opener=null;window.open('','_self');window.close();">取消</a>
</div>
</form>
</body>
</html>