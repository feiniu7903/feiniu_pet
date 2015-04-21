package com.lvmama.operate.job;
/**
 * 根据前一天的订单查找订单关联产品所在的城市攻略，根据订单中的下单人，联系人邮箱发送攻略
 * 尚正元
 * 2011-12-06
 */
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.edm.EdmOrderPlaceGuide;
import com.lvmama.comm.pet.po.edm.EdmSubscribeBatch;
import com.lvmama.comm.vo.Constant;
import com.lvmama.operate.job.model.UserEmailModel;
import com.lvmama.operate.service.EdmSubscribeBatchService;
import com.lvmama.operate.service.OnlineMarketingService;
import com.lvmama.operate.util.FtpUtil;
import com.lvmama.operate.util.upload.UploadFileCtrl;
 /**.
 * 发送攻略
 * @author shangzhengyuan
 */
public class EdmSendGuideJob {
    /**.
     * 记录日志
     */
    private static Log log = LogFactory.getLog(EdmSendGuideJob.class);
    /**.
     * 查询订单与EMAIL，景区对应关系
     */
    private OnlineMarketingService onlineMarketingService;
    /**.
     * EDM服务
     */
    private EdmSubscribeBatchService edmSubscribeBatchService;
    /**.
     * quatz运行方法
     */
    public void run() {
        if (Constant.getInstance().isJobRunnable()) {
            //1.取得目的地，邮箱列表
            List<EdmOrderPlaceGuide>  list = onlineMarketingService.getPlaceEmail();
            //2.对根据目的地对邮箱进行分组
            Map<String,List<String>> map = new HashMap<String,List<String>>();
            for(EdmOrderPlaceGuide placeEmail:list){
                String placeId = placeEmail.getPlaceId();
                String placeName = placeEmail.getPlaceName();
                String email = placeEmail.getEmail();
                List<String> subList;
                if(null!=map.get(placeId+"="+placeName)){
                    subList = map.get(placeId+"="+placeName);
                }else{
                    subList = new ArrayList<String>();
                    map.put(placeId+"="+placeName, subList);
                }
                subList.add(email);
            }
            //3.根据目的地发送邮件
            if(!map.keySet().isEmpty()){
                UploadFileCtrl uc = new UploadFileCtrl();
                Iterator<String> iterator = map.keySet().iterator();
                //用城市分组邮箱发送邮件
                while(iterator.hasNext()){
                    String place = iterator.next();
                    //创建邮箱地址内容
                    List<String> subList = map.get(place);
                    String[] subArray = place.split("=");
                    String rn = "\r\n";
                    StringBuffer sb = new StringBuffer("emailAddr\r\n");
                    for(String email:subList){
                        sb.append(email).append(rn);
                    }
                    //上传邮箱地址文件
                    File receiveEmail=uc.writeFile(sb.toString().getBytes(), subArray[0]+".txt",".txt");
                    String fileurl = receiveEmail.getAbsolutePath();
                    String url =(new FtpUtil()).uploadFile(fileurl);
                    if(null==url){
                        log.warn("根据订单发送目的攻略邮件 文件没有上传成功  " + (subArray.length>1?subArray[1]:"")+" 邮箱列表 "+sb.toString());
                    }
                    String contentUrl=Constant.getInstance().getGuideEdmUrl();
                    //创建发送批次
                    EdmSubscribeBatch edmBatch = getEdmSubscribeBatch((subArray.length>1?subArray[1]:""),url,contentUrl+subArray[0]+".html");
                    try{
                    	UserEmailModel userEmailModel = new UserEmailModel();
                    	String [] emaisl = edmBatch.getReceiveEmailContent().split(";");
                    	for (String string : emaisl) {
                    		userEmailModel.getUserEmailList().add(string);
                    	}
                    	userEmailModel.setTaskName("攻略任务");
                    	userEmailModel.setTemplateName("攻略模板");
                    	userEmailModel.getColumns().add("email");
                        //发送并保存信息
                        edmSubscribeBatchService.insert(edmBatch,userEmailModel,false);
                        Thread.sleep(10000);
                    }catch(Exception e){
                        log.warn("根据订单发送目的攻略邮件出错:"+e);
                    }
                }
            }
        }
    }
    /**
     * 创建一条发送批次
     * @param placeName 目的地名称
     * @param recerveEmailUrl 发送邮箱文件地址
     * @param emailContentUrl 邮件模板地址
     * @return
     */
    private EdmSubscribeBatch getEdmSubscribeBatch(final String placeName,final String recerveEmailUrl,final String emailContentUrl){
        EdmSubscribeBatch edmBatch = new EdmSubscribeBatch();
        edmBatch.setSendUserEmail(com.lvmama.operate.util.Constant.DEFAULT_SEND_EMAIL);
        edmBatch.setSendUserId(com.lvmama.operate.util.Constant.DEFAULT_SEND_USER);
        edmBatch.setEmailSubject("感谢您订购驴妈妈"+placeName+"产品，小编深度采编"+placeName+"旅游攻略奉上"); //标题
        edmBatch.setReceiveEmailUrl(recerveEmailUrl);//收件人邮箱文件地址
        edmBatch.setEmailContentUrl(emailContentUrl);//邮件模板文件地址
        edmBatch.setEmailType("DESTINATION_GUIDE_EMAIL");//邮件类型(目的地攻略)
        edmBatch.setEmailSendType("0");//发送类型(立即发送)
        edmBatch.setSendType("0");//通道类型(常规通道)
        return edmBatch;
    }
    public OnlineMarketingService getOnlineMarketingService() {
        return onlineMarketingService;
    }
    public void setOnlineMarketingService(
            OnlineMarketingService onlineMarketingService) {
        this.onlineMarketingService = onlineMarketingService;
    }
    public EdmSubscribeBatchService getEdmSubscribeBatchService() {
        return edmSubscribeBatchService;
    }
    public void setEdmSubscribeBatchService(
            EdmSubscribeBatchService edmSubscribeBatchService) {
        this.edmSubscribeBatchService = edmSubscribeBatchService;
    }
}
