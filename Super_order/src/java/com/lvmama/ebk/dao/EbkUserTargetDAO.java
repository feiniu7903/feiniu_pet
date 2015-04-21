
package com.lvmama.ebk.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.eplace.EbkUserTarget;
/**
 * EBK_USER_TARGET 表的CRUD.<br/>
 * 表内所存数据 为EBK 用户和履行对象的关联数据，由于此需求之前，<br>
 * EBK用户是绑定的采购产品，如果产品后续增加，或者删除，则需要手动维护绑定关系，<br>
 * 所以，如果用户绑定到履行对象上，就不需要手动维护此关系，简化了产品录入的操作并减少<br>
 * 数据关系维护成本
 * @author zhangkexing
 * @version 1.0
 * @since 2013-05-03
 */

public class EbkUserTargetDAO extends BaseIbatisDAO{	

	public Long insert(EbkUserTarget ebkUserTarget) {
		return (Long)super.insert("EBK_USER_TARGET.insert", ebkUserTarget);
	}
	public void delete(EbkUserTarget ebkUserTarget){
		super.delete("EBK_USER_TARGET.delete",ebkUserTarget);
	}
	
	@SuppressWarnings("unchecked")
	public List<EbkUserTarget> getEbkUserTargetList(Map<String, Object> params) {
		return (List<EbkUserTarget>)super.queryForList("EBK_USER_TARGET.queryTargetListByParams", params);
	}
}
