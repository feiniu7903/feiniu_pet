SELECT '"' || '是否来电记录' || '","' || 
              '日期' || '","' || 
              '联系方式' || '","' ||
              '备注' || '","' || 
              '操作人' || '","'|| 
              '游玩时间' || '","'|| 
              '离店时间' || '","'|| 
              '出发地' || '","'|| 
              '目的地' || '","'|| 
              '游玩天数' || '","'|| 
              '人数' || '","'|| 
              '产品id' || '","'|| 
              '产品区域' || '","'|| 
              '业务类型' || '","'|| 
              '服务类型' || '","'|| 
              '服务子类型' || '"'    
  FROM DUAL
 WHERE ROWNUM = 1
UNION ALL
 SELECT DISTINCT '"' || TO_CHAR(CALL_BACK) || '","' ||
                TO_CHAR(FEEDBACK_TIME,'yyyy-MM-dd hh24:mi:ss') || '","' || 
                TO_CHAR(MOBILE) || '","' ||
                TO_CHAR(MEMO) || '","' ||
                TO_CHAR(OPERATOR_USER_ID) || '","'||
                TO_CHAR(visit_time)||'","'||
                TO_CHAR(visit_time)||'","'||
                TO_CHAR(from_place_name)||'","'||
                TO_CHAR(to_place_name)||'","'||
                TO_CHAR(day)||'","'||
                TO_CHAR(quantity)||'","'||
                TO_CHAR(product_id)||'","'||
                TO_CHAR(product_zone)||'","'||
                TO_CHAR(business_type)||'","'||
                TO_CHAR(service_type)||'","'||
                TO_CHAR(sub_service_type)||'"'
      FROM lvmama_pet.conn_record

 