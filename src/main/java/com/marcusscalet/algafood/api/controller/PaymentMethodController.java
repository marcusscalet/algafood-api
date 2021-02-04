package com.marcusscalet.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.marcusscalet.algafood.api.assembler.PaymentMethodDTOAssembler;
import com.marcusscalet.algafood.api.assembler.PaymentMethodInputDisassembler;
import com.marcusscalet.algafood.api.model.PaymentMethodDTO;
import com.marcusscalet.algafood.api.model.input.PaymentMethodInput;
import com.marcusscalet.algafood.domain.exception.BusinessException;
import com.marcusscalet.algafood.domain.exception.PaymentMethodNotFoundException;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.service.PaymentMethodRegistrationService;

@RestController
@RequestMapping(value = "/payment-method")
public class PaymentMethodController {

	@Autowired
	private PaymentMethodRegistrationService paymentMethodRegistrationService;

	@Autowired
	private PaymentMethodDTOAssembler paymentMethodDTOAssembler;

	@Autowired
	private PaymentMethodInputDisassembler paymentMethodInputDisassembler;

	@GetMapping
	public ResponseEntity<List<PaymentMethodDTO>> listAll() {
		List<PaymentMethod> paymentMethodsList = paymentMethodRegistrationService.listAll();
		
		List<PaymentMethodDTO> paymentMethodsDTO = paymentMethodDTOAssembler
				.toCollectionDTO(paymentMethodsList);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(paymentMethodsDTO);
	}

	@GetMapping("/{paymentMethodId}")
	public ResponseEntity<PaymentMethodDTO> find(@PathVariable Long paymentMethodId) {
		PaymentMethod paymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		PaymentMethodDTO paymentMethodDTO = paymentMethodDTOAssembler.toDTO(paymentMethod);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic())
				.body(paymentMethodDTO);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PaymentMethodDTO add(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
		try {
			PaymentMethod paymentMethod = paymentMethodInputDisassembler.toDomainObject(paymentMethodInput);

			return paymentMethodDTOAssembler.toDTO(paymentMethodRegistrationService.savePaymentMethod(paymentMethod));
		} catch (PaymentMethodNotFoundException e) {
			throw new BusinessException(e.getMessage());
		}

	}

	@PutMapping("/{paymentMethodId}")
	public PaymentMethodDTO update(@PathVariable Long paymentMethodId,
			@Valid @RequestBody PaymentMethodInput paymentMethodInput) {

		PaymentMethod currentPaymentMethod = paymentMethodRegistrationService.searchOrFail(paymentMethodId);

		paymentMethodInputDisassembler.copyToDomainObject(paymentMethodInput, currentPaymentMethod);

		currentPaymentMethod = paymentMethodRegistrationService.savePaymentMethod(currentPaymentMethod);

		return paymentMethodDTOAssembler.toDTO(currentPaymentMethod);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long paymentMethodId) {
		paymentMethodRegistrationService.removePaymentMethod(paymentMethodId);
	}
}
