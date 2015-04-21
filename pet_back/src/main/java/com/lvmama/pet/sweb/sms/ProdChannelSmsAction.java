package com.lvmama.pet.sweb.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.prod.ProdChannelSms;
import com.lvmama.comm.bee.service.prod.ProdChannelService;
import com.lvmama.comm.pet.po.prod.ProdChannel;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant.SEND_MSG_CHANNEL;

/**
 * @author likun 产品渠道相关短信模板管理
 * */
@Results({
		@Result(name = "index", location = "/WEB-INF/pages/back/sms/prod_channel_sms_index.jsp"),
		@Result(name = "addOrEdit", location = "/WEB-INF/pages/back/sms/prod_channel_sms_save.jsp"),
		@Result(name = "list", location = "/prodChannelSms/index.do", type = "redirect") })
@Namespace("/prodChannelSms")
public class ProdChannelSmsAction extends BackBaseAction {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Log logger = LogFactory
			.getLog(ProdChannelSmsAction.class);

	private Map<String, String> channelMap = null;

	private List<ProdChannel> prodChannels = null;

	private List<ComSmsTemplate> smsTemplates = null;

	public void doBefore() {
		// 获取短信发送通道
		channelMap = new HashMap<String, String>();
		SEND_MSG_CHANNEL[] channel = SEND_MSG_CHANNEL.values();
		for (SEND_MSG_CHANNEL send_MSG_CHANNEL : channel) {
			channelMap.put(send_MSG_CHANNEL.getCode(),
					send_MSG_CHANNEL.getCnName());
		}
		// 获取所有产品渠道
		prodChannels = this.prodChannelService.selectProdChannelByAll();
		// 获取所有super短信模板
		smsTemplates = this.superSmsTemplateRemoteService.getListByParams(null);

	}

	/**
	 * super短信模板页面
	 * 
	 * @return
	 */
	@Action("index")
	public String index() {
		doBefore();
		Map<String, Object> params = getParameterMap();
		// 取得数据总数量
		this.pageModel.setTotalResultSize(this.prodChannelService
				.getProdChannelSmsListCountByParams(params));
		System.out.println(JSONSerializer.toJSON(params).toString());
		// 初始化分页信息
		this.pageModel.buildUrl(getRequest());
		this.pageModel.setCurrentPage(this.page);
		params.put("skipResults", this.pageModel.getStartRows() - 1);
		params.put("maxResults", this.pageModel.getEndRows());
		this.pageModel.setItems(this.prodChannelService
				.getProdChannelSmsListByParams(params));
		return "index";
	}

	/**
	 * 添加super短信模板
	 * 
	 * @return
	 */
	@Action("add")
	public String add() {
		doBefore();
		setRequestAttribute("operate", "add");
		return "addOrEdit";
	}

	/**
	 * 编辑短信模板
	 * 
	 * @return
	 */
	@Action("edit")
	public String edit() {
		doBefore();
		setRequestAttribute("operate", "edit");
		if (this.id != null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("channelSmsId", this.id);
			List<ProdChannelSms> list = this.prodChannelService
					.getProdChannelSmsListByParams(params);
			if (list != null && list.size() >= 1) {
				printLog("将要修改产品渠道对应短信模板信息:"
						+ JSONSerializer.toJSON(list.get(0)));
				setRequestAttribute("model", list.get(0));
			}
		}
		return "addOrEdit";
	}

	@Action("save")
	public void save() throws Exception {
		String result = "1";

		if (this.model != null) {
			String channelCodeParam = getRequestParameter("model.channelCode");
			if (StringUtil.isNotEmptyString(channelCodeParam)
					&& channelCodeParam.indexOf("_") != -1) {
				this.model.setChannelId(Long.valueOf(channelCodeParam
						.substring(0, channelCodeParam.indexOf("_"))));
				this.model.setChannelCode(channelCodeParam
						.substring(channelCodeParam.indexOf("_") + 1));
			}
			if (StringUtil.isEmptyString(this.model.getContent())
					&& StringUtil.isNotEmptyString(this.model.getTemplateId())) {
				ComSmsTemplate template = this.superSmsTemplateRemoteService
						.selectByPrimaryKey(this.model.getTemplateId());
				if (template != null) {
					this.model.setContent(template.getContent());
				}
			}

			if (isExists(model.getChannelSmsId(), model.getChannelId(),
					model.getTemplateId())) {
				result = "该短信模板已经加入到了该渠道，不能再次添加!";
			} else {
				if ("edit".equals(operate)) {
					printLog("修改产品渠道对应短信模板信息:" + JSONSerializer.toJSON(model));
					// 修改
					this.prodChannelService.updateProdChannelSms(this.model);
				} else {
					try {
						printLog("添加产品渠道对应短信模板信息:"
								+ JSONSerializer.toJSON(model));
						this.prodChannelService.add(this.model);
					} catch (Exception e) {
						String mes = e.getMessage();
						if (StringUtil.isNotEmptyString(mes)
								&& mes.indexOf("COM_SMS_TEMPLATE_PK") != -1) {
							result = "短信模板ID已经存在，请换一个!";
						} else {
							throw e;
						}
					}
				}
			}
		}
		sendAjaxMsg(result);
	}

	/**
	 * 判断模板渠道是否已经配置
	 * 
	 * @return
	 */
	private boolean isExists(Long channelSmsId, Long channelId,
			String templateId) {
		boolean flag = false;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelId", channelId);
		params.put("templateId", templateId);
		List<ProdChannelSms> list = this.prodChannelService
				.getProdChannelSmsListByParams(params);
		if (list != null) {
			if (channelSmsId != null) {
				for (ProdChannelSms prodChannelSms : list) {
					if (!channelSmsId.equals(prodChannelSms.getChannelSmsId())) {
						flag = true;
						break;
					}
				}
			} else {
				flag = list.size() >= 1 ? true : false;
			}
		}
		return flag;
	}

	/**
	 * 删除短信模板
	 * 
	 * @return
	 */
	@Action("delete")
	public String delete() {
		if (id != null) {
			this.prodChannelService.deleteChannelSms(this.id);
		}
		return "list";
	}
	
	@Action("editBatchChannel")
	public String editBatchChannel() throws Exception {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = getParameterMap();
		printLog("批量修改super短信渠道," + JSONSerializer.toJSON(paramMap));
		this.prodChannelService.updateBatchChannel(paramMap);
		resultMap.put("msg", "批量修改渠道成功!");
		this.outputToClient(JSONSerializer.toJSON(resultMap).toString());
		return null;
	}

	/**
	 * 记录日志
	 * 
	 * @param msg
	 */
	public void printLog(String msg) {
		if (getSessionUser() != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", getSessionUser().getUserId());
			map.put("userName", getSessionUser().getUserName());
			map.put("realName", getSessionUser().getRealName());
			msg = "操作人:" + JSONSerializer.toJSON(map).toString() + msg;
		}
		log.info(msg);
	}

	/**
	 * 模板ID
	 */
	private Long id;

	/**
	 * 操作标志
	 */
	private String operate;

	/**
	 * 产品渠道相关短信模板
	 */
	private ProdChannelSms model;

	/**
	 * 产品销售渠道相关短信模板service
	 */
	private ProdChannelService prodChannelService;

	/**
	 * lvmama_super短信模板service
	 */
	private ComSmsTemplateService superSmsTemplateRemoteService;

	private Page<ProdChannelSms> pageModel = new Page<ProdChannelSms>();

	public Map<String, String> getChannelMap() {
		return channelMap;
	}

	public void setChannelMap(Map<String, String> channelMap) {
		this.channelMap = channelMap;
	}

	public List<ProdChannel> getProdChannels() {
		return prodChannels;
	}

	public void setProdChannels(List<ProdChannel> prodChannels) {
		this.prodChannels = prodChannels;
	}

	public List<ComSmsTemplate> getSmsTemplates() {
		return smsTemplates;
	}

	public void setSmsTemplates(List<ComSmsTemplate> smsTemplates) {
		this.smsTemplates = smsTemplates;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public ProdChannelSms getModel() {
		return model;
	}

	public void setModel(ProdChannelSms model) {
		this.model = model;
	}

	public ProdChannelService getProdChannelService() {
		return prodChannelService;
	}

	public void setProdChannelService(ProdChannelService prodChannelService) {
		this.prodChannelService = prodChannelService;
	}

	public ComSmsTemplateService getSuperSmsTemplateRemoteService() {
		return superSmsTemplateRemoteService;
	}

	public void setSuperSmsTemplateRemoteService(
			ComSmsTemplateService superSmsTemplateRemoteService) {
		this.superSmsTemplateRemoteService = superSmsTemplateRemoteService;
	}

	public Page<ProdChannelSms> getPageModel() {
		return pageModel;
	}

	public void setPageModel(Page<ProdChannelSms> pageModel) {
		this.pageModel = pageModel;
	}

	public static void main(String[] args) {
		/*
		 * Field[] fs = ComSmsTemplate.class.getDeclaredFields(); for (Field
		 * field : fs) { System.out.println(field.getName()); }
		 */

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext-pet-back-beans.xml");
		System.out.println(context.getBean("superSmsTemplateRemoteService"));
	}

}
