package com.lvmama.businesses.review.util;
import java.io.File;
import java.util.Date;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 内容审核工具
 * @author nixianjun
 *
 */
public class KeyWordUtils {
    /**
     * 导出keyWord关键字列表
     */
    public static final String KEYWORDDOWNLOADTEMPLATE="/WEB-INF/pages/web/keyWordTemplate.xlsx";
    
    public static final String SENDEMAIL_TOADDRESS="zhongshuangxi@lvmama.com";
    public static final String SENDEMAIL_FROMADDRESS="";
    public static final String SENDEMAIL_FROMNAME="后台系统";
    public static final String SENDEMAIL_SENDSTATUS="UNSEND";
    
    public static final String BUSSINESS_PAGE_SIZE_="bussiness_page_size_";
    public static final String BBSCONTENT="bbsContent";
	public static final String BBSSUBJECT = "bbsSubject";

	public static final String CMTCOMMENT = "cmtComment";

	public static final String CMTREPLY = "cmtReply";

	public static final String GLARTICLE = "glarticle";

	public static final String GLCOMMENT = "glcomment";

	public static final String PHPCMSCONTENT = "phpcmscontent";

	public static final String PHPCMSCOMMENT = "phpcmscomment";
    
    
    public static String writeExcelByjXls(Map<String,Object> beans,String template){
        try {
        File templateResource = ResourceUtil.getResourceFile(template);
        XLSTransformer transformer = new XLSTransformer();
        String destFileName = Constant.getTempDir() + "/excel"+new Date().getTime();
        transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName);
        return destFileName;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
