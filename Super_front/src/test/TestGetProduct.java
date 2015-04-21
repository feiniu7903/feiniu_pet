import org.junit.Test;

import com.lvmama.common.prd.dao.ProdProductDAO;
import com.lvmama.common.prd.po.ProdProduct;


public class TestGetProduct {
	
	@Test
	public void GetProduct(){
		ProdProductDAO pdao=new ProdProductDAO();
		ProdProduct prodProduct=new ProdProduct();
		long id=20133;
		prodProduct=(ProdProduct)pdao.selectByPrimaryKey(id);
		
		System.out.println("获取产品id:"+prodProduct.getProductId());
	}
}
