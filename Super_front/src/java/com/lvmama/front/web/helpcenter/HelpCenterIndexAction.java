/**
 * 
 */
package com.lvmama.front.web.helpcenter;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.info.InfoHelpCenter;
import com.lvmama.comm.pet.po.info.InfoQuesTypeHierarchy;
import com.lvmama.comm.pet.service.info.InfoService;
import com.lvmama.comm.utils.StackOverFlowUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.BaseAction;


/**
 * @author liuyi
 *
 */
@Results( { 
	@Result(name = "helpIndex", location = "/WEB-INF/pages/helpCenter/index.ftl", type = "freemarker"),
	@Result(name = "chazuo", location = "/WEB-INF/pages/helpCenter/chazuo.ftl", type = "freemarker"),
	@Result(name = "time", location = "/WEB-INF/pages/helpCenter/internation_time.ftl", type = "freemarker"),
	@Result(name = "passport_visa", location = "/WEB-INF/pages/helpCenter/passport_visa.ftl", type = "freemarker"),
	@Result(name = "contentTypePage", location = "/WEB-INF/pages/helpCenter/contentTypePage.ftl", type = "freemarker"),
	@Result(name = "contentDetailPage", location = "/WEB-INF/pages/helpCenter/contentDetailPage.ftl", type = "freemarker")
})
public class HelpCenterIndexAction extends BaseAction {
	
	private static Logger logger = Logger.getLogger(HelpCenterIndexAction.class);
	private List<InfoQuesTypeHierarchy> helpCenterCategoryList;
	private InfoService infoServiceBean;
	private Long contentTypeId;
	private List<InfoHelpCenter> helpCenterContentList;
	private Long contentId;
	private int detailContentIndex;
	
	//for seo
	private String currentContentTypeName;
	private String currentBusinessTypeName;
	private String seoCurrentContentTitle;
	private String seoCurrentContentContent;
	
	
	@Action("/help/index")
	public String goToIndexPage()
	{
		try
		{
			getTwoLevelHelpCenterCategory();
		}
		catch (Exception ex)
		{
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		
		}
		return "helpIndex";
	}
	
	/**
	 * 各国插座
	 * @return
	 */
	@Action("/help/goToChaZuo")
	public String goToChaZuoPage()
	{
		try
		{
		     getTwoLevelHelpCenterCategory();
		}
		catch (Exception ex)
		{
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		
		}
		return "chazuo";
	}
	
	@Action("/help/goToTime")
	public String goToTimePage()
	{
		try
		{
			getTwoLevelHelpCenterCategory();
		}
		catch (Exception ex)
		{
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		
		}
		return "time";
	}
	
	@Action("/help/goToPassportVisa")
	public String goToPassportVisa()
	{
		try
		{
			getTwoLevelHelpCenterCategory();
		}
		catch (Exception ex)
		{
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		
		}
		return "passport_visa";
	}
	
	
	@Action("/help/goToContentTypePage")
	public String goToContentTypePage()
	{
		try
		{
			getTwoLevelHelpCenterCategory();		
			
			com.lvmama.comm.pet.po.info.InfoHelpCenter helpCenter=new com.lvmama.comm.pet.po.info.InfoHelpCenter();
			helpCenter.setTypeId(contentTypeId);
			this.helpCenterContentList = this.infoServiceBean.queryInfoHelpList(helpCenter);
			setBusinessTypeNameAndContentTypeName(this.helpCenterCategoryList,this.contentTypeId);
		}
		catch (Exception ex)
		{
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		}
		return "contentTypePage";
	}
	
	@Action("/help/goToContentDetailPage")
	public String goToContentDetailPage()
	{
		try
		{
			if(null == this.contentTypeId)
			{
				InfoHelpCenter currentHelpInfo = this.infoServiceBean.getInfoHelpById(this.contentId);
				this.contentTypeId = currentHelpInfo.getTypeId();
			}
			
			getTwoLevelHelpCenterCategory();
			com.lvmama.comm.pet.po.info.InfoHelpCenter helpCenter=new com.lvmama.comm.pet.po.info.InfoHelpCenter();
			helpCenter.setTypeId(contentTypeId);
			this.helpCenterContentList = this.infoServiceBean.queryInfoHelpList(helpCenter);

			this.detailContentIndex = -1;
			for(int i = 0; i < this.helpCenterContentList.size(); i++)
			{
				if(this.helpCenterContentList.get(i).getId().equals(this.contentId))
				{
					this.seoCurrentContentTitle =this.helpCenterContentList.get(i).getTitle();
					this.seoCurrentContentContent =this.helpCenterContentList.get(i).getTypeName()+","+this.getHelpCenterContentList().get(i).getTitle();
					this.detailContentIndex = i;
					break;
				}
			}
			setBusinessTypeNameAndContentTypeName(this.helpCenterCategoryList,this.contentTypeId);	
		}
		catch (Exception ex)
		{
			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
		
		}
		return "contentDetailPage";
	}
	
	private void setBusinessTypeNameAndContentTypeName(List<InfoQuesTypeHierarchy> helpCenterCategoryList, Long contentTypeId)
	{
		if(null != helpCenterCategoryList && 0 < (helpCenterCategoryList.size()))
		{
			for(int i = 0; i < helpCenterCategoryList.size(); i++)
			{
				InfoQuesTypeHierarchy infoQuesTypeHierarchy = helpCenterCategoryList.get(i);
				if(infoQuesTypeHierarchy.getTypeId().equals(contentTypeId))
				{
					this.currentContentTypeName = infoQuesTypeHierarchy.getTypeName();
					this.currentBusinessTypeName = infoQuesTypeHierarchy.getParentTypeName();
					break;
				}
				else
				{
					setBusinessTypeNameAndContentTypeName(infoQuesTypeHierarchy.getChildInfoQuesTypeHierarchyList(), contentTypeId);
				}
			}
		}
	}
	
	private void getTwoLevelHelpCenterCategory(){
		helpCenterCategoryList=infoServiceBean.queryInfoQuesTypeHierarchyByObjectType(Constant.INFO_TYPE.INFO_QUES_HELP_CENTER.name());
		for(InfoQuesTypeHierarchy iq:helpCenterCategoryList){
			iq.setChildInfoQuesTypeHierarchyList(infoServiceBean.queryInfoQuesTypeHierarchyByObjectType(Constant.INFO_TYPE.INFO_QUES_HELP_CENTER.name(), iq.getTypeId()));
		}
	}

	public List<InfoQuesTypeHierarchy> getHelpCenterCategoryList() {
		return helpCenterCategoryList;
	}

	

	public void setContentTypeId(Long contentTypeId) {
		this.contentTypeId = contentTypeId;
	}

	public Long getContentTypeId() {
		return contentTypeId;
	}

	public void setHelpCenterContentList(List<InfoHelpCenter> helpCenterContentList) {
		this.helpCenterContentList = helpCenterContentList;
	}

	public List<InfoHelpCenter> getHelpCenterContentList() {
		return helpCenterContentList;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setDetailContentIndex(int detailContentIndex) {
		this.detailContentIndex = detailContentIndex;
	}

	public int getDetailContentIndex() {
		return detailContentIndex;
	}

	public void setCurrentContentTypeName(String currentContentTypeName) {
		this.currentContentTypeName = currentContentTypeName;
	}

	public String getCurrentContentTypeName() {
		return currentContentTypeName;
	}

	public void setCurrentBusinessTypeName(String currentBusinessTypeName) {
		this.currentBusinessTypeName = currentBusinessTypeName;
	}

	public String getCurrentBusinessTypeName() {
		return currentBusinessTypeName;
	}
	
	public void setSeoCurrentContentTitle(String seoCurrentContentTitle) {
		this.seoCurrentContentTitle = seoCurrentContentTitle;
	}

	public String getSeoCurrentContentTitle() {
		return seoCurrentContentTitle;
	}

	public void setSeoCurrentContentContent(String seoCurrentContentContent) {
		this.seoCurrentContentContent = seoCurrentContentContent;
	}

	public String getSeoCurrentContentContent() {
		return seoCurrentContentContent;
	}

	/**
	 * @param infoServiceBean the infoServiceBean to set
	 */
	public void setInfoServiceBean(InfoService infoServiceBean) {
		this.infoServiceBean = infoServiceBean;
	}
}
