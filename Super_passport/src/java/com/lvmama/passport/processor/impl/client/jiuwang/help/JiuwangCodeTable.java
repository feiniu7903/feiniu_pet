package com.lvmama.passport.processor.impl.client.jiuwang.help;

/**
 * 订单状态
 * Created by linkai on 2014/5/7.
 */
public class JiuwangCodeTable {

    /**
     * 订单状态
     */
    public static enum ORDER_STATUS {
        PREPAY_ORDER_INIT("预付：初始订单"),
        PREPAY_ORDER_BOOK_FAILED("预付：预订失败"),
        PREPAY_ORDER_NOT_PAYED("预付：预订成功，待支付"),
        PREPAY_ORDER_CANCEL("预付：订单已取消"),
        PREPAY_ORDER_PRINTING("预付：已付款，出票中"),
        PREPAY_ORDER_PRINT_FAILED("预付：出票失败"),
        PREPAY_ORDER_PRINT_SUCCESS("预付：出票成功"),
        PREPAY_ORDER_REFUNDED("预付：已退订"),
        PREPAY_ORDER_CONSUMED("预付：已消费");

        private String cnName;
        ORDER_STATUS(String name){
            this.cnName=name;
        }
        public String getCode(){
            return this.name();
        }
        public String getCnName(){
            return this.cnName;
        }
        public static String getCnName(String code){
            for(ORDER_STATUS item : ORDER_STATUS.values()){
                if(item.getCode().equals(code))
                {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString(){
            return this.name();
        }

    }

    /**
     * 订单状态
     */
    public static enum RESULT_CODE {
        CODE_1000("1000", "成功"),
        CODE_90001("90001", "报文解析异常，请检查报文结构"),
        CODE_90002("90002", "验证不通过"),
        CODE_90003("90003", "无效IP访问"),
        CODE_90005("90005", "交互XML解析错误"),
        CODE_90006("90006", "供应商标识不存在"),
        CODE_90007("90007", "web服务调用错误"),
        CODE_90008("90008", "IP校验失败"),
        CODE_90009("90009", "不支持该接口"),
        CODE_99998("99998", "OTA接口出错"),
        CODE_99999("99999", "畅游通接口出错");

        private String code;
        private String cnName;
        RESULT_CODE(String code, String cnName){
            this.code = code;
            this.cnName=cnName;
        }
        public String getCode(){
            return this.code;
        }
        public String getCnName(){
            return this.cnName;
        }
        public String toString(){
            return this.name();
        }
    }

    /**
     * 退款返回状态
     */
    public static enum REFUND_RESULT {
        /**  同意退款 */
        APPROVE("同意退款"),
        /**  拒绝退款 */
        REJECT("拒绝退款"),
        /**  重试退款 */
        RETRY("重试退款");

        private String cnName;
        REFUND_RESULT(String name){
            this.cnName=name;
        }
        public String getCode(){
            return this.name();
        }
        public String getCnName(){
            return this.cnName;
        }
        public static String getCnName(String code){
            for(REFUND_RESULT item : REFUND_RESULT.values()){
                if(item.getCode().equals(code))
                {
                    return item.getCnName();
                }
            }
            return code;
        }
        public String toString(){
            return this.name();
        }
    }
}
