package com.lvmama.comm.pet.client;


import java.io.File;
import java.io.FileInputStream;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lvmama.comm.pet.client.FSClient;
import com.lvmama.comm.pet.vo.ComFile;
import com.lvmama.comm.vo.Constant;

@ContextConfiguration(locations = { "classpath:/applicationContext-lvmama-comm-beans-test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class FSClientTest {

	@Autowired
	private FSClient fsClient;

	private Long uploadFile() {
		try {
			File file = File.createTempFile("" + System.currentTimeMillis(),
					"txt");

			FileInputStream fis = new FileInputStream(file);
			if (fis != null) {
				int len = fis.available();
				byte[] fileData = new byte[len];
				fis.read(fileData);
				fis.close();

				Long pkId = fsClient.uploadFile(file.getName(), fileData,
						Constant.COM_FS_SERVER_TYPE.EMAIL_FILE.name());
				System.out.println("uploadFile文件ID为： " + pkId);
				return pkId;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Long(0);
	}

	@Test
	public void testUploadFile() {
		Assert.assertTrue(uploadFile() > 0);
	}

	@Test
	public void testDownloadFile() {
		Long pkId = uploadFile();
		ComFile comFile = fsClient.downloadFile(pkId);
		System.out.println("testDownloadFile 文件名称为： " + comFile.getFileName());
		Assert.assertNotNull(comFile.getFileName());

	}

}
