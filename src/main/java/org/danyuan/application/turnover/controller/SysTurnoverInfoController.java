package org.danyuan.application.turnover.controller;

import java.util.Map;

import org.danyuan.application.common.base.BaseController;
import org.danyuan.application.common.base.BaseControllerImpl;
import org.danyuan.application.common.base.BaseResult;
import org.danyuan.application.common.base.Pagination;
import org.danyuan.application.common.base.ResultUtil;
import org.danyuan.application.turnover.po.SysTurnoverInfo;
import org.danyuan.application.turnover.service.SysTurnoverInfoService;
import org.danyuan.application.turnover.vo.SysTurnoverInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @文件名 SysTurnoverInfoController.java
 * @包名 org.danyuan.application.turnover.controller
 * @描述 TODO(用一句话描述该文件做什么)
 * @时间 2019年12月4日 下午4:09:09
 * @author Administrator
 * @版本 V1.0
 */
@RestController
@RequestMapping("/sysTurnoverInfo")
public class SysTurnoverInfoController extends BaseControllerImpl<SysTurnoverInfo> implements BaseController<SysTurnoverInfo> {
	
	@Autowired
	SysTurnoverInfoService sysTurnoverInfoService;

	@RequestMapping("/pageCost")
	public BaseResult<Page<SysTurnoverInfo>> pageCost(@RequestBody Pagination<SysTurnoverInfo> vo) {
		return ResultUtil.success(sysTurnoverInfoService.pageCost(vo));
	}
	
	@RequestMapping("/pageIncome")
	public BaseResult<Page<SysTurnoverInfo>> pageIncome(@RequestBody Pagination<SysTurnoverInfo> vo) {
		return ResultUtil.success(sysTurnoverInfoService.pageCost(vo));
	}

	@RequestMapping("/chart")
	public BaseResult<Map<String, Object>> chart(@RequestBody SysTurnoverInfoVo vo) {
		return ResultUtil.success(sysTurnoverInfoService.chart(vo));
	}
	
}
