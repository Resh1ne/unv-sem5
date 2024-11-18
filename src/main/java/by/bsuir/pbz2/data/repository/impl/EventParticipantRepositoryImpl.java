package by.bsuir.pbz2.data.repository.impl;

import by.bsuir.pbz2.data.entity.EventParticipant;
import by.bsuir.pbz2.data.repository.EventParticipantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class EventParticipantRepositoryImpl implements EventParticipantRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<EventParticipant> findById(Long id) {
        return Optional.ofNullable(manager.find(EventParticipant.class, id));
    }

    @Override
    public List<EventParticipant> findAll() {
        return manager.createQuery("from EventParticipant", EventParticipant.class).getResultList();
    }

    @Override
    public EventParticipant save(EventParticipant entity) {
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
        EventParticipant eventParticipant = manager.find(EventParticipant.class, id);
        if (eventParticipant == null) {
            return false;
        }
        manager.remove(eventParticipant);
        return true;
    }
}
