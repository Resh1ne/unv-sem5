package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.entity.Message;
import by.bsuir.pbz2.data.repository.MessageRepository;
import by.bsuir.pbz2.service.dto.MessageDto;
import by.bsuir.pbz2.service.exception.ResourceNotFoundException;
import by.bsuir.pbz2.service.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements by.bsuir.pbz2.service.MessageService {
    private final MessageRepository messageRepository;
    private final DataMapper dataMapper;

    @Override
    public MessageDto create(MessageDto dto) {
        Message message = dataMapper.toEntity(dto);
        Message messageCreated = messageRepository.save(message);
        return dataMapper.toDto(messageCreated);
    }

    @Override
    public MessageDto getById(Long id) {
        return messageRepository
                .findById(id)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Message with id " + id + " not found"));
    }

    @Override
    public List<MessageDto> getAll() {
        return messageRepository
                .findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public MessageDto update(MessageDto dto) {
        Message message = dataMapper.toEntity(dto);
        Message messageCreated = messageRepository.save(message);
        return dataMapper.toDto(messageCreated);
    }

    @Override
    public void delete(Long id) {
        if (!messageRepository.delete(id)) {
            throw new ResourceNotFoundException("No message with id: " + id);
        }
    }
}
