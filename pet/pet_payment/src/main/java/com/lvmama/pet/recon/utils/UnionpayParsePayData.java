package com.lvmama.pet.recon.utils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;



import com.lvmama.comm.utils.DateUtil;
import com.lvmama.pet.vo.UnionPayResponseBean;

/**
 * 银联对账文件解析类
 * @author ZHANG Nan
 */
public class UnionpayParsePayData{
	public static List<UnionPayResponseBean> parseData(String datas){
		try {
			if (StringUtils.isBlank(datas)) {
				return null;
			}
			List<UnionPayResponseBean> unionPayResponseBeanList = new ArrayList<UnionPayResponseBean>();
			
			BufferedReader br = new BufferedReader(new StringReader(datas.trim()));
			String dataTmp = null;
			// 解析文件
			while ((dataTmp = br.readLine()) != null) {
				unionPayResponseBeanList.add(convertBean(dataTmp));
			}
			return unionPayResponseBeanList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static UnionPayResponseBean convertBean(String csv) {
		UnionPayResponseBean unionPayResponseBean = new UnionPayResponseBean();
		//交易流水号
		unionPayResponseBean.setBankGatewayTradeNo(csv.substring(87,87+21).trim());
		//交易类型
		unionPayResponseBean.setTransactionType(csv.substring(215,215+2).trim());
		//对账流水号
		unionPayResponseBean.setBankPaymentTradeNo(csv.substring(112,112+32).trim());
		//交易时间
		unionPayResponseBean.setTransactionTime(DateUtil.stringToDate(new SimpleDateFormat("yyyy").format(new Date())+csv.substring(35,35+10).trim(), "yyyyMMddHHmmss")); 
		//金额
		unionPayResponseBean.setAmount(Long.parseLong(csv.substring(66,66+12).trim()));
		//支付方式
		unionPayResponseBean.setPayType(csv.substring(109,109+2).trim());
		return unionPayResponseBean;
	}
}
