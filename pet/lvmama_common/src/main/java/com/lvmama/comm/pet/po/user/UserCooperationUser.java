package com.lvmama.comm.pet.po.user;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * 第三方合作商用户和驴妈妈用户的对应表
 *
 * @author Brian
 *
 */
public class UserCooperationUser implements java.io.Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1488185405191154674L;

	/**
	 * 唯一标示
	 */
	private Long cooperationUserId;
	/**
	 * 合作商名称
	 */
	private String cooperation;
	/**
	 * 驴妈妈用户标识
	 */
	private Long userId;
	/**
	 * 合作商的唯一标示
	 */
	private String cooperationUserAccount;

	/**
	 * Constructor
	 */
	public UserCooperationUser() {
		
	}

	/**
	 * Constructor
	 *
	 * @param cooperation
	 *            合作商名称
	 * @param userId
	 *            驴妈妈用户标识
	 * @param cooperationUserAccout
	 *            合作商唯一标示
	 */
	public UserCooperationUser(final String cooperation, final Long userId,
			final String cooperationUserAccount) {
		this();
		this.cooperation = cooperation;
		this.userId = userId;
		this.cooperationUserAccount = cooperationUserAccount;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", cooperationUserId)
				.append("cooperation", cooperation).append("userId", userId)
				.append("cooperationUserAccount", cooperationUserAccount)
				.toString();
	}

	// setter and getter
	public Long getCooperationUserId() {
		return cooperationUserId;
	}

	public void setCooperationUserId(final Long cooperationUserId) {
		this.cooperationUserId = cooperationUserId;
	}

	public String getCooperation() {
		return cooperation;
	}

	public void setCooperation(final String cooperation) {
		this.cooperation = cooperation;
	}

	public final Long getUserId() {
		return userId;
	}

	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCooperationUserAccount() {
		return cooperationUserAccount;
	}

	public void setCooperationUserAccount(final String cooperationUserAccount) {
		this.cooperationUserAccount = cooperationUserAccount;
	}
}
