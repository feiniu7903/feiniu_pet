package com.lvmama.order.jobs;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.prod.ProdHotSellSeq;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.utils.homePage.PindaoPageUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * 自动获取热销排行 显示在驴妈妈首页
 * AutoCatchLvHomeHotSeqJob
 * @author zhongshuangxi
 * 2014-05-12
 *
 */
public class AutoCatchLvHomeHotSeqJob implements Runnable {
    private static final Log log = LogFactory.getLog(AutoCatchLvHomeHotSeqJob.class);
    private ProdProductService prodProductService;
    
    private static Long THREE_COUNT = 3L;
    private static Long FIVE_COUNT  = 5L;
    private static Long TWO_COUNT   = 2L;
    private static Long ONE_COUNT   = 1L;
    
    private static String PROD_TYPE_TICKET = Constant.PRODUCT_TYPE.TICKET.getCode();//门票
    private static String PROD_TYPE_HOTEL  = Constant.PRODUCT_TYPE.HOTEL.getCode();//酒店
    
    private static String GROUP = Constant.SUB_PRODUCT_TYPE.GROUP.getCode();//短途跟团游
    private static String GROUP_LONG = Constant.SUB_PRODUCT_TYPE.GROUP_LONG.getCode();//长途跟团游
    private static String FREENESS_LONG = Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode();//长途自由行
    private static String FREENESS = Constant.SUB_PRODUCT_TYPE.FREENESS.getCode();//目的地自由行
    private static String FREENESS_FOREIGN = Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode();//出境自由行
    private static String GROUP_FOREIGN = Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode();//出境跟团游
    private static String YOULUN = Constant.REGION_NAMES.YOULUN.getCode();//邮轮
    
    private static Long THREE_MONTHS = 30*3L;
    private static Long SIX_MONTHS = 30*6L;
    
    private static String _ZZY = PindaoPageUtils.HOT_TYPE._ZZY.getCode();
    private static String _CJY = PindaoPageUtils.HOT_TYPE._CJY.getCode();
    private static String _GTY = PindaoPageUtils.HOT_TYPE._GTY.getCode();
    private static String _JDL = PindaoPageUtils.HOT_TYPE._JDL.getCode();
    private static String _MPL = PindaoPageUtils.HOT_TYPE._MPL.getCode();
    private static String _ZYX = PindaoPageUtils.HOT_TYPE._ZYX.getCode();
    
    @Override
    public void run() {
        if (Constant.getInstance().isJobRunnable()) {
            try{
                log.info("AutoCatchLvHomeHotSeqJob run...");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
                String nowTimeByChannel = formatter.format(new Date());
                
//                init(nowTimeByChannel-1);//删除全部风险太大，考虑一个月运行一次风险。故保留 根据年月取值
                
                //SH
                String placeCodeSH = PindaoPageUtils.PLACEID_PLACECODE.SH.getPlacecode();
                String homePageFromPlaceNameSH = PindaoPageUtils.homePageFromPlaceNameSH;
                /**上海站-自助游-前台显示销量三个月___自助游（门票3+目的地自由行2）    当地  最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSH,THREE_MONTHS,PROD_TYPE_TICKET,null,THREE_COUNT,placeCodeSH+_ZZY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSH,THREE_MONTHS,null,FREENESS,TWO_COUNT,placeCodeSH+_ZZY,nowTimeByChannel);
                /**上海站-跟团游-前台显示销量三个月___跟团游（短途2+长途3）    当地出发    最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSH,THREE_MONTHS,null,GROUP,TWO_COUNT,placeCodeSH+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSH,THREE_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeSH+_GTY,nowTimeByChannel);
                /**上海站-出境游-前台显示销量六个月___出境（自由行2+跟团2+邮轮1）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSH,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeSH+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSH,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeSH+_CJY,nowTimeByChannel);
//                getHotSellSeqByProductAndYL(homePageFromPlaceNameSH,SIX_MONTHS,null,null,ONE_COUNT,placeCodeSH+_CJY,nowTimeByChannel,YOULUN);//游轮

                //ZJ
                String placeCodeZJ = PindaoPageUtils.PLACEID_PLACECODE.HZ.getPlacecode();
                String homePageFromPlaceNameZJ = PindaoPageUtils.homePageFromPlaceNameZJ;
                /**浙江站-自助游-前台显示销量三个月___自助游（门票3+目的地自由行2）    当地  最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameZJ,THREE_MONTHS,PROD_TYPE_TICKET,null,THREE_COUNT,placeCodeZJ+_ZZY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameZJ,THREE_MONTHS,null,FREENESS,TWO_COUNT,placeCodeZJ+_ZZY,nowTimeByChannel);
                /**浙江站-跟团游-前台显示销量三个月___跟团游（短途2+长途3）    当地出发    最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameZJ,THREE_MONTHS,null,GROUP,TWO_COUNT,placeCodeZJ+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameZJ,THREE_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeZJ+_GTY,nowTimeByChannel);
                /**浙江站-出境游-前台显示销量六个月___出境（自由行2+跟团3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameZJ,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeZJ+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameZJ,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeZJ+_CJY,nowTimeByChannel);
                
                //GD
                String placeCodeGD = PindaoPageUtils.PLACEID_PLACECODE.GZ.getPlacecode();
                String homePageFromPlaceNameGD = PindaoPageUtils.homePageFromPlaceNameGD;
                /**广东站-自助游-前台显示销量六个月___自助游（门票3+目的地自由行2）    当地  最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameGD,SIX_MONTHS,PROD_TYPE_TICKET,null,THREE_COUNT,placeCodeGD+_ZZY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameGD,SIX_MONTHS,null,FREENESS,TWO_COUNT,placeCodeGD+_ZZY,nowTimeByChannel);
                /**广东站-跟团游-前台显示销量六个月___跟团游（短途2+长途3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameGD,SIX_MONTHS,null,GROUP,TWO_COUNT,placeCodeGD+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameGD,SIX_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeGD+_GTY,nowTimeByChannel);
                /**广东站-出境游-前台显示销量六个月___出境（自由行2+跟团3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameGD,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeGD+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameGD,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeGD+_CJY,nowTimeByChannel);
                
                //SC
                String placeCodeSC = PindaoPageUtils.PLACEID_PLACECODE.CD.getPlacecode();
                String homePageFromPlaceNameSC = PindaoPageUtils.homePageFromPlaceNameSC;
                /**四川站-自助游-前台显示销量六个月___自助游（门票3+目的地自由行2）    当地  最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSC,SIX_MONTHS,PROD_TYPE_TICKET,null,THREE_COUNT,placeCodeSC+_ZZY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSC,SIX_MONTHS,null,FREENESS,TWO_COUNT,placeCodeSC+_ZZY,nowTimeByChannel);
                /**四川站-跟团游-前台显示销量六个月___跟团游（短途2+长途3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSC,SIX_MONTHS,null,GROUP,TWO_COUNT,placeCodeSC+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSC,SIX_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeSC+_GTY,nowTimeByChannel);
                /**四川站-出境游-前台显示销量六个月___出境（自由行2+跟团3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSC,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeSC+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSC,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeSC+_CJY,nowTimeByChannel);
                
                //BJ
                String placeCodeBJ = PindaoPageUtils.PLACEID_PLACECODE.BJ.getPlacecode();
                String homePageFromPlaceNameBJ = PindaoPageUtils.homePageFromPlaceNameBJ;
                /**北京站-门票类-前台显示销量三个月___跟团游（门票5） 当地  最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameBJ,THREE_MONTHS,PROD_TYPE_TICKET,null,FIVE_COUNT,placeCodeBJ+_MPL,nowTimeByChannel);
                /**北京站-自由行-前台显示销量六个月___自由行（目的地3+长途2）5 当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameBJ,SIX_MONTHS,null,FREENESS,THREE_COUNT,placeCodeBJ+_ZYX,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameBJ,SIX_MONTHS,null,FREENESS_LONG,TWO_COUNT,placeCodeBJ+_ZYX,nowTimeByChannel);
                /**北京站-出境游-前台显示销量六个月___出境（自由行2+跟团3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameBJ,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeBJ+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameBJ,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeBJ+_CJY,nowTimeByChannel);
           
                //HN
                String placeCodeHN = PindaoPageUtils.PLACEID_PLACECODE.SY.getPlacecode();
                String homePageFromPlaceNameHN = PindaoPageUtils.homePageFromPlaceNameHN;
                /**海南站-酒店类-前台显示销量六个月___酒店5 当地  最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameHN,SIX_MONTHS,PROD_TYPE_HOTEL,null,FIVE_COUNT,placeCodeHN+_JDL,nowTimeByChannel);
                /**海南站-门票类-前台显示销量六个月___门票5 当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameHN,SIX_MONTHS,PROD_TYPE_TICKET,null,FIVE_COUNT,placeCodeHN+_MPL,nowTimeByChannel);
                /**海南站-跟团游-前台显示销量六个月___跟团游（短途2+长途3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameHN,SIX_MONTHS,null,GROUP,TWO_COUNT,placeCodeHN+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameHN,SIX_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeHN+_GTY,nowTimeByChannel);
                
                //CQ
                String placeCodeCQ = PindaoPageUtils.PLACEID_PLACECODE.CQ.getPlacecode();
                String homePageFromPlaceNameCQ = PindaoPageUtils.homePageFromPlaceNameCQ;
                /**重庆站-自助游-前台显示销量六个月___自助游（门票3+目的地自由行2）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameCQ,SIX_MONTHS,PROD_TYPE_TICKET,null,THREE_COUNT,placeCodeCQ+_ZZY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameCQ,SIX_MONTHS,null,FREENESS,TWO_COUNT,placeCodeCQ+_ZZY,nowTimeByChannel);
                /**重庆站-跟团游-前台显示销量六个月___跟团游（短途2+长途3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameCQ,SIX_MONTHS,null,GROUP,TWO_COUNT,placeCodeCQ+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameCQ,SIX_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeCQ+_GTY,nowTimeByChannel);
                /**重庆站-出境游-前台显示销量六个月___出境（自由行2+跟团3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameCQ,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeCQ+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameCQ,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeCQ+_CJY,nowTimeByChannel);
                
                //SZ
                String placeCodeSZ = PindaoPageUtils.PLACEID_PLACECODE.SZ.getPlacecode();
                String homePageFromPlaceNameSZ = PindaoPageUtils.homePageFromPlaceNameSZ;
                /**深圳站-自助游-前台显示销量六个月___自助游（门票3+目的地自由行2）    当地  最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSZ,SIX_MONTHS,PROD_TYPE_TICKET,null,THREE_COUNT,placeCodeSZ+_ZZY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSZ,SIX_MONTHS,null,FREENESS,TWO_COUNT,placeCodeSZ+_ZZY,nowTimeByChannel);
                /**深圳站-跟团游-前台显示销量六个月___跟团游（短途2+长途3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSZ,SIX_MONTHS,null,GROUP,TWO_COUNT,placeCodeSZ+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSZ,SIX_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeSZ+_GTY,nowTimeByChannel);
                /**深圳站-出境游-前台显示销量六个月___出境（自由行2+跟团3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameSZ,SIX_MONTHS,null,FREENESS_FOREIGN,TWO_COUNT,placeCodeSZ+_CJY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameSZ,SIX_MONTHS,null,GROUP_FOREIGN,THREE_COUNT,placeCodeSZ+_CJY,nowTimeByChannel);
                
                //JS
                String placeCodeJS = PindaoPageUtils.PLACEID_PLACECODE.NJ.getPlacecode();
                String homePageFromPlaceNameJS = PindaoPageUtils.homePageFromPlaceNameJS;
                /**江苏站-门票类-前台显示销量三个月___门票5 当地  最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameJS,THREE_MONTHS,PROD_TYPE_TICKET,null,FIVE_COUNT,placeCodeJS+_MPL,nowTimeByChannel);
                /**江苏站-自由行-前台显示销量三个月___自由行（目的地3+长途2）5  当地出发    最高  1月  3个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameJS,THREE_MONTHS,null,FREENESS,THREE_COUNT,placeCodeJS+_ZYX,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameJS,THREE_MONTHS,null,FREENESS_LONG,TWO_COUNT,placeCodeJS+_CJY,nowTimeByChannel);
                /**江苏站-跟团游-前台显示销量六个月___跟团游（短途2+长途3）    当地出发    最高  1月  6个月销量*/
                getHotSellSeqByProduct(homePageFromPlaceNameJS,SIX_MONTHS,null,GROUP,TWO_COUNT,placeCodeJS+_GTY,nowTimeByChannel);
                getHotSellSeqByProduct(homePageFromPlaceNameJS,SIX_MONTHS,null,GROUP_LONG,THREE_COUNT,placeCodeJS+_GTY,nowTimeByChannel);
                
                log.info("AutoCatchLvHomeHotSeqJob run...");
            }catch(Exception e){
                log.error("AutoCatchLvHomeHotSeqJob run... Exception "+e);
                e.printStackTrace();
            }
            log.info("AutoCatchLvHomeHotSeqJob run end...");
        }
    }

    /**
     * 初始化 将数据表里的所有数据清除
     */
//    public void init(int old_){
//        prodProductService.deleteProdHotSell();
//    }
    
    /**
     * 查询热销根据分站出发地条件查询
     * @param prodPlaceIds
     * @param orderCreateTime
     * @param productType
     * @param subProductType
     * @param endRow
     * @return
     */
    public void getHotSellSeqByProduct(String prodPlaceIds, long orderCreateTime, String productType, String subProductType, long endRow,String channel,String baseChannel){
        List<ProdProduct> temp =  prodProductService.queryHotSeqByProdTypeAndPlaceId(prodPlaceIds,orderCreateTime,productType,subProductType,null,endRow);
        setHotSellSeq(temp,channel,baseChannel);
    }
    
    
    public void getHotSellSeqByProductAndYL(String prodPlaceIds, long orderCreateTime, String productType, String subProductType, long endRow,String channel,String baseChannel,String regionName){
        List<ProdProduct> temp =  prodProductService.queryHotSeqByProdTypeAndPlaceId(prodPlaceIds,orderCreateTime,productType,subProductType,regionName,endRow);
        setHotSellSeq(temp,channel,baseChannel);
    }
    /**
     * 添加热销数据到临时表中
     * @param prodProductList
     * @param channel
     */
    public void setHotSellSeq(List<ProdProduct> prodProductList,String channel,String baseChannel){
        for (ProdProduct pp : prodProductList) {
            ProdHotSellSeq phss = new ProdHotSellSeq();
            phss.setProductId(pp.getProductId());
            phss.setProductName(pp.getProductName());
            phss.setProdType(pp.getProductType());
            phss.setSubProdType(pp.getSubProductType());
            phss.setDescription(pp.getDescription());
            phss.setSellPrice(pp.getSellPrice()/100);
            phss.setMarketPrice(pp.getMarketPrice()/100);
            phss.setImgUrl("http://pic.lvmama.com/pics/"+pp.getSmallImage());
            phss.setOrderQuantity(pp.getOrderCount());
            phss.setChannel(channel);
            phss.setBaseChannel(baseChannel);
            prodProductService.insertProdHotSell(phss);
        }
    }
    
    /**get/set**/
    public ProdProductService getProdProductService() {
        return prodProductService;
    }

    public void setProdProductService(ProdProductService prodProductService) {
        this.prodProductService = prodProductService;
    }
}
