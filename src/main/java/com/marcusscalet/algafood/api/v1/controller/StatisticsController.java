package com.marcusscalet.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marcusscalet.algafood.api.openapi.controller.StatisticsControllerOpenApi;
import com.marcusscalet.algafood.api.v1.AlgaLinks;
import com.marcusscalet.algafood.domain.filter.DailySalesFilter;
import com.marcusscalet.algafood.domain.model.dto.DailySales;
import com.marcusscalet.algafood.domain.service.SalesReportService;
import com.marcusscalet.algafood.infrastructure.service.query.SalesQueryServiceImpl;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController implements StatisticsControllerOpenApi {

	@Autowired
	private SalesQueryServiceImpl salesQueryServiceImpl;

	@Autowired
	private SalesReportService salesReportService;

	@Autowired
	private AlgaLinks algaLinks;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public StatisticsModel statistics() {
	    var statisticsModel = new StatisticsModel();
	    
	    statisticsModel.add(algaLinks.linkToStatisticsDailySales("daily-sales"));
	    
	    return statisticsModel;
	}       
	
	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DailySales> queryDailySales(DailySalesFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		return salesQueryServiceImpl.queryDailySales(filter, timeOffset);
	}

	@GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> queryDailySalesPdf(DailySalesFilter filter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {

		byte[] bytesPdf = salesReportService.generateDailySalesReport(filter, timeOffset);

		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
	
	public static class StatisticsModel extends RepresentationModel<StatisticsModel> {
	}

}
