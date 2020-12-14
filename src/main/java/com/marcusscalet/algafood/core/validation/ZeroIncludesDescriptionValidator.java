package com.marcusscalet.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ZeroIncludesDescriptionValidator implements ConstraintValidator<ZeroIncludesDescription, Object> {

	private String valueField;
	private String descriptionField;
	private String requiredDescription;
	
	@Override
	public void initialize(ZeroIncludesDescription constraint) {
		this.valueField = constraint.valueField();
		this.descriptionField = constraint.descriptionField();
		this.requiredDescription = constraint.requiredDescription();
	}
	
	@Override
	public boolean isValid(Object objetoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		try {
			BigDecimal valor = (BigDecimal) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), valueField)
					.getReadMethod().invoke(objetoValidacao);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(objetoValidacao.getClass(), descriptionField)
					.getReadMethod().invoke(objetoValidacao);
			
			if (valor != null && BigDecimal.ZERO.compareTo(valor) == 0 && descricao != null) {
				valido = descricao.toLowerCase().contains(this.requiredDescription.toLowerCase());
			}
			
			return valido;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

}