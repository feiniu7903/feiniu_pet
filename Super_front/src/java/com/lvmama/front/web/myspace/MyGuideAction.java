package com.lvmama.front.web.myspace;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.user.UserUser;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/myspace/sub/guide.ftl", type = "freemarker")
})
public class MyGuideAction extends SpaceBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -4509005317720761354L;

	private int publishPage = 1;
	private int favoritePage = 1;
	private int downloadPage = 1;
	
	@Action("/myspace/share/guide")
	public String execute() {
		if (publishPage < 0) {
			publishPage = 1;
		}
		if (favoritePage < 0)  {
			favoritePage = 1;
		}
		if (downloadPage < 0)  {
			downloadPage = 1;
		}
		UserUser user = getUser();
		if (null != user) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

	public int getPublishPage() {
		return publishPage;
	}

	public void setPublishPage(int publishPage) {
		this.publishPage = publishPage;
	}

	public int getFavoritePage() {
		return favoritePage;
	}

	public void setFavoritePage(int favoritePage) {
		this.favoritePage = favoritePage;
	}

	public int getDownloadPage() {
		return downloadPage;
	}

	public void setDownloadPage(int downloadPage) {
		this.downloadPage = downloadPage;
	}
}
