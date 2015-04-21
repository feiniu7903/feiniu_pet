package com.lvmama.tnt.comm.util;

import java.util.List;

import com.lvmama.comm.utils.Pagination;
import com.lvmama.tnt.comm.vo.Page;
import com.lvmama.tnt.user.po.TntUser;

public abstract class FetchPager<T> {

	private Page<T> page;

	private boolean ajaxFlag;

	public void setAjaxFlag(boolean ajaxFlag) {
		this.ajaxFlag = ajaxFlag;
	}

	public FetchPager(Page<T> page) {
		this.page = page;
	}

	public List<T> fetch() {
		List<T> list = null;
		if (page != null) {
			T t = page.getParam();
			if (t != null) {
				if (page.isAutoCount()) {
					long totalResultSize = getTotalCount(t);
					page.setTotalResultSize(totalResultSize);
				}
				list = fetchDetail(page);
				if (list != null && !list.isEmpty()) {
					if (ajaxFlag)
						buildJSONUrl(page);
					else {
						buildUrl(page);
					}
					page.setPagination(getPagination(page, page.getModel()));
				}
			}
		}
		return list;

	}

	protected abstract long getTotalCount(T t);

	protected abstract List<T> fetchDetail(Page<T> page);

	/**
	 * 初始化链接
	 * 
	 * @param req
	 */
	public void buildUrl(Page<T> page) {
		page.setUrl(getPageUrl(page));
		initUrl(page);
	}

	public void buildJSONUrl(Page<T> page) {
		buildJSONUrl(page, false);
	}

	protected abstract String getPageUrl(Page<T> page);

	protected abstract void initUrl(Page<T> page);

	public void buildJSONUrl(Page<T> page, boolean use) {
		page.setAjaxFlag(true);
		String url = getPageUrl(page);
		if (use) {
			if (url.endsWith("&page=")) {
				url = url.replace("&page=", "");
			}
			if (!url.contains("useAjax")) {
				String requestUri;
				if (url.contains("?")) {
					requestUri = "&useAjax=true";
				} else {
					requestUri = "?useAjax=true";
				}
				url = url + requestUri;
			}
		}
		page.setUrl(url);
		initUrl(page);
	}

	/**
	 * 默认第10类分页方式组装分页Html
	 * 
	 * @author: ranlongfei 2012-8-16 下午6:39:41
	 * @return
	 */
	public String getPagination(Page<T> page) {
		return pagination(page, 10);
	}

	/**
	 * 默认第10类分页方式组装分页Html
	 * 
	 * @author: ranlongfei 2012-8-16 下午6:39:41
	 * @return
	 */
	public String getPagination(Page<T> page, int model) {
		if (model == 0)
			return doStartTag(page);
		else {
			return pagination(page, 10);
		}
	}

	/**
	 * 按分页类型组装分页Html
	 * 
	 * @author: ranlongfei 2012-8-16 下午6:40:39
	 * @param type
	 * @return
	 */
	public String pagination(Page<T> page, int type) {
		Pagination p = new Pagination(page.getPageSize(),
				page.getTotalPageNum(), page.getUrl(), page.getCurrentPage());
		p.setMode(type);
		p.setTotalResultSize(page.getTotalResultSize());
		if (page.isAjaxFlag()) {
			p.setType("ajax");
		}
		return p.doStartTag();
	}

	public String getPagination2(Page<T> page) {
		return doStartTag(page);
	}

	public String doStartTag(Page<T> p) {
		long page = p.getCurrentPage();
		long endPage2 = 0;
		long prevPage = page - 1;
		long nextPage = page + 1;
		long totalPage = p.getTotalPageNum();
		String action = p.getUrl();

		StringBuffer pageString = new StringBuffer("<div class='paging'>");
		pageString.append("<div class='pagebox'>");
		pageString.append("<span class='pageinfo'>");
		pageString.append("共 <b class='num'>" + p.getTotalResultSize()
				+ "</b> 条记录，每页显示 " + p.getPageSize() + "条记录");
		pageString.append("</span>");
		if (prevPage < 1) {
			pageString
					.append(" <span class='prevpage'><i class='larr'></i>上一页</span>");
		} else {
			pageString.append("<a class='prevpage' href='"
					+ toPage(action, prevPage)
					+ "'><i class='larr'></i>上一页</a>");
		}

		if (page != 1)
			pageString.append("<a href='" + toPage(action, 1) + "' title='第 "
					+ 1 + "页'>1</a>");
		if (page >= 5)
			pageString.append("<span class='pagemore'>...</span>");

		if (totalPage > page + 5) {
			endPage2 = page + 5;
		} else {
			endPage2 = totalPage;
		}
		for (long i = page - 5; i <= endPage2; i++) {
			if (i > 0) {
				if (i == page) {
					pageString.append("<span class='pagesel'>" + i + "</span>");
				} else {
					if (i != 1 && i != totalPage) {
						pageString.append("<a href='" + toPage(action, i)
								+ "' title='第 " + i + "页'>" + i + "</a>");
					}
				}
			}
		}
		if (page + 3 < totalPage)
			pageString.append("<span class='pagemore'>...</span>");
		if (page != totalPage)
			pageString.append("<a href='" + toPage(action, totalPage)
					+ "' title='第 " + totalPage + "页'>" + totalPage + "</a>");
		if (nextPage > totalPage) {
			pageString
					.append(" <span class='nextpage'><i class='rarr'></i>上一页</span>");
		} else {
			pageString.append("<a class='nextpage' href='"
					+ toPage(action, nextPage)
					+ "' title='下一页'><i class='rarr'></i>下一页</a>");
		}
		pageString.append("</div>");
		pageString.append("</div>");
		return pageString.toString();
	}

	private String toPage(String action, long page) {
		return action + page;
	}

	public static void main(String args[]) {
		FetchPager<TntUser> t = new WebFetchPager<TntUser>(null) {

			@Override
			protected long getTotalCount(TntUser t) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			protected List<TntUser> fetchDetail(Page<TntUser> page) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		Page<TntUser> p = new Page<TntUser>();
		p.setUrl("http://localhost/users/");
		p.setPageSize(10);
		p.setCurrentPage(2l);
		p.setTotalResultSize(21);
		System.err.println(t.doStartTag(p));
	}
}
