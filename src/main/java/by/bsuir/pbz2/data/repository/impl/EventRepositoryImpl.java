package by.bsuir.pbz2.data.repository.impl;

import by.bsuir.pbz2.data.entity.Event;
import by.bsuir.pbz2.data.repository.EventRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EventRepositoryImpl implements EventRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Event> findById(Long id) {
        return Optional.ofNullable(manager.find(Event.class, id));
    }

    @Override
    public List<Event> findAll() {
        return manager.createQuery("from Event", Event.class).getResultList();
    }

    @Override
    public Event save(Event entity) {
        Long id = entity.getId();
        if (id == null) {
            manager.persist(entity);
        } else {
            manager.merge(entity);
        }
        return entity;
    }

    @Override
    public boolean delete(Long id) {
        Event event = manager.find(Event.class, id);
        if (event == null) {
            return false;
        }
        manager.remove(event);
        return true;
    }
}
