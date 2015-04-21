<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<#-- Jquery dependencies-->
<script type="text/javascript" src="${basePath}js/base/jquery-1.7.2.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery-ui-1.8.5.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.datepick-zh-CN.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.jsonSuggest-2.min.js"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.validate.js"></script>
<link rel="stylesheet" href="${basePath}css/jquery-ui-1.8.18.custom.css">

<#-- JqGrid dependencies-->
<script type="text/javascript" src="${basePath}js/base/common.js"></script>
<script type="text/javascript" src="${basePath}js/base/jqGrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${basePath}js/base/jqGrid/js/jquery.jqGrid.src.js"></script>
<link rel="stylesheet" href="${basePath}js/base/jqGrid/css/ui.jqgrid.css">
<link rel="stylesheet" href="${basePath}js/base/jqGrid/css/ui.multiselect.css">

<#-- jquery artDialog dependencies-->
<script type="text/javascript" src="${basePath}js/base/artDialog/artDialog.min.js"></script> 
<script type="text/javascript" src="${basePath}js/base/artDialog/artDialog.plugins.min.js"></script>
<link href="${basePath}js/base/artDialog/skins/blue.css" rel="stylesheet" />

<#-- Calendar control dependencies-->
<script type="text/javascript" src="${basePath}js/base/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}js/base/My97DatePicker/lang/en.js"></script>
<link rel="stylesheet" href="${basePath}js/base/My97DatePicker/skin/default/datepicker.css">
<link rel="stylesheet" href="${basePath}js/base/My97DatePicker/skin/WdatePicker.css">

<#-- Autocomplete dependencies-->
<link rel="stylesheet" href="${basePath}js/base/autocomplete/jquery.ui.autocomplete.css">
<script src="${basePath}js/base/autocomplete/jquery.ui.core.js"></script>
<script src="${basePath}js/base/autocomplete/jquery.ui.widget.js"></script>
<script src="${basePath}js/base/autocomplete/jquery.ui.position.js"></script>
<script src="${basePath}js/base/autocomplete/jquery.ui.autocomplete.js"></script>
<#-- Global style-->
<link rel="stylesheet" href="${basePath}css/finance.css"/>

<#if grid_row_auto_height?? && grid_row_auto_height>
<style>
.ui-jqgrid tr.jqgrow td {
  white-space: normal !important;
  height:auto;
  vertical-align:text-top;
  padding-top:2px;
 }
</style>
</#if>
<#setting number_format="#.##"> 
