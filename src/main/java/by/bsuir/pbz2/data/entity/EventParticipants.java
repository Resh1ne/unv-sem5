package by.bsuir.pbz2.data.entity;

import by.bsuir.pbz2.data.entity.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventParticipants {
    private Long id;
    private Event event;
    private User user;
    private Role role;
    private LocalDateTime joinedAt;
}
