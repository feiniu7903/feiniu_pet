package com.lvmama.businesses.sweb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.businesses.review.util.KeyWordUtils;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.businesses.po.review.KeyWord;
import com.lvmama.comm.businesses.service.review.BbsService;
import com.lvmama.comm.businesses.service.review.GuideService;
import com.lvmama.comm.businesses.service.review.InfonewsService;
import com.lvmama.comm.pet.service.review.KeyWordService;
import com.lvmama.comm.utils.DateUtil;

@Results( {
    @Result(name = "keyword_list", location = "/WEB-INF/pages/web/review/keyword_list.jsp"),
    @Result(name = "keyword_edit", location = "/WEB-INF/pages/web/review/keyword_edit.jsp"),
    @Result(name = "go_keyword_list",type="redirect", location ="/keyword/keyWordList.do" )
})
public class KeyWordAction extends BackBaseAction{
    private static final long serialVersionUID = 1L;
    private static final Log log = LogFactory.getLog(KeyWordAction.class);
    private String startDate ;
    private String endDate;
    private String kContent;
    private int pageSize; 
    private String keyWordLists="";
    private String keyWordIdLists = "";
    private int keyId;
    
    private KeyWord keyWord;
    private KeyWordService keyWordService;
    private List<KeyWord> keyWordList;
    Boolean flag=false;//ajax返回值
    @Autowired
    private BbsService bbsService;
    @Autowired
    private GuideService guideService;
    @Autowired
    private InfonewsService infonewsService;
    /**
     *  list 页面
     */
    @Action("/keyword/keyWordList")
    public String execute() throws Exception{
        list();
        return "keyword_list";
    }
    
    /**
     * 批量保存
     * @return
     * @throws Exception
     */
    @Action("/keyword/keyWordSave")
    public String saveKeyWords() throws Exception{
        try{
            String[] keyWordContent = keyWordLists.split("\r\n");
            List<KeyWord> keyWordsList = new ArrayList<KeyWord>();
            KeyWord key ;
            for (int i = 0; i < keyWordContent.length; i++) {
                if(!"".endsWith(keyWordContent[i].trim())){
                    if(null != keyWordContent[i].split(",")){
                        String[] splitKeyWords = keyWordContent[i].split(",");
                        for (int j = 0; j < splitKeyWords.length; j++) {
                            key =new KeyWord();
                            key.setkContent(splitKeyWords[j]);
                            KeyWord keywordBack= keyWordService.querykeyWordByparam(key);
                            if(keywordBack==null){
                                keyWordsList.add(key);
                            }
                        }
                    }else{
                        key =new KeyWord();
                        key.setkContent(keyWordContent[i]);
                        KeyWord keywordBack= keyWordService.querykeyWordByparam(key);
                         if(keywordBack==null){
                        	keyWordsList.add(key);
                        }
                    }
                }
            }
            keyWordService.batchInsert(keyWordsList);
            /**
             * 同步到论坛 攻略，资讯
             */
            bbsService.reviewKeywordSynInset(keyWordsList);
            
        }catch(Exception e){
            log.error(e);
        }
        return "go_keyword_list";
    }
    
    /**
     * 去修改页面
     * @return
     * @throws Exception
     */
    @Action("/keyword/goUpdatekeyWord")
    public String goUpdateKeyWord() throws Exception{
        if(!"".equals(keyId)){
            keyWord = keyWordService.queryKeyWordByKeyID(keyId);
            return "keyword_edit";
        }
        return "go_keyword_list";
    }
    
    /**
     * 处理修改请求
     * @return
     * @throws Exception
     */
    @Action("/keyword/doUpdatekeyWord")
    public String doUpdateKeyWord() throws Exception{
        try{
        keyWord = keyWordService.queryKeyWordByKeyID(keyId);
        String oldContent=keyWord.getkContent();
        keyWord.setkContent(kContent);
        keyWordService.updateKeyWord(keyWord);
        /**同步 bbs,攻略，资讯**/
        bbsService.synKeyWordUpdate(oldContent,kContent);
        kContent="";
        }catch(Exception e){
            log.error(e);
        }
        return "go_keyword_list";
    }
    
    /**
     * 确定删除
     * @return
     * @throws Exception
     */
    @Action("/keyword/doDeleteKeyWord")
    public String doDelteKeyWord() throws Exception{
        try{
        keyWord = keyWordService.queryKeyWordByKeyID(keyId);
        String content=keyWord.getkContent();
        keyWordService.deleteByKeyTarget(keyWord);
        /**同步 bbs,攻略，资讯**/
        bbsService.synDeleteBykeyWord(content);
        }catch(Exception e){
            log.error(e);
        }
        return "go_keyword_list";
    }
    
    /**
     * 选中批量删除
     * @return
     * @throws Exception
     */
    @Action("/keyword/batchDeleteKeyWord")
    public String doBatchDelteKeyWord() throws Exception{
        try{
        String[] keyWordIds = keyWordIdLists.split(",");
        List<KeyWord> keyWordsList = new ArrayList<KeyWord>();
        KeyWord key ;
        for (int i = 0; i < keyWordIds.length; i++) {
            key =keyWordService.queryKeyWordByKeyID(Long.valueOf(keyWordIds[i]));
            keyWordsList.add(key);
        }
        keyWordService.batchDeleteKeyWord(keyWordsList);
        /**同步 bbs,攻略，资讯**/
        bbsService.synBatchDeleteKeyWord(keyWordsList);
        }catch(Exception e){
            log.error(e);
        }
        return "go_keyword_list";
    }
    
    /**
     * 导出elx表格
     */
    @Action("/keyword/writeExportExcelData")
    public void writeExportExcelData() {
        keyWordList=keyWordService.batchQueryKeyWordByParam(null);
        if (null != keyWordList) {
            Map<String, Object> beans = new HashMap<String, Object>();
            beans.put("keyWordList", keyWordList);
            String excelTemplate = KeyWordUtils.KEYWORDDOWNLOADTEMPLATE;
            String destFileName = KeyWordUtils.writeExcelByjXls(beans,
                    excelTemplate);
            super.writeAttachment(destFileName, "DownLoadKeyWord"+DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
        }

    }
    
    /**
     * initPage
     */
    protected void list(){
        if(0==pageSize)pageSize=10;
        pagination=initPage();
        pagination.setPageSize(pageSize);
        Map<String,Object> param=builderParam();
        pagination.setTotalResultSize(keyWordService.getCountKeyWordListByParam(param));
        if(pagination.getTotalResultSize()>0){
            param.put("startRows", pagination.getStartRows());
            param.put("endRows", pagination.getEndRows());
            keyWordList=keyWordService.batchQueryKeyWordByParam(param);
            kContent =(String) param.get("kContent");
        }
        pagination.buildUrl(getRequest());
    }
    
    protected Map<String, Object> builderParam(){
        Map<String, Object> param = new HashMap<String, Object>();
        
        if(null != kContent && !"".equals(kContent)){
            param.put("kContent",kContent);
        }
        if(null != startDate &&!"".equals(startDate)){
            param.put("startDate",convertToDate(startDate+" 00:00:00"));
        }
        if(null !=  endDate &&!"".equals(endDate)){
            param.put("endDate",convertToDate(endDate+" 23:59:59"));
        }
        
        return param;
    }
    
    private Date convertToDate(String dateStr){
        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
    /**
     * Getter/Setter ----------------------------
     */

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getKContent() {
        return kContent;
    }

    public void setKContent(String kContent) {
        this.kContent = kContent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyWordLists() {
        return keyWordLists;
    }

    public void setKeyWordLists(String keyWordLists) {
        this.keyWordLists = keyWordLists;
    }

    public String getKeyWordIdLists() {
        return keyWordIdLists;
    }

    public void setKeyWordIdLists(String keyWordIdLists) {
        this.keyWordIdLists = keyWordIdLists;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public KeyWord getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(KeyWord keyWord) {
        this.keyWord = keyWord;
    }

    public KeyWordService getKeyWordService() {
        return keyWordService;
    }

    public void setKeyWordService(KeyWordService keyWordService) {
        this.keyWordService = keyWordService;
    }

    public List<KeyWord> getKeyWordList() {
        return keyWordList;
    }

    public void setKeyWordList(List<KeyWord> keyWordList) {
        this.keyWordList = keyWordList;
    }

}
