package com.lvmama.search.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author yuzhibing
 *
 */
public class OutPutLogger
{
    public static final String SEARCH = "search";
    public static final String SYSTEM = "system";

    private static Logger logger = Logger.getLogger(OutPutLogger.class.getName());
    private static Logger loggerKeyWord = Logger.getLogger("com.lvmama.common.KeywordLogger");

    /**
     * 记录日志信息
     *
     * @param level  日志级别，包括(DEBUG, INFO, WARN, ERROR
     * @param action 操作动作，如 search ...
     * @param detail对应于操作的具体描述，如搜索时指定的关键词.
     *               调用事例： SearchLogger.log(Level.INFO, SearchLogger.SEARCH, "keyword");
     *               SearchLogger.log(SearchLogger.SEARCH, "keyword");
     *               SearchLogger.log(SearchLogger.SEARCH);
     */
    public static void log(Level level, String action, String detail)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("ACTION:");
        sb.append(action);
        if (detail != null && !detail.equals(""))
        {
            sb.append("  DETAIL:");
            sb.append(detail);
        }

        logger.log(level, sb.toString());
    }

    public static void log(String action, String detail)
    {
        log(Level.INFO, action, detail);
    }

    public static void log(String action)
    {
        log(action, "");
    }

    public static void logKeyword(String wd)
    {
        loggerKeyWord.info(wd);
    }
}
