package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.vo.Constant;

public class PermPermission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9003279667160588715L;
	
	private Long permissionId;
	private String name;
	private String url;
	private String type;//类型（URL/元素/数据）
	private String embed;
	private String category;
	private String permLevel;
	private Long seq;
	private boolean isChecked;
	private Long parentId;
	private String valid;
	private String urlPattern;
	
	private String parentName;//父类名称.
	private String zkType="URL";
	private Boolean addVisible=false;//
	private Boolean editVisible=false;//
	private Boolean delVisible=false;//
	private Boolean viewVisible=false;//
	private Boolean selected=false;//下拉选项框是否被选中.
	private List<PermPermission> subList = new ArrayList<PermPermission>();
	
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmbed() {
		return embed;
	}

	public void setEmbed(String embed) {
		this.embed = embed;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public boolean isContainer() {
		return subList.size()>0;
	}
	
	public boolean isMenu() {
		return Constant.PERMISSION_TYPE.URL.name().equals(type);
	}

	public boolean isComponent() {
		return Constant.PERMISSION_TYPE.ELEMENT.name().equals(type);
	}

	public String getPermLevel() {
		return permLevel;
	}

	public void setPermLevel(String permLevel) {
		this.permLevel = permLevel;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public List<PermPermission> getSubList() {
		return subList;
	}

	public void setSubList(List<PermPermission> subList) {
		this.subList = subList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}


	public String getZkType() {
		if(Constant.PERM_PERMISSION_TYPE_ENUM.ELEMENT.name().equals(this.type)){
			this.zkType="元素";
		}
		return zkType;
	}

	public Boolean getAddVisible() {
		if(Constant.PERM_PERMISSION_TYPE_ENUM.ELEMENT.name().equals(this.type)){
			return false;
		}else if(Constant.PERM_PERMISSION_TYPE_ENUM.URL.name().equals(this.type)&&!"3".equals(this.permLevel)){
			return true;
		}
		return addVisible;
	}

	public Boolean getEditVisible() {
		if(Constant.PERM_PERMISSION_TYPE_ENUM.URL.name().equals(this.type)){
			return true;
		}
		return editVisible;
	}

	public Boolean getDelVisible() {
		if(Constant.PERM_PERMISSION_TYPE_ENUM.ELEMENT.name().equals(this.type)||"N".equals(this.valid)){
			return false;
		}else if(!"1".equals(this.permLevel)&&Constant.PERM_PERMISSION_TYPE_ENUM.URL.name().equals(this.type)){
			return true;
		} 
		return delVisible;
	}
	
	public Boolean getViewVisible() {
		if("2".equals(this.permLevel)||"3".equals(this.permLevel)){
			return true;
		} 
		return viewVisible;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getZhCategory() {
		return Constant.RESOURCE_GROUP.getCnName(category);
	}

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	
}
