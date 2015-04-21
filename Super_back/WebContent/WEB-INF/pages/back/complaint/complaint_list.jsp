<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    <title>投诉列表</title>

    <link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css"/>
    <link rel="stylesheet" href="<%=basePath%>js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css"/>

    <script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=basePath%>js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>
    <s:include value="/WEB-INF/pages/back/base/jquery.jsp"/>
    <script type="text/javascript" src="<%=basePath%>js/ord/ord.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/dialog.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/prod/condition.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/jquery.form.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/phoneorder/important_tips.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/complaint/complaint.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/lvmama_common.js"></script>
    <style type="text/css">
        .ui-widget-content table{
            border: 0;
            background-color: #cccccc;
            width: 100%;
            font-size: 12;
            text-align: center;
            margin-top: 10px;
            cellspacing:1;
            cellpadding:1;
        }
        .ui-widget-content table tbody {
            background-color: #ffffff;
            font-family: Arial;
            font-size: 13px;
        }

        .ui-widget-content table tbody tr td {
            text-align: center;
            color: #333333;
            font-family: Arial;
            font-size: 13px;
            padding-left: 10px;
            padding-right: 10px;
            width: auto;
        }
        .ui-widget-content form table{
            border: 0;
            background-color: #ffffff;
            width: 400px;
            font-size: 12;
            text-align: center;
            margin-top: 10px;
        }
        .ui-widget-content form table tbody tr td {
            text-align: center;
            color: #333333;
            font-family: Arial;
            font-size: 13px;
            width: auto;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $("a.complaintView").click(function () {
                var complaintId = $(this).attr("data");

                window.location.href = "<%=basePath%>order/complaint/toComplaintEdit.do?ncComplaint.complaintId="+ complaintId+"&view=true";

/*
                $("#complaintEdit").showWindow({
                            url: "<%=basePath%>order/complaint/toComplaintEdit.do",
                            data: {
                                "ncComplaint.complaintId": complaintId,
                                "view":true
                            }
                        }
                );
*/
            });
            $("a.complaintEdit").click(function () {
                var complaintId = $(this).attr("data");
                /* var edit = $(this).attr("edit");

                if(edit=="true"){ */
                    window.location.href = "<%=basePath%>order/complaint/toComplaintEdit.do?ncComplaint.complaintId="+ complaintId+"&view=false";
                /* }else{
                    alert("没有权限");
                } */


            });

            $("#transferButton").click(function () {

                var compIds = "";
                $("input[name='compIds']:checked").each(function () {
                    if (true == $(this).attr("checked")) {
                        compIds += $(this).attr('value') + ",";
                    }
                });
                if(compIds.length>0) {
                    compIds = compIds.substr(0,compIds.length-1);
                }

                if(compIds.length==0){
                    alert("请选择投诉单");
                    return;
                }

                $('#select_staff_div').showWindow({
                            title: '转移客服',
                            width: 450,
                            height: 250,
                            data: {
                                "selectUserType":"TRANSFER"
                            }
                        }
                );

            });
            $("#checkAll").click(
    			function(){
    				if(this.checked){
    					$("input[name='compIds']").each(function(){this.checked=true;});
    				}else{
    					$("input[name='compIds']").each(function(){this.checked=false;});
    				}
    			}
    		);
        });

        function transferComplaint(userNames){

            var names = "";

            $.each(userNames, function (i, val) {
                names += val + ",";
            });

            if(names.length>0){
                names = names.substr(0,names.length-1);
            }

            if(names.length==0){
                alert("请选择用户");
            }

            var compIds = "";
            $("input[name='compIds']:checked").each(function () {
                if (true == $(this).attr("checked")) {
                    compIds += $(this).attr('value') + ",";
                }
            });
            if(compIds.length>0) {
                compIds = compIds.substr(0,compIds.length-1);
            }

            $.post(
                    "<%=basePath%>order/complaint/transferComplaint.do",
                    {
                        "complaintIds": compIds,
                        "transferComplaintUserNames":names
                    },
                    function (data) {
                        if (data.jsonMap.status == "success") {
                            alert("转移客服成功");
                            location.reload();
                        } else {
                            alert(data.jsonMap.status);
                        }
                    }
            );
        }
        function closeStaffDialog() {
            $("#select_staff_div").dialog("close");
        }
    </script>
</head>

<body>
<div class="main main02">
<div class="row1">
<form id="query_form" name="query_form" action="<%=basePath%>order/complaint/queryComplaintList.do" method="post">
<table border="0" cellspacing="0" cellpadding="0" class="newInput"
       width="100%">
<tr>
    <td><em>投诉日期：</em></td>
    <td colspan="3">
        <input id="complaintDateStart" name="complaintDateStart" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="complaintDateStart"/>"/>
        ~
        <input id="complaintDateEnd" name="complaintDateEnd" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="complaintDateEnd"/>"/>
    </td>
    <td><em>处理日期：</em></td>
    <td colspan="3">
        <input id="startProcessTimeStart" name="startProcessTimeStart" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="startProcessTimeStart"/>"/>
        ~
        <input id="startProcessTimeEnd" name="startProcessTimeEnd" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="startProcessTimeEnd"/>"/>
    </td>
</tr>
<tr>
    <td><em>结案日期：</em></td>
    <td colspan="3">
        <input id="closeTimeStart" name="closeTimeStart" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="closeTimeStart"/>"/>
        ~
        <input id="closeTimeEnd" name="closeTimeEnd" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="closeTimeEnd"/>"/>
    </td>
    <td><em>完成日期：</em></td>
    <td colspan="3">
        <input id="completionTimeStart" name="completionTimeStart" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="completionTimeStart"/>"/>
        ~
        <input id="completionTimeEnd" name="completionTimeEnd" type="text"
               onclick="WdatePicker({isShowClear:true,readOnly:true})"
               class="input_text01 Wdate"
               value="<s:property value="completionTimeEnd"/>"/>
    </td>
</tr>
<tr>
    <td><em>投诉编号：</em></td>
    <td>
        <input type="text" class="newtext1" name="complaintId"
               value="<s:property value="complaintId"/>"/>
    </td>
    <td><em>订单号：</em></td>
    <td>
        <input type="text" class="newtext1" name="orderId"
               value="<s:property value="orderId"/>"/>
    </td>
    <td><em>投诉处理人：</em></td>
    <td>
        <input type="text" name="currentProcessPeople" class="newtext1"
               value="<s:property value="currentProcessPeople"/>"/>
    </td>
    <td><em>录入人：</em></td>
    <td>
        <input type="text" class="newtext1" name="entryPeople"
               value="<s:property value="entryPeople"/>"/>
    </td>
</tr>
<tr>
    <td>
        <em>投诉会员名：</em>
    </td>
    <td>
        <input type="text" class="newtext1" name="complaintName"
               value="<s:property value="complaintName"/>"/>
    </td>
    <td><em>投诉联系人：</em></td>
    <td>
        <input type="text" name="contact" class="newtext1"
               value="<s:property value="contact"/>"/>
    </td>
    <td><em>联系电话：</em></td>
    <td>
        <input type="text" class="newtext1" name="contactMobile"
               value="<s:property value="contactMobile"/>"/>
    </td>
    <td><em>产品编号：</em></td>
    <td>
        <input type="text" name="productId" class="newtext1"
               value="<s:property value="productId"/>"/>
    </td>
</tr>
<tr>
    <td><em>产品名称：</em></td>
    <td>
        <input type="text" name="productName" class="newtext1"
               value="<s:property value="productName"/>"/>
    </td>
    <td><em>产品类型：</em></td>
    <td>
        <s:select list="@com.lvmama.comm.vo.Constant$ORDER_TYPE@values()"
                  listKey="code" listValue="cnName" headerKey=""
                  headerValue="请选择" name="productType" value="productType"/>
    </td>
    <td><em>投诉来源：</em></td>
    <td>
        <select name="source">
            <option value="">请选择</option>
            <option value="VISITORS_CALL" <s:if test="ncComplaint.source=='VISITORS_CALL'">selected</s:if>>游客来电</option>
            <option>--------</option>
            <option value="GOVERNMENT" <s:if test="ncComplaint.source=='GOVERNMENT'">selected</s:if>>政府</option>
            <option value="MEDIA" <s:if test="ncComplaint.source=='MEDIA'">selected</s:if>>媒体</option>
            <option value="MICROBLOGGING" <s:if test="ncComplaint.source=='MICROBLOGGING'">selected</s:if>>微博</option>
            <option>--------</option>
            <option value="INTERNAL_FEEDBACK" <s:if test="ncComplaint.source=='INTERNAL_FEEDBACK'">selected</s:if>>内部反馈</option>
            <option value="SUPPLIER" <s:if test="ncComplaint.source=='SUPPLIER'">selected</s:if>>供应商</option>
            <option>--------</option>
            <option value="TOURISM_AUTHORITY" <s:if test="ncComplaint.source=='TOURISM_AUTHORITY'">selected</s:if>>旅监</option>
            <option value="CONSUMERS_ASSOCIATION" <s:if test="ncComplaint.source=='CONSUMERS_ASSOCIATION'">selected</s:if>>消协</option>
            <option value="TRADE_AND_INDUSTRY_BUREAU" <s:if test="ncComplaint.source=='TRADE_AND_INDUSTRY_BUREAU'">selected</s:if>>工商</option>
            <option value="LAW_ENFORCEMENT" <s:if test="ncComplaint.source=='LAW_ENFORCEMENT'">selected</s:if>>执法大队</option>
            <option>--------</option>
            <option value="OTHER" <s:if test="ncComplaint.source=='OTHER'">selected</s:if>>其他</option>
        </select>
    </td>
    <td><em>投诉类型：</em></td>
    <td>
        <s:select list="ncComplaintTypeList" listKey="typeId"
                  listValue="typeName" headerKey="" headerValue="请选择"
                  name="complaintType" value="complaintType"/>
    </td>
</tr>
<tr>
    <td><em>是否升级：</em></td>
    <td>
        <s:select list="#{'':'全部','YES':'是','NO':'否','SUSPECT':'疑似升级'}" name="upgrade"/>
    </td>
    <td><em>是否紧急：</em></td>
    <td>
        <s:select list="#{'':'全部','YES':'是','NO':'否'}" name="urgent"/>
    </td>
    <td><em>产品所属中心：</em></td>
    <td>
        <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_BELONGS_CENTER@values()"
                  listKey="code" listValue="cnName"
                  headerKey="" headerValue="请选择"
                  name="belongsCenter" value="belongsCenter"/>
    </td>
    <td><em>处理结果：</em></td>
    <td>
        <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_CASH_COMPLAINT@values()"
                  listKey="code" listValue="cnName" headerKey=""
                  headerValue="请选择" name="treatmentType" value="treatmentType"/>
    </td>
</tr>
<tr>
    <td><em>回复时效：</em></td>
    <td>
        <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_REPLY_AGING@values()"
                  listKey="code" listValue="cnName" headerKey=""
                  headerValue="请选择" name="replyAging" value="replyAging"/>
    </td>
    
     <td><em>所属系统：</em></td>
    <td >

        <s:select id="sysCodeSelect" list="@com.lvmama.comm.vo.Constant$COMPLAINT_SYS_CODE@values()"
                  listKey="code" listValue="cnName" headerKey=""
                  headerValue="请选择" name="sysCode" value="sysCode"/>

    </td>
    
    <td><em>处理状态：</em></td>
    <td colspan="3" id="statusTd">
    	<s:checkbox name="view" onclick="$('#statusTd').find('input[type=checkbox]').attr('checked', this.checked)"></s:checkbox>
		全部
        <s:checkboxlist list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_PROCESSING_STATUS@values()"
                  listKey="code" listValue="cnName"
                  name="processStatus" value="processStatus"/>

    </td>
   
</tr>
<tr>
    <td colspan="5"></td>
    <td align="center">
        <input type="submit" class="button" id="searchBtn" value="查询"/>
    </td>
    <td align="center">
        <input type="button" class="button" id="addBtn" onclick="addComplaint();" value="新增"/>
    </td>
    <%--生产--%>
    <mis:checkPerm permCode="3406" permParentCode="${permId}">
    <%--测试--%>
    <%--<mis:checkPerm permCode="3433" permParentCode="${permId}">--%>
        <td align="center">
            <input type="button" class="button" id="transferButton" value="强制转移客服"/>
        </td>
    </mis:checkPerm>
</tr>
</table>
</form>
</div>
<div class="row2" style="text-align: center;">
    <table border="0" cellspacing="0" cellpadding="0" class="newTable">
        <tr class="newTableTit">
        	<td><input type="checkbox" name="checkAll" id="checkAll" value="1" /></td>
            <td>投诉编号</td>
            <td>投诉联系人</td>
            <td>投诉日期</td>
            <td>产品编号</td>
            <td>产品名称</td>
            <td>订单号</td>
            <td>回复时效</td>
            <td>当前处理人</td>
            <td>是否紧急</td>
            <td>处理结果</td>
            <td>处理状态</td>
            <td>提醒</td>
            <td>所属系统</td>
            <td>操作</td>
        </tr>
        <s:iterator value="pagination.records" var="complaint">
            <tr>
        		<td>
        			<input type="checkbox" name="compIds" value="<s:property value="complaintId"/>"/>
        		</td>
                <td>
                    <a href="javascript:void(0)" data="<s:property value="complaintId"/>" class="complaintView">
                        <s:property value="complaintId"/>
                    </a>
                </td>
                <td>
                    <s:property value="contact"/>
                </td>
                <td>
                    <s:property value="zhComplaintDate"/>
                </td>
                <td>
                	<s:if test="sysCode=='VST'">
                		<a href="javascript:openWin('http://hotels.lvmama.com/hotel/${product.productId }')">
	                        <s:property value="product.productId"/>
	                    </a>
                	</s:if>
                	<s:else>
	                    <a class="showImportantTips" href="javascript:void(0)"
	                       productId="<s:property value="product.productId"/>">
	                        <s:property value="product.productId"/>
	                    </a>
                    </s:else>
                </td>
                <td>
                    <s:property value="product.productName"/>
                </td>
                <td>
                	<s:if test="sysCode=='VST'">
                    	<a href="javascript:openWin('/vst_back/order/orderStatusManage/showOrderStatusManage.do?orderId=${orderId }')">${orderId}</a>
                	</s:if>
                 	<s:else>
                    	<a href="javascript:openWin('/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId=${orderId }',700,700)">${orderId}</a>
                    </s:else>
                </td>
                <td>
                    <s:property value="strReplyAging"/>
                </td>
                <td>
                    <s:property value="currentProcessPeople"/>
                </td>
                <td>
                    <s:if test="urgent=='YES'">
                        <span style="color:red;">${strUrgent }</span>
                    </s:if>
                    <s:else>
                        ${strUrgent }
                    </s:else>
                </td>
                <td>
                    <s:property value="complaintResult.strTreatmentType"/>
                </td>
                <td>
                    <s:property value="strProcessStatus"/>
                </td>
                <td>
                	<s:if test="processStatus!='COMPLETE' && processStatus!='CLOSE'">
                		<s:if test="complaintRemind.processInfo!=null">
	                        <img src="<%=request.getContextPath()%>/img/icon/remind.png" style="cursor: pointer;" title="${complaintRemind.zhRemindTime }&nbsp;${complaintRemind.processInfo }" alt=""/>
	                    </s:if>
                    </s:if>
                </td>
                <td>
                	${sysCodeCnName }
                </td>
                <td>
                    <%--生产--%>
                    <mis:checkPerm permCode="3407" permParentCode="${permId}">
                        <%--测试--%>
                    <%--<mis:checkPerm permCode="3434" permParentCode="${permId}">--%>
                    <a href="javascript:void(0)" data="<s:property value="complaintId"/>" <%-- edit="<s:property value="permUser.userName==currentProcessPeople"/>" --%> class="complaintEdit">处理</a>
                    </mis:checkPerm>
                    <a href="javascript:void(0)" class="showLogDialog" param="{'objectId':'${complaintId }','objectType':'NC_COMPLAINT'}">日志</a>
                </td>
            </tr>
        </s:iterator>
    </table>
</div>
<table width="90%" border="0" align="center">
    <s:include value="/WEB-INF/pages/back/base/pag.jsp"/>
</table>
</div>
<div class="orderpop" id="historyDiv" style="display: none;"
     href="/super_back/ord/showOrderDetailById.do">
</div>
<div id="bg" class="bg" style="display: none;">
    <iframe
            style="position: absolute; width: 100%; height: 100%; _filter: alpha(opacity = 0); opacity =0; border-style: none; z-index: -1">
    </iframe>
</div>
<div id="complaintEdit" url="<%=basePath%>order/complaint/toComplaintEdit.do"></div>
<div id="select_staff_div" url="<%=basePath%>order/complaint/showSelectStaffDialog.do"></div>
</body>
</html>


