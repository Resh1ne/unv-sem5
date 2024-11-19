package by.bsuir.pbz2.service.mapper.impl;

import by.bsuir.pbz2.data.entity.Event;
import by.bsuir.pbz2.data.entity.EventParticipant;
import by.bsuir.pbz2.data.entity.Message;
import by.bsuir.pbz2.data.entity.User;
import by.bsuir.pbz2.service.dto.EventDto;
import by.bsuir.pbz2.service.dto.EventParticipantDto;
import by.bsuir.pbz2.service.dto.MessageDto;
import by.bsuir.pbz2.service.dto.UserDto;
import by.bsuir.pbz2.service.mapper.DataMapper;
import org.springframework.stereotype.Component;

@Component
public class DataMapperImpl implements DataMapper {
    @Override
    public Event toEntityWithId(EventDto dto) {
        Event event = toEntity(dto);
        event.setId(dto.getId());
        return event;
    }

    @Override
    public Event toEntity(EventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setMaxViewers(dto.getMaxViewers());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setAccessKey(dto.getAccessKey());
        event.setHost(toEntityWithId(dto.getHost()));
        return event;
    }

    @Override
    public EventDto toDto(Event entity) {
        EventDto eventDto = new EventDto();
        eventDto.setId(entity.getId());
        eventDto.setTitle(entity.getTitle());
        eventDto.setDescription(entity.getDescription());
        eventDto.setMaxViewers(entity.getMaxViewers());
        eventDto.setStartTime(entity.getStartTime());
        eventDto.setEndTime(entity.getEndTime());
        eventDto.setAccessKey(entity.getAccessKey());
        eventDto.setHost(toDto(entity.getHost()));
        return eventDto;
    }

    @Override
    public User toEntityWithId(UserDto dto) {
        User user = toEntity(dto);
        user.setId(dto.getId());
        return user;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCreatedAt(dto.getCreatedAt());
        return user;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setUsername(entity.getUsername());
        userDto.setEmail(entity.getEmail());
        userDto.setPassword(entity.getPassword());
        userDto.setCreatedAt(entity.getCreatedAt());
        return userDto;
    }

    @Override
    public EventParticipant toEntity(EventParticipantDto dto) {
        EventParticipant eventParticipant = new EventParticipant();
        eventParticipant.setEvent(toEntityWithId(dto.getEvent()));
        eventParticipant.setUser(toEntityWithId(dto.getUser()));
        eventParticipant.setRole(dto.getRole());
        eventParticipant.setJoinedAt(dto.getJoinedAt());
        return eventParticipant;
    }

    @Override
    public EventParticipantDto toDto(EventParticipant entity) {
        EventParticipantDto eventParticipantDto = new EventParticipantDto();
        eventParticipantDto.setId(entity.getId());
        eventParticipantDto.setEvent(toDto(entity.getEvent()));
        eventParticipantDto.setUser(toDto(entity.getUser()));
        eventParticipantDto.setRole(entity.getRole());
        eventParticipantDto.setJoinedAt(entity.getJoinedAt());
        return eventParticipantDto;
    }

    @Override
    public Message toEntity(MessageDto dto) {
        Message message = new Message();
        message.setEvent(toEntityWithId(dto.getEvent()));
        message.setUser(toEntityWithId(dto.getUser()));
        message.setContent(dto.getContent());
        message.setCreatedAt(dto.getCreatedAt());
        return message;
    }

    @Override
    public MessageDto toDto(Message entity) {
        MessageDto messageDto = new MessageDto();
        messageDto.setId(entity.getId());
        messageDto.setEvent(toDto(entity.getEvent()));
        messageDto.setUser(toDto(entity.getUser()));
        messageDto.setContent(entity.getContent());
        messageDto.setCreatedAt(entity.getCreatedAt());
        return messageDto;
    }
}
