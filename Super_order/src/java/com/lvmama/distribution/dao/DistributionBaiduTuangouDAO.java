package com.lvmama.distribution.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.distribution.DistributionBaiduTuangou;

public class DistributionBaiduTuangouDAO extends BaseIbatisDAO {

	public DistributionBaiduTuangouDAO() {
		super();
	}

	public Long insert(DistributionBaiduTuangou record) {
		Object newKey = super.insert("DISTRIBUTION_BAIDU_TUANGOU.insert", record);
		return (Long) newKey;
	}

	public Long insertSelective(DistributionBaiduTuangou record) {
		Object newKey = super.insert("DISTRIBUTION_BAIDU_TUANGOU.insertSelective", record);
		return (Long) newKey;
	}

	public DistributionBaiduTuangou selectByPrimaryKey(Long baiduTuangouProductId) {
		DistributionBaiduTuangou key = new DistributionBaiduTuangou();
		key.setBaiduTuangouProductId(baiduTuangouProductId);
		DistributionBaiduTuangou record = (DistributionBaiduTuangou) super.queryForObject("DISTRIBUTION_BAIDU_TUANGOU.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKeySelective(DistributionBaiduTuangou record) {
		int rows = super.update("DISTRIBUTION_BAIDU_TUANGOU.updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(DistributionBaiduTuangou record) {
		int rows = super.update("DISTRIBUTION_BAIDU_TUANGOU.updateByPrimaryKey", record);
		return rows;
	}

	@SuppressWarnings("unchecked")
	public List<DistributionBaiduTuangou> selectBaiduTuangouProducts(Map<String , Object> params) {
		return (List<DistributionBaiduTuangou>) super.queryForList("DISTRIBUTION_BAIDU_TUANGOU.selectBaiduTuangouProducts", params);
	}

	public Long getDistributionBaiduTuangouTotalCount() {
		return (Long) super.queryForObject("DISTRIBUTION_BAIDU_TUANGOU.getDistributionBaiduTuangouTotalCount");
	}

	public DistributionBaiduTuangou selectBaiduTuangouProductByProductId(Long productId) {
		return (DistributionBaiduTuangou) super.queryForObject("DISTRIBUTION_BAIDU_TUANGOU.selectBaiduTuangouProductByProductId", productId);
	}
	
	public void deleteAllBaiduTuangouProducts() {
		super.delete("DISTRIBUTION_BAIDU_TUANGOU.deleteAllBaiduTuangouProducts");
	}
	
	public void deleteBaiduTuangouProductByProductId(Long productId) {
		super.delete("DISTRIBUTION_BAIDU_TUANGOU.deleteBaiduTuangouProductByProductId", productId);
	}
}