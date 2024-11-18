package by.bsuir.pbz2.data.repository.impl;

import by.bsuir.pbz2.data.entity.Message;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MessageRepositoryImpl implements by.bsuir.pbz2.data.repository.MessageRepository {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(manager.find(Message.class, id));
    }

    @Override
    public List<Message> findAll() {
        return manager.createQuery("from Message", Message.class).getResultList();
    }

    @Override
    public Message save(Message entity) {
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
        Message message = manager.find(Message.class, id);
        if (message == null) {
            return false;
        }
        manager.remove(message);
        return true;
    }
}
