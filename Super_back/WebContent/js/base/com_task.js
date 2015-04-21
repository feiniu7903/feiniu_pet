$(document).ready(function () {

    $("#cancelComTaskButton").click(function () {
        window.location.href = basePath + "pub/toComTaskList.do";
    });

    $("#saveComTaskButton").click(function () {

        var comTask_taskName = $("#comTask_taskName").val();
        if ($.trim(comTask_taskName) == "") {
            alert("“任务名称”不能为空");
            return;
        }

        var comTask_webServiceUrl = $("#comTask_webServiceUrl").val();
        if ($.trim(comTask_webServiceUrl) == "") {
            alert("“webService地址”不能为空");
            return;
        }

        var comTask_cycle = $("#comTask_cycle").val();
        if (comTask_cycle == 'WEEK') {
            var count = $("input[name='weeks']:checkbox:checked").size();
            if (count == 0) {
                alert("请选择周几执行");
                return;
            }
            $("#comTask_cycleDimension").val("1");
        }

        var comTask_planTime = $("#comTask_planTime").val();
        if ($.trim(comTask_planTime) == "") {
            alert("“计划时间”不能为空");
            return;
        }

        var cycleDimension = $("#comTask_cycleDimension");
        var cycleDimensionVal = $.trim($(cycleDimension).val());
        var type = "^[0-9]*[1-9][0-9]*$";
        var re = new RegExp(type);
        if (cycleDimensionVal.match(re) == null) {
            alert("“周期尺度”应为正整数!");
            $(cycleDimension).val("1");
            $(cycleDimension).focus();
            return;
        }

        $.post(
            basePath + "pub/saveComTask.do",
            $("#com_task_form").serialize(),
            function (data) {

                if (data.jsonMap.status == "success") {
                    alert("保存任务成功");
                    window.location.href = basePath + "pub/toComTaskList.do";
                } else {
                    alert(data.jsonMap.status);
                }
            }
        );
    });

    $("#comTask_cycle").change(function () {
        if (this.value == 'WEEK') {
            $("#week").show();
            $("#dimension").hide();
        } else {
            $("#week").hide();
            $("#dimension").show();
        }
    }).change();
});