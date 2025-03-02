package com.epam.finaltask.model;

import com.epam.finaltask.enums.VoucherStatus;
import com.epam.finaltask.travelTypes.HotelType;
import com.epam.finaltask.travelTypes.TourType;
import com.epam.finaltask.travelTypes.TransferType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private String description;
    private double price;

    @Enumerated(EnumType.STRING)
    private TourType tourType;

    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @Enumerated(EnumType.STRING)
    private HotelType hotelType;

    @Enumerated(EnumType.STRING)
    private VoucherStatus status;

    private LocalDate arrivalDate;
    private LocalDate evictionDate;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    private boolean isHot;
}
