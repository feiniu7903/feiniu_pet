<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:set var="basePath"><%=request.getContextPath()%>
</s:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>上传附件:</title>
    <script type="text/javascript">
        var basePath = '/pet_back';
    </script>
    <script type="text/javascript" src="/pet_back/js/base/ajaxupload.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/themes/complaint/complaint_edit.css"/>
    <style type="text/css">
        .ar {
            text-align: right;
            padding-right: 5px;
        }

        .al {
            text-align: left;
            padding-left: 5px;
        }
    </style>
    <script type="text/javascript">

        $(function () {
            $("#uploadChangeFile").fileUpload({
                onSubmit: function () {
                    $("#uploadChangeFile").attr("disabled", true);
                },
                onComplete: function (file, dt) {
                    var data = eval("(" + dt + ")");
                    if (data.success) {
                        $("#fileId").val(data.file);
                        $("#fileName").val(data.fileName);
                        $("#fileDowload").text("上传成功");
                    } else {
                        alert(data.msg);
                    }
                    $("#uploadChangeFile").removeAttr("disabled");
                }});

            $("#saveButton").click(function(){


                if($.trim($("#comAffix_name").val())==""){
                    alert("请填写附件名称");
                    return;
                }
                if($("#fileId").val()==""){
                    alert("请选上传文件");
                    return;
                }

                $.post(
                        "/super_back/order/complaint/saveComplaintConfirmationAffix.do",
                        $("#com_affix_from").serialize(),
                        function (data) {
                            if (data.jsonMap.status == "success") {
                                alert("保存成功");
                                $("#confirmation_affix_div").dialog("close");
                                refreshComplaintConfirmationList();
                            } else {
                                alert(data.jsonMap.status);
                            }
                        }
                );

            });
        });
    </script>
</head>
<body>
<div>
    <form id="com_affix_from" style="text-align: center;">
        <input type="hidden" name="comAffix.objectId" value="<s:property value="ncComplaint.complaintId"/>">
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar"> *附件名称：</td>
                <td class="nc_tr_body al">
                    <input type="text" id="comAffix_name" name="comAffix.name"/>
                </td>
            </tr>
            <tr>
                <td class="nc_tr_head ar"> *上传：</td>
                <td class="nc_tr_body al">
                    <input type="button" value="上传" id="uploadChangeFile" serverType="COM_AFFIX"/>
                    <input type="hidden" id="fileId" name="comAffix.fileId"/>
                    <input type="hidden" id="fileName" name="comAffix.memo"/>
                    <span id="fileDowload"></span>
                </td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value="保存"/>
    </form>
</div>

</body>
</html>