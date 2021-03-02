package com.marcusscalet.algafood.api.v1.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuisineInput {

	@ApiModelProperty(example = "Tailandesa", required = true)
    @NotBlank
    private String name;
}
