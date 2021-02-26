package com.marcusscalet.algafood.api.v1.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.marcusscalet.algafood.api.model.input.PaymentMethodInput;
import com.marcusscalet.algafood.api.openapi.controller.PaymentMethodControllerOpenApi;
import com.marcusscalet.algafood.api.v1.assembler.PaymentMethodInputDisassembler;
import com.marcusscalet.algafood.api.v1.assembler.PaymentMethodModelAssembler;
import com.marcusscalet.algafood.api.v1.model.PaymentMethodModel;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.PaymentMethodNotFoundException;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.repository.PaymentMethodRepository;
import com.marcusscalet.algafood.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping(value = "/payment-methods")
public class PaymentMethodController implements PaymentMethodControllerOpenApi{

	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	@Autowired
	private PaymentMethodModelAssembler paymentMethodModelAssembler;

	@Autowired
	private PaymentMethodInputDisassembler paymentMethodInputDisassembler;

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;
	
	@GetMapping
	public ResponseEntity<CollectionModel<PaymentMethodModel>> listAll(ServletWebRequest request) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		
		OffsetDateTime lastUpdateDate = paymentMethodRepository.getLastUpdateDate();
		
		if(lastUpdateDate != null) {
			eTag = String.valueOf(lastUpdateDate.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		List<PaymentMethod> paymentMethodsList = paymentMethodRegistrationService.listAll();
		
		CollectionModel<PaymentMethodModel> paymentMethodsModel = paymentMethodModelAssembler
				.toCollectionModel(paymentMethodsList);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(paymentMethodsModel);
	}

	@GetMapping("/{paymentMethodId}")
	public ResponseEntity<PaymentMethodModel> find(@PathVariable Long paymentMethodId, ServletWebRequest request) {
		
		ShallowEtagHeaderFilter.disableContentCaching(request.getRequest());
		String eTag = "0";
		
		OffsetDateTime lastUpdateDate = paymentMethodRepository.getLastUpdateDateById(paymentMethodId);
		
		if(lastUpdateDate != null) {
			eTag = String.valueOf(lastUpdateDate.toEpochSecond());
		}
		
		if(request.checkNotModified(eTag)) {
			return null;
		}
		
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		PaymentMethodModel paymentMethodModel = paymentMethodModelAssembler.toModel(paymentMethod);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.eTag(eTag)
				.body(paymentMethodModel);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentMethodModel add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		try {
			PaymentMethod paymentMethod = paymentMethodInputDisassembler.toDomainObject(paymentMethodInput);

			return paymentMethodModelAssembler.toModel(paymentMethodRegistrationService.savePaymentMethod(paymentMethod));
		} catch (PaymentMethodNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@PutMapping("/{paymentMethodId}")
	public PaymentMethodModel update(@PathVariable Long paymentMethodId,
			@Valid @RequestBody PaymentMethodInput paymentMethodInput) {

		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, currentPaymentMethod);

		currentPaymentMethod = paymentMethodRegistrationService.savePaymentMethod(currentPaymentMethod);

		return paymentMethodModelAssembler.toModel(currentPaymentMethod);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long paymentMethodId) {
		paymentMethodRegistrationService.removePaymentMethod(paymentMethodId);
	}
}
