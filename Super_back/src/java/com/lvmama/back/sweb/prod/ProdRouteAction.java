/**
 * 
 */
package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.ord.RouteUtil;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import java.util.List;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/base/route_product.jsp"),
	@Result(name="auditingShow",location="/WEB-INF/pages/back/prod/auditing/route_product_auditing_show.jsp")
})
public class ProdRouteAction extends ProdProductAction<ProdRoute>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3154036290715075903L;
	//电子合同
	private ProdEContract prodEContract=new ProdEContract();
	//是否需要电子合同
	private String productEContract;
	private String[] productTravelFormalities;//选中的旅游手续
	private String[] productGuideServices;//导游服务
	private String[] productGroupTypes;//组团方式
	protected String saveProductUrl="saveRouteProduct";//保存的action地址
	
	

	public ProdRouteAction() {
		super(Constant.PRODUCT_TYPE.ROUTE);
		addFields("eContract","qiFlag");
	}
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdProductAction#doSaveBefore()
	 */
	@Override
	protected ResultHandle doSaveBefore() {
		// TODO Auto-generated method stub
		ResultHandle handle=super.doSaveBefore();
		if(handle.isFail()){
			return handle;
		}
		if(!Constant.SUB_PRODUCT_TYPE.FREENESS.name().equals(product.getSubProductType())){
			if(StringUtils.isEmpty(product.getGroupType())){
				handle.setMsg("非目的地自由行产品必须选择组团类型");
				return handle;
			}
		}else{
			product.setGroupType(null);
		}
		if(RouteUtil.hasTravelGroupProduct(product.getSubProductType())){
			if(product.getAheadConfirmHours()==null||product.getAheadConfirmHours()<0){
				handle.setMsg("选择短线、出境、长线、自助巴士班时提前确认成团小时数不能小于等于0");
				return handle;
			}
			
			if(StringUtils.isEmpty(product.getTravelGroupCode())){
				handle.setMsg("选择短线、出境、长线、自助巴士班时团号不可以为空");
				return handle;
			}
		}
		if(hasEContract()){//如果需要签合同需要把合同当中的数据合并到prodEContract当中
			if(ArrayUtils.isEmpty(productTravelFormalities)){
				handle.setMsg("旅游手续必须选中一个");
				return handle;
			}
			if(ArrayUtils.contains(productTravelFormalities, "OTHERS")){
				if(StringUtils.isEmpty(prodEContract.getOtherTravelFormalities())){
					handle.setMsg("旅游手续选中其他时文本框不可以为空");
					return handle;
				}
				
			}
			prodEContract.setTravelFormalities(merge(productTravelFormalities));
//			
//			if(ArrayUtils.isEmpty(productGuideServices)){
//				handle.setMsg("导游服务不可以为空");
//				return handle;
//			}
			if(null !=productGuideServices){
				prodEContract.setGuideService(merge(productGuideServices));
			}
			if(ArrayUtils.isEmpty(productGroupTypes)){
				handle.setMsg("组团方式不可以为空");
				return handle;
			}
			
			
			
			if(ArrayUtils.contains(productGroupTypes, "AGENCY")){
				if(StringUtils.isEmpty(prodEContract.getAgency())){
					handle.setMsg("被委托组团框不可以为空");
					return handle;
				}
			}
			prodEContract.setGroupType(merge(productGroupTypes));
			/**
			if(StringUtils.isEmpty(prodEContract.getAgencyAddress())){
				handle.setMsg("地接社名称/地址/联系人/联系方式/电话 不可以为空");
				return handle;
			}
			*/
		}
		
		product.seteContract(hasEContract()?Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name():Constant.ECONTRACT_TYPE.NEEDNOT_ECONTRACT.name());
		return handle;
	}
	
	

	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProdProductAction#doSaveAfter()
	 */
	@Override
	protected ResultHandle doSaveAfter() {
		// TODO Auto-generated method stub
		ResultHandle handle=super.doSaveAfter();
		if(handle.isFail()){
			return handle;
		}
		if(hasEContract()){
			ProdEContract oldProdEContract=prodProductService.getProdEContractByProductId(product.getProductId());
			if(oldProdEContract!=null){
				prodEContract.seteContractId(oldProdEContract.geteContractId());
			}
			prodEContract.setProductId(product.getProductId());
			prodProductService.saveEContract(prodEContract);
		}
		return handle;
	}

	@Override
	@Action(value="/prod/editRouteProduct")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		initEditProduct();
		if(product.isEContract()){//如果需要电子合同就读取电子合同出来
			prodEContract=prodProductService.getProdEContractByProductId(productId);
			if(prodEContract!=null){
				productTravelFormalities=StringUtils.split(prodEContract.getTravelFormalities(),",");
				productGuideServices=StringUtils.split(prodEContract.getGuideService(),",");
				productGroupTypes=StringUtils.split(prodEContract.getGroupType(),",");
			}
		}
		productEContract=BooleanUtils.toStringTrueFalse(product.isEContract());
		if(product.getPayDeposit()!=null&&product.getPayDeposit()>0){//当前只在线路当中使用，定金变成元
			product.setPayDeposit(product.getPayDeposit()/100);
		}
		return goAfter();
	}

	@Override
	@Action("/prod/toAddRouteProduct")
	public String goResult() {
		product.setQiFlag("true");
		//It is default that fill traveller's name and card number for route.
		this.firstTravellerInfoOptions = new String[]{"NAME","CARD_NUMBER","MOBILE"};
		this.productTravellerInfoOptions = new String[]{"NAME","CARD_NUMBER"};
		return goAfter();
	}

	@Override
	@Action("/prod/saveRouteProduct")
	public void save() {
		// TODO Auto-generated method stub
		saveProduct();
	}

	/**
	 * @return the prodEContract
	 */
	public ProdEContract getProdEContract() {
		return prodEContract;
	}

	/**
	 * @param prodEContract the prodEContract to set
	 */
	public void setProdEContract(ProdEContract prodEContract) {
		this.prodEContract = prodEContract;
	}

	/**
	 * @return the productEContract
	 */
	public String getProductEContract() {
		return productEContract;
	}
	
	/**
	 * 判断是否提交的数据是需要签合同
	 * @return
	 */
	private boolean hasEContract(){
		return StringUtils.equals("true", productEContract);
	}
	
	

	/**
	 * 发布合同范本列表
	 * @return
	 */
	public List<CodeItem> getEcontractTemplateList(){
		return CodeSet.getInstance().getCodeListAndBlank("ECONTRACT_TEMPLATE");
	}
	
	/**
	 * 旅游手续列表
	 * @return
	 */
	public List<CodeItem> getTravelFormalitiesList(){
		return CodeSet.getInstance().getCodeList("TRAVEL_FORMALITIES");
	}
	
	/**
	 * 服务列表
	 * @return
	 */
	public List<CodeItem> getGuideServiceList(){
		return CodeSet.getInstance().getCodeList("GUIDE_SERVICE");
	}
	
	public List<CodeItem> getGroupTypeList(){
		return CodeSet.getInstance().getCodeList("GROUP_TYPE");
	}

	/**
	 * @return the productTravelFormalities
	 */
	public String[] getProductTravelFormalities() {
		return productTravelFormalities;
	}

	/**
	 * @param productTravelFormalities the productTravelFormalities to set
	 */
	public void setProductTravelFormalities(String[] productTravelFormalities) {
		this.productTravelFormalities = productTravelFormalities;
	}

	/**
	 * @return the productGuideServices
	 */
	public String[] getProductGuideServices() {
		return productGuideServices;
	}

	/**
	 * @param productGuideServices the productGuideServices to set
	 */
	public void setProductGuideServices(String[] productGuideServices) {
		this.productGuideServices = productGuideServices;
	}

	
	
	

	/**
	 * @return the productGroupTypes
	 */
	public String[] getProductGroupTypes() {
		return productGroupTypes;
	}

	/**
	 * @param productGroupTypes the productGroupTypes to set
	 */
	public void setProductGroupTypes(String[] productGroupTypes) {
		this.productGroupTypes = productGroupTypes;
	}
	
	
	public List<CodeItem> getSubProductTypeList(){
		return ProductUtil.getRouteSubTypeList();
	}



	/**
	 * @param productEContract the productEContract to set
	 */
	public void setProductEContract(String productEContract) {
		this.productEContract = productEContract;
	}



	/**
	 * @return the saveProductUrl
	 */
	public String getSaveProductUrl() {
		return saveProductUrl;
	}
	
	public List<CodeItem> getRegionNamesList(){
		return ProductUtil.getregionNamesXLQT();
	}
	
}
