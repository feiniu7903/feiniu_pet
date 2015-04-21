package com.lvmama.pet.sweb.usertags;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.usertags.UserTags;
import com.lvmama.comm.pet.po.usertags.UserTagsRelationship;
import com.lvmama.comm.pet.service.usertags.UserTagsRelationshipService;
import com.lvmama.comm.pet.service.usertags.UserTagsService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PageElementModel;

/**
 * 标签关系
 * @author yifan
 *
 */
@Results({
		@Result(name = "search_tagsRes", location = "/WEB-INF/pages/back/user/usertags/add_tagsRes.jsp"),
		@Result(name = "pre_tagsRes", location = "/WEB-INF/pages/back/user/usertags/pre_tagsRes.jsp"),
		@Result(name = "edit_tagsRes", location = "/WEB-INF/pages/back/user/usertags/edit_tagsRes.jsp"),
		@Result(name = "mage_tagsRes", location = "/WEB-INF/pages/back/user/usertags/mage_tagsRes.jsp")})
public class UserTagsRelationshipAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;
	private List<PageElementModel> userTagsResType = Collections.emptyList();
	private UserTagsRelationship tagsRes;
	private UserTagsRelationshipService userTagsRelationshipService;
	private UserTagsService userTagsService;
	private UserTags tags;
	private List<UserTags> tagsList = null;
	private List<UserTagsRelationship> relationships = null;
	private Map<String,Object> tagsGroupMap = null;
	private String tagsIdList,tagsResTypeList;
	private String search_tagsName;
	Map<String, Object> param;

	/**
	 * 添加标签近义初始页
	 */
	@Action("/tagsRes/searchTagsRes")
	public String execute() throws Exception {
		param = new HashMap<String, Object>();
		param.put("tagsId", this.tags.getTagsId());
		tags = userTagsService.queryAllUserTagsByParam(param).get(0);
		if (tags!=null && tags.getTagsName()!=null) {
			userTagsResType = this.getTagsResType();
			if(search_tagsName == null){
				search_tagsName = this.tags.getTagsName();
			}
			tagsList = userTagsService.queryAllUserTagsByRes(this.getSearchTagsResData());
		}
		return "search_tagsRes";
	}
	
	/**
	 * 添加标签近义操作
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/addTagsRes")
	public String addTagsRes() throws Exception{
		String[] _tagsIds = this.tagsIdList.split(",");
		String[] _tagsResTypes = this.tagsResTypeList.split(",");
		param = new HashMap<String, Object>();
		param.put("tagsId1", tags.getTagsId());
		for (int i = 0; i < _tagsIds.length; i++) {
			param.put("tagsId2", _tagsIds[i]);
			param.put("relationshipType", _tagsResTypes[i]);
			userTagsRelationshipService.saveUserTagsRes(param);
		}
		this.outputToClient("true");
		return null;
	}
	
	
	/**
	 * 预处理标签近义初始页
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/preTagsRes")
	public String preTagsRes() throws Exception{
		this.initPreTagsResPage(0);
		this.relationships = userTagsRelationshipService.queryAllUserTagsPinyin(param);
		userTagsResType = this.getTagsResType();
		return "pre_tagsRes";
	}
	
	/**
	 * 标签近义编辑初始
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/preUpdTagsRes")
	public String preUpdTagsRes()throws Exception{
		param = new HashMap<String, Object>();
		param.put("relationshipId", this.tagsRes.getRelationshipId());
		this.tagsRes = userTagsRelationshipService.queryAllUserTagsRes(param).get(0);
		userTagsResType = this.getTagsResType();
		return "edit_tagsRes";
	}
	
	/**
	 * 标签近义编辑
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/updTagsRes")
	public String updTagsRes() throws Exception{
		param = new HashMap<String, Object>();
		param.put("relationshipId", this.tagsRes.getRelationshipId());
		param.put("relationshipType", this.tagsRes.getRelationshipType());
		userTagsRelationshipService.updateUserTagsResType(param);
		this.outputToClient("true");
		return null;
	}
	
	/**
	 * 预处理标签近义操作
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/addPreTagsRes")
	public String addPreTagsRes() throws Exception{
		String[] _tagsResTypes = this.tagsResTypeList.split(","); 
		String[] _tagsIdsArr = this.tagsIdList.split("\\|");
		String[] _tagsIds;
		param = new HashMap<String, Object>();
		for (int i = 0; i < _tagsResTypes.length; i++) {
			_tagsIds = _tagsIdsArr[i].split(",");
			param.put("tagsId1", _tagsIds[0]);
			param.put("tagsId2", _tagsIds[1]);
			param.put("relationshipType", _tagsResTypes[i]);
			userTagsRelationshipService.saveUserTagsRes(param);
		}
		this.outputToClient("true");
		return null;
	}
	
	/**
	 * 标签近义管理初始页
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/searchMageTagsRes")
	public String searchMageTagsRes() throws Exception{
		this.initPreTagsResPage(1);
		this.relationships = userTagsRelationshipService.queryAllUserTagsRes(param);
		userTagsResType = this.getTagsResType();
		return "mage_tagsRes";
	}
	
	/**
	 * 删除近义关系
	 * @return
	 * @throws Exception
	 */
	@Action("/tagsRes/delMageTagsRes")
	public String delMageTagsRes() throws Exception{
		param = new HashMap<String, Object>();
		param.put("relationshipIds", this.tagsIdList.substring(0,this.tagsIdList.length()-1));
		userTagsRelationshipService.deleteUserTagsRes(param);
		this.outputToClient("true");
		return null;
	}
	
	private List<PageElementModel> getTagsResType(){
		return Constant.USERTAGSRES_TYPE.getList();
	}
	
	private Map<String, Object> getSearchTagsResData() {
		param.clear();
		if (tags != null) {
			param.put("tagsName", search_tagsName);
			param.put("tagsId2", this.tags.getTagsId());
		}
		return param;
	}
	
	
	private void initPreTagsResPage(int var){
        param = new HashMap<String, Object>();
        pagination=initPage();
        pagination.setPageSize(10);
        if(var==0)
        	pagination.setTotalResultSize(userTagsRelationshipService.queryAllUserTagsPinyinCount(param));
        else if(var == 1){
        	if(this.search_tagsName!= null && !this.search_tagsName.equals(""))
    			param.put("search_tagsName", this.search_tagsName);
        	if(this.tagsRes == null){
        		param.put("relationshipType", 1);
        	}else{
        		param.put("relationshipType", this.tagsRes.getRelationshipType());
        	}
        	pagination.setTotalResultSize(userTagsRelationshipService.queryAllUserTagsResByCount(param));
        	
        }
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows", pagination.getEndRows());
        }
        pagination.buildUrl(getRequest());
    }
	
	public UserTagsRelationship getTagsRes() {
		return tagsRes;
	}

	public void setTagsRes(UserTagsRelationship tagsRes) {
		this.tagsRes = tagsRes;
	}

	public UserTagsRelationshipService getUserTagsRelationshipService() {
		return userTagsRelationshipService;
	}

	public void setUserTagsRelationshipService(
			UserTagsRelationshipService userTagsRelationshipService) {
		this.userTagsRelationshipService = userTagsRelationshipService;
	}

	public UserTagsService getUserTagsService() {
		return userTagsService;
	}

	public void setUserTagsService(UserTagsService userTagsService) {
		this.userTagsService = userTagsService;
	}

	public UserTags getTags() {
		return tags;
	}

	public void setTags(UserTags tags) {
		this.tags = tags;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public List<PageElementModel> getUserTagsResType() {
		return userTagsResType;
	}

	public void setUserTagsResType(List<PageElementModel> userTagsResType) {
		this.userTagsResType = userTagsResType;
	}

	public List<UserTags> getTagsList() {
		return tagsList;
	}

	public void setTagsList(List<UserTags> tagsList) {
		this.tagsList = tagsList;
	}

	public Map<String, Object> getTagsGroupMap() {
		return tagsGroupMap;
	}

	public void setTagsGroupMap(Map<String, Object> tagsGroupMap) {
		this.tagsGroupMap = tagsGroupMap;
	}

	public String getTagsIdList() {
		return tagsIdList;
	}

	public void setTagsIdList(String tagsIdList) {
		this.tagsIdList = tagsIdList;
	}

	public String getSearch_tagsName() {
		return search_tagsName;
	}

	public void setSearch_tagsName(String search_tagsName) {
		this.search_tagsName = search_tagsName;
	}

	public String getTagsResTypeList() {
		return tagsResTypeList;
	}

	public void setTagsResTypeList(String tagsResTypeList) {
		this.tagsResTypeList = tagsResTypeList;
	}

	public List<UserTagsRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<UserTagsRelationship> relationships) {
		this.relationships = relationships;
	}
	
}
