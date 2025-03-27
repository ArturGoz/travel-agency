package com.epam.finaltask.service;

import com.epam.finaltask.dto.UserDTO;

public interface UserService {
    UserDTO register(UserDTO userDTO);

    UserDTO updateUser(String username, UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    UserDTO changeAccountStatus(UserDTO userDTO);

    UserDTO getUserById(String id);

    UserDTO doBlockUser(String id, Boolean doBlock);
}
