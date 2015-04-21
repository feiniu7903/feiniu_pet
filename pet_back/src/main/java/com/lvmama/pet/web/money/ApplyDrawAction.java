package com.lvmama.pet.web.money;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.lvmama.comm.pet.po.pub.ComBank;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.pet.web.BaseAction;


public class ApplyDrawAction extends BaseAction{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private CashAccountService cashAccountService;
	private PlaceCityService placeCityService;
	HashMap<String,Object> userInfo;
	private List<ComBank> bankList;
	private List<ComProvince> provinceList;
	private List<ComCity> cityList;
	private ComBank drawBank; //提现银行
	Listbox drawto;
	Textbox account;
	Textbox accountName;
	Textbox manulBranchBank;
	//是否为后台发起的提现申请
	Textbox isSuperback;
	//提现金额
	Textbox drawMoney;
	//对公对私标志
	Radiogroup flag;
	
	Listbox province;
	Listbox city;
	Textbox kaiHuHang;
	
	@SuppressWarnings("unchecked")
	protected void doBefore() throws Exception {
		//
		userInfo = (HashMap<String,Object>)Executions.getCurrent().getArg().get("item");
		//
		bankList = cashAccountService.getComBankList();
		ComBank comBank =new ComBank();
		comBank.setBankName("--请选择--");
		comBank.setId(0L);
		bankList.add(0, comBank);

		provinceList = placeCityService.getProvinceList();
	}

	public void applyDraw() {
		if(StringUtils.isBlank(drawMoney.getValue())){
			alert("提现金额不能为空!");
			return ;
		}
		Long drawMoneyFen=0L;
		try {
			drawMoneyFen=PriceUtil.moneyConvertLongPrice(drawMoney.getValue());	
		} catch (Exception e) {
			alert("提现金额输入必须为正整数!");
			return ;
		}
		if(drawMoneyFen<=0){
			alert("提现金额必须大于0!");
			return ;
		}
		Long maxDrawMoney=((CashAccountVO) userInfo.get("moneyAccount")).getMaxDrawMoney();
		if(drawMoneyFen>maxDrawMoney){
			alert("提现金额不能大于最大提现金额："+((CashAccountVO) userInfo.get("moneyAccount")).getMaxDrawMoneyYuan());
			return ;
		}
		if(drawto.getSelectedItem() == null) {
			alert("请选择要提现到的银行！");
			return;
		}
		/**
		 * 支付宝
		 */
		if (drawto.getSelectedItem().getLabel().equals("支付宝")) {
			if (account.getValue() == null
					|| account.getValue().equals("")) {
				alert("支付宝账户不能为空!");
				return;
			}
			if (accountName.getValue() == null
					|| accountName.getValue().equals("")) {
				alert("支付宝姓名不能为空!");
				return;
			}
			try {
				cashAccountService.applyDraw2AliPay(((UserUser) userInfo
						.get("user")).getId(), account.getValue(),
						accountName.getValue(), drawMoneyFen, false,
						this.getSessionUserName(),this.isSuperback.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				alert("没有足够的可提现金额!");
				return;
			}
		} else {
			
			if(drawBank==null || null == drawBank.getBankName()){
				alert("请选择提现银行!");
				return;
			}
			
			if(null==flag.getSelectedItem()){
				alert("请选择对公对私!");
				return;
			}
			
			//对公需要收款银行先关信息
			String provinceName="";
			String cityName="";
			String kaiHuHangName="";
			if(null!=flag.getSelectedItem()&&"1".equals(flag.getSelectedItem().getValue())){
				if(null==province.getSelectedItem()||province.getSelectedItem().getLabel().equals("请选择")){
					alert("请选择收款银行所在省份!");
					return;
				}
				if(null==city.getSelectedItem()||city.getSelectedItem().getLabel().equals("请选择")){
					alert("请选择收款银行所在市!");
					return;
				}
				if(StringUtils.isBlank(kaiHuHang.getValue())){
					alert("请输入收款支行名称!");
					return;
				}
				provinceName=province.getSelectedItem().getLabel();
				cityName=city.getSelectedItem().getLabel();
				kaiHuHangName=kaiHuHang.getValue();
				if(!StringUtils.endsWith(cityName, "市")){
					cityName=cityName+"市";
				}
			}
			if (StringUtils.isBlank(account.getValue())) {
				alert("帐号不能为空!");
				return;
			}
			if (StringUtils.isBlank(accountName.getValue())) {
				alert("收款户名不能为空!");
				return;
			}
			
			try {
				isSuperback.getValue();
				cashAccountService.applyDraw2Bank(((UserUser) userInfo
						.get("user")).getId(), drawBank.getBankName(),
						account.getValue(), accountName.getValue(), kaiHuHangName, provinceName, cityName,
						drawMoneyFen, false,flag.getSelectedItem().getValue(), this
								.getSessionUserName(),this.isSuperback.getValue());
			} catch (Exception e) {
				e.printStackTrace();
				alert("没有足够的可提现金额!");
				return;
			}
		}
		alert("申请成功!");
		this.refreshParent("search");
		this.closeWindow();
	}

	/**
	 * 根据省份查询城市
	 * @param provinceId
	 */
	public void loadCitys(String provinceId) {
		if(StringUtils.isBlank(provinceId)){
			return;
		}
		this.cityList=placeCityService.getCityListByProvinceId(provinceId);
	}


	public String getCityId() {
		return null;
	}

	public HashMap<String, Object> getUserInfo() {
		return userInfo;
	}

	public List<ComBank> getBankList() {
		return bankList;
	}

	public void setBankList(List<ComBank> bankList) {
		this.bankList = bankList;
	}

	public List<ComProvince> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<ComProvince> provinceList) {
		this.provinceList = provinceList;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}

	public void setCityList(List<ComCity> cityList) {
		this.cityList = cityList;
	}

	public ComBank getDrawBank() {
		return drawBank;
	}

	public void setDrawBank(ComBank drawBank) {
		this.drawBank = drawBank;
	}

	public Textbox getIsSuperback() {
		return isSuperback;
	}

	public void setIsSuperback(Textbox isSuperback) {
		this.isSuperback = isSuperback;
	}
}
