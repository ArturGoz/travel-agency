package com.epam.finaltask.service;

import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.EntityNotFoundException;
import com.epam.finaltask.mapper.VoucherMapper;
import com.epam.finaltask.model.*;
import com.epam.finaltask.repository.UserRepository;
import com.epam.finaltask.repository.VoucherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepository voucherRepository;
    private final VoucherMapper voucherMapper;
    private final UserRepository userRepository;


    @Override
    public VoucherDTO create(VoucherDTO voucherDTO) {
        Voucher voucher = voucherMapper.toVoucher(voucherDTO);
        Voucher savedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(savedVoucher);
    }

    @Override
    public VoucherDTO order(String id, String username) {
        log.info("Voucher with id {} ordered", id);
        Voucher voucher = findVoucherById(id);

        if (voucher.getStatus() != VoucherStatus.UNREGISTERED)
            throw new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(),
                    "No voucher with id " + id + " UNREGISTERED");

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(StatusCodes.ENTITY_NOT_FOUND.name(),
                        "Entity not found"));

        voucher.setUser(user);
        voucher.setStatus(VoucherStatus.REGISTERED);
        Voucher savedVoucher = voucherRepository.save(voucher);

        return voucherMapper.toVoucherDTO(savedVoucher);
    }

    @Override
    public VoucherDTO update(String id, VoucherDTO voucherDTO) {
        Voucher voucher = findVoucherById(id);
        Voucher updatedVoucher = voucherMapper.toVoucher(voucherDTO);

        voucher.setTitle(updatedVoucher.getTitle());
        voucher.setDescription(updatedVoucher.getDescription());
        voucher.setPrice(updatedVoucher.getPrice());
        voucher.setTourType(updatedVoucher.getTourType());
        voucher.setTransferType(updatedVoucher.getTransferType());
        voucher.setHotelType(updatedVoucher.getHotelType());
        voucher.setStatus(updatedVoucher.getStatus());
        voucher.setArrivalDate(updatedVoucher.getArrivalDate());
        voucher.setEvictionDate(updatedVoucher.getEvictionDate());
        voucher.setUser(updatedVoucher.getUser());
        voucher.setIsHot(updatedVoucher.getIsHot());

        Voucher savedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(savedVoucher);
    }

    @Override
    public void delete(String voucherId) {
        Voucher voucher = findVoucherById(voucherId);
        voucherRepository.deleteById(String.valueOf(UUID.fromString(voucherId)));
    }

    @Override
    public VoucherDTO changeHotStatus(String id, String isHot) {
        Voucher voucher = findVoucherById(id);
        voucher.setIsHot(Boolean.parseBoolean(isHot));
        Voucher savedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(savedVoucher);
    }

    @Override
    public List<VoucherDTO> findAllByUserId(String userId) {
        return voucherRepository.findAllByUserId(String.valueOf(UUID.fromString(userId))).stream()
                .map(voucherMapper::toVoucherDTO).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> findAllByTourType(String tourType) {
        return voucherRepository.findAllByTourType(TourType.valueOf(tourType)).stream()
                .map(voucherMapper::toVoucherDTO).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> findAllByTransferType(String transferType) {
        return voucherRepository.findAllByTransferType(TransferType.valueOf(transferType)).stream()
                .map(voucherMapper::toVoucherDTO).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> findAllByPrice(String price) {
        return voucherRepository.findAllByPrice(Double.valueOf(price)).stream()
                .map(voucherMapper::toVoucherDTO).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> findAllByHotelType(String hotelType) {
        return voucherRepository.findAllByHotelType(HotelType.valueOf(hotelType)).stream()
                .map(voucherMapper::toVoucherDTO).collect(Collectors.toList());
    }

    @Override
    public List<VoucherDTO> findAll() {
        return processAndSortVouchers(voucherRepository.findAll());
    }

    @Override
    public List<VoucherDTO> findAllByStatus(String status) {
        return processAndSortVouchers(voucherRepository.findAllByStatus(VoucherStatus.valueOf(status)));
    }

    @Override
    public VoucherDTO changeStatus(String id, String status) {
        Voucher voucher = findVoucherById(id);
        voucher.setStatus(VoucherStatus.valueOf(status));
        Voucher savedVoucher = voucherRepository.save(voucher);
        return voucherMapper.toVoucherDTO(savedVoucher);
    }


    @Override
    public List<VoucherDTO> findAllByUsername(String username) {
        return voucherRepository.findAllByUserUsername(username).stream()
                .map(voucherMapper::toVoucherDTO).collect(Collectors.toList());
    }

    private List<VoucherDTO> processAndSortVouchers(List<Voucher> vouchers) {
        return vouchers.stream()
                .map(voucherMapper::toVoucherDTO)
                .sorted(Comparator.comparing(
                        dto -> "true".equalsIgnoreCase(dto.getIsHot()),
                        Comparator.reverseOrder()
                ))
                .collect(Collectors.toList());
    }

    private Voucher findVoucherById(String id) {
        return voucherRepository.findById(String.valueOf(UUID.fromString(id)))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Voucher with Id %s not found", id),
                        StatusCodes.ENTITY_NOT_FOUND.name()));
    }
}
