//Gabriel Kiewietz
//230990703
//10 July 2025

package za.co.hireahelper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.repository.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    @Autowired
    public MessageServiceImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message create(Message message) {
        return this.repository.save(message);
    }

    @Override
    public Message read(String messageId) {
        return this.repository.findById(messageId).orElse(null);
    }

    @Override
    public Message update(Message message) {
        return this.repository.save(message);
    }

    @Override
    public boolean delete(String messageId) {
        if (this.repository.existsById(messageId)) {
            this.repository.deleteById(messageId);
            return true;
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        return this.repository.findAll();
    }
}

