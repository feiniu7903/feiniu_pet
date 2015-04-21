/**
 * 
 */
package com.lvmama.comm.vo.cert.builder.content;

/**
 * @author yangbin
 *
 */
public abstract class EbkCertLabelUtil {

	enum TRAVEL{
		name("游客名称"),
		mobile("手机号码"),
		certNo("证件号"),
		zhCertType("证件类型");
		
		String zhName;

		TRAVEL(String arg0) {
			this.zhName = arg0;
		}

		public String getZhName() {
			return zhName;
		}
	}
	
	enum ITEM{
		visitTime("游玩日期"),
		metaProductName("产品名称");
		String zhName;
		
		ITEM(String arg0){
			this.zhName = arg0;
		}
	}
	
	enum HOTEL_ITEM{
		visitTime("入住日期"),
		metaProductName("产品名称");
		String zhName;
		
		HOTEL_ITEM(String arg0){
			this.zhName = arg0;
		}

		public String getZhName() {
			return zhName;
		}
	}
}
