/**
 * 
 */
package com.lvmama.prd.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.prod.ProdJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdPackJourneyProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductJourney;
import com.lvmama.comm.bee.po.prod.ProdProductJourneyPack;
import com.lvmama.comm.bee.service.prod.ProdProductJourneyService;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.utils.ord.ProductJourneyUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdJourneyPackDAO;
import com.lvmama.prd.dao.ProdJourneyProductDAO;
import com.lvmama.prd.dao.ProdPackJourneyDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductJourneyDAO;

/**
 * @author yangbin
 *
 */
public class ProdProductJourneyServiceImpl implements ProdProductJourneyService {
	
	private ProdProductJourneyDAO prodProductJourneyDAO;
	private ProdJourneyProductDAO prodJourneyProductDAO;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	private ProdJourneyPackDAO prodJourneyPackDAO;
	private ProdPackJourneyDAO prodPackJourneyDAO;
	
	private ComLogDAO comLogDAO;

	/* (non-Javadoc)
	 * @see com.lvmama.back.service.ProdProductJourneyService#changeJourneyProdutProp()
	 */
	@Override
	public void changeJourneyProdutProp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteJourneyProduct(Long pk, String operatorName) {
		ProdJourneyProduct pjp=prodJourneyProductDAO.selectByPrimaryKey(pk);
		Assert.notNull(pjp,"行程产品不存在");
		prodJourneyProductDAO.deleteByPrimaryKey(pk);
		comLogDAO.insert("PROD_JOURNEY_PRODUCT", pjp.getProdJourenyId(),
				pk, operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.deleteJourneyProduct.name(),
				"删除行程打包产品", "销售类别ID"+pjp.getProdBranchId(), "PROD_PRODUCT_JOURNEY");
	}



	@Override
	public void deleteProductJourney(Long pk, String operatorName) {
		ProdProductJourney ppj=prodProductJourneyDAO.selectByPrimaryKey(pk);
		Assert.notNull(ppj,"行程不存在");
		
		//同时删除行程打包的所有的产品
		prodJourneyProductDAO.deleteAllByJourney(pk);
		
		prodProductJourneyDAO.deleteByPrimaryKey(pk);
		
		
		comLogDAO.insert("PROD_PRODUCT_JOURNEY", ppj.getProductId(), pk,
				operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.deleteProductJourney.name(),
				"删除行程", null, "PROD_PRODUCT");
		
		//同时删除行程当中所有的相关的产品
		
	}


	/* (non-Javadoc)
	 * @see com.lvmama.back.service.ProdProductJourneyService#save(com.lvmama.prd.po.ProdProductJourney, java.lang.String)
	 */
	@Override
	public ProdProductJourney save(ProdProductJourney journey,
			String operatorName) {
		
		journey.setMinTime(journey.getMaxTime());//现在为了保证行程是固定的。最大值与最小值都存入一样的
		
		if(journey.getProdJourenyId()==null){
			journey.setProdJourenyId(prodProductJourneyDAO.insert(journey));
			comLogDAO.insert("PROD_PRODUCT_JOURNEY",
					journey.getProductId(),journey.getProdJourenyId(), 
					operatorName, Constant.COM_LOG_PRODUCT_EVENT.insertJourneyProduct
							.name(), "添加行程", null, "PROD_PRODUCT_JOURNEY");
		}else{
			prodProductJourneyDAO.updateByPrimaryKeySelective(journey);
		}
		
		return journey;
	}

	/* (non-Javadoc)
	 * @see com.lvmama.back.service.ProdProductJourneyService#save(com.lvmama.prd.po.ProdJourneyProduct, java.lang.Long, java.lang.String)
	 */
	@Override
	public ResultHandleT<ProdJourneyProduct> save(ProdJourneyProduct product,
			String operatorName) {
		ResultHandleT<ProdJourneyProduct> result=new ResultHandleT<ProdJourneyProduct>();
		try{
			Assert.notNull(product);
			Assert.notNull(product.getProdJourenyId());
			Assert.notNull(product.getProdBranchId());
			
			
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(product.getProdBranchId());
			Assert.notNull(branch,"类别不存在");
			
			ProdProduct prodProduct=prodProductDAO.selectProductDetailByPrimaryKey(branch.getProductId());
			ProdProductJourney ppj=prodProductJourneyDAO.selectByPrimaryKey(product.getProdJourenyId());
			
			if(prodProduct.getProductId().equals(ppj.getProductId())){
				throw new IllegalArgumentException("不可以打包当前产品的类别");
			}
			Assert.notNull(prodProduct,"添加的类别所属产品不存在");
			if(prodProduct.isPaymentToSupplier()){
				throw new IllegalArgumentException("不可打包支付给供应商的产品");
			}
			if(prodProduct.isHotel()&&StringUtils.equals(prodProduct.getSubProductType(), Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name())){
				throw new IllegalArgumentException("自主打包自由行不可以打包酒店套餐");
			}else if(prodProduct.isRoute()){
				if(prodProduct.hasSelfPack()){
					throw  new IllegalArgumentException("不可以打包另一个超级自由行");
				}
			}
			
			if(product.getJourneyProductId()!=null){
				ProdJourneyProduct pjp = prodJourneyProductDAO.selectByPrimaryKey(product.getJourneyProductId());
				Assert.notNull(pjp,"对应的打包产品不存在");
				prodJourneyProductDAO.updateByPrimaryKey(product);
				comLogDAO.insert("PROD_JOURNEY_PRODUCT",
						product.getProdJourenyId(), product.getJourneyProductId(),
						operatorName, Constant.COM_LOG_PRODUCT_EVENT.editJourneyProduct
								.name(), "编辑产品", null, "PROD_PRODUCT_JOURNEY");
			}else{
				//新添加时要判断是否重复
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("prodBranchId", product.getProdBranchId());
				map.put("prodJourenyId", product.getProdJourenyId());
				List<ProdJourneyProduct> list=prodJourneyProductDAO.selectByParam(map);
				if(CollectionUtils.isNotEmpty(list)){
					throw new IllegalArgumentException("一个行程段当中不可以重复添加");
				}
				product.setJourneyProductId(prodJourneyProductDAO.insert(product));
				comLogDAO.insert("PROD_JOURNEY_PRODUCT",
						product.getProdJourenyId(), product.getJourneyProductId(),
						operatorName, Constant.COM_LOG_PRODUCT_EVENT.insertJourneyProduct
								.name(), "添加产品 类别","添加销售产品 类别ID:"+product.getProdBranchId(), "PROD_PRODUCT_JOURNEY");				
			}
			result.setReturnContent(product);
		}catch(IllegalArgumentException ex){
			result.setMsg(ex.getMessage());
		}
		return result;
	}
	

	/**
	 * @param prodProductJourneyDAO the prodProductJourneyDAO to set
	 */
	public void setProdProductJourneyDAO(ProdProductJourneyDAO prodProductJourneyDAO) {
		this.prodProductJourneyDAO = prodProductJourneyDAO;
	}

	/**
	 * @param prodJourneyProductDAO the prodJourneyProductDAO to set
	 */
	public void setProdJourneyProductDAO(ProdJourneyProductDAO prodJourneyProductDAO) {
		this.prodJourneyProductDAO = prodJourneyProductDAO;
	}

	public void setProdJourneyPackDAO(ProdJourneyPackDAO prodJourneyPackDAO) {
		this.prodJourneyPackDAO = prodJourneyPackDAO;
	}

	public void setProdPackJourneyDAO(ProdPackJourneyDAO prodPackJourneyDAO) {
		this.prodPackJourneyDAO = prodPackJourneyDAO;
	}
	
	
	@Override
	public Map<String,List<ProdJourneyProduct>> selectJourneyProductDetailMap(
			Long journeyId,boolean unique) {
		List<ProdJourneyProduct> products=null;
		if(!unique){
			products=prodJourneyProductDAO.selectJourneyProductListByJourneyId(journeyId);
		}else{
			products=prodJourneyProductDAO.selectJourneyProductUniqueListByJourneyId(journeyId,null);//一个组的一个产品只读取一个
		}
		if(CollectionUtils.isNotEmpty(products)){
			//初始化对应的类别，产品数据
			initJourneyProduct(products);
			
			
			return ProductJourneyUtil.conver(products);
		}
		return null;
	}
	
	/**
	 * 初始化行程打包当中的产品与类别
	 * @param list 该值需要在调用方保证不为空
	 */
	private void initJourneyProduct(List<ProdJourneyProduct> list){
		for(ProdJourneyProduct pjp:list){
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(pjp.getProdBranchId());
			
			ProdProduct pp=prodProductDAO.selectByPrimaryKey(branch.getProductId());
			branch.setProdProduct(pp);
			
			pjp.setProdBranch(branch);
		}
	}

	@Override
	public List<ProdProductJourney> selectProductJourneyListByProductId(
			Long productId) {
		return prodProductJourneyDAO.selectListByProductId(productId);
	}
 

	@Override
	public List<ProdJourneyProduct> selectJourneyProductListByJourneyType(
			Long prodJourneyId, String type) {		
		List<ProdJourneyProduct> list=prodJourneyProductDAO.selectByProductType(prodJourneyId,type);
		if(CollectionUtils.isNotEmpty(list)){
			//初始化打包产品对应的branch,product
			initJourneyProduct(list);
		}
		return list;
	}
	public List<ProdJourneyProduct> selectJourneyProductUniqueList(Long prodJourneyId,String type){
		List<ProdJourneyProduct> list=prodJourneyProductDAO.selectJourneyProductUniqueListByJourneyId(prodJourneyId,type);
		if(CollectionUtils.isNotEmpty(list)){
			//初始化打包产品对应的branch,product
			initJourneyProduct(list);
		}
		return list;
	}

	@Override
	public List<ProdJourneyProduct> selectJourneyProductByGroup(
			Long journeyGroupId) {
		Assert.notNull(journeyGroupId);
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("journeyGroupId", journeyGroupId);
		return prodJourneyProductDAO.selectByParam(map);
	}
	
	@Override
	public ResultHandle changeJourneyProdutDiscount(ProdJourneyProduct journeyProduct,String operatorName) {
		ResultHandle handle=new ResultHandle();
		try{
			Assert.notNull(journeyProduct.getJourneyProductId(),"产品信息不存在");
			Assert.notNull(journeyProduct.getDiscount(),"优惠金额为空");
			
			ProdJourneyProduct pjp=prodJourneyProductDAO.selectByPrimaryKey(journeyProduct.getJourneyProductId());
			Assert.notNull(pjp,"产品不存在");
			
			pjp.setDiscount(journeyProduct.getDiscount());
			
			prodJourneyProductDAO.updateByPrimaryKey(pjp);
			comLogDAO.insert("PROD_JOURNEY_PRODUCT",
					pjp.getProdJourenyId(), pjp.getJourneyProductId(),
					operatorName, Constant.COM_LOG_PRODUCT_EVENT.editJourneyProduct
							.name(), "更改优惠","更改销售产品 类别ID:"+pjp.getProdBranchId()+"优惠:"+pjp.getDiscountYuan(), "PROD_PRODUCT_JOURNEY");
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}

	

	/**
	 * @param prodProductDAO the prodProductDAO to set
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	/**
	 * @param prodProductBranchDAO the prodProductBranchDAO to set
	 */
	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	@Override
	public ProdProductJourney selectProductJourneyByPK(Long prodJourenyId) {
		return prodProductJourneyDAO.selectByPrimaryKey(prodJourenyId);
	}

	
	

	@Override
	public void changeJourneyProductDefault(ProdJourneyProduct journeyProduct,
			String selected,String operatorName) {
		ProdJourneyProduct entity=prodJourneyProductDAO.selectByPrimaryKey(journeyProduct.getJourneyProductId());
		Assert.notNull(entity,"产品不存在");
		
		//取出打包的产品对应的产品类型
		ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(entity.getProdBranchId());
		Assert.notNull(branch,"类别信息不存在");
		
		ProdProduct prodProduct=prodProductDAO.selectByPrimaryKey(branch.getProductId());
		prodJourneyProductDAO.updateDefault(entity,prodProduct.getProductType(),selected);
		
		comLogDAO.insert("PROD_JOURNEY_PRODUCT",
				entity.getProdJourenyId(), entity.getJourneyProductId(),
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.editJourneyProduct
						.name(), "更改类别默认值","更改行程["+prodProduct.getZhProductType()+"组]销售产品 类别ID:"+entity.getProdBranchId()+"默认值为:"+selected, "PROD_PRODUCT_JOURNEY");
	}

	/**
	 * 更新酒店时并且是为true时.会把同行程当中同类型的已经选中的清除掉
	 */
	@Override
	public void changeJourneyProductRequire(ProdJourneyProduct journeyProduct,
			String selected,String operatorName) {		
		ProdJourneyProduct entity=prodJourneyProductDAO.selectByPrimaryKey(journeyProduct.getJourneyProductId());
		Assert.notNull(entity,"产品不存在");
		String zhProductType=null;
		if(StringUtils.equalsIgnoreCase(selected, "true")){
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(entity.getProdBranchId());
			if(branch!=null){
				ProdProduct product=prodProductDAO.selectByPrimaryKey(branch.getProductId());
				if(product!=null&&product.isHotel()){//如果是酒店产品，必选只能设置一个值.需要取出对应的另外的必选值清除掉,现在只有酒店，以后会增加大交通.
					prodJourneyProductDAO.clearRequireByJourneyAndType(entity.getProdJourenyId(),product.getProductType());
					zhProductType=product.getZhProductType();
				}
			}
		}
		entity.setRequire(selected);		
		prodJourneyProductDAO.updateByPrimaryKey(entity);
		
		comLogDAO.insert("PROD_JOURNEY_PRODUCT",
				entity.getProdJourenyId(), entity.getJourneyProductId(),
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.editJourneyProduct
						.name(), "更改类别必选值","更改行程["+zhProductType+"组]销售产品 类别ID:"+entity.getProdBranchId()+"必选值为:"+selected, "PROD_PRODUCT_JOURNEY");
	}

	@Override
	public void changeJourneyPolicy(ProdProductJourney productJourney,
			String type, String selected,String operatorName) {
		ProdProductJourney ppj=prodProductJourneyDAO.selectByPrimaryKey(productJourney.getProdJourenyId());
		Assert.notNull(ppj,"行程不存在");
		
		ppj.setPolicy(type,selected);
		
		prodProductJourneyDAO.updateByPrimaryKey(ppj);
		
		comLogDAO.insert("PROD_PRODUCT_JOURNEY",
				null, ppj.getProdJourenyId(),
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.editJourneyProduct
						.name(), "更改组策略","更改行程["+Constant.PRODUCT_TYPE.getCnName(type)+"组] 属性为："+(StringUtils.equals(selected, "true")?"必选":"可选"), null);
	}

	@Override
	public void changeJourneyTime(ProdProductJourney productJourney,
			String operatorName) {
		ProdProductJourney ppj=prodProductJourneyDAO.selectByPrimaryKey(productJourney.getProdJourenyId());
		Assert.notNull(ppj,"行程不存在");
		ppj.setJourneyTime(productJourney.getJourneyTime());
		
		prodProductJourneyDAO.updateByPrimaryKey(ppj);
		
		comLogDAO.insert("PROD_PRODUCT_JOURNEY",
				null, ppj.getProdJourenyId(),
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.editProductJourney
						.name(), "编辑行程时间", "set journeyTime="+ppj.getJourneyTime(), null);
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	
	
	
	public ProdProductJourneyPack queryProductJourneyPackByPackId(Long packId){
		
		ProdProductJourneyPack prodProductJourneyPack = prodJourneyPackDAO.queryProductJourneyPackByPackId(packId);
		if(prodProductJourneyPack!=null){
			prodProductJourneyPack.setProdPackJourenyProducts(prodPackJourneyDAO.queryByPackJourneyId(packId));
			List<ProdProductJourney> prodProductJourneyList = selectProductJourneyListByProductId(prodProductJourneyPack.getProductId());
			for(ProdProductJourney ppj:prodProductJourneyList){
				ppj.setProdJourneyGroup(selectPackJourneyProductDetailMap(packId,ppj.getProdJourenyId()));
			}
			prodProductJourneyPack.setProdProductJourneys(prodProductJourneyList);
		}
		return prodProductJourneyPack;
	}

	public List<ProdProductJourneyPack> queryProductJourneyPackByProductId(Long productId){
		List<ProdProductJourneyPack> ppjps = prodJourneyPackDAO.queryJourneyPackByProductId(productId);
		if(ppjps!=null){
			for(ProdProductJourneyPack ppjp : ppjps){
				ppjp.setProdPackJourenyProducts(prodPackJourneyDAO.queryByPackJourneyId(ppjp.getProdJourneyPackId()));
				List<ProdProductJourney> prodProductJourneyList = selectProductJourneyListByProductId(productId);
				for(ProdProductJourney ppj:prodProductJourneyList){
					ppj.setProdJourneyGroup(selectPackJourneyProductDetailMap(ppjp.getProdJourneyPackId(),ppj.getProdJourenyId()));
				}
				ppjp.setProdProductJourneys(prodProductJourneyList);
			}
		}
		return ppjps;
	}

	private Map<String,List<ProdJourneyProduct>> selectPackJourneyProductDetailMap(Long packId,Long journeyId) {
		List<ProdJourneyProduct> products=null;
		products=prodJourneyProductDAO.selectJourneyProductListByJourneyIdAndPackId(packId,journeyId);
		if(CollectionUtils.isNotEmpty(products)){
			//初始化对应的类别，产品数据
			initJourneyProduct(products);
			return ProductJourneyUtil.conver(products);
		}
		return null;
	}
	
	public void savePack(ProdProductJourneyPack prodProductJourneyPack,String operatorName){
		
		
		if(prodProductJourneyPack.getProdJourneyPackId()==null){
			Long packid = prodJourneyPackDAO.savePack(prodProductJourneyPack);
			prodPackJourneyDAO.save(prodProductJourneyPack.getProdPackJourenyProducts(), packid);
			comLogDAO.insert("PROD_PACK_PRODUCT",packid, packid,
					operatorName, Constant.COM_LOG_PRODUCT_EVENT.insertProdPack
							.name(), "新增线路套餐","套餐名称: "+prodProductJourneyPack.getPackName()+" 所属产品ID" + prodProductJourneyPack.getProductId(), "PROD_PACK_PRODUCT");
		}else{
			ProdProductJourneyPack oldProdProductJourneyPack = queryProductJourneyPackByPackId(prodProductJourneyPack.getProdJourneyPackId());
			prodJourneyPackDAO.savePack(prodProductJourneyPack);
			prodPackJourneyDAO.save(prodProductJourneyPack.getProdPackJourenyProducts(), prodProductJourneyPack.getProdJourneyPackId());
			insertEditPackLog(oldProdProductJourneyPack,prodProductJourneyPack,operatorName);
			
		}
		
	}
	
	
	private void insertEditPackLog(ProdProductJourneyPack oldProdProductJourneyPack,ProdProductJourneyPack prodProductJourneyPack,String operatorName ) {
		
		String str="";
		if(!oldProdProductJourneyPack.getPackName().equals(prodProductJourneyPack.getPackName())){
			str= str + "套餐名称["+ oldProdProductJourneyPack.getPackName() +"-> "+ prodProductJourneyPack.getPackName() +"]";
		}
		List<ProdPackJourneyProduct> oldprodPackJourneyProducts = oldProdProductJourneyPack.getProdPackJourenyProducts();
		List<ProdPackJourneyProduct> prodPackJourneyProducts = prodProductJourneyPack.getProdPackJourenyProducts();
		List<ProdPackJourneyProduct> list3 = new ArrayList<ProdPackJourneyProduct>();
		List<ProdPackJourneyProduct> list4 = new ArrayList<ProdPackJourneyProduct>();
		
		if(oldprodPackJourneyProducts!=null && prodPackJourneyProducts!=null){
			for(ProdPackJourneyProduct oldpjp: oldprodPackJourneyProducts){
				for(ProdPackJourneyProduct npjp: prodPackJourneyProducts){
					if(oldpjp.getJourneyProductId().equals(npjp.getJourneyProductId())){
						list3.add(oldpjp);
						list4.add(npjp);
					}
				}
			}
			oldprodPackJourneyProducts.removeAll(list3);
			prodPackJourneyProducts.removeAll(list4);
			
		}
		
		if(oldprodPackJourneyProducts!=null && oldprodPackJourneyProducts.size()>0){
			str= str + "删除套餐产品[";
			for(ProdPackJourneyProduct oldpjp: oldprodPackJourneyProducts){
				str= str +"  getJourneyProductId="+ oldpjp.getJourneyProductId() ;
			}
			str= str +"]";
		}
		if(prodPackJourneyProducts!=null && prodPackJourneyProducts.size()>0){
			str= str + "新增套餐产品[";
			for(ProdPackJourneyProduct oldpjp: prodPackJourneyProducts){
				str= str +"  getJourneyProductId="+ oldpjp.getJourneyProductId() ;
			}
			str= str +"]";
		}
		comLogDAO.insert("PROD_PACK_PRODUCT",
				oldProdProductJourneyPack.getProdJourneyPackId(), oldProdProductJourneyPack.getProdJourneyPackId(),
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.updateProdPack
						.name(), "编辑套餐信息",str, "PROD_PACK_PRODUCT");
		
	}

	public void deletePack(Long packId,String operatorName){
		prodJourneyPackDAO.deletePack(packId);
		prodPackJourneyDAO.delete(packId);
		comLogDAO.insert("PROD_PACK_PRODUCT",
				packId, packId,
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.deleteProdPack
						.name(), "删除套餐信息","套餐ID:"+packId, "PROD_PACK_PRODUCT");
	}

	public void updatePackOnLine(ProdProductJourneyPack prodProductJourneyPack,String operatorName){
		prodJourneyPackDAO.updatePackOnLine(prodProductJourneyPack);
		
		String oldv = "true".equals(prodProductJourneyPack.getOnLine())?"false":"true";
		comLogDAO.insert("PROD_PACK_PRODUCT",
				prodProductJourneyPack.getProdJourneyPackId(), prodProductJourneyPack.getProdJourneyPackId(),
				operatorName, Constant.COM_LOG_PRODUCT_EVENT.updateProdPack
						.name(), "更新套餐上线状态","原值"+ oldv +" -> 新值:"+prodProductJourneyPack.getOnLine(), "PROD_PACK_PRODUCT");
	}
	
}
