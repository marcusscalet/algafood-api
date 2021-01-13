package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.AddressDTO;
import com.marcusscalet.algafood.domain.model.Address;

@Component
public class AddressDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public AddressDTO toDTO(Address address) {
		return modelMapper.map(address, AddressDTO.class);
	}
	
	public List<AddressDTO> toCollectionDTO(List<Address> addressList){
		return addressList.stream().map(address -> toDTO(address)).collect(Collectors.toList());
	}
}
