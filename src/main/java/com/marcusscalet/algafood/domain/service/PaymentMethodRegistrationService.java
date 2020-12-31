package com.marcusscalet.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marcusscalet.algafood.domain.exception.EntityBeingUsedException;
import com.marcusscalet.algafood.domain.exception.PaymentMethodNotFoundException;
import com.marcusscalet.algafood.domain.model.PaymentMethod;
import com.marcusscalet.algafood.domain.repository.PaymentMethodRepository;

@Service
public class PaymentMethodRegistrationService {

	private static final String MSG_PAYMENT_METHOD_BEING_USED = "Método de pagamento com código %d não pode ser removido, pois está em uso";

	@Autowired
	private PaymentMethodRepository paymentMethodRepository;

	@Transactional
	public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
		return paymentMethodRepository.save(paymentMethod);

	}

	@Transactional
	public void removePaymentMethod(Long paymentMethodId) {
		try {
			paymentMethodRepository.deleteById(paymentMethodId);
			paymentMethodRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new PaymentMethodNotFoundException(paymentMethodId);
		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(String.format(MSG_PAYMENT_METHOD_BEING_USED, paymentMethodId));
		}
	}

	public PaymentMethod searchOrFail(Long payMentMethodId) {
		return paymentMethodRepository.findById(payMentMethodId)
				.orElseThrow(() -> new PaymentMethodNotFoundException(payMentMethodId));
	}
}
