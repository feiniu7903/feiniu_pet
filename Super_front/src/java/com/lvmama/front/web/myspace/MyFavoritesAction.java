package com.lvmama.front.web.myspace;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mobile.MobileFavoriteService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
/**
 * 驴妈妈网站手机收藏 
 * @author nixianjun 2013-6-4
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/favorites.ftl", type = "freemarker")
})
public class MyFavoritesAction  extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -4509005317720761354L;
 
	private MobileFavoriteService mobileFavoriteService;
	
	private Long placeCount=0L;
	private Long productCount=0L;
	private Long guideCount=0L;
	private Long tuangouCount=0L;
	private List<MobileFavorite> placeMobileFavoriteList;
	private List<MobileFavorite> productMobileFavoriteList;
	private List<MobileFavorite> guideMobileFavoriteList;
	private List<MobileFavorite> tuangouMobileFavoriteList;
	private Page<MobileFavorite> placePage;
	private Page<MobileFavorite> productPage;
	private Page<MobileFavorite> guidePage;
	private Page<MobileFavorite> tuangouPage;
	
	private long PAGESIZE=9L;
    private long page=1L;
    private String objectType;
    private Long objectId;
    private String objectName;
    private String objectImageUrl;
	private Long id;

 	@Action("/myspace/share/favorites")
	public String execute() {
 		UserUser user = getUser();
 		if (null == user) {
 			return ERROR;
 		}
 		Map<String,Object> param=new HashMap<String, Object>();
 		param.put("userId", user.getId());
 
 		placeFavorites(user,param,this.page);
 		
 		productFavorites(user,param,1L);
 		
 		guideFavorites(user,param,1L);
 		
 		tuangouFavorites(user,param,1L);
 		
 		return SUCCESS;
	}

	/**
	 * 景点收藏
	 * @return
	 */
	@Action("/myspace/share/ticketFavorite")
	public void ticketFavorite() throws Exception{
		Map<String,Object> result=new HashMap<String,Object>();
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("objectId", objectId);
		param.put("objectType",Constant.CLIENT_FAVORITE_TYPE.PLACE.getCode());
		param.put("userId", getUser().getId());
		List<MobileFavorite> list = this.mobileFavoriteService.queryMobileFavoriteList(param);
		if (list == null || list.size()==0) {
			MobileFavorite mf = new MobileFavorite();
			mf.setObjectId(objectId);
			objectName = new String(objectName.getBytes("ISO-8859-1"),"UTF-8");
			mf.setObjectName(objectName);
			mf.setObjectType(Constant.CLIENT_FAVORITE_TYPE.PLACE.getCode());
			mf.setObjectImageUrl(objectImageUrl);
			mf.setUserId(getUser().getId());
			mf.setCreatedTime(new Date());
			this.mobileFavoriteService.insertMobileFavorite(mf);
			result.put("success", "true");
			printRtn(result);
		}else{
			result.put("success", "false");
			result.put("message", "repeat");
			printRtn(result);
		}
	}
	
	/**
     * 收藏团购
     * @return
     */
    @Action("/myspace/share/tuangouFavorite")
    public void tuangouFavorite() throws Exception{
        Map<String,Object> result=new HashMap<String,Object>();
        Map<String,Object> param = new HashMap<String, Object>();
        
        param.put("objectId", objectId);
        param.put("objectType",Constant.CLIENT_FAVORITE_TYPE.TUANGOU.getCode());
        param.put("userId", getUser().getId());
        List<MobileFavorite> list = this.mobileFavoriteService.queryMobileFavoriteList(param);
        if (list == null || list.size()==0) {
            MobileFavorite mf = new MobileFavorite();
            mf.setObjectId(objectId);
            objectName = new String(objectName.getBytes("ISO-8859-1"),"UTF-8");
            mf.setObjectName(objectName);
            mf.setObjectType(Constant.CLIENT_FAVORITE_TYPE.TUANGOU.getCode());
            mf.setUserId(getUser().getId());
            mf.setCreatedTime(new Date());
            this.mobileFavoriteService.insertMobileFavorite(mf);
            result.put("success", "true");
            printRtn(result);
        }else{
            result.put("success", "false");
            result.put("message", "repeat");
            printRtn(result);
        }
    }
	
	/**
	 * 输出返回码
	 * @param request request
	 * @param response response
	 * @param object Ajax返回的对象
	 * @throws IOException IOException
	 */
	protected void printRtn(final Object object) throws IOException {
		String json = null;
		getResponse().setContentType("text/json; charset=utf-8");
		if (null == object) {
			return;
		} else {
			if (object instanceof java.util.Collection) {
				json = JSONArray.fromObject(object).toString();
			} else {
				json = JSONObject.fromObject(object).toString();
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("返回对象:" + json);
			}
		}
		if (getRequest().getParameter("jsoncallback") == null) {
			getResponse().getWriter().print(json);
		} else {
				getResponse().getWriter().print(getRequest().getParameter(
						"jsoncallback") + "(" +json + ")");				
		}
	}
 	
 	@Action("/myspace/share/productFavorites")
	public String productFavorites() {
 		UserUser user = getUser();
 		if (null == user) {
 			return ERROR;
 		}
 		Map<String,Object> param=new HashMap<String, Object>();
 		param.put("userId", user.getId());
 
 		placeFavorites(user,param,1L);
 		
 		productFavorites(user,param,this.page);
 		
 		guideFavorites(user,param,1L);
 		
 		tuangouFavorites(user,param,1L);
 		
 		return SUCCESS;
	}
 	@Action("/myspace/share/guideFavorites")
	public String guideFavorites() {
 		UserUser user = getUser();
 		if (null == user) {
 			return ERROR;
 		}
 		Map<String,Object> param=new HashMap<String, Object>();
 		param.put("userId", user.getId());
 
 		placeFavorites(user,param,1L);
 		
 		productFavorites(user,param,1L);
 		
 		guideFavorites(user,param,this.page);
 		
 		tuangouFavorites(user,param,1L);
 		
 		return SUCCESS;
	}
 	
  	private void placeFavorites(UserUser user,Map<String,Object> param,Long currentPage){
  		//place
 		param.put("objectType", Constant.CLIENT_FAVORITE_TYPE.PLACE.getCode());
 		placeCount= mobileFavoriteService.countMobileFavoriteList(param);
 		//分页
 		placePage=new Page<MobileFavorite>(placeCount);
 		placePage.setUrl("/myspace/share/favorites.do?objectType=PLACE&page=");
 		placePage.setCurrentPage(currentPage);
 		placePage.setPageSize(PAGESIZE);
  		param.put("startRows", placePage.getStartRows());
 		param.put("endRows", placePage.getEndRows());
 		if(placeCount!=null&&placeCount!=0){
 			placeMobileFavoriteList=mobileFavoriteService.queryMobileFavoritePlaceListForHome(param);
 			placePage.setItems(placeMobileFavoriteList);
 		}
  	}
  	
  	private void productFavorites(UserUser user,Map<String,Object> param,Long currentPage){
 		//product
 		param.put("objectType", Constant.CLIENT_FAVORITE_TYPE.PRODUCT.getCode());
 		productCount= mobileFavoriteService.countMobileFavoriteList(param);
 		//分页
 		productPage=new Page<MobileFavorite>(productCount);
 		productPage.setUrl("/myspace/share/productFavorites.do?objectType=PRODUCT&page=");
 		productPage.setCurrentPage(currentPage);
 		productPage.setPageSize(PAGESIZE);
 		param.put("startRows", productPage.getStartRows());
 		param.put("endRows", productPage.getEndRows());
 		if(productCount!=null&&productCount!=0){
 			productMobileFavoriteList=mobileFavoriteService.queryMobileFavoriteProductListForHome(param);
 			productPage.setItems(productMobileFavoriteList);
 		}
  	}
  	
  	private void guideFavorites(UserUser user,Map<String,Object> param,Long currentPage){
  	   //guide
 		param.put("objectType", Constant.CLIENT_FAVORITE_TYPE.GUIDE.getCode());
 		guideCount= mobileFavoriteService.countMobileFavoriteList(param);
 		//分页
 		guidePage=new Page<MobileFavorite>(guideCount);
 		guidePage.setUrl("/myspace/share/guideFavorites.do?objectType=GUIDE&page=");
 		guidePage.setCurrentPage(currentPage);
 		guidePage.setPageSize(PAGESIZE);
 		param.put("startRows", guidePage.getStartRows());
 		param.put("endRows", guidePage.getEndRows());
 		if(guideCount!=null&&guideCount!=0){
 			guideMobileFavoriteList=mobileFavoriteService.queryMobileFavoriteGuideListForHome(param);
 			guidePage.setItems(guideMobileFavoriteList);
 		}
  	}
  	
  	private void tuangouFavorites(UserUser user,Map<String,Object> param,Long currentPage){
        //place
        param.put("objectType", Constant.CLIENT_FAVORITE_TYPE.TUANGOU.getCode());//跟产品是一个道理的 则调用
        tuangouCount= mobileFavoriteService.countMobileFavoriteList(param);
        //分页
        tuangouPage=new Page<MobileFavorite>(tuangouCount);
        tuangouPage.setUrl("/myspace/share/favorites.do?objectType="+Constant.CLIENT_FAVORITE_TYPE.TUANGOU.getCode()+"&page=");
        tuangouPage.setCurrentPage(currentPage);
        tuangouPage.setPageSize(PAGESIZE);
        param.put("startRows", tuangouPage.getStartRows());
        param.put("endRows", tuangouPage.getEndRows());
        if(tuangouCount!=null&&tuangouCount!=0){
            tuangouMobileFavoriteList=mobileFavoriteService.queryMobileFavoriteProductListForHome(param);
            tuangouPage.setItems(tuangouMobileFavoriteList);
        }
    }
  	
	 @Action("/myspace/share/delFavorites")
 	public void delFavorites() {
		String data = "";
		if (null != id) {
			MobileFavorite mf = mobileFavoriteService
					.selectMobileFavoriteById(id);
			if (null != mf) {
				int result = mobileFavoriteService.deleteMobileFavoriteById(mf
						.getId());
				if (result > 0) {
					data = "success";
				}
			}
		}
		this.sendAjaxMsg(data);
	}

	public void setMobileFavoriteService(MobileFavoriteService mobileFavoriteService) {
		this.mobileFavoriteService = mobileFavoriteService;
	}

	public Long getPlaceCount() {
		return placeCount;
	}
	
	public Long getTuangouCount() {
        return tuangouCount;
    }

	public Long getProductCount() {
		return productCount;
	}

	public Long getGuideCount() {
		return guideCount;
	}

	public List<MobileFavorite> getProductMobileFavoriteList() {
		return productMobileFavoriteList;
	}

	public List<MobileFavorite> getPlaceMobileFavoriteList() {
		return placeMobileFavoriteList;
	}

	public List<MobileFavorite> getGuideMobileFavoriteList() {
		return guideMobileFavoriteList;
	}
	
    public List<MobileFavorite> getTuangouMobileFavoriteList() {
        return tuangouMobileFavoriteList;
    }
	
	public void setId(Long id) {
		this.id = id;
	}
	public Page<MobileFavorite> getPlacePage() {
		return placePage;
	}
	public void setPlacePage(Page<MobileFavorite> placePage) {
		this.placePage = placePage;
	}
	public Page<MobileFavorite> getProductPage() {
		return productPage;
	}
	public void setProductPage(Page<MobileFavorite> productPage) {
		this.productPage = productPage;
	}
	public Page<MobileFavorite> getGuidePage() {
		return guidePage;
	}
    public Page<MobileFavorite> getTuangouPage() {
        return tuangouPage;
    }
	public void setGuidePage(Page<MobileFavorite> guidePage) {
		this.guidePage = guidePage;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}


	public Long getObjectId() {
		return objectId;
	}


	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}


	public String getObjectName() {
		return objectName;
	}


	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectImageUrl() {
		return objectImageUrl;
	}

	public void setObjectImageUrl(String objectImageUrl) {
		this.objectImageUrl = objectImageUrl;
	}

}
