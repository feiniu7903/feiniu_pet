<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>敏感词列表</title>
    <s:include value="/WEB-INF/pages/pub/jquery.jsp"/>
    <script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
    <link rel="stylesheet" type="text/css" href="${basePath}css/ui-common.css"></link>
	<link rel="stylesheet" type="text/css" href="${basePath}css/ui-components.css"></link>
	<link rel="stylesheet" type="text/css" href="${basePath}css/panel-content.css"></link>
	<script type="text/javascript" src="${basePath}/js/base/log.js"></script>
    <style type="text/css">
    	.button {display:inline;}
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".editSensitiveWord").click(function () {
                var sensitiveId = $(this).attr("data");
                var data = {};
				if(typeof(sensitiveId) != 'undefined' && sensitiveId != "") {
					data = {"sensitiveId":sensitiveId};
				}
				var $div = $('#editSensitiveWordDiv');
				$div.load('/pet_back/sensitiveWord/showEditDialog.do', data, function() {
					$div.dialog( {
						title : "修改敏感词",
						width : 1000,
						modal : true
					});
				});
            });

            $("a.deleteSensitiveWord").click(function () {
			 	if (confirm("确定删除该敏感词吗？")) {
			 		var sensitiveId = $(this).attr("data");
					if(typeof(sensitiveId) == 'undefined' || sensitiveId == null || sensitiveId == "") {
						alert("操作异常！");
						return false;
					}
	                $.post(
	                        "/pet_back/sensitiveWord/deleteSensitiveWord.do",
	                        {
	                            "sensitiveId": sensitiveId
	                        },
	                        function (data) {
	                        	var dt = eval("(" + data +")");
	                            if (dt.success) {
	                                alert("操作成功");
	                                location.reload(window.location.href);
	                            } else {
	                                alert(dt.msg);
	                            }
	                        }
	                );
	            }
            });
            
            $("input.chk_all").change(function() {
            	var checked = $(this).attr("checked");
            	if(checked) {
            		$("input[type=checkbox][name=sensitiveIds]").attr("checked", true);
            	} else {
            		$("input[type=checkbox][name=sensitiveIds]").attr("checked", false);
            	}
            });
            
            $("input.deleteWords").click(function() {
            	var len = $("input[type=checkbox][name=sensitiveIds]:checked").length;
            	if(len < 1) {
            		alert("请选择需要删除的敏感词!");
            		return false;
            	}
            	var confirm = window.confirm("确定删除选中的敏感词吗?");
        		if (confirm) {
        			$.post(
	                        "/pet_back/sensitiveWord/deleteSensitiveWords.do",
	                        $("#result_form").serialize(),
	                        function (data) {
	                        	var dt = eval("(" + data +")");
	                            if (dt.success) {
	                                alert("操作成功");
	                                location.reload(window.location.href);
	                            } else {
	                                alert(dt.msg);
	                            }
	                        }
	                );
        		}
            });
            
            $(".importExcel").click(function() {
				var $div = $('#importExcelDiv');
				$div.load('/pet_back/sensitiveWord/showImportDialog.do', {}, function() {
					$div.dialog( {
						title : "导入excel",
						width : 1000,
						modal : true
					});
				});
            });
            
            $("input.exportAll").click(function() {
            	var confirm = window.confirm("确定导出所有数据吗?");
        		if (confirm) {
        			$("#exportForm").submit();
        		}
            });
        });
    </script>
</head>

<body>
<div class="iframe-content">
	<div class="p_box">
        <form id="query_form" action="/pet_back/sensitiveWord/toSensitiveWord.do" method="post">
            <table class="p_table form-inline" width="100%">
                <tr>
                    <td><em>敏感词名称：</em><s:textfield cssClass="newtext1" name="content"/>
                    </td>
                    <td width="60%">
                        <input type="submit" class="button" value=" 查 询 "/>&nbsp;&nbsp;
                        <input type="button" class="button editSensitiveWord" value=" 新 增 "/>&nbsp;&nbsp;
                        <input type="button" class="button importExcel" value="导入Excel文件"/>&nbsp;&nbsp;
                        <input type="button" class="button exportAll" value="全部导出" />
                        &nbsp;&nbsp;
                        <a href="javascript:void(0)" class="showLogDialog"
						param="{'parentId':'0','parentType':'SENSITIVE_WORD','objectType':'SENSITIVE_WORD'}">日志</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
	<form id="exportForm" action="/pet_back/sensitiveWord/exportAll.do" method="post"></form>
    <div class="p_box">
    	<form id="result_form">
			<table class="p_table table_center">
	            <tr>
	            	<th>&nbsp;</th>
	                <th>编号</th>
	                <th>敏感词</th>
	                <th>操作</th>
	            </tr>
	            <s:iterator value="pagination.items">
	                <tr>
	                	<td><input type="checkbox" name="sensitiveIds" value="${sensitiveId }" /></td>
	                    <td>${sensitiveId }</td>
	                    <td>${content}</td>
	                    <td>
	                        <a href="javascript:void(0);" class="editSensitiveWord" data="${sensitiveId}">修改</a>
	                        <a href="javascript:void(0);" class="deleteSensitiveWord" data="${sensitiveId }">删除</a>
	                    </td>
	                </tr>
	            </s:iterator>
	            <tr>
	            	<td colspan="4"><input type="checkbox" class="chk_all" />&nbsp;全选&nbsp;&nbsp;&nbsp;&nbsp;
	            	<input type="button" class="button deleteWords" value=" 删 除 " /></td>
	            </tr>
	            <tr>
	  				<td colspan="2" align="right">总条数：<s:property value="pagination.totalResultSize"/></td>
					<td colspan="2"><div style="text-align: right;"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagePost(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div></td>
	 			</tr>
	        </table>
        </form>
    </div>
    <div id="editSensitiveWordDiv"></div>
    <div id="importExcelDiv"></div>
</div>
</body>
</html>


