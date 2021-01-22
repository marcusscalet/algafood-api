package com.marcusscalet.algafood.domain.service;

import java.util.List;

import com.marcusscalet.algafood.domain.filter.DailySalesFilter;
import com.marcusscalet.algafood.domain.model.dto.DailySales;

public interface SalesQueryService {

	List<DailySales> queryDailySales(DailySalesFilter filter, String timeOffset);
}
