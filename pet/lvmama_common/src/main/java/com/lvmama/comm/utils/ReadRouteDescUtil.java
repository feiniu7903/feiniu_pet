package com.lvmama.comm.utils;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.vo.Constant;

public class ReadRouteDescUtil {
	private static final Log logger=LogFactory.getLog(ReadRouteDescUtil.class);
	public static final String ROUTE_TYPE_LONG="特别说明（以下内容将签入合同）：\r\n1.当发生不可抗力或危及游客人身，财产安全的情形时，本社可以调整或者变更行程安排，如有超出费用（如住、食及交通费、国家航空运价调整等）我社有权追加收取。\r\n2.住宿只含每人每天一床位。若出现单男单女，客人可以选择补房差、拼房或加床。如以上2种方式无法安排，需要支付单房差。若一大带一小报名，必须补房差，使用一间房。\r\n3.机票一经开出不得签转、更改、退票，报名时请提供客人的准确姓名及身份证号码（十二岁以下儿童还需提供出生年月）。\r\n4.本销售价格为综合旅游报价，持有老年证、军官证、导游证等证件均不退还门票。如游客在行程进行中，因其自身原因发生减少综合旅游报价项目的视为自动放弃，费用均无法退还；临时提出增加或变更综合旅游报价项目，必须获得全团客人的签字认可，并将可能被要求支付团散差价。\r\n5.如游客在行程进行中，在未同旅行社协商一致的情况下，擅自离团，将视为本旅游合同（包括所有综合旅游服务项目及保险责任）的自动终止，离团后的各项费用均无法退还。\r\n6.因不可抗拒的客观原因（如天灾、战争、罢工、天气、漓江水位落差等因素）和非我社原因（县级以上旅游主管单位做出该地区不适合旅游的决定，航空公司航班延误或取消、报名人数不足十人无法成团等特殊情况），我社会在一定时限内（具体取消通知时限以产品页面描述为准）有权取消发团，并退还所付金额，合同终止。若需要改期或拼团，则另行安排。\r\n7.在旅游过程中、游客所参加的旅游活动应选择自己能够控制风险的旅游项目，并对自己安全负责。\r\n8.游客因自身疾病和个人原因导致人身意外或财产损失的，旅行社不承担相应责任。\r\n9.游客同意上海驴妈妈国际旅行社有限公司将当地旅游接待服务委托给当地具有相应资质的旅行社。\r\n10.若本社及合作伙伴有任何形式的优惠活动，不得重复享受优惠。";
	public static final String ROUTE_TYPE_SHORT="特别说明（以下内容将签入合同）：\r\n1.当发生不可抗力或危及游客人身，财产安全的情形时，本社可以调整或者变更行程安排，费用按照多退少补的原则双方协商承担（如住、食及交通费、国家航空运价调整等）。 \r\n2.儿童价格请参照产品页面的标注，敬请了解儿童价格所包含的服务内容。 \r\n3.本销售价格为综合旅游报价，持有老年证、军官证、导游证等证件均不退还门票。如游客在行程进行中，发生减少综合旅游报价项目情况时，除景点协议门票价外，其余费用均无法退还；临时提出增加或变更综合旅游报价项目，必须获得全团客人的签字认可，并将可能被要求支付团散差价。 如果行程中的导游推荐自费项目中，客人自行先行购买了门票， 本司不负责提供该自费项目中的往返交通及讲解服务。\r\n4.如游客在行程进行中，在未同旅行社协商一致的情况下，擅自离团，将视为本旅游合同（包括所有综合旅游服务项目及保险责任）的自动终止，未产生的旅游项目，除景点协议门票价外，其余费用均无法退还。 \r\n5.因不可抗拒的客观原因（如天灾、战争、罢工等）和非我社原因（县级以上旅游主管单位做出该地区不适合旅游的决定，报名人数不足十人无法成团等特殊情况）造成团队无法出行，我社退还旅游者所付金额，合同终止。若旅游者需要改期或拼团，则双方协商另行安排。 \r\n6.游客同意上海驴妈妈国际旅行社有限公司将当地旅游接待服务委托给以下具有相应资质的旅行社提供相关服务。\r\n7.行程内车程时间、景点游览、自由活动、时间以当天实际游览为准仅供参考，以当日实际所用时间为准。 ";
	public static String readRouteDesc(final String type,final String subType){
		String key=readSubType(type,subType);
		return key;
	}
	public static String readSubType(final String type,final String subType){
		if(Constant.PRODUCT_TYPE.ROUTE.name().equals(type)){
			if(Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(subType)||Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(subType)){
				return ROUTE_TYPE_LONG;
			}else if(Constant.SUB_PRODUCT_TYPE.SELFHELP_BUS.name().equals(subType)||Constant.SUB_PRODUCT_TYPE.GROUP.name().equals(subType)){
				return ROUTE_TYPE_SHORT;
			}
		}
		logger.warn("产品类型："+type+" 产品子类型:"+subType+" 不是线路产品中在线签约的子类型");
		return null;
	}
}
