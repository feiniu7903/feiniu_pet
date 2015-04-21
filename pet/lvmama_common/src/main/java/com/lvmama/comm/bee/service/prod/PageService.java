package com.lvmama.comm.bee.service.prod;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.prod.LimitSaleTime;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.RouteProduct;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.vo.view.ViewProdProductJourneyDetail;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.ProductResult;
 
public interface PageService {
	ProdCProduct getProdCProduct(long productId);
	ProdProduct getProdProductByProductId(Long productId);
	ProdProductBranch getProdBranchByProdBranchId(Long prodBranchId);
	ProdProductBranch selectDefaultBranchByProductId(Long productId);
	/**
	 * 读取一个产品的在线的非附加类别.
	 * @param productId
	 * @return
	 */
	List<ProdProductBranch> getProdBranchListByProductId(Long productId);
	 Map<String, Object> getProdCProductInfo(long productId, boolean isPreview);
	 boolean isResourceConfirm(Long prodBranchId,Date visitDate);
 	/**
	 * @author yuzhibing
	 * @param productId
	 * @return
	 */
	ViewPage getViewPageByproductId(long productId);
	
	 ViewPage getViewPage(Long id);
	/**
	 * 
	 * @author yuzhibing
	 * @param pageId
	 * @return
	 */
	List<ViewJourney> getViewJourneyByProductId(long productId);
	/**
	 * 
	 * @author yuzhibing
	 * @param productId
	 * @return
	 */
	RouteProduct getPageRouteProductByProductId(long productId);

	/**
	 * 产品查询关联景区或者目的地
	 * @param productId
	 * @return
	 */
	Place getToDestByProductId(Long productId);
	/**
	 * 是否是团购产品
	 * @param productId
	 * @return
	 */
	boolean isGroupProduct(Long productId);
	
	/**
	 * 销售产品是否可售
	 * @param id
	 * @param choseDate
	 * @return
	 */
	 boolean checkDateCanSale(Long id, Date choseDate);

	 LimitSaleTime getDateCanSale(Long id, Date choseDate);
	/**
	 * 
	 * @param productId
	 * @param prodBranchId
	 * @param visitDate
	 * @return
	 */
	boolean isVisitDateProduct(Long productId,Long prodBranchId,Date visitDate);
	
	/**
	 * 根据产品ID获取产品类别信息
	 * @param productId 产品编号
	 * @param additional 附加类型。false:非附加类型，true:附加类型
	 * @return
	 */
	List<ProdProductBranch> getProductBranchByProductId(Long productId, String additional);
	
	
	/**
	 * 读取一个产品的一天的行程数据.
	 * @param productId
	 * @param visitTime
	 * @param adult
	 * @param child
	 * @return
	 */
	ViewProdProductJourneyDetail getProductJourneyFromProductId(
			Long productId, Date visitTime, Long adult, Long child);
	
	/**
	 * 填充行程小贴士
	 * @param viewJourneyList 行程列表
	 */
	public List<ViewJourney> fillJourneyTipsList(List<ViewJourney> viewJourneyList);
	
	
	/**
	 * 查询一个产品ID是否存在产品，在80000以下的数据不存在取类别当中的产品ID跳转
	 * @param id
	 * @return
	 */
	ProductResult findProduct(Long id);
	MetaProduct getMetaProductByMetaProductId(Long metaProductId);
	public List<ViewJourney> getViewJourneyByMultiJourneyId(long multiJourneyId);
	/**查询产品是否存在内部提示信息*/
	boolean isInteriorExist(Long productId);
}
