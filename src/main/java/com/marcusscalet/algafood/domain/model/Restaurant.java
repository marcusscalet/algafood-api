package com.marcusscalet.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurant {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(name = "shipping_fee", nullable = false)
	private BigDecimal shippingFee;

	@ManyToOne
	@JoinColumn(name = "cuisine_id", nullable = false)
	private Cuisine cuisine;

	@Embedded
	private Address address;

	private Boolean active = Boolean.TRUE;
	
	private Boolean open = Boolean.TRUE;
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime registrationDate;

	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime updateDate;

	@ManyToMany
	@JoinTable(name = "restaurant_payment_method", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "payment_method_id"))
	private Set<PaymentMethod> paymentMethod = new HashSet<>();

	@OneToMany(mappedBy = "restaurant")
	private List<Product> products = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "restaurant_accountable_user", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> accountable = new HashSet<>();
	
	public void activate() {
		setActive(true);
	}

	public void inactivate() {
		setActive(false);
	}
	
	public void open() {
		setOpen(true);
	}

	public void close() {
	    setOpen(false);
	}     
	
	public boolean isOpen() {
	    return this.open;
	}

	public boolean isClosed() {
	    return !isOpen();
	}

	public boolean isInactive() {
	    return !isActive();
	}

	public boolean isActive() {
	    return this.active;
	}
	
	public boolean allowedToOpen() {
	    return isActive() && isClosed();
	}

	public boolean allowedToActivate() {
	    return isInactive();
	}

	public boolean allowedToInactivate() {
	    return isActive();
	}

	public boolean allowedToClose() {
	    return isOpen();
	}       
	
	public boolean removePaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethod().remove(paymentMethod);
	}
	
	public boolean addPaymentMethod(PaymentMethod paymentMethod) {
		return getPaymentMethod().add(paymentMethod);
	}
	
	public boolean acceptedPaymentMethod(PaymentMethod paymentMethod) {
	    return getPaymentMethod().contains(paymentMethod);
	}

	public boolean notAcceptedPaymentMethod(PaymentMethod paymentMethod) {
	    return !acceptedPaymentMethod(paymentMethod);
	}
	
	public boolean addUser(User user) {
		return getAccountable().add(user);
	}
	
	public boolean removeUser(User user) {
		return getAccountable().remove(user);
	}
}
