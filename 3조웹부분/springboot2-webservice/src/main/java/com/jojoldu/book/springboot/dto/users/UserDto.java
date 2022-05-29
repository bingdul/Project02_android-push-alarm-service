package com.jojoldu.book.springboot.dto.users;

import com.jojoldu.book.springboot.domain.users.Users;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Users toEntity(){
        return Users.builder()
                .id(id)
                .username(username)
                .password(password)
                .build();
    }

    @Builder
    public UserDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}