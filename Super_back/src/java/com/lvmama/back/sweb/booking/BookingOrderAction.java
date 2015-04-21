package com.lvmama.back.sweb.booking;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.util.JSONUtils;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.booking.po.BookingHotel;
import com.lvmama.comm.booking.vo.BookingOrder;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "bookingOrderList", location = "/WEB-INF/pages/back/booking/bookingOrderList.jsp")
	})
@Namespace("/bookingOrder")
public class BookingOrderAction extends BackBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8840801424336950796L;
	protected final Log log =LogFactory.getLog(this.getClass().getName());

	private String begin_date;
	private String end_date;
	private String checkout_from;
	private String checkout_until;
	private String id;
	private String jsonString;
	private Integer offset;
	private Integer rows;
	private String direction;
	private List<BookingOrder> bookingOrderList;

	/**
	 * 订单列表
	 * @return
	 */
	@Action("bookingOrderList")
	public String bookingOrderList(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		String bookingUser = Constant.getInstance().getProperty("booking.user");
		String url = "https://" + bookingUser + "@distribution-xml.booking.com/json/bookings.getBookingDetails";//远程url
		
		this.rows = 300;
		
		if(null==this.offset||this.offset<0){
			this.offset=0;
		}
		
		if("first".equals(direction)){
			this.offset=0;
		}else{
			if("previous".equals(direction)){
				if(offset > rows){
					offset = offset - rows;
				}else{
					this.offset=0;
				}
				
			}
			if("next".equals(direction)){
				offset = offset + rows;
			}
		}
		
		map.put("offset", offset+"");
		
		map.put("rows", rows+"");
		
		if(!StringUtil.isEmptyString(this.id)){
			map.put("id", id);
		}
		
		if(!StringUtil.isEmptyString(this.begin_date)){
			map.put("begin_date", begin_date);
		}
		
		if(!StringUtil.isEmptyString(this.end_date)){
			map.put("end_date", end_date);
		}
		
		if(!StringUtil.isEmptyString(this.checkout_from)){
			map.put("checkout_from", checkout_from);
		}
		
		if(!StringUtil.isEmptyString(this.checkout_until)){
			map.put("checkout_until", checkout_until);
		}
		
		jsonString = HttpsUtil.requestGet(url,map);//返回值
		
		
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));
		
		bookingOrderList = JsonUtil.getList4Json(jsonString, BookingOrder.class, null);
		
		StringBuilder hotel_ids = new StringBuilder();

		if(bookingOrderList.size()>0){
			boolean isFirst = true;
			for(BookingOrder bo:bookingOrderList){
				Integer hotel_id = bo.getHotel_id();
				if(hotel_id!=null){
					if(isFirst){
						hotel_ids.append(hotel_id);
						isFirst = false;
					}else{
						hotel_ids.append(","+ hotel_id);
					}
					
				}
			}
		
			List<BookingHotel> bookingHotelList = this.getBookingHotels(hotel_ids.toString());
			
			if(bookingOrderList.size()>0 && bookingHotelList.size()>0){
				for(BookingOrder bo:bookingOrderList){
					for(BookingHotel bh:bookingHotelList){
						if(bh.getHotel_id().equals(bo.getHotel_id())){
							bo.setHotel_name(bh.getName());
							bo.setHotel_countrycode(bh.getCountrycode());
							bo.setHotel_city(bh.getCity());
						}
					}
				}
			}
		
	    }
		
		return "bookingOrderList";
	}
	
	private List<BookingHotel> getBookingHotels(String hotel_ids){
		Map<String,Object> map = new HashMap<String,Object>();
		String bookingUser = Constant.getInstance().getProperty("booking.user");
		String url = "https://" + bookingUser + "@distribution-xml.booking.com/json/bookings.getHotels";//远程url
		
		map.put("hotel_ids", hotel_ids);
		
		String jsonHotelString = HttpsUtil.requestGet(url,map);//返回值
		
		if(jsonHotelString!=null){
			List<BookingHotel> bookingHotelList = JsonUtil.getList4Json(jsonHotelString, BookingHotel.class, null);
			return bookingHotelList;
		}else{
			return null;
		}
		
	}
	
	/**
	 * 导出订单列表
	 * @return
	 */
	@Action("exportBookingOrderList")
	public void exportBookingOrderList(){
		Map<String,Object> map = new HashMap<String,Object>();
		//String url = "https://lvmamasynw:7660@distribution-xml.booking.com/json/bookings.getBookingDetails";//远程url
		this.rows = 300;
		
		map.put("rows", rows+"");
		
		if(!StringUtil.isEmptyString(this.id)){
			map.put("id", id);
		}
		
		if(!StringUtil.isEmptyString(this.begin_date)){
			map.put("begin_date", begin_date);
		}
		
		if(!StringUtil.isEmptyString(this.end_date)){
			map.put("end_date", end_date);
		}
		
		if(!StringUtil.isEmptyString(this.checkout_from)){
			map.put("checkout_from", checkout_from);
		}
		
		if(!StringUtil.isEmptyString(this.checkout_until)){
			map.put("checkout_until", checkout_until);
		}
		
		bookingOrderList = this.getExportBookingOrderList(map);
		
		StringBuilder hotel_ids = new StringBuilder();

		if(bookingOrderList.size()>0){
			boolean isFirst = true;
			for(BookingOrder bo:bookingOrderList){
				Integer hotel_id = bo.getHotel_id();
				if(hotel_id!=null){
					if(isFirst){
						hotel_ids.append(hotel_id);
						isFirst = false;
					}else{
						hotel_ids.append(","+ hotel_id);
					}
					
				}
			}
		
			List<BookingHotel> bookingHotelList = this.getBookingHotels(hotel_ids.toString());
			
			if(bookingOrderList.size()>0 && bookingHotelList!=null && bookingHotelList.size()>0){
				for(BookingOrder bo:bookingOrderList){
					for(BookingHotel bh:bookingHotelList){
						if(bh.getHotel_id().equals(bo.getHotel_id())){
							bo.setHotel_name(bh.getName());
							bo.setHotel_countrycode(bh.getCountrycode());
							bo.setHotel_city(bh.getCity());
						}
					}
				}
			}
	    }
		
		this.output(bookingOrderList, "/WEB-INF/resources/template/bookingOrderTemplate.xls");
	}
	
	/**
	 * 获取导出订单集合
	 * @param map
	 * @return
	 */
	private List<BookingOrder> getExportBookingOrderList(Map<String,Object> map){
		String bookingUser = Constant.getInstance().getProperty("booking.user");
		String url = "https://" + bookingUser + "@distribution-xml.booking.com/json/bookings.getBookingDetails";//远程url
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd"}));
		
		List<BookingOrder> orderList = new ArrayList<BookingOrder>();

		int offset = 0;
		
		while(true){
			map.put("offset", offset+"");
			String exportJsonString = HttpsUtil.requestGet(url,map);//返回值
			List<BookingOrder> list = JsonUtil.getList4Json(exportJsonString, BookingOrder.class, null);
			
			if(list.size()<1){
				break;
			}
			
			orderList.addAll(list);
			offset += 300;
		}
		
		return orderList;
	}
	
	/**
	 * 导出
	 * @param list
	 * @param template
	 */
	private void output(List list,String template){
		FileInputStream fin=null;
		OutputStream os=null;
		try
		{
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put("list", list);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel"+new Date().getTime()+".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
					
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os=getResponse().getOutputStream();
			fin=new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			
			os.flush();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}

	/**private method*******************************************************/

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getCheckout_from() {
		return checkout_from;
	}

	public void setCheckout_from(String checkout_from) {
		this.checkout_from = checkout_from;
	}

	public String getCheckout_until() {
		return checkout_until;
	}

	public void setCheckout_until(String checkout_until) {
		this.checkout_until = checkout_until;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public List<BookingOrder> getBookingOrderList() {
		return bookingOrderList;
	}

	public void setBookingOrderList(List<BookingOrder> bookingOrderList) {
		this.bookingOrderList = bookingOrderList;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
