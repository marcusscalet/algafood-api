package com.marcusscalet.algafood.domain.service;

import com.marcusscalet.algafood.domain.filter.DailySalesFilter;

public interface SalesReportService {

	byte[] generateDailySalesReport(DailySalesFilter filter, String timeOffset);
}
