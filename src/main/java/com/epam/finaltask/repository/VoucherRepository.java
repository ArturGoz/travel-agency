package com.epam.finaltask.repository;

import com.epam.finaltask.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String>, JpaSpecificationExecutor<Voucher> {
    List<Voucher> findAllByUserId(String userId);

    List<Voucher> findAllByTourType(TourType tourType);

    List<Voucher> findAllByTransferType(TransferType transferType);

    List<Voucher> findAllByPrice(Double price);

    List<Voucher> findAllByHotelType(HotelType hotelType);

    List<Voucher> findAllByUserUsername(String username);

    List<Voucher> findAllByStatus(VoucherStatus status);

    Page<Voucher> findAll(Specification<Voucher> spec, Pageable pageable);
}

