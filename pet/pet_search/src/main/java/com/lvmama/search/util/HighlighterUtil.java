package com.lvmama.search.util;

import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;

public class HighlighterUtil {
	private static Logger logger = Logger.getLogger(HighlighterUtil.class);

	public static String getHighLightText(String text, Query query, Analyzer analyzer) throws IOException {
		String highLightText = text;
		// 高亮设置
		SimpleHTMLFormatter simpleHtmlFormatter = new SimpleHTMLFormatter("<B style=\"color:red;\">", "</B>");// 设定高亮显示的格式，也就是对高亮显示的词组加上前缀后缀
		Highlighter highlighter = new Highlighter(simpleHtmlFormatter, new QueryScorer(query));
		highlighter.setTextFragmenter(new SimpleFragmenter(150));// 设置每次返回的字符数.想必大家在使用搜索引擎的时候也没有一并把全部数据展示出来吧，当然这里也是设定只展示部分数据
		TokenStream tokenStream = analyzer.tokenStream("", new StringReader(highLightText));
		try {
			highLightText = highlighter.getBestFragment(tokenStream, highLightText);
			if (StringUtils.isEmpty(highLightText)) {
				highLightText = text;
			}
		} catch (Exception e) {
			logger.error("高亮显示error");
		}
		return highLightText;
	}
}
