package com.lvmama.pet.sweb.seo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendBlock;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.RecommendBlockService;
import com.lvmama.comm.vo.Constant;

/**
 * @author zuozhengpeng
 *
 */
@Results({ 
	@Result(name = "success", location = "/WEB-INF/pages/back/seo/recommend/recommendBlock.jsp"),
	@Result(name = "recommendBlock", type="redirect", location="/seo/recommendBlock.do?pageChannel=${pageChannel}&palceId={placeId}"),
	@Result(name = "getRecommendInfoSource", location = "/WEB-INF/pages/back/seo/recommend/getRecommendInfo.jsp")
	})
public class RecommendBlockAction extends com.lvmama.comm.BackBaseAction{
	private static final long serialVersionUID = -1933262201783089923L;
	private RecommendBlockService recommendBlockService;
	private ProductSearchInfoService productSearchInfoService;
	private PlaceService placeService; 

	private Long id;
	/**
	 * 需要添加到推荐表中的对象Id（目的地酒店景区产品等的id）
	 */
	private Long objectId;
	private String keywords;
	private Map<String, Object> param;
	private RecommendBlock recommendBlock = new RecommendBlock();
	private List<List<Object>> recos = new ArrayList<List<Object>>();
	private List<Place> places = new ArrayList<Place>();
	private List<Place> mudidi = new ArrayList<Place>();
	private List<Place> hotels = new ArrayList<Place>();
	private List<ProductSearchInfo> productsList = new ArrayList<ProductSearchInfo>();
	private RecommendBlock sonBlock;
	private String pageChannel = "";
	private String productType;
	private String autoPlaceName;
	private String autoFromPlaceName;
	/**目的地/景点ID*/
	private String placeId = "";
	private boolean checkBlock;
	private Long fromPlaceId;
	private Long toPlaceId;


	@Action("/seo/recommendBlock")
	public String execute() throws Exception {
		createBlockCheck(checkBlock);
		if (StringUtils.isNotEmpty(this.keywords)){
			this.keywords = this.keywords.trim();
		}else{
			this.keywords = "";
		}
		//目的地推荐
		if(!"".equals(placeId)){
			this.getSession().setAttribute("placeId", placeId);
		}else{
			if("dest".equals(pageChannel)&&!"".equals(this.getSession().getAttribute("placeId"))){
				placeId=(String) this.getSession().getAttribute("placeId");
			}
		}
		
		param = new HashMap<String, Object>();
		param.put("name", this.keywords);
		param.put("pageChannel", pageChannel);
		param.put("dataCode", placeId);
		param.put("parent", "true");
		List<RecommendBlock> lis = recommendBlockService.queryRecommendBlockByParam(param);
		for (RecommendBlock recommendBlock : lis) {
			List<Object> lis1 = new ArrayList<Object>();
			lis1.add(recommendBlock);
			
			param = new HashMap<String, Object>();
 			param.put("pageChannel", pageChannel);
			param.put("parentRecommendBlockId", recommendBlock.getRecommendBlockId());
			List<RecommendBlock> sub = recommendBlockService.queryRecommendBlockByParam(param);
			lis1.add(sub);
			recos.add(lis1);
		}

		if (this.id != null && this.id > 0) {
			recommendBlock = (RecommendBlock) this.recommendBlockService.getRecommendBlockById(id);
			if (recommendBlock.getParentRecommendBlockId()!=null) {
				sonBlock = recommendBlock;
				recommendBlock = (RecommendBlock) this.recommendBlockService.getRecommendBlockById( new Long(recommendBlock.getParentRecommendBlockId()));
			}
		}

		return SUCCESS;
	}
	
	/**
	 * 添加模块
	 * @throws Exception
	 */
	public void addRecommendBlock() throws Exception {
		String json = "{\"flag\":\"false\"}";
		if (recommendBlock != null && StringUtils.isNotEmpty(recommendBlock.getName())) {
			this.recommendBlockService.insertRecommendBlock(recommendBlock);
		}
		json = "{\"flag\":\"true\"}";
		this.responseWrite(json);
	}
	
	public void delRecommendBlock() {
		String json = "{\"flag\":\"false\"}";
		recommendBlockService.deleteRecommendBlockAndInfo(recommendBlock);
		json = "{\"flag\":\"true\"}";
		this.responseWrite(json);
	}

	public String updateRecommendBlock() throws Exception {
		RecommendBlock target = null;
		if (recommendBlock.getRecommendBlockId() != null){
			target = (RecommendBlock) this.recommendBlockService.getRecommendBlockById( recommendBlock.getRecommendBlockId());
		}
		if (target != null && recommendBlock != null && StringUtils.isNotEmpty(recommendBlock.getName())) {
			target.setPageChannel(recommendBlock.getPageChannel());
			target.setName(recommendBlock.getName());
			target.setDataCode(recommendBlock.getDataCode());
			if (target.getParentRecommendBlockId()!=null) {
				if (recommendBlock.getItemNumberLimit() != null && StringUtils.isNotEmpty(recommendBlock.getItemNumberLimit().toString().trim())){
					target.setItemNumberLimit(recommendBlock.getItemNumberLimit());
				}else{
					target.setItemNumberLimit(0l);
				}
			} else {
				target.setItemNumberLimit(recommendBlock.getItemNumberLimit());
				this.placeId = recommendBlock.getDataCode();
			}
			this.recommendBlockService.updateRecommendBlock(target);
		}else{
			this.recommendBlockService.insertRecommendBlock(recommendBlock);
		}
		this.pageChannel=recommendBlock.getPageChannel();
		return "recommendBlock";
	}

	/**
	 * 添加推荐
	 * @return
	 * @author:nixianjun 2013-7-3
	 */
	@Action("/seo/getRecommendInfoSource")
	public String getRecommendInfoSource(){
		if (this.id != null && this.id > 0) {
			sonBlock = (RecommendBlock) this.recommendBlockService.getRecommendBlockById(id);
			//有查询
			if (StringUtils.isNotEmpty(keywords)||objectId!=null||StringUtils.isNotEmpty(productType)||toPlaceId!=null||fromPlaceId!=null) {
				pagination=initPage();
				pagination.setPageSize(15);
				Map<String, Object> param = new HashMap<String, Object>();
				if (sonBlock.getModeType().equalsIgnoreCase("3")) {
					param.put("productId", objectId);
					param.put("productName", keywords);
					if("HOTEL".equals(productType)){
						List<String> list=new ArrayList<String>();
						list.add("HOTEL_SUIT");
						param.put("subProductType", list);
					}
					param.put("productType", productType);
					param.put("placeId", toPlaceId);
					param.put("fromPlaceId", fromPlaceId);
					pagination.setTotalResultSize(productSearchInfoService.countProductSearchInfoByParam(param));
					if(pagination.getTotalResultSize()>0){
						param.put("startRows", pagination.getStartRows());
						param.put("endRows", pagination.getEndRows());
						productsList = productSearchInfoService.queryProductSearchInfoByParam(param);
					}
				} else{
					param.put("isValid", "Y");
					param.put("name", keywords);
					if (sonBlock.getModeType().equalsIgnoreCase("2")) {
						param.put("stage", "2");
						param.put("placeId", objectId);
						pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
						if(pagination.getTotalResultSize()>0){
							param.put("startRows", pagination.getStartRows());
							param.put("endRows", pagination.getEndRows());
							places = placeService.queryPlaceListByParam(param);
						}
					} else if (sonBlock.getModeType().equalsIgnoreCase("1")) {
						param.put("stage", "1");
						param.put("placeId", objectId);
						pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
						if(pagination.getTotalResultSize()>0){
							param.put("startRows", pagination.getStartRows());
							param.put("endRows", pagination.getEndRows());
							mudidi = placeService.queryPlaceListByParam(param);
						}
					} else if (sonBlock.getModeType().equalsIgnoreCase("5")) {
						param.put("stage", "3");
						param.put("placeId", objectId);
						pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
						if(pagination.getTotalResultSize()>0){
							param.put("startRows", pagination.getStartRows());
							param.put("endRows", pagination.getEndRows());
							hotels = placeService.queryPlaceListByParam(param);
						}
					}else if (sonBlock.getModeType().equalsIgnoreCase("6")) {
						List<String> list=new ArrayList<String>();
						list.add("1");list.add("2");
						param.put("stages", list);
						param.put("placeId", objectId);
						pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
						if(pagination.getTotalResultSize()>0){
							param.put("startRows", pagination.getStartRows());
							param.put("endRows", pagination.getEndRows());
							mudidi = placeService.queryPlaceListByParam(param);
						}
					}
				}
				pagination.buildUrl(getRequest());
			}
		}
		return "getRecommendInfoSource";
	}
	
	public void getRecommendBlockById(){
		RecommendBlock recomBlock=recommendBlockService.getRecommendBlockById(recommendBlock.getRecommendBlockId());	
		this.responseWrite(JSONArray.fromObject(recomBlock).toString());
	}
	
	//根据checkFlag来决定是否判断并创建block
	private void createBlockCheck(boolean checkFlag){
		if (checkFlag) {
			param = new HashMap<String, Object>();
			param.put("pageChannel", pageChannel);
			param.put("dataCode", placeId);
			List<RecommendBlock> blocks = recommendBlockService.queryRecommendBlockByParam(param);
			@SuppressWarnings("unused")
			String[] destPrefixArray = Constant.getMultiDestPrefixArray();
			if(blocks.isEmpty()){
				createNewBlock();
				createCommonBlock(false);
			}else{
				createCommonBlock(true);
			}
		}
	}

	//通用创建block,主要加入了多前缀的判断及创建逻辑
	private void createCommonBlock(boolean isCheck){
		String[] destPrefixArray = Constant.getMultiDestPrefixArray();
		List<RecommendBlock> blocks=null;
		String dataCode="";
		if (destPrefixArray != null && destPrefixArray.length > 0) {
			String[] subPre=null;
			for (String pre : destPrefixArray) {
				subPre=Constant.getMultiPrefixArray(pre);
				if(isCheck){
					dataCode= subPre[0] + "_" + placeId;
					param = new HashMap<String, Object>();
					param.put("dataCode", dataCode);
					blocks = recommendBlockService.queryRecommendBlockByParam(param);
					if(blocks.isEmpty()){
						createPreBlock(subPre,placeId);
					}
				}else{
					createPreBlock(subPre,placeId);
				}
			}
		}
	}
	//创建特推及焦点图block(之前的需求)
	private void createNewBlock() {
		RecommendBlock block = new RecommendBlock();
		block.setDataCode(placeId);
		block.setName("特推");
		block.setLevels(1L);
		block.setPageChannel(pageChannel);
		
		block= recommendBlockService.insertRecommendBlock(block);
		if(block!=null&&block.getRecommendBlockId()!=null){
			Long blockId = block.getRecommendBlockId();
			block = new RecommendBlock();
			block.setDataCode(placeId);
			block.setModeType("3");
			block.setName("焦点图产品");
			block.setParentRecommendBlockId(blockId);
			block.setLevels(2L);
			block.setPageChannel(pageChannel);
			recommendBlockService.insertRecommendBlock(block);
		}
	}
	//创建带前缀的block记录
	private void createPreBlock(String[] prefix,String dataCode){
		param = new HashMap<String, Object>();
		param.put("dataCode", dataCode);
		param.put("levels", 1);
		List<RecommendBlock> blockList=(List<RecommendBlock>)recommendBlockService.queryRecommendBlockByParam(param);
		Long blockId=null;
		if(blockList!=null&&blockList.size()>0){
			blockId=blockList.get(0).getRecommendBlockId();
		}
		RecommendBlock block = new RecommendBlock();
		block.setDataCode(prefix[0]+"_"+dataCode);
		block.setModeType(prefix[1]);
		block.setName(prefix[2]);
		block.setParentRecommendBlockId(blockId);
		block.setLevels(2L);
		block.setPageChannel(pageChannel);
		recommendBlockService.insertRecommendBlock(block);
	}
	
	public void scy() throws IOException {
		com.lvmama.comm.utils.ExeSh.exeSh(Constant.getScyPath());
		getResponse().setContentType("text/json; charset=gb2312");
		getResponse().getWriter().write("{msg:'同步命令已经发出'}");
	}

	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public RecommendBlock getRecommendBlock() {
		return recommendBlock;
	}

	public void setRecommendBlock(RecommendBlock recommendBlock) {
		this.recommendBlock = recommendBlock;
	}
	
	public void setRecommendBlockService(RecommendBlockService recommendBlockService) {
		this.recommendBlockService = recommendBlockService;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public RecommendBlock getSonBlock() {
		return sonBlock;
	}

	public void setSonBlock(RecommendBlock sonBlock) {
		this.sonBlock = sonBlock;
	}
	
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPageChannel() {
		return pageChannel;
	}

	public void setPageChannel(String pageChannel) {
		this.pageChannel = pageChannel;
	}

	public void setCheckBlock(boolean checkBlock) {
		this.checkBlock = checkBlock;
	}

	public List<Place> getPlaces() {
		return places;
	}

	public List<Place> getMudidi() {
		return mudidi;
	}

	public List<Place> getHotels() {
		return hotels;
	}

	public List<ProductSearchInfo> getProductsList() {
		return productsList;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public List<List<Object>> getRecos() {
		return recos;
	}

	public void setRecos(List<List<Object>> recos) {
		this.recos = recos;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public void setToPlaceId(Long toPlaceId) {
		this.toPlaceId = toPlaceId;
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public Long getToPlaceId() {
		return toPlaceId;
	}

	public String getAutoPlaceName() {
		return autoPlaceName;
	}

	public void setAutoPlaceName(String autoPlaceName) {
		this.autoPlaceName = autoPlaceName;
	}

	public String getAutoFromPlaceName() {
		return autoFromPlaceName;
	}

	public void setAutoFromPlaceName(String autoFromPlaceName) {
		this.autoFromPlaceName = autoFromPlaceName;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductType() {
		return productType;
	}
	
}
