package com.lvmama.hotel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.lvmama.comm.utils.AESUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.hotel.client.longtengjielv.impl.LongtengjielvClientImpl;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderRequest;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.longtengjielv.Authorization;
import com.lvmama.hotel.support.HotelOrderServiceSupport;
import com.lvmama.hotel.support.longtengjielv.impl.LongtengjielvHotelOrderServiceSupport;

public class LongtengjielvClientTest {
    private List<RoomType> roomTypeList = null;
    private List<Append> appendList = null;
    private LongtengjielvClientImpl client = null;
    private Date startDate = null;
    private Date endDate = null;

    @Before
    public void setUp() throws Exception {
        client = new LongtengjielvClientImpl();
        startDate = DateUtils.parseDate("2013-04-02", new String[] { "yyyy-MM-dd" });
        endDate = DateUtils.addDays(startDate, 3);
    }

    /**
     * 查询指定酒店所有上线房型
     *
     * @throws Exception
     */
    @Test
    public void simplifyRoomTypeInfo() throws Exception {
        String hotelCode = "22733";
        try {
            List<String> list = client.simplifyRoomTypeInfo(hotelCode);
            if (list != null) {
                for (String str : list) {
                    System.out.println(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询指定房型每一天的售价
     *
     * @throws Exception
     */
    @Test
    public void simplifyRoomTypePriceInfo() throws Exception {
        roomTypeList = client.simplifyRoomTypePriceInfo("22733", "127010", startDate, endDate, "CNY");
        System.out.println(roomTypeList);
    }

    /**
     * 查询指定房型每一天的房态
     *
     * @throws Exception
     */
    @Test
    public void simplifyRoomStatusInfo() throws Exception {
        roomTypeList = client.simplifyRoomStatusInfo("22733", "127010", startDate, endDate);
        System.out.println(roomTypeList);
    }

    /**
     * 查询指定房型每一天的附加费用
     *
     * @throws Exception
     */
    @Test
    public void simplifyRoomPriceAppendInfo() throws Exception {
        appendList = client.simplifyRoomPriceAppendInfo("22733", "127010", startDate, endDate, "CNY");
        System.out.println(appendList);
    }

    @Test
    public void submitOrder() throws Exception {
        int dayCount = 3;
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setAppendList(appendList);
        orderRequest.setRoomTypeID("127010");
        orderRequest.setHotelID("22733");
        orderRequest.setCheckInDate(new Date());
        orderRequest.setCheckOutDate(DateUtils.addDays(new Date(), dayCount));
        orderRequest.setQuantity(2L);
        orderRequest.setCurrency("CNY");
        orderRequest.setContactName("guobin");

        appendList = new ArrayList<Append>();
        for (int i = 0; i < dayCount; i++) {
            Append append = new Append();
            append.addToken("28001");
            append.addToken("26005");
            append.setTimePriceDate(new Date());
            append.setQuantity(1L);
            appendList.add(append);
        }
        HotelOrderServiceSupport hotelOrderServiceSupport = new LongtengjielvHotelOrderServiceSupport();
        System.out.println(hotelOrderServiceSupport.submitOrder(orderRequest));
    }

    @Test
    public void cancelOrder() throws Exception {
        client.newOnlineCancel_Orders("CN1303140003B9");
    }

    /**
     * 查询订单
     *
     * @throws Exception
     */
    @Test
    public void queryOrder() throws Exception {
        System.out.println(client.newOrderSearch("CN1303210004B9"));
    }

    /**
     * 查询指定酒店是否上线
     *
     * @throws Exception
     */
    @Test
    public void isHotelOnline() throws Exception {
        System.out.println(client.isHotelOnline("22733"));
    }

    /**
     * 查询指定房型是否上线
     *
     * @throws Exception
     */
    @Test
    public void isRoomTypeOnline() throws Exception {
        System.out.println(client.isRoomTypeOnline("22733", "127010"));
    }

    @Test
    public void push() {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><CNResponse><HotelID>22733</HotelID><RoomID>127010</RoomID><Type>1</Type><DatePhase><D><B></B><E></E></D></DatePhase></CNResponse>";
        AESUtil aesUtil = new AESUtil(Authorization.getInstance().getKey(), Authorization.getInstance().getIv());
        HttpsUtil.requestPostXml("http://localhost:8080/passport/longtengjielv/sync.do", aesUtil.encryptAES(xml));
    }
}
