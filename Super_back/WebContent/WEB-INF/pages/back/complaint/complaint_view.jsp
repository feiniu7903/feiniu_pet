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
<div class="table_box" style="width: 95%">
<br>
<span class="title_head"> 投诉描述 </span>
<span style="float: right;"><a href="<%=request.getContextPath()%>/order/complaint/toComplaintList.do">返回列表</a></span>
<div class="mrtit3" style="padding:10px;">
    <form id="complaint_info_form">
        <table class="nc_form">
            <tr>
                <th>*投诉会员名：</th>
                <td><s:property value="ncComplaint.userName"/></td>
                <th>*性别：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$GENDER@getCnName(ncComplaint.gender)"/>
                </td>
                <th>*投诉日期：</th>
                <td><s:date name="ncComplaint.complaintDate" format="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th>*开始处理时间：</th>
                <td><s:date name="ncComplaint.startProcessTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <th>*结案时间：</th>
                <td><s:date name="ncComplaint.closeTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <th>*完成时间：</th>
                <td><s:date name="ncComplaint.completionTime" format="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
            <tr>
                <th>*投诉单号：</th>
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
                    <s:if test="view==false">
                        <s:if test="ncComplaint.processStatus != 'COMPLETE' && ncComplaint.processStatus != 'CLOSE'">
                            <img src="<%=request.getContextPath()%>/img/icon/edit.png" id="editRelatedComplaint">
                        </s:if>
                    </s:if>
                </td>
                <th>*多次投诉：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_UPGRADE@getCnName(ncComplaint.repeatedComplaint)"/>
                </td>
            </tr>
            <tr>
                <th>*投诉联系人：</th>
                <td><s:property value="ncComplaint.contact"/></td>
                <th>*联系电话：</th>
                <td><s:property value="ncComplaint.contactMobile"/></td>
                <th>*投诉人身份：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_IDENTITY@getCnName(ncComplaint.identity)"/>
                </td>
            </tr>
            <tr>
                <th>*回复时效：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_REPLY_AGING@getCnName(ncComplaint.replyAging)"/>
                </td>
                <th>*是否紧急：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_UPGRADE@getCnName(ncComplaint.urgent)"/>
                </td>
                <th>*录入人：</th>
                <td><s:property value="ncComplaint.entryPeople"/></td>
            </tr>
            <tr>
                <th>是否升级：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_UPGRADE@getCnName(ncComplaint.upgrade)"/>
                </td>
                <th>*投诉来源：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_SOURCE@getCnName(ncComplaint.source)"/>
                </td>
                <th>*当前处理人：</th>
                <td><s:property value="ncComplaint.currentProcessPeople"/></td>
            </tr>
            <tr>
                <th>订单号：</th>
                <td>
                    <s:if test="ncComplaint.orderId !=null">
                        <a style="cursor: pointer" onclick="showDetailDiv('historyDiv', '<s:property value="ncComplaint.orderId"/>')"><s:property value="ncComplaint.orderId"/></a>
                        <s:hidden id="ncComplaint_orderId" name="ncComplaint.orderId"/>
                    </s:if>
                </td>
                <th>产品名称：</th>
                <td>
                    <div style="width: 200px;pxword-wrap: break-word; word-break: break-all;">
                    <s:iterator id="orderItem" value="ordOrder.ordOrderItemProds">
                        <p>
                            <a class="showImportantTips" href="javascript:void(0)"
                               productId="${orderItem.productId}"
                               prodBranchId="${orderItem.prodBranchId}">
                                    ${orderItem.productName}
                            </a>
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
                <th>*产品所属中心：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_BELONGS_CENTER@getCnName(ncComplaint.belongsCenter)"/>

                </td>
            </tr>
            <tr>
                <th>*处理状态：</th>
                <td>
                    <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_PROCESSING_STATUS@getCnName(ncComplaint.processStatus)"/>
                </td>
                <th>*投诉类型：</th>
                <td>
                    <s:property value="complaintTypeCnName"/>
                </td>
                <th>关联订单：</th>
                <td>
                    <s:iterator value="relatedOrders" var="ro">
                        <a onclick="showDetailDiv('historyDiv', '${ro}')" style="cursor: pointer">${ro}</a> &nbsp;
                    </s:iterator>
                </td>
            </tr>
            <tr>
                <th>*投诉详情：</th>
                <td colspan="5">
                    <div style="width: 800px;pxword-wrap: break-word; word-break: break-all;">
                    <s:property value="ncComplaint.detailsComplaint"/>
                        </div>
                </td>
            </tr>
            <s:if test="!view">
                <tr>
                    <td colspan="6">
                        <input id="activationComplaint" type="button" value="重新激活" class="right-button08"/>
                    </td>
                </tr>
            </s:if>
        </table>
    </form>
</div>
<br>
<span class="title_head"> 投诉处理追踪信息 </span>
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

<div id="remind_list">
    <table>
        <s:iterator value="ncComplaintRemindList">
            <tr class="nc_tr_body">
                <td>自我提醒：</td>
                <td><s:date name="remindTime" format="yyyy-MM-dd HH:mm:ss"/></td>
                <td><s:property value="processInfo"/></td>
            </tr>
        </s:iterator>
    </table>
</div>
<br>
<span class="title_head"> 处理结果 </span>

<form id="complaint_result_from">
    <s:hidden name="ncComplaintResult.resultId"/>
    <s:hidden name="ncComplaint.complaintId"/>

    <input type="hidden" name="ncComplaintResult.complaintId" value="<s:property value="ncComplaint.complaintId"/>"/>
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head ar"> *处理选项：</td>
            <td class="nc_tr_body al">
                <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_CASH_COMPLAINT@getCnName(ncComplaintResult.treatmentType)"/>
            </td>
        </tr>
            <tr>
                <td class="nc_tr_head ar"> 积分补偿：</td>
                <td class="nc_tr_body al">
                    <s:property value="ncComplaintResult.integralCompensation"/>
                </td>
            </tr>
            <tr>
                <td class="nc_tr_head ar"> 礼品补偿：</td>
                <td class="nc_tr_body al">
                    <s:property value="ncComplaintResult.gifiCompendation"/>
                </td>
            </tr>
        <s:if test="ncComplaintResult.treatmentType=='CASHCOMPLAINT'">
            <tr>
                <td class="nc_tr_head ar"> 现金补偿：</td>
                <td class="nc_tr_body al">
                    <s:property value="ncComplaintResult.cashCompensation"/> RMB （补偿金额，并非退款金额）
                </td>
            </tr>
        </s:if>
        <tr>
            <td class="nc_tr_head ar"> *备注：</td>
            <td class="nc_tr_body al">
                <s:property value="ncComplaintResult.remark"/>
            </td>
        </tr>
    </table>
</form>
<br>
<span class="title_head"> 补偿退款记录 </span>

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
                <s:property value="ncComplaintDutyReparation.totalAmount"/> RMB
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
                            </th>
                            <td>名称：</td>
                            <td><s:property value="mainName"/></td>
                            <td>赔偿金额：</td>
                            <td><s:property value="amount"/> RMB</td>
                        </tr>
                    </s:iterator>
                </table>
            </td>
        </tr>
        <tr>
            <td class="nc_tr_head ar">*备注：</td>
            <td class="nc_tr_body al" colspan="3"><s:property value="ncComplaintDutyReparation.remark"/></td>
        </tr>
    </table>
</form>
<br>

<p class="nc_tr_body"><span class="title_head"> 责任认定 </span> </p>

<form id="complaint_duty_form">
    <s:hidden name="ncComplaint.complaintId"/>
    <s:hidden name="ncComplaintDuty.dutyId" id="ncComplaintDuty_dutyId"/>
    <input type="hidden" name="ncComplaintDuty.complaintId" value="<s:property value="ncComplaint.complaintId"/>"/>
    <input type="hidden" name="ncComplaintDuty.type" value="DUTY"/>
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head ar">*问题类型：</td>
            <td class="nc_tr_body al">
                <s:property value="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_DUTY_DEFECT_CATEGORY@getCnName(ncComplaintDuty.defectCategory)"/>
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
                            </th>
                            <td>名称：</td>
                            <td><s:property value="mainName"/></td>
                        </tr>
                    </s:iterator>
                </table>


            </td>
        </tr>
        <%-- <tr>
            <td class="nc_tr_head ar">*备注：</td>
            <td class="nc_tr_body al" colspan="3"><s:property value="ncComplaintDuty.remark"/></td>
        </tr> --%>
        <tr>
            <td class="nc_tr_head ar">问题简述：</td>
            <td class="nc_tr_body al" colspan="3"><s:property value="ncComplaintDuty.desc"/></td>
        </tr>
        <tr>
            <td class="nc_tr_head ar">认定结果：</td>
            <td class="nc_tr_body al" colspan="3"><s:property value="ncComplaintDuty.result"/></td>
        </tr>
        <tr>
            <td class="nc_tr_head ar">改进建议：</td>
            <td class="nc_tr_body al" colspan="3"><s:property value="ncComplaintDuty.advice"/></td>
        </tr>
    </table>
</form>

<br>
<span class="title_head"> 责任认定书存档 </span>
<s:if test="view==false">
<span class="nc_tr_body"><a style="cursor: pointer;" id="confirmationButton">上传附件</a></span>
</s:if>
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
<%--
<br>
<span class="title_head"> 完成 </span>

<form id="complaint_complete_from">
    <s:hidden name="ncComplaint.complaintId"/>
    <table class="nc_table" cellspacing="1" cellpadding="1" style="width: 750px;">
        <tr>
            <td class="nc_tr_head" style="text-align: right;width: 150px;"> *状态：</td>
            <td class="nc_tr_body al">完成</td>
        </tr>
        <tr>
            <td class="nc_tr_head ar"> 完成备注或意见：</td>
            <td class="nc_tr_body al">
                <s:property value="ncComplaint.remark"/>
            </td>
        </tr>
    </table>
</form>
--%>
<div id="tracking_upload_affix_div" url="${basePath}order/complaint/showTrackingUploadDialog.do"></div>
<div id="remind_div" url="${basePath}order/complaint/showComplaintRemindDialog.do"></div>
<div id="email_div" url="${basePath}order/complaint/toSendEmail.do"></div>
<div id="message_div" url="${basePath}order/complaint/showMessageDialog.do"></div>
<div id="transfer_div" url="${basePath}order/complaint/showTransferDialog.do"></div>
<div id="upgrade_div" url="${basePath}order/complaint/showUpgradeDialog.do"></div>
<div id="confirmation_affix_div" url="${basePath}order/complaint/showConfirmationUploadDialog.do"></div>
<div id="compensation_refund_div" url="${basePath}ord/refundMent/ordRefundAdd.zul?orderId=<s:property value="ncComplaint.orderId"/>&serviceType=COMPLAINT&saleServiceId=<s:property value="ncComplaint.complaintId"/>&windowType=NcComplaint"></div>
<div id="select_department_div" url="${basePath}order/complaint/showSelectDepartmentDialog.do"></div>
<div id="select_staff_div" url="${basePath}order/complaint/showSelectStaffDialog.do"></div>
<div id="select_order_supplier_div" url="${basePath}order/complaint/showOrderSupplierDialog.do"></div>
<div id="update_order_id_div" url="${basePath}order/complaint/showUpdateOrderIdDialog.do"></div>
<div id="edit_related_complaint_div" url="${basePath}order/complaint/showEditRelatedComplaintDialog.do"></div>
<div id="bg"></div>
<div class="orderpop" id="historyDiv" style="display: none;" href="<%=basePath%>ord/showHistoryOrderDetail.do">
</div>
</body>
</html>
