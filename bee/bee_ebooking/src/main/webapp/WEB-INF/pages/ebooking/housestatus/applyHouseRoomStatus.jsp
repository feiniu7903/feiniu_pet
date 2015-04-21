<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<head>
<meta charset="utf-8" />
<title>驴妈妈供应商管理系统</title>
<link rel="stylesheet" href="http://pic.lvmama.com/styles/new_v/reset.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/msg_ord_snspt.css">
<link rel="stylesheet" href="http://pic.lvmama.com/styles/global_backpop.css">
<link rel="stylesheet" media="all" href="http://pic.lvmama.com/styles/ebooking/base.css">
<script src="http://pic.lvmama.com/js/jquery1.8.js"></script>
<script src="${contextPath}/js/base/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}/js/base/jquery.validate.js"></script>
<script type="text/javascript">
var basePath = "${basePath}";
var nowDate = "<s:date name='createTimeBegin' format='yyyy-MM-dd'/>";
</script>
<script src="http://pic.lvmama.com/js/ebooking/ebooking.js"></script>
<script type="text/javascript" src="${basePath}/js/ebooking/applyHouseRoomStatus.js"></script>
<style type="text/css">
.baoliu {
	width: 120px;
	color: #555;
}
</style>
</head>
<body id="body_ftwh" class="ebooking_house">
<jsp:include page="../../common/head.jsp"></jsp:include>
<div class="breadcrumb">
	<ul class="breadcrumb_nav">
		<li class="home">首页</li>
    	<li>库存维护</li>    	
    	<li>酒店房态维护</li>
    </ul>
    <a href="http://www.lvmama.com/zt/ppt/ebk/fangtai-guide.ppt" class="ppt_xz">库存维护操作PPT下载</a>
</div>
<!--以上是公用部分-->
<!--订单处理开始-->
<dl class="order_nav">
	<dt>库存维护</dt>
</dl>
<form id="changeHouseRootStatusForm" method="post">
<div class="roomStatus">
    <dl>
        <dt>查询房态：</dt>
        <dd>
           	 <ul class="roomStatus_list">
                <li class="search_ul_b_3">
                    <strong>选择日期：</strong>
                    <p><input id="Calendar71" type="text" name="ebkHouseStatus.beginDate" value="">
                    ~<input id="Calendar81" type="text" name="ebkHouseStatus.endDate"  ></p>
                    <span>&nbsp;&nbsp;查询时间区间不超过2个月</span>
                </li>
            	<li>
                <strong>选择房型：</strong>
                <p class="roomStatus_fx">
                	<label for="chamber_all"><input type="checkbox" value="" name="" id="chamber_all">全部产品</label>
            		<s:iterator value="productBranchList" var="bran">
            			<label for="chamber_${metaBranchId }"><input type="checkbox" value="${metaBranchId }" name="metaProductBranchId" id="chamber_${metaBranchId }">${branchName }</label>
            		</s:iterator>
                </p>
            	</li>
                <li>
                 	<input class="roomStatus_cx" type="button" value="查询" onclick="reSerachHousePriceTable()"/>
                </li>
            </ul>
        </dd>
    </dl>
</div>
<div class="roomStatus roomStatus_b">
	<dl>
		<dt class="width90"></dt>
		<dd>
			<ul class="roomStatus_list">
				<li><strong>星期：</strong>
					<p>
						<label for="week_all"><input id="week_all" name="week_all" type="checkbox" value="">全部</label> 
						<label for="week_1"><input id="week_1" name="ebkHouseStatus.applyWeek" type="checkbox" value="一">星期一</label>
						<label for="week_2"><input id="week_2" name="ebkHouseStatus.applyWeek" type="checkbox" value="二">星期二</label>
						<label for="week_3"><input id="week_3" name="ebkHouseStatus.applyWeek" type="checkbox" value="三">星期三</label>
						<label for="week_4"><input id="week_4" name="ebkHouseStatus.applyWeek" type="checkbox" value="四">星期四</label>
						<label for="week_5"><input id="week_5" name="ebkHouseStatus.applyWeek" type="checkbox" value="五">星期五</label>
						<label for="week_6"><input id="week_6" name="ebkHouseStatus.applyWeek" type="checkbox" value="六">星期六</label>
						<label for="week_7"><input id="week_7" name="ebkHouseStatus.applyWeek" type="checkbox" value="七">星期日</label>
					</p></li>
				<li><strong>增减保留房：</strong>
					<p>
						<label style="margin-right: 17px;"><input name="ebkHouseStatus.baoliu" id="baoliuAdd" type="radio" value="true" class="baoliu_add" checked>增加</label> 
						<label style="margin-left: 20px;"><input name="ebkHouseStatus.baoliu" id="baoliuReduce" type="radio" value="false">减少</label>
						<input id="baoliuQuantityInput" type="hidden" name="ebkHouseStatus.baoliuQuantity" value="0"/>
						<input type="text" name="quantity" id="baoliuQuantityId" value="输入增加的数量" class="baoliu" onFocus="if (value =='输入增加的数量' || value =='输入减少的数量'){value =''}" onBlur="if (value ==''){value='输入增加的数量' || value =='输入减少的数量'}" />(为空或非数字不做修改)
					</p></li>
				<li><strong>是否超卖：</strong>
					<p>
						<label style="margin-right: 49px;"><input name="ebkHouseStatus.chaomai" id="chaomaiTrueId" type="radio" value="true" checked>是</label> 
						<label><input name="ebkHouseStatus.chaomai" id="chaomaiFalseId" type="radio" value="false">否</label>
						<span style="color:red;">&nbsp;&nbsp;&nbsp;超卖选择“是”代表当天保留房售完后订单自动变为需确认房源；选择“否”代表当天保留房售完后自动关房。</span>	
					</p></li>
				<li><strong>是否满房：</strong>
					<p>
						<label><input name="ebkHouseStatus.manfang" id="manfangFalseId" type="radio" value="MAN_FANG_FALSE" checked>非满房</label> 
						<label><input name="ebkHouseStatus.manfang" id="manfangTrueId" type="radio" value="MAN_FANG_TRUE">满房</label>
					</p></li>
				<li>
				<input  class="roomStatus_xg"  type="button" value="保存修改" onclick="checkAndSubmit($('#changeHouseRootStatusForm'));"/>
				</li>
			</ul>
		</dd>
	</dl>
</div>
</form>
<!-- 时间价格表 -->
<div id="timePriceDiv">
</div>
<!--公用底部-->
<script type="text/javascript" src="http://pic.lvmama.com/js/ui/lvmamaUI/lvmamaUI.js"  charset="utf-8"></script>
<jsp:include page="../../common/footer.jsp"></jsp:include>
</body>
</html>