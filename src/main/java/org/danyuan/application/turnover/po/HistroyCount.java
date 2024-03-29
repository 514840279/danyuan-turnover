package org.danyuan.application.turnover.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.danyuan.application.common.base.BaseEntity;

/**
 * @文件名 HistoryCount.java
 * @包名 org.danyuan.application.turnover.po
 * @描述 TODO(用一句话描述该文件做什么)
 * @时间 2019年12月3日 上午11:06:43
 * @author Administrator
 * @版本 V1.0
 */
@Entity
@Table(name = "histroy_count")
public class HistroyCount extends BaseEntity {
	@Column(name = "name", columnDefinition = " varchar2(20)   COMMENT '金额来源名称'")
	private String	name;	// 金额来源
	@Column(name = "money", columnDefinition = " double  default 0.00  COMMENT '剩余金额'")
	private Double	money;	// 金额
	@Column(name = "type", columnDefinition = " varchar2(20)   COMMENT '金额来源类型'")
	private String	type;	// 分类 银行卡，支付宝，微信，借出，借入
	
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
	
}
