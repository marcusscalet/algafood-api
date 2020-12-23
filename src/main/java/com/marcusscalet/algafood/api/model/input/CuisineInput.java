package com.marcusscalet.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineInput {

    @NotBlank
    private String name;
}
