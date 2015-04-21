/**
 * 
 */
package com.lvmama.back.sweb.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.MetaBranchRelateProdBranch;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.EbkSuperClientService;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="PAGE_NOT_FOUND",location="/WEB-INF/pages/back/meta/page_not_found.jsp"),
	@Result(name="TO_EDIT_TICKET",location="/meta/toEditTicket.do?metaProductId=${metaProductId}",type="redirect"),
	@Result(name="TO_EDIT_HOTEL",location="/meta/toEditHotel.do?metaProductId=${metaProductId}",type="redirect"),
	@Result(name="TO_EDIT_ROUTE",location="/meta/toEditRoute.do?metaProductId=${metaProductId}",type="redirect"),
	@Result(name="TO_EDIT_OTHER",location="/meta/toEditOther.do?metaProductId=${metaProductId}",type="redirect"),
	@Result(name="TO_EDIT_TRAFFIC",location="/meta/toEditTraffic.do?metaProductId=${metaProductId}",type="redirect"),
	
	@Result(name="TO_ADD_TICKET",location="/meta/toAddTicket.do",type="redirect"),
	@Result(name="TO_ADD_HOTEL",location="/meta/toAddHotel.do",type="redirect"),
	@Result(name="TO_ADD_ROUTE",location="/meta/toAddRoute.do",type="redirect"),
	@Result(name="TO_ADD_OTHER",location="/meta/toAddOther.do",type="redirect"),
	@Result(name="TO_ADD_TRAFFIC",location="/meta/toAddTraffic.do",type="redirect")
})
public class MetaProductAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3474466938167361376L;
	private MetaProductService metaProductService;
	private MetaProductBranchService metaProductBranchService;
	private EbkSuperClientService ebkSuperClientService;
	private ProdProductService prodProductService;
	protected final Log log =LogFactory.getLog(this.getClass().getName());
	private String search;
	private String productType;// = Constant.PRODUCT_TYPE.TICKET.name();
	private String subProductType;
	private String branchType;
	private Long metaProductId;
	private Long adultQuantity;
	private Long childQuantity;
	private String testtt;
	public String getTesttt() {
		return testtt;
	}

	/**
	 * ajax读取产品信息.
	 */
	@Action("/meta/searchMetaList")
	public void searchMetaList(){
		Map<String,Object> param = new HashMap<String,Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("productName", search);
			if(search.matches("\\d+")){
				param.put("orProductId", NumberUtils.toLong(search));
			}
		}
		if(StringUtils.isNotEmpty(productType)){
			param.put("productType", productType);
		}
		if(StringUtils.isNotEmpty(subProductType)){
			param.put("subProductType", subProductType);			
		}
		param.put("valid", "Y");
		List<MetaProduct> list = metaProductService.findMetaProduct(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			try{
				for(MetaProduct mp:list){
					JSONObject obj=new JSONObject();
					obj.put("id", mp.getMetaProductId());
					obj.put("text", mp.getProductName());
					array.add(obj);
				}
			}catch(Exception ex){				
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	/**
	 * 检查一个采购类别是否与提供的参数对应的相同
	 * 检查的值包含类别类型，成人数，儿童数
	 * @param branch
	 * @return
	 */
	private boolean checkSameType(MetaProductBranch branch){
		return StringUtils.equals(branch.getBranchType(), branchType)
				&& branch.getAdultQuantity().equals(adultQuantity)
				&& branch.getChildQuantity().equals(childQuantity);
	}
	
	@Action("/meta/getMetaBranchJSON")
	public void getMetaBranchJSON(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(metaProductId,"采购产品不存在");
			List<MetaProductBranch> list=metaProductBranchService.selectBranchListByProductId(metaProductId,"Y");
			boolean find=false;
			if(CollectionUtils.isNotEmpty(list)){
				
				JSONArray array=new JSONArray();
				for(MetaProductBranch branch:list){
					//类别类型为空或不为空的情况下必须一置
					if(branchType==null||(branchType!=null&& checkSameType(branch))){
						JSONObject obj=new JSONObject();
						obj.put("branchId", branch.getMetaBranchId());
						obj.put("branchName", branch.getBranchName());
						array.add(obj);
					}
				}
				if(!array.isEmpty()){
					find=true;
					result.put("list",array);
				}
			}
			result.put("find", find);
		}catch(Exception ex){
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	/**
	 * 跳转到不同的编辑页面
	 * @return
	 */
	@Action("/meta/toEditProduct")
	public String toEditProduct(){
		if (metaProductId == null || metaProductId < 1) {
			return "PAGE_NOT_FOUND";
		}
		MetaProduct mp=metaProductService.getMetaProduct(metaProductId);
		String page;
		if(mp==null){
			page="PAGE_NOT_FOUND";
		}else{
			page="TO_EDIT_".concat(mp.getProductType());
		}
		return page;
	}
	
	@Action("/meta/toAddProduct")
	public String toAddProduct(){
		String page = "PAGE_NOT_FOUND";
		if(StringUtils.isNotEmpty(productType)){			
			page="TO_ADD_"+productType.trim();
		}
		return page;
	}
	
	@Action("/meta/changeValid")
	public void changValid(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(metaProductId,"修改的对象不存在");
			MetaProduct mp=metaProductService.getMetaProduct(metaProductId);
			Assert.notNull(mp,"修改的对象不存在");
			
			mp.setValid(StringUtils.equalsIgnoreCase("Y", mp.getValid())?"N":"Y");
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("metaProductId", mp.getMetaProductId());
			map.put("valid", mp.getValid());
			map.put("validStr", mp.getStrValid());
			map.put("productName", mp.getProductName());
			metaProductService.changeMetaProductValid(map,getOperatorNameAndCheck());
			
			/**
			 * 修改中间表酒店产品信息
			 * vst
			 */
			if (isNotSwitch()) {
				log.info("start updateVstEbkSuperProd method");
				MetaProduct metaProduct1=metaProductService.getMetaProduct(mp.getMetaProductId());  //采购对象
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
				log.info("update vst updateEbkSuperProd,MetaProductId:"+metaProduct1.getMetaProductId()+",saleFlag:"+flag);
				String flags="N";
				if(flag==true){
					flags="Y";
				}
				Map<MetaProduct,String> maps=new HashMap<MetaProduct,String>();
				maps.put(metaProduct1, flags);
				ArrayList<String> results=ebkSuperClientService.updateEbkSuperProd(maps).getReturnContent();
				for (String item : results) {
					String [] strings = item.split("#"); 
					if ("0".equals(strings[1])) {
						log.error("Call vst RPC service 'updateEbkSuperProd' error:"+strings[0]+"  操作失败！");
					}else if("1".equals(strings[1])){
						log.info("Call vst RPC service 'updateEbkSuperProd' "+strings[0]+"  success.");
					}
				}
			}
			result.put("butValid", mp.getButValid());
			result.put("strValid", mp.getStrValid());
			result.put("valid", mp.getValid());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setEbkSuperClientService(EbkSuperClientService ebkSuperClientService) {
		this.ebkSuperClientService = ebkSuperClientService;
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
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * @param metaProductService the metaProductService to set
	 */
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	/**
	 * @param metaProductId the metaProductId to set
	 */
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	/**
	 * @param metaProductBranchService the metaProductBranchService to set
	 */
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	/**
	 * @return the metaProductId
	 */
	public Long getMetaProductId() {
		return metaProductId;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @param subProductType the subProductType to set
	 */
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	/**
	 * @param branchType the branchType to set
	 */
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}

	/**
	 * @param adultQuantity the adultQuantity to set
	 */
	public void setAdultQuantity(Long adultQuantity) {
		this.adultQuantity = adultQuantity;
	}

	/**
	 * @param childQuantity the childQuantity to set
	 */
	public void setChildQuantity(Long childQuantity) {
		this.childQuantity = childQuantity;
	}

	
}
