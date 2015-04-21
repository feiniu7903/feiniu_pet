package com.lvmama.comm.bee.service.tmall;

import java.util.Date;
import java.util.List;

import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.domain.PurchaseOrder;


public class TOPInterface {
    private static final Log log = LogFactory.getLog(TOPInterface.class);
    private static final String SESSIONKEY="610272786b06d0da7282c90f5a289a74db41ebd6aed3b80490728022";  //TOP分配给用户的SessionKey，通过登陆授权获取,其值有个失效期，在失效前要重新请求,这个尚未确定
    private static final String APPKEY="21114511"; //TOP分配给应用的AppKey
    private static final String SECRET="5724dae251a15f4c4d2044c9b6dd5590";//TOP分配的加密密钥
    private static final String URL="http://gw.api.taobao.com/router/rest"; //淘宝接口调用地址

    private static final String SESSIONKEY_ECTIT="61000049423d1e8ec51251bc300ba75db8f9dadc83a09ee490728022";//电子凭证sessionkey
    private static final String APPKEY_ECTIT="21528321";//电子凭证appkey
    private static final String SECRET_ECTIT="2e51cb0e29ff01850ae72323d59b2bae";//电子凭证sercet
//	private static final String format="json";  //指定响应格式。默认xml,目前支持格式为xml,json
//	private static final String version="2.0";    //API协议版本，可选值:2.0。
//	private static final String sign_method="md5";   //参数的加密方法选择，可选值是：md5,hmac。这个参数只存在于2.0中

    //===========分销平台 Begin===========
    //private static final String DISTRIBUTOR_SESSIONKEY="6101515145347c4211016341bb2932b23a658648c479dea490728022";
    //private static final String DISTRIBUTOR_APPKEY="21562226";
    //private static final String DISTRIBUTOR_SECRET="0dc647f8d471cb775188f417972b2a95";
    //private static TaobaoClient distributorClient=new DefaultTaobaoClient(URL, DISTRIBUTOR_APPKEY, DISTRIBUTOR_SECRET);
    //===========分销平台 End  ===========
	
	/*   驴妈妈门票接口         */
//	private static String APPKEY_LVMAMA = "21625323";
//	private static String APPSECRET_LVMAMA = "9a8c03550eed382b8bcf4bd3ac14745c";
//	private static String SESSIONKEY_LVMAMA = "6100503ced013a8d12cf0079f66ae4328f5333e9d23cceb490728022";

//	private static TaobaoClient clientLvmama = new DefaultTaobaoClient(URL, APPKEY_LVMAMA, APPSECRET_LVMAMA);

    private static TaobaoClient client=new DefaultTaobaoClient(URL, APPKEY, SECRET);   //获得淘宝调用客户端对象

    private static TaobaoClient cilent_etict=new DefaultTaobaoClient(URL, APPKEY_ECTIT, SECRET_ECTIT);

    /****
     * 根据交易编号 获取单笔交易的详细信息 【TOP】
     * @param tid  交易编号
     * @return 交易详情   JSON类型的字符串
     *
     */
    public static TradeFullinfoGetResponse getFullIfo(String tid) throws Exception{
        String fields="seller_nick,buyer_nick,title,type,created,sid,tid,seller_flag,seller_memo,seller_rate,buyer_rate,status,payment,discount_fee,promotion_details,adjust_fee,post_fee,total_fee,pay_time,end_time,alipay_no,modified,consign_time,buyer_obtain_point_fee,point_fee,real_point_fee,received_payment,commission_fee,pic_path,num_iid,num_iid,num,price,cod_fee,cod_status,shipping_type,receiver_name,receiver_state,receiver_city,receiver_district,receiver_address,receiver_zip,receiver_mobile,receiver_phone,orders.title,orders.pic_path,orders.price,orders.num,orders.iid,orders.num_iid,orders.sku_id,orders.refund_status,orders.status,orders.oid,orders.total_fee,orders.payment,orders.discount_fee,orders.adjust_fee,orders.sku_properties_name,orders.item_meal_name,orders.buyer_rate,orders.seller_rate,orders.outer_iid,orders.outer_sku_id,orders.ticket_outer_id,orders.refund_id,orders.seller_type,eticket_ext";
        TradeFullinfoGetResponse response=null;
        TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
        req.setFields(fields);
        req.setTid(Long.valueOf(tid));
        response = client.execute(req , SESSIONKEY);
        System.out.println(response.getBody());
        return response;
    }
    /***
     * 搜索当前会话用户作为卖家已卖出的交易数据 （只能获取到三个月以内的交易信息） 【TOP】
     * @param startDateTime    查询三个月内交易创建时间开始。格式:yyyy-MM-dd HH:mm:ss
     * @param endDateTime      查询交易创建时间结束。格式:yyyy-MM-dd HH:mm:ss
     * @param status 交易状态 ,可选值: WAIT_BUYER_PAY(等待买家付款) WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)....
     * @return   查询条件的交易数据  JSON字符串格式
     */
    public static String getTradesSold(Date startDateTime,Date endDateTime,long pageNum,String status){
        TradesSoldGetRequest req=new TradesSoldGetRequest();
        req.setFields("buyer_nick,receiver_mobile,seller_flag,tid,orders.price,orders.outer_iid,orders.outer_sku_id,orders.ticket_outer_id,orders.status,orders.sku_properties_name");
        String res_json;
        req.setStartCreated(startDateTime);
        req.setEndCreated(endDateTime);
        req.setStatus(status);
        req.setPageNo(pageNum);
        req.setPageSize(100L);
        try {
            TradesSoldGetResponse response = client.execute(req , SESSIONKEY);
            if(response!=null && response.getErrorCode()!=null){
                log.error(response.getErrorCode()+":"+response.getMsg()+response.getSubCode()+":"+response.getSubMsg());
                return  null;
            }
            res_json=response.getBody();
        } catch (Exception e) {
            log.error("getTradesSold ApiException:>>"+e);
            return null;
        }
        return res_json;
    }
    /***
     * 根据交易ID修改一笔交易备注    包括旗帜颜色和备注内容   【TOP】
     * @param tid   交易id
     * @param memo  设置的备注内容
     * @param flag  交易备注旗帜，可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)
     * @return   此笔交易的更新时间 和 tid   JSON字符串格式
     */
    public static String updateMemo(long tid, String memo, long flag){
        TradeMemoUpdateRequest req=new TradeMemoUpdateRequest();
        req.setTid(tid);
        req.setMemo(memo);
        req.setFlag(flag);
        req.setReset(false);
        String res_json="";
        try {
            TradeMemoUpdateResponse response = client.execute(req , SESSIONKEY);
            if(response.getErrorCode()!=null){
                log.error(tid+"updateMemo Error!!!!"+response.getErrorCode()+":"+response.getMsg()+response.getSubCode()+":"+response.getSubMsg());
                return  null;
            }
            res_json=response.getBody();
            System.out.println(res_json);
        } catch (ApiException e) {
            log.error(tid+"updateMemo ApiException:>>"+e);
            return null;
        }
        return res_json;
    }

    /***
     * 合作方在发码成功后回调该接口，淘宝电子凭证平台会更新订单状态
     * @param tid
     * @param code
     * @param token
     * @throws ApiException
     */
    public static VmarketEticketSendResponse eticketSend(Long tid,String code,String token) throws ApiException{
        VmarketEticketSendRequest req=new VmarketEticketSendRequest();
        req.setOrderId(tid);
        req.setVerifyCodes(code);
        req.setToken(token);
        VmarketEticketSendResponse response = cilent_etict.execute(req , SESSIONKEY_ECTIT);
        log.info(tid+" callback top eticket send interface's result: "+response.getSubCode()+" :"+response.getSubMsg());
        return response;
    }

    /***
     * 合作方正确收到通知后，如果重新发的码与之前不同或是重新生成的，请返回{"code":300},否则返回{"code":200}
     * 成功接收到重新发码通知并返回相应的返回结果后，准备好要重发的码后回调此接口, 回调成功后，再将相应的码发送到用户手机
     * @param tid
     * @param code
     * @param token
     * @throws ApiException
     */
    public static VmarketEticketResendResponse eticketReSend(Long tid,String code,String token) throws ApiException{
        VmarketEticketResendRequest req=new VmarketEticketResendRequest();
        req.setOrderId(tid);
        req.setVerifyCodes(code);
        req.setToken(token);
        VmarketEticketResendResponse response = cilent_etict.execute(req , SESSIONKEY_ECTIT);
        log.info(tid+" callback top eticket resend interface's result: "+response.getSubCode()+" :"+response.getSubMsg());
        return response;
    }

    /**
     * 用户在线下进行消费的时候，提供凭证码进行核销，驴妈妈在将订单设为履行状态后回调此接口通知淘宝电子凭证平台
     * @param tid
     * @param code
     * @param token
     * @throws ApiException
     */
    public static void eticketConsume(Long tid,String code,Long cousumeNum,String token) throws ApiException{
        VmarketEticketConsumeRequest req=new VmarketEticketConsumeRequest();
        req.setOrderId(tid);
        req.setVerifyCode(code);
        req.setConsumeNum(cousumeNum);
        req.setToken(token);
        req.setSerialNum(System.currentTimeMillis()+"");
        log.info(req.getSerialNum()+":>>>>>>>>>>>>>>>>>>>>>>>>>");
        VmarketEticketConsumeResponse response = cilent_etict.execute(req , SESSIONKEY_ECTIT);
        log.info(tid+" callback top eticket consume interface's result: "+response.getSubCode()+" :"+response.getSubMsg());
    }

    /**
     * 电子凭证冲正接口
     * @param tid
     * @param reverse_code
     * @param reverse_num
     * @param consume_secial_num
     * @param token
     * @return
     * @throws ApiException
     */
    public static VmarketEticketReverseResponse eticketReverse(Long tid,String reverse_code,Long reverse_num,String consume_secial_num,String token) throws ApiException{
        VmarketEticketReverseRequest req=new VmarketEticketReverseRequest();
        req.setOrderId(tid);
        req.setReverseCode(reverse_code);
        req.setReverseNum(reverse_num);
        req.setConsumeSecialNum(consume_secial_num);
        req.setToken(token);
        VmarketEticketReverseResponse response = cilent_etict.execute(req , SESSIONKEY_ECTIT);
        log.info(tid+" callback top eticket reverse interface's result: "+response.getSubCode()+" :"+response.getSubMsg());
        return response;
    }

    //===========分销平台 Begin===========
    /**
     *
     * @param startDateTime 起始时间 格式 yyyy-MM-dd HH:mm:ss.支持到秒的查询。
     * @param endDateTime 结束时间 格式 yyyy-MM-dd HH:mm:ss.支持到秒的查询。
     * @param pageNum 页码(大于0的整数。默认为1)
     * @param status 交易状态 WAIT_BUYER_CONFIRM_GOODS(已发货)
     * @return 根据查询条件查询出 PurchaseOrder
     */
    public static List<PurchaseOrder> getPurchaseOrderList(Date startDateTime,Date endDateTime,Long pageNum,String status,Long fenXiaoId){
        FenxiaoOrdersGetRequest req=new FenxiaoOrdersGetRequest();
        if(fenXiaoId!=null){
            req.setPurchaseOrderId(fenXiaoId);
        }else{
            req.setStartCreated(startDateTime);
            req.setEndCreated(endDateTime);
            req.setStatus(status);
            req.setPageNo(pageNum);
            req.setPageSize(50L);
        }
        //req.setFields("dealer_order_details.product_id");
        List<PurchaseOrder> purchaseOrderList=null;
        try {
            FenxiaoOrdersGetResponse  response = client.execute(req , SESSIONKEY);
            if(response!=null && response.getErrorCode()!=null){
                log.error(response.getErrorCode()+":"+response.getMsg()+response.getSubCode()+":"+response.getSubMsg());
                return  null;
            }
            purchaseOrderList=response.getPurchaseOrders();
        } catch (ApiException e) {
            log.error("getTradesSold ApiException:>>"+e);
            return null;
        }
        return purchaseOrderList;
    }
    /***
     * 根据交易ID修改一笔交易备注    包括旗帜颜色和备注内容   【TOP】
     * @param purchaseOrderId   交易id
     * @param memo  设置的备注内容
     * @param flag  交易备注旗帜，可选值为：0(灰色), 1(红色), 2(黄色), 3(绿色), 4(蓝色), 5(粉红色)
     * @return   此笔交易的更新时间 和 tid   JSON字符串格式
     */
    public static Boolean updateDistributorMemo(Long purchaseOrderId, String memo, Long flag){
        Boolean isSuccess=false;
        FenxiaoOrderRemarkUpdateRequest req=new FenxiaoOrderRemarkUpdateRequest();
        req.setPurchaseOrderId(purchaseOrderId);
        req.setSupplierMemo(memo);
        req.setSupplierMemoFlag(flag);
        try {
            FenxiaoOrderRemarkUpdateResponse response = client.execute(req , SESSIONKEY);
            if(response.getErrorCode()!=null){
                log.error(purchaseOrderId+"updateDistributorMemo Error!!!!"+response.getErrorCode()+":"+response.getMsg()+response.getSubCode()+":"+response.getSubMsg());
            }
            isSuccess=response.getIsSuccess();
        } catch (ApiException e) {
            log.error(purchaseOrderId+"updateDistributorMemo ApiException:>>"+e);
        }
        return isSuccess;

    }
    //===========分销平台 End  ===========


    public static boolean isDebug = false;
	
	/* ================ 淘宝商品同步接口  start ================*/


    /**
     * 获取标准类目属性值
     * taobao.itempropvalues.get
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.SbYt6t&path=cid:3-apiId:13
     */
    public static ItempropvaluesGetResponse findItempropvaluesGet(Long cid, String pvs, String fields, int timeoutNum) throws ApiException {
        ItempropvaluesGetRequest request = new ItempropvaluesGetRequest();
        if (StringUtils.isEmpty(fields)) {
            fields = "cid,pid,prop_name,vid,name,name_alias,status,sort_order,is_parent";
        }
        request.setFields(fields);
        request.setCid(cid);
        request.setPvs(pvs);
        request.setType(2L);
        request.setAttrKeys(null);
        ItempropvaluesGetResponse response;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            printTaobaoResponse(response);
        } catch (ApiException e) {
            //			e.printStackTrace();
            log.error("Find TaoBaoItems onsale error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findItempropvaluesGet(cid, pvs, fields, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }


    /**
     * 获取当前会话用户出售中的商品列表
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @return ItemsOnsaleGetResponse
     * @throws ApiException
     */
    public static ItemsOnsaleGetResponse findTaoBaoItemsOnsale(long pageNo, long pageSize) throws ApiException {
        return findTaoBaoItemsOnsale(pageNo, pageSize, null, null, null);
    }

    /**
     * 获取当前会话用户出售中的商品列表
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @param fields		查询字段
     * @return ItemsOnsaleGetResponse
     * @throws ApiException
     */
    public static ItemsOnsaleGetResponse findTaoBaoItemsOnsale(long pageNo, long pageSize, String fields) throws ApiException {
        return findTaoBaoItemsOnsale(pageNo, pageSize, null, null, fields);
    }

    /**
     * 获取当前会话用户出售中的商品列表
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @param startModified	商品起始修改时间
     * @param endModified	商品结束修改时间
     * @return  ItemsOnsaleGetResponse
     * @throws ApiException
     */
    public static ItemsOnsaleGetResponse findTaoBaoItemsOnsale(long pageNo, long pageSize, Date startModified, Date endModified, String fields) throws ApiException {
        return findTaoBaoItemsOnsale(pageNo, pageSize, startModified, endModified, fields, 3);
    }


    /**
     * taobao.items.onsale.get
     * 获取当前会话用户出售中的商品列表
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.SVEqeS&path=cid:4-apiId:18
     * 返回参数
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:63-apiId:18-invokePath:items
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @param startModified	商品起始修改时间
     * @param endModified	商品结束修改时间
     * @param fields	    查询字段
     * @param timeoutNum	超时次数
     * @return  ItemsOnsaleGetResponse
     * @throws ApiException
     */
    public static ItemsOnsaleGetResponse findTaoBaoItemsOnsale(long pageNo, long pageSize, Date startModified, Date endModified, String fields, int timeoutNum) throws ApiException {
        ItemsOnsaleGetRequest request = new ItemsOnsaleGetRequest();
        if (StringUtils.isEmpty(fields)) {
            fields = "approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id";
        }
        request.setFields(fields);
        request.setPageNo(pageNo);	// 默认为1，最大101
        request.setPageSize(pageSize);	//默认为40，最大200
        // 商品起始修改时间
        if (startModified != null) {
            request.setStartModified(startModified);
        }
        // 商品结束修改时间
        if (endModified != null) {
            request.setEndModified(endModified);
        }
        ItemsOnsaleGetResponse response;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find TaoBaoItems onsale error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findTaoBaoItemsOnsale(pageNo, pageSize, startModified, endModified, fields, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * 得到当前会话用户库存中的商品列表
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @return  ItemsInventoryGetResponse
     * @throws ApiException
     */
    public static ItemsInventoryGetResponse findTaoBaoItemsInventory(long pageNo, long pageSize) throws ApiException {
        return findTaoBaoItemsInventory(pageNo, pageSize, null, null, null);
    }

    /**
     * 得到当前会话用户库存中的商品列表
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @param fields	    查询字段
     * @return  ItemsInventoryGetResponse
     * @throws ApiException
     */
    public static ItemsInventoryGetResponse findTaoBaoItemsInventory(long pageNo, long pageSize, String fields) throws ApiException {
        return findTaoBaoItemsInventory(pageNo, pageSize, null, null, fields);
    }

    /**
     * 得到当前会话用户库存中的商品列表
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @param startModified	商品起始修改时间
     * @param endModified	商品结束修改时间
     * @param fields	    查询字段
     * @return  ItemsInventoryGetResponse
     * @throws ApiException
     */
    public static ItemsInventoryGetResponse findTaoBaoItemsInventory(long pageNo, long pageSize, Date startModified, Date endModified, String fields) throws ApiException {
        return findTaoBaoItemsInventory(pageNo, pageSize, startModified, endModified, fields, 3);
    }

    /**
     * taobao.items.inventory.get
     * 得到当前会话用户库存中的商品列表
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.Re5BpE&path=cid:4-apiId:162
     * 返回参数
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:63-apiId:162-invokePath:items
     *
     * @param pageNo		页码。取值范围:大于零小于等于101的整数;默认值为1
     * @param pageSize		每页条数。取值范围:大于零的整数;最大值：200；默认值：40
     * @param startModified	商品起始修改时间
     * @param endModified	商品结束修改时间
     * @param fields        查询字段
     * @param timeoutNum	超时次数
     * @return  ItemsInventoryGetResponse
     * @throws ApiException
     */
    public static ItemsInventoryGetResponse findTaoBaoItemsInventory(long pageNo, long pageSize, Date startModified, Date endModified, String fields, int timeoutNum) throws ApiException {
        ItemsInventoryGetRequest request = new ItemsInventoryGetRequest();
        if (StringUtils.isEmpty(fields)) {
            fields = "approve_status,num_iid,title,nick,type,cid,pic_url,num,props,valid_thru,list_time,price,has_discount,has_invoice,has_warranty,has_showcase,modified,delist_time,postage_id,seller_cids,outer_id";
        }
        request.setFields(fields);
        request.setPageNo(pageNo);	// 默认为1，最大101
        request.setPageSize(pageSize);	//默认为40，最大200
        // 商品起始修改时间
        if (startModified != null) {
            request.setStartModified(startModified);
        }
        // 商品结束修改时间
        if (endModified != null) {
            request.setEndModified(endModified);
        }
        ItemsInventoryGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find TaoBaoItems inventory error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findTaoBaoItemsInventory(pageNo, pageSize, startModified, endModified, fields, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * taobao.ticket.items.get
     * 批量获取新门票类目商品信息
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.u97AQK&path=cid:4-apiId:22591
     * 查询结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:21115-apiId:22591-invokePath:ticket_items
     *
     * @param itemIds	批量获取信息的商品标识。最多不能超过20个。
     * @return
     * @throws ApiException
     */
    public static TicketItemsGetResponse findTaobaoTicketItems(String itemIds) throws ApiException {
        return findTaobaoTicketItems(itemIds, 3);
    }

    /**
     * taobao.ticket.items.get
     * 批量获取新门票类目商品信息
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.u97AQK&path=cid:4-apiId:22591
     * 查询结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:21115-apiId:22591-invokePath:ticket_items
     *
     * @param itemIds	批量获取信息的商品标识。最多不能超过20个。
     * @param timeoutNum	超时次数
     * @return
     * @throws ApiException
     */
    public static TicketItemsGetResponse findTaobaoTicketItems(String itemIds, int timeoutNum) throws ApiException {
        TicketItemsGetRequest request = new TicketItemsGetRequest();
        request.setFields("num_iid,cat_id,product_id,title,auction_status,etc,skus");
        request.setItemIds(itemIds);
        TicketItemsGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find Taobao TicketItems error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findTaobaoTicketItems(itemIds, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * taobao.item.sku.delete
     * 删除SKU
     * http://api.taobao.com/apidoc/api.htm?path=cid:4-apiId:314
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:21116-apiId:22589-invokePath:ticket_item_process_result
     *
     * @param itemId		需要更新的门票商品标识（只支持门票二期商品）
     * @param properties	门票商品产品规格信息
     * @return  ItemSkuDeleteResponse
     * @throws ApiException
     */
    public static ItemSkuDeleteResponse ItemSkuDelete(Long itemId, String properties, int timeoutNum) throws ApiException {
        ItemSkuDeleteRequest request=new ItemSkuDeleteRequest();
        request.setNumIid(itemId);
        request.setProperties(properties);
        ItemSkuDeleteResponse response;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find Taobao TicketItems error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = ItemSkuDelete(itemId, properties, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }



    /**
     * taobao.ticket.item.update
     * 编辑新门票类目商品
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.RsBGKE&path=cid:4-apiId:22589
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:21116-apiId:22589-invokePath:ticket_item_process_result
     *
     * @param itemId		需要更新的门票商品标识（只支持门票二期商品）
     * @param skus			门票商品产品规格信息
     * @param auctionStatus	门票商品状态（onsale：上架，instock：仓库）
     * @return
     * @throws ApiException
     */
    public static TicketItemUpdateResponse updateTaobaoTicketTtem(Long itemId, String skus, String auctionStatus) throws ApiException {
        return updateTaobaoTicketTtem(itemId, skus, auctionStatus, 3);
    }

    /**
     * taobao.ticket.item.update
     * 编辑新门票类目商品
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.RsBGKE&path=cid:4-apiId:22589
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:4-dataStructId:21116-apiId:22589-invokePath:ticket_item_process_result
     *
     * @param itemId		需要更新的门票商品标识（只支持门票二期商品）
     * @param skus			门票商品产品规格信息
     * @param auctionStatus	门票商品状态（onsale：上架，instock：仓库）
     * @param timeoutNum	超时次数
     * @return
     * @throws ApiException
     */
    public static TicketItemUpdateResponse updateTaobaoTicketTtem(Long itemId, String skus, String auctionStatus, int timeoutNum) throws ApiException {
        TicketItemUpdateRequest request = new TicketItemUpdateRequest();
        request.setItemId(itemId);

        if (auctionStatus != null) {
            request.setAuctionStatus(auctionStatus);
        }
        if (skus != null) {
            request.setSkus(skus);
        }
        TicketItemUpdateResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Update TaobaoTicketTtem error!itemId=" + itemId + ", timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = updateTaobaoTicketTtem(itemId, skus, auctionStatus, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }


    /**
     * taobao.travel.items.get
     * 获取一个旅游度假线路商品接口
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.a5loQ9&path=cid:20280-apiId:21799
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:20280-dataStructId:20896-apiId:21799-invokePath:travel_items
     *
     * @param itemId	需要更新的门票商品标识
     * @return
     * @throws ApiException
     */
    public static TravelItemsGetResponse findTaobaoTravelItems(Long itemId) throws ApiException {
        return findTaobaoTravelItems(itemId, 3);
    }

    /**
     * taobao.travel.items.get
     * 获取一个旅游度假线路商品接口（超时重试）
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.a5loQ9&path=cid:20280-apiId:21799
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:20280-dataStructId:20896-apiId:21799-invokePath:travel_items
     *
     * @param itemId	需要更新的门票商品标识
     * @param timeoutNum	超时次数
     * @return  TravelItemsGetResponse
     * @throws ApiException
     */
    public static TravelItemsGetResponse findTaobaoTravelItems(Long itemId, int timeoutNum) throws ApiException {
        TravelItemsGetRequest request = new TravelItemsGetRequest();
        request.setItemId(itemId);

        TravelItemsGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find TaobaoTravelItems error! itemId=" + itemId + ", timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findTaobaoTravelItems(itemId, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * taobao.travel.items.update
     * 更新一个旅游度假线路商品接口
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.sv4rM8&path=cid:20280-apiId:21798
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:20280-dataStructId:20896-apiId:21798-invokePath:travel_items
     *
     * @param itemId				需要更新的门票商品标识
     * @param pidVid				要删除的pidVid
     * @param comboPriceCalendar	Json串，全量更新套餐价格日历信息（针对旅游度假线路的销售属性），定义了两个套餐日历价格
     * @param approveStatus			商品上传后的状态。可选值:onsale(出售中),instock(仓库中);默认值:onsale。
     * @param duration				最晚成团提前天数，最小0天，最大为300天。
     * @param outerId				设置供应商编码
     * @return  TravelItemsUpdateResponse
     * @throws ApiException
     */
    public static TravelItemsUpdateResponse updateTaobaoTravelItems(Long itemId, String pidVid, String comboPriceCalendar, String approveStatus, Long duration, String outerId) throws ApiException {
        return updateTaobaoTravelItems(itemId, pidVid, comboPriceCalendar, approveStatus, duration, outerId, 3);
    }

    /**
     * taobao.travel.items.update
     * 更新一个旅游度假线路商品接口
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.sv4rM8&path=cid:20280-apiId:21798
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:20280-dataStructId:20896-apiId:21798-invokePath:travel_items
     *
     * @param itemId				需要更新的门票商品标识
     * @param pidVid				要删除的pidVid
     * @param comboPriceCalendar	Json串，全量更新套餐价格日历信息（针对旅游度假线路的销售属性），定义了两个套餐日历价格
     * @param approveStatus			商品上传后的状态。可选值:onsale(出售中),instock(仓库中);默认值:onsale。
     * @param duration				最晚成团提前天数，最小0天，最大为300天。
     * @param outerId				设置供应商编码
     * @param timeoutNum			超时次数
     * @return  TravelItemsUpdateResponse
     * @throws ApiException
     */
    public static TravelItemsUpdateResponse updateTaobaoTravelItems(Long itemId, String pidVid, String comboPriceCalendar, String approveStatus, Long duration, String outerId, int timeoutNum) throws ApiException {
        TravelItemsUpdateRequest request = new TravelItemsUpdateRequest();
        request.setItemId(itemId);

        if (StringUtils.isNotBlank(pidVid)) {
            // 先删除，再添加
            request.setRemoveComboPriceCalendar(pidVid);
            if (comboPriceCalendar != null) {
                request.setAddComboPriceCalendar(comboPriceCalendar);
            }
        } else {
            if (comboPriceCalendar != null) {
                // 全量更新
                request.setComboPriceCalendar(comboPriceCalendar);
            }
        }
        if (approveStatus != null) {
            request.setApproveStatus(approveStatus);
        }
        if (duration != null) {
            request.setDuration(duration);
        }
        if (outerId != null) {
            request.setOuterId(outerId);
        }
        TravelItemsUpdateResponse response;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("update TaobaoTravelItems error! itemId=" + itemId + ", timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = updateTaobaoTravelItems(itemId, pidVid, comboPriceCalendar, approveStatus, duration, outerId, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }


    /**
     * 查询商家被授权品牌列表和类目列表
     * 其实就是一级目录
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.gccn0G&path=cid:3-apiId:161
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:71-apiId:161-invokePath:seller_authorize
     *
     * @return
     * @throws ApiException
     */
    public static ItemcatsAuthorizeGetResponse findItemcatsAuthorize() throws ApiException {
        return findItemcatsAuthorize(3);
    }

    /**
     * 查询商家被授权品牌列表和类目列表
     * 其实就是一级目录
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.gccn0G&path=cid:3-apiId:161
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:71-apiId:161-invokePath:seller_authorize
     *
     * @param timeoutNum			超时次数
     * @return
     * @throws ApiException
     */
    public static ItemcatsAuthorizeGetResponse findItemcatsAuthorize(int timeoutNum) throws ApiException {
        ItemcatsAuthorizeGetRequest request = new ItemcatsAuthorizeGetRequest();
        request.setFields("brand.vid,brand.name,item_cat.cid,item_cat.name,item_cat.status,item_cat.sort_order,item_cat.parent_cid,item_cat.is_parent");
        ItemcatsAuthorizeGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find ItemcatsAuthorize error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findItemcatsAuthorize(timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * 获取后台供卖家发布商品的标准商品类目
     * http://api.taobao.com/apidoc/api.htm?path=cid:3-apiId:122
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:45-apiId:122-invokePath:item_cats
     *
     * @param parentCid		父商品类目 id，0表示根节点, 传输该参数返回所有子类目。 (cids、parent_cid至少传一个)
     *						支持最大值为：9223372036854775807
     *						支持最小值为：0
     * @param cids			商品所属类目ID列表，用半角逗号(,)分隔 例如:(18957,19562,) (cids、parent_cid至少传一个)
     *						支持最大值为：9223372036854775807
     *						支持最小值为：0
     * @return
     * @throws ApiException
     */
    public static ItemcatsGetResponse findItemcats(Long parentCid, String cids) throws ApiException {
        return findItemcats(parentCid, cids, 3);
    }

    /**
     * 获取后台供卖家发布商品的标准商品类目
     * http://api.taobao.com/apidoc/api.htm?path=cid:3-apiId:122
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:45-apiId:122-invokePath:item_cats
     *
     * @param parentCid		父商品类目 id，0表示根节点, 传输该参数返回所有子类目。 (cids、parent_cid至少传一个)
     *						支持最大值为：9223372036854775807
     *						支持最小值为：0
     * @param cids			商品所属类目ID列表，用半角逗号(,)分隔 例如:(18957,19562,) (cids、parent_cid至少传一个)
     *						支持最大值为：9223372036854775807
     *						支持最小值为：0
     * @param timeoutNum	超时次数
     * @return
     * @throws ApiException
     */
    public static ItemcatsGetResponse findItemcats(Long parentCid, String cids, int timeoutNum) throws ApiException {
        ItemcatsGetRequest request = new ItemcatsGetRequest();
        request.setFields("cid,parent_cid,name,is_parent");
        if (parentCid != null) {
            request.setParentCid(parentCid);
        }
        if (cids != null) {
            request.setCids(cids);
        }
        ItemcatsGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find Itemcats error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findItemcats(parentCid, cids, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * 获取标准商品类目属性
     * http://api.taobao.com/apidoc/api.htm?path=cid:3-apiId:121
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:76-apiId:121-invokePath:item_props
     *
     * @param cid	叶子类目ID，如果只传cid，则只返回一级属性,通过taobao.itemcats.get获得叶子类目ID
     * @return
     * @throws ApiException
     */
    public static ItempropsGetResponse findItemprops(Long cid) throws ApiException {
        return findItemprops(cid, 3);
    }

    /**
     * 获取标准商品类目属性
     * http://api.taobao.com/apidoc/api.htm?path=cid:3-apiId:121
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:76-apiId:121-invokePath:item_props
     *
     * @param cid	叶子类目ID，如果只传cid，则只返回一级属性,通过taobao.itemcats.get获得叶子类目ID
     * @param timeoutNum 超时次数
     * @return
     * @throws ApiException
     */
    public static ItempropsGetResponse findItemprops(Long cid, int timeoutNum) throws ApiException {
        ItempropsGetRequest request = new ItempropsGetRequest();
        request.setFields("pid,name,must,multi,prop_values");
        request.setCid(cid);
        ItempropsGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find Itemprops error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findItemprops(cid, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    /**
     * 获取标准类目属性值
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.vMeMhb&path=cid:3-apiId:13
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:50-apiId:13-invokePath:prop_values
     *
     * @param cid			叶子类目ID ,通过taobao.itemcats.get获得叶子类目ID
     * @return
     * @throws ApiException
     */
    public static ItempropvaluesGetResponse findItempropvalues(Long cid) throws ApiException {
        return findItempropvalues(cid, 3);
    }

    /**
     * 获取标准类目属性值
     * http://api.taobao.com/apidoc/api.htm?spm=0.0.0.0.vMeMhb&path=cid:3-apiId:13
     * 返回结果
     * http://api.taobao.com/apidoc/dataStruct.htm?path=cid:3-dataStructId:50-apiId:13-invokePath:prop_values
     *
     * @param cid			叶子类目ID ,通过taobao.itemcats.get获得叶子类目ID
     * @param timeoutNum	超时次数
     * @return
     * @throws ApiException
     */
    public static ItempropvaluesGetResponse findItempropvalues(Long cid, int timeoutNum) throws ApiException {
        ItempropvaluesGetRequest request = new ItempropvaluesGetRequest();
        request.setFields("cid,pid,prop_name,vid,name,name_alias,status,sort_order");
        request.setCid(cid);
        ItempropvaluesGetResponse response = null;
        try {
            response = cilent_etict.execute(request, SESSIONKEY_ECTIT);
            // 打印消息
            printTaobaoResponse(response);
        } catch (ApiException e) {
//			e.printStackTrace();
            log.error("Find ItemcatsAuthorize error!timeoutNum=" + timeoutNum);
            if (timeoutNum > 0 && e.getMessage().contains("SocketTimeoutException")) {
                timeoutNum--;
                response = findItempropvalues(cid, timeoutNum);
            } else {
                throw e;
            }
        }
        return response;
    }

    public static void printTaobaoResponse(TaobaoResponse response) {
        if (isDebug) {
            StringBuilder sb = new StringBuilder();
            sb.append("IsSuccess ----" + response.isSuccess());
            sb.append(", ");
            sb.append("IsSuccess ----" + response.isSuccess());
            sb.append(", ");
            sb.append("ErrorCode ----" + response.getErrorCode());
            sb.append(", ");
            sb.append("Msg ----" + response.getMsg());
            sb.append(", ");
            sb.append("SubCode ----" + response.getSubCode());
            sb.append(", ");
            sb.append("SubMsg ----" + response.getSubMsg());
            sb.append(", ");
            sb.append("Params ----" + response.getParams());
            sb.append(", ");
            sb.append("TopForbiddenFields ----" + response.getTopForbiddenFields());
            sb.append(", ");
            sb.append("Body ----" + response.getBody());
            System.out.println(sb.toString());
        }
    }
	/* ================ 淘宝商品同步接口  end ================*/


    public static void main(String[] args) throws Exception {
//		Date dsDate=DateUtil.dsDay_Date(new Date(), -6);
//		Long pageNum=0L;
//		List<PurchaseOrder> purchaseOrderList=TOPInterface.getPurchaseOrderList(dsDate, new Date(),pageNum, "WAIT_BUYER_CONFIRM_GOODS",null);
//		System.out.println("============>Begin:");
//		if(purchaseOrderList!=null && purchaseOrderList.size()>0){
//			System.out.println("============>purchaseOrderList:"+purchaseOrderList.size()+"=====");
//			for(PurchaseOrder purchaseOrder:purchaseOrderList){
//			//	System.out.println("purchaseOrder FenXiaoId:============>"+purchaseOrder.getFenxiaoId());
//				//System.out.println("purchaseOrder FenXiaoId:============>"+purchaseOrder.getSnapshotUrl());
//				for(SubPurchaseOrder subPurchaseOrder:purchaseOrder.getSubPurchaseOrders()){
//					//System.out.println("purchaseOrder FenXiaoId:============>"+subPurchaseOrder.getFenxiaoId());
//					System.out.println("purchaseOrder FenXiaoId:============>"+subPurchaseOrder.getSnapshotUrl());
//				}
//			}
//		}
//		System.out.println("============>End:");

//		getFullIfo("461832505651894");
        //updateMemo(461707818031894L,"",2L);
    }
}
