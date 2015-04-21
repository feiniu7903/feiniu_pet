/**
 *
 */
package com.lvmama.jinjiang;


import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.jinjiang.comm.JinjiangUtil;
import com.lvmama.jinjiang.model.response.AbstractResponse;
import com.lvmama.passport.utils.HttpsUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

/**
 * @author chenkeke
 */
public class JinjiangClient {
    /**
     * 时间价格同步cache时间
     */
    public static final String keyPrice = "CACHE_KEY_JINJIANG_SYNCPRICE";
    /**
     * 线路同步cache时间
     */
    public static final String keyLine = "CACHE_KEY_JINJIANG_SYNCLINE";
    private final static Log LOG = LogFactory.getLog(JinjiangClient.class);
    private static final int cacheSecond = 2 * 60 * 60;

    /**
     * 放入调用完同步线路产品后的更新时间
     */
    public static void putSycTime(String key, Date syncTime) {
        MemcachedUtil.getInstance().set(key, cacheSecond, syncTime);
    }

    /**
     * 获取 memcache缓存的同步时间
     */
    public static Date getSycTime(String key) {
        Object obj = MemcachedUtil.getInstance().get(key);
        if (obj == null) {
            // 缓存时间为小时，单位计算为7200秒，没有取到值时，先用当前日期的前一天放入值
            MemcachedUtil.getInstance().set(key, cacheSecond, DateUtil.getBeforeDay(new Date()));
        }
        return (Date) MemcachedUtil.getInstance().get(key);
    }

    /**
     * 请求操作
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends Response> T execute(Request request) {
        try {

            JSONObject json = JSONObject.fromObject(request, JinjiangUtil.getJsonConfig());
            String requestjson = json.toString();
            LOG.info("requesturl = " + request.getRequestURI() + "   requestjson = " + requestjson);
            //Map<String,String> requestParas = new HashMap<String, String>();
            //requestParas.put("request", requestjson);

            String responseJson = HttpsUtil.requestPostJson(request.getRequestURI(), json.toString());
            LOG.info("responseJson = " + responseJson);
            T res = (T) request.getResponseClazz().newInstance();
            res = res.parse(responseJson);
            return res;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) createDefaultResponse(request);
    }

    private Response createDefaultResponse(Request request) {
        Response res = null;
        try {
            res = request.getResponseClazz().newInstance();
            if (res instanceof AbstractResponse) {
                ((AbstractResponse) res).setSuccess(false);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return res;
    }


}
