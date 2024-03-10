package com.example.devise.Chat;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Chatmessage")
@Getter
@Setter
public class ChatMessage {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "id")
    private Long id;
    @Column(name = "chatid")
    private Long chatId;
    @Column(name = "senderid")
    private Long senderId;
    @Column(name = "recepientid")
    private Long recipientId;
    @Column(name = "content")
    private String content;
    @Column(name = "datecreation")
    private Date timestamp;
}
