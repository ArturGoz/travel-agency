package com.epam.finaltask.controller.rest.VoucherController;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/vouchers")
@RequiredArgsConstructor
public class AdminVoucherController {
    private final VoucherService voucherService;

    @PostMapping("/create")
    public ResponseEntity<RemoteResponse> createVoucher(@RequestBody VoucherDTO vDto) {
        VoucherDTO createdVDto = voucherService.create(vDto);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "Voucher is successfully created",
                List.of(createdVDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/change/{voucherId}")
    public ResponseEntity<RemoteResponse> updateVoucher(@PathVariable String voucherId,
                                                        @RequestBody VoucherDTO vDto) {
        VoucherDTO updatedVDto = voucherService.update(voucherId, vDto);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "Voucher is successfully updated",
                List.of(updatedVDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{voucherId}")
    public ResponseEntity<RemoteResponse> deleteVoucher(@PathVariable String voucherId) {
        voucherService.delete(voucherId);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(),
                String.format("Voucher with Id %s has been deleted", voucherId),
                null
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<RemoteResponse> getAllVouchersByStatus(@PathVariable String status) {
        List<VoucherDTO> voucherList = voucherService.findAllByStatus(status);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "voucherList is successfully obtained",
                voucherList
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }
}
