package com.lvmama.passport.processor.impl.client.jiuwang.help;

import com.kayak.telpay.mpi.util.StringUtils;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.passport.processor.impl.client.jiuwang.client.v1.asOTA.OTAClientV1;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.request.CreateOrderRequestBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.request.CreatePaymentOrderRequestBody;
import com.lvmama.passport.processor.impl.client.jiuwang.model.v1.response.GetProductByOTAResponseBody;
import com.lvmama.passport.processor.impl.util.OrderUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建订单帮助类
 * Created by linkai on 2014/5/7.
 */
public class JiuwangCreateOrderHelp {
    private OrdOrder ordOrder;
    private PassCode passCode;

    private OrdOrderItemMeta itemMeta;
    private OrdPerson contact;
    private GetProductByOTAResponseBody.ProductInfos.ProductInfo productInfo;

    // 采购产品ID 对应的 退款手续费比例
    private static Map<Long, Double> REFUNDCHARGE_MAP;
    static {
        REFUNDCHARGE_MAP = new HashMap<Long, Double>();
        // 九寨沟观光车的采购ID， 手续费是 分 为单位(手续费为 2%)
        REFUNDCHARGE_MAP.put(91786L, 0.02);
        // 九寨沟门票采购ID， 手续费是 分 为单位(手续费为 2%)
        REFUNDCHARGE_MAP.put(84833L, 0.02);
        // 测试环境上的，采购产品ID
        REFUNDCHARGE_MAP.put(52995L, 0.02);
    }

    // 供应商产品ID 对应的 退款手续费比例
    private static Map<String, Double> REFUNDCHARGE_RESOURCEID_MAP;
    static {
        REFUNDCHARGE_RESOURCEID_MAP = new HashMap<String, Double>();
        // 手续费是 分 为单位(手续费为 2%)
//        REFUNDCHARGE_RESOURCEID_MAP.put("428", 0.02);
//        REFUNDCHARGE_RESOURCEID_MAP.put("2711", 0.02);
    }

    public JiuwangCreateOrderHelp(OrdOrder ordOrder, PassCode passCode) {
        this.ordOrder = ordOrder;
        this.passCode = passCode;
        init();
    }

    private void init() {
        getItemMeta();
        getContact();
    }

    /**
     * 创建订单Boby
     * @return CreateOrderRequestBody
     */
    public CreateOrderRequestBody createOrderBody() throws Exception {
        return new CreateOrderHelp().createOrderBody();
    }


    /**
     * 创建订单Boby
     * @return CreateOrderRequestBody
     */
    public CreatePaymentOrderRequestBody createPaymentOrderBody() throws Exception {
        return new CreatePaymentOrderHelp().createPaymentOrderBody();
    }



    /**
     * 初始化产品信息
     * @throws Exception
     */
    public void initProductByResourceId() throws Exception {
        if (productInfo == null) {
            productInfo = OTAClientV1.getProductByResourceId(getResourceId());
            if (productInfo == null) {
                throw  new RuntimeException("没有该供应商产品信息！");
            }
        }
    }

    /**
     * 获取联系人
     */
    public OrdPerson getContact() {
        if (contact == null) {
//            contact = OrderUtil.init().getContract(ordOrder);
            contact = ordOrder.getContact();
        }
        return contact;
    }

    /**
     * 获取订单采购产品信息
     */
    public OrdOrderItemMeta getItemMeta() {
        if (itemMeta == null) {
            itemMeta = OrderUtil.init().getItemMeta(ordOrder, passCode);
        }
        return itemMeta;
    }

    /**
     * 获取单价
     */
    public Long getSellPrice() {
        return itemMeta.getSellPrice();
    }

    /**
     * 获取销售数量
     */
    public Long getQuantity() {
        return itemMeta.getProductQuantity() * itemMeta.getQuantity();
    }

    /**
     * 获取销售总金额
     */
    public Long getOrderPrice() {
        return getSellPrice() * getQuantity();
    }

    /**
     * 供应商产品ID
     */
    public String getResourceId() {
        return itemMeta.getProductIdSupplier();
    }

    /**
     * 供应商订单ID
     */
    public String getPartnerorderId() {
        return passCode.getExtId();
    }

    /**
     * 销售产品ID
     */
    public Long getProductId() {
        OrdOrderItemProd ordOrderItemProd = ordOrder.getOrdOrderItemProds().get(0);
        return ordOrderItemProd.getProductId();
    }

    /**
     * 采购产品ID
     */
    public Long getMetaProductId() {
        return getItemMeta().getMetaProductId();
    }

    /**
     * 获取退款手续费
     * @return 手续费
     */
    public Long getOrderRefundCharge() {
        // 通过 采购ID 进行手续费处理
        if (REFUNDCHARGE_MAP.get(getMetaProductId()) != null) {
            BigDecimal ratio = new BigDecimal(REFUNDCHARGE_MAP.get(getMetaProductId()));
            long total = getSellPrice() * getQuantity();
            ratio = ratio.multiply(BigDecimal.valueOf(total));
            return ratio.longValue();
        }
        // 通过 产品ID 进行手续费处理
        /*if (REFUNDCHARGE_MAP.get(getProductId()) != null) {
            BigDecimal ratio = new BigDecimal(REFUNDCHARGE_MAP.get(getProductId()));
            long total = getSellPrice() * getQuantity();
            ratio = ratio.multiply(BigDecimal.valueOf(total));
            return ratio.longValue();
        }*/
        // 通过 供应商产品ID 进行手续费处理
        String resourceId = getResourceId();
        if (REFUNDCHARGE_RESOURCEID_MAP.get(resourceId) != null) {
            BigDecimal ratio = new BigDecimal(REFUNDCHARGE_RESOURCEID_MAP.get(resourceId));
            long total = getSellPrice() * getQuantity();
            ratio = ratio.multiply(BigDecimal.valueOf(total));
            return ratio.longValue();
        }

        return 0L;
    }

    /**
     * 获取退款金额
     * @return 退款金额
     */
    public Long getOrderRefundPrice() {
        return getOrderPrice() - getOrderRefundCharge();
    }

    /**
     * 获取帮助类
     * @param passCode 申码类
     * @return 帮组类
     */
    public static JiuwangCreateOrderHelp getHelp(PassCode passCode) {
        OrderService orderServiceProxy = (OrderService) SpringBeanProxy.getBean("orderServiceProxy");
        // 获取订单对象
        OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(passCode.getOrderId());
        // 获取帮助类
        return new JiuwangCreateOrderHelp(ordOrder, passCode);
    }

    /**
     * 获取 证件类型CODE
     * @param name CnName
     * @return CODE
     */
    private static String getCredentialsType(String name) {
        if (StringUtils.equals(name, "身份证")) {
            return "ID_CARD";
        } else if (StringUtils.equals(name, "护照")) {
            return "HUZHAO";
        } else if (StringUtils.equals(name, "台胞证")) {
            return "TAIBAO";
        } else if (StringUtils.equals(name, "港澳通行证")) {
            return "GANGAO";
        }
        return name;
    }

    class CreateOrderHelp {
        /**
         * 创建订单Boby
         * @return CreateOrderRequestBody
         */
        public CreateOrderRequestBody createOrderBody() throws Exception {
            // 初始化产品信息
            initProductByResourceId();
            // 获取采购数量
            Long quantity = getQuantity();
            // 销售单价
            Long sellPrice = getSellPrice();
            // 销售总价
            Long orderPrice = getOrderPrice();

            // ---------------------创建产品-----------------------
            String resourceId, productName, visitDate, sellPriceStr;

            resourceId = getResourceId();   // 产品ID
            productName = productInfo.getBaseInfo().getProductName(); // 产品名称
            visitDate = itemMeta.getVisitTimeDay();     // 游玩时间
            sellPriceStr = String.valueOf(sellPrice);   // 单价
            CreateOrderRequestBody.OrderInfo.Product product = createProduct(resourceId, productName, visitDate, sellPriceStr);

            // ---------------------取票人信息-----------------------
            String name, mobile, credentialsType, credentials;

            name = contact.getName();       // 姓名
            mobile = contact.getMobile();   // 手机号
            credentialsType = contact.getCertType();// 证件类型
            credentials = contact.getCertNo();      // 证件号
            CreateOrderRequestBody.OrderInfo.ContactPerson contactPerson = createContactPerson(name, mobile, credentialsType, credentials);

            // ---------------------游客信息-----------------------

            // 是否需要游客信息
            String bookPersonType = productInfo.getBookConfig().getBookPersonType();
            CreateOrderRequestBody.OrderInfo.VisitPerson visitPerson = createVisitPerson(bookPersonType);

            // ---------------------订单信息-----------------------
            String orderId, orderQuantity, orderPriceStr;

            orderId = String.valueOf(passCode.getSerialNo());    //申码流水号
            orderQuantity = String.valueOf(quantity);           //获取订单数量
            orderPriceStr = String.valueOf(orderPrice);  //订单价格
            CreateOrderRequestBody.OrderInfo orderInfo = createOrderInfo(orderId, orderQuantity, orderPriceStr, product, contactPerson, visitPerson);

            // ---------------------创建订单-----------------------
            CreateOrderRequestBody body = new CreateOrderRequestBody();
            body.setOrderInfo(orderInfo);
            return body;
        }

        /**
         * 创建订单信息
         */
        private CreateOrderRequestBody.OrderInfo createOrderInfo(String orderId, String orderQuantity, String orderPrice,
                                                                 CreateOrderRequestBody.OrderInfo.Product product,
                                                                 CreateOrderRequestBody.OrderInfo.ContactPerson contactPerson,
                                                                 CreateOrderRequestBody.OrderInfo.VisitPerson visitPerson) {
            CreateOrderRequestBody.OrderInfo orderInfo = new CreateOrderRequestBody.OrderInfo();
            //游客信息，根据产品的实际情况处理
            //产品bookPersonType为CONTACT_PERSON时，person节点赋空值即可
            orderInfo.setVisitPerson(visitPerson);
            //OTA订单ID
            orderInfo.setOrderId(orderId);
            //产品信息
            orderInfo.setProduct(product);
            //取票人信息
            orderInfo.setContactPerson(contactPerson);
            //订单数量
            orderInfo.setOrderQuantity(orderQuantity);
            //订单金额
            orderInfo.setOrderPrice(orderPrice);
            //订单状态 (预付：初始订单)
            orderInfo.setOrderStatus(JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_INIT.toString());
            return orderInfo;
        }

        /**
         * 创建产品
         */
        private CreateOrderRequestBody.OrderInfo.Product createProduct(String resourceId, String productName, String visitDate, String sellPrice) {
            CreateOrderRequestBody.OrderInfo.Product product = new CreateOrderRequestBody.OrderInfo.Product();
            product.setResourceId(resourceId);//产品ID
            product.setProductName(productName);//票名
            product.setVisitDate(visitDate);//游览日期，yyyy-MM-dd
            product.setSellPrice(sellPrice);//单价，单位：分
            return product;
        }

        /**
         * 创建取票人
         */
        private CreateOrderRequestBody.OrderInfo.ContactPerson createContactPerson(String name, String mobile, String credentialsType, String credentials) {
            CreateOrderRequestBody.OrderInfo.ContactPerson contactPerson = new CreateOrderRequestBody.OrderInfo.ContactPerson();
            contactPerson.setName(name);//取票人姓名
            contactPerson.setMobile(mobile);//取票人电话
            contactPerson.setCredentialsType(getCredentialsType(credentialsType));// 取票人证件类型
            contactPerson.setCredentials(credentials);//取票人证件号
            return contactPerson;
        }

        /**
         * 创建游玩人
         */
        private CreateOrderRequestBody.OrderInfo.VisitPerson createVisitPerson(String bookPersonType) {
            CreateOrderRequestBody.OrderInfo.VisitPerson visitPerson = new CreateOrderRequestBody.OrderInfo.VisitPerson();
            // CONTACT_PERSON： 只需要取票人信息        CONTACT_PERSON_AND_VISIT_PERSON：需要游客和取票人信息
            if (StringUtils.equals(bookPersonType, "CONTACT_PERSON_AND_VISIT_PERSON")) {
                List<CreateOrderRequestBody.OrderInfo.VisitPerson.Person> persons = visitPerson.getPerson();
                List<OrdPerson> travellerList = ordOrder.getTravellerList();
                for (OrdPerson ordPerson : travellerList) {
                    CreateOrderRequestBody.OrderInfo.VisitPerson.Person person = new CreateOrderRequestBody.OrderInfo.VisitPerson.Person();
                    person.setName(ordPerson.getName());
                    person.setCredentialsType(getCredentialsType(ordPerson.getCertType()));// 证件类型
                    person.setCredentials(ordPerson.getCertNo());
                    persons.add(person);
                }
            }
            return visitPerson;
        }
    }

    class CreatePaymentOrderHelp {
        /**
         * 创建订单Boby
         * @return CreateOrderRequestBody
         */
        public CreatePaymentOrderRequestBody createPaymentOrderBody() throws Exception {
            // 初始化产品信息
            initProductByResourceId();
            // 获取采购数量
            Long quantity = getQuantity();
            // 销售单价
            Long sellPrice = getSellPrice();
            // 销售总价
            Long orderPrice = getOrderPrice();

            // ---------------------创建产品-----------------------
            String resourceId, productName, visitDate, sellPriceStr;

            resourceId = getResourceId();   // 产品ID
            productName = productInfo.getBaseInfo().getProductName(); // 产品名称
            visitDate = itemMeta.getVisitTimeDay();     // 游玩时间
            sellPriceStr = String.valueOf(sellPrice);   // 单价
            CreatePaymentOrderRequestBody.OrderInfo.Product product = createProduct(resourceId, productName, visitDate, sellPriceStr);

            // ---------------------取票人信息-----------------------
            String name, mobile, credentialsType, credentials;

            name = contact.getName();       // 姓名
            mobile = contact.getMobile();   // 手机号
            credentialsType = contact.getCertType();// 证件类型
            credentials = contact.getCertNo();      // 证件号
            CreatePaymentOrderRequestBody.OrderInfo.ContactPerson contactPerson = createContactPerson(name, mobile, credentialsType, credentials);

            // ---------------------游客信息-----------------------

            // 是否需要游客信息
            String bookPersonType = productInfo.getBookConfig().getBookPersonType();
            CreatePaymentOrderRequestBody.OrderInfo.VisitPerson visitPerson = createVisitPerson(bookPersonType);

            // ---------------------订单信息-----------------------
            String orderId, orderQuantity, orderPriceStr;

            orderId = String.valueOf(passCode.getSerialNo());    //申码流水号
            orderQuantity = String.valueOf(quantity);           //获取订单数量
            orderPriceStr = String.valueOf(orderPrice);  //订单价格
            CreatePaymentOrderRequestBody.OrderInfo orderInfo = createOrderInfo(orderId, orderQuantity, orderPriceStr, product, contactPerson, visitPerson);

            // ---------------------创建订单-----------------------
            CreatePaymentOrderRequestBody body = new CreatePaymentOrderRequestBody();
            body.setOrderInfo(orderInfo);
            return body;
        }

        /**
         * 创建订单信息
         */
        private CreatePaymentOrderRequestBody.OrderInfo createOrderInfo(String orderId, String orderQuantity, String orderPrice,
                                                                        CreatePaymentOrderRequestBody.OrderInfo.Product product,
                                                                        CreatePaymentOrderRequestBody.OrderInfo.ContactPerson contactPerson,
                                                                        CreatePaymentOrderRequestBody.OrderInfo.VisitPerson visitPerson) {
            CreatePaymentOrderRequestBody.OrderInfo orderInfo = new CreatePaymentOrderRequestBody.OrderInfo();
            //游客信息，根据产品的实际情况处理
            //产品bookPersonType为CONTACT_PERSON时，person节点赋空值即可
            orderInfo.setVisitPerson(visitPerson);
            //OTA订单ID
            orderInfo.setOrderId(orderId);
            //产品信息
            orderInfo.setProduct(product);
            //取票人信息
            orderInfo.setContactPerson(contactPerson);
            //订单数量
            orderInfo.setOrderQuantity(orderQuantity);
            //订单金额
            orderInfo.setOrderPrice(orderPrice);
            //订单状态 (已付款)
            orderInfo.setOrderStatus(JiuwangCodeTable.ORDER_STATUS.PREPAY_ORDER_PRINTING.toString());
            return orderInfo;
        }

        /**
         * 创建产品
         */
        private CreatePaymentOrderRequestBody.OrderInfo.Product createProduct(String resourceId, String productName, String visitDate, String sellPrice) {
            CreatePaymentOrderRequestBody.OrderInfo.Product product = new CreatePaymentOrderRequestBody.OrderInfo.Product();
            product.setResourceId(resourceId);//产品ID
            product.setProductName(productName);//票名
            product.setVisitDate(visitDate);//游览日期，yyyy-MM-dd
            product.setSellPrice(sellPrice);//单价，单位：分
            return product;
        }

        /**
         * 创建取票人
         */
        private CreatePaymentOrderRequestBody.OrderInfo.ContactPerson createContactPerson(String name, String mobile, String credentialsType, String credentials) {
            CreatePaymentOrderRequestBody.OrderInfo.ContactPerson contactPerson = new CreatePaymentOrderRequestBody.OrderInfo.ContactPerson();
            contactPerson.setName(name);//取票人姓名
            contactPerson.setMobile(mobile);//取票人电话
            contactPerson.setCredentialsType(getCredentialsType(credentialsType));// 取票人证件类型
            contactPerson.setCredentials(credentials);//取票人证件号
            return contactPerson;
        }

        /**
         * 创建游玩人
         */
        private CreatePaymentOrderRequestBody.OrderInfo.VisitPerson createVisitPerson(String bookPersonType) {
            CreatePaymentOrderRequestBody.OrderInfo.VisitPerson visitPerson = new CreatePaymentOrderRequestBody.OrderInfo.VisitPerson();
            // CONTACT_PERSON： 只需要取票人信息        CONTACT_PERSON_AND_VISIT_PERSON：需要游客和取票人信息
            if (StringUtils.equals(bookPersonType, "CONTACT_PERSON_AND_VISIT_PERSON")) {
                List<CreatePaymentOrderRequestBody.OrderInfo.VisitPerson.Person> persons = visitPerson.getPerson();
                List<OrdPerson> travellerList = ordOrder.getTravellerList();
                for (OrdPerson ordPerson : travellerList) {
                    CreatePaymentOrderRequestBody.OrderInfo.VisitPerson.Person person = new CreatePaymentOrderRequestBody.OrderInfo.VisitPerson.Person();
                    person.setName(ordPerson.getName());
                    person.setCredentialsType(getCredentialsType(ordPerson.getCertType()));// 证件类型
                    person.setCredentials(ordPerson.getCertNo());
                    persons.add(person);
                }
            }
            return visitPerson;
        }
    }
}
