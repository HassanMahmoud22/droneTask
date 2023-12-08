package com.example.dronetask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.dronetask.constant.Constraints;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class MedicationDTO {

    @JsonProperty(required = true)
    @NotEmpty(message = "Medication name can't be empty")
    @NotBlank(message = "Medication name can't be blank")
    @NotNull(message = "Medication name can't be null")
    @Pattern(regexp = "^[a-zA-Z0-9.\\-\\/_ ]*$", message="allowed only letters, numbers, ‘-‘, ‘_’")
    private String name;

    @JsonProperty(required = true)
    @NotNull(message = "Medication weight can't be null")
    @DecimalMax(value = "500", message = "Can't carry Medications with size greater than " + Constraints.WEIGHT_LIMIT_MAX + " gr")
    @DecimalMin(value = "1", message = "Can't carry Medications with size less than" + Constraints.WEIGHT_LIMIT_MIN + " gr")
    @NotNull
    private Double weight;

    @Id
    @JsonProperty(required = true)
    @NotEmpty(message = "Medication code can't be empty")
    @NotBlank(message = "Medication code can't be blank")
    @NotNull(message = "Medication code can't be null")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "The Code of Medication is allowed only upper case letters, underscore and numbers")
    private String code;

    @NotEmpty(message = "Medication image can't be empty")
    @NotBlank(message = "Medication image can't be blank")
    @NotNull(message = "Medication image can't be null")
    private String image;

    public MedicationDTO() {
    }

    public MedicationDTO(String name, Double weight, String code, String image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }
}