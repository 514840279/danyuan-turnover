package org.danyuan.application.turnover.po;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.danyuan.application.common.base.BaseEntity;

/**
 * @文件名 SysTurnoverInfo.java
 * @包名 org.danyuan.application.turnover.po
 * @描述 TODO(用一句话描述该文件做什么)
 * @时间 2019年12月4日 下午3:50:08
 * @author Administrator
 * @版本 V1.0
 */
@Entity
@Table(name = "sys_turnover_info")
public class SysTurnoverInfo extends BaseEntity {
	
	private String	type;		// 收入，支出
	private String	category;	// 分类，吃饭，借出，收入，。。。。
	private String	name;		// 详细
	private Double	money;		// 金额
	
	/**
	 * @方法名 getType
	 * @功能 返回变量 type 的值
	 * @return String
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @方法名 setType
	 * @功能 设置变量 type 的值
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @方法名 getName
	 * @功能 返回变量 name 的值
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @方法名 setName
	 * @功能 设置变量 name 的值
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @方法名 getMoney
	 * @功能 返回变量 money 的值
	 * @return Double
	 */
	public Double getMoney() {
		return money;
	}
	
	/**
	 * @方法名 setMoney
	 * @功能 设置变量 money 的值
	 */
	public void setMoney(Double money) {
		this.money = money;
	}
	
	/**
	 * @方法名 getCategory
	 * @功能 返回变量 category 的值
	 * @return String
	 */
	public String getCategory() {
		return category;
	}
	
	/**
	 * @方法名 setCategory
	 * @功能 设置变量 category 的值
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	
}
