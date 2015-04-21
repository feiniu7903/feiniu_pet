package com.lvmama.pet.pub.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComPictureService;

@ContextConfiguration(locations = { "classpath*:/applicationContext-pet-public-beans.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ComPictureServiceTest {

	@Autowired
	private ComPictureService comPictureService;
	
	@Test
	public void testSavePicture() {
		Long pkId = createComPicture();
		System.out.println("testSavePicture生成"+pkId);
		Assert.assertTrue(pkId > 0);
	}

	private Long createComPicture() {
		ComPicture pic = new ComPicture();
		pic.setPictureName("测试用图片名称0");
		pic.setPictureObjectId(1233201L);
		pic.setPictureObjectType("VIEW_PAGE");
		pic.setPictureUrl("/asdasdasd/asdafasdfasd/aasdfasdf/ss.htm");
		pic.setSeq(0);
		pic.setIsNew(Boolean.TRUE);
		Long pkId = comPictureService.savePicture(pic);
		pic.setPictureId(pkId);
		return pkId;
	}

	@Test
	public void testSavePictureList() {
		List<ComPicture> list = new ArrayList<ComPicture>();
		ComPicture pic = new ComPicture();
		pic.setPictureName("测试用图片名称1");
		pic.setPictureObjectId(23322122322L);
		pic.setPictureObjectType("VIEW_PAGE");
		pic.setPictureUrl("/asdasdasd/asdafasdfasd/aasdfasdf/ss.htm");
		pic.setSeq(1);
		pic.setIsNew(Boolean.TRUE);
		list.add(pic);
		ComPicture pic2 = new ComPicture();
		pic2.setPictureName("测试用图片名称2");
		pic2.setPictureObjectId(23322122322L);
		pic2.setPictureObjectType("VIEW_PAGE");
		pic2.setPictureUrl("/asdasdasd/asdafasdfasd/aasdfasdf/ss2.htm");
		pic2.setSeq(2);
		pic2.setIsNew(Boolean.TRUE);
		list.add(pic2);
		Long result = comPictureService.savePictureList(list);
		Assert.assertEquals(result, new Long(2));
	}

	@Test
	public void testGetPictureByPK() {
		Long pkId = createComPicture();
		ComPicture picc = comPictureService.getPictureByPK(pkId);
		System.out.println("testGetPictureByPK查询"+picc.getPictureId());
		Assert.assertEquals(pkId, picc.getPictureId());
	}

	@Test
	public void testGetPictureByPKs() {
		Long pkId = createComPicture();
		Long[] ids = {pkId};
		List<ComPicture> list = comPictureService.getPictureByPKs(ids);
		System.out.println("testGetPictureByPKs查询"+list.get(0).getPictureId());
		Assert.assertEquals(list.size(), 1);
	}

	@Test
	public void testChangeSeq() {
		List<ComPicture> list = comPictureService.getPictureByObjectIdAndType(23322122322L, "VIEW_PAGE");
		Long id = list.get(0).getPictureId();
		comPictureService.changeSeq(id, "up");
		ComPicture picc = comPictureService.getPictureByPK(id);
		Assert.assertEquals(picc.getSeq(), new Integer(1));
	}

	@Test
	public void testGetPictureByPageId() {
		List<ComPicture> list = comPictureService.getPictureByPageId(1233201L);
		Assert.assertEquals(list.size(), 3);
	}

	@Test
	public void testGetPictureByObjectIdAndType() {
		List<ComPicture> list = comPictureService.getPictureByObjectIdAndType(23322122322L, "VIEW_PAGE");
		Assert.assertEquals(list.size(), 2);
	}
	
	@Test
	public void testDeletePicture() {
		Long pkId = createComPicture();
		comPictureService.deletePicture(pkId);
		ComPicture picc = comPictureService.getPictureByPK(pkId);
		Assert.assertNull(picc);
	}
}
