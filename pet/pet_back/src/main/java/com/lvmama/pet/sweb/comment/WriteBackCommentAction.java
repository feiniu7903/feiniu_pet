package com.lvmama.pet.sweb.comment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONSerializer;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.comment.DicCommentLatitude;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.comment.DicCommentLatitudeService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.CmtLatitudeVO;
import com.lvmama.comm.vo.comment.CommonCmtCommentVO;
import com.lvmama.pet.sweb.comment.dto.ExcelCellModel;

/**
 * 点评回复的查询
 * 
 * @author yuzhizeng
 * 
 */
@Results({ 
	@Result(name = "queryProduct", location = "/WEB-INF/pages/back/comment/queryProduct.jsp"),
	@Result(name = "writeBackComment", location = "/WEB-INF/pages/back/comment/writeBackComment.jsp"),
	@Result(name = "writeBatchComment", location = "/WEB-INF/pages/back/comment/writeBatchComment.jsp"),
	@Result(name = "error", location = "/error.jsp")
	})
public class WriteBackCommentAction extends BackBaseAction {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 2246881921641488950L;
	/**
	 * 日志记录器
	 */
	private static final Logger LOG = Logger.getLogger(WriteBackCommentAction.class);
	/**
	 * 点评服务接口
	 */
	private CmtCommentService cmtCommentService;
	
	protected ProdProductService prodProductService;
	
	private String productId;
	/**
	 * 点评维度列表
	 */
	private String[] latitudeIds;
	/**
	 * 点评维度评分列表
	 */
	private String scores;
	/**
	 * 点评维度列表
	 */
	private List<DicCommentLatitude> commentLatitudeList;
	/**
	 * 点评内容
	 */
	private String content;
	
	private ProdProduct product;
	
	private Date endDate;
	
	private ProdProductPlaceService prodProductPlaceService;
	
	private PlaceService placeService;
	
	private DicCommentLatitudeService dicCommentLatitudeService;
	
	private UserUserProxy userUserProxy;
	/**
	 * 显示错误信息
	 */
	private String showErrorMessage;
	/**
	 * 保存成功
	 */
	private String result;
	
	private File file; //上传的文件
    private String fileFileName; //文件名称
    private String fileContentType; //文件类型
	
	private final String userNames = "棕貓oooO;闹闹;啊宝;小小盛;justG/　;﹏×悅悅;.|'mY's﹖;0o喵喵o0z;大米gg;囧囧囧囧囧;ｖ﹏红茶;[尐Ｖ。];兲眞;緑袖子;王、子;＇Ｂａｂｙ￣°;隐身;凃上胭脂｀;媚眼飞;梦琪;点点馨儿;狮子座向日葵;天涯湘草;隐身;丁小乙;唐僧洗头用飘柔;雾岛之樱;liyu...;丢了翅膀的鱼;柠檬树叶;火神的光芒;深深的留念;哈妮宝贝;悠悠云;健猪小蜜子;夜蔓;细雨霏霏;巴蒂1988;紫竹星云;water221638;零零幺;冰洁雪儿;nana1208;外挂滤镜;加勒比海蓝;www.菜地.com;¨(_君灬o;圹圹;卡西莫多;哦最稀饭你;独伫小桥风满袖;less3366;简单萱萱;火花7588;双色猫眼se;benbenlong002;凡高的麦田;人品太次郎;卖女孩的小火柴;阮红棉;zhao831110;懒的郁闷;起个逼名想一天;猪吧猪吧;暧安;戒掉了;想LLL~~361;唐尸三摆手;中国移不动;采姑娘的老蘑菇;蓝齐儿;透明罐子;黑白天平;准专业水平;簸箕簸箕;马喜藻;海瞳;yoyo4444;会抒情的猪;免疫针;猫少爷;嫁个有钱人;奇懒无比;嘿嘿嘿哟哟哟;基地→海湾圣姑;老婆只能找①个 ……;cs-ak-47;三只草蜢赛蟑螂;罂粟花;奇冤待雪;陌上纤虹;I.B.M;白水;SKY－baby;章无计;SARS亦难匹敌;有一种流行叫做经典;蚕宝宝;满天星*^_^*;沙洲孤鸿;沈缨缨;静 待 椛 开;无所不能;原地♂旋转;炒股高手;师生恋;411の瞬;喜阴不喜光;小之驴;taotao;兜兜有糖;港剧师太;好书伴你行;火影忍者;黑神;空罐少女;朽木白哉;斑目一角;跳跃灵魂;卡梅隆安东尼;上海阿德哥;叛逆的鲁鲁修;珍珠奶茶真好喝啊;木瓜奶茶;dime55;卡卡西2009;老娘舅;玛丽隔壁的;美食小王子;龙虎斗;么力斯古;日剧女王;见着MM直咧嘴;平淡是真;不应如此草率;馋的淘气;健猪小蜜子520;上网没意思;下雨天带砖不带伞;没吃饭的蛐蛐;其色香味必为上乘;乌溜溜的黑眼猪;大牙;令狐帅帅;巴乔;好穷小子;小干探;都市猎人;上海神经;都市稻草人;边缘人;确实色盲;装淑女;一般男人;皮蛋公主;文学痞子;白国际;观众丁;听风客;好兵帅克;吴雨;拿破伦二世;橙色荷兰;流泪谷;梦想之巅;沙漠超;心儿;后悔有药;觉醒的奴隶;假球迷;楚国小生;黯然骗子;^_^开心宝宝;菜菜子-521;卷毛猪;小雾宝贝;我行我素;纵横江湖数载;贪吃嗜睡;驰骋网络;趴在墙上等红杏;烦恼无罪快活有理;破名憋倒英雄汉;起一个名真的好难;水厂退休才返聘;板砖破武术;烦恼频生;追寻浪花的雨;钻石王老五;兔毛加兔毛等于佛毛;表酱紫;眼睛直迷糊瞅啥;杨柳醉轻烟;白天上班晚上上网;团扇扑流萤;上海成熟男人;起个网名想一天;网站过客;笨头笨脑;黑夜不懂晨的明;dao此一游;乡望于江湖;沪太路;天上一棵星;佛前的小草;猪是吃货咩;游天下骑骑马;心忧天下游;快乐赱四方;不温柔的慈悲;我想哭哭不出;忧伤的祝福;想入佛门;傍晚六点下班;出门见大海;天下是我的家;秋到花开;家里的麦子;大自然的恩赐;林恩;我不是方圈圈;等待爱疯5;季天佑;少年天下游;心怀天下走四方;zhutou12345;恐怖汤森;xujia12345;阔少945;pentitum-v;intelceleron;althoughme;骑马找圈圈;先写攻略后去玩;翼龙爱旅行;我们要在一起;橙子圈圈爱旅行;我men的love;难看没人知道;帅得惊动全村;旅游找媳妇儿;爱旅游爱照相;无敌小兔妞;一弟大兔子;我爱你玩儿;秋天的旅行;感动馋在旅行;消shi的记忆;失业者的影院;爸爸我爱旅行;江男程二狗;爹爹跌叠被;在哭不给糖吃;我爱故事会;cheng2gou;南汇大兔子;萝卜兔仔;吃兔子的萝卜;小虎狸猫;向春哥学习;毛主席的好孩子;我hold住了;社员都是向阳花;出门买香烟;吃夏天的冰激凌;冬天吃螃蟹;夏天不知冬天冷;孔龙妹妹;迷信姐的传说;佛祖百福;驴子出没注意;交警敬礼;我爱江山好;秋天荷花开;卖蘑菇商店;苹果没梨好吃;jiangsir;ai小雪;孤雁飞;安庆人好;我是湖北妞;吖是东北;离底或;冬天去取经;恐怖糖森;隆中县令;四川人要得;吃辣的妹子;红果果的秋;竹轩的故乡;孤老爱旅行;菜碟炫;化身为蝶;海边的卡夫卡;黑涩信封;枫溯雨桐;漠尐妞;Loraine;连夜赶论文;爱旅行交朋友;挨踢客;hjk43;我乃是农民;cqlmg;少年汪精卫;我的密码1到9;ournn;不拉是我的;sunheng272;lazecony;天涯孤泪;callmeseed;法拉利与蜗牛;挂QQ到太阳;食肉的尼姑;墓悲;时尚猫猫2008;呼呼舒;kyodule;阑夕;芭娃;micro_bug;city_lover;meiliwangshi;dz8123;junmafeichi;weizhang206;桐翦西枫;无脚的飞鸟;stoic9011;suetsnow30;hiyou40;ivyyly;chji911;解药;百年国米;babagirl;爱独行爱自游;karenzp_85;yb_bill;牵手走天下;yangjie1985;1.13498182E8;风筝轻轻;HANSIL;qj2491;右手旁边K;球球的主人;来日方长169;lanlan1314;夕丫头;langfanyun;我喜欢蓝色LV;勒勾拉斯;0755ask;daha2000;ervin777;yeeyong;LovelvU;520ivy;小甜甜;西北天狼lxp126;wuyan333666;baixiaoju;冰雪儿1982;senlan001;天上天下;hzq1539;深白色;ahyu1971;墨斗PP;xd0734576;hnlili;Deliajj;皮蛋;红袖gz;piaoxu5566;mars-qq;兔宰之;新兰59;yychen719;vincentyi;winlyh;木独猪popo;王主玉;555EEG;由小雨;平淡生活0518;五月天;天涯小柴;马马虎虎;小路;任性囡囡;小刀;pilgrim_yang;teddy941;海水777;神de使者;奔跑中的无聊;huying123;vigy;lzt127;smilevicky522;Iamloli;freedom;刘玄德;apple;helloeva;小在在;哦卖糕的;54yg52cc;zyz9797;sharonsr;enlight;neo0660;流浪汉;love_qiong;jackson;小小乐淘淘;H_X_Q;迷路;alaochen;zyt9797;js_jane;岁月如歌;闯荡江湖;moon;feifei;内酷;嗲囡囡;瘦人;达赖喇嘛;框框仔;聪明的小帅哥;iloveyou;superman;很好很强大;完颜无泪;xiaojing5678;Lukky大人;小汤姆;GARNET CROW;百你度;彩虹泪光;沉默的麻雀;大鹏展翅;黑白风格;还我青春年华;拾金不昧;我是大美女;银河;用户名;mimi;米粉;五丈原;tony119;等你到天长地久;梅兰芳;奔驰一族;青藏高原;shuizhongyang;野尻太君;稻子;飘零上大;qwe007;雅韵女人;阿舟;yellowhh;wayborn;lksdo;汽车代理;西门公子;hoverQQ;onemoretime;千里之行;甲天下;lj8_lijian;我的猫猫;此间有清风;星星之火;宝贝不乖;那伤很美;请不要再重复;农大嘴;0夏天0^^y;精装白粉笔;w2x_xiaoxia;瑞小姐;蔻蔻~;pearlmakeup;小爪子嘿嘿;昱烨;装弹弓;CaTyiP;天使琪;西子淡妆;查查;56767.0;23423.0;878.0;4336.0;17173.0;紫叶飞扬;沐月;最初幻想;兰色;茶花烟雨;暮春残景;苏格年华;风清云淡;天国阶梯;night;柠檬草的味道;3点水;hunter;泰迪熊;小雨点扫楼梯;partytime;西班牙狂舞;带你去看海;永恒之家;rocket;我要的幸福;上海1943;时光飞逝;白天黑夜;小麦;小红帽;朵儿;纯白奶茶;浪花迭起;1片淡雅;吃猫的鱼;梦回小妖;机器狗;冷夜公子;怪味豆豆;城市伊人;花开花谢;一千零一夜;薄荷叶子;开始懂了;云飞扬;十字路口;烟雨江南;麦芽糖苦涩;默默思念;叫兽小星;蔚蓝;沙漠超;心儿;后悔有药;觉醒的奴隶;假球迷;楚国小生;黯然骗子;^_^开心宝宝;菜菜子-521;卷毛猪;小雾宝贝;我行我素;纵横江湖数载;贪吃嗜睡;驰骋网络;趴在墙上等红杏;烦恼无罪快活有理;破名憋倒英雄汉;起一个名真的好难;水厂退休才返聘;板砖破武术;烦恼频生;追寻浪花的雨;钻石王老五;兔毛加兔毛等于佛毛;表酱紫;眼睛直迷糊瞅啥;杨柳醉轻烟;白天上班晚上上网;团扇扑流萤;上海成熟男人;";
    //private final String userNames ="分销测试;bruce_001;bruce_1026;bruce_2001;";
	@Action(value="/commentManager/writeBackComment")
	public String writeBackComment(){
		
		if(productId == null) return "queryProduct";
		
		product = prodProductService.getProdProductById(Long.parseLong(productId));
		if (null == product) {
			showErrorMessage = "没有找到产品";
			result = "fail";
			return "queryProduct";
		}
		 
		//对于酒店和门票的产品点评，要插入对应目的地信息，并使用对应目的地的维度
		if(product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name()) 
				|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name()))
		{
			Long destPlaceId = getProductCommentDestId(Long.parseLong(productId));
			if(destPlaceId == null){
				showErrorMessage = "产品没有目的地";
				result = "fail";
				return "queryProduct";
			}
			Place toPlace = placeService.queryPlaceByPlaceId(destPlaceId);
			if(toPlace == null){ 
				showErrorMessage = "产品没有目的地";
				result = "fail";
				return "queryProduct";
			}
			commentLatitudeList = dicCommentLatitudeService.getLatitudesOfProduct(toPlace, product.getProductType());
			
		} else {
			//线路
			commentLatitudeList = dicCommentLatitudeService.getLatitudesOfProduct(null, product.getProductType());
		}
		if(commentLatitudeList == null || commentLatitudeList.size() == 0){ 
			showErrorMessage = "产品没有找到对应的维度";
			result = "fail";
		}	
		
		return "writeBackComment";
	}
	
	
	@Action(value="/commentManager/saveBackComment")
	public String saveBackComment(){
		
		String[] userNameStr = userNames.split(";");
		String userName = userNameStr[getRandomUsefulCount()];
		UserUser userUser = userUserProxy.getUsersByMobOrNameOrEmailOrCard(userName);
		 
		String saveContent = null;
		if (!StringUtils.isEmpty(content)) {
			saveContent = StringUtil.filterOutHTMLTags(content);
		}

		ProdProduct product = prodProductService.getProdProductById(Long.parseLong(productId));
		
		if (null == product) {
			showErrorMessage = "产品没有";
			result = "fail";
			return "writeBackComment";
		}
		 
		CommonCmtCommentVO comment = new CommonCmtCommentVO();

		// 对于门票产品点评和酒店产品点评需要保存目的地
		if (product.getProductType().equals(Constant.PRODUCT_TYPE.TICKET.name())
				|| product.getProductType().equals(Constant.PRODUCT_TYPE.HOTEL.name())) {
			
			Long destPlaceId = getProductCommentDestId(Long.parseLong(productId));
			if(destPlaceId == null){
				showErrorMessage = "产品没有目的地";
				result = "fail";
				return "writeBackComment";
			}
			comment.setPlaceId(destPlaceId);
		}
		
		comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE);
		comment.setProductId(Long.parseLong(productId));
		comment.setOrderId(null);
		comment.setContent(saveContent);
		comment.setCreatedTime(randomDate(endDate));
		comment.setAuditTime(new Date());
		comment.setCmtLatitudes(getCmtLatitude());
		comment.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name());
		comment.setChannel(Constant.CHANNEL.BACKEND.getCode());
		cmtCommentService.insert(userUser, comment);
		
		showErrorMessage = "写点评成功,请继续";
		result = "success";
		
		return "queryProduct";
	}
 
	/**
	 * 获取点评产品所对应的目的地
	 * @return
	 */
	private Long getProductCommentDestId(Long productId) {
		Long destPlaceId = prodProductPlaceService.selectDestByProductId(productId);
		
		if(destPlaceId == null){
			List<ProdProductPlace> prodProductPlaceList =  prodProductPlaceService.getProdProductPlaceListByProductId(productId);
			if (prodProductPlaceList != null && prodProductPlaceList.size() > 0) {
				destPlaceId = prodProductPlaceList.get(0).getPlaceId();
			} else {
				LOG.error("the dest place of product is null " + productId);
				return null;
			}
		}
		return destPlaceId;
	}
	
	// 获取维度打分
	private List<CmtLatitudeVO> getCmtLatitude() {
		
		List<CmtLatitudeVO> latitudes = new ArrayList<CmtLatitudeVO>();
		String[] scoresNum = scores.split(",");
		for (int i = 0; i < latitudeIds.length; i++) {
			CmtLatitudeVO cmtLatitudeVO = new CmtLatitudeVO();
			cmtLatitudeVO.setLatitudeId(latitudeIds[i]);
			cmtLatitudeVO.setScore(Integer.parseInt(scoresNum[i].toString()));
			latitudes.add(cmtLatitudeVO);
		}
		return latitudes;
	}
	
	public int getRandomUsefulCount() {
		Random random = new Random();
		int randomNumber = random.nextInt(50);
		return (int) randomNumber + 1;
	}

	private  Date randomDate(Date endDate) {  
        try {  
        	Calendar calendar=Calendar.getInstance();
        	calendar.setTime(endDate);
        	calendar.add(Calendar.DATE,-90);
        	Date startDate=calendar.getTime(); 
            long date = random(startDate.getTime(), endDate.getTime());
            return new Date(date);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    private  long random(long begin, long end) {  
        long rtn = begin + (long) (Math.random() * (end - begin));  
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值  
        if (rtn == begin || rtn == end) {  
            return random(begin, end);  
        }  
        return rtn;  
    }  
    
    /**
     * 跳转到批量写点评页面
     * @return
     */
    @Action(value = "/commentManager/writeBatchComment")
    public String writeBatchComment(){
    	return "writeBatchComment";
    }
    
	/**
	 * 下载批量导入评论的文件
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	@Action(value = "/commentManager/downLoadTemplate")
	public String downLoadTemplate() throws IOException {
		try {
			product = prodProductService.getProdProductById(Long
					.parseLong(productId));
			if (null == product) {
				showErrorMessage = "没有找到产品";
				result = "fail";
			} else {

				// 对于酒店和门票的产品点评，要插入对应目的地信息，并使用对应目的地的维度
				if (product.getProductType().equals(
						Constant.PRODUCT_TYPE.TICKET.name())
						|| product.getProductType().equals(
								Constant.PRODUCT_TYPE.HOTEL.name())) {
					Long destPlaceId = getProductCommentDestId(Long
							.parseLong(productId));
					if (destPlaceId == null) {
						showErrorMessage = "产品没有目的地";
						result = "fail";
					} else {
						Place toPlace = placeService
								.queryPlaceByPlaceId(destPlaceId);
						if (toPlace == null) {
							showErrorMessage = "产品没有目的地";
							result = "fail";
						} else {
							commentLatitudeList = dicCommentLatitudeService
									.getLatitudesOfProduct(toPlace,
											product.getProductType());
						}
					}
				} else {
					// 线路
					commentLatitudeList = dicCommentLatitudeService
							.getLatitudesOfProduct(null,
									product.getProductType());
				}
				if (commentLatitudeList == null
						|| commentLatitudeList.size() == 0) {
					showErrorMessage = "产品没有找到对应的维度";
					result = "fail";
				}
			}
			if ("fail".equals(result)) {
				Map<String, String> resultMap = new HashMap<String, String>();
				resultMap.put("result", result);
				resultMap.put("message", showErrorMessage);
			} else {
				// 创建excel文件对象
				HSSFWorkbook wb = new HSSFWorkbook();
				// 创建一个张表
				Sheet sheet = wb.createSheet();
				int columnIndex = 0, rowIndex = 0;
				// 创建第一行
				Row row = sheet.createRow(rowIndex++);
				Cell cell = row.createCell(columnIndex++);
				cell.setCellValue("产品ID:" + productId);

				columnIndex = 0;
				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
						commentLatitudeList.size()+1));
				row = sheet.createRow(rowIndex++);
				for (DicCommentLatitude lat : commentLatitudeList) {
					cell = row.createCell(columnIndex++);
					sheet.setColumnWidth(cell.getColumnIndex(), 3000);
					cell.setCellValue(lat.getName());
				}
				cell = row.createCell(columnIndex++);
				sheet.setColumnWidth(cell.getColumnIndex(), 3000);
				cell.setCellValue("总分");
				cell = row.createCell(columnIndex++);
				sheet.setColumnWidth(cell.getColumnIndex(), 20000);
				cell.setCellValue("评论内容");
				this.getResponse().reset();
				getResponse().addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String("commentTemplate.xls".getBytes()));
				wb.write(this.getResponse().getOutputStream());
				this.getResponse().getOutputStream().flush();
				this.getResponse().getOutputStream().close();
			}
			return null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = "fail";
			showErrorMessage = "下载文件失败";
		}
		if ("fail".equals(result)) {
			this.getRequest().setAttribute("error", showErrorMessage);
			return "error";
		}
		return null;
	}
	
	/**
	 * 上传批量导入数据的文件,并插入点评数据
	 * 
	 * @throws IOException
	 */
	@Action(value = "/commentManager/saveBatchRecomment")
	public void saveBatchRecomment() throws Exception {
		Date startDate = DateUtil.getDateByStr(this.getRequestParameter("startDate"),
				DateUtil.PATTERN_yyyy_MM_dd);
		Date endDate = DateUtil.getDateByStr(this.getRequestParameter("endDate"),
				DateUtil.PATTERN_yyyy_MM_dd);;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long destPlaceId = null;
		List<String> message = new ArrayList<String>();
		result = "1";
		if (StringUtil.isEmptyString(this.fileFileName)
				|| !this.fileFileName.endsWith(".xls")) {
			result = "-1";
			message.add("上传文件格式有误，必须为xls类型的文件!");
		} else {
			List<List<ExcelCellModel>> list = parseXlsToList(file);
			if (list.size() <= 2) {
				result = "-1";
				message.add("上传的数据不能为空!");
			} else {
				productId = list
						.get(0)
						.get(0)
						.getCellValue()
						.substring(
								list.get(0).get(0).getCellValue()
										.lastIndexOf(":") + 1);
				product = prodProductService.getProdProductById(Long
						.parseLong(productId));
				if (null == product) {
					result = "-1";
					message.add("没有找到产品");
				} else {
					// 对于酒店和门票的产品点评，要插入对应目的地信息，并使用对应目的地的维度
					if (product.getProductType().equals(
							Constant.PRODUCT_TYPE.TICKET.name())
							|| product.getProductType().equals(
									Constant.PRODUCT_TYPE.HOTEL.name())) {
						destPlaceId = getProductCommentDestId(Long
								.parseLong(productId));
						if (destPlaceId == null) {
							message.add("产品没有目的地");
							result = "-1";
						} else {
							Place toPlace = placeService
									.queryPlaceByPlaceId(destPlaceId);
							if (toPlace == null) {
								message.add("产品没有目的地");
								result = "-1";
							} else {
								commentLatitudeList = dicCommentLatitudeService
										.getLatitudesOfProduct(toPlace,
												product.getProductType());
							}
						}
					} else {
						// 线路
						commentLatitudeList = dicCommentLatitudeService
								.getLatitudesOfProduct(null,
										product.getProductType());
					}
					if (!"-1".equals(result)) {
						if (commentLatitudeList == null
								|| commentLatitudeList.size() == 0) {
							message.add("产品没有找到对应的维度");
							result = "-1";
						}
					}
				}
			}
			if (!"-1".equals(result)) {
				List<ExcelCellModel> latsCellList = list.get(1);
				List<DicCommentLatitude> lats = new ArrayList<DicCommentLatitude>();
				for (int i = 0; i < latsCellList.size()-1; i++) {
					ExcelCellModel excelCellModel = latsCellList.get(i);
					DicCommentLatitude dto = getLasForListByName(commentLatitudeList,
							excelCellModel.getCellValue());
					if(dto!=null){
						lats.add(dto);
					}
				}
				if (lats.size() != commentLatitudeList.size()) {
					StringBuilder latstr = new StringBuilder();
					for (int i = 0; i < commentLatitudeList.size(); i++) {
						latstr.append(commentLatitudeList.get(i).getName())
								.append((i < commentLatitudeList.size() - 1) ? ","
										: "");
					}
					message.add("excel中维度信息有误，维度应该为:[" + latstr + "]");
					result = "-1";
				}
				List<CommonCmtCommentVO> commentVOs = new ArrayList<CommonCmtCommentVO>();
				try {
					for (int i = 2; i < list.size(); i++) {
						List<ExcelCellModel> cellRow = list.get(i);
						CommonCmtCommentVO comment = new CommonCmtCommentVO();
						int j = 0;
						List<CmtLatitudeVO> cmtLatitudes = new ArrayList<CmtLatitudeVO>();
						//设置维度分数
						for (DicCommentLatitude dicCommentLatitude : lats) {
							CmtLatitudeVO cmtLatitudeVO = new CmtLatitudeVO();
							cmtLatitudeVO.setLatitudeId(dicCommentLatitude
									.getLatitudeId());
							cmtLatitudeVO.setScore((int)Double.parseDouble(cellRow
									.get(j++).getCellValue()));
							cmtLatitudes.add(cmtLatitudeVO);
						}
						
						//设置总分
						CmtLatitudeVO cmtLatitudeVO = new CmtLatitudeVO();
						cmtLatitudeVO.setLatitudeId("FFFFFFFFFFFFFFFFFFFFFFFFFFFF");
						cmtLatitudeVO.setScore((int)Double.parseDouble(cellRow
								.get(j++).getCellValue()));
						cmtLatitudes.add(cmtLatitudeVO);
						//设置其他属性
						comment.setPlaceId(destPlaceId);
						comment.setCmtType(Constant.EXPERIENCE_COMMENT_TYPE);
						comment.setProductId(Long.parseLong(productId));
						comment.setOrderId(null);
						comment.setCmtLatitudes(cmtLatitudes);
						comment.setContent(cellRow.get(j).getCellValue());
						comment.setCreatedTime(getRandomDate(startDate, endDate));
						comment.setAuditTime(new Date());
						comment.setIsAudit(Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS
								.name());
						comment.setChannel(Constant.CHANNEL.BACKEND.getCode());
						UserUser user = getUser(i-2);
						if(user!=null){
							comment.setUserId(user.getId());
							comment.setUserName(user.getUserName());
						}
						commentVOs.add(comment);
					}
					
					message.add("导入点评数据成功，总共导入了"+commentVOs.size()+"条数据!");
					this.cmtCommentService.insertBatchComment(commentVOs);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					result = "-1";
					message.add("文件内容有误!");

				}
			}
		}
		
		resultMap.put("result", result);
		resultMap.put("message", message);
		this.outputToClient(JSONSerializer.toJSON(resultMap).toString());
	}
	
	private UserUser getUser(int index){
		return getUser(index,0);
	}
	
	private UserUser getUser(int index,int loopIndex){
		String[] userNameStr = userNames.split(";");
		String userName = userNameStr[index%userNameStr.length];
		UserUser userUser = userUserProxy.getUsersByMobOrNameOrEmailOrCard(userName);
		if(userUser==null && loopIndex<=10){
			return getUser(++index,++loopIndex);
		}
		return userUser;
	}
	
	public Date getRandomDate(Date startDate, Date endDate) {
		int plusDay = Integer
				.valueOf(((endDate.getTime() - startDate.getTime()) / 86400000)
						+ "");
		if (plusDay <= 0) {
			plusDay = 0;
		}
		Random rd = new Random();
		Date date = DateUtil.dsDay_Date(startDate, rd.nextInt(plusDay));
		date.setHours(getHours());
		date.setMinutes(getMis());
		return date;
	}

	public int getHours() {
		int hs[] = new int[] { 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
				20, 21, 22, 23 };
		Random rd = new Random();
		return hs[rd.nextInt(hs.length)];
	}

	public int getMis() {
		Random rd = new Random();
		return rd.nextInt(60);
	}
	
	private DicCommentLatitude getLasForListByName(List<DicCommentLatitude> commentLatitudeList,String latName){
		for (DicCommentLatitude dicCommentLatitude : commentLatitudeList) {
			if(StringUtil.isNotEmptyString(latName) && dicCommentLatitude.getName().contains(latName.trim())){
				return dicCommentLatitude;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		WriteBackCommentAction action =	new WriteBackCommentAction();
		List<List<ExcelCellModel>> list = action.parseXlsToList(new File("C:\\Users\\likun\\Documents\\i'm office\\PersonalData\\User\\likun@69108666\\RecvedFile\\1.xls"));
		for (List<ExcelCellModel> list2 : list) {
			for (ExcelCellModel excelCellModel : list2) {
				System.out.print(excelCellModel.getCellValue().trim()+";");
			}
		}
		
	}
	
	public List<List<ExcelCellModel>> parseXlsToList(File file) throws Exception{
		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(file));
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum()+1;
		List<List<ExcelCellModel>> result = new ArrayList<List<ExcelCellModel>>();
		for (int i = 0; i < rowNum; i++) {
			List<ExcelCellModel> cellList = new ArrayList<ExcelCellModel>();
			Row row = sheet.getRow(i);
			int cellNum = row.getLastCellNum();
			for (int j = 0; j < cellNum; j++) {
				Cell cell = row.getCell(j);
				ExcelCellModel cellModel = new ExcelCellModel();
				cellModel.setCellIndex(j);
				cellModel.setRowIndex(i);
				cellModel.setCellValue(getStringCellValue(cell));
				cellList.add(cellModel);
			}
			result.add(cellList);
		}
		return result;
	}
	
	 /**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(Cell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case Cell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
        if (cell == null) {
            return "";
        }
        return strCell;
    }

	
	
	
	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String[] getLatitudeIds() {
		return latitudeIds;
	}

	public void setLatitudeIds(String[] latitudeIds) {
		this.latitudeIds = latitudeIds;
	}

	public String getScores() {
		return scores;
	}

	public void setScores(String scores) {
		this.scores = scores;
	}

	public List<DicCommentLatitude> getCommentLatitudeList() {
		return commentLatitudeList;
	}

	public void setCommentLatitudeList(List<DicCommentLatitude> commentLatitudeList) {
		this.commentLatitudeList = commentLatitudeList;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ProdProduct getProduct() {
		return product;
	}

	public void setProduct(ProdProduct product) {
		this.product = product;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setDicCommentLatitudeService(
			DicCommentLatitudeService dicCommentLatitudeService) {
		this.dicCommentLatitudeService = dicCommentLatitudeService;
	}

	public String getShowErrorMessage() {
		return showErrorMessage;
	}

	public void setShowErrorMessage(String showErrorMessage) {
		this.showErrorMessage = showErrorMessage;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getFileFileName() {
		return fileFileName;
	}


	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}


	public String getFileContentType() {
		return fileContentType;
	}


	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	
	
}
