package com.itmy.entity.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;


/**
 * 	分页信息
 * @Author: niusaibo
 * @date: 2023-10-13 11:27
 */
@Data
public class PageResModel<T> {

	/**
	 * 接口数据
	 */
	private List<T> data;

	/**
	 * 页数
	 */
	private Long pageNum;

	/**
	 * 每页大小
	 */
	private Long pageSize;

	/**
	 * 总页数
	 */
	private Long totalPage;

	/**
	 * 总数
	 */
	private Long total;

	public PageResModel() {
	}

	public PageResModel(IPage<T> page) {
		this.data = page.getRecords();
		this.pageNum = page.getCurrent();
		this.pageSize = page.getSize();
		this.totalPage = page.getPages();
		this.total = page.getTotal();
	}

	public PageResModel(List<T> data, IPage page) {
		this.data = data;
		this.pageNum = page.getCurrent();
		this.pageSize = page.getSize();
		this.totalPage = page.getPages();
		this.total = page.getTotal();
	}

	public PageResModel(List<T> data, PageResModel pageResModel) {
		this.data = data;
		this.pageNum = pageResModel.getPageNum();
		this.pageSize = pageResModel.getPageSize();
		this.totalPage = pageResModel.getTotalPage();
		this.total = pageResModel.getTotal();
	}

	public static PageResModel empty(Long pageNum, Long pageSize) {
		PageResModel pageResModel = new PageResModel<String>();
		pageResModel.setPageNum(pageNum);
		pageResModel.setPageSize(pageSize);
		pageResModel.setTotal(0L);
		pageResModel.setTotalPage(0L);
		return pageResModel;
	}

}
