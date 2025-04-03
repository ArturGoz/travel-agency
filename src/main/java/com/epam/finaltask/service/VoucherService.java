package com.epam.finaltask.service;

import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.model.Voucher;
import com.epam.finaltask.model.VoucherStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface VoucherService {
    VoucherDTO create(VoucherDTO voucherDTO);

    VoucherDTO order(String id, String userId);

    VoucherDTO update(String id, VoucherDTO voucherDTO);

    void delete(String voucherId);

    VoucherDTO changeHotStatus(String id, String isHot);

    List<VoucherDTO> findAllByUserId(String userId);

    List<VoucherDTO> findAllByTourType(String tourType);

    List<VoucherDTO> findAllByTransferType(String transferType);

    List<VoucherDTO> findAllByPrice(String price);

    List<VoucherDTO> findAllByHotelType(String hotelType);

    List<VoucherDTO> findAll();

    List<VoucherDTO> findAllByUsername(String username);

    List<VoucherDTO> findAllByStatus(String status);

    VoucherDTO changeStatus(String id, String status);

    Page<Voucher> findAll(Specification<Voucher> spec, Pageable pageable);
}
