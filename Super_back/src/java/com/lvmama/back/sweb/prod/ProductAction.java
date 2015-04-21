package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;

public abstract class ProductAction extends AbstractProductAction{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8153665813907273273L;
	protected String productType;
	protected ProdProduct product;
	protected TopicMessageProducer productMessageProducer;
	
	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public String goResult() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.AbstractProductAction#doBefore()
	 */
	@Override
	public boolean doBefore() {
		if(productId==null||productId<1){
			return false;
		}
		
		product=prodProductService.getProdProduct(productId);
		if(product==null){
			return false;
		}
		productType=product.getProductType();
		return true;
	}

	/**
	 * 发送产品更新消息.
	 * @param productId
	 */
	protected void sendUpdateProductMsg(final Long productId){
		try{
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateMessage(productId));
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/**
	 * @param productMessageProducer the productMessageProducer to set
	 */
	public void setProductMessageProducer(
			TopicMessageProducer productMessageProducer) {
		this.productMessageProducer = productMessageProducer;
	}
}
