/**
 * 
 */
package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;

import com.lvmama.comm.pet.po.search.ProdTrainCache;

/**
 * @author yangbin
 *
 */
public class TrainBean implements Comparable<TrainBean>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 805026401449520730L;
	private List<ProdTrainCache> ticketList;
	private ProdTrainCache first;
	private boolean soldout;
	public List<ProdTrainCache> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<ProdTrainCache> ticketList) {
		this.ticketList = ticketList;
		first = this.ticketList.get(0);
		for(ProdTrainCache c:ticketList){
			if(c.hasSoldout()){
				soldout=true;
				break;
			}
		}
	}
	
	public ProdTrainCache getFirst(){
		return first;
	}

	public boolean isStartStation(){
		return BooleanUtils.toBoolean(first.getStartStation());
	}
	
	public boolean isEndStation(){
		return BooleanUtils.toBoolean(first.getEndStation());
	}
	
	public boolean isSoldout(){
		return soldout; 
	}
	
	private int compareDepartureTime(long other){
		if(first.getDepartureTime()>other){
			return 1;
		}else if(first.getDepartureTime()<other){
			return -1;
		}
		return 0;
	}

	@Override
	public int compareTo(TrainBean o) {
		if(!this.isSoldout()&&!o.isSoldout()||this.isSoldout()&&o.isSoldout()){
			return compareDepartureTime(o.getFirst().getDepartureTime());
		}else if(this.isSoldout()){
			return -1;
		}else{
			return 1;
		}
	}
	
	public String getZhTakeTime(){
		if(first!=null && first.getTakenTime()!=null){
			long tmp=first.getTakenTime();
			long hour=0;
			if(tmp>=60){
				hour = tmp/60;
				tmp=tmp-hour*60;
			}
			StringBuffer sb = new StringBuffer();
			if(hour>0){
				sb.append(hour);
				sb.append("时");
			}
			if(tmp>0){
				sb.append(tmp);
				sb.append("分");
			}
			return sb.toString();
		}
		return "";
	}
}
