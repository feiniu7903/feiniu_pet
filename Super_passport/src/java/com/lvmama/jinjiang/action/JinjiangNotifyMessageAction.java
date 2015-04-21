/**
 *
 */
package com.lvmama.jinjiang.action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.jinjiang.JinjiangClient;
import com.lvmama.jinjiang.Request;
import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.comm.JinjiangUtil;
import com.lvmama.jinjiang.model.request.NotifyCancelOrderRequest;
import com.lvmama.jinjiang.model.response.NotifyCancelOrderResponse;
import com.lvmama.jinjiang.service.JinjiangOrderService;
import com.lvmama.jinjiang.service.JinjiangProductService;
import com.lvmama.jinjiang.vo.order.OrderMessage;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;

/**
 *
 */
public class JinjiangNotifyMessageAction extends BaseAction{
    private final static Log LOG=LogFactory.getLog(JinjiangNotifyMessageAction.class);

    private static final long serialVersionUID = 2789214759578257993L;
    private JinjiangOrderService jinjiangOrderService;
    private JinjiangProductService jinjiangProductService;
    private String productId;

    /**
     * 初始化json Header Content-Type=application/json
     * @return  资料解码
     * @throws IOException
     */
    private String initRequest() throws IOException{
        // 读取请求内容
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(getRequest().getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        return sb.toString();
    }

    @Action("/jinjiang/notifyCancelOrder")
    public void notifyCancelOrder(){
        NotifyCancelOrderResponse res = new NotifyCancelOrderResponse("0000","处理成功");
        try {
            String request = this.initRequest();
            LOG.info("request= " + request);
            JSONObject jsonObject = JSONObject.fromObject(request);
            NotifyCancelOrderRequest req = (NotifyCancelOrderRequest)JSONObject.toBean(jsonObject, NotifyCancelOrderRequest.class);
            //验证密文
            if(validCheck(req)){
                OrderMessage mes = jinjiangOrderService.notifyCancelOrder(req);
                res.setErrorcode(mes.getCode());
                res.setErrormessage(mes.getMessage());
            }else {
                res.setErrorcode("0002");
                res.setErrormessage("密文验证失败");
            }

        } catch (Exception e) {
            res.setErrorcode("0002");
            res.setErrormessage("数据校验不通过！");
            LOG.info("锦江通知取消异常： " + e.getMessage());
        }
        //返回请求
        this.responseJson(res);
    }

    /**
     * 更新供应商产品入库
     */
    @Action("/jinjiang/saveTempStockProduct")
    public void saveTempStockProduct(){
        try {
            Date dateTime = parseDate(this.getRequest().getParameter("startTime"));
            if (dateTime != null) {
                LOG.info("saveTempStockProduct startTime:" + DateUtil.formatDate(dateTime, "yyyy-MM-dd"));
                jinjiangProductService.putSycTime(JinjiangClient.keyLine, dateTime);
            }
            jinjiangProductService.saveTempStockProduct();
        } catch (Exception e) {
            LOG.error("JinjiangSaveTempStockProduct Exception:",e);
        }
    }

    /**
     * 更新单个产品
     */
    @Action("/jinjiang/updateProduct")
    public void updateProduct(){
        Date updateTimeEnd = new Date();
        Date updateTimeStart = DateUtils.addDays(updateTimeEnd,-1);
        try {
            jinjiangProductService.updateProductStocked(productId, updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            LOG.error("JinjiangUpdateProduct Exception:",e);
        }
    }

    /**
     * 更新所有产品
     */
    @Action("/jinjiang/updateAllProduct")
    public void updateAllProduct(){
        Date updateTimeEnd = new Date();
        Date updateTimeStart = jinjiangProductService.getSycTime(JinjiangClient.keyLine);
        jinjiangProductService.putSycTime(JinjiangClient.keyLine,updateTimeEnd);
        try {
            jinjiangProductService.updateAllProductTimePrices(updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            LOG.error("JinjiangUpdateProduct Exception:",e);
        }
    }

    /**
     * 更新单个产品时间价格
     */
    @Action("/jinjiang/updateProdPrice")
    public void updateProdPrice(){
        Date updateTimeEnd = new Date();
        Date updateTimeStart = DateUtils.addDays(updateTimeEnd,-1);
        try {
            jinjiangProductService.updateProductStocked(productId, updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            LOG.error("JinjiangUpdateProduct Exception:",e);
        }
    }

    /**
     * 更新所有产品时间价格
     */
    @Action("/jinjiang/updateAllProdPrice")
    public void updateAllProdPrice(){
        Date updateTimeEnd = new Date();
        Date updateTimeStart = jinjiangProductService.getSycTime(JinjiangClient.keyPrice);
        jinjiangProductService.putSycTime(JinjiangClient.keyPrice,updateTimeEnd);
        try {
            jinjiangProductService.updateAllProductTimePrices(updateTimeStart, updateTimeEnd);
        } catch (Exception e) {
            LOG.error("JinjiangUpdateProduct Exception:",e);
        }
    }

    /**
     * 更新单个产品时间价格
     */
    @Action("/jinjiang/updateProductTimePrice")
    public void updateOneProdPrice(){
        String lineCode = getRequest().getParameter("lineCode");
        Date updateTimeStart = parseDate(getRequest().getParameter("startTime"));
        Date updateTimeEnd = parseDate(getRequest().getParameter("endTime"));
        if (StringUtils.isNotBlank(lineCode)) {
            try {
                if (updateTimeStart == null) {
                    updateTimeStart = jinjiangProductService.getSycTime(JinjiangClient.keyPrice);
                }
                if (updateTimeEnd == null) {
                    updateTimeEnd = new Date();
                }
                // 更新lineCode 时间价格
                jinjiangProductService.updateProductTimePrice(lineCode, updateTimeStart, updateTimeEnd);
            } catch (Exception e) {
                LOG.error("JinjiangUpdateProduct Exception:",e);
            }
        }
    }

    /**
     * 保存单个线路产品到临时表
     */
    @Action("/jinjiang/saveOneLineToTempStockProduct")
    public void saveOneLineToTempStockProduct(){
        String lineCode = getRequest().getParameter("lineCode");
        Date updateTimeStart = parseDate(getRequest().getParameter("startTime"));
        Date updateTimeEnd = parseDate(getRequest().getParameter("endTime"));
        if (StringUtils.isNotBlank(lineCode)) {
            try {
                if (updateTimeStart == null) {
                    updateTimeStart = jinjiangProductService.getSycTime(JinjiangClient.keyLine);
                }
                if (updateTimeEnd == null) {
                    updateTimeEnd = new Date();
                }
                // 更新lineCode 时间价格
                jinjiangProductService.saveTempStockProduct(lineCode, updateTimeStart, updateTimeEnd);
            } catch (Exception e) {
                LOG.error("JinjiangUpdateProduct Exception:",e);
            }
        }
    }

    private Date parseDate(String str) {
        if (StringUtils.isNotBlank(str)) {
            try {
                return DateUtils.parseDate(str, new String[]{"yyyy-MM-dd", "yyyy/MM/dd"});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 返回请求
     * @param response  Response
     */
    private void responseJson(Response response){

        this.getResponse().setContentType("application/json;charset=UTF-8");
        this.getResponse().setCharacterEncoding("UTF-8");
        JSONObject json = JSONObject.fromObject(response,JinjiangUtil.getJsonConfig());
        try {
            LOG.info("resjson= " + json.toString());
            PrintWriter out = this.getResponse().getWriter();
            out.write(json.toString());
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证密文
     * @param request
     * @return
     */
    private boolean validCheck(Request request){
        // 根据通道 + 时间戳 + 密钥
        String ciphertext = JinjiangUtil.ciphertextEncode(request.getChannelCode(), request.getTimestamp());
        if(ciphertext.equals(request.getCiphertext())){
            return true;
        }
        return false;
    }
    public void setJinjiangOrderService(JinjiangOrderService jinjiangOrderService) {
        this.jinjiangOrderService = jinjiangOrderService;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public void setJinjiangProductService(JinjiangProductService jinjiangProductService) {
        this.jinjiangProductService = jinjiangProductService;
    }


}
