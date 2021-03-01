package com.marcusscalet.algafood.api.v2.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("CuisineInput")
@Getter
@Setter
public class CuisineInputV2 {

	@ApiModelProperty(example = "Tailandesa", required = true)
    @NotBlank
    private String cuisineName;
}
