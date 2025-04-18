package com.epam.finaltask.controller.rest;


import com.epam.finaltask.dto.RemoteResponse;
import com.epam.finaltask.dto.UserDTO;
import com.epam.finaltask.exception.StatusCodes;
import com.epam.finaltask.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RemoteResponse> addUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO createdUserDto = userService.register(userDTO);
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "User is successfully registered",
                List.of(createdUserDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.CREATED);
    }


    @PatchMapping("/{username}")
    public ResponseEntity<RemoteResponse> updateUser(@PathVariable("username") String username,
                                                     @RequestBody @Valid UserDTO userDTO) {
        UserDTO updatedUserDto = userService.updateUser(username, userDTO);
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "User is successfully updated",
                List.of(updatedUserDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }


    @GetMapping("/{username}")
    public ResponseEntity<RemoteResponse> getUserByUsername(@PathVariable("username") String username) {
        UserDTO createdUserDto = userService.getUserByUsername(username);
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "User was obtained successfully",
                List.of(createdUserDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @GetMapping("/data")
    public ResponseEntity<RemoteResponse> getUserByHeader(
            @RequestHeader(value = "X-User-Name", required = false) String username) {
        UserDTO createdUserDto = userService.getUserByUsername(username);
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "User was obtained successfully",
                List.of(createdUserDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

    @PatchMapping("/{userId}/block")
    public ResponseEntity<RemoteResponse> block_unblockUser(@PathVariable String userId,
                                                            @RequestParam Boolean doBlock) {
        UserDTO userDto = userService.doBlockUser(userId, doBlock);
        RemoteResponse remoteResponse = RemoteResponse.create(
                true, StatusCodes.OK.name(), "User block changed to " + doBlock,
                List.of(userDto)
        );
        return new ResponseEntity<>(remoteResponse, HttpStatus.OK);
    }

}
