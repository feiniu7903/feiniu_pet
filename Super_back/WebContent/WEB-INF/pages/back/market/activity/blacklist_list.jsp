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
    <title>黑名单列表</title>
    <s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
    <script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>
    <style type="text/css">
    	.button {display:inline;}
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".editBlacklist").click(function () {
                var blackId = $(this).attr("data");
                var data = {};
				if(typeof(blackId) != 'undefined' && blackId != "") {
					data = {"blackId":blackId};
				}
				var $div = $('#editBlacklistDiv');
				$div.load('/super_back/mark_activity/showEditDialog.do', data, function() {
					$div.dialog( {
						title : "黑名单",
						width : 1000,
						modal : true
					});
				});
            });

            $("a.deleteBlacklist").click(function () {
			 	if (confirm("确定删除黑名单吗？")) {
			 		var blackId = $(this).attr("data");
					if(typeof(blackId) == 'undefined' || blackId == null || blackId == "") {
						alert("操作异常！");
						return false;
					}
	                $.post(
	                        "/super_back/mark_activity/deleteMarkActivityBlacklist.do",
	                        {
	                            "blackId": blackId
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
            
            $(".importCsv").click(function() {
				var $div = $('#importCsvDiv');
				$div.load('/super_back/mark_activity/showImportDialog.do', {}, function() {
					$div.dialog( {
						title : "导入csv",
						width : 1000,
						modal : true
					});
				});
            });
        });
    </script>
</head>

<body>
<div class="main main02">
    <div class="row1">
        <form id="query_form" action="/super_back/mark_activity/toMarkActivityBacklist.do" method="post">
            <table border="0" cellspacing="0" cellpadding="0" class="newTable">
                <tr>
                    <td><em>关键字：</em><s:textfield cssClass="newtext1" name="keyword"/>
                     <s:select list="#{'':'全部','MOBILE_NUMBER':'手机号','EMAIL':'邮箱'}" name="keywordType"></s:select>
                    </td>
                    <td width="60%">
                        <input type="submit" class="button" value=" 查 询 "/>&nbsp;&nbsp;
                        <input type="button" class="button editBlacklist" value=" 添加黑名单 "/>&nbsp;&nbsp;
                        <input type="button" class="button importCsv" value="批量导入CSV文件"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <div class="row2">
        <table border="0" cellspacing="0" cellpadding="0" class="newTable" style="text-align: center;">
            <tr class="newTableTit">
                <th>编号</th>
                <th>手机号</th>
                <th>邮箱</th>
                <th>操作</th>
            </tr>
            <s:iterator value="pagination.records">
                <tr>
                    <td>${blackId }</td>
                    <td>${mobileNumber}&nbsp;</td>
                    <td>${email}&nbsp;</td>
                    <td>
                        <a href="javascript:void(0);" class="editBlacklist" data="${blackId}">修改</a>
                        <a href="javascript:void(0);" class="deleteBlacklist" data="${blackId }">删除</a>
                    </td>
                </tr>
            </s:iterator>
        </table>
    </div>
    <table width="90%" border="0" align="center">
        <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
    </table>
    <div id="editBlacklistDiv"></div>
    <div id="importCsvDiv"></div>
</div>
</body>
</html>


