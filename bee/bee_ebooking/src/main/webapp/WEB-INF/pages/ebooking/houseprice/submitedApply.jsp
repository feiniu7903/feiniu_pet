<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery142.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript">
$(function(){
 
	$(".search_ul_t").ui("calendar", {
		input : "#Calendar7",
		parm : {
			dateFmt : "yyyy-MM-dd",
			maxDate : "#F{$dp.$D('Calendar8')}"
		}
	});
	$(".search_ul_t").ui("calendar", {
		input : "#Calendar8",
		parm : {
			dateFmt : 'yyyy-MM-dd',
			minDate : "#F{$dp.$D('Calendar7')}"
		}
	});
});
function showDetail(id){
	$("#housePriceDetailWin").load("housePriceApplyDetail.do",{housePriceId:id},
			function(data){
				tan_show1();
			}
	);
}
function tan_show1(){
	var index = $('.rizhi_show1').index(this);
	var _hight_w =$(window).height();
	var _hight_t =$('.eject_rz').eq(index).height();
	var _hight =_hight_w - _hight_t;
	var _top = $(window).scrollTop()+_hight/2;
	var height_w =$(document).height();
	$('.eject_rz').css({'top':_top}).show();
	$('.bg_opacity2').css({'height':_hight_t+31,'top':_top-5}).show();
	$('.bg_opacity1').css({'height':height_w,'width':$(document.body).width()}).show();

	$('.close').click(function(){
		$('.show_hide').hide();
		}
	); 
} 
</script>
</head>
<body id="body_fjwh">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>变价申请</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/fangjia-guide.ppt" class="ppt_xz">变价申请操作PPT下载</a>
</div>
<!--以上是公用部分-->
<!--订单处理开始-->
<dl class="order_nav">
	<dt>变价申请</dt>
   <dd ><a href="${basePath}ebooking/houseprice/changePriceSuggest.do">变价申请</a></dd>
    <dd><a href="${basePath}ebooking/houseprice/searchHousePrice.do">价格查询</a></dd>
    <dd class="order_nav_dd"><a href="javascript:void(0)">已提交申请</a></dd>
</dl>
<form id="submitedApplyForm" action="${basePath}ebooking/houseprice/submitedApply.do" method="post">
<ul class="order_all">
    <li class="order_all_li">
    	<div class="order_list">
    	<dl>
        	<dt>查找申请：</dt>
            <dd>
            	<ul class="search_ul_t">
                    <li class="search_ul_b_3">
                    	<label>提交日期：<input id="Calendar7" type="text" name="createTimeBegin" >
                    	~</label><input id="Calendar8" type="text" name="createTimeEnd"  >
                    </li>
                    <li>
                    	<label>申请状态：
                        	<s:select list="suggestAuditStatusList" id="suggestAduitStatusId"
						name="ebkHousePrice.status" listKey="code" listValue="name" ></s:select>
                        </label>
                    </li>
                    <li class="search_ul_b_but"><span onclick="$('#submitedApplyForm').submit();">查询</span></li> 
                </ul>
            </dd>
        </dl>
    	</div>
    </li>
</ul>
</form>
<!--申请列表-->
<div class="tableWrap tableWrap_top0">
	<div class="table01Header">申请列表</div>
	<table width="960" border="0" class="table01">
    	<tr>
      	  <th class="tab_td1">申请单号</th>
   		  <th class="tab_td2">申请主题</th>
   		  <th width="160">提交时间</th>
   		  <th width="80">提交人</th>
   		  <th width="100">状态</th>
   		  <th width="108">操作</th>
   		</tr>
   		
   		<s:iterator value="ebkHousePricePage.items" var="item">
    	<tr>
      	  <td class="tab_td1"><s:property value="housePriceId" /></td>
   		  <td class="tab_td2"><s:property value="subject"/></td>
   		  <td><s:date name="createTime" format="yyyy-MM-dd"/></td>
   		  <td><s:property value="submitUser" /></td>
   		  <td><span class="<s:property value='auditStatus.color'/>"><s:property value='auditStatus.cnName'/></span></td>
   		  <td>
   		  	<a class="rizhi_show1" href="javascript:void(0);" onclick="showDetail(${item.housePriceId })">查看</a>
   		  	<s:if test="auditStatus.deleteOrCancel">| 
   		  		<a href="${basePath}ebooking/houseprice/cancelChangePriceSuggest.do?ebkHousePrice.housePriceId=<s:property value='housePriceId' />">
   		  			撤销</a>
   		  	</s:if>
   		  	
   		  </td>
   		</tr>    
   		</s:iterator>    
    </table>
<div style="width: 958px;margin: 0 auto;">
	<s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pageSupplier(ebkHousePricePage,'')"/>
</div>
</div>
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
<div class="bg_opacity1 show_hide"></div>
			<iframe class="bg_opacity2 show_hide"></iframe>
			<div id="housePriceDetailWin" class="eject_rz show_hide" style="text-align:left">
			</div>
</body>
</html>
