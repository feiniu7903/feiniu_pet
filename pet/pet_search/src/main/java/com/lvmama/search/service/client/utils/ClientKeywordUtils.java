package com.lvmama.search.service.client.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.search.vo.ClientPlaceSearchVO;
import com.lvmama.search.action.web.OneSearchAction;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.LocalSession;
import com.lvmama.search.util.LocalCacheManager;
/**
 * 
 * @author dengcheng
 *
 */
public class ClientKeywordUtils {

	public static String transKeyWords(String transCode_keyword){
			List ikKeywords = OneSearchAction.ikSegmenter(transCode_keyword);
			int j = 0;
			String[] arrSynonyms = new String[ikKeywords.size()];
			transCode_keyword = "";
			for (int i = 0; i < ikKeywords.size(); i++) {
				//取得keyword同义词进行search
				LikeHashMap synonymsMap = (LikeHashMap) LocalCacheManager.get("COM_SEARCH_KEYWORD_SYNONYMS");
				//keyword满足同义词的分词数量最大为3组
				if(synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).size()>0 && j<3){
					arrSynonyms[i]="";
					for (Iterator iter = ((List)synonymsMap.get(String.valueOf(ikKeywords.get(i)), true).get(0)).iterator(); iter.hasNext();) {
						arrSynonyms[i] = arrSynonyms[i] + (String)iter.next() + ",";
					}
					transCode_keyword = transCode_keyword + arrSynonyms[i];
					arrSynonyms[i] = arrSynonyms[i].substring(0, arrSynonyms[i].length()-1).trim();
					++j;
				}else{
					if(StringUtils.isNotBlank(arrSynonyms[i])){
						arrSynonyms[i] = arrSynonyms[i] + String.valueOf(ikKeywords.get(i));
					}else{
						arrSynonyms[i] = String.valueOf(ikKeywords.get(i));
					}
					transCode_keyword = transCode_keyword + String.valueOf(ikKeywords.get(i))  + ",";
				}
			}
		
			//把搜索内容的拆分的各个数组进行配对
			List synonymsList = new ArrayList<String[]>();
			for (int i = 0; i < arrSynonyms.length; i++) {
				List tempList = new ArrayList<String>();
				String[] arr = arrSynonyms[i].split(",");
				if (synonymsList.size()>0){
					for (Object object : synonymsList) {
						String synonyms1 = new String();
						synonyms1 = String.valueOf(object);
						for (int l = 0; l < arr.length; l++) {
							String synonyms = new String();
							synonyms = synonyms1 + arr[l]+",";
							tempList.add(synonyms);
						}
					}
					synonymsList = (List) ((ArrayList) tempList).clone();
				}else{
					for (int l = 0; l < arr.length; l++) {
						String synonyms = new String();
						synonyms = synonyms + arr[l]+",";
						tempList.add(synonyms);
					}
					synonymsList = (List) ((ArrayList) tempList).clone();
				}
				
			}
			for (int i = 0; i < synonymsList.size(); i++) {
				synonymsList.set(i, String.valueOf(synonymsList.get(i)).substring(0, String.valueOf(synonymsList.get(i)).length()-1).trim());
			}
			
			transCode_keyword = transCode_keyword.substring(0, transCode_keyword.length()-1);
			//把分组同义词配对结果放入缓存
//			LocalCacheManager.put(transCode_keyword, synonymsList);
			LocalSession.set(transCode_keyword, synonymsList);
			return transCode_keyword;
	}
}
