package com.lvmama.pet.sweb.mark.coupon;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.mark.ProductTypeEle;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.mark.MarkCouponProductService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.pub.ComLogService;

public class CouponBackBaseAction extends BackBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 6545702346668511862L;
	/**
	 * 订单优惠批次和产品对应关系的远程服务
	 */
	protected MarkCouponProductService markCouponProductService;
	/**
	 * 优惠券批次远程服务
	 */
	protected MarkCouponService markCouponService;

	protected ProdProductService prodProductService;
	protected ComLogService comLogService;

	/**
	 * 保存操作日志.
	 * @param objectType 表名.
	 * @param objectId  objectId
	 * @param operatorName operatorName
	 * @param logType logType
	 * @param logName logName
	 * @param content content
	 */
	protected void saveComLog(final String objectType, final Long objectId,
			final String operatorName, final String logType,
			final String logName, final String content) {
		final ComLog log = new ComLog();
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		log.setContent(content);
		comLogService.addComLog(log);
	}

	/**
	 * 将产品子类型列表转换为CodeItem列表 ，以提供页面显示.
	 * @param subProductTypeList 产品子类型列表.
	 * @return CodeItem列表.
	 */
	protected List<CodeItem> productTypeEleList2CodeItemList(List<ProductTypeEle> subProductTypeList) {
		List<CodeItem> result = new ArrayList<CodeItem>();
		for (ProductTypeEle e : subProductTypeList) {
			CodeItem ci = new CodeItem();
			ci.setName(e.getSubProductTypeName());
			ci.setCode(e.getSubProductType());
			ci.setChecked(Boolean.toString(e.isChecked()));
			result.add(ci);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	protected String initUrl(String actionDo) {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> pns = getRequest().getParameterNames();
		while (pns.hasMoreElements()) {
			String pn = pns.nextElement();
			if ("page".equalsIgnoreCase(pn)) {
				continue;
			} 
			if ("productName".equalsIgnoreCase(pn)) {
				try {
					sb.append(pn + "=" + new String(getRequest().getParameter(pn).getBytes("ISO-8859-1"),"utf-8") + "&");
				} catch (UnsupportedEncodingException e) {
				
				}
				continue;
			}
			
			sb.append(pn + "=" + getRequest().getParameter(pn) + "&");
		}
		return "pet_back/mark/coupon/" + actionDo + "?" + sb.toString() + "page=argPage";
		
	}
	
	public void setMarkCouponProductService(
			MarkCouponProductService markCouponProductService) {
		this.markCouponProductService = markCouponProductService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

}
