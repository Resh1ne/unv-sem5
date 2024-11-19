package by.bsuir.pbz2.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private EventDto event;
    private UserDto user;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
}
