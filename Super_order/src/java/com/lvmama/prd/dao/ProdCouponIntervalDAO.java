package com.lvmama.prd.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdCouponInterval;

public class ProdCouponIntervalDAO  extends BaseIbatisDAO {
	
	   public ProdCouponInterval selectByPrimaryKey(Long prodCouponIntervalId) {
		    ProdCouponInterval record = (ProdCouponInterval) super.queryForObject("PROD_COUPON_INTERVAL.selectByPrimaryKey", prodCouponIntervalId);
	        return record;
	    }
	   
	   public int deleteByParams(Map<String, Object> params) {
		   if(params != null && params.size() > 0){
				return super.delete("PROD_COUPON_INTERVAL.deleteByParams", params);
			}else{
				return 0;
			}
	    }
	   
	   public int deleteByPrimaryKey(ProdCouponInterval prodCouponInterval) {
	        int rows = super.delete("PROD_COUPON_INTERVAL.deleteByPrimaryKey", prodCouponInterval);
	        return rows;
	    }
	   
	   public Long insert(ProdCouponInterval record) {
	       return (Long)super.insert("PROD_COUPON_INTERVAL.insert", record);
	    }
	   
		public int batchInsert(final List<ProdCouponInterval> list) {
			 return (Integer) super.execute(new SqlMapClientCallback<Object>() { 
			        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
			            executor.startBatch(); 
			            for (ProdCouponInterval prodCouponInterval : list) { 
			                executor.insert("PROD_COUPON_INTERVAL.insert", prodCouponInterval); 
			            } 
			            return executor.executeBatch(); 
			        } 
			    }); 
		}
		
	   @SuppressWarnings("unchecked")
	   public List<ProdCouponInterval> selectByParams(Map<String, Object> params) {
			return super.queryForList("PROD_COUPON_INTERVAL.selectByParams", params);
		}
	    
	    public Integer selectRowCount(Map<String, Object> params) {
	    	return (Integer) super.queryForObject("PROD_COUPON_INTERVAL.selectByParamsCount", params);
	    }
    
	    /**
	     * 查询促优惠的最大优惠有效期间(区间)
	     * @param param
	     * @return
	     */
		public ProdCouponInterval selectValidDate(Map<String, Object> param) {
			if(param != null && param.size() > 0){
				return (ProdCouponInterval) super.queryForObject("PROD_COUPON_INTERVAL.selectValidDate",param);
			}else{
				return null;
			}
		}
	    
}
