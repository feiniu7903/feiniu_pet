package com.lvmama.pet.sweb.pub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.pub.ComKeJetAds;
import com.lvmama.comm.pet.po.pub.ComKeJetWord;
import com.lvmama.comm.pet.service.pub.ComKeJetAdsService;
import com.lvmama.comm.pet.service.pub.ComKeJetWordService;
import com.lvmama.comm.utils.KeJieAdsProxy;
import com.lvmama.comm.utils.MemcachedUtil;

@Results( {
	@Result(name = "success", location = "/WEB-INF/pages/back/kejet/index.jsp"),
	@Result(name = "edit", location = "/WEB-INF/pages/back/kejet/edit.jsp")
})
public class ComKeJetWordAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -43754870172530531L;
	private List<ComKeJetWord> comKeJetWordList;
	private ComKeJetWord comKeJetWord = new ComKeJetWord();
	
	private ComKeJetWordService comKeJetWordService;
	private ComKeJetAdsService comKeJetAdsService;
	private static String[] FILIALE = { "SH", "BJ", "CD", "GZ", "HZ", "NJ",
			"SZ", "SY" };	
	
	@Action("/pub/kejet/index")
	public String index() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (null != comKeJetWord && null != comKeJetWord.getId()) {
			param.put("id", comKeJetWord.getId());
		}
		if (null != comKeJetWord && null != comKeJetWord.getDescription()) {
			param.put("description", comKeJetWord.getDescription());
		}
		comKeJetWordList= comKeJetWordService.queryByParam(param);
	
	   return SUCCESS;
	}

	@Action("/pub/kejet/edit")
	public String edit() {
		if (null != comKeJetWord && null != comKeJetWord.getId()) {
			comKeJetWord = comKeJetWordService.queryByPK(comKeJetWord.getId());
		}
	
	   return "edit";
	}
	
	@Action("/pub/kejet/save")
	public void save() {
		if (null != comKeJetWord && null != comKeJetWord.getId()) {
			comKeJetWordService.update(comKeJetWord);
		} else {
			comKeJetWordService.insert(comKeJetWord);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", "操作成功！");
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	
	@Action("/pub/kejet/del")
	public void del() {
		if (null != comKeJetWord && null != comKeJetWord.getId()) {
			comKeJetWordService.delete(comKeJetWord.getId());
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", "操作成功！");
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
	}
	
	@Action("/pub/kejet/refreshMem")
	public void refreshMem() {
		Long id = Long.parseLong(getRequest().getParameter("comKeJetWord.id"));
		if (null != id) {
			String memcacheKey = "KEJETPROXY_TEXT_" + id;
			MemcachedUtil.getInstance().remove(memcacheKey);
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", "操作成功！");
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());		
	}
	
	@Action("/pub/kejet/refreshMemKey")
	public void refreshMemKey() {
		String refreshMemKey = getRequest().getParameter("refreshMemKey");
		if (StringUtils.isNotEmpty(refreshMemKey)) {
			List<ComKeJetAds> adsList = comKeJetAdsService.queryAll();
			for (ComKeJetAds ads : adsList) {
				if ("Y".equalsIgnoreCase(ads.getExtKey())) {
					for (String filiale : FILIALE) {
						if (refreshMemKey.equals(ads.getAp() + "_" + ads.getCt() + "_" + filiale)) {
							MemcachedUtil.getInstance().remove(refreshMemKey);
							KeJieAdsProxy.getKeJetAdsContent(ads.getAp(),
									ads.getCt(), filiale);
							break;
						}
					}
				} else {
					if (refreshMemKey.equals(ads.getAp() + "_" + ads.getCt())) {
						MemcachedUtil.getInstance().remove(refreshMemKey);
						KeJieAdsProxy.getKeJetAdsContent(ads.getAp(),
								ads.getCt(), ads.getExtKey());
						break;
					}
				}
			}
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", "操作成功！");
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());		
	}
	
	@Action("/pub/kejet/refreshAllMem")
	public void refreshAllMem() {
		List<ComKeJetAds> adsList = comKeJetAdsService.queryAll();
		for (ComKeJetAds ads : adsList) {
			if ("Y".equalsIgnoreCase(ads.getExtKey())) {
				for (String filiale : FILIALE) {
					KeJieAdsProxy.getKeJetAdsContent(ads.getAp(),
							ads.getCt(), filiale);
					try {
						Thread.sleep(500L);
					} catch (InterruptedException e) {
					}
				}
			} else {
				KeJieAdsProxy.getKeJetAdsContent(ads.getAp(), ads.getCt(),
						ads.getExtKey());
			}
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
			}
		}		
		
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", "操作成功！");
		this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());		
	}	

	public List<ComKeJetWord> getComKeJetWordList() {
		return comKeJetWordList;
	}

	public void setComKeJetWordService(ComKeJetWordService comKeJetWordService) {
		this.comKeJetWordService = comKeJetWordService;
	}

	public ComKeJetWord getComKeJetWord() {
		return comKeJetWord;
	}

	public void setComKeJetWord(ComKeJetWord comKeJetWord) {
		this.comKeJetWord = comKeJetWord;
	}

	public void setComKeJetAdsService(ComKeJetAdsService comKeJetAdsService) {
		this.comKeJetAdsService = comKeJetAdsService;
	}
}
