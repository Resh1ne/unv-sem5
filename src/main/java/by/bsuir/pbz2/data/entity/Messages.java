package by.bsuir.pbz2.data.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Messages {
    private Long id;
    private Event event;
    private User user;
    private String content;
    private LocalDateTime createdAt;
}
