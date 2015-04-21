select '"'||
'产品类型'||'","'||
'产品子类型'||'","'||
'订单号'||'","'||
'子订单号'||'","'||
'订单来源渠道'||'","'||
'创建时间'||'","'||
'首处理时间'||'","'||
'游玩时间'||'","'||
'传真时间'||'","'||
'订单状态'||'","'||
'支付状态'||'","'||
'支付时间'||'","'||
'支付渠道'||'","'||
'预授权状态'||'","'||
'pos机商户名称'||'","'||
'内部交易号'||'","'||
'外部交易号'||'","'||
'对账流水号'||'","'||
'结算周期'||'","'||
'销售产品名称'||'","'||
'销售产品ID '||'","'||
'采产品名称'||'","'||
'采购产品ID'||'","'||
'凭证对象名称'||'","'||
'结算对象名称'||'","'||
'供应商名称'||'","'||
'供应商ID'||'","'||
'我方结算公司'||'","'||
'所属分公司'||'","'||
'订购数量'||'","'||
'采购数量'||'","'||
'成人数量'||'","'||
'儿童数量'||'","'||
'订单产品金额'||'","'||
'订单优惠金额'||'","'||
'订单金额调整'||'","'||
'订单应收金额'||'","'||
'子订单销售价'||'","'||
'子订单金额'||'","'||
'子订单结算总价'||'","'||
'采购产品实收金额'||'","'||
'实收总金额'||'","'||
'结算单价'||'","'||
'结算总价'||'","'||
'结算费用变化'||'","'||
'实际结算单价'||'","'||
'实际结算总价'||'","'||
'退款金额'||'","'||
'退款渠道'||'","'||
'退款日期'||'","'||
'补偿金额'||'","'||
'补偿渠道'||'","'||
'剩余金额'||'","'||
'支付对象'||'","'||
'预订方式'||'","'||
'用户名'||'","'||
'订购次数'||'","'||
'终审操作人'||'","'||
'信息审核人'||'","'||
'被分单人'||'","'||
'资源审核人'||'","'||
'联系人'||'","'||
'手机号'||'","'||
'用户帐号地址'||'","'||
'订单备注'||'","'||
'后台客服下单人员'||'","'||
'订单联系地址'||'","'||
'银行名'||'","'||
'银行帐号名'||'","'||
'银帐帐号'||'","'||
'支付宝名称'||'","'||
'支付宝帐号'||'","'||
'团号'||'","'||
'销售产品我方结算主体'||'","'||
'取消原因'||'","'||
'支付转移原订单号'||'","'||
'支付转移新订单号'||'","'||
'返佣率'||'","'||
'返佣金额'||'"'
from report_detail_tv  where rownum=1 union all
select '"'||product_type_zh||'","'||
SUB_PRODUCT_TYPE_ZH||'","'||
to_char(order_id)||'","'||
to_char(order_item_prod_id)||'","'||
channel_zh||'","'||
to_char(create_time,'yyyy-mm-dd hh24:mi:ss')||'","'||
to_char(deal_time,'yyyy-mm-dd hh24:mi:ss')||'","'||
to_char(visit_time,'yyyy-mm-dd')||'","'||
to_char(max_send_time,'yyyy-mm-dd hh24:mi:ss')||'","'||
order_status_zh||'","'||
payment_status_zh ||'","'||
to_char(payment_time,'yyyy-mm-dd hh24:mi:ss')||'","'||
payment_gateway_zh||'","'''||
pay_pre_status||'","'''||
commercial_name||'","'''||
serial||'","'''||
gateway_trade_no||'","'||
payment_trade_no||'","'||
settlement_period||'","'||
replace(product_name,'"','')||'","'||
product_id  ||'","'||
replace(meta_product_name,'"','')||'","'||
meta_product_id  ||'","'||
certificat_ename||'","'||
settlement_name||'","'||
supplier_name||'","'||
supplier_id ||'","'||
supplier_id_zh||'","'||
filiale_name_zh||'","'||
to_char(quantity)||'","'||
to_char(product_quantity)||'","'||
to_char(adult_quantity)||'","'||
to_char(child_quantity)||'","'||
to_char(order_pay)||'","'||
to_char(sum_youhui_amount)||'","'||
to_char(adjustment_amount)||'","'||
to_char(ought_pay)||'","'||
to_char(price_unit)||'","'||
to_char(item_pay)||'","'||
to_char(product_settlement_price)||'","'||
to_char(item_payed_amount)||'","'||
to_char(actual_pay)||'","'||
to_char(actual_settlement_price)||'","'||
to_char(actual_settlement_price*product_quantity)||'","'||
to_char(total_pay_amount-actual_settlement_price*product_quantity)||'","'||
to_char(real_item_price)||'","'||
to_char(total_pay_amount)||'","'||
to_char(sum_order_refund_amount)||'","'||
to_char(refund_channel)||'","'||
to_char(refund_time,'yyyy-mm-dd')||'","'||
to_char(sum_compensation_amount)||'","'||
to_char(compensation_channel)||'","'||
to_char(last_price)||'","'||
payment_target_zh||'","'||
channel_zh||'","'||
user_name||'","'||
to_char(order_count)||'","'||
final_operator_name||'","'||
info_operator_name||'","'||
sub_operator_name ||'","'||
audit_operator_name ||'","'||
concat_name||'","'||
mobile||'","'||
address||'","'||
(case when length(nvl(order_memo,' '))<=1000 then replace(order_memo,'"','') else
replace(substr(order_memo,1,1000),'"','')||' 更多请查看订单监控' end) ||'","'||
backorder_operator_name||'","'||
receivers_address||'","'||
bank_name||'","'||
bank_account_name||'","'''||
bank_account||'","'||
alipay_name||'","'||
alipay_account||'","'||
TRAVEL_GROUP_CODE||'","'||
decode(sub_product_type_zh,'其它','旅行社','房差','旅行社','保险','旅行社','出境跟团游','旅行社','出境自由行','旅行社','短途跟团游','旅行社','长途跟团游','旅行社','自助巴士班','旅行社','长途自由行','旅行社','景域')||'","'||
order_cancel_reason||'","'||
ori_order_id||'","'||
transfer_new_order_id||'","'||
rake_back_rate||'","'||
rake_back_price||'"'
from report_detail_tv  t