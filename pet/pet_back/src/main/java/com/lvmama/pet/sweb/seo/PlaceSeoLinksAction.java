package com.lvmama.pet.sweb.seo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.SeoUtils;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.utils.SeoLinksExcelUtils;
import org.apache.commons.logging.Log;
/**
 * seo-友情链接
 * @author zhongshuangxi
 */
@Results({
    @Result( name="seo_list",location="/WEB-INF/pages/back/seo/place/seo_list.jsp"),
    @Result( name="querySeoListForPageTable",location="/WEB-INF/pages/back/seo/place/querySeoListForPageTable.jsp"),
    @Result( name="seo_edit",location="/WEB-INF/pages/back/seo/place/seo_edit.jsp"),
    @Result( name="toAddSeoLinksPage",location="/WEB-INF/pages/back/seo/place/toAddSeoLinksPage.jsp"),
    @Result( name="importExcel",location="/WEB-INF/pages/back/seo/place/importExcel.jsp")
})
public class PlaceSeoLinksAction extends BackBaseAction{
    private static final long serialVersionUID = -232994545999999341L;
    private static final Log log = LogFactory.getLog(PlaceSeoLinksAction.class);
    private PlaceService placeService;
    private SeoLinksService seoLinksService;
    
    private List<CodeItem> seoLinksIndexList ;
    private Place place;
    private SeoLinks seoLinks;
    private SeoLinks editSeoLinks;
    private String seoList;
    
    private List<SeoLinks> list;
    private File file;
    private String stage;
    private String location;
    private String search;
     
    /**
     * 默认页
     * @return
     * @throws Exception
     * @author zhongshuangxi
     */
    @Action("/seoLinks/seoPlaceList")
    public String execute() throws Exception{
        initSeoListPage();
        return "seo_list";
    }
    @Action("/seoLinks/querySeoListForPageTable")
    public String querySeoListForPageTable(){
    	 initSeoListPage();
         return "querySeoListForPageTable";
    }
    
    /**
     * 编辑页面开始页
     * @return
     * @throws Exception
     * @author zhongshuangxi
     */
    @Action("/seoLinks/toSeoPlaceEdits")
    public String toSeoPlaceEdits() throws Exception{
    	//进入编辑页面
     	if(null != seoLinks && null != seoLinks.getSeoLinksId()){
     		editSeoLinks = seoLinksService.querySeoBySeoID(seoLinks.getSeoLinksId());
    	}     
    	return "seo_edit";
    }
    /**
     * 编辑
     * @return
     * @throws Exception
     * @author zhongshuangxi
     */
    @Action("/seoLinks/doSeoPlaceEdits")
    public void doSeoPlaceEdits() {
    	Boolean flag=false;
    	try{
    		//修改seo-友情链接
    		if(editSeoLinks.getSeoLinksId()!=null ){
    			//去重
    			Map<String,Object> map=new HashMap<String,Object>();
    			map.put("placeId", editSeoLinks.getPlaceId());
    			map.put("linkUrl", editSeoLinks.getLinkUrl());
    			map.put("removeSeoId", editSeoLinks.getSeoLinksId());
    			List<SeoLinks> seoList= this.seoLinksService.querySeoLinksByParam(map);
    			if((!seoList.isEmpty())&&seoList.size()==1){
    				flag=false;
    			}else{
    				seoLinksService.update(editSeoLinks);  
    				flag=true;
    			}
    		}
    	}catch(Exception e){
    		log.error(" SEO - 友情链接 编辑："+e);
    	}finally{
    		this.sendAjaxMsg(flag.toString());
    	}
     }
    @Action("/seoLinks/toAddSeoLinksPage")
    public String  toAddSeoLinksPage(){
    	this.initLocationList();
    	return "toAddSeoLinksPage";
    }
    /**
     * 
     * 保存seoLinks
     * @author nixianjun 2013-8-13
     */
    @Action("/seoLinks/saveSeoLinks")
    public void saveSeoLinks() {
    	Boolean flag=false;
     	try{
    		if(seoLinks.getPlaceId()!=null){
    			//去重
    			Map<String,Object> map=new HashMap<String,Object>();
    			map.put("placeId", seoLinks.getPlaceId());
    			map.put("linkUrl", seoLinks.getLinkUrl());
    			List<SeoLinks> seoList= this.seoLinksService.querySeoLinksByParam(map);
    			if(!seoList.isEmpty()){
    				flag=false;
    			}else{
    				this.seoLinksService.insert(seoLinks);
    				flag=true;
    			}
     		} 
    	}catch(Exception e){
    	    log.error("SEO - 友情链接 保存："+e);
     	}finally{
    		this.sendAjaxMsg(flag.toString());
    	}
    }

    /**
     * 删除单条seo
     * @return
     * @throws Exception
     * @author zhongshuangxi
     */    
    @Action("/seoLinks/doDeleteSeo")
    public void doDeleteSeo(){
       //写一个根据seoID删除一条友情链接
        Boolean flag=false;
        try{
          seoLinksService.deleteBySeoLinksId(seoLinks.getSeoLinksId());
          flag=true;
        }catch(Exception e){
            log.error("SEO - 友情链接 单条删除："+e);
        }finally{
            this.sendAjaxMsg(flag.toString());
        }
    }
    

    /**
     * 批量删除通过stage，location
     * @return
     * @throws Exception
     * @author nixianjun 2013-8-12
     */
    @Action("/seoLinks/batchDeleteSeo")
    public void batchDeleteSeo(){
		Map<String, Object> message = new HashMap<String, Object>();
		try {
			if (null != this.stage && null != this.location) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("placeStage", stage);
				map.put("location", this.location);
				seoLinksService.batchDeleteByParam(map);
				message.put("flag", true);
			}else{
				message.put("flag", false);
			}
		} catch (Exception e) {
		    log.error(" SEO - 友情链接 批量删除："+e);
			message.put("flag", false);
		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(message).toString());
		}
     }
    /**
     * 根据选中的seo删除
     * @return
     * @throws Exception
     * @author zhongshuangxi
     */
    @Action("/seoLinks/doSelectDelete")
    public String doSelectDelete() throws Exception{
      List<SeoLinks> list = new ArrayList<SeoLinks>();
       //写一个根据seoID删除一条友情链接
        if(null!=seoList){
            String[] seoIdList = seoList.split(",");
            for (int i = 0; i < seoIdList.length; i++) {
                seoLinks = seoLinksService.querySeoBySeoID(Long.valueOf(seoIdList[i]));
                list.add(seoLinks);
            }
            try{
                seoLinksService.batchDeleteSeoLinks(list);
            }catch(Exception e){
                log.error(" SEO - 友情链接 选中删除："+e);
            }
            
        } 
        seoLinks=null;
        initSeoListPage();
        return "seo_list";
    }
    @Action("/seoLinks/toSeoPlaceAdd")
    public String toSeoPlaceAdd() {
       this.initLocationList();
         //进入编辑页面
     if(null != seoLinks && null != seoLinks.getSeoLinksId()){
         seoLinks = seoLinksService.querySeoBySeoID(seoLinks.getSeoLinksId());
     }     
        return "seo_edit";
    }
    /**
     * 导入excel初始化页面
     * @return
     * @author nixianjun 2013-8-9
     */
    @Action("/seoLinks/importExcel")
    public String importExcel(){
        return "importExcel";
    }
    /**
     * 导入文件数据
     * 
     * @author nixianjun 2013-8-9
     */
    @Action("/seoLinks/importExcelData")
    public void  importExcelData(){
    	if(StringUtil.isEmptyString(stage)){
    		this.sendAjaxMsg("stage为空！");
    		return;
    	}
    	String message="";
     	//解析数据
    	String xmlConfig=ResourceUtil.getResourceFileName(SeoLinksExcelUtils.SEOLINKSEXCELCONFIGXMLABSPATH);
      	List<SeoLinks> seoList=SeoLinksExcelUtils.parseExcelByjXls(file.getAbsolutePath(), xmlConfig);
      	if(null!=seoList&&(!seoList.isEmpty())){
      		if(seoList.size()>5000){
      			this.sendAjaxMsg("数据条数超过5000条，请缩减条数!");
        		return;
      		}
      		//数据做筛选
      		List<SeoLinks> batchList=new ArrayList<SeoLinks>();
      		List<Long> errorPlaceIdList=new ArrayList<Long>();
      		//去重集合
      		Set<String> setList=new HashSet<String>();
      		for(SeoLinks seo:seoList){
      			if(null!=seo.getPlaceId()){
      			   Place place=placeService.queryPlaceByPlaceId(seo.getPlaceId());
      			   if(null!=place &&place.getStage().equals(stage)){
      				   //判断此对象是否是已有的,数据库没有
      				  if((!setList.contains(seo.getPlaceIdAndUrl()))&&(!hasSeolink(seo))){
      					  setList.add(seo.getPlaceIdAndUrl());
      					  batchList.add(seo);
      				  }
      			   }else {
      				 errorPlaceIdList.add(seo.getPlaceId());
      				   continue;
      			   }
      			}
      		}
      		//数据插入
      		if(null!=batchList&&!(batchList.isEmpty())){
      			seoLinksService.batchInsert(batchList);
      			if(null!=errorPlaceIdList&&(!errorPlaceIdList.isEmpty())){
      				message="操作失败:无效的"+Constant.PLACE_STAGE.getCnName(stage)
      						+"id:"+errorPlaceIdList.toString();
      			}else{
      				message="success";
      			}
      		}else{
      			message="操作失败:插入数据为空,可能有重复数据，或者place的id不存在！";
      		}
      	}else{
      		message="操作失败:插入数据为空,解析excel没有得到数据！";
      	}
      	this.sendAjaxMsg(message);
     }
    
    /**
     * 判断同一个place下的友情链接是否存在
     * @param s
     * @return true 存在 false 不存在
     * @author nixianjun 2013-8-21
     */
    private Boolean hasSeolink(SeoLinks s){
    	//去重
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("placeId", s.getPlaceId());
		map.put("linkUrl", s.getLinkUrl());
		List<SeoLinks> seoList= this.seoLinksService.querySeoLinksByParam(map);
		if((!seoList.isEmpty())&&seoList.size()>0){//存在
			return true;
		}else{
			return false;
		}
     }
    
        
    /**
     * 导出excel数据
     * 
     * @author nixianjun 2013-8-12
     */
	@Action("/seoLinks/writeExportExcelData")
	public void writeExportExcelData() {
		if (StringUtil.isEmptyString(stage)) {
			this.sendAjaxMsg("stage为空");
			return;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("placeStage", stage);
		map.put("location", location);
		List<SeoLinks> seoList = seoLinksService.batchQuerySeoLinksByParam(map);
		if (null != seoList) {
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("seoList", seoList);
			beans.put("placeStage", Constant.PLACE_STAGE.getCnName(String.valueOf(stage)));
			String excelTemplate = SeoLinksExcelUtils.SEOLINKSEXCELWRITETEMPPATH;
			String destFileName = SeoLinksExcelUtils.writeExcelByjXls(beans,
					excelTemplate);
			super.writeAttachment(destFileName, "seoLinksexcel"+DateUtil.formatDate(new Date(), "yyyy MM dd"));
		}

	}
	@Action("/seoLinks/place/searchPlace")
	public void searchPlace(){		
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("name", search);
		param.put("stage", stage);
		List<Place> list = this.placeService.queryPlaceListByParam(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(Place cp:list){
				JSONObject obj=new JSONObject();
				obj.put("id", cp.getPlaceId());
				obj.put("text", cp.getName());
				obj.put("airportCode", cp.getAirportCode());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

    //根据查询条件和分页显示数据
    public List<SeoLinks> initSeoListPage(){
        Map<String,Object> param = new HashMap<String, Object>();
           this.initLocationList();
            if(seoLinks!=null){
               if(StringUtils.isNotEmpty(seoLinks.getPlaceName())){
                   param.put("placeName",seoLinks.getPlaceName());
               }
               if(StringUtils.isNotEmpty(seoLinks.getLocation())){
                   param.put("location", seoLinks.getLocation());
               }
               if(StringUtils.isNotEmpty(seoLinks.getLinkUrl())){
                   param.put("linkUrl",seoLinks.getLinkUrl());
               }
            }
            param.put("placeStage",place.getStage());
            
            pagination=initPage();
            pagination.setPageSize(50);
            pagination.setTotalResultSize(seoLinksService.getCountSeoLinksByParam(param));
            if(pagination.getTotalResultSize()>0){
                param.put("startRows", pagination.getStartRows());
                param.put("endRows", pagination.getEndRows());
            }
            pagination.buildUrl(getRequest());   
        
        return list = seoLinksService.batchQuerySeoLinksByParam(param);
    } 
    
    /**
     * 初始化seo位置
     * 
     * @author nixianjun 2013-8-13
     */
    private void initLocationList(){
    	  if(Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode().equals(place.getStage())){
              seoLinksIndexList = SeoUtils.queryLocationTypeByStageDest();
          }else if(Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode().equals(place.getStage())){
              seoLinksIndexList = SeoUtils.queryLocationTypeByStageScenic();
          }else{
              seoLinksIndexList = SeoUtils.queryLocationTypeByStageHotel();
          }
    }
    
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public SeoLinks getSeoLinks() {
        return seoLinks;
    }

    public void setSeoLinks(SeoLinks seoLinks) {
        this.seoLinks = seoLinks;
    }

    public List<SeoLinks> getList() {
        return list;
    }

    public void setList(List<SeoLinks> list) {
        this.list = list;
    }

    public SeoLinksService getSeoLinksService() {
        return seoLinksService;
    }

    public void setSeoLinksService(SeoLinksService seoLinksService) {
        this.seoLinksService = seoLinksService;
    }

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}


	/**
	 * @param placeService the placeService to set
	 */
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}


	/**
	 * @return the stage
	 */
	public String getStage() {
		return stage;
	}

	/**
	 * @param stage the stage to set
	 */
	public void setStage(String stage) {
		this.stage = stage;
	}
    public String getSeoList() {
        return seoList;
    }
    public void setSeoList(String seoList) {
        this.seoList = seoList;
    }

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the seoLinksIndexList
	 */
	public List<CodeItem> getSeoLinksIndexList() {
		return seoLinksIndexList;
	}

	/**
	 * @param seoLinksIndexList the seoLinksIndexList to set
	 */
	public void setSeoLinksIndexList(List<CodeItem> seoLinksIndexList) {
		this.seoLinksIndexList = seoLinksIndexList;
	}

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	/**
	 * @return the editSeoLinks
	 */
	public SeoLinks getEditSeoLinks() {
		return editSeoLinks;
	}

	/**
	 * @param editSeoLinks the editSeoLinks to set
	 */
	public void setEditSeoLinks(SeoLinks editSeoLinks) {
		this.editSeoLinks = editSeoLinks;
	}
	
}
