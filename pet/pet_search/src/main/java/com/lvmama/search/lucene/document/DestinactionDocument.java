package com.lvmama.search.lucene.document;

import java.util.StringTokenizer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.search.util.LuceneCommonDic;



public class DestinactionDocument extends AbstactDocument {
	
	/**
	 * 新版本 @author huangzhi
	 * */
	
	
	/** 门票总数 */
	public static String TICKET_NUM = "ticketNum";
	
	public static String DEST_SUBJECTS = "destSubjects";
	
	public static String DEST_FREENESS_NUM = "destFreeneeeNum";
	
	public static String DEST_PERIPHERY_NUM = "destPeripheryNum";
	
	public static String DEST_ABROAD_NUM = "destAbroadNum";
	
	public static String DEST_INTERNAL_NUM = "destInternalNum";
	
	public static String OWNFIELD = "ownfield";
	
	private final static String DELIM = ",";
	
	public static String SHORT_ID="shortId";
	
	public Document createDocument(Object t) {
		PlaceBean placeBean = (PlaceBean) t;
		Document doc = new Document();
		StringField id = new StringField("id", placeBean.getId(), Field.Store.YES);
		if (placeBean.getBoost() > 0) {
			id.setBoost(placeBean.getBoost());
		}
		doc.add(id);
		doc.add(new Field("imageUrl", placeBean.getImageUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		//doc.add(new Field("name", placeBean.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("summary", placeBean.getSummary(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("contents", placeBean.getContent(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field("pinYinUrl", placeBean.getPinYinUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("hfkw", placeBean.getHfkw(), Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("hasHotel", placeBean.getHasHotel(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field(PlaceDocument.SHORT_ID, placeBean.getShortId() + "", Field.Store.YES, Field.Index.NOT_ANALYZED));	
		//加入默认字段
		doc.add(new TextField(DestinactionDocument.OWNFIELD,LuceneCommonDic.OWNFIELD,Field.Store.YES));
		
		String pinYin = placeBean.getPinYin();
		if (pinYin != null && !"".equals(pinYin)) {
			for (int i = 0; i < pinYin.length(); i++) {
				doc.add(new Field("pinYin", pinYin.substring(i), Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
		}
				
		doc.add(new LongField(DestinactionDocument.TICKET_NUM,placeBean.getTicketNum(),Field.Store.YES));
		
		doc.add(new LongField(DestinactionDocument.DEST_FREENESS_NUM,placeBean.getDestFreenessNum(), Field.Store.YES));
		
		doc.add(new LongField(DestinactionDocument.DEST_ABROAD_NUM, placeBean.getDestAbroadNum(),Field.Store.YES));
		
		doc.add(new LongField(DestinactionDocument.DEST_PERIPHERY_NUM,placeBean.getDestPeripheryNum(), Field.Store.YES));
		
		doc.add(new LongField(DestinactionDocument.DEST_INTERNAL_NUM,placeBean.getDestInternalNum(), Field.Store.YES));

		String destSubjects = placeBean.getDestSubjects();
		if (destSubjects!= null && !"".equals(destSubjects)) {
			StringTokenizer st = new StringTokenizer(destSubjects, DELIM);
			while (st.hasMoreTokens()) {
				  doc.add(new Field(DestinactionDocument.DEST_SUBJECTS, st.nextToken(), Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
		} else {
			doc.add(new Field(DestinactionDocument.DEST_SUBJECTS, "", Field.Store.YES, Field.Index.NOT_ANALYZED));
		}
		
	
		
		String name = placeBean.getName();
		//根据name添加顺序，依次将新域值中分词后的词元加入到索引中
		if (name != null && !"".equals(name)) {
			for (int i = 0; i <name.length(); i++) {
				doc.add(new Field("name", name.substring(i), Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
			for (int i = 1; i <=name.length(); i++) {
				doc.add(new Field("name", name.substring(0,i), Field.Store.YES, Field.Index.NOT_ANALYZED));
			}
		}
		
		//doc.add(new Field("lpUrl", placeBean.getLpUrl(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		Long seq_val = null;
		try {
			seq_val = Long.parseLong(placeBean.getSeq());
		} catch (NumberFormatException e) {
			seq_val = 0l;
		}
		doc.add(new LongField("seq",seq_val, Field.Store.YES));
		return doc;
	}

	public Object parseDocument(Document doc) {
		PlaceBean s = new PlaceBean();
		s.setId(doc.get("id"));
		//s.setName(doc.get("name"));
		s.setSummary(doc.get("summary"));
		s.setImageUrl(doc.get("imageUrl"));
		s.setSaleFavourable(doc.get("saleFavourable"));
		s.setPinYinUrl(doc.get("pinYinUrl"));
		s.setPinYin(doc.getValues("pinYin")[0].toUpperCase());
		s.setName(doc.getValues("name")[0]);
		s.setShortId(doc.get(PlaceDocument.SHORT_ID));
		//s.setLpUrl(doc.get("lpUrl"));
		s.setDestAbroadNum(new Long(doc.get(PlaceDocument.DEST_ABROAD_NUM)));
		s.setDestFreenessNum(new Long(doc.get(PlaceDocument.DEST_FREENESS_NUM)));
		s.setDestInternalNum(new Long(doc.get(PlaceDocument.DEST_INTERNAL_NUM)));
		s.setDestPeripheryNum(new Long(doc.get(PlaceDocument.DEST_PERIPHERY_NUM)));
		return s;
	}
}
