package com.marcusscalet.algafood.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.marcusscalet.algafood.domain.model.PaymentMethod;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

	@Query("select max(updateDate) from PaymentMethod")
	OffsetDateTime getLastUpdateDate();
	
	@Query("select updateDate from PaymentMethod where id = :paymentMethodId")
	OffsetDateTime getLastUpdateDateById(Long paymentMethodId);       
}
