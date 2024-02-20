package com.epam.finaltask.service;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.model.HotelType;
import com.epam.finaltask.model.TourType;
import com.epam.finaltask.model.TransferType;
import com.epam.finaltask.model.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    VoucherDTO create(VoucherDTO voucherDTO);
    VoucherDTO order(String id, String userId);
    VoucherDTO update(String id, VoucherDTO voucherDTO);
    void delete(String voucherId);
    VoucherDTO changeHotStatus(String id, VoucherDTO voucherDTO);
    List<VoucherDTO> findAllByUserId(String userId);

    List<VoucherDTO> findAllByTourType(String tourType);
    List<VoucherDTO> findAllByTransferType(String transferType);
    List<VoucherDTO> findAllByPrice(String price);
    List<VoucherDTO> findAllByHotelType(String hotelType);

    List<VoucherDTO> findAll();
}
