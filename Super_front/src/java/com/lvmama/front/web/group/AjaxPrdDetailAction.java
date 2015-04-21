package com.lvmama.front.web.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.front.web.BaseAction;
@ParentPackage("json-default")
@SuppressWarnings("unused")
public class AjaxPrdDetailAction extends BaseAction {

	private Long productId;
	private Long pageSize;
	private Long page;
	private GroupDreamService groupDreamService;
	private ProductSearchInfoService productSearchInfoService;
	private Map<String,Object>joinUsersMap;
	private UserUserProxy userUserProxy;
	private  List<ProductSearchInfo> enjoyPrdList;
	@Action(value="/group/ajaxQueryJoinGroupUsers",results=@Result(type="json",name="groupJoinUsers",params={"includeProperties","joinUsersMap.*"}))
	/**
	 * 某产品成功购买用户
	 */
	public String ajaxQueryJoinGroupUsersByPrd(){
		
		joinUsersMap=  groupDreamService.getPrdJoinUsers(productId,page,pageSize);
		if(joinUsersMap.containsKey("resultList")){
			List<Map> resultList=(List<Map>)joinUsersMap.get("resultList");
			if(CollectionUtils.isNotEmpty(resultList)){
				List<String> userNoList=new ArrayList<String>();
				for(Map item:resultList){
					userNoList.add((String)item.get("USER_ID"));					
				}
				if(!userNoList.isEmpty()){
					List<UserUser> users=userUserProxy.getUsersListByUserNoList(userNoList);
					Map<String,String> map=new HashMap<String, String>();
					if(CollectionUtils.isNotEmpty(users)){
						for(UserUser uu:users){
							map.put(uu.getUserNo(), uu.getUserName());
						}
					}
					for(Map item:resultList){
						String userId=(String)item.get("USER_ID");
						//替换或截短用户名
						String userName = map.get(userId);
						if(StringUtils.isNotEmpty(userName)){
							String subUserName = StringUtil.replaceOrCutUserName(-1, userName);
							item.put("USER_NAME", subUserName);
						}
					}
					joinUsersMap.put("resultList", resultList);
				}
			}
		}
		return "groupJoinUsers";
	}
	@Action(value="/group/ajaxQueryEnjoyPrdList",results=@Result(type="json",name="enjoyPrdList",params={"includeProperties","enjoyPrdList.*"}))
	/**
	 * 查询用户可能会喜欢的产品
	 */
	public String ajaxQueryEnjoyPrdList(){
		
		enjoyPrdList = productSearchInfoService.getEnjoyProductList(productId, pageSize);
		return "enjoyPrdList";
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Map<String, Object> getJoinUsersMap() {
		return joinUsersMap;
	}

	public void setJoinUsersMap(Map<String, Object> joinUsersMap) {
		this.joinUsersMap = joinUsersMap;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}
 
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
	public List<ProductSearchInfo> getEnjoyPrdList() {
		return enjoyPrdList;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	
}
