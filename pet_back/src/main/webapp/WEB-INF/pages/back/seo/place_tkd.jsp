<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="l" uri="/tld/lvmama-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
		+ request.getServerName() + ":" + request.getServerPort()
		+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>tkd管理</title>
<s:include value="/WEB-INF/pages/pub/jquery.jsp" />
<s:include value="/WEB-INF/pages/pub/suggest.jsp" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/panel-content.css"></link>
<script type="text/javascript" src="<%=basePath %>/js/place/houtai.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/place/jquery.autocomplate.js"></script>
<script type="text/javascript">
$(function () {
    $("#auto_place").autocomplete("<%=basePath %>/place/autocomplate.do", {dataType: "xml", width: "auto"});
});
</script>
    <script type="text/javascript">
	    var basePath='<%=basePath %>';
    	function setReadio(hiddenId,value){
    		document.getElementById(hiddenId).value=value;
    	}
    	
    	function editPlaceTkd(id){
    		if(id!=0){
	    		$.ajax({type:"POST", url:basePath+"/seo/placeTkd!placeDetail.do", data:{'place.placeId':id}, dataType:"json", success:function (json) {
	    			var place=json[0];
			 			$("#ipt_placeId").val(place.placeId);
						$("#ipt_placeName").html("<a href='http://www.lvmama.com/dest/"+place.pinYinUrl+"' target='_blank'>"+place.name+"</a>");
						$("#ipt_seoTitle").val(place.seoTitle);
						$("#ipt_seoKeyword").val(place.seoKeyword);
						$("#ipt_seoDescription").val(place.seoDescription);
						$("#ipt_seoContent").val(place.seoContent);
						$("#ipt_description").val(place.description);
				},error: function(){
				    alert("加载失败");
				}
	    		});
			}else{
					$("#ipt_seoTitle").val("");
			 		$("#ipt_seoKeyword").val("");
			 		$("#ipt_seoDescription").val("");
			 		$("#ipt_seoContent").val("");
			}
    		
    		$("#updatePlaceTkd").dialog({
        		modal:true,
        		title:"编辑",
        		width:750,
        		height:600
            });
    	}
    	
    	function updatePlaceTkd(){
    		var placeId=$("#ipt_placeId").val();
			var seoTitle=$("#ipt_seoTitle").val();
			var seoKeyword=$("#ipt_seoKeyword").val();
			var seoDescription=$("#ipt_seoDescription").val();
			var seoContent=$("#ipt_seoContent").val();
			if(seoContent!="" && seoContent.length>4000){
				alert("seoContent过长，请合理设置！");
				return;
			}
	    	if(placeId!=0&&placeId!=""){
	    		if(seoTitle==""&&(seoKeyword!=""||seoDescription!="")){
	    			alert("如果设置了seoKeyword或者seoDescription,则必须设置seoTitle");
	    			return;
	    		}
	    		
		    	var param = {'place.placeId':placeId,'place.seoTitle':seoTitle,'place.seoKeyword':seoKeyword,'place.seoDescription':seoDescription,'place.seoContent':seoContent}
		    		$.ajax({type:"POST", url:basePath+"/seo/placeTkd!updateSeoTkd.do", data:param, dataType:"json", success:function (json) {
			    			if(json.flag){
								alert("修改成功");
								window.location.reload(true);
							}else{	
								alert("修改失败");
							}
						},error: function(){
						    alert("提交失败");
						}
		    		});
			} else{
				alert("请选择修改信息");
			}
    	}
    </script>
  </head>
  
<body>
<div class="iframe-content">
  <div class="p_box">
    <form action="<%=basePath %>/seo/placeTkd.do?place.stage=<s:property value="place.stage"/>" method="post">
		<table class="p_table form-inline" width="100%">
		  <tbody>
			<tr>
				<td class="p_label"><s:if test='place.stage==2'>景区 </s:if><s:elseif test='place.stage==3'>酒店 </s:elseif><s:elseif test='place.stage==1'><input type="hidden" id="auto_place" name="autoPlaceName"  value="">目的地</s:elseif>名称:</td> 
				<td><input type="text" name="place.name" value="${place.name }" style="line-height: 30px; height: 30px;"></td> 
				<s:if test='place.stage=="2"||place.stage=="3"'>
                   <td class="p_label">直属目的地:<input type="text" id="auto_place" name="autoPlaceName"  style="height:30px;" value="<s:property value="autoPlaceName"/>">
                   <input type="hidden" name="place.parentPlaceId" value="<s:property value="place.parentPlaceId"/>"/>
                   </td>
                   </s:if>
				<td class="p_label">是否个性填写:</td>
				<td>
					<input type="radio" onclick="setReadio('seoIsEdit','')" name="isEdit" value="" <s:if test='seoIsEdit==""'>checked="checked"</s:if>>不限
					<input type="radio" name="isEdit" onclick="setReadio('seoIsEdit','Y')" <s:if test='seoIsEdit=="Y"'>checked="checked"</s:if> value="Y">已填
					<input type="radio" name="isEdit" onclick="setReadio('seoIsEdit','N')" <s:if test='seoIsEdit=="N"'>checked="checked"</s:if> value="N">未填
					<input type="hidden" id="seoIsEdit" name="seoIsEdit" value="${seoIsEdit }">
				</td>
			</tr>
			</tbody>
		</table>
		<p class="tc mt20"><button type="submit" class="btn btn-small w5">查询</button></p>
	</form>
  </div>
  
  <div class="p_box">
    <table width="100%" border="0" cellpadding="4" cellspacing="1" bgcolor="#464646" class="p_table table_center">
      <tbody>
              <tr>
				<s:if test='place.stage==2 ||place.stage==3'>
					<th bgcolor="#EEEEEE">直属目的地</th>
				</s:if>
				<th width="13%" height="20" align="center" bgcolor="#EEEEEE"><s:if test='place.stage==1'>目的地</s:if><s:if test='place.stage==2'>景区</s:if><s:if test='place.stage==3'>酒店</s:if>名称</th>
                <th width="14%" align="center" bgcolor="#EEEEEE">title</th>
                <th width="20%" align="center" bgcolor="#EEEEEE">keywords</th>
                <th width="20%" align="center" bgcolor="#EEEEEE">discription</th>
                <s:if test='place.stage==1'>
                <th width="5%" align="center" bgcolor="#EEEEEE">包含景点</th>
                <th width="5%" align="center" bgcolor="#EEEEEE">包含酒店</th>
                </s:if>
                <th width="20%" align="center" bgcolor="#EEEEEE">操作</th>
              </tr>
              <s:iterator value="pagination.items" var="pVo" status="status">
              <tr>
                <s:if test='place.stage==2 ||place.stage==3'>
                <td bgcolor="#FFFFFF"><a href="<%=basePath %>/seo/placeTkd.do?place.stage=<s:property value="#pVo.parentPlace.stage"/>&place.placeId=<s:property value="#pVo.parentPlace.placeId"/>"><s:property value="#pVo.parentPlace.name"/></a> </td>
                </s:if>
                <td bgcolor="#FFFFFF"><a href="http://www.lvmama.com/dest/<s:property value="#pVo.place.pinYinUrl"/>" target="_blank"><s:property value="#pVo.place.name"/></a> </td>
                <td bgcolor="#FFFFFF"><s:property value="#pVo.place.seoTitle"/> </td>
                <td bgcolor="#FFFFFF"><s:property value="#pVo.place.seoKeyword"/></td>
                <td bgcolor="#FFFFFF"><s:property value="#pVo.place.seoDescription"/></td>
                <s:if test='place.stage==1'>
                <td bgcolor="#FFFFFF" nowrap="nowrap"><a href="<%=basePath %>/seo/placeTkd.do?place.stage=2&place.parentPlaceId=<s:property value="#pVo.place.placeId"/>" >包含景点</a></td>
                <td bgcolor="#FFFFFF" nowrap="nowrap"><a href="<%=basePath %>/seo/placeTkd.do?place.stage=3&place.parentPlaceId=<s:property value="#pVo.place.placeId"/>" >包含酒店</a></td>
                </s:if>
                <td bgcolor="#FFFFFF" nowrap="nowrap">
                	<a href="javascript:void(0)" onclick="editPlaceTkd('<s:property value="#pVo.place.placeId"/>');">编辑</a>
                </td>
              </tr>
              </s:iterator>
              <tr style="background:white">
              <td colspan="2" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
		      <td align="right" colspan="5"> <s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination)"/></td>
				</tr>
				</tbody>
            </table>
 </div>
			

<div style="display: none" id="updatePlaceTkd">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" class="p_table form-inline">
		<tr>
			<td  class="p_label" width="15%">
				<s:if test='place.stage==2'>景区 </s:if><s:elseif test='place.stage==3'>酒店 </s:elseif><s:elseif test='place.stage==1'>目的地</s:elseif>名称:
			</td>
			<td bgcolor="#FFFFFF" >
				<span id="ipt_placeName"></span>
			</td>
		</tr>
		<tr>
		    <td class="p_label" width="15%">Title<br/>(页面标题):</td>
			<td>
				<textarea id="ipt_seoTitle" name="place.seoTitle" class="p-textarea"></textarea>
			</td>
		</tr>	
		<tr>
			<td class="p_label" width="15%">Keywords<br/>(页面标题):</td>
			<td >
				<textarea id="ipt_seoKeyword" name="place.seoKeyword" class="p-textarea"></textarea>
			</td>
		</tr>	
		<tr>
			<td class="p_label" width="15%">Description<br/>(页面标题):</td>
			<td >
				<textarea id="ipt_seoDescription" name="place.seoDescription" class="p-textarea"></textarea>
			</td>
		</tr>
		<tr>
			<td class="p_label" width="15%">seoContent<br/>(友情链接):</td>
			<td >
				<input type="hidden" id="ipt_placeId" name="place.placeId" value=""/>
				<textarea rows="8" id="ipt_seoContent" name="place.seoContent" class="p-textarea"></textarea>
			</td>
		</tr>
	</table>
	<p class="tc mt10">
		<input type="button" onclick="updatePlaceTkd()" value="提交" class="btn btn-small w3">
	</p>
    </div>
</div>
  </body>
</html>
