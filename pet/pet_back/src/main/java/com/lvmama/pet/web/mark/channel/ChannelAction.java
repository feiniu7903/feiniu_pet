package com.lvmama.pet.web.mark.channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;

import com.lvmama.comm.pet.po.mark.MarkChannel;
import com.lvmama.comm.pet.service.mark.MarkChannelService;
import com.lvmama.comm.pet.vo.mark.MarkChannelVO;
import com.lvmama.comm.utils.StringUtil;

/**
 * 功能描述 ：会员卡，优惠券渠道管理
 * 所属          ：景域.驴妈妈
 * 创建 人    ：shangzhengyuan
 * 创建时间： 2011-04-19 
 */
public class ChannelAction extends com.lvmama.pet.web.BaseAction { 
	private static final long serialVersionUID = -8706595209025469771L;
	
	private static final String THREE_CHANNEL_CODE_VALUELESS="01000";
	
	private MarkChannelService markChannelService;
	List<MarkChannelVO> channelList; // 查询结界列表
	private Map<String, Object> searchConds = new HashMap<String, Object>(); // 查询条件映射

	private List<MarkChannel> firstChannelList; // 一级渠道下拉选项
	private List<MarkChannel> secondChannelList; // 二级渠道下拉选项
	private List<MarkChannel> threeChannelList; // 三级渠道下拉选项
	
	
/*	private Checkbox threeChannel_usedByOutterMedia;*/
	private Checkbox threeChannel_usedByProfitSharing;
	
	private String firstName;
	private String firstCode;
	private String secondName;
	private String secondCode;
	private String threeName;
	private String threeCode;
	
	private Label label;
	private Label labelInner;
	private Label labelOutter;
	private Label labelCoupon;
	
	private boolean firstChannelChecked;
	private boolean secondChannelChecked;
	private boolean threeChannelChecked;

	private MarkChannel firstChannel; // 一级渠道单个信息
	private MarkChannel secondChannel; // 二级渠道单个信息
	private MarkChannel threeChannel; // 三级渠道单个信息
	
	private String deleteChannelId;
	private Long firstId; // 选中的一级渠道ID
	private Long secondId; // 选中的二级渠道ID
	private Long threeId; // 选中的三级渠道ID
	
	/*private Listbox threeChannelApplicationType;*/
	
	private boolean isLosc;
	
	private boolean isCoupon;

	/**
	 * 初始化页面前加载
	 */
	protected void doBefore() throws Exception {
		if (firstChannel == null) {
			firstChannel = new MarkChannel();
		}
		if (secondChannel == null) {
			secondChannel = new MarkChannel();
		}
		if (threeChannel == null) {
			threeChannel = new MarkChannel();
		}
		
		// 1.取得一级渠道下拉选项
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("layer", 1);
		params.put("valid", "Y");
		firstChannelList = markChannelService.search(params);
		
		// 填入请选择选项
		setNullOption(firstChannelList);
		/*if (firstId != null) {*/
			// 2.取得二级渠道下拉选项
			params.clear();
			params.put("layer", 2);
			params.put("valid", "Y");
			/*params.put("fatherId", firstId);*/
			secondChannelList = markChannelService.search(params);
			
			// 填入请选择选项
			setNullOption(secondChannelList);
			if (secondId != null) {
				// 3.取得三级渠道下拉选项
				params.clear();
				params.put("layer", 3);
				params.put("valid", "Y");
				params.put("fatherId", secondId);
				threeChannelList = markChannelService.search(params);
				// 填入请选择选项
				setNullOption(threeChannelList);
				
				// 设置选中标识
				setOptionSign(secondChannelList, secondId, 2);
				setOptionSign(threeChannelList, threeId, 3);
			} else {
				threeChannelList = new ArrayList<MarkChannel>();
				setNullOption(threeChannelList);
			}

			setOptionSign(firstChannelList, firstId, 1);
		/*} else {
			secondChannelList = new ArrayList<MarkChannel>();
			threeChannelList = new ArrayList<MarkChannel>();
			setNullOption(secondChannelList);
			setNullOption(threeChannelList);
		}*/
	}
	
	@Override
	protected void doAfter() throws Exception {
		if (threeId != null) {
			MarkChannel pChannel3 = markChannelService.selectByPrimaryKey(threeId);
			updateThirdLevelCheckBox(pChannel3);
		}
	}

	/**
	 * 根据查询条件取得要显示的渠道列表
	 */
	public void search() {
		if(null!=label.getAttribute("channelApplicationType")){
			searchConds.put("channelApplicationType", label.getAttribute("channelApplicationType"));
		}
		if(null!=label.getAttribute("range")){
			searchConds.put("range", label.getAttribute("range"));
		}
		// 1. 取得要显示数据的总数
		searchConds = initialPageInfoByMap(markChannelService.countComplexVO(searchConds), searchConds);
		// 2. 取得渠道列表
		channelList = markChannelService.searchComplexVO(searchConds);
	}

	/**
	 * 根据输入的信息插入渠道 如果是二级，三级渠道要取得父渠道ID
	 */
	public void insert() throws Exception {
		// 设置三级渠道代码，以让后台使用channel_id去截取生成
		setThreeChannelCode();
		// 如果校验成功 ，则执行插入操作
		if (validate() && validRelateChannel() && validRelateEcho()) {
			// 校验新增的渠道信息是否已存在
			boolean sb1 = saveChannel(searchConds.get("IU1"), firstChannel, "一", 0);
			boolean sb2 = saveChannel(searchConds.get("IU2"), secondChannel, "二", 0);
			boolean sb3 = saveChannelTwo(searchConds.get("IU3"), threeChannel, "三", 0,secondChannel);
			if (sb1 && sb2 && sb3) {
				boolean bn = false;
				// 取得一级渠道的ID
				Long firstId = firstChannel.getChannelId();
				// 如果有修改标识则执行修改
				if (searchConds.get("IU1") != null && (Boolean) searchConds.get("IU1")) {
					// 如果一级渠道的代码不为空并且名称不为空，则新增并返回ID
					if (firstChannel.getChannelCode() != null && firstChannel.getChannelName() != null) {
						setChannelInfo(firstChannel, null, 1);
						firstId = markChannelService.insertMarkDicChannelWithLog(firstChannel, this.getSessionUserName());
						bn = true;
					}
				}
				// 取得二级渠道的ID
				Long secondId = secondChannel.getChannelId();
				// 如果有修改标识则执行修改
				if (searchConds.get("IU2") != null && (Boolean) searchConds.get("IU2")) {
					// 如果二级渠道的代码不为空并且名称不为空，则新增并返回ID
					if (secondChannel.getChannelCode() != null && secondChannel.getChannelName() != null) {
						setChannelInfo(secondChannel, firstId, 2);
						secondId = markChannelService.insertMarkDicChannelWithLog(secondChannel, getSessionUserName());
						bn = true;
					}
				}
				// 设置三级渠道的父ID
				threeChannel.setFatherId(secondId);
				// 如果有修改标识则执行修改
				if (searchConds.get("IU3") != null && (Boolean) searchConds.get("IU3")) {
					// 如果三级渠道的代码不为空并且名称不为空，则新增
					if (threeChannel.getChannelCode() != null && threeChannel.getChannelName() != null) {
						setChannelInfo(threeChannel, secondId, 3);
						threeChannel.setProfitSharing(this.threeChannel_usedByProfitSharing.isChecked() ? "true" : "false");
						if(labelInner!=null){
							threeChannel.setChannelApplicationType((String) this.labelInner.getAttribute("attr1"));//设置3级渠道类型，优惠券还是LOSC
							threeChannel.setRange((String) this.labelInner.getAttribute("attr2"));//站内  还是站外
						}
						else if(labelOutter!=null){
							threeChannel.setChannelApplicationType((String) this.labelOutter.getAttribute("attr1"));//设置3级渠道类型，优惠券还是LOSC
							threeChannel.setRange((String) this.labelOutter.getAttribute("attr2"));//站内  还是站外
						}
						else if(labelCoupon!=null){
							threeChannel.setChannelApplicationType((String) this.labelCoupon.getAttribute("attr1"));//设置3级渠道类型，优惠券还是LOSC
							threeChannel.setRange((String) this.labelCoupon.getAttribute("attr2"));//站内  还是站外
						}
						
						markChannelService.insertMarkDicChannelWithLog(threeChannel, getSessionUserName());
						bn = true;
					}
				}
				if (bn) {
					Messagebox.show("新增渠道成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					super.refreshParent("search");
					super.closeWindow();
				} else {
					Messagebox.show("请补充要新增的渠道信息", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}
	}

	/**
	 * 根据输入的信息修改渠道
	 */
	public void update() throws Exception {
		// 如果校验成功 ，则执行修改操作
		if (validate() && validRelateEcho()) {
			// 校验修改后的渠道信息是否已存在
			boolean sb1 = saveChannel(searchConds.get("IU1"), firstChannel, "一", 1);
			boolean sb2 = saveChannel(searchConds.get("IU2"), secondChannel, "二", 1);
			boolean sb3 = saveChannelTwo(searchConds.get("IU3"), threeChannel, "三", 1,secondChannel);
			boolean bn = false;
			if (sb1 && sb2 && sb3) {
				// 如果有修改标识则执行修改
				if (searchConds.get("IU1") != null && (Boolean) searchConds.get("IU1")) {
					// 如果一级渠道不为空并且ID不为空，则执行修改操作
					if (firstChannel != null && firstChannel.getChannelId() != null) {
						markChannelService.updateMarkDicChannelByPrimaryKeyWithLog(firstChannel, getSessionUserName());
						bn = true;
					}
				}
				// 如果有修改标识则执行修改
				if (searchConds.get("IU2") != null && (Boolean) searchConds.get("IU2")) {
					// 如果二级渠道不为空并且ID不为空，则执行修改操作
					if (secondChannel != null && secondChannel.getChannelId() != null) {
						markChannelService.updateMarkDicChannelByPrimaryKeyWithLog(secondChannel, getSessionUserName());
						bn = true;
					}
				}
				// 如果有修改标识则执行修改
				if (searchConds.get("IU3") != null && (Boolean) searchConds.get("IU3")) {
					// 如果三级渠道不为空并且ID不为空，则执行修改操作
					if (threeChannel != null && threeChannel.getChannelId() != null) {
						threeChannel.setProfitSharing(this.threeChannel_usedByProfitSharing.isChecked() ? "true" : "false");
						if(labelInner!=null){
							threeChannel.setChannelApplicationType((String) this.labelInner.getAttribute("attr1"));//设置3级渠道类型，优惠券还是LOSC
							threeChannel.setRange((String) this.labelInner.getAttribute("attr2"));//站内  还是站外
						}
						else if(labelOutter!=null){
							threeChannel.setChannelApplicationType((String) this.labelOutter.getAttribute("attr1"));//设置3级渠道类型，优惠券还是LOSC
							threeChannel.setRange((String) this.labelOutter.getAttribute("attr2"));//站内  还是站外
						}
						else if(labelCoupon!=null){
							threeChannel.setChannelApplicationType((String) this.labelCoupon.getAttribute("attr1"));//设置3级渠道类型，优惠券还是LOSC
							threeChannel.setRange((String) this.labelCoupon.getAttribute("attr2"));//站内  还是站外
						}
						markChannelService.updateMarkDicChannelByPrimaryKeyWithLog(threeChannel, getSessionUserName());
						bn = true;
					}
				}
				if (bn) {
					Messagebox.show("修改渠道成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
					super.refreshParent("search");
					super.closeWindow();
				} else {
					Messagebox.show("请选择要修改的渠道", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				}
			}
		}
	}

	private boolean saveChannel(Object saveSign, MarkChannel obj, String layer, int count) throws Exception {
		if (saveSign != null && (Boolean) saveSign) {
			if (obj != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				if (null != obj.getChannelCode()) {
					params.put("channelCode", obj.getChannelCode());
					List<MarkChannel> list = markChannelService.search(params);
					if (list.size() > count && !THREE_CHANNEL_CODE_VALUELESS.equals(obj.getChannelCode())) {
						Messagebox.show(layer + "级渠道名称或代码已存在", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
						return false;
					}
				}
				if (null != obj.getChannelName()) {
					params.clear();
					params.put("channelName", obj.getChannelName());
					List<MarkChannel> list = markChannelService.search(params);
					if (list.size() > count) {
						Messagebox.show(layer + "级渠道名称或代码已存在", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
						return false;
					}
				}
				
			}
		}
		return true;
	}
	/**
	 * 保存三级渠道
	 * @param saveSign
	 * @param obj
	 * @param layer
	 * @param count
	 * @return
	 */
	private boolean saveChannelTwo(Object saveSign, MarkChannel obj, String layer, int count,MarkChannel twoMarkChannel) throws Exception {
		if (saveSign != null && (Boolean) saveSign) {
			if (obj != null) {
				Map<String, Object> params = new HashMap<String, Object>();
				if (null != obj.getChannelCode()) {
					params.put("channelCode", obj.getChannelCode());
					List<MarkChannel> list = markChannelService.search(params);
					if (list.size() > count && !THREE_CHANNEL_CODE_VALUELESS.equals(obj.getChannelCode())) {
						Messagebox.show(layer + "级渠道名称或代码已存在", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
						return false;
					}
				}
				if (null != obj.getChannelName()&&twoMarkChannel.getChannelId()!=null) {
					params.clear();
					params.put("fatherId",twoMarkChannel.getChannelId());
					params.put("channelName", obj.getChannelName());
					params.put("layer",3);
					List<MarkChannel> list = markChannelService.search(params);
					if (list.size() > count) {
						Messagebox.show(layer + "级渠道名称或代码已存在", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
						return false;
					}
				}
				
			}
		}
		return true;
	}

	/**
	 * 根据页面输入的信息进行校验
	 * 
	 * @return boolean
	 */
	public boolean validate() throws Exception {
		boolean[] b = { false };
		String[] array = new String[] { "三", "名称", "代码", "6", "41" };
		if (!channelValid(threeChannel, b, array)) {
			return false;
		}
		b[0]=validateChannelComment(threeChannel);
		array = new String[] { "二", "名称", "代码", "6", "41" };
		if (!channelValid(secondChannel, b, array)) {
			return false;
		}
		array = new String[] { "一", "名称", "代码", "6", "41" };
		if (!channelValid(firstChannel, b, array)) {
			return false;
		}
		if (b[0] == false) {
			Messagebox.show("请补充要提交的信息", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		
	/*	if(threeChannelApplicationType == null || threeChannelApplicationType.getSelectedItem() == null){
			Messagebox.show("请选择渠道应用于LOSC还是优惠券", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}*/
		
		return true;
	}

	private boolean channelValid(MarkChannel channel, boolean[] b, String[] map) throws Exception {
		String[] array;
		String key, value;
		if (channel != null && (channel.getChannelCode() != null || channel.getChannelName() != null)) {
			array = new String[] { map[0], map[1], map[2], map[3] };
			key = channel.getChannelName();
			value = channel.getChannelCode();
			if (!(subValid(b, array, value, key, 30))) {
				return false;
			}
			array = new String[] { map[0], map[2], map[1], map[4] };
			if (!(subValid(b, array, key, value, 40))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据是否验证，渠道层级，长度限制，渠道名及渠道码进行核验
	 * 
	 * @param b
	 * @param array
	 * @param key
	 * @param value
	 * @return
	 * @throws Exception
	 */
	private boolean subValid(boolean[] b, String[] array, String key, String value, Integer length) throws Exception {
		if (b[0] == true || (key != null && key.trim().length() > 0)) {
			b[0] = true;
			if (StringUtil.isEmptyString(key) || StringUtil.isEmptyString(value)) {
				Messagebox.show("请补充" + array[0] + "级渠道" + array[1] + "信息", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
			byte[] ba = key.getBytes();
			if (ba.length > length) {
				Messagebox.show(array[0] + "级渠道" + array[2] + "长度只能小于" + array[3] + "个字符", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}
		return true;
	}

	private boolean validRelateChannel() throws Exception {
		if (threeChannel != null && (threeChannel.getChannelName() != null || threeChannel.getChannelCode() != null)) {
			if (secondChannel == null) {
				Messagebox.show("请选择或新增父渠道", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			} else if (secondChannel.getChannelCode() == null && secondChannel.getChannelName() == null) {
				Messagebox.show("请选择或新增父渠道", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}
		if (secondChannel != null && (secondChannel.getChannelName() != null || secondChannel.getChannelCode() != null)) {
			if (firstChannel == null) {
				Messagebox.show("请选择或新增父渠道", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			} else if (firstChannel.getChannelCode() == null && firstChannel.getChannelName() == null) {
				Messagebox.show("请选择或新增父渠道", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}
		return true;
	}

	private boolean validRelateEcho() throws Exception {
		if (!(validSubRelateEcho(firstChannel, secondChannel, "一", "二"))) {
			return false;
		}
		if (!(validSubRelateEcho(secondChannel, threeChannel, "二", "三"))) {
			return false;
		}
		if (!(validSubRelateEcho(firstChannel, threeChannel, "一", "三"))) {
			return false;
		}
		return true;
	}

	private boolean validSubRelateEcho(MarkChannel one, MarkChannel two, String str1, String str2) throws Exception {
		if (one != null && two != null) {
			String name1 = one.getChannelName();
			String name2 = two.getChannelName();
			String code1 = one.getChannelCode();
			String code2 = two.getChannelCode();
			if (name1 != null && name2 != null && name1.equals(name2)) {
				Messagebox.show(str1 + "级渠道与" + str2 + "级渠道名称相同", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
			if (code1 != null && code2 != null && code1.equals(code2)) {
				Messagebox.show(str1 + "级渠道与" + str2 + "级渠道代码相同", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
				return false;
			}
		}
		return true;
	}

	/**
	 * 设置渠道下拉框中为空的选项
	 * 
	 * @param list
	 */
	private void setNullOption(List<MarkChannel> list) {
		MarkChannel obj = new MarkChannel();
		obj.setChannelId(null);
		obj.setChannelName(" 请选择   ");
		list.add(0, obj);
	}

	
	/**
	 * 设置三级渠道代码，以便不检查渠道代码是否为空，让后台自动生成
	 */
	private void setThreeChannelCode(){
		if (null != threeChannel 
				&& !StringUtil.isEmptyString(threeChannel.getChannelName()) 
				&& null==threeChannel.getChannelCode() ){
			setCodeNull(threeChannel);
		}
	}
	/**
	 * @throws InterruptedException 
	 * 
	 */
	private boolean validateChannelComment(final MarkChannel threeChannel) throws InterruptedException{
		if(null!=threeChannel && !StringUtil.isEmptyString(threeChannel.getChannelName()) 
				&& !StringUtil.isEmptyString(threeChannel.getChannelComment())
				&& threeChannel.getChannelComment().length()>800){
			Messagebox.show("哈哈，三级渠道描述超长了，请检查是否超过400个字。", "警告", Messagebox.OK, Messagebox.EXCLAMATION);
			return false;
		}
		return true;
	}
	
	/**
	 * 设置渠道代码，以便不检查渠道代码是否为空
	 * @param channel
	 */
	private void setCodeNull(MarkChannel channel){
		if (null!=channel && null==channel.getChannelCode() ) {
			channel.setChannelCode(THREE_CHANNEL_CODE_VALUELESS);
		}
	}
	
	/**
	 * 当渠道下拉选项被改变是执行
	 * 
	 * @param channelId
	 * @param layer
	 */
	public void changeChannel(Long channelId, Integer layer) {
		// 1.如果渠道ID不为空，则到后台查询数据
		if (channelId != null) {
			MarkChannel pChannel = markChannelService.selectByPrimaryKey(channelId);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("layer", layer);
			params.put("fatherId", channelId);
			if (layer.equals(2)) {// 如果层级为2，则查询二级渠道
				secondChannelList = markChannelService.search(params);
				// 清除三级渠道
				threeChannelList.clear();
				// 设置“请选择”选项
				setNullOption(secondChannelList);
				setNullOption(threeChannelList);
				
			} else if (layer.equals(3)) {// 如果层级为3，则查询三级渠道
				threeChannelList = markChannelService.search(params);
				// 设置“请选择”选项
				setNullOption(threeChannelList);

				this.updateThirdLevelCheckBox(pChannel);
			} else if (layer.equals(4)) {

			}
			// 2.如果渠道ID为空，则不查询数据
		} else {
			if (layer.equals(2)) {// 如果层级为2，则清空二级渠道选项及三级渠道选项，同时设置"请选择"选项
				secondChannelList.clear();
				setNullOption(secondChannelList);
				threeChannelList.clear();
				setNullOption(threeChannelList);
			} else if (layer.equals(3)) {// 如果层级为3，则三级渠道选项，同时设置"请选择"选项
				threeChannelList.clear();
				setNullOption(threeChannelList);
			}
		}
	}

	/**
	 * 当渠道选项被改变时，记录被选中的数据
	 * 
	 * @param channelId
	 * @param layer
	 */
	public void putChannelId(Long channelId, Integer layer) {
		searchConds.put(layer.toString(), channelId);
		inputChannelInfo(channelId, layer);
	}

	/**
	 * 选中渠道下拉选项后显示其信息
	 * 
	 * @return
	 */
	public void inputChannelInfo(Long channelId, Integer layer) {
		MarkChannel channel = null;
		if (channelId != null) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("channelId", channelId);
			List<MarkChannel> list = markChannelService.search(parameters);
			if (list.size() > 0) {
				channel = list.get(0);
			} else {
				channel = new MarkChannel();
			}
		} else {
			channel = new MarkChannel();
		}
		if (layer.equals(1)) {
			firstChannel = channel;
			secondChannel = new MarkChannel();
			threeChannel = new MarkChannel();
		} else if (layer.equals(2)) {
			secondChannel = channel;
			threeChannel = new MarkChannel();
		} else if (layer.equals(3)) {
			threeChannel = channel;
			updateThirdLevelCheckBox(channel);
		}
	}
	
	private void updateThirdLevelCheckBox(MarkChannel mdc) {
		if (null != threeChannel_usedByProfitSharing) {
			if ("true".equals(mdc.getProfitSharing())) {
				threeChannel_usedByProfitSharing.setChecked(true);
			} else {
				threeChannel_usedByProfitSharing.setChecked(false);
			}
		}
	}

	/**
	 * 修改ThreeChannel 的 应用关系
	 * 
	 * @param value
	 * @param checked
	 */
//	public void setThreeChannelUsedBy(String value, boolean checked) {
//		String beforeUsedBy = threeChannel.getUsedBy();
//		if (checked) {
//			if (StringUtils.isBlank(beforeUsedBy)) {
//				threeChannel.setUsedBy(value);
//			} else {
//				threeChannel.setUsedBy(beforeUsedBy + "," + value);
//			}
//		} else {
//			if (beforeUsedBy.indexOf("," + value) > 0) {
//				threeChannel.setUsedBy(beforeUsedBy.replace("," + value, ""));
//			} else {
//				threeChannel.setUsedBy(beforeUsedBy.replace(value, ""));
//			}
//		}
//	}

	/**
	 * 设置修改或新增标识
	 * 
	 * @param layer
	 * @param sign
	 */
	public void setSaveSign(Integer layer, boolean sign) {
		searchConds.put("IU" + layer.toString(), sign);
	}

	/**
	 * 设置渠道信息
	 * 
	 * @param obj
	 * @param fatherId
	 * @param layer
	 */
	private void setChannelInfo(MarkChannel obj, Long fatherId, Integer layer) {
		obj.setFatherId(fatherId);
		obj.setLayer(layer);
		obj.setValid("Y");
		obj.setCreateTime(new java.util.Date());
	}
	/**
	 * 为当前选中的渠道选项设置标识
	 * 
	 * @param list
	 * @param channelId
	 */
	private void setOptionSign(List<MarkChannel> list, Long id, Integer layer) {
		for (MarkChannel obj : list) {
			if (id != null && id.equals(obj.getChannelId())) {
				obj.setChecked("true");
				if (layer == 1) {
					firstChannel = obj;
				} else if (layer == 2) {
					secondChannel = obj;
				} else if (layer == 3) {
					threeChannel = obj;
				}
			}
		}
	}
	
	
	public MarkChannelService getMarkChannelService() {
		return markChannelService;
	}

	public void setMarkChannelService(
			MarkChannelService markDictChannelService) {
		this.markChannelService = markDictChannelService;
	}

	public List<MarkChannelVO> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<MarkChannelVO> channelList) {
		this.channelList = channelList;
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}

	public List<MarkChannel> getFirstChannelList() {
		return firstChannelList;
	}

	public void setFirstChannelList(List<MarkChannel> firstChannelList) {
		this.firstChannelList = firstChannelList;
	}

	public List<MarkChannel> getSecondChannelList() {
		return secondChannelList;
	}

	public void setSecondChannelList(List<MarkChannel> secondChannelList) {
		this.secondChannelList = secondChannelList;
	}

	public List<MarkChannel> getThreeChannelList() {
		return threeChannelList;
	}

	public void setThreeChannelList(List<MarkChannel> threeChannelList) {
		this.threeChannelList = threeChannelList;
	}

	public boolean isFirstChannelChecked() {
		return firstChannelChecked;
	}

	public void setFirstChannelChecked(boolean firstChannelChecked) {
		this.firstChannelChecked = firstChannelChecked;
	}

	public boolean isSecondChannelChecked() {
		return secondChannelChecked;
	}

	public void setSecondChannelChecked(boolean secondChannelChecked) {
		this.secondChannelChecked = secondChannelChecked;
	}

	public boolean isThreeChannelChecked() {
		return threeChannelChecked;
	}

	public void setThreeChannelChecked(boolean threeChannelChecked) {
		this.threeChannelChecked = threeChannelChecked;
	}

	public MarkChannel getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(MarkChannel firstChannel) {
		this.firstChannel = firstChannel;
	}

	public MarkChannel getSecondChannel() {
		return secondChannel;
	}

	public void setSecondChannel(MarkChannel secondChannel) {
		this.secondChannel = secondChannel;
	}

	public MarkChannel getThreeChannel() {
		return threeChannel;
	}

	public void setThreeChannel(MarkChannel threeChannel) {
		this.threeChannel = threeChannel;
	}

	public String getDeleteChannelId() {
		return deleteChannelId;
	}

	public void setDeleteChannelId(String deleteChannelId) {
		this.deleteChannelId = deleteChannelId;
	}

	public Long getFirstId() {
		return firstId;
	}

	public void setFirstId(Long firstId) {
		this.firstId = firstId;
	}

	public Long getSecondId() {
		return secondId;
	}

	public void setSecondId(Long secondId) {
		this.secondId = secondId;
	}

	public Long getThreeId() {
		return threeId;
	}

	public void setThreeId(Long threeId) {
		this.threeId = threeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstCode() {
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getSecondCode() {
		return secondCode;
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode;
	}

	public String getThreeName() {
		return threeName;
	}

	public void setThreeName(String threeName) {
		this.threeName = threeName;
	}

	public String getThreeCode() {
		return threeCode;
	}

	public void setThreeCode(String threeCode) {
		this.threeCode = threeCode;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public Label getLabelInner() {
		return labelInner;
	}

	public void setLabelInner(Label labelInner) {
		this.labelInner = labelInner;
	}

	public Label getLabelOutter() {
		return labelOutter;
	}

	public void setLabelOutter(Label labelOutter) {
		this.labelOutter = labelOutter;
	}

	public Label getLabelCoupon() {
		return labelCoupon;
	}

	public void setLabelCoupon(Label labelCoupon) {
		this.labelCoupon = labelCoupon;
	}

/*	public Checkbox getThreeChannel_usedByOutterMedia() {
		return threeChannel_usedByOutterMedia;
	}

	public void setThreeChannel_usedByOutterMedia(
			Checkbox threeChannel_usedByOutterMedia) {
		this.threeChannel_usedByOutterMedia = threeChannel_usedByOutterMedia;
	}*/

	public Checkbox getThreeChannel_usedByProfitSharing() {
		return threeChannel_usedByProfitSharing;
	}

	public void setThreeChannel_usedByProfitSharing(
			Checkbox threeChannel_usedByProfitSharing) {
		this.threeChannel_usedByProfitSharing = threeChannel_usedByProfitSharing;
	}
/*
	public Listbox getThreeChannelApplicationType() {
		return threeChannelApplicationType;
	}

	public void setThreeChannelApplicationType(Listbox threeChannelApplicationType) {
		this.threeChannelApplicationType = threeChannelApplicationType;
	}
	*/
	
	
}
