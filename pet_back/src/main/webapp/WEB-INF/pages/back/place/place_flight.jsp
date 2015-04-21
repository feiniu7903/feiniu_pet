<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><title>航班详细信息</title>
<script type="text/javascript" src="<%=basePath %>js/base/date.js"></script>
<script type="text/javascript">
		 $(function(){
			  $("#searchAirport_start").jsonSuggest({
					url : "/pet_back/place/placeAirportBysearch.do",
					maxResults : 10,
					width : 300,
					emptyKeyup : false,
					minCharacters : 1,
					onSelect : function(item) {
					$("#start_airportId").val(item.id);
					showPlaceInfo(item.placeId,true);
				}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#searchAirport_start").val("");
					$("#start_airportId").val("");
					$("#city_start").val("");
				    $("#start_placeId").val("");
				}
			});
 
			  $("#searchAirport_arrive").jsonSuggest({
					url : "/pet_back/place/placeAirportBysearch.do",
					maxResults : 10,
					width : 300,
					emptyKeyup : false,
					minCharacters : 1,
					onSelect : function(item) {
						$("#arrive_airportId").val(item.id);
						showPlaceInfo(item.placeId,false);
					}
			}).change(function() {
				if ($.trim($(this).val()) == "") {
					$("#searchAirport_arrive").val("");
					$("#arrive_airportId").val("");
					$("#city_arrive").val("");
				    $("#arrive_placeId").val("");
				}
			});
			$("#placeFlightFrom").validateAndSubmit(function($form,dt){
        	 			var data=eval("("+dt+")");
						if (data.success) {
							alert('操作成功！');
							document.location.reload();
						}else{
							alert(data.msg);
						}
			 },{onSubmit:function($form){
				 var length=$("input[name='berthInfoOptions']:checked").length;
				 if(length==0){
		              alert('舱位信息为必填项！');
		              return false;
			      }
			     return true;
			}});
			
			function showPlaceInfo(placeId,witch){
				$.ajax({
					type:"post",
			        url:"/pet_back/place/queryPlaceByPlaceId.do",
			        data:"placeId="+placeId,
			        error:function(){
			            alert("与服务器交互错误!请稍候再试!");
			        },
			        success:function(data){
			        var dt=eval("("+data+")");
				     if(witch){
				        $("#city_start").val(dt.cityName);
				        $("#start_placeId").val(dt.placeId);
				      }else{
				        $("#city_arrive").val(dt.cityName);
						$("#arrive_placeId").val(dt.placeId);	
				      }
			       }
			  });
			}

		 });
		 function getFlightInfo() {
			 var $form = $("#placeFlightFrom");
			 var flightNo = $.trim($form.find("input[name='placeFlight.flightNo']").val());
			 if(flightNo == "") {
				 alert("请填写航班号！");
				 return;
			 }
			 $("#synBtn").attr("disabled", true);
			 $.ajax({
					type:"post",
			        url:"/pet_back/place/getFlightInfo.do",
			        data:"flightNo="+flightNo,
			        error:function(){
			            alert("与服务器交互错误!请稍候再试!");
			        },
			        success:function(data){
			        	var d=eval("("+data+")");
			        	if (d.success) {
			        		$form.find("input[name='placeFlight.startAirport.airportName']").val(d.startAirportName);
			        		$form.find("input[name='placeFlight.startAirportId']").val(d.startAirportId);
			        		$form.find("input[name='placeFlight.arriveAirport.airportName']").val(d.arriveAirportName);
			        		$form.find("input[name='placeFlight.arriveAirportId']").val(d.arriveAirportId);
			        		$form.find("input[name='placeFlight.startPlaceName']").val(d.startPlaceName);
			        		$form.find("input[name='placeFlight.startPlaceId']").val(d.startPlaceId);
			        		$form.find("input[name='placeFlight.arrivePlaceName']").val(d.arrivePlaceName);
			        		$form.find("input[name='placeFlight.arrivePlaceId']").val(d.arrivePlaceId);
			        		$form.find("input[name='placeFlight.startTime']").val(d.startTime);
			        		$form.find("input[name='placeFlight.arriveTime']").val(d.arriveTime);
			        		$form.find("input[name='placeFlight.flightTime']").val(d.flightTime);
			        		$form.find("input[name='placeFlight.startTerminal']").val(d.startTerminal);
			        		$form.find("input[name='placeFlight.arriveTerminal']").val(d.arriveTerminal);
			        		
			        		//选中航空公司
			        		$form.find("select[name='placeFlight.airlineId'] option").each(function() {
			        			if(d.airlineId.match($(this).text())) {
			        				$(this).attr("selected", true);
			        				return false;
			        			}
			        		});
			        		$form.find("select[name='placeFlight.airplaneId'] option").each(function() {
			        			if($(this).text().match(d.airplaneId)) {
			        				$(this).attr("selected", true);
			        				return false;
			        			}
			        		});
						} else {
							alert(d.msg);
						}
			        	$("#synBtn").attr("disabled", false);
			       }
			  });
		 }
		 
		 function closeDialog(){
				$("#viewPlaceFlightDiv").dialog("close");
			}
	</script></head>
	<body>
	<div class="p_box">
	<form action="<%=basePath%>place/placeFlightSave.do" method="post" name="placeFlightFrom" id="placeFlightFrom">
	<s:hidden name="placeFlight.placeFlightId"/>
		<table class="p_table no_bd form-inline" width="100%">
			<tr class="label">
				<td class="label"><span class="required">*</span>航班号:</td>
				<td><s:textfield name="placeFlight.flightNo" cssClass="required" />
					<input class="btn btn-small w5" type="button" id="synBtn" value="同步" onclick="javascript:getFlightInfo();"/> 
				</td>
			    <td class="label"><span class="required">*</span>航空公司:</td>
				<td><s:select list="placeAirlineList" headerValue="请选择" headerKey=""
						listKey="placeAirlineId" listValue="airlineName"
						name="placeFlight.airlineId" cssClass="required" /></td>
			</tr>
			<tr class="label">
				<td class="label"><span class="required">*</span>始发机场:</td>
				<td>
				<s:textfield id="searchAirport_start" name="placeFlight.startAirport.airportName" cssClass="required" />
				<s:hidden name="placeFlight.startAirportId" id="start_airportId" />
				</td>
				<td class="label"><span class="required">*</span>到达机场:</td>
				<td>
				<s:textfield id="searchAirport_arrive" name="placeFlight.arriveAirport.airportName" cssClass="required" />
				<s:hidden name="placeFlight.arriveAirportId" id="arrive_airportId" /></td>
			</tr>
			<tr>
				<td class="label"><span class="red">*</span>出发城市:</td>
				<td><s:textfield id="city_start" name="placeFlight.startPlaceName" readonly="true"/>
				<s:hidden name="placeFlight.startPlaceId" id="start_placeId"/>
				</td>
				<td class="label"><span class="required">*</span>抵达城市:</td>
				<td>
				<s:textfield id="city_arrive" name="placeFlight.arrivePlaceName" readonly="true"/>
				<s:hidden name="placeFlight.arrivePlaceId" id="arrive_placeId"/>
				</td>
			</tr>
				<tr>
				<td class="label"><span class="red">*</span>起飞时间:</td>
				<td><s:textfield name="placeFlight.startTime" id="startTime" cssClass="required times" /></td>
				<td class="label"><span class="required">*</span>降落时间:</td>
				<td><s:textfield name="placeFlight.arriveTime" id="arriveTime" cssClass="required times" /></td>
			</tr>
			<tr>
				<td class="label"><span class="red">*</span>经停次数:</td>
				<td><s:textfield name="placeFlight.stopTime" cssClass="required digits" /></td>
				<td class="label">机型:</td>
				<td><s:select list="placePlaneModel"
						headerValue="请选择" headerKey="" listKey="placeModelId"
						listValue="planeName" name="placeFlight.airplaneId" /></td>
			</tr>
			<tr>
				<td class="label"><span class="red">*</span>飞行时间:</td>
				<td><s:textfield name="placeFlight.flightTime" cssClass="required number digits"/>小时</td>
				<td class="label"><span class="required">*</span>舱位信息:</td>
				<td><s:checkboxlist name="berthInfoOptions" list="trafficeBranchList" listKey="code" listValue="name"/></td>
			</tr>
			<tr>
				<td class="label">始发航站楼:</td>
				<td><s:textfield name="placeFlight.startTerminal"/></td>
				<td class="label">到达航站楼:</td>
				<td><s:textfield name="placeFlight.arriveTerminal"/></td>
			</tr>
		</table>
	    <p class="tc mt10"><input type="submit"  class="btn btn-small w3" value="确定"/>
	     <input class="btn btn-small w5" type="button"  value="关闭" onclick="javascript:closeDialog();"/> 
	    </p>
	</form>
	</div>
	</body>
</html>