<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<link rel="stylesheet" href="${basePath}/css/base/jquery.jsonSuggest.css"/>
<link rel="stylesheet" href="http://pic.lvmama.com/min/index.php?f=/styles/new_v/ob_common/ui-components.css,/styles/v4/modules/tip.css,/styles/v4/modules/dialog.css,/styles/v4/modules/button.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/base/jquery.cropzoom.css" />
<style type="text/css">
.jsonSuggest{position: absolute; background: #efefef; border: #ddd solid 1px;}
.jsonSuggest li{ padding:4px 0; display:block;}
#dateAllInfoDiv{border:1px solid #cccccc; background:#FFFFEE;padding:5px; display:none; position:absolute; z-index:999;} 

.time_box .plug_calendar_t li{ background:#f0f7ff;}
.plug_calendar_d .day_t td{ padding:0 5px 0 0; height:16px; background:#f0f7ff; color:#666;}
.plug_calendar_d_box table{ width:100%;}
.plug_calendar_d .align_r{ text-align:right; padding-right:5px;}
.time_table_new .time_box .plug_calendar_d li{ height:132px;}
.time_table_new .plug_calendar_d li .c_gray{ color:#00bb00;}
.time_table_new .plug_calendar_d li.plug_calendar_full .c_gray{}
#dateAllInfoDiv .c_gray{ color:#00bb00; text-align:right;}
#dateAllInfoDiv td{ color:#666;}
.time_table_new .plug_calendar_d li td{ color:#666;}

.time_table_new .plug_calendar_d li.weitijiao .c_gray,#dateAllInfoDiv .weitijiao .c_gray{ color:#ff4040;} 
.time_table_new .plug_calendar_d li.daishenhe .c_gray,#dateAllInfoDiv .daishenhe .c_gray{ color:#ff8800;} 
.time_table_new .plug_calendar_d li.jujue .c_gray,#dateAllInfoDiv .jujue .c_gray{ color:#cc66cc;}

.tishi_text{ padding:10px 20px 0 30px; height:30px; line-height:30px;} 
.tishi_text p{ float:right; color:#666; line-height:30px;} 
.tishi_text p span{ margin-right:10px;} 
.orange2{ color:#ff4040;} 
.green{ color:#00bb00;} 
.chengse{ color:#ff8800;} 
.zise{ color:#cc66cc;} 
.jibenxinxi #teshuRadioY{width:20px;height:20px;marign-left:10px;}
.jibenxinxi #teshuRadioN{width:20px;height:20px;marign-left:10px;}
</style>

<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<!--jquery.jsonSuggest.min.js一定要在 jquery1.8.js文件下引用 否则会报错 -->
<script type="text/javascript" src="${basePath}/js/base/jquery.jsonSuggest.min.js"></script>
<script src="${basePath}/js/base/jquery.form.js"></script>
<script src="${basePath}/js/base/select2.min.js"></script>
<script src="${basePath}/js/base/jquery.validate.js"></script>
<script src="http://pic.lvmama.com/js/v4/modules/pandora-dialog.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.easyui.min-1.3.1.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/notice.js"></script>
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js" charset="utf-8"></script>
<script type="text/javascript" src="${basePath}js/base/jquery.cropzoom.js"></script>
<script type="text/javascript" src="${basePath}/kindeditor-3.5.1/kindeditor.js"></script>

<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/ebkProduct.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/ebkProdRelation.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/ebkProdBranch.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/query_date_util.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/editEbkProdProductInfo.js"></script>
