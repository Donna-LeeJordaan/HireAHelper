// Gabriel Kiewietz
// 230990703
// 18 May 2024

package za.co.hireahelper.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    private String messageId;
    private LocalDateTime timeStamp;
    private String content;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    protected Message() {}

    private Message(Builder builder) {
        this.messageId = builder.messageId;
        this.timeStamp = builder.timeStamp;
        this.content = builder.content;
    }

    public String getMessageId() {
        return messageId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId='" + messageId + '\'' +
                ", timeStamp=" + timeStamp +
                ", content='" + content + '\'' +
                '}';
    }

    public static class Builder {
        private String messageId;
        private LocalDateTime timeStamp;
        private String content;

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public Builder setTimeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder copy(Message message) {
            this.messageId = message.messageId;
            this.timeStamp = message.timeStamp;
            this.content = message.content;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
