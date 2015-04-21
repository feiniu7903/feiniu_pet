package com.lvmama.jinjiang;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.jinjiang.model.*;
import com.lvmama.jinjiang.model.request.*;
import com.lvmama.jinjiang.model.response.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JinJiangApiTest {
    private JinjiangClient jinjiangClient;
    private Date updateTimeStart;
    private Date updateTimeEnd;

    @Before
    public void init() {
        jinjiangClient = new JinjiangClient();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -30);
        updateTimeStart = c.getTime();

        updateTimeEnd = new Date();
        System.out.println("updateTimeStart=" + DateUtil.formatDate(updateTimeStart, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss) + "   updateTimeEnd=" + DateUtil.formatDate(updateTimeEnd, DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss));
    }

    /**
     * 1. 批量同步线路代码(分销商至平台)
     */
    @Test
    public void testGetLineCodesByUpdateTime() {
        LineCodesRequest request = new LineCodesRequest(updateTimeStart, updateTimeEnd);
//        LineCodesRequest request = new LineCodesRequest(new Date(1398672311325L), new Date(1398672758247L));

        try {
            long time = System.currentTimeMillis();
            LineCodesResponse response = jinjiangClient.execute(request);
            List<String> list = response.getLineCodes();
            System.out.println("查询用时（秒）：" + (System.currentTimeMillis() - time) / 1000);
            if (list != null) {
                System.out.println("总记录数：" + list.size());
                for (String str : list) {
                    System.out.println(str);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 2. 获取线路详情(分销商至平台)
     */
    @Test
    public void testGetLineByCode() {
        // 线路代码
        String lineCode = "3C896B06-2158-4B7F-BD7F-D6F55DD6AC1E";
        GetLineRequest request = new GetLineRequest(lineCode, updateTimeStart, updateTimeEnd);
        try {
            GetLineResposne resposne = jinjiangClient.execute(request);
            /*Line line = resposne.getLine();
            if (line == null) {
                System.out.println("=======>>> 线路详情对象 Line is null !");
                return;
            }
            System.out.println("===================== Line 线路详情对象 =======================");
            JavaBeanUtil.printJavaBean(Line.class, line);
            // 主题
            List<Subject> subjects = line.getSubjects();
            if (subjects != null) {
                for (Subject subject : subjects) {
                    System.out.println("===================== Subject 主题 =======================");
                    JavaBeanUtil.printJavaBean(Subject.class, subject);
                }
            }
            // 团列表
            List<Group> groups = line.getGroups();
            if (groups != null) {
                for (Group group : groups) {
                    System.out.println("===================== Group 团列表 =======================");
                    JavaBeanUtil.printJavaBean(Group.class, group);

                    // 线路特色
                    LineFeature lineFeature = group.getLineFeature();
                    if (lineFeature != null) {
                        System.out.println("===================== lineFeature 线路特色 =======================");
                        JavaBeanUtil.printJavaBean(LineFeature.class, lineFeature);
                    }
                    // 价格列表
                    List<GroupPrice> prices = group.getPrices();
                    if (prices != null) {
                        for (GroupPrice groupPrice : prices) {
                            System.out.println("===================== GroupPrice 价格列表 =======================");
                            JavaBeanUtil.printJavaBean(GroupPrice.class, groupPrice);
                        }
                    }
                    // 行程列表
                    List<Journey> journeys = group.getJourneys();
                    if (journeys != null) {
                        for (Journey journey : journeys) {
                            System.out.println("===================== Journey 行程列表 =======================");
                            JavaBeanUtil.printJavaBean(Journey.class, journey);
                        }
                    }
                    // 附件列表
                    List<MediaAttach> attachs = group.getAttachs();
                    if (attachs != null) {
                        for (MediaAttach mediaAttach : attachs) {
                            System.out.println("===================== MediaAttach 附件列表 =======================");
                            JavaBeanUtil.printJavaBean(MediaAttach.class, mediaAttach);
                        }
                    }
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 3. 获取签证信息(分销商至平台)
     */
    @Test
    public void testGetVisasByVisaCode() {
        String visaCode = "6F971A67-7CC0-487E-8D67-4648D64BA6C9";
        GetVisasRequest request = new GetVisasRequest(visaCode);
        try {
            GetVisasResposne response = jinjiangClient.execute(request);
            List<Visa> visas = response.getVisas();
            if (visas == null) {
                System.out.println("=======>>> visas is null !");
                return;
            }
            for (Visa visa : visas) {
                System.out.println("===================== visa 签证 =======================");
                JavaBeanUtil.printJavaBean(Visa.class, visa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 4 实时获取团信息(分销商至平台)
     */
    @Test
    public void testRealTimeGetGroup() {
        String groupCode = "SJTFTA141115-001S";
        GetGroupRequest request = new GetGroupRequest(groupCode);
        try {
            GetGroupResposne response = jinjiangClient.execute(request);
            SimpleGroup simpleGroup = response.getSimpleGroup();
            if (simpleGroup == null) {
                System.out.println("=======>>> SimpleGroup is null !");
                return;
            }
            System.out.println("===================== simpleGroup 团对象 =======================");
            JavaBeanUtil.printJavaBean(SimpleGroup.class, simpleGroup);
            // 价格列表
            List<GroupPrice> prices = simpleGroup.getPrices();
            if (prices != null) {
                for (GroupPrice groupPrice : prices) {
                    System.out.println("===================== GroupPrice 价格列表 =======================");
                    JavaBeanUtil.printJavaBean(GroupPrice.class, groupPrice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 5 创建订单接口(分销商至平台)
     */
    @Test
    public void testAddOrder() {
        OrderTestHelp help = new OrderTestHelp();
        try {
            help.init();
            help.createOrder("LVMAMA_TEST_007", "SJTFTA141115-001S");
            help.setContact("lv_testName");
            help.addGuest("lv_李四");
//			help.addGuest("lv_张三");
//			help.addGuest("lv_小张三");

            Order order = help.getOrder();

            AddOrderRequest request = new AddOrderRequest(order);
            AddOrderResponse response = jinjiangClient.execute(request);
            OutOrder outOrder = response.getOutOrder();
            if (outOrder == null) {
                System.out.println("=======>>> OutOrder is null !");
                return;
            }
            System.out.println("===================== OutOrder 订单对象 =======================");
            JavaBeanUtil.printJavaBean(OutOrder.class, outOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 6 订单已支付接口(分销商至平台)
     */
    @Test
    public void testNotifyPayed() {
        String orderNo = "1000140416000344";
        String thirdOrderNo = "LVMAMA_TEST_007";
        PayedRequest request = new PayedRequest();
        request.setOrderNo(orderNo);
        request.setThirdOrderNo(thirdOrderNo);
        // 3760
        request.setActualAmount(BigDecimal.valueOf(10));

        try {
            PayedResponse response = jinjiangClient.execute(request);
/*			orderNo  平台订单编号
			thirdOrderNo  第三方订单号
			orderStatus   订单状态(BOOKING_CONFIRMED已确认)
			payStatus     支付状态(PAYED已支付)*/
            System.out.println("平台订单编号：" + response.getOrderNo());
            System.out.println("第三方订单号：" + response.getThirdOrderNo());
            System.out.println("订单状态：" + response.getOrderStatus());
            System.out.println("支付状态：" + response.getPayStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 7 取消订单(分销商至平台)
     */
    @Test
    public void testCancelOrder() {
        String orderNo = "1000140415000312";
        String thirdOrderNo = "LVMAMA_TEST_006";
        CancelOrderRequest request = new CancelOrderRequest(orderNo, thirdOrderNo);
        try {
            CancelOrderResponse response = jinjiangClient.execute(request);
/*			orderNo  平台订单编号
			thirdOrderNo  第三方订单号
			orderStatus   订单状态(取消成功则订单状态变为:CANCELED已取消,取消失败订单状态不改变。)
			payStatus     支付状态(PAY_WAITING待支付)*/
            System.out.println("平台订单编号：" + response.getOrderNo());
            System.out.println("第三方订单号：" + response.getThirdOrderNo());
            System.out.println("订单状态：" + response.getOrderStatus());
            System.out.println("支付状态：" + response.getPayStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 8 通知取消订单(平台至分销商)
     */
    @Test
    public void testNotifyCancelOrder() {
        String orderNo = "1000140414000168";			// 平台订单号
        String thirdOrderNo = "LVMAMA_TEST_003";		// 分销商订单号
        String orderStatus = "CANCELED";				// 订单状态
        BigDecimal lossAmount = BigDecimal.valueOf(1000);	// 损失金额
        String payStatus = "PAY_REFUNDED";				// 支付状态
        String refundRemark = "";						// 退款备注

        NotifyCancelOrderRequest request = new NotifyCancelOrderRequest();
        request.setOrderNo(orderNo);
        request.setThirdOrderNo(thirdOrderNo);
        request.setOrderStatus(orderStatus);
        request.setLossAmount(lossAmount);
        request.setPayStatus(payStatus);
        request.setRefundRemark(refundRemark);
        try {
            NotifyCancelOrderResponse response = jinjiangClient.execute(request);
            System.out.println("错误代码：" + response.getErrorcode());
            System.out.println("错误描述：" + response.getErrormessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 9 查询订单(分销商至平台)
     */
    @Test
    public void testGetOrder() {
        String orderNo = "1000140415000312";
        String thirdOrderNo = "LVMAMA_TEST_006";
        GetOrderRequest request = new GetOrderRequest(orderNo, thirdOrderNo);
        try {
            GetOrderResponse response = jinjiangClient.execute(request);
            // 订单对象
            OrderDetail order = response.getOrder();
            if (order == null) {
                System.out.println("=======>>> 订单对象 order is null !");
                return;
            }
            System.out.println("===================== OrderDetail 订单对象  =======================");
            JavaBeanUtil.printJavaBean(OrderDetail.class, order);
            // 联系人
            Contact contact = order.getContact();
            if (contact != null) {
                System.out.println("===================== Contact 联系人  =======================");
                JavaBeanUtil.printJavaBean(Contact.class, contact);
            }
            // 游客列表
            List<Guest> guests = order.getGuests();
            if (guests != null) {
                for (Guest guest : guests) {
                    System.out.println("===================== Guest 游客列表 =======================");
                    JavaBeanUtil.printJavaBean(Guest.class, guest);
                }
            }
            // 订单应收
            List<Receivable> receivables = order.getReceivables();
            if (receivables != null) {
                for (Receivable receivable : receivables) {
                    System.out.println("===================== Receivable 订单应收 =======================");
                    JavaBeanUtil.printJavaBean(Receivable.class, receivable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
