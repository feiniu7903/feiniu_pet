package com.lvmama.jinjiang;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;

import com.lvmama.jinjiang.comm.TimestampToDateMorpher;
import com.lvmama.jinjiang.model.Contact;
import com.lvmama.jinjiang.model.Group;
import com.lvmama.jinjiang.model.GroupPrice;
import com.lvmama.jinjiang.model.Guest;
import com.lvmama.jinjiang.model.Line;
import com.lvmama.jinjiang.model.Order;
import com.lvmama.jinjiang.model.Receivable;
import com.lvmama.jinjiang.model.response.GetLineResposne;

public class OrderTestHelp {
	private String linePath = "E:\\work\\项目\\锦江之星\\测试数据\\线路.txt";
	private String contactPath = "E:\\work\\项目\\锦江之星\\测试数据\\联系人.txt";
	private String guestPath = "E:\\work\\项目\\锦江之星\\测试数据\\游客.txt";
	
	private Map<String, Line> lineMap;
	
	private Map<String, Group> groupMap;
	private Map<String, Contact> contactMap;
	private Map<String, Guest> guestMap;
	
	private File2LineHelp fileHelp;
	
	private Order order;
	
	public OrderTestHelp() {
//		init();
	}
	
	public void init() throws IOException {
		this.fileHelp = new File2LineHelp();
		this.lineMap = new HashMap<String, Line>();
		this.groupMap = new HashMap<String, Group>();
		this.contactMap = new HashMap<String, Contact>();
		this.guestMap = new HashMap<String, Guest>();
		
		initLine();
		initGroup();
		initContact();
		initGuest();
	}
	
	private void initLine() throws IOException {
		String str = null;
		fileHelp.setFile(linePath);
		// 获取线路
		GetLineResposne resposne = new GetLineResposne();
		try {
			while (fileHelp.nextLine()) {
				str = fileHelp.getLine();
				if (StringUtils.isNotBlank(str)) {
					GetLineResposne obj = resposne.parse(str);
					Line line = obj.getLine();
					if (line != null) {
						lineMap.put(line.getLineCode(), line);
					}
				}
			}
		} finally {
			fileHelp.close();
		}
	}
	
	private void initGroup() throws IOException {
		Set<String> set = lineMap.keySet();
		for (String key : set) {
			if (lineMap.get(key) != null) {
				Line line = lineMap.get(key);
				List<Group> groups = line.getGroups();
				if (groups != null) {
					for (Group group : groups) {
						if (group != null) {
							groupMap.put(group.getGroupCode(), group);
						}
					}
				}
			}
		}
	}
	
	private void initContact() throws IOException {
		String str = null;
		fileHelp.setFile(contactPath);
		try {
			while (fileHelp.nextLine()) {
				str = fileHelp.getLine();
				if (StringUtils.isNotBlank(str)) {
					JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
					JSONObject jsonObject = JSONObject.fromObject(str);
					Contact contact = (Contact) JSONObject.toBean(jsonObject, Contact.class);
					contactMap.put(contact.getName(), contact);
				}
			}
		} finally {
			fileHelp.close();
		}
	}
	
	private void initGuest() throws IOException {
		String str = null;
		fileHelp.setFile(guestPath);
		try {
			while (fileHelp.nextLine()) {
				str = fileHelp.getLine();
				if (StringUtils.isNotBlank(str)) {
					JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
					JSONObject jsonObject = JSONObject.fromObject(str);
					Guest guest = (Guest) JSONObject.toBean(jsonObject, Guest.class);
					guestMap.put(guest.getName(), guest);
				}
			}
		} finally {
			fileHelp.close();
		}
	}

	public static Order json2Order(String json) {
//		String json = "{\"thirdOrderNo\": \"LVMAMA_TEST_001\",\"groupCode\": \"SJTFTA141115-001S\",\"contact\": {\"telephone\": \"66666666\",\"mobile\": \"13517713196\",\"fax\": \"123456789012\",\"postCode\": \"666666\",\"email\": \"lm_test@qq.com\",\"address\": \"lm_testAddress\",\"name\": \"lm_testName\"},\"totalPersonsNum\": 2,\"guests\": [{\"name\": \"lv_张三\",\"mobile\": \"13517713197\",\"sex\": \"MALE\",\"category\": \"ADULT\",\"certificationType\": \"NATIONALID\",\"certificationNumber\": \"110105200901010687\",\"birthday\": 915120000000,\"otherContactInfo\": \"other\",\"priceCategory\": \"ADULT\"},{\"name\": \"lv_李四\",\"mobile\": \"13517713196\",\"sex\": \"FEMALE\",\"category\": \"ADULT\",\"certificationType\": \"NATIONALID\",\"certificationNumber\": \"110105200901010687\",\"birthday\": 946656000000,\"otherContactInfo\": \"other\",\"priceCategory\": \"ADULT\"}],\"adultNum\": 2,\"childNum\": 0,\"receivables\": [{\"priceCode\": \"2660210\",\"reservationPrice\": 1680,\"copies\": 2}],\"totalAmount\": 3360,\"remark\": \"驴妈妈测试订单！\",\"extension\": null}";
		JSONUtils.getMorpherRegistry().registerMorpher(new TimestampToDateMorpher());
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		classMap.put("contact", Contact.class);
		classMap.put("guests", Guest.class);
		classMap.put("receivables", Receivable.class);
		return (Order)JSONObject.toBean(jsonObject, Order.class, classMap);
	}
	
	public void createOrder(String OrderNo, String groupCode) {
		order = new Order();
		order.setThirdOrderNo(OrderNo);	// 分销商订单号
		order.setGroupCode(groupCode);	// 团代码
//		order.setContact(getContact());	// 联系人
		order.setTotalPersonsNum(0);	// 订单人数
//		order.setGuests(getGuests());	// 游客列表
		order.setAdultNum(0);	// 成人数
		order.setChildNum(0);	// 儿童数
//		order.setReceivables(getReceivables());	// 订单应收
		order.setTotalAmount(new BigDecimal(0));	// 订单总金额
		order.setRemark("test lkjf !!");		// 订单备注
		order.setExtension(null);	// 备用字段
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setContact(String name) {
		if (contactMap.get(name) != null) {
			order.setContact(contactMap.get(name));
		}
	}
	
	public void addGuest(String name) {
		if (guestMap.get(name) != null) {
			Guest guest = guestMap.get(name);
			List<Guest> guests = order.getGuests();
			if (guests == null) {
				guests = new ArrayList<Guest>();
				order.setGuests(guests);
			}
			boolean b = true;
			for (Guest g : guests) {
				if (name.equals(g.getName())) {
					b = false;
					break;
				}
			}
			if (b) {
				guests.add(guest);
				
				// 重算价格
				int num;
				String category = guest.getCategory();
				
				List<Receivable> receivables = order.getReceivables();
				if (receivables == null) {
					receivables = new ArrayList<Receivable>();
					order.setReceivables(receivables);
				}
				
				Receivable receivable = getReceivable(category);
				// 添加人数
				// 儿童
				if (StringUtils.equals(category, "CHILD")) {
					num = order.getChildNum();
					order.setChildNum(++num);
				} else {
					num = order.getAdultNum();
					order.setAdultNum(++num);
				}
				// 设置列表
				b = true;
				for (Receivable r : receivables) {
					if (r.getPriceCode().equals(receivable.getPriceCode())) {
						num = r.getCopies();
						r.setCopies(++num);
						b = false;
					}
				}
				if (b) {
					receivables.add(receivable);
				}
				// 设置总人数
				num = order.getTotalPersonsNum();
				order.setTotalPersonsNum(++num);
				// 设置总金额
				order.setTotalAmount(order.getTotalAmount().add(receivable.getReservationPrice()));
			}
		}
	}
	
	private Receivable getReceivable(String category) {
		Receivable receivable = new Receivable();
		String groupCode = order.getGroupCode();
		if (groupMap.get(groupCode) == null) {
			throw new RuntimeException("find group info failed! groupCode" + groupCode);
		} else {
			Group group = groupMap.get(groupCode);
			List<GroupPrice> prices = group.getPrices();
			if (category.equals("CHILD")) {
				for (GroupPrice groupPrice : prices) {
					String c = groupPrice.getCategory();
					if (c.equals("基本价") || c.equals("儿童价")) {
						receivable.setCopies(1);
						receivable.setPriceCode(groupPrice.getCode());
						// 销售价
						receivable.setReservationPrice(groupPrice.getSalePrice());
						break;
					}
				}
			} else if (category.equals("ADULT")) {
				for (GroupPrice groupPrice : prices) {
					String c = groupPrice.getCategory();
					if (c.equals("基本价") || c.equals("成人价")) {
						receivable.setCopies(1);
						receivable.setPriceCode(groupPrice.getCode());
						// 销售价
						receivable.setReservationPrice(groupPrice.getSalePrice());
						break;
					}
				}
			}
		}
		return receivable;
	}
	
	
	/*public static Order getOrder() {
		Order order = new Order();
		order.setThirdOrderNo("T0001");	// 分销商订单号
		order.setGroupCode("");	// 团代码
		order.setContact(getContact());	// 联系人
		order.setTotalPersonsNum(1);	// 订单人数
		order.setGuests(getGuests());	// 游客列表
		order.setAdultNum(2);	// 成人数
		order.setChildNum(2);	// 儿童数
		order.setReceivables(getReceivables());	// 订单应收
		order.setTotalAmount(new BigDecimal(123));	// 订单总金额
		order.setRemark("");		// 订单备注
		order.setExtension(null);	// 备用字段
		
		return order;
	}
	private static Contact getContact() {
		Contact contact = new Contact();
		contact.setName("");	// 姓名
		contact.setTelephone("124345");	// 电话
		contact.setMobile("");	// 手机
		contact.setFax("");		// 传真
		contact.setAddress("");	// 地址
		contact.setPostCode("");	// 邮编
		contact.setEmail("");	//邮箱
		return contact;
	}
	
	private static List<Guest> getGuests() {
		List<Guest> guests = new ArrayList<Guest>();
		
		return guests;
	}
	
	private static List<Receivable> getReceivables() {
		List<Receivable> receivables = new ArrayList<Receivable>();
//		receivables.add(getReceivable(priceCode, reservationPrice, copies));
		return receivables;
	}
	
	private static Receivable getReceivable(String priceCode, BigDecimal reservationPrice, Integer copies) {
		Receivable receivable = new Receivable();
		receivable.setPriceCode(priceCode);
		receivable.setReservationPrice(reservationPrice);
		receivable.setCopies(copies);
		return receivable;
	}*/
}
