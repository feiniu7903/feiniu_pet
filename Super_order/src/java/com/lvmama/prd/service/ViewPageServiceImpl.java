package com.lvmama.prd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;

import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.po.pub.ComCondition;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.ProdProductDAO;
import com.lvmama.prd.dao.ViewPageDAO;
import com.lvmama.prd.logic.ProductLogic;

public class ViewPageServiceImpl implements ViewPageService {

	private ViewPageDAO viewPageDAO;
	private ProdProductDAO prodProductDAO;
	private ProductLogic productLogic;
	
	private ComLogDAO comLogDAO;
	
	
	public ViewPage getViewPageByProductId(Long productId){
		return viewPageDAO.getViewPageByProductId(productId);
	}
     /**
      * 更新VIEWCONTENT
      */
	public void saveViewContent(ViewPage record,String operatorName) {	
		List<ViewContent> vc = (List<ViewContent>) viewPageDAO
			.selectByPrimaryKey(record.getProductId()).getContentList();
		StringBuffer sb=new StringBuffer();
		for(final ViewContent content:record.getContentList()){
			ViewContent oldContent=(ViewContent)CollectionUtils.find(vc, new Predicate() {
				
				@Override
				public boolean evaluate(Object arg0) {
					ViewContent vv=(ViewContent)arg0;
					return StringUtils.equals(vv.getContentType(),content.getContentType());
				}
			});
			
			if(oldContent==null){
				viewPageDAO.insertViewContent(content);
				sb.append(getLogContent(content.getContentType(), "添加"));
			}else{
				if(oldContent != null && content != null){
					if(!compareObject(oldContent.getContent(), content.getContent())){
						String old=oldContent.getContent();
						if(old==null||old.equals("null")){
							old="";
						}
						String str = getLogContent(content.getContentType(), "更新");
						str += "[原来值："+old+", 新值："+content.getContent()+"]";
						oldContent.setContent(content.getContent());
						oldContent.setMultiJourneyId(content.getMultiJourneyId());
						viewPageDAO.updateViewContent(oldContent);
						sb.append(str);
					}
				}
			}
		}
		comLogDAO.insert("VIEW_PAGE",record.getProductId(),record.getProductId(),operatorName,
				Constant.COM_LOG_PRODUCT_EVENT.editProductDescription.name(),
				"编辑描述信息", sb.toString(), "PROD_PRODUCT");
	}

	
	public List<ComCondition> getAllConditions(List<ProdProduct> products) {
		return productLogic.getAllConditionsByProduct(products);
	}

	public Integer countByParams(Map params) {
		return this.viewPageDAO.countByParams(params);
	}

	public List findByParams(Map params) {
		return this.viewPageDAO.findByParams(params);
	}

	public int deleteByPrimaryKey(Long pageId) {
		return this.viewPageDAO.deleteByPrimaryKey(pageId);
	}

	public ResultHandle addViewPage(ViewPage record) {
		ResultHandle result=new ResultHandle();
		ProdProduct product = prodProductDAO.selectProductDetailByPrimaryKey(record.getProductId());
		if(product==null){
			result.setMsg("产品不存在");
			return result;
		}
		product.setWrapPage("true");
		prodProductDAO.updateByPrimaryKey(product);
		viewPageDAO.insert(record);
		boolean isMultiJourney = false;
		if(product.isRoute()) {
			ProdRoute pr = (ProdRoute) product;
			isMultiJourney = pr.hasMultiJourney();
		}
		record.initContents(isMultiJourney);
		List<ViewContent> contensts = record.getContentList();
		for (ViewContent content : contensts) {
			if (content.getContentId() == null) {
				viewPageDAO.insertViewContent(content);
			}
		}
		return result;
	}

	public boolean isProductUsed(Long productId) {
		Integer count = this.viewPageDAO.countByProductId(productId);
		if (count != null && count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isProductUnUsed(Long productId) {
		Integer count = this.viewPageDAO.countDeleteByProductId(productId);
		if (count != null && count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public int updateValidByProductId(Long productId) {
		return this.viewPageDAO.updateValidByProductId(productId);
	}

	public ViewPage getViewPage(Long pageId) {
		return this.viewPageDAO.selectByPrimaryKey(pageId);
	}

	public int update(ViewPage record) {
		return this.viewPageDAO.updateByPrimaryKey(record);
	}

	public void setViewPageDAO(ViewPageDAO viewPageDAO) {
		this.viewPageDAO = viewPageDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setProductLogic(ProductLogic productLogic) {
		this.productLogic = productLogic;
	}

	public void deleteViewPage(Map params) {
		viewPageDAO.deleteByPageId(params);
	}

	public Integer selectRowCount(Map searchConds) {
		return viewPageDAO.selectRowCount(searchConds);
	}

	public Long copyPage(Long srcPageId, Long productId) {
		ViewPage viewPage = viewPageDAO.selectByProductId(productId);
		if (viewPage != null) {
			viewPageDAO.updateValidByProductId(productId);
		} else {
			viewPage = viewPageDAO.selectByPrimaryKey(srcPageId);
			Long beforePageId = viewPage.getPageId();
			viewPage.setProductId(productId);
			viewPageDAO.insert(viewPage);
			ProdProduct pp = prodProductDAO.selectByPrimaryKey(productId);
			pp.setWrapPage("true");
			prodProductDAO.updateByPrimaryKey(pp);
			Long afterPageId = viewPage.getPageId();
			copyViewContent(viewPage, afterPageId);
		}
		return viewPage.getPageId();
	}

	private void copyViewContent(ViewPage viewPage, Long afterPageId) {
		List<ViewContent> viewContentList = viewPage.getContentList();
		if (viewContentList.size() > 0) {
			for (ViewContent viewContent : viewContentList) {
				viewContent.setContentId(null);
				viewContent.setPageId(afterPageId);
				viewPageDAO.insertViewContent(viewContent);
			}
		}
	}

	public ViewPage selectByProductId(Long productId) {
		return this.viewPageDAO.selectByProductId(productId);
	}
 
	private String getLogContent(String type,String operate){
		StringBuffer sb = new StringBuffer();
		sb.append(operate);
		String typeName="";
		if(type.toUpperCase().equals("MANAGERRECOMMEND"))typeName = "产品经理推荐";
		if(type.toUpperCase().equals("ANNOUNCEMENT"))typeName = "公告";
		if(type.toUpperCase().equals("COSTCONTAIN"))typeName = "费用包含";
		if(type.toUpperCase().equals("NOCOSTCONTAIN"))typeName = "费用不包含";
		if(type.toUpperCase().equals("SHOPPINGEXPLAIN"))typeName = "购物说明";
		if(type.toUpperCase().equals("ORDERTOKNOWN"))typeName = "预订须知";
		if(type.toUpperCase().equals("ACITONTOKNOW"))typeName = "行前须知";
		if(type.toUpperCase().equals("REFUNDSEXPLANATION"))typeName = "退改单提醒";
		if(type.toUpperCase().equals("PLAYPOINTOUT"))typeName = "游玩提示";
		if(type.toUpperCase().equals("TRAFFICINFO"))typeName = "交通信息";
		if(type.toUpperCase().equals("VISA"))typeName = "签证/签注";
		if(type.toUpperCase().equals("FEATURES"))typeName = "产品特色";
		if(type.toUpperCase().equals("INTERIOR"))typeName = "内部提示";
		if(type.toUpperCase().equals("RECOMMENDPROJECT"))typeName = "推荐项目";
		sb.append(typeName);
		sb.append(";");
		return sb.toString();
	}
	
	private boolean compareObject(String obj1,String obj2){
		if(obj1 == null && obj2 == null)return true;
		else if(null != obj1 && null != obj2 && obj1.trim().equals(obj2.trim()))return true;
		else return false;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}
	@Override
	public ViewContent getViewContentByMultiJourneyId(Long multiJourneyId, String contentType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("multiJourneyId", multiJourneyId);
		params.put("contentType", contentType);
		return viewPageDAO.getViewContentByMultiJourneyId(params);
	}
	@Override
	public void insertViewContent(ViewContent content) {
		viewPageDAO.insertViewContent(content);
	}
	
	@Override
	public void updateViewContent(ViewContent content) {
		viewPageDAO.updateViewContent(content);
	}
	@Override
	public ViewContent getViewContentByContentId(Long contentId) {
		return viewPageDAO.getViewContentByContentId(contentId);
	}
	
	@Override
	public List<ViewContent>  getViewContentByMultiJourneyId(Long multiJourneyId) {
		return viewPageDAO.getMJViewContentByMultiJourneyId(multiJourneyId);
	}
}
