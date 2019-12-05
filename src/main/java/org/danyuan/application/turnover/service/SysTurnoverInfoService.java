package org.danyuan.application.turnover.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.danyuan.application.common.base.BaseService;
import org.danyuan.application.common.base.BaseServiceImpl;
import org.danyuan.application.common.base.Pagination;
import org.danyuan.application.turnover.dao.SysTurnoverInfoDao;
import org.danyuan.application.turnover.po.SysTurnoverInfo;
import org.danyuan.application.turnover.vo.SysTurnoverInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @文件名 SysTurnoverInfoService.java
 * @包名 org.danyuan.application.turnover.service
 * @描述 TODO(用一句话描述该文件做什么)
 * @时间 2019年12月4日 下午4:08:41
 * @author Administrator
 * @版本 V1.0
 */
@Service("sysTurnoverInfoService")
public class SysTurnoverInfoService extends BaseServiceImpl<SysTurnoverInfo> implements BaseService<SysTurnoverInfo> {

	@Autowired
	SysTurnoverInfoDao	sysTurnoverInfoDao;

	@Autowired
	JdbcTemplate		jdbcTemplate;

	/**
	 * @方法名 pageCost
	 * @功能 TODO(这里用一句话描述这个方法的作用)
	 * @参数 @param vo
	 * @参数 @return
	 * @返回 Page<SysTurnoverInfo>
	 * @author Administrator
	 * @throws
	 */
	public Page<SysTurnoverInfo> pageCost(Pagination<SysTurnoverInfo> vo) {
		Order order = Order.desc(vo.getSortName());
		Sort sort = Sort.by(order);
		PageRequest request = PageRequest.of(vo.getPageNumber() - 1, vo.getPageSize(), sort);
		return sysTurnoverInfoDao.findAll(new Specification<SysTurnoverInfo>() {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Predicate toPredicate(Root<SysTurnoverInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<>();
				if (vo.getInfo().getTurnDate() != null && !"".equals(vo.getInfo().getTurnDate().toString())) {
					list.add(criteriaBuilder.equal(root.get("turnDate"), vo.getInfo().getTurnDate()));
				}
				if (vo.getInfo().getCategory() != null && !"".equals(vo.getInfo().getCategory())) {
					list.add(criteriaBuilder.equal(root.get("category"), vo.getInfo().getCategory()));
				}
				return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
			}

		}, request);
	}
	
	/**
	 * @方法名 chart
	 * @功能 TODO(这里用一句话描述这个方法的作用)
	 * @参数 @param vo
	 * @参数 @return
	 * @返回 Object
	 * @author Administrator
	 * @throws
	 */
	public Map<String, Object> chart(SysTurnoverInfoVo vo) {
		StringBuilder columnStringBuilderBuilder = new StringBuilder();
		StringBuilder groupStringBuilderBuilder = new StringBuilder();
		switch (vo.getDateType()) {
			case "YEAR":
				groupStringBuilderBuilder.append(" substr(TURN_DATE,0,4)");
				columnStringBuilderBuilder.append(" substr(TURN_DATE,0,4) as datestr");
				break;
			case "MONTH":
				groupStringBuilderBuilder.append(" substr(TURN_DATE,0,7)");
				columnStringBuilderBuilder.append(" substr(TURN_DATE,0,7) as datestr");
				break;
			case "DAY":
				groupStringBuilderBuilder.append(" TURN_DATE");
				columnStringBuilderBuilder.append(" TURN_DATE as datestr");
				break;
		}

		if (vo.getType() != null && vo.getType().size() > 0) {
			groupStringBuilderBuilder.append(",CATEGORY ");
			columnStringBuilderBuilder.append(",CATEGORY ");
		}

		if ((vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0)) {
			groupStringBuilderBuilder.append(",TYPE ");
			columnStringBuilderBuilder.append(",TYPE ");
		}
		Map<String, Object> params = new HashMap<>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" SELECT  ");
		stringBuilder.append(columnStringBuilderBuilder);
		stringBuilder.append(",sum(MONEY ) as money  ");
		stringBuilder.append(" FROM SYS_TURNOVER_INFO  t ");
		if ((vo.getType() != null && vo.getType().size() > 0) || (vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0)) {
			stringBuilder.append(" where 1=1 ");
			if (vo.getType() != null && vo.getType().size() > 0) {
				stringBuilder.append(" and CATEGORY in (:CATEGORY) ");
				params.put("CATEGORY", vo.getType());
			}
			
			if ((vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0)) {
				stringBuilder.append(" and TYPE  in (:TYPE ) ");
				params.put("TYPE", vo.getZhichu().addAll(vo.getShouru()));
			}
		}
		stringBuilder.append(" group by ");
		stringBuilder.append(groupStringBuilderBuilder);

		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Map<String, Object>> list = template.queryForList(stringBuilder.toString(), params);
		
		Map<String, Object> map = new HashMap<>();

		return map;
	}

}
