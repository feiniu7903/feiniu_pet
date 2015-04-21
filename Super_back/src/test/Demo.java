import org.apache.commons.lang3.BooleanUtils;


public class Demo {

	public static void main(String[] args) throws Exception{
		String s="false";
		String v=String.valueOf(!BooleanUtils.toBoolean(s));
		System.out.println(v);
	}
}
