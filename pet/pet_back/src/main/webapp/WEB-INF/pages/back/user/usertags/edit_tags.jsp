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
   <s:if test="null != tags && null != userTagsType">
        <input type="hidden" name="tags.tagsId" value="${tags.tagsId }" />
            <table class="p_table form-inline" width="80%">
                <tr>
                    <td colspan="2">用户标签编辑</td>
                </tr>
                 <tr class="p_label">
                    <td class="p_label">标签名称:</td>
                    <td>${tags.tagsName }</td>
                </tr>
                <tr>
                    <td class="p_label">标签拼音:</td>
                    <td><input id="tagsPinYin" type="text" name="tags.tagsPinYin" value="${tags.tagsPinYin }"></td>
                </tr>
                <tr>
                    <td class="p_label">一级类别:</td>
                    <td>
                        <s:select id="firstType" list="userTagsFirstType" name="userTagsType.typeFirstType" listKey="elementValue" listValue="elementValue"></s:select>
                    </td>
                </tr>
                <tr>
                    <td class="p_label">二级类别:</td>
                    <td><select id="secondType" name="userTagsType.typeSecondType">
                    <option value="${userTagsType.typeSecondType }" selected="selected">${userTagsType.typeSecondType }</option>
                    </select></td>
                </tr>
                <tr>
                   <td class="p_label">状态:</td>
                    <td>
                    <s:if test="1==tags.tagsStatus">
                        <input type="radio" name="tags.status" value="1" checked="checked"/>可用 &nbsp;&nbsp;
                        <input type="radio" name="tags.status" value="2" />不可用
                    </s:if>
                    <s:elseif test="2==tags.tagsStatus">
	                    <input type="radio" name="tags.status" value="1" />可用 &nbsp;&nbsp;
	                    <input type="radio" name="tags.status" value="2" checked="checked"/>不可用
	                </s:elseif>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                    <input type="button" class="btn btn-small w5" id="updateUserTags" value="确&nbsp;&nbsp;定" onclick="doUpdateByTags()"/>&nbsp;&nbsp;
                    <input type="button" value="取&nbsp;&nbsp;消" class="btn btn-small w5" onclick="$('#popDiv').dialog('close')"></td>
                </tr>
            </table>
    </s:if>
        </div>
    </div>
</body>
<script type="text/javascript">
changeFirstOnloadSecondDoUpdate();

function changeFirstOnloadSecondDoUpdate(){
	$("#firstType").click( function() {
        var firstType=$(this).val();
        $.post( basePath +"/tagsType/changeFirstTypeOnloadSecondType.do",{
            "userTagsType.typeFirstType":firstType
        },function(data){
          $("#secondType").empty();
          $.each(data, function(i,item){
            var opt="<option value='"+item.second+"'>"+item.second+"</option>";
            $("#secondType").append(opt);
          });
          $("#secondType").show();
        },"json");
    });
}

function doUpdateByTags(){
	var tagsId = $("input[name=tags.tagsId]").val();
    var tagsPinYin = $("#tagsPinYin").val();
    var tagsStatus = $("input[name=tags.status]:checked").val();
    var firstType = $("#firstType").val();
    var secondType = $("#secondType").find("option:selected").text();
    
    if(""==tagsPinYin.trim()){
    	alert("拼音不能为空!");
    	return false;
    }
    
    if(tagsPinYin.indexOf(" ")>0){
    	alert("拼音请不要输入空格");
    	return false;
    }
    
    $.ajax({
        type : "POST",
        url : "${basePath}tags/update.do",
        data : {
        	"tags.tagsId" : tagsId,
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