package com.lvmama.search.listener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.lvmama.comm.pet.service.search.ComSearchTranscodeService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.search.synonyms.LikeHashMap;
import com.lvmama.search.synonyms.SynonymsExcelSheetParser;
import com.lvmama.search.util.ConfigHelper;
import com.lvmama.search.util.LocalCacheManager;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SearchDataLoader extends ContextLoaderListener implements
		ServletContextListener {

	private static Log log = LogFactory.getLog(SearchDataLoader.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		initTranscode();
	} 
	public  static void initTranscode(){
		log.info("=========beigin initTranscode=========");
		ComSearchTranscodeService comSearchTranscodeService = (ComSearchTranscodeService) SpringBeanProxy.getBean("comSearchTranscodeService");
		Map<Long,String> transcodeMap = comSearchTranscodeService.searchAll();
		LocalCacheManager.put("COM_SEARCH_TRANSCODE_ID_KEYWORD", transcodeMap, 0);
		Map<String,Long> codeMap = new HashMap<String,Long>();
		for(Entry<Long, String> entry:transcodeMap.entrySet()){
			codeMap.put(entry.getValue(), entry.getKey());
		}
		LocalCacheManager.put("COM_SEARCH_TRANSCODE_KEYWORD_ID", codeMap, 0);

		// cache中放入同义词map
		LikeHashMap synonymsMap = new LikeHashMap();
		File file = new File(ConfigHelper.getString("SYNONYMS_PATH"));
		if (file != null) {
			SynonymsExcelSheetParser parser = new SynonymsExcelSheetParser(file);
			List<List> datas = parser.getDatasInSheet(0);
			for (int i = 1; i < datas.size(); i++) {
				List row = datas.get(i);
				if (synonymsMap.get(String.valueOf(row.get(1)), true).size()>0) {
					List<String> tempList = (List<String>) synonymsMap.get(String.valueOf(row.get(1)), true).get(0);
					tempList.add(String.valueOf(row.get(0)));
					String removeKey = synonymsMap.remove(String.valueOf(row.get(1)), true);
					synonymsMap.put(removeKey+"~"+String.valueOf(row.get(0)), tempList);
				} else {
					List<String> tempList = new ArrayList<String>();
					tempList.add(String.valueOf(row.get(1)));//key值也作为value
					tempList.add(String.valueOf(row.get(0)));
					synonymsMap.put(String.valueOf(row.get(1))+"~"+String.valueOf(row.get(0)), tempList);
				}
			}
		}
		LocalCacheManager.put("COM_SEARCH_KEYWORD_SYNONYMS", synonymsMap, 0);
		System.out.println();
		log.info("=========initTranscode success=========");
	}
}
