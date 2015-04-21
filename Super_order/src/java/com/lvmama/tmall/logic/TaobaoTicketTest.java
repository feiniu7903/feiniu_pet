package com.lvmama.tmall.logic;

import java.util.ArrayList;
import java.util.List;

import com.taobao.api.response.TicketItemUpdateResponse;
import org.junit.Test;

import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.utils.DateUtil;
import com.taobao.api.domain.TicketItem;
import com.taobao.api.response.TicketItemsGetResponse;

public class TaobaoTicketTest {
	@Test
	public void testFindTicketInfo() {
		Long itemId = 38081712377L;
		try {
			TOPInterface.isDebug = true;
			String itemIds = itemId.toString();
			TicketItemsGetResponse response = TOPInterface.findTaobaoTicketItems(itemIds);
			System.out.println(response.getBody());
			TicketItem ticketItem = response.getTicketItems().get(0);
			System.out.println(ticketItem.getSkus());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTicketStatusTest() {
		/* 测试上下架  */
		
		Long itemId = 36732396832L;
		Long prodBranchId = 291761L;
		
		String pid_vid = "10682468-60484007_6180395-62353507";
		String outerId = "69697,291761";
				
		TaobaoProductSync taobaoProductSync = new TaobaoProductSync();
		taobaoProductSync.setTbItemId(itemId);
		// （淘宝票类型，1：实体票；2：电子票；）
		taobaoProductSync.setTbTicketType("1");
		
		TaobaoTicketSku taobaoTicketSku = new TaobaoTicketSku();
		taobaoTicketSku.setProdBranchId(prodBranchId);
		taobaoTicketSku.setTbOuterId(outerId);
		taobaoTicketSku.setTbPidVid(pid_vid);
		
		try {
			TicketItemHelp help = new TicketItemHelp(taobaoProductSync, taobaoTicketSku);
//			help.updateAuctionStatus("onsale");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTicketSkuTest() {
		Long itemId = 38081712377L;
		Long prodBranchId = 291690L;
		
		String pid_vid = "10682468-60484007_6180395-62353507";
		String outerId = "69697,291690";
				
		TaobaoProductSync taobaoProductSync = new TaobaoProductSync();
		taobaoProductSync.setTbItemId(itemId);
		// （淘宝票类型，1：实体票；2：电子票；）
		taobaoProductSync.setTbTicketType("1");
		
		TaobaoTicketSku taobaoTicketSku = new TaobaoTicketSku();
		taobaoTicketSku.setProdBranchId(prodBranchId);
		taobaoTicketSku.setTbOuterId(outerId);
		taobaoTicketSku.setTbPidVid(pid_vid);
		
		List<ProdTimePrice> prodTimePrices = new ArrayList<ProdTimePrice>();
		addProdTimePrice(prodTimePrices, "2014-04-03", -1L, 8500L, -16L);
		addProdTimePrice(prodTimePrices, "2014-04-04", -1L, 8500L, -16L);
		addProdTimePrice(prodTimePrices, "2014-04-05", -1L, 8530L, -16L);
		addProdTimePrice(prodTimePrices, "2014-04-06", -1L, 8500L, -16L);
		addProdTimePrice(prodTimePrices, "2014-04-07", -1L, 8400L, -16L);
		addProdTimePrice(prodTimePrices, "2014-04-08", -1L, 810L, -16L);
		addProdTimePrice(prodTimePrices, "2014-04-09", -1L, 8900L, -16L);
		
		try {
			TicketItemHelp help = new TicketItemHelp(taobaoProductSync, taobaoTicketSku);
			help.updateSkuEffDates(prodTimePrices);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


    @Test
    public void testUpdateTicketEffDates() {
        String oldSku = "{\"SKU\":{\"10682468-60484007_6180395-62353507\":{\"returnRule\":{\"type\":1,\"value\":\"\"},\"changeRule\":{\"type\":1,\"value\":\"\"},\"guide\":\"999\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-06-10\",\"endDate\":\"2014-07-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"0\",\"aheadDays\":null,\"aheadAtHour\":null,\"aheadAtMinute\":null,\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291761\",\"price\":999.0,\"inventory\":100,\"tag\":1},\"2\":{\"type\":\"0\",\"effDate\":{\"startDate\":\"2014-08-01\",\"endDate\":\"2014-08-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":null},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"23\",\"aheadAtMinute\":\"59\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69698,291691\",\"price\":666.0,\"inventory\":1000,\"tag\":2}},\"save\":true}}}";
        TOPInterface.isDebug = true;
        try {
            Long itemId = 38081712377L;

            String pid_vid = "10682468-60484007_6180395-62353507";
            String outerId = "69697,291761";

            TicketItemHelp help = new TicketItemHelp(null, null);
            String skus = help.getUpdateSku(oldSku, pid_vid, outerId, null);
            System.out.println("更新sku===> " + skus);
            TicketItemUpdateResponse response = TOPInterface.updateTaobaoTicketTtem(itemId, oldSku, null);
            System.out.println("body======>>" + response.getBody());
            if (response.isSuccess()) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
            TicketItemsGetResponse taobaoTicketItems = TOPInterface.findTaobaoTicketItems(itemId.toString());
            System.out.println("当前sku===> " + taobaoTicketItems.getTicketItems().get(0).getSkus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTicketEffDatesTest() {
        Long itemId = 38081712377L;
        Long prodBranchId = 291761L;

        String pid_vid = "10682468-60484007_6180395-62353507";
        String outerId = "69697,291761";

        TaobaoProductSync taobaoProductSync = new TaobaoProductSync();
        taobaoProductSync.setTbItemId(itemId);
        // （淘宝票类型，1：实体票；2：电子票；）
        taobaoProductSync.setTbTicketType("1");

        TaobaoTicketSku taobaoTicketSku = new TaobaoTicketSku();
        taobaoTicketSku.setProdBranchId(prodBranchId);
        taobaoTicketSku.setTbOuterId(outerId);
        taobaoTicketSku.setTbPidVid(pid_vid);
        taobaoTicketSku.setTbTicketType("1");

/*        List<ProdTimePrice> prodTimePrices = new ArrayList<ProdTimePrice>();
        addProdTimePrice(prodTimePrices, "2014-06-03", -1L, 8500L, -16L);
        addProdTimePrice(prodTimePrices, "2014-06-04", -1L, 8500L, -16L);
        addProdTimePrice(prodTimePrices, "2014-06-05", -1L, 8530L, -16L);
        addProdTimePrice(prodTimePrices, "2014-06-06", -1L, 8500L, -16L);
        addProdTimePrice(prodTimePrices, "2014-06-07", -1L, 8400L, -16L);
        addProdTimePrice(prodTimePrices, "2014-06-08", -1L, 810L, -16L);
        addProdTimePrice(prodTimePrices, "2014-06-09", -1L, 8900L, -16L);*/
        String oldSku = "{\"SKU\":{\"10682468-60484007_6180395-62353507\":{\"returnRule\":{\"type\":1,\"value\":\"\"},\"changeRule\":{\"type\":1,\"value\":\"\"},\"guide\":\"222\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-06-10\",\"endDate\":\"2014-06-30\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"10\",\"aheadAtMinute\":\"59\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291761\",\"price\":666.0,\"inventory\":100,\"tag\":1},\"2\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-07-01\",\"endDate\":\"2014-08-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"23\",\"aheadAtMinute\":\"59\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291765\",\"price\":999.0,\"inventory\":100,\"tag\":2}},\"save\":true},\"10682468-60484007_6180395-55550265\":{\"returnRule\":{\"type\":1,\"value\":\"\"},\"changeRule\":{\"type\":1,\"value\":\"\"},\"guide\":\"666\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-06-10\",\"endDate\":\"2014-06-30\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"20\",\"aheadAtMinute\":\"59\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291761\",\"price\":100.0,\"inventory\":100,\"tag\":1}},\"save\":true}}}";
        try {
            TicketItemHelp help = new TicketItemHelp(taobaoProductSync, taobaoTicketSku);
            String str = help.getUpdateSku(oldSku, pid_vid, outerId, null);

            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	private void addProdTimePrice(List<ProdTimePrice> prodTimePrices, String specDate, Long dayStock, Long price, Long aheadHour) {
		ProdTimePrice prodTimePrice = new ProdTimePrice();
		prodTimePrice.setSpecDate(DateUtil.toDate(specDate, "yyyy-MM-dd"));
		prodTimePrice.setDayStock(dayStock);
		prodTimePrice.setPrice(price);
		prodTimePrice.setAheadHour(aheadHour);
		prodTimePrices.add(prodTimePrice);
	}

    /**
     * 获取 淘宝门票Sku
     * @throws Exception
     */
    @Test
    public void testGetTaobaoTicketSkus() throws Exception {
        long itemId = 38081712377L;
        long cId = 50458021L;
        String skus = "{\"SKU\":{\"10682468-60484007_6180395-62353507\":{\"returnRule\":{\"type\":2,\"value\":\"本产品一经预定，不退不改。（如果遇紧急情况，我们将尽量为亲协调，但不能保证，退票或改期成功。）\"},\"changeRule\":{\"type\":2,\"value\":\"本产品一经预定，不退不改。（如果遇紧急情况，我们将尽量为亲协调，但不能保证，退票或改期成功。）\"},\"guide\":\"本产品一经预定，不退不改。（如果遇紧急情况，我们将尽量为亲协调，但不能保证，退票或改期成功。）\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-08-01\",\"endDate\":\"2014-09-01\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"18\",\"aheadAtMinute\":\"0\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291765\",\"price\":1002.0,\"inventory\":10000,\"tag\":1},\"2\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-06-04\",\"endDate\":\"2014-07-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"18\",\"aheadAtMinute\":\"0\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291765\",\"price\":1001.0,\"inventory\":10000,\"tag\":2}},\"save\":true},\"10682468-60484007_6180395-55550265\":{\"returnRule\":{\"type\":1,\"value\":\"\"},\"changeRule\":{\"type\":1,\"value\":\"\"},\"guide\":\"222222222222222222\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2014-06-04\",\"endDate\":\"2014-07-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"0\",\"aheadDays\":null,\"aheadAtHour\":null,\"aheadAtMinute\":null,\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"69697,291765\",\"price\":999.0,\"inventory\":100,\"tag\":1}},\"save\":true}}}";

        TaobaoSyncHelp help = new TaobaoSyncHelp(null);
        help.initCid();
        help.getTaobaoTicketSkus(cId, skus, itemId);

    }
}

