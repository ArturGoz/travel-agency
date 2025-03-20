package com.epam.finaltask.controller.rest;

import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.VoucherDTO;
import com.epam.finaltask.dto.VoucherOrderRequest;
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
@Slf4j
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
        log.info("Creating voucher : {}", vDto);
        VoucherDTO createdVDto = voucherService.create(vDto);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"Voucher is successfully created",
                List.of(createdVDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/change/{usernameId}")
    public ResponseEntity<RemoteResponse> updateVoucher(@PathVariable String usernameId,
                                                        @RequestBody VoucherDTO vDto) {
        VoucherDTO updatedVDto = voucherService.update(usernameId, vDto);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),"Voucher is successfully updated",
                List.of(updatedVDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @PatchMapping("/{usernameId}")
    public ResponseEntity<RemoteResponse> changeVoucherStatus(@PathVariable String usernameId,
                                                              @RequestBody VoucherDTO vDto) {

        VoucherDTO voucherDTO = voucherService.changeHotStatus(usernameId,vDto);
        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),
                "Voucher status is successfully changed",
                List.of(voucherDTO)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @DeleteMapping("/{usernameId}")
    public ResponseEntity<RemoteResponse> deleteVoucher(@PathVariable String usernameId) {
        voucherService.delete(usernameId);

        RemoteResponse remoteResponse = RemoteResponse.create(
                true,StatusCodes.OK.name(),
                String.format("Voucher with Id %s has been deleted", usernameId),
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
