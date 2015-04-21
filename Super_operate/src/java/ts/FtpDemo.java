package ts;
import com.lvmama.operate.util.FtpUtil;


public class FtpDemo {

	public static void main(String[] args) {
		FtpUtil ftpUtil = new FtpUtil();
		System.out.println(ftpUtil.uploadFile("F:\\freemarker.rar"));;
		String fn = System.getProperty("java.io.tmpdir")
                + System.getProperty("file.separator");
		System.out.println(fn);
		
		byte[] bs;
		try {
			bs = ftpUtil.readFile("/home/lv/聪明的女人.txt");
			System.out.println("aaa"+new String(bs));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
