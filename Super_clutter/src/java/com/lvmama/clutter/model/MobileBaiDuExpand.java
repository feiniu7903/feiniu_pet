package com.lvmama.clutter.model;

import java.io.Serializable;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.clutter.web.BaseAction;

public class MobileBaiDuExpand  implements Serializable{
	private final String VERSION = "wap-2-0.3";

    private final int VISIT_DURATION = 1800;

    private final int VISITOR_MAX_AGE = 31536000;

    private final String[][] SEARCH_ENGINE_LIST = {
        {"1", "baidu.com", "word|wd|w"}, 
        {"2", "google.com", "q"}, 
        {"4", "sogou.com", "query"}, 
        {"6", "search.yahoo.com", "p"}, 
        {"7", "yahoo.cn", "q"}, 
        {"8", "soso.com", "w"}, 
        {"11", "youdao.com", "q"}, 
        {"12", "gougou.com", "search"}, 
        {"13", "bing.com", "q"},
        {"14", "so.com", "q"}, 
        {"14", "so.360.cn", "q"}, 
        {"15", "jike.com", "q"}, 
        {"16", "qihoo.com", "kw"}, 
        {"17", "etao.com", "q"}, 
        {"18", "soku.com", "keyword"},
        {"19", "easou.com", "q"}, 
        {"20", "glb.uc.cn", "keyword|word|q"}
    };

    private String siteId = "";
    private String[] domainName = {};

    private String searchEngine = "";
    private String searchWord = "";

    private String visitUrl = "";

    private int eventType = 0;
    private String eventProperty = "";

    private HttpServletRequest request = null;
    private HttpServletResponse response = null;

    private String getRandomNumber()  {
        return Integer.toString((int) (Math.random() * 0x7fffffff));
    }

    private String htmlEncode(String text) {
        return text.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;");
    }

    private String getQueryValue(String url, String key) {
        Pattern p = Pattern.compile("(^|&|\\?|#)(" + key + ")=([^&#]*)(&|$|#)");
        Matcher m = p.matcher(url);
        if (m.find()) {
            return m.group(3);
        } else {
            return "";
        }
    }

    private boolean isSubDomain(String subDomain, String domain) {
        subDomain = "." + subDomain.replaceAll(":\\d+", "");
        domain = "." + domain.replaceAll(":\\d+", "");
        int index = subDomain.indexOf(domain);
        return (index > -1 && index + domain.length() == subDomain.length());
    }

    private String getCookie(String key) {
        String value = "";
        Cookie[] cookies = this.request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(key)) {
                    value = cookies[i].getValue();
                }
            }
        }
        return value;
    }

    private void setCookie(String key, String value) {
        this.setCookie(key, value, -1);
    }

    private void setCookie(String key, String value, int duration) {
        Cookie cookie = new Cookie(key, value);
        if (duration != -1) {
            cookie.setMaxAge(duration);
        }
        cookie.setPath("/");
        String hostName = this.request.getServerName();
        for (int i = 0; i < this.domainName.length; i++) {
            if (this.isSubDomain(hostName, this.domainName[i])) {
                hostName = this.domainName[i];
            }
        }
        cookie.setDomain(hostName);
        this.response.addCookie(cookie);
    }

    private String replaceSpecialChars(String text) {
        return text.replaceAll("'", "'0").replaceAll("\\*", "'1").replaceAll("!", "'2");
    }

    private boolean isIncludedInDomain(String hostName) {
        for (int i = 0; i < this.domainName.length; i++) {
            if (this.isSubDomain(hostName, this.domainName[i])) {
                return true;
            }
        }
        return false;
    }

    private int getSourceType(String path, String referer, long currentPageVisitTime, long lastPageVisitTime) {
        String pathHost = "";
        String refererHost = "";
        try {
            pathHost = (new URL(path)).getHost();
            refererHost = (new URL(referer)).getHost();
        } catch (Exception e) {}
        if (referer == "" || (this.isIncludedInDomain(pathHost) && this.isIncludedInDomain(refererHost))) {
            if (currentPageVisitTime - lastPageVisitTime > this.VISIT_DURATION) {
                return 1;
            } else {
                return 4;
            }
        } else {
            for (int i = 0; i < this.SEARCH_ENGINE_LIST.length; i++) {
                Pattern p = Pattern.compile("(^|\\.)" + this.SEARCH_ENGINE_LIST[i][1].replaceAll("\\.", "\\\\."));
                Matcher m = p.matcher(refererHost);
                if (m.find()) {
                    this.searchWord = this.getQueryValue(referer, this.SEARCH_ENGINE_LIST[i][2]);
                    if (this.searchWord != "" || this.SEARCH_ENGINE_LIST[i][0] == "2" || this.SEARCH_ENGINE_LIST[i][0] == "14" || this.SEARCH_ENGINE_LIST[i][0] == "17") {
                        this.searchEngine = this.SEARCH_ENGINE_LIST[i][0];
                        return 2;
                    }
                }
            }
            return 3;
        }
    }

    private String getPixelUrl() {
        String path = this.request.getRequestURL().toString();
        String query = this.request.getQueryString();
        if (path != null && query != null) {
            path = path + "?" + query;
        }
        if (path == null) {
            path = "";
        }

        String referer = this.request.getHeader("referer");
        if (referer == null) {
            referer = "";
        }

        long currentPageVisitTime = System.currentTimeMillis() / 1000;

        String lpvtCookie = this.getCookie("Hm_lpvt_" + this.siteId);
        long lastPageVisitTime = lpvtCookie != "" ? Long.parseLong(lpvtCookie) : 0;

        String lastVisitTime = this.getCookie("Hm_lvt_" + this.siteId);

        int sourceType = this.getSourceType(path, referer, currentPageVisitTime, lastPageVisitTime);
        int isNewVisit = (sourceType == 4) ? 0 : 1;

        this.setCookie("Hm_lpvt_" + this.siteId, "" + currentPageVisitTime);
        this.setCookie("Hm_lvt_" + this.siteId, "" + currentPageVisitTime, this.VISITOR_MAX_AGE);

        String pixelUrl = "";
        try {
            pixelUrl = "http://hm.baidu.com/hm.gif" +
                "?si=" + this.siteId +
                "&et=" + this.eventType +
                (this.eventProperty != "" ? "&ep=" + URLEncoder.encode(this.eventProperty, "UTF-8") : "") +
                "&nv=" + isNewVisit +
                "&st=" + sourceType +
                (this.searchEngine != "" ? "&se=" + this.searchEngine : "") +
                (this.searchWord != "" ? "&sw=" + URLEncoder.encode(this.searchWord, "UTF-8") : "") +
                (lastVisitTime != "" ? "&lt=" + lastVisitTime : "") +
                (referer != "" ? "&su=" + URLEncoder.encode(referer, "UTF-8") : "") +
                (this.visitUrl != "" ? "&u=" + URLEncoder.encode(this.visitUrl, "UTF-8") : "") +
                "&v=" + this.VERSION +
                "&rnd=" + this.getRandomNumber();

            String[] adTags = {"cm", "cp", "cw", "ci", "cf"};
            String[] adKeys = {"hmmd", "hmpl", "hmkw", "hmci", "hmsr"};
            for (int i = 0; i < adKeys.length; i++) {
                String value = this.getQueryValue(path, adKeys[i]);
                if (value != "") {
                    pixelUrl += "&" + adTags[i] + "=" + URLEncoder.encode(value, "UTF-8");
                }
            }
        } catch (Exception e) {}

        return this.htmlEncode(pixelUrl);
    }

    public MobileBaiDuExpand(String siteId) {
        this.siteId = siteId;
    }

    public void setAccount(String siteId) {
        this.siteId = siteId;
    }

    public void setDomainName(String... domainName) {
        this.domainName = domainName;
    }

    public void setHttpServletObjects(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public String trackPageview() {
        return this.trackPageview("");
    }

    public String trackPageview(String url) {
        this.eventType = 0;
        this.eventProperty = "";

        String baseUrl = this.request.getScheme() + "://" + this.request.getServerName();
        if (this.request.getServerPort() != 80 && this.request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }

        if (url.indexOf("/") == 0) {
            this.visitUrl = baseUrl + url;
        } else {
            this.visitUrl = "";
        }

        return getPixelUrl();
    }

    public String trackEvent(String category, String action) {
        return this.trackEvent(category, action, "", "");
    }

    public String trackEvent(String category, String action, String label) {
        return this.trackEvent(category, action, label, "");
    }

    public String trackEvent(String category, String action, String label, String value) {
        this.eventType = 4;
        this.eventProperty = this.replaceSpecialChars(category) +
            "*" + this.replaceSpecialChars(action) +
            (label != "" ? "*" + this.replaceSpecialChars(label) : "") +
            (value != "" ? "*" + this.replaceSpecialChars(value) : "");
        this.visitUrl = "";
        return getPixelUrl();
    }
}
