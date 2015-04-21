package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;

public class ProdJourneyProductDAO extends BaseIbatisDAO {

    public ProdJourneyProductDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long journeyProductId) {
        ProdJourneyProduct key = new ProdJourneyProduct();
        key.setJourneyProductId(journeyProductId);
        int rows = super.delete("PROD_JOURNEY_PRODUCT.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdJourneyProduct record) {
        return (Long)super.insert("PROD_JOURNEY_PRODUCT.insert", record);
    }

    public void insertSelective(ProdJourneyProduct record) {
        super.insert("PROD_JOURNEY_PRODUCT.insertSelective", record);
    }

    public ProdJourneyProduct selectByPrimaryKey(Long journeyProductId) {
        ProdJourneyProduct key = new ProdJourneyProduct();
        key.setJourneyProductId(journeyProductId);
        ProdJourneyProduct record = (ProdJourneyProduct) super.queryForObject("PROD_JOURNEY_PRODUCT.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProdJourneyProduct record) {
        int rows = super.update("PROD_JOURNEY_PRODUCT.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdJourneyProduct record) {
        int rows = super.update("PROD_JOURNEY_PRODUCT.updateByPrimaryKey", record);
        return rows;
    }
 
    public List<ProdJourneyProduct> selectJourneyProductListByJourneyId(Long journeyId){
    	ProdJourneyProduct record=new ProdJourneyProduct();
    	record.setProdJourenyId(journeyId);
    	return super.queryForList("PROD_JOURNEY_PRODUCT.selectJourneyProductListByJourneyId", record);
    }
    
    /**
     * 读取一个行程当中的产品列表，按产品id,行程组id去重复,查询的结果包含产品ID.
     * @param journeyId
     * @return
     */
    public List<ProdJourneyProduct> selectJourneyProductUniqueListByJourneyId(Long journeyId,String productType){
    	Assert.notNull(journeyId);
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("prodJourenyId", journeyId);
    	map.put("productType", productType);
    	return super.queryForList("PROD_JOURNEY_PRODUCT.selectJourneyProductUniqueListByJourneyId", map);
    }
    
    public List<ProdJourneyProduct> selectByParam(Map<String,Object> map){
    	Assert.notEmpty(map);
    	return super.queryForList("PROD_JOURNEY_PRODUCT.selectByParam",map);
    }
    
    public void deleteAllByJourney(Long prodJourneyId){
    	ProdJourneyProduct pjp=new ProdJourneyProduct();
    	pjp.setProdJourenyId(prodJourneyId);
    	super.delete("PROD_JOURNEY_PRODUCT.deleteAllByJourney", pjp);
    }
    
    /**
     * 读取行程打包的产品列表
     * @param prodJourneyId
     * @param type
     * @return
     */
    public List<ProdJourneyProduct> selectByProductType(Long prodJourneyId,String type){
    	Assert.notNull(prodJourneyId);
		Assert.notNull(type);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("productType", type);
		map.put("prodJourenyId", prodJourneyId);
		return super.queryForList("PROD_JOURNEY_PRODUCT.selectByProductType",map);
    }
    
    /**
     * 设置默认的产品
     * @param journeyProduct
     * @param journeyProduct 
     * @param selected
     */
    public void updateDefault(ProdJourneyProduct journeyProduct,String productType,String selected){
    	if(StringUtils.equalsIgnoreCase(selected, "true")){
    		//先去掉产品当中已经选中的类
    		Map<String,Object> map=new HashMap<String, Object>();
    		map.put("productType", productType);
    		map.put("prodJourenyId", journeyProduct.getProdJourenyId());
    		map.put("journeyProductId", journeyProduct.getJourneyProductId());
    		super.update("PROD_JOURNEY_PRODUCT.updateDefault",map);
    	}
    	
    	journeyProduct.setDefaultProduct(selected);
    	updateByPrimaryKeySelective(journeyProduct);
    }
    
    /**
     * 清除一个行程段下的一个类别的必选列表.
     */
    public void clearRequireByJourneyAndType(Long prodJourneyId,String productType){
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("productType", productType);
    	map.put("prodJourneyId", prodJourneyId);
    	super.update("PROD_JOURNEY_PRODUCT.clearRequireFlag",map);
    }

	public List<ProdJourneyProduct> selectJourneyProductListByJourneyIdAndPackId(Long packId,
			Long journeyId) {
		Map<String,Object> map=new HashMap<String, Object>();
    	map.put("packId", packId);
    	map.put("prodJourneyId", journeyId);
    	return super.queryForList("PROD_JOURNEY_PRODUCT.selectJourneyProductListByJourneyIdAndPackId",map);
	}
}