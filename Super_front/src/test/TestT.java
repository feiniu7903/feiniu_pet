
public class TestT {

	public static void main(String[] age){
		System.out.println(TestT.moneyConvertStr(1));
	}
	
	public static String moneyConvertStr(long price){
		float f = (float)price/100;
		return f+"";
	}
}
