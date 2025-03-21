package com.epam.finaltask.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequest {
    private String voucherId;
    private List<?> additionalDetails;
}
