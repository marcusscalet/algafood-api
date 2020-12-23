package com.marcusscalet.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcusscalet.algafood.api.model.CuisineDTO;
import com.marcusscalet.algafood.domain.model.Cuisine;

@Component
public class CuisineDTOAssembler {
	
	@Autowired
    private ModelMapper modelMapper;
    
    public CuisineDTO toDTO(Cuisine cuisine) {
        return modelMapper.map(cuisine, CuisineDTO.class);
    }
    
    public List<CuisineDTO> toCollectionDTO(List<Cuisine> cuisines) {
        return cuisines.stream()
                .map(cuisine -> toDTO(cuisine))
                .collect(Collectors.toList());
    }   

}
