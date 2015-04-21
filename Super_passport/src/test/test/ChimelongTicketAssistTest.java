package test;

import java.util.Date;

import junit.framework.TestCase;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.lvmama.passport.processor.impl.client.chimelong.TicketServiceClient;
import com.lvmama.passport.processor.impl.client.chimelong.TicketServicePortType;

public class ChimelongTicketAssistTest extends TestCase {
	private static final Log LOG = LogFactory.getLog(ChimelongTicketAssistTest.class);
	private TicketServicePortType chlongticketAssist;
	public void setUp(){
		TicketServiceClient client = new TicketServiceClient();
		//create a default service endpoint
		chlongticketAssist = client.getTicketServiceHttpPort();
	}
	/**
	 * 测试ChimelongTicketAssist单例.
	 */
	public void testSingle(){
		//ChimelongTicketAssist chimelongticketAssist2=ChimelongTicketAssist.getInstance();
		//assertEquals(chlongticketAssist, chimelongticketAssist2);
	}
	/**
	 * 查询票基本类型.
	 * @return
	 */
	public void testqueryBaseCategory(){
//		List<BaseCategory> list = chlongticketAssist.queryBaseCategory();
//		for (BaseCategory baseCategory : list) {
//			//LOG.info("票基本类型Name==="+baseCategory.getName().getValue()+"=====Code========"+baseCategory.getCode().getValue()+"====Persons====="+baseCategory.getPersons());
//		}
	}
	/**
	 * 查询公园.
	 * @return
	 */
	public void testqueryParks(){
//		List<Park> parklist=chlongticketAssist.queryParks();
//		assertNotNull(parklist);
//		for (Park park : parklist) {
//			//LOG.info("公园Name==="+park.getName().getValue()+"===code==="+park.getCode().getValue());
//		}
	}
	/**
	 * 查询基本票.
	 * @return
	 */
	public void testqueryBaseTickets(){
//		List<BaseTicket> baseTicketlist =chlongticketAssist.queryBaseTickets();
//		assertNotNull(baseTicketlist);
//		for (BaseTicket baseTicket : baseTicketlist) {
//			//LOG.info("基本票Name==="+baseTicket.getName().getValue()+"===code==="+baseTicket.getCode().getValue());
//		}
	}
	/**
	 * 查询公园基本票对应关系.
	 * @return
	 */
	public void testqueryParkBaseTicket(){
//		List<ParkBaseTicket> parkBaseTicketlist=chlongticketAssist.queryParkBaseTicket();
//		assertNotNull(parkBaseTicketlist);
//		for (ParkBaseTicket parkBaseTicket : parkBaseTicketlist) {
//			//LOG.info("公园基本票对应Name==="+parkBaseTicket.getParkCode().getValue()+"===code==="+parkBaseTicket.getBaseTicketCode().getValue());
//		}
	}
	/**
	 * 查询代理商可售票.
	 * @param ver_no
	 * @param mer_no
	 * @param orderInfo
	 * @param sign
	 * @return
	 */
	@Test
	public  void testqueryAllTickets(){/*
		String orderInfo=StringUtils.getNextDate(new Date(),1, "yyyy-MM-dd HH:mm:ss")+"|"+StringUtils.getNextDate(new Date(),5, "yyyy-MM-dd HH:mm:ss")+"|01|02|03|04|05";
		try{
		//'chlongticketAssist.queryAllTickets(in0, in1, in2, in3)
//		List<Ticket> ticketlist=chlongticketAssist.queryTickets(orderInfo);
//		List<Ticket> ticketlist=(List)obj[0];
//		for (Ticket ticket : ticketlist) {
//			LOG.info(""
//					+ticket.getCateName().getValue()+"====="
//					+ticket.getCateCode().getValue()+"===="
//					+ticket.getBaseName().getValue()+"===="
//					+ticket.getBaseCode().getValue()+"===="
//					+ticket.getBaseAndCateCode().getValue()+"=="
//					+ticket.getPrice1().getValue()+"======"
//					+ticket.getPrice2().getValue());
//		}

		}catch(Exception e){
			LOG.error(e);
		}
	*/}
	/**
	 * 查询指定日子公园剩余人次数.
	 * @param date
	 * @return
	 */
	public void testqueryParkTicketAvaiableQuantity(){
	}

}
