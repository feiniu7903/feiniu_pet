package com.lvmama.tnt.front.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.tnt.prod.service.TntProdPolicyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml" })
public class TntContractServiceTest {

	@Autowired
	private TntProdPolicyService tntProdPolicyService;

	@Test
	public void testQuery() {
		long cu = System.currentTimeMillis();
		for (int i = 0; i < 50; i++) {
			String rule = 1600 + i + "1600-(1600-1500)*0.8";
			System.err.println(tntProdPolicyService.getPriceByRule(rule));
		}
		System.err.println(System.currentTimeMillis() - cu);
	}

	public static void main(String args[]) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("1600-(1600-1500)*0.8");
		System.err.println(exp.getValue());
	}
}
