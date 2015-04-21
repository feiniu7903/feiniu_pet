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
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.flightNo'></textarea></td>";
	html+="<td><textarea class='w120' id='flightInfo' name='groupTravelInfo.airport'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.terminal'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.takeOffTime'></textarea></td>";
	html+="<td><textarea class='w80' id='flightInfo' name='groupTravelInfo.remarks'></textarea></td>";
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
	if ($("#productName").val().replace(/\s/g,"")=='') {
		alert('旅游线路不能为空');
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
	<h3>出团通知书—长途跟团游</h3>
    <div class="chutuan_list">
        <table class="chutuan_table1">
            <tr>
                <td width="120" align="right"><b>致尊敬的游客：</b></td>
                <td></td>
            </tr>
            <tr>
                <td width="120" align="right"><span class="f_red">*</span>旅游线路：</td>
                <td><textarea id="productName" name="productName" class="w600">${productName}</textarea></td>
            </tr>
            <tr>
                <td width="120" align="right"><span class="f_red">*</span>客人姓名：</td>
                <td><textarea name="firstTravellerName" readonly="readonly" class="w600">${firstTravellerName}</textarea></td>
            </tr>
        </table>
    </div>
    <div class="chutuan_list">
        <table class="chutuan_table2">
            <tr>
                <td align="right">订单编号：</td>
                <td><textarea readonly="readonly">${orderId}</textarea></td>
                <td align="right">旅游日期：</td>
                <td><textarea name="travelTimeStart" readonly="readonly" class="w120">${travelTimeStart}</textarea>~<textarea name="travelTimeEnd" readonly="readonly" class="w120">${travelTimeEnd}</textarea></td>
            </tr>
            <tr>
                <td align="right">接团方式：</td>
                <td><textarea name="groupTravelInfo.groupType">${groupTravelInfo.groupType}</textarea></td>
                <td align="right">接机导游电话：</td>
                <td><textarea name="groupTravelInfo.contactTel">${groupTravelInfo.contactTel}</textarea></td>
            </tr>
            <tr>
                <td align="right">当地应急电话：</td>
                <td><textarea name="groupTravelInfo.localTel">${groupTravelInfo.localTel}</textarea></td>
                <td align="right">驴妈妈应急联系电话：</td>
                <td><textarea readonly="readonly">60561632*1022</textarea></td>
            </tr>
            <tr>
                <td align="right">驴妈妈质检电话：</td>
                <td><textarea readonly="readonly">021-60561634</textarea></td>
                <td align="right">代理社：</td>
                <td><textarea readonly="readonly">上海驴妈妈国际旅行社有限公司</textarea></td>
            </tr>
            
        </table>
    </div>
    <div class="chutuan_list">
    	<table>
        	<tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>客人信息：</b></td>
                <td>
                	<table class="table01">
                    	<tr>
                        	<th>姓名</th>
                            <th>证件类型</th>
                            <th>证件号码</th>
                            <th>联系方式</th>
                        </tr>
                        <s:iterator value="travellerList" var="traveller">
                        	<tr>
                        	<td><textarea name="travellerInfo.name" class="w150" readonly="readonly">${traveller.name}</textarea></td>
                            <td>
                            <textarea class="w150" readonly="readonly">${traveller.zhCertType}</textarea>
                            <textarea name="travellerInfo.certType" style="display:none">${traveller.certType}</textarea>
                            </td>
                            <td><textarea name="travellerInfo.certNo" class="w150" readonly="readonly">${traveller.certNo}</textarea></td>
                            <td><textarea name="travellerInfo.mobile" class="w150" readonly="readonly">${traveller.mobile}</textarea></td>
                            </tr>
                         </s:iterator>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>航班信息：</b></td>
                <td>
                	<table id="tableFlight" class="table01">
                    	<tr>
                        	<th>出发日期</th>
                            <th>航班号（车次号）</th>
                            <th>机场（火车站）</th>
                            <th>航站楼</th>
                            <th>起飞时间</th>
                            <th width="80">备注</th>
                            <th width="60"></th>
                        </tr>
                        <s:if test="travelFlightList!=null || travelFlightList.size!=0">
                        <s:iterator value="travelFlightList" var="obj">
                    	<tr>
                    		<td><textarea id='Calendar1' readonly='readonly' class='w80' id="flightInfo" name='groupTravelInfo.flightDate'>${obj.flightDate }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.flightNo'>${obj.flightNo }</textarea></td>
                    		<td><textarea class='w120' id="flightInfo" name='groupTravelInfo.airport'>${obj.airport }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.terminal'>${obj.terminal }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.takeOffTime'>${obj.takeOffTime }</textarea></td>
                    		<td><textarea class='w80' id="flightInfo" name='groupTravelInfo.remarks'>${obj.remarks }</textarea></td>
                    		<td><span class="fp_btn add_traffic_span" onclick="deleteOrdRouteTravelFlight(this)">删除</span></td>
                    	</tr>
                        </s:iterator>  
                        </s:if>
                    </table>
                    <span class="fp_btn add_traffic_span" onclick="insertOrdRouteTravelFlight()">新增一栏</span>
                    <p class="mt10"><span class="f_red">！</span>建议：请各位游客到达机场后从进口进入，办理登机手续，领取登机牌。</p>
                    <p>具体登记手续操作按照大屏幕显示为准或询问柜台服务人员。</p>
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
    	<table class="chutuan_table3">
        	<tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>费用包含：</b></td>
                <td><textarea id="costContain" name="costContain">${costContain}</textarea></td>
            </tr>
            <tr>
                <td width="120" align="right" class="vtop"><span class="f_red">*</span><b>费用不包含：</b></td>
                <td><textarea id="noCostContain" name="noCostContain">${noCostContain}</textarea></td>
            </tr>
        </table>
    </div>
    
    <div class="chutuan_list">
    	<table>
            <tr>
                <td width="120" align="right" class="vtop"><b>行前须知：</b></td>
                <td><textarea name="actionToKnow">${actionToKnow}</textarea></td>
            </tr>
        </table>
    </div>
</div>
<div class="btn_box">
	<a class="fp_btn" href="javascript:previewGroupAdviceNote()" target="_self">预览</a>
    <a class="fp_btn" href="javascript:submitGroupAdviceNoteForm()" target="_self">提交</a>
    <a class="fp_btn" href="javascript:window.opener=null;window.open('','_self');window.close();">取消</a>
</div>
</form>
</body>
</html>