package com.lvmama.comm.bee.service.prod;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.utils.json.ResultHandle;


public interface ProdProductRelationService {
	
	 ResultHandle updateSaleNumType(Long relationId,String saleNumType);	 
	 
	 /**
	  * 添加关联产品
	  * @param productId 当前产品
	  * @param branch 被关联的类别
	  * @return
	  */
	 ProdProductRelation addRelation(Long productId,ProdProductBranch branch,String operatorName);
	 
	 ProdProductRelation addRelation(Long mainProductId, Long additionalProductId, String operatorName);
	 
	 ProdProductRelation getProdRelation(Long productId,Long relationBranchId);
	 
	 /**
	  * 读取一个关联信息的详细信息，包括里面的产品信息及类别信息
	  * @param pk
	  * @return
	  */
	 ProdProductRelation getProdRelationDetail(Long pk);
	 
	 /**
	  * 删除
	  * @param relationId
	  */
	 void deleteRelation(Long relationId,String operatorName);
	 
	 /**
	  * 读取打包的关联产品
	  * @param productId
	  * @return
	  */
	 List<ProdProductRelation> getRelatProduct(Long productId);
	 
	 public List<ProdProductRelation> getRelationProductsByProductId(Long productId,Date visitTime);
}
