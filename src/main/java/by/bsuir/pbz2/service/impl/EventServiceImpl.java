package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.entity.Event;
import by.bsuir.pbz2.data.repository.EventRepository;
import by.bsuir.pbz2.service.EventService;
import by.bsuir.pbz2.service.dto.EventDto;
import by.bsuir.pbz2.service.exception.ResourceNotFoundException;
import by.bsuir.pbz2.service.mapper.DataMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final DataMapper dataMapper;

    @Override
    public EventDto create(EventDto dto) {
        String accessKey = generateAccessKey();
        Event event = dataMapper.toEntity(dto);
        event.setAccessKey(accessKey);
        Event eventCreated = eventRepository.save(event);
        return dataMapper.toDto(eventCreated);
    }

    @Override
    public EventDto getById(Long id) {
        return eventRepository
                .findById(id)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + id + " not found"));
    }

    @Transactional
    @Override
    public EventDto getEventByAccessKey(String key) {
        return eventRepository
                .findEventByAccessKey(key)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Event with key " + key + " not found"));
    }

    @Override
    public List<EventDto> getAll() {
        return eventRepository
                .findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public EventDto update(EventDto dto) {
        Event event = dataMapper.toEntity(dto);
        Event eventCreated = eventRepository.save(event);
        return dataMapper.toDto(eventCreated);
    }

    @Override
    public void delete(Long id) {
        if (!eventRepository.delete(id)) {
            throw new ResourceNotFoundException("No event with id: " + id);
        }
    }

    private String generateAccessKey() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder key = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            key.append(characters.charAt(random.nextInt(characters.length())));
        }
        return key.toString();
    }
}
