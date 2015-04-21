package com.lvmama.passport.web.pass;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.lvmama.ZkBaseAction;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.passport.disney.DisneyUtil;
import com.lvmama.passport.disney.model.Event;
import com.lvmama.passport.disney.model.EventResponse;
import com.lvmama.passport.disney.model.ProductBean;
import com.lvmama.passport.disney.model.Show;
import com.lvmama.passport.disney.model.ShowResponse;

public class DisneyProductAction  extends ZkBaseAction{
	/**
	 * 查询条件
	 */
	private Map<String, Object> queryOption = new HashMap<String, Object>();
	private static final long serialVersionUID = 7090361444752615343L;
	private List<ProductBean> disneyProducts= new ArrayList<ProductBean>();;
	
	public void doQuery() throws Exception {
		disneyProducts.clear();
		Date startDate =(Date)queryOption.get("startDate");
    	Date endDate = (Date)queryOption.get("endDate");
    	if(startDate == null){
    		startDate = new Date();
    	}
    	if(endDate == null){
    		endDate = DateUtils.addYears(startDate, 7);
    	}
    	
    	String fromDate=DateUtil.formatDate(startDate, "yyyy-MM-dd");
    	String toDate=DateUtil.formatDate(endDate, "yyyy-MM-dd");;
		
		EventResponse res=DisneyUtil.init().getEvents();
		for(Event event:res.getEvents()){
			String response=DisneyUtil.init().getShows(event.getEventId(),fromDate,toDate);
			ShowResponse showRes=DisneyUtil.init().paseShowResponse(response);
			if(showRes.getShows()!=null && showRes.getShows().size()>0){
				for(Show show:showRes.getShows()){
					ProductBean bean=new ProductBean();
					bean.setEventId(event.getEventId());
					bean.setName(event.getName());
					bean.setRemark(event.getRemark());
					bean.setShowId(show.getShowId());
					bean.setShowDateTime(show.getShowDateTime());
					bean.setCutoffDateTime(show.getCutoffDateTime());
					disneyProducts.add(bean);
				}
			}else{
				ProductBean bean=new ProductBean();
				bean.setEventId(event.getEventId());
				bean.setName(event.getName());
				bean.setRemark(event.getRemark());
				bean.setShowId("");
				bean.setShowDateTime("");
				bean.setCutoffDateTime("");
				disneyProducts.add(bean);
			}
		}
			
	}
	
	public List<ProductBean> getDisneyProducts() {
		return disneyProducts;
	}

	public void setQueryOption(Map<String, Object> queryOption) {
		this.queryOption = queryOption;
	}

	public Map<String, Object> getQueryOption() {
		return queryOption;
	}


	public void setDisneyProducts(List<ProductBean> disneyProducts) {
		this.disneyProducts = disneyProducts;
	}
}
