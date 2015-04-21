SELECT 
'"' || '订单号' || '","' || '销售产品' || '","' ||
'采购产品' || '","' ||
'打包数量' || '","' ||
'张数' || '","' ||
'取票人' || '","' ||
'游玩时间' || '","' ||
'实际履行时间' || '","' ||
'结算价' || '","' ||
'实际结算价' || '","' ||
'结算总价' || '"' 
from dual where rownum = 1
union all
SELECT DISTINCT
'"' || 
to_char(ORD_ORDER_ITEM_META.ORDER_ID) || '","' || 
ORD_ORDER_ITEM_PROD.PRODUCT_NAME || '","' ||
ORD_ORDER_ITEM_META.PRODUCT_NAME || '","' || 
to_char(ORD_ORDER_ITEM_META.PRODUCT_QUANTITY) || '","' ||
to_char(ORD_ORDER_ITEM_META.QUANTITY) || '","' || 
ORD_PERSON.NAME || '","' || 
to_char(ORD_ORDER_ITEM_META.VISIT_TIME,'YYYY-MM-DD') || '","' ||
to_char(decode(
(select op.create_time from ord_perform op
where ( op.object_id=ORD_ORDER_ITEM_META.Order_Item_Meta_Id and op.object_type='ORD_ORDER_ITEM_META' )
or ( op.object_id=ORD_ORDER_ITEM_META.Order_Id and op.object_type='ORD_ORDER' )
), null, ORD_ORDER_ITEM_META.Visit_Time, (select op.create_time from ord_perform op
where ( op.object_id=ORD_ORDER_ITEM_META.Order_Item_Meta_Id and op.object_type='ORD_ORDER_ITEM_META' )
or ( op.object_id=ORD_ORDER_ITEM_META.Order_Id and op.object_type='ORD_ORDER' )
)), 'YYYY-MM-DD hh24:mi:ss')
|| '","' ||  
to_char(ORD_ORDER_ITEM_META.SETTLEMENT_PRICE / 100) || '","' || 
to_char(ORD_ORDER_ITEM_META.ACTUAL_SETTLEMENT_PRICE / 100) || '","' ||
to_char((ORD_ORDER_ITEM_META.ACTUAL_SETTLEMENT_PRICE * ORD_ORDER_ITEM_META.PRODUCT_QUANTITY * ORD_ORDER_ITEM_PROD.QUANTITY) / 100) || '"' 
 FROM ORD_SUB_SETTLEMENT,
 ORD_SUB_SETTLEMENT_ITEM,
 ORD_ORDER_ITEM_META,
 ORD_ORDER_ITEM_PROD,
 ORD_PERSON