package by.bsuir.pbz2.service;

import by.bsuir.pbz2.service.dto.EventDto;

import java.util.Optional;

public interface EventService extends CrudService<Long, EventDto> {
    EventDto getEventByAccessKey(String key);

}
