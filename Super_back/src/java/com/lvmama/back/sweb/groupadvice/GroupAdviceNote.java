package com.lvmama.back.sweb.groupadvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.utils.DateUtil;

/**
 * 
 * 生成出团通知书文档.<br/>
 * 出团通知书模板分两种,一种在线html模板,一种word模板,
 * 将订单、产品、行程信息写入到模板文件中生成一份完整的出团通知书.
 */
public abstract class GroupAdviceNote {
	public static final String DOCX_TEMPLATE = "DOCX_TEMPLATE";
	public static final String HTML_TEMPLATE = "HTML_TEMPLATE";
	private static final Logger logger = Logger.getLogger(GroupAdviceNote.class);
	protected OrdOrder ordOrder;
	protected ProdRoute prodProduct;
	protected Map<String,Object> dataMap = new HashMap<String,Object>();
	protected ViewPage viewPage;
	
	private List<ViewJourney> viewJourneyList = new ArrayList<ViewJourney>();
	private String templatePath;
	private String fileName;
	
	/**
	 * 根据相应的模板文档类型实例化相应的对象.
	 * @param type 模板文档类型,取值为DOCX_TEMPLATE,HTML_TEMPLATE其中之一.
	 * @return 返回对应的实例对象.
	 */
	public static GroupAdviceNote getInstance(String type) {
		if (DOCX_TEMPLATE.equals(type)) {
			return new DocxTemplateGroupAdviceNote();
		} else if (HTML_TEMPLATE.equals(type)) {
			return new HtmlTemplateGroupAdviceNote();
		} else {
			throw new IllegalArgumentException(type);
		}
	}
	
	
	public String getFileName() {
		if (this.fileName == null) {
			this.fileName = this.createFileName();
		}
		return this.fileName;
	}
	private String createFileName() {
		return GroupAdviceNoteUtils.createFileName(this.ordOrder.getOrderId(), this.fileSuffix());
	}
	protected abstract String fileSuffix();
	
	public void createFile() {
		this.initDataMap();
		
		this.doCreateFile();
		
	}
	
	protected abstract void doCreateFile();
	
	
	protected void initDataMap() {
		 //销售产品名称.
		 this.dataMap.put("productName", this.ordOrder.getMainProduct().getProductName());
		 //订单编号.
		 this.dataMap.put("orderId", this.ordOrder.getOrderId());
		 //客人姓名.
		 this.dataMap.put("travellerInfo",  this.travellerInfo());
		 //旅游日期.
		 this.dataMap.put("travelTimeRange", this.travelTimeRange());
		 //(待定)组团社,采购产品供应商名称.
		 //this.dataMap.put("", "");
		 
		 this.doInitDataMap();
	}
	protected abstract void doInitDataMap();
	
	//客人姓名,列第一个游玩人信息，算出总人数. 例: 张三 等 7位.
	private String travellerInfo() {
		StringBuilder sb = new StringBuilder();
		List<OrdPerson> travellerList = this.ordOrder.getTravellerList();
		if (travellerList != null && !travellerList.isEmpty()) {
			sb.append(travellerList.iterator().next().getName());
			sb.append(" 等 ");
			sb.append(travellerList.size());
			sb.append(" 位.");
		}
		return sb.toString();
	}
	
	//游玩时间起始和结束时间（格式：2012年3月9日-2012-03-12）游玩起始时间：订单的游玩时间
	// 游玩结束时间：订单的游玩时间+行程天数-1
	private String travelTimeRange() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtil.getFormatDate(this.ordOrder.getVisitTime(), "yyyy-MM-dd"));
		sb.append("至");
		//行程天数.
		int days = this.prodProduct.getDays().intValue();
		Date endDate = DateUtil.dsDay_Date(this.ordOrder.getVisitTime(),(days - 1));
		sb.append(DateUtil.getFormatDate(endDate, "yyyy-MM-dd")); 
		return sb.toString();
	}
	
	protected List<ViewJourneyVo> getViewJourneyVoList() {
		List<ViewJourneyVo> result = new ArrayList<ViewJourneyVo>(this.viewJourneyList.size());
		for (ViewJourney e : this.viewJourneyList) {
			result.add(new ViewJourneyVo(e,this.ordOrder.getVisitTime()));
		}
		return result;
	}
	
	public void setProdProduct(ProdRoute prodProduct) {
		this.prodProduct = prodProduct;
	}
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}
	public void setOrdOrder(OrdOrder ordOrder) {
		this.ordOrder = ordOrder;
	}
	public void setViewJourneyList(List<ViewJourney> list) {
		this.viewJourneyList = list;
	}
	public String getTemplatePath() {
		return templatePath;
	}


	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}
	public ViewPage getViewPage() {
		return viewPage;
	}


	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}
	protected static class ViewJourneyVo {
		private ViewJourney viewJourney ;
		private Date visitTime;
		private ViewJourneyVo(ViewJourney viewJourney,Date visitTime) {
			this.viewJourney = viewJourney;
			this.visitTime = visitTime;
			
		}
		//行程的日期. 根据游玩日期和当前行程序列号得出具体的行程日期.
		public String getVisitTime() {
			return DateUtil.getFormatDate(DateUtil.dsDay_Date(this.visitTime,(this.viewJourney.getSeq().intValue() - 1)), "yyyy-MM-dd");
		}
		//行程的内容.
		public String getContent() {
			return this.viewJourney.getContent();
		}
		//早中晚餐.
		public String getDinner() {
			return this.viewJourney.getDinner();
		}
		//住宿.
		public String getHotel() {
			return this.viewJourney.getHotel();
		}
	}
}
