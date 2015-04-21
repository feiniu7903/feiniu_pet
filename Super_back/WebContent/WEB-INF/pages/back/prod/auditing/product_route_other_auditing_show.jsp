<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>super后台——产品标签</title>
    <link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/base/log.js"></script>

    <script type="text/javascript" src="/super_back/js/ui/jquery-ui-1.8.5.js"></script>
    <script type="text/javascript" src="/super_back/js/base/jquery.datepick-zh-CN.js"></script>
    <script type="text/javascript" src="/super_back/js/base/jquery.jsonSuggest.js"></script>
    <script type="text/javascript" src="/super_back/js/base/jquery.showLoading.min.js"></script>
    <link href="/super_back/themes/base/showLoading.css" rel="stylesheet" type="text/css"/>
    <link href="/super_back/themes/suggest/jquery.suggest.css" rel="stylesheet" type="text/css"/>
    <link href="/super_back/themes/base/jquery.ui.all.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/super_back/js/base/jquery.jsonSuggest-2.min.js"></script>
    <link href="/super_back/themes/base/jquery.jsonSuggest.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/prod/sensitive_word.js"></script>

    <script type="text/javascript">
        function CheckForm() {
            var a = $("#comPcSubjectList1 option:selected").text();
            var b = $("#comPcSubjectList2 option:selected").text();
            if (a == b) {
                alert("请选择不同主题");
                return false;
            }
            else {
                return true;
            }
        }
        $(function () {
            if ($('#show').val() == null || $('#show').val() == "") {
                $('#div2').hide();
                $('#div3').hide();
                alert("该产品无标签");
            }


        })
    </script>
    <style type="text/css">
        .depart_list {
            width: 600px
        }

        .depart_list span {
            width: 120px;
            float: left;
            display: block
        }
    </style>
</head>
<body>

<!-- hidden区域 -->
<input type="hidden" id="productId" value="<s:property value='product.productId' />"/>

<div class="main main02">
    <div class="row1">
        <h3 class="newTit">产品列表

        </h3>
    </div>
    <div class="row3" id="div2">
        <form action="prod/saveProductOther2.do" method="post" onsubmit="return dataCheck();">
            <input type="hidden" name="product.productId" value="<s:property value='product.productId' />"/>
            <input type="hidden" name="productId" value="${productId}"/>

            <input type="hidden" name="dataStr" id="dataStr" value=""/>

            <table cellspacing="0" cellpadding="0" border="0" width="100%" class="proLabel">
                <tbody>
                <tr>
                    <td colspan="4"><h4 class="pro_tit">产品属性1&nbsp;<span>产品属性:(提示：产品属性主要用于搜索，请准确填写)</span></h4></td>
                </tr>
                <tr>
                    <td><b>线路分类：<span class="require">[*]</span></b></td>
                    <td colspan="3">
                        <s:radio name="routeCateGory" list="routeCateGoryList" listKey="code" listValue="name"/>
                        <input id="show" type="hidden" value="<s:property value="routeCateGoryList[0].name"/>"/>
                    </td>
                </tr>
                <tr>
                    <td><b>线路标准：<span class="require">[*]</span></b></td>
                    <td>
                        <s:radio name="routeStandard" list="routeStandardList" listKey="code" listValue="name"/>
                    </td>
                </tr>
                <s:if test="product.subProductType=='GROUP_FOREIGN'||product.subProductType=='FREENESS_FOREIGN'"> </td>
                    </tr>
                    <tr>
                        <td><b>出境区域：<span class="require">[*]</span></b></td>
                        <td>
                            <s:checkboxlist list="departAreaList" name="departArea" listKey="code" listValue="name" value="departAreaCheckedList"></s:checkboxlist>
                        </td>
                    </tr>
                </s:if>
                <s:if test="productTypeVOList!=null&&productTypeVOList.size>0">
                    <input type="hidden" id="typeNum" value="<s:property value='productTypeVOList.size'/>"/>
                    <s:iterator value="productTypeVOList" id="vo" status="index">
                        <tr>
                            <td><b><s:property value="modelType.modelName"/>：<span class="require">[*]</span></b></td>
                            <td><input type="hidden" id="choiceFlag<s:property value='#index.index+1'/>" value="<s:property value='modelType.isMultiChoice'/>"/>
                                <s:if test='modelType.isMultiChoice=="N"'>
                                    <s:iterator value="productModelPropertyList">
                                        <input type="radio" name="productModelProperty<s:property value='#index.index+1'/>" id="prodModelProperty<s:property value='id' />" value="<s:property value='id' />"/><s:property value="property"/>
                                    </s:iterator>
                                </s:if>
                                <s:else>
                                    <s:iterator value="productModelPropertyList">
                                        <input type="checkbox" name="productModelProperty<s:property value='#index.index+1'/>" id="prodModelProperty<s:property value='id' />" value="<s:property value='id' />"/><s:property value="property"/>
                                    </s:iterator>
                                </s:else>


                            </td>
                        </tr>
                    </s:iterator>
                </s:if>
                <s:else>
                    <input type="hidden" id="typeNum" value="0"/>
                </s:else>
                <tr>
                    <td><b>出发班期：<span class="require">[*]</span></b></td>
                    <td>
                        <input type="hidden" id="travelTime" name="travelTime" value=""/>
                        <input type="radio" name="travelTimeSelect" id="travelTimeSelect1" value="AUTO_TIME_UPDATE"/> <label>自动根据时间价格表更新</label>
                        <input type="radio" name="travelTimeSelect" id="travelTimeSelect2" value="CUSTOMER_TIME"/>&nbsp;&nbsp;<input type="text" name="travelTimeValue" id="travelTimeValue" value=""/>
                        &nbsp;&nbsp;<span class="txt_grey">例如：天天出发；每周一、三</span>
                    </td>
                </tr>

                </tbody>
            </table>
        </form>
        <!--proLabel end-->
    </div>

    <s:if test="modelPropertyList2!=null && modelPropertyList2.size>0">
        <div class="row3">
            <h4 class="pro_tit">产品属性2 &nbsp;<span>提示：产品属性主要用于搜索，请准确填写；需要的属性内容没有，请联系线下运营小组，进行添加；</span></h4>
            <s:iterator value="modelPropertyList2">
                <s:if test="isMaintain='Y'">
                    <div class="pro_probox">
                        <table cellspacing="0" cellpadding="0" class="newInput">
                            <tbody>
                            <tr>
                                <td width="120"><b><s:property value="getModeTypeById(firstModelId).modelName"/>&gt;<s:property value="getModeTypeById(secondModelId).modelName"/>：</b></td>
                    <%--
                        <td>
                                    <div class="link_sel_out">
                                        <input type="text" class="newtext1" name="" value="空搜索为查看全部" onfocus="this.value=''">
                                        <input type="button" class="opt_btn opt_btn_search" style="color:black;" name="" value="搜索" firstModelId="<s:property value='firstModelId'/>", secondModelId="<s:property value='secondModelId'/>">
                                        <input type="button" class="opt_btn opt_btn_add" style="color:black;" name="" value="增加" secondModelId="<s:property value='secondModelId'/>" isMaintain="<s:property value='getModeTypeById(secondModelId).isMaintain'/>" isMultiChoice="<s:property value='getModeTypeById(secondModelId).isMultiChoice'/>">
                                        <ul class="like_select"></ul>
                                    </div>
                                </td>
                                <td width="100">
                                    <span class="txt_grey"><s:if test='getModeTypeById(secondModelId).isMultiChoice=="Y"'>该属性可多选</s:if><s:elseif test='getModeTypeById(secondModelId).isMultiChoice=="2"'>该属性最多选2条</s:elseif><s:else>该属性只能有一个</s:else></span>
                                </td>
                                <td><span class="opt_btn pro_del" style="color:black;" secondModelId="<s:property value='secondModelId'/>">删除选中的属性</span></td>
                                --%>
                            </tr>
                            </tbody>
                        </table>
                        <div class="pro_type_td" secondModelId="<s:property value='secondModelId'/>">
                            <font class="<s:property value='secondModelId'/>">
                            </font>
                        </div>
                    </div>
                    <!--box-->
                </s:if>
            </s:iterator>

        </div>
    </s:if>
</div>
<!--main01 main02 end-->
<div>

</div>
<div id="div3">
    <h3>&nbsp;&nbsp;上车地点</h3>
	<form class="mySensitiveForm">
        <table class="table_list">
            <tr>
                <td  style="text-align: center"><strong>上车地点</strong></td>
            </tr>
            <s:iterator value="assemblyList" var="assembly">
                <tr>
                    <td  style="text-align: center"><s:property value="assemblyPoint"/><input type="hidden" name="assemblyPoint${productId}" value="<s:property value='assemblyPoint'/>" class="sensitiveVad" /></td>
                </tr>
            </s:iterator>
        </table>
     </form>
</div>
</body>
<script type="text/javascript">
function initDataBind() {
    var initDataStr = "<s:property value='initDataStr'/>";
    if (initDataStr != null && initDataStr != "") {
        var initDataArray = initDataStr.split("_");
        for (var n = 0; n < initDataArray.length; n++) {
            var data = initDataArray[n].split(",");
            var productId = $("#productId").val();
            var secondModelId = data[0];
            var id = data[1];
            var propertyName = data[2];
            var $propertyArray = $(".pro_type_td").find("." + secondModelId);
            var htmlSrc = "<font class='font" + id + "'><input type='checkbox'  value='" + id + "' name='modelPropertys'><label>" + propertyName + "</label></font>";
            $propertyArray.append(htmlSrc);
        }
    }
}
function initProperty1() {
    var initDataStr = "<s:property value='initPropertyOne'/>";
    if (initDataStr != null && initDataStr != "") {
        var initDataArray = initDataStr.split(",");
        for (var i = 0; i < initDataArray.length; i++) {
            $("#prodModelProperty" + initDataArray[i] + "").attr("checked", true);
            //$("#prodModelProperty"+initDataArray[i]+"").attr("checked","checked");
        }

    }
    var travelTime = "<s:property value='travelTime'/>";
    if (travelTime != null && travelTime != "") {
        if (travelTime == "AUTO_TIME_UPDATE") {
            $("#travelTimeSelect1").attr("checked", "true");
            $("#travelTimeValue").attr("disabled", "disabled");
        } else {
            $("#travelTimeSelect2").attr("checked", "true");
            $("#travelTimeValue").val(travelTime);
        }
    } else {
        var productType = "<s:property value='product.subProductType'/>";
        if (productType != null) {
            if (productType != "FREENESS") {
                $("#travelTimeSelect1").attr("checked", "true");
                $("#travelTimeValue").attr("disabled", "disabled");
            } else {
                $("#travelTimeSelect2").attr("checked", "true");
                $("#travelTimeValue").val("天天出发");

            }
        }

    }
}

function dataCheck() {

    var routeCateGory = $("input[name='routeCateGory']:checked").val();
    var routeStandard = $("input[name='routeStandard']:checked").val();
    if (routeCateGory == null || routeCateGory == "") {
        alert("请选择线路分类!");
        return false;
    }
    if (routeStandard == null || routeStandard == "") {
        alert("请选择线路标准!");
        return false;
    }

    var dataWrapStr = "";
    var num = parseInt($("#typeNum").val());
    if (num != 0) {
        for (var i = 1; i <= num; i++) {
            var dataStr = "";
            var choiceFlag = $("#choiceFlag" + i + "").val();
            if (choiceFlag == "") {
                var checkedValue = $("input[name='productModelProperty" + i + "']:checked").val();
                dataStr = "productModelProperty" + i + "_N_" + checkedValue + "";
            } else {
                var checkedValue = "";
                $("input[name='productModelProperty" + i + "']:checked").each(function () {
                    checkedValue += $(this).val() + "*";
                });
                if (checkedValue != null && checkedValue != "") {
                    checkedValue = checkedValue.substring(0, checkedValue.length - 1);
                }
                dataStr = "productModelProperty" + i + "_Y_" + checkedValue + "";
            }
            dataWrapStr += dataStr + ",";

        }

    }
    $("#dataStr").val(dataWrapStr);
    var travel = $("input[name='travelTimeSelect']:checked").val();
    if (travel == null || travel == "") {
        alert("请选择出发班期!");
        return false;
    }
    if (travel == "CUSTOMER_TIME") {
        $("#travelTime").val($("#travelTimeValue").val());
    } else {
        $("#travelTime").val(travel);
    }
    return true;
}


$(function () {
    initDataBind();
    initProperty1();


    $("#travelTimeSelect1").live("click", function () {
        $("#travelTimeValue").attr("disabled", "disabled");
    })
    $("#travelTimeSelect2").live("click", function () {
        $("#travelTimeValue").attr("disabled", "");
    })


    /**
     * 搜索按钮事件
     */
    $(".opt_btn_search").click(function () {
        var $likeSelectObj = $(this).siblings(".like_select");

        // 若是显示则隐藏
        if ($likeSelectObj.css("display") == "block") {
            $likeSelectObj.hide();
            return;
        }
        $(".like_select").hide();

        var fid = $(this).attr("firstModelId");
        var sid = $(this).attr("secondModelId");
        var property = $(this).siblings(".newtext1").val();
        if (property == "空搜索为查看全部")
            property = "";

        // 显示搜索内容
        var liValue = searchModelProperty(fid, sid, property);
        $likeSelectObj.empty();
        $likeSelectObj.html(liValue);
        $likeSelectObj.show();
    });

    /**
     * 增加按钮事件
     */
    $(".opt_btn_add").live("click", function () {
        var $likeSelectObj = $(this).siblings(".like_select");
        var id = $likeSelectObj.find(".like_select_crt").attr("pid");
        var property = $likeSelectObj.find(".like_select_crt").html();
        var secondModelId = $(this).attr("secondModelId");
        var $propertyArray = $(".pro_type_td").find("." + secondModelId);

        if (property == null || property == "") {
            alert("请选择要添加属性！");
            return;
        }

        var isMultiChoice = $(this).attr("isMultiChoice");
        if (isMultiChoice == "N") {
            if ($propertyArray.find("font").length > 0) {
                alert("不能多选！");
                $likeSelectObj.hide();
                return;
            }
        } else if (isMultiChoice == "2") {
            if (secondModelId == "14") {
                if ($propertyArray.find("input").length > 1) {
                    alert("该属性最多选2条！");
                    $likeSelectObj.hide();
                    return;
                }
            }
        }


        var fontId = $propertyArray.find(".font" + id);
        if (fontId.length != 0) {
            alert("不能重复添加，请重新选择！");
            return;
        } else {
            var htmlSrc = "<font class='font" + id + "'><input type='checkbox' value='" + id + "' id='id" + id + "' name='modelPropertys'><label>" + property + "</label></font>"
            $propertyArray.append(htmlSrc);
            //$("#id"+id).attr("checked",'true');
            $likeSelectObj.hide();
        }
    });

    /**
     * 点击弹出框的属性事件
     */
    $(".like_select li").live("click", function () {
        $(this).addClass("like_select_crt").siblings().removeClass("like_select_crt");
    });

    /**
     * 点击删除操作
     */
    $(".pro_del").live("click", function () {
        var secondModelId = $(this).attr("secondModelId");
        var flag = true;
        $("." + secondModelId).find("input").each(function () {
            if ($(this).attr("checked")) {
                flag = false;
                var pid = $(this).val();
                $(".font" + pid).remove();
            }
        })
        if (flag) {
            alert("请选择要删除的属性！")
            return;
        }
    });

    /**
     * 搜索模块属性
     */
    function searchModelProperty(firstModelId, secondModelId, property) {
        var liValue = "";
        var propertyName = "";
        <s:iterator value="modelPropertyList">
        propertyName = "<s:property value='property'/>";
        if (firstModelId == "<s:property value='firstModelId'/>" && secondModelId == "<s:property value='secondModelId'/>" && (property == "" || propertyName.lastIndexOf(property) != -1) && "Y" == "<s:property value='isValid'/>") {
            liValue += "<li pid='<s:property value='id'/>'><s:property value='property'/></li> "
        }
        </s:iterator>
        return liValue;
    }

    $("#other2_save").live("click", function () {
        var dataStr = "";
        $("div").find(".pro_type_td").each(function () {
            var lineData = "";
            var attrData = $(this).attr("secondModelId");
            /*lineData="*"+attrData;*/
            var flag = false;
            $(this).find("." + attrData).each(function () {
                $(this).find("input[type='checkbox']").each(function () {
                    flag = true;
                    lineData += $(this).val() + ",";
                });
            });
            dataStr += lineData;
        });
        if (dataStr != "" && dataStr != null) {
            dataStr = dataStr.substring(0, dataStr.length - 1);
        }
        var productId = $("#productId").val();
        var modelProData = productId + "*" + dataStr;
        $.ajax({
            type: "post",
            url: "<%=basePath%>prod/saveProductOther.do",
            data: "modelProData=" + modelProData + "&btnType=2",
            success: function (data) {
                if (data == "OK") {
                    alert("处理成功!");
                    window.location.href = "<%=basePath%>prod/toProductOther.do?productId=" + productId;
                }
            },
            error: function (er) {
                alert("与服务器交互出现错误!请稍后再试!" + er);
            }
        });
    })


})
</script>
</html>
