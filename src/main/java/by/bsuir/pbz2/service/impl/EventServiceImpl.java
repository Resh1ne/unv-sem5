package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.entity.Event;
import by.bsuir.pbz2.data.repository.EventRepository;
import by.bsuir.pbz2.service.EventService;
import by.bsuir.pbz2.service.dto.EventDto;
import by.bsuir.pbz2.service.exception.ResourceNotFoundException;
import by.bsuir.pbz2.service.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final DataMapper dataMapper;

    @Override
    public EventDto create(EventDto dto) {
        Event event = dataMapper.toEntity(dto);
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
}
