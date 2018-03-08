package com.edu.bean;

import java.util.List;

/**
 * SelectServlet 中json
 * @author Administrator
 *
 */
public class SelectJsonBean {
	private int code;
	private String msg;
	private List<Investigation> selectInfo;
	private String startEndData; // 起始日期 例如：2018/03/01-2018/03/08
	
	public String getStartEndData() {
		return startEndData;
	}
	public void setStartEndData(String startEndData) {
		this.startEndData = startEndData;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public List<Investigation> getSelectInfo() {
		return selectInfo;
	}
	public void setSelectInfo(List<Investigation> selectInfo) {
		this.selectInfo = selectInfo;
	}
}
