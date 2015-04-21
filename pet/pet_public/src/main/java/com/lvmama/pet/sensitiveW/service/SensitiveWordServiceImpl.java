package com.lvmama.pet.sensitiveW.service;

import com.lvmama.comm.pet.po.sensitiveW.SensitiveWord;
import com.lvmama.comm.pet.service.sensitiveW.SensitiveWordService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.pet.sensitiveW.dao.SensitiveWordDAO;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SensitiveWordServiceImpl implements SensitiveWordService {

	private SensitiveWordDAO sensitiveWordDAO;

	private Map<String, SensitiveWord> sensitiveWordMap;

	private static final Log logger = LogFactory
			.getLog(SensitiveWordServiceImpl.class);

	private static Object LOCK = new Object();

	private Integer localVersion = 0;

	private String key = "initSensitiveWordCacheMap_version";

	/**
	 * 初始化缓存数据
	 */
	@Override
	public void initSensitiveWordCacheMap(boolean create) {
		synchronized (LOCK) {
			if (create) {
				List<SensitiveWord> list = sensitiveWordDAO.selectAll();
				sensitiveWordMap = new LinkedHashMap<String, SensitiveWord>();
				for (SensitiveWord sw : list) {
					sensitiveWordMap.put(sw.getContent(), sw);
				}
				
				if(localVersion == 0)
					localVersion ++;
				
				Object obj = MemcachedUtil.getInstance().get(key);

				if (obj != null) {
					Integer cacheVersion = (Integer) obj;
					localVersion = cacheVersion;
				} else {
					MemcachedUtil.getInstance().set(key, localVersion);
				}

				logger.info("init sensitive word cache success..."
						+ sensitiveWordMap.size() + "条记录...localVersion:" + localVersion + "...cacheVersion:" + obj);
			} else {
				if (sensitiveWordMap != null) {
					sensitiveWordMap.clear();
				}

				Object obj = MemcachedUtil.getInstance().get(key);

				if (obj != null) {
					Integer cacheVersion = (Integer) obj;
					if (localVersion < cacheVersion) {
						localVersion = cacheVersion;
					}
				}

				MemcachedUtil.getInstance().set(key, localVersion + 1);

				logger.info("clear sensitive word cache success...localVersion:" + localVersion + "...cacheVersion:" + (localVersion + 1));
			}

		}
	}

	@Override
	public Long insert(SensitiveWord sensitiveWord) {
		// 判断该敏感词是否已经存在
		if (sensitiveWordDAO.checkIsExisted(sensitiveWord.getContent())) {
			return null;
		}
		Long id = sensitiveWordDAO.insert(sensitiveWord);
		initSensitiveWordCacheMap(false);
		return id;
	}

	@Override
	public Long update(SensitiveWord sensitiveWord) {
		// 判断该敏感词是否已经存在
		if (sensitiveWordDAO.checkIsExisted(sensitiveWord.getContent())) {
			return null;
		}
		Long count = sensitiveWordDAO.update(sensitiveWord);
		initSensitiveWordCacheMap(false);
		return count;
	}

	@Override
	public void deleteByPrimaryKey(Long sensitiveId) {
		sensitiveWordDAO.deleteByPrimaryKey(sensitiveId);
		initSensitiveWordCacheMap(false);
	}

	@Override
	public Long selectByParamsCount(Map<String, Object> params) {
		return sensitiveWordDAO.selectByParamsCount(params);
	}

	@Override
	public List<SensitiveWord> selectByParams(Map<String, Object> params) {
		return sensitiveWordDAO.selectByParams(params);
	}

	@Override
	public SensitiveWord selectByPrimaryKey(Long sensitiveId) {
		return sensitiveWordDAO.selectByPrimaryKey(sensitiveId);
	}

	public SensitiveWordDAO getSensitiveWordDAO() {
		return sensitiveWordDAO;
	}

	public void setSensitiveWordDAO(SensitiveWordDAO sensitiveWordDAO) {
		this.sensitiveWordDAO = sensitiveWordDAO;
	}

	@Override
	public List<SensitiveWord> selectListByIds(Long[] sensitiveIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sensitiveIds", sensitiveIds);
		return sensitiveWordDAO.selectListByIds(params);
	}

	@Override
	public void deleteByIds(Long[] sensitiveIds) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sensitiveIds", sensitiveIds);
		sensitiveWordDAO.deleteByIds(params);
		initSensitiveWordCacheMap(false);
	}

	private void getMemcachedMap() {
		logger.info("getMemcachedMap....");
		if (localVersion == 0) {
			initSensitiveWordCacheMap(true);
		} else {
			Object obj = MemcachedUtil.getInstance().get(key);
			if (obj != null) {
				Integer cacheVersion = (Integer) obj;
				logger.info("localVersion...." + localVersion + "...cacheVersion:" + cacheVersion);
				if (localVersion < cacheVersion) {
					initSensitiveWordCacheMap(true);
				}
			} else {
				initSensitiveWordCacheMap(true);
			}
		}
	}

	@Override
	public List<SensitiveWord> getAllSensitiveWords() {
		getMemcachedMap();
		List<SensitiveWord> list = new ArrayList<SensitiveWord>();
		Iterator<SensitiveWord> it = sensitiveWordMap.values().iterator();
		while (it.hasNext()) {
			SensitiveWord sw = it.next();
			list.add(sw);
		}
		return list;
	}

	@Override
	public boolean checkSensitiveWords(String value) {
		if (StringUtils.isEmpty(value)) {
			return false;
		}
		getMemcachedMap();
		Iterator<String> it = sensitiveWordMap.keySet().iterator();
		while (it.hasNext()) {
			String word = it.next();
			// 忽略大小写匹配
			Pattern pat = Pattern.compile(word, Pattern.CASE_INSENSITIVE);  
			Matcher mat = pat.matcher(value);  
			if(mat.find()) {
				logger.info("checkSensitiveWords...有敏感词[" + word + "]");
				return true;
			}
		}
		logger.info("checkSensitiveWords...没敏感词");
		return false;
	}

	@Override
	public String returnSensitiveWords(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		getMemcachedMap();
		StringBuffer msg = new StringBuffer();
		Iterator<String> it = sensitiveWordMap.keySet().iterator();
		while (it.hasNext()) {
			String word = it.next();
			// 忽略大小写匹配
			Pattern pat = Pattern.compile(word, Pattern.CASE_INSENSITIVE);  
			Matcher mat = pat.matcher(value);  
			if(mat.find()) {
				msg.append("'").append(word).append("'、");
			}
		}
		if (msg.length() > 0) {
			msg.setLength(msg.length() - 1);
			logger.info("returnSensitiveWords...有敏感词[" + msg.toString() + "]");
			return msg.toString();
		}
		logger.info("returnSensitiveWords...没敏感词");
		return null;
	}
}
