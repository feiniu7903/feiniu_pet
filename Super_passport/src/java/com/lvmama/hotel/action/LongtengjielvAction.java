package com.lvmama.hotel.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.AESUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.hotel.model.longtengjielv.Authorization;
import com.lvmama.hotel.service.longtengjielv.LongtengjielvProductService;

public class LongtengjielvAction extends BaseAction {
	private static final long serialVersionUID = 5753930457177630853L;

	private static final Log log = LogFactory.getLog(LongtengjielvAction.class);

	private static final int TYPE_HOTEL = 1;// 酒店
	private static final int TYPE_ROOMTYPE = 2;// 房型
	private static final int TYPE_PRICE = 3;// 售价
	private static final int TYPE_ROOMSTATUS = 4;// 房态
	private static final int TYPE_APPEND = 5;// 附加费用

	private LongtengjielvProductService longtengjielvProductService;

	private String hotelID;
	private String roomID;
	private String type;
	private Date startDate;
	private Date endDate;

	@Action("/longtengjielv/update")
	public void update() throws Exception {
		startDate = DateUtil.getTodayYMDDate();
		endDate = DateUtils.addYears(startDate, 1);
		longtengjielvProductService.updateRoomTypesAndAdditionals(startDate, endDate);
		sendAjaxMsg("更新完成");
	}

	@Action("/longtengjielv/test")
	public void test() throws Exception {
		sendAjaxMsg("30000");
		Date startDate = DateUtil.getTodayYMDDate();
		Date endDate = DateUtils.addYears(startDate, 1);
		switch (Integer.parseInt(type)) {
		case TYPE_HOTEL:
			longtengjielvProductService.onOfflineHotel(hotelID);
			break;
		case TYPE_ROOMTYPE:
			longtengjielvProductService.onOfflineRoomType(hotelID, roomID);
			break;
		case TYPE_PRICE:
			longtengjielvProductService.updateRoomTypeTimePrice(hotelID, roomID, startDate, endDate);
			break;
		case TYPE_ROOMSTATUS:
			longtengjielvProductService.updateRoomTypeTimeStock(hotelID, roomID, startDate, endDate);
			break;
		case TYPE_APPEND:
			longtengjielvProductService.updateAdditionalTimePrice(hotelID, roomID, startDate, endDate);
			break;
		}
	}

	/**
	 * 同步对方推送的酒店数据
	 */
	@Action("/longtengjielv/sync")
	public void sync() {
		sendAjaxMsg("30000");
		String plaintext = parsePostRequest();
		if (plaintext != null) {
			try {
				Date startDate = DateUtil.getTodayYMDDate();
				Date endDate = DateUtils.addYears(startDate, 1);
				String hotelID = TemplateUtils.getElementValue(plaintext, "//CNResponse/HotelID");
				String roomID = TemplateUtils.getElementValue(plaintext, "//CNResponse/RoomID");
				String type = TemplateUtils.getElementValue(plaintext, "//CNResponse/Type");
				log.info("hotelID=" + hotelID + ", roomID=" + roomID + ", type=" + type);
				switch (Integer.parseInt(type)) {
				case TYPE_HOTEL:
					longtengjielvProductService.onOfflineHotel(hotelID);
					break;
				case TYPE_ROOMTYPE:
					longtengjielvProductService.onOfflineRoomType(hotelID, roomID);
					break;
				case TYPE_PRICE:
					longtengjielvProductService.updateRoomTypeTimePrice(hotelID, roomID, startDate, endDate);
					break;
				case TYPE_ROOMSTATUS:
					longtengjielvProductService.updateRoomTypeTimeStock(hotelID, roomID, startDate, endDate);
					break;
				case TYPE_APPEND:
					longtengjielvProductService.updateAdditionalTimePrice(hotelID, roomID, startDate, endDate);
					break;
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	private String parsePostRequest() {
		HttpServletRequest req = this.getRequest();
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			br = req.getReader();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}
		} catch (IOException e) {
			log.error(e);
			return null;
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		String ciphertext = sb.toString();
		log.info("ciphertext=" + ciphertext);
		AESUtil aesUtil = new AESUtil(Authorization.getInstance().getKey(), Authorization.getInstance().getIv());
		String plaintext = aesUtil.decryptAES(ciphertext).trim();
		log.info("plaintext=" + plaintext);
		return plaintext;
	}

	public void setLongtengjielvProductService(LongtengjielvProductService longtengjielvProductService) {
		this.longtengjielvProductService = longtengjielvProductService;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
