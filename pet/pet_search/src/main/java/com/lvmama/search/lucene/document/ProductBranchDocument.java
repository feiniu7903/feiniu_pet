package com.lvmama.search.lucene.document;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.search.vo.ProductBranchBean;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.LuceneCommonDic;

/**
 * 产品下类别产品DOCUMENT实现类
 * 
 * @author huangzhi
 **/
public class ProductBranchDocument extends AbstactDocument {
	public static String PROD_BRANCH_ID = "prodBranchId";
	public static String CREATE_TIME = "createTime";
	public static String UPDATE_TIME = "updateTime";
	public static String BRANCH_NAME = "branchName";
	public static String BED_TYPE = "bedType";
	public static String DESCRIPTION = "description";
	public static String BROADBAND = "broadBand";
	public static String CASH_REFUND = "cashRefund";
	public static String BREAKFAST = "breakfast";
	public static String MARKET_PRICE = "marketPrice";
	public static String SELL_PRICE = "sellPrice";
	public static String ICON = "icon";
	public static String ADDITIONAL = "additional";
	public static String ON_LINE = "onLine";
	public static String VALID = "valid";
	public static String PRODUCT_ALL_PLACE_IDS = "productAllPlaceIds";
	public static String PRODUCT_ALLTO_PLACE_CONTENT = "productAlltoPlaceContent";
	public static String PRODUCT_ID = "productId";
	public static String VISIBLE = "visible";
	public static String DEFAULT_BRANCH = "defaultBranch";
	public static String CHANNEL = "channel";
	public static String SUB_PRODUCT_TYPE = "subProudctType";
	public static String VALID_BEGIN_TIME = "validBeginTime";
	public static String VALID_END_TIME = "validEndTime";
	public static String INVALID_DATE_MEMO = "invalidDateMemo";
	/** 普通排序的socre **/
	public static String NORMALSCORE = "normalScore";	
	/** hbase的关键字 **/
	public static String HBASEKEY = "hbasekey";	
	/** hbase的关键字的score **/
	public static String HBASEKEYSCORE = "hbasekeyscore";
	
	public static String OWNFIELD = "ownfield";
	
	//微信分享标识
	private static String SHAREWEIXIN = "shareweixin";

	public ProductBranchDocument() {

	}

	// 传入PRUDUCTBEAN 建立DOCUMNET. 注意这里改参数
	public Document createDocument(Object t) {
		ProductBranchBean productBranchBean = (ProductBranchBean) t;
		Document doc = new Document();
		double normalScore=1;
		try {
			normalScore=DocumentUtil.normalScore(productBranchBean);
		} catch (Exception e1) {
			throw new RuntimeException();
		}
		doc.add(new DoubleField(ProductDocument.NORMALSCORE, normalScore, Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.PROD_BRANCH_ID, productBranchBean.getProdBranchId() + "", Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.CREATE_TIME, productBranchBean.getCreateTime(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.UPDATE_TIME, productBranchBean.getUpdateTime(), Field.Store.YES));
//		doc.add(new StringField(ProductBranchDocument.BRANCH_NAME, productBranchBean.getBranchName(), Field.Store.YES));
		doc.add(new TextField(ProductBranchDocument.BRANCH_NAME, productBranchBean.getBranchName(), Field.Store.YES));
		//加入默认字段
	    doc.add(new TextField(ProductBranchDocument.OWNFIELD,LuceneCommonDic.OWNFIELD,Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.BED_TYPE, productBranchBean.getBedType(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.DESCRIPTION, productBranchBean.getDescription(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.BROADBAND, productBranchBean.getBroadBand(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.CASH_REFUND, productBranchBean.getCashRefund(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.BREAKFAST, productBranchBean.getBreakfast(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.MARKET_PRICE, productBranchBean.getMarketPrice() + "", Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.SELL_PRICE, productBranchBean.getSellPrice() + "", Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.ICON, productBranchBean.getIcon(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.ADDITIONAL, productBranchBean.getAdditional(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.ON_LINE, productBranchBean.getOnLine(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.VALID, productBranchBean.getValid(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.PRODUCT_ID, productBranchBean.getProductId() + "", Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.VISIBLE, productBranchBean.getVisible(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.DEFAULT_BRANCH, productBranchBean.getDefaultBranch(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.CHANNEL, productBranchBean.getChannel(), Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.SUB_PRODUCT_TYPE, productBranchBean.getSubProudctType(), Field.Store.YES));
		String vb = productBranchBean.getValidBeginTime() == null ? "" : DateUtil.formatDate(productBranchBean.getValidBeginTime(), "yyyy-MM-dd");
		String ve = productBranchBean.getValidEndTime() == null ? "" : DateUtil.formatDate(productBranchBean.getValidEndTime(), "yyyy-MM-dd");
		doc.add(new StringField(ProductBranchDocument.VALID_BEGIN_TIME, vb, Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.VALID_END_TIME, ve, Field.Store.YES));
		doc.add(new StringField(ProductBranchDocument.INVALID_DATE_MEMO, productBranchBean.getInvalidDateMemo(), Field.Store.YES));
		String shareweixin = productBranchBean.getShareweixin() == null ? "" : productBranchBean.getShareweixin();
		doc.add(new StringField(ProductBranchDocument.SHAREWEIXIN, shareweixin, Field.Store.YES));
		/****/
		String productAllToPlaceIds = productBranchBean.getProductAllPlaceIds();
		if (productAllToPlaceIds != null && !"".equals(productAllToPlaceIds)) {
			StringTokenizer st = new StringTokenizer(productAllToPlaceIds, ",");
			while (st.hasMoreTokens()) {
				doc.add(new StringField(ProductBranchDocument.PRODUCT_ALL_PLACE_IDS, st.nextToken(), Field.Store.YES));
				// 解决destPresentStr不为空，但是为"," 的BUG!
				if (productAllToPlaceIds.equals(",")) {
					doc.add(new StringField(ProductBranchDocument.PRODUCT_ALL_PLACE_IDS, " ", Field.Store.YES));
				}
			}
		} else {
			doc.add(new StringField(ProductBranchDocument.PRODUCT_ALL_PLACE_IDS, "", Field.Store.YES));
		}

		/****/
		String productAllToPlaceContentStr = productBranchBean.getProductAlltoPlaceContent();
		String productAllToPlaceContent = getChinaWordStr(productAllToPlaceContentStr) + "," + getPinyinWordStr(productAllToPlaceContentStr);
		if (StringUtils.isNotEmpty(productAllToPlaceContent) && !",".equals(productAllToPlaceContent)) {
			Map<String, String> check = new HashMap<String, String>();
			StringTokenizer st = new StringTokenizer(productAllToPlaceContent, ",");
			while (st.hasMoreTokens()) {
				StringTokenizer kg = new StringTokenizer(st.nextToken(), " ");
				while (kg.hasMoreTokens()) {
					String key = kg.nextToken();
					if (!check.containsKey(key)) {
						if (!"中国".equals(key) && !"亚洲".equals(key)) {// 过滤掉中国，亚洲
							doc.add(new StringField(ProductBranchDocument.PRODUCT_ALLTO_PLACE_CONTENT, key, Field.Store.YES));
						}
						// 特殊字符处理，特殊字符不进入索引！
						if (key.length() != CommonUtil.escapeString(key).length()) {
							// System.out.println(key);
							doc.add(new StringField(ProductBranchDocument.PRODUCT_ALLTO_PLACE_CONTENT, CommonUtil.escapeString(key), Field.Store.YES));
						}
						check.put(key, key);

					}
				}
			}
		} else {
			doc.add(new StringField(ProductBranchDocument.PRODUCT_ALLTO_PLACE_CONTENT, "", Field.Store.YES));
		}

		return doc;
	}

	// 把document转换成PRODUCTBEAN
	public Object parseDocument(Document doc) {
		ProductBranchBean s = new ProductBranchBean();
		s.setProdBranchId(new Long(doc.get(ProductBranchDocument.PROD_BRANCH_ID)));
		s.setBranchName(doc.get(ProductBranchDocument.BRANCH_NAME));
		s.setBedType(doc.get(ProductBranchDocument.BED_TYPE));
		s.setDescription(doc.get(ProductBranchDocument.DESCRIPTION));
		s.setBroadBand(doc.get(ProductBranchDocument.BROADBAND));
		s.setCashRefund(doc.get(ProductBranchDocument.CASH_REFUND));
		s.setBreakfast(doc.get(ProductBranchDocument.BREAKFAST));
		BigDecimal marketPrice = new BigDecimal(doc.get(ProductBranchDocument.MARKET_PRICE));
		s.setMarketPrice(marketPrice.multiply(new BigDecimal(100)).longValue());
		BigDecimal bigDecimal = new BigDecimal(doc.get(ProductBranchDocument.SELL_PRICE));
		s.setSellPrice(bigDecimal.multiply(new BigDecimal(100)).longValue());
		s.setIcon(doc.get(ProductBranchDocument.ICON));
		s.setProductId(doc.get(ProductBranchDocument.PRODUCT_ID));
		s.setDefaultBranch(doc.get(ProductBranchDocument.DEFAULT_BRANCH));
		s.setSubProudctType(doc.get(ProductBranchDocument.SUB_PRODUCT_TYPE));
		String vb = doc.get(ProductBranchDocument.VALID_BEGIN_TIME);
		String ve = doc.get(ProductBranchDocument.VALID_END_TIME);
		s.setValidBeginTime(StringUtil.isEmptyString(vb) ? null : DateUtil.stringToDate(vb, "yyyy-MM-dd"));
		s.setValidEndTime(StringUtil.isEmptyString(ve) ? null : DateUtil.stringToDate(ve, "yyyy-MM-dd"));
		s.setInvalidDateMemo(doc.get(ProductBranchDocument.INVALID_DATE_MEMO));
		s.setShareweixin(StringUtil.isEmptyString(doc.get(ProductBranchDocument.SHAREWEIXIN)) ? null : doc.get(ProductBranchDocument.SHAREWEIXIN));
		return s;
	}

	/**
	 * 把一个字符串:{中文A~pinyinA~pyA,中文B~pinyinB~pyB,....}中的中文抽出了转换成格式
	 * :{中文A,中文B...},中文顺序不变,去重复.
	 * 
	 * @return String
	 */
	public static String getChinaWordStr(String str) {
		// 分隔符全部变成逗号
		StringBuffer stringBuffer = new StringBuffer();
		// 排重用hashmap
		HashMap<String, String> check = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			StringTokenizer kg = new StringTokenizer(st.nextToken(), "~");
			while (kg.hasMoreTokens()) {
				String word = kg.nextToken();
				// 判断是中文：只要不是纯拼音的都是中文
				if (word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null) {// 新的数据,保留
						check.put(word, word);
						stringBuffer.append(word).append(",");
					}
				}
			}

		}
		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		return stringBuffer.toString();
	}

	/**
	 * 把一个字符串:{中文A~pinyinA~pyA,中文B~pinyinB~pyB,....}中的全拼,简拼抽出了转换成格式
	 * :{pinyinA,pyA,pinyinB,pyB...},去重复.
	 * 
	 * @return String
	 */
	public static String getPinyinWordStr(String str) {
		// 分隔符全部变成逗号
		StringBuffer stringBuffer = new StringBuffer();
		// 排重用hashmap
		HashMap<String, String> check = new HashMap<String, String>();
		StringTokenizer st = new StringTokenizer(str, ",");
		while (st.hasMoreTokens()) {
			StringTokenizer kg = new StringTokenizer(st.nextToken(), "~");
			while (kg.hasMoreTokens()) {
				String word = kg.nextToken();
				// 判断是拼音
				if (!word.matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					if (check.get(word) == null) {// 新的数据,保留
						check.put(word, word);
						stringBuffer.append(word).append(",");
					}
				}
			}

		}
		if (stringBuffer.toString().endsWith(",")) {
			stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		}
		return stringBuffer.toString();
	}

}
