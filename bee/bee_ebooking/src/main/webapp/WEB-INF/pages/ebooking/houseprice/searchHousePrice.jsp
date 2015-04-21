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
<link rel="stylesheet" href="${contextPath}/css/base/select2/select2.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="${contextPath}/js/base/select2.min.js"></script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>

<script type="text/javascript">
$(function(){
	$("#searchHousePriceForm #productBranch").select2();
	$("#searchHousePriceForm #productBranch").val("${ebkHousePrice.metaBranchId}");
	$("#yearSelectId").val("${year}");
	$("#monthSelectId").val("${month}");
	
});

</script>
</head>
<body id="body_fjwh">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>变价申请</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/fangjia-guide.ppt" class="ppt_xz">变价维护操作PPT下载</a>
</div>
<!--以上是公用部分-->
<!--订单处理开始-->
<dl class="order_nav">
	<dt>变价申请</dt>
   <dd ><a href="${basePath}ebooking/houseprice/changePriceSuggest.do">变价申请</a></dd>
    <dd class="order_nav_dd"><a href="javascript:void(0)">价格查询</a></dd>
    <dd><a href="${basePath}ebooking/houseprice/submitedApply.do">已提交申请</a></dd>
</dl>
<form id="searchHousePriceForm" action="${basePath}ebooking/houseprice/searchHousePrice.do" method="post">
	<ul class="order_all">
    <li class="order_all_li">
    	<div class="order_list">
    	<dl>
        	<dt class="width5"></dt>
            <dd>
            	<ul class="search_ul_t">
                    <li>
                    	<label>选择产品：
							<select id="productBranch" class="width_auto" name="ebkHousePrice.metaBranchId">
								<option value="">全部产品</option>
								<s:iterator var="pb" value="productBranchList" > 
									<s:if test="productType == 'ROUTE'">
									<option value="${pb.metaBranchId }" productType="${pb.productType }">${pb.metaProductName } - ${pb.branchName }(${pb.metaBranchId })</option>
									</s:if>
									<s:else>
									<option value="${pb.metaBranchId }" productType="${pb.productType }">${pb.branchName }(${pb.metaBranchId })</option>
									</s:else>
								</s:iterator>
							</select> 								
                        </label>
                    </li>
                    <li>
                    	<label>年份：
                        	<select class="width_auto" id="yearSelectId" name="year">
                        		<s:iterator begin="yearNow" end="yearNow+10">
                            	<option value="<s:property/>"><s:property/></option>
                        		</s:iterator>
                            </select>
                        </label>
                    </li>
                    <li class="search_ul_b_5">
                    	<label>月份：
                        	<select id="monthSelectId" name="month">
                            	<option value="0">1</option>
                                <option value="1">2</option>
                                <option value="2">3</option>
                                <option value="3">4</option>
                                <option value="4">5</option>
                                <option value="5">6</option>
                                <option value="6">7</option>
                                <option value="7">8</option>
                                <option value="8">9</option>
                                <option value="9">10</option>
                                <option value="10">11</option>
                                <option value="11">12</option>
                            </select>
                        </label>
                    </li>
                    <li class="search_ul_b_but"><span onclick="$('#searchHousePriceForm').submit();">查询</span></li>
                </ul>
            </dd>
        </dl>
    </div>
    </li>
</ul>
</form>

<!--价格表-->
 <div class="housePrices_cx">
	<p class="explain"><b>说明：</b>1.灰色代表价格已过期，空白表示当天无价格； 2.显示格式：<span class="c_gray">门市价</span> / <span class="orange">建议售价</span> / <span class="gray5">结算价</span> / <span class="green">早餐份数</span>。</p>
 <s:iterator value="branchNameCalendarModelList" var="item"  >
    <jsp:include page="./housePriceTable.jsp"></jsp:include>
 </s:iterator>
</div>
 
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>
