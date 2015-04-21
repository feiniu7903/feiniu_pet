package com.lvmama.comm.search.vo;

import java.io.Serializable;
import java.util.Comparator;

/**
 * AutoCompletePlaceDto对象转换而来的自动补全对象：[匹配字符_中文名称_排序串_SEQ],用在自动补全的匹配和排序上.
 * @author zhangzhenhua
 * 
 */

public class AutoCompletePlaceObject implements Serializable {
	private static final long serialVersionUID = 7392025382616567644L;
	protected String stage;
	protected String shortId;
	protected String pinyin;
	protected String matchword;
	protected String words;
	protected String matchSEQ;
	protected Long seq;
	protected String savedStage;
	public AutoCompletePlaceObject(){
		super();
	}
	public AutoCompletePlaceObject(String stage, String shortId, String pinyin, String matchword, String words, String matchSEQ, Long seq) {
		super();
		this.stage = stage;
		this.shortId = shortId;
		this.pinyin = pinyin;
		this.matchword = matchword;
		this.words = words;
		this.matchSEQ = matchSEQ;
		this.seq = seq;
		this.savedStage = new String(stage);
	}

	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
		if(savedStage == null ){
			this.savedStage = new String(stage);
		}
	}

	public String getShortId() {
		return shortId;
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getMatchword() {
		return matchword;
	}

	public void setMatchword(String matchword) {
		this.matchword = matchword;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	public String getMatchSEQ() {
		return matchSEQ;
	}

	public void setMatchSEQ(String matchSEQ) {
		this.matchSEQ = matchSEQ;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	/**
	 * 按关键字（目的地地标）排序的比较器
	 * 
	 * @author huangzhi
	 * 
	 */
	public static class comparatorMatchSEQ implements Comparator<AutoCompletePlaceObject> {
		@Override
		public int compare(AutoCompletePlaceObject o1, AutoCompletePlaceObject o2) {
			AutoCompletePlaceObject s1 = (AutoCompletePlaceObject) o1;
			AutoCompletePlaceObject s2 = (AutoCompletePlaceObject) o2;
			int result = s1.matchSEQ.compareTo(s2.matchSEQ);
			return result;
		}
	}
	
	/**
	 * 通用搜索补全按关键字（目的地地标）排序的比较器
	 * 
	 * @author huangzhi
	 * 
	 */
	public static class comparatorAllInOneMatchSEQ implements Comparator<AutoCompletePlaceObject> {
		@Override
		public int compare(AutoCompletePlaceObject o1, AutoCompletePlaceObject o2) {
			AutoCompletePlaceObject s1 = (AutoCompletePlaceObject) o1;
			AutoCompletePlaceObject s2 = (AutoCompletePlaceObject) o2;
			int result = s1.matchSEQ.compareTo(s2.matchSEQ);
			return result;
		}
	}

	/**
	 * 按关键字（目的地地标）排序的比较器
	 * 
	 * @author huangzhi
	 * 
	 */
	public static class comparatorMatchMatchWord implements Comparator<AutoCompletePlaceObject> {
		@Override
		public int compare(AutoCompletePlaceObject o1, AutoCompletePlaceObject o2) {
			AutoCompletePlaceObject s1 = (AutoCompletePlaceObject) o1;
			AutoCompletePlaceObject s2 = (AutoCompletePlaceObject) o2;
			int result = s1.matchword.compareTo(s2.matchword);
			return result;
		}
	}
	
	/**
	 * 按pinyin排序的比较器
	 * 
	 * @author huangzhi
	 * 
	 */
	public static class comparatorPinyin implements Comparator<AutoCompletePlaceObject> {
		@Override
		public int compare(AutoCompletePlaceObject o1, AutoCompletePlaceObject o2) {
			AutoCompletePlaceObject s1 = (AutoCompletePlaceObject) o1;
			AutoCompletePlaceObject s2 = (AutoCompletePlaceObject) o2;
			int result = s1.pinyin.compareTo(s2.pinyin);
			return result;
		}
	}
	@Override
	public boolean equals(Object obj) {
		if(this==obj){
			return true ;
		}
		if(obj==null ){
			return false;
		}
		if(obj!=null && obj instanceof AutoCompletePlaceObject){
			if(((AutoCompletePlaceObject)obj).words.equals(this.words))
				return  true;
		}
		return false;
	}
 
	public String getSavedStage() {
		return savedStage;
	}
	public void setSavedStage(String savedStage) {
		this.savedStage = savedStage;
	}
	@Override
	public int hashCode() {
		return  words.hashCode();
 
	}
}
