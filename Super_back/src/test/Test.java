import com.lvmama.back.sweb.util.marketing.ItemInfo;
import com.lvmama.back.sweb.util.marketing.MarketingBiLogic;
import com.lvmama.comm.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 14-1-10<p/>
 * Time: 上午10:10<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class Test {
    public static void main(String[] args) throws Exception {

        Object o = new Object();


//        dataModel();
        long total = 1234;
        long pageSize = 100;
        long pageTotal = getPageTotal(total, pageSize);
        System.out.println("pageTotal = " + pageTotal);

        for (int page = 1; page <= pageTotal; page++) {
            long[] p = pageStartEnd(page, pageSize);
            System.out.println("page " + page + " :" + p[0] + " ~ " + p[1]);
        }


    }

    private static long[] pageStartEnd(long page, long pageSize) {

        long[] p = new long[2];
        p[0] = page == 1 ? 1 : page * pageSize - pageSize + 1;
        p[1] = page == 1 ? pageSize : page * pageSize;
        return p;
    }


    private static long getPageTotal(long total, long size) {
        return total % size == 0 ? total / size : total / size + 1;
    }


    private static void dataModel() throws Exception {
        String parameter =
                "/shared/Marketing/注册1周年," +
                        "/shared/Marketing/注册2周年," +
                        "/shared/Marketing/注册3周年," +
                        "/shared/Marketing/注册初次关怀-注册后第7天," +
                        "/shared/Marketing/游玩前,/shared/Marketing/游玩归来," +
                        "/shared/Marketing/用户列表导出格式," +
                        "/shared/Marketing/用户列表格式," +
                        "/shared/Marketing/长期未登录用户（6个月未登录）- 未游玩过," +
                        "/shared/Marketing/长期未登录用户（6个月未登录）- 游玩过";

        List<String> errList = new ArrayList<String>();
        ItemInfo[] itemInfos = MarketingBiLogic.getMarkModelDatas();

        for (ItemInfo info : itemInfos) {
            System.out.println(info.getPath());
        }
        System.out.println("--------------------------------");
        for (ItemInfo info : itemInfos) {
            String nowTimeStr = DateUtil.formatDate(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");

            String[] paths = info.getPath().split("/");
            String name = paths[paths.length - 1];

            if (!"SRS_UserID".equals(name)) {
                if (StringUtils.isNotBlank(parameter) && parameter.contains(info.getPath())) {
                    System.out.println("#### path:" + info.getPath() + "," + nowTimeStr);
                    try {
                        MarketingBiLogic.saveResultSet(info.getPath(), name + "_" + nowTimeStr);
                    } catch (Exception e) {
                        e.printStackTrace();
                        errList.add(info.getPath());
                    }
                }
            }


        }
        System.out.println("err path : " + errList.toString());
    }
}
