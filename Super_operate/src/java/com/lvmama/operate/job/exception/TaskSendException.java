package com.lvmama.operate.job.exception;

/**
 * 任务发送异常
 * 
 * @author likun
 * @date 2013/12/24
 */
public class TaskSendException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TaskSendException(String msg) {
		super(msg);
	}

	public static void main(String[] args) {
		try {
			throw new TaskSendException("错误!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
