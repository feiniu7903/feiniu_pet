package com.lvmama.tmall.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.lvmama.comm.bee.po.tmall.ProdTimePrice;
import com.lvmama.comm.bee.po.tmall.TaobaoProductSync;
import com.lvmama.comm.bee.po.tmall.TaobaoTravelCombo;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.lvmama.comm.utils.DateUtil;
import com.taobao.api.domain.TravelItems;
import com.taobao.api.domain.TravelItemsCombo;
import com.taobao.api.domain.TravelItemsPriceCalendar;
import com.taobao.api.domain.TravelItemsPropValue;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.ItemsOnsaleGetResponse;
import com.taobao.api.response.TravelItemsGetResponse;

public class TaobaoTravelTest {
	
	@Test
	public void testFindTaoBaoItemsOnsale() {
		try {
			ItemsOnsaleGetResponse response = TOPInterface.findTaoBaoItemsOnsale(1, 50);
			System.out.println(response.getTotalResults());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindTaoBaoItemsInventory() {
		try {
			ItemsInventoryGetResponse response = TOPInterface.findTaoBaoItemsInventory(1, 50);
			System.out.println(response.getTotalResults());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindTraveInfo() {
		Long itemId = 38082660203L;
		try {
			TravelItemsGetResponse response = TOPInterface.findTaobaoTravelItems(itemId);
			if (response.isSuccess()) {
				TravelItems travelItems = response.getTravelItems();
				System.out.println(travelItems.getTitle());
				System.out.println(travelItems.getApproveStatus());
				System.out.println(travelItems.getDuration());
				if (travelItems != null && travelItems.getTravelItemsCombos() != null) {
					List<TravelItemsCombo> item = travelItems.getTravelItemsCombos();
					for (TravelItemsCombo travelItemsCombo : item) {
						System.out.println("--------------------------------------------------");
						TravelItemsPropValue combo = travelItemsCombo.getCombo();
						JavaBeanUtil.printJavaBean(TravelItemsPropValue.class, combo);
						List<TravelItemsPriceCalendar> comboPriceCalendars = travelItemsCombo.getComboPriceCalendars();
						for (TravelItemsPriceCalendar travelItemsPriceCalendar : comboPriceCalendars) {
							JavaBeanUtil.printJavaBean(TravelItemsPriceCalendar.class, travelItemsPriceCalendar);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTravelStatusTest() {
		Long itemId = 37742452984L;
		Long prodBranchId = 1210210L;
		
		Long pid = 5919063L;
		Long vid = 3266779L;
//		Long cid = 120832001L;
		String name = "厦门3天2晚双人自由行";
				
		TaobaoProductSync taobaoProductSync = new TaobaoProductSync();
		taobaoProductSync.setTbItemId(itemId);
		
		TaobaoTravelCombo taobaoTravelCombo = new TaobaoTravelCombo();
		taobaoTravelCombo.setTbComboName(name);
		taobaoTravelCombo.setTbPid(pid);
		taobaoTravelCombo.setTbVid(vid);
		try {
			TravelItemHelp help = new TravelItemHelp(taobaoProductSync, taobaoTravelCombo);
			// 可选值:onsale(出售中),instock(仓库中);默认值:onsale。
			help.updateTravelItemStatus("onsale");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTravelDurationTest() {
		Long itemId = 38082660203L;
		Long duration = 1L;
		
		TaobaoProductSync taobaoProductSync = new TaobaoProductSync();
		taobaoProductSync.setTbItemId(itemId);
		try {
			TravelItemHelp help = new TravelItemHelp(taobaoProductSync);
			// 最晚成团提前天数，最小0天，最大为300天。
			help.updateTravelItemDuration(duration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTravelTest() {
		Long itemId = 38082660203L;
		Long prodBranchId = 291694L;
		
		Long pid = 5919063L;
		Long vid = 3266781L;
//		Long cid = 120832001L;
		String name = "厦门3天2晚双人自由行";
				
		TaobaoProductSync taobaoProductSync = new TaobaoProductSync();
		taobaoProductSync.setTbItemId(itemId);
		
		TaobaoTravelCombo taobaoTravelCombo = new TaobaoTravelCombo();
		taobaoTravelCombo.setTbComboName(name);
		taobaoTravelCombo.setTbPid(pid);
		taobaoTravelCombo.setTbVid(vid);
		
		
		Map<String, List<ProdTimePrice>> ptpMap = new HashMap<String, List<ProdTimePrice>>();
		
		List<ProdTimePrice> prodTimePrices = new ArrayList<ProdTimePrice>();
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-02", "yyyy-MM-dd"), 10L, 118600L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-03", "yyyy-MM-dd"), 10L, 118600L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-04", "yyyy-MM-dd"), 10L, 118600L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-05", "yyyy-MM-dd"), 10L, 118600L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-06", "yyyy-MM-dd"), 10L, 118600L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-07", "yyyy-MM-dd"), 10L, 118600L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-08", "yyyy-MM-dd"), 10L, 118600L);
		ptpMap.put("1", prodTimePrices);
		
		prodTimePrices = new ArrayList<ProdTimePrice>();
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-02", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-03", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-04", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-05", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-06", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-07", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-08", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-09", "yyyy-MM-dd"), 10L, 22200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-10", "yyyy-MM-dd"), 10L, 22200L);
		ptpMap.put("2", prodTimePrices);
		
		prodTimePrices = new ArrayList<ProdTimePrice>();
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-07", "yyyy-MM-dd"), 10L, 32200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-08", "yyyy-MM-dd"), 10L, 32200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-09", "yyyy-MM-dd"), 10L, 32200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-11", "yyyy-MM-dd"), 10L, 32200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-12", "yyyy-MM-dd"), 10L, 32200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-13", "yyyy-MM-dd"), 10L, 32200L);
		addProdTimePrice(prodTimePrices, DateUtil.toDate("2014-04-14", "yyyy-MM-dd"), 10L, 32200L);
		ptpMap.put("3", prodTimePrices);
		
		TravelItemHelp help = new TravelItemHelp(taobaoProductSync, taobaoTravelCombo);
		help.updateTravelComboCalendar(ptpMap);
	}
	
	private void addProdTimePrice(List<ProdTimePrice> prodTimePrices, Date specDate, Long dayStock, Long price) {
		ProdTimePrice prodTimePrice = new ProdTimePrice();
		prodTimePrice.setSpecDate(specDate);
		prodTimePrice.setDayStock(dayStock);
		prodTimePrice.setPrice(price);
		prodTimePrices.add(prodTimePrice);
	}
	
	private void addFile2ProdTimePrice(String fileName, List<ProdTimePrice> prodTimePrices) {
		String manPrice = "manPrice";
		String manNum = "manNum";
		String date = "date";
		File file = new File("d:/22222.txt");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = null;
			while (true) {
				str = br.readLine();
				if (str == null) {
					break;
				}
				if (StringUtils.isNotBlank(str)) {
					String[] strs = str.split(",");
					if (strs.length > 3) {
						Date specDate = null;
						Long dayStock = null;
						Long price = null;
						for (String v : strs) {
							String[] vs = v.split("=");
							if (vs.length > 1) {
								String v1 = vs[0].trim();
								String v2 = vs[1].trim();
								if (v1.equals(manPrice)) {
									price = Long.valueOf(v2);
								} else if (v1.equals(manNum)) {
									dayStock = Long.valueOf(v2);
								} else if (v1.equals(date)) {
									specDate = DateUtil.toDate(v2, "yyyy-MM-dd");
								}
							}
						}
						addProdTimePrice(prodTimePrices, specDate, dayStock, price);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
