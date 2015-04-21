package com.lvmama.tnt.mapper.prod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.prod.mapper.TntProdPolicyMapper;
import com.lvmama.tnt.prod.po.TntProdPolicy;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class ProdpolicyTest {
	@Autowired
	private TntProdPolicyMapper tntProdPolicyMapper;

	@Test
	public void query() {
		TntProdPolicy policy = new TntProdPolicy ();
		policy.setPolicyType(TntConstant.PROD_POLICY_TYPE.CUT_PROFIT.name());
		policy.setTargetType(TntConstant.PROD_TARGET_TYPE.CHANNEL.name());
		policy.setTargetId(1L);
		System.out.println(tntProdPolicyMapper.queryByTarget(policy));
		
	}
}
