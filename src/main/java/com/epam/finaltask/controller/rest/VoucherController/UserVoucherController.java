package com.epam.finaltask.controller.rest.VoucherController;

import com.epam.finaltask.dto.PaginatedResponse;
import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.VoucherRequest;
import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.model.Voucher;
import com.epam.finaltask.model.VoucherStatus;
import com.epam.finaltask.service.VoucherService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/vouchers")
@RequiredArgsConstructor
public class UserVoucherController {
    private final VoucherService voucherService;

    @GetMapping
    public ResponseEntity<RemoteResponse> getVouchers(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String status) {

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Voucher> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) predicates.add(cb.equal(root.get("status"), VoucherStatus.valueOf(status)));

            if (search != null && !search.trim().isEmpty()) {
                String searchTerm = "%" + search.trim().toLowerCase() + "%";
                predicates.add(cb.like(
                        cb.lower(root.get("title")),
                        searchTerm
                ));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Voucher> voucherPage = voucherService.findAll(spec, pageable);

        PaginatedResponse<Voucher> response = new PaginatedResponse<>(
                voucherPage.getContent(),
                voucherPage.getNumber(),
                voucherPage.getSize(),
                voucherPage.getTotalElements(),
                voucherPage.getTotalPages()
        );

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "voucherList is successfully obtained",
                List.of(response)
        );

        return ResponseEntity.ok(remoteResponse);
    }

    @PostMapping("/order")
    public ResponseEntity<RemoteResponse> orderVoucher(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestBody VoucherRequest voucherOrderRequest) {
        VoucherDTO voucherDTO = voucherService.order(voucherOrderRequest.getVoucherId(), username);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(),
                "voucher was successfully ordered",
                List.of(voucherDTO)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);

    }
}
