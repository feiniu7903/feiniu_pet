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
	html+="<td><textarea id='"+calendarId+"' readonly='readonly' id='flightInfo' name='groupTravelInfo.flightDate' class='w80' ></textarea></td>";
	html+="<td><textarea class='w120' id='flightInfo' name='groupTravelInfo.travelInfo'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.flightNo'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.takeOffTime'></textarea></td>";
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
	$(".fp_btn.add_traffic_span").hide();
	var html = $("#div_content").html();
	$("#content").val(html);
	$(".fp_btn.add_traffic_span").show();
	document.forms[0].action="${basePath}/ebooking/task/previewGroupAdviceNote.do";
	document.forms[0].method = "post";
	document.forms[0].submit();
}

function check() {
	if ($("#gatherTime").val().replace(/\s/g,"")=='') {
		alert('集合时间不能为空');
		return false;
	}
	if ($("#gatherPlace").val().replace(/\s/g,"")=='') {
		alert('集合地点不能为空');
		return false;
	}
	if ($("#takeFlag").val().replace(/\s/g,"")=='') {
		alert('接机标志不能为空');
		return false;
	}
	if ($("#contactTel").val().replace(/\s/g,"")=='') {
		alert('领队及联系电话不能为空');
		return false;
	}
	if ($("#contactTel2").val().replace(/\s/g,"")=='') {
		alert('送团及联系电话不能为空');
		return false;
	}
	if ($("#flightInfo").val()==null || $("#flightInfo").val()=='') {
		alert('航班信息不能为空');
		return false;
	}
	return true;
}

function submitGroupAdviceNoteForm(){
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
<form name="groupAdviceNoteForm" id="groupAdviceNoteForm" method="post">
<input type="hidden" id="sendType" name="sendType" />
<input type="hidden" id="content" name="content" value=""/>
<input type="hidden" id="orderId" name="orderId" value="${orderId}"/>
<input type="hidden" id="subProductType" name="subProductType" value="${subProductType}"/>
<!--出团通知书开始-->
<div class="chutuan_box" id="div_content">
	<h3>出团通知书—出境跟团游</h3>
    <div class="chutuan_list">
        <table class="chutuan_table1">
            <tr>
                <td width="120" align="right"><b>致尊敬的游客：</b></td>
                <td></td>
            </tr>
            <tr>
                <td align="right">出发日期：</td>
                <td><textarea name="travelTimeStart" class="w120" readonly="readonly">${travelTimeStart }</textarea>~<textarea name="travelTimeEnd" class="w120" readonly="readonly">${travelTimeEnd }</textarea></td>
            </tr>
        </table>
    </div>
    <div class="chutuan_list">
        <table class="chutuan_table2">
            <tr>
                <td width="120" align="right"><span class="f_red">*</span>集合时间：</td>
                <td><textarea id="gatherTime" name="groupTravelInfo.gatherTime">${groupTravelInfo.gatherTime }</textarea></td>
                <td width="140" align="right"><span class="f_red">*</span>集合地点：</td>
                <td><textarea id="gatherPlace" name="groupTravelInfo.gatherPlace">${groupTravelInfo.gatherPlace }</textarea></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>接机标志：</td>
                <td><textarea id="takeFlag" name="groupTravelInfo.takeFlag">${groupTravelInfo.takeFlag }</textarea></td>
                <td align="right"><span class="f_red">*</span>领队及联系电话：</td>
                <td><textarea id="contactTel" name="groupTravelInfo.contactTel">${groupTravelInfo.contactTel }</textarea></td>
            </tr>
            <tr>
                <td align="right"><span class="f_red">*</span>送团及联系电话：</td>
                <td><textarea id="contactTel2" name="groupTravelInfo.contactTel2">${groupTravelInfo.contactTel2 }</textarea></td>
                <td align="right">国内紧急联系电话：</td>
                <td><textarea readonly="readonly">86-21-61800980</textarea></td>
            </tr>
            <tr>
                <td align="right">境外地接社：</td>
                <td><textarea name="groupTravelInfo.foreignGroupName">${groupTravelInfo.foreignGroupName }</textarea></td>
                <td align="right">境外地接社紧急联系人：</td>
                <td><textarea name="groupTravelInfo.foreignGroupContact">${groupTravelInfo.foreignGroupContact }</textarea></td>
            </tr>
            <tr>
                <td align="right">境外地接社地址：</td>
                <td><textarea name="groupTravelInfo.foreignGroupAddress">${groupTravelInfo.foreignGroupAddress }</textarea></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </div>
    <div class="chutuan_list">
    	<table>
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>航班信息：</b></td>
                <td>
                	<table id="tableFlight" class="table01">
                		<tr>
                        	<th>日期</th>
                            <th>行程</th>
                            <th>航班号</th>
                            <th>起飞、到达时间</th>
                            <th width="100"></th>
                        </tr>
                        <s:if test="travelFlightList!=null || travelFlightList.size!=0">
                        <s:iterator value="travelFlightList" var="obj">
                    	<tr>
                    		<td><textarea id='Calendar1' readonly='readonly' class='w80' id="flightInfo" name='groupTravelInfo.flightDate'>${obj.flightDate }</textarea></td>
                    		<td><textarea class='w120' id="flightInfo" name='groupTravelInfo.travelInfo'>${obj.travelInfo }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.flightNo'>${obj.flightNo }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.takeOffTime'>${obj.takeOffTime }</textarea></td>
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
                <td width="120" align="right" class="vtop"><b>特别提醒：</b></td>
                <td><textarea name="actionToKnow">${actionToKnow}</textarea></td>
            </tr>
        </table>
    </div>
    <p class="chutuan_ts">
    	尊敬的游客"出团通知"共有3类文件，请收到时做核对如有确实请及时联系客服人员<br />
        1、团队出发及紧急联系信息；<br />
        2、旅游行程信息（行程安排、航班、酒店、餐食）；<br />
        3、目的地国家的旅游须知及注意事项。
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