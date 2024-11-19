package by.bsuir.pbz2.service.dto;

import by.bsuir.pbz2.data.entity.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventParticipantDto {
    private Long id;
    private EventDto event;
    private UserDto user;
    private Role role;
    private LocalDateTime joinedAt = LocalDateTime.now();
}
