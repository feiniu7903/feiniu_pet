$(document).ready(function () {

    $("#cancelMarkActivityButton").click(function () {
        window.location.href = basePath + "mark_activity/toMarkActivityList.do";
    });

    $("#saveMarkActivityButton").click(function () {

        var markActivity_activityName = $("#markActivity_activityName").val();
        if ($.trim(markActivity_activityName) == "") {
            alert("“活动名称”不能为空");
            return;
        }
        var markActivity_status = $("#markActivity_status").val();
        if ($.trim(markActivity_status) == "") {
            alert("“活动状态”不能为空");
            return;
        }
        var markActivity_personCharge = $("#markActivity_personCharge").val();
        if ($.trim(markActivity_personCharge) == "") {
            alert("“负责人”不能为空");
            return;
        }
        var markActivity_markActivityItemEmail_channel = $("#markActivity_markActivityItemEmail_channel").val();
        if ($.trim(markActivity_markActivityItemEmail_channel) == "") {
            alert("“选择营销渠道”不能为空");
            return;
        }
        var markActivity_markActivityItemEmail_sendWay = $("#markActivity_markActivityItemEmail_sendWay").val();
        if ($.trim(markActivity_markActivityItemEmail_sendWay) == "") {
            alert("“发送方式”不能为空");
            return;
        }
        var markActivity_markActivityItemEmail_sendTime = $("#markActivity_markActivityItemEmail_sendTime").val();
        if ($.trim(markActivity_markActivityItemEmail_sendTime) == "") {
            alert("“发送时间”不能为空");
            return;
        }
        var markActivity_markActivityItemEmail_dataModel = $("#markActivity_markActivityItemEmail_dataModel").val();
        if ($.trim(markActivity_markActivityItemEmail_dataModel) == "") {
            alert("“选择数据模型”不能为空");
            return;
        }
        var markActivity_markActivityItemEmail_content = $("#markActivity_markActivityItemEmail_content").val();
        if ($.trim(markActivity_markActivityItemEmail_content) == "") {
            alert("“邮件内容”不能为空");
            return;
        }
        var markActivity_markActivityItemEmail_excludeTimes = $("#markActivity_markActivityItemEmail_excludeTimes").val();
        if ($.trim(markActivity_markActivityItemEmail_excludeTimes) == "") {
            alert("“发送次数”不能为空");
            return;
        }

        if (isNaN(markActivity_markActivityItemEmail_excludeTimes)) {
            alert("“发送次数”必须是数字");
            $("#markActivity_markActivityItemEmail_excludeTimes").val("");
            $("#markActivity_markActivityItemEmail_excludeTimes").focus();
            return;
        }

        var markActivity_markActivityItemEmail_cycle = $("#markActivity_markActivityItemEmail_cycle").val();
        if (markActivity_markActivityItemEmail_sendWay == 'AUTOMATIC' && markActivity_markActivityItemEmail_cycle == 'WEEK') {
            var count = $("input[name='weeks']:checkbox:checked").size();
            if (count == 0) {
                alert("请选择周几发送");
                return;
            }
        }

        $.post(
            basePath + "mark_activity/saveMarkActivity.do",
            $("#mark_activity_form").serialize(),
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("保存活动成功");
                    window.location.href = basePath + "mark_activity/toMarkActivityList.do";
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );
    });

    $("#markActivity_markActivityItemEmail_sendWay").change(function () {
        if (this.value == 'AUTOMATIC') {
            $("#cycle").show();
            initCycle();
        } else {
            $("#cycle").hide();
        }
    });
    $("#markActivity_markActivityItemEmail_cycle").change(function () {
        if (this.value == 'WEEK') {
            $("#week").show();
            initCycle();
        } else {
            $("#week").hide();
        }
    });
    $("#markActivity_markActivityItemEmail_dataModel").change(function () {
        if (this.value == '') {
            $("#dataModelSpan").html("");
        } else {
            setDataModelSpan(this.value);
        }
    });
});
function init() {

    var markActivity_markActivityItemEmail_sendWay = $("#markActivity_markActivityItemEmail_sendWay").val();
    if (markActivity_markActivityItemEmail_sendWay == 'AUTOMATIC') {
        $("#cycle").show();
        initCycle();
    } else {
        $("#cycle").hide();
    }

    var markActivity_markActivityItemEmail_dataModel = $("#markActivity_markActivityItemEmail_dataModel").val();
    if (markActivity_markActivityItemEmail_dataModel != '') {
        setDataModelSpan(markActivity_markActivityItemEmail_dataModel);
    }

}
function initCycle() {
    var markActivity_markActivityItemEmail_cycle = $("#markActivity_markActivityItemEmail_cycle").val();
    if (markActivity_markActivityItemEmail_cycle == 'WEEK') {
        $("#week").show();
    } else {
        $("#week").hide();
    }
}
function setDataModelSpan(dataModel) {
    $.post(
        basePath + "mark_activity/getDataModelTotal.do",
        {
            "markActivity.markActivityItemEmail.dataModel": dataModel
        },
        function (data) {
            if (data.jsonMap.status == "success") {
                $("#dataModelSpan").html("共筛选出:<span style=\"color: red;\">" + data.jsonMap.total + "</span>人");
            } else {
                alert(data.jsonMap.status);
            }
        }
    );
}
