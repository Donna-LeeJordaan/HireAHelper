// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Message;
import java.time.LocalDateTime;

public class MessageFactory {
    public static Message createMessage(String messageId, LocalDateTime timeStamp, String content) {
        if (messageId == null || messageId.isEmpty()
                || timeStamp == null
                || content == null || content.isEmpty()) {
            return null;
        }

        return new Message.Builder()
                .setMessageId(messageId)
                .setTimeStamp(timeStamp)
                .setContent(content)
                .build();
    }
}
