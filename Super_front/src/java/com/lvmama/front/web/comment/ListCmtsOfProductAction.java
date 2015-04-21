package com.lvmama.front.web.comment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.ResultPath;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtSpecialSubjectVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;

/**
 * 获取某产品的所有已审核点评
 * @author yuzhizeng
 *
 */
@ParentPackage("lvInputInterceptorPackage")
@ResultPath("/lvInputInterceptor")
@Results({
	@Result(name = "listCmtsOfProduct", location = "/WEB-INF/pages/comment/product/listCmtsOfProduct.ftl", type = "freemarker"),
	@Result(name = "productList", location = "/WEB-INF/pages/comment/product/productList.ftl", type = "freemarker"),
	@Result(name = "relateProductList", location = "/WEB-INF/pages/comment/product/relateProductList.ftl", type = "freemarker")
})
public class ListCmtsOfProductAction  extends CmtBaseAction {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -9156075983404512777L;
	/**
	 * 产品点评分页配置信息
	 */
	private Page<CommonCmtCommentVO> productPageConfig;
	/**
	 * 每页记录数
	 */
	private long productPageSize = 10;
	/**
	 * 当前页
	 */
	private long productPage = 1;
	/**
	 * 相关产品点评分页配置信息
	 */
	private Page<CommonCmtCommentVO> relateProductPageConfig;
	/**
	 * 相关产品每页记录数
	 */
	private long relateProductPageSize = 10;
	/**
	 * 相关产品当前页
	 */
	private long relateProductPage = 1;
	/**
	 * 获取产品的所有点评(扩展实体)
	 */
	private List<CommonCmtCommentVO> cmtCommentVOList;
	/**
	 * 相关产品所有点评
	 */
	private List<CommonCmtCommentVO> relateProductCmtCommentVOList = new ArrayList<CommonCmtCommentVO>();
	/**
	 * 产品ID
	 */
	private long productId;
	/**
	 * 产品ID列表
	 */
	private String productIDs;
	/**
	 * 获取产品的点评统计信息
	 */
	private CmtTitleStatisticsVO cmtTitleStatisticsVO;
	/**
	 * 专题
	 */
	private List<CmtSpecialSubjectVO> cmtSpecialSubjectList = new ArrayList<CmtSpecialSubjectVO>();
	/**
	 * 首页推荐小驴说事列表
	 */
	private List<CmtSpecialSubjectVO> newsList = new ArrayList<CmtSpecialSubjectVO>();
	/**
	 * 是否显示分页
	 */
	private String productPageFlag = "Y";
	/**
	 * 是否显示分页
	 */
	private String relateProductPageFlag = "Y";
	/**
	 * 维度平均分统计
	 */
	private List<CmtLatitudeStatistics> cmtLatitudeStatisticsList;
	/**
	 * 用户最新产品点评列表
	 */
	private List<CommonCmtCommentVO> lastestUserProductCmtList;
	/**
	 * 城市自游志
	 */
	private Map<String, List<RecommendInfo>> recommendInfoMap;
	//后台LP推荐城市游记
	private static final Long BLOCKID = 15035l;
	private static final String STATION = "LP";
		
	@Action(
			value="/comment/listCmtsOfProduct"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String listCmtsOfProduct(){

		//获取产品点评统计
		cmtTitleStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(productId);
		if (null == cmtTitleStatisticsVO) {
			LOG.error("点评统计记录不存在.");
			return ERROR_PAGE;
		}
		
		cmtTitleStatisticsVO = composeCmtTitleStatistics(cmtTitleStatisticsVO);
		
		//获取城市自游志
		//recommendInfoMap = getYouJiRecInfoByBlockIdAndStationFromCache(BLOCKID, STATION);
		//cmtSpecialSubjectList = getIndexPageOfCmtSpecialSubjectList();
		
		//获得某个产品的点评维度统计平均分
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		this.cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
		
		return "listCmtsOfProduct";
	}
	
	/**
	 * 产品点评
	 * @return
	 */
	@Action(
			value="/comment/getProductList"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String getProductList(){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		parameters.put("isAudit",  Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		Long totalRecords = cmtCommentService.getCommentTotalCount(parameters);
		
		long pageConfigNum = 120;
		pageConfigNum = Math.min(totalRecords, pageConfigNum);//控制页面显示记录数
		this.productPageConfig = Page.page(pageConfigNum, productPageSize, productPage);

		parameters.put("_startRow", this.productPageConfig.getStartRows() + "");
		parameters.put("_endRow", this.productPageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		cmtCommentVOList = cmtCommentService.getCmtCommentList(parameters);
		
		for(CommonCmtCommentVO vo : cmtCommentVOList){
			vo = composeComment(vo);
		}
		cmtCommentVOList = composeUserImagOfComment(cmtCommentVOList);
		
		this.productPageConfig.setItems(this.cmtCommentVOList);
		if(pageConfigNum > productPageSize){
			productPageFlag = "Y";
		}
		else{
			productPageFlag = "N";
		}
		
		UserUser user = getUser();
		
		if(user != null){
			Long userId = getUser().getId();
			//获取用户的三条最新点评
			if(userId != null && productPage == 1){
				lastestUserProductCmtList = getLastestUserProductCmts(userId, productId); 
			}
		}	
		if (productPageConfig.getItems().size() > 0) {
			productPageConfig.setUrl("/comment/getProductList.do?productId="+productId+"&productPage=");
		}
		//获取点评发布的图片
		//cmtCommentService.fillApprovedPicture(cmtCommentVOList);
		
		return "productList";
	}
	
	/**
	 * 相关产品点评
	 * @return
	 */
	@Action(
			value="/comment/getRelateProductList"		
			,interceptorRefs={
					@InterceptorRef(value = "commentParamCheckInterceptor"),
					@InterceptorRef("defaultStack")
			}
	)
	public String getRelateProductList() {
       if(this.productIDs != null)//为了保护如果睿广取不到产品ID，但是用户还是看了相关产品评论页
       {
   		String[] productIDsArray = this.productIDs.split(",");
		List<String> productIDsList = new ArrayList<String>();
		for (String productID : productIDsArray) {
			productIDsList.add(productID);
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("productIds", productIDsList);
		parameters.put("isAudit", Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		Long totalRecords = cmtCommentService.getCommentTotalCount(parameters);	
		long pageConfigNum = 120;	
		pageConfigNum = Math.min(totalRecords, pageConfigNum);//控制页面显示记录数
		relateProductPageConfig = Page.page(pageConfigNum, relateProductPageSize, relateProductPage);
		
		parameters.put("_startRow", relateProductPageConfig.getStartRows() + "");
		parameters.put("_endRow", relateProductPageConfig.getEndRows() + "");
		parameters.put("createTime321", "true");
		
		List<CommonCmtCommentVO> list= cmtCommentService.getCmtCommentList(parameters);
		for(CommonCmtCommentVO vo : list){
			CommonCmtCommentVO productCmtCommentVO = composeComment(vo);
			relateProductCmtCommentVOList.add(productCmtCommentVO);
		}
		relateProductCmtCommentVOList = composeUserImagOfComment(relateProductCmtCommentVOList);
		relateProductPageConfig.setItems(relateProductCmtCommentVOList);
		
		if(pageConfigNum > relateProductPageSize){
			relateProductPageFlag = "Y";
		}
		else{
			relateProductPageFlag = "N";
		}
		
		if (relateProductPageConfig.getItems().size() > 0) {
			relateProductPageConfig.setUrl("/comment/getRelateProductList.do?productIDs="+productIDs+"&relateProductPage=");
		}
		//获取点评发布的图片
		//cmtCommentService.fillApprovedPicture(relateProductCmtCommentVOList);
       } else{
    	   relateProductCmtCommentVOList = new ArrayList<CommonCmtCommentVO>();
       }
		
		return "relateProductList";
	}
	
	
	
	/**
	 * 获取用户当前产品最新的三条点评
	 * @return
	 */
	private List<CommonCmtCommentVO> getLastestUserProductCmts(Long userId, long productId) {
		
		Map<String, Object> lastestCmtParams = new HashMap<String, Object>();
		lastestCmtParams.put("userId", userId);
		lastestCmtParams.put("productId", productId);
		lastestCmtParams.put("_startRow", 0);
		lastestCmtParams.put("_endRow", 3);
		lastestCmtParams.put("createTime321", "createTime321");
		lastestCmtParams.put("isHide", "displayall");
		return cmtCommentService.getCmtCommentList(lastestCmtParams);
	}
	
	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	/**
	 * @param cmtCommentVOList the cmtCommentVOList to set
	 */
	public void setCmtCommentVOList(List<CommonCmtCommentVO> cmtCommentVOList) {
		this.cmtCommentVOList = cmtCommentVOList;
	}

	/**
	 * @return the cmtCommentVOList
	 */
	public List<CommonCmtCommentVO> getCmtCommentVOList() {
		return cmtCommentVOList;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(long productId) {
		this.productId = productId;
	}

	/**
	 * @return the productId
	 */
	public long getProductId() {
		return productId;
	}

	/**
	 * @return the cmtSpecialSubjectList
	 */
	public List<CmtSpecialSubjectVO> getCmtSpecialSubjectList() {
		return cmtSpecialSubjectList;
	}

	/**
	 * @return the newsList
	 */
	public List<CmtSpecialSubjectVO> getNewsList() {
		return newsList;
	}

	/**
	 * @param productPageConfig the productPageConfig to set
	 */
	public void setProductPageConfig(Page<CommonCmtCommentVO> productPageConfig) {
		this.productPageConfig = productPageConfig;
	}

	/**
	 * @return the productPageConfig
	 */
	public Page<CommonCmtCommentVO> getProductPageConfig() {
		return productPageConfig;
	}

	/**
	 * @param productIDs the productIDs to set
	 */
	public void setProductIDs(String productIDs) {
		this.productIDs = productIDs;
	}

	/**
	 * @return the productIDs
	 */
	public String getProductIDs() {
		return productIDs;
	}

	/**
	 * @param relateProductPageConfig the relateProductPageConfig to set
	 */
	public void setRelateProductPageConfig(Page<CommonCmtCommentVO> relateProductPageConfig) {
		this.relateProductPageConfig = relateProductPageConfig;
	}

	/**
	 * @return the relateProductPageConfig
	 */
	public Page<CommonCmtCommentVO> getRelateProductPageConfig() {
		return relateProductPageConfig;
	}

	/**
	 * @param productPageSize the productPageSize to set
	 */
	public void setProductPageSize(long productPageSize) {
		this.productPageSize = productPageSize;
	}

	/**
	 * @return the productPageSize
	 */
	public long getProductPageSize() {
		return productPageSize;
	}

	/**
	 * @param productPage the productPage to set
	 */
	public void setProductPage(long productPage) {
		this.productPage = productPage;
	}

	/**
	 * @return the productPage
	 */
	public long getProductPage() {
		return productPage;
	}

	/**
	 * @param relateProductPageSize the relateProductPageSize to set
	 */
	public void setRelateProductPageSize(long relateProductPageSize) {
		this.relateProductPageSize = relateProductPageSize;
	}

	/**
	 * @return the relateProductPageSize
	 */
	public long getRelateProductPageSize() {
		return relateProductPageSize;
	}

	/**
	 * @param relateProductPage the relateProductPage to set
	 */
	public void setRelateProductPage(long relateProductPage) {
		this.relateProductPage = relateProductPage;
	}

	/**
	 * @return the relateProductPage
	 */
	public long getRelateProductPage() {
		return relateProductPage;
	}

	/**
	 * @param relateProductCmtCommentVOList the relateProductCmtCommentVOList to set
	 */
	public void setRelateProductCmtCommentVOList(
			List<CommonCmtCommentVO> relateProductCmtCommentVOList) {
		this.relateProductCmtCommentVOList = relateProductCmtCommentVOList;
	}

	/**
	 * @return the relateProductCmtCommentVOList
	 */
	public List<CommonCmtCommentVO> getRelateProductCmtCommentVOList() {
		return relateProductCmtCommentVOList;
	}

	/**
	 * @param cmtLatitudeStatisticsList the cmtLatitudeStatisticsList to set
	 */
	public void setCmtLatitudeStatisticsList(
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList) {
		this.cmtLatitudeStatisticsList = cmtLatitudeStatisticsList;
	}

	/**
	 * @return the cmtLatitudeStatisticsList
	 */
	public List<CmtLatitudeStatistics> getCmtLatitudeStatisticsList() {
		return cmtLatitudeStatisticsList;
	}

	/**
	 * @param productPageFlag the productPageFlag to set
	 */
	public void setProductPageFlag(String productPageFlag) {
		this.productPageFlag = productPageFlag;
	}

	/**
	 * @return the productPageFlag
	 */
	public String getProductPageFlag() {
		return productPageFlag;
	}

	/**
	 * @param relateProductPageFlag the relateProductPageFlag to set
	 */
	public void setRelateProductPageFlag(String relateProductPageFlag) {
		this.relateProductPageFlag = relateProductPageFlag;
	}

	/**
	 * @return the relateProductPageFlag
	 */
	public String getRelateProductPageFlag() {
		return relateProductPageFlag;
	}

	/**
	 * @param lastestUserProductCmtList the lastestUserProductCmtList to set
	 */
	public void setLastestUserProductCmtList(
			List<CommonCmtCommentVO> lastestUserProductCmtList) {
		this.lastestUserProductCmtList = lastestUserProductCmtList;
	}

	/**
	 * @return the lastestUserProductCmtList
	 */
	public List<CommonCmtCommentVO> getLastestUserProductCmtList() {
		return lastestUserProductCmtList;
	}

	public CmtTitleStatisticsVO getCmtTitleStatisticsVO() {
		return cmtTitleStatisticsVO;
	}

	public void setCmtTitleStatisticsVO(CmtTitleStatisticsVO cmtTitleStatisticsVO) {
		this.cmtTitleStatisticsVO = cmtTitleStatisticsVO;
	}	

	public Map<String, List<RecommendInfo>> getRecommendInfoMap() {
		return recommendInfoMap;
	}

	public static String getStation() {
		return STATION;
	}
	
}
