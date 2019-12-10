package org.danyuan.application.turnover.service;

import java.math.BigDecimal;
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
		
		Map<String, Object> params = new HashMap<>();
		StringBuilder stringBuilder1 = new StringBuilder();
		if (vo.getType() != null && vo.getType().size() > 0) {
			stringBuilder1.append(" SELECT  ");
			stringBuilder1.append(columnStringBuilderBuilder);
			stringBuilder1.append(",CATEGORY as TYPE ");
			stringBuilder1.append(",sum(MONEY ) as money  ");
			stringBuilder1.append(" FROM SYS_TURNOVER_INFO  t ");
			stringBuilder1.append(" where   ");
			stringBuilder1.append(" CATEGORY in (:CATEGORY) ");
			params.put("CATEGORY", vo.getType());
			stringBuilder1.append(" group by ");
			stringBuilder1.append(groupStringBuilderBuilder);
			stringBuilder1.append(",CATEGORY");
			
		}

		StringBuilder stringBuilder2 = new StringBuilder();
		List<String> typelist = new ArrayList<String>();
		if ((vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0)) {
			if (vo.getZhichu() != null && vo.getZhichu().size() > 0) {
				typelist.addAll(vo.getZhichu());
			}
			if (vo.getShouru() != null && vo.getShouru().size() > 0) {
				typelist.addAll(vo.getShouru());
			}
			stringBuilder2.append(" SELECT  ");
			stringBuilder2.append(columnStringBuilderBuilder);
			stringBuilder2.append(",TYPE ");
			stringBuilder2.append(",sum(MONEY ) as money  ");
			stringBuilder2.append(" FROM SYS_TURNOVER_INFO  t ");
			stringBuilder2.append(" where   ");
			stringBuilder2.append("  TYPE  in (:TYPE ) ");
			params.put("TYPE", typelist);
			stringBuilder2.append(" group by ");
			stringBuilder2.append(groupStringBuilderBuilder);
			stringBuilder2.append(",TYPE ");
		}

		StringBuilder stringBuilder = new StringBuilder();
		if ((vo.getType() != null && vo.getType().size() > 0) && ((vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0))) {
			stringBuilder.append(stringBuilder1.toString());
			stringBuilder.append(" union ");
			stringBuilder.append(stringBuilder2.toString());
		} else if ((vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0)) {
			stringBuilder.append(stringBuilder2.toString());
		} else if (vo.getType() != null && vo.getType().size() > 0) {
			stringBuilder.append(stringBuilder1.toString());
		} else {
			stringBuilder.append(" SELECT  ");
			stringBuilder.append(columnStringBuilderBuilder);
			stringBuilder.append(",sum(MONEY ) as money  ");
			stringBuilder.append(" FROM SYS_TURNOVER_INFO  t ");
			stringBuilder.append(" group by ");
			stringBuilder.append(groupStringBuilderBuilder);
		}
		stringBuilder.append(" order by datestr");
		NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate(jdbcTemplate);
		List<Map<String, Object>> resultList = template.queryForList(stringBuilder.toString(), params);
		
		Map<String, Object> map = new HashMap<>();

		List<String> xAxis_data = new ArrayList<>();
		groupX(resultList, xAxis_data);

		if (vo.getType() != null && vo.getType().size() > 0) {
			typelist.addAll(vo.getType());
		}
		List<Map<String, Object>> series_data = new ArrayList<>();

		if (((vo.getZhichu() != null && vo.getZhichu().size() > 0) || (vo.getShouru() != null && vo.getShouru().size() > 0)) || (vo.getType() != null && vo.getType().size() > 0)) {
			// 分组
			for (String type : typelist) {
				Map<String, Object> sdata = new HashMap<>();
				sdata.put("type", "bar");
				sdata.put("name", type);

				List<Double> series_data_data = new ArrayList<>();
				for (String string : xAxis_data) {
					BigDecimal money = new BigDecimal(0);
					boolean check = true;
					for (Map<String, Object> map2 : resultList) {
						if (map2.get("DATESTR").toString().equals(string) && map2.get("TYPE").toString().equals(type)) {
							money = money.add(new BigDecimal(map2.get("MONEY").toString()));
							check = false;
							break;
						}
					}
					if (check) {
						series_data_data.add(Double.valueOf(0));
					} else {
						series_data_data.add(money.doubleValue());
					}
				}
				sdata.put("data", series_data_data);
				series_data.add(sdata);
			}
		} else {
			
			typelist.add("数量");
			Map<String, Object> sdata = new HashMap<>();
			sdata.put("type", "bar");
			sdata.put("name", "数量");
			List<Double> series_data_data = new ArrayList<>();
			for (Map<String, Object> map2 : resultList) {
				series_data_data.add(Double.valueOf(map2.get("MONEY").toString()));
			}
			sdata.put("data", series_data_data);
			series_data.add(sdata);
			
		}
		
		map.put("series_data", series_data);
		map.put("xAxis_data", xAxis_data);
		map.put("legend_data", typelist);

		return map;
	}

	/**
	 * @param resultList
	 * @param xAxis_data
	 */
	private void groupX(List<Map<String, Object>> resultList, List<String> xAxis_data) {
		for (Map<String, Object> map : resultList) {
			if (xAxis_data == null) {
				xAxis_data = new ArrayList<String>();
			}
			if (!xAxis_data.contains(map.get("DATESTR").toString())) {
				xAxis_data.add(map.get("DATESTR").toString());
			}
		}
		
	}

	/**
	 * @return
	 */
	public List<Map<String, Object>> total() {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(" SELECT");
		stringbuilder.append(" t.CATEGORY as name,");
		stringbuilder.append(" sum( money)  as money");
		stringbuilder.append(" FROM SYS_TURNOVER_INFO t");
		stringbuilder.append(" group by t.CATEGORY ");
		stringbuilder.append(" union");
		stringbuilder.append(" select");
		stringbuilder.append("  '历史' as name,");
		stringbuilder.append(" sum( money)  as money");
		stringbuilder.append(" from HISTROY_COUNT c");
		return jdbcTemplate.queryForList(stringbuilder.toString());
	}

}
