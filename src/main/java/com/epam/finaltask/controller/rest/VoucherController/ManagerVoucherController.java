package com.epam.finaltask.controller.rest.VoucherController;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.VoucherRequest;
import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/vouchers")
@RequiredArgsConstructor
public class ManagerVoucherController {
    private final VoucherService voucherService;

    @GetMapping("/list")
    public ResponseEntity<RemoteResponse> getAllVouchers() {
        List<VoucherDTO> voucherDTOList = voucherService.findAll();

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "voucherList is successfully obtained",
                voucherDTOList
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @PostMapping("/changeStatus")
    public ResponseEntity<RemoteResponse> changeVoucherStatus(@RequestBody VoucherRequest voucherRequest) {
        VoucherDTO voucherDTO = voucherService.changeStatus(voucherRequest.getVoucherId(),
                (String) voucherRequest.getAdditionalDetails().get(0));
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(),
                "Voucher status is successfully changed",
                List.of(voucherDTO)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @PostMapping("/changeHotStatus")
    public ResponseEntity<RemoteResponse> changeVoucherHotStatus(@RequestBody VoucherRequest voucherRequest) {
        VoucherDTO voucherDTO = voucherService.changeHotStatus(voucherRequest.getVoucherId(),
                (String) voucherRequest.getAdditionalDetails().get(0));
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(),
                "Voucher hot status is successfully changed",
                List.of(voucherDTO)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }
}
