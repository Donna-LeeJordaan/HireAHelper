//Gabriel Kiewietz
// 230990703
// 10 July 2025

package za.co.hireahelper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.service.MessageService;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService service;

    @PostMapping
    public Message create(@RequestBody Message message) {
        return service.create(message);
    }

    @GetMapping("/{messageId}")
    public Message read(@PathVariable String messageId) {
        return service.read(messageId);
    }

    @PutMapping
    public Message update(@RequestBody Message message) {
        return service.update(message);
    }

    @DeleteMapping("/{messageId}")
    public boolean delete(@PathVariable String messageId) {
        return service.delete(messageId);
    }

    @GetMapping
    public List<Message> getAll() {
        return service.getAll();
    }
}