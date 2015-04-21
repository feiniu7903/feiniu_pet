package com.lvmama.bee.web.eplace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.bee.vo.ord.PerformDetail;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.vo.Constant;

@Results({ 
	@Result(name = "query", location = "/WEB-INF/pages/eplace/passport/unPassport.jsp")
	})
/**
 * 查询通关类(jsp版).
 * 
 * @author huangl
 */
public class QueryUndoPassPortAction extends EbkBaseAction {
	private static final long serialVersionUID = 1L;
	private OrderService orderServiceProxy ;
	private CompositeQuery compositeQuery;
	private PerformDetailRelate performDetailRelate;
	private Map<String,List<PerformDetail>> orderMap;
	private PassCodeService passCodeService;
	private PerformTargetService performTargetService;
	/**
	 * 查询条件，至少存在一项
	 */
	private String port_mobile;
	private String port_orderId;
	private String port_userName;
	private String port_passPort;
	private String playTimeStart;
	private String playTimeEnd;
	private String passwordCertificate;
	
	/**
	 * 综合查询， 通过手机号，订单号，通关码等查询订单列表.
	 */
	@Action(value="/eplace/queryPassPort")
	public String queryPassPort() {
		if (checkData(port_mobile, port_orderId, port_userName, port_passPort,passwordCertificate)) {
			compositeQuery = new CompositeQuery();
			performDetailRelate = new PerformDetailRelate();
			//用户产品权限限制
			List<Long> branchIds=(ArrayList<Long>)getSession(Constant.Session_EBOOKING_USER_META_BRANCH_LIST);
			performDetailRelate.setBranchIds(branchIds);
			performDetailRelate.setPerformStatus(Constant.ORDER_PERFORM_STATUS.UNPERFORMED);
			//订单状态限制
			performDetailRelate.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			
			/* 根据供手机号码查询 */
			if (StringUtils.isNotEmpty(port_mobile)) {
				performDetailRelate.setContactMobile(port_mobile.trim());
			}
			/* 根据订单编号查询 */
			if (StringUtils.isNotEmpty(port_orderId)) {
				performDetailRelate.setOrderId(Long.valueOf(port_orderId.trim()));
			}
			/* 根据供姓名查询 */
			if (StringUtils.isNotEmpty(port_userName)) {
				performDetailRelate.setContactName(port_userName.trim());
			}
			/* 根据密码券查询 */
			if (StringUtils.isNotEmpty(passwordCertificate)) {
				performDetailRelate.setPasswordCertificate(passwordCertificate.trim());
			}
			//按照辅助码查询
			if(StringUtils.isNotBlank(port_passPort)){
				List<PassCode> passCodes=passCodeService.getPassCodeByAddCode(port_passPort);
				if(passCodes!=null && passCodes.size()>0){
					PassCode passCode=passCodes.get(0);
					if(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name().equalsIgnoreCase(passCode.getObjectType())){
						performDetailRelate.setOrderItemMetaId(passCode.getObjectId());
					}else{
						//按照辅助码查询时要判断订单号是否传入
						if(StringUtils.isBlank(port_orderId) || port_orderId.equals(passCode.getObjectId().toString())){
							performDetailRelate.setOrderId(passCode.getObjectId());
						}else{
							return "query";
						}
					}
				}else{
					return "query";
				}
			}
			compositeQuery.setPerformDetailRelate(performDetailRelate);
			List<PerformDetail> list = new ArrayList<PerformDetail>();

			if(CollectionUtils.isNotEmpty(branchIds))
			{
				list = orderServiceProxy.queryPerformDetailForEplaceList(compositeQuery);
			}
			for(PerformDetail item:list){
				List<MetaPerform> metaPerformLst=performTargetService.getMetaPerformByMetaProductId(item.getMetaProductId());
				if(metaPerformLst!=null && metaPerformLst.size()>0){
					item.setTargetId(metaPerformLst.get(0).getTargetId());
				}
			}
			orderMap=getMergePerformDetail(list);
		}
		return "query";
	}
	
	/**
	 * 如果条件数据都为空，返回fasle
	 * @return
	 */
	private boolean checkData(String port_mobile,String port_orderId,String port_userName,String port_passPort,String passwordCertificate){
		if(StringUtils.isBlank(port_mobile)&&StringUtils.isBlank(port_orderId)&&StringUtils.isBlank(port_userName)&&StringUtils.isBlank(port_passPort)&&StringUtils.isBlank(passwordCertificate)){
			return false;
		}
		return true;
	}
	/**
	 * 合并相同履行对象订单
	 * @param list
	 * @return
	 */
	private Map<String,List<PerformDetail>> getMergePerformDetail( List<PerformDetail> list){
		Map<String,List<PerformDetail>> orderMap=new HashMap<String,List<PerformDetail>>();
		for(PerformDetail performDetail:list){
			String key=""+performDetail.getOrderId()+"_"+performDetail.getTargetId();
			if(orderMap.containsKey(key)){
				List<PerformDetail> innerPds=orderMap.get(key);
				innerPds.add(performDetail);
			}else{
				List<PerformDetail> innerPds=new ArrayList<PerformDetail>();
				innerPds.add(performDetail);
				orderMap.put(key, innerPds);
			}
		}
		Map<String,List<PerformDetail>> backMap=new TreeMap<String,List<PerformDetail>>(new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				String[] os1=o1.split("_");
				String[] os2=o2.split("_");
				if(os1[0].equals(os2[0])){
					return (new Long(os2[1])).compareTo(new Long(os1[1]));
				}else{
					return (new Long(os2[0])).compareTo(new Long(os1[0]));
				}
			}
		});
		backMap.putAll(orderMap);
		return backMap;
	}
	
	public String initPageStr(){
		StringBuffer strBuf=new StringBuffer();
		return strBuf.toString();
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}


	public CompositeQuery getCompositeQuery() {
		return compositeQuery;
	}

	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}

	public PerformDetailRelate getPerformDetailRelate() {
		return performDetailRelate;
	}

	public void setPerformDetailRelate(PerformDetailRelate performDetailRelate) {
		this.performDetailRelate = performDetailRelate;
	}

	public String getPort_mobile() {
		return port_mobile;
	}
	public void setPort_mobile(String port_mobile) {
		this.port_mobile = port_mobile;
	}
	public String getPort_orderId() {
		return port_orderId;
	}
	public void setPort_orderId(String port_orderId) {
		this.port_orderId = port_orderId;
	}
	public String getPort_userName() {
		return port_userName;
	}
	public void setPort_userName(String port_userName) {
		this.port_userName = port_userName;
	}
	public String getPort_passPort() {
		return port_passPort;
	}
	public void setPort_passPort(String port_passPort) {
		this.port_passPort = port_passPort;
	}
	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}
	public Map<String, List<PerformDetail>> getOrderMap() {
		return orderMap;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	public String getPlayTimeStart() {
		return playTimeStart;
	}
	public void setPlayTimeStart(String playTimeStart) {
		this.playTimeStart = playTimeStart;
	}
	public String getPlayTimeEnd() {
		return playTimeEnd;
	}
	public void setPlayTimeEnd(String playTimeEnd) {
		this.playTimeEnd = playTimeEnd;
	}
	public String getPasswordCertificate() {
		return passwordCertificate;
	}
	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}
}
