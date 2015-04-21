package com.lvmama.search.lucene.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.PlaceHotelBean;

public class PlaceHotelDocument extends PlaceDocument {

	/**
	 * 酒店主题
	 */
	public static String HOTEL_TOPIC = "hotelTopic";
	/**
	 * 产品主题
	 */
	public static String PROD_TOPIC = "prodTopic";
	/**
	 * 产品标签
	 */
	public static String  PROD_TAGS_NAME = "prodTagsName";
	/**
	 * 酒店星级
	 */
	public static String HOTEL_STAR = "hotelStar";
	
	/**
	 * 酒店星级
	 */
	public static String HOTEL_STAR_MERGE = "hotelStarMerge";
	/**
	 * 酒店图片(标记为列表展示的大图)
	 */
	public static String HOTEL_IMAGE = "hotelImage";
	
	public static String HOTEL_IMAGE_NAME = "hotelImageName";
	
	public static String HOTEL_IMAGE_CONTEXT = "hotelImageContext";
	/**
	 * 图片显示 (大图 or 小图)
	 */
	public static String PIC_DISPLAY = "picDisplay";
	/**
	 * 一句话推荐
	 */
	public static String RECOMMEND_CONTENT ="recommendContent";
	/**
	 * 团购产品数量
	 */
	public static String GROUP_PRODUCT_NUM= "groupProductNum";
	
	@Override
	public Document createDocument(Object t) {
		PlaceHotelBean ph = (PlaceHotelBean)t;
		Document doc = super.createDocument(ph);
		
		if(StringUtils.isNotBlank(ph.getHotelTopic())){
			String[] topics = ph.getHotelTopic().split(",");
			for(String topic : topics){
				doc.add(new StringField(HOTEL_TOPIC, topic, Field.Store.YES));
			}
		}else{
			doc.add(new StringField(HOTEL_TOPIC, "", Field.Store.YES));
		}
		
		/**TOPICS,字段中数据按分隔符","分词**/
		String topicsStr = ph.getProdTopic();
		String topics_word =  getChinaWordStr(topicsStr);
		String topics_pinyin = getPinyinWordStr(topicsStr);
		String topics = StringUtils.join(new String[]{topics_word,topics_pinyin},",");
		if (topics!= null && !"".equals(topics)  ) {
			StringTokenizer st = new StringTokenizer(topics, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				  String key=st.nextToken();
				  doc.add(new StringField(PlaceHotelDocument.PROD_TOPIC, key, Field.Store.YES));
			}
		} else {
				 doc.add(new StringField(PlaceHotelDocument.PROD_TOPIC, "", Field.Store.YES));
		}
		
		/**产品标签,字段中数据按分隔符","分词**/
		String prodTagsStr = ph.getProdTagsName();
		String prodTags_word =  getChinaWordStr(prodTagsStr);
		String prodTags_pinyin = getPinyinWordStr(prodTagsStr);
		String prodTags = StringUtils.join(new String[]{prodTags_word,prodTags_pinyin},",");
		if (prodTags!= null && !"".equals(prodTags)  ) {
			StringTokenizer st = new StringTokenizer(prodTags, SearchConstants.DELIM);
			while (st.hasMoreTokens()) {
				  String key=st.nextToken();
				  doc.add(new StringField(PlaceHotelDocument.PROD_TAGS_NAME, key, Field.Store.YES));
			}
		} else {
				 doc.add(new StringField(PlaceHotelDocument.PROD_TAGS_NAME, "", Field.Store.YES));
		}
		
		String hotelStarMerge = "";
		if( "8".equals(ph.getHotelStar() ) || "7".equals(ph.getHotelStar()) 	){
			hotelStarMerge="5";
		}else if( "6".equals(ph.getHotelStar() ) || "5".equals(ph.getHotelStar()) 	){
			hotelStarMerge="4";
		}if( "4".equals(ph.getHotelStar() ) || "3".equals(ph.getHotelStar()) 	){
			hotelStarMerge="3";
		}if( "2".equals(ph.getHotelStar() ) || "1".equals(ph.getHotelStar()) 	){
			hotelStarMerge="2";
		}
		
		doc.add(new StringField(HOTEL_STAR, ph.getHotelStar(), Field.Store.YES));
		doc.add(new StringField(HOTEL_STAR_MERGE, hotelStarMerge, Field.Store.YES));
		doc.add(new StringField(HOTEL_IMAGE, ph.getHotelImage(), Field.Store.YES));
		doc.add(new StringField(HOTEL_IMAGE_NAME, ph.getHotelImageName(), Field.Store.YES));
		doc.add(new StringField(HOTEL_IMAGE_CONTEXT, ph.getHotelImageContext(), Field.Store.YES));
		doc.add(new StringField(PIC_DISPLAY, ph.getPicDisplay(), Field.Store.YES));
		doc.add(new StringField(RECOMMEND_CONTENT, ph.getRecommendContent(), Field.Store.YES));
		doc.add(new IntField(GROUP_PRODUCT_NUM, ph.getGroupProductNum(), Field.Store.YES));
		
		return doc;
	}
	@Override
	public  Object parseDocument(Document doc){
		
		PlaceHotelBean ph  = (PlaceHotelBean) super.parseDocument(new PlaceHotelBean(), doc);
		
		ph.setHotelImage(doc.get(PlaceHotelDocument.HOTEL_IMAGE));
		ph.setHotelImageName(doc.get(PlaceHotelDocument.HOTEL_IMAGE_NAME));
		ph.setHotelImageContext(doc.get(PlaceHotelDocument.HOTEL_IMAGE_CONTEXT));
		List<Map<String,String>> imageList = new ArrayList<Map<String,String>>();
		if(StringUtils.isNotBlank(ph.getHotelImage())){
			String[] images = ph.getHotelImage().split(",");
			String[] imageNames = ph.getHotelImageName().split(",");
			String[] imageContexts = ph.getHotelImageContext().split(",");
			
			for(int i = 0 ; i< images.length ;i++){
				Map<String,String> img = new HashMap<String,String>();
				img.put("url", images[i]);
				String name = "";
				if(images.length == imageNames.length){
					name = imageNames[i];
				}
				img.put("name", name);
				String context = "";
				if(images.length == imageContexts.length){
					context = imageContexts[i];
				}
				img.put("context", context);
				imageList.add(img);
			}
		}
		ph.setHotelImageList(imageList);
		
		ph.setHotelStar(doc.get(PlaceHotelDocument.HOTEL_STAR));
		
		ph.setHotelStarMerge(doc.get(PlaceHotelDocument.HOTEL_STAR_MERGE));
		
		ph.setRecommendContent(doc.get(PlaceHotelDocument.RECOMMEND_CONTENT));
		
		ph.setGroupProductNum(Integer.parseInt(doc.get(PlaceHotelDocument.GROUP_PRODUCT_NUM)));
		
		ph.setPicDisplay(doc.get(PlaceHotelDocument.PIC_DISPLAY));
		
		String[] topics = doc.getValues(PlaceHotelDocument.HOTEL_TOPIC);
		
		String[] prodTopics = doc.getValues(PlaceHotelDocument.PROD_TOPIC);
		
		if (prodTopics.length == 0 ) {
			ph.setProdTopic("");
		}else{
			List<String> tmp_list = new ArrayList<String>();
			for (int i = 0; i <prodTopics.length; i++) {
				// 判断是中文
				if (prodTopics[i].matches("[^\n]*[\u4e00-\u9fa5]+[^\n]*")) {
					tmp_list.add(prodTopics[i]);
				}
			}		
			ph.setProdTopic(StringUtils.join(tmp_list,","));
			ph.setProdTopicList(tmp_list);
		}
		List<String> topicList = new ArrayList<String>();
		for(String topic: topics){
			if(StringUtils.isNotEmpty(topic)){
				topicList.add(topic);
			}
		}
		ph.setHotelTopic(StringUtils.join(topicList,","));
		ph.setHotelTopicList(topicList);
		
		
		return ph;
	}
}
