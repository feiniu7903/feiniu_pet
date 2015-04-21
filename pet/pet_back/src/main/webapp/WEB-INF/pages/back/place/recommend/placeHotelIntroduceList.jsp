<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript" src="/super_back/js/prod/sensitive_word.js"></script>
<style type="text/css">
	.div_m1{font-size: 15px;font-weight:bold;}
</style>
	<form class="mySensitiveForm">
	<div class="div_m1">一句话推荐<font color="red">[至少需要一句]</font>--说明：100字内描述酒店的特色特点。让人们能神往</div>
	<table class="p_table table_center" style="width: 950px;">
		<tr>
			<th width="560px;">一句话推荐内容</th>
			<th width="150px;">开始时间</th>
			<th width="150px;">结束时间</th>
			<th width="90px;">操作</th>
		</tr>
		<s:iterator value="placeHotelNoticeList">
			<tr>
				<td><s:property value="noticeContent"/><input type="hidden" value="<s:property value='noticeContent'/>" name="noticeContent${noticeId }" class="sensitiveVad" /></td>
				<td><s:date name="noticeStartTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="noticeEndTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="#" onclick="$.ajaxDialog('${basePath}/place/initPlaceHotelNotice.do?placeHotelNotice.noticeId=<s:property value="noticeId"/>&successUrl=/place/queryAllIntroduce.do','一句话推荐修改','placeHotel_TwoId');">修改</a>
					<a href="#" onclick="javasctipt:deleteValidate('${basePath}/place/deletePlaceHotelNotice.do?placeHotelNotice.noticeId=<s:property value="noticeId"/>&placeId=<s:property value="placeId"/>&noticeType=<s:property value="noticeType"/>');">删除</a>&nbsp;&nbsp;
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="4" class="tc mt10 mb10">
				<input class="btn btn-small w5" type="button" value="新增" onclick="$.ajaxDialog('${basePath}/place/initPlaceHotelNotice.do?placeHotelNotice.placeId=${placeHotelNotice.placeId}&noticeType=RECOMMEND&successUrl=/place/queryAllIntroduce.do','一句话推荐新增','placeHotel_TwoId');"/>
			</td>
		</tr>
	</table>
	<!-- 房型  -->
	<div>&nbsp;</div>
	<div>&nbsp;</div>
	<div class="div_m1">房型</div>
	<table class="p_table table_center" style="width:950px;">
		<tr>
			<th width="150px;">房型名称</th>
			<th width="100px;">加床</th>
			<th width="480px;">内容概述</th>
			<th width="80px;">排序</th>
			<th width="90px;">操作</th>
		</tr>
		<s:iterator value="placeHotelRoomList">
			<tr>
				<td><s:property value="roomName"/><input type="hidden" value="<s:property value='roomName'/>" name="roomName${roomId }" class="sensitiveVad" /></td>
				<td>
					<s:if test="addBed == 2">可加床&nbsp;&nbsp;
						<s:if test="addBedCost == 0">免费</s:if>
						<s:else><s:property value="addBedCost"/>元/床/间夜</s:else>
						</s:if>
					<s:else>不可加床</s:else>
				</td>
				<td><div style="width:560px;overflow:hidden; white-space: nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis; "><s:property value='roomRecommendNoHtml'/><input type="hidden" value="<s:property value='roomRecommendNoHtml'/>" name="roomRecommendNoHtml${roomId }" class="sensitiveVad" /></div></td>
				<td><s:property value="seqNum"/></td>
				<td>
					<a href="#" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelRooms.do?placeHotelRoom.roomId=<s:property value="roomId"/>',title:'房型修改',id:'room_dialog',kindEditorId:'roomRecommendId'});">修改</a>
					<a href="#" onclick="javascript:deleteValidate('${basePath}/place/deletePlaceHotelRoom.do?placeHotelRoom.roomId=<s:property value="roomId"/>&placeHotelRoom.placeId=<s:property value="placeId"/>');">删除</a>&nbsp;&nbsp;
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="5" class="tc mt10 mb10">
				<input id="room_add_btn" class="btn btn-small w5" type="button" value="新增"  
				onclick='$.ajaxDialog({url:"${basePath}/place/initPlaceHotelRooms.do?placeHotelRoom.placeId=${placeHotelRoom.placeId}",title:"房型添加",id:"room_dialog",kindEditorId : "roomRecommendId"});' />
			</td>
		</tr>
	</table>
	<div>&nbsp;</div>
	<div>&nbsp;</div>
	<!-- 酒店特色服务  -->
	<div class="div_m1">酒店特色--(说明：图文方式描绘，酒店的特色服务点，如温泉的汤池、赌博机、东南亚的餐饮特色等 )</div>
	<table class="p_table table_center" style="width: 950px;">
		<tr>
			<th width="300px;">服务名称</th>
			<th width="560px;">内容概述</th>
			<th width="80px;">排序</th>
			<th width="90px;">操作</th>
		</tr>
		<s:iterator value="placeHotelRecommendList">
			<s:if test="recommentType == 'SPECIAL'">
			<tr>
				<td><s:property value="recommendName"/><input type="hidden" value="<s:property value='recommendName'/>" name="recommendName${recommendId }" class="sensitiveVad" /></td>
				<td><div style="width:560px;overflow:hidden; white-space: nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis; "><s:property value="recommentContentNoHtml"/><input type="hidden" value="<s:property value='recommentContentNoHtml'/>" name="recommentContentNoHtml${recommendId }" class="sensitiveVad" /></div></td>
				<td><s:property value="seqNum"/></td>
				<td>
					<a href="#" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelRecommend.do?placeHotelRecommend.recommendId=<s:property value="recommendId"/>',title:'酒店特色服务修改',id:'recommend_dialog',kindEditorId:'hotelRecommendId'});">修改</a>
					<a href="#" onclick="javasctipt:deleteValidate('${basePath}/place/deletePlaceHotelRecommend.do?placeHotelRecommend.recommendId=<s:property value="recommendId"/>&placeHotelRecommend.placeId=<s:property value="placeId"/>&placeHotelRecommend.recommentType=<s:property value="recommentType"/>');">删除</a>&nbsp;&nbsp;
				</td>
			</tr>
			</s:if>
		</s:iterator>
		<tr>
			<td colspan="5" class="tc mt10 mb10">																																																																	   		
				<input  class="btn btn-small w5" type="button" value="新增" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelRecommend.do?placeHotelRecommend.placeId=${placeHotelRecommend.placeId}&placeHotelRecommend.recommentType=SPECIAL',title:'酒店特色服务添加',id:'recommend_dialog',kindEditorId:'hotelRecommendId'});"/>
			</td>
		</tr>
	</table>
	<div>&nbsp;</div>
	<div>&nbsp;</div>
	<!-- 玩法 -->
	<div class="div_m1">玩法--(说明：图文方式描绘，酒店自身该怎么玩，酒店周边怎么玩。将酒店可玩的点深度刻画，营造人们出游的冲动。)</div>
	<table class="p_table table_center" style="width: 950px;">
		<tr>
			<th width="300px;">服务名称</th>
			<th width="560px;">内容概述</th>
			<th width="80px;">排序</th>
			<th width="90px;">操作</th>
		</tr>
		<s:iterator value="placeHotelRecommendList">
			<s:if test="recommentType == 'PLAYING'">
			<tr>
				<td><s:property value="recommendName"/><input type="hidden" value="<s:property value='recommendName'/>" name="recommendName${recommendId }" class="sensitiveVad" /></td>
				<td><div style="width:560px;overflow:hidden; white-space: nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis; "><s:property value="recommentContentNoHtml"/><input type="hidden" value="<s:property value='recommentContentNoHtml'/>" name="recommentContentNoHtml${recommendId }" class="sensitiveVad" /></div></td>
				<td><s:property value="seqNum"/></td>
				<td>
					<a href="#" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelRecommend.do?placeHotelRecommend.recommendId=<s:property value="recommendId"/>',title:'玩法修改',id:'recommend_dialog',kindEditorId:'hotelRecommendId'});">修改</a>
					<a href="#" onclick="javascript:deleteValidate('${basePath}/place/deletePlaceHotelRecommend.do?placeHotelRecommend.recommendId=<s:property value="recommendId"/>&placeHotelRecommend.placeId=<s:property value="placeId"/>&placeHotelRecommend.recommentType=<s:property value="recommentType"/>');">删除</a>
				</td>
			</tr>
			</s:if>
		</s:iterator>
		<tr>
			<td colspan="5" class="tc mt10 mb10">
				<input class="btn btn-small w5" type="button" value="新增" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelRecommend.do?placeHotelRecommend.placeId=${placeHotelRecommend.placeId}&placeHotelRecommend.recommentType=PLAYING',title:'玩法添加',id:'recommend_dialog',kindEditorId:'hotelRecommendId'});"/>
			</td>
		</tr>
	</table>
	<form>
	<div style="padding-top: 10px;text-align:center;"><input class="btn btn-small w5" type="button" value="保存" onclick="dataValidate('${basePath}/place/dataValidate.do?placeId=${placeId}');"/></div>	
<script type="text/javascript">
		function dataValidate(url){
			$.ajax({
				url:url
				,type:'POST'
				,success:function(message){
					if(message == "success"){
		      			alert("保存成功");
		      			$("#introduce_dialog").dialog("close");
					}else{
						alert("至少需要一条一句话推荐!");
					}
				},error:function(){alert("保存失败");}
			});
		}
		function deleteValidate(url){
			if(confirm("您确定要删除此条数据吗?")){
				$.ajax({
					url:url
					,type:'POST'
					,success:function(message){
						reloadIntroduce();
		      			alert("删除成功");
					},error:function(){alert("删除失败");}
				});
			}
		}
		function reloadIntroduce(){
			$.ajaxDialog({url:"${basePath}/place/queryAllIntroduce.do?placeId=${placeHotelNotice.placeId}" + "&math=" + Math.random(),titel:"酒店介绍",id:"introduce_dialog"});
		}
</script>