package com.lvmama.comm.pet.service.prod;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.prod.ProdProductHead;
import com.lvmama.comm.pet.vo.product.RelatedProduct;

public interface ProdProductHeadService {
	/**
	 * 获取最新的产品标识
	 * @return 产品标识号
	 * <p>各大系统在新增销售产品的时，都应该先调用此方法获取唯一的产品标识号。</p>
	 */
	Long generateProductId();
	
	/**
     * 根据产品标识找到产品头信息
     * @param productId 产品标识
     * @return 产品头
     */
	ProdProductHead getProdProductHeadByProductId(Long productId);
	 
    /**
     * 保存产品头信息
     * @param head 产品头信息
     * @return 保存完成的产品头信息
     */
    ProdProductHead save(ProdProductHead head);
    
    /**
     * 更新产品头信息
     * @param head 产品头信息
     * @return 更新完成的产品头信息
     */
    ProdProductHead update(ProdProductHead head); 
    
    /**
     * 根据条件查询产品头
     * @param param 条件
     * @return
     */
    List<ProdProductHead> query(Map<String, Object> param);
    
    /**
     * 根据产品标识获取一个附加类产品
     * @deprecated 这个方法是否放在此处需要商议，待有更多的实践代码后才决定
     * @param productId 产品标识
     * @return 附加类产品
     * <p>根据产品标识，获取一个可以被其他产品关联销售的附件类产品。所获取的产品屏蔽了此产品自身的众多其他属性，只被作为关联销售的产品出售.<br/>
     * 这个方法是否应该在此处，现在尚未确定。以为pet中的产品代理类目前结果形态不明确，所以开发人员需要做好此函数将被迁移的准备.
     * </p>
     */
    RelatedProduct getRelatedProduct(Long productId);
}
