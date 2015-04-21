package com.lvmama.tnt.front.order.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.tnt.order.po.TntOrder;
import com.lvmama.tnt.order.vo.Person;

public class OrderCollecttionVo {

	private OrdOrder ordOrder = new OrdOrder();
	private TntOrder tntOrder = new TntOrder();
	/**
	 *  取票人列表.
	 */
	private List<Person> personList = new ArrayList<Person>();
	/**
	 * 游玩人列表.
	 */
	private List<Person> travellerList = new ArrayList<Person>();
	
	private String waitPaymenTime;
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}
	public void setOrdOrder(OrdOrder ordOrder) {
		if(ordOrder!=null){
			this.ordOrder = ordOrder;
		}		
	}
	public TntOrder getTntOrder() {
		return tntOrder;
	}
	public void setTntOrder(TntOrder tntOrder) {
		if(tntOrder!=null){
			this.tntOrder = tntOrder;
		}	
	}
	public String getWaitPaymenTime() {
		if(StringUtil.isNotEmptyString(this.waitPaymenTime)){
			return waitPaymenTime;
		}
		return "0 分钟";
	}
	public void setWaitPaymenTime(String waitPaymenTime) {
		this.waitPaymenTime = waitPaymenTime;
	}
	
	public String getZhCreateTime(){
		if(this.getOrdOrder().getCreateTime()!=null){
			return DateUtil.getFormatDate(this.getOrdOrder().getCreateTime(), "yyyy-MM-dd HH:mm");
		}
		return "";
	}
	
	public String getContactHiddenMobile(){
		if(this.getOrdOrder().getContact().getMobile()!=null){
			return com.lvmama.comm.utils.StringUtil.hiddenMobile(this.getOrdOrder().getContact().getMobile());
		}
		return this.getOrdOrder().getContact().getMobile();
	}
	public List<Person> getPersonList() {
		return personList;
	}
	public void setPersonList(List<OrdPerson> personList) {
		if(personList!=null){
			for(OrdPerson per:personList){
				Person  person = new Person();
				BeanUtils.copyProperties(per, person);
				person.setCnCertType(per.getZhCertType());
				person.setPersonType(per.getPersonType());
				person.setFullName(per.getName());
				if(per.getCertNo()!=null){
					person.setHiddenIDCard(com.lvmama.comm.utils.StringUtil.hiddenIDCard(per.getCertNo()));
				}
				if(per.getEmail()!=null){
					person.setHiddenEmail(com.lvmama.comm.utils.StringUtil.hiddenEmail(per.getEmail()));
				}
				if(per.getMobile()!=null){
					person.setHiddenMobile(com.lvmama.comm.utils.StringUtil.hiddenMobile(per.getMobile()));
				}
				this.personList.add(person);
			}
		}
	}
	public List<Person> getTravellerList() {
		return travellerList;
	}
	public void setTravellerList(List<OrdPerson> travellerList) {
		if(travellerList!=null){
			for(OrdPerson per:travellerList){
				Person  person = new Person();
				BeanUtils.copyProperties(per, person);
				person.setCnCertType(per.getZhCertType());
				person.setPersonType(per.getPersonType());
				person.setFullName(per.getName());
				if(per.getCertNo()!=null){
					person.setHiddenIDCard(com.lvmama.comm.utils.StringUtil.hiddenIDCard(per.getCertNo()));
				}
				if(per.getEmail()!=null){
					person.setHiddenEmail(com.lvmama.comm.utils.StringUtil.hiddenEmail(per.getEmail()));
				}
				if(per.getMobile()!=null){
					person.setHiddenMobile(com.lvmama.comm.utils.StringUtil.hiddenMobile(per.getMobile()));
				}
				this.travellerList.add(person);
			}
		}
	}
	
	public String getSaveAmountYuan(){
		Float n = this.ordOrder.getMarketAmountYuan()-this.tntOrder.getOrderAmountYuan();
		return PriceUtil.formatDecimal(n);
	}
	
}
