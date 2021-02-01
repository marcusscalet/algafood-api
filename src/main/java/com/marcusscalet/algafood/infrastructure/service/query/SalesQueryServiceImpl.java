package com.marcusscalet.algafood.infrastructure.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.filter.DailySalesFilter;
import com.marcusscalet.algafood.domain.model.Order;
import com.marcusscalet.algafood.domain.model.StatusOrder;
import com.marcusscalet.algafood.domain.model.dto.DailySales;
import com.marcusscalet.algafood.domain.service.SalesQueryService;

@Repository
public class SalesQueryServiceImpl implements SalesQueryService {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<DailySales> queryDailySales(DailySalesFilter filter, String timeOffset) {
		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(DailySales.class);
		var root = query.from(Order.class);

		var predicates = new ArrayList<Predicate>();

		var functionConvertTzCreationDate = builder.function(
				"convert_tz", Date.class, 
				root.get("creationDate"),
				builder.literal("+00:00"), 
				builder.literal(timeOffset));
		
		var functionDateOnCreationDate = builder.function(
				"date",
				Date.class,
				functionConvertTzCreationDate);

		var selection = builder.construct(
				DailySales.class, 
				functionDateOnCreationDate, 
				builder.count(root.get("id")),
				builder.sum(root.get("totalCost")));
		
		if (filter.getRestaurantId() != null) {
			predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
		}
		
		if (filter.getCreationStartDate() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getCreationStartDate()));
		}

		if (filter.getCreationEndDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getCreationEndDate()));
		}
		
		predicates.add(root.get("status").in(StatusOrder.CONFIRMED, StatusOrder.DELIVERED));

		query.select(selection);
		query.where(predicates.toArray(new Predicate[0]));
		query.groupBy(functionDateOnCreationDate);

		return manager.createQuery(query).getResultList();
	}

}
