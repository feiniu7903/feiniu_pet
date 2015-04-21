package com.lvmama.pet.recon.utils;

import java.util.List;

import com.lvmama.comm.pet.vo.PayDataImportBean;
/**
 * 解析银行数据的模板类
 * @author ranlongfei 2012-6-26
 * @version
 */
public interface ParsePayData {
	/**
	 * 下载数据
	 * <br>sParaTemp 中封装具体参数
	 * @author: ranlongfei 2012-6-26 下午04:45:19
	 * @param sParaTemp
	 * @return
	 * @throws Exception
	 */
	List<PayDataImportBean> parseData(String data) throws Exception;
	/**
	 * 得到平台类型代码
	 * 
	 * @author: ranlongfei 2012-6-27 上午10:26:15
	 * @return
	 */
	String getReconGwType();
}
