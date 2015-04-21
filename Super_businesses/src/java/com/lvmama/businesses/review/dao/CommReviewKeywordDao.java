package com.lvmama.businesses.review.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.businesses.po.review.KeyWord;

public class CommReviewKeywordDao extends BaseIbatisDAO{
 
	private static final long serialVersionUID = 13464534L;

	public void reviewKeywordSynInset(List<KeyWord> list) {
 	 super.batchInsert("PHP_REVIEW_KEYWORD.batchInsertKeyWord", list);
	}
	public void synKeyWordUpdate(String oldContent, String newContent) {
		 Map<String,Object> map=new HashMap<String,Object>();
		 map.put("oldContent", oldContent);
		 map.put("newContent", newContent);
		 super.update("PHP_REVIEW_KEYWORD.updateKeyWord",map);
	}
	public void synDeleteBykeyWord(String content){
		 KeyWord key=new KeyWord();
		 key.setkContent(content);
		 super.delete("PHP_REVIEW_KEYWORD.deleteKeyWord",key);
	}
	public void synBatchDeleteKeyWord(List<KeyWord> keyWordsList){
		super.batchDelete("PHP_REVIEW_KEYWORD.deleteKeyWord", keyWordsList);
	}
 
	 
}
