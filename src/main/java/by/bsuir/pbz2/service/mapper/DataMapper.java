package by.bsuir.pbz2.service.mapper;

import by.bsuir.pbz2.data.entity.Event;
import by.bsuir.pbz2.data.entity.EventParticipant;
import by.bsuir.pbz2.data.entity.Message;
import by.bsuir.pbz2.data.entity.User;
import by.bsuir.pbz2.service.dto.EventDto;
import by.bsuir.pbz2.service.dto.EventParticipantDto;
import by.bsuir.pbz2.service.dto.MessageDto;
import by.bsuir.pbz2.service.dto.UserDto;

public interface DataMapper {
    Event toEntityWithId(EventDto dto);
    Event toEntity(EventDto dto);
    EventDto toDto(Event entity);
    User toEntityWithId(UserDto dto);
    User toEntity(UserDto dto);
    UserDto toDto(User entity);
    EventParticipant toEntity(EventParticipantDto dto);
    EventParticipantDto toDto(EventParticipant entity);
    Message toEntity(MessageDto dto);
    MessageDto toDto(Message entity);
}
