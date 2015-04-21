package com.lvmama.pet.recon.utils;
import java.io.Reader;
import java.util.List;

import com.lvmama.comm.pet.vo.PayDataImportBean;

/**
 * 中国银行数据下载
 * @author ranlongfei 2012-6-27
 * @version
 */
public class BOCParsePayData implements ParsePayData {

	public PayDataImportBean convertBean(String[] csv) {
		return null;
	}
	
	public List<PayDataImportBean> parse(Reader in) throws Exception {
		return null;
	}
	
	/**
	 * 
	 * @author: ranlongfei 2012-6-27 上午11:44:56
	 * @param args
	 */
	public static void main(String[] args) {
		
	}

	public List<PayDataImportBean> parseData(String data) throws Exception {
		return null;
	}

	@Override
	public String getReconGwType() {
		return null;
	}

}
