<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>super后台——行程展示</title>
    <link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.jsonSuggest.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/remoteUrlLoad.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.showLoading.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/prod/date.js"></script>
    <link href="<%=request.getContextPath()%>/themes/base/showLoading.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css"/>
    <link href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript">
        $(function () {
            var $edit_multi_journey_div = null;
            var $edit_fee_detail_div = null;
            $(".editMultiJourney")
                    .click(
                    function () {
                        var productId = $("#productId").val();
                        if (productId == "") {
                            alert("数据异常！");
                            return fasle;
                        }
                        if ($edit_multi_journey_div == null) {
                            $edit_multi_journey_div = $("<div style='display:none' id='edit_multi_journey_div'>");
                            $edit_multi_journey_div.appendTo($("body"));
                        }
                        var tit = $(this).attr("title");
                        var data = {'productId': productId};
                        var d = $(this).attr("data");
                        if (typeof(d) != 'undefined') {
                            data ['viewMultiJourney.multiJourneyId'] = d;
                        }
                        var cy = $(this).attr("copy");
                        if (typeof(cy) != 'undefined') {
                            data ['copy'] = cy;
                        }
                        $edit_multi_journey_div.load(
                                "/super_back/view/toEditMultiJourney.do", data,
                                function () {
                                    $edit_multi_journey_div.dialog({
                                        title: tit + "行程列表",
                                        width: 800,
                                        modal: true
                                    });
                                });
                    });

            $(".editFeeDetail")
                    .click(
                    function () {
                        var productId = $("#productId").val();
                        if (productId == "") {
                            alert("数据异常！");
                            return fasle;
                        }
                        if ($edit_fee_detail_div == null) {
                            $edit_fee_detail_div = $("<div style='display:none' id='edit_fee_detail_div'>");
                            $edit_fee_detail_div.appendTo($("body"));
                        }
                        var data = {'productId': productId};
                        var d = $(this).attr("data");
                        if (typeof(d) != 'undefined') {
                            data ['multiJourneyId'] = d;
                        }
                        $edit_fee_detail_div.load(
                                "/super_back/view/toEditFeeDetail.do", data,
                                function () {
                                    $edit_fee_detail_div.dialog({
                                        title: "费用说明",
                                        width: 1000,
                                        modal: true
                                    });
                                });
                    });

            $(".changeValidStatus").click(function () {
                var productId = $("#productId").val();
                if (productId == "") {
                    alert("数据异常！");
                    return fasle;
                }
                var d = $(this).attr("data");
                if (typeof(d) == 'undefined' || d == "") {
                    alert("数据异常!");
                    return false;
                }
                var status = $(this).attr("status");
                if (status == 'Y') {
                    if (!confirm("设置为无效，将会删除对应的时间价格表，是否确认操作？")) {
                        return false;
                    }
                }
                $.ajax({
                    type: "POST",
                    url: "/super_back/view/changeValidStatus.do",
                    data: {'viewMultiJourney.productId': productId, 'viewMultiJourney.multiJourneyId': d},
                    async: false,
                    timeout: 3000,
                    success: function (dt) {
                        var data = eval("(" + dt + ")");
                        if (data.success) {
                            alert("操作成功");
                            $("#queryMultiJourneyBtn").click();
                        } else {
                            alert(data.msg);
                        }
                    }
                });
            });
        });

        function checkFormBeforeSubmit() {
            var re = /^[1-9]\d*$/;
            var days = $.trim($("input[name='viewMultiJourney.days']").val());
            if ($.trim(days) != "" && !re.test(days)) {
                alert("行程天数必须为大于0的正整数!");
                return false;
            }
            var nights = $.trim($("input[name='viewMultiJourney.nights']").val());
            if ($.trim(nights) != "" && !re.test(nights)) {
                alert("晚数必须为大于0的正整数!");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<div class="main main02">
    <div class="row1">
        <h3 class="newTit"> 销售产品信息 </h3>

        <div class="nav">
        </div>
    </div>
    <div class="row2">
        <dl class="trave">
            <dt>
                <strong class="add_margin_left">行程列表</strong>
            </dt>
            <dd>
                <table class="newTable" width="90%" border="0" cellspacing="0"
                       cellpadding="0" id="multiJourney">
                    <tr class="newTableTit">
                        <td style="width: 15%">行程名称</td>
                        <td style="width: 10%">行程天数</td>
                        <td style="width: 25%">内容描述</td>
                        <td style="width: 15%">录入时间</td>
                        <td style="width: 10%">是否有效</td>
                    </tr>
                    <s:iterator value="pagination.records" var="journey">
                        <tr>
                            <td>${journeyName }</td>
                            <td>${days }天${nights }晚</td>
                            <td><s:property value="@com.lvmama.comm.utils.StringUtil@subStringStr(content,20)"/></td>
                            <td><s:date name="createTime" format="yyyy-MM-dd HH:ss:mm"/></td>
                            <td>${zhValid }</td>
                        </tr>
                    </s:iterator>
                </table>
                <table width="90%" border="0" align="center">
                    <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
                </table>
            </dd>
        </dl>
        <!--travel end-->
    </div>
    <!--row5 end-->
</div>
<!--main01 main05 end-->
</body>
</html>

