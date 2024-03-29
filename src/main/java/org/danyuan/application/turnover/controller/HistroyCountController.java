package org.danyuan.application.turnover.controller;

import org.danyuan.application.common.base.BaseController;
import org.danyuan.application.common.base.BaseControllerImpl;
import org.danyuan.application.turnover.po.HistroyCount;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @文件名 HistroyCountController.java
 * @包名 org.danyuan.application.turnover.controller
 * @描述 TODO(用一句话描述该文件做什么)
 * @时间 2019年12月4日 下午4:05:50
 * @author Administrator
 * @版本 V1.0
 */
@RestController
@RequestMapping("/histroyCount")
public class HistroyCountController extends BaseControllerImpl<HistroyCount> implements BaseController<HistroyCount> {

}
