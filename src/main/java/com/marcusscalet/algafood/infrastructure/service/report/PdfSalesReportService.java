package com.marcusscalet.algafood.infrastructure.service.report;

import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcusscalet.algafood.domain.filter.DailySalesFilter;
import com.marcusscalet.algafood.domain.service.SalesQueryService;
import com.marcusscalet.algafood.domain.service.SalesReportService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class PdfSalesReportService implements SalesReportService {

	@Autowired
	private SalesQueryService salesQueryService;

	@Override
	public byte[] generateDailySalesReport(DailySalesFilter filter, String timeOffset) {

		try {
			var inputStream = this.getClass().getResourceAsStream("/reports/jasper/daily-sales.jasper");

			var params = new HashMap<String, Object>();
			params.put("REPORT_LOCALE", new Locale("pt", "BR"));

			var dailySales = salesQueryService.queryDailySales(filter, timeOffset);
			var dataSource = new JRBeanCollectionDataSource(dailySales);

			var jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (Exception e) {
			throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
		}
	}

}
