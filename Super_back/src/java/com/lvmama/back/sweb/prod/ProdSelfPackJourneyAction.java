/**
 * 
 */
package com.lvmama.back.sweb.prod;

import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductJourneyService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自主打包产品专用行程打包处理.
 * @author yangbin
 *
 */
@Results({
	@Result(name="input",location="/WEB-INF/pages/back/prod/product_journey.jsp"),
	@Result(name="auditingShow",location="/WEB-INF/pages/back/prod/auditing/product_journey_auditing_show.jsp"),
	@Result(name="edit_journey_pack",location="/WEB-INF/pages/back/prod/product_editJourneyPack.jsp"),
	@Result(name="journey_group_form",location="/WEB-INF/pages/back/prod/journey_group_form.jsp")
})

public class ProdSelfPackJourneyAction extends ProductAction{

	private ProdProductJourneyService prodProductJourneyService;
	private ProdProductPlaceService prodProductPlaceService;
	private ProdProductBranchService prodProductBranchService;
	private PlaceService placeService; 
	
	private List<ProdProductJourney> prodJourneyList;
	private List<ProdProduct> productList;
	private List<ProdProductPlace> prodPlaceList;
	private List<ProdProductJourneyPack> prodProductJourneyPackList;
	private ProdProductJourney prodJourney;
	private ProdJourneyProduct prodJourneyProduct=new ProdJourneyProduct();
	private ProdProductJourneyPack prodProductJourneyPack;
	private String type;
	private String checked;//选中使用
	private Long prodJourneyId;
	private Long packId;
//	private Long prodBranchId;
	

	public ProdSelfPackJourneyAction() {
		super();
		setMenuType("selfpack");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 747825731246840468L;

	@Action(value="/prod/toSelfPackJourneyAuditingShow")
	public String toSelfPackJourneyAuditingShow() {
		this.goEdit();

        return "auditingShow";
	}

	@Override
	@Action(value="/prod/toSelfPackJourney")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		loadProdJourneyList(true);
		prodPlaceList=prodProductPlaceService.selectByProductId(productId);
		return goAfter();
	}

	private void loadProdJourneyList(boolean unique) {
		prodJourneyList=prodProductJourneyService.selectProductJourneyListByProductId(productId);
		if(CollectionUtils.isNotEmpty(prodJourneyList)){
			for(ProdProductJourney ppj:prodJourneyList){//初始化相关的实体
				ppj.setFromPlace(placeService.queryPlaceByPlaceId(ppj.getFromPlaceId()));
				ppj.setToPlace(placeService.queryPlaceByPlaceId(ppj.getToPlaceId()));				
				ppj.setProdJourneyGroup(prodProductJourneyService.selectJourneyProductDetailMap(ppj.getProdJourenyId(),unique));
			}
		}
	}

	@Action("/prod/toEditPack")
	public String to_editJourneyPack(){
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		loadProdJourneyList(false);
		if(packId!=null && packId>0){
			prodProductJourneyPack = prodProductJourneyService.queryProductJourneyPackByPackId(packId);
		}
		if(prodProductJourneyPack==null){
			prodProductJourneyPack = new ProdProductJourneyPack();
			prodProductJourneyPack.setProductId(productId);
			prodProductJourneyPack.setValid("true");
		}
		prodProductJourneyPack.setProdProductJourneys(this.prodJourneyList);
		prodProductJourneyPack.initCheckedProduct();
		return "edit_journey_pack";
	}
	
	@Action("/prod/toSavePack")
	public String to_saveJourneyPack(){
		if(StringUtils.isEmpty(prodProductJourneyPack.getPackName())){
			return "";
		}
		List<String> prodBranchIds = prodProductJourneyPack.getProdBranchIds();
		List<ProdPackJourneyProduct> prodPackJourneyProducts = new ArrayList<ProdPackJourneyProduct>();
		for(String bid:prodBranchIds){
			String ids[] = bid.split("-");
			if(ids!=null&&ids.length==2){
				ProdPackJourneyProduct ppp = new ProdPackJourneyProduct();
				ppp.setProdBranchId(Long.valueOf(ids[1].trim()));
				ppp.setJourneyProductId(Long.valueOf(ids[0].trim()));
				ppp.setProdJourneyPackId(packId);
				prodPackJourneyProducts.add(ppp);
			}
		}
		if(packId!=null){//已有套餐保存更新
			ProdProductJourneyPack oldProdProductJourneyPack = prodProductJourneyService.queryProductJourneyPackByPackId(packId);
			if(oldProdProductJourneyPack!=null){
				oldProdProductJourneyPack.setPackName(prodProductJourneyPack.getPackName());
				oldProdProductJourneyPack.setProdPackJourenyProducts(prodPackJourneyProducts);
				prodProductJourneyService.savePack(oldProdProductJourneyPack,getOperatorNameAndCheck());		
				return "edit_journey_pack";
			}
		}
		//保存新套餐 新套餐默认下线
		prodProductJourneyPack.setProductId(productId);
		prodProductJourneyPack.setOnLine("false");
		prodProductJourneyPack.setProdPackJourenyProducts(prodPackJourneyProducts);
		prodProductJourneyService.savePack(prodProductJourneyPack,getOperatorNameAndCheck());		
		return "edit_journey_pack";
	}
	
	@Override
	@Action("/prod/saveSelfPackJourney")
	public void save() {
		JSONResult result=new JSONResult(getResponse());

		if(prodJourney==null){
			result.raise("行程不存在").output();
			return;
		}
		if(prodJourney.getToPlaceId()==null||prodJourney.getFromPlaceId()==null){
			result.raise("行程标的不可以为空").output();
			return;
		}
		boolean hasNew=prodJourney.getProdJourenyId()==null;
		//酒店门票策略设置为必选
		prodJourney.setTrafficPolicy("true");
		prodJourney.setHotelPolicy("true");
		prodJourney.setTicketPolicy("true");
		ProdProductJourney saveProdJourney = prodProductJourneyService.save(prodJourney, getOperatorNameAndCheck());
		
		JsonConfig config=new JsonConfig();
		config.setJavaPropertyFilter(new PropertyFilter() {
			final String fields[]={"prodJourenyId","fromPlaceId","toPlaceId","journeyTime"};
			@Override
			public boolean apply(Object arg0, String name, Object arg2) {
				if(arg0 instanceof ProdProductJourney){
					return ArrayUtils.contains(fields, name);
				}
				return false;
			}
		});
		JSONObject obj=JSONObject.fromObject(saveProdJourney, config);
		obj.put("journeyTimeStr", saveProdJourney.getJourneyTimeStr());
		PropertyFilter pf=new PropertyFilter() {
			final String fields[]={"placeId","name","stage","provice","city"};
			@Override
			public boolean apply(Object arg0, String name, Object arg2) {
				if(arg0 instanceof Place){
					return ArrayUtils.contains(fields, name);
				}
				return false;
			}
		};
		config.setJavaPropertyFilter(pf);
		
		Place place=placeService.queryPlaceByPlaceId(saveProdJourney.getFromPlaceId());
		if(place!=null){
			JSONObject placeObj=JSONObject.fromObject(place, config);	
			obj.put("fromPlace", placeObj);
		}
		
		place=placeService.queryPlaceByPlaceId(saveProdJourney.getToPlaceId());
		if(place!=null){
			JSONObject placeObj=JSONObject.fromObject(place,config);
			obj.put("toPlace", placeObj);
		}
		result.put("prodJourney", obj);		
		result.put("hasNew", hasNew);			
		
		result.output();
	}
	
	@Action("/prod/saveJourneyTime")
	public void saveJourneyTime(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourney);
			prodJourney.setMinTime(prodJourney.getMaxTime());//现在保证最大值与最小值数据一置
			prodProductJourneyService.changeJourneyTime(prodJourney,getOperatorNameAndCheck());
			result.put("journeyTime", prodJourney.getJourneyTime());
			result.put("journeyTimeStr", prodJourney.getJourneyTimeStr());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 读取一个行程的一类产品
	 */
	@Action("/prod/loadJourneyUniqueProduct")
	public void loadJourneyUniqueProduct(){
		JSONResult result=new JSONResult();
		try{
			boolean find=false;
			List<ProdJourneyProduct> list=prodProductJourneyService.selectJourneyProductUniqueList(prodJourneyId, type);
			if(CollectionUtils.isNotEmpty(list)){
				net.sf.json.JSONArray array=new net.sf.json.JSONArray();
				for(ProdJourneyProduct pp:list){
					array.add(pp.getProdBranch().getProdProduct().getProductName());
				}
				result.put("list", array);
				find=true;
			}
			result.put("find", find);
		}catch(Exception ex){
			result.raise(ex);			
		}
		result.output(getResponse());
	}
	
	/**
	 * 显示一个行程的产品类型相关的产品.
	 * @return
	 */
	@Action(value="/prod/loadJourneyProductList")	
	public String loadJourneyProductList(){
		if(StringUtils.isNotEmpty(type)){
			prodJourney=prodProductJourneyService.selectProductJourneyByPK(prodJourneyId);
			List<ProdJourneyProduct> list=prodProductJourneyService.selectJourneyProductListByJourneyType(prodJourneyId, type);			
			if(CollectionUtils.isNotEmpty(list)){
				Map<Long,ProdProduct> map=new HashMap<Long, ProdProduct>();
				for(ProdJourneyProduct pjp:list){
					if(!map.containsKey(pjp.getProductId())){
						ProdProduct pp=pjp.getProdBranch().getProdProduct();
						pp.setProdJourneyProductList(new ArrayList<ProdJourneyProduct>());
						map.put(pjp.getProductId(), pjp.getProdBranch().getProdProduct());
					}
				}
				
				for(ProdJourneyProduct pjp:list){
					ProdProduct pp=map.get(pjp.getProductId());
					pp.getProdJourneyProductList().add(pjp);
				}
				
				productList=new ArrayList<ProdProduct>();
				for(Long key:map.keySet()){
					productList.add(map.get(key));
				}
			}
		}
		return "journey_group_form";
	}
	
	/**
	 * 删除一个行程段.
	 * 需要删除对应的里面的产品信息
	 */
	@Action("/prod/deleteProdJourney")
	public void delete(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourneyId,"行程不存在");
			prodProductJourneyService.deleteProductJourney(prodJourneyId, getOperatorNameAndCheck());
		}catch(Exception ex){
			result.raise(ex);			
		}
		result.output(getResponse());
	}
	
	
	/**
	 * 添加一个行程产品
	 */
	@Action("/prod/addJourneyProduct")
	public void addJourneyProduct(){
		JSONResult result=new JSONResult(getResponse());
		try{
			Assert.notNull(prodJourneyProduct.getProdBranchId(),"产品类别不可以为空");
			Assert.notNull(prodJourneyProduct.getProdJourenyId(),"行程不存在");
			prodJourneyProduct.setDiscount(0L);
			ResultHandleT<ProdJourneyProduct> pjp=prodProductJourneyService.save(prodJourneyProduct, getOperatorNameAndCheck());
			if(pjp.isFail()){
				result.raise(pjp).output();
				return;
			}
			
			JsonConfig config=new JsonConfig();
			config.setJavaPropertyFilter(new PropertyFilter() {
				
				final String fields[]={"journeyProductId","require","defaultProduct","discount","prodJourenyId"};
				@Override
				public boolean apply(Object obj, String name, Object val) {
					if(obj instanceof ProdJourneyProduct){
						return ArrayUtils.contains(fields, name);
					}
					return false;
				}
			});
			
			JSONObject obj=JSONObject.fromObject(pjp,config);
			
			ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(pjp.getReturnContent().getProdBranchId());
			if(branch!=null){
				config.setJavaPropertyFilter(new PropertyFilter() {
					final String fields[]={"prodBranchId","branchName"};
					@Override
					public boolean apply(Object arg0, String arg1, Object arg2) {
						if(arg0 instanceof ProdProductBranch){
							return ArrayUtils.contains(fields, arg1);
						}
						return false;
					}
				});
				JSONObject branchObj=JSONObject.fromObject(branch,config);
				ProdProduct pp=prodProductService.getProdProduct(branch.getProductId());
				if(pp!=null){
					JSONObject ppObj=new JSONObject();
					ppObj.put("productId", pp.getProductId());
					ppObj.put("productName", pp.getProductName());
					branchObj.put("product",ppObj);					
				}
				obj.put("branch", branchObj);
			}
			
			result.put("journey", obj);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output();
	}
	
	/**
	 * 修改优惠
	 */
	@Action("/prod/changeJPDiscount")
	public void changeJPDiscount(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourneyProduct);
			Assert.notNull(prodJourneyProduct.getJourneyProductId(),"产品信息不存在");
			Assert.notNull(prodJourneyProduct.getDiscount(),"优惠金额为空");
			
			ResultHandle handle = prodProductJourneyService.changeJourneyProdutDiscount(prodJourneyProduct,getOperatorNameAndCheck());
			if(handle.isFail()){
				result.raise(handle).output();
				return;
			}
			result.put("discountYuan", prodJourneyProduct.getDiscountYuan());
		}catch(Exception ex){
			result.raise(ex);
		}		
		result.output(getResponse());
	}
	
	/**
	 * 删除一个行程产品
	 */
	@Action("/prod/deleteJourneyProduct")
	public void deleteJourneyProduct(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourneyProduct.getJourneyProductId(),"产品不存在");
			prodProductJourneyService.deleteJourneyProduct(prodJourneyProduct.getJourneyProductId(), getOperatorNameAndCheck());			
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * 修改一个行程产品的必选属性.
	 * 
	 */
	@Action("/prod/changeJourneyRequire")
	public void changeJourneyRequire(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourneyProduct.getJourneyProductId(),"产品不存在");
			prodProductJourneyService.changeJourneyProductRequire(prodJourneyProduct,checked,getOperatorNameAndCheck());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	/**
	 * 修改一个行程产品的默认属性.
	 * 
	 */
	@Action("/prod/changeJourneyDefault")
	public void changeJourneyDefault(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourneyProduct.getJourneyProductId(),"产品不存在");
			prodProductJourneyService.changeJourneyProductDefault(prodJourneyProduct,checked,getOperatorNameAndCheck());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action("/prod/changeJourneyPolicy")
	public void changeJourneyPolicy(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodJourney);
			Assert.notNull(prodJourney.getProdJourenyId(),"行程不存在");
			Assert.notNull(type,"当前的产品类型不存在");
			
			prodProductJourneyService.changeJourneyPolicy(prodJourney,type,checked,getOperatorNameAndCheck());
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	@Action("/prod/getProdPackListJSON")
	public void getProdPackListJSON(){
		JSONResult result=new JSONResult();
		result.put("find", false);
		try{
			Assert.notNull(productId);
			List<ProdProductJourneyPack> prodProductJourneyPacks = prodProductJourneyService.queryProductJourneyPackByProductId(productId);
			if(prodProductJourneyPacks!=null && prodProductJourneyPacks.size()>0){
				result.put("find", true);
				JSONArray array=new JSONArray();
				for(ProdProductJourneyPack ppjp:prodProductJourneyPacks){
					JSONObject obj=new JSONObject();
					obj.put("packId", ppjp.getProdJourneyPackId());
					obj.put("packName", ppjp.getPackName());
					obj.put("onLine", ppjp.getOnLine());
					JSONArray ppjArr = new JSONArray();
					if(ppjp.getProdProductJourneys() == null) continue;
					for(ProdProductJourney ppj:ppjp.getProdProductJourneys()){
						JSONObject ppjOb=new JSONObject();
						ppjOb.put("journeyTimeStr", ppj.getJourneyTimeStr());
						ppjOb.put("hotelList", getProductArray(ppj.getHotelList()));
						ppjOb.put("trafficList", getProductArray(ppj.getTrafficList()));
						ppjOb.put("routeList", getProductArray(ppj.getRouteList()));
						ppjOb.put("ticketList", getProductArray(ppj.getTicketList()));
						ppjArr.add(ppjOb);
					}
					obj.put("prodProductJourneys",ppjArr);
					array.add(obj);
				}
				result.put("ProdProductJourneyPacks", array);
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}

	private JSONArray getProductArray(List<ProdJourneyProduct> list) {
		JSONArray ppjArr = new JSONArray();
		if(list!=null){
			for(ProdJourneyProduct pp:list){
				JSONObject ppjOb=new JSONObject();
				ppjOb.put("productName", pp.getProdBranch().getProdProduct().getProductName());
				ppjArr.add(ppjOb);
			}
		}
		return ppjArr;
	}

	@Action("/prod/delPack")
	public void delPack(){
		JSONResult result=new JSONResult();
		try{
			if(packId!=null){
				prodProductJourneyService.deletePack(packId,getOperatorNameAndCheck());
			}
			result.put("result", true);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action("/prod/updatePackOnLine")
	public void updatePackValid(){
		JSONResult result=new JSONResult();
		try{
			String onLine = prodProductJourneyPack.getOnLine();
			if(packId!=null){
				prodProductJourneyPack = prodProductJourneyService.queryProductJourneyPackByPackId(packId);
				if(prodProductJourneyPack!=null && !prodProductJourneyPack.getOnLine().equals(onLine)){
					prodProductJourneyPack.setOnLine(onLine);
					prodProductJourneyService.updatePackOnLine(prodProductJourneyPack,getOperatorNameAndCheck());
				}
			}
			result.put("result", true);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	/**
	 * @return the prodJourneyList
	 */
	public List<ProdProductJourney> getProdJourneyList() {
		return prodJourneyList;
	}

	/**
	 * @param prodJourneyList the prodJourneyList to set
	 */
	public void setProdJourneyList(List<ProdProductJourney> prodJourneyList) {
		this.prodJourneyList = prodJourneyList;
	}

	/**
	 * @param prodProductJourneyService the prodProductJourneyService to set
	 */
	public void setProdProductJourneyService(
			ProdProductJourneyService prodProductJourneyService) {
		this.prodProductJourneyService = prodProductJourneyService;
	}
	
	
	public String getProductTitle(Long prodBranchId){
		StringBuffer sb=new StringBuffer();
		ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		if(branch!=null){
			
			ProdProduct pp=prodProductService.getProdProduct(branch.getProductId());
			if(pp!=null){
				sb.append(pp.getProductName());
			}
			sb.append("-【");
			sb.append(branch.getBranchName());
			sb.append("】");
		}		
		return sb.toString();
	}

	/**
	 * @param prodProductBranchService the prodProductBranchService to set
	 */
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param prodJourneyId the prodJourneyId to set
	 */
	public void setProdJourneyId(Long prodJourneyId) {
		this.prodJourneyId = prodJourneyId;
	}
	

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return the prodJourneyId
	 */
	public Long getProdJourneyId() {
		return prodJourneyId;
	}

	/**
	 * @return the prodJourney
	 */
	public ProdProductJourney getProdJourney() {
		return prodJourney;
	}

	/**
	 * @param prodJourney the prodJourney to set
	 */
	public void setProdJourney(ProdProductJourney prodJourney) {
		this.prodJourney = prodJourney;
	}

	/**
	 * @return the prodJourneyProduct
	 */
	public ProdJourneyProduct getProdJourneyProduct() {
		return prodJourneyProduct;
	}

	/**
	 * @param prodJourneyProduct the prodJourneyProduct to set
	 */
	public void setProdJourneyProduct(ProdJourneyProduct prodJourneyProduct) {
		this.prodJourneyProduct = prodJourneyProduct;
	}
	

	/**
	 * 该方法只在type存在的请求当中使用
	 * @return
	 */
	public String getProductTypeStr(){
		return Constant.PRODUCT_TYPE.getCnName(type);
	}
	
	/**
	 * 该方法只在加载产品时才使用
	 * @return
	 */
	public boolean isJourneyPolicy(){		
		return prodJourney!=null&&prodJourney.isPolicy(type);
	}

	/**
	 * @return the prodPlaceList
	 */
	public List<ProdProductPlace> getProdPlaceList() {
		return prodPlaceList;
	}

	/**
	 * @param prodProductPlaceService the prodProductPlaceService to set
	 */
	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	/**
	 * @return the productList
	 */
	public List<ProdProduct> getProductList() {
		return productList;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(String checked) {
		this.checked = checked;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public List<ProdProductJourneyPack> getProdProductJourneyPackList() {
		return prodProductJourneyPackList;
	}

	public void setProdProductJourneyPackList(
			List<ProdProductJourneyPack> prodProductJourneyPackList) {
		this.prodProductJourneyPackList = prodProductJourneyPackList;
	}

	public ProdProductJourneyPack getProdProductJourneyPack() {
		return prodProductJourneyPack;
	}

	public void setProdProductJourneyPack(
			ProdProductJourneyPack prodProductJourneyPack) {
		this.prodProductJourneyPack = prodProductJourneyPack;
	}

	public Long getPackId() {
		return packId;
	}

	public void setPackId(Long packId) {
		this.packId = packId;
	}
	
	
}
