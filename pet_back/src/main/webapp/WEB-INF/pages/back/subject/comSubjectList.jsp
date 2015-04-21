<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>PC内容，主题列表管理</title>
	<s:include value="/WEB-INF/pages/pub/new_jquery.jsp"/>
	<script type="text/javascript" src="${basePath}/js/base/jquery.form.js"></script>
</head>
<body>
<div id="popDiv" style="display: none"></div>
<div class="iframe-content">
<div class="p_box">
<form method="post" action="${basePath}/pub/subject/subjectList.do" id="querySubjectForm">
	<table class="p_table no_bd form-inline" width="100%">
	 	<tr>
		    <td class="p_label">主题类型：</td>
		    <td><select name="comSubject.subjectType" id="comSubject.subjectType">
				<option value="" <s:if test="comSubject.subjectType==''">selected="selected"</s:if>>所有</option>
				<option value="ROUTE" <s:if test="comSubject.subjectType=='ROUTE'">selected="selected"</s:if> >线路</option>
				<option value="PLACE" <s:if test="comSubject.subjectType=='PLACE'">selected="selected"</s:if>>景点</option>
				<option value="HOTEL" <s:if test="comSubject.subjectType=='HOTEL'">selected="selected"</s:if>>酒店</option>
				</select></td>
		  <td class="p_label">主题名称：</td>
		  <td><input name="comSubject.subjectName" value="${comSubject.subjectName }">
		  </tr>
	    <tr><td  class="p_label">是否标红：</td>
	       <td> <select name="comSubject.ifBold" id="comSubject.ifBold">
				<option value="" <s:if test='comSubject.ifBold==""' >selected="selected"</s:if> >全部</option>
				<option value="N" <s:if test='comSubject.ifBold=="N"' >selected="selected"</s:if> >否</option>
				<option value="Y" <s:if test='comSubject.ifBold=="Y"'  >selected="selected"</s:if> >是</option>
				</select></td>
			<td  class="p_label">是否有效：</td>
			<td><select name="comSubject.isValid" id="comSubject.isValid">
				<option value="" <s:if test='comSubject.isValid==""' >selected="selected"</s:if>>全部</option>
				<option value="N" <s:if test='comSubject.isValid=="N"' >selected="selected"</s:if>>否</option>
				<option value="Y" <s:if test='comSubject.isValid=="Y"' >selected="selected"</s:if>>是</option>
				</select></td>
			</tr>
		<tr>		
		</table>
	 <p class="tc mt10 mb10">
		  <input class="btn btn-small w5" name="submit" value="查询" type="submit" id="form-submit-btn"/>
		  <input  class="btn btn-small w5" name="saveSeq" value="批量排序" type="button" id="saveSubjectSeq">
		  <input  class="btn btn-small w5" name="newComSubject" value="新增酒店主题" type="button" onClick="javascript:resize('','HOTEL');" id="newComSubject">
	 </p>			
</form>
</div>
<table class="p_table" cellspacing="0" cellpadding="0">
	  <tr>
		<th width="65px" style="text-align: left;"><input type="checkbox" name="chk_all" id="chk_all"/>全选</th>
	    <th width="75px">主题ID</th>
	    <th width="145px">主题名称</th>
	    <th width="65px">主题拼音</th>
	    <th width="50px">类型</th>
	    <th width="200px">引用(景点/酒店)</th>
	    <th width="75px">引用次数 </th>
	    <th width="50px">标红</th>
	    <th width="50px">状态</th>
	    <th width="75px">创建时间</th>
	    <th width="75px">更新时间</th>
	    <th width="55px">排序值</th>
	    <th width="50px">操作</th>
  	</tr>
  <s:iterator value="comSubjectList" var="comSubject">
	  <tr>
			<td><input class="checkbox" type="checkbox" name="chk_list"  value='<s:property value="comSubjectId" />'/></td>
			<td><s:property value="comSubjectId" /></td>
			<td><s:property value="subjectName" /></td>
			<td><s:property value="subjectPinyin" /></td>
			<td><s:property value="subjectTypeStr" /></td>
			<td>
				<s:if test="subjectType=='PLACE'">
					<a href="javascript:;" onclick="viewPlace('<s:property value="subjectName" />');return false;">查看景点</a>
				</s:if>
				<s:elseif test="subjectType=='HOTEL'">
					 <a href="javascript:;" onclick="viewHotel('<s:property value="subjectName" />');return false;">查看酒店</a>
				</s:elseif>
			</td>
			<td><s:property value="usedByCount" /></td>
			<td><s:property value="ifBoldStr" /></td>
			<td>
				<s:if test='isValid=="N"'><span style="color:red" >无效</span></s:if> 
				<s:elseif test='isValid=="Y"'><span style="color:green" >有效</span></s:elseif>
			</td>
			<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td><s:date name="updateTime" format="yyyy-MM-dd HH:mm:ss"/></td>
			<td><input type="text" class="seq" data-value="<s:property value="seq" />" id='chk_list_<s:property value="comSubjectId" />' value='<s:property value="seq" />'/></td>
			<td ><a href="javascript:resize('<s:property value="comSubjectId" />','<s:property value="subjectType" />');">修改</a></td> 
	  </tr>
  </s:iterator>
</table>
<p></p>
<p></p>
<form method="post" id="place-post-form" action="${basePath}/place/scenicList.do">
		<input type="hidden" name="place.firstTopic" />
</form>
<form method="post" id="hotel-post-form" action="${basePath}/place/hotelList.do">
		<input type="hidden" name="place.firstTopic" />
</form>
<div id="win" icon="icon-save" title="My Window"></div>
	<script type="text/javascript">
	
	function viewPlace(key){
		var form=$("#place-post-form");
		$(form).find("input[name='place.firstTopic']").val(key);
		$(form).submit();
	}
	
	function viewHotel(key){
		var form=$("#hotel-post-form");
		$(form).find("input[name='place.firstTopic']").val(key);
		$(form).submit();
	}
	
	function resize(placeId,subjectType){
		var url='${basePath}pub/subject/subjectEdie.do?comSubjectIdStr='+placeId+'&subjectTypeStr='+subjectType+'&math='+Math.random();
		showDetail(url,'主题管理');
 	};
	function showDetail(url,title) {
		 $("#popDiv").html("");
		 $("#popDiv").load(url,function() {
				$(this).dialog({
		         		modal:true,
		         		title:title,
		         		width:650,
		        		height:500
		             });
		         });		
		}
	$(function() {
		$("#chk_all").click(function(){
			$("input[name='chk_list']").attr("checked",$(this).attr("checked"));
		});

		var reg = new RegExp("^((-[0-9]+)|([0-9])*)$");
		$(".seq").blur(function(){
			var oldValue=$(this).attr("data-value");
			var ck=$(this).parents("tr").find("input[type='checkbox']");
			if(!reg.test(this.value)){
				alert("排序值请输入数字!");
				this.focus();
			}
			if(oldValue!=this.value){
				$(ck).attr("checked","checked");
			}else{
				(ck).attr("checked","");
			}
		});
		
		$('#saveSubjectSeq').click(function() {
			var arrChk=$("input[name='chk_list']:checked");
			var subjectSeqs="";
			if(arrChk.length==0){
				alert("请选择要排序的记录!");
				return;
			}
			if(confirm("您确认要修改排序吗?")){
				$(arrChk).each(function(){
					subjectSeqs+=this.value+"_"+$("#chk_list_"+this.value).val()+",";
				});
			    
				if(subjectSeqs!=""){
					subjectSeqs=subjectSeqs.substring(0,subjectSeqs.length-1);
				}
				$.ajax({
					type:"post",
			        url:"${basePath}pub/subject/updateSeq.do",
			        data:"subjectSeqs="+subjectSeqs,
			        dataType:"html",
			        error:function(){
			            alert("与服务器交互错误!请稍候再试!");
			        },
			        success:function(data){
			        	if(data=="success"){
			        		alert("修改成功!");
			        		$("#form-submit-btn").trigger("click");
			        	}else{
			        		alert("修改失败!");
			        	}
			        }
				});
			}
			
		});
	});
	</script>
</div>
</body>
</html>
