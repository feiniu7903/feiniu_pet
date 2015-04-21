package com.lvmama.comm.vo.enums;

public enum SeoIndexPageCodeEnum {
	CH_TICKET("CH_TICKET", "门票"), CH_HOTEL("CH_HOTEL", "酒店"), CH_FREETOUR("CH_FREETOUR", "自由行"),CH_AROUND("CH_AROUND","周边游")
	,CH_DESTROUTE("CH_DESTROUTE","国内游"),CH_ABROAD("CH_ABROAD","出境游"),CH_TUANGOU("CH_TUANGOU","团购"),CH_GLOBALHOTEL("CH_GLOBALHOTEL","境外酒店"),CH_CUSTOMED("CH_CUSTOMED","企业专区")
	,CH_DEST_PLACE("CH_DEST_PLACE","目的地首页")
	,CH_DEST_PLACE_MAPS("CH_DEST_PLACE_MAPS","目的地地图")
	,CH_DEST_PLACE_MAPS_ABROAD("CH_DEST_PLACE_MAPS_ABROAD","目的地地图-出境")
	,CH_DEST_PLACE_WEATHER("CH_DEST_PLACE_WEATHER","目的地天气")
	,CH_DEST_PLACE_WEATHER_ABROAD("CH_DEST_PLACE_WEATHER_ABROAD","目的地天气-出境")
	,CH_DEST_PLACE_TICKET("CH_DEST_PLACE_TICKET","目的地门票")
	,CH_DEST_PLACE_TICKET_ABROAD("CH_DEST_PLACE_TICKET_ABROAD","目的地门票-出境")
	,CH_DEST_PLACE_HOTEL("CH_DEST_PLACE_HOTEL","目的地酒店")
	,CH_DEST_PLACE_HOTEL_ABROAD("CH_DEST_PLACE_HOTEL_ABROAD","目的地酒店-出境")
	,CH_DEST_PLACE_SURROUNDING("CH_DEST_PLACE_SURROUNDING","目的地跟团游")
	,CH_DEST_PLACE_SURROUNDING_NFD("PLACE_SURROUNDING_NFD","目的地跟团游-不包含出发地")
	,CH_DEST_PLACE_FREENESS("CH_DEST_PLACE_FREENESS","目的地自由行")
	,CH_DEST_PLACE_DESTTODEST("CH_DEST_PLACE_DESTTODEST","目的地自由行含交通-国内")
	,CH_DEST_PLACE_DESTTODEST_NFD("PLACE_DESTTODEST_NFD","目的地自由行含交通-国内-不包含出发地")
	,CH_DEST_PLACE_DESTTODESTABROAD("CH_DEST_PLACE_DESTTODESTABROAD","目的地自由行含交通-出境")
	,CH_DEST_PLACE_DESTTODESTABROAD_NFD("PLACE_DESTTODESTABROAD_NFD","目的地自由行含交通-出境-不包含出发地")
	,CH_DEST_PLACE_DESTTODESTGROUP("CH_DEST_PLACE_DESTTODESTGROUP","目的地跟团-出境")
	,CH_DEST_PLACE_DESTTODESTGROUP_NFD("PLACE_DESTTODESTGROUP_NFD","目的地跟团-出境-不包含出发地")
	,CH_DEST_PLACE_DESTTODESTFREE("CH_DEST_PLACE_DESTTODESTFREE","目的地自由行-出境")
	,CH_DEST_PLACE_DESTTODESTFREE_NFD("PLACE_DESTTODESTFREE_NFD","目的地自由行-出境-不包含出发地")
	,CH_DEST_HOTEL("CH_DEST_HOTEL","酒店首页")
	,CH_DEST_HOTEL_HOLIDAY_DETAILS("CH_DEST_HOTEL_HOLIDAY_DETAILS","度假酒店详情页")
	,CH_DEST_SCENIC("CH_DEST_SCENIC","景区首页")
	,CH_DEST_NEWSCENIC("CH_DEST_NEWSCENIC","新景区首页")
	,CH_DEST_SCENIC_MAPS("CH_DEST_SCENIC_MAPS","景区地图")
	,CH_DEST_SCENIC_PACKAGE("CH_DEST_SCENIC_PACKAGE","景区自由行")
	,CH_DEST_SCENIC_LINE("CH_DEST_SCENIC_LINE","景区跟团游")
	,CH_DEST_SCENIC_LINE_AFD("CH_DEST_SCENIC_LINE_AFD","景区跟团游-包含出发地")
	,CH_DEST_SCENIC_HOTELS("CH_DEST_SCENIC_HOTELS","景区酒店")
	,CH_GUIDE("CH_GUIDE","攻略"),CH_COMMENT("CH_COMMENT","点评");
	private String code;
	private String name;

	SeoIndexPageCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public static String getName(String code) {
		for (SeoIndexPageCodeEnum item : SeoIndexPageCodeEnum.values()) {
			if (item.getCode().equals(code)) {
				return item.getName();
			}
		}
		return code;
	}
}
