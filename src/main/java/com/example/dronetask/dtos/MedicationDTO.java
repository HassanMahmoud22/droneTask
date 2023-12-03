package com.example.dronetask.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class MedicationDTO {

    @JsonProperty(required = false)
    private Long id;

    @JsonProperty(required = true)
    @NotEmpty(message = "name can't be empty")
    @NotBlank(message = "name can't be blank")
    @NotNull(message = "name can't be null")
    @Pattern(regexp = "^[a-zA-Z0-9.\\-\\/_ ]*$", message="allowed only letters, numbers, ‘-‘, ‘_’")
    private String name;

    @JsonProperty(required = true)
    @NotNull(message = "medication weight can't be null")
    @Max(value=500, message="medication weight exceeds drones weight limit")
    private Double weight;

    @JsonProperty(required = true)
    @NotEmpty(message = "medication code can't be empty")
    @NotBlank(message = "medication code can't be blank")
    @NotNull(message = "medication code can't be null")
    @Pattern(regexp = "^[A-Z0-9.\\-\\/_ ]*$", message="allowed only upper case letters, underscore and numbers")
    private String code;

    @NotEmpty(message = "medication image can't be empty")
    @NotBlank(message = "medication image can't be blank")
    @NotNull(message = "medication image can't be null")
    private String image;

}