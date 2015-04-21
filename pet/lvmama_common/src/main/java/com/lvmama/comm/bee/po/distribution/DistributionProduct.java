package com.lvmama.comm.bee.po.distribution;

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
import com.lvmama.comm.utils.StringUtil;

/**
 * 分销产品信息
 * @author lipengcheng
 *
 */
public class DistributionProduct implements Serializable{
	private static final long serialVersionUID = 5859273289544354715L;
	private Long distributionProductId;
	/** 分销商ID */
	private Long distributorInfoId;
	/** 驴妈妈销售产品ID */
	private Long productId;
	/** 销售产品类别*/
	private Long productBranchId;
	/** 分销产品的录入时间 */
	private Date createTime;
	/**
	 * 是否有效（黑名单）
	 */
	private String volid;
	
	private ViewPage viewPage;
	private ProdProduct prodProduct;
	private Place fromDest;
	private Place toDest;
	private List<DistributorInfo> distributorList;
	// 行程说明
	private List<ViewJourney> viewJourneyList;
	
	//产品返现金额
	private Long commentsCashback;
	
	private String needAutoUpdateCashBack;
	
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
	
	public ProdRoute getProdRoute() {
		return (ProdRoute) this.prodProduct;
	}

	public ProdTicket getProdTicket() {
		return (ProdTicket) this.prodProduct;
	}

	public ProdHotel getProdHotel() {
		return (ProdHotel) this.prodProduct;
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
	/**
	 * 取得产品的支付方式(京东)
	 * @return
	 */
	public String getPaymentTypeForJingdong() {
		if (Boolean.valueOf(prodProduct.getPayToLvmama())) {
			return "1";// 在线支付
		} else {
			return "2";// 支付给景区
		}
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
		if (fromDest == null) {
			fromDest = new Place();
		}
		return fromDest;
	}

	public void setFromDest(Place fromDest) {
		this.fromDest = fromDest;
	}

	public Place getToDest() {
		if (toDest == null) {
			toDest = new Place();
		}
		return toDest;
	}


	public void setToDest(Place toDest) {
		this.toDest = toDest;
	}

	public Long getDistributionProductId() {
		return distributionProductId;
	}

	public void setDistributionProductId(Long distributionProductId) {
		this.distributionProductId = distributionProductId;
	}

	public Long getDistributorInfoId() {
		return distributorInfoId;
	}

	public void setDistributorInfoId(Long distributorInfoId) {
		this.distributorInfoId = distributorInfoId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public List<DistributorInfo> getDistributorList() {
		return distributorList;
	}

	public void setDistributorList(List<DistributorInfo> distributorList) {
		this.distributorList = distributorList;
	}

	public List<ViewJourney> getViewJourneyList() {
		return viewJourneyList;
	}

	public void setViewJourneyList(List<ViewJourney> viewJourneyList) {
		this.viewJourneyList = viewJourneyList;
	}

	public String getVolid() {
		return volid;
	}

	public void setVolid(String volid) {
		this.volid = volid;
	}

	public Long getCommentsCashback() {
		return commentsCashback;
	}

	public void setCommentsCashback(Long commentsCashback) {
		this.commentsCashback = commentsCashback;
	}

	public String getNeedAutoUpdateCashBack() {
		return StringUtil.replaceNullStr(needAutoUpdateCashBack);
	}

	public void setNeedAutoUpdateCashBack(String needAutoUpdateCashBack) {
		this.needAutoUpdateCashBack = needAutoUpdateCashBack;
	}
	
	
}
