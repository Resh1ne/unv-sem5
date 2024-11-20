package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.entity.User;
import by.bsuir.pbz2.data.repository.UserRepository;
import by.bsuir.pbz2.service.UserService;
import by.bsuir.pbz2.service.dto.UserDto;
import by.bsuir.pbz2.service.exception.AppException;
import by.bsuir.pbz2.service.exception.ResourceNotFoundException;
import by.bsuir.pbz2.service.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DataMapper dataMapper;

    @Override
    public UserDto create(UserDto dto) {
        User user = dataMapper.toEntity(dto);
        User userCreated = userRepository.save(user);
        return dataMapper.toDto(userCreated);
    }

    @Override
    public UserDto getById(Long id) {
        return userRepository
                .findById(id)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public UserDto update(UserDto dto) {
        User user = dataMapper.toEntity(dto);
        User userCreated = userRepository.save(user);
        return dataMapper.toDto(userCreated);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.delete(id)) {
            throw new ResourceNotFoundException("No user with id: " + id);
        }
    }

    @Override
    public Optional<UserDto> getByEmail(String email) {
        return Optional.ofNullable(userRepository
                .findByEmail(email)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found")));
    }

    @Override
    public UserDto login(String email, String password) {
        return userRepository.findAll()
                .stream()
                .map(dataMapper::toDto)
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new AppException("Account not found"));
    }
}
