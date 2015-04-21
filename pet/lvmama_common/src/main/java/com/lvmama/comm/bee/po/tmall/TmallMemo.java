package com.lvmama.comm.bee.po.tmall;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TmallMemo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5021844519340383701L;

	private static final Log log = LogFactory.getLog(TmallMemo.class);

	private Date tourDate;// 游玩日期
	private String seller;// 淘宝客服工号
	private List<TmallPerson> persons;// 联系人,游玩人等

	public Date getTourDate() {
		return tourDate;
	}

	public void setTourDate(Date tourDate) {
		this.tourDate = tourDate;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public List<TmallPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<TmallPerson> list) {
		this.persons = list;
	}

	public static HashMap<String, TmallMemo> processMemo(String tmallMemo) {
		if (tmallMemo == null)
			return null;
		String[] tour = null;
		if (tmallMemo.indexOf("e") != -1) {
			tour = tmallMemo.split("e");
		} else {
			return null;
		}
		HashMap<String, TmallMemo> map = new HashMap<String, TmallMemo>();
		List<TmallPerson> list = null;
		TmallMemo memo = null;
		TmallPerson person = null;
		Date tourDate = null;
		for (int k = 0; k < tour.length; k++) {
			memo = new TmallMemo();
			list = new ArrayList<TmallPerson>();
			String[] ordInfo = tour[k].toLowerCase().split("\\r\\n");
			if(ordInfo.length==1){
				ordInfo = tour[k].toLowerCase().split("\\n");
			}

			if (ordInfo.length == 4) {
				String[] temp = null;
				if (ordInfo[0].indexOf("a") != -1) {
					temp = ordInfo[0].split("a");
				} else {
					continue;
				}
				if (temp.length == 2) {
					person = new TmallPerson(temp[0], temp[1]);
				} else if(temp.length == 3) {
					person = new TmallPerson(temp[0], temp[1], temp[2]);
				}else{
					continue;
				}
				list.add(person);
				memo.setSeller(ordInfo[3]);
				try {
					tourDate = new SimpleDateFormat("yyyy-MM-dd").parse(ordInfo[1]);
				} catch (ParseException e) {
					log.error("date parse error, date is: " + ordInfo[1]);
					tourDate = null;
				}
				memo.setTourDate(tourDate);
				memo.setPersons(list);
				if(map.get(ordInfo[2])==null){
					map.put(ordInfo[2], memo);
				}
			} else if (ordInfo.length > 4) {
				String[] ps = null;
				for (int i = 0; i < ordInfo.length - 3; i++) {
					if (ordInfo[i].indexOf("a") != -1) {
						ps = ordInfo[i].split("a");
					} else {
						continue;
					}
					if (ps.length == 2) {
						person = new TmallPerson(ps[0], ps[1]);
					} else if(ps.length == 3){
						person = new TmallPerson(ps[0], ps[1], ps[2]);
					}else{
						continue;
					}
					list.add(person);
				}

				memo.setSeller(ordInfo[ordInfo.length - 1]);
				try {
					tourDate = new SimpleDateFormat("yyyy-MM-dd")
							.parse(ordInfo[ordInfo.length - 3]);
				} catch (ParseException e) {
					log.error("date parse error, date is: " + ordInfo[1]);
					tourDate = null;
				}
				memo.setTourDate(tourDate);
				memo.setPersons(list);
				if(map.get(ordInfo[ordInfo.length - 2])==null){
					map.put(ordInfo[ordInfo.length - 2], memo);
				}
			}
		}

		return map;

	}
	public static void main(String[] args) {
		String tmallMemo="陈志鹏a13761324977\n陈志鹏a13761324977\n2013-07-20\n206710\nlv5078\ne\n陈志鹏a13761324977\n陈志鹏a13761324977\n2013-07-20\n102921\nlv5078\ne" ;
		@SuppressWarnings("rawtypes")
		Map map=processMemo(tmallMemo);
		System.out.println(map.size());
	}
	

}
