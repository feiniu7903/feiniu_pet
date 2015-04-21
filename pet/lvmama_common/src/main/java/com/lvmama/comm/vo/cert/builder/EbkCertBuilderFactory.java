/**
 * 
 */
package com.lvmama.comm.vo.cert.builder;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.cert.builder.content.EbkCertCompositeBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertFlightBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertHotelBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertHotelSuitBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertRouteBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertSingleRoomCompositeBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertTicketBuilder;
import com.lvmama.comm.vo.cert.builder.content.EbkCertTicketCompositeBuilder;
import com.lvmama.comm.vo.cert.builder.data.EbkCertDataFlightBuilder;
import com.lvmama.comm.vo.cert.builder.data.EbkCertDataHotelBuilder;
import com.lvmama.comm.vo.cert.builder.data.EbkCertDataHotelSuitBuilder;
import com.lvmama.comm.vo.cert.builder.data.EbkCertDataRouteBuilder;
import com.lvmama.comm.vo.cert.builder.data.EbkCertDataTicketBuilder;

/**
 * @author yangbin
 *
 */
public class EbkCertBuilderFactory {

	public static final EbkCertDataBuilder create(Map<String,Object> params){
		Assert.isTrue(params.containsKey(EbkCertDataBuilder.EBK_CERTIFICATE));
		Assert.isTrue(params.containsKey(EbkCertDataBuilder.ORD_ORDER));
		
		EbkCertificate ebkCertificate = (EbkCertificate)params.get(EbkCertDataBuilder.EBK_CERTIFICATE);
		String productType=ebkCertificate.getProductType();
		if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.HOTEL.name())){
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(ebkCertificate.getSubProductType())){
				return new EbkCertDataHotelSuitBuilder(params);
			}else{
				return new EbkCertDataHotelBuilder(params);
			}
		}else if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.ROUTE.name())){
			return new EbkCertDataRouteBuilder(params);
		}else if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.TICKET.name())){
			return new EbkCertDataTicketBuilder(params);
		}else if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.TRAFFIC.name())){
			return new EbkCertDataFlightBuilder(params);
		}
		throw new IllegalArgumentException("don't has current productType builder");
	}
	
	/**
	 * 不生成变更单的生成器
	 * @param ebkCertificate
	 * @return
	 */
	public static final EbkCertBuilder create(EbkCertificate ebkCertificate){
		return create(ebkCertificate,true);
	}
	
	public static final EbkCertBuilder createChange(EbkCertificate ebkCertificate){
		if(Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name().equals(ebkCertificate.getSubProductType())){
			return new EbkCertSingleRoomCompositeBuilder(ebkCertificate);
		}else if(Constant.PRODUCT_TYPE.TICKET.name().equals(ebkCertificate.getProductType())){
			return new EbkCertTicketCompositeBuilder(ebkCertificate);
		}
		return new EbkCertCompositeBuilder(ebkCertificate);
	}
	
	public static final EbkCertBuilder create(EbkCertificate ebkCertificate,boolean change){
		Assert.notNull(ebkCertificate);
		String productType=ebkCertificate.getProductType();
		if(change&&ebkCertificate.hasEbkCertificateTypeChange()){
			return createChange(ebkCertificate);
		}
		if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.HOTEL.name())){
			if(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name().equals(ebkCertificate.getSubProductType())){
				return new EbkCertHotelSuitBuilder();
			}else{
				return new EbkCertHotelBuilder();
			}
		}else if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.ROUTE.name())){
			return new EbkCertRouteBuilder();
		}else if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.TICKET.name())){
			return new EbkCertTicketBuilder();
		}else if(StringUtils.equals(productType, Constant.PRODUCT_TYPE.TRAFFIC.name())){
			return new EbkCertFlightBuilder();
		}
		throw new IllegalArgumentException("don't has current productType builder");
	}
	
}
