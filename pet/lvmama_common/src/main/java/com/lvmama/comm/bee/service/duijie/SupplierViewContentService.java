package com.lvmama.comm.bee.service.duijie;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierViewContent;

/**
 * 供应商产品内容接口
 * @author yanzhirong
 */
public interface SupplierViewContentService {

	public void insert(SupplierViewContent content);
	
	public void update(SupplierViewContent content);
	
	public List<SupplierViewContent> selectSupplierViewContentByCondition(Map<String,Object> params);
}
