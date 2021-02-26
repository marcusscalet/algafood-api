package com.marcusscalet.algafood.api.v1.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.v1.model.AddressModel;
import com.marcusscalet.algafood.domain.model.Address;

@Component
public class AddressModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public AddressModel toModel(Address address) {
		return modelMapper.map(address, AddressModel.class);
	}
	
	public List<AddressModel> toCollectionModel(List<Address> addressList){
		return addressList.stream().map(address -> toModel(address)).collect(Collectors.toList());
	}
}
