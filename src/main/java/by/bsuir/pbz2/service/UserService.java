package by.bsuir.pbz2.service;

import by.bsuir.pbz2.service.dto.UserDto;

import java.util.Optional;

public interface UserService extends CrudService<Long, UserDto> {
    Optional<UserDto> getByEmail(String email);
}
