package by.bsuir.pbz2.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private Long id;
    private String title;
    private String description;
    private Integer maxViewers = 100;
    private LocalDateTime startTime = LocalDateTime.now();
    private LocalDateTime endTime;
    private String accessKey;
    private UserDto host;
}
