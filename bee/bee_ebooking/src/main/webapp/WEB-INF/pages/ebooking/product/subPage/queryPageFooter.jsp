<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div style="padding-left: 0px;" class="table01Footer"><s:property escape="false" value="@com.lvmama.comm.utils.Pagination@pagination(pagination.pageSize,pagination.totalPageNum,pagination.url,pagination.currentPage)"/></div>