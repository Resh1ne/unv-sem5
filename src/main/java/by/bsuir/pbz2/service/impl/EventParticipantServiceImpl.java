package by.bsuir.pbz2.service.impl;

import by.bsuir.pbz2.data.entity.EventParticipant;
import by.bsuir.pbz2.data.repository.EventParticipantRepository;
import by.bsuir.pbz2.service.EventParticipantService;
import by.bsuir.pbz2.service.dto.EventParticipantDto;
import by.bsuir.pbz2.service.exception.ResourceNotFoundException;
import by.bsuir.pbz2.service.mapper.DataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventParticipantServiceImpl implements EventParticipantService {
    private final EventParticipantRepository eventParticipantRepository;
    private final DataMapper dataMapper;

    @Override
    public EventParticipantDto create(EventParticipantDto dto) {
        EventParticipant eventParticipant = dataMapper.toEntity(dto);
        EventParticipant eventParticipantCreated = eventParticipantRepository.save(eventParticipant);
        return dataMapper.toDto(eventParticipantCreated);
    }

    @Override
    public EventParticipantDto getById(Long id) {
        return eventParticipantRepository
                .findById(id)
                .map(dataMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Event participants with id " + id + " not found"));
    }

    @Override
    public List<EventParticipantDto> getAll() {
        return eventParticipantRepository
                .findAll()
                .stream()
                .map(dataMapper::toDto)
                .toList();
    }

    @Override
    public EventParticipantDto update(EventParticipantDto dto) {
        EventParticipant eventParticipant = dataMapper.toEntity(dto);
        EventParticipant eventParticipantCreated = eventParticipantRepository.save(eventParticipant);
        return dataMapper.toDto(eventParticipantCreated);
    }

    @Override
    public void delete(Long id) {
        if (!eventParticipantRepository.delete(id)) {
            throw new ResourceNotFoundException("No event participant with id: " + id);
        }
    }
}
