/**
 * 
 */
package com.lvmama.back.sweb.prod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdTraffic;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.service.prod.ProdContainerProductService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.EbkSuperClientService;

/**
 * 产品相关的操作.
 * @author yangbin
 *
 */
public class ProdProductOpAction  extends ProductAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7561384828063612211L;
	private ProdProductBranchService prodProductBranchService;
	ProdContainerProductService  prodContainerProductService;
	private EbkSuperClientService ebkSuperClientService;
	private MetaProductService metaProductService;
	private MetaProductBranchService metaProductBranchService;
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	

	/**
	 * 修改产品的上下线.
	 */
	@Action("/prod/changeProductOnline")
	public void changeProductOnLine(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(productId,"产品不存在");
			ProdProduct pp=prodProductService.getProdProduct(productId);
			
			if(pp==null){
				throw new Exception("产品不存在.");
			}
			
			//转换成相反的值.
			pp.setOnLine(Boolean.toString(!BooleanUtils.toBoolean(pp.getOnLine())));
			//if(!pp.hasSelfPack()){//不是自主打包的时候才做默认类别检查，自主打包的判断以后再加
			if(pp.isOnLine()){//变更上线
				if(!prodProductBranchService.hasDefaultBranch(productId)){
					throw new Exception("产品没有设置默认类别，不可以上线");
				}
				if(pp.isFlight()){
					ProdTraffic pt=(ProdTraffic)pp;
					if(pt.getGoFlightId()==null||(pt.hasRound()&&pt.getBackFlightId()==null)){
						throw new Exception("机票信息的航班信息不正确，不可以上线");
					}
				}
				//检查被打包的采购产品对应的供应商的合同审核状态 add by shihui
				ResultHandle handle = prodProductService.checkAllMetaSupplierContractStatus(productId);
				if(handle.isFail()) {
					throw new Exception(handle.getMsg());
				}
			}
			//}
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("productId", productId);
			params.put("onLine", pp.getOnLine());
			params.put("productName", pp.getProductName());
			params.put("onLineStr", pp.getStrOnLine());
			prodProductService.markIsSellable(params,getOperatorNameAndCheck());
			
			if (isNotSwitch()) {
			List<MetaProduct> metaProductList= metaProductService.getMetaProductByProductId(pp.getProductId());
			Map<MetaProduct,String> map=new HashMap<MetaProduct,String>();
			for (int k = 0; k < metaProductList.size(); k++) {
				MetaProduct metaProduct1=metaProductService.getMetaProduct(metaProductList.get(k).getMetaProductId());  //采购对象
				List<MetaProductBranch> list=metaProductBranchService.selectBranchListByProductId(metaProduct1.getMetaProductId()); //采购类别
				boolean flag=false;
				for (int i = 0; i < list.size(); i++) {
					List<MetaBranchRelateProdBranch> metaBranchRelateProdBranchList = metaProductBranchService.selectProdProductAndProdBranchByMetaBranchId(Long.valueOf(String.valueOf(list.get(i).getMetaBranchId())));
					//根据采购类别查看关联的销售类别结果集类
					List<Long> productIds= new ArrayList<Long>();
					for (int j = 0; j < metaBranchRelateProdBranchList.size(); j++) {
						MetaBranchRelateProdBranch metaBranchRelateProdBranch = metaBranchRelateProdBranchList.get(j);  //销售类别对象
						if(productIds.contains(metaBranchRelateProdBranch.getProdProductId())){
							continue;
						}
						productIds.add(metaBranchRelateProdBranch.getProdProductId());
						ProdProduct prodProduct=prodProductService.getProdProductById(metaBranchRelateProdBranch.getProdProductId());//销售对象
						String online=prodProduct.getOnLine();
						if (online.equals("true")) {
							flag=true;
							break;
						}
					}
				}
				String flags="N";
				if(flag==true){
					flags="Y";
				}
				map.put(metaProduct1, flags);
			}
			log.info("update vst updateEbkSuperProd");
			ArrayList<String> results=ebkSuperClientService.updateEbkSuperProd(map).getReturnContent();
			for (String item : results) {
				String [] strings = item.split("#"); 
				if ("0".equals(strings[1])) {
					log.error("Call vst RPC service 'updateEbkSuperProd' error:"+strings[0]+"  操作失败！");
				}else if("1".equals(strings[1])){
					log.info("Call vst RPC service 'updateEbkSuperProd' "+strings[0]+"  success.");
				}
			}
			
		}//结束
			productMessageProducer.sendMsg(MessageFactory.newProductOnOffMessage(productId));
			//给客户端传结果值.
			result.put("onLine", pp.getOnLine());
			result.put("strOnLine", pp.getStrOnLine());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public boolean isNotSwitch(){
		Boolean isCallPRC=false;
		String control = Constant.getInstance().getProperty("vst.ebkSuperClientService");
		log.info("vst RPC 'ebkSuperClientService' service call cfg:"+control);
		if (StringUtils.isNotBlank(control)) {
			isCallPRC=Boolean.valueOf(control);
		}
		return isCallPRC;
	}
	
	/**
	 * 逻辑删除一个产品
	 */
	@Action("/prod/deleteProduct")
	public void delete(){
		JSONResult result = new JSONResult();
		try{
			Assert.notNull(productId,"产品不存在");
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("productId", productId);
			prodProductService.deleteProduct(params,getOperatorName());
			 prodContainerProductService.deleteContainerProduct(null, productId);
			sendUpdateProductMsg(productId);
			removeProductCache(productId);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 复制一个产品
	 */
	@Action("/prod/copyProduct")
	public void copyProduct(){
		JSONResult result = new JSONResult();
		try{
			Assert.notNull(productId,"产品不存在");
			prodProductService.copyProduct(productId, getOperatorNameAndCheck());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 读取一个产品的推荐信息.
	 */
	@Action("/prod/loadProductRecomment")
	public void loadProductRecomment(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(productId,"产品信息不存在.");
			ProdProduct pp=prodProductService.getProdProduct(productId);
			result.put("productName", pp.getProductName());
			result.put("recommendInfoFirst", pp.getRecommendInfoFirst());
			result.put("recommendInfoSecond", pp.getRecommendInfoSecond());
			result.put("recommendInfoThird", pp.getRecommendInfoThird());
			
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action("/prod/saveProductRecomment")
	public void saveProductRecomment(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(product);
			Assert.notNull(product.getProductId(),"产品不存在");
			prodProductService.updateProdRecommendWord(product);
		}catch(Exception ex){
			result.raise(ex);
		}		
		result.output(getResponse());
	}

	/**
	 * 该方法不使用.
	 */
	@Override
	public String goEdit() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 该方法不使用.
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setProdContainerProductService(
			ProdContainerProductService prodContainerProductService) {
		this.prodContainerProductService = prodContainerProductService;
	}

	/**
	 * 清除缓存
	 * */
	@Action("/prod/doCleanCache")
	public void doCleanCache(){
		JSONResult result=new JSONResult();
		try{
			MemcachedUtil.getInstance().remove("PROD_C_PRODUCT_INFO_" + productId);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	public void setEbkSuperClientService(EbkSuperClientService ebkSuperClientService) {
		this.ebkSuperClientService = ebkSuperClientService;
	}
}
