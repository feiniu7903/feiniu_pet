<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="mis" uri="/tld/lvmama-tags.tld" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/ui/jquery-ui-1.8.5.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/jquery.datepick-zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/op/op_travel_group.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/affix_upload.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/lvmama_common.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/lvmama_dialog.js"></script>
    <script type="text/javascript" src="/pet_back/js/base/dialog.js"></script>

    <script src="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/WdatePicker.js"></script>
    <script src="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/lang/en.js"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/skin/default/datepicker.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/js/op/groupBudget/component/My97DatePicker/skin/WdatePicker.css"/>
	


    <script type="text/javascript" src="<%=request.getContextPath()%>/js/complaint/complaint_edit.js?Math.random()"></script>

    <script type="text/javascript" src="<%=request.getContextPath()%>/js/base/remoteUrlLoad.js"></script>
    <script language="javascript" src="<%=request.getContextPath()%>/js/ord/ord.js" type="text/javascript"></script>
    <script language="javascript" src="<%=request.getContextPath()%>/js/ord/in_add.js" type="text/javascript"></script>
    <script language="javascript" src="<%=request.getContextPath()%>/js/ord/ord_div.js" type="text/javascript"></script>
    <script src="${basePath}js/phoneorder/important_tips.js" type="text/javascript"></script>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/base/jquery.ui.all.css"/>
     <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/cc.css"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/complaint/complaint_edit.css?Math.random()"/>

</head>
<body>
<div class="table_box" style="width:960px;">
<br>
<span class="title_head"> 投诉描述 </span>
<span style="float: right;"><a href="<%=request.getContextPath()%>/order/complaint/toComplaintList.do">返回列表</a></span>
<div class="mrtit3" style="padding:10px;">
    <form id="complaint_info_form">
        <table class="nc_form">
            <tr>
                <th><span class="startCls">*</span>投诉会员名：</th>
                <td><s:property value="ncComplaint.userName"/></td>
                <th><span class="startCls">*</span>性别：</th>
                <td>
                    <input type="radio" name="ncComplaint.gender" value="MAN" <s:if test="ncComplaint.gender == 'MAN'">checked="checked" </s:if>>男
                    <input type="radio" name="ncComplaint.gender" value="WOMAN" <s:if test="ncComplaint.gender == 'WOMAN'">checked="checked" </s:if>>女
                </td>
                <th><span class="startCls">*</span>投诉日期：</th>
                <td><s:date name="ncComplaint.complaintDate" format="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th><span class="startCls">*</span>开始处理时间：</th>
                <td><s:date name="ncComplaint.startProcessTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <th><span class="startCls">*</span>结案时间：</th>
                <td><s:date name="ncComplaint.closeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <th><span class="startCls">*</span>完成时间：</th>
                <td><s:date name="ncComplaint.completionTime" format="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th><span class="startCls">*</span>投诉单号：</th>
                <td>
                    <s:property value="ncComplaint.complaintId"/>
                    <s:hidden name="ncComplaint.complaintId"/>
                </td>
                <th>关联投诉：</th>
                <td>
                    <span id="relatedComplaintTable">
                    <s:iterator value="relatedComplaints" var="rc">
                        <a href="${basePath}order/complaint/toComplaintEdit.do?ncComplaint.complaintId=${rc}&view=true">${rc}</a>&nbsp;
                    </s:iterator>
                    </span>
                        <img src="<%=request.getContextPath()%>/img/icon/edit.png" id="editRelatedComplaint">
                </td>
                <th><span class="startCls">*</span>多次投诉：</th>
                <td><s:select list="#{'NO':'否','YES':'是'}" name="ncComplaint.repeatedComplaint"/></td>
            </tr>
            <tr>
                <th><span class="startCls">*</span>投诉联系人：</th>
                <td><s:textfield name="ncComplaint.contact" id="ncComplaint_contact" maxlength="20"/></td>
                <th><span class="startCls">*</span>联系电话：</th>
                <td><s:textfield name="ncComplaint.contactMobile" id="ncComplaint_contactMobile" maxlength="20"/></td>
                <th><span class="startCls">*</span>投诉人身份：</th>
                <td>

                    <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_IDENTITY@values()"
                              listKey="code" listValue="cnName"
                              headerKey="" headerValue="请选择"
                              name="ncComplaint.identity" id="ncComplaint_identity" value="ncComplaint.identity"/>
                </td>
            </tr>
            <tr>
                <th><span class="startCls">*</span>回复时效：</th>
                <td>
                    <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_REPLY_AGING@values()"
                              listKey="code" listValue="cnName"
                              headerKey="" headerValue="请选择"
                              name="ncComplaint.replyAging" value="ncComplaint.replyAging"/>
                </td>
                <th><span class="startCls">*</span>是否紧急：</th>
                <td>
                    <s:select list="#{'NO':'否','YES':'是'}" name="ncComplaint.urgent"/>
                </td>
                <th><span class="startCls">*</span>录入人：</th>
                <td><s:property value="ncComplaint.entryPeople"/></td>
            </tr>
            <tr>
                <th>是否升级：</th>
                <td>
                    <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_UPGRADE@values()"
                              listKey="code" listValue="cnName"
                              name="ncComplaint.upgrade" value="ncComplaint.upgrade"/>
                </td>
                <th><span class="startCls">*</span>投诉来源：</th>
                <td>
                    <select name="ncComplaint.source" id="ncComplaint_source">
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
                <th><span class="startCls">*</span>当前处理人：</th>
                <td><s:property value="ncComplaint.currentProcessPeople"/></td>
            </tr>
            <tr>
                <th>订单号：</th>
                <td>
                    <s:if test="ncComplaint.orderId !=null">
                    	<s:if test="ncComplaint.sysCode=='VST'">
	                        <a style="cursor: pointer" onclick="openWin('/vst_back/order/orderStatusManage/showOrderStatusManage.do?orderId=${ncComplaint.orderId }')"><s:property value="ncComplaint.orderId"/></a>
                    	</s:if>
                    	<s:else>
	                        <a style="cursor: pointer" onclick="showDetailDiv('historyDiv', '<s:property value="ncComplaint.orderId"/>')"><s:property value="ncComplaint.orderId"/></a>
                    	</s:else>
                        <s:hidden id="ncComplaint_orderId" name="ncComplaint.orderId"/>
                    </s:if>
                    <img src="<%=request.getContextPath()%>/img/icon/edit.png" onclick="updateOrderId()">
                </td>
                <th>产品名称：</th>
                <td>
                    <div style="width: 200px;pxword-wrap: break-word; word-break: break-all;">
                    <s:iterator id="orderItem" value="ordOrder.ordOrderItemProds">
                        <p>
                        	<s:if test="ncComplaint.sysCode=='VST'">
                        		<a href="javascript:openWin('http://hotels.lvmama.com/hotel/${orderItem.productId}')"
	                               >
	                                    ${orderItem.productName}
	                            </a>
                        	</s:if>
                        	<s:else>
	                            <a class="showImportantTips" href="javascript:void(0)"
	                               productId="${orderItem.productId}"
	                               prodBranchId="${orderItem.prodBranchId}">
	                                    ${orderItem.productName}
	                            </a>
                            </s:else>
                        </p>
                    </s:iterator>
                    </div>
                </td>
                <th>产品经理：</th>
                <td><s:property value="permUser.userName"/></td>
            </tr>
            <tr>
                <th>下单日期：</th>
                <td><s:date name="ordOrder.createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <th>出游日期：</th>
                <td><s:date name="ordOrder.visitTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <th>游客合同：</th>
                <td>
                    <s:if test='"NEED_ECONTRACT"==ordOrder.needContract'>
                        <a href='<%=request.getContextPath()%>/ord/downPdfContractDetail.do?orderId=${ordOrder.orderId}&productId=${ordOrder.mainProduct.productId}'>下载合同</a>
                        <a href='<%=request.getContextPath()%>/ord/downAdditionDetail.do?orderId=${ordOrder.orderId}&productId=${ordOrder.mainProduct.productId}'>下载补充条款</a>
                    </s:if>
                    <s:else>
                        无
                    </s:else>
                </td>
            </tr>
            <tr>
                <th>发票情况：</th>
                <td>
                    <s:if test="ordOrder.needInvoice=='true'">是</s:if>
                    <s:elseif test="ordOrder.needInvoice=='false'">否</s:elseif>
                </td>
                <th>游玩人数：</th>
                <td><s:property value="ncComplaint.numberPeople"/></td>
                <th><span class="startCls">*</span>产品所属中心：</th>
                <td>
                    <s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_BELONGS_CENTER@values()"
                              listKey="code" listValue="cnName"
                              headerKey="" headerValue="请选择"
                              id="ncComplaint_belongsCenter" name="ncComplaint.belongsCenter" value="ncComplaint.belongsCenter"/>
                </td>
            </tr>
            <tr>
                <th><span class="startCls">*</span>处理状态：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_PROCESSING_STATUS@getCnName(ncComplaint.processStatus)"/>
                </td>
                <th><span class="startCls">*</span>投诉类型：</th>
                <td>
                    <s:select list="ncComplaintTypeList" listKey="typeId" listValue="typeName"
                              headerKey="" headerValue="请选择"
                              name="ncComplaint.complaintType" value="ncComplaint.complaintType"/>
                </td>
                <th>关联订单：</th>
                <td>
                     <span id="relatedOrderTable">
                    <s:iterator value="relatedOrders" var="ro">
                        <a onclick="showDetailDiv('historyDiv', '${ro}')" style="cursor: pointer">${ro}</a> &nbsp;
                    </s:iterator>
                    </span>
                    <img src="<%=request.getContextPath()%>/img/icon/edit.png" id="editRelatedOrder">
                </td>
            </tr>
            <tr>
                <th><span class="startCls">*</span>投诉详情：</th>
                <td colspan="5">
                    <s:textarea name="ncComplaint.detailsComplaint" id="ncComplaint_detailsComplaint" maxlength="500"/>
                    <span id="detailsComplaintCount"  style="font-size: 12px;color: red;">0</span>/500(你还可输入<span id="detailsComplaintCount1"  style="font-size: 12px;color: red;">500</span>个汉字)
                </td>
            </tr>
                <tr>
                    <td colspan="6">
                            <input id="updateComplaint" type="button" class="button_11" value="更新投诉"/>
                            <input id="closeComplaint" type="button" class="button_11" value="关闭"/>
                    </td>
                </tr>
        </table>
    </form>
</div>
<br>
<span class="title_head"> 投诉处理追踪信息 </span>
筛选类别：
<s:select list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_TRACKING_TYPE_2@values()"
          listKey="code" listValue="cnName"
          headerKey="" headerValue="全部"
          id="complaintTrackingType"
        />
<input id="selectComplaintTracking" type="button" class="button_11" value="  查询  "/>
<br>

<div id="complaintTrackingTable">
    <table class="nc_table" cellspacing="1" cellpadding="1">
        <tr class="nc_tr_head">
            <td>操作时间</td>
            <td>类别</td>
            <td>详细内容</td>
            <td>操作员</td>
            <td>相关附件</td>
        </tr>
        <s:iterator value="ncComplaintTrackingList">
            <tr class="nc_tr_body">
                <td><s:date name="operationTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td><s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_TRACKING_TYPE@getCnName(category)"/></td>
                <td><s:property value="details"/></td>
                <td><s:property value="operator"/></td>
                <td>
                    <s:if test="comAffix != null">
                        <a title="<s:property value="comAffix.name"/>" href="/pet_back/contract/downLoad.do?path=<s:property value="comAffix.fileId"/>" style="cursor: pointer;">
                            <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_TRACKING_FILE_TYPE@getCnName(comAffix.fileType)"/>
                        </a>

                    </s:if>

                </td>
            </tr>
        </s:iterator>
    </table>
</div>
<br>
<form id="complaint_tracking_add_from">
    <table style="font-family: Arial; font-size: 12px; font-weight: bold; font-style: normal; text-decoration: none;">
        <tr>
            <td colspan="3">

                处理： <s:radio list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_TRACKING_TYPE_2@values()"
                             listKey="code" listValue="cnName"
                             name="ncComplaintTracking.category"  value="'TREATMENT_RECOMMENDATIONS'"/>
            </td>

        </tr>
        <tr>
            <td rowspan="2">
                <span id="ncComplaintTrackingFileInfo"> </span> <br>
                <textarea id="ncComplaintTracking_details" name="ncComplaintTracking.details" maxlength="500"></textarea>
                <input type="hidden" id="ncComplaintTracking_trackingId" name="ncComplaintTracking.trackingId">
            </td>
            <td colspan="2">
                <input type="radio" value="PROCESSING" id="ncComplaint_processStatusPROCESSING" name="ncComplaint.processStatus" <s:if test="ncComplaint.processStatus=='PROCESSING'">checked="checked"</s:if>>
                <label for="ncComplaint_processStatusPROCESSING">处理中</label>
                <input type="radio" value="SUSPENDED" id="ncComplaint_processStatusSUSPENDED" name="ncComplaint.processStatus" <s:if test="ncComplaint.processStatus=='SUSPENDED'">checked="checked"</s:if>>
                <label for="ncComplaint_processStatusSUSPENDED">暂缓</label>
                <input type="radio" value="CLOSED" id="ncComplaint_processStatusCLOSED" name="ncComplaint.processStatus" <s:if test="ncComplaint.processStatus=='CLOSED'">checked="checked"</s:if>>
                <label for="ncComplaint_processStatusCLOSED">结案</label>
                <input type="radio" value="STOP" id="ncComplaint_processStatusSTOP" name="ncComplaint.processStatus" <s:if test="ncComplaint.processStatus=='STOP'">checked="checked"</s:if>>
                <label for="ncComplaint_processStatusSTOP">中止</label>
                <s:hidden name="ncComplaint.complaintId"/>
            </td>
        </tr>
        <tr>
            <td>
                <a style="cursor:pointer;" id="trackingUploadAffixButton" href="javascript:void(0);">上传附件</a>
            </td>
            <td>
                <input type="button" id="saveComplaintTracking" class="button_11" value="保存投诉处理过程">
            </td>
        </tr>

    </table>

</form>

<p class="nc_tr_body">
    <span><a style="cursor: pointer;" id="remindButton">自我提醒</a></span>
    <span><a style="cursor: pointer;" id="sendEmailButton">发送邮件</a></span>
    <span><a style="cursor: pointer;" id="sendMessageButton">发送信息</a></span>
    <span><a style="cursor: pointer;" id="transferButton">转移客服</a></span>
</p>
<div id="remind_list">
    <table>
        <s:iterator value="ncComplaintRemindList">
            <tr class="nc_tr_body">
                <td>自我提醒：</td>
                <td><s:date name="remindTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td><s:property value="processInfo"/></td>
                <td>
                        <img src="<%=request.getContextPath()%>/img/icon/edit.png" alt="编辑" onclick="editRemind(<s:property value="remindId"/>)">
                        <img src="<%=request.getContextPath()%>/img/icon/delete.png" alt="删除" onclick="deleteRemind(<s:property value="remindId"/>)">
                </td>
            </tr>
        </s:iterator>
    </table>
</div>
<br>
<span class="title_head"> 处理结果 </span>

<form id="complaint_result_from">
    <s:hidden name="ncComplaintResult.resultId" id="ncComplaintResult_resultId"/>
    <s:hidden name="ncComplaint.complaintId"/>

    <input type="hidden" name="ncComplaintResult.complaintId" value="<s:property value="ncComplaint.complaintId"/>"/>
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head ar"><span class="startCls">*</span>处理选项：</td>
            <td class="nc_tr_body al">
                <s:radio list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_CASH_COMPLAINT@values()"
                         listKey="code" listValue="cnName"
                         name="ncComplaintResult.treatmentType" value="ncComplaintResult.treatmentType" cssClass="treatmentType"/>
            </td>
        </tr>
        <tr id="COMMUNICATION_TR">
            <td class="nc_tr_head ar"> 积分补偿：</td>
            <td class="nc_tr_body al">
                <s:textfield name="ncComplaintResult.integralCompensation" id="ncComplaintResult_integralCompensation"/>
            </td>
        </tr>
        <tr id="APOLOGY_TR">
            <td class="nc_tr_head ar"> 礼品补偿：</td>
            <td class="nc_tr_body al">
                <s:textfield name="ncComplaintResult.gifiCompendation" id="ncComplaintResult_gifiCompendation"/>
            </td>
        </tr>
        <tr id="CASHCOMPLAINT_TR" <s:if test="ncComplaintResult.treatmentType=='COMMUNICATION' || ncComplaintResult.treatmentType=='APOLOGY'">style="display: none"</s:if>>
            <td class="nc_tr_head ar"> 现金补偿：</td>
            <td class="nc_tr_body al">
                <s:textfield name="ncComplaintResult.cashCompensation" id="ncComplaintResult_cashCompensation"/> RMB （补偿金额，并非退款金额）
            </td>
        </tr>
        <tr>
            <td class="nc_tr_head ar"><span class="startCls">*</span>备注：</td>
            <td class="nc_tr_body al">
                <s:textarea name="ncComplaintResult.remark" id="ncComplaintResult_remark" maxlength="20"/>
            </td>
        </tr>
    </table>
</form>
<input type="button" value=" 保存处理结果 " id="saveComplaintResultButton" class="button_11" style="margin-left: 100px;margin-top: 10px;">
<br>
<br>
<span class="title_head"> 补偿退款记录 </span>
<s:if test="ncComplaint.orderId != null">
<span class="nc_tr_body"><input type="button" id="compensationRefundButton" class="button_11" value="录入补偿退款"></span>
</s:if>
<div id="compensationRefundRecordTable">
    <table class="nc_table" cellspacing="1" cellpadding="1">
        <tr class="nc_tr_head">
            <td>退款单号</td>
            <td>退款金额</td>
            <td>退款类型</td>
            <td>处理内容</td>
            <td>提交人</td>
            <td>提交时间</td>
            <td>退款状态</td>
            <td>退款时间</td>
        </tr>
        <s:iterator value="ordRefundmentList">
            <tr class="nc_tr_body">
                <td><s:property value="refundmentId"/></td>
                <td><s:property value="amountYuan"/></td>
                <td><s:property value="refundTypeName"/></td>
                <td><s:property value="memo"/></td>
                <td><s:property value="operatorName"/></td>
                <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td><s:property value="statusName"/></td>
                <td><s:date name="refundTime" format="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </s:iterator>
    </table>
</div>
<br>
<p class="nc_tr_body">
    <span class="title_head"> 补偿认定 </span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="selectOrderSupplierButton"  type="reparation">获取订单中供应商</a></span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="selectDepartmentButton" type="reparation">选择部门</a></span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="selectStaffButton" type="reparation">选择员工</a></span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="corporateHeadquartersButton" type="reparation">公司总部</a></span>
</p>

<form id="complaint_duty_reparation_form">
    <s:hidden name="ncComplaint.complaintId"/>
    <input type="hidden" name="ncComplaintDuty.dutyId" id="ncComplaintDutyReparation_dutyId" value="<s:property value="ncComplaintDutyReparation.dutyId"/>"/>
    <input type="hidden" name="ncComplaintDuty.complaintId" value="<s:property value="ncComplaint.complaintId"/>"/>
    <input type="hidden" name="ncComplaintDuty.type" value="REPARATION"/>
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head ar">
                赔付总金额：
            </td>
            <td class="nc_tr_body al">
                <input type="text" id="ncComplaintDutyReparation_totalAmount" name="ncComplaintDuty.totalAmount" readonly
                       style="width: 80px;text-align: right;" value="<s:property value="ncComplaintDutyReparation.totalAmount"/>"> RMB
            </td>
        </tr>
        <tr>
            <td class="nc_tr_head ar">补偿认定：</td>
            <td class="nc_tr_body al">
                <table id="complaintDutyDetailsReparationTable">
                    <s:iterator value="ncComplaintDutyDetailsReparationList" status="status">
                        <tr>
                            <th>
                                <s:property value="dutyMain"/>
                                <input type="hidden" value="<s:property value="dutyMain"/>" name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].dutyMain">
                                <input type="hidden" value="<s:property value="dutyId"/>" name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].dutyId">
                            </th>
                            <td>名称：</td>
                            <td><input type="text" value="<s:property value="mainName"/>" readonly name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].mainName"></td>
                            <td>赔偿金额：</td>
                            <td><input type="text" style="width: 80px;text-align: right;" value="<s:property value="amount"/>" name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].amount" onkeyup="addAmount(this)" class="duty_details_reparation_amount"> RMB</td>
                            <td>
                                <img onclick="deleteDutyDetails(this)" alt="删除" src="<%=request.getContextPath()%>/img/icon/delete.png">
                            </td>
                        </tr>
                    </s:iterator>
                </table>
            </td>
        </tr>
        <tr>
            <td class="nc_tr_head ar"><span class="startCls">*</span>备注：</td>
            <td class="nc_tr_body al" colspan="3"><textarea id="ncComplaintDutyReparation_remark" name="ncComplaintDuty.remark"><s:property value="ncComplaintDutyReparation.remark"/></textarea></td>
        </tr>
    </table>
        <input type="button" value=" 保存补偿认定 " id="saveDutyReparationButton" class="button_11" style="margin-left: 100px; margin-top: 10px;">
</form>
<br>
<p class="nc_tr_body">
    <span class="title_head"> 责任认定 </span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="selectOrderSupplierButton" type="duty">获取订单中供应商</a></span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="selectDepartmentButton" type="duty">选择部门</a></span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="selectStaffButton" type="duty">选择员工</a></span>
        <span><a style="cursor: pointer;margin-left: 20px;" class="corporateHeadquartersButton" type="duty">公司总部</a></span>
</p>

<form id="complaint_duty_form">
    <s:hidden name="ncComplaint.complaintId"/>
    <s:hidden name="ncComplaintDuty.dutyId" id="ncComplaintDuty_dutyId"/>
    <input type="hidden" name="ncComplaintDuty.complaintId" value="<s:property value="ncComplaint.complaintId"/>"/>
    <input type="hidden" name="ncComplaintDuty.type" value="DUTY"/>
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head ar"><span class="startCls">*</span>问题类型：</td>
            <td class="nc_tr_body al" id="defectCategorysTd">
                 <s:checkboxlist list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_DUTY_DEFECT_CATEGORY@values()"
                  listKey="code" listValue="cnName" 
                  name="ncComplaintDuty.defectCategory" value="ncComplaintDuty.defectCategory"/>
            </td>
        </tr>
        <tr>
            <td class="nc_tr_head ar">责任认定：</td>
            <td class="nc_tr_body al">
                <table id="complaintDutyDetailsTable">
                    <s:iterator value="ncComplaintDutyDetailsList" status="status">
                        <tr>
                            <th>
                                <s:property value="dutyMain"/>
                                <input type="hidden" value="<s:property value="dutyMain"/>" name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].dutyMain">
                                <input type="hidden" value="<s:property value="dutyId"/>" name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].dutyId">
                            </th>
                            <td>名称：</td>
                            <td><input type="text" value="<s:property value="mainName"/>" readonly name="ncComplaintDutyDetailsList[<s:property value="#status.index"/>].mainName"></td>
                            <td>
                                <img onclick="deleteDutyDetails(this)" alt="删除" src="<%=request.getContextPath()%>/img/icon/delete.png">
                            </td>
                        </tr>
                    </s:iterator>
                </table>
            </td>
        </tr>
        <%-- <tr>
            <td class="nc_tr_head ar"><span class="startCls">*</span>备注：</td>
            <td class="nc_tr_body al" colspan="3"><s:textarea id="ncComplaintDuty_remark" name="ncComplaintDuty.remark"/></td>
        </tr> --%>
        <tr>
            <td class="nc_tr_head ar"><span class="startCls"></span>问题简述：</td>
            <td class="nc_tr_body al" colspan="3"><s:textarea id="ncComplaintDuty_desc" name="ncComplaintDuty.desc"/></td>
        </tr>
        <tr>
            <td class="nc_tr_head ar"><span class="startCls"></span>认定结果：</td>
            <td class="nc_tr_body al" colspan="3"><s:textarea id="ncComplaintDuty_result" name="ncComplaintDuty.result"/></td>
        </tr>
        <tr>
            <td class="nc_tr_head ar"><span class="startCls"></span>改进建议：</td>
            <td class="nc_tr_body al" colspan="3"><s:textarea id="ncComplaintDuty_advice" name="ncComplaintDuty.advice"/></td>
        </tr>
    </table>
        <input type="button" value=" 保存责任认定 " id="saveDutyButton" class="button_11" style="margin-left: 100px; margin-top: 10px;">
</form>

<br>
<span class="title_head"> 责任认定书存档 </span>
<span class="nc_tr_body"><a style="cursor: pointer;" id="confirmationButton">上传附件</a></span>
<div id="complaintConfirmationTable">
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head ar">责任认定书：</td>
            <td class="nc_tr_body al" id="ncComplaintConfirmationList">
                <table>
                    <s:iterator value="confirmationAffixList">
                        <tr>
                            <td>
                                <a href="/pet_back/contract/downLoad.do?path=<s:property value="fileId"/>" style="cursor: pointer;">
                                    <s:property value="name"/>
                                </a>
                            </td>
                        </tr>
                    </s:iterator>
                </table>
            </td>
        </tr>
    </table>
</div>
<div id="tracking_upload_affix_div" url="${basePath}order/complaint/showTrackingUploadDialog.do"></div>
<div id="remind_div" url="${basePath}order/complaint/showComplaintRemindDialog.do"></div>
<div id="email_div" url="${basePath}order/complaint/toSendEmail.do"></div>
<div id="message_div" url="${basePath}order/complaint/showMessageDialog.do"></div>
<div id="transfer_div" url="${basePath}order/complaint/showTransferDialog.do"></div>
<div id="upgrade_div" url="${basePath}order/complaint/showUpgradeDialog.do"></div>
<div id="confirmation_affix_div" url="${basePath}order/complaint/showConfirmationUploadDialog.do"></div>
<div id="compensation_refund_div" url="${basePath}ord/refundMent/ordRefundAdd.zul?orderId=<s:property value="ncComplaint.orderId"/>&serviceType=COMPLAINT&saleServiceId=<s:property value="ncComplaint.complaintId"/>&windowType=NcComplaint&sysCode=${ncComplaint.sysCode}"></div>
<div id="select_department_div" url="${basePath}order/complaint/showSelectDepartmentDialog.do"></div>
<div id="select_staff_div" url="${basePath}order/complaint/showSelectStaffDialog.do"></div>
<div id="select_order_supplier_div" url="${basePath}order/complaint/showOrderSupplierDialog.do?sysCode=${ncComplaint.sysCode}"></div>
<div id="update_order_id_div" url="${basePath}order/complaint/showUpdateOrderIdDialog.do"></div>
<div id="edit_related_complaint_div" url="${basePath}order/complaint/showEditRelatedComplaintDialog.do"></div>
<div id="edit_related_order_div" url="${basePath}order/complaint/showEditRelatedOrderDialog.do"></div>
<div id="bg"></div>
<div class="orderpop" id="historyDiv" style="display: none;" href="<%=basePath%>ord/showHistoryOrderDetail.do">
</div>
</body>
</html>
