<%@ page language="java" pageEncoding="UTF-8" %>
<%
String basePath = request.getContextPath();
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>手动添加用户标签</title>
</head>
<s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
<script charset="utf-8" src="${basePath}/js/recon/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>/js/base/typeFirstType.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
<body>
    <div class="iframe-content">
        <div class="p_box">
            <table class="p_table form-inline" width="80%">
                <tr>
                    <td colspan="2">手动添加用户标签</td>
                </tr>
                 <tr class="p_label">
                    <td class="p_label">标签名称</td>
                    <td><input id="tagsName" type="text" name="tags.tagsName" onblur="searchPinyinByWord()"/></td>
                </tr>
                <tr>
                    <td class="p_label">标签拼音</td>
                    <td><select id="tagsPinYin" name="tags.tagsPinYin"></select></td>
                </tr>
                <tr>
                    <td class="p_label">一级类别:</td>
                    <td>
                        <s:select id="typeFirstType" list="userTagsFirstType" name="userTagsType.typeFirstType" listKey="elementValue" listValue="elementValue"></s:select>
                    </td>
                </tr>
                <tr>
                    <td class="p_label">二级类别：</td>
                    <td><select id="typeSecondType" name="userTagsType.typeSecondType"></select></td>
                </tr>
                <tr>
                   <td class="p_label">状态</td>
                    <td>
                    <input type="radio" name="tags.tagsStatus" value="1" checked="checked"/>可用 &nbsp;&nbsp;
                    <input type="radio" name="tags.tagsStatus" value="2"/>不可用
                    </td>
                </tr>
                
                <tr class="p_label">
                    <td colspan="2"><input type="submit" class="btn btn-small w5"
                        id="addAndCheck" value="添&nbsp;&nbsp;加" onclick="doSaveAndCheck()"/></td>
                </tr>
            </table>
        </div>
    </div>
</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
changeFirstTypeOnloadSecondType();

function searchPinyinByWord(){
	var tagsName = $("#tagsName").val();
	 $.post(basePath +"/tags/searchPinyinByTagsName.do",{
         "tags.tagsName":tagsName
     },function(data){
    	 $("#tagsPinYin").empty();
         $.each(data, function(i,item){
           var opt="<option value='"+item.pinyin+"'>"+item.pinyin+"</option>";
           $("#tagsPinYin").append(opt);
         });
         $("#tagsPinYin").show();
       },"json");
    
}


function doSaveAndCheck(){
	var tagsName = $("#tagsName").val();
	var tagsPinYin = $("#tagsPinYin").val();
	var tagsStatus = $("input[name=tags.tagsStatus]:checked").val();
	var firstType = $("#typeFirstType").val();
	var secondType = $("#typeSecondType").find("option:selected").text();
	
	if("" == tagsName.trim()){
		alert("请输入标签名称");
		return false;
	}
	
	if(tagsName.indexOf(" ")>0){
		alert("标签名称请不要输入空格");
		return false;
	}
	
	if(tagsName.length>20){
		alert("标签名称不可以大于20个字符，请重新输入");
		return false;
	}
	
	$.ajax({
        type : "POST",
        url : "${basePath}tags/isExistCheck.do",
        data : {
            "tags.tagsName" : tagsName,
            "tags.tagsPinYin" : tagsPinYin,
            "tags.tagsStatus" : tagsStatus, 
            "userTagsType.typeFirstType" : firstType,
            "userTagsType.typeSecondType": secondType
        },
        success : function(json) {
            if (json == "exist") {
				alert("此标签已经存在，请重新输入");
			}else if (json == "true") {
				alert("操作成功!");
				window.location.reload(true);
			}else {
				alert("操作失败!");
			}
		}
	});
}
</script>
</html>