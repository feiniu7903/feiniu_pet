package com.lvmama.back.web.utils.insurance.alianz;

public class AlianzTest {
	private static AllianzPolicySoap12Stub binding;
	public static void main(String[] args) {
        try {
            binding = (AllianzPolicySoap12Stub) new AllianzPolicyLocator().getAllianzPolicySoap12();
            AlianzGetPolicy getPolicy = new AlianzGetPolicy("112233", new AlianzPolicyRecord("ASCY_3","20111001","郑","致力","M","310101197706032415","13917677725"), "lvmama");
            System.out.println(binding.getPolicy(getPolicy.toXML()));
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
        } catch (Exception re) {
        	re.printStackTrace();
        }
	}
}
