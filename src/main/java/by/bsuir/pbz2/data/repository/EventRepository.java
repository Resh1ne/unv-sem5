package by.bsuir.pbz2.data.repository;

import by.bsuir.pbz2.data.entity.Event;

import java.util.Optional;

public interface EventRepository extends CrudRepository<Long, Event> {
    Optional<Event> findEventByAccessKey(String event);
}
