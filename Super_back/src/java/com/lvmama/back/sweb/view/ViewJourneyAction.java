package com.lvmama.back.sweb.view;

import com.lvmama.back.sweb.prod.ProdViewPageBaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Results({
	@Result(name = "input", location = "/WEB-INF/pages/back/view/view_journey.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/view_journey_auditing_show.jsp"),
	@Result(name = "goAction", location ="/view/toViewJourney.do?productId=${productId}&multiJourneyId=${multiJourneyId}",type="redirect"),
	@Result(name = "goViewMultiJourney", location="/view/toViewMultiJourney.do?productId=${productId}",type="redirect"),
	@Result(name = "toViewMultiJourneyAuditingShow", location="/view/toViewMultiJourneyAuditingShow.do?productId=${productId}",type="redirect")
	})
public class ViewJourneyAction extends ProdViewPageBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2659538242533611086L;
	private ViewPageJourneyService viewPageJourneyService;
	private ProdProductPlaceService prodProductPlaceService;
	private String traffic;
	private String journeyTarget[];
	private ViewJourney viewJourney = new ViewJourney();// 表单数据bean
	private List<ViewJourney> dataList;// 表格数据
	private List<CodeItem> prodPlaceList = new ArrayList<CodeItem>();// 游玩景点列表
	private Long seq;
	private String title;
	private String content;
	private String dinner;
	private String breakfast;
	private String lunch;
	private String supper;
	private String hotel;
	private Long journeyId;
	private Long multiJourneyId;
	private String multiJourneyName;
	private ViewMultiJourneyService viewMultiJourneyService;
	private String[] trafficListValues;
	public String[] getTrafficListValues() {
		return trafficListValues;
	}
	public void setTrafficListValues(String[] trafficListValues) {
		this.trafficListValues = trafficListValues;
	}
	/***/
	
	public ViewJourneyAction() {
		super();
		setMenuType("journey");		
	}
	
	
	/* (non-Javadoc)
	 * @see com.lvmama.back.sweb.prod.ProductAction#doBefore()
	 */
	@Override
	public boolean doBefore() {
		// TODO Auto-generated method stub
		if(!super.doBefore()){
			return false;
		}
		if(!(product instanceof ProdRoute)){
			return false;
		}
		return true;
	}

    @Action(value="/view/toViewJourneyAuditingShow")
    public String toViewJourneyAuditingShow(){
        String resultName = this.goEdit();
        if(resultName.equals("input")){
            return "auditingShow";
        }else if(resultName.equals("goViewMultiJourney")){
            return "toViewMultiJourneyAuditingShow";
        }else {
            return resultName;
        }

    }
	/**
	 * 页面载入显示
	 * */
	@Override
	@Action(value="/view/toViewJourney")
	public String goEdit() {
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		ProdRoute pr = (ProdRoute)product;
		//多行程
		if(pr.hasMultiJourney() && multiJourneyId == null) {
			return "goViewMultiJourney";
		} else {//单行程
		    this.loadDataList();
			this.initProductPlaceList();
			return goAfter();
		}
	}

	/**
	 * 添加行程
	 * zx
	 */
	@Override
	@Action("/view/saveProductJourney")
	public void save() {
		JSONResult result=new JSONResult();
		
		try {
			Assert.notNull(viewJourney.getProductId(),"产品信息不存在");
			
			
			StringBuffer sb = new StringBuffer("");
			if(trafficListValues != null && trafficListValues.length >0){
				for (int i = 0; i < trafficListValues.length; i++) {
					sb.append(trafficListValues[i]).append(",");
				}
				viewJourney.setTraffic(sb.toString().substring(0, sb.toString().length()-1));
			}
			//String temp = "早餐："+getBreakfast() +" , 午餐："+getLunch()+" , 晚餐："+getSupper();
			//viewJourney.setDinner(temp);
			viewJourney.setPageId(viewJourney.getProductId());
			
			checkViewPage(viewJourney.getProductId());			
			
			boolean hasNew = (viewJourney.getJourneyId() == null);
			if(viewJourney.getJourneyId() == null){
				viewPageJourneyService.insertViewJourney(viewJourney,getOperatorNameAndCheck());
			}else{
				viewPageJourneyService.updateViewJourney(viewJourney,getOperatorNameAndCheck());
			}
			
			viewJourney.setTrafficDesc(CodeSet.getInstance().getCodeName(Constant.CODE_TYPE.TRAFFIC_TYPE.name(), viewJourney.getTraffic()));
			
			prodProductService.markProductSensitive(viewJourney.getProductId(), hasSensitiveWord);
			result.put("productId", viewJourney.getProductId());
			result.put("productJourney", viewJourney);
			result.put("hasNew", hasNew);
			removeProductCache(viewJourney.getProductId());
			//发送修改销售产品的通知ebk消息
			productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(viewJourney.getProductId()));
		} catch (Exception e) {
			result.raise(e);
		}
		result.output(getResponse());
	}
	
	@Action("/view/editJourney")
	public void toEditJourney(){
		JSONResult result=new JSONResult();
		try {
			Assert.notNull(journeyId,"行程信息不存在");
			ViewJourney viewJourney = viewPageJourneyService.loadViewJourney(journeyId);
			if(null != viewJourney){
				result.put("productJourney", viewJourney);
			}else{
				result.put("productJourney", null);
			}
		} catch (Exception e) {
			result.raise(e);
		}
		result.output(getResponse());
	}
	
	/**
	 * 删除
	 * */
	@Action("/view/doDelete")
	public String doDelete(){
		viewPageJourneyService.deleteViewJourney(journeyId,this.getOperatorName());
		this.loadDataList();
		//重新记录敏感词标识
		prodProductService.checkAndUpdateIsHasSensitiveWords(productId);
		//发送修改销售产品的通知ebk消息
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productId));
		return "goAction";
	}
	
	/**
	 * 添加
	 * */
	@Action("/view/insertPlace")
	public String insertPlace(){
		viewJourney.setTraffic(traffic);
		viewJourney.setProductId(productId);
		List<String> placeList=new ArrayList<String>();
		if(!ArrayUtils.isEmpty(journeyTarget)){
			for (String a:journeyTarget) {
				placeList.add(a);
			}
			viewJourney.setProdTargetId(placeList);
		}
		viewJourney.setDinner(dinner);
		viewJourney.setHotel(hotel);
		viewJourney.setSeq(seq);
		viewJourney.setPageId(productId);
		viewJourney.setTitle(title);
		viewJourney.setContent(content);
		if (this.viewJourney.getJourneyId() != null && this.viewJourney.getJourneyId() > 0) {
			this.viewPageJourneyService.updateViewJourney(viewJourney,this.getOperatorName());
			this.loadDataList();
		} else {
			this.viewPageJourneyService.insertViewJourney(viewJourney,this.getOperatorName());
			this.loadDataList();
			this.dataList.add(viewJourney);
		}
		return "goAction";
	}
	
	/**
	 * 加载
	 * */
	public void loadDataList() {
		if(this.productId != null) {
			ProdProduct prod = prodProductService.getProdProduct(productId);
			if(prod.isRoute()) {
				ProdRoute pr = (ProdRoute) prod;
				if(pr.hasMultiJourney()) {
					if(multiJourneyId != null) {
						dataList = viewPageJourneyService.getViewJourneyByMultiJourneyId(multiJourneyId);
						ViewMultiJourney vmj = viewMultiJourneyService.selectByPrimaryKey(multiJourneyId);
						multiJourneyName = vmj.getJourneyName();
					}
				} else {
					dataList = viewPageJourneyService.getViewJourneysByProductId(productId);
				}
			}
		}
	}
	/**
	 * 初始化产品
	 * */
	private void initProductPlaceList(){
		List<ProdProductPlace> placeList = null;
		if(productId != null) {
			placeList = prodProductPlaceService.selectByProductId(productId);
		} 
		for (Iterator<ProdProductPlace> iter = placeList.iterator(); iter.hasNext();) {
			ProdProductPlace place = (ProdProductPlace) iter.next();
			CodeItem codeItem=new CodeItem();
			codeItem.setName(place.getPlaceName());
			codeItem.setCode(String.valueOf(place.getPlaceId()));
			this.prodPlaceList.add(codeItem);
		}		
	}
	

	
	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public String getHotel() {
		return hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public ViewJourney getViewJourney() {
		return viewJourney;
	}

	public void setViewJourney(ViewJourney viewJourney) {
		this.viewJourney = viewJourney;
	}

	public List<ViewJourney> getDataList() {
		return dataList;
	}

	public void setDataList(List<ViewJourney> dataList) {
		this.dataList = dataList;
	}

	public List<CodeItem> getTrafficList() {
		return CodeSet.getInstance().getCachedCodeList(Constant.CODE_TYPE.TRAFFIC_TYPE.name());
	}

	public List<CodeItem> getProdPlaceList() {
		return prodPlaceList;
	}

	public void setProdPlaceList(List<CodeItem> prodPlaceList) {
		this.prodPlaceList = prodPlaceList;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	

	/**
	 * @return the journeyTarget
	 */
	public String[] getJourneyTarget() {
		return journeyTarget;
	}
	/**
	 * @param journeyTarget the journeyTarget to set
	 */
	public void setJourneyTarget(String[] journeyTarget) {
		this.journeyTarget = journeyTarget;
	}
	public Long getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}
	public Long getMultiJourneyId() {
		return multiJourneyId;
	}
	public void setMultiJourneyId(Long multiJourneyId) {
		this.multiJourneyId = multiJourneyId;
	}
	public String getMultiJourneyName() {
		return multiJourneyName;
	}
	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}
}
