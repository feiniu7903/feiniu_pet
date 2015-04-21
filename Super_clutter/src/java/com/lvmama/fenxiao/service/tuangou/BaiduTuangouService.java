package com.lvmama.fenxiao.service.tuangou;

import java.io.Writer;
import java.util.List;

import com.lvmama.fenxiao.model.tuangou.BaiduAccessToken;

public interface BaiduTuangouService {
	/**
	 * 通过Authorization Code获取的Access Token使用双缓存保存起来
	 */
	BaiduAccessToken getAccessTokenByAuthorizationCode(String authorizationCode);

	/**
	 * 保存到百度团购产品信息表
	 */
	void saveBaiduTuangouProduct(Long productId);

	/**
	 * 以XML格式输出百度团购产品信息
	 */
	void outputBaiduTuangouXML(Writer out) throws Exception;

	/**
	 * 团购订单信息提交（在付款成功后调用）
	 * 
	 * @param orderId
	 *            驴妈妈订单号
	 * @param tn
	 *            百度推广渠道唯一ID，如为空，则表示非百度推广渠道的订单；否则为baidutuan_tg
	 * @param baiduid
	 *            直接回传百度推广渠道带过去的32位 baiduid参数（不能做任何改动）
	 */
	void saveOrder(Long orderId, String tn, String baiduid);

	/**
	 * 查询所有可分销团购产品ID
	 */
	List<Long> selectAllBaiduTuangouProductIds(Long startRow, Long endRow);

	/**
	 * 获取团购产品总数
	 */
	Long getGroupProdIdCount();

	/**
	 * 删除所有团购产品
	 */
	void deleteAllBaiduTuangouProducts();

	/**
	 * 判断产品是否属于团购类产品
	 */
	boolean isTuangouProduct(Long productId);
	
	/**
	 * 从双缓存里清除Authorization Code类型的Access Token
	 */
	public void removeAuthorizationCodeAccessToken();
	
	/**
	 * 从双缓存里清除Client Credentials类型的Access Token
	 */
	public void removeClientCredentialsAccessToken();
}
