<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>推广活动列表</title>
    <s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
    <script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
	<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
	<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
	<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
	<script type="text/javascript" src="${basePath}/js/base/date.js"></script>
    <style type="text/css">
    	.button {display:inline;}
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
        	$(".editActivity").click(function() {
        		var activityId = $(this).parents("tr").attr("data");
                var data = {};
				if(typeof(activityId) != 'undefined' && activityId != "") {
					data = {"activityId":activityId};
				}
				var $div = $('#editActivityDiv');
				$div.load('/pet_back/lvcc/showEditDialog.do', data, function() {
					$div.dialog( {
						title : "编辑推广活动",
						width : 500,
						modal : true
					});
				});
        	});
        	
        	$(".manageChannel").click(function() {
        		var $div = $("#manageChannelDiv");
        		$div.load('/pet_back/lvcc/showChannel.do', {},  function() {
					$div.dialog( {
						title : "渠道管理",
						width : 500,
						modal : true
					});
				});
        	});
        	
        	$(".changeValid").click(function() {
        		var activityId = $(this).parents("tr").attr("data");
        		var valid = $(this).attr("data");
        		if(valid == "N") {
        			if(!confirm("其他有效的活动将变为无效！确定将该活动改为\"有效\"吗？")) {
        				return false;
        			}
        		} else {
        			if(!confirm("确定将该活动变为\"无效\"吗？")) {
        				return false;
        			}
        		}
        		$.post("/pet_back/lvcc/changeValid.do", {'activityId': activityId, 'valid': valid}, function(data) {
					var dt = eval("(" + data + ")");
					if (dt.success) {
						alert("操作成功");
						window.location.reload();
					} else {
						alert(dt.msg);
					}
				});
        	});
        });
    </script>
</head>

<body>
<div class="iframe-content">
	<div class="p_box">
        <form id="query_form" action="/pet_back/lvcc/toActivityList.do" method="post">
            <table class="p_table form-inline" width="100%">
                <tr>
                    <td>
                    	<em>推广名称：</em>
                    	<s:textfield cssClass="newtext1" name="name"/>
                    </td>
                    <td>
                    	<em>创建时间：</em>
                    	<s:textfield cssClass="newtext1 date" readonly="true"  name="createBeginTime">
                    		<s:param name="value" ><s:date name="createBeginTime" format="yyyy-MM-dd" /></s:param>
                    	</s:textfield>-
                    	<s:textfield cssClass="newtext1 date" readonly="true"  name="createEndTime">
                    		<s:param name="value" ><s:date name="createEndTime" format="yyyy-MM-dd" /></s:param>
                    	</s:textfield>
                    </td>
                    <td>
                    	<em>状态：</em>
                    	<s:select name="valid" list="#{'':'全部','Y':'有效','N':'无效'}" />
                    </td>
                </tr>
                <tr>
                	<td></td>
                	<td colspan="2">
                        <input type="submit" class="button" value=" 查 询 "/>&nbsp;&nbsp;
                        <input type="button" class="button editActivity" value=" 新 增 " />&nbsp;&nbsp;
                        <a href="javascript:void(0);" class="manageChannel" >渠道管理</a>
                    </td>
                 </tr>
            </table>
        </form>
    </div>
    <div class="p_box">
			<table class="p_table table_center">
	            <tr>
	            	<th>ID</th>
	                <th>推广名称</th>
	                <th>开始日期</th>
	                <th>结束日期</th>
	                <th>状态</th>
	                <th>创建时间</th>
	                <th>操作</th>
	            </tr>
	            <s:iterator value="pagination.items">
	                <tr data="${activityId}" >
	                	<td>${activityId }</td>
	                    <td>${name }</td>
	                    <td><s:date name="beginDate" format="yyyy-MM-dd" /></td>
	                    <td><s:date name="endDate" format="yyyy-MM-dd" /></td>
	                    <td>${zhValid }</td>
	                    <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
	                    <td>
	                        <a href="javascript:void(0);" class="editActivity" >修改</a>|
	                        <a href="javascript:void(0);" class="changeValid"  data="${valid }">设为<s:if test='valid=="Y"'>无效</s:if><s:else>有效</s:else></a>
	                    </td>
	                </tr>
	            </s:iterator>
	            <tr>
	  				<td colspan="2" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
					<td colspan="5"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
	 			</tr>
	        </table>
    </div>
</div>
<div id="editActivityDiv"></div>
<div id="manageChannelDiv"></div>
</body>
</html>


