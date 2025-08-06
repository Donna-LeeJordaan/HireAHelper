// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.factory;

import za.co.hireahelper.domain.Client;
import za.co.hireahelper.domain.Message;
import za.co.hireahelper.domain.ServiceProvider;
import za.co.hireahelper.util.Helper;

import java.time.LocalDateTime;

public class MessageFactory {

    public static Message createMessage(String messageId, LocalDateTime timeStamp,
                                        String content, Client client,
                                        ServiceProvider serviceProvider) {
//jjj
        if (Helper.isNullOrEmpty(messageId) ||
                Helper.isNullOrEmpty(content) ||
                timeStamp == null) {
            return null;
        }

        return new Message.Builder()
                .setMessageId(messageId)
                .setTimeStamp(timeStamp)
                .setContent(content)
                .setClient(client)
                .setServiceProvider(serviceProvider)
                .build();
    }

    public static Message createMessage(String msg001, LocalDateTime now, String s) {
        return null;
    }

    public static Message buildMessage(String msg01, String client01, String provider01, String s) {
   return null; }
}
