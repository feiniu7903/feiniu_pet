package com.lvmama.comm.bee.po.ckdevice;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lvmama.comm.bee.po.prod.ProdHotel;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ProdTicket;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComPicture;
/**
 * 
 * @author gaoxin
 *
 */
public class CkDeviceProduct  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5266828314790013310L;
	private Long deviceProductId;
	private Long deviceInfoId;
	private Long productId;
	private Long productBranchId;
	private Long metaProductId;
	private Long metaBranchId;
	private Date createTime;
	private String volid;
	private String printVolid;
	
	
	private ViewPage viewPage;
	private ProdProduct prodProduct;
	private Place fromDest;
	private Place toDest;
	private List<ViewJourney> viewJourneyList;

	/**
	 * 获得产品所有说明
	 * @return
	 */
	public Map<String, String> initContentMap() {
		Map<String, String> contentMap = new HashMap<String, String>();
		if (viewPage != null) {
			Map<String, Object> map = viewPage.getContents();
			if (map != null) {
				Set<String> keys = map.keySet();
				Iterator<String> it = keys.iterator();
				while (it.hasNext()) {
					ViewContent viewContent = (ViewContent) map.get(it.next());
					contentMap.put(viewContent.getContentType(), viewContent.getContent());
				}
			}
		}
		return contentMap;
	}
	
	public List<ComPicture> initImages() {
		List<ComPicture> pictureList = null;
		if (viewPage != null) {
			pictureList = viewPage.getPictureList();
		}
		return pictureList;
	}
	
	/**
	 * 取得产品的支付方式
	 * @return
	 */
	public String getPaymentType() {
		if (Boolean.valueOf(prodProduct.getPayToLvmama())) {
			return "online";// 在线支付
		} else {
			return "offline";// 支付给景区
		}
	}
	
	public ProdRoute getProdRoute() {
		return (ProdRoute) this.prodProduct;
	}

	public ProdTicket getProdTicket() {
		return (ProdTicket) this.prodProduct;
	}

	public ProdHotel getProdHotel() {
		return (ProdHotel) this.prodProduct;
	}
	
	public Long getDeviceProductId() {
		return deviceProductId;
	}
	public void setDeviceProductId(Long deviceProductId) {
		this.deviceProductId = deviceProductId;
	}
	public Long getDeviceInfoId() {
		return deviceInfoId;
	}
	public void setDeviceInfoId(Long deviceInfoId) {
		this.deviceInfoId = deviceInfoId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductBranchId() {
		return productBranchId;
	}
	public void setProductBranchId(Long productBranchId) {
		this.productBranchId = productBranchId;
	}
	public Long getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(Long metaProductId) {
		this.metaProductId = metaProductId;
	}
	public Long getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getVolid() {
		return volid;
	}
	public void setVolid(String volid) {
		this.volid = volid;
	}
	public ViewPage getViewPage() {
		return viewPage;
	}
	public void setViewPage(ViewPage viewPage) {
		this.viewPage = viewPage;
	}
	public ProdProduct getProdProduct() {
		return prodProduct;
	}
	public void setProdProduct(ProdProduct prodProduct) {
		this.prodProduct = prodProduct;
	}
	public Place getFromDest() {
		return fromDest == null ? new Place() : fromDest;
	}
	public void setFromDest(Place fromDest) {
		this.fromDest = fromDest;
	}
	public Place getToDest() {
		return toDest == null ? new Place() : toDest;
	}
	public void setToDest(Place toDest) {
		this.toDest = toDest;
	}
	public List<ViewJourney> getViewJourneyList() {
		return viewJourneyList;
	}
	public void setViewJourneyList(List<ViewJourney> viewJourneyList) {
		this.viewJourneyList = viewJourneyList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPrintVolid() {
		return printVolid;
	}

	public void setPrintVolid(String printVolid) {
		this.printVolid = printVolid;
	}

	public String getString() {
		return "[deviceProductId=" + deviceProductId
				+ ", deviceInfoId=" + deviceInfoId + ", productId=" + productId
				+ ", productBranchId=" + productBranchId + ", metaProductId="
				+ metaProductId + ", metaBranchId=" + metaBranchId
				+ ", volid=" + volid + ", printVolid=" + printVolid 
				+ "]";
	}
	
}
