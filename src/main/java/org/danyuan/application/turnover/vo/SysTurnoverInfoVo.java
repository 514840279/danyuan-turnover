package org.danyuan.application.turnover.vo;

import java.util.List;

import org.danyuan.application.common.base.Pagination;
import org.danyuan.application.turnover.po.SysTurnoverInfo;

/**
 * @文件名 SysTurnoverInfoVo.java
 * @包名 org.danyuan.application.turnover.vo
 * @描述 TODO(用一句话描述该文件做什么)
 * @时间 2019年12月5日 下午5:45:31
 * @author Administrator
 * @版本 V1.0
 */
public class SysTurnoverInfoVo extends Pagination<SysTurnoverInfo> {
	List<String>	zhichu;
	List<String>	shouru;
	List<String>	type;
	String			dateType;
	
	/**
	 * @方法名 getZhichu
	 * @功能 返回变量 zhichu 的值
	 * @return List<String>
	 */
	public List<String> getZhichu() {
		return zhichu;
	}
	
	/**
	 * @方法名 setZhichu
	 * @功能 设置变量 zhichu 的值
	 */
	public void setZhichu(List<String> zhichu) {
		this.zhichu = zhichu;
	}
	
	/**
	 * @方法名 getShouru
	 * @功能 返回变量 shouru 的值
	 * @return List<String>
	 */
	public List<String> getShouru() {
		return shouru;
	}
	
	/**
	 * @方法名 setShouru
	 * @功能 设置变量 shouru 的值
	 */
	public void setShouru(List<String> shouru) {
		this.shouru = shouru;
	}
	
	/**
	 * @方法名 getType
	 * @功能 返回变量 type 的值
	 * @return List<String>
	 */
	public List<String> getType() {
		return type;
	}
	
	/**
	 * @方法名 setType
	 * @功能 设置变量 type 的值
	 */
	public void setType(List<String> type) {
		this.type = type;
	}
	
	/**
	 * @方法名 getDateType
	 * @功能 返回变量 dateType 的值
	 * @return String
	 */
	public String getDateType() {
		return dateType;
	}
	
	/**
	 * @方法名 setDateType
	 * @功能 设置变量 dateType 的值
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	
}
