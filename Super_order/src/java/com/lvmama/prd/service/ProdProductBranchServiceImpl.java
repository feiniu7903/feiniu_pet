package com.lvmama.prd.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.TimeRange;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.LogViewUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ProdProductRelationDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;
import com.lvmama.prd.logic.ProdProductBranchLogic;
import com.lvmama.prd.logic.ProductTimePriceLogic;

public class ProdProductBranchServiceImpl implements ProdProductBranchService {

	private ProdProductBranchDAO prodProductBranchDAO;
	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	private ProdProductRelationDAO prodProductRelationDAO;
	private MetaProductDAO metaProductDAO;
	private ProdProductDAO prodProductDAO;
	private MetaProductBranchDAO metaProductBranchDAO;
	private ComLogDAO comLogDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	private ProdProductService prodProductService;
	private MetaProductBranchService metaProductBranchService;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ProdTimePriceDAO prodTimePriceDAO;
	
	private ProdProductBranchLogic prodProductBranchLogic;
	private SupplierService supplierService;
	private SupContractService supContractService;

	@Override
	public List<ProdProductBranch> getProductBranchByMetaProdBranchId(
			Long metaBranchId) {
		return prodProductBranchDAO.getProductBranchByMetaProdBranchId(metaBranchId);
	}
	
	@Override
	public List<ProdProductBranch> getProductBranchByProductId(Long productId,String additional,String online) {
		return prodProductBranchDAO.getProductBranchByProductId(productId,additional,online,null);
	}

	public void setProdProductBranchDAO(
			ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	@Override
	public ResultHandleT<ProdProductBranch> saveBranch(ProdProductBranch branch,String operatorName) {
		ResultHandleT<ProdProductBranch> handle = new ResultHandleT<ProdProductBranch>();
		try{
			if (branch.getProdBranchId() == null) {
				branch.setOnline("false");
				Long pk = prodProductBranchDAO.insert(branch);
				String branchTypeCn=getBranchTypeCnName(branch);
				if(StringUtils.isBlank(branchTypeCn)){
					branchTypeCn=branch.getBranchType();
				}
				comLogDAO.insert("PROD_PRODUCT_BRANCH", branch
						.getProductId(), pk, operatorName,
						Constant.COM_LOG_PRODUCT_EVENT.insertProdBranch.name(),
						"创建类别", "类别类型 [ "+branchTypeCn+" ],类别名称[ "+branch.getBranchName()+ " ]", "PROD_PRODUCT");
				
				branch.setProdBranchId(pk);
			} else {
				ProdProductBranch ppb = selectProdProductBranchByPK(branch
						.getProdBranchId());
				Assert.notNull(ppb, "修改的类别不存在");
	
				Assert.isTrue(ppb.getProductId().equals(branch.getProductId()),
						"修改的类别不属于原产品");
	
				prodProductBranchDAO.updateByPrimaryKeySelective(branch);
				
				ProdProduct prodProduct=prodProductDAO.selectProductDetailByPrimaryKey(ppb.getProductId());
				ppb.setProdProduct(prodProduct);
				
				String logContent  = getUpdateLogContent(ppb, branch);
				
				if(logContent != null && logContent.trim().length()>0){
					comLogDAO.insert("PROD_PRODUCT_BRANCH", branch.getProductId(), branch.getProdBranchId(), operatorName,
							Constant.COM_LOG_PRODUCT_EVENT.updateProdBranch.name(),
							"修改类别", logContent, "PROD_PRODUCT");
				}
			}
			handle.setReturnContent(branch);
		}catch(IllegalArgumentException ex){
			
		}
		return handle;
	}
	
	/**
	 * 类别中文名 
	 * @param branch 类别类型
	 * @return
	 */
	private String getBranchTypeCnName(ProdProductBranch branch){
		String cnName=null;
		List<CodeItem> codeItems=new ArrayList<CodeItem>();
		if(null!=branch.getProdProduct()){
			if(branch.getProdProduct().hasSelfPack()){
				codeItems=CodeSet.getInstance().getCodeList(ProductUtil.getBranchSetByType("SELF_PACK"));
			}else{
				codeItems=CodeSet.getInstance().getCodeList(ProductUtil.getBranchSetByType(branch.getProdProduct().getProductType()));
			}
		}
		for (CodeItem codeItem : codeItems) {
			if(codeItem.getCode().equals(branch.getBranchType())){
				cnName=codeItem.getName();
				break;
			}
		}
		return cnName;
	}
	
	
	public String getUpdateLogContent(ProdProductBranch oldProdProductBranch,ProdProductBranch preProdProductBranch){
		StringBuffer sb = new StringBuffer("");
		String branchTypeCn=getBranchTypeCnName(oldProdProductBranch);
		if(StringUtils.isBlank(branchTypeCn)){
			branchTypeCn=oldProdProductBranch.getBranchType();
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getBranchType()).equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getBranchType()))) {
			sb.append(LogViewUtil.logEditStr("类别类型", branchTypeCn, getBranchTypeCnName(preProdProductBranch)));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getBranchName()).equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getBranchName()))) {
			sb.append(LogViewUtil.logEditStr("类别名称", oldProdProductBranch.getBranchName(), preProdProductBranch.getBranchName()));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getAdditional()).equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getAdditional()))) {
			sb.append(LogViewUtil.logEditStr("是否附加", "true".equals(oldProdProductBranch.getAdditional()) ? "是" : "否", "true".equals(preProdProductBranch.getAdditional()) ? "是" : "否"));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getAdultQuantity() + "").equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getAdultQuantity() + ""))
				|| !LogViewUtil.logIsEmptyStr(preProdProductBranch.getChildQuantity() + "").equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getChildQuantity() + ""))) {
			sb.append(LogViewUtil.logEditStr("成人、儿童数量", oldProdProductBranch.getAdultQuantity() + "," + oldProdProductBranch.getChildQuantity(), preProdProductBranch.getAdultQuantity() + ","
					+ preProdProductBranch.getChildQuantity()));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getPriceUnit()).equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getPriceUnit()))) {
			sb.append(LogViewUtil.logEditStr("计价单位", oldProdProductBranch.getPriceUnit(), preProdProductBranch.getPriceUnit()));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getMinimum()+ "").equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getMinimum()+ ""))
				|| !LogViewUtil.logIsEmptyStr(preProdProductBranch.getMaximum()+ "").equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getMaximum() + ""))) {
			sb.append(LogViewUtil.logEditStr("最小起订量、最大起订量", oldProdProductBranch.getMinimum() + "," + oldProdProductBranch.getMaximum(), preProdProductBranch.getMinimum() + ","
					+ preProdProductBranch.getMaximum()));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getDescription()).equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getDescription()))) {
			sb.append(LogViewUtil.logEditStr("类别描述", oldProdProductBranch.getDescription(), preProdProductBranch.getDescription()));
		}
		if (!LogViewUtil.logIsEmptyStr(preProdProductBranch.getVisible()).equals(LogViewUtil.logIsEmptyStr(oldProdProductBranch.getVisible()))) {
			sb.append(LogViewUtil.logEditStr("是否前台显示", "true".equals(oldProdProductBranch.getVisible()) ? "是" : "否", "true".equals(preProdProductBranch.getVisible()) ? "是" : "否"));
		}
		return sb.toString();
	}
	

	@Override
	public ProdProductBranch selectProdProductBranchByPK(Long pk) {
		return prodProductBranchDAO.selectByPrimaryKey(pk);
	}

	@Override
	public void delete(Long pk) {
		prodProductBranchDAO.deleteByPrimaryKey(pk);
	}

	@Override
	public ResultHandle changeDef(Long pk, String operatorName) {
		ResultHandle handle = new ResultHandle();
		ProdProductBranch branch = selectProdProductBranchByPK(pk);
		try{
			Assert.notNull(branch, "产品类别不存在");
	
			Assert.isTrue(!branch.hasDefault(), "当前类别已经是默认类别");
			Assert.isTrue(branch.hasOnline(),"产品必须要上线才能设置为默认");
			Assert.isTrue(branch.hasVisible(),"产品必须要前台显示才能设置为默认");
			prodProductBranchDAO.clearProductDef(branch.getProductId());
	
			branch.setDefaultBranch("true");
			prodProductBranchDAO.updateByPrimaryKey(branch);
	
			comLogDAO.insert("PROD_PRODUCT_BRANCH", branch.getProductId(), pk, operatorName,
					Constant.COM_LOG_PRODUCT_EVENT.changeBranchDef.name(),
					"更改默认类别", "修改类别[类别名称："+branch.getBranchType()+"]为默认类别", "PROD_PRODUCT");
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	@Override
	public ResultHandle changeDefEBK(ProdProductBranch branch, String operatorName) {
		ResultHandle handle = new ResultHandle();
		try{
			Assert.notNull(branch, "产品类别不存在");
			prodProductBranchDAO.clearProductDef(branch.getProductId());
			branch.setDefaultBranch("true");
			prodProductBranchDAO.updateByPrimaryKey(branch);
			comLogDAO.insert("PROD_PRODUCT_BRANCH", branch.getProductId(), branch.getProdBranchId(), operatorName,
					Constant.COM_LOG_PRODUCT_EVENT.changeBranchDef.name(),
					"更改默认类别", "修改类别[类别名称："+branch.getBranchType()+"]为默认类别", "PROD_PRODUCT");
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	

	@Override
	public ResultHandle OnOfflineProductBranch(ProdProductBranch branch , String operatorName) {
		ResultHandle result=new ResultHandle();
		try{
			prodProductBranchDAO.updateByPrimaryKey(branch);
			comLogDAO.insert("PROD_PRODUCT_BRANCH", branch.getProductId(), branch.getProdBranchId(), operatorName,
					Constant.COM_LOG_PRODUCT_EVENT.updateProdBranch.name(),
					"更改产品上下线状态", "更新产品状态为："+(branch.hasOnline()?"上线":"下线"), "PROD_PRODUCT");
		}catch(IllegalArgumentException ex){
			result.setMsg(ex.getMessage());
		}
		return result;
	}
	
	@Override
	public ResultHandle changeOnline(Long pk, String operatorName) {
		ProdProductBranch branch = selectProdProductBranchByPK(pk);
		ResultHandle result=new ResultHandle();
		try{
			Assert.notNull(branch, "产品类别不存在");
			branch.setOnline(String.valueOf(!BooleanUtils.toBoolean(branch
					.getOnline())));// 设置为相反值
			if(!branch.hasOnline()){
				Assert.isTrue(!branch.hasDefault(), "默认产品不能下线");
			}
			prodProductBranchDAO.updateByPrimaryKey(branch);
	
			comLogDAO.insert("PROD_PRODUCT_BRANCH", branch.getProductId(), pk, operatorName,
					Constant.COM_LOG_PRODUCT_EVENT.updateProdBranch.name(),
					"更改产品上下线状态", "更新产品状态为："+(branch.hasOnline()?"上线":"下线"), "PROD_PRODUCT");
		}catch(IllegalArgumentException ex){
			result.setMsg(ex.getMessage());
		}
		return result;
	}

	@Override
	public ResultHandle deleteBranchByLogic(Long pk, String operatorName) {
		ProdProductBranch branch = selectProdProductBranchByPK(pk);		
		ResultHandle handle = new ResultHandle();
		try{
			Assert.notNull(branch, "产品类别不存在");
			Assert.isTrue(!branch.hasDefault(),"默认类别不能删除");
			branch.setValid("N");
			prodProductBranchDAO.updateByPrimaryKey(branch);
			if(branch.hasAdditional()&&branch.hasOnline()){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("productId", branch.getProductId());
				map.put("prodBranchId", branch.getProdBranchId());
				List<ProdProductRelation> list=prodProductRelationDAO.selectProdRelationByParam(map);
				if(CollectionUtils.isNotEmpty(list)){
					ProdProductRelation ppr=list.get(0);
					if(ppr!=null){
						prodProductRelationDAO.deleteByPrimaryKey(ppr.getRelatProductId());
					}
				}
			}
			comLogDAO.insert("PROD_PRODUCT", null, branch.getProductId(),operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateProdProduct.name(),
					"编辑销售产品", "删除类别[类别名称："+branch.getBranchName()+"]", null);
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	@Override
	public ResultHandle deleteBranchByLogicForEBK(Long pk, String operatorName) {
		ProdProductBranch branch = selectProdProductBranchByPK(pk);		
		ResultHandle handle = new ResultHandle();
		try{
			Assert.notNull(branch, "产品类别不存在");
			branch.setValid("N");
			prodProductBranchDAO.updateByPrimaryKey(branch);
			if(branch.hasAdditional()&&branch.hasOnline()){
				Map<String,Object> map=new HashMap<String, Object>();
				map.put("productId", branch.getProductId());
				map.put("prodBranchId", branch.getProdBranchId());
				List<ProdProductRelation> list=prodProductRelationDAO.selectProdRelationByParam(map);
				if(CollectionUtils.isNotEmpty(list)){
					ProdProductRelation ppr=list.get(0);
					if(ppr!=null){
						prodProductRelationDAO.deleteByPrimaryKey(ppr.getRelatProductId());
					}
				}
			}
			comLogDAO.insert("PROD_PRODUCT", null, branch.getProductId(),operatorName,
					Constant.COM_LOG_ORDER_EVENT.updateProdProduct.name(),
					"编辑销售产品", "删除类别[类别名称："+branch.getBranchName()+"]", null);
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
	
	
	
	@Override
	public List<ProdProductBranchItem> selectBranchItemByBranchId(Long branchId) {
		return prodProductBranchItemDAO
				.selectBranchItemByProdBranchId(branchId);
	}

	/**
	 * @param prodProductBranchItemDAO
	 *            the prodProductBranchItemDAO to set
	 */
	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	@Override
	public ProdProductBranchItem addItem(ProdProductBranchItem item,ProdProductBranch branch, String operatorName) { 
		Long pk = prodProductBranchItemDAO.insert(item);
		item.setBranchItemId(pk);
		//变更销售产品的支付对象		
		updatePaymentTarget(branch.getProductId());
		MetaProductBranch metaBranch = metaProductBranchDAO
				.selectBrachByPrimaryKey(item.getMetaBranchId());
		MetaProduct needAddMetaProduct = metaProductDAO.getMetaProductByPk(metaBranch.getMetaProductId());
		comLogDAO.insert("PROD_PRODUCT_BRANCH_ITEM", branch
				.getProdBranchId(), item.getBranchItemId(), operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.insertProdBranchItem.name(),
				"添加销售类别打包", MessageFormat.format("打包的采购产品名称为[ {0} ].", needAddMetaProduct.getProductName()), "PROD_PRODUCT_BRANCH");
		item.setMetaProductId(metaBranch.getMetaProductId());
		return item;
	}
	
	/**
	 * 删除类别上不存在销售的时间价格
	 * @param prodBranchId
	 * @param bean
	 * @param operator
	 */
	void deleteTimePrice(Long prodBranchId,TimePrice bean,String operator){
		productTimePriceLogic.deleteTimePrice(bean, operator);
	}
	
	/**
	 * 修改供应商的支付对象.
	 * @param productId
	 */
	public void updatePaymentTarget(Long productId) {
		boolean payToLvmama = false;
		boolean payToSupplier = false;
		List<MetaProduct> metas = metaProductDAO.getMetaProductByProductId(productId);
		for (MetaProduct metaProduct : metas) {
			if (metaProduct.isPaymentToLvmama()) {
				payToLvmama = true;
			}
			if (metaProduct.isPaymentToSupplier()) {
				payToSupplier = true;
			}
		}
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("productId", productId);
		param.put("payToLvmama", Boolean.toString(payToLvmama));
		param.put("payToSupplier", Boolean.toString(payToSupplier));
	    this.prodProductDAO.updatePaymentTarget(param);		
	}

	/**
	 * 判断一个打包是否存在
	 * 
	 * @param metaBranchId
	 *            采购类别
	 * @param prodBranchId
	 *            销售类别
	 * @return
	 */
	public boolean existsProductBranchItem(Long metaBranchId, Long prodBranchId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaBranchId", metaBranchId);
		map.put("prodBranchId", prodBranchId);

		return CollectionUtils.isNotEmpty(prodProductBranchItemDAO
				.selectByParam(map));
	}
 
	@Override
	public ResultHandle deleteItem(Long branchItemPK, String operator) {
		ProdProductBranchItem item = prodProductBranchItemDAO
				.selectByPrimaryKey(branchItemPK);
		ResultHandle handle = new ResultHandle();
		try{
			Assert.notNull(item, "打包项不存在");
	
			prodProductBranchItemDAO.deleteByPrimaryKey(branchItemPK);
			comLogDAO.insert("PROD_PRODUCT_BRANCH_ITEM",
					item.getProdBranchId(), branchItemPK, operator,
					Constant.COM_LOG_PRODUCT_EVENT.deleteProdBranchItem.name(),
					"删除销售类别打包", "删除绑定采购类别ID："+item.getMetaBranchId(), "PROD_PRODUCT_BRANCH");
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}
   
	@Override
	public ProdProductBranchItem selectBranchItemByPK(Long pk) {
		return prodProductBranchItemDAO.selectByPrimaryKey(pk);
	}

	@Override
	public List<ProdProductBranchItem> selectUniqueBranchIdByMeta(
			Long metaBranchId) {
		return prodProductBranchItemDAO.selectUniqueBranchIdByMeta(metaBranchId);
	}

	@Override
	public ResultHandle changeIcon(Long prodBranchId, String icon) {
		ProdProductBranch branch=selectProdProductBranchByPK(prodBranchId);
		ResultHandle handle = new ResultHandle();
		try{
			Assert.notNull(branch,"类别不存在");
			
			ProdProduct pp=prodProductDAO.selectProductDetailByPrimaryKey(branch.getProductId());
			Assert.notNull(pp);
			Assert.isTrue(pp.isHotel(),"只有酒店才可以添加.");
			branch.setIcon(icon);
			
			prodProductBranchDAO.updateByPrimaryKey(branch);
		}catch(IllegalArgumentException ex){
			handle.setMsg(ex.getMessage());
		}
		return handle;
	}

	/**
	 * @param metaProductDAO the metaProductDAO to set
	 */
	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	/**
	 * @param prodProductDAO the prodProductDAO to set
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	/**
	 * @param productTimePriceLogic the productTimePriceLogic to set
	 */
	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	@Override
	public boolean hasDefaultBranch(Long productId) {
		return prodProductBranchDAO.getProductDefaultBranchByProductId(productId)!=null;
	}
	
	@Override
	public void updatePriceByBranchId(Long prodBranchId) {
		productTimePriceLogic.updateBranchPriceByBranchId(prodBranchId);
	}

	@Override
	public Page<Long> selectAllBranchId(long pageSize,long page) {
		Page<Long> pp = Page.page(pageSize, page);
		long total=prodProductBranchDAO.selectAllCount();
		pp.setTotalResultSize(total);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("skipResults", pp.getStartRows());
		params.put("maxResults", pp.getEndRows());
		pp.setItems(prodProductBranchDAO.selectAll(params));
		return pp;
	}

	@Override
	public List<ProdProductBranchItem> selectItemListByMetaBranch(
			Long metaBranchId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("metaBranchId", metaBranchId);		
		return prodProductBranchItemDAO.selectByParam(map);
	}
	
	@Override
	public List<ProdProductBranch> getProductBranchDetailByProductId(Map<String, Object> params) {
		List<ProdProductBranch> list = getProductBranchByProductId((Long)params.get("productId"), (String)params.get("additional")); 
		List<ProdProductBranch> res = new ArrayList<ProdProductBranch>(); 
		Date visitTime = (Date) params.get("visitTime");
		if(CollectionUtils.isNotEmpty(list)){ 
			for(ProdProductBranch branch:list){
				Object o = params.get("onLine");
				ProdProductBranch newBranch=null;
				//下测试单
				if(null != o && !Boolean.parseBoolean(o.toString())) {
					newBranch = prodProductBranchLogic.testFill(branch, visitTime);
				} else {
					newBranch = prodProductBranchLogic.fill(branch, visitTime);
				}
				if(newBranch!=null){
					res.add(newBranch);
				}
			} 
		} 
		return res; 
	}
	
	public Date selectNearBranchTimePriceByBranchId(final Long prodBranchId){
		return productTimePriceLogic.selectNearBranchTimePriceByBranchId(prodBranchId);
	}
	
	@Override
	public ProdProductBranch getProductBranchDetailByBranchId(Long branchId, Date visitTime, boolean onLine) {
		ProdProductBranch branch = prodProductBranchDAO.selectByPrimaryKey(branchId);
		if(branch!=null){
			//下测试单
			if(!onLine) {
				branch=prodProductBranchLogic.testFill(branch, visitTime);
			} else {
				branch=prodProductBranchLogic.fill(branch, visitTime);
			}
		}
		return branch; 
	}
	
	@Override
	public ProdProductBranch getProductBranchDetailByBranchId(
			ProdProductBranch prodBranch, Date visitTime) {
		return prodProductBranchLogic.fill(prodBranch, visitTime,true,false);
	}
	
	@Override
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional) {
		return prodProductBranchDAO.getProductBranchByProductId(productId, additional,"true",null);
	}
	
	public List<ProdProductBranch> getProductBranchByProductId(Long productId) {
		return prodProductBranchDAO.getProductBranchByProductId(productId, null,null,null);
	}

	@Override
	public List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional, String isonline,boolean isBuildProdDetail) {
		List<ProdProductBranch> branchs = prodProductBranchDAO.getProductBranchByProductId(productId, additional,isonline,null);
		if(isBuildProdDetail){
			for (ProdProductBranch prodProductBranch : branchs) {
				buildProduct(prodProductBranch);
			}
		}
		return branchs;
	}
	
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}

	/**
	 * @param prodProductBranchLogic the prodProductBranchLogic to set
	 */
	public void setProdProductBranchLogic(
			ProdProductBranchLogic prodProductBranchLogic) {
		this.prodProductBranchLogic = prodProductBranchLogic;
	}

	/**
	 * @param prodProductRelationDAO the prodProductRelationDAO to set
	 */
	public void setProdProductRelationDAO(
			ProdProductRelationDAO prodProductRelationDAO) {
		this.prodProductRelationDAO = prodProductRelationDAO;
	}

	@Override
	public void updateTimePriceByProdBranchId(Long prodBranchId) {
		Date end=prodTimePriceDAO.selectMaxTimePriceByProdBranchId(prodBranchId);
		Date start=DateUtil.getDayStart(new Date());
		if(end==null||end.before(start)){//如果时间在今天之前不做处理
			return;
		}
		TimeRange timeRange=new TimeRange(start,end);
		updateTimePriceByProdBranchId(prodBranchId,timeRange);		
	}

	@Override
	public void updateTimePriceByProdBranchId(Long prodBranchId,
			TimeRange timeRange) {
		productTimePriceLogic.updateTimePriceByProdBranchId(prodBranchId,timeRange);
	}

	@Override
	public ProdProductBranch getPreProductBranch(Map<String, Object> params) {
		return prodProductBranchDAO.getPreProductBranch(params);
	}

	@Override
	public ProdProductBranch getNextProductBranch(Map<String, Object> params) {
		return prodProductBranchDAO.getNextProductBranch(params);
	}

	@Override
	public void updateProductBranchSerialNumber(Map<String, Object> params) {
		prodProductBranchDAO.updateProductBranchSerialNumber(params);
	}

	@Override
	public boolean selectIntersectionMetaProduct(Long prodBranchId,
			Long beginTime, Long endTime, Long metaBranchId) {
		return this.productTimePriceLogic.selectIntersectionMetaProduct(prodBranchId, beginTime, endTime, metaBranchId);
	}

	@Override
	public List<Date> checkTimePriceContain(Date today, Long prodBranchId,
			Long metaBranchId) {
		return this.productTimePriceLogic.checkTimePriceContain(today, prodBranchId, metaBranchId);
	}

	@Override
	public void deleteTimePrice(List<Date> dates,Long prodBranchId, String operatorName) {
		for(Date d:dates){
			TimePrice timePrice=new TimePrice();
			timePrice.setBeginDate(d);
			timePrice.setEndDate(d);
			timePrice.setProdBranchId(prodBranchId);
			productTimePriceLogic.deleteTimePrice(timePrice, operatorName);
		}
		
	}

	@Override
	public List<ProdProductBranchItem> selectItemListByParam(
			Map<String, Object> param) {
		return this.prodProductBranchItemDAO.selectByParam(param);
	}

	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public TimePrice getProdTimePrice(Long prodBranchId, Date specDate) {
		return productTimePriceLogic.calcProdTimePrice(prodBranchId, specDate);
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	@Override
	public boolean checkPhoneOrderTime(Long prodBranchId) {
		Date date = DateUtil.getTodayYMDDate();
		ProdProductBranch branch = prodProductService
				.getPhoneProdBranchDetailByProdBranchId(prodBranchId, date, true);
		// 当天不可售
		if (branch == null) {
			return false;
		}
		if(StringUtils.isEmpty(branch.getTodayOrderAble())) {
			return false;
		}
		if(!"true".equalsIgnoreCase(branch.getTodayOrderAble())) {
			return false;
		}
		// 查找销售类别关联的采购类别
		List<MetaProductBranch> metaProductBranchList = metaProductBranchDAO
				.getMetaProductBranchByProdBranchId(prodBranchId);
		if(metaProductBranchList.isEmpty()) {
			return false;
		}
		
		
		
//		if(metaProductBranchList.size() > 1) {
//			return false;
//		}
		for (MetaProductBranch metaProductBranch : metaProductBranchList) {
			MetaProduct metaProduct = metaProductDAO.getMetaProduct(
					metaProductBranch.getMetaProductId(),
					Constant.PRODUCT_TYPE.TICKET.name());
			if(metaProduct == null) {
				return false;
			}
			MetaProductTicket metaProductTicket = (MetaProductTicket) metaProduct;
			Long lastPassTime = metaProductTicket.getLastPassTime();
			Long lastTicketTime = metaProductTicket
					.getLastTicketTime();
			TimePrice price = metaProductBranchService.getTimePrice(
					metaProductBranch.getMetaBranchId(), date);
			if (price == null) {
				return false;
			}
			
			if(!metaProductTicket.hasTodayOrderAble()){
				return false;
			}
			
			String latestUseTime = price.getLatestUseTime();
			
			if (StringUtils.isNotEmpty(latestUseTime)
					&& lastPassTime!=null
					&&lastTicketTime!=null) {
				int times=0;
				if(branch.getProdProduct().isPaymentToLvmama()){
					times=lastPassTime.intValue()+lastTicketTime.intValue()+30;
				}  else {
					//支付给供应商不需要30分钟等待时间
					times=lastPassTime.intValue()+lastTicketTime.intValue();
				}
				
				Date d = DateUtils.addMinutes(price.getLatestUseTimeDate(), -times);
				if (new Date().after(d)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	@Override
	public int updateByPrimaryKeySelective(ProdProductBranch record) {
		int rows = prodProductBranchDAO.updateByPrimaryKeySelective(record);
		return rows;
	}
	
	public TimePrice  calcCurrentProdTimePric(final Long prodBranchId,final Date specDate){
		return productTimePriceLogic.calcCurrentProdTimePrice(prodBranchId, specDate);
	}

	@Override
	public List<ProdProductBranchItem> selectItemsByMetaProductId(
			Long metaProductId) {
		return prodProductBranchItemDAO.selectItemsByMetaProductId(metaProductId);
	}
	@Override
	public List<ProdProductBranch> selectProdTrainBranchsByParams(
			Map<String, Object> param) {
		return prodProductBranchDAO.selectProdTrainBranchsByParams(param);
	}
	@Override
	public ResultHandle checkMetaSupplierContractStatus(Long prodBranchId) {
		ResultHandle handle = new ResultHandle();
		List<MetaProduct> metaList = metaProductDAO.getMetaProductsByProdBranchId(prodBranchId);
		for (MetaProduct metaProduct : metaList) {
			handle = checkSupplierContractStatus(metaProduct);
			if(handle.isFail()) {
				return handle;
			}
		}
		return handle;
	}
	
	public List<TimePrice> selectProdTimePriceByProdBranchId(Long prodBranchId, Date specDateStart, Date specDateEnd){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("prodBranchId", prodBranchId);
		param.put("beginDate", specDateStart);
		param.put("endDate", specDateEnd);
		List<TimePrice> timePriceList = prodTimePriceDAO.selectProdTimePriceByParams(param);
		return timePriceList;
	}
	
	public List<TimePrice> selectMetaTimePriceByMetaBranchId(Long metaBranchId, Date specDateStart, Date specDateEnd){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("metaBranchId", metaBranchId);
		param.put("specDateStart", specDateStart);
		param.put("specDateEnd", specDateEnd);
		List<TimePrice> timePriceList = metaTimePriceDAO.getMetaTimePriceByBranchIdAsc(param);
		return timePriceList;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	private ResultHandle checkSupplierContractStatus(MetaProduct metaProduct) {
		ResultHandle handle = new ResultHandle();
		Long supplierId = metaProduct.getSupplierId();
		SupSupplier sup = supplierService.getSupplier(supplierId);
		Date d = DateUtil.toDate("2013-06-20", "yyyy-MM-dd");
		//2013-06-20之前的不校验
		if(sup.getCreateTime().after(d)) {
			Long contractId = metaProduct.getContractId();
			if(contractId == null) {
				handle.setMsg("该类别所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同为空");
				return handle;
			}
			SupContract contract = supContractService.getContract(contractId);
			if(contract == null) {
				handle.setMsg("该类别所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同为空");
				return handle;
			}
			String audit = contract.getContractAudit();
			if(StringUtils.isEmpty(audit)) {
				handle.setMsg("该类别所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同未审核");
				return handle;
			}
			if(!Constant.CONTRACT_AUDIT.PASS.name().equals(contract.getContractAudit())) {
				handle.setMsg("该类别所绑定的采购产品id[" + metaProduct.getMetaProductId() + "]对应的供应商合同未审核");
				return handle;
			}
		}
		return handle;
	}

	@Override
	public ResultHandle checkBranchItemSupplierContractStatus(Long metaBranchId) {
		MetaProduct metaProduct = metaProductDAO.getMetaProductByBranchId(metaBranchId);
		return checkSupplierContractStatus(metaProduct);
	}

	@Override
	public List<ProdProductBranch> selectProdBranchsByStationStation(
			Long stationStationId) {
		return prodProductBranchDAO.selectProdBranchsByStationStation(stationStationId);
	}

	@Override
	public List<ProdProductBranch> selectByParam(Map<String, Object> map) {
		List<ProdProductBranch> prodProductBranchList = this.prodProductBranchDAO.selectByParam(map);
		for (ProdProductBranch prodProductBranch : prodProductBranchList) {
			List<TimePrice> timePriceList = productTimePriceLogic.getTimePriceList(prodProductBranch.getProductId(), prodProductBranch.getProdBranchId(), 90,new Date(System.currentTimeMillis()));
			prodProductBranch.setTimePriceList(timePriceList);
		}
		
		return prodProductBranchList;
	}
    
	@Override
	public List<ProdProductBranch> selectProdBranchByMetaProductId(Long metaProductId,boolean isBuildProdDetail) {
		List<ProdProductBranch> prodBranchList = new ArrayList<ProdProductBranch>();
		List<MetaProductBranch> mertaBranchList = metaProductBranchService.selectBranchListByProductId(metaProductId);
		for (MetaProductBranch metaProductBranch : mertaBranchList) {
			Long metaBranchId = metaProductBranch.getMetaBranchId();
			List<ProdProductBranch> list = this.getProductBranchByMetaProdBranchId(metaBranchId);
			if(isBuildProdDetail){
				for (ProdProductBranch prodProductBranch : list) {
					buildProduct(prodProductBranch);
				}
			}
			prodBranchList.addAll(list);
		}
		return prodBranchList;
	}
	
	
	
	@Override
	public List<ProdProductBranch> selectB2BProd(Map<String, Object> map) {
		List<ProdProductBranch> list = prodProductBranchDAO.selectB2BProd(map);
		if(list!=null&&!list.isEmpty()){
			for(ProdProductBranch p:list){
				buildProduct(p);
			}
		}
		return list;
	}
	
	private void buildProduct(ProdProductBranch t){
		if(t!=null){
			Long productId = t.getProductId();
			if(productId!=null){
				ProdProduct pp = prodProductDAO.selectProdWithToPlaceById(productId);
				t.setProdProduct(pp);
			}
		}		
	}
	
	@Override
	public long selectB2BProdCount(Map<String, Object> map) {
		return prodProductBranchDAO.selectB2BProdCount(map);
	}

	@Override
	public ProdProductBranch selectB2BProdByParam(Map<String, Object> map) {
		ProdProductBranch p = prodProductBranchDAO.selectB2BProdByParam(map);
		buildProduct(p);
		return p;
	}

	@Override
	public ProdProductBranch selectB2BProdByBranchId(Long branchId) {
		ProdProductBranch p = prodProductBranchDAO.selectByPrimaryKey(branchId);
		buildProduct(p);
		return p;
	}

	@Override
	public void updateTimePriceForBranchId(Map<String, Object> paramMap) {
		prodTimePriceDAO.updateTimePriceForBranchId(paramMap);
	}
}
