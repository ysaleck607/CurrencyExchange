package com.example.devise.ChatRoom;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chatroom")
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "chatroomid")
    private Long id;
    @Column(name = "chatid")
    private Long chatId;
    @Column(name = "senderid")
    private Long senderId;
    @Column(name = "recepientid")
    private Long recipientId;
}

