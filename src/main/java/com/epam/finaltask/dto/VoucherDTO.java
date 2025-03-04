package com.epam.finaltask.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherDTO {
    private UUID userId;
    private String title;
    private String description;
    private double price;
    private String tourType;
    private String transferType;
    private String hotelType;
    private String status;
    private LocalDate arrivalDate;
    private LocalDate evictionDate;
    private boolean isHot;

    public void setIsHot(String hot) {
        isHot = Boolean.parseBoolean(hot);
    }

    public String getIsHot() {
        return Boolean.toString(isHot);
    }

    public void setId(String id) {
        this.userId = UUID.fromString(id);
    }

}
