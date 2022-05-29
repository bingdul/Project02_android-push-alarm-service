package com.jojoldu.book.springboot.domain.users;

import com.jojoldu.book.springboot.domain.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private String fcmtoken;
    @CreationTimestamp private LocalDateTime createdDate;

    public void fcmTokenUpdate(String fcmtoken){
        this.fcmtoken=fcmtoken;
    }

}
