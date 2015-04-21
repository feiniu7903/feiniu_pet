package com.lvmama.front.web.myspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.OrderAndComment;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComIps;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.CommentUtil;
import com.lvmama.comm.utils.IPMap;
import com.lvmama.comm.vo.ComIpsAreaData;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtPlaceTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CmtProdTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.comm.vo.comment.PlaceCmtCommentVO;
import com.lvmama.comm.vo.comment.ProductCmtCommentVO;

/**
 * 我的点评Action层
 * @author yuzhizeng
 *
 */
@Results({
	@Result(name = "myComment", location = "/WEB-INF/pages/myspace/sub/myComment.ftl", type = "freemarker"),
	@Result(name = "error", location = "/error.jsp", type = "redirect")
})
public class MyCommentAction extends SpaceBaseAction {
	
	/**
	 *  序列
	 */
	private static final long serialVersionUID = -6488044410901037765L;
	
	private final static int PAGE_SIZE = 10;
	
	private long currentPoint;  //积分
	private float awardBalanceYuan;  //奖金余额元
	
	private CashAccountService cashAccountService;
	
	private CmtCommentService cmtCommentService;
	
	private OrderService orderServiceProxy;
	
	private CmtTitleStatistisService cmtTitleStatistisService;

	private PlaceService placeService;
	
	private ProdProductService prodProductService;
	
	/**
	 * 待点评订单产品分页配置信息
	 */
	private long needProductCommentCount;  //待产品点评数
	private Page<CmtProdTitleStatisticsVO> needProductCommentPageConfig;
	private int currentNeedProductCommentPage = 1;
	private PlaceSearchInfoService placeSearchInfoService;
	
	
	/**已点评分页配置信息
	 * 
	 */
	private long countOfUserCmt; //已点评数
	private Page<CommonCmtCommentVO> alreadyCommentPageConfig;
	private int currentAlreadyCommentPage = 1;
	
	
	/**
	 * 待点评景点分页配置信息
	 * 
	 */
	private long needPlaceCommentCount; //待景点点评数
	private Page<CmtPlaceTitleStatisticsVO> needPlaceCommentPageConfig;
	private int currentNeedPlaceCommentPage = 1;

	
	
	private void initPage(String type){

		if(type.equals("needComment"))
		{
			//设置待点评产品信息
			setNeedProductCommentInfoByUsersOrder(true);
			setAlreadyCommentInfo(false);
		}
		else if(type.equals("alreadyComment"))
		{
			//设置用户已点评信息
			setAlreadyCommentInfo(true);
			setNeedProductCommentInfoByUsersOrder(false);
		}

		
		//设置待点评景点信息
		setNeedPlaceCommentInfoByUsersOrder();
		CashAccount cashAccount=cashAccountService.queryCashAccountByUserId(getUser().getId());
		if(cashAccount == null)
		{
			this.setAwardBalanceYuan(0); //转换为元
		}
		else
		{
			this.setAwardBalanceYuan(cashAccount.getBonusBalanceFloat()); //转换为元
		}
		
	}
	
	/**
	 * 设置待点评产品信息
	 * @return
	 */
	private void setNeedProductCommentInfoByUsersOrder(boolean needSetPageInfo)
	{
		List<CmtProdTitleStatisticsVO> needProductCommentInfoList = new ArrayList<CmtProdTitleStatisticsVO>();
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(getUser().getUserId());
		
		for(int i = 0; i < canCommentOrderProductList.size(); i++)
		{
			OrderAndComment canCommentOrderProduct = canCommentOrderProductList.get(i);
			Map<String,Object> parameters = new HashMap<String, Object>();
			parameters.put("orderId", canCommentOrderProduct.getOrderId());
			parameters.put("productId", canCommentOrderProduct.getProductId());
			parameters.put("isHide", "displayall");
			List<CommonCmtCommentVO> cmtCommentList = cmtCommentService.getCmtCommentList(parameters);
			if(cmtCommentList == null || cmtCommentList.size() == 0)//该订单产品未被点评过，可以点评
			{
				CmtProdTitleStatisticsVO cmtProdTitleStatisticsVO = CommentUtil.composeProdTitleStatistics(canCommentOrderProduct);
				needProductCommentInfoList.add(cmtProdTitleStatisticsVO);
			}
		}
		needProductCommentCount = needProductCommentInfoList.size();
		
		if(needSetPageInfo)
		{
			//分页处理(注意这是一个“伪动态”分页，其实所有LIST数据都已经取出了)
			needProductCommentPageConfig = new Page<CmtProdTitleStatisticsVO>(needProductCommentCount, PAGE_SIZE, currentNeedProductCommentPage);		
			needProductCommentPageConfig.setItems(needProductCommentInfoList.subList((currentNeedProductCommentPage-1)*PAGE_SIZE, 
					Math.min((currentNeedProductCommentPage-1)*PAGE_SIZE+PAGE_SIZE, needProductCommentInfoList.size())));
			if (needProductCommentPageConfig.getItems().size() > 0) {
				this.needProductCommentPageConfig.setUrl("/myspace/share/comment.do?currentAlreadyCommentPage="+currentAlreadyCommentPage+"&currentNeedProductCommentPage=");
			}
		}
	}
	
	/**
	 * 设置待点评景点信息
	 */
	private void setNeedPlaceCommentInfoByUsersOrder()
	{
		List<CmtPlaceTitleStatisticsVO> needPlaceCommentInfoList = new ArrayList<CmtPlaceTitleStatisticsVO>();
		//获取可点评的订单信息
		List<OrderAndComment> canCommentOrderProductList = orderServiceProxy.selectCanCommentOrderProductByUserNo(getUser().getUserId());
		for(int i = 0; i < canCommentOrderProductList.size(); i++)
		{
			OrderAndComment canCommentOrderProduct = canCommentOrderProductList.get(i);
			Long prodId =canCommentOrderProduct.getProductId();
			//调用接口获得place
	
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("productId", prodId);
			//获取景点基本统计对象
			List<CmtTitleStatisticsVO> cmtTitleStatisticsVOList = cmtTitleStatistisService.getCommentStatisticsList(parameters);
			if(cmtTitleStatisticsVOList.size() > 0)
			{
				//获取产品对应的标的
				List<Place> placeList = placeService.getPlaceByProductId(prodId);
				for(int j = 0; j < placeList.size(); j++)
				{
					//将景点详细信息注入
					PlaceSearchInfo placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(placeList.get(j).getPlaceId());

					CmtPlaceTitleStatisticsVO cmtPlaceTitleStatisticsVO = CommentUtil.composePlaceTitleStatistics(cmtTitleStatisticsVOList.get(0), placeSearchInfo);
					needPlaceCommentInfoList.add(cmtPlaceTitleStatisticsVO);
				}
			}
			
			if(needPlaceCommentInfoList.size() >= 20)//保持最多只取20条
			{
				needPlaceCommentInfoList = needPlaceCommentInfoList.subList(0, 20);
				break;
			}
		}
		
		//添加没有待点评订单时候的景区推荐
		if(canCommentOrderProductList.size()==0){
			String requestIP=getRequest().getRemoteAddr(); 
//			String requestIP="202.98.96.68";
			ComIps comIpsArea = ComIpsAreaData.selectComIpsAreaByIp(IPMap.stringToLong(requestIP));
			 List<Place> places=null;
			if(comIpsArea!=null){
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("city", comIpsArea.getCityName());
				param.put("endRows", 12);
				places=placeService.queryPlaceListByParam(param);
			}
			if(places!=null&&places.size()>0){
				for(int j = 0; j < places.size(); j++)
				{
					//将景点详细信息注入
					PlaceSearchInfo placeSearchInfo = placeSearchInfoService.getPlaceSearchInfoByPlaceId(places.get(j).getPlaceId());
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("placeId", places.get(j).getPlaceId());
					//获取景点基本统计对象
					List<CmtTitleStatisticsVO> cmtTitleStatisticsVOList = cmtTitleStatistisService.getCommentStatisticsList(parameters);
					if(cmtTitleStatisticsVOList.size()>0){
						CmtPlaceTitleStatisticsVO cmtPlaceTitleStatisticsVO = CommentUtil.composePlaceTitleStatistics(cmtTitleStatisticsVOList.get(0), placeSearchInfo);
						needPlaceCommentInfoList.add(cmtPlaceTitleStatisticsVO);
					}
				}
			}
		}
		
		
		needPlaceCommentCount = needPlaceCommentInfoList.size();

		//分页处理(注意这是一个“伪动态”分页，其实所有LIST数据都已经取出了)
		needPlaceCommentPageConfig = new Page<CmtPlaceTitleStatisticsVO>(needPlaceCommentCount, 4, currentNeedPlaceCommentPage);		
		needPlaceCommentPageConfig.setItems(needPlaceCommentInfoList.subList((currentNeedPlaceCommentPage-1)*4, 
				Math.min((currentNeedPlaceCommentPage-1)*4+4, needPlaceCommentInfoList.size())));
		if (needPlaceCommentPageConfig.getItems().size() > 0) {
			this.needPlaceCommentPageConfig.setUrl("/myspace/share/comment.do?currentNeedPlaceCommentPage=");
		}
	}
	
	/**
	 * 设置用户已点评信息
	 */
	private void setAlreadyCommentInfo(boolean needSetPageInfo)
	{
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userId", getUser().getId());
		parameters.put("isHide", "displayall");//已点评显示所有显示或隐藏的点评
		countOfUserCmt = cmtCommentService.getCommentTotalCount(parameters);
		
		if(needSetPageInfo)
		{
			alreadyCommentPageConfig = new Page<CommonCmtCommentVO>(countOfUserCmt, PAGE_SIZE, currentAlreadyCommentPage);
			parameters.put("createTime321", "Y");
			parameters.put("_startRow", (alreadyCommentPageConfig.getStartRows()) + "");
			parameters.put("_endRow", alreadyCommentPageConfig.getEndRows() + "");
			List<CommonCmtCommentVO> commentList = cmtCommentService.getCmtCommentList(parameters);
			
			//获取产品点评或景点点评额外信息
			for(int i = 0; i < commentList.size(); i++)
			{
				CommonCmtCommentVO commonCmtCommentVO = commentList.get(i);
				if(commonCmtCommentVO.getCmtType().equals(Constant.EXPERIENCE_COMMENT_TYPE) && commonCmtCommentVO.getProductId()!= null)
				{
					ProdProduct product = prodProductService.getProdProductById(commonCmtCommentVO.getProductId());
					if(product != null)
					{
						ProductCmtCommentVO productCmtCommentVO = CommentUtil.composeProductComment(commonCmtCommentVO, product, null);
						commentList.set(i, productCmtCommentVO);
					}
				}
				else//Constant.EXPERIENCE_COMMON_TYPE
				{
					Place place = placeService.queryPlaceByPlaceId(commonCmtCommentVO.getPlaceId());
					PlaceCmtCommentVO placeCmtCommentVO = CommentUtil.composePlaceComment(commonCmtCommentVO, place);
					if(placeCmtCommentVO != null)
					{
						commentList.set(i, placeCmtCommentVO);
					}
				}
			}
			
			alreadyCommentPageConfig.setItems(commentList);
			if (alreadyCommentPageConfig.getItems().size() > 0) {
				this.alreadyCommentPageConfig.setUrl("/myspace/share/alreadyComment.do?currentNeedProductCommentPage="+currentNeedProductCommentPage+"&currentAlreadyCommentPage=");
			}
		}
	}
	

	/**
	 * 用户的待点评
	 * http://www.lvmama.com/comment/myCmt!userCmtList.do
	 * @return 跳转页面
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/share/comment")
	public String myComment() {
		if (!isLogin()) {
			return "error";
		}
		initPage("needComment");
		currentPoint = getUser().getPoint(); 
		
		return "myComment";
	}
	
	
	
	/**
	 * 用户的已点评
	 * http://www.lvmama.com/comment/myCmt!userCmtList.do
	 * @return 跳转页面
	 */
	@SuppressWarnings("unchecked")
	@Action("/myspace/share/alreadyComment")
	public String myAlreadyComment() {
		if (!isLogin()) {
			return "error";
		}
		initPage("alreadyComment");
		currentPoint = getUser().getPoint(); 
		
		return "myComment";
	}


	public long getCountOfUserCmt() {
		return countOfUserCmt;
	}

	public void setCountOfUserCmt(long countOfUserCmt) {
		this.countOfUserCmt = countOfUserCmt;
	}

	public long getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(long currentPoint) {
		this.currentPoint = currentPoint;
	}

	public float getAwardBalanceYuan() {
		return awardBalanceYuan;
	}

	public void setAwardBalanceYuan(float awardBalanceYuan) {
		this.awardBalanceYuan = awardBalanceYuan;
	}

	public CmtCommentService getCmtCommentService() {
		return cmtCommentService;
	}

	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public long getNeedProductCommentCount() {
		return needProductCommentCount;
	}

	public void setNeedProductCommentCount(long needProductCommentCount) {
		this.needProductCommentCount = needProductCommentCount;
	}

	public int getCurrentNeedProductCommentPage() {
		return currentNeedProductCommentPage;
	}

	public void setCurrentNeedProductCommentPage(
			int currentNeedProductCommentPage) {
		this.currentNeedProductCommentPage = currentNeedProductCommentPage;
	}

	public int getCurrentAlreadyCommentPage() {
		return currentAlreadyCommentPage;
	}

	public void setCurrentAlreadyCommentPage(int currentAlreadyCommentPage) {
		this.currentAlreadyCommentPage = currentAlreadyCommentPage;
	}

	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public Page<CmtProdTitleStatisticsVO> getNeedProductCommentPageConfig() {
		return needProductCommentPageConfig;
	}

	public void setNeedProductCommentPageConfig(
			Page<CmtProdTitleStatisticsVO> needProductCommentPageConfig) {
		this.needProductCommentPageConfig = needProductCommentPageConfig;
	}

	public Page<CommonCmtCommentVO> getAlreadyCommentPageConfig() {
		return alreadyCommentPageConfig;
	}

	public void setAlreadyCommentPageConfig(Page<CommonCmtCommentVO> alreadyCommentPageConfig) {
		this.alreadyCommentPageConfig = alreadyCommentPageConfig;
	}

	public CmtTitleStatistisService getCmtTitleStatistisService() {
		return cmtTitleStatistisService;
	}

	public void setCmtTitleStatistisService(CmtTitleStatistisService cmtTitleStatistisService) {
		this.cmtTitleStatistisService = cmtTitleStatistisService;
	}

	public long getNeedPlaceCommentCount() {
		return needPlaceCommentCount;
	}

	public void setNeedPlaceCommentCount(long needPlaceCommentCount) {
		this.needPlaceCommentCount = needPlaceCommentCount;
	}

	public Page<CmtPlaceTitleStatisticsVO> getNeedPlaceCommentPageConfig() {
		return needPlaceCommentPageConfig;
	}

	public void setNeedPlaceCommentPageConfig(
			Page<CmtPlaceTitleStatisticsVO> needPlaceCommentPageConfig) {
		this.needPlaceCommentPageConfig = needPlaceCommentPageConfig;
	}

	public int getCurrentNeedPlaceCommentPage() {
		return currentNeedPlaceCommentPage;
	}

	public void setCurrentNeedPlaceCommentPage(int currentNeedPlaceCommentPage) {
		this.currentNeedPlaceCommentPage = currentNeedPlaceCommentPage;
	}

	public void setPlaceSearchInfoService(PlaceSearchInfoService placeSearchInfoService) {
		this.placeSearchInfoService = placeSearchInfoService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
	
}
