package by.bsuir.pbz2.data.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private Long id;
    private String title;
    private String description;
    private Integer maxViewer;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String accessKey;
    private User host;
}
