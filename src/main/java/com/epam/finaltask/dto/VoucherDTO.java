package com.epam.finaltask.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private String id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;

    @NotBlank(message = "Tour type cannot be blank")
    private String tourType;

    @NotBlank(message = "Transfer type cannot be blank")
    private String transferType;

    @NotBlank(message = "Hotel type cannot be blank")
    private String hotelType;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Arrival date is required")
    private LocalDate arrivalDate;

    @NotNull(message = "Eviction date is required")
    private LocalDate evictionDate;

    @NotBlank(message = "isHot cannot be blank")
    private String isHot;
}
