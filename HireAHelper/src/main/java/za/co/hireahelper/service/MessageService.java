
package za.co.hireahelper.service;

import za.co.hireahelper.domain.Message;
import java.util.List;

public interface MessageService extends IService<Message, String> {
    List<Message> getAll();
}

