package com.lvmama.back.sweb.prod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.service.sensitiveW.SensitiveWordService;
import com.lvmama.comm.utils.json.JSONResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

/**
 * @author shihui
 */
public class ProdSensitiveWordAction extends BaseAction {
	
	private static final long serialVersionUID = 7540713321102830472L;

	private String[] dataList;
	
	private SensitiveWordService sensitiveWordService;
	
    private ProdProductService prodProductService;
    
    private Long productId;
    
    private String hasSensitiveWord;
	
	/**
	 * 校验敏感词
	 * */
	@Action(value = "/prod/validateSensitiveWords")
	public void validateSensitiveWords() {
		JSONResult result = new JSONResult();
		JSONArray array = new JSONArray();
		try {
			if(dataList != null && dataList.length > 0) {
				Pattern p_html = Pattern.compile("<[^>]+>", Pattern.CASE_INSENSITIVE);
				for (int i = 0; i < dataList.length; i++) {
					String data = dataList[i];
					int index = data.indexOf(":");
					String name = data.substring(0, index);
					String value = data.substring(index + 1);
					
					if(StringUtils.isNotEmpty(value)) {
						Matcher m_html = p_html.matcher(value);
						value = m_html.replaceAll("");
						
						String msg = sensitiveWordService.returnSensitiveWords(value);
						if(StringUtils.isNotEmpty(msg)) {
							JSONObject obj = new JSONObject();
							obj.put("name", name);
							obj.put("msg", msg);
							array.add(obj);
						}
					}
				}
			}
			result.put("result", array);
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
		result.output(getResponse());
	}
	
	/**
     * 提交审核前,校验产品各部分信息是否包含敏感词,更新相应的字段
     * 
     * add by shihui
     * */
    @Action("/prod/validateProductSensitiveWords")
    public void validateProductSensitiveWords() {
    	JSONResult result = new JSONResult();
    	try {
    		result.put("hasSensitiveWords", prodProductService.checkAndUpdateIsHasSensitiveWords(productId));
		} catch (Exception e) {
			result.raise(e.getMessage());
		}
    	result.output(getResponse());
    }
    
	public String[] getDataList() {
		return dataList;
	}

	public void setDataList(String[] dataList) {
		this.dataList = dataList;
	}

	public void setSensitiveWordService(SensitiveWordService sensitiveWordService) {
		this.sensitiveWordService = sensitiveWordService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getHasSensitiveWord() {
		return hasSensitiveWord;
	}

	public void setHasSensitiveWord(String hasSensitiveWord) {
		this.hasSensitiveWord = hasSensitiveWord;
	}
}
