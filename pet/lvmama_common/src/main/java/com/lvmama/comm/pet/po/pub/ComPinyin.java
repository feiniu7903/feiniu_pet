package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.List;

public class ComPinyin  implements Serializable {

	private static final long serialVersionUID = -7878712481263373230L;

	private Long comPinyinId;

	private String word;
	
	private List<String> wordList;

	private String pinyin;
	public ComPinyin() {
		
	}
	public Long getComPinyinId() {
		return comPinyinId;
	}

	public List<String> getWordList() {
		return wordList;
	}

	public void setWordList(List<String> wordList) {
		this.wordList = wordList;
	}

	public void setComPinyinId(Long comPinyinId) {
		this.comPinyinId = comPinyinId;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
}