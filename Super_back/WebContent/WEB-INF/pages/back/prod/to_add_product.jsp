<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>super后台——销售产品列表</title>
<link href="<%=basePath%>style/houtai.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/base/jquery-1.4.4.min.js"></script>
</head>
 
<body>
<div class="main main06">
	<div class="row1">
    	<h3 class="newTit">产品列表<a href="#">添加新产品</a></h3>
         <jsp:include page="/WEB-INF/pages/back/prod/product_menu.jsp"></jsp:include>
  </div><!--row1 end-->
  <div class="row2">    
        <ul class="row2Top">
        	<li><label>产品名称：</label><input type="text" class="text1" name="text1"><label>产品编号：</label><input type="text" class="text1" name="text1"></li>
        	<li><label>提前预订小时数：</label><input type="text" class="text1" name="text1"><input name="checkbox" type="checkbox" value="游客姓名" class="checkbox checkbox2" /><span>仅能捆绑销售</span></li>
        	<li><label>所属公司：</label><input type="text" class="text1" name="text1"><label>产品经理：</label><input type="text" class="text1" name="text1"></li>
        	<li>
            	<label>我方结算主体：</label>
                <select>
                  <option selected="selected">上海景域集团有限公司</option>
                  <option>上海驴妈妈国际旅游社</option>
                </select>
            </li>
        	<li><label>产品名称：</label><input name="checkbox" type="checkbox" value="游客姓名" class="checkbox" /><span>游客姓名</span><input name="checkbox" type="checkbox" value="游客姓名" class="checkbox" /><span>游客手机</span><input name="checkbox" type="checkbox" value="游客姓名" class="checkbox" /><span>游客证件号</span></li>
            <li><label>上下线时间：</label><input type="text" class="text1" name="text1"><i>-</i><input type="text" class="text1" name="text1"></li>
        	<li><label>是否发送短信：</label><input name="radio" class="radio01" type="radio" /><span>是</span><input name="radio" class="radio01" type="radio" /><span>否</span></li>
            <li><label>短信内容：</label><textarea name="text2" cols="text2" rows="text2" class="text2"></textarea></li>       
            <li class="search">
            	<label>标的：</label> <input name="text" type="text" class="text1" /><em class="button">新增</em><br /> <br /> 
                <table width="80%" border="0" cellspacing="0" cellpadding="0">
                  <tr class="tableTit">
                    <td>编号</td>
                    <td>名称</td>
                    <td>支付信息</td>
                    <td>履行信息</td>
                    <td>操作</td>
                  </tr>
                  <tr>
                    <td>ADJHSDF</td>
                    <td>上海欢乐谷</td>
                    <td>在线</td>
                    <td>支付金额</td>
                    <td><a href="#">删除</a><a href="#">目的地</a><a href="#">出发地</a></td>
                  </tr>
                </table>            	
            </li>
        </ul>
        <ul class="row2Top row2Top2">
        	<li><h3 class="titBotton">其他属性</h3> </li>
        	<li><label>是否可以使用优惠券：</label><input name="radio" class="radio01" type="radio" /><span>是</span><input name="radio" class="radio01" type="radio" /><span>否</span></li>
            <li><label>&nbsp;</label><input name="checkbox" type="checkbox" value="实体票" class="checkbox" /><span>实体票</span></li>
        	<li><label>销售渠道：</label><input name="checkbox" type="checkbox" value="游客姓名" class="checkbox" /><span>驴妈妈前台</span><input name="checkbox" type="checkbox" value="游客姓名" class="checkbox" /><span>驴妈妈购物</span></li>
        </ul>
        <div class="rowpro">       
            <ul class="row2Top">
                <li><label>产品名称：</label><input type="text" class="text1" name="text1"><label>产品编号：</label><input type="text" class="text1" name="text1"></li>
                <li><label>所属公司：</label><input type="text" class="text1" name="text1"><label>产品经理：</label><input type="text" class="text1" name="text1"></li>
                <li>
                    <label>我方结算主体：</label>
                    <select>
                      <option selected="selected">上海景域集团有限公司</option>
                      <option>上海驴妈妈国际旅游社</option>
                    </select>
                </li>   
            </ul>
    	    <h3 class="titBotton">关联对象</h3>  	
            <dl>
                <dt>
                    <label>履行对象</label>
                    <select>
                      <option selected="selected">上海景域集团有限公司</option>
                      <option>上海驴妈妈国际旅游社</option>
                    </select>
                    <em class="button">选择</em>
              </dt>
                <dd>            	
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="tableTit">
                        <td>编号</td>
                        <td>名称</td>
                        <td>支付信息</td>
                        <td>履行信息</td>
                        <td>操作</td>
                      </tr>
                      <tr>
                        <td>ADJHSDF</td>
                        <td>上海欢乐谷</td>
                        <td>在线</td>
                        <td>支付金额</td>
                        <td><a href="#">删除</a></td>
                      </tr>
                    </table>
                </dd>
            </dl>
            <dl>
                <dt>
                    <label>履行对象</label>
                    <select>
                      <option selected="selected">上海景域集团有限公司</option>
                      <option>上海驴妈妈国际旅游社</option>
                    </select>
                    <em class="button">选择</em>
              </dt>
                <dd>            	
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="tableTit">
                        <td>编号</td>
                        <td>名称</td>
                        <td>支付信息</td>
                        <td>履行信息</td>
                        <td>操作</td>
                      </tr>
                      <tr>
                        <td>ADJHSDF</td>
                        <td>上海欢乐谷</td>
                        <td>在线</td>
                        <td>支付金额</td>
                        <td><a href="#">删除</a></td>
                      </tr>
                    </table>
                </dd>
            </dl>
            <dl>
                <dt>
                    <label>履行对象</label>
                    <select>
                      <option selected="selected">上海景域集团有限公司</option>
                      <option>上海驴妈妈国际旅游社</option>
                    </select>
                    <em class="button">选择</em>
              </dt>
                <dd>            	
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr class="tableTit">
                        <td>编号</td>
                        <td>名称</td>
                        <td>支付信息</td>
                        <td>履行信息</td>
                        <td>操作</td>
                      </tr>
                      <tr>
                        <td>ADJHSDF</td>
                        <td>上海欢乐谷</td>
                        <td>在线</td>
                        <td>支付金额</td>
                        <td><a href="#">删除</a></td>
                      </tr>
                    </table>
                </dd>
            </dl>
        </div><!--rowpro end-->
        <div class="rowpro rowpro02">      
    	    <h3 class="titBotton">其他属性</h3>  	
            <ul>
            	<li><label>支付对象：</label><input name="radio" class="radio01" type="radio" /><span>支付给供应商</span>
                <input name="radio" class="radio01" type="radio" /><span>支付给驴妈妈</span></li>
            	<li><label>电子通关码有效天数：</label><input type="text" class="text1" name="text1"></li>
            	<li><label>电子通过终端显示内容：</label><textarea name="text2" cols="text2" rows="text2" class="text2"></textarea></li>
            </ul>          
        </div><!--rowpro end-->        
        <p class="main4Bottom"><em class="button">保存</em><em class="button">修改</em></p> 
  </div><!--row2 end-->
</div><!--main01 main04 end-->
</body>
</html>



