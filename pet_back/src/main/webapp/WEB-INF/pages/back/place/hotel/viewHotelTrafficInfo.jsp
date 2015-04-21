<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>销售信息添加</title>
<script type="text/javascript">
</script>
</head>
<body>
	<div id="trafficInfoListDiv" class="cg_tab_list" style="display:block;" >
		<table class="p_table" style="width:800px;">
			<s:iterator value="trafficInfoList" var="trafficInfo">
				<tr class="tr_${id}">
					<td class="p_label"  data="${trafficStyle}" style="width:80px"><strong>${chTrafficInfo }</strong></td>
					<td width="200px">${from }</td>
					<td class="p_label" style="width:100px"><strong>距离酒店：</strong></td>
					<td width="100px">${distance }公里</td>
					<td class="p_label" style="width:60px"><strong>备注：</strong></td>
					<td width="200px">${description}</td>
					<td class="p_label" style="width:100px">
						<a href="javascript:modify(${id});">[修改]</a> 
						<a href="javascript:delHotelTrafficInfo(${id});">[删除]</a>
					</td>
				</tr>			
			</s:iterator>
		</table>
		<p class="tc mt10 mb10">
			<input class="btn btn-small w5" value="新增交通方式" id="BtnAddHotelTrafficInfo"></input> 
			<!-- <input class="btn btn-small w5" value="关闭" onclick='$("#popDiv").dialog("close");'></input> -->
		</p>
	</div>
	<div id="AddHotelTrafficInfo" style="display: none;">
	<table  class="p_table form-inline" style="width:800px">
			<tr>
				<td class="p_label" width="20%"><span class="red">*</span>交通类型</td>
				<td>
					<input type="hidden" name="hotelTrafficInfo.id" id="hotelTrafficInfo_id" />
					<select name="hotelTrafficInfo.trafficStyle" id="hotelTrafficInfo_trafficStyle">
						<option value="1">自驾路线</option>
						<option value="2">火车站</option>
						<option value="3">机场</option>
						<option value="4">市中心</option>
						<option value="5">码头</option>
						<option value="6">地铁</option>
						<option value="7">长途汽车站</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="p_label" width="20%"><span class="red">*</span>出发地</td>
				<td><input type="text" id="hotelTrafficInfo_from" name="hotelTrafficInfo.from" width="100px" placeholder="例如：人民广场"/></td>
			</tr>			
			<tr>
				<td class="p_label"><span class="red">*</span>距离酒店</td>
				<td><input type="text" id="hotelTrafficInfo_distance" name="hotelTrafficInfo.distance" placeholder="例如：10"/>公里</td>
			</tr>
			<tr>
				<td class="p_label"><span class="red">*</span>备注</td>
				<td><textarea class="p-textarea" id="hotelTrafficInfo_description" name="details.description" placeholder="例如：乘坐地铁2号线(浦东国际机场方向)，在世纪大道站下车(12号口出)，步行约490米。乘坐出租车约22元。"></textarea></td>
			</tr>
		</table>
		<p class="tc mt10 mb10">
			<input class="btn btn-small w5" value="保存" id="BtnSaveHotelTrafficInfo"/>  　　 
			<input class="btn btn-small w5" value="取消" id="btnCancel"/>
		</p>
		</div>
</body>
<script type="text/javascript">
			 $(function(){
				
				$("#BtnAddHotelTrafficInfo").click(function(){
					$("#hotelTrafficInfo_from").val("");
					$("#hotelTrafficInfo_distance").val("");
					$("#hotelTrafficInfo_description").val("");
					$("#trafficInfoListDiv").hide();
			 		$("#AddHotelTrafficInfo").show();
				});
				
				$("#BtnSaveHotelTrafficInfo").click(function(){
					if ($("#hotelTrafficInfo_trafficStyle").val() == "") {
						alert("请选择交通方式");
						$("#hotelTrafficInfo_trafficStyle").focus();
						return;
					}
					if ($("#hotelTrafficInfo_from").val() == "") {
						alert("请输入出发地");
						$("#hotelTrafficInfo_from").focus();
						return;
					}
					if ($("#hotelTrafficInfo_distance").val() == "") {
						alert("请输入距离");
						$("#hotelTrafficInfo_distance").focus();
						return;
					}
					if ($("#hotelTrafficInfo_description").val() == "") {
						alert("请输入备注");
						$("#hotelTrafficInfo_description").focus();
						return;
					}
					$.ajax({
	        	 		url: "<%=basePath%>/hotel/saveHotelTrafficInfo.do",
						type:"post",
	        	 		data: { "hotelTrafficInfo.id":$("#hotelTrafficInfo_id").val(),
								"hotelTrafficInfo.placeId":"${placeId}",
								"hotelTrafficInfo.trafficStyle":$("#hotelTrafficInfo_trafficStyle").val(),
								"hotelTrafficInfo.from":$("#hotelTrafficInfo_from").val(),
								"hotelTrafficInfo.distance":$("#hotelTrafficInfo_distance").val(),
								"hotelTrafficInfo.description":$("#hotelTrafficInfo_description").val()
							},
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
	        	 		dataType:"json",
	        	 		success: function(result) {
							if (result.success) {
								alert("保存成功");
								$.ajaxDialog('${basePath}/hotel/viewHotelTrafficInfo.do?placeId=${placeId}&stage=3','酒店交通信息','traffice_info_dialog');
							} else {
								alert("保存失败");
							}
	        	 		}
	        		});
				})
				$("#btnCancel").click(function(){
					$("#AddHotelTrafficInfo").hide();
					$("#trafficInfoListDiv").show();
				});
			 });
			 
			 function modify(id) {
			 		$("#trafficInfoListDiv").hide();
			 		$("#AddHotelTrafficInfo").show();
			 		
					var obj = $(".tr_" + id + " > td").first();
					
					$("#hotelTrafficInfo_id").val(id);
					$.each($("#hotelTrafficInfo_trafficStyle > option"), function(){
						if ($(this).attr("value") == obj.attr("data")) {
							$(this).attr("selected","selected")
						} else {
							$(this).removeAttr("selected");
						}
					});
					$("#hotelTrafficInfo_from").val(obj.next().html());
					$("#hotelTrafficInfo_distance").val(obj.next().next().next().html().substr(0, obj.next().next().next().html().length - 2));
					$("#hotelTrafficInfo_description").val(obj.next().next().next().next().next().html());
					
					$("#AddHotelTrafficInfo").show();
				 }
			 
			 function delHotelTrafficInfo(id) {
				 var flag = window.confirm("您确定需要删除此条目吗?");
				 if (flag) {
					 $.ajax({
		        	 		url: "<%=basePath%>/hotel/delHotelTrafficInfo.do",
							type:"post",
		        	 		data: {"hotelTrafficInfo.id":id},
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
		        	 		dataType:"json",
		        	 		success: function(result) {
		        	 			alert("删除成功!");
		        	 			$.ajaxDialog('${basePath}/hotel/viewHotelTrafficInfo.do?placeId=${placeId}&stage=3','酒店交通信息','traffice_info_dialog')
		        	 		}
		        		});
				 }
			 }
</script>
</html>