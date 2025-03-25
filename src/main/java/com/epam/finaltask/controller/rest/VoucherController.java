package com.epam.finaltask.controller.rest;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.VoucherOrderRequest;
import com.epam.finaltask.dto.VoucherRequest;
import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;

    @GetMapping("/list")
    public ResponseEntity<RemoteResponse> getAllVouchers() {
        List<VoucherDTO> voucherDTOList = voucherService.findAll();

        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(),"voucherList is successfully obtained",
                voucherDTOList
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<RemoteResponse> getAllVouchersByStatus(@PathVariable String status) {
        List<VoucherDTO> voucherList = voucherService.findAllByStatus(status);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"voucherList is successfully obtained",
                voucherList
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @GetMapping("/{usernameId}")
    public ResponseEntity<RemoteResponse> getAllVouchers(@PathVariable String usernameId) {
        List<VoucherDTO> voucherList = voucherService.findAllByUserId(usernameId);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"voucherList is successfully obtained",
                voucherList
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @GetMapping("/userList")
    public ResponseEntity<RemoteResponse> getAllVouchersByJwtForUser(
            @RequestHeader(value = "X-User-Name", required = false) String username) {
        List<VoucherDTO> voucherList = voucherService.findAllByUsername(username);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"voucherList is successfully obtained",
                voucherList
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RemoteResponse> createVoucher(@RequestBody VoucherDTO vDto) {
        VoucherDTO createdVDto = voucherService.create(vDto);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"Voucher is successfully created",
                List.of(createdVDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/change/{voucherId}")
    public ResponseEntity<RemoteResponse> updateVoucher(@PathVariable String voucherId,
                                                        @RequestBody VoucherDTO vDto) {
        VoucherDTO updatedVDto = voucherService.update(voucherId, vDto);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"Voucher is successfully updated",
                List.of(updatedVDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @PostMapping("/changeStatus")
    public ResponseEntity<RemoteResponse> changeVoucherStatus(@RequestBody VoucherRequest voucherRequest) {
        VoucherDTO voucherDTO = voucherService.changeStatus(voucherRequest.getVoucherId(),
                (String) voucherRequest.getAdditionalDetails().get(0));
        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),
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
                true,StatusCodes.OK.name(),
                "Voucher hot status is successfully changed",
                List.of(voucherDTO)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{voucherId}")
    public ResponseEntity<RemoteResponse> deleteVoucher(@PathVariable String voucherId) {
        voucherService.delete(voucherId);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),
                String.format("Voucher with Id %s has been deleted", voucherId),
                null
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @PostMapping("/order")
    public ResponseEntity<RemoteResponse> orderVoucher(
            @RequestHeader(value = "X-User-Name", required = false) String username,
            @RequestBody VoucherOrderRequest voucherOrderRequest) {
        VoucherDTO voucherDTO = voucherService.order(voucherOrderRequest.getVoucherId(),username);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),
                "voucher was successfully ordered",
                List.of(voucherDTO)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);

    }
}
