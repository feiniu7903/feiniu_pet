var dutyDetailsIndex = 101;

$(function () {

    $("#updateComplaint").click(function () {

        var ncComplaint_contact = $("#ncComplaint_contact").val();
        if ($.trim(ncComplaint_contact) == "") {
            alert("“投诉联系人”不能为空");
            return;
        }

        var ncComplaint_contactMobile = $("#ncComplaint_contactMobile").val();
        if ($.trim(ncComplaint_contactMobile) == "") {
            alert("“联系电话”不能为空");
            return;
        }

        var ncComplaint_identity = $("#ncComplaint_identity").val();
        if ($.trim(ncComplaint_identity) == "") {
            alert("“投诉人身份”不能为空");
            return;
        }

        var ncComplaint_source = $("#ncComplaint_source").val();
        if ($.trim(ncComplaint_source) == "") {
            alert("“投诉来源”不能为空");
            return;
        }

        var ncComplaint_replyAging = $("#ncComplaint_replyAging").val();
        if ($.trim(ncComplaint_replyAging) == "") {
            alert("“回复时效”不能为空");
            return;
        }

        var ncComplaint_belongsCenter = $("#ncComplaint_belongsCenter").val();
        if ($.trim(ncComplaint_belongsCenter) == "") {
            alert("“产品所属中心”不能为空");
            return;
        }

        var from_serial = $("#complaint_info_form").serialize();

        $.post(
            "/super_back/order/complaint/updateComplaint.do",
            from_serial,
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("更新成功");
                    window.location.reload(window.location.href);
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );

    });
    $("#activationComplaint").click(function () {

        $.post(
            "/super_back/order/complaint/activationComplaint.do",
            {
                "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
            },
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("激活成功");
                    window.location.reload(window.location.href);
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );

    });
    $("#saveComplaintResultButton").click(function () {

        var treatmentType = $("input[class='treatmentType']:checked").val();

        if (typeof(treatmentType) == "undefined") {
            alert("请选择“处理选项”");
            return;
        }
        var ncComplaintResult_remark = $("#ncComplaintResult_remark").val();
        if ($.trim(ncComplaintResult_remark) == "") {
            alert("请填写“备注”信息");
            return;
        }

        $.post(
            "/super_back/order/complaint/saveComplaintResult.do",
            $("#complaint_result_from").serialize(),
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("保存成功");
                    $("#ncComplaintResult_resultId").val(data.jsonMap.resultId);

                    var treatmentType = $("input[class='treatmentType']:checked").val();

                    if (treatmentType == "COMMUNICATION" || treatmentType == "APOLOGY") {
                        $("#ncComplaintResult_cashCompensation").val("");
                    }
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );

    });
    $("#closeComplaint").click(function () {

        $.post(
            "/super_back/order/complaint/closeComplaint.do",
            {
                "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
            },
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("关闭成功");
                    window.location.reload(window.location.href);
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );

    });

    $("#selectComplaintTracking").click(function () {
        refreshComplaintTrackingList();
    });

    $("#saveComplaintTracking").click(function () {
        var ct_cate = $('input[name="ncComplaintTracking.category"]:checked').val();
        if (typeof(ct_cate) == "undefined") {
            alert("请选择类别");
            return;
        }
        var ncComplaintTracking_details = $("#ncComplaintTracking_details").val();

        if ($.trim(ncComplaintTracking_details) == "") {
            alert("“追踪信息”不能为空");
            return;
        }

        $.post(
            "/super_back/order/complaint/saveComplaintTracking.do",
            $("#complaint_tracking_add_from").serialize(),
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("保存成功");
                    $("#complaintTrackingType").val("");
                    $("#ncComplaintTracking_trackingId").val("");
                    refreshComplaintTrackingList();
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );
    });
    $("#complaintCompleteButton").click(function () {

        $.post(
            "/super_back/order/complaint/completeComplaint.do",
            $("#complaint_complete_from").serialize(),
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("投诉处理完毕");
                    window.location.reload(window.location.href);
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );
    });

    $("#trackingUploadAffixButton").click(function () {

        $('#tracking_upload_affix_div').showWindow({
                title: '上传附件',
                width: 500,
                data: {
                    "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });
    $("#confirmationButton").click(function () {

        $('#confirmation_affix_div').showWindow({
                title: '上传附件',
                width: 500,
                data: {
                    "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });
    $("#remindButton").click(function () {

        $('#remind_div').showWindow({
                title: '自我提醒',
                width: 500,
                data: {
                    "ncComplaintRemind.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });
    $("#showHistoryOrderDetailButton").click(function () {

        $('#historyDiv').showWindow({
                title: '订单详情',
                width: 1000,
                data: {
                    "orderId": $("#ncComplaint_orderId").val()
                }
            }
        );

    });
    $("#editRelatedComplaint").click(function () {

        $('#edit_related_complaint_div').showWindow({
                title: '编辑关联投诉',
                width: 600,
                data: {
                    "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });
 $("#editRelatedOrder").click(function () {

        $('#edit_related_order_div').showWindow({
                title: '编辑关联订单',
                width: 600,
                data: {
                    "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });

    $("#transferButton").click(function () {

        $('#select_staff_div').showWindow({
                title: '转移客服',
                width: 450,
                height: 250,
                data: {
                    "selectUserType": "TRANSFER"
                }
            }
        );

    });
    $(".treatmentType").click(function () {
        if ($(this).val() == "COMMUNICATION" || $(this).val() == "APOLOGY") {
            $("#CASHCOMPLAINT_TR").hide();
        }
        if ($(this).val() == "CASHCOMPLAINT") {
            $("#CASHCOMPLAINT_TR").show();
        }

    });
    $("#sendEmailButton").click(function () {
        $('#email_div').showWindow({
                title: '发送邮件',
                width: 850,
                data: {
                    "complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );
    });
    $("#sendMessageButton").click(function () {
        $('#message_div').showWindow({
                title: '发送信息',
                width: 600,
                data: {
                    "complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );
    });
    $("#upgradeButton").click(function () {

        $('#upgrade_div').showWindow({
                title: '投诉升级',
                width: 500,
                data: {
                    "ncComplaintTracking.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });
    $("#compensationRefundButton").click(function () {

        $('#compensation_refund_div').showWindow({
                title: '录入补偿退款',
                width: 870,
                data: {
                    "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
                }
            }
        );

    });

    $(".selectOrderSupplierButton").click(function () {

        var orderId = $("#ncComplaint_orderId").val();

        if (orderId == null) {
            alert("请先录入订单号");
            return;
        }
        $('#select_order_supplier_div').showWindow({
                title: '获取订单中供应商',
                width: 870,
                data: {
                    "orderId": orderId,
                    "complaintDutyType":this.type
                }
            }
        );

    });
    $(".selectDepartmentButton").click(function () {

        $('#select_department_div').showWindow({
                title: '选择部门',
                width: 350 ,
                data: {
                    "complaintDutyType":this.type
                }
            }
        );

    });
    $(".selectStaffButton").click(function () {

        $('#select_staff_div').showWindow({
                title: '选择员工',
                width: 700,
                height: 400,
                data: {
                    "selectUserType": "SELECT_STAFF",
                    "complaintDutyType":this.type
                }
            }
        );

    });
    $(".corporateHeadquartersButton").click(function () {
        addDutyDetails("公司总部", ["公司总部"],this.type);

    });

    $("#saveDutyButton").click(function () {

    	var boxs = $('#defectCategorysTd').find('input[type=checkbox]:checked');
    	if (boxs.length == 0) {
    		 alert("请选问题类型");
    		 return;
    	}
    	var ncComplaintDuty_desc = $("#ncComplaintDuty_desc").val();
        if ($.trim(ncComplaintDuty_desc) == "") {
            alert("请填写问题简述");
            return;
        }
        var ncComplaintDuty_result = $("#ncComplaintDuty_result").val();
        if ($.trim(ncComplaintDuty_result) == "") {
        	alert("请填写认定结果");
        	return;
        }
        var ncComplaintDuty_advice = $("#ncComplaintDuty_advice").val();
        if ($.trim(ncComplaintDuty_advice) == "") {
        	alert("请填写改进建议");
        	return;
        }
//            var defectCategory = $("#ncComplaintDuty_defectCategory").val();

//            if (defectCategory == "") {
//                alert("请选择缺陷类别");
//                return;
//            }
    	/*
    	var remark = $("#ncComplaintDuty_remark").val();
        if ($.trim(remark) == "") {
            alert("请填写备注");
            return;
        }*/

        if (confirm("保存责任认定，整个投诉处理完成，后续请补充上传相关责任认定书")) {

            $.post(
                "/super_back/order/complaint/saveComplaintDuty.do",
                $("#complaint_duty_form").serialize(),
                function (data) {

                    if (data.jsonMap.status == "success") {
                        alert("保存成功");
                        window.location.reload(window.location.href);
                    } else {
                        alert(data.jsonMap.status);
                    }
                }
            );

        }
    });

    $("#saveDutyReparationButton").click(function () {

        var remark = $("#ncComplaintDutyReparation_remark").val();

        if ($.trim(remark) == "") {
            alert("请填写备注");
            return;
        }


        var dd_bool = true;

        $("input[class='duty_details_reparation_amount']").each(function () {

            if ($.trim($(this).val()) == "") {
                alert("请填写赔偿金额");
                $(this).focus();
                dd_bool = false;
                return false;
            }
        });

        if (!dd_bool) {
            return;
        }

        var from = $("#complaint_duty_reparation_form").serialize();
        $.post(
            "/super_back/order/complaint/saveComplaintDuty.do",
            from,
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("保存成功");
                    window.location.reload(window.location.href);
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );

    });

    $("#ncComplaint_detailsComplaint").keyup(function () {
        checkdetailsComplaintLength();
    });

    checkdetailsComplaintLength();
});


function refreshComplaintTrackingList() {
    $.post(
        "/super_back/order/complaint/selectComplaintTracking.do",
        {
            "ncComplaint.complaintId": $("#ncComplaint_complaintId").val(),
            "ncComplaintTracking.category": $("#complaintTrackingType").val()
        },
        function (data) {

            if (data.jsonMap.status == "success") {
                resetComplaintTrackingTable(data.jsonMap.ncComplaintTrackingList);
            } else {
                alert(data.jsonMap.status);
            }
        }
    );
}
function resetComplaintTrackingTable(ncComplaintTrackingList) {

    var html = "<table class=\"nc_table\" cellspacing=\"1\" cellpadding=\"4\">" +
        "        <tr class=\"nc_tr_head\">" +
        "            <td>操作时间</td>" +
        "            <td>类别</td>" +
        "            <td>详细内容</td>" +
        "            <td>操作员</td>" +
        "            <td>相关附件</td>" +
        "        </tr>";


    $(ncComplaintTrackingList).each(function () {
        html += "<tr class=\"nc_tr_body\">";
        var time = this.operationTime;
        time = time.replace("T", " ");
        html += "     <td>" + time + "</td>";
        html += "     <td>" + this.category + "</td>";
        html += "     <td>" + (this.details == null ? "" : this.details) + "</td>";
        html += "     <td>" + this.operator + "</td>";

        if (this.comAffix == null) {
            html += "     <td></td>";
        } else {
            html += "<td>";
            html += "   <a title=\"" + this.comAffix.name + "\" href=\"/pet_back/contract/downLoad.do?path=" + this.comAffix.fileId + "\" style=\"cursor: pointer;\">";
            html += this.comAffix.fileType;
            html += "   </a>";
            html += "</td>";
        }


        html += "</tr>";
    });

    html += "    </table>";

    $("#complaintTrackingTable").html(html);

}

function editRemind(remindId) {

    $('#remind_div').showWindow({
            title: '自我提醒',
            width: 500,
            data: {
                "ncComplaintRemind.remindId": remindId
            }
        }
    );
}
function deleteRemind(remindId) {
    if (confirm("确定删除自我提醒么？")) {
        $.post(
            "/super_back/order/complaint/deleteComplaintRemind.do",
            {
                "ncComplaintRemind.remindId": remindId
            },
            function (data) {
                if (data.jsonMap.status == "success") {
                    alert("删除成功");
                    refreshComplaintRemindList();
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );
    }
}
function deleteConfirmation(affixId) {
    if (confirm("确定删除责任认定书么？")) {
        $.post(
            "/super_back/order/complaint/deleteComplaintConfirmation.do",
            {
                "comAffix.affixId": affixId
            },
            function (data) {
                if (data.jsonMap.status == "success") {
                    alert("删除成功");
                    refreshComplaintConfirmationList();
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );
    }
}
function refreshComplaintRemindList() {
    $.post(
        "/super_back/order/complaint/selectComplaintRemind.do",
        {
            "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
        },
        function (data) {

            if (data.jsonMap.status == "success") {
                resetComplaintRemindTable(data.jsonMap.ncComplaintRemindList);
            } else {
                alert(data.jsonMap.status);
            }
        }
    );
}

function resetComplaintRemindTable(ncComplaintRemindList) {
    var html = "";

    html += "<table>";
    $(ncComplaintRemindList).each(function () {
        html += "   <tr class=\"nc_tr_body\">";
        html += "    <td>自我提醒：</td>";
        var time = this.remindTime;
        time = time.replace("T", " ");
        html += "    <td>" + time + "</td>";
        html += "    <td>" + this.processInfo + "</td>";
        html += "    <td>";
        html += "        <img src=\"/super_back/img/icon/edit.png\" alt=\"编辑\" onclick=\"editRemind(" + this.remindId + ")\">";
        html += "        <img src=\"/super_back/img/icon/delete.png\" alt=\"删除\" onclick=\"deleteRemind(" + this.remindId + ")\">";
        html += "    </td>";
        html += "   </tr>";
    });
    html += "</table>";

    $("#remind_list").html(html);

}
function refreshComplaintConfirmationList() {
    $.post(
        "/super_back/order/complaint/selectComplaintConfirmationAffixList.do",
        {
            "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
        },
        function (data) {

            if (data.jsonMap.status == "success") {
                resetComplaintConfirmationTable(data.jsonMap.confirmationAffixList);
            } else {
                alert(data.jsonMap.status);
            }
        }
    );
}
function resetComplaintConfirmationTable(confirmationAffixList) {
    var html = "<table>";
    $(confirmationAffixList).each(function () {
        html += "   <tr>";
        html += "       <td>";
        html += "           <a href=\"/pet_back/contract/downLoad.do?path=" + this.fileId + "\" style=\"cursor: pointer;\">" + this.name + "</a>";
        html += "       </td>";
//        html += "       <td>";
//        html += "           <img src=\"/super_back/img/icon/delete.png\" alt=\"删除\" onclick=\"deleteConfirmation(" + this.affixId + ")\">";
//        html += "       </td>";
        html += "   </tr>";
    });
    html += "</table>";
    $("#ncComplaintConfirmationList").html(html);

}

function addDutyDetails(dutyMain, mainNames, type) {

    var html = "";

    if (type == "reparation") {

        $.each(mainNames, function (i, value) {
            var ii = dutyDetailsIndex++;
            html += "<tr>";
            html += "   <th>";
            html += dutyMain;
            html += "       <input type='hidden' name='ncComplaintDutyDetailsList[" + ii + "].dutyMain' value='" + dutyMain + "'>";
            html += "       <input type='hidden' name='ncComplaintDutyDetailsList[" + ii + "].dutyId' value='" + $("#ncComplaintDutyReparation_dutyId").val() + "'>";
            html += "   </th>";
            html += "   <td>名称：</td>";
            html += "   <td><input name='ncComplaintDutyDetailsList[" + ii + "].mainName' type='text' readonly value='" + value + "'></td>";
            html += "   <td>赔偿金额：</td>";
            html += "   <td><input type='text' class='duty_details_reparation_amount' onkeyup='addAmount(this)' name='ncComplaintDutyDetailsList[" + ii + "].amount' style='width: 80px;text-align: right;'> RMB</td>";
            html += "   <td><img src='/super_back/img/icon/delete.png' alt='删除' onclick='deleteDutyDetails(this)'></td>";
            html += "</tr>";
        });

        $("#complaintDutyDetailsReparationTable").append(html);

    } else if(type=="duty"){

        $.each(mainNames, function (i, value) {
            var ii = dutyDetailsIndex++;
            html += "<tr>";
            html += "   <th>";
            html += dutyMain;
            html += "       <input type='hidden' name='ncComplaintDutyDetailsList[" + ii + "].dutyMain' value='" + dutyMain + "'>";
            html += "       <input type='hidden' name='ncComplaintDutyDetailsList[" + ii + "].dutyId' value='" + $("#ncComplaintDuty_dutyId").val() + "'>";
            html += "   </th>";
            html += "   <td>名称：</td>";
            html += "   <td><input name='ncComplaintDutyDetailsList[" + ii + "].mainName' type='text' readonly value='" + value + "'></td>";
            html += "   <td><img src='/super_back/img/icon/delete.png' alt='删除' onclick='deleteDutyDetails(this)'></td>";
            html += "</tr>";
        });

        $("#complaintDutyDetailsTable").append(html);
    }
}
function deleteDutyDetails(obj) {
    $(obj).parent().parent().remove();
    resertAmount();
}

function closeStaffDialog() {
    $("#select_staff_div").dialog("close");
}
function updateOrderId() {

    $('#update_order_id_div').showWindow({
            title: '修改订单号',
            width: 500,
            data: {
                "ncComplaint.complaintId": $("#ncComplaint_complaintId").val()
            }

        }
    );
}
function resetRelatedComplaintTable(complaintIds) {
    var html = "";

    $.each(complaintIds, function (i, val) {
        html += "<a href='/super_back/order/complaint/toComplaintEdit.do?ncComplaint.complaintId=" + val + "&view=true'>" + val + "</a>&nbsp;";
    });
    $("#relatedComplaintTable").html(html);
}
function resetRelatedOrderTable(relatedOrders) {
    var html = "";

    $.each(relatedOrders, function (i, val) {

        html += "<a onclick=\"showDetailDiv('historyDiv', '"+val+"')\" style=\"cursor: pointer\">"+val+"</a>&nbsp;";
    });
    $("#relatedOrderTable").html(html);
}

function transferComplaint(userNames) {

    var names = "";

    $.each(userNames, function (i, val) {
        names += val + ",";
    });

    if (names.length > 0) {
        names = names.substr(0, names.length - 1);
    }

    $.post(
        "/super_back/order/complaint/transferComplaint.do",
        {
            "complaintIds": $("#ncComplaint_complaintId").val(),
            "transferComplaintUserNames": names
        },
        function (data) {
            if (data.jsonMap.status == "success") {
                alert("转移客服成功");
                window.location.href = "/super_back/order/complaint/toComplaintList.do";
            } else {
                alert(data.jsonMap.status);
            }
        }
    );
}


function closeCompensationRefundDialog() {
    $('#compensation_refund_div').dialog("close");
    window.location.reload(window.location.href);
}

function checkdetailsComplaintLength() {
    try {
        $("#detailsComplaintCount").text($("#ncComplaint_detailsComplaint").val().length);
        $("#detailsComplaintCount1").text(500 - $("#ncComplaint_detailsComplaint").val().length);
    } catch (e) {

    }
}
function deleteNcComplaintTrackingFileInfo() {
    $("#ncComplaintTracking_trackingId").val("");
    $("#ncComplaintTrackingFileInfo").html("");
}
function removejscssfile(filename, filetype) {
    var targetelement = (filetype == "js") ? "script" : (filetype == "css") ? "link" : "none";
    var targetattr = (filetype == "js") ? "src" : (filetype == "css") ? "href" : "none";
    var allsuspects = document.getElementsByTagName(targetelement);
    for (var i = allsuspects.length; i >= 0; i--) {
        if (allsuspects[i] && allsuspects[i].getAttribute(targetattr) != null && allsuspects[i].getAttribute(targetattr).indexOf(filename) != -1) {
            allsuspects[i].parentNode.removeChild(allsuspects[i]);
        }
    }
}
setInterval(function(){
    removejscssfile("houtai.css","css");
},1000);

function addAmount(obj){
    var amount = $(obj).val();
    if(isNaN(amount)){
        alert("请输入数字");
        $(obj).val("");
        $(obj).focus();
    }
    resertAmount();
}
function resertAmount(){
    var total = 0;
    $("input[class='duty_details_reparation_amount']").each(function () {
        if($.trim($(this).val())!=""){
            total += parseFloat($(this).val());
        }
    });

    $("#ncComplaintDutyReparation_totalAmount").val(total);
}
