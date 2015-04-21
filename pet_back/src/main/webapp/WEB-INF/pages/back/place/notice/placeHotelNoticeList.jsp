<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<table class="p_table table_center" style="width:950px;">
		<tr>
			<th width="60px;">类型</th>
			<th width="500px;">公告内容</th>
			<th width="150px;">开始时间</th>
			<th width="150px;">结束时间</th>
			<th width="90px;">操作</th>
		</tr>
		<s:iterator value="placeHotelNoticeList">
			<tr>
				<td>
					<s:if test="noticeType == 'INTERNAL'">对内</s:if>
					<s:if test="noticeType == 'ALL'">全部</s:if>
					<s:if test="noticeType == 'SCENIC'">公告</s:if>
				</td>	
				<td><s:property value="noticeContent"/></td>
				<td><s:date name="noticeStartTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td><s:date name="noticeEndTime" format="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<a href="#" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelNotice.do?placeHotelNotice.noticeId=<s:property value="noticeId"/>&successUrl=<s:property value="successUrl"/>&noticeType=<s:property value="noticeType"/>',title:'公告修改',id:'placeHotel_TwoId'});">修改</a>
					<a href="#" onclick="javasctipt:deleteValidate('${basePath}/place/deletePlaceHotelNotice.do?placeHotelNotice.noticeId=<s:property value="noticeId"/>&placeId=<s:property value="placeId"/>&successUrl=<s:property value="successUrl"/>&noticeType=<s:property value="noticeType"/>');">删除</a>&nbsp;&nbsp;
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="5" class="tc mt10 mb10">
				<input class="btn btn-small w5" type="button" value="新增" onclick="$.ajaxDialog({url:'${basePath}/place/initPlaceHotelNotice.do?placeHotelNotice.placeId=${placeHotelNotice.placeId}&successUrl=<s:property value="successUrl"/>&noticeType=<s:property value="noticeType"/>',title:'公告添加',id:'placeHotel_TwoId'});"/>
			</td>
		</tr>
	</table>
<script type="text/javascript">
		function deleteValidate(url){
			if(confirm("您确定要删除此条数据吗?")){
				$.ajax({
					url:url
					,type:'POST'
					,success:function(data){
						$("#placeHotel_OneId").dialog("close"); 
						$.ajaxDialog({url:'${basePath}${successUrl}?placeId=${placeHotelNotice.placeId}&noticeType=${noticeType}',title:'公告',id:'placeHotel_OneId'}); 
		      			alert("删除成功");
					},error:function(){alert("删除失败");}
				});
			}
		}
</script>