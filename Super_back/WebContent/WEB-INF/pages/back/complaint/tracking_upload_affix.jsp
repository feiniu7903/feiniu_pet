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
                    $("#uploadImg").show();
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
                    $("#uploadImg").hide();
                }});

            $("#saveButton").click(function(){
                var ct_cate = $('input[name="comAffix.fileType"]:checked').val();
                if (typeof(ct_cate) == "undefined") {
                    alert("请选择附件类型");
                    return;
                }

                if($.trim($("#comAffix_name").val())==""){
                    alert("请填写附件名称");
                    return;
                }
                if($("#fileId").val()==""){
                    alert("请选上传文件");
                    return;
                }

                $.post(
                        "/super_back/order/complaint/saveComplaintTrackingAffix.do",
                        $("#com_affix_from").serialize(),
                        function (data) {
                            if (data.jsonMap.status == "success") {
                                $("#ncComplaintTracking_trackingId").val(data.jsonMap.trackingId);
                                $("#tracking_upload_affix_div").dialog("close");



                                setFileInfo();



                                alert("保存成功");
                            } else {
                                alert(data.jsonMap.status);
                            }
                        }
                );

            });
        });
        function setFileInfo(){

            var fileType = $("input[class='fileTypeCls']:checked").val();

            var fileTypeName = "";


            if(fileType == "RECORDING"){
                fileTypeName="录音";
            }
            if(fileType == "PICTURE"){
                fileTypeName="图片";
            }
            if(fileType == "DOCUMENTS"){
                fileTypeName="文档";
            }

            var html = "";
            html += fileTypeName + " : ";
            html += "<a href='/pet_back/contract/downLoad.do?path=" + $("#fileId").val() + "' style='cursor: pointer;'>";
            html += $("#comAffix_name").val();
            html += "</a> ";
            html +="(最大支持10M) ";
            html +="<img src='<%=request.getContextPath()%>/img/icon/delete.png' alt='删除' onclick='deleteNcComplaintTrackingFileInfo()'>";

            $("#ncComplaintTrackingFileInfo").html(html);
        }
    </script>
</head>
<body>
<div>
    <form id="com_affix_from" style="text-align: center;">
        <table class="nc_table" cellspacing="1" cellpadding="1" width="100%">
            <tr>
                <td class="nc_tr_head ar"> *附件类型：</td>
                <td class="nc_tr_body al">
                    <s:radio list="@com.lvmama.comm.vo.Constant$NC_COMPLAINT_TRACKING_FILE_TYPE@values()"
                             listKey="code" listValue="cnName"
                             name="comAffix.fileType" cssClass="fileTypeCls"/>
                </td>
            </tr>
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
                    <span id="uploadImg" style="display: none;">上传中 <img src="<%=request.getContextPath()%>/img/base/ajaxload.gif" alt=""></span>
                    <input type="hidden" id="fileId" name="comAffix.fileId"/>
                    <input type="hidden" id="fileName" name="comAffix.memo"/>
                    <span id="fileDowload"></span>
                    <span>(最大支持10M)</span>
                </td>
            </tr>
        </table>
        <br>
        <input type="button" onclick="" id="saveButton" value="保存"/>
    </form>
</div>

</body>
</html>