package com.lvmama.front.web.buy;

import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.ord.OrdEContract;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.ord.TravelDescriptionService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.client.EContractClient;
import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.service.econtract.OrdEContractService;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.utils.econtract.EcontractUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;

@Results({
		@Result(name = "viewContract", location = "/WEB-INF/pages/buy/201107/orderContract.ftl", type = "freemarker"),
		@Result(name = "initContract", location = "/WEB-INF/pages/buy/201107/initContract.ftl", type = "freemarker"),
		@Result(name = "viewOrderTravel", location = "/WEB-INF/pages/buy/201107/orderTravel.ftl", type = "freemarker"),
		@Result(name = "initViewOrderTravel", location = "/WEB-INF/pages/buy/201107/initOrderTravel.ftl", type = "freemarker"),
		@Result(name = "emptyContract_guonei", location = "/WEB-INF/pages/buy/201107/econtract/guonei.ftl", type = "freemarker"),
		@Result(name = "emptyContract_chujing", location = "/WEB-INF/pages/buy/201107/econtract/chujing.ftl", type = "freemarker"),
		@Result(name = "emptyContract_xieyi", location = "/WEB-INF/pages/buy/201107/econtract/xieyi.ftl", type = "freemarker"),
		@Result(name ="viewOrderContractAndTravel", location = "/WEB-INF/pages/buy/201107/contractContentAndTravel.ftl", type = "freemarker")})
public class ContractTemplateAction extends BaseAction {

	private static final long serialVersionUID = 3643384954617528510L;
	private static final Logger logger = Logger
			.getLogger(ContractTemplateAction.class);
	private Long orderId;	
	private OrdEContractService ordEContractService;
	private TravelDescriptionService travelDescriptionService;
	private EContractClient contractClient;
	private FSClient fsClient;
	protected OrderService orderServiceProxy;
	private String contractContent;
	private String orderTravel;
	private Long productId;
	private ProdProductService prodProductService;
	
	@Action("/ord/isExistContract") 
	public void isExistContract() throws Exception {
		if(!isLogin()) {
    		this.getResponse().getWriter().print("{success:false}");
    		return;
    	}
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (order == null 
				|| null == this.getBookerUserId() 
				|| !this.getBookerUserId().equals(order.getUserId()) 
				|| null == ordEContractService.queryByOrderId(orderId)) {
		   LOG.info("订单"+orderId+"被userId="+this.getUserId()+"非法访问");
		   this.getResponse().getWriter().print("{success:false}");
		   return;
		} else {
			this.getResponse().getWriter().print("{success:true}");
		}
	}
	
	@Action("/ord/initContract")
	public String initContract() {
		contractContent = "<div>合同正在生成，请稍后......</div>";
		return "initContract";	
	}
	
	@Action("/ord/viewContract")
	public String viewContract() {
		if(!isLogin()){
    		return ERROR;
    	}
    	OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if ( order==null || null == this.getBookerUserId() || !this.getBookerUserId().equals(order.getUserId()) ) {
		   LOG.info("订单"+orderId+"被userId="+this.getUserId()+"非法访问");
		   return ERROR;
		}
		setContractContent();
		return "viewContract";
	}
	/**
	 * 查看空的电子合同
	 * @return
	 */
	@Action("/ord/emptyContract")
	public String emptyContract() {
		ProdEContract prodEContract = prodProductService.getProdEContractByProductId(productId);
		if(prodEContract == null){
			return ERROR;
		}
		String groupType = prodEContract.getGroupType();
		if("BYONESELF".equals(groupType)){
			return "emptyContract_xieyi"; //自组团的显示委托协议书
		}else{
			ProdProduct prod = prodProductService.getProdProduct(productId);
			if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.toString().equals(prod.getSubProductType())){
				return "emptyContract_chujing"; //出境跟团游 显示出境合同
			}else{
				return "emptyContract_guonei"; //其他的显示 国内合同
			}
		}
	}
	@Action("/ord/initViewOrderTravel")
	public String initviewOrderTravel() {
		orderTravel = "正在读取行程，请稍等......";
		return "initViewOrderTravel";	
	}
	
	@Action("/ord/isExistOrderTravel") 
	public void isExistOrderTravel() throws Exception {
		orderTravel = contractClient.loadRouteTravel(orderId);
		if(null==orderTravel){
		   this.getResponse().getWriter().print("{success:false}");
		   return;
		} else {
			this.getResponse().getWriter().print("{success:true}");
		}
	}	

	@Action("/ord/viewOrderTravel")
	public String viewOrderTravel() {
		setTravelContent();
		return "viewOrderTravel";
	}
	@Action("/ord/viewOrderContractAndTravel")
	public String viewOrderContractAndTravel(){
		if(!isLogin()){
    		return ERROR;
    	}
    	OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if ( order==null || null == this.getBookerUserId() || !this.getBookerUserId().equals(order.getUserId()) ) {
		   LOG.info("订单"+orderId+"被userId="+this.getUserId()+"非法访问");
		   return ERROR;
		}
		setContractContent();
		setTravelContent();
		return "viewOrderContractAndTravel";
	}
	
	private void setContractContent(){
		if(isUserOrder()){
			OrdEContract contract = ordEContractService.queryByOrderId(orderId);
			if(null != contract){
				try {
		    		 ComFile comFile = fsClient.downloadFile(contract.getFixedFileId());
		    		 contractContent =new String(comFile.getFileData(),"UTF-8");
					if(null!=contractContent){
						contractContent = contractContent.replaceAll("<traveller>.*</traveller>", "");
					}else{
						contractContent = "<div>合同暂时没有生成，5秒后自动刷新 或 <a href='javascript:document.location.reload();'>[点击刷新]</a></div><script  type=\"text/javascript\">setTimeout(function(){document.location.reload();},5000);</script>";
					}
				} catch (UnsupportedEncodingException e) {
					logger.warn("合同没有生成,显示出错"+e);
					contractContent = "<div>合同暂时没有生成，请联系客服</div>";
				} catch (Exception e) {
					logger.warn("合同没有生成,显示出错"+e);
					contractContent = "<div>合同暂时没有生成，请联系客服</div>";
				}
			}else{
				contractContent = "<div>合同暂时没有生成，5秒后自动刷新 或 <a href='javascript:document.location.reload();'>[点击刷新]</a></div><script  type=\"text/javascript\">setTimeout(function(){document.location.reload();},5000);</script>";
			}
		}
	}
	private void setTravelContent(){
		if(isUserOrder()){
			/*String travelTemplate = EcontractUtil.getOrderTravelTemplate();
			String subTravelTemplate = EcontractUtil.getOrderTravelSubTemplate();;
			Long fileId = travelDescriptionService.viewOrderTravel(orderId);
			if(null == fileId){
				orderTravel = "<div>没有取到订单行程，请联系客服</div>";
			}
			ComFile comFile = fsClient.downloadFile(fileId);
			
			File file = comFile.getFile();
			try {
				String xmlData = new String(FileUtil.getBytesFromFile(file));
				orderTravel = TemplateFillDataUtil.deserializeMap(xmlData, subTravelTemplate, travelTemplate);
			} catch (Exception e) {
				StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), e);
			} */
			orderTravel = contractClient.loadRouteTravel(orderId);
			if(null==orderTravel){
				orderTravel = "<div>没有取到订单行程，请联系客服</div>";
			}
		}
	}
	private boolean isUserOrder(){
		if(null == getUser()){
			orderTravel = contractContent = "您还没有登录，暂不能查看内容";
			return Boolean.FALSE;
		}
		String userId = getUser().getUserId();
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(!userId.equals(order.getUserId())){
			orderTravel = contractContent = "您的订单里没有此订单号的订单";
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	  

	public void setOrdEContractService(OrdEContractService ordEContractService) {
		this.ordEContractService = ordEContractService;
	}

	public TravelDescriptionService getTravelDescriptionService() {
		return travelDescriptionService;
	}

	public void setTravelDescriptionService(
			TravelDescriptionService travelDescriptionService) {
		this.travelDescriptionService = travelDescriptionService;
	}

	public String getContractContent() {
		return contractContent;
	}

	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}

	public String getOrderTravel() {
		return orderTravel;
	}

	public void setOrderTravel(String orderTravel) {
		this.orderTravel = orderTravel;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public FSClient getFsClient() {
		return fsClient;
	}

	public void setFsClient(FSClient fsClient) {
		this.fsClient = fsClient;
	}

	public void setContractClient(EContractClient contractClient) {
		this.contractClient = contractClient;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	
}