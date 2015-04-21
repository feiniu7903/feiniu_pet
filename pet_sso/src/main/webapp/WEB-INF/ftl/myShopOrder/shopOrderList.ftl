<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" /> 
<title>无标题文档</title>
<script type="text/javascript" src="http://pic.lvmama.com/js/jquery142.js"></script>
<link href="http://www.lvmama.com/style/myspace.css" type="text/css" rel="stylesheet" />
</head>

<body>
        <div class="content contentDh">
    	<div class="contentJFTop"><h3 class="tit">兑换记录</h3></div>
        <div class="textTop">
        	<ul>
				<li><a href="../point/myPoint.do">我的积分</a></li>
				<li class="on"><a href="../shop/orderList.do">兑换记录</a></li>
				<li><a href="../point/myPointDescription.do">积分说明</a></li>
            </ul>
        </div>
    	<div class="jifen01">
        	<div class="jifen01Left">
            	<dl>
                	<dt><p>当前可用积分</p><p>年末到期积分</p></dt>
                    <dd><p><@s.property value="currentPoint"/></p><p><@s.property value="aboutToExpiredPoint"/></p></dd>
                    <dd class="jifen01Left02"><a href="javascript:void(0)" onClick="window.parent.location.href= 'http://www.lvmama.com/points'" class="jifen01Left02New">积分商城</a><a href="../point/myPointDescription.do">积分规则说明</a></dd>
                </dl>
            </div>            
        	<div class="jifen01Right">
            	<dl>
                	<dt>积分转告通知</dt>
                    <dd>驴妈妈会员积分系统于2011.11.22日全面升级，<a href="http://www.lvmama.com/points" target="_blank">积分商城</a>已同步推出。新的积分系统将取代现有的积分及驴币系统。您的驴币将按照1:100比例转入新积分，即1个驴币=100个新积分；老积分与等级被取消。获取积分方式请参考<a href="http://www.lvmama.com/points/help" target="_blank">积分商城帮助中心</a>。感谢您对驴妈妈一如既往的支持。 </dd>
                </dl>
            </div>
        </div>
        <div class="jifenText01">
        	<div class="jifenText01Top">
        	    <form action="/nsso/shop/orderList.do" method="post">
	                <select name="productType" onchange="this.form.submit();">
	                        <option ${(productType=='')?string('selected','')} value="">全部</option>
	                        <option ${(productType=='COUPON')?string('selected','')} value="COUPON">优惠券</option>
	                        <option ${(productType=='PRODUCT')?string('selected','')} value="PRODUCT">实物</option>
	            	</select>
            	</form>
                <strong>选择查看</strong>
            	<span>兑换记录</span>
            </div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="tit">
                    <td width="120">订单编号</td>
                    <td>物品名称</td>
                    <td width="120">消耗积分</td>
                    <td width="120">兑换时间</td>
                    <td width="100">订单状态</td>
                    <td width="80">操作</td>
                  </tr>
				  <@s.iterator id="orderListId" value="shopOrderList" status="index">
                  <tr>
                  	<td><@s.property value="orderId" /></td>
                    <td><@s.property value="productName"/></td>
                    <td><@s.property value="actualPay"/></td>
                    <td><@s.property value="createTime"/></td>
                    <td><@s.property value="formatOrderStatus"/></td>
                    <td><a href="/nsso/shop/orderDetail.do?orderId=<@s.property value='orderId' />">查看</td>
                  </tr> 
				  </@s.iterator>                 
                </table>
						<div class="page_order">
						<@s.property escape="false" value="@com.lvmama.common.utils.Pagination@pagination(pageConfig.pageSize,pageConfig.totalPageNum,pageConfig.url,pageConfig.currentPage)"/>
						</div>
        </div>
</div>
</body>
</html>
