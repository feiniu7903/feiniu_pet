package com.lvmama.pet.recon.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.pet.po.pay.FinReconBankStatement;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.FTPUtil;
import com.lvmama.comm.utils.FTPUtil.FtpConf;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 下载杉德POS机的财务明细数据
 * 
 * @author lvhao 2014-5-15
 * @version
 */
public class SandPosPayReconUtil{

	public static List<FinReconBankStatement> queryReconData(Date date){
		// 日期
		String fileDate = DateUtil.formatDate(date, "yyMMdd");
		String remoteFileName = "lvmama" + fileDate + ".xls";
		
		FtpConf ftpConf = new FtpConf();
		ftpConf.setUrl("116.228.234.35");
		ftpConf.setPort(21);
		ftpConf.setUsername("lvmama");
		ftpConf.setPassword("lvmama@123");

		try {
			List<FinReconBankStatement> list = new ArrayList<FinReconBankStatement>();
			//读取远程ftp文件内容
			List<String> dataList = FTPUtil.getFileAllLine(ftpConf,remoteFileName);
			if(dataList!=null && dataList.size()>10){
				//前7行和后3行不用存储
				int length = dataList.size()-3;
				for(int i=7;i<length;i++){
					String item[] = dataList.get(i).split("\\|");
					
					boolean payFlag = false;
					FinReconBankStatement finReconBankStatement = new FinReconBankStatement();
					finReconBankStatement.setGateway(Constant.PAYMENT_GATEWAY.SAND_POS.name());
					finReconBankStatement.setTransactionType(Constant.TRANSACTION_TYPE.PAYMENT.name());
					finReconBankStatement.setCreateTime(new Date());
					
					for (int j = 1; j < item.length; j++) {
						String value = item[j];
						if (StringUtils.isNotEmpty(value)) {
							value = value.trim();
							if(j==1){
								finReconBankStatement.setBankPaymentTradeNo(value);
							}else if(j==2){
								finReconBankStatement.setAmount(PriceUtil.convertToFen(value));
							}else if(j==5){
								//支付方式为01的数据为支付成功
								if("01".equals(value)){payFlag = true;}
							}else if(j==9){
								finReconBankStatement.setBankGatewayTradeNo(value);
							}else if(j==16){
								finReconBankStatement.setTransactionTime(DateUtil.toDate(value,"yyyyMMddHHmmss"));
								finReconBankStatement.setDownloadTime(DateUtil.toDate(value.substring(0, 8),"yyyyMMdd"));
							}
						}
					}
					//将杉德POS支付成功的数据放入集合中
					if(payFlag){
						list.add(finReconBankStatement);
					}
				}
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
