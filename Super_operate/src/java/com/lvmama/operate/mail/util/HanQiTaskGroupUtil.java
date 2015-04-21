package com.lvmama.operate.mail.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;

/**
 * 汉启任务组工具类
 * 
 * @author likun
 * 
 */
public class HanQiTaskGroupUtil {

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(getTaskGroupByName("驴妈妈最新活动"),
				true));
	}

	private static Logger logger = Logger.getLogger(HanQiTaskGroupUtil.class);

	/**
	 * 通过组的名称获取组的信息
	 * 
	 * @param groupName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TaskGroup getTaskGroupByName(String groupName) {
		logger.info("获取任务组groupName:" + groupName);
		TaskGroup result = null;
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("hqTaskGroup.xml"));
			List<Element> taskGroups = document.getRootElement().elements(
					"taskGroup");
			boolean flag = false;
			for (Element element : taskGroups) {
				if (groupName != null
						&& groupName.equals(element.attribute("name")
								.getValue())) {
					result = new TaskGroup(element.attributeValue("id"),
							element.attributeValue("name"));
					flag = true;
					break;
				}
			}
			if (!flag) {
				// 如果没有找到，则返回默认的
				result = new TaskGroup(document.getRootElement()
						.attributeValue("defaultGroupId"), document
						.getRootElement().attributeValue("defaultGroupName"));
			}
		} catch (Exception e) {
			logger.error("读取hqTaskGroup.xml文件出错");
			logger.error(e.getMessage(), e);
		}
		if (logger.isInfoEnabled()) {
			logger.info("获取任务组" + JSON.toJSONString(result, true));
		}
		return result;
	}

	/**
	 * 汉启的任务组
	 * 
	 * @author likun
	 * 
	 */
	public static class TaskGroup {

		public TaskGroup(String groupId, String groupName) {
			this.groupId = groupId;
			this.groupName = groupName;
		}

		/**
		 * 任务组id
		 */
		private String groupId;
		/**
		 * 任务组名称
		 */
		private String groupName;

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}

	}

}
