package com.lvmama.back.sweb.tmall;

import java.util.List;

import com.lvmama.comm.bee.po.tmall.TaobaoTicketSku;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.back.utils.JavaBeanUtil;
import com.lvmama.comm.bee.service.tmall.TOPInterface;
import com.taobao.api.domain.Brand;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.PropValue;
import com.taobao.api.domain.SellerAuthorize;
import com.taobao.api.domain.TicketItem;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.TravelItems;
import com.taobao.api.domain.TravelItemsCombo;
import com.taobao.api.domain.TravelItemsPriceCalendar;
import com.taobao.api.domain.TravelItemsPropValue;
import com.taobao.api.response.ItemcatsAuthorizeGetResponse;
import com.taobao.api.response.ItemcatsGetResponse;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.ItemsInventoryGetResponse;
import com.taobao.api.response.TicketItemsGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TravelItemsGetResponse;

public class TaoBaoApiTest {
	
	@Test
	public void testTaoBaoItemsOnsaleGetRequest() {
		// 获取 在售 商品信息
		try {
			TOPInterface.findTaoBaoItemsInventory(1, 50);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTaoBaoItemsInventoryGet() {
		// 获取 在库 商品信息
		try {
			ItemsInventoryGetResponse response = TOPInterface.findTaoBaoItemsInventory(1, 50);
			System.out.println(response.getTotalResults());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTaobaoTicketItemsGet() {
		// 获取门票属性消息
		try {
			String itemIds = "38081712377";
			TicketItemsGetResponse response = TOPInterface.findTaobaoTicketItems(itemIds);
			TicketItem ticketItem = response.getTicketItems().get(0);
			System.out.println(ticketItem.getSkus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testTaobaoTicketTtemUpdate() {
		// 更新门票信息

		// 目前还不能测试
	}
	
	@Test
	public void testTaobaoTravelItemsGet() {
		// 获取线路信息
		// 9625741958 17077493886 37608315483 36501670946
		long itemId = Long.valueOf("17077493886");
		try {
			TravelItemsGetResponse response = TOPInterface.findTaobaoTravelItems(itemId);
			if (response.isSuccess()) {
				TravelItems travelItems = response.getTravelItems();
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
	public void testTaobaoTravelItemsUpdate() {
		// 更新线路信息
		try {
			// 
			long itemId = Long.valueOf("37609670786");
			
//			TOPInterface.updateTaobaoTravelItems(itemId, comboPriceCalendar, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindItemcatsAuthorize() {
		/* 查询商家被授权品牌列表和类目列表 */
		try {
			// 50025707 线路，50454031 门票
			ItemcatsAuthorizeGetResponse response = TOPInterface.findItemcatsAuthorize();
			SellerAuthorize sellerAuthorize = response.getSellerAuthorize();
			List<Brand> brands = sellerAuthorize.getBrands();	// 品牌列表
			List<ItemCat> xItemCats = sellerAuthorize.getXinpinItemCats();	// 被授权的新品类目列表
			List<ItemCat> itemCats = sellerAuthorize.getItemCats();		// 类目列表
			for (ItemCat itemCat : itemCats) {
				JavaBeanUtil.printJavaBean(ItemCat.class, itemCat);
			}
			System.out.println("-------------------------");
			if (xItemCats != null) {
				for (ItemCat itemCat : xItemCats) {
					JavaBeanUtil.printJavaBean(ItemCat.class, itemCat);
				}
			}
			System.out.println("-------------------------");
			if (brands != null) {
				for (Brand brand : brands) {
					JavaBeanUtil.printJavaBean(Brand.class, brand);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindItemcats() {
		/* 获取后台供卖家发布商品的标准商品类目 */
		try {
			// 50025707 线路，50454031 门票
			Long parentCid = null;
			String cids = "50458021";
			ItemcatsGetResponse response = TOPInterface.findItemcats(parentCid, cids);
			List<ItemCat> itemCats = response.getItemCats();		// 类目列表
			for (ItemCat itemCat : itemCats) {
				JavaBeanUtil.printJavaBean(ItemCat.class, itemCat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindItemprops() {
		/* 获取标准商品类目属性 */
		try {
			// 
			Long cid = 50458021L;
			ItempropsGetResponse response = TOPInterface.findItemprops(cid);
			List<ItemProp> itemProps = response.getItemProps();
			for (ItemProp itemProp : itemProps) {
				JavaBeanUtil.printJavaBean(ItemProp.class, itemProp);
				List<PropValue> prop = itemProp.getPropValues();
				for (PropValue propValue : prop) {
					JavaBeanUtil.printJavaBean(PropValue.class, propValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testTaoBao() {
		try {
			// http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.GHYaym&path=cid:5-apiId:54
			
			String tid = "579576425441735";
			TradeFullinfoGetResponse response = TOPInterface.getFullIfo(tid);
			System.out.println(response.getBody());
			Trade trade = response.getTrade();
			List<Order> orders = trade.getOrders();
			for (Order order : orders) {
				JavaBeanUtil.printJavaBean(Order.class, order);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testJson() {
		try {
		String jsonStr = "{\"SKU\":{\"10682468-60484007_6180395-62353507\":{\"returnRule\":{\"type\":2,\"value\":\"当日预定当日的产品，人数一经预定成功后不做任何更改，如有人数变动，请在游玩前1天16点前联系旺旺在线客服或拨打客服热线021-51212001，一旦验证不退不换不改，请谅解。\"},\"changeRule\":{\"type\":2,\"value\":\"无法改期。如需改期，请退票重拍。请参考退票规则。\"},\"guide\":\"★开放时间：08:30-17:00；<br />★景区地址：海南省万宁市万城镇东山岭文化旅游区；<br />★取票地点：景区7个售票窗口，任何一个都可以；<br />★入园凭证：报名字、手机号码，凭身份证取票；<br />★特殊人群：<br />A、免费政策：<br />a.1.2米以下(不含1.2米）儿童可免费入园；<br />b.70周岁以上（含70周岁）老年人、现役军人凭有效证件（军官证、士兵证）、可享受免门票；<br />B、优惠政策：<br />a.1.2&le;身高&lt;1.4米儿童购优惠价25元/人，1.4米以上（含1.4米）的游客正常购票；<br />b.60周岁&le;年龄&le;69周岁老年人（凭老年优待证）、全日制大学本科及以下学历学生（凭学生证）、残疾人（凭国家残联颁发的残疾证）享受购优惠价25元/人；<br />c.海南省内居民凭本人有效证件享受海南居民优惠价35元/人；\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2013-11-22\",\"endDate\":\"2014-10-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"14\",\"aheadAtMinute\":\"0\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"94040,1211542\",\"price\":20.0,\"inventory\":9855,\"tag\":1}},\"save\":true},\"10682468-60484008_6180395-62353507\":{\"returnRule\":{\"type\":2,\"value\":\"当日预定当日的产品，人数一经预定成功后不做任何更改，如有人数变动，请在游玩前1天16点前联系旺旺在线客服或拨打客服热线021-51212001，一旦验证不退不换不改，请谅解。\"},\"changeRule\":{\"type\":2,\"value\":\"无法改期。如需改期，请退票重拍。请参考退票规则。\"},\"guide\":\"★开放时间：08:30-17:00；<br />★景区地址：海南省万宁市万城镇东山岭文化旅游区；<br />★取票地点：景区7个售票窗口，任何一个都可以；<br />★入园凭证：报名字、手机号码，凭身份证取票；<br />★特殊人群：<br />A、免费政策：<br />a.1.2米以下(不含1.2米）儿童可免费入园；<br />b.70周岁以上（含70周岁）老年人、现役军人凭有效证件（军官证、士兵证）、可享受免门票；<br />B、优惠政策：<br />a.1.2&le;身高&lt;1.4米儿童购优惠价25元/人，1.4米以上（含1.4米）的游客正常购票；<br />b.60周岁&le;年龄&le;69周岁老年人（凭老年优待证）、全日制大学本科及以下学历学生（凭学生证）、残疾人（凭国家残联颁发的残疾证）享受购优惠价25元/人；<br />c.海南省内居民凭本人有效证件享受海南居民优惠价35元/人；\",\"effDates\":{\"1\":{\"type\":\"1\",\"effDate\":{\"startDate\":\"2013-11-22\",\"endDate\":\"2014-10-31\",\"weeks\":null,\"startHour\":null,\"startMinute\":null,\"endHour\":null,\"endMinute\":null,\"effDays\":\"1\"},\"timeLimit\":{\"limit\":{\"type\":\"1\",\"aheadDays\":\"0\",\"aheadAtHour\":\"14\",\"aheadAtMinute\":\"0\",\"aheadHours\":null,\"aheadMinutes\":null},\"autoActivate\":{\"type\":null,\"time\":null}},\"outerId\":\"94040,1211561\",\"price\":15.0,\"inventory\":9994,\"tag\":1}},\"save\":true}}}";

//		JSONObject obj = JsonHelp.path2JsonObject(jsonStr, "SKU", "10682468-60484007_6180395-62353507");
//		
//		String str = obj.getString("guide");
//		System.out.println(str);
		
//		Map<String, String> map = new HashMap<String, String>();
//		JSONObject sku = JsonHelp.path2JsonObject(jsonStr, "SKU");
//		String[] keys = JsonHelp.getJsonKeys(sku);
//		for (String key : keys) {
//			JSONObject type = JsonHelp.path2JsonObject(sku, key, "effDates");
//			String typeKey = JsonHelp.getJsonKeys(type)[0];
//			String outerId = JsonHelp.path2String(type, typeKey, "outerId");
//			if (outerId != null) {
//				map.put(key, outerId.toString());
//			}
//		}
		
//		System.out.println(JsonHelpUtil.updateJson(jsonStr, "111111111111111111111", "SKU", "10682468-60484007_6180395-62353507", "returnRule", "value"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
